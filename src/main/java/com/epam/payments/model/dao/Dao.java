package com.epam.payments.model.dao;

import com.epam.payments.exception.sql.DaoSQLException;

import java.util.List;

public interface Dao<E> {
    Long insert(E entity) throws DaoSQLException;

    void update(E entity) throws DaoSQLException;

    void delete(long id) throws DaoSQLException;

    List<E> selectAll() throws DaoSQLException;

    E selectById(long id) throws DaoSQLException;
}
