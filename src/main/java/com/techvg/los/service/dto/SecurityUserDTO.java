package com.techvg.los.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.techvg.los.domain.SecurityUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SecurityUserDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String designation;

    @NotNull
    private String username;

    @NotNull
    private String passwordHash;

    private String mobileNo;

    private String email;

    private String description;

    private String department;

    private String imageUrl;

    private String imageUrlContentType;
    private Boolean isActivated;

    private String langKey;

    private String activationKey;

    private String resetKey;

    private Instant resetDate;

    private String createdBy;

    private Instant createdOn;

    private Instant lastModified;

    private String lastModifiedBy;

    private String employeeId;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private Long branchId;

    private OrganisationDTO organisation;

    private Set<SecurityPermissionDTO> securityPermissions = new HashSet<>();

    private Set<SecurityRoleDTO> securityRoles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrlContentType() {
        return imageUrlContentType;
    }

    public void setImageUrlContentType(String imageUrlContentType) {
        this.imageUrlContentType = imageUrlContentType;
    }

    public Boolean getIsActivated() {
        return isActivated;
    }

    public void setIsActivated(Boolean isActivated) {
        this.isActivated = isActivated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Set<SecurityPermissionDTO> getSecurityPermissions() {
        return securityPermissions;
    }

    public void setSecurityPermissions(Set<SecurityPermissionDTO> securityPermissions) {
        this.securityPermissions = securityPermissions;
    }

    public Set<SecurityRoleDTO> getSecurityRoles() {
        return securityRoles;
    }

    public void setSecurityRoles(Set<SecurityRoleDTO> securityRoles) {
        this.securityRoles = securityRoles;
    }

    public OrganisationDTO getOrganisation() {
        return organisation;
    }

    public void setOrganisation(OrganisationDTO organisation) {
        this.organisation = organisation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecurityUserDTO)) {
            return false;
        }

        SecurityUserDTO securityUserDTO = (SecurityUserDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, securityUserDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
	@Override
	public String toString() {
		return "SecurityUserDTO{" +
				"id=" + getId() +
				", firstName='" + getFirstName() + "'" +
				", lastName='" + getLastName() + "'" +
				", designation='" + getDesignation() + "'" +
				", username='" + getUsername() + "'" +
				", passwordHash='" + getPasswordHash() + "'" +
				", mobileNo='" + getMobileNo() + "'" +
				", email='" + getEmail() + "'" +
				", description='" + getDescription() + "'" +
				", department='" + getDepartment() + "'" +
				", imageUrl='" + getImageUrl() + "'" +
				", isActivated='" + getIsActivated() + "'" +
				", langKey='" + getLangKey() + "'" +
				", activationKey='" + getActivationKey() + "'" +
				", resetKey='" + getResetKey() + "'" +
				", resetDate='" + getResetDate() + "'" +
				", createdBy='" + getCreatedBy() + "'" +
				", createdOn='" + getCreatedOn() + "'" +
				", lastModified='" + getLastModified() + "'" +
				", lastModifiedBy='" + getLastModifiedBy() + "'" +
				", employeeId='" + getEmployeeId() + "'" +
				", freeField1='" + getFreeField1() + "'" +
				", freeField2='" + getFreeField2() + "'" +
				", freeField3='" + getFreeField3() + "'" +
				", freeField4='" + getFreeField4() + "'" +
				", branchId=" + getBranchId() +
				", securityPermissions=" + getSecurityPermissions() +
				", securityRoles=" + getSecurityRoles() +
				"}";
	}
}
