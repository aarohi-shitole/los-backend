package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.VoucherCode;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.VouchersHistory} entity. This class is used
 * in {@link com.techvg.los.web.rest.VouchersHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vouchers-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VouchersHistoryCriteria implements Serializable, Criteria {

    /**
     * Class for filtering VoucherCode
     */
    public static class VoucherCodeFilter extends Filter<VoucherCode> {

        public VoucherCodeFilter() {}

        public VoucherCodeFilter(VoucherCodeFilter filter) {
            super(filter);
        }

        @Override
        public VoucherCodeFilter copy() {
            return new VoucherCodeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdOn;

    private StringFilter createdBy;

    private InstantFilter voucherDate;

    private VoucherCodeFilter code;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter freeField5;

    private StringFilter freeField6;

    private Boolean distinct;

    public VouchersHistoryCriteria() {}

    public VouchersHistoryCriteria(VouchersHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.voucherDate = other.voucherDate == null ? null : other.voucherDate.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.freeField6 = other.freeField6 == null ? null : other.freeField6.copy();
        this.distinct = other.distinct;
    }

    @Override
    public VouchersHistoryCriteria copy() {
        return new VouchersHistoryCriteria(this);
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

    public InstantFilter getVoucherDate() {
        return voucherDate;
    }

    public InstantFilter voucherDate() {
        if (voucherDate == null) {
            voucherDate = new InstantFilter();
        }
        return voucherDate;
    }

    public void setVoucherDate(InstantFilter voucherDate) {
        this.voucherDate = voucherDate;
    }

    public VoucherCodeFilter getCode() {
        return code;
    }

    public VoucherCodeFilter code() {
        if (code == null) {
            code = new VoucherCodeFilter();
        }
        return code;
    }

    public void setCode(VoucherCodeFilter code) {
        this.code = code;
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
        final VouchersHistoryCriteria that = (VouchersHistoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(voucherDate, that.voucherDate) &&
            Objects.equals(code, that.code) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(freeField6, that.freeField6) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            createdOn,
            createdBy,
            voucherDate,
            code,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            freeField5,
            freeField6,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VouchersHistoryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (voucherDate != null ? "voucherDate=" + voucherDate + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (freeField5 != null ? "freeField5=" + freeField5 + ", " : "") +
            (freeField6 != null ? "freeField6=" + freeField6 + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
