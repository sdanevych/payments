package com.epam.payments.exception;

public class PaginationException extends RuntimeException{
    public PaginationException(String message) {
        super(message);
    }
}
