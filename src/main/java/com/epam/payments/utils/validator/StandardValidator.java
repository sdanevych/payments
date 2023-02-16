package com.epam.payments.utils.validator;

public class StandardValidator {
    private static final String SCRIPT_DETECTION_REGEX = "</?script>";

    public static String replaceScript(String value){
        return value.replaceAll(SCRIPT_DETECTION_REGEX, "");
    }
}
