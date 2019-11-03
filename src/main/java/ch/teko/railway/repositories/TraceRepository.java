package ch.teko.railway.repositories;

import ch.teko.railway.entities.Trace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for all trace database operations
 */
@Repository
public interface TraceRepository extends JpaRepository<Trace, Long> {
    // empty
}