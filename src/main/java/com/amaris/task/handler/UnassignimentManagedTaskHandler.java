package com.amaris.task.handler;

import javax.transaction.Transactional;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.amaris.task.model.Employee;
import com.amaris.task.model.Task;
import com.amaris.task.service.impl.TaskActionServiceImpl;

@Service
public class UnassignimentManagedTaskHandler extends ManagedTaskHandler {
	@Autowired
	private TaskActionServiceImpl taskActionService;
	
	public UnassignimentManagedTaskHandler() {
		super(null);
	}
	
	@Override
	@Transactional
	public void doExecute(final Task task, final Employee employee) {
		this.taskActionService.manageAssignmentAction(task, employee);
	}
	
}