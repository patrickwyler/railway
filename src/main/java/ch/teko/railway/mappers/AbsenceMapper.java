package ch.teko.railway.mappers;

import ch.teko.railway.dtos.AbsenceDto;
import ch.teko.railway.entities.Absence;
import ch.teko.railway.models.AbsenceModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper for populating data between DTO<->Model<->Entity
 */
@Component
@Mapper(componentModel = "spring")
public interface AbsenceMapper {

    Absence absenceModelToAbsence(AbsenceModel absenceModel);

    AbsenceModel absenceToAbsenceModel(Absence absence);

    AbsenceModel absenceDtoToAbsenceModel(AbsenceDto absenceDto);

    List<AbsenceModel> absencesToAbsenceModels(List<Absence> absences);

    List<AbsenceDto> absenceModelsToAbsenceDtos(List<AbsenceModel> absenceModels);

    List<Absence> absenceModelsToAbsence(List<AbsenceModel> absenceModels);

}
