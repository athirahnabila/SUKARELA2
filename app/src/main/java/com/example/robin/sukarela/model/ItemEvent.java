package com.example.robin.sukarela.model;

import com.example.robin.sukarela.utility.Helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItemEvent {

    public static final List<ItemEvent> EVENTS = new ArrayList<>();

    private String image;

    private String title;
    private String description;
    private String date_posted;
    private String date_event;

    private Status status;

    enum Status {Completed, Ongoing, Cancelled}

    public ItemEvent(String image, String title, String description, String date_event) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.date_event = date_event;

        // cannot be change external
        date_posted = Helper.getStringDateTime(new Date());
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
