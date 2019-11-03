package ch.teko.railway.mappers;

import ch.teko.railway.dtos.WorkScheduleDto;
import ch.teko.railway.models.WorkScheduleModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for populating data from Model->DTO
 */
@Component
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface WorkScheduleMapper {

    WorkScheduleDto workScheduleModelToWorkScheduleDto(WorkScheduleModel workScheduleModel);

}
