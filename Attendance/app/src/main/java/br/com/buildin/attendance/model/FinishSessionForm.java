package br.com.buildin.attendance.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by samuelferreira on 29/12/16.
 */
public class FinishSessionForm implements Serializable {
    private static final long serialVersionUID = 1225592620332165699L;

    private boolean hasBoughtSomething;
    private boolean hasTestedProduct;
    private BigDecimal purchaseValue;
    private Integer sessionTimeSeconds;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasBoughtSomething() {
        return hasBoughtSomething;
    }

    public void setHasBoughtSomething(boolean hasBoughtSomething) {
        this.hasBoughtSomething = hasBoughtSomething;
    }

    public boolean isHasTestedProduct() {
        return hasTestedProduct;
    }

    public void setHasTestedProduct(boolean hasTestedProduct) {
        this.hasTestedProduct = hasTestedProduct;
    }

    public BigDecimal getPurchaseValue() {
        return purchaseValue;
    }

    public void setPurchaseValue(BigDecimal purchaseValue) {
        this.purchaseValue = purchaseValue;
    }

    public Integer getSessionTimeSeconds() {
        return sessionTimeSeconds;
    }

    public void setSessionTimeSeconds(Integer sessionTimeSeconds) {
        this.sessionTimeSeconds = sessionTimeSeconds;
    }
}