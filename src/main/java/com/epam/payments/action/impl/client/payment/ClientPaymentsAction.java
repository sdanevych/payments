package com.epam.payments.action.impl.client.payment;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.dao.DaoFactory;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.PaymentDao;
import com.epam.payments.model.entity.payment.Payment;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.service.PaymentService;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.MessageConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.constant.SettingsConstant;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.resource_manager.MessageManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class ClientPaymentsAction implements DatabaseAction {
    private final PaymentService paymentService;
    {
        DaoFactory daoFactory = MySqlDaoFactory.getInstance();
        PaymentDao paymentDao = daoFactory.getPaymentDao();
        paymentService = PaymentService.getInstance();
        paymentService.setPaymentDao(paymentDao);
    }

    @Override
    public Navigator execute(HttpServletRequest request) throws ActionException {
        Navigator navigator = new Navigator();
        HttpSession session = request.getSession();
        MessageManager messageManager = getLocalizedMessageManager(session);
        User user = ((User) session.getAttribute(AttributeConstant.USER));
        Long userId = user.getId();
        Long page = PageManager.getPage(request);
        Long itemsPerPage = Payment.getItemsPerPage();
        List<Payment> clientPayments = (List<Payment>) session.getAttribute(AttributeConstant.CLIENT_PAYMENTS);
        List<Payment> clientReceivedPayments = paymentService.getPaymentsReceivedByClient(userId);
        EntitySortManager paymentSortManager = (EntitySortManager) session.getAttribute(AttributeConstant.PAYMENTS_SORT_MANAGER);
        PaginationManager<Payment> paymentsPaginationManager = paymentService.getPaymentsPaginationManagerByUserId(userId, page, itemsPerPage, paymentSortManager);
        request.setAttribute(AttributeConstant.PAGINATION_MANAGER, paymentsPaginationManager);
        session.setAttribute(AttributeConstant.CLIENT_RECEIVED_PAYMENTS, clientReceivedPayments);
        if (clientPayments.isEmpty()) {
            request.setAttribute(AttributeConstant.NO_CLIENT_PAYMENTS,
                    messageManager.getMessage(MessageConstant.INFO_NO_CLIENT_PAYMENTS));
        }
        navigator.setPagePath(PageManager.getProperty(PagePathsConstant.PAGE_PATH_CLIENT_PAYMENTS));
        return navigator;
    }
}
