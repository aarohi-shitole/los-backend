package com.techvg.los.service.dto;

import com.techvg.los.domain.enumeration.DocumentHelper;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.RemarkHistory} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RemarkHistoryDTO implements Serializable {

    private Long id;

    private String remark;

    private Double loanAmt;

    private Double modifiedAmt;

    private Double loanInterest;

    private DocumentHelper tag;

    private Double modifiedInterest;

    private Instant createdOn;

    private String createdBy;

    private String branch;

    private String applicationNo;

    private Instant lastModified;

    private String lastModifiedBy;

    private Boolean verified;

    private Boolean flag;

    private String remarkCode;

    private String remarkType;

    private String remarkSubType;

    private Long referenceId;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private String freeField5;

    private Double valutaionAmt;

    private Long securityUserId;

    private LoanApplicationsDTO loanApplications;

    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(Double loanAmt) {
        this.loanAmt = loanAmt;
    }

    public Double getModifiedAmt() {
        return modifiedAmt;
    }

    public void setModifiedAmt(Double modifiedAmt) {
        this.modifiedAmt = modifiedAmt;
    }

    public Double getLoanInterest() {
        return loanInterest;
    }

    public void setLoanInterest(Double loanInterest) {
        this.loanInterest = loanInterest;
    }

    public Double getModifiedInterest() {
        return modifiedInterest;
    }

    public void setModifiedInterest(Double modifiedInterest) {
        this.modifiedInterest = modifiedInterest;
    }

    public DocumentHelper getTag() {
        return tag;
    }

    public void setTag(DocumentHelper tag) {
        this.tag = tag;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
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

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
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

    public void setRemarkCode(String remarkCode) {
        this.remarkCode = remarkCode;
    }

    public String getRemarkType() {
        return remarkType;
    }

    public void setRemarkType(String remarkType) {
        this.remarkType = remarkType;
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

    public Double getValutaionAmt() {
        return valutaionAmt;
    }

    public void setValutaionAmt(Double valutaionAmt) {
        this.valutaionAmt = valutaionAmt;
    }

    public Long getSecurityUserId() {
        return securityUserId;
    }

    public void setSecurityUserId(Long securityUserId) {
        this.securityUserId = securityUserId;
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
        if (!(o instanceof RemarkHistoryDTO)) {
            return false;
        }

        RemarkHistoryDTO remarkHistoryDTO = (RemarkHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, remarkHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
	@Override
	public String toString() {
		return "RemarkHistoryDTO{" + "id=" + getId() + ", remark='" + getRemark() + "'" + ", loanAmt=" + getLoanAmt()
				+ ", modifiedAmt=" + getModifiedAmt() + ", loanInterest=" + getLoanInterest() + ", modifiedInterest="
				+ getModifiedInterest() + ", createdOn='" + getCreatedOn() + "'" + ", createdBy='" + getCreatedBy()
				+ "'" + ", branch='" + getBranch() + "'" + ", applicationNo='" + getApplicationNo() + "'"
				+ ", lastModified='" + getLastModified() + "'" + ", lastModifiedBy='" + getLastModifiedBy() + "'"
				+ ", verified='" + getVerified() + "'" + ", flag ='" + getFlag() + "'" + ", tag='" + getTag() + "'"
				+ ", remarkCode='" + getRemarkCode() + "'" + ", remarkType='" + getRemarkType() + "'"
				+ ", remarkSubType='" + getRemarkSubType() + "'" + ", referenceId='" + getReferenceId() + "'"
				+ ", freeField1='" + getFreeField1() + "'" + ", freeField2='" + getFreeField2() + "'" + ", freeField3='"
				+ getFreeField3() + "'" + ", freeField4='" + getFreeField4() + "'" + ", freeField5='" + getFreeField5()
				+ "'" + ", valutaionAmt='" + getValutaionAmt() + "'" + ", securityUserId=" + getSecurityUserId()
				+ ", loanApplications=" + getLoanApplications() + "}";
	}

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
