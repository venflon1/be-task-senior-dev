package com.amaris.task.service.param;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.amaris.task.model.Employee;
import com.amaris.task.model.Task;
import com.amaris.task.service.impl.TaskActionServiceImpl;

public class AssignimentManagedTaskHandler extends ManagedTaskHandler {
	@Autowired
	private TaskActionServiceImpl taskActionService;
	
	public AssignimentManagedTaskHandler() {
		super(null);
	}
	
	@Override
	@Transactional
	public void doExecute(final Task task, final Employee employee) {
		this.taskActionService.manageAssignmentAction(task, employee);
	}

}