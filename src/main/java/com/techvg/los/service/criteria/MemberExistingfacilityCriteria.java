package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.CreditRating;
import com.techvg.los.domain.enumeration.FacilityStatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.MemberExistingfacility} entity. This class is used
 * in {@link com.techvg.los.web.rest.MemberExistingfacilityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /member-existingfacilities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberExistingfacilityCriteria implements Serializable, Criteria {

    /**
     * Class for filtering FacilityStatus
     */
    public static class FacilityStatusFilter extends Filter<FacilityStatus> {

        public FacilityStatusFilter() {}

        public FacilityStatusFilter(FacilityStatusFilter filter) {
            super(filter);
        }

        @Override
        public FacilityStatusFilter copy() {
            return new FacilityStatusFilter(this);
        }
    }

    /**
     * Class for filtering CreditRating
     */
    public static class CreditRatingFilter extends Filter<CreditRating> {

        public CreditRatingFilter() {}

        public CreditRatingFilter(CreditRatingFilter filter) {
            super(filter);
        }

        @Override
        public CreditRatingFilter copy() {
            return new CreditRatingFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter year;

    private StringFilter facilityName;

    private StringFilter facilitatedFrom;

    private StringFilter nature;

    private DoubleFilter amtInLac;

    private StringFilter purpose;

    private InstantFilter sectionDate;

    private DoubleFilter outstandingInLac;

    private DoubleFilter emiAmt;

    private FacilityStatusFilter status;

    private CreditRatingFilter rating;

    private BooleanFilter isDeleted;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter freeField5;

    private StringFilter remark;

    private LongFilter memberId;

    private Boolean distinct;

    public MemberExistingfacilityCriteria() {}

    public MemberExistingfacilityCriteria(MemberExistingfacilityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.facilityName = other.facilityName == null ? null : other.facilityName.copy();
        this.facilitatedFrom = other.facilitatedFrom == null ? null : other.facilitatedFrom.copy();
        this.nature = other.nature == null ? null : other.nature.copy();
        this.amtInLac = other.amtInLac == null ? null : other.amtInLac.copy();
        this.purpose = other.purpose == null ? null : other.purpose.copy();
        this.sectionDate = other.sectionDate == null ? null : other.sectionDate.copy();
        this.outstandingInLac = other.outstandingInLac == null ? null : other.outstandingInLac.copy();
        this.emiAmt = other.emiAmt == null ? null : other.emiAmt.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.rating = other.rating == null ? null : other.rating.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MemberExistingfacilityCriteria copy() {
        return new MemberExistingfacilityCriteria(this);
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

    public IntegerFilter getYear() {
        return year;
    }

    public IntegerFilter year() {
        if (year == null) {
            year = new IntegerFilter();
        }
        return year;
    }

    public void setYear(IntegerFilter year) {
        this.year = year;
    }

    public StringFilter getFacilityName() {
        return facilityName;
    }

    public StringFilter facilityName() {
        if (facilityName == null) {
            facilityName = new StringFilter();
        }
        return facilityName;
    }

    public void setFacilityName(StringFilter facilityName) {
        this.facilityName = facilityName;
    }

    public StringFilter getFacilitatedFrom() {
        return facilitatedFrom;
    }

    public StringFilter facilitatedFrom() {
        if (facilitatedFrom == null) {
            facilitatedFrom = new StringFilter();
        }
        return facilitatedFrom;
    }

    public void setFacilitatedFrom(StringFilter facilitatedFrom) {
        this.facilitatedFrom = facilitatedFrom;
    }

    public StringFilter getNature() {
        return nature;
    }

    public StringFilter nature() {
        if (nature == null) {
            nature = new StringFilter();
        }
        return nature;
    }

    public void setNature(StringFilter nature) {
        this.nature = nature;
    }

    public DoubleFilter getAmtInLac() {
        return amtInLac;
    }

    public DoubleFilter amtInLac() {
        if (amtInLac == null) {
            amtInLac = new DoubleFilter();
        }
        return amtInLac;
    }

    public void setAmtInLac(DoubleFilter amtInLac) {
        this.amtInLac = amtInLac;
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

    public InstantFilter getSectionDate() {
        return sectionDate;
    }

    public InstantFilter sectionDate() {
        if (sectionDate == null) {
            sectionDate = new InstantFilter();
        }
        return sectionDate;
    }

    public void setSectionDate(InstantFilter sectionDate) {
        this.sectionDate = sectionDate;
    }

    public DoubleFilter getOutstandingInLac() {
        return outstandingInLac;
    }

    public DoubleFilter outstandingInLac() {
        if (outstandingInLac == null) {
            outstandingInLac = new DoubleFilter();
        }
        return outstandingInLac;
    }

    public void setOutstandingInLac(DoubleFilter outstandingInLac) {
        this.outstandingInLac = outstandingInLac;
    }

    public DoubleFilter getEmiAmt() {
        return emiAmt;
    }

    public DoubleFilter emiAmt() {
        if (emiAmt == null) {
            emiAmt = new DoubleFilter();
        }
        return emiAmt;
    }

    public void setEmiAmt(DoubleFilter emiAmt) {
        this.emiAmt = emiAmt;
    }

    public FacilityStatusFilter getStatus() {
        return status;
    }

    public FacilityStatusFilter status() {
        if (status == null) {
            status = new FacilityStatusFilter();
        }
        return status;
    }

    public void setStatus(FacilityStatusFilter status) {
        this.status = status;
    }

    public CreditRatingFilter getRating() {
        return rating;
    }

    public CreditRatingFilter rating() {
        if (rating == null) {
            rating = new CreditRatingFilter();
        }
        return rating;
    }

    public void setRating(CreditRatingFilter rating) {
        this.rating = rating;
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

    public StringFilter getRemark() {
        return remark;
    }

    public StringFilter remark() {
        if (remark == null) {
            remark = new StringFilter();
        }
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
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
        final MemberExistingfacilityCriteria that = (MemberExistingfacilityCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(year, that.year) &&
            Objects.equals(facilityName, that.facilityName) &&
            Objects.equals(facilitatedFrom, that.facilitatedFrom) &&
            Objects.equals(nature, that.nature) &&
            Objects.equals(amtInLac, that.amtInLac) &&
            Objects.equals(purpose, that.purpose) &&
            Objects.equals(sectionDate, that.sectionDate) &&
            Objects.equals(outstandingInLac, that.outstandingInLac) &&
            Objects.equals(emiAmt, that.emiAmt) &&
            Objects.equals(status, that.status) &&
            Objects.equals(rating, that.rating) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            year,
            facilityName,
            facilitatedFrom,
            nature,
            amtInLac,
            purpose,
            sectionDate,
            outstandingInLac,
            emiAmt,
            status,
            rating,
            isDeleted,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            freeField5,
            remark,
            memberId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberExistingfacilityCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (facilityName != null ? "facilityName=" + facilityName + ", " : "") +
            (facilitatedFrom != null ? "facilitatedFrom=" + facilitatedFrom + ", " : "") +
            (nature != null ? "nature=" + nature + ", " : "") +
            (amtInLac != null ? "amtInLac=" + amtInLac + ", " : "") +
            (purpose != null ? "purpose=" + purpose + ", " : "") +
            (sectionDate != null ? "sectionDate=" + sectionDate + ", " : "") +
            (outstandingInLac != null ? "outstandingInLac=" + outstandingInLac + ", " : "") +
            (emiAmt != null ? "emiAmt=" + emiAmt + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (rating != null ? "rating=" + rating + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (freeField5 != null ? "freeField5=" + freeField5 + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
