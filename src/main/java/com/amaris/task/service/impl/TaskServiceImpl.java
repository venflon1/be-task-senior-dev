package com.amaris.task.service.impl;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.amaris.task.exception.ResourceNotFoundException;
import com.amaris.task.handler.ManageTaskEmployeeParam;
import com.amaris.task.handler.ManagedTaskHandler;
import com.amaris.task.model.Employee;
import com.amaris.task.model.Task;
import com.amaris.task.model.TaskAction;
import com.amaris.task.repository.TaskRepository;
import com.amaris.task.service.CrudEmployeeService;
import com.amaris.task.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Validated
@Slf4j
public class TaskServiceImpl extends CrudTaskServiceImpl implements TaskService {
	private final CrudEmployeeService crudEmployeeService;
	
	public TaskServiceImpl(
			TaskRepository taskRepository,
			ModelMapper modelMapper, 
			CrudEmployeeService crudEmployeeService,
			TaskActionServiceImpl taskActionService) {
		super(taskRepository, modelMapper);
		this.crudEmployeeService = crudEmployeeService;
	}

	@Override
	public void changeDueDate(@NotNull final Long taskId, @NotNull @Future final Date dueDate) {
		final String errorMessage = String.format("Error to change task due date. Task With id: %s not exists", taskId);
		final Task task = this.getById(taskId)
				.orElseThrow( () -> new ResourceNotFoundException(errorMessage) );

		task.setDueDate(dueDate);
		this.update(taskId, task);
	}

	@Override
	public void manageTaskEmployee(@NotNull @Valid ManagedTaskHandler manageTaskHandler) {
		log.info("manageTaskEmployee START - args=[manageTaskHandler={}]", manageTaskHandler);
		final Long taskId = manageTaskHandler.getTaskId();
		final Task task = this.getById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Task With id: %s not exists", taskId)));

		final Long employeeId = manageTaskHandler.getEmployeeId();
		final Employee employee = this.crudEmployeeService
			.getById(employeeId)
			.orElseThrow(() -> new ResourceNotFoundException(String.format("Employee With id: %s not exists", employeeId)));

		manageTaskHandler.doExecute(task, employee);
	}
}