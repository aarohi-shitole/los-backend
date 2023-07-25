package com.techvg.los.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.AmortizationDetails} entity. This class is used
 * in {@link com.techvg.los.web.rest.AmortizationDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /amortization-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AmortizationDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter installmentDate;

    private DoubleFilter totalOutstandingPrincipleAmt;

    private DoubleFilter totalOutstandngInterestAmt;

    private DoubleFilter paidPrincipleAmt;

    private DoubleFilter paidInterestAmt;

    private DoubleFilter balPrincipleAmt;

    private DoubleFilter balInterestAmt;

    private DoubleFilter loanEmiAmt;

    private DoubleFilter principleEMI;

    private DoubleFilter roi;

    private StringFilter paymentStatus;

    private StringFilter year;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter freeField5;

    private StringFilter freeField6;

    private LongFilter loanAccountId;

    private Boolean distinct;

    public AmortizationDetailsCriteria() {}

    public AmortizationDetailsCriteria(AmortizationDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.installmentDate = other.installmentDate == null ? null : other.installmentDate.copy();
        this.totalOutstandingPrincipleAmt = other.totalOutstandingPrincipleAmt == null ? null : other.totalOutstandingPrincipleAmt.copy();
        this.totalOutstandngInterestAmt = other.totalOutstandngInterestAmt == null ? null : other.totalOutstandngInterestAmt.copy();
        this.paidPrincipleAmt = other.paidPrincipleAmt == null ? null : other.paidPrincipleAmt.copy();
        this.paidInterestAmt = other.paidInterestAmt == null ? null : other.paidInterestAmt.copy();
        this.balPrincipleAmt = other.balPrincipleAmt == null ? null : other.balPrincipleAmt.copy();
        this.balInterestAmt = other.balInterestAmt == null ? null : other.balInterestAmt.copy();
        this.loanEmiAmt = other.loanEmiAmt == null ? null : other.loanEmiAmt.copy();
        this.principleEMI = other.principleEMI == null ? null : other.principleEMI.copy();
        this.roi = other.roi == null ? null : other.roi.copy();
        this.paymentStatus = other.paymentStatus == null ? null : other.paymentStatus.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.freeField6 = other.freeField6 == null ? null : other.freeField6.copy();
        this.loanAccountId = other.loanAccountId == null ? null : other.loanAccountId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AmortizationDetailsCriteria copy() {
        return new AmortizationDetailsCriteria(this);
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

    public InstantFilter getInstallmentDate() {
        return installmentDate;
    }

    public InstantFilter installmentDate() {
        if (installmentDate == null) {
            installmentDate = new InstantFilter();
        }
        return installmentDate;
    }

    public void setInstallmentDate(InstantFilter installmentDate) {
        this.installmentDate = installmentDate;
    }

    public DoubleFilter getTotalOutstandingPrincipleAmt() {
        return totalOutstandingPrincipleAmt;
    }

    public DoubleFilter totalOutstandingPrincipleAmt() {
        if (totalOutstandingPrincipleAmt == null) {
            totalOutstandingPrincipleAmt = new DoubleFilter();
        }
        return totalOutstandingPrincipleAmt;
    }

    public void setTotalOutstandingPrincipleAmt(DoubleFilter totalOutstandingPrincipleAmt) {
        this.totalOutstandingPrincipleAmt = totalOutstandingPrincipleAmt;
    }

    public DoubleFilter getTotalOutstandngInterestAmt() {
        return totalOutstandngInterestAmt;
    }

    public DoubleFilter totalOutstandngInterestAmt() {
        if (totalOutstandngInterestAmt == null) {
            totalOutstandngInterestAmt = new DoubleFilter();
        }
        return totalOutstandngInterestAmt;
    }

    public void setTotalOutstandngInterestAmt(DoubleFilter totalOutstandngInterestAmt) {
        this.totalOutstandngInterestAmt = totalOutstandngInterestAmt;
    }

    public DoubleFilter getPaidPrincipleAmt() {
        return paidPrincipleAmt;
    }

    public DoubleFilter paidPrincipleAmt() {
        if (paidPrincipleAmt == null) {
            paidPrincipleAmt = new DoubleFilter();
        }
        return paidPrincipleAmt;
    }

    public void setPaidPrincipleAmt(DoubleFilter paidPrincipleAmt) {
        this.paidPrincipleAmt = paidPrincipleAmt;
    }

    public DoubleFilter getPaidInterestAmt() {
        return paidInterestAmt;
    }

    public DoubleFilter paidInterestAmt() {
        if (paidInterestAmt == null) {
            paidInterestAmt = new DoubleFilter();
        }
        return paidInterestAmt;
    }

    public void setPaidInterestAmt(DoubleFilter paidInterestAmt) {
        this.paidInterestAmt = paidInterestAmt;
    }

    public DoubleFilter getBalPrincipleAmt() {
        return balPrincipleAmt;
    }

    public DoubleFilter balPrincipleAmt() {
        if (balPrincipleAmt == null) {
            balPrincipleAmt = new DoubleFilter();
        }
        return balPrincipleAmt;
    }

    public void setBalPrincipleAmt(DoubleFilter balPrincipleAmt) {
        this.balPrincipleAmt = balPrincipleAmt;
    }

    public DoubleFilter getBalInterestAmt() {
        return balInterestAmt;
    }

    public DoubleFilter balInterestAmt() {
        if (balInterestAmt == null) {
            balInterestAmt = new DoubleFilter();
        }
        return balInterestAmt;
    }

    public void setBalInterestAmt(DoubleFilter balInterestAmt) {
        this.balInterestAmt = balInterestAmt;
    }

    public DoubleFilter getLoanEmiAmt() {
        return loanEmiAmt;
    }

    public DoubleFilter loanEmiAmt() {
        if (loanEmiAmt == null) {
            loanEmiAmt = new DoubleFilter();
        }
        return loanEmiAmt;
    }

    public void setLoanEmiAmt(DoubleFilter loanEmiAmt) {
        this.loanEmiAmt = loanEmiAmt;
    }

    public DoubleFilter getPrincipleEMI() {
        return principleEMI;
    }

    public DoubleFilter principleEMI() {
        if (principleEMI == null) {
            principleEMI = new DoubleFilter();
        }
        return principleEMI;
    }

    public void setPrincipleEMI(DoubleFilter principleEMI) {
        this.principleEMI = principleEMI;
    }

    public DoubleFilter getRoi() {
        return roi;
    }

    public DoubleFilter roi() {
        if (roi == null) {
            roi = new DoubleFilter();
        }
        return roi;
    }

    public void setRoi(DoubleFilter roi) {
        this.roi = roi;
    }

    public StringFilter getPaymentStatus() {
        return paymentStatus;
    }

    public StringFilter paymentStatus() {
        if (paymentStatus == null) {
            paymentStatus = new StringFilter();
        }
        return paymentStatus;
    }

    public void setPaymentStatus(StringFilter paymentStatus) {
        this.paymentStatus = paymentStatus;
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

    public LongFilter getLoanAccountId() {
        return loanAccountId;
    }

    public LongFilter loanAccountId() {
        if (loanAccountId == null) {
            loanAccountId = new LongFilter();
        }
        return loanAccountId;
    }

    public void setLoanAccountId(LongFilter loanAccountId) {
        this.loanAccountId = loanAccountId;
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
        final AmortizationDetailsCriteria that = (AmortizationDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(installmentDate, that.installmentDate) &&
            Objects.equals(totalOutstandingPrincipleAmt, that.totalOutstandingPrincipleAmt) &&
            Objects.equals(totalOutstandngInterestAmt, that.totalOutstandngInterestAmt) &&
            Objects.equals(paidPrincipleAmt, that.paidPrincipleAmt) &&
            Objects.equals(paidInterestAmt, that.paidInterestAmt) &&
            Objects.equals(balPrincipleAmt, that.balPrincipleAmt) &&
            Objects.equals(balInterestAmt, that.balInterestAmt) &&
            Objects.equals(loanEmiAmt, that.loanEmiAmt) &&
            Objects.equals(principleEMI, that.principleEMI) &&
            Objects.equals(roi, that.roi) &&
            Objects.equals(paymentStatus, that.paymentStatus) &&
            Objects.equals(year, that.year) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(freeField6, that.freeField6) &&
            Objects.equals(loanAccountId, that.loanAccountId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            installmentDate,
            totalOutstandingPrincipleAmt,
            totalOutstandngInterestAmt,
            paidPrincipleAmt,
            paidInterestAmt,
            balPrincipleAmt,
            balInterestAmt,
            loanEmiAmt,
            principleEMI,
            roi,
            paymentStatus,
            year,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            freeField5,
            freeField6,
            loanAccountId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AmortizationDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (installmentDate != null ? "installmentDate=" + installmentDate + ", " : "") +
            (totalOutstandingPrincipleAmt != null ? "totalOutstandingPrincipleAmt=" + totalOutstandingPrincipleAmt + ", " : "") +
            (totalOutstandngInterestAmt != null ? "totalOutstandngInterestAmt=" + totalOutstandngInterestAmt + ", " : "") +
            (paidPrincipleAmt != null ? "paidPrincipleAmt=" + paidPrincipleAmt + ", " : "") +
            (paidInterestAmt != null ? "paidInterestAmt=" + paidInterestAmt + ", " : "") +
            (balPrincipleAmt != null ? "balPrincipleAmt=" + balPrincipleAmt + ", " : "") +
            (balInterestAmt != null ? "balInterestAmt=" + balInterestAmt + ", " : "") +
            (loanEmiAmt != null ? "loanEmiAmt=" + loanEmiAmt + ", " : "") +
            (principleEMI != null ? "principleEMI=" + principleEMI + ", " : "") +
            (roi != null ? "roi=" + roi + ", " : "") +
            (paymentStatus != null ? "paymentStatus=" + paymentStatus + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (freeField5 != null ? "freeField5=" + freeField5 + ", " : "") +
            (freeField6 != null ? "freeField6=" + freeField6 + ", " : "") +
            (loanAccountId != null ? "loanAccountId=" + loanAccountId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
