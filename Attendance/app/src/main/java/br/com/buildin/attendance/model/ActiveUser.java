package br.com.buildin.attendance.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by samuelferreira on 03/01/17.
 */

public class ActiveUser {
    private long id;
    private long startTimestamp;
    private String title;

    public ActiveUser(String title, long startTimestamp, long id) {
        this.id = id;
        this.title = title;
        this.startTimestamp = startTimestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}