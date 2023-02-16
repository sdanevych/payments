package com.epam.payments.service;

import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.sort.EntitySortManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
class AccountServiceTest {
    @Mock
    private static AccountDao accountDao;
    private static AccountService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(AccountServiceTest.class);
        underTest = AccountService.getInstance();
        underTest.setAccountDao(accountDao);
    }

    @Test
    void checkIsSufficientBalanceToCreatePaymentWhenAccountBalanceGreaterThanPaymentAmount() {
        Account account = new Account();
        account.setBalance(BigDecimal.TEN);
        BigDecimal paymentAmount = BigDecimal.ONE;
        boolean isSufficientBalance = underTest.isSufficientBalanceToCreatePayment(account, paymentAmount);
        assertThat(isSufficientBalance).isTrue();
    }

    @Test
    void checkIsSufficientBalanceToCreatePaymentWhenAccountBalanceLessThanPaymentAmount() {
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        BigDecimal paymentAmount = BigDecimal.ONE;
        boolean isSufficientBalance = underTest.isSufficientBalanceToCreatePayment(account, paymentAmount);
        assertThat(isSufficientBalance).isFalse();
    }

    @Test
    void checkIsSufficientBalanceToCreatePaymentWhenAccountBalanceEqualThanPaymentAmount() {
        Account account = new Account();
        account.setBalance(BigDecimal.ONE);
        BigDecimal paymentAmount = BigDecimal.ONE;
        boolean isSufficientBalance = underTest.isSufficientBalanceToCreatePayment(account, paymentAmount);
        assertThat(isSufficientBalance).isFalse();
    }

    @Test
    void checkIsUniqueAccountNameForSpecificUserPositive() {
        Long selectedAccountsCount = 0L;
        given(accountDao.selectCountByUserIdAndName(anyLong(), anyString())).willReturn(selectedAccountsCount);
        boolean isUniqueAccountName = underTest.isUniqueAccountNameForSpecificUser(1L, "John");
        assertThat(isUniqueAccountName).isTrue();
    }

    @Test
    void checkIsUniqueAccountNameForSpecificUserNegative() {
        Long selectedAccountsCount = 1L;
        given(accountDao.selectCountByUserIdAndName(anyLong(), anyString())).willReturn(selectedAccountsCount);
        boolean isUniqueAccountName = underTest.isUniqueAccountNameForSpecificUser(1L, "John");
        assertThat(isUniqueAccountName).isFalse();
    }

    @Test
    void checkIsCorrectAccountsPaginationManagerReturnedByUserId() {
        Long userId = 1L, page = 3L, itemsPerPage = 3L, expectedTotalPages = 4L;
        int accountsCount = 10;
        EntitySortManager entitySortManager = new EntitySortManager();
        List<Account> accountList = Collections.nCopies(accountsCount, new Account());
        PaginationManager<Account> expectedPaginationManager = new PaginationManager<>();
        expectedPaginationManager.setPaginationList(accountList);
        expectedPaginationManager.setPage(page);
        expectedPaginationManager.setTotalPages(expectedTotalPages);
        given(accountDao.selectUserAccountsInRange(anyLong(), anyLong(), anyLong(), any(EntitySortManager.class)))
                .willReturn(accountList);
        given(accountDao.selectCountByUserID(anyLong())).willReturn((long) accountsCount);

        PaginationManager<Account> actualAccountPaginationManager = underTest
                .getAccountsPaginationManagerByUserId(userId, page, itemsPerPage, entitySortManager);

        assertThat(actualAccountPaginationManager).isEqualTo(expectedPaginationManager);
    }

    @Test
    void checkIsCorrectAllAccountsPaginationManagerReturned() {
        Long page = 3L, itemsPerPage = 3L, expectedTotalPages = 4L;
        int accountsCount = 10;
        EntitySortManager entitySortManager = new EntitySortManager();
        List<Account> accountList = Collections.nCopies(accountsCount, new Account());
        PaginationManager<Account> expectedAllAccountsPaginationManager = new PaginationManager<>();
        expectedAllAccountsPaginationManager.setPaginationList(accountList);
        expectedAllAccountsPaginationManager.setPage(page);
        expectedAllAccountsPaginationManager.setTotalPages(expectedTotalPages);
        given(accountDao.selectAllAccountsInRange(anyLong(), anyLong(), any(EntitySortManager.class)))
                .willReturn(accountList);
        given(accountDao.selectAllCount()).willReturn((long) accountsCount);
        PaginationManager<Account> actualAllAccountsPaginationManager = underTest
                .getAllAccountsPaginationManager(page, itemsPerPage, entitySortManager);
        assertThat(actualAllAccountsPaginationManager).isEqualTo(expectedAllAccountsPaginationManager);
    }
}