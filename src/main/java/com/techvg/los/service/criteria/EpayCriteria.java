package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.RepaymentFreqency;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.Epay} entity. This class is used
 * in {@link com.techvg.los.web.rest.EpayResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /epays?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EpayCriteria implements Serializable, Criteria {

    /**
     * Class for filtering RepaymentFreqency
     */
    public static class RepaymentFreqencyFilter extends Filter<RepaymentFreqency> {

        public RepaymentFreqencyFilter() {}

        public RepaymentFreqencyFilter(RepaymentFreqencyFilter filter) {
            super(filter);
        }

        @Override
        public RepaymentFreqencyFilter copy() {
            return new RepaymentFreqencyFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter instrumentType;

    private LocalDateFilter dtFromDate;

    private LocalDateFilter dtToDate;

    private StringFilter bankCode;

    private StringFilter bankBranchCode;

    private StringFilter accountType;

    private StringFilter accountNo;

    private DoubleFilter maxCeilAmount;

    private DoubleFilter installmentAmount;

    private DoubleFilter maxInstallmentAmount;

    private StringFilter mandateRefNo;

    private StringFilter depositBank;

    private StringFilter mandateType;

    private RepaymentFreqencyFilter frequency;

    private StringFilter ifscCode;

    private StringFilter utrNo;

    private BooleanFilter isDeleted;

    private BooleanFilter isActive;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private Boolean distinct;

    public EpayCriteria() {}

    public EpayCriteria(EpayCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.instrumentType = other.instrumentType == null ? null : other.instrumentType.copy();
        this.dtFromDate = other.dtFromDate == null ? null : other.dtFromDate.copy();
        this.dtToDate = other.dtToDate == null ? null : other.dtToDate.copy();
        this.bankCode = other.bankCode == null ? null : other.bankCode.copy();
        this.bankBranchCode = other.bankBranchCode == null ? null : other.bankBranchCode.copy();
        this.accountType = other.accountType == null ? null : other.accountType.copy();
        this.accountNo = other.accountNo == null ? null : other.accountNo.copy();
        this.maxCeilAmount = other.maxCeilAmount == null ? null : other.maxCeilAmount.copy();
        this.installmentAmount = other.installmentAmount == null ? null : other.installmentAmount.copy();
        this.maxInstallmentAmount = other.maxInstallmentAmount == null ? null : other.maxInstallmentAmount.copy();
        this.mandateRefNo = other.mandateRefNo == null ? null : other.mandateRefNo.copy();
        this.depositBank = other.depositBank == null ? null : other.depositBank.copy();
        this.mandateType = other.mandateType == null ? null : other.mandateType.copy();
        this.frequency = other.frequency == null ? null : other.frequency.copy();
        this.ifscCode = other.ifscCode == null ? null : other.ifscCode.copy();
        this.utrNo = other.utrNo == null ? null : other.utrNo.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EpayCriteria copy() {
        return new EpayCriteria(this);
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

    public StringFilter getInstrumentType() {
        return instrumentType;
    }

    public StringFilter instrumentType() {
        if (instrumentType == null) {
            instrumentType = new StringFilter();
        }
        return instrumentType;
    }

    public void setInstrumentType(StringFilter instrumentType) {
        this.instrumentType = instrumentType;
    }

    public LocalDateFilter getDtFromDate() {
        return dtFromDate;
    }

    public LocalDateFilter dtFromDate() {
        if (dtFromDate == null) {
            dtFromDate = new LocalDateFilter();
        }
        return dtFromDate;
    }

    public void setDtFromDate(LocalDateFilter dtFromDate) {
        this.dtFromDate = dtFromDate;
    }

    public LocalDateFilter getDtToDate() {
        return dtToDate;
    }

    public LocalDateFilter dtToDate() {
        if (dtToDate == null) {
            dtToDate = new LocalDateFilter();
        }
        return dtToDate;
    }

    public void setDtToDate(LocalDateFilter dtToDate) {
        this.dtToDate = dtToDate;
    }

    public StringFilter getBankCode() {
        return bankCode;
    }

    public StringFilter bankCode() {
        if (bankCode == null) {
            bankCode = new StringFilter();
        }
        return bankCode;
    }

    public void setBankCode(StringFilter bankCode) {
        this.bankCode = bankCode;
    }

    public StringFilter getBankBranchCode() {
        return bankBranchCode;
    }

    public StringFilter bankBranchCode() {
        if (bankBranchCode == null) {
            bankBranchCode = new StringFilter();
        }
        return bankBranchCode;
    }

    public void setBankBranchCode(StringFilter bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    public StringFilter getAccountType() {
        return accountType;
    }

    public StringFilter accountType() {
        if (accountType == null) {
            accountType = new StringFilter();
        }
        return accountType;
    }

    public void setAccountType(StringFilter accountType) {
        this.accountType = accountType;
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

    public DoubleFilter getMaxCeilAmount() {
        return maxCeilAmount;
    }

    public DoubleFilter maxCeilAmount() {
        if (maxCeilAmount == null) {
            maxCeilAmount = new DoubleFilter();
        }
        return maxCeilAmount;
    }

    public void setMaxCeilAmount(DoubleFilter maxCeilAmount) {
        this.maxCeilAmount = maxCeilAmount;
    }

    public DoubleFilter getInstallmentAmount() {
        return installmentAmount;
    }

    public DoubleFilter installmentAmount() {
        if (installmentAmount == null) {
            installmentAmount = new DoubleFilter();
        }
        return installmentAmount;
    }

    public void setInstallmentAmount(DoubleFilter installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public DoubleFilter getMaxInstallmentAmount() {
        return maxInstallmentAmount;
    }

    public DoubleFilter maxInstallmentAmount() {
        if (maxInstallmentAmount == null) {
            maxInstallmentAmount = new DoubleFilter();
        }
        return maxInstallmentAmount;
    }

    public void setMaxInstallmentAmount(DoubleFilter maxInstallmentAmount) {
        this.maxInstallmentAmount = maxInstallmentAmount;
    }

    public StringFilter getMandateRefNo() {
        return mandateRefNo;
    }

    public StringFilter mandateRefNo() {
        if (mandateRefNo == null) {
            mandateRefNo = new StringFilter();
        }
        return mandateRefNo;
    }

    public void setMandateRefNo(StringFilter mandateRefNo) {
        this.mandateRefNo = mandateRefNo;
    }

    public StringFilter getDepositBank() {
        return depositBank;
    }

    public StringFilter depositBank() {
        if (depositBank == null) {
            depositBank = new StringFilter();
        }
        return depositBank;
    }

    public void setDepositBank(StringFilter depositBank) {
        this.depositBank = depositBank;
    }

    public StringFilter getMandateType() {
        return mandateType;
    }

    public StringFilter mandateType() {
        if (mandateType == null) {
            mandateType = new StringFilter();
        }
        return mandateType;
    }

    public void setMandateType(StringFilter mandateType) {
        this.mandateType = mandateType;
    }

    public RepaymentFreqencyFilter getFrequency() {
        return frequency;
    }

    public RepaymentFreqencyFilter frequency() {
        if (frequency == null) {
            frequency = new RepaymentFreqencyFilter();
        }
        return frequency;
    }

    public void setFrequency(RepaymentFreqencyFilter frequency) {
        this.frequency = frequency;
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

    public BooleanFilter getIsDeleted() {
        return isDeleted;
    }

    public BooleanFilter isDeleted() {
        if (isDeleted == null) {
            isDeleted = new BooleanFilter();
        }
        return isDeleted;
    }

    public void setIsDeleted(BooleanFilter isDeleted) {
        this.isDeleted = isDeleted;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public BooleanFilter isActive() {
        if (isActive == null) {
            isActive = new BooleanFilter();
        }
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
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
        final EpayCriteria that = (EpayCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(instrumentType, that.instrumentType) &&
            Objects.equals(dtFromDate, that.dtFromDate) &&
            Objects.equals(dtToDate, that.dtToDate) &&
            Objects.equals(bankCode, that.bankCode) &&
            Objects.equals(bankBranchCode, that.bankBranchCode) &&
            Objects.equals(accountType, that.accountType) &&
            Objects.equals(accountNo, that.accountNo) &&
            Objects.equals(maxCeilAmount, that.maxCeilAmount) &&
            Objects.equals(installmentAmount, that.installmentAmount) &&
            Objects.equals(maxInstallmentAmount, that.maxInstallmentAmount) &&
            Objects.equals(mandateRefNo, that.mandateRefNo) &&
            Objects.equals(depositBank, that.depositBank) &&
            Objects.equals(mandateType, that.mandateType) &&
            Objects.equals(frequency, that.frequency) &&
            Objects.equals(ifscCode, that.ifscCode) &&
            Objects.equals(utrNo, that.utrNo) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            instrumentType,
            dtFromDate,
            dtToDate,
            bankCode,
            bankBranchCode,
            accountType,
            accountNo,
            maxCeilAmount,
            installmentAmount,
            maxInstallmentAmount,
            mandateRefNo,
            depositBank,
            mandateType,
            frequency,
            ifscCode,
            utrNo,
            isDeleted,
            isActive,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EpayCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (instrumentType != null ? "instrumentType=" + instrumentType + ", " : "") +
            (dtFromDate != null ? "dtFromDate=" + dtFromDate + ", " : "") +
            (dtToDate != null ? "dtToDate=" + dtToDate + ", " : "") +
            (bankCode != null ? "bankCode=" + bankCode + ", " : "") +
            (bankBranchCode != null ? "bankBranchCode=" + bankBranchCode + ", " : "") +
            (accountType != null ? "accountType=" + accountType + ", " : "") +
            (accountNo != null ? "accountNo=" + accountNo + ", " : "") +
            (maxCeilAmount != null ? "maxCeilAmount=" + maxCeilAmount + ", " : "") +
            (installmentAmount != null ? "installmentAmount=" + installmentAmount + ", " : "") +
            (maxInstallmentAmount != null ? "maxInstallmentAmount=" + maxInstallmentAmount + ", " : "") +
            (mandateRefNo != null ? "mandateRefNo=" + mandateRefNo + ", " : "") +
            (depositBank != null ? "depositBank=" + depositBank + ", " : "") +
            (mandateType != null ? "mandateType=" + mandateType + ", " : "") +
            (frequency != null ? "frequency=" + frequency + ", " : "") +
            (ifscCode != null ? "ifscCode=" + ifscCode + ", " : "") +
            (utrNo != null ? "utrNo=" + utrNo + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (isActive != null ? "isActive=" + isActive + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
