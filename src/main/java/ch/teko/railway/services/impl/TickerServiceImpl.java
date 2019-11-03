package ch.teko.railway.services.impl;

import ch.teko.railway.models.StationModel;
import ch.teko.railway.models.TrainModel;
import ch.teko.railway.services.StationService;
import ch.teko.railway.services.TickerService;
import com.google.common.collect.Iterables;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@Getter(value = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TickerServiceImpl implements TickerService {

    @Getter(value = AccessLevel.PUBLIC)
    LocalTime time;

    // all active trains while generating the timetable
    @Getter(value = AccessLevel.PUBLIC)
    Set<TrainModel> trainModels;

    // all used trains
    @Getter(value = AccessLevel.PUBLIC)
    Set<TrainModel> usedTrainModels;

    HashMap<Long, StationModel> stationStore = new HashMap<>();

    StationService stationService;

    public TickerServiceImpl( StationService stationService) {
        this.stationService = stationService;

        initalize();
    }

    public void initalize() {
        time = LocalTime.of(0, 0);
        trainModels = new HashSet<>();
        usedTrainModels = new HashSet<>();

        // add all stations to station store
        getStationService().getAllStations().forEach(stationModel -> set(stationModel.getId(), stationModel));
    }

    public void tick() {
        time = time.plusMinutes(1);

        trainModels.forEach(TrainModel::onTick);
    }

    public void add(final TrainModel train) {
        // create runtime id
        train.setRuntimeId(UUID.randomUUID().toString());

        log.info("Added train with uuid to ticker: " + train.getRuntimeId()+" name "+train.getName());

        trainModels.add(train);
        usedTrainModels.add(train);
    }

    public void removeStoppedTrains() {
        final Set<TrainModel> currentTrainModels = new HashSet<>(trainModels);

        currentTrainModels.stream()
                .filter(TrainModel::isStopDriving)
                .forEach(this::remove);
    }

    private void remove(final TrainModel train) {
        log.info("Removed train with uuid from ticker: " + train.getRuntimeId()+" name "+train.getName());

        // add train to repository of endstation
        StationModel endstationForTrain = get(((StationModel) Iterables.getLast(train.getPath())).getId());
        endstationForTrain.addTrain(train);
        set(endstationForTrain.getId(), endstationForTrain);

        train.reset();
        trainModels.remove(train);
    }

    public StationModel get(long id){
        return getStationStore().get(id);
    }

    public void set(long id, StationModel stationModel){
        getStationStore().put(id, stationModel);
    }
}