package com.epam.payments.utils;

import java.util.UUID;

public class ConfirmationCodeGenerator {
    public static String generateConfirmationCode() {
        return UUID.randomUUID().toString();
    }
}
