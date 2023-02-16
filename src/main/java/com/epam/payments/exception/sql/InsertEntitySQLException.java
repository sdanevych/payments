package com.epam.payments.exception.sql;

public class InsertEntitySQLException extends DaoSQLException {
    public InsertEntitySQLException(String message, Throwable cause) {
        super(message, cause);
    }
}
