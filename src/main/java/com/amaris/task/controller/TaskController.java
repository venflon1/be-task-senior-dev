package com.amaris.task.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amaris.task.handler.ManagedTaskHandler;
import com.amaris.task.model.Task;
import com.amaris.task.model.TaskAction;
import com.amaris.task.service.CrudTaskService;
import com.amaris.task.service.TaskService;
import com.amaris.task.service.impl.TaskActionServiceImpl;

import lombok.extern.slf4j.Slf4j;

@RestController
@Validated
@Slf4j
public class TaskController {
	private final TaskService taskService;
	@Qualifier(value = "crudTaskService")
	private final CrudTaskService crudTaskService;
	private final TaskActionServiceImpl taskActionService;

	public TaskController(
			TaskService taskService, 
			CrudTaskService crudTaskService,
			TaskActionServiceImpl taskActionService) {
		this.taskService = taskService;
		this.crudTaskService = crudTaskService;
		this.taskActionService = taskActionService;
	}
	
	@GetMapping
	public ResponseEntity<List<Task>> getAllTasks() {
		log.info("get all tasks");
		final List<Task> tasks = this.crudTaskService.getAll();
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable(name = "id") final Long id) {
		log.info("get task with id: {} ", id);
		final Optional<Task> task = this.crudTaskService.getById(id);
		if( task.isPresent() ) {
			return ResponseEntity.ok(task.get());
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(path = "/manage")
	public ResponseEntity<Void> manageTask(
		@RequestParam(name = "action") final TaskAction taskAction,
		@RequestParam(name = "taskId") final Long taskId,
		@RequestParam(name = "employeeId", required = false) final Long employeeId) {
		log.info("manage task id: {}, action={}, employee id: {}", taskId, taskAction.toString(), employeeId);
		if( (taskAction == TaskAction.ASSIGNMENT || taskAction == TaskAction.REASSIGNMENT)
			 &&	Objects.isNull(employeeId) ) {
			return ResponseEntity.badRequest().build();
		}
		
		final ManagedTaskHandler managedTaskHandler = ManagedTaskHandler.of(taskAction);
		managedTaskHandler.setTaskActionService(this.taskActionService);
		managedTaskHandler.setTaskId(taskId);
		managedTaskHandler.setEmployeeId(employeeId);
		this.taskService.manageTaskEmployee(managedTaskHandler);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(path = "/{id}/due-date/{dueDate}")
	public ResponseEntity<Void> changeDueDateTask(
		@PathVariable(name = "id") final Long taskId,
		@PathVariable(name = "dueDate") final String dueDateString) {
		log.info("change due date: '{}' to task with id: {}", dueDateString, taskId);
		
		Date dueDate = null;
		try {  
			dueDate = new SimpleDateFormat("dd-MM-yyyy").parse(dueDateString);  
		} catch (ParseException ex) {
			final String errorMessage = String.format("Error to parsing due date: '%s'. Verify due date is in this format: 'dd-MM-yyyy'", dueDateString);
			log.error(errorMessage, ex);
			return ResponseEntity.badRequest().build();
		}
		
		this.taskService.changeDueDate(taskId, dueDate);
		return ResponseEntity.ok().build();
	}
}