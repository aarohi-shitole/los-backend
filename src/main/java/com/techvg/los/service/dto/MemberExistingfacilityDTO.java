package com.techvg.los.service.dto;

import com.techvg.los.domain.enumeration.CreditRating;
import com.techvg.los.domain.enumeration.FacilityStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.MemberExistingfacility} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberExistingfacilityDTO implements Serializable {

    private Long id;

    private Integer year;

    private String facilityName;

    private String facilitatedFrom;

    private String nature;

    private Double amtInLac;

    private String purpose;

    private Instant sectionDate;

    private Double outstandingInLac;

    private Double emiAmt;

    private FacilityStatus status;

    private CreditRating rating;

    private Boolean isDeleted;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private String freeField5;

    private String remark;

    private Long memberId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilitatedFrom() {
        return facilitatedFrom;
    }

    public void setFacilitatedFrom(String facilitatedFrom) {
        this.facilitatedFrom = facilitatedFrom;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public Double getAmtInLac() {
        return amtInLac;
    }

    public void setAmtInLac(Double amtInLac) {
        this.amtInLac = amtInLac;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Instant getSectionDate() {
        return sectionDate;
    }

    public void setSectionDate(Instant sectionDate) {
        this.sectionDate = sectionDate;
    }

    public Double getOutstandingInLac() {
        return outstandingInLac;
    }

    public void setOutstandingInLac(Double outstandingInLac) {
        this.outstandingInLac = outstandingInLac;
    }

    public Double getEmiAmt() {
        return emiAmt;
    }

    public void setEmiAmt(Double emiAmt) {
        this.emiAmt = emiAmt;
    }

    public FacilityStatus getStatus() {
        return status;
    }

    public void setStatus(FacilityStatus status) {
        this.status = status;
    }

    public CreditRating getRating() {
        return rating;
    }

    public void setRating(CreditRating rating) {
        this.rating = rating;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberExistingfacilityDTO)) {
            return false;
        }

        MemberExistingfacilityDTO memberExistingfacilityDTO = (MemberExistingfacilityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, memberExistingfacilityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberExistingfacilityDTO{" +
            "id=" + getId() +
            ", year=" + getYear() +
            ", facilityName='" + getFacilityName() + "'" +
            ", facilitatedFrom='" + getFacilitatedFrom() + "'" +
            ", nature='" + getNature() + "'" +
            ", amtInLac=" + getAmtInLac() +
            ", purpose='" + getPurpose() + "'" +
            ", sectionDate='" + getSectionDate() + "'" +
            ", outstandingInLac=" + getOutstandingInLac() +
            ", emiAmt=" + getEmiAmt() +
            ", status='" + getStatus() + "'" +
            ", rating='" + getRating() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", freeField5='" + getFreeField5() + "'" +
            ", remark='" + getRemark() + "'" +
            ", memberId=" + getMemberId() +
            "}";
    }
}
