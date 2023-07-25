package com.techvg.los.domain.enumeration;

/**
 * The LedgerClassification enumeration.
 */
public enum LedgerClassification {
    BALANCE_SHEET("Balance_Sheet"),
    TRADING_ACCOUNT("Trading_Account"),
    PROFIT_AND_LOSS("Profit_And_Loss");

    private final String value;

    LedgerClassification(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
