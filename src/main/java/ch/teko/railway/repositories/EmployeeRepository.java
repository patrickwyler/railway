package ch.teko.railway.repositories;

import ch.teko.railway.entities.Employee;
import ch.teko.railway.enums.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for all employee database operations
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Find all available employees by function
     *
     * @param function Function
     * @return List of employees
     */
    List<Employee> findAvailableEmployeesByFunction(Function function);
}