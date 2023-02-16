package com.epam.payments.action.impl.admin;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.UserDao;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.service.UserService;
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

public class AdminUsersAction implements DatabaseAction {
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
        MessageManager messageManager = getLocalizedMessageManager(session);
        List<User> users = userService.getAllUsers();
        if (!users.isEmpty()) {
            Long page = PageManager.getPage(request);
            Long itemsPerPage = User.getItemsPerPage();
            EntitySortManager userSortManager = (EntitySortManager) session
                    .getAttribute(AttributeConstant.USERS_SORT_MANAGER);
            PaginationManager<User> usersPaginationManager = userService
                    .getAllUsersPaginationManager(page, itemsPerPage, userSortManager);
            request.setAttribute(AttributeConstant.PAGINATION_MANAGER, usersPaginationManager);
        } else {
            request.setAttribute(AttributeConstant.NO_USERS,
                    messageManager.getMessage(MessageConstant.INFO_NO_USERS));
        }
        navigator.setPagePath(PageManager.getProperty(PagePathsConstant.PAGE_PATH_ADMIN_USERS));
        return navigator;
    }
}
