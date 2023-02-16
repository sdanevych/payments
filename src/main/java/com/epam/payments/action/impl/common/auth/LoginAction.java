package com.epam.payments.action.impl.common.auth;

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
import com.epam.payments.model.entity.user.Role;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.model.entity.user.UserStatus;
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
import com.epam.payments.utils.validator.UserRegistrationValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class LoginAction implements DatabaseAction {
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
        HttpSession session = request.getSession();
        MessageManager messageManager = getLocalizedMessageManager(session);
        String email = request.getParameter(AttributeConstant.EMAIL);
        String password = request.getParameter(AttributeConstant.PASSWORD);
        String pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_LOGIN);
        if (UserRegistrationValidator.isCorrectEmail(email) && UserRegistrationValidator.isCorrectPassword(password)) {
            User user = userService.getUserByEmailAndPassword(email, password);
            if (user != null) {
                if (user.getStatus() == UserStatus.ACTIVE) {
                    Role userRole = user.getRole();
                    switch (userRole) {
                        case ADMIN -> pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_ADMIN_MAIN);
                        case CLIENT -> pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_MAIN);
                    }
                    Long userId = user.getId();
                    EntitySortManager accountsSortManager = new EntitySortManager();
                    EntitySortManager paymentsSortManager = new EntitySortManager();
                    EntitySortManager usersSortManager = new EntitySortManager();
                    EntitySortManager adminAllPaymentsSortManager = new EntitySortManager();
                    session.setAttribute(AttributeConstant.LOGIN, user.getLogin());
                    session.setAttribute(AttributeConstant.ROLE, user.getRole());
                    session.setAttribute(AttributeConstant.USER, user);
                    session.setAttribute(AttributeConstant.PASSWORD, password);
                    session.setAttribute(AttributeConstant.ACCOUNTS_SORT_MANAGER, accountsSortManager);
                    session.setAttribute(AttributeConstant.PAYMENTS_SORT_MANAGER, paymentsSortManager);
                    session.setAttribute(AttributeConstant.ADMIN_ALL_PAYMENTS_SORT_MANAGER, adminAllPaymentsSortManager);
                    session.setAttribute(AttributeConstant.USERS_SORT_MANAGER, usersSortManager);

                    switch (userRole) {
                        case ADMIN -> {
                            List<Account> allAccounts = accountService.getAllAccounts();
                            List<Payment> allPayments = paymentService.getAllPayments();
                            List<User> allUsers = userService.getAllUsers();
                            session.setAttribute(AttributeConstant.ALL_ACCOUNTS, allAccounts);
                            session.setAttribute(AttributeConstant.ALL_PAYMENTS, allPayments);
                            session.setAttribute(AttributeConstant.ALL_USERS, allUsers);
                            session.setAttribute(AttributeConstant.USERS_SORT_MANAGER, usersSortManager);
                            session.setAttribute(AttributeConstant.ADMIN_ACCOUNTS_PAGE_PAGINATION_URL_PATTERN,
                                    PageManager.getProperty(SettingsConstant.ADMIN_ACCOUNTS_PAGE_PAGINATION_URL_PATTERN));
                            session.setAttribute(AttributeConstant.ADMIN_PAYMENTS_PAGE_PAGINATION_URL_PATTERN,
                                    PageManager.getProperty(SettingsConstant.ADMIN_PAYMENTS_PAGE_PAGINATION_URL_PATTERN));
                            session.setAttribute(AttributeConstant.ADMIN_USERS_PAGE_PAGINATION_URL_PATTERN,
                                    PageManager.getProperty(SettingsConstant.ADMIN_USERS_PAGE_PAGINATION_URL_PATTERN));
                        }
                        case CLIENT -> {
                            List<Account> allClientAccounts = accountService.getClientAccountsByUserId(userId);
                            List<Account> clientActiveAccounts = accountService.getActiveAccounts(userId);
                            List<Long> clientAccountIdsWithBankCards = accountService.getClientAccountIdsWithBankCards(userId);
                            List<Account> clientActiveAccountsWithBankCards = accountService.getActiveAccountsWithBankCards(userId);
                            List<Payment> clientPayments = paymentService.getAllClientPayments(userId);
                            List<Payment> clientReceivedPayments = paymentService.getPaymentsReceivedByClient(userId);
                            session.setAttribute(AttributeConstant.CLIENT_ACCOUNTS, allClientAccounts);
                            session.setAttribute(AttributeConstant.CLIENT_ACTIVE_ACCOUNTS, clientActiveAccounts);
                            session.setAttribute(AttributeConstant.CLIENT_ACCOUNT_IDS_WITH_BANK_CARDS, clientAccountIdsWithBankCards);
                            session.setAttribute(AttributeConstant.CLIENT_ACTIVE_ACCOUNTS_WITH_BANK_CARDS, clientActiveAccountsWithBankCards);
                            session.setAttribute(AttributeConstant.CLIENT_PAYMENTS, clientPayments);
                            session.setAttribute(AttributeConstant.CLIENT_RECEIVED_PAYMENTS, clientReceivedPayments);
                            session.setAttribute(AttributeConstant.CLIENT_ACCOUNTS_PAGE_PAGINATION_URL_PATTERN,
                                    PageManager.getProperty(SettingsConstant.CLIENT_ACCOUNTS_PAGE_PAGINATION_URL_PATTERN));
                            session.setAttribute(AttributeConstant.CLIENT_PAYMENTS_PAGE_PAGINATION_URL_PATTERN,
                                    PageManager.getProperty(SettingsConstant.CLIENT_PAYMENTS_PAGE_PAGINATION_URL_PATTERN));
                        }
                    }

                } else if (user.getStatus() == UserStatus.BLOCKED) {
                    request.setAttribute(AttributeConstant.SIGN_IN_FAIL,
                            messageManager.getMessage(MessageConstant.ERROR_SIGN_IN_USER_STATUS_BLOCKED));
                }
            } else {
                request.setAttribute(AttributeConstant.SIGN_IN_FAIL,
                        messageManager.getMessage(MessageConstant.ERROR_SIGN_IN_INCORRECT_CREDENTIALS));
            }
        } else {
            request.setAttribute(AttributeConstant.SIGN_IN_FAIL,
                    messageManager.getMessage(MessageConstant.ERROR_SIGN_IN_INCORRECT_CREDENTIALS));
        }
        Navigator navigator = new Navigator();
        navigator.setPagePath(pagePath);
        return navigator;
    }
}
