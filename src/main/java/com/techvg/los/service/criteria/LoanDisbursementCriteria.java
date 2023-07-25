package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.PaymentMode;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.LoanDisbursement} entity. This class is used
 * in {@link com.techvg.los.web.rest.LoanDisbursementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /loan-disbursements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanDisbursementCriteria implements Serializable, Criteria {

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

    private InstantFilter dtDisbDate;

    private StringFilter accountNo;

    private StringFilter ifscCode;

    private DoubleFilter disbAmount;

    private IntegerFilter disbuNumber;

    private StringFilter disbursementStatus;

    private PaymentModeFilter paymentMode;

    private StringFilter utrNo;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private DoubleFilter sanctionAmt;

    private IntegerFilter numOfInstalls;

    private DoubleFilter installmentAmt;

    private InstantFilter firstInstDate;

    private InstantFilter secondInstDate;

    private DoubleFilter roi;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter freeField5;

    private StringFilter freeField6;

    private StringFilter loanApplicationNo;

    private LongFilter productId;

    private LongFilter securityUserId;

    private LongFilter loanAccountId;

    private Boolean distinct;

    public LoanDisbursementCriteria() {}

    public LoanDisbursementCriteria(LoanDisbursementCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dtDisbDate = other.dtDisbDate == null ? null : other.dtDisbDate.copy();
        this.accountNo = other.accountNo == null ? null : other.accountNo.copy();
        this.ifscCode = other.ifscCode == null ? null : other.ifscCode.copy();
        this.disbAmount = other.disbAmount == null ? null : other.disbAmount.copy();
        this.disbuNumber = other.disbuNumber == null ? null : other.disbuNumber.copy();
        this.disbursementStatus = other.disbursementStatus == null ? null : other.disbursementStatus.copy();
        this.paymentMode = other.paymentMode == null ? null : other.paymentMode.copy();
        this.utrNo = other.utrNo == null ? null : other.utrNo.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.sanctionAmt = other.sanctionAmt == null ? null : other.sanctionAmt.copy();
        this.numOfInstalls = other.numOfInstalls == null ? null : other.numOfInstalls.copy();
        this.installmentAmt = other.installmentAmt == null ? null : other.installmentAmt.copy();
        this.firstInstDate = other.firstInstDate == null ? null : other.firstInstDate.copy();
        this.secondInstDate = other.secondInstDate == null ? null : other.secondInstDate.copy();
        this.roi = other.roi == null ? null : other.roi.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.freeField6 = other.freeField6 == null ? null : other.freeField6.copy();
        this.loanApplicationNo = other.loanApplicationNo == null ? null : other.loanApplicationNo.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.loanAccountId = other.loanAccountId == null ? null : other.loanAccountId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LoanDisbursementCriteria copy() {
        return new LoanDisbursementCriteria(this);
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

    public InstantFilter getDtDisbDate() {
        return dtDisbDate;
    }

    public InstantFilter dtDisbDate() {
        if (dtDisbDate == null) {
            dtDisbDate = new InstantFilter();
        }
        return dtDisbDate;
    }

    public void setDtDisbDate(InstantFilter dtDisbDate) {
        this.dtDisbDate = dtDisbDate;
    }

    public StringFilter getAccountNo() {
        return accountNo;
    }

    public StringFilter accountNo() {
        if (accountNo == null) {
            accountNo = new StringFilter();
        }
        return accountNo;
    }

    public void setAccountNo(StringFilter accountNo) {
        this.accountNo = accountNo;
    }

    public StringFilter getIfscCode() {
        return ifscCode;
    }

    public StringFilter ifscCode() {
        if (ifscCode == null) {
            ifscCode = new StringFilter();
        }
        return ifscCode;
    }

    public void setIfscCode(StringFilter ifscCode) {
        this.ifscCode = ifscCode;
    }

    public DoubleFilter getDisbAmount() {
        return disbAmount;
    }

    public DoubleFilter disbAmount() {
        if (disbAmount == null) {
            disbAmount = new DoubleFilter();
        }
        return disbAmount;
    }

    public void setDisbAmount(DoubleFilter disbAmount) {
        this.disbAmount = disbAmount;
    }

    public IntegerFilter getDisbuNumber() {
        return disbuNumber;
    }

    public IntegerFilter disbuNumber() {
        if (disbuNumber == null) {
            disbuNumber = new IntegerFilter();
        }
        return disbuNumber;
    }

    public void setDisbuNumber(IntegerFilter disbuNumber) {
        this.disbuNumber = disbuNumber;
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

    public StringFilter getUtrNo() {
        return utrNo;
    }

    public StringFilter utrNo() {
        if (utrNo == null) {
            utrNo = new StringFilter();
        }
        return utrNo;
    }

    public void setUtrNo(StringFilter utrNo) {
        this.utrNo = utrNo;
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

    public void setSanctionAmt(DoubleFilter sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public DoubleFilter getSanctionAmt() {
        return sanctionAmt;
    }

    public DoubleFilter sanctionAmt() {
        if (sanctionAmt == null) {
            sanctionAmt = new DoubleFilter();
        }
        return sanctionAmt;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public IntegerFilter getNumOfInstalls() {
        return numOfInstalls;
    }

    public IntegerFilter numOfInstalls() {
        if (numOfInstalls == null) {
            numOfInstalls = new IntegerFilter();
        }
        return numOfInstalls;
    }

    public void setNumOfInstalls(IntegerFilter numOfInstalls) {
        this.numOfInstalls = numOfInstalls;
    }

    public DoubleFilter getInstallmentAmt() {
        return installmentAmt;
    }

    public DoubleFilter installmentAmt() {
        if (installmentAmt == null) {
            installmentAmt = new DoubleFilter();
        }
        return installmentAmt;
    }

    public void setInstallmentAmt(DoubleFilter installmentAmt) {
        this.installmentAmt = installmentAmt;
    }

    public void setFirstInstDate(InstantFilter firstInstDate) {
        this.firstInstDate = firstInstDate;
    }

    public InstantFilter getFirstInstDate() {
        return firstInstDate;
    }

    public InstantFilter firstInstDate() {
        if (firstInstDate == null) {
            firstInstDate = new InstantFilter();
        }
        return firstInstDate;
    }

    public InstantFilter getSecondInstDate() {
        return secondInstDate;
    }

    public InstantFilter secondInstDate() {
        if (secondInstDate == null) {
            secondInstDate = new InstantFilter();
        }
        return secondInstDate;
    }

    public void setSecondInstDate(InstantFilter secondInstDate) {
        this.secondInstDate = secondInstDate;
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

    public StringFilter getLoanApplicationNo() {
        return loanApplicationNo;
    }

    public StringFilter loanApplicationNo() {
        if (loanApplicationNo == null) {
            loanApplicationNo = new StringFilter();
        }
        return loanApplicationNo;
    }

    public void setLoanApplicationNo(StringFilter loanApplicationNo) {
        this.loanApplicationNo = loanApplicationNo;
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

    public LongFilter getSecurityUserId() {
        return securityUserId;
    }

    public LongFilter securityUserId() {
        if (securityUserId == null) {
            securityUserId = new LongFilter();
        }
        return securityUserId;
    }

    public void setSecurityUserId(LongFilter securityUserId) {
        this.securityUserId = securityUserId;
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
        final LoanDisbursementCriteria that = (LoanDisbursementCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dtDisbDate, that.dtDisbDate) &&
            Objects.equals(accountNo, that.accountNo) &&
            Objects.equals(ifscCode, that.ifscCode) &&
            Objects.equals(disbAmount, that.disbAmount) &&
            Objects.equals(disbuNumber, that.disbuNumber) &&
            Objects.equals(disbursementStatus, that.disbursementStatus) &&
            Objects.equals(paymentMode, that.paymentMode) &&
            Objects.equals(utrNo, that.utrNo) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(sanctionAmt, that.sanctionAmt) &&
            Objects.equals(numOfInstalls, that.numOfInstalls) &&
            Objects.equals(installmentAmt, that.installmentAmt) &&
            Objects.equals(firstInstDate, that.firstInstDate) &&
            Objects.equals(secondInstDate, that.secondInstDate) &&
            Objects.equals(roi, that.roi) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(freeField6, that.freeField6) &&
            Objects.equals(loanApplicationNo, that.loanApplicationNo) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(loanAccountId, that.loanAccountId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dtDisbDate,
            accountNo,
            ifscCode,
            disbAmount,
            disbuNumber,
            disbursementStatus,
            paymentMode,
            utrNo,
            lastModified,
            lastModifiedBy,
            sanctionAmt,
            numOfInstalls,
            installmentAmt,
            firstInstDate,
            secondInstDate,
            roi,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            freeField5,
            freeField6,
            loanApplicationNo,
            productId,
            securityUserId,
            loanAccountId,
            distinct
        );
    }

    // prettier-ignore
	@Override
	public String toString() {
		return "LoanDisbursementCriteria{" +
				(id != null ? "id=" + id + ", " : "") +
				(dtDisbDate != null ? "dtDisbDate=" + dtDisbDate + ", " : "") +
				(accountNo != null ? "accountNo=" + accountNo + ", " : "") +
				(ifscCode != null ? "ifscCode=" + ifscCode + ", " : "") +
				(disbAmount != null ? "disbAmount=" + disbAmount + ", " : "") +
				(disbuNumber != null ? "disbuNumber=" + disbuNumber + ", " : "") +
				(disbursementStatus != null ? "disbursementStatus=" + disbursementStatus + ", " : "") +
				(paymentMode != null ? "paymentMode=" + paymentMode + ", " : "") +
				(utrNo != null ? "utrNo=" + utrNo + ", " : "") +
				(lastModified != null ? "lastModified=" + lastModified + ", " : "") +
				(lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
				(sanctionAmt != null ? "sanctionAmt=" + sanctionAmt + ", " : "") +
				(numOfInstalls != null ? "numOfInstalls=" + numOfInstalls + ", " : "") +
				(installmentAmt != null ? "installmentAmt=" + installmentAmt + ", " : "") +
				(firstInstDate != null ? "firstInstDate=" + firstInstDate + ", " : "") +
				(secondInstDate != null ? "secondInstDate=" + secondInstDate + ", " : "") +
				(roi != null ? "roi=" + roi + ", " : "") +
				(freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
				(freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
				(freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
				(freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
				(freeField5 != null ? "freeField5=" + freeField5 + ", " : "") +
				(freeField6 != null ? "freeField6=" + freeField6 + ", " : "") +
				(loanApplicationNo != null ? "loanApplicationNo=" + loanApplicationNo + ", " : "") +
				(productId != null ? "productId=" + productId + ", " : "") +
				(securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
				(loanAccountId != null ? "loanAccountId=" + loanAccountId + ", " : "") +
				(distinct != null ? "distinct=" + distinct + ", " : "") +
				"}";
	}
}
