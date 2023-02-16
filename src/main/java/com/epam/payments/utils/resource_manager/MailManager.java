package com.epam.payments.utils.resource_manager;

import com.epam.payments.utils.constant.FilenameConstant;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
import java.util.ResourceBundle;

public class MailManager {
    private String smtpHost;
    private String smtpPort;
    private String userName;
    private String userPass;

    private static final Logger LOG = LogManager.getLogger(MailManager.class);

    private final ResourceBundle resourceBundle;

    public MailManager(String mailPropertiesFile) {
        resourceBundle = ResourceBundle.getBundle(mailPropertiesFile);
        smtpHost = resourceBundle.getString("mail.smtp.host");
        smtpPort = resourceBundle.getString("mail.smtp.port");
        userName = resourceBundle.getString("mail.user.name");
        userPass = resourceBundle.getString("mail.user.password");
    }

    public Session createMailSession() {
        LOG.debug(String.format("Mail session data: smtpHost:%s, smtpPort:%s, userName:%s",
                smtpHost, smtpPort, userName));
        Properties sessionProperties = new Properties();
        sessionProperties.setProperty("mail.transport.protocol", "smtp");
        sessionProperties.setProperty("mail.host", smtpHost);
        sessionProperties.setProperty("mail.smtp.auth", "true");
        sessionProperties.setProperty("mail.smtp.starttls.enable", "true");
        sessionProperties.setProperty("mail.smtp.port", smtpPort);
        sessionProperties.setProperty("mail.smtp.socketFactory.port", smtpPort);
        sessionProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        sessionProperties.setProperty("mail.smtp.socketFactory.fallback", "false");
        sessionProperties.setProperty("mail.smtp.quitwait", "false");
        return Session.getDefaultInstance(sessionProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, userPass);
            }
        });
    }

    public String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
