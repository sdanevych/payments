package com.epam.payments.action.impl.client;

import com.epam.payments.action.Action;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.entity.user.Role;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.resource_manager.PageManager;
import jakarta.servlet.http.HttpServletRequest;

public class MainAction implements Action {
    @Override
    public Navigator execute(HttpServletRequest request) throws ActionException {
        Navigator navigator = new Navigator();
        String page = null;
        Role attributeRole = (Role) request.getSession().getAttribute(AttributeConstant.ROLE);
        if (attributeRole == Role.CLIENT){
            page = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_MAIN);
        } else if (attributeRole == Role.ADMIN) {
            page = PageManager.getProperty(PagePathsConstant.PAGE_PATH_ADMIN_MAIN);
        }
        navigator.setPagePath(page);
        return navigator;
    }
}
