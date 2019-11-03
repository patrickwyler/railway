package ch.teko.railway.services;

import ch.teko.railway.models.LineModel;

import java.util.List;

/**
 * Interface for all line operations
 */
public interface LineService {

	/**
	 * Get all lines
	 *
	 * @return List of lines
	 */
	List<LineModel> getAllLines();

	/**
	 * Create line
	 *
	 * @param lineModel Line
	 * @return Line
	 */
	LineModel createLine(final LineModel lineModel);

	/**
	 * Update line
	 *
	 * @param lineModel line
	 * @return line
	 */
	LineModel updateLine(final LineModel lineModel);

	/**
	 * Delete line by id
	 *
	 * @param id Id of line
	 */
	void deleteLineById(final long id);

	/**
	 * Get line by id
	 *
	 * @param id Id of line
	 * @return line
	 */
	LineModel getLineById(final long id);
}
