package com.epam.payments.action.impl.client.account;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.BankCardDao;
import com.epam.payments.model.entity.account.BankCard;
import com.epam.payments.service.BankCardService;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.resource_manager.PageManager;
import jakarta.servlet.http.HttpServletRequest;

public class BankcardDetailsAction implements DatabaseAction {
    private final BankCardService bankCardService;

    {
        DaoFactory daoFactory = MySqlDaoFactory.getInstance();
        BankCardDao bankCardDao = daoFactory.getBankCardDao();
        bankCardService = BankCardService.getInstance();
        bankCardService.setBankCardDao(bankCardDao);
    }

    @Override
    public Navigator execute(HttpServletRequest request) throws ActionException {
        Navigator navigator = new Navigator();
        Long accountId = Long.valueOf(request.getParameter(AttributeConstant.CLIENT_ACCOUNT_ID));
        BankCard bankCard = bankCardService.getBankCardByAccountId(accountId);
        request.setAttribute(AttributeConstant.CLIENT_ACCOUNT_BANK_CARD, bankCard);
        navigator.setPagePath(PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_BANK_CARD));
        return navigator;
    }
}
