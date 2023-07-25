package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.los.domain.enumeration.AssetNature;
import com.techvg.los.domain.enumeration.AssetType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MemberAssets.
 */
@Entity
@Table(name = "member_assets")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberAssets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "asset_cost")
    private Double assetCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_type")
    private AssetType assetType;

    @Column(name = "area_in_sq_ft")
    private Double areaInSqFt;

    @Enumerated(EnumType.STRING)
    @Column(name = "asset_nature")
    private AssetNature assetNature;

    @Column(name = "address")
    private String address;

    @Column(name = "land_mark")
    private String landMark;

    @Column(name = "asset_owner")
    private String assetOwner;

    @Column(name = "completion_year")
    private String completionYear;

    @Column(name = "market_value")
    private Double marketValue;

    @Column(name = "mortgage_percentage")
    private Double mortgagePercentage;

    @Column(name = "is_insured")
    private Boolean isInsured;

    @Column(name = "is_under_used")
    private Boolean isUnderUsed;

    @Column(name = "is_owned")
    private Boolean isOwned;

    @Column(name = "is_mortgage")
    private Boolean isMortgage;

    @Column(name = "land_type")
    private String landType;

    @Column(name = "land_gat_no")
    private String landGatNo;

    @Column(name = "land_area_in_hector")
    private Double landAreaInHector;

    @Column(name = "jindagi_patrak_no")
    private String jindagiPatrakNo;

    @Column(name = "jindagi_amount")
    private Double jindagiAmount;

    @Column(name = "mortgage_Creation_date")
    private Instant mortgageCreationDate;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    @Column(name = "free_field_5")
    private String freeField5;

    @Column(name = "free_field_6")
    private String freeField6;

    @ManyToOne
    @JsonIgnoreProperties(value = { "enquiryDetails", "branch", "member", "securityUser" }, allowSetters = true)
    private Member member;

    @ManyToOne
    @JsonIgnoreProperties(value = { "member", "guarantor" }, allowSetters = true)
    private Guarantor guarantor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "member", "securityUser", "product" }, allowSetters = true)
    private LoanApplications loanApplications;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MemberAssets id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAssetCost() {
        return this.assetCost;
    }

    public MemberAssets assetCost(Double assetCost) {
        this.setAssetCost(assetCost);
        return this;
    }

    public void setAssetCost(Double assetCost) {
        this.assetCost = assetCost;
    }

    public AssetType getAssetType() {
        return this.assetType;
    }

    public MemberAssets assetType(AssetType assetType) {
        this.setAssetType(assetType);
        return this;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public Double getAreaInSqFt() {
        return this.areaInSqFt;
    }

    public MemberAssets areaInSqFt(Double areaInSqFt) {
        this.setAreaInSqFt(areaInSqFt);
        return this;
    }

    public void setAreaInSqFt(Double areaInSqFt) {
        this.areaInSqFt = areaInSqFt;
    }

    public AssetNature getAssetNature() {
        return this.assetNature;
    }

    public MemberAssets assetNature(AssetNature assetNature) {
        this.setAssetNature(assetNature);
        return this;
    }

    public void setAssetNature(AssetNature assetNature) {
        this.assetNature = assetNature;
    }

    public String getAddress() {
        return this.address;
    }

    public MemberAssets address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandMark() {
        return this.landMark;
    }

    public MemberAssets landMark(String landMark) {
        this.setLandMark(landMark);
        return this;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getAssetOwner() {
        return this.assetOwner;
    }

    public MemberAssets assetOwner(String assetOwner) {
        this.setAssetOwner(assetOwner);
        return this;
    }

    public void setAssetOwner(String assetOwner) {
        this.assetOwner = assetOwner;
    }

    public String getCompletionYear() {
        return this.completionYear;
    }

    public MemberAssets completionYear(String completionYear) {
        this.setCompletionYear(completionYear);
        return this;
    }

    public void setCompletionYear(String completionYear) {
        this.completionYear = completionYear;
    }

    public Double getMarketValue() {
        return this.marketValue;
    }

    public MemberAssets marketValue(Double marketValue) {
        this.setMarketValue(marketValue);
        return this;
    }

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }

    public Double getMortgagePercentage() {
        return this.mortgagePercentage;
    }

    public MemberAssets mortgagePercentage(Double mortgagePercentage) {
        this.setMortgagePercentage(mortgagePercentage);
        return this;
    }

    public void setMortgagePercentage(Double mortgagePercentage) {
        this.mortgagePercentage = mortgagePercentage;
    }

    public Boolean getIsInsured() {
        return this.isInsured;
    }

    public MemberAssets isInsured(Boolean isInsured) {
        this.setIsInsured(isInsured);
        return this;
    }

    public void setIsInsured(Boolean isInsured) {
        this.isInsured = isInsured;
    }

    public Boolean getIsUnderUsed() {
        return this.isUnderUsed;
    }

    public MemberAssets isUnderUsed(Boolean isUnderUsed) {
        this.setIsUnderUsed(isUnderUsed);
        return this;
    }

    public void setIsUnderUsed(Boolean isUnderUsed) {
        this.isUnderUsed = isUnderUsed;
    }

    public Boolean getIsOwned() {
        return this.isOwned;
    }

    public MemberAssets isOwned(Boolean isOwned) {
        this.setIsOwned(isOwned);
        return this;
    }

    public void setIsOwned(Boolean isOwned) {
        this.isOwned = isOwned;
    }

    public Boolean getIsMortgage() {
        return this.isMortgage;
    }

    public MemberAssets isMortgage(Boolean isMortgage) {
        this.setIsMortgage(isMortgage);
        return this;
    }

    public void setIsMortgage(Boolean isMortgage) {
        this.isMortgage = isMortgage;
    }

    public String getLandType() {
        return this.landType;
    }

    public MemberAssets landType(String landType) {
        this.setLandType(landType);
        return this;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    public String getLandGatNo() {
        return this.landGatNo;
    }

    public MemberAssets landGatNo(String landGatNo) {
        this.setLandGatNo(landGatNo);
        return this;
    }

    public void setLandGatNo(String landGatNo) {
        this.landGatNo = landGatNo;
    }

    public Double getLandAreaInHector() {
        return this.landAreaInHector;
    }

    public MemberAssets landAreaInHector(Double landAreaInHector) {
        this.setLandAreaInHector(landAreaInHector);
        return this;
    }

    public void setLandAreaInHector(Double landAreaInHector) {
        this.landAreaInHector = landAreaInHector;
    }

    public String getJindagiPatrakNo() {
        return this.jindagiPatrakNo;
    }

    public MemberAssets jindagiPatrakNo(String jindagiPatrakNo) {
        this.setJindagiPatrakNo(jindagiPatrakNo);
        return this;
    }

    public void setJindagiPatrakNo(String jindagiPatrakNo) {
        this.jindagiPatrakNo = jindagiPatrakNo;
    }

    public Double getJindagiAmount() {
        return this.jindagiAmount;
    }

    public MemberAssets jindagiAmount(Double jindagiAmount) {
        this.setJindagiAmount(jindagiAmount);
        return this;
    }

    public void setJindagiAmount(Double jindagiAmount) {
        this.jindagiAmount = jindagiAmount;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public MemberAssets lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public Instant getMortgageCreationDate() {
        return this.mortgageCreationDate;
    }

    public MemberAssets mortgageCreationDate(Instant mortgageCreationDate) {
        this.setMortgageCreationDate(mortgageCreationDate);
        return this;
    }

    public void setMortgageCreationDate(Instant mortgageCreationDate) {
        this.mortgageCreationDate = mortgageCreationDate;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public MemberAssets lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public MemberAssets createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public MemberAssets createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public MemberAssets isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public MemberAssets freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public MemberAssets freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public MemberAssets freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public MemberAssets freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public MemberAssets freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getFreeField6() {
        return this.freeField6;
    }

    public MemberAssets freeField6(String freeField6) {
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

    public MemberAssets member(Member member) {
        this.setMember(member);
        return this;
    }

    public Guarantor getGuarantor() {
        return this.guarantor;
    }

    public void setGuarantor(Guarantor guarantor) {
        this.guarantor = guarantor;
    }

    public MemberAssets guarantor(Guarantor guarantor) {
        this.setGuarantor(guarantor);
        return this;
    }

    public LoanApplications getLoanApplications() {
        return this.loanApplications;
    }

    public void setLoanApplications(LoanApplications loanApplications) {
        this.loanApplications = loanApplications;
    }

    public MemberAssets loanApplications(LoanApplications loanApplications) {
        this.setLoanApplications(loanApplications);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberAssets)) {
            return false;
        }
        return id != null && id.equals(((MemberAssets) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberAssets{" +
            "id=" + getId() +
            ", assetCost=" + getAssetCost() +
            ", assetType='" + getAssetType() + "'" +
            ", areaInSqFt=" + getAreaInSqFt() +
            ", assetNature='" + getAssetNature() + "'" +
            ", address='" + getAddress() + "'" +
            ", landMark='" + getLandMark() + "'" +
            ", assetOwner='" + getAssetOwner() + "'" +
            ", completionYear='" + getCompletionYear() + "'" +
            ", marketValue=" + getMarketValue() +
            ", mortgagePercentage=" + getMortgagePercentage() +
            ", isInsured='" + getIsInsured() + "'" +
            ", isUnderUsed='" + getIsUnderUsed() + "'" +
            ", isOwned='" + getIsOwned() + "'" +
            ", isMortgage='" + getIsMortgage() + "'" +
            ", landType='" + getLandType() + "'" +
            ", landGatNo='" + getLandGatNo() + "'" +
            ", landAreaInHector=" + getLandAreaInHector() +
            ", jindagiPatrakNo='" + getJindagiPatrakNo() + "'" +
            ", jindagiAmount=" + getJindagiAmount() +
            ", lastModified='" + getLastModified() + "'" +
            ", mortgageCreationDate='" + getMortgageCreationDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", freeField5='" + getFreeField5() + "'" +
            ", freeField6='" + getFreeField6() + "'" +
            "}";
    }
}
