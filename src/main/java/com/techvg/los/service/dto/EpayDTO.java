package com.techvg.los.service.dto;

import com.techvg.los.domain.enumeration.RepaymentFreqency;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.Epay} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EpayDTO implements Serializable {

    private Long id;

    private String instrumentType;

    private LocalDate dtFromDate;

    private LocalDate dtToDate;

    private String bankCode;

    private String bankBranchCode;

    private String accountType;

    private String accountNo;

    private Double maxCeilAmount;

    private Double installmentAmount;

    private Double maxInstallmentAmount;

    private String mandateRefNo;

    private String depositBank;

    private String mandateType;

    private RepaymentFreqency frequency;

    private String ifscCode;

    private String utrNo;

    private Boolean isDeleted;

    private Boolean isActive;

    private Instant lastModified;

    private String lastModifiedBy;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public LocalDate getDtFromDate() {
        return dtFromDate;
    }

    public void setDtFromDate(LocalDate dtFromDate) {
        this.dtFromDate = dtFromDate;
    }

    public LocalDate getDtToDate() {
        return dtToDate;
    }

    public void setDtToDate(LocalDate dtToDate) {
        this.dtToDate = dtToDate;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankBranchCode() {
        return bankBranchCode;
    }

    public void setBankBranchCode(String bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public Double getMaxCeilAmount() {
        return maxCeilAmount;
    }

    public void setMaxCeilAmount(Double maxCeilAmount) {
        this.maxCeilAmount = maxCeilAmount;
    }

    public Double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(Double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }

    public Double getMaxInstallmentAmount() {
        return maxInstallmentAmount;
    }

    public void setMaxInstallmentAmount(Double maxInstallmentAmount) {
        this.maxInstallmentAmount = maxInstallmentAmount;
    }

    public String getMandateRefNo() {
        return mandateRefNo;
    }

    public void setMandateRefNo(String mandateRefNo) {
        this.mandateRefNo = mandateRefNo;
    }

    public String getDepositBank() {
        return depositBank;
    }

    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    public String getMandateType() {
        return mandateType;
    }

    public void setMandateType(String mandateType) {
        this.mandateType = mandateType;
    }

    public RepaymentFreqency getFrequency() {
        return frequency;
    }

    public void setFrequency(RepaymentFreqency frequency) {
        this.frequency = frequency;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getUtrNo() {
        return utrNo;
    }

    public void setUtrNo(String utrNo) {
        this.utrNo = utrNo;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    public String getFreeField1() {
        return freeField1;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EpayDTO)) {
            return false;
        }

        EpayDTO epayDTO = (EpayDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, epayDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EpayDTO{" +
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
