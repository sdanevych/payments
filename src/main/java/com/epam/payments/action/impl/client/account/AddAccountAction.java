package com.epam.payments.action.impl.client.account;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.service.AccountService;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.MessageConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.resource_manager.MessageManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;
import com.epam.payments.utils.validator.AccountValidator;
import com.epam.payments.utils.validator.StandardValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class AddAccountAction implements DatabaseAction {
    private final AccountService accountService;

    {
        DaoFactory daoFactory = MySqlDaoFactory.getInstance();
        AccountDao accountDao = daoFactory.getAccountDao();
        accountService = AccountService.getInstance();
        accountService.setAccountDao(accountDao);
    }

    @Override
    public Navigator execute(HttpServletRequest request) throws ActionException {
        Navigator navigator = new Navigator();
        HttpSession session = request.getSession();
        MessageManager messageManager = getLocalizedMessageManager(session);
        User user = (User) session.getAttribute(AttributeConstant.USER);
        Long userId = user.getId();
        List<Account> allClientAccounts = (List<Account>) session.getAttribute(AttributeConstant.CLIENT_ACCOUNTS);
        List<Long> clientAccountIdsWithBankCards = accountService.getClientAccountIdsWithBankCards(userId);
        String accountName = request.getParameter(AttributeConstant.ACCOUNT_NAME);
        accountName = StandardValidator.replaceScript(accountName);
        Long page = PageManager.getPage(request);
        Long itemsPerPage = Account.getItemsPerPage();
        EntitySortManager accountsSortManager = (EntitySortManager) session.getAttribute(AttributeConstant.ACCOUNTS_SORT_MANAGER);
        PaginationManager<Account> accountsPaginationManager = accountService.getAccountsPaginationManagerByUserId(userId, page, itemsPerPage, accountsSortManager);
        session.setAttribute(AttributeConstant.CLIENT_ACCOUNT_IDS_WITH_BANK_CARDS, clientAccountIdsWithBankCards);
        request.setAttribute(AttributeConstant.ACCOUNT_NAME, accountName);
        request.setAttribute(AttributeConstant.PAGINATION_MANAGER, accountsPaginationManager);

        boolean isValidAccountData = true;
        if (!AccountValidator.isValidAccountName(accountName)) {
            request.setAttribute(AttributeConstant.INVALID_ACCOUNT_NAME,
                    messageManager.getMessage(MessageConstant.ERROR_INVALID_ACCOUNT_NAME));
            isValidAccountData = false;
        }
        if (!accountService.isUniqueAccountNameForSpecificUser(userId, accountName)) {
            request.setAttribute(AttributeConstant.ACCOUNT_NAME_ALREADY_EXISTS,
                    messageManager.getMessage(MessageConstant.ERROR_ACCOUNT_NAME_ALREADY_EXISTS));
            isValidAccountData = false;
        }
        if (allClientAccounts.isEmpty()) {
            request.setAttribute(AttributeConstant.NO_CLIENT_ACCOUNTS,
                    messageManager.getMessage(MessageConstant.INFO_NO_CLIENT_ACCOUNTS));
        }

        if (isValidAccountData) {
            Account createdAccount = accountService.addAccount(userId, accountName);
            accountsPaginationManager = accountService.getAccountsPaginationManagerByUserId(userId, page, itemsPerPage, accountsSortManager);
            List<Account> clientActiveAccounts = accountService.getActiveAccounts(userId);
            request.setAttribute(AttributeConstant.PROCESSING_CLIENT_ACCOUNT, createdAccount);
            session.setAttribute(AttributeConstant.CLIENT_ACTIVE_ACCOUNTS, clientActiveAccounts);
            request.setAttribute(AttributeConstant.PAGINATION_MANAGER, accountsPaginationManager);
            request.setAttribute(AttributeConstant.ADD_ACCOUNT_SUCCESS,
                    messageManager.getMessage(MessageConstant.INFO_ADD_ACCOUNT_SUCCESS));
        } else {
            request.setAttribute(AttributeConstant.ADD_ACCOUNT_FAIL,
                    messageManager.getMessage(MessageConstant.ERROR_ADD_ACCOUNT_FAIL));
        }
        navigator.setPagePath(PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_ACCOUNTS));
        return navigator;
    }
}
