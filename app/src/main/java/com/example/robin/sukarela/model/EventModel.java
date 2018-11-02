package com.example.robin.sukarela.model;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.List;

public class EventModel {

    private List<String> join_list;

    private String title;
    private String description;
    private String image;

    private Date start;
    private Date end;

    public EventModel() {
        // require empty contructor
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Exclude
    public boolean isStatus() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) return join_list.contains(user.getUid());
        else return false;
    }

    @Override
    public String toString() {
        return "EventModel{" +
                "join_list=" + join_list +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
