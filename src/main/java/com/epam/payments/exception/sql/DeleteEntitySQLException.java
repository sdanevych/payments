package com.epam.payments.exception.sql;

public class DeleteEntitySQLException extends DaoSQLException {
    public DeleteEntitySQLException(String message, Throwable cause) {
        super(message, cause);
    }
}
