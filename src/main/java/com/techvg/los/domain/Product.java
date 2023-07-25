package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "prod_code")
    private String prodCode;

    @Column(name = "prod_name")
    private String prodName;

    @Column(name = "bpi_treatment_flag")
    private String bpiTreatmentFlag;

    @Column(name = "amortization_method")
    private String amortizationMethod;

    @Column(name = "amortization_type")
    private String amortizationType;

    @Column(name = "compounding_freq")
    private String compoundingFreq;

    @Column(name = "emi_rounding")
    private String emiRounding;

    @Column(name = "last_row_emi_threshold")
    private Double lastRowEMIThreshold;

    @Column(name = "grace_days")
    private Long graceDays;

    @Column(name = "resch_lockin_period")
    private Long reschLockinPeriod;

    @Column(name = "prepay_after_inst_no")
    private Long prepayAfterInstNo;

    @Column(name = "prepay_before_inst_no")
    private Long prepayBeforeInstNo;

    @Column(name = "min_installment_gap_bet_prepay")
    private Long minInstallmentGapBetPrepay;

    @Column(name = "min_prepay_amount")
    private Double minPrepayAmount;

    @Column(name = "foreclose_after_inst_no")
    private Double forecloseAfterInstNo;

    @Column(name = "foreclose_before_insta_no")
    private Double forecloseBeforeInstaNo;

    @Column(name = "min_tenor")
    private Double minTenor;

    @Column(name = "max_tenor")
    private Double maxTenor;

    @Column(name = "min_installment_amount")
    private Double minInstallmentAmount;

    @Column(name = "max_installment_amount")
    private Double maxInstallmentAmount;

    @Column(name = "drop_line_amount")
    private Double dropLineAmount;

    @Column(name = "drop_line_odyn")
    private String dropLineODYN;

    @Column(name = "drop_line_perc")
    private Long dropLinePerc;

    @Column(name = "drop_mode")
    private String dropMode;

    @Column(name = "drop_l_ine_freq")
    private String dropLIneFreq;

    @Column(name = "drop_line_cycle_day")
    private Long dropLineCycleDay;

    @Column(name = "roi")
    private Double roi;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "prod_colour")
    private String prodColour;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    @Column(name = "free_field_5")
    private String freeField5;

    @Column(name = "prod_icon_url")
    private String prodIconUrl;

    @ManyToOne
    private LoanCatagory loanCatagory;

    @ManyToOne
    @JsonIgnoreProperties(value = { "address" }, allowSetters = true)
    private Organisation organisation;

    @ManyToOne
    @JsonIgnoreProperties(value = { "branch", "ledgerAccounts" }, allowSetters = true)
    private LedgerAccounts ledgerAccounts;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdCode() {
        return this.prodCode;
    }

    public Product prodCode(String prodCode) {
        this.setProdCode(prodCode);
        return this;
    }

    public void setProdCode(String prodCode) {
        this.prodCode = prodCode;
    }

    public String getProdName() {
        return this.prodName;
    }

    public Product prodName(String prodName) {
        this.setProdName(prodName);
        return this;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getBpiTreatmentFlag() {
        return this.bpiTreatmentFlag;
    }

    public Product bpiTreatmentFlag(String bpiTreatmentFlag) {
        this.setBpiTreatmentFlag(bpiTreatmentFlag);
        return this;
    }

    public void setBpiTreatmentFlag(String bpiTreatmentFlag) {
        this.bpiTreatmentFlag = bpiTreatmentFlag;
    }

    public String getAmortizationMethod() {
        return this.amortizationMethod;
    }

    public Product amortizationMethod(String amortizationMethod) {
        this.setAmortizationMethod(amortizationMethod);
        return this;
    }

    public void setAmortizationMethod(String amortizationMethod) {
        this.amortizationMethod = amortizationMethod;
    }

    public String getAmortizationType() {
        return this.amortizationType;
    }

    public Product amortizationType(String amortizationType) {
        this.setAmortizationType(amortizationType);
        return this;
    }

    public void setAmortizationType(String amortizationType) {
        this.amortizationType = amortizationType;
    }

    public String getCompoundingFreq() {
        return this.compoundingFreq;
    }

    public Product compoundingFreq(String compoundingFreq) {
        this.setCompoundingFreq(compoundingFreq);
        return this;
    }

    public void setCompoundingFreq(String compoundingFreq) {
        this.compoundingFreq = compoundingFreq;
    }

    public String getEmiRounding() {
        return this.emiRounding;
    }

    public Product emiRounding(String emiRounding) {
        this.setEmiRounding(emiRounding);
        return this;
    }

    public void setEmiRounding(String emiRounding) {
        this.emiRounding = emiRounding;
    }

    public Double getLastRowEMIThreshold() {
        return this.lastRowEMIThreshold;
    }

    public Product lastRowEMIThreshold(Double lastRowEMIThreshold) {
        this.setLastRowEMIThreshold(lastRowEMIThreshold);
        return this;
    }

    public void setLastRowEMIThreshold(Double lastRowEMIThreshold) {
        this.lastRowEMIThreshold = lastRowEMIThreshold;
    }

    public Long getGraceDays() {
        return this.graceDays;
    }

    public Product graceDays(Long graceDays) {
        this.setGraceDays(graceDays);
        return this;
    }

    public void setGraceDays(Long graceDays) {
        this.graceDays = graceDays;
    }

    public Long getReschLockinPeriod() {
        return this.reschLockinPeriod;
    }

    public Product reschLockinPeriod(Long reschLockinPeriod) {
        this.setReschLockinPeriod(reschLockinPeriod);
        return this;
    }

    public void setReschLockinPeriod(Long reschLockinPeriod) {
        this.reschLockinPeriod = reschLockinPeriod;
    }

    public Long getPrepayAfterInstNo() {
        return this.prepayAfterInstNo;
    }

    public Product prepayAfterInstNo(Long prepayAfterInstNo) {
        this.setPrepayAfterInstNo(prepayAfterInstNo);
        return this;
    }

    public void setPrepayAfterInstNo(Long prepayAfterInstNo) {
        this.prepayAfterInstNo = prepayAfterInstNo;
    }

    public Long getPrepayBeforeInstNo() {
        return this.prepayBeforeInstNo;
    }

    public Product prepayBeforeInstNo(Long prepayBeforeInstNo) {
        this.setPrepayBeforeInstNo(prepayBeforeInstNo);
        return this;
    }

    public void setPrepayBeforeInstNo(Long prepayBeforeInstNo) {
        this.prepayBeforeInstNo = prepayBeforeInstNo;
    }

    public Long getMinInstallmentGapBetPrepay() {
        return this.minInstallmentGapBetPrepay;
    }

    public Product minInstallmentGapBetPrepay(Long minInstallmentGapBetPrepay) {
        this.setMinInstallmentGapBetPrepay(minInstallmentGapBetPrepay);
        return this;
    }

    public void setMinInstallmentGapBetPrepay(Long minInstallmentGapBetPrepay) {
        this.minInstallmentGapBetPrepay = minInstallmentGapBetPrepay;
    }

    public Double getMinPrepayAmount() {
        return this.minPrepayAmount;
    }

    public Product minPrepayAmount(Double minPrepayAmount) {
        this.setMinPrepayAmount(minPrepayAmount);
        return this;
    }

    public void setMinPrepayAmount(Double minPrepayAmount) {
        this.minPrepayAmount = minPrepayAmount;
    }

    public Double getForecloseAfterInstNo() {
        return this.forecloseAfterInstNo;
    }

    public Product forecloseAfterInstNo(Double forecloseAfterInstNo) {
        this.setForecloseAfterInstNo(forecloseAfterInstNo);
        return this;
    }

    public void setForecloseAfterInstNo(Double forecloseAfterInstNo) {
        this.forecloseAfterInstNo = forecloseAfterInstNo;
    }

    public Double getForecloseBeforeInstaNo() {
        return this.forecloseBeforeInstaNo;
    }

    public Product forecloseBeforeInstaNo(Double forecloseBeforeInstaNo) {
        this.setForecloseBeforeInstaNo(forecloseBeforeInstaNo);
        return this;
    }

    public void setForecloseBeforeInstaNo(Double forecloseBeforeInstaNo) {
        this.forecloseBeforeInstaNo = forecloseBeforeInstaNo;
    }

    public Double getMinTenor() {
        return this.minTenor;
    }

    public Product minTenor(Double minTenor) {
        this.setMinTenor(minTenor);
        return this;
    }

    public void setMinTenor(Double minTenor) {
        this.minTenor = minTenor;
    }

    public Double getMaxTenor() {
        return this.maxTenor;
    }

    public Product maxTenor(Double maxTenor) {
        this.setMaxTenor(maxTenor);
        return this;
    }

    public void setMaxTenor(Double maxTenor) {
        this.maxTenor = maxTenor;
    }

    public Double getMinInstallmentAmount() {
        return this.minInstallmentAmount;
    }

    public Product minInstallmentAmount(Double minInstallmentAmount) {
        this.setMinInstallmentAmount(minInstallmentAmount);
        return this;
    }

    public void setMinInstallmentAmount(Double minInstallmentAmount) {
        this.minInstallmentAmount = minInstallmentAmount;
    }

    public Double getMaxInstallmentAmount() {
        return this.maxInstallmentAmount;
    }

    public Product maxInstallmentAmount(Double maxInstallmentAmount) {
        this.setMaxInstallmentAmount(maxInstallmentAmount);
        return this;
    }

    public void setMaxInstallmentAmount(Double maxInstallmentAmount) {
        this.maxInstallmentAmount = maxInstallmentAmount;
    }

    public Double getDropLineAmount() {
        return this.dropLineAmount;
    }

    public Product dropLineAmount(Double dropLineAmount) {
        this.setDropLineAmount(dropLineAmount);
        return this;
    }

    public void setDropLineAmount(Double dropLineAmount) {
        this.dropLineAmount = dropLineAmount;
    }

    public String getDropLineODYN() {
        return this.dropLineODYN;
    }

    public Product dropLineODYN(String dropLineODYN) {
        this.setDropLineODYN(dropLineODYN);
        return this;
    }

    public void setDropLineODYN(String dropLineODYN) {
        this.dropLineODYN = dropLineODYN;
    }

    public Long getDropLinePerc() {
        return this.dropLinePerc;
    }

    public Product dropLinePerc(Long dropLinePerc) {
        this.setDropLinePerc(dropLinePerc);
        return this;
    }

    public void setDropLinePerc(Long dropLinePerc) {
        this.dropLinePerc = dropLinePerc;
    }

    public String getDropMode() {
        return this.dropMode;
    }

    public Product dropMode(String dropMode) {
        this.setDropMode(dropMode);
        return this;
    }

    public void setDropMode(String dropMode) {
        this.dropMode = dropMode;
    }

    public String getDropLIneFreq() {
        return this.dropLIneFreq;
    }

    public Product dropLIneFreq(String dropLIneFreq) {
        this.setDropLIneFreq(dropLIneFreq);
        return this;
    }

    public void setDropLIneFreq(String dropLIneFreq) {
        this.dropLIneFreq = dropLIneFreq;
    }

    public Long getDropLineCycleDay() {
        return this.dropLineCycleDay;
    }

    public Product dropLineCycleDay(Long dropLineCycleDay) {
        this.setDropLineCycleDay(dropLineCycleDay);
        return this;
    }

    public void setDropLineCycleDay(Long dropLineCycleDay) {
        this.dropLineCycleDay = dropLineCycleDay;
    }

    public Double getRoi() {
        return this.roi;
    }

    public Product roi(Double roi) {
        this.setRoi(roi);
        return this;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Product isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Product lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Product lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getProdColour() {
        return this.prodColour;
    }

    public Product prodColour(String prodColour) {
        this.setProdColour(prodColour);
        return this;
    }

    public void setProdColour(String prodColour) {
        this.prodColour = prodColour;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public Product freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public Product freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public Product freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public Product freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getProdIconUrl() {
        return this.prodIconUrl;
    }

    public Product prodIconUrl(String prodIconUrl) {
        this.setProdIconUrl(prodIconUrl);
        return this;
    }

    public void setProdIconUrl(String prodIconUrl) {
        this.prodIconUrl = prodIconUrl;
    }

    public LoanCatagory getLoanCatagory() {
        return this.loanCatagory;
    }

    public void setLoanCatagory(LoanCatagory loanCatagory) {
        this.loanCatagory = loanCatagory;
    }

    public Product loanCatagory(LoanCatagory loanCatagory) {
        this.setLoanCatagory(loanCatagory);
        return this;
    }

    public Organisation getOrganisation() {
        return this.organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Product organisation(Organisation organisation) {
        this.setOrganisation(organisation);
        return this;
    }

    public LedgerAccounts getLedgerAccounts() {
        return this.ledgerAccounts;
    }

    public void setLedgerAccounts(LedgerAccounts ledgerAccounts) {
        this.ledgerAccounts = ledgerAccounts;
    }

    public Product ledgerAccounts(LedgerAccounts ledgerAccounts) {
        this.setLedgerAccounts(ledgerAccounts);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", prodCode='" + getProdCode() + "'" +
            ", prodName='" + getProdName() + "'" +
            ", bpiTreatmentFlag='" + getBpiTreatmentFlag() + "'" +
            ", amortizationMethod='" + getAmortizationMethod() + "'" +
            ", amortizationType='" + getAmortizationType() + "'" +
            ", compoundingFreq='" + getCompoundingFreq() + "'" +
            ", emiRounding='" + getEmiRounding() + "'" +
            ", lastRowEMIThreshold=" + getLastRowEMIThreshold() +
            ", graceDays=" + getGraceDays() +
            ", reschLockinPeriod=" + getReschLockinPeriod() +
            ", prepayAfterInstNo=" + getPrepayAfterInstNo() +
            ", prepayBeforeInstNo=" + getPrepayBeforeInstNo() +
            ", minInstallmentGapBetPrepay=" + getMinInstallmentGapBetPrepay() +
            ", minPrepayAmount=" + getMinPrepayAmount() +
            ", forecloseAfterInstNo=" + getForecloseAfterInstNo() +
            ", forecloseBeforeInstaNo=" + getForecloseBeforeInstaNo() +
            ", minTenor=" + getMinTenor() +
            ", maxTenor=" + getMaxTenor() +
            ", minInstallmentAmount=" + getMinInstallmentAmount() +
            ", maxInstallmentAmount=" + getMaxInstallmentAmount() +
            ", dropLineAmount=" + getDropLineAmount() +
            ", dropLineODYN='" + getDropLineODYN() + "'" +
            ", dropLinePerc=" + getDropLinePerc() +
            ", dropMode='" + getDropMode() + "'" +
            ", dropLIneFreq='" + getDropLIneFreq() + "'" +
            ", dropLineCycleDay=" + getDropLineCycleDay() +
            ", roi=" + getRoi() +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", prodColour='" + getProdColour() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", freeField5='" + getFreeField5() + "'" +
            ", prodIconUrl='" + getProdIconUrl() + "'" +
            "}";
    }
}
