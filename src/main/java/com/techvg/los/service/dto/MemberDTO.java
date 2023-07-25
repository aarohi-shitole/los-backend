package com.techvg.los.service.dto;

import com.techvg.los.domain.enumeration.Gender;
import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.domain.enumeration.MaritalStatus;
import com.techvg.los.domain.enumeration.Occupation;
import com.techvg.los.domain.enumeration.ResidentalStatus;
import com.techvg.los.domain.enumeration.Status;
import com.techvg.los.domain.enumeration.StepperNumber;
import com.techvg.los.domain.enumeration.Title;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.los.domain.Member} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberDTO implements Serializable {

    private Long id;

    private Title title;

    private String firstName;

    private String middleName;

    private String lastName;

    private String customerId;

    private String membershipNo;

    private String fatherName;

    private String motherName;

    private Gender gender;

    private LocalDate dob;

    private String email;

    private String mobileNo;

    private String alternateMobile;

    private String fax;

    private String highestQualificationOthers;

    private String contactTimeFrom;

    private String contactTimeTo;

    private String religion;

    private String custCategory;

    private String cast;

    private String aadharCardNo;

    private String panCard;

    private String passportNo;

    private String passportExpiry;

    private String rationCard;

    private ResidentalStatus residenceStatus;

    private MaritalStatus maritalStatus;

    private Long familyMemberCount;

    private Occupation occupation;

    private String nationality;

    private Long noOfDependents;

    private Long age;

    private Instant applicationDate;

    private Status status;

    private String highestQualification;

    private Boolean hasAdharCardVerified;

    private Boolean hasPanCardVerified;

    private LoanStatus loanStatus;

    private String enqRefrenceNo;

    private Integer numberOfAssets;

    private Boolean isActive;

    private Boolean isDeleted;

    private StepperNumber profileStepper;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String residence;

    private String freeField5;

    private String freeField6;

    //    private Long enquiryDetailsId;

    private OrganisationDTO organisation;

    private Long branchId;

    private Long securityUserId;

    private ArrayList<RemarkHistoryDTO> remarkList;

    private Boolean hasFilledInfoWithDoc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMembershipNo() {
        return membershipNo;
    }

    public void setMembershipNo(String membershipNo) {
        this.membershipNo = membershipNo;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAlternateMobile() {
        return alternateMobile;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getHighestQualificationOthers() {
        return highestQualificationOthers;
    }

    public void setHighestQualificationOthers(String highestQualificationOthers) {
        this.highestQualificationOthers = highestQualificationOthers;
    }

    public String getContactTimeFrom() {
        return contactTimeFrom;
    }

    public void setContactTimeFrom(String contactTimeFrom) {
        this.contactTimeFrom = contactTimeFrom;
    }

    public String getContactTimeTo() {
        return contactTimeTo;
    }

    public void setContactTimeTo(String contactTimeTo) {
        this.contactTimeTo = contactTimeTo;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCustCategory() {
        return custCategory;
    }

    public void setCustCategory(String custCategory) {
        this.custCategory = custCategory;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getAadharCardNo() {
        return aadharCardNo;
    }

    public void setAadharCardNo(String aadharCardNo) {
        this.aadharCardNo = aadharCardNo;
    }

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassportExpiry() {
        return passportExpiry;
    }

    public void setPassportExpiry(String passportExpiry) {
        this.passportExpiry = passportExpiry;
    }

    public String getRationCard() {
        return rationCard;
    }

    public void setRationCard(String rationCard) {
        this.rationCard = rationCard;
    }

    public ResidentalStatus getResidenceStatus() {
        return residenceStatus;
    }

    public void setResidenceStatus(ResidentalStatus residenceStatus) {
        this.residenceStatus = residenceStatus;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Long getFamilyMemberCount() {
        return familyMemberCount;
    }

    public void setFamilyMemberCount(Long familyMemberCount) {
        this.familyMemberCount = familyMemberCount;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Long getNoOfDependents() {
        return noOfDependents;
    }

    public void setNoOfDependents(Long noOfDependents) {
        this.noOfDependents = noOfDependents;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Instant getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Instant applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(String highestQualification) {
        this.highestQualification = highestQualification;
    }

    public Boolean getHasAdharCardVerified() {
        return hasAdharCardVerified;
    }

    public void setHasAdharCardVerified(Boolean hasAdharCardVerified) {
        this.hasAdharCardVerified = hasAdharCardVerified;
    }

    public Boolean getHasPanCardVerified() {
        return hasPanCardVerified;
    }

    public void setHasPanCardVerified(Boolean hasPanCardVerified) {
        this.hasPanCardVerified = hasPanCardVerified;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getEnqRefrenceNo() {
        return enqRefrenceNo;
    }

    public void setEnqRefrenceNo(String enqRefrenceNo) {
        this.enqRefrenceNo = enqRefrenceNo;
    }

    public Integer getNumberOfAssets() {
        return numberOfAssets;
    }

    public void setNumberOfAssets(Integer numberOfAssets) {
        this.numberOfAssets = numberOfAssets;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public StepperNumber getProfileStepper() {
        return profileStepper;
    }

    public void setProfileStepper(StepperNumber profileStepper) {
        this.profileStepper = profileStepper;
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

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
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

    //    public Long getEnquiryDetailsId() {
    //        return enquiryDetailsId;
    //    }
    //
    //    public void setEnquiryDetailsId(Long enquiryDetailsId) {
    //        this.enquiryDetailsId = enquiryDetailsId;
    //    }

    public OrganisationDTO getOrganisation() {
        return organisation;
    }

    public void setOrganisation(OrganisationDTO organisation) {
        this.organisation = organisation;
    }

    public Long getSecurityUserId() {
        return securityUserId;
    }

    public void setSecurityUserId(Long securityUserId) {
        this.securityUserId = securityUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberDTO)) {
            return false;
        }

        MemberDTO memberDTO = (MemberDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, memberDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", customerId='" + getCustomerId() + "'" +
            ", membershipNo='" + getMembershipNo() + "'" +
            ", fatherName='" + getFatherName() + "'" +
            ", motherName='" + getMotherName() + "'" +
            ", gender='" + getGender() + "'" +
            ", dob='" + getDob() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", alternateMobile='" + getAlternateMobile() + "'" +
            ", highestQualificationOthers='" + getHighestQualificationOthers() + "'" +
            ", fax='" + getFax() + "'" +
            ", contactTimeFrom='" + getContactTimeFrom() + "'" +
            ", contactTimeTo='" + getContactTimeTo() + "'" +
            ", religion='" + getReligion() + "'" +
            ", custCategory='" + getCustCategory() + "'" +
            ", cast='" + getCast() + "'" +
            ", aadharCardNo='" + getAadharCardNo() + "'" +
            ", panCard='" + getPanCard() + "'" +
            ", passportNo='" + getPassportNo() + "'" +
            ", passportExpiry='" + getPassportExpiry() + "'" +
            ", rationCard='" + getRationCard() + "'" +
            ", residenceStatus='" + getResidenceStatus() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", familyMemberCount=" + getFamilyMemberCount() +
            ", occupation='" + getOccupation() + "'" +
            ", nationality='" + getNationality() + "'" +
            ", noOfDependents=" + getNoOfDependents() +
            ", age=" + getAge() +
            ", applicationDate='" + getApplicationDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", highestQualification='" + getHighestQualification() + "'" +
            ", hasAdharCardVerified='" + getHasAdharCardVerified() + "'" +
            ", hasPanCardVerified='" + getHasPanCardVerified() + "'" +
            ", loanStatus='" + getLoanStatus() + "'" +
            ", enqRefrenceNo='" + getEnqRefrenceNo() + "'" +
            ", numberOfAssets=" + getNumberOfAssets() +
            ", isActive='" + getIsActive() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", profileStepper='" + getProfileStepper() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", residence='" + getResidence() + "'" +
            ", freeField5='" + getFreeField5() + "'" +
            ", freeField6='" + getFreeField6() + "'" +
//            ", enquiryDetailsId=" + getEnquiryDetailsId() +
            ", organisation=" + getOrganisation() +
            ", securityUserId=" + getSecurityUserId() +
            ", branchId=" + getBranchId() +
            "}";
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public ArrayList<RemarkHistoryDTO> getRemarkList() {
        return remarkList;
    }

    public void setRemarkList(ArrayList<RemarkHistoryDTO> remarkList) {
        this.remarkList = remarkList;
    }

    public Boolean getHasFilledInfoWithDoc() {
        return hasFilledInfoWithDoc;
    }

    public void setHasFilledInfoWithDoc(Boolean hasFilledInfoWithDoc) {
        this.hasFilledInfoWithDoc = hasFilledInfoWithDoc;
    }
}
