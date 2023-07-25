package com.techvg.los.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MemberLimit.
 */
@Entity
@Table(name = "member_limit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberLimit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "limit_sanction_amount")
    private Double limitSanctionAmount;

    @Column(name = "dt_limit_sanctioned")
    private LocalDate dtLimitSanctioned;

    @Column(name = "dt_limit_expired")
    private LocalDate dtLimitExpired;

    @Column(name = "purpose")
    private String purpose;

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

    public MemberLimit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLimitSanctionAmount() {
        return this.limitSanctionAmount;
    }

    public MemberLimit limitSanctionAmount(Double limitSanctionAmount) {
        this.setLimitSanctionAmount(limitSanctionAmount);
        return this;
    }

    public void setLimitSanctionAmount(Double limitSanctionAmount) {
        this.limitSanctionAmount = limitSanctionAmount;
    }

    public LocalDate getDtLimitSanctioned() {
        return this.dtLimitSanctioned;
    }

    public MemberLimit dtLimitSanctioned(LocalDate dtLimitSanctioned) {
        this.setDtLimitSanctioned(dtLimitSanctioned);
        return this;
    }

    public void setDtLimitSanctioned(LocalDate dtLimitSanctioned) {
        this.dtLimitSanctioned = dtLimitSanctioned;
    }

    public LocalDate getDtLimitExpired() {
        return this.dtLimitExpired;
    }

    public MemberLimit dtLimitExpired(LocalDate dtLimitExpired) {
        this.setDtLimitExpired(dtLimitExpired);
        return this;
    }

    public void setDtLimitExpired(LocalDate dtLimitExpired) {
        this.dtLimitExpired = dtLimitExpired;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public MemberLimit purpose(String purpose) {
        this.setPurpose(purpose);
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public MemberLimit isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public MemberLimit isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public MemberLimit lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public MemberLimit lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public MemberLimit freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public MemberLimit freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public MemberLimit freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public MemberLimit freeField4(String freeField4) {
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
        if (!(o instanceof MemberLimit)) {
            return false;
        }
        return id != null && id.equals(((MemberLimit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberLimit{" +
            "id=" + getId() +
            ", limitSanctionAmount=" + getLimitSanctionAmount() +
            ", dtLimitSanctioned='" + getDtLimitSanctioned() + "'" +
            ", dtLimitExpired='" + getDtLimitExpired() + "'" +
            ", purpose='" + getPurpose() + "'" +
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
