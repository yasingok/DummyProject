package com.example.order_adapter.common;

public enum OrderStatus {
    ACTIVE("Active"),
    CANCELLED("Cancelled"),
    FAILED("Failed");


    private final String value;

    OrderStatus(String aInValue) {
        this.value = aInValue;
    }
}
