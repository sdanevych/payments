package com.epam.payments.action.impl.admin;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.entity.account.Account;
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

public class AdminAccountsAction implements DatabaseAction {
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
        Long page = PageManager.getPage(request);
        User user = ((User) session.getAttribute(AttributeConstant.USER));
        String pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_LOGIN);
        if (user.getRole() == Role.ADMIN) {
            List<Account> allClientAccounts = (List<Account>) session.getAttribute(AttributeConstant.ALL_ACCOUNTS);
            Long itemsPerPage = Account.getItemsPerPage();
            EntitySortManager accountsSortManager = (EntitySortManager) session.getAttribute(AttributeConstant.ACCOUNTS_SORT_MANAGER);
            PaginationManager<Account> accountsPaginationManager = accountService
                    .getAllAccountsPaginationManager(page, itemsPerPage, accountsSortManager);
            request.setAttribute(AttributeConstant.PAGINATION_MANAGER, accountsPaginationManager);
            session.setAttribute(AttributeConstant.PROCESSING_CLIENT_ACCOUNT, null);
            if (allClientAccounts.isEmpty()) {
                request.setAttribute(AttributeConstant.NO_CLIENT_ACCOUNTS,
                        messageManager.getMessage(MessageConstant.INFO_NO_CLIENT_ACCOUNTS));
            }
            pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_ADMIN_ACCOUNTS);
        }
        navigator.setPagePath(pagePath);
        return navigator;
    }
}
