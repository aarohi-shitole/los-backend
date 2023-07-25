package com.techvg.los.service.dto;

import java.io.Serializable;
import java.time.Instant;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class MortgagedAssetsDTO implements Serializable {

    private Long id;

    private Instant mortgCreateDate;

    private Double mortgPercentage;

    private Long memberId;

    private MemberAssetsDTO memberAssets;

    private Long loanApplicationsId;

    @Override
    public String toString() {
        return (
            "MemberAssetsDTO{" +
            "id=" +
            getId() +
            ", mortgagePercentage=" +
            getMortgPercentage() +
            ", mortgageCreationDate='" +
            getMortgCreateDate() +
            "'" +
            ", memberId=" +
            getMemberId() +
            ", memberAssets=" +
            getMemberAssets() +
            ", loanApplicationsId=" +
            getLoanApplicationsId() +
            "}"
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMortgCreateDate() {
        return mortgCreateDate;
    }

    public void setMortgCreateDate(Instant mortgCreateDate) {
        this.mortgCreateDate = mortgCreateDate;
    }

    public Double getMortgPercentage() {
        return mortgPercentage;
    }

    public void setMortgPercentage(Double mortgPercentage) {
        this.mortgPercentage = mortgPercentage;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public MemberAssetsDTO getMemberAssets() {
        return memberAssets;
    }

    public void setMemberAssets(MemberAssetsDTO memberAssets) {
        this.memberAssets = memberAssets;
    }

    public Long getLoanApplicationsId() {
        return loanApplicationsId;
    }

    public void setLoanApplicationsId(Long loanApplicationsId) {
        this.loanApplicationsId = loanApplicationsId;
    }
}
