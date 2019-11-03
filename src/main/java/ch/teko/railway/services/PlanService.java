package ch.teko.railway.services;

import ch.teko.railway.models.ResourcePlanModel;
import ch.teko.railway.models.TimeTableModel;
import ch.teko.railway.models.WorkScheduleModel;

/**
 * Interface for timetable operations
 */
public interface PlanService {

    /**
     * Reset
     */
    void reset();

    /**
     * Get timetable
     *
     * @return timetable
     */
    TimeTableModel getTimeTable();

    /**
     * Get resource plan
     *
     * @return resource plan
     */
    ResourcePlanModel getResourcesPlan();

    /**
     * Get work schedule
     *
     * @return work schedule
     */
    WorkScheduleModel getWorkSchedule();

}
