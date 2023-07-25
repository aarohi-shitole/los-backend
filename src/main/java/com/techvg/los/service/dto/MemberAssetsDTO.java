package com.techvg.los.service.dto;

import com.techvg.los.domain.enumeration.AssetNature;
import com.techvg.los.domain.enumeration.AssetType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.MemberAssets} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MemberAssetsDTO implements Serializable {

    private Long id;

    private Double assetCost;

    private AssetType assetType;

    private Double areaInSqFt;

    private AssetNature assetNature;

    private String address;

    private String landMark;

    private String assetOwner;

    private String completionYear;

    private Double marketValue;

    private Instant mortgageCreationDate;

    private Double mortgagePercentage;

    private Boolean isInsured;

    private Boolean isUnderUsed;

    private Boolean isOwned;

    private Boolean isMortgage;

    private String landType;

    private String landGatNo;

    private Double landAreaInHector;

    private String jindagiPatrakNo;

    private Double jindagiAmount;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private Instant createdOn;

    private Boolean isDeleted;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private String freeField5;

    private String freeField6;

    private MemberDTO member;

    private GuarantorDTO guarantor;

    private LoanApplicationsDTO loanApplications;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAssetCost() {
        return assetCost;
    }

    public void setAssetCost(Double assetCost) {
        this.assetCost = assetCost;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public Double getAreaInSqFt() {
        return areaInSqFt;
    }

    public void setAreaInSqFt(Double areaInSqFt) {
        this.areaInSqFt = areaInSqFt;
    }

    public AssetNature getAssetNature() {
        return assetNature;
    }

    public void setAssetNature(AssetNature assetNature) {
        this.assetNature = assetNature;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getAssetOwner() {
        return assetOwner;
    }

    public void setAssetOwner(String assetOwner) {
        this.assetOwner = assetOwner;
    }

    public String getCompletionYear() {
        return completionYear;
    }

    public void setCompletionYear(String completionYear) {
        this.completionYear = completionYear;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }

    public Double getMortgagePercentage() {
        return mortgagePercentage;
    }

    public void setMortgagePercentage(Double mortgagePercentage) {
        this.mortgagePercentage = mortgagePercentage;
    }

    public Instant getMortgageCreationDate() {
        return mortgageCreationDate;
    }

    public void setMortgageCreationDate(Instant mortgageCreationDate) {
        this.mortgageCreationDate = mortgageCreationDate;
    }

    public Boolean getIsInsured() {
        return isInsured;
    }

    public void setIsInsured(Boolean isInsured) {
        this.isInsured = isInsured;
    }

    public Boolean getIsUnderUsed() {
        return isUnderUsed;
    }

    public void setIsUnderUsed(Boolean isUnderUsed) {
        this.isUnderUsed = isUnderUsed;
    }

    public Boolean getIsOwned() {
        return isOwned;
    }

    public void setIsOwned(Boolean isOwned) {
        this.isOwned = isOwned;
    }

    public Boolean getIsMortgage() {
        return isMortgage;
    }

    public void setIsMortgage(Boolean isMortgage) {
        this.isMortgage = isMortgage;
    }

    public String getLandType() {
        return landType;
    }

    public void setLandType(String landType) {
        this.landType = landType;
    }

    public String getLandGatNo() {
        return landGatNo;
    }

    public void setLandGatNo(String landGatNo) {
        this.landGatNo = landGatNo;
    }

    public Double getLandAreaInHector() {
        return landAreaInHector;
    }

    public void setLandAreaInHector(Double landAreaInHector) {
        this.landAreaInHector = landAreaInHector;
    }

    public String getJindagiPatrakNo() {
        return jindagiPatrakNo;
    }

    public void setJindagiPatrakNo(String jindagiPatrakNo) {
        this.jindagiPatrakNo = jindagiPatrakNo;
    }

    public Double getJindagiAmount() {
        return jindagiAmount;
    }

    public void setJindagiAmount(Double jindagiAmount) {
        this.jindagiAmount = jindagiAmount;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getFreeField5() {
        return freeField5;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getFreeField6() {
        return freeField6;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    public GuarantorDTO getGuarantor() {
        return guarantor;
    }

    public void setGuarantor(GuarantorDTO guarantor) {
        this.guarantor = guarantor;
    }

    public LoanApplicationsDTO getLoanApplications() {
        return loanApplications;
    }

    public void setLoanApplications(LoanApplicationsDTO loanApplications) {
        this.loanApplications = loanApplications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MemberAssetsDTO)) {
            return false;
        }

        MemberAssetsDTO memberAssetsDTO = (MemberAssetsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, memberAssetsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MemberAssetsDTO{" +
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
            ", mortgageCreationDate='" + getMortgageCreationDate() + "'" +
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
            ", member=" + getMember() +
            ", guarantor=" + getGuarantor() +
            ", loanApplications=" + getLoanApplications() +
            "}";
    }
}
