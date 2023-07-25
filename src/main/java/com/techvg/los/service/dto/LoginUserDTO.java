package com.techvg.los.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * A DTO representing a user, with his authorities.
 */
public class LoginUserDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String imageUrl;

    private String langKey;

    private Instant resetDate;

    private String mobileNo;

    private Set<String> authorities;

    private Set<String> roles;

    private BranchDTO branch;

    private OrganisationDTO organisation;

    public LoginUserDTO() {
        // TODO Auto-generated constructor stub
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((authorities == null) ? 0 : authorities.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((imageUrl == null) ? 0 : imageUrl.hashCode());
        result = prime * result + ((langKey == null) ? 0 : langKey.hashCode());
        result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((mobileNo == null) ? 0 : mobileNo.hashCode());
        result = prime * result + ((resetDate == null) ? 0 : resetDate.hashCode());
        result = prime * result + ((roles == null) ? 0 : roles.hashCode());
        result = prime * result + ((branch == null) ? 0 : branch.hashCode());
        result = prime * result + ((organisation == null) ? 0 : organisation.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        LoginUserDTO other = (LoginUserDTO) obj;
        if (authorities == null) {
            if (other.authorities != null) return false;
        } else if (!authorities.equals(other.authorities)) return false;
        if (email == null) {
            if (other.email != null) return false;
        } else if (!email.equals(other.email)) return false;
        if (firstName == null) {
            if (other.firstName != null) return false;
        } else if (!firstName.equals(other.firstName)) return false;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        if (imageUrl == null) {
            if (other.imageUrl != null) return false;
        } else if (!imageUrl.equals(other.imageUrl)) return false;
        if (langKey == null) {
            if (other.langKey != null) return false;
        } else if (!langKey.equals(other.langKey)) return false;
        if (lastName == null) {
            if (other.lastName != null) return false;
        } else if (!lastName.equals(other.lastName)) return false;
        if (username == null) {
            if (other.username != null) return false;
        } else if (!username.equals(other.username)) return false;
        if (mobileNo == null) {
            if (other.mobileNo != null) return false;
        } else if (!mobileNo.equals(other.mobileNo)) return false;
        if (resetDate == null) {
            if (other.resetDate != null) return false;
        } else if (!resetDate.equals(other.resetDate)) return false;
        if (roles == null) {
            if (other.roles != null) return false;
        } else if (!roles.equals(other.roles)) return false;
        if (branch == null) {
            if (other.branch != null) return false;
        } else if (!branch.equals(other.branch)) return false;
        if (organisation == null) {
            if (other.organisation != null) return false;
        } else if (!organisation.equals(other.organisation)) return false;
        return true;
    }

    @Override
    public String toString() {
        return (
            "LoginUserDTO [id=" +
            id +
            ", firstName=" +
            firstName +
            ", lastName=" +
            lastName +
            ", username=" +
            username +
            ", email=" +
            email +
            ", imageUrl=" +
            imageUrl +
            ", langKey=" +
            langKey +
            ", resetDate=" +
            resetDate +
            ", mobileNo=" +
            mobileNo +
            ", authorities=" +
            authorities +
            ", roles=" +
            roles +
            ", branch=" +
            branch +
            ", organisation=" +
            organisation +
            "]"
        );
    }

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    public OrganisationDTO getOrganisation() {
        return organisation;
    }

    public void setOrganisation(OrganisationDTO organisation) {
        this.organisation = organisation;
    }
}
