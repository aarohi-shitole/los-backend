package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "mortgaged_assets")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MortgagedAssets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mortgage_Create_date")
    private Instant mortgCreateDate;

    @Column(name = "mortg_percentage")
    private Double mortgPercentage;

    @ManyToOne
    @JsonIgnoreProperties(value = { "member", "securityUser", "product" }, allowSetters = true)
    private LoanApplications loanApplications;

    @ManyToOne
    @JsonIgnoreProperties(value = { "member", "guarantor", "loanApplications" }, allowSetters = true)
    private MemberAssets memberAssets;

    @ManyToOne
    @JsonIgnoreProperties(value = { "enquiryDetails", "branch", "member", "securityUser" }, allowSetters = true)
    private Member member;

    public Long getId() {
        return this.id;
    }

    public MortgagedAssets id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMortgCreateDate() {
        return this.mortgCreateDate;
    }

    public MortgagedAssets mortgCreateDate(Instant mortgCreateDate) {
        this.setMortgCreateDate(mortgCreateDate);
        return this;
    }

    public void setMortgCreateDate(Instant mortgCreateDate) {
        this.mortgCreateDate = mortgCreateDate;
    }

    public Double getMortgPercentage() {
        return this.mortgPercentage;
    }

    public MortgagedAssets mortgPercentage(Double mortgPercentage) {
        this.setMortgPercentage(mortgPercentage);
        return this;
    }

    public void setMortgPercentage(Double mortgPercentage) {
        this.mortgPercentage = mortgPercentage;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public MortgagedAssets member(Member member) {
        this.setMember(member);
        return this;
    }

    public LoanApplications getLoanApplications() {
        return this.loanApplications;
    }

    public void setLoanApplications(LoanApplications loanApplications) {
        this.loanApplications = loanApplications;
    }

    public MortgagedAssets loanApplications(LoanApplications loanApplications) {
        this.setLoanApplications(loanApplications);
        return this;
    }

    public MemberAssets getMemberAssets() {
        return this.memberAssets;
    }

    public void setMemberAssets(MemberAssets memberAssets) {
        this.memberAssets = memberAssets;
    }

    public MortgagedAssets memberAssets(MemberAssets memberAssets) {
        this.setMemberAssets(memberAssets);
        return this;
    }

    @Override
    public String toString() {
        return (
            "MortgagedAssets{" +
            "id=" +
            getId() +
            ", mortgagePercentage=" +
            getMortgPercentage() +
            ", mortgageCreationDate='" +
            getMortgCreateDate() +
            "'" +
            "}"
        );
    }
}
