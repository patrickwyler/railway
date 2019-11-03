package ch.teko.railway.services;

import ch.teko.railway.models.StationModel;

import java.util.List;

/**
 * Interface for all station operations
 */
public interface StationService {

	/**
	 * Get all stations
	 *
	 * @return List of stations
	 */
	List<StationModel> getAllStations();

	/**
	 * Check if root station exist
	 *
	 * @return true if root station exists
	 */
	boolean existsRootStation();

	/**
	 * Create station
	 *
	 * @param stationModel station
	 * @return Station
	 */
	StationModel createStation(final StationModel stationModel);

	/**
	 * Update station
	 *
	 * @param stationModel station
	 * @return Station
	 */
	StationModel updateStation(final StationModel stationModel);

	/**
	 * Delete station by id
	 *
	 * @param id id of station
	 */
	void deleteStationById(final long id);

	/**
	 * Get station by id
	 *
	 * @param id id of station
	 * @return station
	 */
	StationModel getStationById(final long id);
}
