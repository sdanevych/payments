package com.epam.payments.service;

import com.epam.payments.model.dao.interfaces.BankCardDao;
import com.epam.payments.model.entity.account.BankCard;
import com.epam.payments.utils.DateTimeManager;

import java.math.BigDecimal;

public class BankCardService {
    private static BankCardService instance;
    private BankCardDao bankCardDao;

    private BankCardService() {
    }

    public static BankCardService getInstance() {
        if (instance == null) {
            synchronized (BankCardService.class) {
                if (instance == null) {
                    instance = new BankCardService();
                }
            }
        }
        return instance;
    }

    public boolean isSufficientBalanceToWithdraw(Long bankCardId, BigDecimal withdrawAmount) {
        BigDecimal bankCardAmount = getBankCard(bankCardId).getBalance();
        return bankCardAmount.compareTo(withdrawAmount) >= 0;
    }

    public BankCard getBankCard(Long id) {
        return bankCardDao.selectById(id);
    }

    public BankCard getBankCardByAccountId(Long accountId) {
        return bankCardDao.selectBankCardByAccountId(accountId);
    }

    public void addBankCard(BankCard bankCard) {
        bankCardDao.insert(bankCard);
    }

    public void setBankCardExpirationDateByMonthAndYear(BankCard bankCard, String expirationDateMonthAndYear) {
        bankCard.setExpirationDate(DateTimeManager.getBankCardExpirationDate(expirationDateMonthAndYear));
    }

    public void update(BankCard bankCard) {
        bankCardDao.update(bankCard);
    }

    public BankCardDao getBankCardDao() {
        return bankCardDao;
    }

    public void setBankCardDao(BankCardDao bankCardDao) {
        this.bankCardDao = bankCardDao;
    }
}
