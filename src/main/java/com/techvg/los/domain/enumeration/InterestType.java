package com.techvg.los.domain.enumeration;

/**
 * The InterestType enumeration.
 */
public enum InterestType {
    FIXED("Fixed"),
    FLOATING("Floating");

    private final String value;

    InterestType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
