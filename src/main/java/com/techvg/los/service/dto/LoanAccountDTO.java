package com.techvg.los.service.dto;

import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.domain.enumeration.NpaClassification;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.LoanAccount} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanAccountDTO implements Serializable {

    private Long id;

    private Double loanAmount;

    private String loanAccountNo;

    private LoanStatus status;

    private Instant loanStartDate;

    private Instant loanEndDate;

    private Instant disbursementDate;

    private Boolean disbursementAllowance;

    private Instant loanPlannedClosureDate;

    private Instant loanCloserDate;

    private Instant emiStartDate;

    private NpaClassification loanNpaClass;

    private String parentAccHeadCode;

    private String loanAccountName;

    private Double disbursementAmt;

    private String disbursementStatus;

    private String repaymentPeriod;

    private String year;

    private Double processingFee;

    private String moratorium;

    private Double roi;

    private Instant lastModified;

    private String lastModifiedBy;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private String freeField5;

    private String freeField6;

    private Long loanApplicationsId;

    private Long memberId;

    private ProductDTO product;

    private BranchDTO branch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanAccountNo() {
        return loanAccountNo;
    }

    public void setLoanAccountNo(String loanAccountNo) {
        this.loanAccountNo = loanAccountNo;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public Instant getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(Instant loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public Instant getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(Instant loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public Instant getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(Instant disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public Boolean getDisbursementAllowance() {
        return disbursementAllowance;
    }

    public void setDisbursementAllowance(Boolean disbursementAllowance) {
        this.disbursementAllowance = disbursementAllowance;
    }

    public Instant getLoanPlannedClosureDate() {
        return loanPlannedClosureDate;
    }

    public void setLoanPlannedClosureDate(Instant loanPlannedClosureDate) {
        this.loanPlannedClosureDate = loanPlannedClosureDate;
    }

    public Instant getLoanCloserDate() {
        return loanCloserDate;
    }

    public void setLoanCloserDate(Instant loanCloserDate) {
        this.loanCloserDate = loanCloserDate;
    }

    public Instant getEmiStartDate() {
        return emiStartDate;
    }

    public void setEmiStartDate(Instant emiStartDate) {
        this.emiStartDate = emiStartDate;
    }

    public NpaClassification getLoanNpaClass() {
        return loanNpaClass;
    }

    public void setLoanNpaClass(NpaClassification loanNpaClass) {
        this.loanNpaClass = loanNpaClass;
    }

    public String getParentAccHeadCode() {
        return parentAccHeadCode;
    }

    public void setParentAccHeadCode(String parentAccHeadCode) {
        this.parentAccHeadCode = parentAccHeadCode;
    }

    public String getLoanAccountName() {
        return loanAccountName;
    }

    public void setLoanAccountName(String loanAccountName) {
        this.loanAccountName = loanAccountName;
    }

    public Double getDisbursementAmt() {
        return disbursementAmt;
    }

    public void setDisbursementAmt(Double disbursementAmt) {
        this.disbursementAmt = disbursementAmt;
    }

    public String getDisbursementStatus() {
        return disbursementStatus;
    }

    public void setDisbursementStatus(String disbursementStatus) {
        this.disbursementStatus = disbursementStatus;
    }

    public String getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public void setRepaymentPeriod(String repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Double getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(Double processingFee) {
        this.processingFee = processingFee;
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

    public String getFreeField1() {
        return freeField1;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return freeField2;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
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

    public Long getLoanApplicationsId() {
        return loanApplicationsId;
    }

    public void setLoanApplicationsId(Long loanApplicationsId) {
        this.loanApplicationsId = loanApplicationsId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanAccountDTO)) {
            return false;
        }

        LoanAccountDTO loanAccountDTO = (LoanAccountDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, loanAccountDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanAccountDTO{" +
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
            ", loanApplicationsId=" + getLoanApplicationsId() +
            ", memberId=" + getMemberId() +
            ", product=" + getProduct() +
            ", branch=" + getBranch() +
            "}";
    }
}
