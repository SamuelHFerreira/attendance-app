package br.com.buildin.attendance.service.body;

import java.io.Serializable;

import br.com.buildin.attendance.model.SellerStatus;

/**
 * Created by samuelferreira on 21/03/17.
 */
public class SellerBody implements Serializable {
    private static final long serialVersionUID = 253876767554726378L;

    private String name;

    private String password;

    private SellerStatus sellerStatus;

    public SellerBody() {
    }

    public SellerBody(SellerStatus status) {
        this.sellerStatus = status;
    }

    public SellerBody(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public SellerStatus getSellerStatus() {
        return sellerStatus;
    }

    public void setSellerStatus(SellerStatus sellerStatus) {
        this.sellerStatus = sellerStatus;
    }
}