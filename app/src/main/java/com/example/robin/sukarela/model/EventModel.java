package com.example.robin.sukarela.model;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class EventModel {

    private Map<String, String> task_assign;

    private List<String> join_list;

    private String title;
    private String description;
    private String image;
    private String location;

    private boolean status;

    private GeoPoint coord;

    private Date start;
    private Date end;

    public EventModel() {
        // require empty contructor
    }

    public Map<String, String> getTask_assign() {
        return task_assign;
    }

    public void setTask_assign(Map<String, String> task_assign) {
        this.task_assign = task_assign;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public GeoPoint getCoord() {
        return coord;
    }

    public void setCoord(GeoPoint coord) {
        this.coord = coord;
    }

    @Exclude
    public boolean isJoined() {
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
