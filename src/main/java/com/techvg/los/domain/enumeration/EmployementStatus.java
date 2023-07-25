package com.techvg.los.domain.enumeration;

/**
 * The EmployementStatus enumeration.
 */
public enum EmployementStatus {
    REGULAR("Regular"),
    TEMPORARY("Temporary"),
    CONTRACT("Contract"),
    PROBATION("Probation"),
    TRAINEE("Trainee");

    private final String value;

    EmployementStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
