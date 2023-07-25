package com.techvg.los.domain.enumeration;

/**
 * The ParameterLookupType enumeration.
 */
public enum ParameterLookupType {
    RELIGION("Religion"),
    CASTE("Caste"),
    CASTE_CATEGORY("Caste_Category"),
    DOC_CATEGORY("Doc_Category"),
    REMARK_TYPE("Remark_Type"),
    ACCOUNT_TYPE("Account_Type");

    //    DEPOSIT_TYPE("Deposit_Type");

    private final String value;

    ParameterLookupType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
