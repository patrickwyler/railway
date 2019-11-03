package ch.teko.railway.services.impl;

import ch.teko.railway.entities.Station;
import ch.teko.railway.mappers.StationMapper;
import ch.teko.railway.models.StationModel;
import ch.teko.railway.repositories.StationRepository;
import ch.teko.railway.services.StationService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@Getter(value = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationServiceImpl implements StationService {

    StationRepository stationRepository;
    StationMapper stationMapper;

    @Autowired
    public StationServiceImpl(StationRepository stationRepository, StationMapper stationMapper) {
        this.stationRepository = stationRepository;
        this.stationMapper = stationMapper;
    }

    @Override
    public List<StationModel> getAllStations() {
        return getStationMapper().stationsToStationModels(getStationRepository().findAll());
    }

    @Override
    public boolean existsRootStation() {
        return getStationRepository().existsRootStation();
    }

    @Override
    public StationModel createStation(final StationModel stationModel) {
        final boolean existsRootStation = getStationRepository().existsRootStation();

        if (!existsRootStation) {
            return saveStationModel(stationModel);
        }

        if (checkStation(stationModel)) {
            // create new station
            return saveStationModel(stationModel);
        }

        return StationModel.builder()
                .errorMessage("Can't create station!")
                .build();

    }

    private boolean checkStation(final StationModel stationModel) {
        return !stationModel.isRootStation();
    }

    private StationModel saveStationModel(final StationModel stationModel) {
        final Station station = getStationRepository().save(getStationMapper().stationModelToStation(stationModel));
        return getStationMapper().stationToStationModel(station);
    }

    @Override
    public StationModel getStationById(long id) {
        final Station station = getStationRepository().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid station Id:" + id));

        return getStationMapper().stationToStationModel(station);
    }

    @Override
    public StationModel updateStation(StationModel stationModel) {
        getStationById(stationModel.getId());

        Station station = getStationRepository().save(getStationMapper().stationModelToStation(stationModel));

        return getStationMapper().stationToStationModel(station);
    }

    @Override
    public void deleteStationById(long id) {
        getStationRepository().deleteById(id);
    }
}
