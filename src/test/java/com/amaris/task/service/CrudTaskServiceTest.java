package com.amaris.task.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
import com.amaris.task.model.Employee;
import com.amaris.task.model.Task;
import com.amaris.task.model.Task.Status;
import com.amaris.task.repository.TaskRepository;
import com.amaris.task.service.impl.CrudTaskServiceImpl;

@ExtendWith(MockitoExtension.class)
class CrudTaskServiceTest {
	@Mock
	private TaskRepository taskRepository;
	@Mock
	private ModelMapper modelMapper;
	@InjectMocks
	private CrudTaskServiceImpl crudTaskService;

	private static TaskEntity taskEntityOne;
	private static TaskEntity taskEntityTwo;
	private static TaskEntity taskEntityThree;
	private static TaskEntity taskEntityFour;
	private static List<TaskEntity> tasksEntity;
	
	private static Task taskModelOne;
	private static Task taskModelTwo;
	private static Task taskModelThree;
	private static Task taskModelFour;
	
	@BeforeAll
	public static void setupData() {
		taskEntityOne = TaskEntity.builder()
				.id(1L)
				.description("Task One")
				.owner( EmployeeEntity.builder().id(1L).name("Bob").build() )
				.statusTask(com.amaris.task.entity.TaskEntity.Status.ASSIGNED)
				.dueDate(new Date())
				.build();
		taskEntityTwo = TaskEntity.builder()
				.id(2L)
				.description("Task Two")
				.owner( EmployeeEntity.builder().id(2L).name("Dan").build() )
				.statusTask(com.amaris.task.entity.TaskEntity.Status.ASSIGNED)
				.dueDate(new Date())
				.build();
		taskEntityThree = TaskEntity.builder()
				.id(3L)
				.description("Task Three")
				.owner( EmployeeEntity.builder().id(3L).name("Tom").build() )
				.statusTask(com.amaris.task.entity.TaskEntity.Status.ASSIGNED)
				.dueDate(new Date())
				.build();
		taskEntityFour = TaskEntity.builder()
				.id(4L)
				.description("Task Four")
				.owner( EmployeeEntity.builder().id(4L).name("Fab").build() )
				.statusTask(com.amaris.task.entity.TaskEntity.Status.ASSIGNED)
				.dueDate(new Date())
				.build();
		tasksEntity = Arrays.asList(
				taskEntityOne, 
				taskEntityTwo, 
				taskEntityThree, 
				taskEntityFour
			);

		
		taskModelOne = Task.builder()
			.id(1L)
			.description("Task One")
			.owner( Employee.builder().id(1L).name("Bob").build() )
			.status(Status.ASSIGNED)
			.dueDate(new Date())
			.build();
		taskModelTwo = Task.builder()
				.id(2L)
				.description("Task Two")
				.owner( Employee.builder().id(2L).name("Dan").build() )
				.status(Status.ASSIGNED)
				.dueDate(new Date())
				.build();
		taskModelThree = Task.builder()
				.id(3L)
				.description("Task Three")
				.owner( Employee.builder().id(3L).name("Tom").build() )
				.status(Status.ASSIGNED)
				.dueDate(new Date())
				.build();
		taskModelFour = Task.builder()
				.id(4L)
				.description("Task Four")
				.owner( Employee.builder().id(4L).name("Fab").build() )
				.status(Status.ASSIGNED)
				.dueDate(new Date())
				.build();
	}
	
	@Test
	@DisplayName(value = "ShouldReturnListOfTasks")
	void getAllTasksTest() {
		Mockito.when( this.taskRepository.findAllAsStream())
			.thenReturn(tasksEntity.stream());
		Mockito.when( this.modelMapper.map(taskEntityOne, Task.class) )
			.thenReturn(taskModelOne);
		Mockito.when( this.modelMapper.map(taskEntityTwo, Task.class) )
			.thenReturn(taskModelTwo);
		Mockito.when( this.modelMapper.map(taskEntityThree, Task.class) )
			.thenReturn(taskModelThree);
		Mockito.when( this.modelMapper.map(taskEntityFour, Task.class) )
			.thenReturn(taskModelFour);
		
		final List<Task> tasks = this.crudTaskService.getAll();
		
		Assertions.assertThat(tasks).isNotNull();
		Assertions.assertThat(tasks.size()).isEqualTo(4);
	}
	
	@Test
	@DisplayName(value = "ShouldReturnTaskByItsId")
	void getTasksByIdTest() {
		final Long taskId = Mockito.anyLong();
		Mockito.when( this.taskRepository.findById(taskId))
			.thenReturn(Optional.of(taskEntityOne));
		Mockito.when( this.modelMapper.map(taskEntityOne, Task.class) )
			.thenReturn(taskModelOne);
		
		final Optional<Task> task = this.crudTaskService.getById(taskId);
		
		Assertions.assertThat(task).isPresent();
		Assertions.assertThat(task.get().getId()).isEqualTo(1L);
		Assertions.assertThat(task.get().getDescription()).isEqualTo("Task One");
		Assertions.assertThat(task.get().getOwner()).isNotNull();
		Assertions.assertThat(task.get().getOwner().getId()).isEqualTo(1L);
		Assertions.assertThat(task.get().getOwner().getName()).isEqualTo("Bob");
		Assertions.assertThat(task.get().getStatus()).isEqualTo(Status.ASSIGNED);
		Assertions.assertThat(task.get().getDueDate()).isNotNull();
	}
	
	@Test
	@DisplayName(value = "ShouldSaveTaskWithSuccess")
	void saveTaskSuccessTest() {
		final Long taskIdToSave = 11L;
		final Task taskToSave = Task.builder()
				.id(taskIdToSave)
				.description("Task for save ")
				.owner(Employee.builder().id(99L).name("Pino").build())
				.status(Status.ASSIGNED)
				.dueDate(new Date())
				.build();
		final TaskEntity taskEntityToSave = TaskEntity.builder()
				.id(taskIdToSave)
				.description("Task for save ")
				.owner(EmployeeEntity.builder().id(99L).name("Pino").build())
				.statusTask(com.amaris.task.entity.TaskEntity.Status.ASSIGNED)
				.dueDate(new Date())
				.build();

		Mockito.when(this.taskRepository.existsById(taskIdToSave))
			.thenReturn(Boolean.FALSE);
		Mockito.when(this.modelMapper.map(taskToSave, TaskEntity.class))
			.thenReturn(taskEntityToSave);
		Mockito.when(this.taskRepository.save(taskEntityToSave))
			.thenReturn(taskEntityToSave);
		
		Long taskIdSaved = this.crudTaskService.save(taskToSave);
		
		Assertions.assertThat(taskIdSaved).isNotNull();
		Assertions.assertThat(taskIdSaved).isEqualTo(taskIdToSave);
	}
}
