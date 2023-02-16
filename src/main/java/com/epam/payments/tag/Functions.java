package com.epam.payments.tag;

import com.epam.payments.utils.constant.SettingsConstant;
import com.epam.payments.utils.resource_manager.PageManager;
import jakarta.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Functions {
    public static boolean contains(List list, Object o) {
        return list.contains(o);
    }

    public static String removeMillisecondPart(Date date) {
        return date == null ? "" : new SimpleDateFormat(
                PageManager.getProperty(SettingsConstant.APPLICATION_DATE_TIME_FORMAT))
                .format(date);
    }

    public static String getCurrentJsp(HttpServletRequest request) {
        return request.getRequestURI().substring(request.getContextPath().length());
    }
}