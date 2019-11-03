package ch.teko.railway.mappers;

import ch.teko.railway.dtos.LineDto;
import ch.teko.railway.entities.Line;
import ch.teko.railway.models.LineModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper for populating data between DTO<->Model<->Entity
 */
@Component
@Mapper(componentModel = "spring", uses = {TrainMapper.class, StationMapper.class, LinePartMapper.class})
public interface LineMapper {

    Line lineModelToLine(LineModel lineModel);

    LineModel lineToLineModel(Line line);

    LineModel lineDtoToLineModel(LineDto lineDto);

    LineDto lineModelToLineDto(LineModel lineModel);

    List<LineDto> lineModelsToLineDtos(List<LineModel> lineModels);

    List<LineModel> linesToLineModels(List<Line> lines);

}
