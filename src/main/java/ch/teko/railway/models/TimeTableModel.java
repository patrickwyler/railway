package ch.teko.railway.models;

import com.google.common.base.Preconditions;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeTableModel {

    static final String SEPARATOR = "|";

    /**
     * Timetable is very nested..
     *
     * Levels:
     * 1. Line
     * 2. Train
     * 3. Runtime ID
     * 4. Station
     * 5. Arrival time & Depature time
     */
    LinkedHashMap<Integer, LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, List<String>>>>> timeTable = new LinkedHashMap<>();

    /**
     * Add new element to timetable
     *
     * @param lineNumber line number
     * @param trainModel train
     * @param positionModel position (station & arrival/depature)
     * @param time time (hh:mm)
     */
    public void addElement(int lineNumber, TrainModel trainModel, PositionModel positionModel, LocalTime time) {
        addLineIfMissing(lineNumber);
        addTrainIfMissing(lineNumber, trainModel);
        addPosition(lineNumber, trainModel, positionModel, time);
    }

    /**
     * Add Line
     * @param lineNumber line number
     *
     */
    private void addLineIfMissing(int lineNumber) {
        if (!getTimeTable().containsKey(lineNumber)) {
            getTimeTable().put(lineNumber, new LinkedHashMap<>());
        }
    }

    /**
     * Add train to line
     *
     * @param lineNumber line number
     * @param trainModel train
     */
    private void addTrainIfMissing(int lineNumber, TrainModel trainModel) {
        Preconditions.checkNotNull(trainModel, "TrainModel is null");
        Preconditions.checkNotNull(trainModel.getRuntimeId(), "Train runtime id is null");
        Preconditions.checkNotNull(trainModel.getName(), "Train name is null");

        if (!getTimeTable().get(lineNumber).containsKey(trainModel.getName())) {
            getTimeTable().get(lineNumber).put(trainModel.getName(), new LinkedHashMap<>());
        }

        if (!getTimeTable().get(lineNumber).get(trainModel.getName()).containsKey(trainModel.getRuntimeId())) {
            getTimeTable().get(lineNumber).get(trainModel.getName()).put(trainModel.getRuntimeId(), new LinkedHashMap<>());
        }
    }

    /**
     * Add position
     *
     * @param lineNumber line number
     * @param trainModel train
     * @param positionModel position
     * @param time time (hh:mm)
     */
    private void addPosition(int lineNumber, TrainModel trainModel, PositionModel positionModel, LocalTime time) {
        AtomicInteger v = new AtomicInteger(0);

        for (var value : getTimeTable().get(lineNumber).get(trainModel.getName()).get(trainModel.getRuntimeId()).values()
        ) {
            value.forEach(a -> v.getAndIncrement());
        }

        // check if new entry of station is needed or not. Each station should only have 2 times attached
        if (isEvenPosition(v)) {
            if (!getTimeTable().get(lineNumber).get(trainModel.getName()).get(trainModel.getRuntimeId()).containsKey(v.get() + SEPARATOR + positionModel.getStation().getName())) {
                getTimeTable().get(lineNumber).get(trainModel.getName()).get(trainModel.getRuntimeId()).put(v.get() + SEPARATOR + positionModel.getStation().getName(), new ArrayList<>());
            }

            getTimeTable().get(lineNumber).get(trainModel.getName()).get(trainModel.getRuntimeId()).get(v.get() + SEPARATOR + positionModel.getStation().getName()).add(positionModel.getTxt() + StringUtils.SPACE + time);
        } else {
            getTimeTable().get(lineNumber).get(trainModel.getName()).get(trainModel.getRuntimeId()).get((v.get() - 1) + SEPARATOR + positionModel.getStation().getName()).add(positionModel.getTxt() + StringUtils.SPACE + time);
        }
    }

    private boolean isEvenPosition(AtomicInteger v) {
        return v.get() % 2 == 0;
    }

}
