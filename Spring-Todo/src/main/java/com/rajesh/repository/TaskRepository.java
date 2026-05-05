package com.rajesh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rajesh.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}