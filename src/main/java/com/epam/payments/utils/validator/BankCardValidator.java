package com.epam.payments.utils.validator;

public class BankCardValidator {
    private static final String BANK_CARD_EXPIRATION_DATE_REGEX = "^(0[1-9]|1[0-2])/([0-9]{2})$";
    private static final String BANK_CARD_NUMBER_REGEX = "^[0-9]{16,19}$";
    private static final String BANK_CARD_CVV_REGEX = "^[0-9]{3}$";

    public static boolean isValidBankCardExpirationDateFormat(String expirationDate) {
        return expirationDate.matches(BANK_CARD_EXPIRATION_DATE_REGEX);
    }

    public static boolean isValidBankCardNumber(String cardNumber) {
        return cardNumber.matches(BANK_CARD_NUMBER_REGEX);
    }

    public static boolean isValidBankCardCvv(String cvv) {
        return cvv.matches(BANK_CARD_CVV_REGEX);
    }
}
