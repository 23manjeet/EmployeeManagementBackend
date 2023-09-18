package com.example.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Employee;
import com.example.exception.NoDataFoundException;
import com.example.repository.EmployeeRepo;
import com.example.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepo empRepo;
	
	@Override
	public ResponseEntity<?> saveEmployee(Employee emp) {
		Employee resp = new Employee();
		if(emp.getEmployeeId()!= null) {
			Optional<Employee> dbData = empRepo.findById(emp.getEmployeeId());
			if(!dbData.isPresent()) {
				throw new NoDataFoundException("No data found for the given id : "+emp.getEmployeeId());
			}
			updateEmployee(emp,dbData.get());
			resp = empRepo.save(dbData.get());
		}else {
			resp = empRepo.save(emp);
		}
		return new ResponseEntity(resp, null, HttpStatus.OK);
	}

	private void updateEmployee(Employee emp, Employee dbData) {
		if(emp.getAge()!=null)
			dbData.setAge(emp.getAge());
		if(emp.getEmailId()!=null)
			dbData.setEmailId(emp.getEmailId());
		if(emp.getFirstName()!=null)
			dbData.setFirstName(emp.getFirstName());
	}

	@Override
	public ResponseEntity<?> fetchEmployee() {
		List<Employee> lst = empRepo.findAll();
				
		if(lst!=null && lst.isEmpty())
			return new ResponseEntity<String>("no data found",HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<List<Employee>>(lst,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> deleteById(Long id) {
		Optional<Employee> emp = empRepo.findById(id);
		if(emp.isPresent()) {
			empRepo.deleteById(id);
			return new ResponseEntity<String>("successfully deleted",HttpStatus.OK);
		}else
			return new ResponseEntity<String>("Couldn't find any data with the given id",HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<?> fetchEmployeeById(Long id) {
		Optional<Employee> emp = empRepo.findById(id);
		if(emp.isPresent()) {
			return new ResponseEntity<Employee>(emp.get(),HttpStatus.OK);
		}else
			return new ResponseEntity<String>("Couldn't find any data with the given id",HttpStatus.NO_CONTENT);
	}

}
