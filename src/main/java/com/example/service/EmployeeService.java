package com.example.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Employee;

@Service
public interface EmployeeService {
	public ResponseEntity<?> saveEmployee(Employee emp);
	public ResponseEntity<?> fetchEmployee();
	public ResponseEntity<?> fetchEmployeeById(Long id);
	public ResponseEntity<?> deleteById(Long id);
}
