package com.epam.payments.utils.constant;

public final class AttributeConstant {
    // user data
    public static final String USER = "user";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ENCRYPTED_PASSWORD = "encryptedPassword";
    public static final String SAME_PASSWORD = "samePassword";
    public static final String LOGIN = "login";
    public static final String FIRST_NAME = "firstName";
    public static final String SECOND_NAME = "secondName";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String ACTUAL_CONFIRMATION_CODE = "actualConfirmationCode";
    public static final String USER_PAYMENT_CONFIRMATION_CODE = "userPaymentConfirmationCode";

    public static final String USER_CONFIRMATION_CODE = "userConfirmationCode";

    // errors
    // todo: set all messages on UI and BE, not all are sent: see quantity of usage
    public static final String INVALID_EMAIL = "invalidEmail";
    public static final String INVALID_LOGIN = "invalidLogin";
    public static final String NOT_SAME_PASSWORDS = "notSamePasswords";
    public static final String INVALID_PHONE_NUMBER = "invalidPhoneNumber";
    public static final String INVALID_PASSWORD = "invalidPassword";
    public static final String INVALID_FIRST_NAME = "invalidFirstName";
    public static final String INVALID_SECOND_NAME = "invalidSecondName";
    public static final String LOGIN_ALREADY_EXISTS = "loginAlreadyExists";
    public static final String EMAIL_ALREADY_EXISTS = "emailAlreadyExists";
    public static final String PHONE_NUMBER_ALREADY_EXISTS = "phoneNumberAlreadyExists";
    public static final String INCORRECT_CONFIRMATION_CODE = "incorrectConfirmationCode";
    public static final String SIGN_IN_FAIL = "signInFail";


    // page settings
    public static final String LANGUAGE = "language";


    public static final String ROLE = "role";
    public static final String SELECTED_LANGUAGE = "selectedLanguage";
    public static final String PAGINATION_MANAGER = "paginationManager";
    public static final String CLIENT_ACCOUNTS = "clientAccounts";
    public static final String NO_CLIENT_ACCOUNTS = "noClientAccounts";
    public static final String CLIENT_PAYMENTS = "clientPayments";
    public static final String NO_CLIENT_PAYMENTS = "noClientPayments";
    public static final String CREATE_PAYMENT_SUCCESS = "createPaymentSuccess";
    public static final String CREATE_PAYMENT_FAIL = "createPaymentFail";
    public static final String SEND_PAYMENT_SUCCESS = "sendPaymentSuccess";
    public static final String CLIENT_ACCOUNT_ID = "clientAccountId";
    public static final String REPLENISH_AMOUNT = "replenishAmount";
    public static final String REPLENISH_SUCCESS = "replenishSuccess";
    public static final String REPLENISH_FAIL = "replenishFail";
    public static final String ENTERED_CVV = "enteredCvv";
    public static final String CLIENT_ACCOUNT_BANK_CARD = "clientAccountBankCard";
    public static final String CLIENT_ACCOUNTS_WITH_BANK_CARDS = "accountsWithBankCards";
    public static final String CLIENT_ACCOUNT_IDS_WITH_BANK_CARDS = "clientAccountIdsWithBankCards";
    public static final String CLIENT_ACTIVE_ACCOUNTS_WITH_BANK_CARDS = "clientActiveAccountsWithBankCards";
    public static final String PAYMENT_SENDER_ACCOUNT = "paymentSenderAccount";
    public static final String PAYMENT_SENDER_ACCOUNT_ID = "paymentSenderAccountId";
    public static final String PAYMENT_RECEIVER_ACCOUNT_ID = "paymentReceiverAccountId";
    public static final String PAYMENT_AMOUNT = "paymentAmount";
    public static final String PAYMENT_BANK_CARD_EXPIRATION_DATE = "paymentBankcardExpirationDate";
    public static final String PAYMENT_BANK_CARD_CVV = "paymentBankcardCvv";
    public static final String BANK_CARD_NUMBER = "bankcardNumber";
    public static final String BANK_CARD_TYPE = "bankcardType";
    public static final String BANK_CARD_CURRENCY = "bankcardCurrency";
    public static final String BANK_CARD_EXPIRATION_DATE = "bankcardExpirationDate";
    public static final String BANK_CARD_CARDHOLDER_NAME = "bankcardCardholderName";
    public static final String BANK_CARD_BALANCE = "bankcardBalance";
    public static final String BANK_CARD_CVV = "bankcardCvv";
    public static final String ADD_BANK_CARD_SUCCESS = "addBankcardSuccess";
    public static final String NOT_ENOUGH_ACCOUNT_AMOUNT = "notEnoughAccountAmount";
    public static final String RECEIVER_AND_SENDER_ACCOUNT_CURRENCIES_NOT_MATCH = "receiverAndSenderAccountCurrenciesNotMatch";
    public static final String RECEIVER_ACCOUNT_NOT_EXISTS = "receiverAccountNotExists";
    public static final String RECEIVER_ACCOUNT_NOT_ACTIVE = "receiverAccountNotActive";
    public static final String INVALID_BANK_CARD_EXPIRATION_DATE_FORMAT = "invalidBankCardExpirationDateFormat";
    public static final String INVALID_BANK_CARD_NUMBER = "invalidBankCardNumber";
    public static final String INVALID_BANK_CARD_CVV = "invalidBankCardCvv";
    public static final String USER_IS_BLOCKED = "userIsBlocked";
    public static final String CREATED_PAYMENT_ID = "createdPaymentId";
    public static final String ACCOUNT_UPDATE_SUCCESS = "accountUpdateSuccess";
    public static final String NO_USERS = "noUsers";
    public static final String NO_PAYMENTS = "noPayments";
    public static final String USER_ID = "userId";
    public static final String USER_UPDATE_SUCCESS = "userUpdateSuccess";
    public static final String PAYMENT_INCORRECT_CONFIRMATION_CODE = "paymentIncorrectConfirmationCode";
    public static final String PAYMENT_IS_NOT_ELIGIBLE_TO_SEND = "paymentNotEligibleToSend";
    public static final String PAYMENT_ID_TO_SEND = "paymentIdToSend";
    public static final String ADD_ACCOUNT_SUCCESS = "addAccountSuccess";
    public static final String ADD_ACCOUNT_FAIL = "addAccountFail";
    public static final String ACCOUNT_NAME = "accountName";
    public static final String INVALID_ACCOUNT_NAME = "invalidAccountName";
    public static final String ACCOUNT_NAME_ALREADY_EXISTS = "accountNameAlreadyExists";
    public static final String ADD_BANK_CARD_FAIL = "addBankCardFail";
    public static final String CLIENT_ACTIVE_ACCOUNTS = "clientActiveAccounts";
    public static final String REGISTRATION_SUCCESS = "registrationSuccess";
    public static final String REGISTRATION_FAIL = "registrationFail";

    public static final String PAGINATION_PAGE = "page";
    public static final String PROCESSING_CLIENT_ACCOUNT = "processingClientAccount";
    public static final String PAYMENT_SENDER_ACCOUNT_CURRENCY = "paymentSenderAccountCurrency";
    public static final String BANK_CARD_EXPIRATION_DATES_NOT_MATCH = "bankCardExpirationDatesNotMatch";
    public static final String BANKCARD_HAS_EXPIRED = "bankCardHasExpired";
    public static final String INCORRECT_ENTERED_CVV = "incorrectEnteredCvv";
    public static final String RECEIVER_BANK_CARD_NOT_EXISTS = "receiverBankCardNotExists";
    public static final String PAYMENT_ACTUAL_CONFIRMATION_CODE = "actualConfirmationCode";
    public static final String SYSTEM_FAILURE_DURING_PAYMENT_PROCESSING = "systemFailureDuringPaymentProcessing";
    public static final String ACCOUNTS_SORT_MANAGER = "accountsSortManager";
    public static final String PAYMENTS_SORT_MANAGER = "paymentsSortManager";
    public static final String ADMIN_ALL_PAYMENTS_SORT_MANAGER = "adminAllPaymentsSortManager";
    public static final String USERS_SORT_MANAGER = "usersSortManager";
    public static final String SORT_COLUMN = "sortColumn";
    public static final String SORT_ORDER = "sortOrder";
    public static final String JSP_PATH = "jspPath";
    public static final String CLIENT_RECEIVED_PAYMENTS = "clientReceivedPayments";
    public static final String CLIENT_ACCOUNTS_PAGE_PAGINATION_URL_PATTERN = "clientAccountsPagePaginationUrlPattern";
    public static final String CLIENT_PAYMENTS_PAGE_PAGINATION_URL_PATTERN = "clientPaymentsPagePaginationUrlPattern";
    public static final String ADMIN_ACCOUNTS_PAGE_PAGINATION_URL_PATTERN = "adminAccountsPagePaginationUrlPattern";
    public static final String ADMIN_PAYMENTS_PAGE_PAGINATION_URL_PATTERN = "adminPaymentsPagePaginationUrlPattern";
    public static final String ADMIN_USERS_PAGE_PAGINATION_URL_PATTERN = "adminUsersPagePaginationUrlPattern";
    public static final String SENT_PAYMENT_ID = "sentPaymentId";
    public static final String ALL_ACCOUNTS = "allAccounts";
    public static final String ALL_PAYMENTS = "allPayments";
    public static final String ALL_USERS = "allUsers";
    public static final String PROCESSING_USER = "processingUser";
}
