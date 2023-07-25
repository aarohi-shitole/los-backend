package com.techvg.los.domain.enumeration;

/**
 * The DeclearationType enumeration.
 */
public enum DeclearationType {
    BOR_DECLARATION("Borrower_Declaration"),
    GURE_DECLARATION("Guantor_Declaration"),
    ORG_TERMS("Organisation_Terms");

    private final String value;

    DeclearationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
