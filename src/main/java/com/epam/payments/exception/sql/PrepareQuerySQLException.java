package com.epam.payments.exception.sql;

import com.epam.payments.model.entity.Entity;

public class PrepareQuerySQLException extends DaoSQLException {
    public PrepareQuerySQLException(Entity entity) {
        super("SQL query params fail to prepare for entity: " + entity);
    }

    public PrepareQuerySQLException() {
    }
}
