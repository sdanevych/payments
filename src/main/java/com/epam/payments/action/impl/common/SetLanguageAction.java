package com.epam.payments.action.impl.common;

import com.epam.payments.action.Action;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.LocaleConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.resource_manager.PageManager;
import jakarta.servlet.http.HttpServletRequest;

public class SetLanguageAction implements Action {
    @Override
    public Navigator execute(HttpServletRequest request) throws ActionException {
        String selectedLanguage = request.getParameter(AttributeConstant.SELECTED_LANGUAGE);
        switch (selectedLanguage) {
            case LocaleConstant.UKRAINIAN_LANGUAGE -> {
                request.getSession().setAttribute(AttributeConstant.LANGUAGE, LocaleConstant.UKRAINIAN_LOCALE);
                request.setAttribute(AttributeConstant.LANGUAGE, LocaleConstant.UKRAINIAN_LOCALE);
            }
            case LocaleConstant.ENGLISH_LANGUAGE -> {
                request.getSession().setAttribute(AttributeConstant.LANGUAGE, LocaleConstant.ENGLISH_LOCALE);
                request.setAttribute(AttributeConstant.LANGUAGE, LocaleConstant.ENGLISH_LOCALE);
            }
        }
        Navigator navigator = new Navigator();
        navigator.setPagePath(PageManager.getProperty(PagePathsConstant.PAGE_PATH_LOGIN));
        return navigator;
    }
}
