package ch.teko.railway.mappers;

import ch.teko.railway.dtos.TraceDto;
import ch.teko.railway.entities.Trace;
import ch.teko.railway.models.TraceModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper for populating data between DTO<->Model<->Entity
 */
@Component
@Mapper(componentModel = "spring", uses = {TrainMapper.class})
public interface TraceMapper {

    Trace traceModelToTrace(TraceModel traceModel);

    TraceModel traceToTraceModel(Trace trace);

    TraceModel traceDtoToTraceModel(TraceDto traceDto);

    TraceDto traceModelToTraceDto(TraceModel traceModel);

    List<TraceDto> traceModelsToTraceDtos(List<TraceModel> traceModels);

    List<TraceModel> tracesToTraceModels(List<Trace> traces);

}
