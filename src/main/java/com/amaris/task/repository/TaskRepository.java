package com.amaris.task.repository;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.amaris.task.entity.TaskEntity;

@Repository
public interface TaskRepository  extends JpaRepository<TaskEntity, Long> {
	@Query(value = "SELECT t FROM TaskEntity t")
	Stream<TaskEntity> findAllAsStream(); 
}