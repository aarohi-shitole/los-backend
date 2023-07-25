package com.techvg.los.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.MemberLimit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberLimitDTO implements Serializable {

    private Long id;

    private Double limitSanctionAmount;

    private LocalDate dtLimitSanctioned;

    private LocalDate dtLimitExpired;

    private String purpose;

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

    public Double getLimitSanctionAmount() {
        return limitSanctionAmount;
    }

    public void setLimitSanctionAmount(Double limitSanctionAmount) {
        this.limitSanctionAmount = limitSanctionAmount;
    }

    public LocalDate getDtLimitSanctioned() {
        return dtLimitSanctioned;
    }

    public void setDtLimitSanctioned(LocalDate dtLimitSanctioned) {
        this.dtLimitSanctioned = dtLimitSanctioned;
    }

    public LocalDate getDtLimitExpired() {
        return dtLimitExpired;
    }

    public void setDtLimitExpired(LocalDate dtLimitExpired) {
        this.dtLimitExpired = dtLimitExpired;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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
        if (!(o instanceof MemberLimitDTO)) {
            return false;
        }

        MemberLimitDTO memberLimitDTO = (MemberLimitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, memberLimitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberLimitDTO{" +
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
