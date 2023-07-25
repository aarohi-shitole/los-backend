package com.techvg.los.service.dto;

import com.techvg.los.domain.enumeration.RepaymentFreqency;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.MasterAgreement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MasterAgreementDTO implements Serializable {

    private Long id;

    private String memberId;

    private String portfolioCode;

    private String productCode;

    private String homeBranch;

    private String servBranch;

    private String homeState;

    private String servState;

    private String gstExempted;

    private String prefRepayMode;

    private String tdsCode;

    private String tdsRate;

    private Double sanctionedAmount;

    private String originationApplnNo;

    private Long cycleDay;

    private String tenor;

    private Double interestRate;

    private RepaymentFreqency repayFreq;

    private Boolean isDeleted;

    private Instant lastModified;

    private String lastModifiedBy;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPortfolioCode() {
        return portfolioCode;
    }

    public void setPortfolioCode(String portfolioCode) {
        this.portfolioCode = portfolioCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getHomeBranch() {
        return homeBranch;
    }

    public void setHomeBranch(String homeBranch) {
        this.homeBranch = homeBranch;
    }

    public String getServBranch() {
        return servBranch;
    }

    public void setServBranch(String servBranch) {
        this.servBranch = servBranch;
    }

    public String getHomeState() {
        return homeState;
    }

    public void setHomeState(String homeState) {
        this.homeState = homeState;
    }

    public String getServState() {
        return servState;
    }

    public void setServState(String servState) {
        this.servState = servState;
    }

    public String getGstExempted() {
        return gstExempted;
    }

    public void setGstExempted(String gstExempted) {
        this.gstExempted = gstExempted;
    }

    public String getPrefRepayMode() {
        return prefRepayMode;
    }

    public void setPrefRepayMode(String prefRepayMode) {
        this.prefRepayMode = prefRepayMode;
    }

    public String getTdsCode() {
        return tdsCode;
    }

    public void setTdsCode(String tdsCode) {
        this.tdsCode = tdsCode;
    }

    public String getTdsRate() {
        return tdsRate;
    }

    public void setTdsRate(String tdsRate) {
        this.tdsRate = tdsRate;
    }

    public Double getSanctionedAmount() {
        return sanctionedAmount;
    }

    public void setSanctionedAmount(Double sanctionedAmount) {
        this.sanctionedAmount = sanctionedAmount;
    }

    public String getOriginationApplnNo() {
        return originationApplnNo;
    }

    public void setOriginationApplnNo(String originationApplnNo) {
        this.originationApplnNo = originationApplnNo;
    }

    public Long getCycleDay() {
        return cycleDay;
    }

    public void setCycleDay(Long cycleDay) {
        this.cycleDay = cycleDay;
    }

    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public RepaymentFreqency getRepayFreq() {
        return repayFreq;
    }

    public void setRepayFreq(RepaymentFreqency repayFreq) {
        this.repayFreq = repayFreq;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MasterAgreementDTO)) {
            return false;
        }

        MasterAgreementDTO masterAgreementDTO = (MasterAgreementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, masterAgreementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MasterAgreementDTO{" +
            "id=" + getId() +
            ", memberId='" + getMemberId() + "'" +
            ", portfolioCode='" + getPortfolioCode() + "'" +
            ", productCode='" + getProductCode() + "'" +
            ", homeBranch='" + getHomeBranch() + "'" +
            ", servBranch='" + getServBranch() + "'" +
            ", homeState='" + getHomeState() + "'" +
            ", servState='" + getServState() + "'" +
            ", gstExempted='" + getGstExempted() + "'" +
            ", prefRepayMode='" + getPrefRepayMode() + "'" +
            ", tdsCode='" + getTdsCode() + "'" +
            ", tdsRate='" + getTdsRate() + "'" +
            ", sanctionedAmount=" + getSanctionedAmount() +
            ", originationApplnNo='" + getOriginationApplnNo() + "'" +
            ", cycleDay=" + getCycleDay() +
            ", tenor='" + getTenor() + "'" +
            ", interestRate=" + getInterestRate() +
            ", repayFreq='" + getRepayFreq() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            "}";
    }
}
