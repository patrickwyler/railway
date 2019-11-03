package ch.teko.railway.controllers;

import ch.teko.railway.dtos.EmployeeDto;
import ch.teko.railway.mappers.EmployeeMapper;
import ch.teko.railway.services.EmployeeService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for employee functions
 */
@Controller
@RequestMapping("/employees")
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeController {

    EmployeeService employeeService;
    EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping("/all")
    public String showAll(final Model model) {
        final List<EmployeeDto> employees = getEmployeeMapper().employeeModelsToEmployeeDtos(getEmployeeService().getAllEmployees());
        model.addAttribute("employees", employees);
        return "employees/all";
    }

    @GetMapping("/create")
    public String createEmployeeForm(final Model model) {
        final EmployeeDto employeeDto = EmployeeDto.builder().build();

        model.addAttribute("form", employeeDto);
        model.addAttribute("employees", getEmployeeMapper().employeeModelsToEmployeeDtos(getEmployeeService().getAllEmployees()));

        return "employees/create";
    }

    @PostMapping("/create")
    public String createEmployeeSubmit(@ModelAttribute final EmployeeDto employeeDto) {
        getEmployeeService().createEmployee(getEmployeeMapper().employeeDtoToEmployeeModel(employeeDto));
        return "redirect:/employees/all";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        final EmployeeDto employeeDto = getEmployeeMapper().employeeModelToEmployeeDto(getEmployeeService().getEmployeeById(id));

        model.addAttribute("form", employeeDto);

        return "employees/edit";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") long id, @Valid EmployeeDto employeeDto, BindingResult result) {
        if (result.hasErrors()) {
            employeeDto.setId(id);
            return "employees/edit";
        }

        getEmployeeService().updateEmployee(getEmployeeMapper().employeeDtoToEmployeeModel(employeeDto));

        return "redirect:/employees/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") long id) {
        getEmployeeService().deleteEmployeeById(id);
        return "redirect:/employees/all";
    }
}
