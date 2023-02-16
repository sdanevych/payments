package com.epam.payments.exception;

import jakarta.mail.internet.AddressException;

public class EmailConfirmationException extends RuntimeException {
    public EmailConfirmationException(Exception e) {
    }
    public EmailConfirmationException(String message, Throwable cause) {
        super(message, cause);
    }
}
