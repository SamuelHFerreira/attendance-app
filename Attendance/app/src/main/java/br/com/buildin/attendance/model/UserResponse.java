package br.com.buildin.attendance.model;

import java.io.Serializable;

/**
 * Created by samuelferreira on 10/01/17.
 */

public class UserResponse implements Serializable {

    private static final long serialVersionUID = 5115117572339754648L;

    private long id;
    private String name;
    private Integer next;
    private String status;
    // TODO create Date format
    private String created_at;
    private String updated_at;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}