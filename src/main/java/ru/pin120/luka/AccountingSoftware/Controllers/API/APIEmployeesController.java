package ru.pin120.luka.AccountingSoftware.Controllers.API;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pin120.luka.AccountingSoftware.Models.Employee;
import ru.pin120.luka.AccountingSoftware.Services.EmployeeService;

import java.util.List;

@RequestMapping("/api")
@RestController
public class APIEmployeesController {
    private EmployeeService employeeService;
    @Autowired
    public void setEmployeeService(EmployeeService employeeService){
        this.employeeService = employeeService;
    }
    @GetMapping("/employees")
    public Iterable<Employee> main(Model model){ return employeeService.getAllEmployees(); }
    /**
     * Добавление сотрудника
     * */
    @GetMapping("/employees/adding")
    public String add(Model model){
        model.addAttribute("employee", new Employee());
        return "employees/adding";
    }
    @PostMapping("/employees/adding")
    public String add(@Valid Employee employee, BindingResult result, Model model){
        if(result.hasErrors()){
            return "employees/adding";
        }
        employeeService.createEmployee(employee);
        return "redirect:/employees";
    }
    /**
     * Изменение сотрудника
     * */
    @GetMapping("/employees/update/{id}")
    public String update(@PathVariable Long id, Model model){
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "employees/editing";
    }
    @PostMapping("/employees/update")
    public String update(@Valid Employee employee, BindingResult result, Model model){
        if(result.hasErrors()){
            return "employees/editing";
        }
        employeeService.updateEmployee(employee);
        return "redirect:/employees";
    }
    /**
     * Удаление сотрудника
     * */
    @GetMapping("/employees/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
}
