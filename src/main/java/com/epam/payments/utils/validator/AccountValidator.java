package com.epam.payments.utils.validator;

public class AccountValidator {
    private static final int ACCOUNT_NAME_MAX_LENGTH = 50;

    public static boolean isValidAccountName(String accountName) {
        return accountName.length() <= ACCOUNT_NAME_MAX_LENGTH;
    }


}
