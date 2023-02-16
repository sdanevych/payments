package com.epam.payments.action.impl.common.auth.registration;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.dao.interfaces.PaymentDao;
import com.epam.payments.model.dao.interfaces.UserDao;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.model.entity.payment.Payment;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.service.AccountService;
import com.epam.payments.service.PaymentService;
import com.epam.payments.service.UserService;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.MessageConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.constant.SettingsConstant;
import com.epam.payments.utils.resource_manager.MessageManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class RegisterConfirmationAction implements DatabaseAction {
    private final AccountService accountService;
    private final PaymentService paymentService;
    private final UserService userService;

    {
        DaoFactory daoFactory = MySqlDaoFactory.getInstance();
        AccountDao accountDao = daoFactory.getAccountDao();
        accountService = AccountService.getInstance();
        accountService.setAccountDao(accountDao);
        PaymentDao paymentDao = daoFactory.getPaymentDao();
        paymentService = PaymentService.getInstance();
        paymentService.setPaymentDao(paymentDao);
        UserDao userDao = daoFactory.getUserDao();
        userService = UserService.getInstance();
        userService.setUserDao(userDao);
    }

    @Override
    public Navigator execute(HttpServletRequest request) throws ActionException {
        Navigator navigator = new Navigator();
        HttpSession session = request.getSession();
        MessageManager messageManager = getLocalizedMessageManager(session);
        String userCode = request.getParameter(AttributeConstant.USER_CONFIRMATION_CODE).trim();
        String actualCode = String.valueOf(session.getAttribute(AttributeConstant.ACTUAL_CONFIRMATION_CODE)).trim();
        String page;
        if (userCode.equals(actualCode)){
            User user = (User) session.getAttribute(AttributeConstant.USER);
            String encryptedPassword = String.valueOf(session.getAttribute(AttributeConstant.ENCRYPTED_PASSWORD));
            Long userId = userService.registerNewUser(user, encryptedPassword);
            user.setId(userId);
            List<Account> allClientAccounts = accountService.getClientAccountsByUserId(userId);
            List<Account> clientActiveAccounts = accountService.getActiveAccounts(userId);
            List<Long> clientAccountIdsWithBankCards = accountService.getClientAccountIdsWithBankCards(userId);
            List<Account> clientActiveAccountsWithBankCards = accountService.getActiveAccountsWithBankCards(userId);
            List<Payment> clientPayments = paymentService.getAllClientPayments(userId);
            List<Payment> clientReceivedPayments = paymentService.getPaymentsReceivedByClient(userId);
            EntitySortManager accountsSortManager = new EntitySortManager();
            EntitySortManager paymentsSortManager = new EntitySortManager();
            EntitySortManager usersSortManager = new EntitySortManager();
            session.setAttribute(AttributeConstant.LOGIN, user.getLogin());
            session.setAttribute(AttributeConstant.USER, user);
            session.setAttribute(AttributeConstant.ROLE, user.getRole());
            session.setAttribute(AttributeConstant.ACCOUNTS_SORT_MANAGER, accountsSortManager);
            session.setAttribute(AttributeConstant.PAYMENTS_SORT_MANAGER, paymentsSortManager);
            session.setAttribute(AttributeConstant.USERS_SORT_MANAGER, usersSortManager);
            session.setAttribute(AttributeConstant.CLIENT_ACCOUNTS, allClientAccounts);
            session.setAttribute(AttributeConstant.CLIENT_ACTIVE_ACCOUNTS, clientActiveAccounts);
            session.setAttribute(AttributeConstant.CLIENT_ACCOUNT_IDS_WITH_BANK_CARDS, clientAccountIdsWithBankCards);
            session.setAttribute(AttributeConstant.CLIENT_ACTIVE_ACCOUNTS_WITH_BANK_CARDS, clientActiveAccountsWithBankCards);
            session.setAttribute(AttributeConstant.CLIENT_PAYMENTS, clientPayments);
            session.setAttribute(AttributeConstant.CLIENT_RECEIVED_PAYMENTS, clientReceivedPayments);
            session.setAttribute(AttributeConstant.PASSWORD, encryptedPassword);
            session.setAttribute(AttributeConstant.CLIENT_ACCOUNTS_PAGE_PAGINATION_URL_PATTERN,
                    PageManager.getProperty(SettingsConstant.CLIENT_ACCOUNTS_PAGE_PAGINATION_URL_PATTERN));
            session.setAttribute(AttributeConstant.CLIENT_PAYMENTS_PAGE_PAGINATION_URL_PATTERN,
                    PageManager.getProperty(SettingsConstant.CLIENT_PAYMENTS_PAGE_PAGINATION_URL_PATTERN));
            session.setAttribute(AttributeConstant.ADMIN_ACCOUNTS_PAGE_PAGINATION_URL_PATTERN,
                    PageManager.getProperty(SettingsConstant.ADMIN_ACCOUNTS_PAGE_PAGINATION_URL_PATTERN));
            session.setAttribute(AttributeConstant.ADMIN_PAYMENTS_PAGE_PAGINATION_URL_PATTERN,
                    PageManager.getProperty(SettingsConstant.ADMIN_PAYMENTS_PAGE_PAGINATION_URL_PATTERN));
            request.setAttribute(AttributeConstant.REGISTRATION_SUCCESS,
                    MessageConstant.INFO_REGISTRATION_SUCCESS);
            page = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_MAIN);
        } else {
            request.setAttribute(AttributeConstant.REGISTRATION_FAIL,
                    messageManager.getMessage(MessageConstant.ERROR_REGISTRATION_FAIL));
            request.setAttribute(AttributeConstant.INCORRECT_CONFIRMATION_CODE,
                    messageManager.getMessage(MessageConstant.ERROR_INCORRECT_CONFIRMATION_CODE));
            page = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CONFIRMATION);
        }
        navigator.setPagePath(page);
        return navigator;
    }
}
