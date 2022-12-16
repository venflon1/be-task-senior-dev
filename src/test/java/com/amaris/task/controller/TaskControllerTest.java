package com.amaris.task.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.amaris.task.service.TaskService;

@SpringBootTest
class TaskControllerTest {
	private static final String TASKS_URL = "http://localhost:8080/tasks";

	@MockBean
	private TaskService taskService;
	
	@Test
	void test() {
		
	}
}
