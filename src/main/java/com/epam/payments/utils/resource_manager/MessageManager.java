package com.epam.payments.utils.resource_manager;

import com.epam.payments.utils.constant.FilenameConstant;
import com.epam.payments.utils.constant.LocaleConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

public final class MessageManager {
    private static final Logger LOGGER = LogManager.getLogger(MessageManager.class);

    private ResourceBundle resourceBundle;

    public MessageManager(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }

    public static MessageManager defineLocale(String locale) {
        MessageManager messageManager = null;
        if (locale == null) {
            messageManager = new MessageManager(ResourceBundle.getBundle(FilenameConstant.MESSAGE_BUNDLE,
                    new Locale(LocaleConstant.UKRAINIAN_LOCALE)));
            LOGGER.info("Default UKRAINIAN_LOCALE is set, as it is not set by the user");
        } else {
            switch (locale) {
                case LocaleConstant.ENGLISH_LOCALE -> messageManager =
                        new MessageManager(ResourceBundle.getBundle(
                                FilenameConstant.MESSAGE_BUNDLE,
                                new Locale(LocaleConstant.ENGLISH_LANGUAGE, LocaleConstant.US)));
                case LocaleConstant.UKRAINIAN_LOCALE -> messageManager =
                        new MessageManager(ResourceBundle.getBundle(
                                FilenameConstant.MESSAGE_BUNDLE,
                                new Locale(LocaleConstant.UKRAINIAN_LANGUAGE, LocaleConstant.UKRAINE)));
            }
        }
        return messageManager;
    }
}
