package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.LedgerClassification;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.LedgerAccounts} entity. This class is used
 * in {@link com.techvg.los.web.rest.LedgerAccountsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ledger-accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LedgerAccountsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering LedgerClassification
     */
    public static class LedgerClassificationFilter extends Filter<LedgerClassification> {

        public LedgerClassificationFilter() {}

        public LedgerClassificationFilter(LedgerClassificationFilter filter) {
            super(filter);
        }

        @Override
        public LedgerClassificationFilter copy() {
            return new LedgerClassificationFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter accountNo;

    private StringFilter accountName;

    private DoubleFilter accBalance;

    private StringFilter accHeadCode;

    private StringFilter ledgerCode;

    private StringFilter appCode;

    private LedgerClassificationFilter ledgerClassification;

    private IntegerFilter level;

    private StringFilter year;

    private StringFilter accountType;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter isDeleted;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter freeField5;

    private LongFilter branchId;

    private LongFilter ledgerAccountsId;

    private Boolean distinct;

    public LedgerAccountsCriteria() {}

    public LedgerAccountsCriteria(LedgerAccountsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.accountNo = other.accountNo == null ? null : other.accountNo.copy();
        this.accountName = other.accountName == null ? null : other.accountName.copy();
        this.accBalance = other.accBalance == null ? null : other.accBalance.copy();
        this.accHeadCode = other.accHeadCode == null ? null : other.accHeadCode.copy();
        this.ledgerCode = other.ledgerCode == null ? null : other.ledgerCode.copy();
        this.appCode = other.appCode == null ? null : other.appCode.copy();
        this.ledgerClassification = other.ledgerClassification == null ? null : other.ledgerClassification.copy();
        this.level = other.level == null ? null : other.level.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.accountType = other.accountType == null ? null : other.accountType.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.branchId = other.branchId == null ? null : other.branchId.copy();
        this.ledgerAccountsId = other.ledgerAccountsId == null ? null : other.ledgerAccountsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LedgerAccountsCriteria copy() {
        return new LedgerAccountsCriteria(this);
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

    public LongFilter getAccountNo() {
        return accountNo;
    }

    public LongFilter accountNo() {
        if (accountNo == null) {
            accountNo = new LongFilter();
        }
        return accountNo;
    }

    public void setAccountNo(LongFilter accountNo) {
        this.accountNo = accountNo;
    }

    public StringFilter getAccountName() {
        return accountName;
    }

    public StringFilter accountName() {
        if (accountName == null) {
            accountName = new StringFilter();
        }
        return accountName;
    }

    public void setAccountName(StringFilter accountName) {
        this.accountName = accountName;
    }

    public DoubleFilter getAccBalance() {
        return accBalance;
    }

    public DoubleFilter accBalance() {
        if (accBalance == null) {
            accBalance = new DoubleFilter();
        }
        return accBalance;
    }

    public void setAccBalance(DoubleFilter accBalance) {
        this.accBalance = accBalance;
    }

    public StringFilter getAccHeadCode() {
        return accHeadCode;
    }

    public StringFilter accHeadCode() {
        if (accHeadCode == null) {
            accHeadCode = new StringFilter();
        }
        return accHeadCode;
    }

    public void setAccHeadCode(StringFilter accHeadCode) {
        this.accHeadCode = accHeadCode;
    }

    public StringFilter getLedgerCode() {
        return ledgerCode;
    }

    public StringFilter ledgerCode() {
        if (ledgerCode == null) {
            ledgerCode = new StringFilter();
        }
        return ledgerCode;
    }

    public void setLedgerCode(StringFilter ledgerCode) {
        this.ledgerCode = ledgerCode;
    }

    public StringFilter getAppCode() {
        return appCode;
    }

    public StringFilter appCode() {
        if (appCode == null) {
            appCode = new StringFilter();
        }
        return appCode;
    }

    public void setAppCode(StringFilter appCode) {
        this.appCode = appCode;
    }

    public LedgerClassificationFilter getLedgerClassification() {
        return ledgerClassification;
    }

    public LedgerClassificationFilter ledgerClassification() {
        if (ledgerClassification == null) {
            ledgerClassification = new LedgerClassificationFilter();
        }
        return ledgerClassification;
    }

    public void setLedgerClassification(LedgerClassificationFilter ledgerClassification) {
        this.ledgerClassification = ledgerClassification;
    }

    public IntegerFilter getLevel() {
        return level;
    }

    public IntegerFilter level() {
        if (level == null) {
            level = new IntegerFilter();
        }
        return level;
    }

    public void setLevel(IntegerFilter level) {
        this.level = level;
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

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public InstantFilter createdOn() {
        if (createdOn == null) {
            createdOn = new InstantFilter();
        }
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
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

    public LongFilter getLedgerAccountsId() {
        return ledgerAccountsId;
    }

    public LongFilter ledgerAccountsId() {
        if (ledgerAccountsId == null) {
            ledgerAccountsId = new LongFilter();
        }
        return ledgerAccountsId;
    }

    public void setLedgerAccountsId(LongFilter ledgerAccountsId) {
        this.ledgerAccountsId = ledgerAccountsId;
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
        final LedgerAccountsCriteria that = (LedgerAccountsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(accountNo, that.accountNo) &&
            Objects.equals(accountName, that.accountName) &&
            Objects.equals(accBalance, that.accBalance) &&
            Objects.equals(accHeadCode, that.accHeadCode) &&
            Objects.equals(ledgerCode, that.ledgerCode) &&
            Objects.equals(appCode, that.appCode) &&
            Objects.equals(ledgerClassification, that.ledgerClassification) &&
            Objects.equals(level, that.level) &&
            Objects.equals(year, that.year) &&
            Objects.equals(accountType, that.accountType) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(branchId, that.branchId) &&
            Objects.equals(ledgerAccountsId, that.ledgerAccountsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            accountNo,
            accountName,
            accBalance,
            accHeadCode,
            ledgerCode,
            appCode,
            ledgerClassification,
            level,
            year,
            accountType,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            isDeleted,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            freeField5,
            branchId,
            ledgerAccountsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LedgerAccountsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (accountNo != null ? "accountNo=" + accountNo + ", " : "") +
            (accountName != null ? "accountName=" + accountName + ", " : "") +
            (accBalance != null ? "accBalance=" + accBalance + ", " : "") +
            (accHeadCode != null ? "accHeadCode=" + accHeadCode + ", " : "") +
            (ledgerCode != null ? "ledgerCode=" + ledgerCode + ", " : "") +
            (appCode != null ? "appCode=" + appCode + ", " : "") +
            (ledgerClassification != null ? "ledgerClassification=" + ledgerClassification + ", " : "") +
            (level != null ? "level=" + level + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (accountType != null ? "accountType=" + accountType + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (freeField5 != null ? "freeField5=" + freeField5 + ", " : "") +
            (branchId != null ? "branchId=" + branchId + ", " : "") +
            (ledgerAccountsId != null ? "ledgerAccountsId=" + ledgerAccountsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
