package ch.teko.railway.mappers;

import ch.teko.railway.dtos.StationDto;
import ch.teko.railway.entities.Station;
import ch.teko.railway.models.StationModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper for populating data between DTO<->Model<->Entity
 */
@Component
@Mapper(componentModel = "spring", uses = {TrainMapper.class})
public interface StationMapper {

    Station stationModelToStation(StationModel stationModel);

    StationModel stationToStationModel(Station station);

    StationModel stationDtoToStationModel(StationDto stationDto);

    StationDto stationModelToStationDto(StationModel stationModel);

    List<StationDto> stationModelsToStationDtos(List<StationModel> stationModels);

    List<StationModel> stationsToStationModels(List<Station> stations);

}
