package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.Gender;
import com.techvg.los.domain.enumeration.MaritalStatus;
import com.techvg.los.domain.enumeration.Occupation;
import com.techvg.los.domain.enumeration.Title;
import com.techvg.los.service.criteria.EmployementDetailsCriteria.ConstitutionTypeFilter;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.Guarantor} entity. This class is used
 * in {@link com.techvg.los.web.rest.GuarantorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /guarantors?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GuarantorCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Title
     */
    public static class TitleFilter extends Filter<Title> {

        public TitleFilter() {}

        public TitleFilter(TitleFilter filter) {
            super(filter);
        }

        @Override
        public TitleFilter copy() {
            return new TitleFilter(this);
        }
    }

    /**
     * Class for filtering Gender
     */
    public static class GenderFilter extends Filter<Gender> {

        public GenderFilter() {}

        public GenderFilter(GenderFilter filter) {
            super(filter);
        }

        @Override
        public GenderFilter copy() {
            return new GenderFilter(this);
        }
    }

    /**
     * Class for filtering Occupation
     */
    public static class OccupationFilter extends Filter<Occupation> {

        public OccupationFilter() {}

        public OccupationFilter(OccupationFilter filter) {
            super(filter);
        }

        @Override
        public OccupationFilter copy() {
            return new OccupationFilter(this);
        }
    }

    /**
     * Class for filtering MaritalStatus
     */
    public static class MaritalStatusFilter extends Filter<MaritalStatus> {

        public MaritalStatusFilter() {}

        public MaritalStatusFilter(MaritalStatusFilter filter) {
            super(filter);
        }

        @Override
        public MaritalStatusFilter copy() {
            return new MaritalStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TitleFilter title;

    private ConstitutionTypeFilter constitutionType;

    private StringFilter firstName;

    private StringFilter middleName;

    private StringFilter lastName;

    private StringFilter membershipNo;

    private GenderFilter gender;

    private LocalDateFilter dob;

    private StringFilter email;

    private StringFilter mobileNo;

    private StringFilter houseOwner;

    private StringFilter natOfBusiness;

    private StringFilter busiRegistration;

    private StringFilter designation;

    private OccupationFilter occupation;

    private StringFilter employerNameAdd;

    private DoubleFilter soclibilAmt;

    private LongFilter age;

    private StringFilter soclibilType;

    private DoubleFilter otherlibilAmt;

    private StringFilter otherlibilType;

    private StringFilter aadharCardNo;

    private StringFilter panCard;

    private MaritalStatusFilter maritalStatus;

    private BooleanFilter hasAdharVerified;

    private BooleanFilter hasPanVerified;

    private IntegerFilter numberOfAssets;

    private DoubleFilter grossAnnualInc;

    private DoubleFilter netIncome;

    private BooleanFilter isIncomeTaxPayer;

    private BooleanFilter isActive;

    private BooleanFilter isDeleted;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter address;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter profileUrl;

    private StringFilter freeField6;

    //    private LongFilter memberAssetsId;
    //
    //    private LongFilter employementDetailsId;

    private LongFilter memberId;

    private LongFilter loanApplicationsId;

    private Boolean distinct;

    public GuarantorCriteria() {}

    public GuarantorCriteria(GuarantorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.constitutionType = other.constitutionType == null ? null : other.constitutionType.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.membershipNo = other.membershipNo == null ? null : other.membershipNo.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.dob = other.dob == null ? null : other.dob.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.houseOwner = other.houseOwner == null ? null : other.houseOwner.copy();
        this.natOfBusiness = other.natOfBusiness == null ? null : other.natOfBusiness.copy();
        this.busiRegistration = other.busiRegistration == null ? null : other.busiRegistration.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.occupation = other.occupation == null ? null : other.occupation.copy();
        this.employerNameAdd = other.employerNameAdd == null ? null : other.employerNameAdd.copy();
        this.soclibilAmt = other.soclibilAmt == null ? null : other.soclibilAmt.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.soclibilType = other.soclibilType == null ? null : other.soclibilType.copy();
        this.otherlibilAmt = other.otherlibilAmt == null ? null : other.otherlibilAmt.copy();
        this.otherlibilType = other.otherlibilType == null ? null : other.otherlibilType.copy();
        this.aadharCardNo = other.aadharCardNo == null ? null : other.aadharCardNo.copy();
        this.panCard = other.panCard == null ? null : other.panCard.copy();
        this.maritalStatus = other.maritalStatus == null ? null : other.maritalStatus.copy();
        this.hasAdharVerified = other.hasAdharVerified == null ? null : other.hasAdharVerified.copy();
        this.hasPanVerified = other.hasPanVerified == null ? null : other.hasPanVerified.copy();
        this.numberOfAssets = other.numberOfAssets == null ? null : other.numberOfAssets.copy();
        this.grossAnnualInc = other.grossAnnualInc == null ? null : other.grossAnnualInc.copy();
        this.netIncome = other.netIncome == null ? null : other.netIncome.copy();
        this.isIncomeTaxPayer = other.isIncomeTaxPayer == null ? null : other.isIncomeTaxPayer.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.profileUrl = other.profileUrl == null ? null : other.profileUrl.copy();
        this.freeField6 = other.freeField6 == null ? null : other.freeField6.copy();
        //        this.memberAssetsId = other.memberAssetsId == null ? null : other.memberAssetsId.copy();
        //        this.employementDetailsId = other.employementDetailsId == null ? null : other.employementDetailsId.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.loanApplicationsId = other.loanApplicationsId == null ? null : other.loanApplicationsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public GuarantorCriteria copy() {
        return new GuarantorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TitleFilter getTitle() {
        return title;
    }

    public TitleFilter title() {
        if (title == null) {
            title = new TitleFilter();
        }
        return title;
    }

    public void setTitle(TitleFilter title) {
        this.title = title;
    }

    public ConstitutionTypeFilter getConstitutionType() {
        return constitutionType;
    }

    public ConstitutionTypeFilter constitutionType() {
        if (constitutionType == null) {
            constitutionType = new ConstitutionTypeFilter();
        }
        return constitutionType;
    }

    public void setConstitutionType(ConstitutionTypeFilter constitutionType) {
        this.constitutionType = constitutionType;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getMiddleName() {
        return middleName;
    }

    public StringFilter middleName() {
        if (middleName == null) {
            middleName = new StringFilter();
        }
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public StringFilter lastName() {
        if (lastName == null) {
            lastName = new StringFilter();
        }
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getMembershipNo() {
        return membershipNo;
    }

    public StringFilter membershipNo() {
        if (membershipNo == null) {
            membershipNo = new StringFilter();
        }
        return membershipNo;
    }

    public void setMembershipNo(StringFilter membershipNo) {
        this.membershipNo = membershipNo;
    }

    public GenderFilter getGender() {
        return gender;
    }

    public GenderFilter gender() {
        if (gender == null) {
            gender = new GenderFilter();
        }
        return gender;
    }

    public void setGender(GenderFilter gender) {
        this.gender = gender;
    }

    public LocalDateFilter getDob() {
        return dob;
    }

    public LocalDateFilter dob() {
        if (dob == null) {
            dob = new LocalDateFilter();
        }
        return dob;
    }

    public void setDob(LocalDateFilter dob) {
        this.dob = dob;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getMobileNo() {
        return mobileNo;
    }

    public StringFilter mobileNo() {
        if (mobileNo == null) {
            mobileNo = new StringFilter();
        }
        return mobileNo;
    }

    public void setMobileNo(StringFilter mobileNo) {
        this.mobileNo = mobileNo;
    }

    public StringFilter getHouseOwner() {
        return houseOwner;
    }

    public StringFilter houseOwner() {
        if (houseOwner == null) {
            houseOwner = new StringFilter();
        }
        return houseOwner;
    }

    public void setHouseOwner(StringFilter houseOwner) {
        this.houseOwner = houseOwner;
    }

    public StringFilter getNatOfBusiness() {
        return natOfBusiness;
    }

    public StringFilter natOfBusiness() {
        if (natOfBusiness == null) {
            natOfBusiness = new StringFilter();
        }
        return natOfBusiness;
    }

    public void setNatOfBusiness(StringFilter natOfBusiness) {
        this.natOfBusiness = natOfBusiness;
    }

    public StringFilter getBusiRegistration() {
        return busiRegistration;
    }

    public StringFilter busiRegistration() {
        if (busiRegistration == null) {
            busiRegistration = new StringFilter();
        }
        return busiRegistration;
    }

    public void setBusiRegistration(StringFilter busiRegistration) {
        this.busiRegistration = busiRegistration;
    }

    public StringFilter getDesignation() {
        return designation;
    }

    public StringFilter designation() {
        if (designation == null) {
            designation = new StringFilter();
        }
        return designation;
    }

    public void setDesignation(StringFilter designation) {
        this.designation = designation;
    }

    public OccupationFilter getOccupation() {
        return occupation;
    }

    public OccupationFilter occupation() {
        if (occupation == null) {
            occupation = new OccupationFilter();
        }
        return occupation;
    }

    public void setOccupation(OccupationFilter occupation) {
        this.occupation = occupation;
    }

    public StringFilter getEmployerNameAdd() {
        return employerNameAdd;
    }

    public StringFilter employerNameAdd() {
        if (employerNameAdd == null) {
            employerNameAdd = new StringFilter();
        }
        return employerNameAdd;
    }

    public void setEmployerNameAdd(StringFilter employerNameAdd) {
        this.employerNameAdd = employerNameAdd;
    }

    public DoubleFilter getSoclibilAmt() {
        return soclibilAmt;
    }

    public DoubleFilter soclibilAmt() {
        if (soclibilAmt == null) {
            soclibilAmt = new DoubleFilter();
        }
        return soclibilAmt;
    }

    public void setSoclibilAmt(DoubleFilter soclibilAmt) {
        this.soclibilAmt = soclibilAmt;
    }

    public LongFilter getAge() {
        return age;
    }

    public LongFilter age() {
        if (age == null) {
            age = new LongFilter();
        }
        return age;
    }

    public void setAge(LongFilter age) {
        this.age = age;
    }

    public StringFilter getSoclibilType() {
        return soclibilType;
    }

    public StringFilter soclibilType() {
        if (soclibilType == null) {
            soclibilType = new StringFilter();
        }
        return soclibilType;
    }

    public void setSoclibilType(StringFilter soclibilType) {
        this.soclibilType = soclibilType;
    }

    public DoubleFilter getOtherlibilAmt() {
        return otherlibilAmt;
    }

    public DoubleFilter otherlibilAmt() {
        if (otherlibilAmt == null) {
            otherlibilAmt = new DoubleFilter();
        }
        return otherlibilAmt;
    }

    public void setOtherlibilAmt(DoubleFilter otherlibilAmt) {
        this.otherlibilAmt = otherlibilAmt;
    }

    public StringFilter getOtherlibilType() {
        return otherlibilType;
    }

    public StringFilter otherlibilType() {
        if (otherlibilType == null) {
            otherlibilType = new StringFilter();
        }
        return otherlibilType;
    }

    public void setOtherlibilType(StringFilter otherlibilType) {
        this.otherlibilType = otherlibilType;
    }

    public StringFilter getAadharCardNo() {
        return aadharCardNo;
    }

    public StringFilter aadharCardNo() {
        if (aadharCardNo == null) {
            aadharCardNo = new StringFilter();
        }
        return aadharCardNo;
    }

    public void setAadharCardNo(StringFilter aadharCardNo) {
        this.aadharCardNo = aadharCardNo;
    }

    public StringFilter getPanCard() {
        return panCard;
    }

    public StringFilter panCard() {
        if (panCard == null) {
            panCard = new StringFilter();
        }
        return panCard;
    }

    public void setPanCard(StringFilter panCard) {
        this.panCard = panCard;
    }

    public MaritalStatusFilter getMaritalStatus() {
        return maritalStatus;
    }

    public MaritalStatusFilter maritalStatus() {
        if (maritalStatus == null) {
            maritalStatus = new MaritalStatusFilter();
        }
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatusFilter maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public BooleanFilter getHasAdharVerified() {
        return hasAdharVerified;
    }

    public BooleanFilter hasAdharVerified() {
        if (hasAdharVerified == null) {
            hasAdharVerified = new BooleanFilter();
        }
        return hasAdharVerified;
    }

    public void setHasAdharVerified(BooleanFilter hasAdharVerified) {
        this.hasAdharVerified = hasAdharVerified;
    }

    public BooleanFilter getHasPanVerified() {
        return hasPanVerified;
    }

    public BooleanFilter hasPanVerified() {
        if (hasPanVerified == null) {
            hasPanVerified = new BooleanFilter();
        }
        return hasPanVerified;
    }

    public void setHasPanVerified(BooleanFilter hasPanVerified) {
        this.hasPanVerified = hasPanVerified;
    }

    public IntegerFilter getNumberOfAssets() {
        return numberOfAssets;
    }

    public IntegerFilter numberOfAssets() {
        if (numberOfAssets == null) {
            numberOfAssets = new IntegerFilter();
        }
        return numberOfAssets;
    }

    public void setNumberOfAssets(IntegerFilter numberOfAssets) {
        this.numberOfAssets = numberOfAssets;
    }

    public DoubleFilter getGrossAnnualInc() {
        return grossAnnualInc;
    }

    public DoubleFilter grossAnnualInc() {
        if (grossAnnualInc == null) {
            grossAnnualInc = new DoubleFilter();
        }
        return grossAnnualInc;
    }

    public void setGrossAnnualInc(DoubleFilter grossAnnualInc) {
        this.grossAnnualInc = grossAnnualInc;
    }

    public DoubleFilter getNetIncome() {
        return netIncome;
    }

    public DoubleFilter netIncome() {
        if (netIncome == null) {
            netIncome = new DoubleFilter();
        }
        return netIncome;
    }

    public void setNetIncome(DoubleFilter netIncome) {
        this.netIncome = netIncome;
    }

    public BooleanFilter getIsIncomeTaxPayer() {
        return isIncomeTaxPayer;
    }

    public BooleanFilter isIncomeTaxPayer() {
        if (isIncomeTaxPayer == null) {
            isIncomeTaxPayer = new BooleanFilter();
        }
        return isIncomeTaxPayer;
    }

    public void setIsIncomeTaxPayer(BooleanFilter isIncomeTaxPayer) {
        this.isIncomeTaxPayer = isIncomeTaxPayer;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public BooleanFilter isActive() {
        if (isActive == null) {
            isActive = new BooleanFilter();
        }
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public BooleanFilter getIsDeleted() {
        return isDeleted;
    }

    public BooleanFilter isDeleted() {
        if (isDeleted == null) {
            isDeleted = new BooleanFilter();
        }
        return isDeleted;
    }

    public void setIsDeleted(BooleanFilter isDeleted) {
        this.isDeleted = isDeleted;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public InstantFilter createdOn() {
        if (createdOn == null) {
            createdOn = new InstantFilter();
        }
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public StringFilter getFreeField2() {
        return freeField2;
    }

    public StringFilter freeField2() {
        if (freeField2 == null) {
            freeField2 = new StringFilter();
        }
        return freeField2;
    }

    public void setFreeField2(StringFilter freeField2) {
        this.freeField2 = freeField2;
    }

    public StringFilter getFreeField3() {
        return freeField3;
    }

    public StringFilter freeField3() {
        if (freeField3 == null) {
            freeField3 = new StringFilter();
        }
        return freeField3;
    }

    public void setFreeField3(StringFilter freeField3) {
        this.freeField3 = freeField3;
    }

    public StringFilter getFreeField4() {
        return freeField4;
    }

    public StringFilter freeField4() {
        if (freeField4 == null) {
            freeField4 = new StringFilter();
        }
        return freeField4;
    }

    public void setFreeField4(StringFilter freeField4) {
        this.freeField4 = freeField4;
    }

    public StringFilter getProfileUrl() {
        return profileUrl;
    }

    public StringFilter profileUrl() {
        if (profileUrl == null) {
            profileUrl = new StringFilter();
        }
        return profileUrl;
    }

    public void setProfileUrl(StringFilter profileUrl) {
        this.profileUrl = profileUrl;
    }

    public StringFilter getFreeField6() {
        return freeField6;
    }

    public StringFilter freeField6() {
        if (freeField6 == null) {
            freeField6 = new StringFilter();
        }
        return freeField6;
    }

    public void setFreeField6(StringFilter freeField6) {
        this.freeField6 = freeField6;
    }

    //    public LongFilter getMemberAssetsId() {
    //        return memberAssetsId;
    //    }
    //
    //    public LongFilter memberAssetsId() {
    //        if (memberAssetsId == null) {
    //            memberAssetsId = new LongFilter();
    //        }
    //        return memberAssetsId;
    //    }
    //
    //    public void setMemberAssetsId(LongFilter memberAssetsId) {
    //        this.memberAssetsId = memberAssetsId;
    //    }
    //
    //    public LongFilter getEmployementDetailsId() {
    //        return employementDetailsId;
    //    }
    //
    //    public LongFilter employementDetailsId() {
    //        if (employementDetailsId == null) {
    //            employementDetailsId = new LongFilter();
    //        }
    //        return employementDetailsId;
    //    }
    //
    //    public void setEmployementDetailsId(LongFilter employementDetailsId) {
    //        this.employementDetailsId = employementDetailsId;
    //    }

    public LongFilter getMemberId() {
        return memberId;
    }

    public LongFilter memberId() {
        if (memberId == null) {
            memberId = new LongFilter();
        }
        return memberId;
    }

    public void setMemberId(LongFilter memberId) {
        this.memberId = memberId;
    }

    public LongFilter getLoanApplicationsId() {
        return loanApplicationsId;
    }

    public void setLoanApplicationsId(LongFilter loanApplicationsId) {
        this.loanApplicationsId = loanApplicationsId;
    }

    public LongFilter loanApplicationsId() {
        if (loanApplicationsId == null) {
            loanApplicationsId = new LongFilter();
        }
        return loanApplicationsId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GuarantorCriteria that = (GuarantorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(constitutionType, that.constitutionType) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(membershipNo, that.membershipNo) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(dob, that.dob) &&
            Objects.equals(email, that.email) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(houseOwner, that.houseOwner) &&
            Objects.equals(natOfBusiness, that.natOfBusiness) &&
            Objects.equals(busiRegistration, that.busiRegistration) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(occupation, that.occupation) &&
            Objects.equals(employerNameAdd, that.employerNameAdd) &&
            Objects.equals(soclibilAmt, that.soclibilAmt) &&
            Objects.equals(age, that.age) &&
            Objects.equals(soclibilType, that.soclibilType) &&
            Objects.equals(otherlibilAmt, that.otherlibilAmt) &&
            Objects.equals(otherlibilType, that.otherlibilType) &&
            Objects.equals(aadharCardNo, that.aadharCardNo) &&
            Objects.equals(panCard, that.panCard) &&
            Objects.equals(maritalStatus, that.maritalStatus) &&
            Objects.equals(hasAdharVerified, that.hasAdharVerified) &&
            Objects.equals(hasPanVerified, that.hasPanVerified) &&
            Objects.equals(numberOfAssets, that.numberOfAssets) &&
            Objects.equals(grossAnnualInc, that.grossAnnualInc) &&
            Objects.equals(netIncome, that.netIncome) &&
            Objects.equals(isIncomeTaxPayer, that.isIncomeTaxPayer) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(address, that.address) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(profileUrl, that.profileUrl) &&
            Objects.equals(freeField6, that.freeField6) &&
            //            Objects.equals(memberAssetsId, that.memberAssetsId) &&
            //            Objects.equals(employementDetailsId, that.employementDetailsId) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(loanApplicationsId, that.loanApplicationsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            constitutionType,
            firstName,
            middleName,
            lastName,
            membershipNo,
            gender,
            dob,
            email,
            mobileNo,
            houseOwner,
            natOfBusiness,
            busiRegistration,
            designation,
            occupation,
            employerNameAdd,
            soclibilAmt,
            age,
            soclibilType,
            otherlibilAmt,
            otherlibilType,
            aadharCardNo,
            panCard,
            maritalStatus,
            hasAdharVerified,
            hasPanVerified,
            numberOfAssets,
            grossAnnualInc,
            netIncome,
            isIncomeTaxPayer,
            isActive,
            isDeleted,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            address,
            freeField2,
            freeField3,
            freeField4,
            profileUrl,
            freeField6,
            //            memberAssetsId,
            //            employementDetailsId,
            memberId,
            loanApplicationsId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GuarantorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (constitutionType != null ? "constitutionType=" + constitutionType + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (middleName != null ? "middleName=" + middleName + ", " : "") +
            (lastName != null ? "lastName=" + lastName + ", " : "") +
            (membershipNo != null ? "membershipNo=" + membershipNo + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (dob != null ? "dob=" + dob + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "") +
            (houseOwner != null ? "houseOwner=" + houseOwner + ", " : "") +
            (natOfBusiness != null ? "natOfBusiness=" + natOfBusiness + ", " : "") +
            (busiRegistration != null ? "busiRegistration=" + busiRegistration + ", " : "") +
            (designation != null ? "designation=" + designation + ", " : "") +
            (occupation != null ? "occupation=" + occupation + ", " : "") +
            (employerNameAdd != null ? "employerNameAdd=" + employerNameAdd + ", " : "") +
            (soclibilAmt != null ? "soclibilAmt=" + soclibilAmt + ", " : "") +
            (age != null ? "age=" + age + ", " : "") +
            (soclibilType != null ? "soclibilType=" + soclibilType + ", " : "") +
            (otherlibilAmt != null ? "otherlibilAmt=" + otherlibilAmt + ", " : "") +
            (otherlibilType != null ? "otherlibilType=" + otherlibilType + ", " : "") +
            (aadharCardNo != null ? "aadharCardNo=" + aadharCardNo + ", " : "") +
            (panCard != null ? "panCard=" + panCard + ", " : "") +
            (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "") +
            (hasAdharVerified != null ? "hasAdharVerified=" + hasAdharVerified + ", " : "") +
            (hasPanVerified != null ? "hasPanVerified=" + hasPanVerified + ", " : "") +
            (numberOfAssets != null ? "numberOfAssets=" + numberOfAssets + ", " : "") +
            (grossAnnualInc != null ? "grossAnnualInc=" + grossAnnualInc + ", " : "") +
            (netIncome != null ? "netIncome=" + netIncome + ", " : "") +
            (isIncomeTaxPayer != null ? "isIncomeTaxPayer=" + isIncomeTaxPayer + ", " : "") +
            (isActive != null ? "isActive=" + isActive + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (profileUrl != null ? "profileUrl=" + profileUrl + ", " : "") +
            (freeField6 != null ? "freeField6=" + freeField6 + ", " : "") +
//            (memberAssetsId != null ? "memberAssetsId=" + memberAssetsId + ", " : "") +
//            (employementDetailsId != null ? "employementDetailsId=" + employementDetailsId + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
            (loanApplicationsId != null ? "loanApplicationsId=" + loanApplicationsId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
