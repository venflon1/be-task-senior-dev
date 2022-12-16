package com.amaris.task.service.impl;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.amaris.task.exception.ResourceNotFoundException;
import com.amaris.task.model.Employee;
import com.amaris.task.model.Task;
import com.amaris.task.model.TaskAction;
import com.amaris.task.repository.TaskRepository;
import com.amaris.task.service.CrudEmployeeService;
import com.amaris.task.service.TaskService;
import com.amaris.task.service.param.ManageTaskEmployeeParam;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Validated
@Slf4j
public class TaskServiceImpl extends CrudTaskServiceImpl implements TaskService {
	@Autowired
	private CrudEmployeeService crudEmployeeService;
	@Autowired
	private TaskActionServiceImpl taskActionService;
	
	public TaskServiceImpl(TaskRepository taskRepository) {
		super(taskRepository, null);
	}

	@Override
	public void manageTaskEmployee(@NotNull @Valid final ManageTaskEmployeeParam manageTaskEmployeeParam) {
		log.info("manageTaskEmployee START - args=[manageTaskEmployeeParam={}]", manageTaskEmployeeParam);
		final Long taskId = manageTaskEmployeeParam.getTaskId();
		final Task task = this.getById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Task With id: %s not exists", taskId)));

		final Long employeeId = manageTaskEmployeeParam.getEmployeeId();
		Employee employee = this.crudEmployeeService
				.getById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Employee With id: %s not exists", employeeId)));

		final TaskAction action = manageTaskEmployeeParam.getAction();
		this.manageTaskAction(action, task, employee);
	}

	private void manageTaskAction(final TaskAction action, final Task task, final Employee employee) {
		switch (action) {
			case ASSIGNMENT:
				this.taskActionService.manageAssignmentAction(task, employee);   break;
			case UNASSIGNMENT:
				this.taskActionService.manageUnassignmentAction(task, employee); break;
			case REASSIGNMENT:
				this.taskActionService.manageReassignmentAction(task, employee); break;
			default:
				final String errorMessage = String.format("Task Action: '%s' is not valid", action.toString());
				throw new UnsupportedOperationException(errorMessage);
		}
	}

	@Override
	public void changeDueDate(@NotNull final Long taskId, @NotNull @Future final Date dueDate) {
		final String errorMessage = String.format("Error to change task due date. Task With id: %s not exists", taskId);
		final Task task = this.getById(taskId)
				.orElseThrow( () -> new ResourceNotFoundException(errorMessage) );

		task.setDueDate(dueDate);
		this.update(taskId, task);
	}
}