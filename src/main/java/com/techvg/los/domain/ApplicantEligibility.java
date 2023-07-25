package com.techvg.los.domain;

import com.techvg.los.domain.enumeration.Occupation;

public class ApplicantEligibility {

    private Occupation employeeType;
    private String applicantName;
    private Long cibilScore;
    private double netIncome;
    private double outstanding;
    private double eligibleAmt;
    private double dememdedAmt;
    private double loanEligibleAmtAgainstMortProperty;
    private double propertyValuation;
    private String remarkBasedOnValuation;
    private String emiCapacityRemark;
    private double applicantAge;
    private double emiCapacityOnIncome;
    private double memberEMICapacity;
    private double requiredEMIhasToPay;

    public ApplicantEligibility() {
        super();
        // TODO Auto-generated constructor stub
    }

    public double getRequiredEMIhasToPay() {
        return requiredEMIhasToPay;
    }

    public void setRequiredEMIhasToPay(double requiredEMIhasToPay) {
        this.requiredEMIhasToPay = requiredEMIhasToPay;
    }

    public double getMemberEMICapacity() {
        return memberEMICapacity;
    }

    public void setMemberEMICapacity(double memberEMICapacity) {
        this.memberEMICapacity = memberEMICapacity;
    }

    public double getEmiCapacityOnIncome() {
        return emiCapacityOnIncome;
    }

    public void setEmiCapacityOnIncome(double emiCapacityOnIncome) {
        this.emiCapacityOnIncome = emiCapacityOnIncome;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Double getPropertyValuation() {
        return propertyValuation;
    }

    public void setPropertyValuation(Double propertyValuation) {
        this.propertyValuation = propertyValuation;
    }

    public Long getCibilScore() {
        return cibilScore;
    }

    public void setCibilScore(Long cibilScore) {
        this.cibilScore = cibilScore;
    }

    public Double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(Double netIncome) {
        this.netIncome = netIncome;
    }

    public Double getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(Double outstanding) {
        this.outstanding = outstanding;
    }

    public Double getEligibleAmt() {
        return eligibleAmt;
    }

    public void setEligibleAmt(Double eligibleAmt) {
        this.eligibleAmt = eligibleAmt;
    }

    public Double getDememdedAmt() {
        return dememdedAmt;
    }

    public void setDememdedAmt(Double dememdedAmt) {
        this.dememdedAmt = dememdedAmt;
    }

    public Occupation getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(Occupation employeeType) {
        this.employeeType = employeeType;
    }

    public Double getLoanEligibleAmtAgainstMortProperty() {
        return loanEligibleAmtAgainstMortProperty;
    }

    public void setLoanEligibleAmtAgainstMortProperty(Double loanEligibleAmtAgainstMortProperty) {
        this.loanEligibleAmtAgainstMortProperty = loanEligibleAmtAgainstMortProperty;
    }

    public String getRemarkBasedOnValuation() {
        return remarkBasedOnValuation;
    }

    public void setRemarkBasedOnValuation(String remarkBasedOnValuation) {
        this.remarkBasedOnValuation = remarkBasedOnValuation;
    }

    public String getEmiCapacityRemark() {
        return emiCapacityRemark;
    }

    public void setEmiCapacityRemark(String emiCapacityRemark) {
        this.emiCapacityRemark = emiCapacityRemark;
    }

    public double getApplicantAge() {
        return applicantAge;
    }

    public void setApplicantAge(double applicantAge) {
        this.applicantAge = applicantAge;
    }
}
