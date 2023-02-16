package com.epam.payments.action.impl.client.account;

import com.epam.payments.action.Action;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.resource_manager.PageManager;
import jakarta.servlet.http.HttpServletRequest;

public class AddBankCardAction implements Action {
    @Override
    public Navigator execute(HttpServletRequest request) throws ActionException {
        Navigator navigator = new Navigator();
        request.setAttribute(AttributeConstant.PAGINATION_PAGE, PageManager.getPage(request));
        request.setAttribute(AttributeConstant.CLIENT_ACCOUNT_ID,
                request.getParameter(AttributeConstant.CLIENT_ACCOUNT_ID));
        navigator.setPagePath(PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_ADD_BANK_CARD));
        return navigator;
    }
}
