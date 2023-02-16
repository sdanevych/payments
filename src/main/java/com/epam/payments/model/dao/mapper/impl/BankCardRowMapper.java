package com.epam.payments.model.dao.mapper.impl;

import com.epam.payments.model.dao.mapper.Column;
import com.epam.payments.model.dao.mapper.RowMapper;
import com.epam.payments.model.entity.account.BankCard;
import com.epam.payments.model.entity.account.BankCardType;
import com.epam.payments.model.entity.account.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BankCardRowMapper implements RowMapper<BankCard> {
    private static BankCardRowMapper instance;

    private BankCardRowMapper() {
    }

    public static BankCardRowMapper getInstance() {
        if (instance == null) {
            synchronized (BankCardRowMapper.class) {
                if (instance == null) {
                    instance = new BankCardRowMapper();
                }
            }
        }
        return instance;
    }

    @Override
    public BankCard map(ResultSet resultSet) throws SQLException {
        BankCard bankCard = new BankCard();
        bankCard.setId(resultSet.getLong(Column.ID));
        bankCard.setAccountId(resultSet.getLong(Column.BANK_CARD_ACCOUNT_ID));
        bankCard.setCardNumber(resultSet.getString(Column.BANK_CARD_NUMBER));
        bankCard.setBankCardType(BankCardType.valueOf(resultSet.getString(Column.BANK_CARD_TYPE)));
        bankCard.setCurrency(Currency.valueOf(resultSet.getString(Column.BANK_CARD_CURRENCY)));
        bankCard.setExpirationDate(resultSet.getDate(Column.BANK_CARD_EXPIRATION_DATE));
        bankCard.setCardholderName(resultSet.getString(Column.BANK_CARD_CARDHOLDER_NAME));
        bankCard.setBalance(resultSet.getBigDecimal(Column.BANK_CARD_BALANCE));
        bankCard.setCvv(resultSet.getString(Column.BANK_CARD_CVV));
        return bankCard;
    }
}
