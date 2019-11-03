package ch.teko.railway.repositories;

import ch.teko.railway.entities.Absence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for all absence database operations
 */
@Repository
public interface AbsenceRepository extends JpaRepository<Absence, Long> {
// empty
}