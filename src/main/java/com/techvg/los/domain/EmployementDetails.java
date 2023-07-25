package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.los.domain.enumeration.CompanyType;
import com.techvg.los.domain.enumeration.ConstitutionType;
import com.techvg.los.domain.enumeration.EmployementStatus;
import com.techvg.los.domain.enumeration.IndustryType;
import com.techvg.los.domain.enumeration.Occupation;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EmployementDetails.
 */
@Entity
@Table(name = "employement_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployementDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Occupation type;

    @Column(name = "employer_name")
    private String employerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EmployementStatus status;

    @Column(name = "designation")
    private String designation;

    @Column(name = "duration")
    private String duration;

    @Column(name = "employer_add")
    private String employerAdd;

    @Column(name = "prev_company_name")
    private String prevCompanyName;

    @Column(name = "prev_companyduration")
    private String prevCompanyduration;

    @Enumerated(EnumType.STRING)
    @Column(name = "org_type")
    private CompanyType orgType;

    @Enumerated(EnumType.STRING)
    @Column(name = "constitution_type")
    private ConstitutionType constitutionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "industry_type")
    private IndustryType industryType;

    @Column(name = "business_reg_no", unique = true)
    private String businessRegNo;

    @Column(name = "comp_owner_ship")
    private Double compOwnerShip;

    @Column(name = "partner_name_1")
    private String partnerName1;

    @Column(name = "partner_name_2")
    private String partnerName2;

    @Column(name = "partner_name_3")
    private String partnerName3;

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

    @Column(name = "free_field_6")
    private String freeField6;

    @ManyToOne
    @JsonIgnoreProperties(value = { "enquiryDetails", "branch", "member", "securityUser" }, allowSetters = true)
    private Member member;

    @ManyToOne
    @JsonIgnoreProperties(value = { "guarentor", "member" }, allowSetters = true)
    private Guarantor guarantor;

    @JsonIgnoreProperties(value = { "state", "district", "taluka" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EmployementDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Occupation getType() {
        return this.type;
    }

    public EmployementDetails type(Occupation type) {
        this.setType(type);
        return this;
    }

    public void setType(Occupation type) {
        this.type = type;
    }

    public String getEmployerName() {
        return this.employerName;
    }

    public EmployementDetails employerName(String employerName) {
        this.setEmployerName(employerName);
        return this;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public EmployementStatus getStatus() {
        return this.status;
    }

    public EmployementDetails status(EmployementStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(EmployementStatus status) {
        this.status = status;
    }

    public String getDesignation() {
        return this.designation;
    }

    public EmployementDetails designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDuration() {
        return this.duration;
    }

    public EmployementDetails duration(String duration) {
        this.setDuration(duration);
        return this;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEmployerAdd() {
        return this.employerAdd;
    }

    public EmployementDetails employerAdd(String employerAdd) {
        this.setEmployerAdd(employerAdd);
        return this;
    }

    public void setEmployerAdd(String employerAdd) {
        this.employerAdd = employerAdd;
    }

    public String getPrevCompanyName() {
        return this.prevCompanyName;
    }

    public EmployementDetails prevCompanyName(String prevCompanyName) {
        this.setPrevCompanyName(prevCompanyName);
        return this;
    }

    public void setPrevCompanyName(String prevCompanyName) {
        this.prevCompanyName = prevCompanyName;
    }

    public String getPrevCompanyduration() {
        return this.prevCompanyduration;
    }

    public EmployementDetails prevCompanyduration(String prevCompanyduration) {
        this.setPrevCompanyduration(prevCompanyduration);
        return this;
    }

    public void setPrevCompanyduration(String prevCompanyduration) {
        this.prevCompanyduration = prevCompanyduration;
    }

    public CompanyType getOrgType() {
        return this.orgType;
    }

    public EmployementDetails orgType(CompanyType orgType) {
        this.setOrgType(orgType);
        return this;
    }

    public void setOrgType(CompanyType orgType) {
        this.orgType = orgType;
    }

    public ConstitutionType getConstitutionType() {
        return this.constitutionType;
    }

    public EmployementDetails constitutionType(ConstitutionType constitutionType) {
        this.setConstitutionType(constitutionType);
        return this;
    }

    public void setConstitutionType(ConstitutionType constitutionType) {
        this.constitutionType = constitutionType;
    }

    public IndustryType getIndustryType() {
        return this.industryType;
    }

    public EmployementDetails industryType(IndustryType industryType) {
        this.setIndustryType(industryType);
        return this;
    }

    public void setIndustryType(IndustryType industryType) {
        this.industryType = industryType;
    }

    public String getBusinessRegNo() {
        return this.businessRegNo;
    }

    public EmployementDetails businessRegNo(String businessRegNo) {
        this.setBusinessRegNo(businessRegNo);
        return this;
    }

    public void setBusinessRegNo(String businessRegNo) {
        this.businessRegNo = businessRegNo;
    }

    public Double getCompOwnerShip() {
        return this.compOwnerShip;
    }

    public EmployementDetails compOwnerShip(Double compOwnerShip) {
        this.setCompOwnerShip(compOwnerShip);
        return this;
    }

    public void setCompOwnerShip(Double compOwnerShip) {
        this.compOwnerShip = compOwnerShip;
    }

    public String getPartnerName1() {
        return this.partnerName1;
    }

    public EmployementDetails partnerName1(String partnerName1) {
        this.setPartnerName1(partnerName1);
        return this;
    }

    public void setPartnerName1(String partnerName1) {
        this.partnerName1 = partnerName1;
    }

    public String getPartnerName2() {
        return this.partnerName2;
    }

    public EmployementDetails partnerName2(String partnerName2) {
        this.setPartnerName2(partnerName2);
        return this;
    }

    public void setPartnerName2(String partnerName2) {
        this.partnerName2 = partnerName2;
    }

    public String getPartnerName3() {
        return this.partnerName3;
    }

    public EmployementDetails partnerName3(String partnerName3) {
        this.setPartnerName3(partnerName3);
        return this;
    }

    public void setPartnerName3(String partnerName3) {
        this.partnerName3 = partnerName3;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public EmployementDetails isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public EmployementDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public EmployementDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public EmployementDetails createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public EmployementDetails createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public EmployementDetails freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public EmployementDetails freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public EmployementDetails freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public EmployementDetails freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public EmployementDetails freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getFreeField6() {
        return this.freeField6;
    }

    public EmployementDetails freeField6(String freeField6) {
        this.setFreeField6(freeField6);
        return this;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public EmployementDetails member(Member member) {
        this.setMember(member);
        return this;
    }

    public Guarantor getGuarantor() {
        return this.guarantor;
    }

    public void setGuarantor(Guarantor guarantor) {
        this.guarantor = guarantor;
    }

    public EmployementDetails guarantor(Guarantor guarantor) {
        this.setGuarantor(guarantor);
        return this;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public EmployementDetails address(Address address) {
        this.setAddress(address);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployementDetails)) {
            return false;
        }
        return id != null && id.equals(((EmployementDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
	@Override
	public String toString() {
		return "EmployementDetails{" + "id=" + getId() + ", type='" + getType() + "'" + ", employerName='"
				+ getEmployerName() + "'" + ", status='" + getStatus() + "'" + ", designation='" + getDesignation()
				+ "'" + ", duration='" + getDuration() + "'" + ", employerAdd='" + getEmployerAdd() + "'"
				+ ", prevCompanyName='" + getPrevCompanyName() + "'" + ", prevCompanyduration='"
				+ getPrevCompanyduration() + "'" + ", orgType='" + getOrgType() + "'" + ", constitutionType='"
				+ getConstitutionType() + "'" + ", industryType='" + getIndustryType() + "'" + ", businessRegNo='"
				+ getBusinessRegNo() + "'" + ", compOwnerShip=" + getCompOwnerShip() + ", partnerName1='"
				+ getPartnerName1() + "'" + ", partnerName2='" + getPartnerName2() + "'" + ", partnerName3='"
				+ getPartnerName3() + "'" + ", isDeleted='" + getIsDeleted() + "'" + ", lastModified='"
				+ getLastModified() + "'" + ", lastModifiedBy='" + getLastModifiedBy() + "'" + ", createdBy='"
				+ getCreatedBy() + "'" + ", createdOn='" + getCreatedOn() + "'" + ", freeField1='" + getFreeField1()
				+ "'" + ", freeField2='" + getFreeField2() + "'" + ", freeField3='" + getFreeField3() + "'"
				+ ", freeField4='" + getFreeField4() + "'" + ", freeField5='" + getFreeField5() + "'" + ", freeField6='"
				+ getFreeField6() + "'" + "}";
	}
}
