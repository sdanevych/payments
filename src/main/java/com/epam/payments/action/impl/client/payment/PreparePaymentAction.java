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
import com.epam.payments.model.entity.account.BankCard;
import com.epam.payments.model.entity.account.Currency;
import com.epam.payments.model.entity.payment.Payment;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.model.entity.user.UserStatus;
import com.epam.payments.service.AccountService;
import com.epam.payments.service.BankCardService;
import com.epam.payments.service.PaymentService;
import com.epam.payments.utils.DateTimeManager;
import com.epam.payments.utils.Encryption;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.MessageConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.resource_manager.MessageManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;
import com.epam.payments.utils.validator.BankCardValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class PreparePaymentAction implements DatabaseAction {
    private final AccountService accountService;
    PaymentService paymentService;
    BankCardService bankCardService;

    {
        DaoFactory daoFactory = MySqlDaoFactory.getInstance();
        AccountDao accountDao = daoFactory.getAccountDao();
        accountService = AccountService.getInstance();
        accountService.setAccountDao(accountDao);
        PaymentDao paymentDao = daoFactory.getPaymentDao();
        paymentService = PaymentService.getInstance();
        paymentService.setPaymentDao(paymentDao);
        BankCardDao bankCardDao = daoFactory.getBankCardDao();
        bankCardService = BankCardService.getInstance();
        bankCardService.setBankCardDao(bankCardDao);
    }

    @Override
    public Navigator execute(HttpServletRequest request) throws ActionException {
        Navigator navigator = new Navigator();
        HttpSession session = request.getSession();
        MessageManager messageManager = getLocalizedMessageManager(session);
        User user = (User) session.getAttribute(AttributeConstant.USER);
        Long paymentSenderAccountId = Long.valueOf(request.getParameter(AttributeConstant.PAYMENT_SENDER_ACCOUNT_ID));
        Account paymentsenderAccount = accountService.getAccount(paymentSenderAccountId);
        Long paymentReceiverAccountId = Long.valueOf(request.getParameter(AttributeConstant.PAYMENT_RECEIVER_ACCOUNT_ID));
        Account paymentReceiverAccount = accountService.getAccount(paymentReceiverAccountId);
        String enteredCvv = request.getParameter(AttributeConstant.PAYMENT_BANK_CARD_CVV);
        BigDecimal paymentAmount = new BigDecimal(request.getParameter(AttributeConstant.PAYMENT_AMOUNT));
        String enteredBankCardExpirationDateString = request.getParameter(AttributeConstant.PAYMENT_BANK_CARD_EXPIRATION_DATE);
        String pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_CREATE_PAYMENT);

        if (paymentReceiverAccount != null) {
            BankCard senderBankCard = bankCardService.getBankCardByAccountId(paymentsenderAccount.getId());
            BankCard receiverBankCard = bankCardService.getBankCardByAccountId(paymentReceiverAccount.getId());
            Date senderBankCardExpirationDate = senderBankCard.getExpirationDate();
            Currency senderCurrency = senderBankCard.getCurrency();

            boolean isPaymentEligible = user.getStatus() == UserStatus.ACTIVE
                    && paymentsenderAccount.getStatus() == AccountStatus.ACTIVE
                    && paymentReceiverAccount.getStatus() == AccountStatus.ACTIVE;

            if (!(paymentReceiverAccount.getStatus() == AccountStatus.ACTIVE)) {
                request.setAttribute(AttributeConstant.RECEIVER_ACCOUNT_NOT_ACTIVE,
                        messageManager.getMessage(MessageConstant.ERROR_RECEIVER_ACCOUNT_NOT_ACTIVE));
            }

            if (!accountService.isSufficientBalanceToCreatePayment(paymentsenderAccount, paymentAmount)) {
                request.setAttribute(AttributeConstant.NOT_ENOUGH_ACCOUNT_AMOUNT,
                        messageManager.getMessage(MessageConstant.ERROR_NOT_ENOUGH_ACCOUNT_AMOUNT));
                isPaymentEligible = false;
            }

            if (BankCardValidator.isValidBankCardExpirationDateFormat(enteredBankCardExpirationDateString)) {
                Date enteredBankCardExpirationDate = DateTimeManager.getBankCardExpirationDate(enteredBankCardExpirationDateString);
                if (enteredBankCardExpirationDate.equals(senderBankCardExpirationDate)) {
                    if (!senderBankCardExpirationDate.after(new Date(System.currentTimeMillis()))) {
                        request.setAttribute(AttributeConstant.BANKCARD_HAS_EXPIRED,
                                messageManager.getMessage(MessageConstant.ERROR_BANKCARD_HAS_EXPIRED));
                        isPaymentEligible = false;
                    }
                } else {
                    request.setAttribute(AttributeConstant.BANK_CARD_EXPIRATION_DATES_NOT_MATCH,
                            messageManager.getMessage(MessageConstant.ERROR_INCORRECT_BANK_CARD_EXPIRATION_DATE));
                    isPaymentEligible = false;
                }

            } else {
                request.setAttribute(AttributeConstant.INVALID_BANK_CARD_EXPIRATION_DATE_FORMAT,
                        messageManager.getMessage(MessageConstant.ERROR_INVALID_BANK_CARD_EXPIRATION_DATE_FORMAT));
                isPaymentEligible = false;
            }

            if (BankCardValidator.isValidBankCardCvv(enteredCvv)) {
                String encryptedEnteredCvv = Encryption.encrypt(enteredCvv);
                if (!encryptedEnteredCvv.equals(senderBankCard.getCvv())) {
                    request.setAttribute(AttributeConstant.INCORRECT_ENTERED_CVV,
                            messageManager.getMessage(MessageConstant.ERROR_INCORRECT_ENTERED_CVV));
                    isPaymentEligible = false;
                }
            } else {
                request.setAttribute(AttributeConstant.INVALID_BANK_CARD_CVV,
                        messageManager.getMessage(MessageConstant.ERROR_INVALID_BANK_CARD_CVV));
                isPaymentEligible = false;
            }


            if (receiverBankCard != null) {
                Currency receiverCurrency = receiverBankCard.getCurrency();
                if (senderCurrency != receiverCurrency) {
                    request.setAttribute(AttributeConstant.RECEIVER_AND_SENDER_ACCOUNT_CURRENCIES_NOT_MATCH,
                            messageManager.getMessage(MessageConstant.ERROR_RECEIVER_AND_SENDER_ACCOUNT_CURRENCIES_DO_NOT_MATCH));
                    isPaymentEligible = false;
                }
            } else {
                request.setAttribute(AttributeConstant.RECEIVER_BANK_CARD_NOT_EXISTS,
                        messageManager.getMessage(MessageConstant.ERROR_RECEIVER_BANK_CARD_NOT_EXISTS));
                isPaymentEligible = false;
            }

            if (isPaymentEligible) {
                Payment createdPayment = paymentService.prepareAndSavePayment(paymentsenderAccount, paymentReceiverAccount, paymentAmount, senderCurrency);
                List<Payment> clientPayments = paymentService.getAllClientPayments(user.getId());
                Long userId = user.getId();
                Long page = PageManager.getPage(request);
                Long itemsPerPage = Payment.getItemsPerPage();
                EntitySortManager paymentSortManager = (EntitySortManager) session.getAttribute(AttributeConstant.PAYMENTS_SORT_MANAGER);
                PaginationManager<Payment> paymentsPaginationManager = paymentService.getPaymentsPaginationManagerByUserId(userId, page, itemsPerPage, paymentSortManager);
                session.setAttribute(AttributeConstant.CLIENT_PAYMENTS, clientPayments);
                request.setAttribute(AttributeConstant.PAGINATION_MANAGER, paymentsPaginationManager);
                request.setAttribute(AttributeConstant.CREATE_PAYMENT_SUCCESS,
                        messageManager.getMessage(MessageConstant.INFO_CREATE_PAYMENT_SUCCESS));
                request.setAttribute(AttributeConstant.CREATED_PAYMENT_ID, createdPayment.getId());
                pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_PAYMENTS);
            } else {
                request.setAttribute(AttributeConstant.CREATE_PAYMENT_FAIL,
                        messageManager.getMessage(MessageConstant.ERROR_CREATE_PAYMENT_FAIL));
            }
        } else {
            request.setAttribute(AttributeConstant.RECEIVER_ACCOUNT_NOT_EXISTS,
                    messageManager.getMessage(MessageConstant.ERROR_RECEIVER_ACCOUNT_NOT_EXISTS));
        }

        Long page = PageManager.getPage(request);
        Long itemsPerPage = Payment.getItemsPerPage();
        EntitySortManager paymentSortManager = (EntitySortManager) session.getAttribute(AttributeConstant.PAYMENTS_SORT_MANAGER);
        PaginationManager<Payment> paymentsPaginationManager =
                paymentService.getPaymentsPaginationManagerByUserId(user.getId(), page, itemsPerPage, paymentSortManager);
        request.setAttribute(AttributeConstant.PAGINATION_MANAGER, paymentsPaginationManager);
        navigator.setPagePath(pagePath);
        return navigator;
    }
}
