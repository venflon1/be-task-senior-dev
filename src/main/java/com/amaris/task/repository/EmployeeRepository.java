package com.amaris.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amaris.task.entity.Employee;

@Repository
public interface EmployeeRepository  extends JpaRepository<Employee, Long> { }