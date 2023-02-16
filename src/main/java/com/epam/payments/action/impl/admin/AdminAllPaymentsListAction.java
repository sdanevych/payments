package com.epam.payments.action.impl.admin;

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
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.resource_manager.MessageManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public class AdminAllPaymentsListAction implements DatabaseAction {
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
        List<Payment> allPayments = paymentService.getAllPayments();
        if (!allPayments.isEmpty()) {
            Long page = PageManager.getPage(request);
            Long itemsPerPage = User.getItemsPerPage();
            EntitySortManager adminAllPaymentsSortManager = (EntitySortManager) session
                    .getAttribute(AttributeConstant.ADMIN_ALL_PAYMENTS_SORT_MANAGER);
            PaginationManager<Payment> paymentsPaginationManager = paymentService
                    .getAllPaymentsPaginationManager(page, itemsPerPage, adminAllPaymentsSortManager);
            request.setAttribute(AttributeConstant.PAGINATION_MANAGER, paymentsPaginationManager);
        } else {
            request.setAttribute(AttributeConstant.NO_PAYMENTS,
                    messageManager.getMessage(MessageConstant.INFO_NO_PAYMENTS));
        }
        navigator.setPagePath(PageManager.getProperty(PagePathsConstant.PAGE_PATH_ADMIN_ALL_PAYMENTS_LIST));
        return navigator;
    }
}
