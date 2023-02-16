package com.epam.payments.action;

import com.epam.payments.action.impl.ActionEnum;
import com.epam.payments.action.impl.DefaultAction;
import jakarta.servlet.http.HttpServletRequest;

public class ActionFactory {
    private static final String ACTION = "action";
    private static Action action = new DefaultAction();

    public static Action defineAction(HttpServletRequest httpServletRequest) {
        String paramAction = httpServletRequest.getParameter(ACTION).toUpperCase();
        if (paramAction.isBlank()) {
            return action;
        }
        ActionEnum actionEnum = ActionEnum.valueOf(paramAction);
        action = actionEnum.getAction();
        return action;
    }
}
