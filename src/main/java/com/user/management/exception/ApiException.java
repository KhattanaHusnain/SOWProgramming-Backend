package com.user.management.exception;

// base
public abstract class ApiException extends RuntimeException {
    protected ApiException(String message) {
        super(message);
    }
}
