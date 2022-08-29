package com.example.order_adapter.exceptions;

public class FlavorNotSupportedException extends Exception{

    public FlavorNotSupportedException() {
        super();
    }

    public FlavorNotSupportedException(String message) {
        super(message);
    }
}
