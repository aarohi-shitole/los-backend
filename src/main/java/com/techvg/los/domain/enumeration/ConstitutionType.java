package com.techvg.los.domain.enumeration;

/**
 * The ConstitutionType enumeration.
 */
public enum ConstitutionType {
    SOLE_PROPRIETOR("Sole_Proprietor"),
    INDIVIDUAL("Individual"),
    PARTNERSHIP("Partnership"),
    LLP("Limited_Liability_Partnership"),
    PVT_LTD_COMPANY("Pvt_Ltd_Company");

    private final String value;

    ConstitutionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
