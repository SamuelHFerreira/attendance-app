package br.com.buildin.attendance.model;

/**
 * Created by samuelferreira on 03/01/17.
 */

public class ActiveUser {
    private String title;
    private long startTimestamp;

    public ActiveUser(String title, long startTimestamp) {
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
}