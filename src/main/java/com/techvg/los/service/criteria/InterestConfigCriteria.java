package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.InterestType;
import com.techvg.los.domain.enumeration.RepaymentFreqency;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.InterestConfig} entity. This class is used
 * in {@link com.techvg.los.web.rest.InterestConfigResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /interest-configs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InterestConfigCriteria implements Serializable, Criteria {

    /**
     * Class for filtering InterestType
     */
    public static class InterestTypeFilter extends Filter<InterestType> {

        public InterestTypeFilter() {}

        public InterestTypeFilter(InterestTypeFilter filter) {
            super(filter);
        }

        @Override
        public InterestTypeFilter copy() {
            return new InterestTypeFilter(this);
        }
    }

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

    private InstantFilter startDate;

    private InstantFilter endDate;

    private StringFilter interestBasis;

    private StringFilter emiBasis;

    private InterestTypeFilter interestType;

    private RepaymentFreqencyFilter intrAccrualFreq;

    private DoubleFilter penalInterestRate;

    private StringFilter penalInterestBasis;

    private StringFilter penalAccountingBasis;

    private DoubleFilter minInterestRate;

    private DoubleFilter maxInterestRate;

    private DoubleFilter extendedInterestRate;

    private DoubleFilter surchargeRate;

    private BooleanFilter isDeleted;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter freeField5;

    private StringFilter freeField6;

    private LongFilter productId;

    private Boolean distinct;

    public InterestConfigCriteria() {}

    public InterestConfigCriteria(InterestConfigCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.interestBasis = other.interestBasis == null ? null : other.interestBasis.copy();
        this.emiBasis = other.emiBasis == null ? null : other.emiBasis.copy();
        this.interestType = other.interestType == null ? null : other.interestType.copy();
        this.intrAccrualFreq = other.intrAccrualFreq == null ? null : other.intrAccrualFreq.copy();
        this.penalInterestRate = other.penalInterestRate == null ? null : other.penalInterestRate.copy();
        this.penalInterestBasis = other.penalInterestBasis == null ? null : other.penalInterestBasis.copy();
        this.penalAccountingBasis = other.penalAccountingBasis == null ? null : other.penalAccountingBasis.copy();
        this.minInterestRate = other.minInterestRate == null ? null : other.minInterestRate.copy();
        this.maxInterestRate = other.maxInterestRate == null ? null : other.maxInterestRate.copy();
        this.extendedInterestRate = other.extendedInterestRate == null ? null : other.extendedInterestRate.copy();
        this.surchargeRate = other.surchargeRate == null ? null : other.surchargeRate.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.freeField6 = other.freeField6 == null ? null : other.freeField6.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public InterestConfigCriteria copy() {
        return new InterestConfigCriteria(this);
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

    public InstantFilter getStartDate() {
        return startDate;
    }

    public InstantFilter startDate() {
        if (startDate == null) {
            startDate = new InstantFilter();
        }
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public InstantFilter getEndDate() {
        return endDate;
    }

    public InstantFilter endDate() {
        if (endDate == null) {
            endDate = new InstantFilter();
        }
        return endDate;
    }

    public void setEndDate(InstantFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getInterestBasis() {
        return interestBasis;
    }

    public StringFilter interestBasis() {
        if (interestBasis == null) {
            interestBasis = new StringFilter();
        }
        return interestBasis;
    }

    public void setInterestBasis(StringFilter interestBasis) {
        this.interestBasis = interestBasis;
    }

    public StringFilter getEmiBasis() {
        return emiBasis;
    }

    public StringFilter emiBasis() {
        if (emiBasis == null) {
            emiBasis = new StringFilter();
        }
        return emiBasis;
    }

    public void setEmiBasis(StringFilter emiBasis) {
        this.emiBasis = emiBasis;
    }

    public InterestTypeFilter getInterestType() {
        return interestType;
    }

    public InterestTypeFilter interestType() {
        if (interestType == null) {
            interestType = new InterestTypeFilter();
        }
        return interestType;
    }

    public void setInterestType(InterestTypeFilter interestType) {
        this.interestType = interestType;
    }

    public RepaymentFreqencyFilter getIntrAccrualFreq() {
        return intrAccrualFreq;
    }

    public RepaymentFreqencyFilter intrAccrualFreq() {
        if (intrAccrualFreq == null) {
            intrAccrualFreq = new RepaymentFreqencyFilter();
        }
        return intrAccrualFreq;
    }

    public void setIntrAccrualFreq(RepaymentFreqencyFilter intrAccrualFreq) {
        this.intrAccrualFreq = intrAccrualFreq;
    }

    public DoubleFilter getPenalInterestRate() {
        return penalInterestRate;
    }

    public DoubleFilter penalInterestRate() {
        if (penalInterestRate == null) {
            penalInterestRate = new DoubleFilter();
        }
        return penalInterestRate;
    }

    public void setPenalInterestRate(DoubleFilter penalInterestRate) {
        this.penalInterestRate = penalInterestRate;
    }

    public StringFilter getPenalInterestBasis() {
        return penalInterestBasis;
    }

    public StringFilter penalInterestBasis() {
        if (penalInterestBasis == null) {
            penalInterestBasis = new StringFilter();
        }
        return penalInterestBasis;
    }

    public void setPenalInterestBasis(StringFilter penalInterestBasis) {
        this.penalInterestBasis = penalInterestBasis;
    }

    public StringFilter getPenalAccountingBasis() {
        return penalAccountingBasis;
    }

    public StringFilter penalAccountingBasis() {
        if (penalAccountingBasis == null) {
            penalAccountingBasis = new StringFilter();
        }
        return penalAccountingBasis;
    }

    public void setPenalAccountingBasis(StringFilter penalAccountingBasis) {
        this.penalAccountingBasis = penalAccountingBasis;
    }

    public DoubleFilter getMinInterestRate() {
        return minInterestRate;
    }

    public DoubleFilter minInterestRate() {
        if (minInterestRate == null) {
            minInterestRate = new DoubleFilter();
        }
        return minInterestRate;
    }

    public void setMinInterestRate(DoubleFilter minInterestRate) {
        this.minInterestRate = minInterestRate;
    }

    public DoubleFilter getMaxInterestRate() {
        return maxInterestRate;
    }

    public DoubleFilter maxInterestRate() {
        if (maxInterestRate == null) {
            maxInterestRate = new DoubleFilter();
        }
        return maxInterestRate;
    }

    public void setMaxInterestRate(DoubleFilter maxInterestRate) {
        this.maxInterestRate = maxInterestRate;
    }

    public DoubleFilter getExtendedInterestRate() {
        return extendedInterestRate;
    }

    public DoubleFilter extendedInterestRate() {
        if (extendedInterestRate == null) {
            extendedInterestRate = new DoubleFilter();
        }
        return extendedInterestRate;
    }

    public void setExtendedInterestRate(DoubleFilter extendedInterestRate) {
        this.extendedInterestRate = extendedInterestRate;
    }

    public DoubleFilter getSurchargeRate() {
        return surchargeRate;
    }

    public DoubleFilter surchargeRate() {
        if (surchargeRate == null) {
            surchargeRate = new DoubleFilter();
        }
        return surchargeRate;
    }

    public void setSurchargeRate(DoubleFilter surchargeRate) {
        this.surchargeRate = surchargeRate;
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
        final InterestConfigCriteria that = (InterestConfigCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(interestBasis, that.interestBasis) &&
            Objects.equals(emiBasis, that.emiBasis) &&
            Objects.equals(interestType, that.interestType) &&
            Objects.equals(intrAccrualFreq, that.intrAccrualFreq) &&
            Objects.equals(penalInterestRate, that.penalInterestRate) &&
            Objects.equals(penalInterestBasis, that.penalInterestBasis) &&
            Objects.equals(penalAccountingBasis, that.penalAccountingBasis) &&
            Objects.equals(minInterestRate, that.minInterestRate) &&
            Objects.equals(maxInterestRate, that.maxInterestRate) &&
            Objects.equals(extendedInterestRate, that.extendedInterestRate) &&
            Objects.equals(surchargeRate, that.surchargeRate) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(freeField6, that.freeField6) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            startDate,
            endDate,
            interestBasis,
            emiBasis,
            interestType,
            intrAccrualFreq,
            penalInterestRate,
            penalInterestBasis,
            penalAccountingBasis,
            minInterestRate,
            maxInterestRate,
            extendedInterestRate,
            surchargeRate,
            isDeleted,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            freeField5,
            freeField6,
            productId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterestConfigCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (interestBasis != null ? "interestBasis=" + interestBasis + ", " : "") +
            (emiBasis != null ? "emiBasis=" + emiBasis + ", " : "") +
            (interestType != null ? "interestType=" + interestType + ", " : "") +
            (intrAccrualFreq != null ? "intrAccrualFreq=" + intrAccrualFreq + ", " : "") +
            (penalInterestRate != null ? "penalInterestRate=" + penalInterestRate + ", " : "") +
            (penalInterestBasis != null ? "penalInterestBasis=" + penalInterestBasis + ", " : "") +
            (penalAccountingBasis != null ? "penalAccountingBasis=" + penalAccountingBasis + ", " : "") +
            (minInterestRate != null ? "minInterestRate=" + minInterestRate + ", " : "") +
            (maxInterestRate != null ? "maxInterestRate=" + maxInterestRate + ", " : "") +
            (extendedInterestRate != null ? "extendedInterestRate=" + extendedInterestRate + ", " : "") +
            (surchargeRate != null ? "surchargeRate=" + surchargeRate + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (freeField5 != null ? "freeField5=" + freeField5 + ", " : "") +
            (freeField6 != null ? "freeField6=" + freeField6 + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
