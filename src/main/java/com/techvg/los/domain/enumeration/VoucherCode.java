package com.techvg.los.domain.enumeration;

/**
 * The VoucherCode enumeration.
 */
public enum VoucherCode {
    LOAN("By_Loan"),
    DEPOSIT("By_Deposit"),
    SHARES("By_Shares"),
    SALES("By_Sales"),
    PURCHASE("By_Purchase"),
    SAVINGS("By_Savings"),
    EXPENSE("By_Expense"),
    ASSETS("By_Assets"),
    INVESTMENT("By_Investment");

    private final String value;

    VoucherCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
