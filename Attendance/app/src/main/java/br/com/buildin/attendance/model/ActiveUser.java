package br.com.buildin.attendance.model;

/**
 * Created by samuelferreira on 03/01/17.
 */

public class ActiveUser {
    private long id;
    private long startTimestamp;
    private String title;
    private boolean isOnAttendance;

    public ActiveUser(String title, long startTimestamp, long id, boolean isOnAttendance) {
        this.id = id;
        this.startTimestamp = startTimestamp;
        this.title = title;
        this.isOnAttendance = isOnAttendance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isOnAttendance() {
        return isOnAttendance;
    }

    public void setOnAttendance(boolean onAttendance) {
        isOnAttendance = onAttendance;
    }
}