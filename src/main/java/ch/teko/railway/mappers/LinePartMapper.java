package ch.teko.railway.mappers;

import ch.teko.railway.dtos.LinePartDto;
import ch.teko.railway.dtos.StationDto;
import ch.teko.railway.dtos.TraceDto;
import ch.teko.railway.entities.LinePart;
import ch.teko.railway.entities.Station;
import ch.teko.railway.entities.Trace;
import ch.teko.railway.models.LinePartModel;
import ch.teko.railway.models.StationModel;
import ch.teko.railway.models.TraceModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for populating data between DTO<->Model<->Entity
 */
@Component
@Mapper(componentModel = "spring", uses = {TrainMapper.class, StationMapper.class})
public interface LinePartMapper {

    Station stationModelToStation(StationModel stationModel);

    StationModel stationToStationModel(Station station);

    StationModel stationDtoToStationModel(StationDto stationDto);

    StationDto stationModelToStationDto(StationModel stationModel);

    Trace traceModelToTrace(TraceModel traceModel);

    TraceModel traceToTraceModel(Trace trace);

    TraceModel traceDtoToTraceModel(TraceDto traceDto);

    TraceDto traceModelToTraceDto(TraceModel traceModel);

    default LinePartModel map(LinePartDto source) {
        if (source instanceof StationDto) {
            return stationDtoToStationModel((StationDto) source);
        }
        return traceDtoToTraceModel((TraceDto) source);
    }

    default LinePart mapToLinePart(LinePartModel source) {
        if (source instanceof StationModel) {
            return stationModelToStation((StationModel) source);
        }
        return traceModelToTrace((TraceModel) source);
    }

    default LinePartDto mapToLinePartDto(LinePartModel source) {
        if (source instanceof StationModel) {
            return stationModelToStationDto((StationModel) source);
        }
        return traceModelToTraceDto((TraceModel) source);
    }

    default LinePartModel mapToLinePartModel(LinePartDto source) {
        if (source instanceof StationDto) {
            return stationDtoToStationModel((StationDto) source);
        }
        return traceDtoToTraceModel((TraceDto) source);
    }

    default LinePartModel mapToLinePartModel2(LinePart source) {
        if (source instanceof Station) {
            return stationToStationModel((Station) source);
        }
        return traceToTraceModel((Trace) source);
    }

    default List<LinePartDto> mapChildrenListOfLinePartModelsToListOfLinePartDtos(List<LinePartModel> source) {
        List<LinePartDto> dtos = new ArrayList<>();
        source.forEach(sourceElement -> dtos.add(mapToLinePartDto(sourceElement)));
        return dtos;
    }

    default List<LinePartModel> mapChildrenListOfLinePartDtosToListOfLinePartModels(List<LinePartDto> source) {
        List<LinePartModel> dtos = new ArrayList<>();
        source.forEach(sourceElement -> dtos.add(mapToLinePartModel(sourceElement)));
        return dtos;
    }

    default List<LinePartModel> mapChildrenListOfLinePartsToListOfLinePartModels(List<LinePart> source) {
        List<LinePartModel> dtos = new ArrayList<>();
        source.forEach(sourceElement -> dtos.add(mapToLinePartModel2(sourceElement)));
        return dtos;
    }

    default List<LinePart> mapChildrenListOfLinePartModelsToListOfLineParts(List<LinePartModel> source) {
        List<LinePart> dtos = new ArrayList<>();
        source.forEach(sourceElement -> dtos.add(mapToLinePart(sourceElement)));
        return dtos;
    }
}
