package ch.teko.railway.services;

import ch.teko.railway.models.TrainModel;

import java.util.List;

/**
 * Interface for all train operations
 */
public interface TrainService {

    /**
     * Get all trains
     *
     * @return List of trains
     */
    List<TrainModel> getAllTrains();

    /**
     * Create train
     *
     * @param trainModel train
     * @return train
     */
    TrainModel createTrain(final TrainModel trainModel);

    /**
     * Update train
     *
     * @param trainModel train
     * @return train
     */
    TrainModel updateTrain(final TrainModel trainModel);

    /**
     * Delete train by id
     *
     * @param id id of train
     */
    void deleteTrainById(final long id);

    /**
     * Get train by id
     *
     * @param id id of train
     * @return train
     */
    TrainModel getTrainById(final long id);
}
