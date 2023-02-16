package com.epam.payments.action.impl.common.sort;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.UserDao;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.service.UserService;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;
import com.epam.payments.utils.sort.SortOrder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AdminSortUsers implements DatabaseAction {
    private final UserService userService;

    {
        UserDao userDao = MySqlDaoFactory.getInstance().getUserDao();
        userService = UserService.getInstance();
        userService.setUserDao(userDao);
    }

    @Override
    public Navigator execute(HttpServletRequest request) throws ActionException {
        Navigator navigator = new Navigator();
        HttpSession session = request.getSession();
        Long page = PageManager.getPage(request);
        String sortColumn = request.getParameter(AttributeConstant.SORT_COLUMN);
        SortOrder sortOrder = SortOrder.valueOf(request.getParameter(AttributeConstant.SORT_ORDER));
        String currentJspPath = request.getParameter(AttributeConstant.JSP_PATH);
        Long itemsPerPage = Account.getItemsPerPage();
        EntitySortManager usersSortManager = (EntitySortManager) session
                .getAttribute(AttributeConstant.USERS_SORT_MANAGER);
        usersSortManager.setSortOrder(sortOrder);
        usersSortManager.setSortColumn(sortColumn);
        PaginationManager<User> usersPaginationManager =
                userService.getAllUsersPaginationManager(page, itemsPerPage, usersSortManager);
        session.setAttribute(AttributeConstant.USERS_SORT_MANAGER, usersSortManager);
        request.setAttribute(AttributeConstant.PAGINATION_PAGE, page);
        request.setAttribute(AttributeConstant.PAGINATION_MANAGER, usersPaginationManager);
        navigator.setPagePath(currentJspPath);
        return navigator;
    }
}
