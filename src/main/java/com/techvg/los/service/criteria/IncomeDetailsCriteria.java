package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.IncomeType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.IncomeDetails} entity. This class is used
 * in {@link com.techvg.los.web.rest.IncomeDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /income-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IncomeDetailsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering IncomeType
     */
    public static class IncomeTypeFilter extends Filter<IncomeType> {

        public IncomeTypeFilter() {}

        public IncomeTypeFilter(IncomeTypeFilter filter) {
            super(filter);
        }

        @Override
        public IncomeTypeFilter copy() {
            return new IncomeTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter year;

    private DoubleFilter grossIncome;

    private DoubleFilter expenses;

    private DoubleFilter netIncome;

    private DoubleFilter income;

    private DoubleFilter paidTaxes;

    private DoubleFilter cashSurplus;

    private IncomeTypeFilter incomeType;

    private BooleanFilter isDeleted;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private StringFilter remark;

    private InstantFilter createdOn;

    private StringFilter otherType;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter freeField5;

    private StringFilter freeField6;

    private LongFilter memberId;

    private Boolean distinct;

    public IncomeDetailsCriteria() {}

    public IncomeDetailsCriteria(IncomeDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.grossIncome = other.grossIncome == null ? null : other.grossIncome.copy();
        this.expenses = other.expenses == null ? null : other.expenses.copy();
        this.netIncome = other.netIncome == null ? null : other.netIncome.copy();
        this.income = other.income == null ? null : other.income.copy();
        this.paidTaxes = other.paidTaxes == null ? null : other.paidTaxes.copy();
        this.cashSurplus = other.cashSurplus == null ? null : other.cashSurplus.copy();
        this.incomeType = other.incomeType == null ? null : other.incomeType.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.otherType = other.otherType == null ? null : other.otherType.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.freeField6 = other.freeField6 == null ? null : other.freeField6.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public IncomeDetailsCriteria copy() {
        return new IncomeDetailsCriteria(this);
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

    public DoubleFilter getGrossIncome() {
        return grossIncome;
    }

    public DoubleFilter grossIncome() {
        if (grossIncome == null) {
            grossIncome = new DoubleFilter();
        }
        return grossIncome;
    }

    public void setGrossIncome(DoubleFilter grossIncome) {
        this.grossIncome = grossIncome;
    }

    public DoubleFilter getExpenses() {
        return expenses;
    }

    public DoubleFilter expenses() {
        if (expenses == null) {
            expenses = new DoubleFilter();
        }
        return expenses;
    }

    public void setExpenses(DoubleFilter expenses) {
        this.expenses = expenses;
    }

    public DoubleFilter getNetIncome() {
        return netIncome;
    }

    public DoubleFilter netIncome() {
        if (netIncome == null) {
            netIncome = new DoubleFilter();
        }
        return netIncome;
    }

    public void setNetIncome(DoubleFilter netIncome) {
        this.netIncome = netIncome;
    }

    public DoubleFilter getIncome() {
        return income;
    }

    public DoubleFilter income() {
        if (income == null) {
            income = new DoubleFilter();
        }
        return income;
    }

    public void setIncome(DoubleFilter income) {
        this.income = netIncome;
    }

    public DoubleFilter getPaidTaxes() {
        return paidTaxes;
    }

    public DoubleFilter paidTaxes() {
        if (paidTaxes == null) {
            paidTaxes = new DoubleFilter();
        }
        return paidTaxes;
    }

    public void setPaidTaxes(DoubleFilter paidTaxes) {
        this.paidTaxes = paidTaxes;
    }

    public DoubleFilter getCashSurplus() {
        return cashSurplus;
    }

    public DoubleFilter cashSurplus() {
        if (cashSurplus == null) {
            cashSurplus = new DoubleFilter();
        }
        return cashSurplus;
    }

    public void setCashSurplus(DoubleFilter cashSurplus) {
        this.cashSurplus = cashSurplus;
    }

    public IncomeTypeFilter getIncomeType() {
        return incomeType;
    }

    public IncomeTypeFilter incomeType() {
        if (incomeType == null) {
            incomeType = new IncomeTypeFilter();
        }
        return incomeType;
    }

    public void setIncomeType(IncomeTypeFilter incomeType) {
        this.incomeType = incomeType;
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

    public StringFilter getOtherType() {
        return otherType;
    }

    public StringFilter otherType() {
        if (otherType == null) {
            otherType = new StringFilter();
        }
        return otherType;
    }

    public void setOtherType(StringFilter otherType) {
        this.otherType = otherType;
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
        final IncomeDetailsCriteria that = (IncomeDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(year, that.year) &&
            Objects.equals(grossIncome, that.grossIncome) &&
            Objects.equals(expenses, that.expenses) &&
            Objects.equals(netIncome, that.netIncome) &&
            Objects.equals(income, that.netIncome) &&
            Objects.equals(paidTaxes, that.paidTaxes) &&
            Objects.equals(cashSurplus, that.cashSurplus) &&
            Objects.equals(incomeType, that.incomeType) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(otherType, that.otherType) &&
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
            year,
            grossIncome,
            expenses,
            netIncome,
            income,
            paidTaxes,
            cashSurplus,
            incomeType,
            isDeleted,
            lastModified,
            lastModifiedBy,
            createdBy,
            remark,
            createdOn,
            otherType,
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
        return "IncomeDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (grossIncome != null ? "grossIncome=" + grossIncome + ", " : "") +
            (expenses != null ? "expenses=" + expenses + ", " : "") +
            (netIncome != null ? "netIncome=" + netIncome + ", " : "") +
            (income != null ? "income=" + income + ", " : "") +
            (paidTaxes != null ? "paidTaxes=" + paidTaxes + ", " : "") +
            (cashSurplus != null ? "cashSurplus=" + cashSurplus + ", " : "") +
            (incomeType != null ? "incomeType=" + incomeType + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (otherType != null ? "otherType=" + otherType + ", " : "") +
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
