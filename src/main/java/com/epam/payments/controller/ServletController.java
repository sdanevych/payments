package com.epam.payments.controller;

import com.epam.payments.action.Action;
import com.epam.payments.action.DatabaseAction;
import com.epam.payments.action.ActionFactory;
import com.epam.payments.exception.ActionException;
import com.epam.payments.exception.sql.NavigationException;
import com.epam.payments.model.database.ConnectionProvider;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.resource_manager.PageManager;
import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "ServletController", urlPatterns = "/controller")
public class ServletController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(ServletController.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        Action action = ActionFactory.defineAction(request);

        boolean isDatabaseConnectionRequired = false;
        if (action instanceof DatabaseAction) {
            isDatabaseConnectionRequired = true;
        }

        if (isDatabaseConnectionRequired) {
            ConnectionProvider.bindConnection();
        }

        Navigator navigator;
        try {
            navigator = action.execute(request);
            String currentPagePath = request.getRequestURI().substring(request.getContextPath().length());
            String destinationPagePath = navigator.getPagePath();
            if (destinationPagePath != null) {
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(destinationPagePath);
                switch (navigator.getTransferType()) {
                    case FORWARD -> requestDispatcher.forward(request, response);
                    case REDIRECT -> response.sendRedirect(request.getContextPath() + destinationPagePath);
                    default -> {
                        LOGGER.error(String.format("Unknown transfer type from %s page to %s page", currentPagePath, destinationPagePath));
                        throw new NavigationException("Unknown navigation type");
                    }
                }
            } else {
                destinationPagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_LOGIN);
                response.sendRedirect(request.getContextPath() + destinationPagePath);
            }
        } catch (ServletException | IOException | ActionException e) {
            LOGGER.error("Error in request processing", e);
        }

        if (isDatabaseConnectionRequired) {
            ConnectionProvider.unbindConnection();
        }
    }

}
