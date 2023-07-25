package com.techvg.los.domain.enumeration;

/**
 * The FacilityStatus enumeration.
 */
public enum FacilityStatus {
    REGULAR("Regular"),
    OVERDUE("OverDue"),
    NPA("Npa");

    private final String value;

    FacilityStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
