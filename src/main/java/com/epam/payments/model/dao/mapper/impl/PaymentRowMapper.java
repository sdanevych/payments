package com.epam.payments.model.dao.mapper.impl;

import com.epam.payments.model.dao.mapper.Column;
import com.epam.payments.model.dao.mapper.RowMapper;
import com.epam.payments.model.entity.account.Currency;
import com.epam.payments.model.entity.payment.Payment;
import com.epam.payments.model.entity.payment.PaymentStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentRowMapper implements RowMapper<Payment> {
    private static PaymentRowMapper instance;

    private PaymentRowMapper() {
    }

    public static PaymentRowMapper getInstance() {
        if (instance == null) {
            synchronized (PaymentRowMapper.class) {
                if (instance == null) {
                    instance = new PaymentRowMapper();
                }
            }
        }
        return instance;
    }
    @Override
    public Payment map(ResultSet resultSet) throws SQLException {
        Payment payment = new Payment();
        payment.setId(resultSet.getLong(Column.ID));
        payment.setSenderAccountId(resultSet.getLong(Column.PAYMENT_SENDER_ACCOUNT_ID));
        payment.setReceiverAccountId(resultSet.getLong(Column.PAYMENT_RECEIVER_ACCOUNT_ID));
        payment.setAmount(resultSet.getBigDecimal(Column.PAYMENT_AMOUNT));
        payment.setCurrency(Currency.valueOf(resultSet.getString(Column.PAYMENT_CURRENCY)));
        payment.setPaymentStatus(PaymentStatus.valueOf(resultSet.getString(Column.PAYMENT_STATUS)));
        payment.setCreateTime(resultSet.getTimestamp(Column.PAYMENT_CREATE_TIME));
        payment.setSendTime(resultSet.getTimestamp(Column.PAYMENT_SEND_TIME));
        return payment;
    }
}
