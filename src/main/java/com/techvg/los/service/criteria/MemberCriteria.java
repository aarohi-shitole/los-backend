package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.Gender;
import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.domain.enumeration.MaritalStatus;
import com.techvg.los.domain.enumeration.Occupation;
import com.techvg.los.domain.enumeration.ResidentalStatus;
import com.techvg.los.domain.enumeration.Status;
import com.techvg.los.domain.enumeration.StepperNumber;
import com.techvg.los.domain.enumeration.Title;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.Member} entity. This
 * class is used in {@link com.techvg.los.web.rest.MemberResource} to receive
 * all the possible filtering options from the Http GET request parameters. For
 * example the following could be a valid request:
 * {@code /members?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberCriteria implements Serializable, Criteria {

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
     * Class for filtering ResidentalStatus
     */
    public static class ResidentalStatusFilter extends Filter<ResidentalStatus> {

        public ResidentalStatusFilter() {}

        public ResidentalStatusFilter(ResidentalStatusFilter filter) {
            super(filter);
        }

        @Override
        public ResidentalStatusFilter copy() {
            return new ResidentalStatusFilter(this);
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
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {

        public StatusFilter() {}

        public StatusFilter(StatusFilter filter) {
            super(filter);
        }

        @Override
        public StatusFilter copy() {
            return new StatusFilter(this);
        }
    }

    /**
     * Class for filtering LoanStatus
     */
    public static class LoanStatusFilter extends Filter<LoanStatus> {

        public LoanStatusFilter() {}

        public LoanStatusFilter(LoanStatusFilter filter) {
            super(filter);
        }

        @Override
        public LoanStatusFilter copy() {
            return new LoanStatusFilter(this);
        }
    }

    /**
     * Class for filtering StepperNumber
     */
    public static class StepperNumberFilter extends Filter<StepperNumber> {

        public StepperNumberFilter() {}

        public StepperNumberFilter(StepperNumberFilter filter) {
            super(filter);
        }

        @Override
        public StepperNumberFilter copy() {
            return new StepperNumberFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TitleFilter title;

    private StringFilter firstName;

    private StringFilter middleName;

    private StringFilter lastName;

    private StringFilter customerId;

    private StringFilter membershipNo;

    private StringFilter fatherName;

    private StringFilter motherName;

    private GenderFilter gender;

    private LocalDateFilter dob;

    private StringFilter email;

    private StringFilter mobileNo;

    private StringFilter alternateMobile;

    private StringFilter fax;

    private StringFilter highestQualificationOthers;

    private StringFilter contactTimeFrom;

    private StringFilter contactTimeTo;

    private StringFilter religion;

    private StringFilter custCategory;

    private StringFilter cast;

    private StringFilter aadharCardNo;

    private StringFilter panCard;

    private StringFilter passportNo;

    private StringFilter passportExpiry;

    private StringFilter rationCard;

    private ResidentalStatusFilter residenceStatus;

    private MaritalStatusFilter maritalStatus;

    private LongFilter familyMemberCount;

    private OccupationFilter occupation;

    private StringFilter nationality;

    private LongFilter noOfDependents;

    private LongFilter age;

    private InstantFilter applicationDate;

    private StatusFilter status;

    private StringFilter highestQualification;

    private BooleanFilter hasAdharCardVerified;

    private BooleanFilter hasPanCardVerified;

    private LoanStatusFilter loanStatus;

    private StringFilter enqRefrenceNo;

    private IntegerFilter numberOfAssets;

    private BooleanFilter isActive;

    private BooleanFilter isDeleted;

    private StepperNumberFilter profileStepper;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter residence;

    private StringFilter freeField5;

    private StringFilter freeField6;

    // private LongFilter enquiryDetailsId;

    private LongFilter organisationId;

    private LongFilter branchId;

    private LongFilter securityUserId;

    private Boolean distinct;

    public MemberCriteria() {}

    public MemberCriteria(MemberCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.lastName = other.lastName == null ? null : other.lastName.copy();
        this.customerId = other.customerId == null ? null : other.customerId.copy();
        this.membershipNo = other.membershipNo == null ? null : other.membershipNo.copy();
        this.fatherName = other.fatherName == null ? null : other.fatherName.copy();
        this.motherName = other.motherName == null ? null : other.motherName.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.dob = other.dob == null ? null : other.dob.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.mobileNo = other.mobileNo == null ? null : other.mobileNo.copy();
        this.alternateMobile = other.alternateMobile == null ? null : other.alternateMobile.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.highestQualificationOthers = other.highestQualificationOthers == null ? null : other.highestQualificationOthers.copy();
        this.contactTimeFrom = other.contactTimeFrom == null ? null : other.contactTimeFrom.copy();
        this.contactTimeTo = other.contactTimeTo == null ? null : other.contactTimeTo.copy();
        this.religion = other.religion == null ? null : other.religion.copy();
        this.custCategory = other.custCategory == null ? null : other.custCategory.copy();
        this.cast = other.cast == null ? null : other.cast.copy();
        this.aadharCardNo = other.aadharCardNo == null ? null : other.aadharCardNo.copy();
        this.panCard = other.panCard == null ? null : other.panCard.copy();
        this.passportNo = other.passportNo == null ? null : other.passportNo.copy();
        this.passportExpiry = other.passportExpiry == null ? null : other.passportExpiry.copy();
        this.rationCard = other.rationCard == null ? null : other.rationCard.copy();
        this.residenceStatus = other.residenceStatus == null ? null : other.residenceStatus.copy();
        this.maritalStatus = other.maritalStatus == null ? null : other.maritalStatus.copy();
        this.familyMemberCount = other.familyMemberCount == null ? null : other.familyMemberCount.copy();
        this.occupation = other.occupation == null ? null : other.occupation.copy();
        this.nationality = other.nationality == null ? null : other.nationality.copy();
        this.noOfDependents = other.noOfDependents == null ? null : other.noOfDependents.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.applicationDate = other.applicationDate == null ? null : other.applicationDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.highestQualification = other.highestQualification == null ? null : other.highestQualification.copy();
        this.hasAdharCardVerified = other.hasAdharCardVerified == null ? null : other.hasAdharCardVerified.copy();
        this.hasPanCardVerified = other.hasPanCardVerified == null ? null : other.hasPanCardVerified.copy();
        this.loanStatus = other.loanStatus == null ? null : other.loanStatus.copy();
        this.enqRefrenceNo = other.enqRefrenceNo == null ? null : other.enqRefrenceNo.copy();
        this.numberOfAssets = other.numberOfAssets == null ? null : other.numberOfAssets.copy();
        this.isActive = other.isActive == null ? null : other.isActive.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.profileStepper = other.profileStepper == null ? null : other.profileStepper.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.residence = other.residence == null ? null : other.residence.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.freeField6 = other.freeField6 == null ? null : other.freeField6.copy();
        // this.enquiryDetailsId = other.enquiryDetailsId == null ? null :
        // other.enquiryDetailsId.copy();
        this.organisationId = other.organisationId == null ? null : other.organisationId.copy();
        this.branchId = other.branchId == null ? null : other.branchId.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MemberCriteria copy() {
        return new MemberCriteria(this);
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

    public StringFilter getCustomerId() {
        return customerId;
    }

    public StringFilter customerId() {
        if (customerId == null) {
            customerId = new StringFilter();
        }
        return customerId;
    }

    public void setCustomerId(StringFilter customerId) {
        this.customerId = customerId;
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

    public StringFilter getFatherName() {
        return fatherName;
    }

    public StringFilter fatherName() {
        if (fatherName == null) {
            fatherName = new StringFilter();
        }
        return fatherName;
    }

    public void setFatherName(StringFilter fatherName) {
        this.fatherName = fatherName;
    }

    public StringFilter getMotherName() {
        return motherName;
    }

    public StringFilter motherName() {
        if (motherName == null) {
            motherName = new StringFilter();
        }
        return motherName;
    }

    public void setMotherName(StringFilter motherName) {
        this.motherName = motherName;
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

    public StringFilter getAlternateMobile() {
        return alternateMobile;
    }

    public StringFilter alternateMobile() {
        if (alternateMobile == null) {
            alternateMobile = new StringFilter();
        }
        return alternateMobile;
    }

    public void setAlternateMobile(StringFilter alternateMobile) {
        this.alternateMobile = alternateMobile;
    }

    public StringFilter getHighestQualificationOthers() {
        return highestQualificationOthers;
    }

    public StringFilter highestQualificationOthers() {
        if (highestQualificationOthers == null) {
            highestQualificationOthers = new StringFilter();
        }
        return highestQualificationOthers;
    }

    public void setHighestQualificationOthers(StringFilter highestQualificationOthers) {
        this.highestQualificationOthers = highestQualificationOthers;
    }

    public StringFilter getFax() {
        return fax;
    }

    public StringFilter fax() {
        if (fax == null) {
            fax = new StringFilter();
        }
        return fax;
    }

    public void setFax(StringFilter fax) {
        this.fax = fax;
    }

    public StringFilter getContactTimeFrom() {
        return contactTimeFrom;
    }

    public StringFilter contactTimeFrom() {
        if (contactTimeFrom == null) {
            contactTimeFrom = new StringFilter();
        }
        return contactTimeFrom;
    }

    public void setContactTimeFrom(StringFilter contactTimeFrom) {
        this.contactTimeFrom = contactTimeFrom;
    }

    public StringFilter getContactTimeTo() {
        return contactTimeTo;
    }

    public StringFilter contactTimeTo() {
        if (contactTimeTo == null) {
            contactTimeTo = new StringFilter();
        }
        return contactTimeTo;
    }

    public void setContactTimeTo(StringFilter contactTimeTo) {
        this.contactTimeTo = contactTimeTo;
    }

    public StringFilter getReligion() {
        return religion;
    }

    public StringFilter religion() {
        if (religion == null) {
            religion = new StringFilter();
        }
        return religion;
    }

    public void setReligion(StringFilter religion) {
        this.religion = religion;
    }

    public StringFilter getCustCategory() {
        return custCategory;
    }

    public StringFilter custCategory() {
        if (custCategory == null) {
            custCategory = new StringFilter();
        }
        return custCategory;
    }

    public void setCustCategory(StringFilter custCategory) {
        this.custCategory = custCategory;
    }

    public StringFilter getCast() {
        return cast;
    }

    public StringFilter cast() {
        if (cast == null) {
            cast = new StringFilter();
        }
        return cast;
    }

    public void setCast(StringFilter cast) {
        this.cast = cast;
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

    public StringFilter getPassportNo() {
        return passportNo;
    }

    public StringFilter passportNo() {
        if (passportNo == null) {
            passportNo = new StringFilter();
        }
        return passportNo;
    }

    public void setPassportNo(StringFilter passportNo) {
        this.passportNo = passportNo;
    }

    public StringFilter getPassportExpiry() {
        return passportExpiry;
    }

    public StringFilter passportExpiry() {
        if (passportExpiry == null) {
            passportExpiry = new StringFilter();
        }
        return passportExpiry;
    }

    public void setPassportExpiry(StringFilter passportExpiry) {
        this.passportExpiry = passportExpiry;
    }

    public StringFilter getRationCard() {
        return rationCard;
    }

    public StringFilter rationCard() {
        if (rationCard == null) {
            rationCard = new StringFilter();
        }
        return rationCard;
    }

    public void setRationCard(StringFilter rationCard) {
        this.rationCard = rationCard;
    }

    public ResidentalStatusFilter getResidenceStatus() {
        return residenceStatus;
    }

    public ResidentalStatusFilter residenceStatus() {
        if (residenceStatus == null) {
            residenceStatus = new ResidentalStatusFilter();
        }
        return residenceStatus;
    }

    public void setResidenceStatus(ResidentalStatusFilter residenceStatus) {
        this.residenceStatus = residenceStatus;
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

    public LongFilter getFamilyMemberCount() {
        return familyMemberCount;
    }

    public LongFilter familyMemberCount() {
        if (familyMemberCount == null) {
            familyMemberCount = new LongFilter();
        }
        return familyMemberCount;
    }

    public void setFamilyMemberCount(LongFilter familyMemberCount) {
        this.familyMemberCount = familyMemberCount;
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

    public StringFilter getNationality() {
        return nationality;
    }

    public StringFilter nationality() {
        if (nationality == null) {
            nationality = new StringFilter();
        }
        return nationality;
    }

    public void setNationality(StringFilter nationality) {
        this.nationality = nationality;
    }

    public LongFilter getNoOfDependents() {
        return noOfDependents;
    }

    public LongFilter noOfDependents() {
        if (noOfDependents == null) {
            noOfDependents = new LongFilter();
        }
        return noOfDependents;
    }

    public void setNoOfDependents(LongFilter noOfDependents) {
        this.noOfDependents = noOfDependents;
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

    public InstantFilter getApplicationDate() {
        return applicationDate;
    }

    public InstantFilter applicationDate() {
        if (applicationDate == null) {
            applicationDate = new InstantFilter();
        }
        return applicationDate;
    }

    public void setApplicationDate(InstantFilter applicationDate) {
        this.applicationDate = applicationDate;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public StatusFilter status() {
        if (status == null) {
            status = new StatusFilter();
        }
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public StringFilter getHighestQualification() {
        return highestQualification;
    }

    public StringFilter highestQualification() {
        if (highestQualification == null) {
            highestQualification = new StringFilter();
        }
        return highestQualification;
    }

    public void setHighestQualification(StringFilter highestQualification) {
        this.highestQualification = highestQualification;
    }

    public BooleanFilter getHasAdharCardVerified() {
        return hasAdharCardVerified;
    }

    public BooleanFilter hasAdharCardVerified() {
        if (hasAdharCardVerified == null) {
            hasAdharCardVerified = new BooleanFilter();
        }
        return hasAdharCardVerified;
    }

    public void setHasAdharCardVerified(BooleanFilter hasAdharCardVerified) {
        this.hasAdharCardVerified = hasAdharCardVerified;
    }

    public BooleanFilter getHasPanCardVerified() {
        return hasPanCardVerified;
    }

    public BooleanFilter hasPanCardVerified() {
        if (hasPanCardVerified == null) {
            hasPanCardVerified = new BooleanFilter();
        }
        return hasPanCardVerified;
    }

    public void setHasPanCardVerified(BooleanFilter hasPanCardVerified) {
        this.hasPanCardVerified = hasPanCardVerified;
    }

    public LoanStatusFilter getLoanStatus() {
        return loanStatus;
    }

    public LoanStatusFilter loanStatus() {
        if (loanStatus == null) {
            loanStatus = new LoanStatusFilter();
        }
        return loanStatus;
    }

    public void setLoanStatus(LoanStatusFilter loanStatus) {
        this.loanStatus = loanStatus;
    }

    public StringFilter getEnqRefrenceNo() {
        return enqRefrenceNo;
    }

    public StringFilter enqRefrenceNo() {
        if (enqRefrenceNo == null) {
            enqRefrenceNo = new StringFilter();
        }
        return enqRefrenceNo;
    }

    public void setEnqRefrenceNo(StringFilter enqRefrenceNo) {
        this.enqRefrenceNo = enqRefrenceNo;
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

    public StepperNumberFilter getProfileStepper() {
        return profileStepper;
    }

    public StepperNumberFilter profileStepper() {
        if (profileStepper == null) {
            profileStepper = new StepperNumberFilter();
        }
        return profileStepper;
    }

    public void setProfileStepper(StepperNumberFilter profileStepper) {
        this.profileStepper = profileStepper;
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

    public StringFilter getFreeField1() {
        return freeField1;
    }

    public StringFilter freeField1() {
        if (freeField1 == null) {
            freeField1 = new StringFilter();
        }
        return freeField1;
    }

    public void setFreeField1(StringFilter freeField1) {
        this.freeField1 = freeField1;
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

    public StringFilter getResidence() {
        return residence;
    }

    public StringFilter residence() {
        if (residence == null) {
            residence = new StringFilter();
        }
        return residence;
    }

    public void setResidence(StringFilter residence) {
        this.residence = residence;
    }

    public StringFilter getFreeField5() {
        return freeField5;
    }

    public StringFilter freeField5() {
        if (freeField5 == null) {
            freeField5 = new StringFilter();
        }
        return freeField5;
    }

    public void setFreeField5(StringFilter freeField5) {
        this.freeField5 = freeField5;
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

    // public LongFilter getEnquiryDetailsId() {
    // return enquiryDetailsId;
    // }
    //
    // public LongFilter enquiryDetailsId() {
    // if (enquiryDetailsId == null) {
    // enquiryDetailsId = new LongFilter();
    // }
    // return enquiryDetailsId;
    // }
    //
    // public void setEnquiryDetailsId(LongFilter enquiryDetailsId) {
    // this.enquiryDetailsId = enquiryDetailsId;
    // }

    public LongFilter getOrganisationId() {
        return organisationId;
    }

    public LongFilter organisationId() {
        if (organisationId == null) {
            organisationId = new LongFilter();
        }
        return organisationId;
    }

    public void setOrganisationId(LongFilter organisationId) {
        this.organisationId = organisationId;
    }

    public LongFilter getBranchId() {
        return branchId;
    }

    public LongFilter branchId() {
        if (branchId == null) {
            branchId = new LongFilter();
        }
        return branchId;
    }

    public void setBranchId(LongFilter branchId) {
        this.branchId = branchId;
    }

    public LongFilter getSecurityUserId() {
        return securityUserId;
    }

    public LongFilter securityUserId() {
        if (securityUserId == null) {
            securityUserId = new LongFilter();
        }
        return securityUserId;
    }

    public void setSecurityUserId(LongFilter securityUserId) {
        this.securityUserId = securityUserId;
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
        final MemberCriteria that = (MemberCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(customerId, that.customerId) &&
            Objects.equals(membershipNo, that.membershipNo) &&
            Objects.equals(fatherName, that.fatherName) &&
            Objects.equals(motherName, that.motherName) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(dob, that.dob) &&
            Objects.equals(email, that.email) &&
            Objects.equals(mobileNo, that.mobileNo) &&
            Objects.equals(alternateMobile, that.alternateMobile) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(highestQualificationOthers, that.highestQualificationOthers) &&
            Objects.equals(contactTimeFrom, that.contactTimeFrom) &&
            Objects.equals(contactTimeTo, that.contactTimeTo) &&
            Objects.equals(religion, that.religion) &&
            Objects.equals(custCategory, that.custCategory) &&
            Objects.equals(cast, that.cast) &&
            Objects.equals(aadharCardNo, that.aadharCardNo) &&
            Objects.equals(panCard, that.panCard) &&
            Objects.equals(passportNo, that.passportNo) &&
            Objects.equals(passportExpiry, that.passportExpiry) &&
            Objects.equals(rationCard, that.rationCard) &&
            Objects.equals(residenceStatus, that.residenceStatus) &&
            Objects.equals(maritalStatus, that.maritalStatus) &&
            Objects.equals(familyMemberCount, that.familyMemberCount) &&
            Objects.equals(occupation, that.occupation) &&
            Objects.equals(nationality, that.nationality) &&
            Objects.equals(noOfDependents, that.noOfDependents) &&
            Objects.equals(age, that.age) &&
            Objects.equals(applicationDate, that.applicationDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(highestQualification, that.highestQualification) &&
            Objects.equals(hasAdharCardVerified, that.hasAdharCardVerified) &&
            Objects.equals(hasPanCardVerified, that.hasPanCardVerified) &&
            Objects.equals(loanStatus, that.loanStatus) &&
            Objects.equals(enqRefrenceNo, that.enqRefrenceNo) &&
            Objects.equals(numberOfAssets, that.numberOfAssets) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(profileStepper, that.profileStepper) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(residence, that.residence) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(freeField6, that.freeField6) &&
            // Objects.equals(enquiryDetailsId, that.enquiryDetailsId) &&
            Objects.equals(organisationId, that.organisationId) &&
            Objects.equals(branchId, that.branchId) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            firstName,
            middleName,
            lastName,
            customerId,
            membershipNo,
            fatherName,
            motherName,
            gender,
            dob,
            email,
            mobileNo,
            alternateMobile,
            fax,
            highestQualificationOthers,
            contactTimeFrom,
            contactTimeTo,
            religion,
            custCategory,
            cast,
            aadharCardNo,
            panCard,
            passportNo,
            passportExpiry,
            rationCard,
            residenceStatus,
            maritalStatus,
            familyMemberCount,
            occupation,
            nationality,
            noOfDependents,
            age,
            applicationDate,
            status,
            highestQualification,
            hasAdharCardVerified,
            hasPanCardVerified,
            loanStatus,
            enqRefrenceNo,
            numberOfAssets,
            isActive,
            isDeleted,
            profileStepper,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            freeField1,
            freeField2,
            freeField3,
            residence,
            freeField5,
            freeField6,
            // enquiryDetailsId,
            organisationId,
            branchId,
            securityUserId,
            distinct
        );
    }

    // prettier-ignore
	@Override
	public String toString() {
		return "MemberCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (title != null ? "title=" + title + ", " : "")
				+ (firstName != null ? "firstName=" + firstName + ", " : "")
				+ (middleName != null ? "middleName=" + middleName + ", " : "")
				+ (lastName != null ? "lastName=" + lastName + ", " : "")
				+ (customerId != null ? "customerId=" + customerId + ", " : "")
				+ (membershipNo != null ? "membershipNo=" + membershipNo + ", " : "")
				+ (fatherName != null ? "fatherName=" + fatherName + ", " : "")
				+ (motherName != null ? "motherName=" + motherName + ", " : "")
				+ (gender != null ? "gender=" + gender + ", " : "") + (dob != null ? "dob=" + dob + ", " : "")
				+ (email != null ? "email=" + email + ", " : "")
				+ (mobileNo != null ? "mobileNo=" + mobileNo + ", " : "")
				+ (alternateMobile != null ? "alternateMobile=" + alternateMobile + ", " : "")
				+ (fax != null ? "fax=" + fax + ", " : "")
				+ (highestQualificationOthers != null ? "highestQualificationOthers=" + highestQualificationOthers + ", " : "")
				+ (contactTimeFrom != null ? "contactTimeFrom=" + contactTimeFrom + ", " : "")
				+ (contactTimeTo != null ? "contactTimeTo=" + contactTimeTo + ", " : "")
				+ (religion != null ? "religion=" + religion + ", " : "")
				+ (custCategory != null ? "custCategory=" + custCategory + ", " : "")
				+ (cast != null ? "cast=" + cast + ", " : "")
				+ (aadharCardNo != null ? "aadharCardNo=" + aadharCardNo + ", " : "")
				+ (panCard != null ? "panCard=" + panCard + ", " : "")
				+ (passportNo != null ? "passportNo=" + passportNo + ", " : "")
				+ (passportExpiry != null ? "passportExpiry=" + passportExpiry + ", " : "")
				+ (rationCard != null ? "rationCard=" + rationCard + ", " : "")
				+ (residenceStatus != null ? "residenceStatus=" + residenceStatus + ", " : "")
				+ (maritalStatus != null ? "maritalStatus=" + maritalStatus + ", " : "")
				+ (familyMemberCount != null ? "familyMemberCount=" + familyMemberCount + ", " : "")
				+ (occupation != null ? "occupation=" + occupation + ", " : "")
				+ (nationality != null ? "nationality=" + nationality + ", " : "")
				+ (noOfDependents != null ? "noOfDependents=" + noOfDependents + ", " : "")
				+ (age != null ? "age=" + age + ", " : "")
				+ (applicationDate != null ? "applicationDate=" + applicationDate + ", " : "")
				+ (status != null ? "status=" + status + ", " : "")
				+ (highestQualification != null ? "highestQualification=" + highestQualification + ", " : "")
				+ (hasAdharCardVerified != null ? "hasAdharCardVerified=" + hasAdharCardVerified + ", " : "")
				+ (hasPanCardVerified != null ? "hasPanCardVerified=" + hasPanCardVerified + ", " : "")
				+ (loanStatus != null ? "loanStatus=" + loanStatus + ", " : "")
				+ (enqRefrenceNo != null ? "enqRefrenceNo=" + enqRefrenceNo + ", " : "")
				+ (numberOfAssets != null ? "numberOfAssets=" + numberOfAssets + ", " : "")
				+ (isActive != null ? "isActive=" + isActive + ", " : "")
				+ (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "")
				+ (profileStepper != null ? "profileStepper=" + profileStepper + ", " : "")
				+ (lastModified != null ? "lastModified=" + lastModified + ", " : "")
				+ (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdOn != null ? "createdOn=" + createdOn + ", " : "")
				+ (freeField1 != null ? "freeField1=" + freeField1 + ", " : "")
				+ (freeField2 != null ? "freeField2=" + freeField2 + ", " : "")
				+ (freeField3 != null ? "freeField3=" + freeField3 + ", " : "")
				+ (residence != null ? "residence=" + residence + ", " : "")
				+ (freeField5 != null ? "freeField5=" + freeField5 + ", " : "")
				+ (freeField6 != null ? "freeField6=" + freeField6 + ", " : "") +
//            (enquiryDetailsId != null ? "enquiryDetailsId=" + enquiryDetailsId + ", " : "") +
				(organisationId != null ? "organisationId=" + organisationId + ", " : "")
				+ (branchId != null ? "branchId=" + branchId + ", " : "")
				+ (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "")
				+ (distinct != null ? "distinct=" + distinct + ", " : "") + "}";
	}
}
