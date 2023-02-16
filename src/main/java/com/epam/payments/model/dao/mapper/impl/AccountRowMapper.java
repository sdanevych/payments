package com.epam.payments.model.dao.mapper.impl;

import com.epam.payments.model.dao.mapper.Column;
import com.epam.payments.model.dao.mapper.RowMapper;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.model.entity.account.AccountStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {
    private static AccountRowMapper instance;

    private AccountRowMapper() {
    }

    public static AccountRowMapper getInstance() {
        if (instance == null) {
            synchronized (AccountRowMapper.class) {
                if (instance == null) {
                    instance = new AccountRowMapper();
                }
            }
        }
        return instance;
    }

    @Override
    public Account map(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getLong(Column.ID));
        account.setUserId(resultSet.getLong(Column.ACCOUNT_USER_ID));
        account.setName(resultSet.getString(Column.ACCOUNT_NAME));
        account.setStatus(AccountStatus.valueOf(resultSet.getString(Column.ACCOUNT_STATUS)));
        account.setBalance(resultSet.getBigDecimal(Column.ACCOUNT_BALANCE));
        account.setCreateTime(resultSet.getTimestamp(Column.ACCOUNT_CREATE_TIME));
        return account;
    }
}
