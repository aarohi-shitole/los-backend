package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.DocumentHelper;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.RemarkHistory} entity.
 * This class is used in {@link com.techvg.los.web.rest.RemarkHistoryResource}
 * to receive all the possible filtering options from the Http GET request
 * parameters. For example the following could be a valid request:
 * {@code /remark-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific
 * {@link Filter} class are used, we need to use fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RemarkHistoryCriteria implements Serializable, Criteria {

    public static class DocumentHelperFilter extends Filter<DocumentHelper> {

        public DocumentHelperFilter() {}

        public DocumentHelperFilter(DocumentHelperFilter filter) {
            super(filter);
        }

        @Override
        public DocumentHelperFilter copy() {
            return new DocumentHelperFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DocumentHelperFilter tag;

    private StringFilter remark;

    private DoubleFilter loanAmt;

    private DoubleFilter modifiedAmt;

    private DoubleFilter loanInterest;

    private DoubleFilter modifiedInterest;

    private InstantFilter createdOn;

    private StringFilter createdBy;

    private StringFilter branch;

    private StringFilter applicationNo;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private BooleanFilter verified;

    private BooleanFilter flag;

    private StringFilter remarkCode;

    private StringFilter remarkType;

    private StringFilter remarkSubType;

    private LongFilter referenceId;

    private StringFilter freeField1;

    private StringFilter freeField2;

    private StringFilter freeField3;

    private StringFilter freeField4;

    private StringFilter freeField5;

    private DoubleFilter valutaionAmt;

    private LongFilter securityUserId;

    private LongFilter loanApplicationsId;

    private Boolean distinct;

    private BooleanFilter isDeleted;

    public RemarkHistoryCriteria() {}

    public RemarkHistoryCriteria(RemarkHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.loanAmt = other.loanAmt == null ? null : other.loanAmt.copy();
        this.modifiedAmt = other.modifiedAmt == null ? null : other.modifiedAmt.copy();
        this.loanInterest = other.loanInterest == null ? null : other.loanInterest.copy();
        this.modifiedInterest = other.modifiedInterest == null ? null : other.modifiedInterest.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.branch = other.branch == null ? null : other.branch.copy();
        this.applicationNo = other.applicationNo == null ? null : other.applicationNo.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.verified = other.verified == null ? null : other.verified.copy();
        this.flag = other.flag == null ? null : other.flag.copy();
        this.remarkCode = other.remarkCode == null ? null : other.remarkCode.copy();
        this.remarkType = other.remarkType == null ? null : other.remarkType.copy();
        this.remarkSubType = other.remarkSubType == null ? null : other.remarkSubType.copy();
        this.tag = other.tag == null ? null : other.tag.copy();
        this.referenceId = other.referenceId == null ? null : other.referenceId.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.valutaionAmt = other.valutaionAmt == null ? null : other.valutaionAmt.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.loanApplicationsId = other.loanApplicationsId == null ? null : other.loanApplicationsId.copy();
        this.isDeleted = other.isDeleted == null ? null : other.isDeleted.copy();
        this.distinct = other.distinct;
    }

    @Override
    public RemarkHistoryCriteria copy() {
        return new RemarkHistoryCriteria(this);
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

    public StringFilter getRemark() {
        return remark;
    }

    public StringFilter remark() {
        if (remark == null) {
            remark = new StringFilter();
        }
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
    }

    public DoubleFilter getLoanAmt() {
        return loanAmt;
    }

    public DoubleFilter loanAmt() {
        if (loanAmt == null) {
            loanAmt = new DoubleFilter();
        }
        return loanAmt;
    }

    public void setLoanAmt(DoubleFilter loanAmt) {
        this.loanAmt = loanAmt;
    }

    public DoubleFilter getModifiedAmt() {
        return modifiedAmt;
    }

    public DoubleFilter modifiedAmt() {
        if (modifiedAmt == null) {
            modifiedAmt = new DoubleFilter();
        }
        return modifiedAmt;
    }

    public void setModifiedAmt(DoubleFilter modifiedAmt) {
        this.modifiedAmt = modifiedAmt;
    }

    public DoubleFilter getLoanInterest() {
        return loanInterest;
    }

    public DoubleFilter loanInterest() {
        if (loanInterest == null) {
            loanInterest = new DoubleFilter();
        }
        return loanInterest;
    }

    public void setLoanInterest(DoubleFilter loanInterest) {
        this.loanInterest = loanInterest;
    }

    public DoubleFilter getModifiedInterest() {
        return modifiedInterest;
    }

    public DoubleFilter modifiedInterest() {
        if (modifiedInterest == null) {
            modifiedInterest = new DoubleFilter();
        }
        return modifiedInterest;
    }

    public void setModifiedInterest(DoubleFilter modifiedInterest) {
        this.modifiedInterest = modifiedInterest;
    }

    public DocumentHelperFilter getTag() {
        return tag;
    }

    public DocumentHelperFilter tag() {
        if (tag == null) {
            tag = new DocumentHelperFilter();
        }
        return tag;
    }

    public void setTag(DocumentHelperFilter tag) {
        this.tag = tag;
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

    public StringFilter getBranch() {
        return branch;
    }

    public StringFilter branch() {
        if (branch == null) {
            branch = new StringFilter();
        }
        return branch;
    }

    public void setBranch(StringFilter branch) {
        this.branch = branch;
    }

    public StringFilter getApplicationNo() {
        return applicationNo;
    }

    public StringFilter applicationNo() {
        if (applicationNo == null) {
            applicationNo = new StringFilter();
        }
        return applicationNo;
    }

    public void setApplicationNo(StringFilter applicationNo) {
        this.applicationNo = applicationNo;
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

    public BooleanFilter getVerified() {
        return verified;
    }

    public BooleanFilter verified() {
        if (verified == null) {
            verified = new BooleanFilter();
        }
        return verified;
    }

    public void setVerified(BooleanFilter verified) {
        this.verified = verified;
    }

    public BooleanFilter getFlag() {
        return flag;
    }

    public BooleanFilter flag() {
        if (flag == null) {
            flag = new BooleanFilter();
        }
        return flag;
    }

    public void setFlag(BooleanFilter flag) {
        this.flag = flag;
    }

    public StringFilter getRemarkCode() {
        return remarkCode;
    }

    public StringFilter remarkCode() {
        if (remarkCode == null) {
            remarkCode = new StringFilter();
        }
        return remarkCode;
    }

    public void setRemarkCode(StringFilter remarkCode) {
        this.remarkCode = remarkCode;
    }

    public StringFilter getRemarkType() {
        return remarkType;
    }

    public StringFilter remarkType() {
        if (remarkType == null) {
            remarkType = new StringFilter();
        }
        return remarkType;
    }

    public void setRemarkType(StringFilter remarkType) {
        this.remarkType = remarkType;
    }

    public StringFilter getRemarkSubType() {
        return remarkSubType;
    }

    public StringFilter remarkSubType() {
        if (remarkSubType == null) {
            remarkSubType = new StringFilter();
        }
        return remarkSubType;
    }

    public void setRemarkSubType(StringFilter remarkSubType) {
        this.remarkSubType = remarkSubType;
    }

    public LongFilter getReferenceId() {
        return referenceId;
    }

    public LongFilter referenceId() {
        if (referenceId == null) {
            referenceId = new LongFilter();
        }
        return referenceId;
    }

    public void setReferenceId(LongFilter referenceId) {
        this.referenceId = referenceId;
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

    public DoubleFilter getValutaionAmt() {
        return valutaionAmt;
    }

    public DoubleFilter valutaionAmt() {
        if (valutaionAmt == null) {
            valutaionAmt = new DoubleFilter();
        }
        return valutaionAmt;
    }

    public void setValutaionAmt(DoubleFilter valutaionAmt) {
        this.valutaionAmt = valutaionAmt;
    }

    public LongFilter getSecurityUserId() {
        return securityUserId;
    }

    public LongFilter securityUserId() {
        if (securityUserId == null) {
            securityUserId = new LongFilter();
        }
        return securityUserId;
    }

    public void setSecurityUserId(LongFilter securityUserId) {
        this.securityUserId = securityUserId;
    }

    public LongFilter getLoanApplicationsId() {
        return loanApplicationsId;
    }

    public LongFilter loanApplicationsId() {
        if (loanApplicationsId == null) {
            loanApplicationsId = new LongFilter();
        }
        return loanApplicationsId;
    }

    public void setLoanApplicationsId(LongFilter loanApplicationsId) {
        this.loanApplicationsId = loanApplicationsId;
    }

    public BooleanFilter getIsDeleted() {
        return isDeleted;
    }

    public BooleanFilter isDeleted() {
        if (isDeleted == null) {
            isDeleted = new BooleanFilter();
        }
        return isDeleted;
    }

    public void setIsDeleted(BooleanFilter isDeleted) {
        this.isDeleted = isDeleted;
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
        final RemarkHistoryCriteria that = (RemarkHistoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(loanAmt, that.loanAmt) &&
            Objects.equals(modifiedAmt, that.modifiedAmt) &&
            Objects.equals(loanInterest, that.loanInterest) &&
            Objects.equals(modifiedInterest, that.modifiedInterest) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(branch, that.branch) &&
            Objects.equals(applicationNo, that.applicationNo) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(verified, that.verified) &&
            Objects.equals(flag, that.flag) &&
            Objects.equals(tag, that.tag) &&
            Objects.equals(remarkCode, that.remarkCode) &&
            Objects.equals(remarkType, that.remarkType) &&
            Objects.equals(remarkSubType, that.remarkSubType) &&
            Objects.equals(referenceId, that.referenceId) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(valutaionAmt, that.valutaionAmt) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(loanApplicationsId, that.loanApplicationsId) &&
            Objects.equals(isDeleted, that.isDeleted) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            remark,
            loanAmt,
            modifiedAmt,
            loanInterest,
            modifiedInterest,
            createdOn,
            createdBy,
            branch,
            tag,
            applicationNo,
            lastModified,
            lastModifiedBy,
            verified,
            flag,
            remarkCode,
            remarkType,
            remarkSubType,
            referenceId,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            freeField5,
            valutaionAmt,
            securityUserId,
            loanApplicationsId,
            isDeleted,
            distinct
        );
    }

    // prettier-ignore
	@Override
	public String toString() {
		return "RemarkHistoryCriteria{" + (id != null ? "id=" + id + ", " : "")
				+ (remark != null ? "remark=" + remark + ", " : "")
				+ (loanAmt != null ? "loanAmt=" + loanAmt + ", " : "")
				+ (modifiedAmt != null ? "modifiedAmt=" + modifiedAmt + ", " : "")
				+ (tag != null ? "tag=" + tag + ", " : "")
				+ (loanInterest != null ? "loanInterest=" + loanInterest + ", " : "")
				+ (modifiedInterest != null ? "modifiedInterest=" + modifiedInterest + ", " : "")
				+ (createdOn != null ? "createdOn=" + createdOn + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (branch != null ? "branch=" + branch + ", " : "")
				+ (applicationNo != null ? "applicationNo=" + applicationNo + ", " : "")
				+ (lastModified != null ? "lastModified=" + lastModified + ", " : "")
				+ (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "")
				+ (verified != null ? "verified=" + verified + ", " : "") 
				+ (flag != null ? "flag=" + flag + "," : "")
				+ (remarkCode != null ? "remarkCode=" + remarkCode + "," : " ")
				+ (remarkType != null ? "remarkType=" + remarkType + "," : " ")
				+ (remarkSubType != null ? "remarkSubType=" + remarkSubType + "," : " ")
				+ (referenceId != null ? "referenceId=" + referenceId + "," : " ")
				+ (freeField1 != null ? "freeField1=" + freeField1 + ", " : "")
				+ (freeField2 != null ? "freeField2=" + freeField2 + ", " : "")
				+ (freeField3 != null ? "freeField3=" + freeField3 + ", " : "")
				+ (freeField4 != null ? "freeField4=" + freeField4 + ", " : "")
				+ (freeField5 != null ? "freeField5=" + freeField5 + ", " : "")
				+ (valutaionAmt != null ? "valutaionAmt=" + valutaionAmt + ", " : "")
				+ (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "")
				+ (loanApplicationsId != null ? "loanApplicationsId=" + loanApplicationsId + ", " : "")
				+ (isDeleted != null ? "isDeleted=" + isDeleted + ", " : "") 
				+ (distinct != null ? "distinct=" + distinct + ", " : "") + "}";
	}
}
