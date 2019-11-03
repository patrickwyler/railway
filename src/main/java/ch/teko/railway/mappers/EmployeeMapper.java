package ch.teko.railway.mappers;

import ch.teko.railway.dtos.EmployeeDto;
import ch.teko.railway.entities.Employee;
import ch.teko.railway.models.EmployeeModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper for populating data between DTO<->Model<->Entity
 */
@Component
@Mapper(componentModel = "spring", uses = {AbsenceMapper.class})
public interface EmployeeMapper {

    Employee employeeModelToEmployee(EmployeeModel employeeModel);

    EmployeeModel employeeToEmployeeModel(Employee employee);

    EmployeeModel employeeDtoToEmployeeModel(EmployeeDto employeeDto);

    EmployeeDto employeeModelToEmployeeDto(EmployeeModel employeeModel);

    List<EmployeeModel> employeesToEmployeeModels(List<Employee> employees);

    List<EmployeeDto> employeeModelsToEmployeeDtos(List<EmployeeModel> employeeModels);

    List<Employee> employeeModelsToEmployee(List<EmployeeModel> employeeModels);

}
