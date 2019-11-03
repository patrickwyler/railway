package ch.teko.railway.repositories;

import ch.teko.railway.entities.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for all train database operations
 */
@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    // empty
}