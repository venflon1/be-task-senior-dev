package com.amaris.task.handler;

import javax.transaction.Transactional;

import com.amaris.task.model.Employee;
import com.amaris.task.model.Task;

import lombok.Data;

@Data
public class AssignimentManagedTaskHandler extends ManagedTaskHandler {
	
	@Override
	@Transactional
	public void doExecute(final Task task, final Employee employee) {
		this.taskActionService.manageAssignmentAction(task, employee);
	}

}