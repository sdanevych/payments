package com.epam.payments.filter;

import com.epam.payments.model.entity.user.Role;
import com.epam.payments.model.entity.user.User;
import com.epam.payments.model.entity.user.UserStatus;
import com.epam.payments.utils.constant.AttributeConstant;
import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.resource_manager.PageManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

public class SecurityFilter implements Filter {
    private final Map<String, Set<Role>> permissions = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Iterator<String> uries = filterConfig.getInitParameterNames().asIterator();
        while (uries.hasNext()) {
            String uri = uries.next();
            Set<Role> roles = new HashSet<>();
            for (String role : filterConfig.getInitParameter(uri).split(";")) {
                roles.add(Role.valueOf(role));
            }
            permissions.put(uri, roles);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String uri = ((HttpServletRequest) request).getRequestURI();
        String contextPath = request.getServletContext().getContextPath();
        uri = uri.substring(contextPath.length());
        Set<Role> roles = permissions.get(uri);
        if (roles != null) {
            HttpSession session = ((HttpServletRequest) request).getSession(false);
            boolean authorized = false;
            if (session != null) {
                User user = (User) session.getAttribute(AttributeConstant.USER);
                if (user != null && user.getStatus() == UserStatus.ACTIVE
                        && roles.contains(user.getRole())) {
                    authorized = true;
                }
            }
            if (!authorized) {
                ((HttpServletResponse) response)
                        .sendRedirect(contextPath + PageManager.getProperty(PagePathsConstant.PAGE_PATH_LOGIN));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
