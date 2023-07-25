package com.techvg.los.domain.enumeration;

/**
 * The NpaClassification enumeration.
 */
public enum NpaClassification {
    SUB_STANDARD_ASSESTS("Sub_Standard_Assets"),
    DOUBTFUL_1("Doubtful_1"),
    DOUBTFUL_2("Doubtful_2"),
    DOUBTFUL_3("Doubtful_3"),
    SUB_STANDARD("Sub_Standard"),
    STANDARD("Standard");

    private final String value;

    NpaClassification(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
