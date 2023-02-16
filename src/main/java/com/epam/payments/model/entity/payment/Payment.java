package com.epam.payments.model.entity.payment;

import com.epam.payments.model.entity.Entity;
import com.epam.payments.model.entity.account.Currency;
import com.epam.payments.utils.constant.SettingsConstant;
import com.epam.payments.utils.resource_manager.PageManager;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public class Payment extends Entity {
    private Long senderAccountId;
    private Long receiverAccountId;
    private BigDecimal amount;
    private Currency currency;
    private PaymentStatus paymentStatus;
    private Timestamp createTime;
    private Timestamp sendTime;
    private static Long itemsPerPage;

    static {
        itemsPerPage = Long.valueOf(PageManager.getProperty(SettingsConstant.PAGINATION_PAYMENT_ITEMS_PER_PAGE));
    }

    public Payment() {
    }

    public Long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(Long senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public Long getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(Long receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public static Long getItemsPerPage() {
        return Payment.itemsPerPage;
    }

    public static void setItemsPerPage(Long itemsPerPage) {
        Payment.itemsPerPage = itemsPerPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (!Objects.equals(senderAccountId, payment.senderAccountId))
            return false;
        if (!Objects.equals(receiverAccountId, payment.receiverAccountId))
            return false;
        if (!Objects.equals(amount, payment.amount)) return false;
        if (currency != payment.currency) return false;
        if (paymentStatus != payment.paymentStatus) return false;
        if (!Objects.equals(createTime, payment.createTime)) return false;
        return Objects.equals(sendTime, payment.sendTime);
    }

    @Override
    public int hashCode() {
        int result = senderAccountId != null ? senderAccountId.hashCode() : 0;
        result = 31 * result + (receiverAccountId != null ? receiverAccountId.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (paymentStatus != null ? paymentStatus.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (sendTime != null ? sendTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "senderAccountId=" + senderAccountId +
                ", receiverAccountId=" + receiverAccountId +
                ", amount=" + amount +
                ", currency=" + currency +
                ", paymentStatus=" + paymentStatus +
                ", createTime=" + createTime +
                ", sendTime=" + sendTime +
                '}';
    }
}
