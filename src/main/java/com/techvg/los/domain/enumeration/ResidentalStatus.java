package com.techvg.los.domain.enumeration;

/**
 * The ResidentalStatus enumeration.
 */
public enum ResidentalStatus {
    RESIDENT("Resident"),
    NON_RESIDENT("Non_Resident"),
    OTHER("Other");

    private final String value;

    ResidentalStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
