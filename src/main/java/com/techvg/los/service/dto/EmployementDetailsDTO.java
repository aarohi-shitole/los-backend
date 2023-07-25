package com.techvg.los.service.dto;

import com.techvg.los.domain.Guarantor;
import com.techvg.los.domain.Member;
import com.techvg.los.domain.enumeration.CompanyType;
import com.techvg.los.domain.enumeration.ConstitutionType;
import com.techvg.los.domain.enumeration.EmployementStatus;
import com.techvg.los.domain.enumeration.IndustryType;
import com.techvg.los.domain.enumeration.Occupation;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.los.domain.EmployementDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployementDetailsDTO implements Serializable {

    private Long id;

    private Occupation type;

    private String employerName;

    private EmployementStatus status;

    private String designation;

    private String duration;

    private String employerAdd;

    private String prevCompanyName;

    private String prevCompanyduration;

    private CompanyType orgType;

    private ConstitutionType constitutionType;

    private IndustryType industryType;

    private String businessRegNo;

    private Double compOwnerShip;

    private String partnerName1;

    private String partnerName2;

    private String partnerName3;

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

    private String freeField6;

    private MemberDTO member;

    private GuarantorDTO guarantor;

    private AddressDTO address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Occupation getType() {
        return type;
    }

    public void setType(Occupation type) {
        this.type = type;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public EmployementStatus getStatus() {
        return status;
    }

    public void setStatus(EmployementStatus status) {
        this.status = status;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEmployerAdd() {
        return employerAdd;
    }

    public void setEmployerAdd(String employerAdd) {
        this.employerAdd = employerAdd;
    }

    public String getPrevCompanyName() {
        return prevCompanyName;
    }

    public void setPrevCompanyName(String prevCompanyName) {
        this.prevCompanyName = prevCompanyName;
    }

    public String getPrevCompanyduration() {
        return prevCompanyduration;
    }

    public void setPrevCompanyduration(String prevCompanyduration) {
        this.prevCompanyduration = prevCompanyduration;
    }

    public CompanyType getOrgType() {
        return orgType;
    }

    public void setOrgType(CompanyType orgType) {
        this.orgType = orgType;
    }

    public ConstitutionType getConstitutionType() {
        return constitutionType;
    }

    public void setConstitutionType(ConstitutionType constitutionType) {
        this.constitutionType = constitutionType;
    }

    public IndustryType getIndustryType() {
        return industryType;
    }

    public void setIndustryType(IndustryType industryType) {
        this.industryType = industryType;
    }

    public String getBusinessRegNo() {
        return businessRegNo;
    }

    public void setBusinessRegNo(String businessRegNo) {
        this.businessRegNo = businessRegNo;
    }

    public Double getCompOwnerShip() {
        return compOwnerShip;
    }

    public void setCompOwnerShip(Double compOwnerShip) {
        this.compOwnerShip = compOwnerShip;
    }

    public String getPartnerName1() {
        return partnerName1;
    }

    public void setPartnerName1(String partnerName1) {
        this.partnerName1 = partnerName1;
    }

    public String getPartnerName2() {
        return partnerName2;
    }

    public void setPartnerName2(String partnerName2) {
        this.partnerName2 = partnerName2;
    }

    public String getPartnerName3() {
        return partnerName3;
    }

    public void setPartnerName3(String partnerName3) {
        this.partnerName3 = partnerName3;
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

    public String getFreeField6() {
        return freeField6;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    public GuarantorDTO getGuarantor() {
        return guarantor;
    }

    public void setGuarantor(GuarantorDTO guarantor) {
        this.guarantor = guarantor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployementDetailsDTO)) {
            return false;
        }

        EmployementDetailsDTO employementDetailsDTO = (EmployementDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employementDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    
    
	@Override
	public String toString() {
		return "EmployementDetailsDTO{" + "id=" + getId() + ", type='" + getType() + "'" + ", employerName='"
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
				+ ", freeField4='" + getFreeField4() + "'"
				+ ", freeField5='" + getFreeField5() + "'" 
				+ ", freeField6='"+ getFreeField6() + "'"
				+ ", address='"+ getAddress() + "'"
				+ ", guarantor='" + getGuarantor() + "'" + ", member=" + getMember() + "}";
	}

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}
