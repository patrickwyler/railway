package ch.teko.railway.models;

import ch.teko.railway.listeners.TickListener;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainModel implements TickListener {

    public static final String TXT_ARRIVAL = "an";
    public static final String TXT_DEPARTURE = "ab";

    @EqualsAndHashCode.Include
    Long id;

    String name;

    String typeDesignation;

    Date commissioningDate;

    Date lastAuditDate;

    Date nextAuditDate;

    int amountOfSeats;

    boolean available;

    // needed for timetable generation and driving on a line

    String runtimeId;

    int position = 0;

    boolean stopDriving;

    int lineNumber;

    List<LinePartModel> path;

    /**
     * Should be executed on each tick
     */
    @Override
    public void onTick() {
        position++; // up count position
        checkIfTrainReachedEndstation(); // check for final destination
    }

    /**
     * Stop train if it reached it's final destination
     */
    private void checkIfTrainReachedEndstation() {
        if (this.getPath().size() == this.getPosition()) {
            setStopDriving(true);
        }
    }

    public PositionModel getPositionOnLine() {
        LinePartModel linePartModel;

        try {
            linePartModel = getPath().get(getPosition());
        } catch (Exception e) {
            return null;
        }

        if (linePartModel instanceof StationModel) {
            StationModel before = null;
            StationModel after = null;

            try {
                before = (StationModel) getPath().get(getPosition() - 1);
            } catch (Exception e) {
                // no position before
            }
            try {
                after = (StationModel) getPath().get(getPosition() + 1);
            } catch (Exception e) {
                // no position after
            }

            if (before == null) {
                return PositionModel.builder()
                        .station(((StationModel) linePartModel))
                        .txt(TXT_ARRIVAL)
                        .build();
            }

            if (after == null) {
                return PositionModel.builder()
                        .station(((StationModel) linePartModel))
                        .txt(TXT_DEPARTURE)
                        .build();
            }
        }

        return null;
    }

    /**
     * Reset train values to default
     */
    public void reset() {
        setPosition(0);
        setStopDriving(false);
        setPath(new ArrayList<>());
        setLineNumber(0);
        setRuntimeId(null);
    }
}
