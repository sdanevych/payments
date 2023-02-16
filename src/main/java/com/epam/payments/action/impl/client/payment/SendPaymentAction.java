package com.epam.payments.action.impl.client.payment;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.dao.interfaces.BankCardDao;
import com.epam.payments.model.dao.interfaces.PaymentDao;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.model.entity.account.AccountStatus;
import com.epam.payments.model.entity.account.Currency;
import com.epam.payments.model.entity.payment.Payment;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.model.entity.user.UserStatus;
import com.epam.payments.service.AccountService;
import com.epam.payments.service.BankCardService;
import com.epam.payments.service.PaymentService;
import com.epam.payments.service.registration.EmailConfirmationService;
import com.epam.payments.utils.ConfirmationCodeGenerator;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.MessageConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.resource_manager.MessageManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;

public class SendPaymentAction implements DatabaseAction {
    private final AccountService accountService;
    private final BankCardService bankCardService;
    private final PaymentService paymentService;

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
        String actualConfirmationCode = ConfirmationCodeGenerator.generateConfirmationCode();
        new EmailConfirmationService(user.getEmail(), actualConfirmationCode, messageManager).start();

        Long paymentId = Long.valueOf(request.getParameter(AttributeConstant.PAYMENT_ID_TO_SEND));
        Payment payment = paymentService.getPayment(paymentId);
        Account senderAccount = accountService.getAccount(payment.getSenderAccountId());
        Account receiverAccount = accountService.getAccount(payment.getReceiverAccountId());
        BigDecimal paymentAmount = payment.getAmount();
        boolean isPaymentEligibleToSend = false;
        if (user.getStatus() == UserStatus.ACTIVE
                && senderAccount.getStatus() == AccountStatus.ACTIVE
                && receiverAccount.getStatus() == AccountStatus.ACTIVE) {
            isPaymentEligibleToSend = true;
        }
        if (receiverAccount == null) {
            request.setAttribute(AttributeConstant.RECEIVER_ACCOUNT_NOT_EXISTS,
                    messageManager.getMessage(MessageConstant.ERROR_RECEIVER_ACCOUNT_NOT_EXISTS));
            isPaymentEligibleToSend = false;
        }
        if (!accountService.isSufficientBalanceToCreatePayment(senderAccount, paymentAmount)) {
            request.setAttribute(AttributeConstant.NOT_ENOUGH_ACCOUNT_AMOUNT,
                    messageManager.getMessage(MessageConstant.ERROR_NOT_ENOUGH_ACCOUNT_AMOUNT));
            isPaymentEligibleToSend = false;
        }

        Currency senderCurrency = bankCardService.getBankCardByAccountId(senderAccount.getId()).getCurrency();
        Currency receiverCurrency = bankCardService.getBankCardByAccountId(receiverAccount.getId()).getCurrency();
        if (senderCurrency != receiverCurrency) {
            request.setAttribute(AttributeConstant.RECEIVER_AND_SENDER_ACCOUNT_CURRENCIES_NOT_MATCH,
                    messageManager.getMessage(MessageConstant.ERROR_RECEIVER_AND_SENDER_ACCOUNT_CURRENCIES_DO_NOT_MATCH));
            isPaymentEligibleToSend = false;
        }

        String pagePath;
        if (isPaymentEligibleToSend) {
            session.setAttribute(AttributeConstant.PAYMENT_ACTUAL_CONFIRMATION_CODE, actualConfirmationCode);
            request.setAttribute(AttributeConstant.PAGINATION_PAGE, PageManager.getPage(request));
            pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_SEND_PAYMENT);
        } else {
            Long userId = user.getId();
            Long page = PageManager.getPage(request);
            Long itemsPerPage = Payment.getItemsPerPage();
            EntitySortManager paymentSortManager = (EntitySortManager) session.getAttribute(AttributeConstant.PAYMENTS_SORT_MANAGER);
            PaginationManager<Payment> paymentsPaginationManager = paymentService.getPaymentsPaginationManagerByUserId(userId, page, itemsPerPage, paymentSortManager);
            request.setAttribute(AttributeConstant.PAGINATION_MANAGER, paymentsPaginationManager);
            request.setAttribute(AttributeConstant.PAYMENT_IS_NOT_ELIGIBLE_TO_SEND,
                    messageManager.getMessage(MessageConstant.ERROR_PAYMENT_IS_NOT_ELIGIBLE_TO_SEND));
            pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_PAYMENTS);
        }
        request.setAttribute(AttributeConstant.PAYMENT_ID_TO_SEND, paymentId);
        navigator.setPagePath(pagePath);
        return navigator;
    }
}
