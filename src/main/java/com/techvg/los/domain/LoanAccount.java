package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.domain.enumeration.NpaClassification;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LoanAccount.
 */
@Entity
@Table(name = "loan_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "loan_amount")
    private Double loanAmount;

    @Column(name = "loan_account_no")
    private String loanAccountNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LoanStatus status;

    @Column(name = "loan_start_date")
    private Instant loanStartDate;

    @Column(name = "loan_end_date")
    private Instant loanEndDate;

    @Column(name = "disbursement_date")
    private Instant disbursementDate;

    @Column(name = "disbursement_allowance")
    private Boolean disbursementAllowance;

    @Column(name = "loan_planned_closure_date")
    private Instant loanPlannedClosureDate;

    @Column(name = "loan_closer_date")
    private Instant loanCloserDate;

    @Column(name = "emi_start_date")
    private Instant emiStartDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_npa_class")
    private NpaClassification loanNpaClass;

    @Column(name = "parent_acc_head_code")
    private String parentAccHeadCode;

    @Column(name = "loan_account_name")
    private String loanAccountName;

    @Column(name = "disbursement_amt")
    private Double disbursementAmt;

    @Column(name = "disbursement_status")
    private String disbursementStatus;

    @Column(name = "repayment_period")
    private String repaymentPeriod;

    @Column(name = "year")
    private String year;

    @Column(name = "processing_fee")
    private Double processingFee;

    @Column(name = "moratorium")
    private String moratorium;

    @Column(name = "roi")
    private Double roi;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    @Column(name = "free_field_5")
    private String freeField5;

    @Column(name = "free_field_6")
    private String freeField6;

    @JsonIgnoreProperties(value = { "member", "securityUser", "product" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private LoanApplications loanApplications;

    @ManyToOne
    @JsonIgnoreProperties(value = { "enquiryDetails", "branch", "member", "securityUser" }, allowSetters = true)
    private Member member;

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

    public LoanAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLoanAmount() {
        return this.loanAmount;
    }

    public LoanAccount loanAmount(Double loanAmount) {
        this.setLoanAmount(loanAmount);
        return this;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanAccountNo() {
        return this.loanAccountNo;
    }

    public LoanAccount loanAccountNo(String loanAccountNo) {
        this.setLoanAccountNo(loanAccountNo);
        return this;
    }

    public void setLoanAccountNo(String loanAccountNo) {
        this.loanAccountNo = loanAccountNo;
    }

    public LoanStatus getStatus() {
        return this.status;
    }

    public LoanAccount status(LoanStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public Instant getLoanStartDate() {
        return this.loanStartDate;
    }

    public LoanAccount loanStartDate(Instant loanStartDate) {
        this.setLoanStartDate(loanStartDate);
        return this;
    }

    public void setLoanStartDate(Instant loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public Instant getLoanEndDate() {
        return this.loanEndDate;
    }

    public LoanAccount loanEndDate(Instant loanEndDate) {
        this.setLoanEndDate(loanEndDate);
        return this;
    }

    public void setLoanEndDate(Instant loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public Instant getDisbursementDate() {
        return this.disbursementDate;
    }

    public LoanAccount disbursementDate(Instant disbursementDate) {
        this.setDisbursementDate(disbursementDate);
        return this;
    }

    public void setDisbursementDate(Instant disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public Boolean getDisbursementAllowance() {
        return this.disbursementAllowance;
    }

    public LoanAccount disbursementAllowance(Boolean disbursementAllowance) {
        this.setDisbursementAllowance(disbursementAllowance);
        return this;
    }

    public void setDisbursementAllowance(Boolean disbursementAllowance) {
        this.disbursementAllowance = disbursementAllowance;
    }

    public Instant getLoanPlannedClosureDate() {
        return this.loanPlannedClosureDate;
    }

    public LoanAccount loanPlannedClosureDate(Instant loanPlannedClosureDate) {
        this.setLoanPlannedClosureDate(loanPlannedClosureDate);
        return this;
    }

    public void setLoanPlannedClosureDate(Instant loanPlannedClosureDate) {
        this.loanPlannedClosureDate = loanPlannedClosureDate;
    }

    public Instant getLoanCloserDate() {
        return this.loanCloserDate;
    }

    public LoanAccount loanCloserDate(Instant loanCloserDate) {
        this.setLoanCloserDate(loanCloserDate);
        return this;
    }

    public void setLoanCloserDate(Instant loanCloserDate) {
        this.loanCloserDate = loanCloserDate;
    }

    public Instant getEmiStartDate() {
        return this.emiStartDate;
    }

    public LoanAccount emiStartDate(Instant emiStartDate) {
        this.setEmiStartDate(emiStartDate);
        return this;
    }

    public void setEmiStartDate(Instant emiStartDate) {
        this.emiStartDate = emiStartDate;
    }

    public NpaClassification getLoanNpaClass() {
        return this.loanNpaClass;
    }

    public LoanAccount loanNpaClass(NpaClassification loanNpaClass) {
        this.setLoanNpaClass(loanNpaClass);
        return this;
    }

    public void setLoanNpaClass(NpaClassification loanNpaClass) {
        this.loanNpaClass = loanNpaClass;
    }

    public String getParentAccHeadCode() {
        return this.parentAccHeadCode;
    }

    public LoanAccount parentAccHeadCode(String parentAccHeadCode) {
        this.setParentAccHeadCode(parentAccHeadCode);
        return this;
    }

    public void setParentAccHeadCode(String parentAccHeadCode) {
        this.parentAccHeadCode = parentAccHeadCode;
    }

    public String getLoanAccountName() {
        return this.loanAccountName;
    }

    public LoanAccount loanAccountName(String loanAccountName) {
        this.setLoanAccountName(loanAccountName);
        return this;
    }

    public void setLoanAccountName(String loanAccountName) {
        this.loanAccountName = loanAccountName;
    }

    public Double getDisbursementAmt() {
        return this.disbursementAmt;
    }

    public LoanAccount disbursementAmt(Double disbursementAmt) {
        this.setDisbursementAmt(disbursementAmt);
        return this;
    }

    public void setDisbursementAmt(Double disbursementAmt) {
        this.disbursementAmt = disbursementAmt;
    }

    public String getDisbursementStatus() {
        return this.disbursementStatus;
    }

    public LoanAccount disbursementStatus(String disbursementStatus) {
        this.setDisbursementStatus(disbursementStatus);
        return this;
    }

    public void setDisbursementStatus(String disbursementStatus) {
        this.disbursementStatus = disbursementStatus;
    }

    public String getRepaymentPeriod() {
        return this.repaymentPeriod;
    }

    public LoanAccount repaymentPeriod(String repaymentPeriod) {
        this.setRepaymentPeriod(repaymentPeriod);
        return this;
    }

    public void setRepaymentPeriod(String repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    public String getYear() {
        return this.year;
    }

    public LoanAccount year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Double getProcessingFee() {
        return this.processingFee;
    }

    public LoanAccount processingFee(Double processingFee) {
        this.setProcessingFee(processingFee);
        return this;
    }

    public void setProcessingFee(Double processingFee) {
        this.processingFee = processingFee;
    }

    public String getMoratorium() {
        return this.moratorium;
    }

    public LoanAccount moratorium(String moratorium) {
        this.setMoratorium(moratorium);
        return this;
    }

    public void setMoratorium(String moratorium) {
        this.moratorium = moratorium;
    }

    public Double getRoi() {
        return this.roi;
    }

    public LoanAccount roi(Double roi) {
        this.setRoi(roi);
        return this;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public LoanAccount lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public LoanAccount lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public LoanAccount freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public LoanAccount freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public LoanAccount freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public LoanAccount freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public LoanAccount freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getFreeField6() {
        return this.freeField6;
    }

    public LoanAccount freeField6(String freeField6) {
        this.setFreeField6(freeField6);
        return this;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    public LoanApplications getLoanApplications() {
        return this.loanApplications;
    }

    public void setLoanApplications(LoanApplications loanApplications) {
        this.loanApplications = loanApplications;
    }

    public LoanAccount loanApplications(LoanApplications loanApplications) {
        this.setLoanApplications(loanApplications);
        return this;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LoanAccount member(Member member) {
        this.setMember(member);
        return this;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LoanAccount product(Product product) {
        this.setProduct(product);
        return this;
    }

    public Branch getBranch() {
        return this.branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public LoanAccount branch(Branch branch) {
        this.setBranch(branch);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanAccount)) {
            return false;
        }
        return id != null && id.equals(((LoanAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanAccount{" +
            "id=" + getId() +
            ", loanAmount=" + getLoanAmount() +
            ", loanAccountNo='" + getLoanAccountNo() + "'" +
            ", status='" + getStatus() + "'" +
            ", loanStartDate='" + getLoanStartDate() + "'" +
            ", loanEndDate='" + getLoanEndDate() + "'" +
            ", disbursementDate='" + getDisbursementDate() + "'" +
            ", disbursementAllowance='" + getDisbursementAllowance() + "'" +
            ", loanPlannedClosureDate='" + getLoanPlannedClosureDate() + "'" +
            ", loanCloserDate='" + getLoanCloserDate() + "'" +
            ", emiStartDate='" + getEmiStartDate() + "'" +
            ", loanNpaClass='" + getLoanNpaClass() + "'" +
            ", parentAccHeadCode='" + getParentAccHeadCode() + "'" +
            ", loanAccountName='" + getLoanAccountName() + "'" +
            ", disbursementAmt=" + getDisbursementAmt() +
            ", disbursementStatus='" + getDisbursementStatus() + "'" +
            ", repaymentPeriod='" + getRepaymentPeriod() + "'" +
            ", year='" + getYear() + "'" +
            ", processingFee=" + getProcessingFee() +
            ", moratorium='" + getMoratorium() + "'" +
            ", roi=" + getRoi() +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", freeField5='" + getFreeField5() + "'" +
            ", freeField6='" + getFreeField6() + "'" +
            "}";
    }
}
