package com.amaris.task.handler;

import javax.validation.constraints.NotNull;

import com.amaris.task.model.TaskAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManageTaskEmployeeParam {
	// Task id to managing
	@NotNull(message = "Task id must be not null")
	private Long taskId;
	
	// Employee id involed with task
	@NotNull(message = "Employee id mustbe not null")
	private Long employeeId;
	
	// Action to do 
	@NotNull(message = "Task action must be not null")
	private TaskAction action;
}