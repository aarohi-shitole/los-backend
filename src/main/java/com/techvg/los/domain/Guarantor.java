package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.los.domain.enumeration.ConstitutionType;
import com.techvg.los.domain.enumeration.Gender;
import com.techvg.los.domain.enumeration.MaritalStatus;
import com.techvg.los.domain.enumeration.Occupation;
import com.techvg.los.domain.enumeration.Title;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Guarantor.
 */
@Entity
@Table(name = "guarantor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Guarantor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "title")
    private Title title;

    @Enumerated(EnumType.STRING)
    @Column(name = "constitution_type")
    private ConstitutionType constitutionType;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "membership_no")
    private String membershipNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "house_owner")
    private String houseOwner;

    @Column(name = "nature_of_business")
    private String natOfBusiness;

    @Column(name = "business_registration")
    private String busiRegistration;

    @Column(name = "designation")
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(name = "occupation")
    private Occupation occupation;

    @Column(name = "employer_name_add")
    private String employerNameAdd;

    @Column(name = "soclibil_amt")
    private Double soclibilAmt;

    @Column(name = "age")
    private Long age;

    @Column(name = "soclibil_type")
    private String soclibilType;

    @Column(name = "otherlibil_amt")
    private Double otherlibilAmt;

    @Column(name = "otherlibil_type")
    private String otherlibilType;

    @Column(name = "aadhar_card_no")
    private String aadharCardNo;

    @Column(name = "pan_card")
    private String panCard;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private MaritalStatus maritalStatus;

    @Column(name = "has_adhar_verified")
    private Boolean hasAdharVerified;

    @Column(name = "has_pan_verified")
    private Boolean hasPanVerified;

    @Column(name = "number_of_assets")
    private Integer numberOfAssets;

    @Column(name = "gross_annual_inc")
    private Double grossAnnualInc;

    @Column(name = "net_income")
    private Double netIncome;

    @Column(name = "is_income_tax_payer")
    private Boolean isIncomeTaxPayer;

    @Column(name = "is_active")
    private Boolean isActive;

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

    @Column(name = "address")
    private String address;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "free_field_6")
    private String freeField6;

    @ManyToOne
    @JsonIgnoreProperties(value = { "enquiryDetails", "branch", "member", "securityUser" }, allowSetters = true)
    private Member member;

    @ManyToOne
    @JsonIgnoreProperties(value = { "member", "securityUser" }, allowSetters = true)
    private LoanApplications loanApplications;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Guarantor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Title getTitle() {
        return this.title;
    }

    public Guarantor title(Title title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public ConstitutionType getConstitutionType() {
        return this.constitutionType;
    }

    public Guarantor constitutionType(ConstitutionType constitutionType) {
        this.setConstitutionType(constitutionType);
        return this;
    }

    public void setConstitutionType(ConstitutionType constitutionType) {
        this.constitutionType = constitutionType;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Guarantor firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Guarantor middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Guarantor lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMembershipNo() {
        return this.membershipNo;
    }

    public Guarantor membershipNo(String membershipNo) {
        this.setMembershipNo(membershipNo);
        return this;
    }

    public void setMembershipNo(String membershipNo) {
        this.membershipNo = membershipNo;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Guarantor gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public Guarantor dob(LocalDate dob) {
        this.setDob(dob);
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return this.email;
    }

    public Guarantor email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return this.mobileNo;
    }

    public Guarantor mobileNo(String mobileNo) {
        this.setMobileNo(mobileNo);
        return this;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getHouseOwner() {
        return this.houseOwner;
    }

    public Guarantor houseOwner(String houseOwner) {
        this.setHouseOwner(houseOwner);
        return this;
    }

    public void setHouseOwner(String houseOwner) {
        this.houseOwner = houseOwner;
    }

    public String getNatOfBusiness() {
        return this.natOfBusiness;
    }

    public Guarantor natOfBusiness(String natOfBusiness) {
        this.setNatOfBusiness(natOfBusiness);
        return this;
    }

    public void setNatOfBusiness(String natOfBusiness) {
        this.natOfBusiness = natOfBusiness;
    }

    public String getBusiRegistration() {
        return this.busiRegistration;
    }

    public Guarantor busiRegistration(String busiRegistration) {
        this.setBusiRegistration(busiRegistration);
        return this;
    }

    public void setBusiRegistration(String busiRegistration) {
        this.busiRegistration = busiRegistration;
    }

    public String getDesignation() {
        return this.designation;
    }

    public Guarantor designation(String designation) {
        this.setDesignation(designation);
        return this;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Occupation getOccupation() {
        return this.occupation;
    }

    public Guarantor occupation(Occupation occupation) {
        this.setOccupation(occupation);
        return this;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public String getEmployerNameAdd() {
        return this.employerNameAdd;
    }

    public Guarantor employerNameAdd(String employerNameAdd) {
        this.setEmployerNameAdd(employerNameAdd);
        return this;
    }

    public void setEmployerNameAdd(String employerNameAdd) {
        this.employerNameAdd = employerNameAdd;
    }

    public Double getSoclibilAmt() {
        return this.soclibilAmt;
    }

    public Guarantor soclibilAmt(Double soclibilAmt) {
        this.setSoclibilAmt(soclibilAmt);
        return this;
    }

    public void setSoclibilAmt(Double soclibilAmt) {
        this.soclibilAmt = soclibilAmt;
    }

    public Long getAge() {
        return this.age;
    }

    public Guarantor age(Long age) {
        this.setAge(age);
        return this;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getSoclibilType() {
        return this.soclibilType;
    }

    public Guarantor soclibilType(String soclibilType) {
        this.setSoclibilType(soclibilType);
        return this;
    }

    public void setSoclibilType(String soclibilType) {
        this.soclibilType = soclibilType;
    }

    public Double getOtherlibilAmt() {
        return this.otherlibilAmt;
    }

    public Guarantor otherlibilAmt(Double otherlibilAmt) {
        this.setOtherlibilAmt(otherlibilAmt);
        return this;
    }

    public void setOtherlibilAmt(Double otherlibilAmt) {
        this.otherlibilAmt = otherlibilAmt;
    }

    public String getOtherlibilType() {
        return this.otherlibilType;
    }

    public Guarantor otherlibilType(String otherlibilType) {
        this.setOtherlibilType(otherlibilType);
        return this;
    }

    public void setOtherlibilType(String otherlibilType) {
        this.otherlibilType = otherlibilType;
    }

    public String getAadharCardNo() {
        return this.aadharCardNo;
    }

    public Guarantor aadharCardNo(String aadharCardNo) {
        this.setAadharCardNo(aadharCardNo);
        return this;
    }

    public void setAadharCardNo(String aadharCardNo) {
        this.aadharCardNo = aadharCardNo;
    }

    public String getPanCard() {
        return this.panCard;
    }

    public Guarantor panCard(String panCard) {
        this.setPanCard(panCard);
        return this;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public MaritalStatus getMaritalStatus() {
        return this.maritalStatus;
    }

    public Guarantor maritalStatus(MaritalStatus maritalStatus) {
        this.setMaritalStatus(maritalStatus);
        return this;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Boolean getHasAdharVerified() {
        return this.hasAdharVerified;
    }

    public Guarantor hasAdharVerified(Boolean hasAdharVerified) {
        this.setHasAdharVerified(hasAdharVerified);
        return this;
    }

    public void setHasAdharVerified(Boolean hasAdharVerified) {
        this.hasAdharVerified = hasAdharVerified;
    }

    public Boolean getHasPanVerified() {
        return this.hasPanVerified;
    }

    public Guarantor hasPanVerified(Boolean hasPanVerified) {
        this.setHasPanVerified(hasPanVerified);
        return this;
    }

    public void setHasPanVerified(Boolean hasPanVerified) {
        this.hasPanVerified = hasPanVerified;
    }

    public Integer getNumberOfAssets() {
        return this.numberOfAssets;
    }

    public Guarantor numberOfAssets(Integer numberOfAssets) {
        this.setNumberOfAssets(numberOfAssets);
        return this;
    }

    public void setNumberOfAssets(Integer numberOfAssets) {
        this.numberOfAssets = numberOfAssets;
    }

    public Double getGrossAnnualInc() {
        return this.grossAnnualInc;
    }

    public Guarantor grossAnnualInc(Double grossAnnualInc) {
        this.setGrossAnnualInc(grossAnnualInc);
        return this;
    }

    public void setGrossAnnualInc(Double grossAnnualInc) {
        this.grossAnnualInc = grossAnnualInc;
    }

    public Double getNetIncome() {
        return this.netIncome;
    }

    public Guarantor netIncome(Double netIncome) {
        this.setNetIncome(netIncome);
        return this;
    }

    public void setNetIncome(Double netIncome) {
        this.netIncome = netIncome;
    }

    public Boolean getIsIncomeTaxPayer() {
        return this.isIncomeTaxPayer;
    }

    public Guarantor isIncomeTaxPayer(Boolean isIncomeTaxPayer) {
        this.setIsIncomeTaxPayer(isIncomeTaxPayer);
        return this;
    }

    public void setIsIncomeTaxPayer(Boolean isIncomeTaxPayer) {
        this.isIncomeTaxPayer = isIncomeTaxPayer;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Guarantor isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Guarantor isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public Guarantor lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public Guarantor lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Guarantor createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public Guarantor createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getAddress() {
        return this.address;
    }

    public Guarantor address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public Guarantor freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public Guarantor freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public Guarantor freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getProfileUrl() {
        return this.profileUrl;
    }

    public Guarantor profileUrl(String profileUrl) {
        this.setProfileUrl(profileUrl);
        return this;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getFreeField6() {
        return this.freeField6;
    }

    public Guarantor freeField6(String freeField6) {
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

    public Guarantor member(Member member) {
        this.setMember(member);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public LoanApplications getLoanApplications() {
        return loanApplications;
    }

    public void setLoanApplications(LoanApplications loanApplications) {
        this.loanApplications = loanApplications;
    }

    public Guarantor loanApplications(LoanApplications loanApplications) {
        this.setLoanApplications(loanApplications);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Guarantor)) {
            return false;
        }
        return id != null && id.equals(((Guarantor) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Guarantor{" +
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
            ", freeField1='" + getAddress() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", profileUrl='" + getProfileUrl() + "'" +
            ", freeField6='" + getFreeField6() + "'" +
            "}";
    }
}
