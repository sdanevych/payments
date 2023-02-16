package com.epam.payments.model.entity.account;

public enum AccountStatus {
    ACTIVE("ACTIVE"),
    BLOCKED("BLOCKED"),
    WAITING_FOR_UNLOCK("WAITING FOR UNLOCK");

    private String label;
    AccountStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
