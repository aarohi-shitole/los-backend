package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.los.domain.enumeration.AddressType;
import com.techvg.los.domain.enumeration.ApplicantType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "loan_application_members")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanApplicationMembers implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "applicant_type")
    private ApplicantType applicantType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "branch", "member", "securityUser" }, allowSetters = true)
    private Member member;

    @ManyToOne
    @JsonIgnoreProperties(value = { "member", "securityUser", "product" }, allowSetters = true)
    private LoanApplications loanApplications;

    public ApplicantType getApplicantType() {
        return this.applicantType;
    }

    public LoanApplicationMembers applicantType(ApplicantType applicantType) {
        this.setApplicantType(applicantType);
        return this;
    }

    public void setApplicantType(ApplicantType applicantType) {
        this.applicantType = applicantType;
    }

    public Long getId() {
        return this.id;
    }

    public LoanApplicationMembers id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LoanApplicationMembers member(Member member) {
        this.setMember(member);
        return this;
    }

    public LoanApplications getLoanApplications() {
        return this.loanApplications;
    }

    public void setLoanApplications(LoanApplications loanApplications) {
        this.loanApplications = loanApplications;
    }

    public LoanApplicationMembers loanApplications(LoanApplications loanApplications) {
        this.setLoanApplications(loanApplications);
        return this;
    }

    @Override
    public String toString() {
        return "LoanApplicationMembers{" + "id=" + getId() + ", applicantType='" + getApplicantType() + "'" + "}";
    }
}
