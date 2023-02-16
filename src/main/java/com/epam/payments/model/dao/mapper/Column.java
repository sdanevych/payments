package com.epam.payments.model.dao.mapper;

public final class Column {
    // common
    public static final String ID = "id";

    // user
    public static final String USER_LOGIN = "login";
    public static final String USER_ROLE = "role";
    public static final String USER_STATUS = "status";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_SECOND_NAME = "second_name";
    public static final String USER_PHONE_NUMBER = "phone_number";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";

    // account
    public static final String ACCOUNT_USER_ID = "user_id";
    public static final String ACCOUNT_NAME = "name";
    public static final String ACCOUNT_STATUS = "status";
    public static final String ACCOUNT_BALANCE = "balance";
    public static final String ACCOUNT_CREATE_TIME = "create_time";

    // bankcard
    public static final String BANK_CARD_ACCOUNT_ID = "account_id";
    public static final String BANK_CARD_NUMBER = "card_number";
    public static final String BANK_CARD_TYPE = "type";
    public static final String BANK_CARD_CURRENCY = "currency";
    public static final String BANK_CARD_EXPIRATION_DATE = "expiration_date";
    public static final String BANK_CARD_CARDHOLDER_NAME = "cardholder_name";
    public static final String BANK_CARD_BALANCE = "balance";
    public static final String BANK_CARD_CVV = "cvv";

    // payment
    public static final String PAYMENT_SENDER_ACCOUNT_ID = "sender_account_id";
    public static final String PAYMENT_RECEIVER_ACCOUNT_ID = "receiver_account_id";
    public static final String PAYMENT_AMOUNT = "amount";
    public static final String PAYMENT_CURRENCY = "currency";
    public static final String PAYMENT_STATUS = "status";
    public static final String PAYMENT_CREATE_TIME = "create_time";
    public static final String PAYMENT_SEND_TIME = "send_time";
}
