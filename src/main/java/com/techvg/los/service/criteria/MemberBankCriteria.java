package com.techvg.los.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.MemberBank} entity. This class is used
 * in {@link com.techvg.los.web.rest.MemberBankResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /member-banks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberBankCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter bankName;

    private StringFilter branchName;

    private LongFilter accountNumber;

    private StringFilter accHolderName;

    private StringFilter ifsccode;

    private StringFilter micrCode;

    private StringFilter swiftCode;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private BooleanFilter isActive;

    private BooleanFilter isDeleted;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter freeField5;

    private StringFilter freeField6;

    private LongFilter memberId;

    private Boolean distinct;

    public MemberBankCriteria() {}

    public MemberBankCriteria(MemberBankCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.branchName = other.branchName == null ? null : other.branchName.copy();
        this.accountNumber = other.accountNumber == null ? null : other.accountNumber.copy();
        this.accHolderName = other.accHolderName == null ? null : other.accHolderName.copy();
        this.ifsccode = other.ifsccode == null ? null : other.ifsccode.copy();
        this.micrCode = other.micrCode == null ? null : other.micrCode.copy();
        this.swiftCode = other.swiftCode == null ? null : other.swiftCode.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.freeField6 = other.freeField6 == null ? null : other.freeField6.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MemberBankCriteria copy() {
        return new MemberBankCriteria(this);
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

    public StringFilter getBankName() {
        return bankName;
    }

    public StringFilter bankName() {
        if (bankName == null) {
            bankName = new StringFilter();
        }
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public StringFilter getBranchName() {
        return branchName;
    }

    public StringFilter branchName() {
        if (branchName == null) {
            branchName = new StringFilter();
        }
        return branchName;
    }

    public void setBranchName(StringFilter branchName) {
        this.branchName = branchName;
    }

    public LongFilter getAccountNumber() {
        return accountNumber;
    }

    public LongFilter accountNumber() {
        if (accountNumber == null) {
            accountNumber = new LongFilter();
        }
        return accountNumber;
    }

    public void setAccountNumber(LongFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public StringFilter getAccHolderName() {
        return accHolderName;
    }

    public StringFilter accHolderName() {
        if (accHolderName == null) {
            accHolderName = new StringFilter();
        }
        return accHolderName;
    }

    public void setAccHolderName(StringFilter accHolderName) {
        this.accHolderName = accHolderName;
    }

    public StringFilter getIfsccode() {
        return ifsccode;
    }

    public StringFilter ifsccode() {
        if (ifsccode == null) {
            ifsccode = new StringFilter();
        }
        return ifsccode;
    }

    public void setIfsccode(StringFilter ifsccode) {
        this.ifsccode = ifsccode;
    }

    public StringFilter getMicrCode() {
        return micrCode;
    }

    public StringFilter micrCode() {
        if (micrCode == null) {
            micrCode = new StringFilter();
        }
        return micrCode;
    }

    public void setMicrCode(StringFilter micrCode) {
        this.micrCode = micrCode;
    }

    public StringFilter getSwiftCode() {
        return swiftCode;
    }

    public StringFilter swiftCode() {
        if (swiftCode == null) {
            swiftCode = new StringFilter();
        }
        return swiftCode;
    }

    public void setSwiftCode(StringFilter swiftCode) {
        this.swiftCode = swiftCode;
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
        final MemberBankCriteria that = (MemberBankCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(branchName, that.branchName) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(accHolderName, that.accHolderName) &&
            Objects.equals(ifsccode, that.ifsccode) &&
            Objects.equals(micrCode, that.micrCode) &&
            Objects.equals(swiftCode, that.swiftCode) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(freeField6, that.freeField6) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            bankName,
            branchName,
            accountNumber,
            accHolderName,
            ifsccode,
            micrCode,
            swiftCode,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            isActive,
            isDeleted,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            freeField5,
            freeField6,
            memberId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberBankCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (bankName != null ? "bankName=" + bankName + ", " : "") +
            (branchName != null ? "branchName=" + branchName + ", " : "") +
            (accountNumber != null ? "accountNumber=" + accountNumber + ", " : "") +
            (accHolderName != null ? "accHolderName=" + accHolderName + ", " : "") +
            (ifsccode != null ? "ifsccode=" + ifsccode + ", " : "") +
            (micrCode != null ? "micrCode=" + micrCode + ", " : "") +
            (swiftCode != null ? "swiftCode=" + swiftCode + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (isActive != null ? "isActive=" + isActive + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (freeField5 != null ? "freeField5=" + freeField5 + ", " : "") +
            (freeField6 != null ? "freeField6=" + freeField6 + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
