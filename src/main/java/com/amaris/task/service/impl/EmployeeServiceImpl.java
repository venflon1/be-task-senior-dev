package com.amaris.task.service.impl;

import java.util.List;
import java.util.Optional;

import org.jvnet.hk2.annotations.Service;

import com.amaris.task.entity.Employee;
import com.amaris.task.repository.EmployeeRepository;
import com.amaris.task.service.EmployeeService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
	private final EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> getAll() {
		log.info("getAll START");
		return this.employeeRepository.findAll();
	}

	@Override
	public Optional<Employee> getById(Long id) {
		log.info("getById START - args=[id={}]", id);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long save(Employee entity) {
		log.info("save START - args=[entity={}]", entity);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Long id, Employee entity) {
		log.info("update START - args=[entity={}]", entity);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Long id) {
		log.info("deleteById START - args=[id={}]", id);
		// TODO Auto-generated method stub
	}
}