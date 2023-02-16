package com.epam.payments.action.impl.client.payment;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.exception.PaymentException;
import com.epam.payments.exception.sql.DaoSQLException;
import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.dao.interfaces.BankCardDao;
import com.epam.payments.model.dao.interfaces.PaymentDao;
import com.epam.payments.model.database.TransactionManager;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.model.entity.account.AccountStatus;
import com.epam.payments.model.entity.account.Currency;
import com.epam.payments.model.entity.payment.Payment;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.model.entity.user.UserStatus;
import com.epam.payments.service.AccountService;
import com.epam.payments.service.BankCardService;
import com.epam.payments.service.PaymentService;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.MessageConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.resource_manager.MessageManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class SendPaymentConfirmationAction implements DatabaseAction {
    private static final Logger LOGGER = LogManager.getLogger(SendPaymentConfirmationAction.class);
    private AccountService accountService;
    private PaymentService paymentService;
    private BankCardService bankCardService;

    {
        DaoFactory daoFactory = MySqlDaoFactory.getInstance();
        AccountDao accountDao = daoFactory.getAccountDao();
        accountService = AccountService.getInstance();
        accountService.setAccountDao(accountDao);
        BankCardDao bankCardDao = daoFactory.getBankCardDao();
        bankCardService = BankCardService.getInstance();
        bankCardService.setBankCardDao(bankCardDao);
        PaymentDao paymentDao = daoFactory.getPaymentDao();
        paymentService = PaymentService.getInstance();
        paymentService.setPaymentDao(paymentDao);
    }

    @Override
    public Navigator execute(HttpServletRequest request) throws ActionException {
        Navigator navigator = new Navigator();
        HttpSession session = request.getSession();
        MessageManager messageManager = getLocalizedMessageManager(session);
        User user = (User) session.getAttribute(AttributeConstant.USER);
        String userPaymentConfirmation = request.getParameter(AttributeConstant.USER_PAYMENT_CONFIRMATION_CODE);
        String actualPaymentConfirmation = (String) session.getAttribute(AttributeConstant.PAYMENT_ACTUAL_CONFIRMATION_CODE);
        Long paymentId = Long.valueOf(request.getParameter(AttributeConstant.PAYMENT_ID_TO_SEND));

        String pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_SEND_PAYMENT);
        try {
            TransactionManager.initiateTransaction();
            Payment payment = paymentService.getPayment(paymentId);
            Account senderAccount = accountService.getAccount(payment.getSenderAccountId());
            Account receiverAccount = accountService.getAccount(payment.getReceiverAccountId());
            BigDecimal paymentAmount = payment.getAmount();
            boolean isPaymentEligibleToSend = user.getStatus() == UserStatus.ACTIVE
                    && senderAccount.getStatus() == AccountStatus.ACTIVE
                    && receiverAccount.getStatus() == AccountStatus.ACTIVE;
            if (receiverAccount == null) {
                isPaymentEligibleToSend = false;
                request.setAttribute(AttributeConstant.RECEIVER_ACCOUNT_NOT_EXISTS,
                        messageManager.getMessage(MessageConstant.ERROR_RECEIVER_ACCOUNT_NOT_EXISTS));
            }
            if (!accountService.isSufficientBalanceToCreatePayment(senderAccount, paymentAmount)) {
                isPaymentEligibleToSend = false;
                request.setAttribute(AttributeConstant.NOT_ENOUGH_ACCOUNT_AMOUNT,
                        messageManager.getMessage(MessageConstant.ERROR_NOT_ENOUGH_ACCOUNT_AMOUNT));
            }

            Currency senderCurrency = bankCardService.getBankCardByAccountId(senderAccount.getId()).getCurrency();
            Currency receiverCurrency = bankCardService.getBankCardByAccountId(receiverAccount.getId()).getCurrency();
            if (senderCurrency != receiverCurrency) {
                isPaymentEligibleToSend = false;
                request.setAttribute(AttributeConstant.RECEIVER_AND_SENDER_ACCOUNT_CURRENCIES_NOT_MATCH,
                        messageManager.getMessage(MessageConstant.ERROR_RECEIVER_AND_SENDER_ACCOUNT_CURRENCIES_DO_NOT_MATCH));
            }
            if (!userPaymentConfirmation.equals(actualPaymentConfirmation)) {
                isPaymentEligibleToSend = false;
                request.setAttribute(AttributeConstant.PAYMENT_INCORRECT_CONFIRMATION_CODE,
                        messageManager.getMessage(MessageConstant.ERROR_PAYMENT_INCORRECT_CONFIRMATION_CODE));
            }
            if (isPaymentEligibleToSend) {
                paymentService.sendAndSavePayment(payment);
                session.setAttribute(AttributeConstant.PAYMENT_ACTUAL_CONFIRMATION_CODE, null);
                Long userId = user.getId();
                Long page = PageManager.getPage(request);
                Long itemsPerPage = Payment.getItemsPerPage();
                EntitySortManager paymentSortManager = (EntitySortManager) session.getAttribute(AttributeConstant.PAYMENTS_SORT_MANAGER);
                PaginationManager<Payment> paymentsPaginationManager = paymentService
                        .getPaymentsPaginationManagerByUserId(userId, page, itemsPerPage, paymentSortManager);
                List<Payment> clientPayments = paymentService.getAllClientPayments(userId);
                session.setAttribute(AttributeConstant.CLIENT_PAYMENTS, clientPayments);
                request.setAttribute(AttributeConstant.SENT_PAYMENT_ID, paymentId);
                request.setAttribute(AttributeConstant.PAGINATION_MANAGER, paymentsPaginationManager);
                request.setAttribute(AttributeConstant.SEND_PAYMENT_SUCCESS,
                        messageManager.getMessage(MessageConstant.INFO_SEND_PAYMENT_SUCCESS));
                pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_PAYMENTS);
            } else {
                request.setAttribute(AttributeConstant.PAYMENT_IS_NOT_ELIGIBLE_TO_SEND,
                        messageManager.getMessage(MessageConstant.ERROR_PAYMENT_IS_NOT_ELIGIBLE_TO_SEND));
            }
            request.setAttribute(AttributeConstant.PAYMENT_ID_TO_SEND, paymentId);
            TransactionManager.commitTransaction();
        } catch (DaoSQLException | PaymentException e) {
            LOGGER.error("System failure during processing payment " + paymentId + ": " + e.getMessage());
            request.setAttribute(AttributeConstant.SYSTEM_FAILURE_DURING_PAYMENT_PROCESSING,
                    messageManager.getMessage(MessageConstant.ERROR_SYSTEM_FAILURE_DURING_PAYMENT_PROCESSING));
            navigator.setPagePath(PagePathsConstant.PAGE_PATH_CLIENT_SEND_PAYMENT);
            TransactionManager.rollbackTransaction();
        }
        navigator.setPagePath(pagePath);
        return navigator;
    }
}
