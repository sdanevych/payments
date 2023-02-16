package com.epam.payments.model.dao.impl.mysql;

import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.dao.interfaces.BankCardDao;
import com.epam.payments.model.dao.interfaces.PaymentDao;
import com.epam.payments.model.dao.interfaces.UserDao;

public class MySqlDaoFactory implements DaoFactory {
    private static MySqlDaoFactory instance;

    private MySqlDaoFactory() {
    }

    public static MySqlDaoFactory getInstance() {
        if (instance == null) {
            synchronized (MySqlDaoFactory.class) {
                if (instance == null) {
                    instance = new MySqlDaoFactory();
                }
            }
        }
        return instance;
    }

    @Override
    public AccountDao getAccountDao() {
        return AccountMySQLDao.getInstance();
    }

    @Override
    public BankCardDao getBankCardDao() {
        return BankCardMySQLDao.getInstance();
    }

    @Override
    public PaymentDao getPaymentDao() {
        return PaymentMySQLDao.getInstance();
    }

    @Override
    public UserDao getUserDao() {
        return UserMySQLDao.getInstance();
    }
}
