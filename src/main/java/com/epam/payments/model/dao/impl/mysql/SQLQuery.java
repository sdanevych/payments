package com.epam.payments.model.dao.impl.mysql;

public abstract class SQLQuery {
    static class UserQuery {
        public static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE id = ?";
        public static final String UPDATE_USER_BY_ID = "UPDATE user SET login = ?, role = ?, status = ?, first_name = ?, " +
                "second_name = ?, phone_number = ?, email = ?, create_time = ? WHERE id = ?";
        public static final String SELECT_ALL_USERS = "SELECT * FROM user";
        public static final String SELECT_COUNT_BY_USER_LOGIN = "SELECT count(id) FROM user WHERE login = ?";
        public static final String SELECT_COUNT_BY_USER_EMAIL = "SELECT count(id) FROM user WHERE email = ?";
        public static final String SELECT_COUNT_BY_USER_PHONE_NUMBER = "SELECT count(id) FROM user WHERE phone_number = ?";
        public static final String SELECT_USER_BY_EMAIL_AND_PASSWORD = "SELECT * FROM user WHERE email = ? and password = ?";
        public static final String INSERT_USER = "INSERT INTO user(login, role, status, first_name," +
                "second_name, phone_number, email, create_time, password) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        public static final String SELECT_USERS_IN_RANGE = "SELECT * FROM user LIMIT ?, ?";
        public static final String QUERY_TO_FORMAT_SELECT_ALL_USERS_ORDERED_IN_RANGE =
                "SELECT * FROM user ORDER BY %s %s LIMIT ?, ?";
        public static final String SELECT_COUNT_ALL_USERS = "SELECT count(id) FROM user";

    }

    static class AccountQuery {
        public static final String SELECT_ALL_ACCOUNTS = "SELECT * FROM account";
        public static final String SELECT_ACCOUNTS_IN_RANGE = "SELECT * FROM account LIMIT ?, ?";
        public static final String SELECT_ACTIVE_USER_ACCOUNTS = "SELECT account.* FROM account INNER JOIN bank_card " +
                "ON account.id=bank_card.account_id WHERE account.status='ACTIVE' AND account.user_id=?";
        public static final String SELECT_USER_ACCOUNTS_BY_USER_ID = "SELECT * FROM account WHERE user_id = ?";
        public static final String SELECT_COUNT_BY_USER_ID = "SELECT count(id) FROM account WHERE user_id = ?";
        public static final String SELECT_COUNT_ALL_ACCOUNTS = "SELECT count(id) FROM account";
        public static final String SELECT_COUNT_BY_USER_ID_AND_ACCOUNT_NAME = "SELECT count(id) FROM account " +
                "WHERE user_id = ? AND name = ?";
        public static final String SELECT_USER_ACCOUNTS_WITH_BANK_CARDS = "SELECT account.* FROM account " +
                "INNER JOIN bank_card ON account.id=bank_card.account_id WHERE account.user_id= ?";
        public static final String SELECT_ACTIVE_USER_ACCOUNTS_WITH_BANK_CARDS = "SELECT account.* FROM account " +
                "INNER JOIN bank_card ON account.id=bank_card.account_id WHERE account.status='ACTIVE' " +
                "AND account.user_id= ?";
        public static final String SELECT_ACCOUNT_BY_ID = "SELECT * FROM account WHERE id = ?";
        public static final String UPDATE_ACCOUNT_BALANCE = "UPDATE account SET balance = ? WHERE id = ?";
        public static final String UPDATE_ACCOUNT_BY_ID = "UPDATE account SET user_id = ?, name = ?, status = ?, " +
                "balance = ?, create_time = ? WHERE id = ?";
        public static final String INSERT_ACCOUNT = "INSERT INTO account(user_id, name, status, balance, create_time)" +
                " VALUES(?, ?, ?, ?, ?)";
        public static final String QUERY_TO_FORMAT_SELECT_USER_ACCOUNTS_BY_USER_ID_ORDERED_IN_RANGE =
                "SELECT * FROM account WHERE user_id = ? ORDER BY %s %s LIMIT ?, ?";
        public static final String QUERY_TO_FORMAT_SELECT_ALL_ACCOUNTS_ORDERED_IN_RANGE =
                "SELECT * FROM account ORDER BY %s %s LIMIT ?, ?";
    }

    static class BankCardQuery {
        public static final String SELECT_BANKCARD_BY_ID = "SELECT * FROM bank_card WHERE id = ?";
        public static final String SELECT_BANKCARD_BY_ACCOUNT_ID = "SELECT * FROM bank_card WHERE account_id = ?";
        public static final String INSERT_BANKCARD = "INSERT INTO bank_card(account_id, card_number, type, currency, " +
                "expiration_date, cardholder_name, balance, cvv) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        ;
        public static final String UPDATE_BANKCARD_BY_ACCOUNT_ID = "UPDATE bank_card SET account_id = ?, " +
                "card_number = ?, type = ?, currency = ?, expiration_date = ?, cardholder_name = ?, balance = ?, " +
                "cvv = ? WHERE id = ?";
        public static final String UPDATE_BANKCARD_BALANCE = "UPDATE bank_card SET balance = ? WHERE id = ?";
    }

    static class PaymentQuery {
        public static final String INSERT_PAYMENT = "INSERT INTO payment(sender_account_id, " +
               "receiver_account_id, amount, currency, status, create_time, send_time) VALUES(?, ?, ?, ?, ?, ?, ?)";
        public static final String SELECT_PAYMENT_BY_ID = "SELECT * FROM payment WHERE id = ?";
        public static final String SELECT_ALL_PAYMENTS = "SELECT * FROM payment";
        public static final String SELECT_PAYMENTS_BY_USER_ID = "SELECT * FROM payment " +
                "WHERE sender_account_id IN (SELECT id FROM account WHERE user_id = ?) " +
                "OR receiver_account_id IN (SELECT id FROM account WHERE user_id = ?)";
        public static final String SELECT_USER_PAYMENTS_BY_USER_ID_IN_RANGE = "SELECT * FROM payment " +
                "WHERE sender_account_id IN (SELECT id FROM account WHERE user_id = ?) " +
                "OR receiver_account_id IN (SELECT id FROM account WHERE user_id = ?) " +
                "LIMIT ?, ?";
        public static final String SELECT_PAYMENTS_FROM_USER = "SELECT * FROM payment " +
                "WHERE sender_account_id IN (SELECT id FROM account WHERE user_id = ?)";
        public static final String SELECT_RECEIVED_PAYMENTS_BY_USER_ID = "SELECT * FROM payment " +
                "WHERE receiver_account_id IN (SELECT id FROM account WHERE user_id = ?)";

        public static final String SELECT_COUNT_PAYMENTS_BY_SENDER_ACCOUNT_ID = "SELECT count(id) FROM payment WHERE sender_account_id = ?";
        public static final String UPDATE_PAYMENT_BY_ID = "UPDATE payment SET sender_account_id = ?, " +
                "receiver_account_id = ?, amount = ?, currency = ?, status = ?, create_time = ?, send_time = ? WHERE id = ?";

        public static final String QUERY_TO_FORMAT_SELECT_USER_PAYMENTS_BY_USER_ID_ORDERED_IN_RANGE =
                "SELECT * FROM payment WHERE sender_account_id IN (SELECT id FROM account WHERE user_id = ?) " +
                        " OR receiver_account_id IN (SELECT id FROM account WHERE user_id = ?) ORDER BY %s %s LIMIT ?, ?";
        public static final String SELECT_PAYMENTS_COUNT_BY_USER_ID = "SELECT COUNT(id) FROM payment " +
                "WHERE sender_account_id IN (SELECT id FROM account WHERE user_id = ?)" +
                "OR receiver_account_id IN (SELECT id FROM account WHERE user_id = ?)";
        public static final String QUERY_TO_FORMAT_SELECT_ALL_PAYMENTS_ORDERED_IN_RANGE =
                "SELECT * FROM payment ORDER BY %s %s LIMIT ?, ?";
        public static final String SELECT_COUNT_ALL_PAYMENTS = "SELECT count(id) FROM payment";
    }
}