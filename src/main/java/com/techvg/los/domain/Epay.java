package com.techvg.los.domain;

import com.techvg.los.domain.enumeration.RepaymentFreqency;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Epay.
 */
@Entity
@Table(name = "epay")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Epay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "instrument_type")
    private String instrumentType;

    @Column(name = "dt_from_date")
    private LocalDate dtFromDate;

    @Column(name = "dt_to_date")
    private LocalDate dtToDate;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "bank_branch_code")
    private String bankBranchCode;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "max_ceil_amount")
    private Double maxCeilAmount;

    @Column(name = "installment_amount")
    private Double installmentAmount;

    @Column(name = "max_installment_amount")
    private Double maxInstallmentAmount;

    @Column(name = "mandate_ref_no")
    private String mandateRefNo;

    @Column(name = "deposit_bank")
    private String depositBank;

    @Column(name = "mandate_type")
    private String mandateType;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency")
    private RepaymentFreqency frequency;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "utr_no")
    private String utrNo;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Epay id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstrumentType() {
        return this.instrumentType;
    }

    public Epay instrumentType(String instrumentType) {
        this.setInstrumentType(instrumentType);
        return this;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public LocalDate getDtFromDate() {
        return this.dtFromDate;
    }

    public Epay dtFromDate(LocalDate dtFromDate) {
        this.setDtFromDate(dtFromDate);
        return this;
    }

    public void setDtFromDate(LocalDate dtFromDate) {
        this.dtFromDate = dtFromDate;
    }

    public LocalDate getDtToDate() {
        return this.dtToDate;
    }

    public Epay dtToDate(LocalDate dtToDate) {
        this.setDtToDate(dtToDate);
        return this;
    }

    public void setDtToDate(LocalDate dtToDate) {
        this.dtToDate = dtToDate;
    }

    public String getBankCode() {
        return this.bankCode;
    }

    public Epay bankCode(String bankCode) {
        this.setBankCode(bankCode);
        return this;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankBranchCode() {
        return this.bankBranchCode;
    }

    public Epay bankBranchCode(String bankBranchCode) {
        this.setBankBranchCode(bankBranchCode);
        return this;
    }

    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public Epay accountType(String accountType) {
        this.setAccountType(accountType);
        return this;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return this.accountNo;
    }

    public Epay accountNo(String accountNo) {
        this.setAccountNo(accountNo);
        return this;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Double getMaxCeilAmount() {
        return this.maxCeilAmount;
    }

    public Epay maxCeilAmount(Double maxCeilAmount) {
        this.setMaxCeilAmount(maxCeilAmount);
        return this;
    }

    public void setMaxCeilAmount(Double maxCeilAmount) {
        this.maxCeilAmount = maxCeilAmount;
    }

    public Double getInstallmentAmount() {
        return this.installmentAmount;
    }

    public Epay installmentAmount(Double installmentAmount) {
        this.setInstallmentAmount(installmentAmount);
        return this;
    }

    public void setInstallmentAmount(Double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public Double getMaxInstallmentAmount() {
        return this.maxInstallmentAmount;
    }

    public Epay maxInstallmentAmount(Double maxInstallmentAmount) {
        this.setMaxInstallmentAmount(maxInstallmentAmount);
        return this;
    }

    public void setMaxInstallmentAmount(Double maxInstallmentAmount) {
        this.maxInstallmentAmount = maxInstallmentAmount;
    }

    public String getMandateRefNo() {
        return this.mandateRefNo;
    }

    public Epay mandateRefNo(String mandateRefNo) {
        this.setMandateRefNo(mandateRefNo);
        return this;
    }

    public void setMandateRefNo(String mandateRefNo) {
        this.mandateRefNo = mandateRefNo;
    }

    public String getDepositBank() {
        return this.depositBank;
    }

    public Epay depositBank(String depositBank) {
        this.setDepositBank(depositBank);
        return this;
    }

    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    public String getMandateType() {
        return this.mandateType;
    }

    public Epay mandateType(String mandateType) {
        this.setMandateType(mandateType);
        return this;
    }

    public void setMandateType(String mandateType) {
        this.mandateType = mandateType;
    }

    public RepaymentFreqency getFrequency() {
        return this.frequency;
    }

    public Epay frequency(RepaymentFreqency frequency) {
        this.setFrequency(frequency);
        return this;
    }

    public void setFrequency(RepaymentFreqency frequency) {
        this.frequency = frequency;
    }

    public String getIfscCode() {
        return this.ifscCode;
    }

    public Epay ifscCode(String ifscCode) {
        this.setIfscCode(ifscCode);
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getUtrNo() {
        return this.utrNo;
    }

    public Epay utrNo(String utrNo) {
        this.setUtrNo(utrNo);
        return this;
    }

    public void setUtrNo(String utrNo) {
        this.utrNo = utrNo;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Epay isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Epay isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Epay lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Epay lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public Epay freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public Epay freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public Epay freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public Epay freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Epay)) {
            return false;
        }
        return id != null && id.equals(((Epay) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Epay{" +
            "id=" + getId() +
            ", instrumentType='" + getInstrumentType() + "'" +
            ", dtFromDate='" + getDtFromDate() + "'" +
            ", dtToDate='" + getDtToDate() + "'" +
            ", bankCode='" + getBankCode() + "'" +
            ", bankBranchCode='" + getBankBranchCode() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", accountNo='" + getAccountNo() + "'" +
            ", maxCeilAmount=" + getMaxCeilAmount() +
            ", installmentAmount=" + getInstallmentAmount() +
            ", maxInstallmentAmount=" + getMaxInstallmentAmount() +
            ", mandateRefNo='" + getMandateRefNo() + "'" +
            ", depositBank='" + getDepositBank() + "'" +
            ", mandateType='" + getMandateType() + "'" +
            ", frequency='" + getFrequency() + "'" +
            ", ifscCode='" + getIfscCode() + "'" +
            ", utrNo='" + getUtrNo() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            "}";
    }
}
