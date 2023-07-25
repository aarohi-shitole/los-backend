package com.techvg.los.domain.enumeration;

/**
 * The AssetNature enumeration.
 */
public enum AssetNature {
    RESIDENTIAL("Residential"),
    COMMERCIAL("Commercial"),
    OTHER("Other");

    private final String value;

    AssetNature(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
