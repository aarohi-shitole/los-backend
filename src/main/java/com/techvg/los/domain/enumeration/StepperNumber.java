package com.techvg.los.domain.enumeration;

/**
 * The StepperNumber enumeration.
 */
public enum StepperNumber {
    STEP_1("Step_1"),
    STEP_2("Step_2"),
    STEP_3("Step_3"),
    STEP_4("Step_4"),
    STEP_5("Step_5"),
    STEP_6("Step_6");

    private final String value;

    StepperNumber(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
