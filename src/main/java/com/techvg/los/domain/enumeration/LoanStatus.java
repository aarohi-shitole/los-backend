package com.techvg.los.domain.enumeration;

/**
 * The LoanStatus enumeration.
 */
public enum LoanStatus {
    DRAFT("Draft"),
    CREATED("Created"),
    APPLIED("Applied"),
    VERIFIED("Verified"),
    PENDING("Pending"),
    AWAITED("Awaited"),
    AWAITED_LEG_VAL("Awaited_Legal_n_valuation"),
    AWAITED_DIR_REM("Awaited_Director_Remark"),
    AWAITED_COMM_REM("Awaited_comm_Remark"),
    APPROVED("Approved"),
    AWAITED_SAN("Awaited_Sanction"),
    SANCTIONED("Sanctioned"),
    AWAITED_DIS("Awaited_Disbursement"),
    REJECTED("Rejected"),
    CANCELLED("cancelled"),
    DISBURSED("Disbursed"),
    ACTIVE("Active"),
    CLOSED("Closed");

    private final String value;

    LoanStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
