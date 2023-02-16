package com.epam.payments.service;

import com.epam.payments.exception.ReplenishException;
import com.epam.payments.exception.sql.DaoSQLException;
import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.dao.interfaces.BankCardDao;
import com.epam.payments.model.database.TransactionManager;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.model.entity.account.BankCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class ReplenishAccountService {
    private static final Logger LOGGER = LogManager.getLogger(ReplenishAccountService.class);
    private static ReplenishAccountService instance;
    private final AccountService accountService;
    private final BankCardService bankCardService;

    {
        DaoFactory daoFactory = MySqlDaoFactory.getInstance();
        AccountDao accountDao = daoFactory.getAccountDao();
        accountService = AccountService.getInstance();
        accountService.setAccountDao(accountDao);
        BankCardDao bankCardDao = daoFactory.getBankCardDao();
        bankCardService = BankCardService.getInstance();
        bankCardService.setBankCardDao(bankCardDao);
    }

    private ReplenishAccountService() {
    }

    public static ReplenishAccountService getInstance() {
        if (instance == null) {
            synchronized (ReplenishAccountService.class) {
                if (instance == null) {
                    instance = new ReplenishAccountService();
                }
            }
        }
        return instance;
    }

    public Account replenishAccountWithBankcard(Account account, BankCard bankCard, BigDecimal replenishAmount) throws ReplenishException {
        if (bankCard == null) {
            throw new ReplenishException("Replenish can not be accomplished. Bankcard is absent");
        }
        BigDecimal accountBalance = account.getBalance();
        BigDecimal newAccountBalance = accountBalance.add(replenishAmount);
        account.setBalance(newAccountBalance);

        BigDecimal bankCardBalance = bankCard.getBalance();
        BigDecimal newBankCardBalance = bankCardBalance.subtract(replenishAmount);
        bankCard.setBalance(newBankCardBalance);

        try {
            TransactionManager.initiateTransaction();
            accountService.updateAccount(account);
            bankCardService.update(bankCard);
            TransactionManager.commitTransaction();
        } catch (DaoSQLException e) {
            LOGGER.error("Account replenish error: " + e.getMessage());
            TransactionManager.rollbackTransaction();
        }
        return account;
    }
}
