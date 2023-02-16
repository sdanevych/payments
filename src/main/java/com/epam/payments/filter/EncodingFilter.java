package com.epam.payments.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class EncodingFilter implements Filter {

    private String encoding;
    private boolean forceEncoding = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
        forceEncoding = Boolean.parseBoolean(filterConfig.getInitParameter("forceEncoding"));
        if (encoding.isBlank()) {
            this.setEncoding("UTF-8");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (this.encoding != null && (this.forceEncoding || request.getCharacterEncoding().isBlank())) {
            request.setCharacterEncoding(encoding);
            if (this.forceEncoding) {
                request.setCharacterEncoding(this.encoding);
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    public void setForceEncoding(boolean forceEncoding) {
        this.forceEncoding = forceEncoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
