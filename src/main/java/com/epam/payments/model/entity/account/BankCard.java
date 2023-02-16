package com.epam.payments.model.entity.account;

import com.epam.payments.model.entity.Entity;
import com.epam.payments.utils.constant.SettingsConstant;
import com.epam.payments.utils.resource_manager.PageManager;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class BankCard extends Entity {
    private Long accountId;
    private String cardNumber;
    private BankCardType bankCardType;
    private Currency currency;
    private Date expirationDate;
    private String cardholderName;
    private BigDecimal balance;
    private String cvv;
    private static Long itemsPerPage;

    static {
        itemsPerPage = Long.valueOf(PageManager.getProperty(SettingsConstant.PAGINATION_BANKCARD_ITEMS_PER_PAGE));
    }

    public BankCard() {
    }

    public BankCard(Long id) {
        super(id);
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BankCardType getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(BankCardType bankCardType) {
        this.bankCardType = bankCardType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public static Long getItemsPerPage() {
        return itemsPerPage;
    }

    public static void setItemsPerPage(Long itemsPerPage) {
        BankCard.itemsPerPage = itemsPerPage;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankCard bankCard = (BankCard) o;

        if (!Objects.equals(accountId, bankCard.accountId)) return false;
        if (!Objects.equals(cardNumber, bankCard.cardNumber)) return false;
        if (bankCardType != bankCard.bankCardType) return false;
        if (currency != bankCard.currency) return false;
        if (!Objects.equals(expirationDate, bankCard.expirationDate))
            return false;
        if (!Objects.equals(cardholderName, bankCard.cardholderName))
            return false;
        if (!Objects.equals(balance, bankCard.balance)) return false;
        return Objects.equals(cvv, bankCard.cvv);
    }

    @Override
    public int hashCode() {
        int result = accountId != null ? accountId.hashCode() : 0;
        result = 31 * result + (cardNumber != null ? cardNumber.hashCode() : 0);
        result = 31 * result + (bankCardType != null ? bankCardType.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
        result = 31 * result + (cardholderName != null ? cardholderName.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (cvv != null ? cvv.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BankCard{" +
                "accountId=" + accountId +
                ", cardNumber='" + cardNumber + '\'' +
                ", bankCardType=" + bankCardType +
                ", currency=" + currency +
                ", expirationDate=" + expirationDate +
                ", cardholderName='" + cardholderName + '\'' +
                ", balance=" + balance +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}
