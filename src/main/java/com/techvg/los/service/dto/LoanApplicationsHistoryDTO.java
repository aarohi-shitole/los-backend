package com.techvg.los.service.dto;

import com.techvg.los.domain.SecurityUser;
import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.domain.enumeration.StepperNumber;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.LoanApplications} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanApplicationsHistoryDTO implements Serializable {

    private Long id;

    private String applicationNo;

    private Double demandAmount;

    private String loanPurpose;

    private LoanStatus status;

    private Integer demandedLandAreaInHector;

    private String seasonOfCropLoan;

    private StepperNumber loanSteps;

    private Boolean isInsured;

    private Double loanBenefitingArea;

    private Double costOfInvestment;

    private Long mortgageDeedNo;

    private Instant mortgageDate;

    private Double extentMorgageValue;

    private Double downPaymentAmt;

    private Double ltvRatio;

    private Double processingFee;

    private Double penalInterest;

    private String moratorium;

    private Double roi;

    private Double commityAmt;

    private Double commityRoi;

    private Double sanctionAmt;

    private Double sanctionRoi;

    private String year;

    private SecurityUser assignedTo;

    private SecurityUser assignedFrom;

    private String securityDocUrl;

    private Instant lastModified;

    private String lastModifiedBy;

    private String margin;

    private String securityOffered;

    private String freeField3;

    private String freeField4;

    private String freeField5;

    private String freeField6;

    private String freeField7;

    private Long memberId;

    private SecurityUser securityUserId;

    private ProductDTO product;

    private BranchDTO branch;

    private LoanApplicationsDTO loanApplication;

    private List<MemberDTO> coApplicantList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Double getDemandAmount() {
        return demandAmount;
    }

    public void setDemandAmount(Double demandAmount) {
        this.demandAmount = demandAmount;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public Integer getDemandedLandAreaInHector() {
        return demandedLandAreaInHector;
    }

    public void setDemandedLandAreaInHector(Integer demandedLandAreaInHector) {
        this.demandedLandAreaInHector = demandedLandAreaInHector;
    }

    public String getSeasonOfCropLoan() {
        return seasonOfCropLoan;
    }

    public void setSeasonOfCropLoan(String seasonOfCropLoan) {
        this.seasonOfCropLoan = seasonOfCropLoan;
    }

    public StepperNumber getLoanSteps() {
        return loanSteps;
    }

    public void setLoanSteps(StepperNumber loanSteps) {
        this.loanSteps = loanSteps;
    }

    public Boolean getIsInsured() {
        return isInsured;
    }

    public void setIsInsured(Boolean isInsured) {
        this.isInsured = isInsured;
    }

    public Double getLoanBenefitingArea() {
        return loanBenefitingArea;
    }

    public void setLoanBenefitingArea(Double loanBenefitingArea) {
        this.loanBenefitingArea = loanBenefitingArea;
    }

    public Double getCostOfInvestment() {
        return costOfInvestment;
    }

    public void setCostOfInvestment(Double costOfInvestment) {
        this.costOfInvestment = costOfInvestment;
    }

    public Long getMortgageDeedNo() {
        return mortgageDeedNo;
    }

    public void setMortgageDeedNo(Long mortgageDeedNo) {
        this.mortgageDeedNo = mortgageDeedNo;
    }

    public Instant getMortgageDate() {
        return mortgageDate;
    }

    public void setMortgageDate(Instant mortgageDate) {
        this.mortgageDate = mortgageDate;
    }

    public Double getExtentMorgageValue() {
        return extentMorgageValue;
    }

    public void setExtentMorgageValue(Double extentMorgageValue) {
        this.extentMorgageValue = extentMorgageValue;
    }

    public Double getDownPaymentAmt() {
        return downPaymentAmt;
    }

    public void setDownPaymentAmt(Double downPaymentAmt) {
        this.downPaymentAmt = downPaymentAmt;
    }

    public Double getLtvRatio() {
        return ltvRatio;
    }

    public void setLtvRatio(Double ltvRatio) {
        this.ltvRatio = ltvRatio;
    }

    public Double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(Double processingFee) {
        this.processingFee = processingFee;
    }

    public Double getPenalInterest() {
        return penalInterest;
    }

    public void setPenalInterest(Double penalInterest) {
        this.penalInterest = penalInterest;
    }

    public String getMoratorium() {
        return moratorium;
    }

    public void setMoratorium(String moratorium) {
        this.moratorium = moratorium;
    }

    public Double getRoi() {
        return roi;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }

    public Double getCommityAmt() {
        return commityAmt;
    }

    public void setCommityAmt(Double commityAmt) {
        this.commityAmt = commityAmt;
    }

    public Double getCommityRoi() {
        return commityRoi;
    }

    public void setCommityRoi(Double commityRoi) {
        this.commityRoi = commityRoi;
    }

    public Double getSanctionAmt() {
        return sanctionAmt;
    }

    public void setSanctionAmt(Double sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public Double getSanctionRoi() {
        return sanctionRoi;
    }

    public void setSanctionRoi(Double sanctionRoi) {
        this.sanctionRoi = sanctionRoi;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public SecurityUser getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(SecurityUser assignedTo) {
        this.assignedTo = assignedTo;
    }

    public SecurityUser getAssignedFrom() {
        return assignedFrom;
    }

    public void setAssignedFrom(SecurityUser assignedFrom) {
        this.assignedFrom = assignedFrom;
    }

    public String getSecurityDocUrl() {
        return securityDocUrl;
    }

    public void setSecurityDocUrl(String securityDocUrl) {
        this.securityDocUrl = securityDocUrl;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getMargin() {
        return margin;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getSecurityOffered() {
        return securityOffered;
    }

    public void setSecurityOffered(String securityOffered) {
        this.securityOffered = securityOffered;
    }

    public String getFreeField3() {
        return freeField3;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return freeField4;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return freeField5;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getFreeField6() {
        return freeField6;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    public String getFreeField7() {
        return freeField7;
    }

    public void setFreeField7(String freeField7) {
        this.freeField7 = freeField7;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public SecurityUser getSecurityUserId() {
        return securityUserId;
    }

    public void setSecurityUserId(SecurityUser securityUserId) {
        this.securityUserId = securityUserId;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public BranchDTO getBranch() {
        return branch;
    }

    public void setbranch(BranchDTO branch) {
        this.branch = branch;
    }

    public LoanApplicationsDTO getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplicationsDTO loanApplication) {
        this.loanApplication = loanApplication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanApplicationsHistoryDTO)) {
            return false;
        }

        LoanApplicationsHistoryDTO loanApplicationsHistoryDTO = (LoanApplicationsHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, loanApplicationsHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanApplicationsDTO{" +
            "id=" + getId() +
            ", applicationNo='" + getApplicationNo() + "'" +
            ", demandAmount=" + getDemandAmount() +
            ", loanPurpose='" + getLoanPurpose() + "'" +
            ", status='" + getStatus() + "'" +
            ", demandedLandAreaInHector=" + getDemandedLandAreaInHector() +
            ", seasonOfCropLoan='" + getSeasonOfCropLoan() + "'" +
            ", loanSteps='" + getLoanSteps() + "'" +
            ", isInsured='" + getIsInsured() + "'" +
            ", loanBenefitingArea=" + getLoanBenefitingArea() +
            ", costOfInvestment=" + getCostOfInvestment() +
            ", mortgageDeedNo=" + getMortgageDeedNo() +
            ", mortgageDate='" + getMortgageDate() + "'" +
            ", extentMorgageValue=" + getExtentMorgageValue() +
            ", downPaymentAmt=" + getDownPaymentAmt() +
            ", ltvRatio=" + getLtvRatio() +
            ", processingFee=" + getProcessingFee() +
            ", penalInterest=" + getPenalInterest() +
            ", moratorium='" + getMoratorium() + "'" +
            ", roi=" + getRoi() +
            ", commityAmt=" + getCommityAmt() +
            ", commityRoi=" + getCommityRoi() +
            ", sectionAmt=" + getSanctionAmt() +
            ", senctionRoi=" + getSanctionRoi() +
            ", year='" + getYear() + "'" +
            ", assignedTo=" + getAssignedTo() +
            ", assignedFrom=" + getAssignedFrom() +
            ", securityDocUrl='" + getSecurityDocUrl() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", margin='" + getMargin() + "'" +
            ", securityOffered='" + getSecurityOffered() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", freeField5='" + getFreeField5() + "'" +
            ", freeField6='" + getFreeField6() + "'" +
            ", freeField7='" + getFreeField7() + "'" +
            ", memberId=" + getMemberId() +
            ", securityUserId=" + getSecurityUserId() +
            ", product=" + getProduct() +
            ", branch=" + getBranch() +
            ", loanApplication=" + getLoanApplication() +
            "}";
    }

    public List<MemberDTO> getCoApplicantList() {
        return coApplicantList;
    }

    public void setCoApplicantList(List<MemberDTO> coApplicantList) {
        this.coApplicantList = coApplicantList;
    }
}
