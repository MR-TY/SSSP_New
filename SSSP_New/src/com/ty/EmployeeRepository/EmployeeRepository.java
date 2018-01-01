package com.ty.EmployeeRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ty.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	Employee getByLastName(String lastName);
}
