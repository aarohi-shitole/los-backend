package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.domain.enumeration.StepperNumber;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * A LoanApplications.
 */
@Entity
@Table(name = "loan_applications")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanApplications implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "application_no")
    private String applicationNo;

    @Column(name = "demand_amount")
    private Double demandAmount;

    @Column(name = "loan_purpose")
    private String loanPurpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LoanStatus status;

    @Column(name = "demanded_land_area_in_hector")
    private Integer demandedLandAreaInHector;

    @Column(name = "season_of_crop_loan")
    private String seasonOfCropLoan;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_steps")
    private StepperNumber loanSteps;

    @Column(name = "is_insured")
    private Boolean isInsured;

    @Column(name = "loan_benefiting_area")
    private Double loanBenefitingArea;

    @Column(name = "cost_of_investment")
    private Double costOfInvestment;

    @Column(name = "mortgage_deed_no")
    private Long mortgageDeedNo;

    @Column(name = "resolution_no")
    private Long resolutionNo;

    @Column(name = "mortgage_date")
    private Instant mortgageDate;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "extent_morgage_value")
    private Double extentMorgageValue;

    @Column(name = "down_payment_amt")
    private Double downPaymentAmt;

    @Column(name = "ltv_ratio")
    private Double ltvRatio;

    @Column(name = "processing_fee")
    private Double processingFee;

    @Column(name = "penal_interest")
    private Double penalInterest;

    @Column(name = "moratorium")
    private String moratorium;

    @Column(name = "roi")
    private Double roi;

    @Column(name = "commity_amt")
    private Double commityAmt;

    @Column(name = "commity_roi")
    private Double commityRoi;

    @Column(name = "sanction_amt")
    private Double sanctionAmt;

    @Column(name = "sanction_roi")
    private Double sanctionRoi;

    @Column(name = "year")
    private String year;

    @Column(name = "remark")
    private String remark;

    @Column(name = "security_doc_url")
    private String securityDocUrl;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "margin")
    private String margin;

    @Column(name = "security_offered")
    private String securityOffered;

    @Column(name = "director_meet_date")
    private Instant directorMeetDate;

    @Column(name = "commity_meet_date")
    private Instant commityMeetDate;

    @Column(name = "director_amount")
    private Double directorAmount;

    @Column(name = "director_roi")
    private Double directorRoi;

    @Column(name = "sanction_date")
    private Instant sanctionDate;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "has_submit_legal")
    private Boolean hasSubmitLegal;

    @Column(name = "has_receive_legal")
    private Boolean hasReceiveLegal;

    @Column(name = "has_required_dirRec")
    private Boolean hasRequiredDirRec;

    @JsonIgnore
    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "assigned_to")
    private SecurityUser assignedTo;

    @OneToOne
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @JoinColumn(name = "assigned_from")
    private SecurityUser assignedFrom;

    //    @ManyToOne
    //    @JsonIgnoreProperties(value = { "enquiryDetails", "branch", "member", "securityUser" }, allowSetters = true)
    //    private Member member;

    @ManyToOne
    @JsonIgnoreProperties(value = { "branch", "securityPermissions", "securityRoles" }, allowSetters = true)
    private SecurityUser securityUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "loanCatagory", "organisation", "ledgerAccounts" }, allowSetters = true)
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(value = { "address", "region", "organisation" }, allowSetters = true)
    private Branch branch;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LoanApplications id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationNo() {
        return this.applicationNo;
    }

    public LoanApplications applicationNo(String applicationNo) {
        this.setApplicationNo(applicationNo);
        return this;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Double getDemandAmount() {
        return this.demandAmount;
    }

    public LoanApplications demandAmount(Double demandAmount) {
        this.setDemandAmount(demandAmount);
        return this;
    }

    public void setDemandAmount(Double demandAmount) {
        this.demandAmount = demandAmount;
    }

    public String getLoanPurpose() {
        return this.loanPurpose;
    }

    public LoanApplications loanPurpose(String loanPurpose) {
        this.setLoanPurpose(loanPurpose);
        return this;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public LoanStatus getStatus() {
        return this.status;
    }

    public LoanApplications status(LoanStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public Integer getDemandedLandAreaInHector() {
        return this.demandedLandAreaInHector;
    }

    public LoanApplications demandedLandAreaInHector(Integer demandedLandAreaInHector) {
        this.setDemandedLandAreaInHector(demandedLandAreaInHector);
        return this;
    }

    public void setDemandedLandAreaInHector(Integer demandedLandAreaInHector) {
        this.demandedLandAreaInHector = demandedLandAreaInHector;
    }

    public String getSeasonOfCropLoan() {
        return this.seasonOfCropLoan;
    }

    public LoanApplications seasonOfCropLoan(String seasonOfCropLoan) {
        this.setSeasonOfCropLoan(seasonOfCropLoan);
        return this;
    }

    public void setSeasonOfCropLoan(String seasonOfCropLoan) {
        this.seasonOfCropLoan = seasonOfCropLoan;
    }

    public StepperNumber getLoanSteps() {
        return this.loanSteps;
    }

    public LoanApplications loanSteps(StepperNumber loanSteps) {
        this.setLoanSteps(loanSteps);
        return this;
    }

    public void setLoanSteps(StepperNumber loanSteps) {
        this.loanSteps = loanSteps;
    }

    public Boolean getIsInsured() {
        return this.isInsured;
    }

    public LoanApplications isInsured(Boolean isInsured) {
        this.setIsInsured(isInsured);
        return this;
    }

    public void setIsInsured(Boolean isInsured) {
        this.isInsured = isInsured;
    }

    public Double getLoanBenefitingArea() {
        return this.loanBenefitingArea;
    }

    public LoanApplications loanBenefitingArea(Double loanBenefitingArea) {
        this.setLoanBenefitingArea(loanBenefitingArea);
        return this;
    }

    public void setLoanBenefitingArea(Double loanBenefitingArea) {
        this.loanBenefitingArea = loanBenefitingArea;
    }

    public Double getCostOfInvestment() {
        return this.costOfInvestment;
    }

    public LoanApplications costOfInvestment(Double costOfInvestment) {
        this.setCostOfInvestment(costOfInvestment);
        return this;
    }

    public void setCostOfInvestment(Double costOfInvestment) {
        this.costOfInvestment = costOfInvestment;
    }

    public Long getResolutionNo() {
        return this.resolutionNo;
    }

    public LoanApplications resolutionNo(Long resolutionNo) {
        this.setResolutionNo(resolutionNo);
        return this;
    }

    public void setResolutionNo(Long resolutionNo) {
        this.resolutionNo = resolutionNo;
    }

    public Long getMortgageDeedNo() {
        return this.mortgageDeedNo;
    }

    public LoanApplications mortgageDeedNo(Long mortgageDeedNo) {
        this.setMortgageDeedNo(mortgageDeedNo);
        return this;
    }

    public void setMortgageDeedNo(Long mortgageDeedNo) {
        this.mortgageDeedNo = mortgageDeedNo;
    }

    public Instant getMortgageDate() {
        return this.mortgageDate;
    }

    public LoanApplications mortgageDate(Instant mortgageDate) {
        this.setMortgageDate(mortgageDate);
        return this;
    }

    public void setMortgageDate(Instant mortgageDate) {
        this.mortgageDate = mortgageDate;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public LoanApplications createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Double getExtentMorgageValue() {
        return this.extentMorgageValue;
    }

    public LoanApplications extentMorgageValue(Double extentMorgageValue) {
        this.setExtentMorgageValue(extentMorgageValue);
        return this;
    }

    public void setExtentMorgageValue(Double extentMorgageValue) {
        this.extentMorgageValue = extentMorgageValue;
    }

    public Double getDownPaymentAmt() {
        return this.downPaymentAmt;
    }

    public LoanApplications downPaymentAmt(Double downPaymentAmt) {
        this.setDownPaymentAmt(downPaymentAmt);
        return this;
    }

    public void setDownPaymentAmt(Double downPaymentAmt) {
        this.downPaymentAmt = downPaymentAmt;
    }

    public Double getLtvRatio() {
        return this.ltvRatio;
    }

    public LoanApplications ltvRatio(Double ltvRatio) {
        this.setLtvRatio(ltvRatio);
        return this;
    }

    public void setLtvRatio(Double ltvRatio) {
        this.ltvRatio = ltvRatio;
    }

    public Double getProcessingFee() {
        return this.processingFee;
    }

    public LoanApplications processingFee(Double processingFee) {
        this.setProcessingFee(processingFee);
        return this;
    }

    public void setProcessingFee(Double processingFee) {
        this.processingFee = processingFee;
    }

    public Double getPenalInterest() {
        return this.penalInterest;
    }

    public LoanApplications penalInterest(Double penalInterest) {
        this.setPenalInterest(penalInterest);
        return this;
    }

    public void setPenalInterest(Double penalInterest) {
        this.penalInterest = penalInterest;
    }

    public String getMoratorium() {
        return this.moratorium;
    }

    public LoanApplications moratorium(String moratorium) {
        this.setMoratorium(moratorium);
        return this;
    }

    public void setMoratorium(String moratorium) {
        this.moratorium = moratorium;
    }

    public Double getRoi() {
        return this.roi;
    }

    public LoanApplications roi(Double roi) {
        this.setRoi(roi);
        return this;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }

    public Double getCommityAmt() {
        return this.commityAmt;
    }

    public LoanApplications commityAmt(Double commityAmt) {
        this.setCommityAmt(commityAmt);
        return this;
    }

    public void setCommityAmt(Double commityAmt) {
        this.commityAmt = commityAmt;
    }

    public Double getCommityRoi() {
        return this.commityRoi;
    }

    public LoanApplications commityRoi(Double commityRoi) {
        this.setCommityRoi(commityRoi);
        return this;
    }

    public void setCommityRoi(Double commityRoi) {
        this.commityRoi = commityRoi;
    }

    public Double getSanctionAmt() {
        return this.sanctionAmt;
    }

    public LoanApplications sanctionAmt(Double sanctionAmt) {
        this.setSanctionAmt(sanctionAmt);
        return this;
    }

    public void setSanctionAmt(Double sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public Double getSanctionRoi() {
        return this.sanctionRoi;
    }

    public LoanApplications sanctionRoi(Double sanctionRoi) {
        this.setSanctionRoi(sanctionRoi);
        return this;
    }

    public void setSanctionRoi(Double sanctionRoi) {
        this.sanctionRoi = sanctionRoi;
    }

    public String getYear() {
        return this.year;
    }

    public LoanApplications year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRemark() {
        return this.remark;
    }

    public LoanApplications remark(String remark) {
        this.setRemark(remark);
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public SecurityUser getAssignedTo() {
        return this.assignedTo;
    }

    public LoanApplications assignedTo(SecurityUser assignedTo) {
        this.setAssignedTo(assignedTo);
        return this;
    }

    public void setAssignedTo(SecurityUser assignedTo) {
        this.assignedTo = assignedTo;
    }

    public SecurityUser getAssignedFrom() {
        return this.assignedFrom;
    }

    public LoanApplications assignedFrom(SecurityUser assignedFrom) {
        this.setAssignedFrom(assignedFrom);
        return this;
    }

    public void setAssignedFrom(SecurityUser assignedFrom) {
        this.assignedFrom = assignedFrom;
    }

    public String getSecurityDocUrl() {
        return this.securityDocUrl;
    }

    public LoanApplications securityDocUrl(String securityDocUrl) {
        this.setSecurityDocUrl(securityDocUrl);
        return this;
    }

    public void setSecurityDocUrl(String securityDocUrl) {
        this.securityDocUrl = securityDocUrl;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public LoanApplications lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public LoanApplications lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getMargin() {
        return this.margin;
    }

    public LoanApplications margin(String margin) {
        this.setMargin(margin);
        return this;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getSecurityOffered() {
        return this.securityOffered;
    }

    public LoanApplications securityOffered(String securityOffered) {
        this.setSecurityOffered(securityOffered);
        return this;
    }

    public void setSecurityOffered(String securityOffered) {
        this.securityOffered = securityOffered;
    }

    public Instant getDirectorMeetDate() {
        return this.directorMeetDate;
    }

    public LoanApplications directorMeetDate(Instant directorMeetDate) {
        this.setDirectorMeetDate(directorMeetDate);
        return this;
    }

    public void setDirectorMeetDate(Instant directorMeetDate) {
        this.directorMeetDate = directorMeetDate;
    }

    public Instant getCommityMeetDate() {
        return this.commityMeetDate;
    }

    public LoanApplications commityMeetDate(Instant commityMeetDate) {
        this.setCommityMeetDate(commityMeetDate);
        return this;
    }

    public void setCommityMeetDate(Instant commityMeetDate) {
        this.commityMeetDate = commityMeetDate;
    }

    public Double getDirectorAmount() {
        return this.directorAmount;
    }

    public LoanApplications directorAmount(Double directorAmount) {
        this.setDirectorAmount(directorAmount);
        return this;
    }

    public void setDirectorAmount(Double directorAmount) {
        this.directorAmount = directorAmount;
    }

    public Double getDirectorRoi() {
        return this.directorRoi;
    }

    public LoanApplications directorRoi(Double directorRoi) {
        this.setDirectorRoi(directorRoi);
        return this;
    }

    public void setDirectorRoi(Double directorRoi) {
        this.directorRoi = directorRoi;
    }

    public Instant getSanctionDate() {
        return this.sanctionDate;
    }

    public LoanApplications sanctionDate(Instant sanctionDate) {
        this.setSanctionDate(sanctionDate);
        return this;
    }

    public void setSanctionDate(Instant sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public Long getMemberId() {
        return this.memberId;
    }

    public LoanApplications memberId(Long memberId) {
        this.setMemberId(memberId);
        return this;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public LoanApplications securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LoanApplications product(Product product) {
        this.setProduct(product);
        return this;
    }

    public Branch getBranch() {
        return this.branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public LoanApplications branch(Branch branch) {
        this.setBranch(branch);
        return this;
    }

    public Boolean getHasSubmitLegal() {
        return this.hasSubmitLegal;
    }

    public LoanApplications hasSubmitLegal(Boolean hasSubmitLegal) {
        this.setHasSubmitLegal(hasSubmitLegal);
        return this;
    }

    public void setHasSubmitLegal(Boolean hasSubmitLegal) {
        this.hasSubmitLegal = hasSubmitLegal;
    }

    public Boolean getHasReceiveLegal() {
        return this.hasReceiveLegal;
    }

    public LoanApplications hasReceiveLegal(Boolean hasReceiveLegal) {
        this.setHasReceiveLegal(hasReceiveLegal);
        return this;
    }

    public void setHasReceiveLegal(Boolean hasReceiveLegal) {
        this.hasReceiveLegal = hasReceiveLegal;
    }

    public Boolean getHasRequiredDirRec() {
        return this.hasRequiredDirRec;
    }

    public LoanApplications hasRequiredDirRec(Boolean hasRequiredDirRec) {
        this.setHasRequiredDirRec(hasRequiredDirRec);
        return this;
    }

    public void setHasRequiredDirRec(Boolean hasRequiredDirRec) {
        this.hasRequiredDirRec = hasRequiredDirRec;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanApplications)) {
            return false;
        }
        return id != null && id.equals(((LoanApplications) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanApplications{" +
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
            ", sanctionAmt=" + getSanctionAmt() +
            ", sannctionRoi=" + getSanctionRoi() +
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
            ", directorAmount='" + getDirectorAmount() + "'" +
            ", commityMeetDate='" + getCommityMeetDate() + "'" +
            ", directorRoi='" + getDirectorRoi() + "'" +
            ", sanctionDate='" + getSanctionDate() + "'" +
            ", memberId='" + getMemberId() + "'" +
            ", hasSubmitLegal='" + getHasSubmitLegal() + "'" +
            ", hasRequiredDirRec='" + getHasRequiredDirRec() + "'" +
            "}";
    }
}
