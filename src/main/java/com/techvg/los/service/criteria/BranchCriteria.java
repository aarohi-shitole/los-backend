package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.BranchType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.Branch} entity. This class is used
 * in {@link com.techvg.los.web.rest.BranchResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /branches?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BranchCriteria implements Serializable, Criteria {

    /**
     * Class for filtering BranchType
     */
    public static class BranchTypeFilter extends Filter<BranchType> {

        public BranchTypeFilter() {}

        public BranchTypeFilter(BranchTypeFilter filter) {
            super(filter);
        }

        @Override
        public BranchTypeFilter copy() {
            return new BranchTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter branchName;

    private StringFilter description;

    private StringFilter branchcode;

    private StringFilter ifscCode;

    private StringFilter micrCode;

    private StringFilter swiftCode;

    private StringFilter ibanCode;

    private BooleanFilter isHeadOffice;

    private StringFilter routingTransitNo;

    private StringFilter phone;

    private StringFilter email;

    private StringFilter webSite;

    private StringFilter fax;

    private BooleanFilter isActivate;

    private BranchTypeFilter branchType;

    private InstantFilter createdOn;

    private StringFilter createdBy;

    private StringFilter isDeleted;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter freeField5;

    private LongFilter addressId;

    private LongFilter organisationId;

    //    private LongFilter branchId;

    private LongFilter regionId;

    private Boolean distinct;

    public BranchCriteria() {}

    public BranchCriteria(BranchCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.branchName = other.branchName == null ? null : other.branchName.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.branchcode = other.branchcode == null ? null : other.branchcode.copy();
        this.ifscCode = other.ifscCode == null ? null : other.ifscCode.copy();
        this.micrCode = other.micrCode == null ? null : other.micrCode.copy();
        this.swiftCode = other.swiftCode == null ? null : other.swiftCode.copy();
        this.ibanCode = other.ibanCode == null ? null : other.ibanCode.copy();
        this.isHeadOffice = other.isHeadOffice == null ? null : other.isHeadOffice.copy();
        this.routingTransitNo = other.routingTransitNo == null ? null : other.routingTransitNo.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.webSite = other.webSite == null ? null : other.webSite.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.isActivate = other.isActivate == null ? null : other.isActivate.copy();
        this.branchType = other.branchType == null ? null : other.branchType.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.organisationId = other.organisationId == null ? null : other.organisationId.copy();
        //        this.branchId = other.branchId == null ? null : other.branchId.copy();
        this.regionId = other.regionId == null ? null : other.regionId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BranchCriteria copy() {
        return new BranchCriteria(this);
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

    public StringFilter getBranchName() {
        return branchName;
    }

    public StringFilter branchName() {
        if (branchName == null) {
            branchName = new StringFilter();
        }
        return branchName;
    }

    public void setBranchName(StringFilter branchName) {
        this.branchName = branchName;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getBranchcode() {
        return branchcode;
    }

    public StringFilter branchcode() {
        if (branchcode == null) {
            branchcode = new StringFilter();
        }
        return branchcode;
    }

    public void setBranchcode(StringFilter branchcode) {
        this.branchcode = branchcode;
    }

    public StringFilter getIfscCode() {
        return ifscCode;
    }

    public StringFilter ifscCode() {
        if (ifscCode == null) {
            ifscCode = new StringFilter();
        }
        return ifscCode;
    }

    public void setIfscCode(StringFilter ifscCode) {
        this.ifscCode = ifscCode;
    }

    public StringFilter getMicrCode() {
        return micrCode;
    }

    public StringFilter micrCode() {
        if (micrCode == null) {
            micrCode = new StringFilter();
        }
        return micrCode;
    }

    public void setMicrCode(StringFilter micrCode) {
        this.micrCode = micrCode;
    }

    public StringFilter getSwiftCode() {
        return swiftCode;
    }

    public StringFilter swiftCode() {
        if (swiftCode == null) {
            swiftCode = new StringFilter();
        }
        return swiftCode;
    }

    public void setSwiftCode(StringFilter swiftCode) {
        this.swiftCode = swiftCode;
    }

    public StringFilter getIbanCode() {
        return ibanCode;
    }

    public StringFilter ibanCode() {
        if (ibanCode == null) {
            ibanCode = new StringFilter();
        }
        return ibanCode;
    }

    public void setIbanCode(StringFilter ibanCode) {
        this.ibanCode = ibanCode;
    }

    public BooleanFilter getIsHeadOffice() {
        return isHeadOffice;
    }

    public BooleanFilter isHeadOffice() {
        if (isHeadOffice == null) {
            isHeadOffice = new BooleanFilter();
        }
        return isHeadOffice;
    }

    public void setIsHeadOffice(BooleanFilter isHeadOffice) {
        this.isHeadOffice = isHeadOffice;
    }

    public StringFilter getRoutingTransitNo() {
        return routingTransitNo;
    }

    public StringFilter routingTransitNo() {
        if (routingTransitNo == null) {
            routingTransitNo = new StringFilter();
        }
        return routingTransitNo;
    }

    public void setRoutingTransitNo(StringFilter routingTransitNo) {
        this.routingTransitNo = routingTransitNo;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public StringFilter phone() {
        if (phone == null) {
            phone = new StringFilter();
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
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

    public StringFilter getWebSite() {
        return webSite;
    }

    public StringFilter webSite() {
        if (webSite == null) {
            webSite = new StringFilter();
        }
        return webSite;
    }

    public void setWebSite(StringFilter webSite) {
        this.webSite = webSite;
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

    public BooleanFilter getIsActivate() {
        return isActivate;
    }

    public BooleanFilter isActivate() {
        if (isActivate == null) {
            isActivate = new BooleanFilter();
        }
        return isActivate;
    }

    public void setIsActivate(BooleanFilter isActivate) {
        this.isActivate = isActivate;
    }

    public BranchTypeFilter getBranchType() {
        return branchType;
    }

    public BranchTypeFilter branchType() {
        if (branchType == null) {
            branchType = new BranchTypeFilter();
        }
        return branchType;
    }

    public void setBranchType(BranchTypeFilter branchType) {
        this.branchType = branchType;
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

    public StringFilter getIsDeleted() {
        return isDeleted;
    }

    public StringFilter isDeleted() {
        if (isDeleted == null) {
            isDeleted = new StringFilter();
        }
        return isDeleted;
    }

    public void setIsDeleted(StringFilter isDeleted) {
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

    //    public LongFilter getBranchId() {
    //        return branchId;
    //    }
    //
    //    public LongFilter branchId() {
    //        if (branchId == null) {
    //            branchId = new LongFilter();
    //        }
    //        return branchId;
    //    }
    //
    //    public void setBranchId(LongFilter branchId) {
    //        this.branchId = branchId;
    //    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    public LongFilter getRegionId() {
        return regionId;
    }

    public LongFilter regionId() {
        if (regionId == null) {
            regionId = new LongFilter();
        }
        return regionId;
    }

    public void setRegionId(LongFilter regionId) {
        this.regionId = regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BranchCriteria that = (BranchCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(branchName, that.branchName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(branchcode, that.branchcode) &&
            Objects.equals(ifscCode, that.ifscCode) &&
            Objects.equals(micrCode, that.micrCode) &&
            Objects.equals(swiftCode, that.swiftCode) &&
            Objects.equals(ibanCode, that.ibanCode) &&
            Objects.equals(isHeadOffice, that.isHeadOffice) &&
            Objects.equals(routingTransitNo, that.routingTransitNo) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(webSite, that.webSite) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(isActivate, that.isActivate) &&
            Objects.equals(branchType, that.branchType) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(organisationId, that.organisationId) &&
            //            Objects.equals(branchId, that.branchId) &&
            Objects.equals(regionId, that.regionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            branchName,
            description,
            branchcode,
            ifscCode,
            micrCode,
            swiftCode,
            ibanCode,
            isHeadOffice,
            routingTransitNo,
            phone,
            email,
            webSite,
            fax,
            isActivate,
            branchType,
            createdOn,
            createdBy,
            isDeleted,
            lastModified,
            lastModifiedBy,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            freeField5,
            addressId,
            organisationId,
            //            branchId,
            regionId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BranchCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (branchName != null ? "branchName=" + branchName + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (branchcode != null ? "branchcode=" + branchcode + ", " : "") +
            (ifscCode != null ? "ifscCode=" + ifscCode + ", " : "") +
            (micrCode != null ? "micrCode=" + micrCode + ", " : "") +
            (swiftCode != null ? "swiftCode=" + swiftCode + ", " : "") +
            (ibanCode != null ? "ibanCode=" + ibanCode + ", " : "") +
            (isHeadOffice != null ? "isHeadOffice=" + isHeadOffice + ", " : "") +
            (routingTransitNo != null ? "routingTransitNo=" + routingTransitNo + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (webSite != null ? "webSite=" + webSite + ", " : "") +
            (fax != null ? "fax=" + fax + ", " : "") +
            (isActivate != null ? "isActivate=" + isActivate + ", " : "") +
            (branchType != null ? "branchType=" + branchType + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (freeField5 != null ? "freeField5=" + freeField5 + ", " : "") +
            (addressId != null ? "addressId=" + addressId + ", " : "") +
            (organisationId != null ? "organisationId=" + organisationId + ", " : "") +
//            (branchId != null ? "branchId=" + branchId + ", " : "") +
            (regionId != null ? "regionId=" + regionId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
