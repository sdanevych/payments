package com.epam.payments.action;

import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.resource_manager.MessageManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface Action {
    Navigator execute(HttpServletRequest request) throws ActionException;

    default MessageManager getLocalizedMessageManager(HttpSession session) {
        return MessageManager.defineLocale((String) session.getAttribute(AttributeConstant.LANGUAGE));
    }
}
