package com.techvg.los.domain.enumeration;

/**
 * The OrganisationType enumeration.
 */
public enum OrganisationType {
    SOCIETY("Society"),
    CO_SOCIETY("Co_Society"),
    BANK("Bank"),
    URBAN_BANK("Urban_bank"),
    RURAL_BANK("Rural_bank");

    private final String value;

    OrganisationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
