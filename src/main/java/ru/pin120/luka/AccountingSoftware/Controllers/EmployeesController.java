package ru.pin120.luka.AccountingSoftware.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.pin120.luka.AccountingSoftware.Models.Employee;
import ru.pin120.luka.AccountingSoftware.Services.EmployeeService;

import java.util.List;

@Controller
public class EmployeesController {
    private EmployeeService employeeService;
    @Autowired
    public void setEmployeeService(EmployeeService employeeService){
        this.employeeService = employeeService;
    }
    @GetMapping("/employees")
    public String main(Model model){
        List<Employee> allEmployees = employeeService.getAllEmployees();
        model.addAttribute("employees", allEmployees);
        return "employees/index";
    }
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
