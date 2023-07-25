package com.techvg.los.service.dto;

import com.techvg.los.domain.enumeration.InterestType;
import com.techvg.los.domain.enumeration.RepaymentFreqency;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.InterestConfig} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InterestConfigDTO implements Serializable {

    private Long id;

    private Instant startDate;

    private Instant endDate;

    private String interestBasis;

    private String emiBasis;

    private InterestType interestType;

    private RepaymentFreqency intrAccrualFreq;

    private Double penalInterestRate;

    private String penalInterestBasis;

    private String penalAccountingBasis;

    private Double minInterestRate;

    private Double maxInterestRate;

    private Double extendedInterestRate;

    private Double surchargeRate;

    private Boolean isDeleted;

    private Instant lastModified;

    private String lastModifiedBy;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private String freeField5;

    private String freeField6;

    private ProductDTO product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getInterestBasis() {
        return interestBasis;
    }

    public void setInterestBasis(String interestBasis) {
        this.interestBasis = interestBasis;
    }

    public String getEmiBasis() {
        return emiBasis;
    }

    public void setEmiBasis(String emiBasis) {
        this.emiBasis = emiBasis;
    }

    public InterestType getInterestType() {
        return interestType;
    }

    public void setInterestType(InterestType interestType) {
        this.interestType = interestType;
    }

    public RepaymentFreqency getIntrAccrualFreq() {
        return intrAccrualFreq;
    }

    public void setIntrAccrualFreq(RepaymentFreqency intrAccrualFreq) {
        this.intrAccrualFreq = intrAccrualFreq;
    }

    public Double getPenalInterestRate() {
        return penalInterestRate;
    }

    public void setPenalInterestRate(Double penalInterestRate) {
        this.penalInterestRate = penalInterestRate;
    }

    public String getPenalInterestBasis() {
        return penalInterestBasis;
    }

    public void setPenalInterestBasis(String penalInterestBasis) {
        this.penalInterestBasis = penalInterestBasis;
    }

    public String getPenalAccountingBasis() {
        return penalAccountingBasis;
    }

    public void setPenalAccountingBasis(String penalAccountingBasis) {
        this.penalAccountingBasis = penalAccountingBasis;
    }

    public Double getMinInterestRate() {
        return minInterestRate;
    }

    public void setMinInterestRate(Double minInterestRate) {
        this.minInterestRate = minInterestRate;
    }

    public Double getMaxInterestRate() {
        return maxInterestRate;
    }

    public void setMaxInterestRate(Double maxInterestRate) {
        this.maxInterestRate = maxInterestRate;
    }

    public Double getExtendedInterestRate() {
        return extendedInterestRate;
    }

    public void setExtendedInterestRate(Double extendedInterestRate) {
        this.extendedInterestRate = extendedInterestRate;
    }

    public Double getSurchargeRate() {
        return surchargeRate;
    }

    public void setSurchargeRate(Double surchargeRate) {
        this.surchargeRate = surchargeRate;
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

    public String getFreeField5() {
        return freeField5;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getFreeField6() {
        return freeField6;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InterestConfigDTO)) {
            return false;
        }

        InterestConfigDTO interestConfigDTO = (InterestConfigDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, interestConfigDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterestConfigDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", interestBasis='" + getInterestBasis() + "'" +
            ", emiBasis='" + getEmiBasis() + "'" +
            ", interestType='" + getInterestType() + "'" +
            ", intrAccrualFreq='" + getIntrAccrualFreq() + "'" +
            ", penalInterestRate=" + getPenalInterestRate() +
            ", penalInterestBasis='" + getPenalInterestBasis() + "'" +
            ", penalAccountingBasis='" + getPenalAccountingBasis() + "'" +
            ", minInterestRate=" + getMinInterestRate() +
            ", maxInterestRate=" + getMaxInterestRate() +
            ", extendedInterestRate=" + getExtendedInterestRate() +
            ", surchargeRate=" + getSurchargeRate() +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", freeField5='" + getFreeField5() + "'" +
            ", freeField6='" + getFreeField6() + "'" +
            ", product=" + getProduct() +
            "}";
    }
}
