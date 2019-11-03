package ch.teko.railway.repositories;

import ch.teko.railway.entities.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for all line database operations
 */
@Repository
public interface LineRepository extends JpaRepository<Line, Long> {
    // empty
}
