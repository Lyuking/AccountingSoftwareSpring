package ru.pin120.luka.AccountingSoftware.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.pin120.luka.AccountingSoftware.Models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
