package com.kajol.yourdailytaskmaneger;

// Task.java
public class Task {
    private int id;
    private String title;
    private String description;

    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    // Getters and Setters
    public int getId()
    { return id; }
    public String getTitle()
    { return title; }
    public String getDescription()
    { return description; }
}

