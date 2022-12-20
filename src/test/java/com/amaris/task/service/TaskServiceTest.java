package com.amaris.task.service;

import static org.mockito.Mockito.times;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.amaris.task.entity.EmployeeEntity;
import com.amaris.task.entity.TaskEntity;
import com.amaris.task.exception.ResourceNotFoundException;
import com.amaris.task.model.Employee;
import com.amaris.task.model.Task;
import com.amaris.task.model.Task.Status;
import com.amaris.task.model.TaskAction;
import com.amaris.task.repository.TaskRepository;
import com.amaris.task.service.impl.TaskActionServiceImpl;
import com.amaris.task.service.impl.TaskServiceImpl;
import com.amaris.task.service.param.ManageTaskEmployeeParam;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
	@Mock
	private CrudEmployeeService crudEmployeeService;
	@Mock
	private TaskActionServiceImpl taskActionService;
	@Mock
	private TaskRepository taskRepository;
	@Mock
	private ModelMapper modelMapper;
	@InjectMocks
	private TaskServiceImpl taskService;
	
	@Test
	@DisplayName(value = "ShouldManageTask")
	void manageTaskEmployeeSuccessTest() {
		final Long employeeId = 1L;
		final Long taskId = 2L;
		final ManageTaskEmployeeParam manageTaskEmployeeParam = ManageTaskEmployeeParam.builder()
			.action(TaskAction.ASSIGNMENT)
			.employeeId(employeeId)
			.taskId(taskId)
			.build();
		final TaskEntity taskEntity = TaskEntity.builder()
				.id(taskId)
				.description("Task One")
				.assignee( EmployeeEntity.builder().id(1L).name("Bob").build() )
				.statusTask(com.amaris.task.entity.TaskEntity.Status.ASSIGNED)
				.dueDate(new Date())
				.build();
		final Task taskModel = Task.builder()
				.id(taskId)
				.description("Task One")
				.assignee( Employee.builder().id(1L).name("Bob").build() )
				.status(Status.ASSIGNED)
				.dueDate(new Date())
				.build();
		
		Mockito.when(this.taskRepository.findById(taskId))
			.thenReturn(Optional.of(taskEntity));
		Mockito.when(this.modelMapper.map(taskEntity, Task.class) )
			.thenReturn(taskModel);
		Mockito.when(this.crudEmployeeService.getById(employeeId))
			.thenReturn(
				Optional.of(
					Employee.builder()
						.id(employeeId)
						.name("Foo")
						.build()
				)
			);
		
		this.taskService.manageTaskEmployee(manageTaskEmployeeParam);
		
		Mockito.verify(this.taskActionService, times(1))
			.manageAssignmentAction(Mockito.any(Task.class), Mockito.any(Employee.class));
	}
	
	@Test
	@DisplayName(value = "ShouldNotManageTaskBecauseTaskToManageNotExists")
	void manageTaskEmployeeFailForTaksNotExistingTest() {
		final Long employeeId = 1L;
		final Long taskId = 2L;
		final ManageTaskEmployeeParam manageTaskEmployeeParam = ManageTaskEmployeeParam.builder()
			.action(TaskAction.ASSIGNMENT)
			.employeeId(employeeId)
			.taskId(taskId)
			.build();
		final TaskEntity taskEntity = TaskEntity.builder()
				.id(taskId)
				.description("Task One")
				.assignee( EmployeeEntity.builder().id(1L).name("Bob").build() )
				.statusTask(com.amaris.task.entity.TaskEntity.Status.ASSIGNED)
				.dueDate(new Date())
				.build();
		final Task taskModel = Task.builder()
				.id(taskId)
				.description("Task One")
				.assignee( Employee.builder().id(1L).name("Bob").build() )
				.status(Status.ASSIGNED)
				.dueDate(new Date())
				.build();
		
		Mockito.when(this.taskRepository.findById(taskId))
			.thenReturn(Optional.empty());

		Assertions.assertThrows(
			ResourceNotFoundException.class, 
			() -> this.taskService.manageTaskEmployee(manageTaskEmployeeParam)
		);
	}
}
