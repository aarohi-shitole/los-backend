package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.los.domain.enumeration.PaymentMode;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LoanRepaymentDetails.
 */
@Entity
@Table(name = "loan_repayment_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanRepaymentDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "repayment_date")
    private Instant repaymentDate;

    @Column(name = "total_repayment_amt")
    private Double totalRepaymentAmt;

    @Column(name = "installment_number")
    private Integer installmentNumber;

    @Column(name = "principle_paid_amt")
    private Double principlePaidAmt;

    @Column(name = "interest_paid_amt")
    private Double interestPaidAmt;

    @Column(name = "sur_charge_amt")
    private Double surChargeAmt;

    @Column(name = "over_due_amt")
    private Double overDueAmt;

    @Column(name = "balance_interest_amt")
    private Double balanceInterestAmt;

    @Column(name = "balance_principle_amt")
    private Double balancePrincipleAmt;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode")
    private PaymentMode paymentMode;

    @Column(name = "fore_closure_charge_amt")
    private Double foreClosureChargeAmt;

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

    public LoanRepaymentDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRepaymentDate() {
        return this.repaymentDate;
    }

    public LoanRepaymentDetails repaymentDate(Instant repaymentDate) {
        this.setRepaymentDate(repaymentDate);
        return this;
    }

    public void setRepaymentDate(Instant repaymentDate) {
        this.repaymentDate = repaymentDate;
    }

    public Double getTotalRepaymentAmt() {
        return this.totalRepaymentAmt;
    }

    public LoanRepaymentDetails totalRepaymentAmt(Double totalRepaymentAmt) {
        this.setTotalRepaymentAmt(totalRepaymentAmt);
        return this;
    }

    public void setTotalRepaymentAmt(Double totalRepaymentAmt) {
        this.totalRepaymentAmt = totalRepaymentAmt;
    }

    public Integer getInstallmentNumber() {
        return this.installmentNumber;
    }

    public LoanRepaymentDetails installmentNumber(Integer installmentNumber) {
        this.setInstallmentNumber(installmentNumber);
        return this;
    }

    public void setInstallmentNumber(Integer installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public Double getPrinciplePaidAmt() {
        return this.principlePaidAmt;
    }

    public LoanRepaymentDetails principlePaidAmt(Double principlePaidAmt) {
        this.setPrinciplePaidAmt(principlePaidAmt);
        return this;
    }

    public void setPrinciplePaidAmt(Double principlePaidAmt) {
        this.principlePaidAmt = principlePaidAmt;
    }

    public Double getInterestPaidAmt() {
        return this.interestPaidAmt;
    }

    public LoanRepaymentDetails interestPaidAmt(Double interestPaidAmt) {
        this.setInterestPaidAmt(interestPaidAmt);
        return this;
    }

    public void setInterestPaidAmt(Double interestPaidAmt) {
        this.interestPaidAmt = interestPaidAmt;
    }

    public Double getSurChargeAmt() {
        return this.surChargeAmt;
    }

    public LoanRepaymentDetails surChargeAmt(Double surChargeAmt) {
        this.setSurChargeAmt(surChargeAmt);
        return this;
    }

    public void setSurChargeAmt(Double surChargeAmt) {
        this.surChargeAmt = surChargeAmt;
    }

    public Double getOverDueAmt() {
        return this.overDueAmt;
    }

    public LoanRepaymentDetails overDueAmt(Double overDueAmt) {
        this.setOverDueAmt(overDueAmt);
        return this;
    }

    public void setOverDueAmt(Double overDueAmt) {
        this.overDueAmt = overDueAmt;
    }

    public Double getBalanceInterestAmt() {
        return this.balanceInterestAmt;
    }

    public LoanRepaymentDetails balanceInterestAmt(Double balanceInterestAmt) {
        this.setBalanceInterestAmt(balanceInterestAmt);
        return this;
    }

    public void setBalanceInterestAmt(Double balanceInterestAmt) {
        this.balanceInterestAmt = balanceInterestAmt;
    }

    public Double getBalancePrincipleAmt() {
        return this.balancePrincipleAmt;
    }

    public LoanRepaymentDetails balancePrincipleAmt(Double balancePrincipleAmt) {
        this.setBalancePrincipleAmt(balancePrincipleAmt);
        return this;
    }

    public void setBalancePrincipleAmt(Double balancePrincipleAmt) {
        this.balancePrincipleAmt = balancePrincipleAmt;
    }

    public PaymentMode getPaymentMode() {
        return this.paymentMode;
    }

    public LoanRepaymentDetails paymentMode(PaymentMode paymentMode) {
        this.setPaymentMode(paymentMode);
        return this;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Double getForeClosureChargeAmt() {
        return this.foreClosureChargeAmt;
    }

    public LoanRepaymentDetails foreClosureChargeAmt(Double foreClosureChargeAmt) {
        this.setForeClosureChargeAmt(foreClosureChargeAmt);
        return this;
    }

    public void setForeClosureChargeAmt(Double foreClosureChargeAmt) {
        this.foreClosureChargeAmt = foreClosureChargeAmt;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public LoanRepaymentDetails lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public LoanRepaymentDetails lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public LoanRepaymentDetails freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public LoanRepaymentDetails freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public LoanRepaymentDetails freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public LoanRepaymentDetails freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public LoanRepaymentDetails freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getFreeField6() {
        return this.freeField6;
    }

    public LoanRepaymentDetails freeField6(String freeField6) {
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

    public LoanRepaymentDetails loanAccount(LoanAccount loanAccount) {
        this.setLoanAccount(loanAccount);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanRepaymentDetails)) {
            return false;
        }
        return id != null && id.equals(((LoanRepaymentDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanRepaymentDetails{" +
            "id=" + getId() +
            ", repaymentDate='" + getRepaymentDate() + "'" +
            ", totalRepaymentAmt=" + getTotalRepaymentAmt() +
            ", installmentNumber=" + getInstallmentNumber() +
            ", principlePaidAmt=" + getPrinciplePaidAmt() +
            ", interestPaidAmt=" + getInterestPaidAmt() +
            ", surChargeAmt=" + getSurChargeAmt() +
            ", overDueAmt=" + getOverDueAmt() +
            ", balanceInterestAmt=" + getBalanceInterestAmt() +
            ", balancePrincipleAmt=" + getBalancePrincipleAmt() +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", foreClosureChargeAmt=" + getForeClosureChargeAmt() +
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
