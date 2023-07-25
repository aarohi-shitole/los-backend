package com.techvg.los.domain.enumeration;

/**
 * The AssetType enumeration.
 */
public enum AssetType {
    BUNGLOW("Bunglow"),
    FLAT("Flat"),
    FARM_HOUSE("Farm_House"),
    LAND("Land"),
    OTHERS("Others");

    private final String value;

    AssetType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
