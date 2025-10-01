package com.example.EmployeeManagement.Repository;

import com.example.EmployeeManagement.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
