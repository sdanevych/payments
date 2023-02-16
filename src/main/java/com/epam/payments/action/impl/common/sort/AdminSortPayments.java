package com.epam.payments.action.impl.common.sort;

import com.epam.payments.action.DatabaseAction;
import com.epam.payments.controller.Navigator;
import com.epam.payments.exception.ActionException;
import com.epam.payments.model.dao.impl.mysql.MySqlDaoFactory;
import com.epam.payments.model.dao.interfaces.PaymentDao;
import com.epam.payments.model.entity.payment.Payment;
import com.epam.payments.service.PaymentService;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.pagination.PaginationManager;
import com.epam.payments.utils.resource_manager.PageManager;
import com.epam.payments.utils.sort.EntitySortManager;
import com.epam.payments.utils.sort.SortOrder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AdminSortPayments implements DatabaseAction {
    private final PaymentService paymentService;

    {
        PaymentDao paymentDao = MySqlDaoFactory.getInstance().getPaymentDao();
        paymentService = PaymentService.getInstance();
        paymentService.setPaymentDao(paymentDao);
    }

    @Override
    public Navigator execute(HttpServletRequest request) throws ActionException {
        Navigator navigator = new Navigator();
        HttpSession session = request.getSession();
        Long page = PageManager.getPage(request);
        String sortColumn = request.getParameter(AttributeConstant.SORT_COLUMN);
        SortOrder sortOrder = SortOrder.valueOf(request.getParameter(AttributeConstant.SORT_ORDER));
        String currentJspPath = request.getParameter(AttributeConstant.JSP_PATH);
        Long itemsPerPage = Payment.getItemsPerPage();
        EntitySortManager paymentsSortManager = (EntitySortManager) session
                .getAttribute(AttributeConstant.ACCOUNTS_SORT_MANAGER);
        paymentsSortManager.setSortColumn(sortColumn);
        paymentsSortManager.setSortOrder(sortOrder);
        PaginationManager<Payment> paymentsPaginationManager =
                paymentService.getAllPaymentsPaginationManager(page, itemsPerPage, paymentsSortManager);
        session.setAttribute(AttributeConstant.PAYMENTS_SORT_MANAGER, paymentsSortManager);
        request.setAttribute(AttributeConstant.PAGINATION_PAGE, page);
        request.setAttribute(AttributeConstant.PAGINATION_MANAGER, paymentsPaginationManager);
        navigator.setPagePath(currentJspPath);
        return navigator;
    }
}
