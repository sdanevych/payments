package com.epam.payments.action.impl.admin;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.UserDao;
import com.epam.payments.model.entity.user.Role;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.model.entity.user.UserStatus;
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

public class AdminBlockUserAction implements DatabaseAction {
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
        User user = (User) session.getAttribute(AttributeConstant.USER);
        Long userId = Long.valueOf(request.getParameter(AttributeConstant.USER_ID));
        User userToBlock = userService.getUser(userId);
        if (user.getRole() == Role.ADMIN) {
            userToBlock.setStatus(UserStatus.BLOCKED);
            userService.updateUser(userToBlock);
            Long page = PageManager.getPage(request);
            Long itemsPerPage = User.getItemsPerPage();
            EntitySortManager userSortManager = (EntitySortManager) session
                    .getAttribute(AttributeConstant.USERS_SORT_MANAGER);
            PaginationManager<User> usersPaginationManager =
                    userService.getAllUsersPaginationManager(page, itemsPerPage, userSortManager);
            List<User> allUsers = userService.getAllUsers();
            session.setAttribute(AttributeConstant.ALL_USERS, allUsers);
            request.setAttribute(AttributeConstant.PAGINATION_PAGE, page);
            request.setAttribute(AttributeConstant.PROCESSING_USER, userToBlock);
            request.setAttribute(AttributeConstant.PAGINATION_MANAGER, usersPaginationManager);
            request.setAttribute(AttributeConstant.USER_UPDATE_SUCCESS,
                    messageManager.getMessage(MessageConstant.INFO_USER_UPDATE_SUCCESS));
        }
        navigator.setPagePath(PageManager.getProperty(PagePathsConstant.PAGE_PATH_ADMIN_USERS));
        return navigator;
    }
}
