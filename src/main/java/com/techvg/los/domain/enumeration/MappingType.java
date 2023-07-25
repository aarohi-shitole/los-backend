package com.techvg.los.domain.enumeration;

/**
 * The MappingType enumeration.
 */
public enum MappingType {
    HEADOFFICE("HeadOffice"),
    SHARE("Share"),
    CUSTOMER("Customer"),
    LOAN("Loan"),
    SUNDRY("Sundry"),
    PURCHASE("Purchase"),
    SALES("Sales"),
    LOANPRODUCT("LoanProduct"),
    DEPOSIT("Deposit"),
    BORROWING("Borrowing"),
    INVESTMENT("Investment");

    private final String value;

    MappingType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
