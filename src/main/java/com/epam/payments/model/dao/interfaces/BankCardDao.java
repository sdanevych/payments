package com.epam.payments.model.dao.interfaces;

import com.epam.payments.model.dao.Dao;
import com.epam.payments.model.entity.account.BankCard;

public interface BankCardDao extends Dao<BankCard> {
    BankCard selectBankCardByAccountId(long accountId);
}
