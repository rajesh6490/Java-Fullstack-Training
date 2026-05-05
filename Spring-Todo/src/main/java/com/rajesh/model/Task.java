package com.rajesh.model;

import jakarta.persistence.*;



@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "is_completed")
    private boolean isCompleted;

    public Task() {}

    public Task(String name) {
        this.name = name;
        this.isCompleted = false;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public boolean isCompleted() { return isCompleted; }

    public void setName(String name) { this.name = name; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}