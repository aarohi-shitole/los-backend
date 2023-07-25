package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.AddressType;
import com.techvg.los.domain.enumeration.ApplicantType;
import com.techvg.los.service.criteria.AddressCriteria.AddressTypeFilter;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanApplicationMembersCriteria implements Serializable, Criteria {

    public static class ApplicantTypeFilter extends Filter<ApplicantType> {

        public ApplicantTypeFilter() {}

        public ApplicantTypeFilter(ApplicantTypeFilter filter) {
            super(filter);
        }

        @Override
        public ApplicantTypeFilter copy() {
            return new ApplicantTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ApplicantTypeFilter applicantType;

    private LongFilter memberId;

    private LongFilter loanApplicationId;

    private Boolean distinct;

    public LoanApplicationMembersCriteria() {}

    public LoanApplicationMembersCriteria(LoanApplicationMembersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.applicantType = other.applicantType == null ? null : other.applicantType.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.loanApplicationId = other.loanApplicationId == null ? null : other.loanApplicationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LoanApplicationMembersCriteria copy() {
        return new LoanApplicationMembersCriteria(this);
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

    public ApplicantTypeFilter getApplicantType() {
        return applicantType;
    }

    public ApplicantTypeFilter applicantType() {
        if (applicantType == null) {
            applicantType = new ApplicantTypeFilter();
        }
        return applicantType;
    }

    public void setApplicantType(ApplicantTypeFilter applicantType) {
        this.applicantType = applicantType;
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

    public LongFilter getLoanApplicationId() {
        return loanApplicationId;
    }

    public LongFilter loanApplicationId() {
        if (loanApplicationId == null) {
            loanApplicationId = new LongFilter();
        }
        return loanApplicationId;
    }

    public void setLoanApplicationId(LongFilter loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
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
        final LoanApplicationMembersCriteria that = (LoanApplicationMembersCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(applicantType, that.applicantType) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(loanApplicationId, that.loanApplicationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, applicantType, memberId, loanApplicationId, distinct);
    }

    @Override
    public String toString() {
        return (
            "LoanApplicationMembersCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (applicantType != null ? "applicantType=" + applicantType + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
            (loanApplicationId != null ? "loanApplicationId=" + loanApplicationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}"
        );
    }
}
