package com.epam.payments.utils.validator;

public class UserRegistrationValidator {
    private static final String PHONE_NUMBER_REGEX = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";
    private static final String EMAIL_REGEX = "[-a-z0-9!#$%&'*+/=?^_`{|}~]+(\\.[-a-z0-9!#$%&'*+/=?^_`{|}~]+)*@" +
            "([a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\\.)*([a-z]{2,4})";
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    private static final int LOGIN_MAX_LENGTH = 20;
    private static final int USER_NAME_MAX_LENGTH = 255;

    public static boolean isCorrectPhoneNumber(String phoneNumber){
        return phoneNumber.matches(PHONE_NUMBER_REGEX);
    }

    public static boolean isCorrectEmail(String email){
        return email.matches(EMAIL_REGEX);
    }
    public static boolean isCorrectPassword(String password){
        return password.matches(PASSWORD_REGEX);
    }
    public static boolean arePasswordsEqual(String password, String confirmationPassword){
        return password.equals(confirmationPassword);
    }
    public static boolean isValidFirstName(String userFirstName){ return userFirstName.length() <= USER_NAME_MAX_LENGTH;}
    public static boolean isValidSecondName(String userSecondName){ return userSecondName.length() <= USER_NAME_MAX_LENGTH;}
    public static boolean isValidLogin(String login){ return login.length() <= LOGIN_MAX_LENGTH;}
}
