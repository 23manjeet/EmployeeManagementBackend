package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Employee;
import com.example.service.EmployeeService;

import io.micrometer.common.lang.Nullable;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService empService;

	@PostMapping("/save")
	public ResponseEntity<?> saveEmployee(@RequestBody Employee employee){
		ResponseEntity<?> resp;
		try {
			resp = empService.saveEmployee(employee);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e,null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}
	
	@GetMapping("/fetch-employee")
	public ResponseEntity<?> fetchEmployee(@RequestParam(value="id",required = false) Long id){
		if(id == null)
			return empService.fetchEmployee();
		else
			return empService.fetchEmployeeById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id){
		return empService.deleteById(id);
	}
}
