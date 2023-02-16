package com.epam.payments.exception.sql;

public class UpdateEntitySQLException extends DaoSQLException {
    public UpdateEntitySQLException(String message, Throwable cause) {
        super(message, cause);
    }
}
