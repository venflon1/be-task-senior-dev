package com.amaris.task.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class TaskRepositoryTest {
	@Autowired
	private TaskRepository taskRepository;
	
	@Test
	void test() {
		
	}
}