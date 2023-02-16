package com.epam.payments.model.entity.account;

public enum Currency {
    UKRAINE_HRYVNIA ("UAH", "₴"),
    USA_DOLLAR("USD", "$"),
    EURO ("EUR", "€");

    private String code;
    private String symbol;

    Currency(String code, String symbol) {
        this.code = code;
        this.symbol = symbol;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }
}
