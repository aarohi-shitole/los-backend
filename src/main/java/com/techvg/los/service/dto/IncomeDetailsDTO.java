package com.techvg.los.service.dto;

import com.techvg.los.domain.enumeration.IncomeType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.IncomeDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IncomeDetailsDTO implements Serializable {

    private Long id;

    private String year;

    private Double grossIncome;

    private Double expenses;

    private Double netIncome;

    private Double income;

    private Double paidTaxes;

    private Double cashSurplus;

    private IncomeType incomeType;

    private Boolean isDeleted;

    private Instant lastModified;

    private String lastModifiedBy;

    private String createdBy;

    private String remark;

    private Instant createdOn;

    private String otherType;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private String freeField5;

    private String freeField6;

    private Long memberId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Double getGrossIncome() {
        return grossIncome;
    }

    public void setGrossIncome(Double grossIncome) {
        this.grossIncome = grossIncome;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }

    public Double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(Double netIncome) {
        this.netIncome = netIncome;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getPaidTaxes() {
        return paidTaxes;
    }

    public void setPaidTaxes(Double paidTaxes) {
        this.paidTaxes = paidTaxes;
    }

    public Double getCashSurplus() {
        return cashSurplus;
    }

    public void setCashSurplus(Double cashSurplus) {
        this.cashSurplus = cashSurplus;
    }

    public IncomeType getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(IncomeType incomeType) {
        this.incomeType = incomeType;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public String getOtherType() {
        return otherType;
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType;
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

    public String getFreeField6() {
        return freeField6;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncomeDetailsDTO)) {
            return false;
        }

        IncomeDetailsDTO incomeDetailsDTO = (IncomeDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, incomeDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
	@Override
	public String toString() {
		return "IncomeDetailsDTO{" + "id=" + getId() + ", year='" + getYear() + "'" + ", grossIncome="
				+ getGrossIncome() + ", expenses=" + getExpenses() + ", netIncome=" + getNetIncome()+ ", income=" + getIncome() + ", paidTaxes="
				+ getPaidTaxes() + ", cashSurplus=" + getCashSurplus() + ", incomeType='" + getIncomeType() + "'"
				+ ", isDeleted='" + getIsDeleted() + "'" + ", lastModified='" + getLastModified() + "'"
				+ ", lastModifiedBy='" + getLastModifiedBy() + "'" + ", createdBy='" + getCreatedBy() + "'"+ ", remark='" + getRemark() + "'"
				+ ", createdOn='" + getCreatedOn() + "'" + ", otherType='" + getOtherType() + "'" + ", freeField2='"
				+ getFreeField2() + "'" + ", freeField3='" + getFreeField3() + "'" + ", freeField4='" + getFreeField4()
				+ "'" + ", freeField5='" + getFreeField5() + "'" + ", freeField6='" + getFreeField6() + "'"
				+ ", memberId=" + getMemberId() + "}";
	}
}
