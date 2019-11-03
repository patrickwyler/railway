package ch.teko.railway.repositories;

import ch.teko.railway.entities.Carriage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for all carriage database operations
 */
@Repository
public interface CarriageRepository extends JpaRepository<Carriage, Long> {
    // empty
}