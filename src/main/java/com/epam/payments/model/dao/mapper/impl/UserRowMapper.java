package com.epam.payments.model.dao.mapper.impl;

import com.epam.payments.model.dao.mapper.Column;
import com.epam.payments.model.dao.mapper.RowMapper;
import com.epam.payments.model.entity.user.Role;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.model.entity.user.UserStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    private static UserRowMapper instance;

    private UserRowMapper() {
    }

    public static UserRowMapper getInstance() {
        if (instance == null) {
            synchronized (UserRowMapper.class) {
                if (instance == null) {
                    instance = new UserRowMapper();
                }
            }
        }
        return instance;
    }

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(Column.ID));
        user.setLogin(resultSet.getString(Column.USER_LOGIN));
        user.setRole(Role.valueOf(resultSet.getString(Column.USER_ROLE)));
        user.setStatus(UserStatus.valueOf(resultSet.getString(Column.USER_STATUS)));
        user.setFirstName(resultSet.getString(Column.USER_FIRST_NAME));
        user.setSecondName(resultSet.getString(Column.USER_SECOND_NAME));
        user.setPhoneNumber(resultSet.getString(Column.USER_PHONE_NUMBER));
        user.setEmail(resultSet.getString(Column.USER_EMAIL));
        return user;
    }
}
