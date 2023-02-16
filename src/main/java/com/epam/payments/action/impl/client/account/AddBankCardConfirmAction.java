package com.epam.payments.action.impl.client.account;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.dao.interfaces.BankCardDao;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.model.entity.account.BankCard;
import com.epam.payments.model.entity.account.BankCardType;
import com.epam.payments.model.entity.account.Currency;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.service.AccountService;
import com.epam.payments.service.BankCardService;
import com.epam.payments.utils.Encryption;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.MessageConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.resource_manager.MessageManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;
import com.epam.payments.utils.validator.BankCardValidator;
import com.epam.payments.utils.validator.StandardValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.List;
public class AddBankCardConfirmAction implements DatabaseAction {
    private final AccountService accountService;
    private final BankCardService bankCardService;

    {
        DaoFactory daoFactory = MySqlDaoFactory.getInstance();
        AccountDao accountDao = daoFactory.getAccountDao();
        accountService = AccountService.getInstance();
        accountService.setAccountDao(accountDao);
        BankCardDao bankCardDao = daoFactory.getBankCardDao();
        bankCardService = BankCardService.getInstance();
        bankCardService.setBankCardDao(bankCardDao);
    }

    @Override
    public Navigator execute(HttpServletRequest request) throws ActionException {
        Navigator navigator = new Navigator();
        HttpSession session = request.getSession();
        MessageManager messageManager = getLocalizedMessageManager(session);
        Long accountId = Long.valueOf(request.getParameter(AttributeConstant.CLIENT_ACCOUNT_ID));
        Account processingAccount = accountService.getAccount(accountId);
        String expirationDateString = request.getParameter(AttributeConstant.BANK_CARD_EXPIRATION_DATE);
        Long page = PageManager.getPage(request);
        Long userId = ((User) session.getAttribute(AttributeConstant.USER)).getId();
        Long itemsPerPage = Account.getItemsPerPage();
        EntitySortManager accountsSortManager = (EntitySortManager) session.getAttribute(AttributeConstant.ACCOUNTS_SORT_MANAGER);
        PaginationManager<Account> accountsPaginationManager = accountService.getAccountsPaginationManagerByUserId(userId, page, itemsPerPage, accountsSortManager);
        request.setAttribute(AttributeConstant.PAGINATION_MANAGER, accountsPaginationManager);
        boolean isValidBankCardData = true;
        if (!BankCardValidator.isValidBankCardExpirationDateFormat(expirationDateString)) {
            request.setAttribute(AttributeConstant.INVALID_BANK_CARD_EXPIRATION_DATE_FORMAT,
                    messageManager.getMessage(MessageConstant.ERROR_INVALID_BANK_CARD_EXPIRATION_DATE_FORMAT));
            isValidBankCardData = false;
        }

        String bankCardNumber = request.getParameter(AttributeConstant.BANK_CARD_NUMBER);
        if (!BankCardValidator.isValidBankCardNumber(bankCardNumber)) {
            request.setAttribute(AttributeConstant.INVALID_BANK_CARD_NUMBER,
                    messageManager.getMessage(MessageConstant.ERROR_INVALID_BANK_CARD_NUMBER));
            isValidBankCardData = false;
        }

        String bankCardCvv = request.getParameter(AttributeConstant.BANK_CARD_CVV);
        if (!BankCardValidator.isValidBankCardCvv(bankCardCvv)) {
            request.setAttribute(AttributeConstant.INVALID_BANK_CARD_CVV,
                    messageManager.getMessage(MessageConstant.ERROR_INVALID_BANK_CARD_CVV));
            isValidBankCardData = false;
        }

        String pagePath;
        if (isValidBankCardData) {
            BankCardType bankCardType = BankCardType.valueOf(request.getParameter(AttributeConstant.BANK_CARD_TYPE));
            Currency bankCardCurrency = Currency.valueOf(request.getParameter(AttributeConstant.BANK_CARD_CURRENCY));
            String cardholderName = request.getParameter(AttributeConstant.BANK_CARD_CARDHOLDER_NAME);
            BigDecimal bankCardBalance = new BigDecimal(request.getParameter(AttributeConstant.BANK_CARD_BALANCE));
            String cvv = request.getParameter(AttributeConstant.BANK_CARD_CVV);
            BankCard bankCard = new BankCard();
            bankCard.setAccountId(accountId);
            bankCard.setCardNumber(StandardValidator.replaceScript(bankCardNumber));
            bankCard.setBankCardType(bankCardType);
            bankCard.setCurrency(bankCardCurrency);
            bankCardService.setBankCardExpirationDateByMonthAndYear(bankCard, expirationDateString);
            bankCard.setCardholderName(StandardValidator.replaceScript(cardholderName));
            bankCard.setBalance(bankCardBalance);
            bankCard.setCvv(Encryption.encrypt(StandardValidator.replaceScript(cvv)));
            bankCardService.addBankCard(bankCard);
            List<Long> clientAccountIdsWithBankCards = accountService.getClientAccountIdsWithBankCards(userId);
            List<Account> clientActiveAccountsWithBankCards = accountService.getActiveAccountsWithBankCards(userId);
            session.setAttribute(AttributeConstant.CLIENT_ACCOUNT_IDS_WITH_BANK_CARDS, clientAccountIdsWithBankCards);
            session.setAttribute(AttributeConstant.CLIENT_ACTIVE_ACCOUNTS_WITH_BANK_CARDS, clientActiveAccountsWithBankCards);
            request.setAttribute(AttributeConstant.PROCESSING_CLIENT_ACCOUNT, processingAccount);
            request.setAttribute(AttributeConstant.ADD_BANK_CARD_SUCCESS,
                    messageManager.getMessage(MessageConstant.INFO_ADD_BANK_CARD_SUCCESS));
            pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_ACCOUNTS);
        } else {
            request.setAttribute(AttributeConstant.ADD_BANK_CARD_FAIL,
                    messageManager.getMessage(MessageConstant.ERROR_ADD_BANK_CARD_FAIL));
            pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_ADD_BANK_CARD);
        }
        navigator.setPagePath(pagePath);
        return navigator;
    }
}
