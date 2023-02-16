package com.epam.payments.action.impl.client.account;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.AccountDao;
import com.epam.payments.model.dao.interfaces.BankCardDao;
import com.epam.payments.model.entity.account.Account;
import com.epam.payments.model.entity.account.AccountStatus;
import com.epam.payments.model.entity.account.BankCard;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.service.AccountService;
import com.epam.payments.service.BankCardService;
import com.epam.payments.service.ReplenishAccountService;
import com.epam.payments.utils.Encryption;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.MessageConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.resource_manager.MessageManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.List;

public class ReplenishAccountConfirmAction implements DatabaseAction {
    private final BankCardService bankCardService;
    private final AccountService accountService;
    private final ReplenishAccountService replenishAccountService;

    {
        replenishAccountService = ReplenishAccountService.getInstance();
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
        BigDecimal replenishAmount = new BigDecimal(request.getParameter(AttributeConstant.REPLENISH_AMOUNT));
        String enteredCvv = Encryption.encrypt(request.getParameter(AttributeConstant.ENTERED_CVV));
        Long accountId = Long.valueOf(request.getParameter(AttributeConstant.CLIENT_ACCOUNT_ID));
        Account account = accountService.getAccount(accountId);
        Long page = PageManager.getPage(request);
        BankCard bankCard = bankCardService.getBankCardByAccountId(account.getId());
        Long bankCardId = bankCard.getId();
        String actualCvv = bankCard.getCvv();
        Long userId = ((User) session.getAttribute(AttributeConstant.USER)).getId();
        List<Long> clientAccountIdsWithBankCards = accountService.getClientAccountIdsWithBankCards(userId);
        Long itemsPerPage = Account.getItemsPerPage();
        EntitySortManager accountsSortManager = (EntitySortManager) session.getAttribute(AttributeConstant.ACCOUNTS_SORT_MANAGER);
        PaginationManager<Account> accountsPaginationManager = accountService
                .getAccountsPaginationManagerByUserId(userId, page, itemsPerPage, accountsSortManager);
        session.setAttribute(AttributeConstant.CLIENT_ACCOUNT_IDS_WITH_BANK_CARDS, clientAccountIdsWithBankCards);
        request.setAttribute(AttributeConstant.PAGINATION_MANAGER, accountsPaginationManager);
        request.setAttribute(AttributeConstant.PROCESSING_CLIENT_ACCOUNT, account);
        if (account.getStatus() == AccountStatus.ACTIVE
                && bankCardService.isSufficientBalanceToWithdraw(bankCardId, replenishAmount)
                && enteredCvv.equals(actualCvv)) {
            replenishAccountService.replenishAccountWithBankcard(account, bankCard, replenishAmount);
            clientAccountIdsWithBankCards = accountService.getClientAccountIdsWithBankCards(userId);
            itemsPerPage = Account.getItemsPerPage();
            accountsPaginationManager = accountService.getAccountsPaginationManagerByUserId(userId, page, itemsPerPage, accountsSortManager);
            session.setAttribute(AttributeConstant.CLIENT_ACCOUNT_IDS_WITH_BANK_CARDS, clientAccountIdsWithBankCards);
            request.setAttribute(AttributeConstant.PAGINATION_MANAGER, accountsPaginationManager);
            request.setAttribute(AttributeConstant.REPLENISH_SUCCESS,
                    messageManager.getMessage(MessageConstant.INFO_REPLENISH_SUCCESS));
        } else {
            request.setAttribute(AttributeConstant.REPLENISH_FAIL,
                    messageManager.getMessage(MessageConstant.INFO_REPLENISH_FAIL));
        }
        navigator.setPagePath(PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_ACCOUNTS));
        return navigator;
    }
}
