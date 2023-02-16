package com.epam.payments.model.dao.interfaces;

import com.epam.payments.model.dao.Dao;
import com.epam.payments.model.entity.payment.Payment;
import com.epam.payments.utils.sort.EntitySortManager;

import java.util.List;

public interface PaymentDao extends Dao<Payment> {

    List<Payment> selectPaymentsFromUser(Long userId);

    List<Payment> selectPaymentsReceivedByUser(Long userId);

    List<Payment> selectUserPaymentsInRange(Long userId, Long offset, Long recordsCount, EntitySortManager entitySortManager);

    Long selectCountPaymentsBySenderAccountId(Long userId);

    List<Payment> selectPaymentsByUserId(Long userId);

    Long selectCountPaymentsByUserId(Long userId);

    List<Payment> selectAllPaymentsInRange(Long offset, Long recordsCount, EntitySortManager adminAllPaymentsSortManager);

    Long selectAllCount();
}
