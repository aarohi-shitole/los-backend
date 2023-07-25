package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.los.domain.enumeration.IncomeType;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A IncomeDetails.
 */
@Entity
@Table(name = "income_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IncomeDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "year")
    private String year;

    @Column(name = "gross_income")
    private Double grossIncome;

    @Column(name = "expenses")
    private Double expenses;

    @Column(name = "net_income")
    private Double netIncome;

    @Column(name = "income")
    private Double income;

    @Column(name = "paid_taxes")
    private Double paidTaxes;

    @Column(name = "cash_surplus")
    private Double cashSurplus;

    @Enumerated(EnumType.STRING)
    @Column(name = "income_type")
    private IncomeType incomeType;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "remark")
    private String remark;

    @Column(name = "created_on")
    private Instant createdOn;

    @Column(name = "other_type")
    private String otherType;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @Column(name = "free_field_4")
    private String freeField4;

    @Column(name = "free_field_5")
    private String freeField5;

    @Column(name = "free_field_6")
    private String freeField6;

    @ManyToOne
    @JsonIgnoreProperties(value = { "enquiryDetails", "branch", "member", "securityUser" }, allowSetters = true)
    private Member member;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IncomeDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return this.year;
    }

    public IncomeDetails year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Double getGrossIncome() {
        return this.grossIncome;
    }

    public IncomeDetails grossIncome(Double grossIncome) {
        this.setGrossIncome(grossIncome);
        return this;
    }

    public void setGrossIncome(Double grossIncome) {
        this.grossIncome = grossIncome;
    }

    public Double getExpenses() {
        return this.expenses;
    }

    public IncomeDetails expenses(Double expenses) {
        this.setExpenses(expenses);
        return this;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }

    public Double getNetIncome() {
        return this.netIncome;
    }

    public IncomeDetails netIncome(Double netIncome) {
        this.setNetIncome(netIncome);
        return this;
    }

    public void setNetIncome(Double netIncome) {
        this.netIncome = netIncome;
    }

    public Double getIncome() {
        return this.income;
    }

    public IncomeDetails income(Double income) {
        this.setIncome(income);
        return this;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getPaidTaxes() {
        return this.paidTaxes;
    }

    public IncomeDetails paidTaxes(Double paidTaxes) {
        this.setPaidTaxes(paidTaxes);
        return this;
    }

    public void setPaidTaxes(Double paidTaxes) {
        this.paidTaxes = paidTaxes;
    }

    public Double getCashSurplus() {
        return this.cashSurplus;
    }

    public IncomeDetails cashSurplus(Double cashSurplus) {
        this.setCashSurplus(cashSurplus);
        return this;
    }

    public void setCashSurplus(Double cashSurplus) {
        this.cashSurplus = cashSurplus;
    }

    public IncomeType getIncomeType() {
        return this.incomeType;
    }

    public IncomeDetails incomeType(IncomeType incomeType) {
        this.setIncomeType(incomeType);
        return this;
    }

    public void setIncomeType(IncomeType incomeType) {
        this.incomeType = incomeType;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public IncomeDetails isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public IncomeDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public IncomeDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public IncomeDetails createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getRemark() {
        return this.remark;
    }

    public IncomeDetails remark(String remark) {
        this.setRemark(remark);
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Instant getCreatedOn() {
        return this.createdOn;
    }

    public IncomeDetails createdOn(Instant createdOn) {
        this.setCreatedOn(createdOn);
        return this;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getOtherType() {
        return this.otherType;
    }

    public IncomeDetails otherType(String otherType) {
        this.setOtherType(otherType);
        return this;
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public IncomeDetails freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public IncomeDetails freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public IncomeDetails freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public IncomeDetails freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getFreeField6() {
        return this.freeField6;
    }

    public IncomeDetails freeField6(String freeField6) {
        this.setFreeField6(freeField6);
        return this;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public IncomeDetails member(Member member) {
        this.setMember(member);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncomeDetails)) {
            return false;
        }
        return id != null && id.equals(((IncomeDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IncomeDetails{" +
            "id=" + getId() +
            ", year='" + getYear() + "'" +
            ", grossIncome=" + getGrossIncome() +
            ", expenses=" + getExpenses() +
            ", income=" + getIncome() +
            ", netIncome=" + getNetIncome() +
            ", paidTaxes=" + getPaidTaxes() +
            ", cashSurplus=" + getCashSurplus() +
            ", incomeType='" + getIncomeType() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", remark='" + getRemark() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", otherType='" + getOtherType() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", freeField5='" + getFreeField5() + "'" +
            ", freeField6='" + getFreeField6() + "'" +
            "}";
    }
}
