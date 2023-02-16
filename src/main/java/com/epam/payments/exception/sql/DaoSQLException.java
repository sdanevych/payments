package com.epam.payments.exception.sql;

public class DaoSQLException extends RuntimeException {
    public DaoSQLException() {}

    public DaoSQLException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoSQLException(String message) {
        super(message);
    }

}
