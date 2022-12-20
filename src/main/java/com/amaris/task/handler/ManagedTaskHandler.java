package com.amaris.task.handler;

import javax.validation.constraints.NotNull;

import com.amaris.task.model.Employee;
import com.amaris.task.model.Task;
import com.amaris.task.model.TaskAction;
import com.amaris.task.service.impl.TaskActionServiceImpl;

import lombok.Data;

@Data
public abstract class ManagedTaskHandler {
	// Task id to managing
	@NotNull(message = "Task id must be not null")
	protected Long taskId;
	
	// Employee id involed with task
	@NotNull(message = "Employee id mustbe not null")
	protected Long employeeId;
	
	protected TaskActionServiceImpl taskActionService;
	
	public final static ManagedTaskHandler of(final TaskAction taskAction) {
		switch (taskAction) {
		case ASSIGNMENT:
			return new AssignimentManagedTaskHandler();
		case REASSIGNMENT:
			return new ReassignimentManagedTaskHandler();
		case UNASSIGNMENT:
			return new UnassignimentManagedTaskHandler();
		default:
			throw new UnsupportedOperationException("Action not supported!");
		}
	}
	
	public abstract void doExecute(final Task task, final Employee employee);
}