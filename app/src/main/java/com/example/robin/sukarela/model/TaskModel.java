package com.example.robin.sukarela.model;

import java.util.List;

public class TaskModel {

    private String title;
    private String description;

    public TaskModel() {
        // require empty constructor
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TaskModel{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
