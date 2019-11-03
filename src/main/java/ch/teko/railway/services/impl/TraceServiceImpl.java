package ch.teko.railway.services.impl;

import ch.teko.railway.entities.Trace;
import ch.teko.railway.mappers.TraceMapper;
import ch.teko.railway.models.TraceModel;
import ch.teko.railway.repositories.TraceRepository;
import ch.teko.railway.services.TraceService;
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
public class TraceServiceImpl implements TraceService {

    TraceRepository traceRepository;
    TraceMapper traceMapper;

    @Autowired
    public TraceServiceImpl(TraceRepository traceRepository, TraceMapper traceMapper) {
        this.traceRepository = traceRepository;
        this.traceMapper = traceMapper;
    }

    @Override
    public List<TraceModel> getAllTraces() {
        return getTraceMapper().tracesToTraceModels(getTraceRepository().findAll());
    }

    @Override
    public TraceModel createTrace(final TraceModel traceModel) {
        final Trace trace = getTraceRepository().save(getTraceMapper().traceModelToTrace(traceModel));
        return getTraceMapper().traceToTraceModel(trace);
    }

    @Override
    public TraceModel getTraceById(long id) {
        final Trace trace = getTraceRepository().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid trace Id:" + id));

        return getTraceMapper().traceToTraceModel(trace);
    }

    @Override
    public TraceModel updateTrace(TraceModel traceModel) {
        getTraceById(traceModel.getId());

        Trace trace = getTraceRepository().save(getTraceMapper().traceModelToTrace(traceModel));

        return getTraceMapper().traceToTraceModel(trace);
    }

    @Override
    public void deleteTraceById(long id) {
        getTraceRepository().deleteById(id);
    }
}
