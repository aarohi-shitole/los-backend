package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.los.domain.enumeration.CreditRating;
import com.techvg.los.domain.enumeration.FacilityStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MemberExistingfacility.
 */
@Entity
@Table(name = "member_existingfacility")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberExistingfacility implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "year")
    private Integer year;

    @Column(name = "facility_name")
    private String facilityName;

    @Column(name = "facilitated_from")
    private String facilitatedFrom;

    @Column(name = "nature")
    private String nature;

    @Column(name = "amt_in_lac")
    private Double amtInLac;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "section_date")
    private Instant sectionDate;

    @Column(name = "outstanding_in_lac")
    private Double outstandingInLac;

    @Column(name = "emi_amt")
    private Double emiAmt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FacilityStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "rating")
    private CreditRating rating;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

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

    @Column(name = "remark")
    private String remark;

    @ManyToOne
    @JsonIgnoreProperties(value = { "enquiryDetails", "branch", "member", "securityUser" }, allowSetters = true)
    private Member member;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MemberExistingfacility id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return this.year;
    }

    public MemberExistingfacility year(Integer year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getFacilityName() {
        return this.facilityName;
    }

    public MemberExistingfacility facilityName(String facilityName) {
        this.setFacilityName(facilityName);
        return this;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilitatedFrom() {
        return this.facilitatedFrom;
    }

    public MemberExistingfacility facilitatedFrom(String facilitatedFrom) {
        this.setFacilitatedFrom(facilitatedFrom);
        return this;
    }

    public void setFacilitatedFrom(String facilitatedFrom) {
        this.facilitatedFrom = facilitatedFrom;
    }

    public String getNature() {
        return this.nature;
    }

    public MemberExistingfacility nature(String nature) {
        this.setNature(nature);
        return this;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public Double getAmtInLac() {
        return this.amtInLac;
    }

    public MemberExistingfacility amtInLac(Double amtInLac) {
        this.setAmtInLac(amtInLac);
        return this;
    }

    public void setAmtInLac(Double amtInLac) {
        this.amtInLac = amtInLac;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public MemberExistingfacility purpose(String purpose) {
        this.setPurpose(purpose);
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Instant getSectionDate() {
        return this.sectionDate;
    }

    public MemberExistingfacility sectionDate(Instant sectionDate) {
        this.setSectionDate(sectionDate);
        return this;
    }

    public void setSectionDate(Instant sectionDate) {
        this.sectionDate = sectionDate;
    }

    public Double getOutstandingInLac() {
        return this.outstandingInLac;
    }

    public MemberExistingfacility outstandingInLac(Double outstandingInLac) {
        this.setOutstandingInLac(outstandingInLac);
        return this;
    }

    public void setOutstandingInLac(Double outstandingInLac) {
        this.outstandingInLac = outstandingInLac;
    }

    public Double getEmiAmt() {
        return this.emiAmt;
    }

    public MemberExistingfacility emiAmt(Double emiAmt) {
        this.setEmiAmt(emiAmt);
        return this;
    }

    public void setEmiAmt(Double emiAmt) {
        this.emiAmt = emiAmt;
    }

    public FacilityStatus getStatus() {
        return this.status;
    }

    public MemberExistingfacility status(FacilityStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(FacilityStatus status) {
        this.status = status;
    }

    public CreditRating getRating() {
        return this.rating;
    }

    public MemberExistingfacility rating(CreditRating rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(CreditRating rating) {
        this.rating = rating;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public MemberExistingfacility isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public MemberExistingfacility lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public MemberExistingfacility lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public MemberExistingfacility createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public MemberExistingfacility createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public MemberExistingfacility freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public MemberExistingfacility freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public MemberExistingfacility freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public MemberExistingfacility freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public MemberExistingfacility freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getRemark() {
        return this.remark;
    }

    public MemberExistingfacility remark(String remark) {
        this.setRemark(remark);
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public MemberExistingfacility member(Member member) {
        this.setMember(member);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberExistingfacility)) {
            return false;
        }
        return id != null && id.equals(((MemberExistingfacility) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberExistingfacility{" +
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
            "}";
    }
}
