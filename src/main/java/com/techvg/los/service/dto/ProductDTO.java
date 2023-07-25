package com.techvg.los.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.Product} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductDTO implements Serializable {

    private Long id;

    private String prodCode;

    private String prodName;

    private String bpiTreatmentFlag;

    private String amortizationMethod;

    private String amortizationType;

    private String compoundingFreq;

    private String emiRounding;

    private Double lastRowEMIThreshold;

    private Long graceDays;

    private Long reschLockinPeriod;

    private Long prepayAfterInstNo;

    private Long prepayBeforeInstNo;

    private Long minInstallmentGapBetPrepay;

    private Double minPrepayAmount;

    private Double forecloseAfterInstNo;

    private Double forecloseBeforeInstaNo;

    private Double minTenor;

    private Double maxTenor;

    private Double minInstallmentAmount;

    private Double maxInstallmentAmount;

    private Double dropLineAmount;

    private String dropLineODYN;

    private Long dropLinePerc;

    private String dropMode;

    private String dropLIneFreq;

    private Long dropLineCycleDay;

    private Double roi;

    private Boolean isDeleted;

    private Instant lastModified;

    private String lastModifiedBy;

    private String prodColour;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private String freeField5;

    private String prodIconUrl;

    private LoanCatagoryDTO loanCatagory;

    private Long organisationId;

    private Long ledgerAccountsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdCode() {
        return prodCode;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getBpiTreatmentFlag() {
        return bpiTreatmentFlag;
    }

    public void setBpiTreatmentFlag(String bpiTreatmentFlag) {
        this.bpiTreatmentFlag = bpiTreatmentFlag;
    }

    public String getAmortizationMethod() {
        return amortizationMethod;
    }

    public void setAmortizationMethod(String amortizationMethod) {
        this.amortizationMethod = amortizationMethod;
    }

    public String getAmortizationType() {
        return amortizationType;
    }

    public void setAmortizationType(String amortizationType) {
        this.amortizationType = amortizationType;
    }

    public String getCompoundingFreq() {
        return compoundingFreq;
    }

    public void setCompoundingFreq(String compoundingFreq) {
        this.compoundingFreq = compoundingFreq;
    }

    public String getEmiRounding() {
        return emiRounding;
    }

    public void setEmiRounding(String emiRounding) {
        this.emiRounding = emiRounding;
    }

    public Double getLastRowEMIThreshold() {
        return lastRowEMIThreshold;
    }

    public void setLastRowEMIThreshold(Double lastRowEMIThreshold) {
        this.lastRowEMIThreshold = lastRowEMIThreshold;
    }

    public Long getGraceDays() {
        return graceDays;
    }

    public void setGraceDays(Long graceDays) {
        this.graceDays = graceDays;
    }

    public Long getReschLockinPeriod() {
        return reschLockinPeriod;
    }

    public void setReschLockinPeriod(Long reschLockinPeriod) {
        this.reschLockinPeriod = reschLockinPeriod;
    }

    public Long getPrepayAfterInstNo() {
        return prepayAfterInstNo;
    }

    public void setPrepayAfterInstNo(Long prepayAfterInstNo) {
        this.prepayAfterInstNo = prepayAfterInstNo;
    }

    public Long getPrepayBeforeInstNo() {
        return prepayBeforeInstNo;
    }

    public void setPrepayBeforeInstNo(Long prepayBeforeInstNo) {
        this.prepayBeforeInstNo = prepayBeforeInstNo;
    }

    public Long getMinInstallmentGapBetPrepay() {
        return minInstallmentGapBetPrepay;
    }

    public void setMinInstallmentGapBetPrepay(Long minInstallmentGapBetPrepay) {
        this.minInstallmentGapBetPrepay = minInstallmentGapBetPrepay;
    }

    public Double getMinPrepayAmount() {
        return minPrepayAmount;
    }

    public void setMinPrepayAmount(Double minPrepayAmount) {
        this.minPrepayAmount = minPrepayAmount;
    }

    public Double getForecloseAfterInstNo() {
        return forecloseAfterInstNo;
    }

    public void setForecloseAfterInstNo(Double forecloseAfterInstNo) {
        this.forecloseAfterInstNo = forecloseAfterInstNo;
    }

    public Double getForecloseBeforeInstaNo() {
        return forecloseBeforeInstaNo;
    }

    public void setForecloseBeforeInstaNo(Double forecloseBeforeInstaNo) {
        this.forecloseBeforeInstaNo = forecloseBeforeInstaNo;
    }

    public Double getMinTenor() {
        return minTenor;
    }

    public void setMinTenor(Double minTenor) {
        this.minTenor = minTenor;
    }

    public Double getMaxTenor() {
        return maxTenor;
    }

    public void setMaxTenor(Double maxTenor) {
        this.maxTenor = maxTenor;
    }

    public Double getMinInstallmentAmount() {
        return minInstallmentAmount;
    }

    public void setMinInstallmentAmount(Double minInstallmentAmount) {
        this.minInstallmentAmount = minInstallmentAmount;
    }

    public Double getMaxInstallmentAmount() {
        return maxInstallmentAmount;
    }

    public void setMaxInstallmentAmount(Double maxInstallmentAmount) {
        this.maxInstallmentAmount = maxInstallmentAmount;
    }

    public Double getDropLineAmount() {
        return dropLineAmount;
    }

    public void setDropLineAmount(Double dropLineAmount) {
        this.dropLineAmount = dropLineAmount;
    }

    public String getDropLineODYN() {
        return dropLineODYN;
    }

    public void setDropLineODYN(String dropLineODYN) {
        this.dropLineODYN = dropLineODYN;
    }

    public Long getDropLinePerc() {
        return dropLinePerc;
    }

    public void setDropLinePerc(Long dropLinePerc) {
        this.dropLinePerc = dropLinePerc;
    }

    public String getDropMode() {
        return dropMode;
    }

    public void setDropMode(String dropMode) {
        this.dropMode = dropMode;
    }

    public String getDropLIneFreq() {
        return dropLIneFreq;
    }

    public void setDropLIneFreq(String dropLIneFreq) {
        this.dropLIneFreq = dropLIneFreq;
    }

    public Long getDropLineCycleDay() {
        return dropLineCycleDay;
    }

    public void setDropLineCycleDay(Long dropLineCycleDay) {
        this.dropLineCycleDay = dropLineCycleDay;
    }

    public Double getRoi() {
        return roi;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getProdColour() {
        return prodColour;
    }

    public void setProdColour(String prodColour) {
        this.prodColour = prodColour;
    }

    public String getFreeField2() {
        return freeField2;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return freeField3;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return freeField4;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return freeField5;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getProdIconUrl() {
        return prodIconUrl;
    }

    public void setProdIconUrl(String prodIconUrl) {
        this.prodIconUrl = prodIconUrl;
    }

    public LoanCatagoryDTO getLoanCatagory() {
        return loanCatagory;
    }

    public void setLoanCatagory(LoanCatagoryDTO loanCatagory) {
        this.loanCatagory = loanCatagory;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }

    public Long getLedgerAccountsId() {
        return ledgerAccountsId;
    }

    public void setLedgerAccountsId(Long ledgerAccountsId) {
        this.ledgerAccountsId = ledgerAccountsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
	@Override
	public String toString() {
		return "ProductDTO{" + "id=" + getId() + ", prodCode='" + getProdCode() + "'" + ", prodName='" + getProdName()
				+ "'" + ", bpiTreatmentFlag='" + getBpiTreatmentFlag() + "'" + ", amortizationMethod='"
				+ getAmortizationMethod() + "'" + ", amortizationType='" + getAmortizationType() + "'"
				+ ", compoundingFreq='" + getCompoundingFreq() + "'" + ", emiRounding='" + getEmiRounding() + "'"
				+ ", lastRowEMIThreshold=" + getLastRowEMIThreshold() + ", graceDays=" + getGraceDays()
				+ ", reschLockinPeriod=" + getReschLockinPeriod() + ", prepayAfterInstNo=" + getPrepayAfterInstNo()
				+ ", prepayBeforeInstNo=" + getPrepayBeforeInstNo() + ", minInstallmentGapBetPrepay="
				+ getMinInstallmentGapBetPrepay() + ", minPrepayAmount=" + getMinPrepayAmount()
				+ ", forecloseAfterInstNo=" + getForecloseAfterInstNo() + ", forecloseBeforeInstaNo="
				+ getForecloseBeforeInstaNo() + ", minTenor=" + getMinTenor() + ", maxTenor=" + getMaxTenor()
				+ ", minInstallmentAmount=" + getMinInstallmentAmount() + ", maxInstallmentAmount="
				+ getMaxInstallmentAmount() + ", dropLineAmount=" + getDropLineAmount() + ", dropLineODYN='"
				+ getDropLineODYN() + "'" + ", dropLinePerc=" + getDropLinePerc() + ", dropMode='" + getDropMode() + "'"
				+ ", dropLIneFreq='" + getDropLIneFreq() + "'" + ", dropLineCycleDay=" + getDropLineCycleDay()
				+ ", roi=" + getRoi() + ", isDeleted='" + getIsDeleted() + "'" + ", lastModified='" + getLastModified()
				+ "'" + ", lastModifiedBy='" + getLastModifiedBy() + "'" + ", prodColour='" + getProdColour() + "'"
				+ ", freeField2='" + getFreeField2() + "'" + ", freeField3='" + getFreeField3() + "'" + ", freeField4='"
				+ getFreeField4() + "'" + ", freeField5='" + getFreeField5() + "'" + ", prodIconUrl='" + getProdIconUrl()
				+ "'" + ", loanCatagory=" + getLoanCatagory() + ", organisationId=" + getOrganisationId()
				+ ", ledgerAccountsId=" + getLedgerAccountsId() + "}";
	}
}
