package com.ty.service;

import java.util.Date;

import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ty.EmployeeRepository.EmployeeRepository;
import com.ty.entity.Employee;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Transactional
	public void Save(Employee employee) {
		if (employee.getId() == null) {
			employee.setCreateTime(new Date());
		}
		employeeRepository.saveAndFlush(employee);
	}

	@Transactional(readOnly = true)
	public Employee getByLastName(String lastName) {
		return employeeRepository.getByLastName(lastName);
	}

	@Transactional(readOnly = true)
	public Employee get(Integer id) {
		return employeeRepository.findOne(id);
	}

	@Transactional(readOnly=true)
	public Page<Employee> getPage(int pageNo, int pageSize){
		PageRequest pageRequest = new PageRequest(pageNo-1, pageSize);
		return employeeRepository.findAll(pageRequest);
	}

	@Transactional
	public void delete(Integer id){
		employeeRepository.delete(id);
	}
	
}
