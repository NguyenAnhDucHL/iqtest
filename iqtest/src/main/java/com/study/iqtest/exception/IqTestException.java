package com.study.iqtest.exception;

public class IqTestException extends RuntimeException {
    public IqTestException(String message) {
        super(message);
    }

    public IqTestException(String message, Throwable cause) {
        super(message, cause);
    }
}