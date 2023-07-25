package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.CompanyType;
import com.techvg.los.domain.enumeration.ConstitutionType;
import com.techvg.los.domain.enumeration.EmployementStatus;
import com.techvg.los.domain.enumeration.IndustryType;
import com.techvg.los.domain.enumeration.Occupation;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.EmployementDetails} entity. This class is used
 * in {@link com.techvg.los.web.rest.EmployementDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employement-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployementDetailsCriteria implements Serializable, Criteria {

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
     * Class for filtering EmployementStatus
     */
    public static class EmployementStatusFilter extends Filter<EmployementStatus> {

        public EmployementStatusFilter() {}

        public EmployementStatusFilter(EmployementStatusFilter filter) {
            super(filter);
        }

        @Override
        public EmployementStatusFilter copy() {
            return new EmployementStatusFilter(this);
        }
    }

    /**
     * Class for filtering CompanyType
     */
    public static class CompanyTypeFilter extends Filter<CompanyType> {

        public CompanyTypeFilter() {}

        public CompanyTypeFilter(CompanyTypeFilter filter) {
            super(filter);
        }

        @Override
        public CompanyTypeFilter copy() {
            return new CompanyTypeFilter(this);
        }
    }

    /**
     * Class for filtering ConstitutionType
     */
    public static class ConstitutionTypeFilter extends Filter<ConstitutionType> {

        public ConstitutionTypeFilter() {}

        public ConstitutionTypeFilter(ConstitutionTypeFilter filter) {
            super(filter);
        }

        @Override
        public ConstitutionTypeFilter copy() {
            return new ConstitutionTypeFilter(this);
        }
    }

    /**
     * Class for filtering IndustryType
     */
    public static class IndustryTypeFilter extends Filter<IndustryType> {

        public IndustryTypeFilter() {}

        public IndustryTypeFilter(IndustryTypeFilter filter) {
            super(filter);
        }

        @Override
        public IndustryTypeFilter copy() {
            return new IndustryTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private OccupationFilter type;

    private StringFilter employerName;

    private EmployementStatusFilter status;

    private StringFilter designation;

    private StringFilter duration;

    private StringFilter employerAdd;

    private StringFilter prevCompanyName;

    private StringFilter prevCompanyduration;

    private CompanyTypeFilter orgType;

    private ConstitutionTypeFilter constitutionType;

    private IndustryTypeFilter industryType;

    private StringFilter businessRegNo;

    private DoubleFilter compOwnerShip;

    private StringFilter partnerName1;

    private StringFilter partnerName2;

    private StringFilter partnerName3;

    private BooleanFilter isDeleted;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter createdBy;

    private InstantFilter createdOn;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter freeField5;

    private StringFilter freeField6;

    private LongFilter memberId;

    private LongFilter guarantorId;

    private LongFilter addressId;

    private Boolean distinct;

    public EmployementDetailsCriteria() {}

    public EmployementDetailsCriteria(EmployementDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.employerName = other.employerName == null ? null : other.employerName.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.designation = other.designation == null ? null : other.designation.copy();
        this.duration = other.duration == null ? null : other.duration.copy();
        this.employerAdd = other.employerAdd == null ? null : other.employerAdd.copy();
        this.prevCompanyName = other.prevCompanyName == null ? null : other.prevCompanyName.copy();
        this.prevCompanyduration = other.prevCompanyduration == null ? null : other.prevCompanyduration.copy();
        this.orgType = other.orgType == null ? null : other.orgType.copy();
        this.constitutionType = other.constitutionType == null ? null : other.constitutionType.copy();
        this.industryType = other.industryType == null ? null : other.industryType.copy();
        this.businessRegNo = other.businessRegNo == null ? null : other.businessRegNo.copy();
        this.compOwnerShip = other.compOwnerShip == null ? null : other.compOwnerShip.copy();
        this.partnerName1 = other.partnerName1 == null ? null : other.partnerName1.copy();
        this.partnerName2 = other.partnerName2 == null ? null : other.partnerName2.copy();
        this.partnerName3 = other.partnerName3 == null ? null : other.partnerName3.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.freeField6 = other.freeField6 == null ? null : other.freeField6.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.guarantorId = other.guarantorId == null ? null : other.guarantorId.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployementDetailsCriteria copy() {
        return new EmployementDetailsCriteria(this);
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

    public OccupationFilter getType() {
        return type;
    }

    public OccupationFilter type() {
        if (type == null) {
            type = new OccupationFilter();
        }
        return type;
    }

    public void setType(OccupationFilter type) {
        this.type = type;
    }

    public StringFilter getEmployerName() {
        return employerName;
    }

    public StringFilter employerName() {
        if (employerName == null) {
            employerName = new StringFilter();
        }
        return employerName;
    }

    public void setEmployerName(StringFilter employerName) {
        this.employerName = employerName;
    }

    public EmployementStatusFilter getStatus() {
        return status;
    }

    public EmployementStatusFilter status() {
        if (status == null) {
            status = new EmployementStatusFilter();
        }
        return status;
    }

    public void setStatus(EmployementStatusFilter status) {
        this.status = status;
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

    public StringFilter getDuration() {
        return duration;
    }

    public StringFilter duration() {
        if (duration == null) {
            duration = new StringFilter();
        }
        return duration;
    }

    public void setDuration(StringFilter duration) {
        this.duration = duration;
    }

    public StringFilter getEmployerAdd() {
        return employerAdd;
    }

    public StringFilter employerAdd() {
        if (employerAdd == null) {
            employerAdd = new StringFilter();
        }
        return employerAdd;
    }

    public void setEmployerAdd(StringFilter employerAdd) {
        this.employerAdd = employerAdd;
    }

    public StringFilter getPrevCompanyName() {
        return prevCompanyName;
    }

    public StringFilter prevCompanyName() {
        if (prevCompanyName == null) {
            prevCompanyName = new StringFilter();
        }
        return prevCompanyName;
    }

    public void setPrevCompanyName(StringFilter prevCompanyName) {
        this.prevCompanyName = prevCompanyName;
    }

    public StringFilter getPrevCompanyduration() {
        return prevCompanyduration;
    }

    public StringFilter prevCompanyduration() {
        if (prevCompanyduration == null) {
            prevCompanyduration = new StringFilter();
        }
        return prevCompanyduration;
    }

    public void setPrevCompanyduration(StringFilter prevCompanyduration) {
        this.prevCompanyduration = prevCompanyduration;
    }

    public CompanyTypeFilter getOrgType() {
        return orgType;
    }

    public CompanyTypeFilter orgType() {
        if (orgType == null) {
            orgType = new CompanyTypeFilter();
        }
        return orgType;
    }

    public void setOrgType(CompanyTypeFilter orgType) {
        this.orgType = orgType;
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

    public IndustryTypeFilter getIndustryType() {
        return industryType;
    }

    public IndustryTypeFilter industryType() {
        if (industryType == null) {
            industryType = new IndustryTypeFilter();
        }
        return industryType;
    }

    public void setIndustryType(IndustryTypeFilter industryType) {
        this.industryType = industryType;
    }

    public StringFilter getBusinessRegNo() {
        return businessRegNo;
    }

    public StringFilter businessRegNo() {
        if (businessRegNo == null) {
            businessRegNo = new StringFilter();
        }
        return businessRegNo;
    }

    public void setBusinessRegNo(StringFilter businessRegNo) {
        this.businessRegNo = businessRegNo;
    }

    public DoubleFilter getCompOwnerShip() {
        return compOwnerShip;
    }

    public DoubleFilter compOwnerShip() {
        if (compOwnerShip == null) {
            compOwnerShip = new DoubleFilter();
        }
        return compOwnerShip;
    }

    public void setCompOwnerShip(DoubleFilter compOwnerShip) {
        this.compOwnerShip = compOwnerShip;
    }

    public StringFilter getPartnerName1() {
        return partnerName1;
    }

    public StringFilter partnerName1() {
        if (partnerName1 == null) {
            partnerName1 = new StringFilter();
        }
        return partnerName1;
    }

    public void setPartnerName1(StringFilter partnerName1) {
        this.partnerName1 = partnerName1;
    }

    public StringFilter getPartnerName2() {
        return partnerName2;
    }

    public StringFilter partnerName2() {
        if (partnerName2 == null) {
            partnerName2 = new StringFilter();
        }
        return partnerName2;
    }

    public void setPartnerName2(StringFilter partnerName2) {
        this.partnerName2 = partnerName2;
    }

    public StringFilter getPartnerName3() {
        return partnerName3;
    }

    public StringFilter partnerName3() {
        if (partnerName3 == null) {
            partnerName3 = new StringFilter();
        }
        return partnerName3;
    }

    public void setPartnerName3(StringFilter partnerName3) {
        this.partnerName3 = partnerName3;
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

    public LongFilter getGuarantorId() {
        return guarantorId;
    }

    public LongFilter guarantorId() {
        if (guarantorId == null) {
            guarantorId = new LongFilter();
        }
        return guarantorId;
    }

    public void setGuarantorId(LongFilter guarantorId) {
        this.guarantorId = guarantorId;
    }

    public LongFilter getAddressId() {
        return addressId;
    }

    public LongFilter addressId() {
        if (addressId == null) {
            addressId = new LongFilter();
        }
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
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
        final EmployementDetailsCriteria that = (EmployementDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(employerName, that.employerName) &&
            Objects.equals(status, that.status) &&
            Objects.equals(designation, that.designation) &&
            Objects.equals(duration, that.duration) &&
            Objects.equals(employerAdd, that.employerAdd) &&
            Objects.equals(prevCompanyName, that.prevCompanyName) &&
            Objects.equals(prevCompanyduration, that.prevCompanyduration) &&
            Objects.equals(orgType, that.orgType) &&
            Objects.equals(constitutionType, that.constitutionType) &&
            Objects.equals(industryType, that.industryType) &&
            Objects.equals(businessRegNo, that.businessRegNo) &&
            Objects.equals(compOwnerShip, that.compOwnerShip) &&
            Objects.equals(partnerName1, that.partnerName1) &&
            Objects.equals(partnerName2, that.partnerName2) &&
            Objects.equals(partnerName3, that.partnerName3) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(freeField6, that.freeField6) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(guarantorId, that.guarantorId) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            type,
            employerName,
            status,
            designation,
            duration,
            employerAdd,
            prevCompanyName,
            prevCompanyduration,
            orgType,
            constitutionType,
            industryType,
            businessRegNo,
            compOwnerShip,
            partnerName1,
            partnerName2,
            partnerName3,
            isDeleted,
            lastModified,
            lastModifiedBy,
            createdBy,
            createdOn,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            freeField5,
            freeField6,
            memberId,
            guarantorId,
            addressId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployementDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (employerName != null ? "employerName=" + employerName + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (designation != null ? "designation=" + designation + ", " : "") +
            (duration != null ? "duration=" + duration + ", " : "") +
            (employerAdd != null ? "employerAdd=" + employerAdd + ", " : "") +
            (prevCompanyName != null ? "prevCompanyName=" + prevCompanyName + ", " : "") +
            (prevCompanyduration != null ? "prevCompanyduration=" + prevCompanyduration + ", " : "") +
            (orgType != null ? "orgType=" + orgType + ", " : "") +
            (constitutionType != null ? "constitutionType=" + constitutionType + ", " : "") +
            (industryType != null ? "industryType=" + industryType + ", " : "") +
            (businessRegNo != null ? "businessRegNo=" + businessRegNo + ", " : "") +
            (compOwnerShip != null ? "compOwnerShip=" + compOwnerShip + ", " : "") +
            (partnerName1 != null ? "partnerName1=" + partnerName1 + ", " : "") +
            (partnerName2 != null ? "partnerName2=" + partnerName2 + ", " : "") +
            (partnerName3 != null ? "partnerName3=" + partnerName3 + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (freeField5 != null ? "freeField5=" + freeField5 + ", " : "") +
            (freeField6 != null ? "freeField6=" + freeField6 + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
            (guarantorId != null ? "guarantorId=" + guarantorId + ", " : "") +
            (addressId != null ? "addressId=" + addressId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
