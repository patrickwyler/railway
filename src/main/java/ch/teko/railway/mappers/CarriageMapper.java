package ch.teko.railway.mappers;

import ch.teko.railway.dtos.CarriageDto;
import ch.teko.railway.entities.Carriage;
import ch.teko.railway.models.CarriageModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper for populating data between DTO<->Model<->Entity
 */
@Component
@Mapper(componentModel = "spring")
public interface CarriageMapper {

    Carriage carriageModelToCarriage(CarriageModel carriageModel);

    CarriageModel carriageToCarriageModel(Carriage carriage);

    CarriageModel carriageDtoToCarriageModel(CarriageDto carriageDto);

    CarriageDto carriageModelToCarriageDto(CarriageModel carriageModel);

    List<CarriageModel> carriagesToCarriageModels(List<Carriage> carriages);

    List<CarriageDto> carriageModelsToCarriageDtos(List<CarriageModel> carriageModels);

    List<Carriage> carriageModelsToCarriages(List<CarriageModel> carriageModels);
}
