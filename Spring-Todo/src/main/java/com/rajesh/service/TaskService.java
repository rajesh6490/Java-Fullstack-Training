package com.rajesh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rajesh.model.Task;
import com.rajesh.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repo;

    public Task addTask(Task task) {
        return repo.save(task);
    }

    public List<Task> getAllTasks() {
        return repo.findAll();
    }

    public Task markCompleted(Long id) {
        Task task = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setCompleted(true);
        return repo.save(task);
    }

    public void deleteTask(Long id) {
        repo.deleteById(id);
    }
}