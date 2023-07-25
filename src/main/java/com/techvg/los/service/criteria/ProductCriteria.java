package com.techvg.los.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.Product} entity. This class is used
 * in {@link com.techvg.los.web.rest.ProductResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /products?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter prodCode;

    private StringFilter prodName;

    private StringFilter bpiTreatmentFlag;

    private StringFilter amortizationMethod;

    private StringFilter amortizationType;

    private StringFilter compoundingFreq;

    private StringFilter emiRounding;

    private DoubleFilter lastRowEMIThreshold;

    private LongFilter graceDays;

    private LongFilter reschLockinPeriod;

    private LongFilter prepayAfterInstNo;

    private LongFilter prepayBeforeInstNo;

    private LongFilter minInstallmentGapBetPrepay;

    private DoubleFilter minPrepayAmount;

    private DoubleFilter forecloseAfterInstNo;

    private DoubleFilter forecloseBeforeInstaNo;

    private DoubleFilter minTenor;

    private DoubleFilter maxTenor;

    private DoubleFilter minInstallmentAmount;

    private DoubleFilter maxInstallmentAmount;

    private DoubleFilter dropLineAmount;

    private StringFilter dropLineODYN;

    private LongFilter dropLinePerc;

    private StringFilter dropMode;

    private StringFilter dropLIneFreq;

    private LongFilter dropLineCycleDay;

    private DoubleFilter roi;

    private BooleanFilter isDeleted;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter prodColour;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter freeField5;

    private StringFilter prodIconUrl;

    private LongFilter loanCatagoryId;

    private LongFilter organisationId;

    private LongFilter ledgerAccountsId;

    private Boolean distinct;

    public ProductCriteria() {}

    public ProductCriteria(ProductCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.prodCode = other.prodCode == null ? null : other.prodCode.copy();
        this.prodName = other.prodName == null ? null : other.prodName.copy();
        this.bpiTreatmentFlag = other.bpiTreatmentFlag == null ? null : other.bpiTreatmentFlag.copy();
        this.amortizationMethod = other.amortizationMethod == null ? null : other.amortizationMethod.copy();
        this.amortizationType = other.amortizationType == null ? null : other.amortizationType.copy();
        this.compoundingFreq = other.compoundingFreq == null ? null : other.compoundingFreq.copy();
        this.emiRounding = other.emiRounding == null ? null : other.emiRounding.copy();
        this.lastRowEMIThreshold = other.lastRowEMIThreshold == null ? null : other.lastRowEMIThreshold.copy();
        this.graceDays = other.graceDays == null ? null : other.graceDays.copy();
        this.reschLockinPeriod = other.reschLockinPeriod == null ? null : other.reschLockinPeriod.copy();
        this.prepayAfterInstNo = other.prepayAfterInstNo == null ? null : other.prepayAfterInstNo.copy();
        this.prepayBeforeInstNo = other.prepayBeforeInstNo == null ? null : other.prepayBeforeInstNo.copy();
        this.minInstallmentGapBetPrepay = other.minInstallmentGapBetPrepay == null ? null : other.minInstallmentGapBetPrepay.copy();
        this.minPrepayAmount = other.minPrepayAmount == null ? null : other.minPrepayAmount.copy();
        this.forecloseAfterInstNo = other.forecloseAfterInstNo == null ? null : other.forecloseAfterInstNo.copy();
        this.forecloseBeforeInstaNo = other.forecloseBeforeInstaNo == null ? null : other.forecloseBeforeInstaNo.copy();
        this.minTenor = other.minTenor == null ? null : other.minTenor.copy();
        this.maxTenor = other.maxTenor == null ? null : other.maxTenor.copy();
        this.minInstallmentAmount = other.minInstallmentAmount == null ? null : other.minInstallmentAmount.copy();
        this.maxInstallmentAmount = other.maxInstallmentAmount == null ? null : other.maxInstallmentAmount.copy();
        this.dropLineAmount = other.dropLineAmount == null ? null : other.dropLineAmount.copy();
        this.dropLineODYN = other.dropLineODYN == null ? null : other.dropLineODYN.copy();
        this.dropLinePerc = other.dropLinePerc == null ? null : other.dropLinePerc.copy();
        this.dropMode = other.dropMode == null ? null : other.dropMode.copy();
        this.dropLIneFreq = other.dropLIneFreq == null ? null : other.dropLIneFreq.copy();
        this.dropLineCycleDay = other.dropLineCycleDay == null ? null : other.dropLineCycleDay.copy();
        this.roi = other.roi == null ? null : other.roi.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.prodColour = other.prodColour == null ? null : other.prodColour.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.prodIconUrl = other.prodIconUrl == null ? null : other.prodIconUrl.copy();
        this.loanCatagoryId = other.loanCatagoryId == null ? null : other.loanCatagoryId.copy();
        this.organisationId = other.organisationId == null ? null : other.organisationId.copy();
        this.ledgerAccountsId = other.ledgerAccountsId == null ? null : other.ledgerAccountsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProductCriteria copy() {
        return new ProductCriteria(this);
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

    public StringFilter getProdCode() {
        return prodCode;
    }

    public StringFilter prodCode() {
        if (prodCode == null) {
            prodCode = new StringFilter();
        }
        return prodCode;
    }

    public void setProdCode(StringFilter prodCode) {
        this.prodCode = prodCode;
    }

    public StringFilter getProdName() {
        return prodName;
    }

    public StringFilter prodName() {
        if (prodName == null) {
            prodName = new StringFilter();
        }
        return prodName;
    }

    public void setProdName(StringFilter prodName) {
        this.prodName = prodName;
    }

    public StringFilter getBpiTreatmentFlag() {
        return bpiTreatmentFlag;
    }

    public StringFilter bpiTreatmentFlag() {
        if (bpiTreatmentFlag == null) {
            bpiTreatmentFlag = new StringFilter();
        }
        return bpiTreatmentFlag;
    }

    public void setBpiTreatmentFlag(StringFilter bpiTreatmentFlag) {
        this.bpiTreatmentFlag = bpiTreatmentFlag;
    }

    public StringFilter getAmortizationMethod() {
        return amortizationMethod;
    }

    public StringFilter amortizationMethod() {
        if (amortizationMethod == null) {
            amortizationMethod = new StringFilter();
        }
        return amortizationMethod;
    }

    public void setAmortizationMethod(StringFilter amortizationMethod) {
        this.amortizationMethod = amortizationMethod;
    }

    public StringFilter getAmortizationType() {
        return amortizationType;
    }

    public StringFilter amortizationType() {
        if (amortizationType == null) {
            amortizationType = new StringFilter();
        }
        return amortizationType;
    }

    public void setAmortizationType(StringFilter amortizationType) {
        this.amortizationType = amortizationType;
    }

    public StringFilter getCompoundingFreq() {
        return compoundingFreq;
    }

    public StringFilter compoundingFreq() {
        if (compoundingFreq == null) {
            compoundingFreq = new StringFilter();
        }
        return compoundingFreq;
    }

    public void setCompoundingFreq(StringFilter compoundingFreq) {
        this.compoundingFreq = compoundingFreq;
    }

    public StringFilter getEmiRounding() {
        return emiRounding;
    }

    public StringFilter emiRounding() {
        if (emiRounding == null) {
            emiRounding = new StringFilter();
        }
        return emiRounding;
    }

    public void setEmiRounding(StringFilter emiRounding) {
        this.emiRounding = emiRounding;
    }

    public DoubleFilter getLastRowEMIThreshold() {
        return lastRowEMIThreshold;
    }

    public DoubleFilter lastRowEMIThreshold() {
        if (lastRowEMIThreshold == null) {
            lastRowEMIThreshold = new DoubleFilter();
        }
        return lastRowEMIThreshold;
    }

    public void setLastRowEMIThreshold(DoubleFilter lastRowEMIThreshold) {
        this.lastRowEMIThreshold = lastRowEMIThreshold;
    }

    public LongFilter getGraceDays() {
        return graceDays;
    }

    public LongFilter graceDays() {
        if (graceDays == null) {
            graceDays = new LongFilter();
        }
        return graceDays;
    }

    public void setGraceDays(LongFilter graceDays) {
        this.graceDays = graceDays;
    }

    public LongFilter getReschLockinPeriod() {
        return reschLockinPeriod;
    }

    public LongFilter reschLockinPeriod() {
        if (reschLockinPeriod == null) {
            reschLockinPeriod = new LongFilter();
        }
        return reschLockinPeriod;
    }

    public void setReschLockinPeriod(LongFilter reschLockinPeriod) {
        this.reschLockinPeriod = reschLockinPeriod;
    }

    public LongFilter getPrepayAfterInstNo() {
        return prepayAfterInstNo;
    }

    public LongFilter prepayAfterInstNo() {
        if (prepayAfterInstNo == null) {
            prepayAfterInstNo = new LongFilter();
        }
        return prepayAfterInstNo;
    }

    public void setPrepayAfterInstNo(LongFilter prepayAfterInstNo) {
        this.prepayAfterInstNo = prepayAfterInstNo;
    }

    public LongFilter getPrepayBeforeInstNo() {
        return prepayBeforeInstNo;
    }

    public LongFilter prepayBeforeInstNo() {
        if (prepayBeforeInstNo == null) {
            prepayBeforeInstNo = new LongFilter();
        }
        return prepayBeforeInstNo;
    }

    public void setPrepayBeforeInstNo(LongFilter prepayBeforeInstNo) {
        this.prepayBeforeInstNo = prepayBeforeInstNo;
    }

    public LongFilter getMinInstallmentGapBetPrepay() {
        return minInstallmentGapBetPrepay;
    }

    public LongFilter minInstallmentGapBetPrepay() {
        if (minInstallmentGapBetPrepay == null) {
            minInstallmentGapBetPrepay = new LongFilter();
        }
        return minInstallmentGapBetPrepay;
    }

    public void setMinInstallmentGapBetPrepay(LongFilter minInstallmentGapBetPrepay) {
        this.minInstallmentGapBetPrepay = minInstallmentGapBetPrepay;
    }

    public DoubleFilter getMinPrepayAmount() {
        return minPrepayAmount;
    }

    public DoubleFilter minPrepayAmount() {
        if (minPrepayAmount == null) {
            minPrepayAmount = new DoubleFilter();
        }
        return minPrepayAmount;
    }

    public void setMinPrepayAmount(DoubleFilter minPrepayAmount) {
        this.minPrepayAmount = minPrepayAmount;
    }

    public DoubleFilter getForecloseAfterInstNo() {
        return forecloseAfterInstNo;
    }

    public DoubleFilter forecloseAfterInstNo() {
        if (forecloseAfterInstNo == null) {
            forecloseAfterInstNo = new DoubleFilter();
        }
        return forecloseAfterInstNo;
    }

    public void setForecloseAfterInstNo(DoubleFilter forecloseAfterInstNo) {
        this.forecloseAfterInstNo = forecloseAfterInstNo;
    }

    public DoubleFilter getForecloseBeforeInstaNo() {
        return forecloseBeforeInstaNo;
    }

    public DoubleFilter forecloseBeforeInstaNo() {
        if (forecloseBeforeInstaNo == null) {
            forecloseBeforeInstaNo = new DoubleFilter();
        }
        return forecloseBeforeInstaNo;
    }

    public void setForecloseBeforeInstaNo(DoubleFilter forecloseBeforeInstaNo) {
        this.forecloseBeforeInstaNo = forecloseBeforeInstaNo;
    }

    public DoubleFilter getMinTenor() {
        return minTenor;
    }

    public DoubleFilter minTenor() {
        if (minTenor == null) {
            minTenor = new DoubleFilter();
        }
        return minTenor;
    }

    public void setMinTenor(DoubleFilter minTenor) {
        this.minTenor = minTenor;
    }

    public DoubleFilter getMaxTenor() {
        return maxTenor;
    }

    public DoubleFilter maxTenor() {
        if (maxTenor == null) {
            maxTenor = new DoubleFilter();
        }
        return maxTenor;
    }

    public void setMaxTenor(DoubleFilter maxTenor) {
        this.maxTenor = maxTenor;
    }

    public DoubleFilter getMinInstallmentAmount() {
        return minInstallmentAmount;
    }

    public DoubleFilter minInstallmentAmount() {
        if (minInstallmentAmount == null) {
            minInstallmentAmount = new DoubleFilter();
        }
        return minInstallmentAmount;
    }

    public void setMinInstallmentAmount(DoubleFilter minInstallmentAmount) {
        this.minInstallmentAmount = minInstallmentAmount;
    }

    public DoubleFilter getMaxInstallmentAmount() {
        return maxInstallmentAmount;
    }

    public DoubleFilter maxInstallmentAmount() {
        if (maxInstallmentAmount == null) {
            maxInstallmentAmount = new DoubleFilter();
        }
        return maxInstallmentAmount;
    }

    public void setMaxInstallmentAmount(DoubleFilter maxInstallmentAmount) {
        this.maxInstallmentAmount = maxInstallmentAmount;
    }

    public DoubleFilter getDropLineAmount() {
        return dropLineAmount;
    }

    public DoubleFilter dropLineAmount() {
        if (dropLineAmount == null) {
            dropLineAmount = new DoubleFilter();
        }
        return dropLineAmount;
    }

    public void setDropLineAmount(DoubleFilter dropLineAmount) {
        this.dropLineAmount = dropLineAmount;
    }

    public StringFilter getDropLineODYN() {
        return dropLineODYN;
    }

    public StringFilter dropLineODYN() {
        if (dropLineODYN == null) {
            dropLineODYN = new StringFilter();
        }
        return dropLineODYN;
    }

    public void setDropLineODYN(StringFilter dropLineODYN) {
        this.dropLineODYN = dropLineODYN;
    }

    public LongFilter getDropLinePerc() {
        return dropLinePerc;
    }

    public LongFilter dropLinePerc() {
        if (dropLinePerc == null) {
            dropLinePerc = new LongFilter();
        }
        return dropLinePerc;
    }

    public void setDropLinePerc(LongFilter dropLinePerc) {
        this.dropLinePerc = dropLinePerc;
    }

    public StringFilter getDropMode() {
        return dropMode;
    }

    public StringFilter dropMode() {
        if (dropMode == null) {
            dropMode = new StringFilter();
        }
        return dropMode;
    }

    public void setDropMode(StringFilter dropMode) {
        this.dropMode = dropMode;
    }

    public StringFilter getDropLIneFreq() {
        return dropLIneFreq;
    }

    public StringFilter dropLIneFreq() {
        if (dropLIneFreq == null) {
            dropLIneFreq = new StringFilter();
        }
        return dropLIneFreq;
    }

    public void setDropLIneFreq(StringFilter dropLIneFreq) {
        this.dropLIneFreq = dropLIneFreq;
    }

    public LongFilter getDropLineCycleDay() {
        return dropLineCycleDay;
    }

    public LongFilter dropLineCycleDay() {
        if (dropLineCycleDay == null) {
            dropLineCycleDay = new LongFilter();
        }
        return dropLineCycleDay;
    }

    public void setDropLineCycleDay(LongFilter dropLineCycleDay) {
        this.dropLineCycleDay = dropLineCycleDay;
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

    public StringFilter getProdColour() {
        return prodColour;
    }

    public StringFilter prodColour() {
        if (prodColour == null) {
            prodColour = new StringFilter();
        }
        return prodColour;
    }

    public void setProdColour(StringFilter prodColour) {
        this.prodColour = prodColour;
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

    public StringFilter getProdIconUrl() {
        return prodIconUrl;
    }

    public StringFilter prodIconUrl() {
        if (prodIconUrl == null) {
            prodIconUrl = new StringFilter();
        }
        return prodIconUrl;
    }

    public void setProdIconUrl(StringFilter prodIconUrl) {
        this.prodIconUrl = prodIconUrl;
    }

    public LongFilter getLoanCatagoryId() {
        return loanCatagoryId;
    }

    public LongFilter loanCatagoryId() {
        if (loanCatagoryId == null) {
            loanCatagoryId = new LongFilter();
        }
        return loanCatagoryId;
    }

    public void setLoanCatagoryId(LongFilter loanCatagoryId) {
        this.loanCatagoryId = loanCatagoryId;
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

    public LongFilter getLedgerAccountsId() {
        return ledgerAccountsId;
    }

    public LongFilter ledgerAccountsId() {
        if (ledgerAccountsId == null) {
            ledgerAccountsId = new LongFilter();
        }
        return ledgerAccountsId;
    }

    public void setLedgerAccountsId(LongFilter ledgerAccountsId) {
        this.ledgerAccountsId = ledgerAccountsId;
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
        final ProductCriteria that = (ProductCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(prodCode, that.prodCode) &&
            Objects.equals(prodName, that.prodName) &&
            Objects.equals(bpiTreatmentFlag, that.bpiTreatmentFlag) &&
            Objects.equals(amortizationMethod, that.amortizationMethod) &&
            Objects.equals(amortizationType, that.amortizationType) &&
            Objects.equals(compoundingFreq, that.compoundingFreq) &&
            Objects.equals(emiRounding, that.emiRounding) &&
            Objects.equals(lastRowEMIThreshold, that.lastRowEMIThreshold) &&
            Objects.equals(graceDays, that.graceDays) &&
            Objects.equals(reschLockinPeriod, that.reschLockinPeriod) &&
            Objects.equals(prepayAfterInstNo, that.prepayAfterInstNo) &&
            Objects.equals(prepayBeforeInstNo, that.prepayBeforeInstNo) &&
            Objects.equals(minInstallmentGapBetPrepay, that.minInstallmentGapBetPrepay) &&
            Objects.equals(minPrepayAmount, that.minPrepayAmount) &&
            Objects.equals(forecloseAfterInstNo, that.forecloseAfterInstNo) &&
            Objects.equals(forecloseBeforeInstaNo, that.forecloseBeforeInstaNo) &&
            Objects.equals(minTenor, that.minTenor) &&
            Objects.equals(maxTenor, that.maxTenor) &&
            Objects.equals(minInstallmentAmount, that.minInstallmentAmount) &&
            Objects.equals(maxInstallmentAmount, that.maxInstallmentAmount) &&
            Objects.equals(dropLineAmount, that.dropLineAmount) &&
            Objects.equals(dropLineODYN, that.dropLineODYN) &&
            Objects.equals(dropLinePerc, that.dropLinePerc) &&
            Objects.equals(dropMode, that.dropMode) &&
            Objects.equals(dropLIneFreq, that.dropLIneFreq) &&
            Objects.equals(dropLineCycleDay, that.dropLineCycleDay) &&
            Objects.equals(roi, that.roi) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(prodColour, that.prodColour) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(prodIconUrl, that.prodIconUrl) &&
            Objects.equals(loanCatagoryId, that.loanCatagoryId) &&
            Objects.equals(organisationId, that.organisationId) &&
            Objects.equals(ledgerAccountsId, that.ledgerAccountsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            prodCode,
            prodName,
            bpiTreatmentFlag,
            amortizationMethod,
            amortizationType,
            compoundingFreq,
            emiRounding,
            lastRowEMIThreshold,
            graceDays,
            reschLockinPeriod,
            prepayAfterInstNo,
            prepayBeforeInstNo,
            minInstallmentGapBetPrepay,
            minPrepayAmount,
            forecloseAfterInstNo,
            forecloseBeforeInstaNo,
            minTenor,
            maxTenor,
            minInstallmentAmount,
            maxInstallmentAmount,
            dropLineAmount,
            dropLineODYN,
            dropLinePerc,
            dropMode,
            dropLIneFreq,
            dropLineCycleDay,
            roi,
            isDeleted,
            lastModified,
            lastModifiedBy,
            prodColour,
            freeField2,
            freeField3,
            freeField4,
            freeField5,
            prodIconUrl,
            loanCatagoryId,
            organisationId,
            ledgerAccountsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (prodCode != null ? "prodCode=" + prodCode + ", " : "") +
            (prodName != null ? "prodName=" + prodName + ", " : "") +
            (bpiTreatmentFlag != null ? "bpiTreatmentFlag=" + bpiTreatmentFlag + ", " : "") +
            (amortizationMethod != null ? "amortizationMethod=" + amortizationMethod + ", " : "") +
            (amortizationType != null ? "amortizationType=" + amortizationType + ", " : "") +
            (compoundingFreq != null ? "compoundingFreq=" + compoundingFreq + ", " : "") +
            (emiRounding != null ? "emiRounding=" + emiRounding + ", " : "") +
            (lastRowEMIThreshold != null ? "lastRowEMIThreshold=" + lastRowEMIThreshold + ", " : "") +
            (graceDays != null ? "graceDays=" + graceDays + ", " : "") +
            (reschLockinPeriod != null ? "reschLockinPeriod=" + reschLockinPeriod + ", " : "") +
            (prepayAfterInstNo != null ? "prepayAfterInstNo=" + prepayAfterInstNo + ", " : "") +
            (prepayBeforeInstNo != null ? "prepayBeforeInstNo=" + prepayBeforeInstNo + ", " : "") +
            (minInstallmentGapBetPrepay != null ? "minInstallmentGapBetPrepay=" + minInstallmentGapBetPrepay + ", " : "") +
            (minPrepayAmount != null ? "minPrepayAmount=" + minPrepayAmount + ", " : "") +
            (forecloseAfterInstNo != null ? "forecloseAfterInstNo=" + forecloseAfterInstNo + ", " : "") +
            (forecloseBeforeInstaNo != null ? "forecloseBeforeInstaNo=" + forecloseBeforeInstaNo + ", " : "") +
            (minTenor != null ? "minTenor=" + minTenor + ", " : "") +
            (maxTenor != null ? "maxTenor=" + maxTenor + ", " : "") +
            (minInstallmentAmount != null ? "minInstallmentAmount=" + minInstallmentAmount + ", " : "") +
            (maxInstallmentAmount != null ? "maxInstallmentAmount=" + maxInstallmentAmount + ", " : "") +
            (dropLineAmount != null ? "dropLineAmount=" + dropLineAmount + ", " : "") +
            (dropLineODYN != null ? "dropLineODYN=" + dropLineODYN + ", " : "") +
            (dropLinePerc != null ? "dropLinePerc=" + dropLinePerc + ", " : "") +
            (dropMode != null ? "dropMode=" + dropMode + ", " : "") +
            (dropLIneFreq != null ? "dropLIneFreq=" + dropLIneFreq + ", " : "") +
            (dropLineCycleDay != null ? "dropLineCycleDay=" + dropLineCycleDay + ", " : "") +
            (roi != null ? "roi=" + roi + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (prodColour != null ? "prodColour=" + prodColour + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (freeField5 != null ? "freeField5=" + freeField5 + ", " : "") +
            (prodIconUrl != null ? "prodIconUrl=" + prodIconUrl + ", " : "") +
            (loanCatagoryId != null ? "loanCatagoryId=" + loanCatagoryId + ", " : "") +
            (organisationId != null ? "organisationId=" + organisationId + ", " : "") +
            (ledgerAccountsId != null ? "ledgerAccountsId=" + ledgerAccountsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
