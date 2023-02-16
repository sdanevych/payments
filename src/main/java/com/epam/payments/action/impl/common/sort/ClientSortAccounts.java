package com.epam.payments.action.impl.common.sort;

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
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;
import com.epam.payments.utils.sort.SortOrder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ClientSortAccounts implements DatabaseAction {
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
        Long page = PageManager.getPage(request);
        Long userId = ((User) session.getAttribute(AttributeConstant.USER)).getId();
        String sortColumn = request.getParameter(AttributeConstant.SORT_COLUMN);
        SortOrder sortOrder = SortOrder.valueOf(request.getParameter(AttributeConstant.SORT_ORDER));
        String currentJspPath = request.getParameter(AttributeConstant.JSP_PATH);
        Long itemsPerPage = Account.getItemsPerPage();
        EntitySortManager accountsSortManager = (EntitySortManager) session
                .getAttribute(AttributeConstant.ACCOUNTS_SORT_MANAGER);
        accountsSortManager.setSortColumn(sortColumn);
        accountsSortManager.setSortOrder(sortOrder);
        session.setAttribute(AttributeConstant.ACCOUNTS_SORT_MANAGER, accountsSortManager);
        PaginationManager<Account> accountsPaginationManager =
                accountService.getAccountsPaginationManagerByUserId(userId, page, itemsPerPage, accountsSortManager);

        request.setAttribute(AttributeConstant.PAGINATION_PAGE, page);
        request.setAttribute(AttributeConstant.PAGINATION_MANAGER, accountsPaginationManager);
        navigator.setPagePath(currentJspPath);
        return navigator;
    }
}
