package com.techvg.los.domain.enumeration;

/**
 * The BranchType enumeration.
 */
public enum BranchType {
    ZONAL_OFFICE("Zonal_Office"),
    BRANCH("Branch");

    private final String value;

    BranchType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
