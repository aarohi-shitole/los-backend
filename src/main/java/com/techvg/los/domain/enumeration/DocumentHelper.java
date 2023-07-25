package com.techvg.los.domain.enumeration;

public enum DocumentHelper {
    MEMBER_ONBOARD("MEMBER_ONBOARD"),
    GUARANTOR_ONBOARD("GUARANTOR_ONBOARD"),
    LOAN_ONBOARD("LOAN_ONBOARD"),
    LOAN_APPRAIZAL("LOAN_APPRAIZAL"),
    LOAN_VALUATION("LOAN_VALUATION"),
    LOAN_LEGAL("LOAN_LEGAL"),
    LOAN_COMMITTEE("LOAN_COMMITTEE"),
    LOAN_RECO_DIRECTOR("LOAN_RECO_DIRECTOR"),
    LOAN_SANCATION("LOAN_SANCATION"),
    LOAN_OFFICE_USE("LOAN_OFFICE_USE"),
    LOAN_APPROVAL("LOAN_APPROVAL");

    private final String value;

    DocumentHelper(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
