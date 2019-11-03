package ch.teko.railway.services.impl;

import ch.teko.railway.entities.Absence;
import ch.teko.railway.entities.Employee;
import ch.teko.railway.enums.Function;
import ch.teko.railway.mappers.AbsenceMapper;
import ch.teko.railway.mappers.EmployeeMapper;
import ch.teko.railway.models.AbsenceModel;
import ch.teko.railway.models.EmployeeModel;
import ch.teko.railway.repositories.AbsenceRepository;
import ch.teko.railway.repositories.EmployeeRepository;
import ch.teko.railway.services.EmployeeService;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter(value = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;
    EmployeeMapper employeeMapper;
    AbsenceRepository absenceRepository;
    AbsenceMapper absenceMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, AbsenceRepository absenceRepository, AbsenceMapper absenceMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.absenceRepository = absenceRepository;
        this.absenceMapper = absenceMapper;
    }

    @Override
    public List<EmployeeModel> getAllEmployees() {
        return getEmployeeMapper().employeesToEmployeeModels(getEmployeeRepository().findAll());
    }

    public List<EmployeeModel> getAvailableEmployeesWithFunctionOnDate(Function function, Date date) {
        Preconditions.checkNotNull(function);
        Preconditions.checkNotNull(date);

        return getEmployeeMapper().employeesToEmployeeModels(getEmployeeRepository().findAvailableEmployeesByFunction(function))
                .stream()
                .filter(employeeModel -> employeeModel.getAbsences()
                        .stream()
                        .noneMatch(absenceModel -> DateUtils.isSameDay(absenceModel.getTime(), date)))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeModel createEmployee(EmployeeModel employeeModel) {
        final Employee employee = getEmployeeRepository().save(getEmployeeMapper().employeeModelToEmployee(employeeModel));
        return getEmployeeMapper().employeeToEmployeeModel(employee);
    }

    @Override
    public AbsenceModel createAbsence(AbsenceModel absenceModel) {
        final Absence absence = getAbsenceRepository().save(getAbsenceMapper().absenceModelToAbsence(absenceModel));
        return getAbsenceMapper().absenceToAbsenceModel(absence);
    }

    @Override
    public EmployeeModel getEmployeeById(long id) {
        final Employee employee = getEmployeeRepository().findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));

        return getEmployeeMapper().employeeToEmployeeModel(employee);
    }

    @Override
    public EmployeeModel updateEmployee(EmployeeModel employeeModel) {
        getEmployeeById(employeeModel.getId());

        Employee employee = getEmployeeRepository().save(getEmployeeMapper().employeeModelToEmployee(employeeModel));

        return getEmployeeMapper().employeeToEmployeeModel(employee);
    }

    @Override
    public void deleteEmployeeById(long id) {
        getEmployeeRepository().deleteById(id);
    }
}
