package com.epam.payments.controller;

import com.epam.payments.utils.constant.PagePathsConstant;
import com.epam.payments.utils.resource_manager.PageManager;

import java.util.Objects;



public class Navigator {
    private String pagePath = PageManager.getProperty(PagePathsConstant.PAGE_PATH_LOGIN);
    private TransferType transferType = TransferType.FORWARD;

    public String getPagePath(){
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        if (Objects.nonNull(pagePath)) {
            this.pagePath = pagePath;
        }
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setRedirectTransferType() {
        this.transferType = TransferType.REDIRECT;
    }
}
