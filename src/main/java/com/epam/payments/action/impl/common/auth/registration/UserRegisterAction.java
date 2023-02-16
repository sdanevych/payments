package com.epam.payments.action.impl.common.auth.registration;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.UserDao;
import com.epam.payments.model.entity.user.Role;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.model.entity.user.UserStatus;
import com.epam.payments.service.UserService;
import com.epam.payments.service.registration.EmailConfirmationService;
import com.epam.payments.utils.ConfirmationCodeGenerator;
import com.epam.payments.utils.Encryption;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.MessageConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.resource_manager.MessageManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.validator.StandardValidator;
import com.epam.payments.utils.validator.UserRegistrationValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class UserRegisterAction implements DatabaseAction {
    private final UserService userService;

    {
        DaoFactory daoFactory = MySqlDaoFactory.getInstance();
        UserDao userDao = daoFactory.getUserDao();
        userService = UserService.getInstance();
        userService.setUserDao(userDao);
    }


    @Override
    public Navigator execute(HttpServletRequest request) {
        Navigator navigator = new Navigator();
        String email = request.getParameter(AttributeConstant.EMAIL);
        String phoneNumber = request.getParameter(AttributeConstant.PHONE_NUMBER);
        String password = request.getParameter(AttributeConstant.PASSWORD);
        String samePassword = request.getParameter(AttributeConstant.SAME_PASSWORD);
        String login = request.getParameter(AttributeConstant.LOGIN);
        String firstName = request.getParameter(AttributeConstant.FIRST_NAME);
        String secondName = request.getParameter(AttributeConstant.SECOND_NAME);

        HttpSession session = request.getSession();
        request.setAttribute(AttributeConstant.INVALID_EMAIL, null);
        request.setAttribute(AttributeConstant.INVALID_PHONE_NUMBER, null);
        request.setAttribute(AttributeConstant.INVALID_PASSWORD, null);
        request.setAttribute(AttributeConstant.NOT_SAME_PASSWORDS, null);
        request.setAttribute(AttributeConstant.INVALID_LOGIN, null);
        request.setAttribute(AttributeConstant.INVALID_FIRST_NAME, null);
        request.setAttribute(AttributeConstant.INVALID_SECOND_NAME, null);
        request.setAttribute(AttributeConstant.LOGIN_ALREADY_EXISTS, null);
        request.setAttribute(AttributeConstant.EMAIL_ALREADY_EXISTS, null);
        request.setAttribute(AttributeConstant.PHONE_NUMBER_ALREADY_EXISTS, null);

        MessageManager messageManager = getLocalizedMessageManager(session);
        String pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_REGISTRATION);

        login = StandardValidator.replaceScript(login);
        firstName = StandardValidator.replaceScript(firstName);
        secondName = StandardValidator.replaceScript(secondName);

        boolean isCorrectUserData = true;
        if (!UserRegistrationValidator.isCorrectEmail(email)) {
            request.setAttribute(AttributeConstant.INVALID_EMAIL,
                    messageManager.getMessage(MessageConstant.ERROR_INVALID_EMAIL));
            isCorrectUserData = false;
        }
        if (!UserRegistrationValidator.isCorrectPhoneNumber(phoneNumber)) {
            request.setAttribute(AttributeConstant.INVALID_PHONE_NUMBER,
                    messageManager.getMessage(MessageConstant.ERROR_INVALID_PHONE_NUMBER));
            isCorrectUserData = false;
        }
        if (!UserRegistrationValidator.isCorrectPassword(password)) {
            request.setAttribute(AttributeConstant.INVALID_PASSWORD,
                    messageManager.getMessage(MessageConstant.ERROR_INVALID_PASSWORD));
            isCorrectUserData = false;
        }
        if (!UserRegistrationValidator.arePasswordsEqual(password, samePassword)) {
            request.setAttribute(AttributeConstant.NOT_SAME_PASSWORDS,
                    messageManager.getMessage(MessageConstant.ERROR_NOT_SAME_PASSWORD));
            isCorrectUserData = false;
        }
        if (!UserRegistrationValidator.isValidLogin(login)) {
            request.setAttribute(AttributeConstant.INVALID_LOGIN,
                    messageManager.getMessage(MessageConstant.ERROR_INVALID_LOGIN));
            isCorrectUserData = false;
        }
        if (!UserRegistrationValidator.isValidFirstName(firstName)) {
            request.setAttribute(AttributeConstant.INVALID_FIRST_NAME,
                    messageManager.getMessage(MessageConstant.ERROR_INVALID_LOGIN));
            isCorrectUserData = false;
        }
        if (!UserRegistrationValidator.isValidSecondName(secondName)) {
            request.setAttribute(AttributeConstant.INVALID_SECOND_NAME,
                    messageManager.getMessage(MessageConstant.ERROR_INVALID_SECOND_NAME));
            isCorrectUserData = false;
        }
        if (!userService.isUniqueLogin(login)) {
            request.setAttribute(AttributeConstant.LOGIN_ALREADY_EXISTS,
                    messageManager.getMessage(MessageConstant.ERROR_LOGIN_ALREADY_EXISTS));
            isCorrectUserData = false;
        }
        if (!userService.isUniqueEmail(email)) {
            request.setAttribute(AttributeConstant.EMAIL_ALREADY_EXISTS,
                    messageManager.getMessage(MessageConstant.ERROR_EMAIL_ALREADY_EXISTS));
            isCorrectUserData = false;
        }
        if (!userService.isUniquePhoneNumber(phoneNumber)) {
            request.setAttribute(AttributeConstant.PHONE_NUMBER_ALREADY_EXISTS,
                    messageManager.getMessage(MessageConstant.ERROR_PHONE_NUMBER_ALREADY_EXISTS));
            isCorrectUserData = false;
        }
        if (isCorrectUserData) {
            User user = new User(login, Role.CLIENT, UserStatus.ACTIVE, firstName, secondName, phoneNumber, email);
            session.setAttribute(AttributeConstant.USER, user);
            session.setAttribute(AttributeConstant.ENCRYPTED_PASSWORD, Encryption.encrypt(password));
            String confirmationCode = ConfirmationCodeGenerator.generateConfirmationCode();
            session.setAttribute(AttributeConstant.ACTUAL_CONFIRMATION_CODE, confirmationCode);
            new EmailConfirmationService(email, confirmationCode, messageManager).start();
            pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CONFIRMATION);
        } else {
            request.setAttribute(AttributeConstant.REGISTRATION_FAIL,
            messageManager.getMessage(MessageConstant.ERROR_REGISTRATION_FAIL));
        }
        navigator.setPagePath(pagePath);
        return navigator;
    }
}
