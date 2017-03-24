package br.com.buildin.attendance.service.body;

import java.io.Serializable;

/**
 * Created by samuelferreira on 21/03/17.
 */
public class QueueBody implements Serializable {
    private static final long serialVersionUID = 253876767554726378L;

    private Long sellerId;
    private Integer status;


    public QueueBody() {
    }

    public QueueBody(Long sellerId) {
        this.sellerId = sellerId;
    }

    public QueueBody(Long sellerId, Integer status) {
        this.sellerId = sellerId;
        this.status = status;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
