package com.techvg.los.domain.enumeration;

/**
 * The IndustryType enumeration.
 */
public enum IndustryType {
    MANUFACTURING("Manufacturing"),
    SERVICE_INDUSTRY("Service_Industry"),
    TRADING("Trading");

    private final String value;

    IndustryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
