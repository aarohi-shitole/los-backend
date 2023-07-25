package com.techvg.los.domain.enumeration;

/**
 * The IncomeType enumeration.
 */
public enum IncomeType {
    PRINCIPAL_SOURCE("Principal_Source"),
    OTHER_SOURCE("Other_Source");

    private final String value;

    IncomeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
