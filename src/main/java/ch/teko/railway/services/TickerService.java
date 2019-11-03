package ch.teko.railway.services;

import ch.teko.railway.models.StationModel;
import ch.teko.railway.models.TrainModel;

import java.time.LocalTime;
import java.util.Set;

/**
 * Interface for all ticker operations
 */
public interface TickerService {

    //
    // General operations
    //

    void initalize();

    void tick();

    void add(final TrainModel train);

    void removeStoppedTrains();


    //
    // Data operations
    //


    StationModel get(long id);

    void set(long id, StationModel stationModel);

    LocalTime getTime();

    Set<TrainModel> getTrainModels();

    Set<TrainModel> getUsedTrainModels();
}
