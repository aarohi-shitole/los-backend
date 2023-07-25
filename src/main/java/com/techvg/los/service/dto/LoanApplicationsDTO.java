package com.techvg.los.service.dto;

import com.techvg.los.domain.ApplicantEligibility;
import com.techvg.los.domain.SecurityUser;
import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.domain.enumeration.StepperNumber;
import java.io.Serializable;
//import java.sql.Array;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.LoanApplications} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanApplicationsDTO implements Serializable {

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

    private Long resolutionNo;

    private Instant mortgageDate;

    private Instant createdOn;

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

    private String remark;

    private SecurityUser assignedTo;

    private SecurityUser assignedFrom;

    private String securityDocUrl;

    private Instant lastModified;

    private String lastModifiedBy;

    private String margin;

    private String securityOffered;

    private Instant directorMeetDate;

    private Instant commityMeetDate;

    private Double directorAmount;

    private Double directorRoi;

    private Instant sanctionDate;

    private String applicantName;

    private Long memberId;

    private Long securityUserId;

    private ProductDTO product;

    private BranchDTO branch;

    private List<MemberDTO> coApplicantList;

    private ArrayList<RemarkHistoryDTO> remarkList;

    private List<LoanMemberDocumentsDTO> documentsDTOList;

    private String[] loanHistoryStatus;

    private Boolean hasReceiveLegal;

    private Boolean hasSubmitLegal;

    private Boolean hasRequiredDirRec;

    private Boolean hasUploadDocWithInformation;

    private Boolean hasFilledInfoWithDoc;

    private ApplicantEligibility applicantEligibility;

    public String[] getLoanHistoryStatus() {
        return loanHistoryStatus;
    }

    public void setLoanHistoryStatus(String[] loanHistoryStatus) {
        this.loanHistoryStatus = loanHistoryStatus;
    }

    public List<LoanMemberDocumentsDTO> getDocumentsDTOList() {
        return documentsDTOList;
    }

    public void setDocumentsDTOList(List<LoanMemberDocumentsDTO> documentsDTOList) {
        this.documentsDTOList = documentsDTOList;
    }

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

    public Long getResolutionNo() {
        return resolutionNo;
    }

    public void setResolutionNo(Long resolutionNo) {
        this.resolutionNo = resolutionNo;
    }

    public Instant getMortgageDate() {
        return mortgageDate;
    }

    public void setMortgageDate(Instant mortgageDate) {
        this.mortgageDate = mortgageDate;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Instant getDirectorMeetDate() {
        return directorMeetDate;
    }

    public void setDirectorMeetDate(Instant directorMeetDate) {
        this.directorMeetDate = directorMeetDate;
    }

    public Instant getCommityMeetDate() {
        return commityMeetDate;
    }

    public void setCommityMeetDate(Instant commityMeetDate) {
        this.commityMeetDate = commityMeetDate;
    }

    public Double getDirectorAmount() {
        return directorAmount;
    }

    public void setDirectorAmount(Double directorAmount) {
        this.directorAmount = directorAmount;
    }

    public Double getDirectorRoi() {
        return directorRoi;
    }

    public void setDirectorRoi(Double directorRoi) {
        this.directorRoi = directorRoi;
    }

    public Instant getSanctionDate() {
        return sanctionDate;
    }

    public void setSanctionDate(Instant sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getSecurityUserId() {
        return securityUserId;
    }

    public void setSecurityUserId(Long securityUserId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanApplicationsDTO)) {
            return false;
        }

        LoanApplicationsDTO loanApplicationsDTO = (LoanApplicationsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, loanApplicationsDTO.id);
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
            ", resolutionNo=" + getResolutionNo() +
            ", mortgageDate='" + getMortgageDate() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
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
            ", remark='" + getRemark() + "'" +
            ", assignedTo=" + getAssignedTo() +
            ", assignedFrom=" + getAssignedFrom() +
            ", securityDocUrl='" + getSecurityDocUrl() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", margin='" + getMargin() + "'" +
            ", securityOffered='" + getSecurityOffered() + "'" +
            ", directorMeetDate='" + getDirectorMeetDate() + "'" +
            ", commityMeetDate='" + getCommityMeetDate() + "'" +
            ", directorAmount='" + getDirectorAmount() + "'" +
            ", directorRoi='" + getDirectorRoi() + "'" +
            ", sanctionDate='" + getSanctionDate() + "'" +
            ", applicantName='" + getApplicantName() + "'" +
            ", memberId=" + getMemberId() +
            ", securityUserId=" + getSecurityUserId() +
            ", product=" + getProduct() +
            ", branch=" + getBranch() +
            "}";
    }

    public List<MemberDTO> getCoApplicantList() {
        return coApplicantList;
    }

    public void setCoApplicantList(List<MemberDTO> coApplicantList) {
        this.coApplicantList = coApplicantList;
    }

    public ArrayList<RemarkHistoryDTO> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(ArrayList<RemarkHistoryDTO> remarkList) {
        this.remarkList = remarkList;
    }

    public Boolean getHasReceiveLegal() {
        return hasReceiveLegal;
    }

    public void setHasReceiveLegal(Boolean hasReceiveLegal) {
        this.hasReceiveLegal = hasReceiveLegal;
    }

    public Boolean getHasSubmitLegal() {
        return hasSubmitLegal;
    }

    public void setHasSubmitLegal(Boolean hasSubmitLegal) {
        this.hasSubmitLegal = hasSubmitLegal;
    }

    public Boolean getHasUploadDocWithInformation() {
        return hasUploadDocWithInformation;
    }

    public void setHasUploadDocWithInformation(Boolean hasUploadDocWithInformation) {
        this.hasUploadDocWithInformation = hasUploadDocWithInformation;
    }

    public Boolean getHasRequiredDirRec() {
        return hasRequiredDirRec;
    }

    public void setHasRequiredDirRec(Boolean hasRequiredDirRec) {
        this.hasRequiredDirRec = hasRequiredDirRec;
    }

    public Boolean getHasFilledInfoWithDoc() {
        return hasFilledInfoWithDoc;
    }

    public void setHasFilledInfoWithDoc(Boolean hasFilledInfoWithDoc) {
        this.hasFilledInfoWithDoc = hasFilledInfoWithDoc;
    }

    public ApplicantEligibility getApplicantEligibility() {
        return applicantEligibility;
    }

    public void setApplicantEligibility(ApplicantEligibility applicantEligibility) {
        this.applicantEligibility = applicantEligibility;
    }
}
