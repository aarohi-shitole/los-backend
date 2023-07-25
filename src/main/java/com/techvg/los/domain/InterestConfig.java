package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.los.domain.enumeration.InterestType;
import com.techvg.los.domain.enumeration.RepaymentFreqency;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InterestConfig.
 */
@Entity
@Table(name = "interest_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InterestConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "end_date")
    private Instant endDate;

    @Column(name = "interest_basis")
    private String interestBasis;

    @Column(name = "emi_basis")
    private String emiBasis;

    @Enumerated(EnumType.STRING)
    @Column(name = "interest_type")
    private InterestType interestType;

    @Enumerated(EnumType.STRING)
    @Column(name = "intr_accrual_freq")
    private RepaymentFreqency intrAccrualFreq;

    @Column(name = "penal_interest_rate")
    private Double penalInterestRate;

    @Column(name = "penal_interest_basis")
    private String penalInterestBasis;

    @Column(name = "penal_accounting_basis")
    private String penalAccountingBasis;

    @Column(name = "min_interest_rate")
    private Double minInterestRate;

    @Column(name = "max_interest_rate")
    private Double maxInterestRate;

    @Column(name = "extended_interest_rate")
    private Double extendedInterestRate;

    @Column(name = "surcharge_rate")
    private Double surchargeRate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

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

    @Column(name = "free_field_5")
    private String freeField5;

    @Column(name = "free_field_6")
    private String freeField6;

    @ManyToOne
    @JsonIgnoreProperties(value = { "loanCatagory", "organisation", "ledgerAccounts" }, allowSetters = true)
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InterestConfig id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public InterestConfig startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public InterestConfig endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getInterestBasis() {
        return this.interestBasis;
    }

    public InterestConfig interestBasis(String interestBasis) {
        this.setInterestBasis(interestBasis);
        return this;
    }

    public void setInterestBasis(String interestBasis) {
        this.interestBasis = interestBasis;
    }

    public String getEmiBasis() {
        return this.emiBasis;
    }

    public InterestConfig emiBasis(String emiBasis) {
        this.setEmiBasis(emiBasis);
        return this;
    }

    public void setEmiBasis(String emiBasis) {
        this.emiBasis = emiBasis;
    }

    public InterestType getInterestType() {
        return this.interestType;
    }

    public InterestConfig interestType(InterestType interestType) {
        this.setInterestType(interestType);
        return this;
    }

    public void setInterestType(InterestType interestType) {
        this.interestType = interestType;
    }

    public RepaymentFreqency getIntrAccrualFreq() {
        return this.intrAccrualFreq;
    }

    public InterestConfig intrAccrualFreq(RepaymentFreqency intrAccrualFreq) {
        this.setIntrAccrualFreq(intrAccrualFreq);
        return this;
    }

    public void setIntrAccrualFreq(RepaymentFreqency intrAccrualFreq) {
        this.intrAccrualFreq = intrAccrualFreq;
    }

    public Double getPenalInterestRate() {
        return this.penalInterestRate;
    }

    public InterestConfig penalInterestRate(Double penalInterestRate) {
        this.setPenalInterestRate(penalInterestRate);
        return this;
    }

    public void setPenalInterestRate(Double penalInterestRate) {
        this.penalInterestRate = penalInterestRate;
    }

    public String getPenalInterestBasis() {
        return this.penalInterestBasis;
    }

    public InterestConfig penalInterestBasis(String penalInterestBasis) {
        this.setPenalInterestBasis(penalInterestBasis);
        return this;
    }

    public void setPenalInterestBasis(String penalInterestBasis) {
        this.penalInterestBasis = penalInterestBasis;
    }

    public String getPenalAccountingBasis() {
        return this.penalAccountingBasis;
    }

    public InterestConfig penalAccountingBasis(String penalAccountingBasis) {
        this.setPenalAccountingBasis(penalAccountingBasis);
        return this;
    }

    public void setPenalAccountingBasis(String penalAccountingBasis) {
        this.penalAccountingBasis = penalAccountingBasis;
    }

    public Double getMinInterestRate() {
        return this.minInterestRate;
    }

    public InterestConfig minInterestRate(Double minInterestRate) {
        this.setMinInterestRate(minInterestRate);
        return this;
    }

    public void setMinInterestRate(Double minInterestRate) {
        this.minInterestRate = minInterestRate;
    }

    public Double getMaxInterestRate() {
        return this.maxInterestRate;
    }

    public InterestConfig maxInterestRate(Double maxInterestRate) {
        this.setMaxInterestRate(maxInterestRate);
        return this;
    }

    public void setMaxInterestRate(Double maxInterestRate) {
        this.maxInterestRate = maxInterestRate;
    }

    public Double getExtendedInterestRate() {
        return this.extendedInterestRate;
    }

    public InterestConfig extendedInterestRate(Double extendedInterestRate) {
        this.setExtendedInterestRate(extendedInterestRate);
        return this;
    }

    public void setExtendedInterestRate(Double extendedInterestRate) {
        this.extendedInterestRate = extendedInterestRate;
    }

    public Double getSurchargeRate() {
        return this.surchargeRate;
    }

    public InterestConfig surchargeRate(Double surchargeRate) {
        this.setSurchargeRate(surchargeRate);
        return this;
    }

    public void setSurchargeRate(Double surchargeRate) {
        this.surchargeRate = surchargeRate;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public InterestConfig isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public InterestConfig lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public InterestConfig lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public InterestConfig freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public InterestConfig freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public InterestConfig freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public InterestConfig freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public InterestConfig freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getFreeField6() {
        return this.freeField6;
    }

    public InterestConfig freeField6(String freeField6) {
        this.setFreeField6(freeField6);
        return this;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public InterestConfig product(Product product) {
        this.setProduct(product);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InterestConfig)) {
            return false;
        }
        return id != null && id.equals(((InterestConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterestConfig{" +
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
            "}";
    }
}
