package ch.teko.railway.services.impl;

import ch.teko.railway.entities.Line;
import ch.teko.railway.mappers.LineMapper;
import ch.teko.railway.models.LineModel;
import ch.teko.railway.models.LinePartModel;
import ch.teko.railway.models.StationModel;
import ch.teko.railway.models.TraceModel;
import ch.teko.railway.repositories.LineRepository;
import ch.teko.railway.repositories.StationRepository;
import ch.teko.railway.repositories.TraceRepository;
import ch.teko.railway.services.LineService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@Getter(value = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LineServiceImpl implements LineService {

    LineRepository lineRepository;
    StationRepository stationRepository;
    TraceRepository traceRepository;
    LineMapper lineMapper;

    @Autowired
    public LineServiceImpl(LineRepository lineRepository, StationRepository stationRepository,
                           TraceRepository traceRepository, LineMapper lineMapper) {
        this.lineRepository = lineRepository;
        this.stationRepository = stationRepository;
        this.traceRepository = traceRepository;
        this.lineMapper = lineMapper;
    }

    @Override
    public List<LineModel> getAllLines() {
        getLineRepository().flush();
        return getLineMapper().linesToLineModels(getLineRepository().findAll());
    }

    @Override
    public LineModel createLine(final LineModel lineModel) {
        lineModel.getLineParts().forEach(this::checkPartExisting);

        Line line = getLineMapper().lineModelToLine(lineModel);
        line = getLineRepository().save(line);
        return getLineMapper().lineToLineModel(line);
    }

    private void checkPartExisting(final LinePartModel p) {
        boolean notExist;

        if (p instanceof StationModel) {
            notExist = !stationRepository.existsById(((StationModel) p).getId());
        } else {
            notExist = !traceRepository.existsById(((TraceModel) p).getId());
        }

        if (notExist) {
            throw new RuntimeException("Part not found");
        }
    }

    @Override
    public LineModel getLineById(long id) {
        final Line line = getLineRepository().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid line Id:" + id));

        return getLineMapper().lineToLineModel(line);
    }

    @Override
    public LineModel updateLine(LineModel lineModel) {
        getLineById(lineModel.getId());

        Line line = getLineRepository().save(getLineMapper().lineModelToLine(lineModel));

        return getLineMapper().lineToLineModel(line);
    }

    @Override
    public void deleteLineById(long id) {
        getLineRepository().deleteById(id);
    }
}
