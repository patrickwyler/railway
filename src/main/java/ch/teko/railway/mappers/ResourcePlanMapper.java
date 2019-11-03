package ch.teko.railway.mappers;

import ch.teko.railway.dtos.ResourcePlanDto;
import ch.teko.railway.models.ResourcePlanModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for populating data from Model->DTO
 */
@Component
@Mapper(componentModel = "spring")
public interface ResourcePlanMapper {

    ResourcePlanDto resourcePlanModelToResourcePlanDto(ResourcePlanModel resourcePlanModel);
}
