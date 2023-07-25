package com.techvg.los.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.LongFilter;

@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MortgagedAssetsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter mortgCreateDate;

    private DoubleFilter mortgPercentage;

    private LongFilter memberId;

    private LongFilter loanApplicationsId;

    private LongFilter memberAssetsId;

    private Boolean distinct;

    public MortgagedAssetsCriteria() {}

    public MortgagedAssetsCriteria(MortgagedAssetsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.mortgPercentage = other.mortgPercentage == null ? null : other.mortgPercentage.copy();
        this.mortgCreateDate = other.mortgCreateDate == null ? null : other.mortgCreateDate.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.loanApplicationsId = other.loanApplicationsId == null ? null : other.loanApplicationsId.copy();
        this.memberAssetsId = other.memberAssetsId == null ? null : other.memberAssetsId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MortgagedAssetsCriteria copy() {
        return new MortgagedAssetsCriteria(this);
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

    public InstantFilter getMortgCreateDate() {
        return mortgCreateDate;
    }

    public InstantFilter mortgCreateDate() {
        if (mortgCreateDate == null) {
            mortgCreateDate = new InstantFilter();
        }
        return mortgCreateDate;
    }

    public void setMortgCreateDate(InstantFilter mortgCreateDate) {
        this.mortgCreateDate = mortgCreateDate;
    }

    public DoubleFilter getMortgPercentage() {
        return mortgPercentage;
    }

    public DoubleFilter mortgPercentage() {
        if (mortgPercentage == null) {
            mortgPercentage = new DoubleFilter();
        }
        return mortgPercentage;
    }

    public void setMortgPercentage(DoubleFilter mortgPercentage) {
        this.mortgPercentage = mortgPercentage;
    }

    public LongFilter getMemberId() {
        return memberId;
    }

    public LongFilter memberId() {
        if (memberId == null) {
            memberId = new LongFilter();
        }
        return memberId;
    }

    public void setMemberId(LongFilter memberId) {
        this.memberId = memberId;
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

    public LongFilter getMemberAssetsId() {
        return memberAssetsId;
    }

    public LongFilter memberAssetsId() {
        if (memberAssetsId == null) {
            memberAssetsId = new LongFilter();
        }
        return memberAssetsId;
    }

    public void setMemberAssetsId(LongFilter memberAssetsId) {
        this.memberAssetsId = memberAssetsId;
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
        final MortgagedAssetsCriteria that = (MortgagedAssetsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(mortgCreateDate, that.mortgCreateDate) &&
            Objects.equals(mortgPercentage, that.mortgPercentage) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(memberAssetsId, that.memberAssetsId) &&
            Objects.equals(loanApplicationsId, that.loanApplicationsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mortgPercentage, mortgCreateDate, memberId, memberAssetsId, loanApplicationsId, distinct);
    }

    // prettier-ignore
	    @Override
	    public String toString() {
	        return "MortgagedAssetsCriteria{" +
	            (id != null ? "id=" + id + ", " : "") +
	            (mortgPercentage != null ? "mortgPercentage=" + mortgPercentage + ", " : "") +
	            (mortgCreateDate != null ? "mortgCreateDate=" + mortgCreateDate + ", " : "") +
	            (memberId != null ? "memberId=" + memberId + ", " : "") +
	            (memberAssetsId != null ? "memberAssetsId=" + memberAssetsId + ", " : "") +
	            (loanApplicationsId != null ? "loanApplicationsId=" + loanApplicationsId + ", " : "") +
	            (distinct != null ? "distinct=" + distinct + ", " : "") +
	            "}";
	    }
}
