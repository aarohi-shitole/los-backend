package com.techvg.los.domain.enumeration;

/**
 * The PaymentMode enumeration.
 */
public enum PaymentMode {
    ONLINE("Online_Payment"),
    CASH("Cash_Payment"),
    TRANSFER("Transfer"),
    CHEQUE("By_Cheque"),
    OTHERS("Other_Mode");

    private final String value;

    PaymentMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
