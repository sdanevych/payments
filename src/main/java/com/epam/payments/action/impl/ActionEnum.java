package com.epam.payments.action.impl;

import com.epam.payments.action.Action;
import com.epam.payments.action.impl.admin.*;
import com.epam.payments.action.impl.client.MainAction;
import com.epam.payments.action.impl.client.account.*;
import com.epam.payments.action.impl.client.payment.*;
import com.epam.payments.action.impl.common.BlockAccountAction;
import com.epam.payments.action.impl.common.SetLanguageAction;
import com.epam.payments.action.impl.common.auth.LoginAction;
import com.epam.payments.action.impl.common.auth.LogoutAction;
import com.epam.payments.action.impl.common.auth.registration.RegisterConfirmationAction;
import com.epam.payments.action.impl.common.auth.registration.SignUpAction;
import com.epam.payments.action.impl.common.auth.registration.UserRegisterAction;
import com.epam.payments.action.impl.common.sort.*;

public enum ActionEnum {
    LOGIN(new LoginAction()),
    SIGN_UP(new SignUpAction()),
    SET_LANGUAGE(new SetLanguageAction()),
    MAIN(new MainAction()),
    USER_REGISTER(new UserRegisterAction()),
    USER_REGISTER_CONFIRMATION(new RegisterConfirmationAction()),
    CLIENT_ACCOUNTS(new ClientAccountsAction()),
    ADD_CLIENT_ACCOUNT(new AddAccountAction()),
    CLIENT_PAYMENTS(new ClientPaymentsAction()),
    CREATE_PAYMENT(new CreatePaymentAction()),
    PREPARE_PAYMENT(new PreparePaymentAction()),
    SEND_PAYMENT(new SendPaymentAction()),
    SEND_PAYMENT_CONFIRMATION(new SendPaymentConfirmationAction()),
    LOG_OUT(new LogoutAction()),
    REPLENISH_ACCOUNT(new ReplenishAccountAction()),
    BLOCK_ACCOUNT(new BlockAccountAction()),
    BANKCARD_DETAILS(new BankcardDetailsAction()),
    ADD_BANK_CARD(new AddBankCardAction()),
    ADD_BANK_CARD_CONFIRMATION(new AddBankCardConfirmAction()),
    REPLENISH_ACCOUNT_CONFIRMATION(new ReplenishAccountConfirmAction()),
    UNBLOCK_ACCOUNT_REQUEST(new UnblockAccountRequestAction()),
    UNBLOCK_ACCOUNT(new AdminUnblockAccountAction()),
    ADMIN_ACCOUNTS(new AdminAccountsAction()),
    ADMIN_USERS(new AdminUsersAction()),
    BLOCK_USER(new AdminBlockUserAction()),
    UNBLOCK_USER(new AdminUnblockUserAction()),
    ADMIN_ALL_PAYMENTS_LIST(new AdminAllPaymentsListAction()),
    ADMIN_SORT_USERS(new AdminSortUsers()),
    ADMIN_SORT_ACCOUNTS(new AdminSortAccounts()),
    ADMIN_SORT_PAYMENTS(new AdminSortPayments()),
    CLIENT_SORT_ACCOUNTS(new ClientSortAccounts()),
    CLIENT_SORT_PAYMENTS(new ClientSortPayments());
    private final Action action;

    ActionEnum(Action action) {
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
