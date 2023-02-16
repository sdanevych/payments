package com.epam.payments.service;

import com.epam.payments.exception.PaymentException;
import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.dao.interfaces.PaymentDao;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.model.entity.account.Currency;
import com.epam.payments.model.entity.payment.Payment;
import com.epam.payments.model.entity.payment.PaymentStatus;
import com.epam.payments.utils.DateTimeManager;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.sort.EntitySortManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class PaymentService {
    private static PaymentService instance;
    private static final Logger LOGGER = LogManager.getLogger(PaymentService.class);
    private PaymentDao paymentDao;
    private AccountDao accountDao;
    private AccountService accountService;

    {
        DaoFactory daoFactory = MySqlDaoFactory.getInstance();
        paymentDao = daoFactory.getPaymentDao();
        accountDao = daoFactory.getAccountDao();
        accountService = AccountService.getInstance();
        accountService.setAccountDao(accountDao);
    }

    private PaymentService() {
    }

    public static PaymentService getInstance() {
        if (instance == null) {
            synchronized (PaymentService.class) {
                if (instance == null) {
                    instance = new PaymentService();
                }
            }
        }
        return instance;
    }

    public Payment getPayment(Long paymentId) {
        return paymentDao.selectById(paymentId);
    }

    public List<Payment> getPaymentsFromClient(Long userId) {
        return paymentDao.selectPaymentsFromUser(userId);
    }

    public List<Payment> getPaymentsReceivedByClient(Long userId) {
        return paymentDao.selectPaymentsReceivedByUser(userId);
    }

    public Payment prepareAndSavePayment(Account senderAccount, Account receiverAccount, BigDecimal paymentAmount, Currency senderCurrency) {
        Payment payment = new Payment();
        payment.setSenderAccountId(senderAccount.getId());
        payment.setReceiverAccountId(receiverAccount.getId());
        payment.setAmount(paymentAmount);
        payment.setCurrency(senderCurrency);
        payment.setPaymentStatus(PaymentStatus.PREPARED);
        payment.setCreateTime(DateTimeManager.getCurrentTimestamp());
        Long paymentId = paymentDao.insert(payment);
        payment.setId(paymentId);
        return payment;
    }

    public Payment sendAndSavePayment(Payment payment) throws PaymentException {
        if (payment.getPaymentStatus() != PaymentStatus.PREPARED) {
            throw new PaymentException("Payment can not be sent. It has not PREPARED status");
        }
        Account senderAccount = accountService.getAccount(payment.getSenderAccountId());
        Account receiverAccount = accountService.getAccount(payment.getReceiverAccountId());
        BigDecimal paymentAmount = payment.getAmount();
        senderAccount.setBalance(senderAccount.getBalance().subtract(paymentAmount));
        receiverAccount.setBalance(receiverAccount.getBalance().add(paymentAmount));
        payment.setPaymentStatus(PaymentStatus.SENT);
        payment.setSendTime(DateTimeManager.getCurrentTimestamp());
        accountDao.update(senderAccount);
        accountDao.update(receiverAccount);
        paymentDao.update(payment);
        return payment;
    }

    public PaginationManager<Payment> getPaymentsPaginationManagerByUserId(Long userId, Long page, Long itemsPerPage, EntitySortManager entitySortManager) {
        Long offset = (page - 1) * itemsPerPage;
        List<Payment> entitiesList = getClientPaymentsInRange(userId, offset, itemsPerPage, entitySortManager);
        Long clientPaymentsCount = getClientPaymentsCount(userId);
        Long totalPages = (clientPaymentsCount / itemsPerPage) + (clientPaymentsCount % itemsPerPage == 0 ? 0 : 1);
        PaginationManager<Payment> paginationManager = new PaginationManager<>();
        paginationManager.setPaginationList(entitiesList);
        paginationManager.setPage(page);
        paginationManager.setTotalPages(totalPages);
        return paginationManager;
    }

    private Long getClientPaymentsCount(Long userId) {
        return paymentDao.selectCountPaymentsByUserId(userId);
    }

    public List<Payment> getAllClientPayments(Long userId) {
        return paymentDao.selectPaymentsByUserId(userId);
    }

    public List<Payment> getAllPayments() {
        return paymentDao.selectAll();
    }

    public Long getPaymentsFromClientCount(Long senderAccountId) {
        return paymentDao.selectCountPaymentsBySenderAccountId(senderAccountId);
    }

    public List<Payment> getClientPaymentsInRange(Long userId, Long offset, Long recordsCount, EntitySortManager entitySortManager) {
        return paymentDao.selectUserPaymentsInRange(userId, offset, recordsCount, entitySortManager);
    }

    public PaginationManager<Payment> getAllPaymentsPaginationManager(Long page, Long itemsPerPage, EntitySortManager adminAllPaymentsSortManager) {
        Long offset = (page - 1) * itemsPerPage;
        List<Payment> entitiesList = getAllPaymentsInRange(offset, itemsPerPage, adminAllPaymentsSortManager);
        Long clientAccountsCount = getAllPaymentsCount();
        Long totalPages = (clientAccountsCount / itemsPerPage) + (clientAccountsCount % itemsPerPage == 0 ? 0 : 1);
        PaginationManager<Payment> paginationManager = new PaginationManager<>();
        paginationManager.setPaginationList(entitiesList);
        paginationManager.setPage(page);
        paginationManager.setTotalPages(totalPages);
        return paginationManager;
    }

    private List<Payment> getAllPaymentsInRange(Long offset, Long recordsCount, EntitySortManager adminAllPaymentsSortManager) {
        return paymentDao.selectAllPaymentsInRange(offset, recordsCount, adminAllPaymentsSortManager);
    }

    private Long getAllPaymentsCount() {
        return paymentDao.selectAllCount();
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public PaymentDao getPaymentDao() {
        return paymentDao;
    }

    public void setPaymentDao(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }
}
