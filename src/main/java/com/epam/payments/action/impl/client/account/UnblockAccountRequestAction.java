package com.epam.payments.action.impl.client.account;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.model.entity.account.AccountStatus;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.service.AccountService;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class UnblockAccountRequestAction implements DatabaseAction {
    private final AccountService accountService = AccountService.getInstance();

    {
        DaoFactory daoFactory = MySqlDaoFactory.getInstance();
        AccountDao accountDao = daoFactory.getAccountDao();
        accountService.setAccountDao(accountDao);
    }

    @Override
    public Navigator execute(HttpServletRequest request) throws ActionException {
        Navigator navigator = new Navigator();
        HttpSession session = request.getSession();
        Long accountId = Long.valueOf(request.getParameter(AttributeConstant.CLIENT_ACCOUNT_ID));
        Account account = accountService.getAccount(accountId);
        account.setStatus(AccountStatus.WAITING_FOR_UNLOCK);
        accountService.updateAccount(account);
        Long userId = ((User) session.getAttribute(AttributeConstant.USER)).getId();
        List<Long> clientAccountIdsWithBankCards = accountService.getClientAccountIdsWithBankCards(userId);
        Long page = PageManager.getPage(request);
        Long itemsPerPage = Account.getItemsPerPage();
        EntitySortManager accountsSortManager = (EntitySortManager) session.getAttribute(AttributeConstant.ACCOUNTS_SORT_MANAGER);
        PaginationManager<Account> accountsPaginationManager = accountService
                .getAccountsPaginationManagerByUserId(userId, page, itemsPerPage, accountsSortManager);
        session.setAttribute(AttributeConstant.CLIENT_ACCOUNT_IDS_WITH_BANK_CARDS, clientAccountIdsWithBankCards);
        request.setAttribute(AttributeConstant.PAGINATION_MANAGER, accountsPaginationManager);
        navigator.setPagePath(PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_ACCOUNTS));
        return navigator;
    }
}
