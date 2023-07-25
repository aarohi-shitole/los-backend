package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.domain.enumeration.NpaClassification;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.LoanAccount} entity. This class is used
 * in {@link com.techvg.los.web.rest.LoanAccountResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /loan-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanAccountCriteria implements Serializable, Criteria {

    /**
     * Class for filtering LoanStatus
     */
    public static class LoanStatusFilter extends Filter<LoanStatus> {

        public LoanStatusFilter() {}

        public LoanStatusFilter(LoanStatusFilter filter) {
            super(filter);
        }

        @Override
        public LoanStatusFilter copy() {
            return new LoanStatusFilter(this);
        }
    }

    /**
     * Class for filtering NpaClassification
     */
    public static class NpaClassificationFilter extends Filter<NpaClassification> {

        public NpaClassificationFilter() {}

        public NpaClassificationFilter(NpaClassificationFilter filter) {
            super(filter);
        }

        @Override
        public NpaClassificationFilter copy() {
            return new NpaClassificationFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter loanAmount;

    private StringFilter loanAccountNo;

    private LoanStatusFilter status;

    private InstantFilter loanStartDate;

    private InstantFilter loanEndDate;

    private InstantFilter disbursementDate;

    private BooleanFilter disbursementAllowance;

    private InstantFilter loanPlannedClosureDate;

    private InstantFilter loanCloserDate;

    private InstantFilter emiStartDate;

    private NpaClassificationFilter loanNpaClass;

    private StringFilter parentAccHeadCode;

    private StringFilter loanAccountName;

    private DoubleFilter disbursementAmt;

    private StringFilter disbursementStatus;

    private StringFilter repaymentPeriod;

    private StringFilter year;

    private DoubleFilter processingFee;

    private StringFilter moratorium;

    private DoubleFilter roi;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter freeField5;

    private StringFilter freeField6;

    private LongFilter loanApplicationsId;

    private LongFilter memberId;

    private LongFilter productId;

    private LongFilter branchId;

    private Boolean distinct;

    public LoanAccountCriteria() {}

    public LoanAccountCriteria(LoanAccountCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.loanAmount = other.loanAmount == null ? null : other.loanAmount.copy();
        this.loanAccountNo = other.loanAccountNo == null ? null : other.loanAccountNo.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.loanStartDate = other.loanStartDate == null ? null : other.loanStartDate.copy();
        this.loanEndDate = other.loanEndDate == null ? null : other.loanEndDate.copy();
        this.disbursementDate = other.disbursementDate == null ? null : other.disbursementDate.copy();
        this.disbursementAllowance = other.disbursementAllowance == null ? null : other.disbursementAllowance.copy();
        this.loanPlannedClosureDate = other.loanPlannedClosureDate == null ? null : other.loanPlannedClosureDate.copy();
        this.loanCloserDate = other.loanCloserDate == null ? null : other.loanCloserDate.copy();
        this.emiStartDate = other.emiStartDate == null ? null : other.emiStartDate.copy();
        this.loanNpaClass = other.loanNpaClass == null ? null : other.loanNpaClass.copy();
        this.parentAccHeadCode = other.parentAccHeadCode == null ? null : other.parentAccHeadCode.copy();
        this.loanAccountName = other.loanAccountName == null ? null : other.loanAccountName.copy();
        this.disbursementAmt = other.disbursementAmt == null ? null : other.disbursementAmt.copy();
        this.disbursementStatus = other.disbursementStatus == null ? null : other.disbursementStatus.copy();
        this.repaymentPeriod = other.repaymentPeriod == null ? null : other.repaymentPeriod.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.processingFee = other.processingFee == null ? null : other.processingFee.copy();
        this.moratorium = other.moratorium == null ? null : other.moratorium.copy();
        this.roi = other.roi == null ? null : other.roi.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.freeField6 = other.freeField6 == null ? null : other.freeField6.copy();
        this.loanApplicationsId = other.loanApplicationsId == null ? null : other.loanApplicationsId.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.branchId = other.branchId == null ? null : other.branchId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LoanAccountCriteria copy() {
        return new LoanAccountCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getLoanAmount() {
        return loanAmount;
    }

    public DoubleFilter loanAmount() {
        if (loanAmount == null) {
            loanAmount = new DoubleFilter();
        }
        return loanAmount;
    }

    public void setLoanAmount(DoubleFilter loanAmount) {
        this.loanAmount = loanAmount;
    }

    public StringFilter getLoanAccountNo() {
        return loanAccountNo;
    }

    public StringFilter loanAccountNo() {
        if (loanAccountNo == null) {
            loanAccountNo = new StringFilter();
        }
        return loanAccountNo;
    }

    public void setLoanAccountNo(StringFilter loanAccountNo) {
        this.loanAccountNo = loanAccountNo;
    }

    public LoanStatusFilter getStatus() {
        return status;
    }

    public LoanStatusFilter status() {
        if (status == null) {
            status = new LoanStatusFilter();
        }
        return status;
    }

    public void setStatus(LoanStatusFilter status) {
        this.status = status;
    }

    public InstantFilter getLoanStartDate() {
        return loanStartDate;
    }

    public InstantFilter loanStartDate() {
        if (loanStartDate == null) {
            loanStartDate = new InstantFilter();
        }
        return loanStartDate;
    }

    public void setLoanStartDate(InstantFilter loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public InstantFilter getLoanEndDate() {
        return loanEndDate;
    }

    public InstantFilter loanEndDate() {
        if (loanEndDate == null) {
            loanEndDate = new InstantFilter();
        }
        return loanEndDate;
    }

    public void setLoanEndDate(InstantFilter loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public InstantFilter getDisbursementDate() {
        return disbursementDate;
    }

    public InstantFilter disbursementDate() {
        if (disbursementDate == null) {
            disbursementDate = new InstantFilter();
        }
        return disbursementDate;
    }

    public void setDisbursementDate(InstantFilter disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public BooleanFilter getDisbursementAllowance() {
        return disbursementAllowance;
    }

    public BooleanFilter disbursementAllowance() {
        if (disbursementAllowance == null) {
            disbursementAllowance = new BooleanFilter();
        }
        return disbursementAllowance;
    }

    public void setDisbursementAllowance(BooleanFilter disbursementAllowance) {
        this.disbursementAllowance = disbursementAllowance;
    }

    public InstantFilter getLoanPlannedClosureDate() {
        return loanPlannedClosureDate;
    }

    public InstantFilter loanPlannedClosureDate() {
        if (loanPlannedClosureDate == null) {
            loanPlannedClosureDate = new InstantFilter();
        }
        return loanPlannedClosureDate;
    }

    public void setLoanPlannedClosureDate(InstantFilter loanPlannedClosureDate) {
        this.loanPlannedClosureDate = loanPlannedClosureDate;
    }

    public InstantFilter getLoanCloserDate() {
        return loanCloserDate;
    }

    public InstantFilter loanCloserDate() {
        if (loanCloserDate == null) {
            loanCloserDate = new InstantFilter();
        }
        return loanCloserDate;
    }

    public void setLoanCloserDate(InstantFilter loanCloserDate) {
        this.loanCloserDate = loanCloserDate;
    }

    public InstantFilter getEmiStartDate() {
        return emiStartDate;
    }

    public InstantFilter emiStartDate() {
        if (emiStartDate == null) {
            emiStartDate = new InstantFilter();
        }
        return emiStartDate;
    }

    public void setEmiStartDate(InstantFilter emiStartDate) {
        this.emiStartDate = emiStartDate;
    }

    public NpaClassificationFilter getLoanNpaClass() {
        return loanNpaClass;
    }

    public NpaClassificationFilter loanNpaClass() {
        if (loanNpaClass == null) {
            loanNpaClass = new NpaClassificationFilter();
        }
        return loanNpaClass;
    }

    public void setLoanNpaClass(NpaClassificationFilter loanNpaClass) {
        this.loanNpaClass = loanNpaClass;
    }

    public StringFilter getParentAccHeadCode() {
        return parentAccHeadCode;
    }

    public StringFilter parentAccHeadCode() {
        if (parentAccHeadCode == null) {
            parentAccHeadCode = new StringFilter();
        }
        return parentAccHeadCode;
    }

    public void setParentAccHeadCode(StringFilter parentAccHeadCode) {
        this.parentAccHeadCode = parentAccHeadCode;
    }

    public StringFilter getLoanAccountName() {
        return loanAccountName;
    }

    public StringFilter loanAccountName() {
        if (loanAccountName == null) {
            loanAccountName = new StringFilter();
        }
        return loanAccountName;
    }

    public void setLoanAccountName(StringFilter loanAccountName) {
        this.loanAccountName = loanAccountName;
    }

    public DoubleFilter getDisbursementAmt() {
        return disbursementAmt;
    }

    public DoubleFilter disbursementAmt() {
        if (disbursementAmt == null) {
            disbursementAmt = new DoubleFilter();
        }
        return disbursementAmt;
    }

    public void setDisbursementAmt(DoubleFilter disbursementAmt) {
        this.disbursementAmt = disbursementAmt;
    }

    public StringFilter getDisbursementStatus() {
        return disbursementStatus;
    }

    public StringFilter disbursementStatus() {
        if (disbursementStatus == null) {
            disbursementStatus = new StringFilter();
        }
        return disbursementStatus;
    }

    public void setDisbursementStatus(StringFilter disbursementStatus) {
        this.disbursementStatus = disbursementStatus;
    }

    public StringFilter getRepaymentPeriod() {
        return repaymentPeriod;
    }

    public StringFilter repaymentPeriod() {
        if (repaymentPeriod == null) {
            repaymentPeriod = new StringFilter();
        }
        return repaymentPeriod;
    }

    public void setRepaymentPeriod(StringFilter repaymentPeriod) {
        this.repaymentPeriod = repaymentPeriod;
    }

    public StringFilter getYear() {
        return year;
    }

    public StringFilter year() {
        if (year == null) {
            year = new StringFilter();
        }
        return year;
    }

    public void setYear(StringFilter year) {
        this.year = year;
    }

    public DoubleFilter getProcessingFee() {
        return processingFee;
    }

    public DoubleFilter processingFee() {
        if (processingFee == null) {
            processingFee = new DoubleFilter();
        }
        return processingFee;
    }

    public void setProcessingFee(DoubleFilter processingFee) {
        this.processingFee = processingFee;
    }

    public StringFilter getMoratorium() {
        return moratorium;
    }

    public StringFilter moratorium() {
        if (moratorium == null) {
            moratorium = new StringFilter();
        }
        return moratorium;
    }

    public void setMoratorium(StringFilter moratorium) {
        this.moratorium = moratorium;
    }

    public DoubleFilter getRoi() {
        return roi;
    }

    public DoubleFilter roi() {
        if (roi == null) {
            roi = new DoubleFilter();
        }
        return roi;
    }

    public void setRoi(DoubleFilter roi) {
        this.roi = roi;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public StringFilter getFreeField1() {
        return freeField1;
    }

    public StringFilter freeField1() {
        if (freeField1 == null) {
            freeField1 = new StringFilter();
        }
        return freeField1;
    }

    public void setFreeField1(StringFilter freeField1) {
        this.freeField1 = freeField1;
    }

    public StringFilter getFreeField2() {
        return freeField2;
    }

    public StringFilter freeField2() {
        if (freeField2 == null) {
            freeField2 = new StringFilter();
        }
        return freeField2;
    }

    public void setFreeField2(StringFilter freeField2) {
        this.freeField2 = freeField2;
    }

    public StringFilter getFreeField3() {
        return freeField3;
    }

    public StringFilter freeField3() {
        if (freeField3 == null) {
            freeField3 = new StringFilter();
        }
        return freeField3;
    }

    public void setFreeField3(StringFilter freeField3) {
        this.freeField3 = freeField3;
    }

    public StringFilter getFreeField4() {
        return freeField4;
    }

    public StringFilter freeField4() {
        if (freeField4 == null) {
            freeField4 = new StringFilter();
        }
        return freeField4;
    }

    public void setFreeField4(StringFilter freeField4) {
        this.freeField4 = freeField4;
    }

    public StringFilter getFreeField5() {
        return freeField5;
    }

    public StringFilter freeField5() {
        if (freeField5 == null) {
            freeField5 = new StringFilter();
        }
        return freeField5;
    }

    public void setFreeField5(StringFilter freeField5) {
        this.freeField5 = freeField5;
    }

    public StringFilter getFreeField6() {
        return freeField6;
    }

    public StringFilter freeField6() {
        if (freeField6 == null) {
            freeField6 = new StringFilter();
        }
        return freeField6;
    }

    public void setFreeField6(StringFilter freeField6) {
        this.freeField6 = freeField6;
    }

    public LongFilter getLoanApplicationsId() {
        return loanApplicationsId;
    }

    public LongFilter loanApplicationsId() {
        if (loanApplicationsId == null) {
            loanApplicationsId = new LongFilter();
        }
        return loanApplicationsId;
    }

    public void setLoanApplicationsId(LongFilter loanApplicationsId) {
        this.loanApplicationsId = loanApplicationsId;
    }

    public LongFilter getMemberId() {
        return memberId;
    }

    public LongFilter memberId() {
        if (memberId == null) {
            memberId = new LongFilter();
        }
        return memberId;
    }

    public void setMemberId(LongFilter memberId) {
        this.memberId = memberId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public LongFilter productId() {
        if (productId == null) {
            productId = new LongFilter();
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getBranchId() {
        return branchId;
    }

    public LongFilter branchId() {
        if (branchId == null) {
            branchId = new LongFilter();
        }
        return branchId;
    }

    public void setBranchId(LongFilter branchId) {
        this.branchId = branchId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LoanAccountCriteria that = (LoanAccountCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(loanAmount, that.loanAmount) &&
            Objects.equals(loanAccountNo, that.loanAccountNo) &&
            Objects.equals(status, that.status) &&
            Objects.equals(loanStartDate, that.loanStartDate) &&
            Objects.equals(loanEndDate, that.loanEndDate) &&
            Objects.equals(disbursementDate, that.disbursementDate) &&
            Objects.equals(disbursementAllowance, that.disbursementAllowance) &&
            Objects.equals(loanPlannedClosureDate, that.loanPlannedClosureDate) &&
            Objects.equals(loanCloserDate, that.loanCloserDate) &&
            Objects.equals(emiStartDate, that.emiStartDate) &&
            Objects.equals(loanNpaClass, that.loanNpaClass) &&
            Objects.equals(parentAccHeadCode, that.parentAccHeadCode) &&
            Objects.equals(loanAccountName, that.loanAccountName) &&
            Objects.equals(disbursementAmt, that.disbursementAmt) &&
            Objects.equals(disbursementStatus, that.disbursementStatus) &&
            Objects.equals(repaymentPeriod, that.repaymentPeriod) &&
            Objects.equals(year, that.year) &&
            Objects.equals(processingFee, that.processingFee) &&
            Objects.equals(moratorium, that.moratorium) &&
            Objects.equals(roi, that.roi) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(freeField6, that.freeField6) &&
            Objects.equals(loanApplicationsId, that.loanApplicationsId) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(branchId, that.branchId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            loanAmount,
            loanAccountNo,
            status,
            loanStartDate,
            loanEndDate,
            disbursementDate,
            disbursementAllowance,
            loanPlannedClosureDate,
            loanCloserDate,
            emiStartDate,
            loanNpaClass,
            parentAccHeadCode,
            loanAccountName,
            disbursementAmt,
            disbursementStatus,
            repaymentPeriod,
            year,
            processingFee,
            moratorium,
            roi,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            freeField5,
            freeField6,
            loanApplicationsId,
            memberId,
            productId,
            branchId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanAccountCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (loanAmount != null ? "loanAmount=" + loanAmount + ", " : "") +
            (loanAccountNo != null ? "loanAccountNo=" + loanAccountNo + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (loanStartDate != null ? "loanStartDate=" + loanStartDate + ", " : "") +
            (loanEndDate != null ? "loanEndDate=" + loanEndDate + ", " : "") +
            (disbursementDate != null ? "disbursementDate=" + disbursementDate + ", " : "") +
            (disbursementAllowance != null ? "disbursementAllowance=" + disbursementAllowance + ", " : "") +
            (loanPlannedClosureDate != null ? "loanPlannedClosureDate=" + loanPlannedClosureDate + ", " : "") +
            (loanCloserDate != null ? "loanCloserDate=" + loanCloserDate + ", " : "") +
            (emiStartDate != null ? "emiStartDate=" + emiStartDate + ", " : "") +
            (loanNpaClass != null ? "loanNpaClass=" + loanNpaClass + ", " : "") +
            (parentAccHeadCode != null ? "parentAccHeadCode=" + parentAccHeadCode + ", " : "") +
            (loanAccountName != null ? "loanAccountName=" + loanAccountName + ", " : "") +
            (disbursementAmt != null ? "disbursementAmt=" + disbursementAmt + ", " : "") +
            (disbursementStatus != null ? "disbursementStatus=" + disbursementStatus + ", " : "") +
            (repaymentPeriod != null ? "repaymentPeriod=" + repaymentPeriod + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (processingFee != null ? "processingFee=" + processingFee + ", " : "") +
            (moratorium != null ? "moratorium=" + moratorium + ", " : "") +
            (roi != null ? "roi=" + roi + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (freeField5 != null ? "freeField5=" + freeField5 + ", " : "") +
            (freeField6 != null ? "freeField6=" + freeField6 + ", " : "") +
            (loanApplicationsId != null ? "loanApplicationsId=" + loanApplicationsId + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (branchId != null ? "branchId=" + branchId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
