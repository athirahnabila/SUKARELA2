package com.example.robin.sukarela.model;

import java.util.List;

public class TaskModel {

    private List<String> join_list;

    private String title;
    private String description;

    public TaskModel() {
        // require empty constructor
    }

    public List<String> getJoin_list() {
        return join_list;
    }

    public void setJoin_list(List<String> join_list) {
        this.join_list = join_list;
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
                "join_list=" + join_list +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
