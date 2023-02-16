package com.epam.payments.model.dao;

import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.dao.interfaces.BankCardDao;
import com.epam.payments.model.dao.interfaces.PaymentDao;
import com.epam.payments.model.dao.interfaces.UserDao;

public interface DaoFactory {
    AccountDao getAccountDao();

    BankCardDao getBankCardDao();

    PaymentDao getPaymentDao();

    UserDao getUserDao();
}
