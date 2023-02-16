package com.epam.payments.model.dao.mapper;

import com.epam.payments.model.dao.mapper.impl.AccountRowMapper;
import com.epam.payments.model.dao.mapper.impl.BankCardRowMapper;
import com.epam.payments.model.dao.mapper.impl.PaymentRowMapper;
import com.epam.payments.model.dao.mapper.impl.UserRowMapper;

public class RowMapperFactory {
    private static RowMapperFactory instance;

    private RowMapperFactory() {
    }

    public static RowMapperFactory getInstance() {
        if (instance == null) {
            synchronized (RowMapperFactory.class) {
                if (instance == null) {
                    instance = new RowMapperFactory();
                }
            }
        }
        return instance;
    }

    public UserRowMapper createUserRowMapper() {
        return UserRowMapper.getInstance();
    }

    public AccountRowMapper createAccountRowMapper() {
        return AccountRowMapper.getInstance();
    }
    public BankCardRowMapper createBankCardRowMapper() {
        return BankCardRowMapper.getInstance();
    }

    public PaymentRowMapper createPaymentRowMapper() {
        return PaymentRowMapper.getInstance();
    }
}
