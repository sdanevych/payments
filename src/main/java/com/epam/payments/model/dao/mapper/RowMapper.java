package com.epam.payments.model.dao.mapper;

import com.epam.payments.model.entity.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<E extends Entity> {
    E map(ResultSet resultSet) throws SQLException;
}
