package com.epam.payments.action.impl.common.auth;

import com.epam.payments.action.Action;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.resource_manager.PageManager;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutAction implements Action {
    @Override
    public Navigator execute(HttpServletRequest request) throws ActionException {
        Navigator navigator = new Navigator();
        request.getSession().invalidate();
        navigator.setPagePath(PageManager.getProperty(PagePathsConstant.PAGE_PATH_LOGIN));
        return navigator;
    }
}
