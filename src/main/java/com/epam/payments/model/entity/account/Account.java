package com.epam.payments.model.entity.account;

import com.epam.payments.model.entity.Entity;
import com.epam.payments.utils.constant.SettingsConstant;
import com.epam.payments.utils.resource_manager.PageManager;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Account extends Entity {
    private Long userId;
    private String name;
    private AccountStatus status;
    private BigDecimal balance;
    private Timestamp createTime;
    private static Long itemsPerPage;

    static {
        itemsPerPage = Long.valueOf(PageManager.getProperty(SettingsConstant.PAGINATION_ACCOUNT_ITEMS_PER_PAGE));
    }

    public Account(Long userId, String name, AccountStatus status, BigDecimal balance, Date createTime) {
        this.userId = userId;
        this.name = name;
        this.status = status;
        this.balance = balance;
    }

    public Account() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public static Long getItemsPerPage() {
        return Account.itemsPerPage;
    }

    public static void setItemsPerPage(Long itemsPerPage) {
        Account.itemsPerPage = itemsPerPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (!Objects.equals(userId, account.userId)) return false;
        if (!Objects.equals(name, account.name)) return false;
        if (status != account.status) return false;
        if (!Objects.equals(balance, account.balance)) return false;
        return Objects.equals(createTime, account.createTime);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId=" + userId +
                ", accountName='" + name + '\'' +
                ", accountStatus=" + status +
                ", balance=" + balance +
                ", createTime=" + createTime +
                '}';
    }
}
