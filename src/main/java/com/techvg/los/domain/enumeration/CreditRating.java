package com.techvg.los.domain.enumeration;

/**
 * The CreditRating enumeration.
 */
public enum CreditRating {
    OUTSTANDING("Outstanding"),
    GOOD("Good"),
    FAIR("Fair"),
    POOR("Poor");

    private final String value;

    CreditRating(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
