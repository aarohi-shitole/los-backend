package com.techvg.los.domain.enumeration;

/**
 * The Status enumeration.
 */
public enum Status {
    DRAFT("Draft"),
    CREATED("Created"),
    VERIFIED("Verified"),
    KYC_GENREATED("KYC_Genreated"),
    LOAN_DEMAND("loan_Demand"),
    LOAN_ACTIVE("Loan_Active"),
    LOAN_CANCELLED("Loan_cancelled"),
    LOAN_CLOSED("Loan_Closed"),
    REJECTED("Rejected");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
