package com.epam.payments.utils.constant;

public final class MessageConstant {
    // auth
    public static final String ERROR_INVALID_EMAIL = "message.error.invalid.email";
    public static final String ERROR_INVALID_PHONE_NUMBER = "message.error.invalid.phone.number";
    public static final String ERROR_INVALID_PASSWORD = "message.error.invalid.password";
    public static final String ERROR_NOT_SAME_PASSWORD = "message.error.not.same.password";
    public static final String ERROR_INVALID_LOGIN = "message.error.invalid.login";
    public static final String ERROR_INVALID_SECOND_NAME = "message.error.invalid.second.name";
    public static final String ERROR_LOGIN_ALREADY_EXISTS = "message.error.login.already.exists";
    public static final String ERROR_EMAIL_ALREADY_EXISTS = "message.error.email.already.exists";
    public static final String ERROR_PHONE_NUMBER_ALREADY_EXISTS = "message.error.phone.number.already.exists";
    public static final String ERROR_INCORRECT_CONFIRMATION_CODE = "message.error.incorrect.confirmation.code";
    public static final String ERROR_SIGN_IN_INCORRECT_CREDENTIALS = "message.error.signIn.incorrect.credentials";

    // mail sender service
    public static final String MAIL_CONFIRMATION_SUBJECT = "mail.confirmation.subject";

    // info
    public static final String INFO_NO_CLIENT_ACCOUNTS = "message.info.no.client.accounts";
    public static final String INFO_REPLENISH_SUCCESS = "message.info.replenish.success";
    public static final String INFO_REPLENISH_FAIL = "message.info.replenish.fail";
    public static final String INFO_ADD_BANK_CARD_SUCCESS = "message.info.add.bankcard.success";
    public static final String INFO_NO_CLIENT_PAYMENTS = "message.info.no.client.payments";
    public static final String ERROR_SIGN_IN_USER_STATUS_BLOCKED = "message.error.signIn.user.status.blocked";

    public static final String INFO_SEND_PAYMENT_SUCCESS = "message.info.payment.send.success";
    public static final String ERROR_NOT_ENOUGH_ACCOUNT_AMOUNT = "message.error.not.enough.account.amount";
    public static final String ERROR_RECEIVER_AND_SENDER_ACCOUNT_CURRENCIES_DO_NOT_MATCH = "message.error.receiver.and.sender.account.currencies.not.match";
    public static final String ERROR_RECEIVER_ACCOUNT_NOT_EXISTS = "message.error.receiver.account.not.exists";
    public static final String ERROR_RECEIVER_ACCOUNT_NOT_ACTIVE = "message.error.receiver.account.not.active";
    public static final String ERROR_INVALID_BANK_CARD_EXPIRATION_DATE_FORMAT = "message.error.invalid.bank.card.expiration.date.format";
    public static final String ERROR_INVALID_BANK_CARD_NUMBER = "message.error.invalid.bank.card.number";
    public static final String ERROR_INVALID_BANK_CARD_CVV = "message.error.invalid.bank.card.cvv";
    public static final String ERROR_USER_IS_BLOCKED = "message.error.user.is.blocked";
    public static final String INFO_CREATE_PAYMENT_SUCCESS = "message.info.payment.create.success";

    public static final String INFO_ACCOUNT_UPDATE_SUCCESS = "message.info.account.update.success";
    public static final String INFO_NO_USERS = "message.info.no.users";
    public static final String INFO_USER_UPDATE_SUCCESS = "message.info.user.update.success";
    public static final String ERROR_PAYMENT_INCORRECT_CONFIRMATION_CODE = "message.error.payment.incorrect.confirmation.code";
    public static final String ERROR_PAYMENT_IS_NOT_ELIGIBLE_TO_SEND = "message.error.payment.is.not.eligible.to.send";

    public static final String INFO_ADD_ACCOUNT_SUCCESS = "message.info.add.account.success";
    public static final String ERROR_ADD_ACCOUNT_FAIL = "message.error.add.account.fail";
    public static final String ERROR_INVALID_ACCOUNT_NAME = "message.error.invalid.account.name";
    public static final String ERROR_ACCOUNT_NAME_ALREADY_EXISTS = "message.error.account.name.already.exists";
    public static final String ERROR_ADD_BANK_CARD_FAIL = "message.error.add.bankcard.fail";
    public static final String INFO_REGISTRATION_SUCCESS = "message.info.registration.success";
    public static final String ERROR_REGISTRATION_FAIL = "message.error.registration.fail";

    public static final String ERROR_INCORRECT_BANK_CARD_EXPIRATION_DATE = "message.error.incorrect.bankcard.expiration.date";
    public static final String ERROR_BANKCARD_HAS_EXPIRED = "message.error.bankcard.has.expired";
    public static final String ERROR_INCORRECT_ENTERED_CVV = "message.error.incorrect.entered.cvv";
    public static final String ERROR_RECEIVER_BANK_CARD_NOT_EXISTS = "message.error.receiver.bankcard.not.exists";
    public static final String ERROR_SYSTEM_FAILURE_DURING_PAYMENT_PROCESSING = "message.error.system.failure.during.payment.processing";
    public static final String INFO_NO_PAYMENTS = "message.info.no.payments";
    public static final String ERROR_CREATE_PAYMENT_FAIL = "message.error.create.payment.fail";
}
