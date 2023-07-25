package com.techvg.los.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.Taluka} entity. This class is used
 * in {@link com.techvg.los.web.rest.TalukaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /talukas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TalukaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter talukaName;

    private BooleanFilter isDeleted;

    private LongFilter lgdCode;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private LongFilter districtId;

    private Boolean distinct;

    public TalukaCriteria() {}

    public TalukaCriteria(TalukaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.talukaName = other.talukaName == null ? null : other.talukaName.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.lgdCode = other.lgdCode == null ? null : other.lgdCode.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.districtId = other.districtId == null ? null : other.districtId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TalukaCriteria copy() {
        return new TalukaCriteria(this);
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

    public StringFilter getTalukaName() {
        return talukaName;
    }

    public StringFilter talukaName() {
        if (talukaName == null) {
            talukaName = new StringFilter();
        }
        return talukaName;
    }

    public void setTalukaName(StringFilter talukaName) {
        this.talukaName = talukaName;
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

    public LongFilter getLgdCode() {
        return lgdCode;
    }

    public LongFilter lgdCode() {
        if (lgdCode == null) {
            lgdCode = new LongFilter();
        }
        return lgdCode;
    }

    public void setLgdCode(LongFilter lgdCode) {
        this.lgdCode = lgdCode;
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

    public LongFilter getDistrictId() {
        return districtId;
    }

    public LongFilter districtId() {
        if (districtId == null) {
            districtId = new LongFilter();
        }
        return districtId;
    }

    public void setDistrictId(LongFilter districtId) {
        this.districtId = districtId;
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
        final TalukaCriteria that = (TalukaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(talukaName, that.talukaName) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(lgdCode, that.lgdCode) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(districtId, that.districtId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, talukaName, isDeleted, lgdCode, lastModified, lastModifiedBy, districtId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TalukaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (talukaName != null ? "talukaName=" + talukaName + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (lgdCode != null ? "lgdCode=" + lgdCode + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (districtId != null ? "districtId=" + districtId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
