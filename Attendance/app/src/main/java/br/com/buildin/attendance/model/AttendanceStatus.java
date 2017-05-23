package br.com.buildin.attendance.model;

/**
 * Created by samuelferreira on 22/05/17.
 */

public enum AttendanceStatus {
    OPEN("Started attendance"),
    CLOSE("Finished attendance");

    private String name;

    AttendanceStatus(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
