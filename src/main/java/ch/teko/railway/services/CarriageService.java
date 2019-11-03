package ch.teko.railway.services;

import ch.teko.railway.models.CarriageModel;

import java.util.List;

/**
 * Interface for all carriage operations
 */
public interface CarriageService {

    /**
     * Get all carriages
     *
     * @return List of carraiges
     */
    List<CarriageModel> getAllCarriages();

    /**
     * Create new carriage
     *
     * @param carriageModel Carriage
     * @return Carriage
     */
    CarriageModel createCarriage(final CarriageModel carriageModel);

    /**
     * Update carriage
     *
     * @param carriageModel Carriage
     * @return Carriage
     */
    CarriageModel updateCarriage(final CarriageModel carriageModel);

    /**
     * Delete carriage by id
     *
     * @param id Id of carriage
     */
    void deleteCarriageById(final long id);

    /**
     * Get carriage by id
     *
     * @param id Id of carriage
     * @return Carriage
     */
    CarriageModel getCarriageById(final long id);
}
