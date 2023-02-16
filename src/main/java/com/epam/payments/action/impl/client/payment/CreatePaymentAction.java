package com.epam.payments.action.impl.client.payment;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.dao.interfaces.BankCardDao;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.model.entity.account.BankCard;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.model.entity.user.UserStatus;
import com.epam.payments.service.AccountService;
import com.epam.payments.service.BankCardService;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.MessageConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.resource_manager.MessageManager;
import com.epam.payments.utils.resource_manager.PageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class CreatePaymentAction implements DatabaseAction {
    private final AccountService accountService;
    private final BankCardService bankCardService;

    {
        DaoFactory daoFactory = MySqlDaoFactory.getInstance();
        AccountDao accountDao = daoFactory.getAccountDao();
        accountService = AccountService.getInstance();
        accountService.setAccountDao(accountDao);
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
        request.setAttribute(AttributeConstant.PAGINATION_PAGE, PageManager.getPage(request));
        String pagePath;
        if (user.getStatus() == UserStatus.ACTIVE) {
            Long paymentSenderAccountId = Long.valueOf(request.getParameter(AttributeConstant.PAYMENT_SENDER_ACCOUNT_ID));
            Account paymentSenderAccount = accountService.getAccount(paymentSenderAccountId);
            BankCard clientBankCard = bankCardService.getBankCardByAccountId(paymentSenderAccountId);
            request.setAttribute(AttributeConstant.PAYMENT_SENDER_ACCOUNT, paymentSenderAccount);
            request.setAttribute(AttributeConstant.PAYMENT_SENDER_ACCOUNT_CURRENCY,
                    clientBankCard.getCurrency());
            pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_CREATE_PAYMENT);
        } else {
            pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_PAYMENTS);
            request.setAttribute(AttributeConstant.USER_IS_BLOCKED,
                    messageManager.getMessage(MessageConstant.ERROR_USER_IS_BLOCKED));
        }
        navigator.setPagePath(pagePath);
        return navigator;
    }
}
