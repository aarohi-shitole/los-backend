package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.NpaClassification;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.NpaSetting} entity. This class is used
 * in {@link com.techvg.los.web.rest.NpaSettingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /npa-settings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NpaSettingCriteria implements Serializable, Criteria {

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

    private NpaClassificationFilter npaClassification;

    private IntegerFilter durationStart;

    private IntegerFilter durationEnd;

    private StringFilter provision;

    private IntegerFilter year;

    private IntegerFilter interestRate;

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

    private LongFilter organisationId;

    private Boolean distinct;

    public NpaSettingCriteria() {}

    public NpaSettingCriteria(NpaSettingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.npaClassification = other.npaClassification == null ? null : other.npaClassification.copy();
        this.durationStart = other.durationStart == null ? null : other.durationStart.copy();
        this.durationEnd = other.durationEnd == null ? null : other.durationEnd.copy();
        this.provision = other.provision == null ? null : other.provision.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.interestRate = other.interestRate == null ? null : other.interestRate.copy();
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
        this.organisationId = other.organisationId == null ? null : other.organisationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public NpaSettingCriteria copy() {
        return new NpaSettingCriteria(this);
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

    public NpaClassificationFilter getNpaClassification() {
        return npaClassification;
    }

    public NpaClassificationFilter npaClassification() {
        if (npaClassification == null) {
            npaClassification = new NpaClassificationFilter();
        }
        return npaClassification;
    }

    public void setNpaClassification(NpaClassificationFilter npaClassification) {
        this.npaClassification = npaClassification;
    }

    public IntegerFilter getDurationStart() {
        return durationStart;
    }

    public IntegerFilter durationStart() {
        if (durationStart == null) {
            durationStart = new IntegerFilter();
        }
        return durationStart;
    }

    public void setDurationStart(IntegerFilter durationStart) {
        this.durationStart = durationStart;
    }

    public IntegerFilter getDurationEnd() {
        return durationEnd;
    }

    public IntegerFilter durationEnd() {
        if (durationEnd == null) {
            durationEnd = new IntegerFilter();
        }
        return durationEnd;
    }

    public void setDurationEnd(IntegerFilter durationEnd) {
        this.durationEnd = durationEnd;
    }

    public StringFilter getProvision() {
        return provision;
    }

    public StringFilter provision() {
        if (provision == null) {
            provision = new StringFilter();
        }
        return provision;
    }

    public void setProvision(StringFilter provision) {
        this.provision = provision;
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

    public IntegerFilter getInterestRate() {
        return interestRate;
    }

    public IntegerFilter interestRate() {
        if (interestRate == null) {
            interestRate = new IntegerFilter();
        }
        return interestRate;
    }

    public void setInterestRate(IntegerFilter interestRate) {
        this.interestRate = interestRate;
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

    public LongFilter getOrganisationId() {
        return organisationId;
    }

    public LongFilter organisationId() {
        if (organisationId == null) {
            organisationId = new LongFilter();
        }
        return organisationId;
    }

    public void setOrganisationId(LongFilter organisationId) {
        this.organisationId = organisationId;
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
        final NpaSettingCriteria that = (NpaSettingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(npaClassification, that.npaClassification) &&
            Objects.equals(durationStart, that.durationStart) &&
            Objects.equals(durationEnd, that.durationEnd) &&
            Objects.equals(provision, that.provision) &&
            Objects.equals(year, that.year) &&
            Objects.equals(interestRate, that.interestRate) &&
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
            Objects.equals(organisationId, that.organisationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            npaClassification,
            durationStart,
            durationEnd,
            provision,
            year,
            interestRate,
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
            organisationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NpaSettingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (npaClassification != null ? "npaClassification=" + npaClassification + ", " : "") +
            (durationStart != null ? "durationStart=" + durationStart + ", " : "") +
            (durationEnd != null ? "durationEnd=" + durationEnd + ", " : "") +
            (provision != null ? "provision=" + provision + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (interestRate != null ? "interestRate=" + interestRate + ", " : "") +
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
            (organisationId != null ? "organisationId=" + organisationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
