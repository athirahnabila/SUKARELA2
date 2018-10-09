package com.example.robin.sukarela.model;

import com.example.robin.sukarela.utility.Helper;

import java.util.Date;

public class Event {

    private int image;

    private String title;
    private String description;
    private String date_posted;
    private String date_event;

    private boolean status;

    public Event(int image, String title, String description, String date_event) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.date_event = date_event;

        // cannot be change external
        date_posted = Helper.getStringDateTime(new Date());
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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

    public String getDate_posted() {
        return date_posted;
    }

    public String getDate_event() {
        return date_event;
    }

    public void setDate_event(String date_event) {
        this.date_event = date_event;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
