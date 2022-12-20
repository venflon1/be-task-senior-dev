package com.amaris.task.service;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.amaris.task.handler.ManagedTaskHandler;

public interface TaskService extends CrudTaskService {
	public void changeDueDate(@NotNull final Long taskId, @NotNull @Future final Date dueDate);
	public void manageTaskEmployee(@NotNull @Valid final ManagedTaskHandler manageTaskHandler);
}