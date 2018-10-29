package com.example.robin.sukarela.model;

import java.util.Date;

public class ItemEvent {

    private String title;
    private String description;
    private String image;

    private Date date_posted;
    private Date date_event;

    private boolean joining;


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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDate_posted() {
        return date_posted;
    }

    public void setDate_posted(Date date_posted) {
        this.date_posted = date_posted;
    }

    public Date getDate_event() {
        return date_event;
    }

    public void setDate_event(Date date_event) {
        this.date_event = date_event;
    }

    public boolean isJoining() {
        return joining;
    }

    public void setJoining(boolean joining) {
        this.joining = joining;
    }
}
