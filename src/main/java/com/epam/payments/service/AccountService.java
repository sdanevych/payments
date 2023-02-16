package com.epam.payments.service;

import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.model.entity.account.AccountStatus;
import com.epam.payments.utils.DateTimeManager;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.sort.EntitySortManager;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {
    private static AccountService instance;
    private AccountDao accountDao;
    private AccountService() {
    }

    public static AccountService getInstance() {
        if (instance == null) {
            synchronized (AccountService.class) {
                if (instance == null) {
                    instance = new AccountService();
                }
            }
        }
        return instance;
    }

    public List<Account> getClientAccountsInRange(Long userId, Long offset, Long recordsCount, EntitySortManager entitySortManager) {
        return accountDao.selectUserAccountsInRange(userId, offset, recordsCount, entitySortManager);
    }

    public List<Account> getAllAccountsInRange(Long offset, Long recordsCount, EntitySortManager entitySortManager) {
        return accountDao.selectAllAccountsInRange(offset, recordsCount, entitySortManager);
    }

    public Account getAccount(Long accountId) {
        return accountDao.selectById(accountId);
    }

    public void updateAccount(Account account) {
        accountDao.update(account);
    }

    public List<Account> getActiveAccounts(Long userId) {
        return accountDao.selectActiveUserAccounts(userId);
    }

    public boolean isSufficientBalanceToCreatePayment(Account account, BigDecimal paymentAmount) {
        BigDecimal accountBalance = account.getBalance();
        return accountBalance.compareTo(paymentAmount) > 0;
    }

    public List<Account> getAllAccounts() {
        return accountDao.selectAll();
    }

    public boolean isUniqueAccountNameForSpecificUser(Long userId, String accountName) {
        long accountNameCountForSpecificUser = accountDao.selectCountByUserIdAndName(userId, accountName);
        return accountNameCountForSpecificUser == 0;
    }

    public Account addAccount(Long userId, String accountName) {
        Account account = new Account();
        account.setUserId(userId);
        account.setName(accountName);
        account.setStatus(AccountStatus.ACTIVE);
        account.setBalance(BigDecimal.ZERO);
        account.setCreateTime(DateTimeManager.getCurrentTimestamp());
        Long id = accountDao.insert(account);
        account.setId(id);
        return account;
    }

    public List<Long> getClientAccountIdsWithBankCards(Long userId) {
        List<Account> accounts = accountDao.selectUserAccountsWithBankCards(userId);
        return accounts.stream().map(Account::getId).toList();
    }

    public List<Account> getActiveAccountsWithBankCards(Long userId) {
        return accountDao.selectUserActiveAccountsWithBankCards(userId);
    }

    public PaginationManager<Account> getAccountsPaginationManagerByUserId(Long userId, Long page, Long itemsPerPage, EntitySortManager entitySortManager) {
        Long offset = (page - 1) * itemsPerPage;
        List<Account> entitiesList = getClientAccountsInRange(userId, offset, itemsPerPage, entitySortManager);
        Long clientAccountsCount = getClientAccountsCount(userId);
        Long totalPages = (clientAccountsCount / itemsPerPage) + (clientAccountsCount % itemsPerPage == 0 ? 0 : 1);
        PaginationManager<Account> paginationManager = new PaginationManager<>();
        paginationManager.setPaginationList(entitiesList);
        paginationManager.setPage(page);
        paginationManager.setTotalPages(totalPages);
        return paginationManager;
    }

    public PaginationManager<Account> getAllAccountsPaginationManager(Long page, Long itemsPerPage, EntitySortManager accountsSortManager) {
        Long offset = (page - 1) * itemsPerPage;
        List<Account> entitiesList = getAllAccountsInRange(offset, itemsPerPage, accountsSortManager);
        Long clientAccountsCount = getAllAccountsCount();
        Long totalPages = (clientAccountsCount / itemsPerPage) + (clientAccountsCount % itemsPerPage == 0 ? 0 : 1);
        PaginationManager<Account> paginationManager = new PaginationManager<>();
        paginationManager.setPaginationList(entitiesList);
        paginationManager.setPage(page);
        paginationManager.setTotalPages(totalPages);
        return paginationManager;
    }

    private Long getClientAccountsCount(Long userId) {
        return accountDao.selectCountByUserID(userId);
    }

    private Long getAllAccountsCount() {
        return accountDao.selectAllCount();
    }

    public List<Account> getClientAccountsByUserId(Long userId) {
        return accountDao.selectAccountsByUserId(userId);
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
