package com.techvg.los.domain.enumeration;

public enum ApplicantType {
    APPLICANT("Applicant"),
    CO_APPLICANT("Co_Applicant");

    private final String value;

    private ApplicantType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
