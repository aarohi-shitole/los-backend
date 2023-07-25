package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.OrganisationType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.Organisation} entity. This class is used
 * in {@link com.techvg.los.web.rest.OrganisationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /organisations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrganisationCriteria implements Serializable, Criteria {

    /**
     * Class for filtering OrganisationType
     */
    public static class OrganisationTypeFilter extends Filter<OrganisationType> {

        public OrganisationTypeFilter() {}

        public OrganisationTypeFilter(OrganisationTypeFilter filter) {
            super(filter);
        }

        @Override
        public OrganisationTypeFilter copy() {
            return new OrganisationTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter orgName;

    private StringFilter description;

    private StringFilter regNumber;

    private StringFilter gstin;

    private StringFilter pan;

    private StringFilter tan;

    private StringFilter phone;

    private StringFilter email;

    private StringFilter webSite;

    private StringFilter fax;

    private BooleanFilter isActivate;

    private OrganisationTypeFilter orgType;

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

    private Boolean distinct;

    public OrganisationCriteria() {}

    public OrganisationCriteria(OrganisationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.orgName = other.orgName == null ? null : other.orgName.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.regNumber = other.regNumber == null ? null : other.regNumber.copy();
        this.gstin = other.gstin == null ? null : other.gstin.copy();
        this.pan = other.pan == null ? null : other.pan.copy();
        this.tan = other.tan == null ? null : other.tan.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.webSite = other.webSite == null ? null : other.webSite.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.isActivate = other.isActivate == null ? null : other.isActivate.copy();
        this.orgType = other.orgType == null ? null : other.orgType.copy();
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
        this.distinct = other.distinct;
    }

    @Override
    public OrganisationCriteria copy() {
        return new OrganisationCriteria(this);
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

    public StringFilter getOrgName() {
        return orgName;
    }

    public StringFilter orgName() {
        if (orgName == null) {
            orgName = new StringFilter();
        }
        return orgName;
    }

    public void setOrgName(StringFilter orgName) {
        this.orgName = orgName;
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

    public StringFilter getRegNumber() {
        return regNumber;
    }

    public StringFilter regNumber() {
        if (regNumber == null) {
            regNumber = new StringFilter();
        }
        return regNumber;
    }

    public void setRegNumber(StringFilter regNumber) {
        this.regNumber = regNumber;
    }

    public StringFilter getGstin() {
        return gstin;
    }

    public StringFilter gstin() {
        if (gstin == null) {
            gstin = new StringFilter();
        }
        return gstin;
    }

    public void setGstin(StringFilter gstin) {
        this.gstin = gstin;
    }

    public StringFilter getPan() {
        return pan;
    }

    public StringFilter pan() {
        if (pan == null) {
            pan = new StringFilter();
        }
        return pan;
    }

    public void setPan(StringFilter pan) {
        this.pan = pan;
    }

    public StringFilter getTan() {
        return tan;
    }

    public StringFilter tan() {
        if (tan == null) {
            tan = new StringFilter();
        }
        return tan;
    }

    public void setTan(StringFilter tan) {
        this.tan = tan;
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

    public OrganisationTypeFilter getOrgType() {
        return orgType;
    }

    public OrganisationTypeFilter orgType() {
        if (orgType == null) {
            orgType = new OrganisationTypeFilter();
        }
        return orgType;
    }

    public void setOrgType(OrganisationTypeFilter orgType) {
        this.orgType = orgType;
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
        final OrganisationCriteria that = (OrganisationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(orgName, that.orgName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(regNumber, that.regNumber) &&
            Objects.equals(gstin, that.gstin) &&
            Objects.equals(pan, that.pan) &&
            Objects.equals(tan, that.tan) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(webSite, that.webSite) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(isActivate, that.isActivate) &&
            Objects.equals(orgType, that.orgType) &&
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
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            orgName,
            description,
            regNumber,
            gstin,
            pan,
            tan,
            phone,
            email,
            webSite,
            fax,
            isActivate,
            orgType,
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
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganisationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (orgName != null ? "orgName=" + orgName + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (regNumber != null ? "regNumber=" + regNumber + ", " : "") +
            (gstin != null ? "gstin=" + gstin + ", " : "") +
            (pan != null ? "pan=" + pan + ", " : "") +
            (tan != null ? "tan=" + tan + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (webSite != null ? "webSite=" + webSite + ", " : "") +
            (fax != null ? "fax=" + fax + ", " : "") +
            (isActivate != null ? "isActivate=" + isActivate + ", " : "") +
            (orgType != null ? "orgType=" + orgType + ", " : "") +
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
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
