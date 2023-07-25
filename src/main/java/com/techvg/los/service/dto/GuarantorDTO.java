package com.techvg.los.service.dto;

import com.techvg.los.domain.enumeration.ConstitutionType;
import com.techvg.los.domain.enumeration.Gender;
import com.techvg.los.domain.enumeration.MaritalStatus;
import com.techvg.los.domain.enumeration.Occupation;
import com.techvg.los.domain.enumeration.Title;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.Guarantor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GuarantorDTO implements Serializable {

    private Long id;

    private Title title;

    private ConstitutionType constitutionType;

    private String firstName;

    private String middleName;

    private String lastName;

    private String membershipNo;

    private Gender gender;

    private LocalDate dob;

    private String email;

    private String mobileNo;

    private String houseOwner;

    private String natOfBusiness;

    private String busiRegistration;

    private String designation;

    private Occupation occupation;

    private String employerNameAdd;

    private Double soclibilAmt;

    private Long age;

    private String soclibilType;

    private Double otherlibilAmt;

    private String otherlibilType;

    private String aadharCardNo;

    private String panCard;

    private MaritalStatus maritalStatus;

    private Boolean hasAdharVerified;

    private Boolean hasPanVerified;

    private Integer numberOfAssets;

    private Double grossAnnualInc;

    private Double netIncome;

    private Boolean isIncomeTaxPayer;

    private Boolean isActive;

    private Boolean isDeleted;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private String address;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private String profileUrl;

    private String freeField6;

    //    private MemberAssetsDTO memberAssets;
    //
    //    private EmployementDetailsDTO employementDetails;

    private Long memberId;

    private Long loanApplicationsId;

    private List<DocumentsDTO> documents;

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

    public ConstitutionType getConstitutionType() {
        return constitutionType;
    }

    public void setConstitutionType(ConstitutionType constitutionType) {
        this.constitutionType = constitutionType;
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

    public String getMembershipNo() {
        return membershipNo;
    }

    public void setMembershipNo(String membershipNo) {
        this.membershipNo = membershipNo;
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

    public String getHouseOwner() {
        return houseOwner;
    }

    public void setHouseOwner(String houseOwner) {
        this.houseOwner = houseOwner;
    }

    public String getNatOfBusiness() {
        return natOfBusiness;
    }

    public void setNatOfBusiness(String natOfBusiness) {
        this.natOfBusiness = natOfBusiness;
    }

    public String getBusiRegistration() {
        return busiRegistration;
    }

    public void setBusiRegistration(String busiRegistration) {
        this.busiRegistration = busiRegistration;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public String getEmployerNameAdd() {
        return employerNameAdd;
    }

    public void setEmployerNameAdd(String employerNameAdd) {
        this.employerNameAdd = employerNameAdd;
    }

    public Double getSoclibilAmt() {
        return soclibilAmt;
    }

    public void setSoclibilAmt(Double soclibilAmt) {
        this.soclibilAmt = soclibilAmt;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getSoclibilType() {
        return soclibilType;
    }

    public void setSoclibilType(String soclibilType) {
        this.soclibilType = soclibilType;
    }

    public Double getOtherlibilAmt() {
        return otherlibilAmt;
    }

    public void setOtherlibilAmt(Double otherlibilAmt) {
        this.otherlibilAmt = otherlibilAmt;
    }

    public String getOtherlibilType() {
        return otherlibilType;
    }

    public void setOtherlibilType(String otherlibilType) {
        this.otherlibilType = otherlibilType;
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

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Boolean getHasAdharVerified() {
        return hasAdharVerified;
    }

    public void setHasAdharVerified(Boolean hasAdharVerified) {
        this.hasAdharVerified = hasAdharVerified;
    }

    public Boolean getHasPanVerified() {
        return hasPanVerified;
    }

    public void setHasPanVerified(Boolean hasPanVerified) {
        this.hasPanVerified = hasPanVerified;
    }

    public Integer getNumberOfAssets() {
        return numberOfAssets;
    }

    public void setNumberOfAssets(Integer numberOfAssets) {
        this.numberOfAssets = numberOfAssets;
    }

    public Double getGrossAnnualInc() {
        return grossAnnualInc;
    }

    public void setGrossAnnualInc(Double grossAnnualInc) {
        this.grossAnnualInc = grossAnnualInc;
    }

    public Double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(Double netIncome) {
        this.netIncome = netIncome;
    }

    public Boolean getIsIncomeTaxPayer() {
        return isIncomeTaxPayer;
    }

    public void setIsIncomeTaxPayer(Boolean isIncomeTaxPayer) {
        this.isIncomeTaxPayer = isIncomeTaxPayer;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getFreeField6() {
        return freeField6;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    //    public MemberAssetsDTO getMemberAssets() {
    //        return memberAssets;
    //    }
    //
    //    public void setMemberAssets(MemberAssetsDTO memberAssets) {
    //        this.memberAssets = memberAssets;
    //    }
    //
    //    public EmployementDetailsDTO getEmployementDetails() {
    //        return employementDetails;
    //    }
    //
    //    public void setEmployementDetails(EmployementDetailsDTO employementDetails) {
    //        this.employementDetails = employementDetails;
    //    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getLoanApplicationsId() {
        return loanApplicationsId;
    }

    public void setLoanApplicationsId(Long loanApplicationsId) {
        this.loanApplicationsId = loanApplicationsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GuarantorDTO)) {
            return false;
        }

        GuarantorDTO guarantorDTO = (GuarantorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, guarantorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GuarantorDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", constitutionType='" + getConstitutionType() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", membershipNo='" + getMembershipNo() + "'" +
            ", gender='" + getGender() + "'" +
            ", dob='" + getDob() + "'" +
            ", email='" + getEmail() + "'" +
            ", mobileNo='" + getMobileNo() + "'" +
            ", houseOwner='" + getHouseOwner() + "'" +
            ", natOfBusiness='" + getNatOfBusiness() + "'" +
            ", busiRegistration='" + getBusiRegistration() + "'" +
            ", designation='" + getDesignation() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", employerNameAdd='" + getEmployerNameAdd() + "'" +
            ", soclibilAmt=" + getSoclibilAmt() +
            ", age=" + getAge() +
            ", soclibilType='" + getSoclibilType() + "'" +
            ", otherlibilAmt=" + getOtherlibilAmt() +
            ", otherlibilType='" + getOtherlibilType() + "'" +
            ", aadharCardNo='" + getAadharCardNo() + "'" +
            ", panCard='" + getPanCard() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", hasAdharVerified='" + getHasAdharVerified() + "'" +
            ", hasPanVerified='" + getHasPanVerified() + "'" +
            ", numberOfAssets=" + getNumberOfAssets() +
            ", grossAnnualInc=" + getGrossAnnualInc() +
            ", netIncome=" + getNetIncome() +
            ", isIncomeTaxPayer='" + getIsIncomeTaxPayer() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", address='" + getAddress() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", profileUrl='" + getProfileUrl() + "'" +
            ", freeField6='" + getFreeField6() + "'" +
//            ", memberAssets=" + getMemberAssets() +
//            ", employementDetails=" + getEmployementDetails() +
            ", memberId=" + getMemberId() +
            ", loanApplicationsId=" + getLoanApplicationsId() +
            "}";
    }

    public List<DocumentsDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentsDTO> documents) {
        this.documents = documents;
    }
}
