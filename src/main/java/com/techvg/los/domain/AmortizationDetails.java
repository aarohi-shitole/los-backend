package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AmortizationDetails.
 */
@Entity
@Table(name = "amortization_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AmortizationDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "installment_date")
    private Instant installmentDate;

    @Column(name = "total_outstanding_principle_amt")
    private Double totalOutstandingPrincipleAmt;

    @Column(name = "total_outstandng_interest_amt")
    private Double totalOutstandngInterestAmt;

    @Column(name = "paid_principle_amt")
    private Double paidPrincipleAmt;

    @Column(name = "paid_interest_amt")
    private Double paidInterestAmt;

    @Column(name = "bal_principle_amt")
    private Double balPrincipleAmt;

    @Column(name = "bal_interest_amt")
    private Double balInterestAmt;

    @Column(name = "loan_emi_amt")
    private Double loanEmiAmt;

    @Column(name = "principle_emi")
    private Double principleEMI;

    @Column(name = "roi")
    private Double roi;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "year")
    private String year;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "free_field_1")
    private String freeField1;

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
    @JsonIgnoreProperties(value = { "loanApplications", "member", "product" }, allowSetters = true)
    private LoanAccount loanAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AmortizationDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getInstallmentDate() {
        return this.installmentDate;
    }

    public AmortizationDetails installmentDate(Instant installmentDate) {
        this.setInstallmentDate(installmentDate);
        return this;
    }

    public void setInstallmentDate(Instant installmentDate) {
        this.installmentDate = installmentDate;
    }

    public Double getTotalOutstandingPrincipleAmt() {
        return this.totalOutstandingPrincipleAmt;
    }

    public AmortizationDetails totalOutstandingPrincipleAmt(Double totalOutstandingPrincipleAmt) {
        this.setTotalOutstandingPrincipleAmt(totalOutstandingPrincipleAmt);
        return this;
    }

    public void setTotalOutstandingPrincipleAmt(Double totalOutstandingPrincipleAmt) {
        this.totalOutstandingPrincipleAmt = totalOutstandingPrincipleAmt;
    }

    public Double getTotalOutstandngInterestAmt() {
        return this.totalOutstandngInterestAmt;
    }

    public AmortizationDetails totalOutstandngInterestAmt(Double totalOutstandngInterestAmt) {
        this.setTotalOutstandngInterestAmt(totalOutstandngInterestAmt);
        return this;
    }

    public void setTotalOutstandngInterestAmt(Double totalOutstandngInterestAmt) {
        this.totalOutstandngInterestAmt = totalOutstandngInterestAmt;
    }

    public Double getPaidPrincipleAmt() {
        return this.paidPrincipleAmt;
    }

    public AmortizationDetails paidPrincipleAmt(Double paidPrincipleAmt) {
        this.setPaidPrincipleAmt(paidPrincipleAmt);
        return this;
    }

    public void setPaidPrincipleAmt(Double paidPrincipleAmt) {
        this.paidPrincipleAmt = paidPrincipleAmt;
    }

    public Double getPaidInterestAmt() {
        return this.paidInterestAmt;
    }

    public AmortizationDetails paidInterestAmt(Double paidInterestAmt) {
        this.setPaidInterestAmt(paidInterestAmt);
        return this;
    }

    public void setPaidInterestAmt(Double paidInterestAmt) {
        this.paidInterestAmt = paidInterestAmt;
    }

    public Double getBalPrincipleAmt() {
        return this.balPrincipleAmt;
    }

    public AmortizationDetails balPrincipleAmt(Double balPrincipleAmt) {
        this.setBalPrincipleAmt(balPrincipleAmt);
        return this;
    }

    public void setBalPrincipleAmt(Double balPrincipleAmt) {
        this.balPrincipleAmt = balPrincipleAmt;
    }

    public Double getBalInterestAmt() {
        return this.balInterestAmt;
    }

    public AmortizationDetails balInterestAmt(Double balInterestAmt) {
        this.setBalInterestAmt(balInterestAmt);
        return this;
    }

    public void setBalInterestAmt(Double balInterestAmt) {
        this.balInterestAmt = balInterestAmt;
    }

    public Double getLoanEmiAmt() {
        return this.loanEmiAmt;
    }

    public AmortizationDetails loanEmiAmt(Double loanEmiAmt) {
        this.setLoanEmiAmt(loanEmiAmt);
        return this;
    }

    public void setLoanEmiAmt(Double loanEmiAmt) {
        this.loanEmiAmt = loanEmiAmt;
    }

    public Double getPrincipleEMI() {
        return this.principleEMI;
    }

    public AmortizationDetails principleEMI(Double principleEMI) {
        this.setPrincipleEMI(principleEMI);
        return this;
    }

    public void setPrincipleEMI(Double principleEMI) {
        this.principleEMI = principleEMI;
    }

    public Double getRoi() {
        return this.roi;
    }

    public AmortizationDetails roi(Double roi) {
        this.setRoi(roi);
        return this;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public AmortizationDetails paymentStatus(String paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getYear() {
        return this.year;
    }

    public AmortizationDetails year(String year) {
        this.setYear(year);
        return this;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public AmortizationDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public AmortizationDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public AmortizationDetails freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public AmortizationDetails freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public AmortizationDetails freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public AmortizationDetails freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public AmortizationDetails freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getFreeField6() {
        return this.freeField6;
    }

    public AmortizationDetails freeField6(String freeField6) {
        this.setFreeField6(freeField6);
        return this;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    public LoanAccount getLoanAccount() {
        return this.loanAccount;
    }

    public void setLoanAccount(LoanAccount loanAccount) {
        this.loanAccount = loanAccount;
    }

    public AmortizationDetails loanAccount(LoanAccount loanAccount) {
        this.setLoanAccount(loanAccount);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AmortizationDetails)) {
            return false;
        }
        return id != null && id.equals(((AmortizationDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AmortizationDetails{" +
            "id=" + getId() +
            ", installmentDate='" + getInstallmentDate() + "'" +
            ", totalOutstandingPrincipleAmt=" + getTotalOutstandingPrincipleAmt() +
            ", totalOutstandngInterestAmt=" + getTotalOutstandngInterestAmt() +
            ", paidPrincipleAmt=" + getPaidPrincipleAmt() +
            ", paidInterestAmt=" + getPaidInterestAmt() +
            ", balPrincipleAmt=" + getBalPrincipleAmt() +
            ", balInterestAmt=" + getBalInterestAmt() +
            ", loanEmiAmt=" + getLoanEmiAmt() +
            ", principleEMI=" + getPrincipleEMI() +
            ", roi=" + getRoi() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", year='" + getYear() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", freeField5='" + getFreeField5() + "'" +
            ", freeField6='" + getFreeField6() + "'" +
            "}";
    }
}
