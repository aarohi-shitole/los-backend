package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SchemesDetails.
 */
@Entity
@Table(name = "schemes_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SchemesDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fd_duration_days")
    private Integer fdDurationDays;

    @Column(name = "interest")
    private Double interest;

    @Column(name = "lock_in_period")
    private Integer lockInPeriod;

    @Column(name = "scheme_name")
    private String schemeName;

    @Column(name = "rd_duration_months")
    private Integer rdDurationMonths;

    @Column(name = "reinvest_interest")
    private Boolean reinvestInterest;

    @Column(name = "min_amount")
    private Double minAmount;

    @Column(name = "last_day_of_scheam")
    private Instant lastDayOfScheam;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "address" }, allowSetters = true)
    private Organisation organisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SchemesDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFdDurationDays() {
        return this.fdDurationDays;
    }

    public SchemesDetails fdDurationDays(Integer fdDurationDays) {
        this.setFdDurationDays(fdDurationDays);
        return this;
    }

    public void setFdDurationDays(Integer fdDurationDays) {
        this.fdDurationDays = fdDurationDays;
    }

    public Double getInterest() {
        return this.interest;
    }

    public SchemesDetails interest(Double interest) {
        this.setInterest(interest);
        return this;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Integer getLockInPeriod() {
        return this.lockInPeriod;
    }

    public SchemesDetails lockInPeriod(Integer lockInPeriod) {
        this.setLockInPeriod(lockInPeriod);
        return this;
    }

    public void setLockInPeriod(Integer lockInPeriod) {
        this.lockInPeriod = lockInPeriod;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public SchemesDetails schemeName(String schemeName) {
        this.setSchemeName(schemeName);
        return this;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public Integer getRdDurationMonths() {
        return this.rdDurationMonths;
    }

    public SchemesDetails rdDurationMonths(Integer rdDurationMonths) {
        this.setRdDurationMonths(rdDurationMonths);
        return this;
    }

    public void setRdDurationMonths(Integer rdDurationMonths) {
        this.rdDurationMonths = rdDurationMonths;
    }

    public Boolean getReinvestInterest() {
        return this.reinvestInterest;
    }

    public SchemesDetails reinvestInterest(Boolean reinvestInterest) {
        this.setReinvestInterest(reinvestInterest);
        return this;
    }

    public void setReinvestInterest(Boolean reinvestInterest) {
        this.reinvestInterest = reinvestInterest;
    }

    public Double getMinAmount() {
        return this.minAmount;
    }

    public SchemesDetails minAmount(Double minAmount) {
        this.setMinAmount(minAmount);
        return this;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Instant getLastDayOfScheam() {
        return this.lastDayOfScheam;
    }

    public SchemesDetails lastDayOfScheam(Instant lastDayOfScheam) {
        this.setLastDayOfScheam(lastDayOfScheam);
        return this;
    }

    public void setLastDayOfScheam(Instant lastDayOfScheam) {
        this.lastDayOfScheam = lastDayOfScheam;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public SchemesDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public SchemesDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public SchemesDetails createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public SchemesDetails createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public SchemesDetails isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public SchemesDetails freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public SchemesDetails freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public SchemesDetails freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public SchemesDetails freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public SchemesDetails freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public Organisation getOrganisation() {
        return this.organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public SchemesDetails organisation(Organisation organisation) {
        this.setOrganisation(organisation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchemesDetails)) {
            return false;
        }
        return id != null && id.equals(((SchemesDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchemesDetails{" +
            "id=" + getId() +
            ", fdDurationDays=" + getFdDurationDays() +
            ", interest=" + getInterest() +
            ", lockInPeriod=" + getLockInPeriod() +
            ", schemeName='" + getSchemeName() + "'" +
            ", rdDurationMonths=" + getRdDurationMonths() +
            ", reinvestInterest='" + getReinvestInterest() + "'" +
            ", minAmount=" + getMinAmount() +
            ", lastDayOfScheam='" + getLastDayOfScheam() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", freeField5='" + getFreeField5() + "'" +
            "}";
    }
}
