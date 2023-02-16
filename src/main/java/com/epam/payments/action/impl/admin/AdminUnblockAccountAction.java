package com.epam.payments.action.impl.admin;

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
import com.epam.payments.utils.constant.MessageConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.resource_manager.MessageManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.epam.payments.model.entity.user.Role.ADMIN;

public class AdminUnblockAccountAction implements DatabaseAction {
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
        MessageManager messageManager = getLocalizedMessageManager(session);
        User user = (User) session.getAttribute(AttributeConstant.USER);
        String pagePath;
        if (user.getRole() == ADMIN) {
            Long accountId = Long.valueOf(request.getParameter(AttributeConstant.CLIENT_ACCOUNT_ID));
            Account account = accountService.getAccount(accountId);
            account.setStatus(AccountStatus.ACTIVE);
            accountService.updateAccount(account);
            Long page = PageManager.getPage(request);
            Long itemsPerPage = Account.getItemsPerPage();
            EntitySortManager accountsSortManager = (EntitySortManager) session.getAttribute(AttributeConstant.ACCOUNTS_SORT_MANAGER);
            PaginationManager<Account> accountsPaginationManager = accountService
                    .getAllAccountsPaginationManager(page, itemsPerPage, accountsSortManager);
            request.setAttribute(AttributeConstant.PAGINATION_MANAGER, accountsPaginationManager);
            request.setAttribute(AttributeConstant.ACCOUNT_UPDATE_SUCCESS,
                    messageManager.getMessage(MessageConstant.INFO_ACCOUNT_UPDATE_SUCCESS));
            request.setAttribute(AttributeConstant.PROCESSING_CLIENT_ACCOUNT, account);
            pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_ADMIN_ACCOUNTS);
        } else {
            pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_LOGIN);
        }
        navigator.setPagePath(pagePath);
        return navigator;
    }
}
