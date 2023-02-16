package com.epam.payments.exception.sql;

public class ParseEntitySQLException extends DaoSQLException {
    public ParseEntitySQLException() {
    }

    public ParseEntitySQLException(String message) {
        super(message);
    }

    public ParseEntitySQLException(String message, Throwable cause) {
        super(message, cause);
    }
}
