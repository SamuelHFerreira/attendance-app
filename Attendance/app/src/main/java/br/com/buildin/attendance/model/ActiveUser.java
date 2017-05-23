package br.com.buildin.attendance.model;

/**
 * Created by samuelferreira on 03/01/17.
 */

public class ActiveUser {
    private long id;
    private long startTimestamp;
    private String title;
    private UserStatus userStatus;

    public ActiveUser(String title, long startTimestamp, long id, UserStatus userStatus) {
        this.id = id;
        this.startTimestamp = startTimestamp;
        this.title = title;
        this.userStatus = userStatus;
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

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public int compareTo(ActiveUser activeUser) {
        UserStatus ownUserStatus = this.getUserStatus();
        UserStatus otherUserStatus = activeUser.getUserStatus();
        if (ownUserStatus == UserStatus.AWAY && otherUserStatus == UserStatus.AWAY )
            return 0;
        if (ownUserStatus == UserStatus.AWAY && otherUserStatus != UserStatus.AWAY)
            return -1;
        return 1;
    }
}