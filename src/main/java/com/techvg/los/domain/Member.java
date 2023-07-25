package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Member.
 */
@Entity
@Table(name = "member")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "title")
    private Title title;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "customer_id", unique = true)
    private String customerId;

    @Column(name = "membership_no", unique = true)
    private String membershipNo;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "mother_name")
    private String motherName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "alternate_mobile")
    private String alternateMobile;

    @Column(name = "fax")
    private String fax;

    @Column(name = "highest_qualification_others")
    private String highestQualificationOthers;

    @Column(name = "contact_time_from")
    private String contactTimeFrom;

    @Column(name = "contact_time_to")
    private String contactTimeTo;

    @Column(name = "religion")
    private String religion;

    @Column(name = "cust_category")
    private String custCategory;

    @Column(name = "cast")
    private String cast;

    @Column(name = "aadhar_card_no", unique = true)
    private String aadharCardNo;

    @Column(name = "pan_card", unique = true)
    private String panCard;

    @Column(name = "passport_no", unique = true)
    private String passportNo;

    @Column(name = "passport_expiry")
    private String passportExpiry;

    @Column(name = "ration_card")
    private String rationCard;

    @Enumerated(EnumType.STRING)
    @Column(name = "residence_status")
    private ResidentalStatus residenceStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Column(name = "family_member_count")
    private Long familyMemberCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "occupation")
    private Occupation occupation;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "no_of_dependents")
    private Long noOfDependents;

    @Column(name = "age")
    private Long age;

    @Column(name = "application_date")
    private Instant applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "highest_qualification")
    private String highestQualification;

    @Column(name = "has_adhar_card_verified")
    private Boolean hasAdharCardVerified;

    @Column(name = "has_pan_card_verified")
    private Boolean hasPanCardVerified;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_status")
    private LoanStatus loanStatus;

    @Column(name = "enq_refrence_no")
    private String enqRefrenceNo;

    @Column(name = "number_of_assets")
    private Integer numberOfAssets;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "profile_stepper")
    private StepperNumber profileStepper;

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

    @Column(name = "residance")
    private String residence;

    @Column(name = "free_field_5")
    private String freeField5;

    @Column(name = "free_field_6")
    private String freeField6;

    //    @Column(name = "member_id")
    //    private Long memberId;

    // @JsonIgnoreProperties(value = { "state", "district", "taluka", "city",
    // "product" }, allowSetters = true)
    // @OneToOne
    // @JoinColumn(unique = true)
    // private EnquiryDetails enquiryDetails;

    @ManyToOne
    @JsonIgnoreProperties(value = { "address" }, allowSetters = true)
    private Organisation organisation;

    @ManyToOne
    @JsonIgnoreProperties(value = { "branch", "address" }, allowSetters = true)
    private Branch branch;

    @ManyToOne
    @JsonIgnoreProperties(value = { "branch", "securityPermissions", "securityRoles" }, allowSetters = true)
    private SecurityUser securityUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Member id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Title getTitle() {
        return this.title;
    }

    public Member title(Title title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Member firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Member middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Member lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public Member customerId(String customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMembershipNo() {
        return this.membershipNo;
    }

    public Member membershipNo(String membershipNo) {
        this.setMembershipNo(membershipNo);
        return this;
    }

    public void setMembershipNo(String membershipNo) {
        this.membershipNo = membershipNo;
    }

    public String getFatherName() {
        return this.fatherName;
    }

    public Member fatherName(String fatherName) {
        this.setFatherName(fatherName);
        return this;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return this.motherName;
    }

    public Member motherName(String motherName) {
        this.setMotherName(motherName);
        return this;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Member gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public Member dob(LocalDate dob) {
        this.setDob(dob);
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return this.email;
    }

    public Member email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public Member mobileNo(String mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAlternateMobile() {
        return this.alternateMobile;
    }

    public Member alternateMobile(String alternateMobile) {
        this.setAlternateMobile(alternateMobile);
        return this;
    }

    public void setAlternateMobile(String alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public String getFax() {
        return this.fax;
    }

    public Member fax(String fax) {
        this.setFax(fax);
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getHighestQualificationOthers() {
        return this.highestQualificationOthers;
    }

    public Member highestQualificationOthers(String highestQualificationOthers) {
        this.setHighestQualificationOthers(highestQualificationOthers);
        return this;
    }

    public void setHighestQualificationOthers(String highestQualificationOthers) {
        this.highestQualificationOthers = highestQualificationOthers;
    }

    public String getContactTimeFrom() {
        return this.contactTimeFrom;
    }

    public Member contactTimeFrom(String contactTimeFrom) {
        this.setContactTimeFrom(contactTimeFrom);
        return this;
    }

    public void setContactTimeFrom(String contactTimeFrom) {
        this.contactTimeFrom = contactTimeFrom;
    }

    public String getContactTimeTo() {
        return this.contactTimeTo;
    }

    public Member contactTimeTo(String contactTimeTo) {
        this.setContactTimeTo(contactTimeTo);
        return this;
    }

    public void setContactTimeTo(String contactTimeTo) {
        this.contactTimeTo = contactTimeTo;
    }

    public String getReligion() {
        return this.religion;
    }

    public Member religion(String religion) {
        this.setReligion(religion);
        return this;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getCustCategory() {
        return this.custCategory;
    }

    public Member custCategory(String custCategory) {
        this.setCustCategory(custCategory);
        return this;
    }

    public void setCustCategory(String custCategory) {
        this.custCategory = custCategory;
    }

    public String getCast() {
        return this.cast;
    }

    public Member cast(String cast) {
        this.setCast(cast);
        return this;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getAadharCardNo() {
        return this.aadharCardNo;
    }

    public Member aadharCardNo(String aadharCardNo) {
        this.setAadharCardNo(aadharCardNo);
        return this;
    }

    public void setAadharCardNo(String aadharCardNo) {
        this.aadharCardNo = aadharCardNo;
    }

    public String getPanCard() {
        return this.panCard;
    }

    public Member panCard(String panCard) {
        this.setPanCard(panCard);
        return this;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public String getPassportNo() {
        return this.passportNo;
    }

    public Member passportNo(String passportNo) {
        this.setPassportNo(passportNo);
        return this;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassportExpiry() {
        return this.passportExpiry;
    }

    public Member passportExpiry(String passportExpiry) {
        this.setPassportExpiry(passportExpiry);
        return this;
    }

    public void setPassportExpiry(String passportExpiry) {
        this.passportExpiry = passportExpiry;
    }

    public String getRationCard() {
        return this.rationCard;
    }

    public Member rationCard(String rationCard) {
        this.setRationCard(rationCard);
        return this;
    }

    public void setRationCard(String rationCard) {
        this.rationCard = rationCard;
    }

    public ResidentalStatus getResidenceStatus() {
        return this.residenceStatus;
    }

    public Member residenceStatus(ResidentalStatus residenceStatus) {
        this.setResidenceStatus(residenceStatus);
        return this;
    }

    public void setResidenceStatus(ResidentalStatus residenceStatus) {
        this.residenceStatus = residenceStatus;
    }

    public MaritalStatus getMaritalStatus() {
        return this.maritalStatus;
    }

    public Member maritalStatus(MaritalStatus maritalStatus) {
        this.setMaritalStatus(maritalStatus);
        return this;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Long getFamilyMemberCount() {
        return this.familyMemberCount;
    }

    public Member familyMemberCount(Long familyMemberCount) {
        this.setFamilyMemberCount(familyMemberCount);
        return this;
    }

    public void setFamilyMemberCount(Long familyMemberCount) {
        this.familyMemberCount = familyMemberCount;
    }

    public Occupation getOccupation() {
        return this.occupation;
    }

    public Member occupation(Occupation occupation) {
        this.setOccupation(occupation);
        return this;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public String getNationality() {
        return this.nationality;
    }

    public Member nationality(String nationality) {
        this.setNationality(nationality);
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Long getNoOfDependents() {
        return this.noOfDependents;
    }

    public Member noOfDependents(Long noOfDependents) {
        this.setNoOfDependents(noOfDependents);
        return this;
    }

    public void setNoOfDependents(Long noOfDependents) {
        this.noOfDependents = noOfDependents;
    }

    public Long getAge() {
        return this.age;
    }

    public Member age(Long age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Instant getApplicationDate() {
        return this.applicationDate;
    }

    public Member applicationDate(Instant applicationDate) {
        this.setApplicationDate(applicationDate);
        return this;
    }

    public void setApplicationDate(Instant applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Status getStatus() {
        return this.status;
    }

    public Member status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getHighestQualification() {
        return this.highestQualification;
    }

    public Member highestQualification(String highestQualification) {
        this.setHighestQualification(highestQualification);
        return this;
    }

    public void setHighestQualification(String highestQualification) {
        this.highestQualification = highestQualification;
    }

    public Boolean getHasAdharCardVerified() {
        return this.hasAdharCardVerified;
    }

    public Member hasAdharCardVerified(Boolean hasAdharCardVerified) {
        this.setHasAdharCardVerified(hasAdharCardVerified);
        return this;
    }

    public void setHasAdharCardVerified(Boolean hasAdharCardVerified) {
        this.hasAdharCardVerified = hasAdharCardVerified;
    }

    public Boolean getHasPanCardVerified() {
        return this.hasPanCardVerified;
    }

    public Member hasPanCardVerified(Boolean hasPanCardVerified) {
        this.setHasPanCardVerified(hasPanCardVerified);
        return this;
    }

    public void setHasPanCardVerified(Boolean hasPanCardVerified) {
        this.hasPanCardVerified = hasPanCardVerified;
    }

    public LoanStatus getLoanStatus() {
        return this.loanStatus;
    }

    public Member loanStatus(LoanStatus loanStatus) {
        this.setLoanStatus(loanStatus);
        return this;
    }

    public void setLoanStatus(LoanStatus loanStatus) {
        this.loanStatus = loanStatus;
    }

    public String getEnqRefrenceNo() {
        return this.enqRefrenceNo;
    }

    public Member enqRefrenceNo(String enqRefrenceNo) {
        this.setEnqRefrenceNo(enqRefrenceNo);
        return this;
    }

    public void setEnqRefrenceNo(String enqRefrenceNo) {
        this.enqRefrenceNo = enqRefrenceNo;
    }

    public Integer getNumberOfAssets() {
        return this.numberOfAssets;
    }

    public Member numberOfAssets(Integer numberOfAssets) {
        this.setNumberOfAssets(numberOfAssets);
        return this;
    }

    public void setNumberOfAssets(Integer numberOfAssets) {
        this.numberOfAssets = numberOfAssets;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Member isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Member isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public StepperNumber getProfileStepper() {
        return this.profileStepper;
    }

    public Member profileStepper(StepperNumber profileStepper) {
        this.setProfileStepper(profileStepper);
        return this;
    }

    public void setProfileStepper(StepperNumber profileStepper) {
        this.profileStepper = profileStepper;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Member lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Member lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Member createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Member createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public Member freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public Member freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public Member freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getResidence() {
        return this.residence;
    }

    public Member residence(String residence) {
        this.setResidence(residence);
        return this;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public Member freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getFreeField6() {
        return this.freeField6;
    }

    public Member freeField6(String freeField6) {
        this.setFreeField6(freeField6);
        return this;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    // public EnquiryDetails getEnquiryDetails() {
    // return this.enquiryDetails;
    // }
    //
    // public void setEnquiryDetails(EnquiryDetails enquiryDetails) {
    // this.enquiryDetails = enquiryDetails;
    // }
    //
    // public Member enquiryDetails(EnquiryDetails enquiryDetails) {
    // this.setEnquiryDetails(enquiryDetails);
    // return this;
    // }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public Member organisation(Organisation organisation) {
        this.setOrganisation(organisation);
        return this;
    }

    //    public Long getMemberId() {
    //        return this.memberId;
    //    }
    //
    //    public void setMemberId(Long memberId) {
    //        this.memberId = memberId;
    //    }
    //
    //    public Member memberId(Long memberId) {
    //        this.setMemberId(memberId);
    //        return this;
    //    }

    public Branch getBranch() {
        return this.branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Member branch(Branch branch) {
        this.setBranch(branch);
        return this;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public Member securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member)) {
            return false;
        }
        return id != null && id.equals(((Member) o).id);
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
		return "Member{" + "id=" + getId() + ", title='" + getTitle() + "'" + ", firstName='" + getFirstName() + "'"
				+ ", middleName='" + getMiddleName() + "'" + ", lastName='" + getLastName() + "'" + ", customerId='"
				+ getCustomerId() + "'" + ", membershipNo='" + getMembershipNo() + "'" + ", fatherName='"
				+ getFatherName() + "'" + ", motherName='" + getMotherName() + "'" + ", gender='" + getGender() + "'"
				+ ", dob='" + getDob() + "'" + ", email='" + getEmail() + "'" + ", mobileNo='" + getMobileNo() + "'"
				+ ", alternateMobile='" + getAlternateMobile() + "'" + ", fax='" + getFax() + "'"
				+ ", highestQualificationOthers='" + getHighestQualificationOthers() + "'"+", contactTimeFrom='" + getContactTimeFrom() + "'" + ", contactTimeTo='" + getContactTimeTo() + "'"
				+ ", religion='" + getReligion() + "'" + ", custCategory='" + getCustCategory() + "'" + ", cast='"
				+ getCast() + "'" + ", aadharCardNo='" + getAadharCardNo() + "'" + ", panCard='" + getPanCard() + "'"
				+ ", passportNo='" + getPassportNo() + "'" + ", passportExpiry='" + getPassportExpiry() + "'"
				+ ", rationCard='" + getRationCard() + "'" + ", residenceStatus='" + getResidenceStatus() + "'"
				+ ", maritalStatus='" + getMaritalStatus() + "'" + ", familyMemberCount=" + getFamilyMemberCount()
				+ ", occupation='" + getOccupation() + "'" + ", nationality='" + getNationality() + "'"
				+ ", noOfDependents=" + getNoOfDependents() + ", age=" + getAge() +
				", applicationDate='" + getApplicationDate() + "'"
				+ ", status='" + getStatus() + "'" + ", highestQualification='" + getHighestQualification() + "'"
				+ ", hasAdharCardVerified='" + getHasAdharCardVerified() + "'" + ", hasPanCardVerified='"
				+ getHasPanCardVerified() + "'" + ", loanStatus='" + getLoanStatus() + "'" + ", enqRefrenceNo='"
				+ getEnqRefrenceNo() + "'" + ", numberOfAssets=" + getNumberOfAssets() + ", isActive='" + getIsActive()
				+ "'" + ", isDeleted='" + getIsDeleted() + "'" + ", profileStepper='" + getProfileStepper() + "'"
				+ ", lastModified='" + getLastModified() + "'" + ", lastModifiedBy='" + getLastModifiedBy() + "'"
				+ ", createdBy='" + getCreatedBy() + "'" + ", createdOn='" + getCreatedOn() + "'" + ", freeField1='"
				+ getFreeField1() + "'" + ", freeField2='" + getFreeField2() + "'" + ", freeField3='" + getFreeField3()
				+ "'" + ", residence='" + getResidence() + "'" + ", freeField5='" + getFreeField5() + "'"
				+ ", freeField6='" + getFreeField6() + "'" + "}";
	}
}
