package com.techvg.los.domain;

import com.techvg.los.domain.enumeration.RepaymentFreqency;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MasterAgreement.
 */
@Entity
@Table(name = "master_agreement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MasterAgreement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "portfolio_code")
    private String portfolioCode;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "home_branch")
    private String homeBranch;

    @Column(name = "serv_branch")
    private String servBranch;

    @Column(name = "home_state")
    private String homeState;

    @Column(name = "serv_state")
    private String servState;

    @Column(name = "gst_exempted")
    private String gstExempted;

    @Column(name = "pref_repay_mode")
    private String prefRepayMode;

    @Column(name = "tds_code")
    private String tdsCode;

    @Column(name = "tds_rate")
    private String tdsRate;

    @Column(name = "sanctioned_amount")
    private Double sanctionedAmount;

    @Column(name = "origination_appln_no")
    private String originationApplnNo;

    @Column(name = "cycle_day")
    private Long cycleDay;

    @Column(name = "tenor")
    private String tenor;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "repay_freq")
    private RepaymentFreqency repayFreq;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MasterAgreement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public MasterAgreement memberId(String memberId) {
        this.setMemberId(memberId);
        return this;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPortfolioCode() {
        return this.portfolioCode;
    }

    public MasterAgreement portfolioCode(String portfolioCode) {
        this.setPortfolioCode(portfolioCode);
        return this;
    }

    public void setPortfolioCode(String portfolioCode) {
        this.portfolioCode = portfolioCode;
    }

    public String getProductCode() {
        return this.productCode;
    }

    public MasterAgreement productCode(String productCode) {
        this.setProductCode(productCode);
        return this;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getHomeBranch() {
        return this.homeBranch;
    }

    public MasterAgreement homeBranch(String homeBranch) {
        this.setHomeBranch(homeBranch);
        return this;
    }

    public void setHomeBranch(String homeBranch) {
        this.homeBranch = homeBranch;
    }

    public String getServBranch() {
        return this.servBranch;
    }

    public MasterAgreement servBranch(String servBranch) {
        this.setServBranch(servBranch);
        return this;
    }

    public void setServBranch(String servBranch) {
        this.servBranch = servBranch;
    }

    public String getHomeState() {
        return this.homeState;
    }

    public MasterAgreement homeState(String homeState) {
        this.setHomeState(homeState);
        return this;
    }

    public void setHomeState(String homeState) {
        this.homeState = homeState;
    }

    public String getServState() {
        return this.servState;
    }

    public MasterAgreement servState(String servState) {
        this.setServState(servState);
        return this;
    }

    public void setServState(String servState) {
        this.servState = servState;
    }

    public String getGstExempted() {
        return this.gstExempted;
    }

    public MasterAgreement gstExempted(String gstExempted) {
        this.setGstExempted(gstExempted);
        return this;
    }

    public void setGstExempted(String gstExempted) {
        this.gstExempted = gstExempted;
    }

    public String getPrefRepayMode() {
        return this.prefRepayMode;
    }

    public MasterAgreement prefRepayMode(String prefRepayMode) {
        this.setPrefRepayMode(prefRepayMode);
        return this;
    }

    public void setPrefRepayMode(String prefRepayMode) {
        this.prefRepayMode = prefRepayMode;
    }

    public String getTdsCode() {
        return this.tdsCode;
    }

    public MasterAgreement tdsCode(String tdsCode) {
        this.setTdsCode(tdsCode);
        return this;
    }

    public void setTdsCode(String tdsCode) {
        this.tdsCode = tdsCode;
    }

    public String getTdsRate() {
        return this.tdsRate;
    }

    public MasterAgreement tdsRate(String tdsRate) {
        this.setTdsRate(tdsRate);
        return this;
    }

    public void setTdsRate(String tdsRate) {
        this.tdsRate = tdsRate;
    }

    public Double getSanctionedAmount() {
        return this.sanctionedAmount;
    }

    public MasterAgreement sanctionedAmount(Double sanctionedAmount) {
        this.setSanctionedAmount(sanctionedAmount);
        return this;
    }

    public void setSanctionedAmount(Double sanctionedAmount) {
        this.sanctionedAmount = sanctionedAmount;
    }

    public String getOriginationApplnNo() {
        return this.originationApplnNo;
    }

    public MasterAgreement originationApplnNo(String originationApplnNo) {
        this.setOriginationApplnNo(originationApplnNo);
        return this;
    }

    public void setOriginationApplnNo(String originationApplnNo) {
        this.originationApplnNo = originationApplnNo;
    }

    public Long getCycleDay() {
        return this.cycleDay;
    }

    public MasterAgreement cycleDay(Long cycleDay) {
        this.setCycleDay(cycleDay);
        return this;
    }

    public void setCycleDay(Long cycleDay) {
        this.cycleDay = cycleDay;
    }

    public String getTenor() {
        return this.tenor;
    }

    public MasterAgreement tenor(String tenor) {
        this.setTenor(tenor);
        return this;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }

    public Double getInterestRate() {
        return this.interestRate;
    }

    public MasterAgreement interestRate(Double interestRate) {
        this.setInterestRate(interestRate);
        return this;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public RepaymentFreqency getRepayFreq() {
        return this.repayFreq;
    }

    public MasterAgreement repayFreq(RepaymentFreqency repayFreq) {
        this.setRepayFreq(repayFreq);
        return this;
    }

    public void setRepayFreq(RepaymentFreqency repayFreq) {
        this.repayFreq = repayFreq;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public MasterAgreement isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public MasterAgreement lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public MasterAgreement lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public MasterAgreement freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public MasterAgreement freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public MasterAgreement freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public MasterAgreement freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MasterAgreement)) {
            return false;
        }
        return id != null && id.equals(((MasterAgreement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MasterAgreement{" +
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
