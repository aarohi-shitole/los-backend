package com.techvg.los.domain.enumeration;

/**
 * The Occupation enumeration.
 */
public enum Occupation {
    SALARIED("Salaried"),
    BUSINESS("Business"),
    PROFESSIONAL("Professional"),
    SERVICE("Service"),
    SELF_EMPLOYED("Self_Employed"),
    AGRICULTURE("Agriculture"),
    PENSIONER("Pensioner");

    private final String value;

    Occupation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
