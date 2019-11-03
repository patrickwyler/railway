package ch.teko.railway.services;

import ch.teko.railway.enums.Function;
import ch.teko.railway.models.AbsenceModel;
import ch.teko.railway.models.EmployeeModel;

import java.util.Date;
import java.util.List;

/**
 * Interface for all employee operations
 */
public interface EmployeeService {

    /**
     * Get all employees
     *
     * @return List of employees
     */
    List<EmployeeModel> getAllEmployees();

    /**
     * Get available employees with function on date
     *
     * @param function Function
     * @param date Available date
     * @return List of employees
     */
    List<EmployeeModel> getAvailableEmployeesWithFunctionOnDate(Function function, Date date);

    /**
     * Create new employee
     *
     * @param employeeModel Employee
     * @return Employee
     */
    EmployeeModel createEmployee(final EmployeeModel employeeModel);

    /**
     * Update employee
     *
     * @param employeeModel Employee
     * @return Employee
     */
    EmployeeModel updateEmployee(final EmployeeModel employeeModel);

    /**
     * Delete employee by id
     *
     * @param id Id of employee
     */
    void deleteEmployeeById(final long id);

    /**
     * Get employee by id
     *
     * @param id Id of employee
     * @return Employee
     */
    EmployeeModel getEmployeeById(final long id);

    /**
     * Create new absence
     *
     * @param absenceModel Absence
     * @return Absence
     */
    AbsenceModel createAbsence(final AbsenceModel absenceModel);
}
