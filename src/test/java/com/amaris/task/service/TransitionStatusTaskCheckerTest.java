package com.amaris.task.service;

import java.util.Date;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.amaris.task.model.Employee;
import com.amaris.task.model.Task;
import com.amaris.task.model.Task.Status;
import com.amaris.task.service.impl.TransitionStatusTaskChecker;

public class TransitionStatusTaskCheckerTest {
	@Test
	@DisplayName(value = "ShouldTaskTraniteToAssignible")
	void canTransiteToAssignableTrueCaseTest() {
		final Task task = Task.builder()
			.id(1L)
			.description("Task A")
			.assignee(null)
			.status(Status.UNASSIGNED)
			.dueDate(new Date())
			.build();
		
		Assertions.assertThat(TransitionStatusTaskChecker.canTransiteToAssignable(task)).isEqualTo(Boolean.TRUE);
	}
	
	@Test
	@DisplayName(value = "ShouldTaskNotTraniteToAssignible")
	void canTransiteToAssignableFalseCaseTest() {
		final Task task1 = Task.builder()
				.id(1L)
				.description("Task A")
				.assignee(Employee.builder().id(1L).name("Aron").build())
				.status(Status.ASSIGNED)
				.dueDate(new Date())
				.build();
		
		Assertions.assertThat(TransitionStatusTaskChecker.canTransiteToAssignable(task1)).isEqualTo(Boolean.FALSE);
		
		final Task task2 = Task.builder()
				.id(1L)
				.description("Task B")
				.assignee(Employee.builder().id(1L).name("Aron").build())
				.status(Status.REASSIGNED)
				.dueDate(new Date())
				.build();
		
		Assertions.assertThat(TransitionStatusTaskChecker.canTransiteToAssignable(task2)).isEqualTo(Boolean.FALSE);
	}
}