package br.com.buildin.attendance.model;

/**
 * Created by samuelferreira on 22/05/17.
 */

public enum SellerStatus {
    OFFLINE("Offline"),
    IN_QUEUE("In queue"),
    IN_ATTENDANCE("In attendance"),
    ABSENT("Not present");

    private String name;

    SellerStatus(String name) {
        this.name = name;
    }
}