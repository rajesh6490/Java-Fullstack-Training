package com.rajesh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.rajesh.model.Task;
import com.rajesh.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskService service;

    // CREATE
    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return service.addTask(task);
    }

    // READ
    @GetMapping
    public List<Task> getTasks() {
        return service.getAllTasks();
    }

    // UPDATE
    @PutMapping("/{id}")
    public Task completeTask(@PathVariable Long id) {
        return service.markCompleted(id);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
        return "Task deleted!";
    }
}