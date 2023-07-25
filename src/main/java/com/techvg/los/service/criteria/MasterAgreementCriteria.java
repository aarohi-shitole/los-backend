package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.RepaymentFreqency;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.MasterAgreement} entity. This class is used
 * in {@link com.techvg.los.web.rest.MasterAgreementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /master-agreements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MasterAgreementCriteria implements Serializable, Criteria {

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

    private StringFilter memberId;

    private StringFilter portfolioCode;

    private StringFilter productCode;

    private StringFilter homeBranch;

    private StringFilter servBranch;

    private StringFilter homeState;

    private StringFilter servState;

    private StringFilter gstExempted;

    private StringFilter prefRepayMode;

    private StringFilter tdsCode;

    private StringFilter tdsRate;

    private DoubleFilter sanctionedAmount;

    private StringFilter originationApplnNo;

    private LongFilter cycleDay;

    private StringFilter tenor;

    private DoubleFilter interestRate;

    private RepaymentFreqencyFilter repayFreq;

    private BooleanFilter isDeleted;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private Boolean distinct;

    public MasterAgreementCriteria() {}

    public MasterAgreementCriteria(MasterAgreementCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.portfolioCode = other.portfolioCode == null ? null : other.portfolioCode.copy();
        this.productCode = other.productCode == null ? null : other.productCode.copy();
        this.homeBranch = other.homeBranch == null ? null : other.homeBranch.copy();
        this.servBranch = other.servBranch == null ? null : other.servBranch.copy();
        this.homeState = other.homeState == null ? null : other.homeState.copy();
        this.servState = other.servState == null ? null : other.servState.copy();
        this.gstExempted = other.gstExempted == null ? null : other.gstExempted.copy();
        this.prefRepayMode = other.prefRepayMode == null ? null : other.prefRepayMode.copy();
        this.tdsCode = other.tdsCode == null ? null : other.tdsCode.copy();
        this.tdsRate = other.tdsRate == null ? null : other.tdsRate.copy();
        this.sanctionedAmount = other.sanctionedAmount == null ? null : other.sanctionedAmount.copy();
        this.originationApplnNo = other.originationApplnNo == null ? null : other.originationApplnNo.copy();
        this.cycleDay = other.cycleDay == null ? null : other.cycleDay.copy();
        this.tenor = other.tenor == null ? null : other.tenor.copy();
        this.interestRate = other.interestRate == null ? null : other.interestRate.copy();
        this.repayFreq = other.repayFreq == null ? null : other.repayFreq.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MasterAgreementCriteria copy() {
        return new MasterAgreementCriteria(this);
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

    public StringFilter getMemberId() {
        return memberId;
    }

    public StringFilter memberId() {
        if (memberId == null) {
            memberId = new StringFilter();
        }
        return memberId;
    }

    public void setMemberId(StringFilter memberId) {
        this.memberId = memberId;
    }

    public StringFilter getPortfolioCode() {
        return portfolioCode;
    }

    public StringFilter portfolioCode() {
        if (portfolioCode == null) {
            portfolioCode = new StringFilter();
        }
        return portfolioCode;
    }

    public void setPortfolioCode(StringFilter portfolioCode) {
        this.portfolioCode = portfolioCode;
    }

    public StringFilter getProductCode() {
        return productCode;
    }

    public StringFilter productCode() {
        if (productCode == null) {
            productCode = new StringFilter();
        }
        return productCode;
    }

    public void setProductCode(StringFilter productCode) {
        this.productCode = productCode;
    }

    public StringFilter getHomeBranch() {
        return homeBranch;
    }

    public StringFilter homeBranch() {
        if (homeBranch == null) {
            homeBranch = new StringFilter();
        }
        return homeBranch;
    }

    public void setHomeBranch(StringFilter homeBranch) {
        this.homeBranch = homeBranch;
    }

    public StringFilter getServBranch() {
        return servBranch;
    }

    public StringFilter servBranch() {
        if (servBranch == null) {
            servBranch = new StringFilter();
        }
        return servBranch;
    }

    public void setServBranch(StringFilter servBranch) {
        this.servBranch = servBranch;
    }

    public StringFilter getHomeState() {
        return homeState;
    }

    public StringFilter homeState() {
        if (homeState == null) {
            homeState = new StringFilter();
        }
        return homeState;
    }

    public void setHomeState(StringFilter homeState) {
        this.homeState = homeState;
    }

    public StringFilter getServState() {
        return servState;
    }

    public StringFilter servState() {
        if (servState == null) {
            servState = new StringFilter();
        }
        return servState;
    }

    public void setServState(StringFilter servState) {
        this.servState = servState;
    }

    public StringFilter getGstExempted() {
        return gstExempted;
    }

    public StringFilter gstExempted() {
        if (gstExempted == null) {
            gstExempted = new StringFilter();
        }
        return gstExempted;
    }

    public void setGstExempted(StringFilter gstExempted) {
        this.gstExempted = gstExempted;
    }

    public StringFilter getPrefRepayMode() {
        return prefRepayMode;
    }

    public StringFilter prefRepayMode() {
        if (prefRepayMode == null) {
            prefRepayMode = new StringFilter();
        }
        return prefRepayMode;
    }

    public void setPrefRepayMode(StringFilter prefRepayMode) {
        this.prefRepayMode = prefRepayMode;
    }

    public StringFilter getTdsCode() {
        return tdsCode;
    }

    public StringFilter tdsCode() {
        if (tdsCode == null) {
            tdsCode = new StringFilter();
        }
        return tdsCode;
    }

    public void setTdsCode(StringFilter tdsCode) {
        this.tdsCode = tdsCode;
    }

    public StringFilter getTdsRate() {
        return tdsRate;
    }

    public StringFilter tdsRate() {
        if (tdsRate == null) {
            tdsRate = new StringFilter();
        }
        return tdsRate;
    }

    public void setTdsRate(StringFilter tdsRate) {
        this.tdsRate = tdsRate;
    }

    public DoubleFilter getSanctionedAmount() {
        return sanctionedAmount;
    }

    public DoubleFilter sanctionedAmount() {
        if (sanctionedAmount == null) {
            sanctionedAmount = new DoubleFilter();
        }
        return sanctionedAmount;
    }

    public void setSanctionedAmount(DoubleFilter sanctionedAmount) {
        this.sanctionedAmount = sanctionedAmount;
    }

    public StringFilter getOriginationApplnNo() {
        return originationApplnNo;
    }

    public StringFilter originationApplnNo() {
        if (originationApplnNo == null) {
            originationApplnNo = new StringFilter();
        }
        return originationApplnNo;
    }

    public void setOriginationApplnNo(StringFilter originationApplnNo) {
        this.originationApplnNo = originationApplnNo;
    }

    public LongFilter getCycleDay() {
        return cycleDay;
    }

    public LongFilter cycleDay() {
        if (cycleDay == null) {
            cycleDay = new LongFilter();
        }
        return cycleDay;
    }

    public void setCycleDay(LongFilter cycleDay) {
        this.cycleDay = cycleDay;
    }

    public StringFilter getTenor() {
        return tenor;
    }

    public StringFilter tenor() {
        if (tenor == null) {
            tenor = new StringFilter();
        }
        return tenor;
    }

    public void setTenor(StringFilter tenor) {
        this.tenor = tenor;
    }

    public DoubleFilter getInterestRate() {
        return interestRate;
    }

    public DoubleFilter interestRate() {
        if (interestRate == null) {
            interestRate = new DoubleFilter();
        }
        return interestRate;
    }

    public void setInterestRate(DoubleFilter interestRate) {
        this.interestRate = interestRate;
    }

    public RepaymentFreqencyFilter getRepayFreq() {
        return repayFreq;
    }

    public RepaymentFreqencyFilter repayFreq() {
        if (repayFreq == null) {
            repayFreq = new RepaymentFreqencyFilter();
        }
        return repayFreq;
    }

    public void setRepayFreq(RepaymentFreqencyFilter repayFreq) {
        this.repayFreq = repayFreq;
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
        final MasterAgreementCriteria that = (MasterAgreementCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(portfolioCode, that.portfolioCode) &&
            Objects.equals(productCode, that.productCode) &&
            Objects.equals(homeBranch, that.homeBranch) &&
            Objects.equals(servBranch, that.servBranch) &&
            Objects.equals(homeState, that.homeState) &&
            Objects.equals(servState, that.servState) &&
            Objects.equals(gstExempted, that.gstExempted) &&
            Objects.equals(prefRepayMode, that.prefRepayMode) &&
            Objects.equals(tdsCode, that.tdsCode) &&
            Objects.equals(tdsRate, that.tdsRate) &&
            Objects.equals(sanctionedAmount, that.sanctionedAmount) &&
            Objects.equals(originationApplnNo, that.originationApplnNo) &&
            Objects.equals(cycleDay, that.cycleDay) &&
            Objects.equals(tenor, that.tenor) &&
            Objects.equals(interestRate, that.interestRate) &&
            Objects.equals(repayFreq, that.repayFreq) &&
            Objects.equals(isDeleted, that.isDeleted) &&
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
            memberId,
            portfolioCode,
            productCode,
            homeBranch,
            servBranch,
            homeState,
            servState,
            gstExempted,
            prefRepayMode,
            tdsCode,
            tdsRate,
            sanctionedAmount,
            originationApplnNo,
            cycleDay,
            tenor,
            interestRate,
            repayFreq,
            isDeleted,
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
        return "MasterAgreementCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
            (portfolioCode != null ? "portfolioCode=" + portfolioCode + ", " : "") +
            (productCode != null ? "productCode=" + productCode + ", " : "") +
            (homeBranch != null ? "homeBranch=" + homeBranch + ", " : "") +
            (servBranch != null ? "servBranch=" + servBranch + ", " : "") +
            (homeState != null ? "homeState=" + homeState + ", " : "") +
            (servState != null ? "servState=" + servState + ", " : "") +
            (gstExempted != null ? "gstExempted=" + gstExempted + ", " : "") +
            (prefRepayMode != null ? "prefRepayMode=" + prefRepayMode + ", " : "") +
            (tdsCode != null ? "tdsCode=" + tdsCode + ", " : "") +
            (tdsRate != null ? "tdsRate=" + tdsRate + ", " : "") +
            (sanctionedAmount != null ? "sanctionedAmount=" + sanctionedAmount + ", " : "") +
            (originationApplnNo != null ? "originationApplnNo=" + originationApplnNo + ", " : "") +
            (cycleDay != null ? "cycleDay=" + cycleDay + ", " : "") +
            (tenor != null ? "tenor=" + tenor + ", " : "") +
            (interestRate != null ? "interestRate=" + interestRate + ", " : "") +
            (repayFreq != null ? "repayFreq=" + repayFreq + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
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
