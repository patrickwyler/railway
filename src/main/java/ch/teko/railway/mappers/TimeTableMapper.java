package ch.teko.railway.mappers;

import ch.teko.railway.dtos.TimeTableDto;
import ch.teko.railway.models.TimeTableModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for populating data from Model->DTO
 */
@Component
@Mapper(componentModel = "spring", uses = {TrainMapper.class})
public interface TimeTableMapper {

    TimeTableDto timeTableModelToTimeTableDto(TimeTableModel timeTableModel);

}
