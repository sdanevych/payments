package com.epam.payments.exception;

import java.security.NoSuchAlgorithmException;

public class EncryptionException extends RuntimeException {
    public EncryptionException(NoSuchAlgorithmException e) {
    }
}
