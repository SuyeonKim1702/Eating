package com.eating.jinwoo.common;

public class EatingException extends RuntimeException {
    private String message;
    @Override
    public String getMessage() {
        return message;
    }

    public EatingException(String message) {
        super(message);
        this.message = message;
    }
    public EatingException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
