package com.techvg.los.service.dto;

import com.techvg.los.domain.enumeration.ApplicantType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.LoanApplicationMembers} entity.
 */

@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanApplicationMembersDTO implements Serializable {

    private Long id;

    private ApplicantType applicantType;

    private Long memberId;

    private Long loanApplicationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public ApplicantType getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(ApplicantType applicantType) {
        this.applicantType = applicantType;
    }

    public Long getLoanApplicationId() {
        return loanApplicationId;
    }

    public void setLoanApplicationId(Long loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanApplicationMembersDTO)) {
            return false;
        }

        LoanApplicationMembersDTO loanApplicationMembersDTO = (LoanApplicationMembersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, loanApplicationMembersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "loanApplicationMembersDTO{" +
            "id=" +
            getId() +
            ", applictionType='" +
            getApplicantType() +
            "'" +
            ", memberId=" +
            getMemberId() +
            ", loanApplicationId=" +
            getLoanApplicationId() +
            "}"
        );
    }
}
