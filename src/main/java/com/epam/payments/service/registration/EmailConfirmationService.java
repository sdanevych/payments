package com.epam.payments.service.registration;

import com.epam.payments.exception.EmailConfirmationException;
import com.epam.payments.utils.constant.FilenameConstant;
import com.epam.payments.utils.constant.MessageConstant;
import com.epam.payments.utils.resource_manager.MailManager;
import com.epam.payments.utils.resource_manager.MessageManager;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EmailConfirmationService extends Thread {
    private static final Logger LOGGER = LogManager.getLogger(EmailConfirmationService.class);
    private MimeMessage message;
    private final String mailRecipient;
    private final String mailSubject;
    private final String mailText;
    MailManager mailManager = new MailManager(FilenameConstant.MAIL_PROPERTIES);
    MessageManager messageManager;

    public EmailConfirmationService(String mailRecipient, String mailText, MessageManager messageManager) {
        this.mailRecipient = mailRecipient;
        this.mailText = mailText;
        this.messageManager = messageManager;
        this.mailSubject = messageManager.getMessage(MessageConstant.MAIL_CONFIRMATION_SUBJECT);
    }

    public void init() {
        Session mailSession = mailManager.createMailSession();
        mailSession.setDebug(true);
        message = new MimeMessage(mailSession);
        try {
            message.setSubject(mailSubject);
            message.setContent(mailText, "text/html");
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailRecipient));
        } catch (AddressException e) {
            String invalidRecipientMailMessage = String.format("E-mail recipient address %s is not valid", mailRecipient);
            LOGGER.error(invalidRecipientMailMessage, e);
            throw new EmailConfirmationException(invalidRecipientMailMessage, e);
        } catch (MessagingException e) {
            String errorInSendingEmailMessage = "Confirmation mail sending error";
            LOGGER.error(errorInSendingEmailMessage, e);
            throw new EmailConfirmationException(errorInSendingEmailMessage, e);
        }
    }

    @Override
    public void run() {
        init();
        try {
            Transport.send(message);
            LOGGER.info("Message is successfully sent to" + mailRecipient);
        } catch (MessagingException e) {
            String confirmationMailTransferErrorMessage = "Confirmation mail transfer error";
            LOGGER.error(confirmationMailTransferErrorMessage, e);
            throw new EmailConfirmationException(confirmationMailTransferErrorMessage, e);
        }
    }
}
