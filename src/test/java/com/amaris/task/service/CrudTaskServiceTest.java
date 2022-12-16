package com.amaris.task.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.amaris.task.repository.TaskRepository;
import com.amaris.task.service.impl.CrudTaskServiceImpl;

@ExtendWith(MockitoExtension.class)
class CrudTaskServiceTest {
	@Mock
	private TaskRepository taskRepository;
	@InjectMocks
	private CrudTaskServiceImpl crudTaskService;
	
	@Test
	void test() {
		
	}
}
