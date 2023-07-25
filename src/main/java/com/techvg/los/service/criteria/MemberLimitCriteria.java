package com.techvg.los.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.MemberLimit} entity. This class is used
 * in {@link com.techvg.los.web.rest.MemberLimitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /member-limits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberLimitCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter limitSanctionAmount;

    private LocalDateFilter dtLimitSanctioned;

    private LocalDateFilter dtLimitExpired;

    private StringFilter purpose;

    private BooleanFilter isDeleted;

    private BooleanFilter isActive;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private Boolean distinct;

    public MemberLimitCriteria() {}

    public MemberLimitCriteria(MemberLimitCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.limitSanctionAmount = other.limitSanctionAmount == null ? null : other.limitSanctionAmount.copy();
        this.dtLimitSanctioned = other.dtLimitSanctioned == null ? null : other.dtLimitSanctioned.copy();
        this.dtLimitExpired = other.dtLimitExpired == null ? null : other.dtLimitExpired.copy();
        this.purpose = other.purpose == null ? null : other.purpose.copy();
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
    public MemberLimitCriteria copy() {
        return new MemberLimitCriteria(this);
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

    public DoubleFilter getLimitSanctionAmount() {
        return limitSanctionAmount;
    }

    public DoubleFilter limitSanctionAmount() {
        if (limitSanctionAmount == null) {
            limitSanctionAmount = new DoubleFilter();
        }
        return limitSanctionAmount;
    }

    public void setLimitSanctionAmount(DoubleFilter limitSanctionAmount) {
        this.limitSanctionAmount = limitSanctionAmount;
    }

    public LocalDateFilter getDtLimitSanctioned() {
        return dtLimitSanctioned;
    }

    public LocalDateFilter dtLimitSanctioned() {
        if (dtLimitSanctioned == null) {
            dtLimitSanctioned = new LocalDateFilter();
        }
        return dtLimitSanctioned;
    }

    public void setDtLimitSanctioned(LocalDateFilter dtLimitSanctioned) {
        this.dtLimitSanctioned = dtLimitSanctioned;
    }

    public LocalDateFilter getDtLimitExpired() {
        return dtLimitExpired;
    }

    public LocalDateFilter dtLimitExpired() {
        if (dtLimitExpired == null) {
            dtLimitExpired = new LocalDateFilter();
        }
        return dtLimitExpired;
    }

    public void setDtLimitExpired(LocalDateFilter dtLimitExpired) {
        this.dtLimitExpired = dtLimitExpired;
    }

    public StringFilter getPurpose() {
        return purpose;
    }

    public StringFilter purpose() {
        if (purpose == null) {
            purpose = new StringFilter();
        }
        return purpose;
    }

    public void setPurpose(StringFilter purpose) {
        this.purpose = purpose;
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
        final MemberLimitCriteria that = (MemberLimitCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(limitSanctionAmount, that.limitSanctionAmount) &&
            Objects.equals(dtLimitSanctioned, that.dtLimitSanctioned) &&
            Objects.equals(dtLimitExpired, that.dtLimitExpired) &&
            Objects.equals(purpose, that.purpose) &&
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
            limitSanctionAmount,
            dtLimitSanctioned,
            dtLimitExpired,
            purpose,
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
        return "MemberLimitCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (limitSanctionAmount != null ? "limitSanctionAmount=" + limitSanctionAmount + ", " : "") +
            (dtLimitSanctioned != null ? "dtLimitSanctioned=" + dtLimitSanctioned + ", " : "") +
            (dtLimitExpired != null ? "dtLimitExpired=" + dtLimitExpired + ", " : "") +
            (purpose != null ? "purpose=" + purpose + ", " : "") +
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
