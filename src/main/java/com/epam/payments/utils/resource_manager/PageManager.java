package com.epam.payments.utils.resource_manager;

import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.FilenameConstant;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ResourceBundle;

public final class PageManager {
    public PageManager() {
    }

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(FilenameConstant.PAGE_PROPERTIES);

    public static String getProperty(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }

    public static Long getPage(HttpServletRequest request) {
        String pageParameter = request.getParameter(AttributeConstant.PAGINATION_PAGE);
        Long page = (pageParameter != null && !pageParameter.isBlank())
                ? Long.valueOf(pageParameter)
                : (Long) request.getAttribute(AttributeConstant.PAGINATION_PAGE);
        return (page == 0) ? 1 : page;
    }
}
