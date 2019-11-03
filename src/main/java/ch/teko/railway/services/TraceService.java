package ch.teko.railway.services;

import ch.teko.railway.models.TraceModel;

import java.util.List;

/**
 * Interface for all trace operations
 */
public interface TraceService {

    /**
     * Get all traces
     *
     * @return List of traces
     */
    List<TraceModel> getAllTraces();

    /**
     * Create trace
     *
     * @param traceModel trace
     * @return trace
     */
    TraceModel createTrace(final TraceModel traceModel);

    /**
     * Update trace
     *
     * @param traceModel trace
     * @return trace
     */
    TraceModel updateTrace(final TraceModel traceModel);

    /**
     * Delete trace by id
     *
     * @param id id of trace
     */
    void deleteTraceById(final long id);

    /**
     * Get trace by id
     *
     * @param id id of trace
     * @return trace
     */
    TraceModel getTraceById(final long id);
}
