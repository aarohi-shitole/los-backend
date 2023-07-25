package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.PaymentMode;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.LoanRepaymentDetails} entity. This class is used
 * in {@link com.techvg.los.web.rest.LoanRepaymentDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /loan-repayment-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanRepaymentDetailsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering PaymentMode
     */
    public static class PaymentModeFilter extends Filter<PaymentMode> {

        public PaymentModeFilter() {}

        public PaymentModeFilter(PaymentModeFilter filter) {
            super(filter);
        }

        @Override
        public PaymentModeFilter copy() {
            return new PaymentModeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter repaymentDate;

    private DoubleFilter totalRepaymentAmt;

    private IntegerFilter installmentNumber;

    private DoubleFilter principlePaidAmt;

    private DoubleFilter interestPaidAmt;

    private DoubleFilter surChargeAmt;

    private DoubleFilter overDueAmt;

    private DoubleFilter balanceInterestAmt;

    private DoubleFilter balancePrincipleAmt;

    private PaymentModeFilter paymentMode;

    private DoubleFilter foreClosureChargeAmt;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter freeField5;

    private StringFilter freeField6;

    private LongFilter loanAccountId;

    private Boolean distinct;

    public LoanRepaymentDetailsCriteria() {}

    public LoanRepaymentDetailsCriteria(LoanRepaymentDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.repaymentDate = other.repaymentDate == null ? null : other.repaymentDate.copy();
        this.totalRepaymentAmt = other.totalRepaymentAmt == null ? null : other.totalRepaymentAmt.copy();
        this.installmentNumber = other.installmentNumber == null ? null : other.installmentNumber.copy();
        this.principlePaidAmt = other.principlePaidAmt == null ? null : other.principlePaidAmt.copy();
        this.interestPaidAmt = other.interestPaidAmt == null ? null : other.interestPaidAmt.copy();
        this.surChargeAmt = other.surChargeAmt == null ? null : other.surChargeAmt.copy();
        this.overDueAmt = other.overDueAmt == null ? null : other.overDueAmt.copy();
        this.balanceInterestAmt = other.balanceInterestAmt == null ? null : other.balanceInterestAmt.copy();
        this.balancePrincipleAmt = other.balancePrincipleAmt == null ? null : other.balancePrincipleAmt.copy();
        this.paymentMode = other.paymentMode == null ? null : other.paymentMode.copy();
        this.foreClosureChargeAmt = other.foreClosureChargeAmt == null ? null : other.foreClosureChargeAmt.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.freeField6 = other.freeField6 == null ? null : other.freeField6.copy();
        this.loanAccountId = other.loanAccountId == null ? null : other.loanAccountId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LoanRepaymentDetailsCriteria copy() {
        return new LoanRepaymentDetailsCriteria(this);
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

    public InstantFilter getRepaymentDate() {
        return repaymentDate;
    }

    public InstantFilter repaymentDate() {
        if (repaymentDate == null) {
            repaymentDate = new InstantFilter();
        }
        return repaymentDate;
    }

    public void setRepaymentDate(InstantFilter repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public DoubleFilter getTotalRepaymentAmt() {
        return totalRepaymentAmt;
    }

    public DoubleFilter totalRepaymentAmt() {
        if (totalRepaymentAmt == null) {
            totalRepaymentAmt = new DoubleFilter();
        }
        return totalRepaymentAmt;
    }

    public void setTotalRepaymentAmt(DoubleFilter totalRepaymentAmt) {
        this.totalRepaymentAmt = totalRepaymentAmt;
    }

    public IntegerFilter getInstallmentNumber() {
        return installmentNumber;
    }

    public IntegerFilter installmentNumber() {
        if (installmentNumber == null) {
            installmentNumber = new IntegerFilter();
        }
        return installmentNumber;
    }

    public void setInstallmentNumber(IntegerFilter installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public DoubleFilter getPrinciplePaidAmt() {
        return principlePaidAmt;
    }

    public DoubleFilter principlePaidAmt() {
        if (principlePaidAmt == null) {
            principlePaidAmt = new DoubleFilter();
        }
        return principlePaidAmt;
    }

    public void setPrinciplePaidAmt(DoubleFilter principlePaidAmt) {
        this.principlePaidAmt = principlePaidAmt;
    }

    public DoubleFilter getInterestPaidAmt() {
        return interestPaidAmt;
    }

    public DoubleFilter interestPaidAmt() {
        if (interestPaidAmt == null) {
            interestPaidAmt = new DoubleFilter();
        }
        return interestPaidAmt;
    }

    public void setInterestPaidAmt(DoubleFilter interestPaidAmt) {
        this.interestPaidAmt = interestPaidAmt;
    }

    public DoubleFilter getSurChargeAmt() {
        return surChargeAmt;
    }

    public DoubleFilter surChargeAmt() {
        if (surChargeAmt == null) {
            surChargeAmt = new DoubleFilter();
        }
        return surChargeAmt;
    }

    public void setSurChargeAmt(DoubleFilter surChargeAmt) {
        this.surChargeAmt = surChargeAmt;
    }

    public DoubleFilter getOverDueAmt() {
        return overDueAmt;
    }

    public DoubleFilter overDueAmt() {
        if (overDueAmt == null) {
            overDueAmt = new DoubleFilter();
        }
        return overDueAmt;
    }

    public void setOverDueAmt(DoubleFilter overDueAmt) {
        this.overDueAmt = overDueAmt;
    }

    public DoubleFilter getBalanceInterestAmt() {
        return balanceInterestAmt;
    }

    public DoubleFilter balanceInterestAmt() {
        if (balanceInterestAmt == null) {
            balanceInterestAmt = new DoubleFilter();
        }
        return balanceInterestAmt;
    }

    public void setBalanceInterestAmt(DoubleFilter balanceInterestAmt) {
        this.balanceInterestAmt = balanceInterestAmt;
    }

    public DoubleFilter getBalancePrincipleAmt() {
        return balancePrincipleAmt;
    }

    public DoubleFilter balancePrincipleAmt() {
        if (balancePrincipleAmt == null) {
            balancePrincipleAmt = new DoubleFilter();
        }
        return balancePrincipleAmt;
    }

    public void setBalancePrincipleAmt(DoubleFilter balancePrincipleAmt) {
        this.balancePrincipleAmt = balancePrincipleAmt;
    }

    public PaymentModeFilter getPaymentMode() {
        return paymentMode;
    }

    public PaymentModeFilter paymentMode() {
        if (paymentMode == null) {
            paymentMode = new PaymentModeFilter();
        }
        return paymentMode;
    }

    public void setPaymentMode(PaymentModeFilter paymentMode) {
        this.paymentMode = paymentMode;
    }

    public DoubleFilter getForeClosureChargeAmt() {
        return foreClosureChargeAmt;
    }

    public DoubleFilter foreClosureChargeAmt() {
        if (foreClosureChargeAmt == null) {
            foreClosureChargeAmt = new DoubleFilter();
        }
        return foreClosureChargeAmt;
    }

    public void setForeClosureChargeAmt(DoubleFilter foreClosureChargeAmt) {
        this.foreClosureChargeAmt = foreClosureChargeAmt;
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

    public LongFilter getLoanAccountId() {
        return loanAccountId;
    }

    public LongFilter loanAccountId() {
        if (loanAccountId == null) {
            loanAccountId = new LongFilter();
        }
        return loanAccountId;
    }

    public void setLoanAccountId(LongFilter loanAccountId) {
        this.loanAccountId = loanAccountId;
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
        final LoanRepaymentDetailsCriteria that = (LoanRepaymentDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(repaymentDate, that.repaymentDate) &&
            Objects.equals(totalRepaymentAmt, that.totalRepaymentAmt) &&
            Objects.equals(installmentNumber, that.installmentNumber) &&
            Objects.equals(principlePaidAmt, that.principlePaidAmt) &&
            Objects.equals(interestPaidAmt, that.interestPaidAmt) &&
            Objects.equals(surChargeAmt, that.surChargeAmt) &&
            Objects.equals(overDueAmt, that.overDueAmt) &&
            Objects.equals(balanceInterestAmt, that.balanceInterestAmt) &&
            Objects.equals(balancePrincipleAmt, that.balancePrincipleAmt) &&
            Objects.equals(paymentMode, that.paymentMode) &&
            Objects.equals(foreClosureChargeAmt, that.foreClosureChargeAmt) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(freeField6, that.freeField6) &&
            Objects.equals(loanAccountId, that.loanAccountId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            repaymentDate,
            totalRepaymentAmt,
            installmentNumber,
            principlePaidAmt,
            interestPaidAmt,
            surChargeAmt,
            overDueAmt,
            balanceInterestAmt,
            balancePrincipleAmt,
            paymentMode,
            foreClosureChargeAmt,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            freeField5,
            freeField6,
            loanAccountId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanRepaymentDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (repaymentDate != null ? "repaymentDate=" + repaymentDate + ", " : "") +
            (totalRepaymentAmt != null ? "totalRepaymentAmt=" + totalRepaymentAmt + ", " : "") +
            (installmentNumber != null ? "installmentNumber=" + installmentNumber + ", " : "") +
            (principlePaidAmt != null ? "principlePaidAmt=" + principlePaidAmt + ", " : "") +
            (interestPaidAmt != null ? "interestPaidAmt=" + interestPaidAmt + ", " : "") +
            (surChargeAmt != null ? "surChargeAmt=" + surChargeAmt + ", " : "") +
            (overDueAmt != null ? "overDueAmt=" + overDueAmt + ", " : "") +
            (balanceInterestAmt != null ? "balanceInterestAmt=" + balanceInterestAmt + ", " : "") +
            (balancePrincipleAmt != null ? "balancePrincipleAmt=" + balancePrincipleAmt + ", " : "") +
            (paymentMode != null ? "paymentMode=" + paymentMode + ", " : "") +
            (foreClosureChargeAmt != null ? "foreClosureChargeAmt=" + foreClosureChargeAmt + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (freeField5 != null ? "freeField5=" + freeField5 + ", " : "") +
            (freeField6 != null ? "freeField6=" + freeField6 + ", " : "") +
            (loanAccountId != null ? "loanAccountId=" + loanAccountId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
