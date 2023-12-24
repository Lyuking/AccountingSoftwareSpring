package ru.pin120.luka.AccountingSoftware.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pin120.luka.AccountingSoftware.Models.Employee;
import ru.pin120.luka.AccountingSoftware.Models.Licence;
import ru.pin120.luka.AccountingSoftware.Repositories.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Метод для создания сотрудника
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Метод для получения сотрудника по ID
    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.orElse(null);
    }

    // Метод для обновления информации о сотруднике
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Метод для получения всех сотрудников
    public List<Employee> getAllEmployees() {
        return (List<Employee>) employeeRepository.findAll();
    }

    // Метод для удаления сотрудника по ID
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee != null) {
            // Устанавливаем связи с компьютерами в null
            for (Licence licence : employee.getLicences()) {
                licence.setEmployee(null);
            }
            employeeRepository.deleteById(id);
        }
    }
}

