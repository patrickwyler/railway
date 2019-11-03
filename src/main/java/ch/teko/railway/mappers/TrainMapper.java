package ch.teko.railway.mappers;

import ch.teko.railway.dtos.TrainDto;
import ch.teko.railway.entities.Train;
import ch.teko.railway.models.TrainModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper for populating data between DTO<->Model<->Entity
 */
@Component
@Mapper(componentModel = "spring",
        uses = {StationMapper.class})
public interface TrainMapper {

    Train trainModelToTrain(TrainModel trainModel);

    TrainModel trainToTrainModel(Train train);

    TrainModel trainDtoToTrainModel(TrainDto trainDto);

    TrainDto trainModelToTrainDto(TrainModel trainModel);

    List<TrainModel> trainsToTrainModels(List<Train> trains);

    List<TrainDto> trainModelsToTrainDtos(List<TrainModel> trainModels);

    List<Train> trainModelsToTrains(List<TrainModel> trainModels);

}
