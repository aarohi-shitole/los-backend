package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.los.domain.enumeration.DocumentHelper;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RemarkHistory.
 */
@Entity
@Table(name = "remark_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RemarkHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "remark")
    private String remark;

    @Column(name = "loan_amt")
    private Double loanAmt;

    @Column(name = "modified_amt")
    private Double modifiedAmt;

    @Column(name = "loan_interest")
    private Double loanInterest;

    @Column(name = "modified_interest")
    private Double modifiedInterest;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "branch")
    private String branch;

    @Column(name = "application_no")
    private String applicationNo;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "flag")
    private Boolean flag;

    @Column(name = "remarkCode")
    private String remarkCode;

    @Column(name = "remarkType")
    private String remarkType;

    @Column(name = "remarkSubType")
    private String remarkSubType;

    @Column(name = "referenceId")
    private Long referenceId;

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

    @Column(name = "valutaion_amt")
    private Double valutaionAmt;

    @Enumerated(EnumType.STRING)
    @Column(name = "tag")
    private DocumentHelper tag;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JsonIgnoreProperties(value = { "branch", "securityPermissions", "securityRoles" }, allowSetters = true)
    private SecurityUser securityUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "member", "securityUser", "product" }, allowSetters = true)
    private LoanApplications loanApplications;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public DocumentHelper getTag() {
        return this.tag;
    }

    public RemarkHistory tag(DocumentHelper tag) {
        this.setTag(tag);
        return this;
    }

    public void setTag(DocumentHelper tag) {
        this.tag = tag;
    }

    public Long getId() {
        return this.id;
    }

    public RemarkHistory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return this.remark;
    }

    public RemarkHistory remark(String remark) {
        this.setRemark(remark);
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getLoanAmt() {
        return this.loanAmt;
    }

    public RemarkHistory loanAmt(Double loanAmt) {
        this.setLoanAmt(loanAmt);
        return this;
    }

    public void setLoanAmt(Double loanAmt) {
        this.loanAmt = loanAmt;
    }

    public Double getModifiedAmt() {
        return this.modifiedAmt;
    }

    public RemarkHistory modifiedAmt(Double modifiedAmt) {
        this.setModifiedAmt(modifiedAmt);
        return this;
    }

    public void setModifiedAmt(Double modifiedAmt) {
        this.modifiedAmt = modifiedAmt;
    }

    public Double getLoanInterest() {
        return this.loanInterest;
    }

    public RemarkHistory loanInterest(Double loanInterest) {
        this.setLoanInterest(loanInterest);
        return this;
    }

    public void setLoanInterest(Double loanInterest) {
        this.loanInterest = loanInterest;
    }

    public Double getModifiedInterest() {
        return this.modifiedInterest;
    }

    public RemarkHistory modifiedInterest(Double modifiedInterest) {
        this.setModifiedInterest(modifiedInterest);
        return this;
    }

    public void setModifiedInterest(Double modifiedInterest) {
        this.modifiedInterest = modifiedInterest;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public RemarkHistory createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public RemarkHistory createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getBranch() {
        return this.branch;
    }

    public RemarkHistory branch(String branch) {
        this.setBranch(branch);
        return this;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getApplicationNo() {
        return this.applicationNo;
    }

    public RemarkHistory applicationNo(String applicationNo) {
        this.setApplicationNo(applicationNo);
        return this;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public RemarkHistory lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public RemarkHistory lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public RemarkHistory verified(Boolean verified) {
        this.setVerified(verified);
        return this;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public RemarkHistory flag(Boolean flag) {
        this.setFlag(flag);
        return this;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getRemarkCode() {
        return remarkCode;
    }

    public RemarkHistory remarkCode(String remarkCode) {
        this.setRemarkCode(remarkCode);
        return this;
    }

    public void setRemarkCode(String remarkCode) {
        this.remarkCode = remarkCode;
    }

    public RemarkHistory remarkType(String remarkType) {
        this.setRemarkType(remarkType);
        return this;
    }

    public String getRemarkType() {
        return remarkType;
    }

    public void setRemarkType(String remarkType) {
        this.remarkType = remarkType;
    }

    public RemarkHistory remarkSubType(String remarkSubType) {
        this.setRemarkType(remarkSubType);
        return this;
    }

    public String getRemarkSubType() {
        return remarkSubType;
    }

    public void setRemarkSubType(String remarkSubType) {
        this.remarkSubType = remarkSubType;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public RemarkHistory freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public RemarkHistory freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public RemarkHistory freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public RemarkHistory freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public RemarkHistory freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public Double getValutaionAmt() {
        return this.valutaionAmt;
    }

    public RemarkHistory valutaionAmt(Double valutaionAmt) {
        this.setValutaionAmt(valutaionAmt);
        return this;
    }

    public void setValutaionAmt(Double valutaionAmt) {
        this.valutaionAmt = valutaionAmt;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public RemarkHistory securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    public LoanApplications getLoanApplications() {
        return this.loanApplications;
    }

    public void setLoanApplications(LoanApplications loanApplications) {
        this.loanApplications = loanApplications;
    }

    public RemarkHistory loanApplications(LoanApplications loanApplications) {
        this.setLoanApplications(loanApplications);
        return this;
    }

    public RemarkHistory isDeleted(Boolean idDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RemarkHistory)) {
            return false;
        }
        return id != null && id.equals(((RemarkHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
	@Override
	public String toString() {
		return "RemarkHistory{" +
				"id=" + getId() +
				", remark='" + getRemark() + "'" +
				", loanAmt=" + getLoanAmt() +
				", modifiedAmt=" + getModifiedAmt() +
				", loanInterest=" + getLoanInterest() +
				", modifiedInterest=" + getModifiedInterest() +
				", createdOn='" + getCreatedOn() + "'" +
				", createdBy='" + getCreatedBy() + "'" +
				", branch='" + getBranch() + "'" +
				", applicationNo='" + getApplicationNo() + "'" +
				", lastModified='" + getLastModified() + "'" +
				", lastModifiedBy='" + getLastModifiedBy() + "'" +
				", verified='" + getVerified() + "'" +
				", flag='" + getFlag() + "'"+ 
				", isDeleted='" + getIsDeleted() + "'"+
				", remarkCode='"+ getRemarkCode() + "'" +
				", remarkRemarkType='"+ getRemarkType() + "'" +
				", remarkRemarkSubType='"+ getRemarkSubType() + "'" +
				", referenceId='"+ getReferenceId() +"'"+
				", freeField1='" + getFreeField1() + "'" +
				", freeField2='" + getFreeField2() + "'" +
				", freeField3='" + getFreeField3() + "'" +
				", freeField4='" + getFreeField4() + "'" +
				", freeField5='" + getFreeField5() + "'" +
				", valutaionAmt='" + getValutaionAmt() + "'" +
				 ", tag='" + getTag() + "'" +
				"}";
	}
}
