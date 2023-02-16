package com.epam.payments.action.impl.common;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.model.entity.account.AccountStatus;
import com.epam.payments.model.entity.user.Role;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.service.AccountService;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.MessageConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.resource_manager.MessageManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class BlockAccountAction implements DatabaseAction {
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
        Long accountId = Long.valueOf(request.getParameter(AttributeConstant.CLIENT_ACCOUNT_ID));
        Account account = accountService.getAccount(accountId);
        account.setStatus(AccountStatus.BLOCKED);
        accountService.updateAccount(account);
        Long userId = user.getId();
        List<Account> clientActiveAccounts = accountService.getActiveAccounts(userId);
        List<Account> clientActiveAccountsWithBankCards = accountService.getActiveAccountsWithBankCards(userId);
        List<Long> clientAccountIdsWithBankCards = accountService.getClientAccountIdsWithBankCards(userId);
        Long page = PageManager.getPage(request);
        Long itemsPerPage = Account.getItemsPerPage();
        EntitySortManager accountsSortManager = (EntitySortManager) session.getAttribute(AttributeConstant.ACCOUNTS_SORT_MANAGER);
        Role userRole = user.getRole();
        PaginationManager<Account> accountsPaginationManager = null;
        String pagePath = null;
        switch (userRole) {
            case ADMIN -> {
                accountsPaginationManager = accountService
                        .getAllAccountsPaginationManager(page, itemsPerPage, accountsSortManager);
                pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_ADMIN_ACCOUNTS);
            }
            case CLIENT -> {
                accountsPaginationManager = accountService
                        .getAccountsPaginationManagerByUserId(userId, page, itemsPerPage, accountsSortManager);
                session.setAttribute(AttributeConstant.CLIENT_ACCOUNT_IDS_WITH_BANK_CARDS, clientAccountIdsWithBankCards);
                session.setAttribute(AttributeConstant.CLIENT_ACTIVE_ACCOUNTS, clientActiveAccounts);
                session.setAttribute(AttributeConstant.CLIENT_ACTIVE_ACCOUNTS_WITH_BANK_CARDS, clientActiveAccountsWithBankCards);
                pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_ACCOUNTS);
            }
            default -> pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_LOGIN);
        }
        request.setAttribute(AttributeConstant.ACCOUNT_UPDATE_SUCCESS,
                messageManager.getMessage(MessageConstant.INFO_ACCOUNT_UPDATE_SUCCESS));
        request.setAttribute(AttributeConstant.PAGINATION_MANAGER, accountsPaginationManager);
        request.setAttribute(AttributeConstant.PROCESSING_CLIENT_ACCOUNT, account);
        navigator.setPagePath(pagePath);
        return navigator;
    }
}
