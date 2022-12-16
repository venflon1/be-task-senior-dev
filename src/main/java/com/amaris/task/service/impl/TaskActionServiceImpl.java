package com.amaris.task.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amaris.task.exception.TaskActionException;
import com.amaris.task.model.Employee;
import com.amaris.task.model.Task;
import com.amaris.task.service.CrudTaskService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskActionServiceImpl {
	private final CrudTaskService crudTaskService;

	public void manageAssignmentAction(final Task task, final Employee employee) {
		log.info("manageAssignmentAction START - args=[task={}, employee={}]", task, employee);
		if ( !TransitionStatusTaskChecker.canTransiteToAssignable(task) ) {
			final String errorMessage = String.format("Task with id: %s cannot be assignable because is already assigned. Try with reassigment action of task", task.getId());
			throw new TaskActionException(errorMessage);
		}

		task.setAssignee(employee);
		task.setStatus(Task.Status.ASSIGNED);
		this.crudTaskService.update(task.getId(), task);
	}

	public void manageUnassignmentAction(final Task task, final Employee employee) {
		log.info("manageUnassignmentAction START - args=[task={}, employee={}]", task, employee);
		final String errorMessage = String.format("Task with id: %s cannot be unassignable because is already unassigned.", task.getId());
		if (!TransitionStatusTaskChecker.canTransiteToUnassignable(task)) {
			throw new TaskActionException(errorMessage);
		}

		task.setAssignee(null);
		task.setStatus(Task.Status.UNASSIGNED);
		this.crudTaskService.update(task.getId(), task);
	}

	public void manageReassignmentAction(final Task task, final Employee employee) {
		log.info("manageReassignmentAction START - args=[task={}, employee={}]", task, employee);
		final String errorMessgae = String.format("Task with id: %s cannot be reassignable because is unassigned. Try to assigned task before reassigned it.", task.getId());
		if ( !TransitionStatusTaskChecker.canTransiteToReassignable(task) ) {
			throw new TaskActionException(errorMessgae);
		}

		task.setAssignee(employee);
		task.setStatus(Task.Status.REASSIGNED);
		this.crudTaskService.update(task.getId(), task);
	}
}