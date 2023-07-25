package com.techvg.los.domain.enumeration;

/**
 * The RepaymentFreqency enumeration.
 */
public enum RepaymentFreqency {
    MONTHLY("Monthly"),
    QUARTERLY("quarterly"),
    HALF_YEARLY("Half_yearly"),
    YEARLY("Yearly");

    private final String value;

    RepaymentFreqency(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
