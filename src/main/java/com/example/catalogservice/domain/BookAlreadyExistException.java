package com.example.catalogservice.domain;

public class BookAlreadyExistException extends RuntimeException {
    public BookAlreadyExistException() {

    }

    public BookAlreadyExistException(String message) {
        super(message);
    }

    public BookAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookAlreadyExistException(Throwable cause) {
        super(cause);
    }

    public BookAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
