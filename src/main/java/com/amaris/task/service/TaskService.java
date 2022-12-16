package com.amaris.task.service;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.amaris.task.service.param.ManageTaskEmployeeParam;

public interface TaskService {
	public void changeDueDate(@NotNull final Long taskId, @NotNull @Future final Date dueDate);
	public void manageTaskEmployee(@NotNull @Valid final ManageTaskEmployeeParam manageTaskEmployeeParam);
}