package ch.teko.railway.repositories;

import ch.teko.railway.entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository for all station database operations
 */
@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    /**
     * Check if root station exists
     *
     * @return "true" if root station exists otherwise "false"
     */
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Station s WHERE s.rootStation = TRUE")
    boolean existsRootStation();

}
