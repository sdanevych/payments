package com.epam.payments.exception.sql;

public class SelectEntitySQLException extends DaoSQLException {
    public SelectEntitySQLException(String message, Throwable cause) {
        super(message, cause);
    }
}
