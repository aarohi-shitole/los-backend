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
@Table(name = "loan_applications_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanApplicationsHistory implements Serializable {

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

    @Column(name = "mortgage_date")
    private Instant mortgageDate;

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

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    @Column(name = "free_field_5")
    private String freeField5;

    @Column(name = "free_field_6")
    private String freeField6;

    @Column(name = "free_field_7")
    private String freeField7;

    @Column(name = "member_id")
    private Long memberId;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "securityUser", "address", "region", "organisation", "product", "branch" }, allowSetters = true)
    private LoanApplications loanApplications;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LoanApplicationsHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationNo() {
        return this.applicationNo;
    }

    public LoanApplicationsHistory applicationNo(String applicationNo) {
        this.setApplicationNo(applicationNo);
        return this;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Double getDemandAmount() {
        return this.demandAmount;
    }

    public LoanApplicationsHistory demandAmount(Double demandAmount) {
        this.setDemandAmount(demandAmount);
        return this;
    }

    public void setDemandAmount(Double demandAmount) {
        this.demandAmount = demandAmount;
    }

    public String getLoanPurpose() {
        return this.loanPurpose;
    }

    public LoanApplicationsHistory loanPurpose(String loanPurpose) {
        this.setLoanPurpose(loanPurpose);
        return this;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public LoanStatus getStatus() {
        return this.status;
    }

    public LoanApplicationsHistory status(LoanStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public Integer getDemandedLandAreaInHector() {
        return this.demandedLandAreaInHector;
    }

    public LoanApplicationsHistory demandedLandAreaInHector(Integer demandedLandAreaInHector) {
        this.setDemandedLandAreaInHector(demandedLandAreaInHector);
        return this;
    }

    public void setDemandedLandAreaInHector(Integer demandedLandAreaInHector) {
        this.demandedLandAreaInHector = demandedLandAreaInHector;
    }

    public String getSeasonOfCropLoan() {
        return this.seasonOfCropLoan;
    }

    public LoanApplicationsHistory seasonOfCropLoan(String seasonOfCropLoan) {
        this.setSeasonOfCropLoan(seasonOfCropLoan);
        return this;
    }

    public void setSeasonOfCropLoan(String seasonOfCropLoan) {
        this.seasonOfCropLoan = seasonOfCropLoan;
    }

    public StepperNumber getLoanSteps() {
        return this.loanSteps;
    }

    public LoanApplicationsHistory loanSteps(StepperNumber loanSteps) {
        this.setLoanSteps(loanSteps);
        return this;
    }

    public void setLoanSteps(StepperNumber loanSteps) {
        this.loanSteps = loanSteps;
    }

    public Boolean getIsInsured() {
        return this.isInsured;
    }

    public LoanApplicationsHistory isInsured(Boolean isInsured) {
        this.setIsInsured(isInsured);
        return this;
    }

    public void setIsInsured(Boolean isInsured) {
        this.isInsured = isInsured;
    }

    public Double getLoanBenefitingArea() {
        return this.loanBenefitingArea;
    }

    public LoanApplicationsHistory loanBenefitingArea(Double loanBenefitingArea) {
        this.setLoanBenefitingArea(loanBenefitingArea);
        return this;
    }

    public void setLoanBenefitingArea(Double loanBenefitingArea) {
        this.loanBenefitingArea = loanBenefitingArea;
    }

    public Double getCostOfInvestment() {
        return this.costOfInvestment;
    }

    public LoanApplicationsHistory costOfInvestment(Double costOfInvestment) {
        this.setCostOfInvestment(costOfInvestment);
        return this;
    }

    public void setCostOfInvestment(Double costOfInvestment) {
        this.costOfInvestment = costOfInvestment;
    }

    public Long getMortgageDeedNo() {
        return this.mortgageDeedNo;
    }

    public LoanApplicationsHistory mortgageDeedNo(Long mortgageDeedNo) {
        this.setMortgageDeedNo(mortgageDeedNo);
        return this;
    }

    public void setMortgageDeedNo(Long mortgageDeedNo) {
        this.mortgageDeedNo = mortgageDeedNo;
    }

    public Instant getMortgageDate() {
        return this.mortgageDate;
    }

    public LoanApplicationsHistory mortgageDate(Instant mortgageDate) {
        this.setMortgageDate(mortgageDate);
        return this;
    }

    public void setMortgageDate(Instant mortgageDate) {
        this.mortgageDate = mortgageDate;
    }

    public Double getExtentMorgageValue() {
        return this.extentMorgageValue;
    }

    public LoanApplicationsHistory extentMorgageValue(Double extentMorgageValue) {
        this.setExtentMorgageValue(extentMorgageValue);
        return this;
    }

    public void setExtentMorgageValue(Double extentMorgageValue) {
        this.extentMorgageValue = extentMorgageValue;
    }

    public Double getDownPaymentAmt() {
        return this.downPaymentAmt;
    }

    public LoanApplicationsHistory downPaymentAmt(Double downPaymentAmt) {
        this.setDownPaymentAmt(downPaymentAmt);
        return this;
    }

    public void setDownPaymentAmt(Double downPaymentAmt) {
        this.downPaymentAmt = downPaymentAmt;
    }

    public Double getLtvRatio() {
        return this.ltvRatio;
    }

    public LoanApplicationsHistory ltvRatio(Double ltvRatio) {
        this.setLtvRatio(ltvRatio);
        return this;
    }

    public void setLtvRatio(Double ltvRatio) {
        this.ltvRatio = ltvRatio;
    }

    public Double getProcessingFee() {
        return this.processingFee;
    }

    public LoanApplicationsHistory processingFee(Double processingFee) {
        this.setProcessingFee(processingFee);
        return this;
    }

    public void setProcessingFee(Double processingFee) {
        this.processingFee = processingFee;
    }

    public Double getPenalInterest() {
        return this.penalInterest;
    }

    public LoanApplicationsHistory penalInterest(Double penalInterest) {
        this.setPenalInterest(penalInterest);
        return this;
    }

    public void setPenalInterest(Double penalInterest) {
        this.penalInterest = penalInterest;
    }

    public String getMoratorium() {
        return this.moratorium;
    }

    public LoanApplicationsHistory moratorium(String moratorium) {
        this.setMoratorium(moratorium);
        return this;
    }

    public void setMoratorium(String moratorium) {
        this.moratorium = moratorium;
    }

    public Double getRoi() {
        return this.roi;
    }

    public LoanApplicationsHistory roi(Double roi) {
        this.setRoi(roi);
        return this;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }

    public Double getCommityAmt() {
        return this.commityAmt;
    }

    public LoanApplicationsHistory commityAmt(Double commityAmt) {
        this.setCommityAmt(commityAmt);
        return this;
    }

    public void setCommityAmt(Double commityAmt) {
        this.commityAmt = commityAmt;
    }

    public Double getCommityRoi() {
        return this.commityRoi;
    }

    public LoanApplicationsHistory commityRoi(Double commityRoi) {
        this.setCommityRoi(commityRoi);
        return this;
    }

    public void setCommityRoi(Double commityRoi) {
        this.commityRoi = commityRoi;
    }

    public Double getSanctionAmt() {
        return this.sanctionAmt;
    }

    public LoanApplicationsHistory sanctionAmt(Double sanctionAmt) {
        this.setSanctionAmt(sanctionAmt);
        return this;
    }

    public void setSanctionAmt(Double sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public Double getSanctionRoi() {
        return this.sanctionRoi;
    }

    public LoanApplicationsHistory sanctionRoi(Double sanctionRoi) {
        this.setSanctionRoi(sanctionRoi);
        return this;
    }

    public void setSanctionRoi(Double sanctionRoi) {
        this.sanctionRoi = sanctionRoi;
    }

    public String getYear() {
        return this.year;
    }

    public LoanApplicationsHistory year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public SecurityUser getAssignedTo() {
        return this.assignedTo;
    }

    public LoanApplicationsHistory assignedTo(SecurityUser assignedTo) {
        this.setAssignedTo(assignedTo);
        return this;
    }

    public void setAssignedTo(SecurityUser assignedTo) {
        this.assignedTo = assignedTo;
    }

    public SecurityUser getAssignedFrom() {
        return this.assignedFrom;
    }

    public LoanApplicationsHistory assignedFrom(SecurityUser assignedFrom) {
        this.setAssignedFrom(assignedFrom);
        return this;
    }

    public void setAssignedFrom(SecurityUser assignedFrom) {
        this.assignedFrom = assignedFrom;
    }

    public String getSecurityDocUrl() {
        return this.securityDocUrl;
    }

    public LoanApplicationsHistory securityDocUrl(String securityDocUrl) {
        this.setSecurityDocUrl(securityDocUrl);
        return this;
    }

    public void setSecurityDocUrl(String securityDocUrl) {
        this.securityDocUrl = securityDocUrl;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public LoanApplicationsHistory lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public LoanApplicationsHistory lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getMargin() {
        return this.margin;
    }

    public LoanApplicationsHistory margin(String margin) {
        this.setMargin(margin);
        return this;
    }

    public void setMargin(String margin) {
        this.margin = margin;
    }

    public String getSecurityOffered() {
        return this.securityOffered;
    }

    public LoanApplicationsHistory securityOffered(String securityOffered) {
        this.setSecurityOffered(securityOffered);
        return this;
    }

    public void setSecurityOffered(String securityOffered) {
        this.securityOffered = securityOffered;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public LoanApplicationsHistory freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public LoanApplicationsHistory freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public LoanApplicationsHistory freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getFreeField6() {
        return this.freeField6;
    }

    public LoanApplicationsHistory freeField6(String freeField6) {
        this.setFreeField6(freeField6);
        return this;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    public String getFreeField7() {
        return this.freeField7;
    }

    public LoanApplicationsHistory freeField7(String freeField7) {
        this.setFreeField7(freeField7);
        return this;
    }

    public void setFreeField7(String freeField7) {
        this.freeField7 = freeField7;
    }

    //    public Member getMember() {
    //        return this.member;
    //    }
    //
    //    public void setMember(Member member) {
    //        this.member = member;
    //    }
    //
    //    public LoanApplications member(Member member) {
    //        this.setMember(member);
    //        return this;
    //    }

    public Long getMemberId() {
        return this.memberId;
    }

    public LoanApplicationsHistory memberId(Long memberId) {
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

    public LoanApplicationsHistory securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LoanApplicationsHistory product(Product product) {
        this.setProduct(product);
        return this;
    }

    public Branch getBranch() {
        return this.branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public LoanApplicationsHistory branch(Branch branch) {
        this.setBranch(branch);
        return this;
    }

    public LoanApplications getLoanApplications() {
        return this.loanApplications;
    }

    public void setLoanApplications(LoanApplications loanApplications) {
        this.loanApplications = loanApplications;
    }

    public LoanApplicationsHistory loanApplications(LoanApplications loanApplications) {
        this.setLoanApplications(loanApplications);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanApplicationsHistory)) {
            return false;
        }
        return id != null && id.equals(((LoanApplicationsHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
	    @Override
	    public String toString() {
	        return "LoanApplicationsHistory{" +
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
	            ", sanctionAmt=" + getSanctionAmt() +
	            ", sannctionRoi=" + getSanctionRoi() +
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
	            ", memberId='" + getMemberId() + "'" +
	            "}";
	    }
}
