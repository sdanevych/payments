package com.epam.payments.model.dao.interfaces;

import com.epam.payments.model.dao.Dao;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.utils.sort.EntitySortManager;

import java.util.List;

public interface AccountDao extends Dao<Account> {
    List<Account> selectUserAccountsInRange(Long userId, Long offset, Long recordsCount, EntitySortManager entitySortManager);
    List<Account> selectAllAccountsInRange(Long offset, Long recordsCount, EntitySortManager entitySortManager);

    List<Account> selectUserAccountsWithBankCards(Long userId);
    List<Account> selectUserActiveAccountsWithBankCards(Long userId);

    Long selectCountByUserIdAndName(Long userId, String accountName);

    List<Account> selectActiveUserAccounts(Long userId);

    List<Account> selectAccountsInRange(Long offset, Long recordsCount);

    List<Account> selectAccountsByUserId(Long userId);

    Long selectCountByUserID(Long userId);
    Long selectAllCount();
}
