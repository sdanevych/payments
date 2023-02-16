package com.epam.payments.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

public class PaginationTag extends SimpleTagSupport {
    private String urlPattern;
    private long page;
    private long totalPages;

    public PaginationTag() {
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public void doTag() throws JspException, IOException {
        StringBuilder paginationTagString = new StringBuilder();
        paginationTagString.append("<nav><ul pagination class=\"pagination mb-0\">");
        String disabled = (page == 1) ? "disabled" : "";
        paginationTagString.append(String.format("<li class=\"pagination-item %s\">" +
                "<a class=\"pagination-link\" href=\"%s%s\" >First</a></li>", disabled, urlPattern, 1));
        paginationTagString.append(String.format("<li class=\"pagination-item %s\">" +
                "<a class=\"pagination-link\" href=\"%s%s\" >Previous</a></li>", disabled, urlPattern, page - 1));
        for (int i = 1; i <= totalPages; i++) {
            String active = (page == i) ? "active" : "";
            paginationTagString.append(String.format("<li class=\"pagination-item %s\">" +
                    "<a class=\"pagination-link\" href=\"%s%3$s\" >%3$s</a></li>", active, urlPattern, i));
        }
        disabled = (page == totalPages) ? "disabled" : "";
        paginationTagString.append(String.format("<li class=\"pagination-item %s\">" +
                "<a class=\"pagination-link\" href=\"%s%s\" >Next</a></li>", disabled, urlPattern, page + 1));
        paginationTagString.append(String.format("<li class=\"pagination-item %s\">" +
                "<a class=\"pagination-link\" href=\"%s%s\" >Last</a></li>", disabled, urlPattern, totalPages));
        paginationTagString.append("</ul></nav>");
        getJspContext().getOut().write(paginationTagString.toString());
    }
}
