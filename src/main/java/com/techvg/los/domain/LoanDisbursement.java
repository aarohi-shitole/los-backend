package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.techvg.los.domain.enumeration.PaymentMode;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * A LoanDisbursement.
 */
@Entity
@Table(name = "loan_disbursement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanDisbursement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dt_disb_date")
    private Instant dtDisbDate;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "disb_amount")
    private Double disbAmount;

    @Column(name = "disbu_number")
    private Integer disbuNumber;

    @Column(name = "disbursement_status")
    private String disbursementStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode")
    private PaymentMode paymentMode;

    @Column(name = "utr_no")
    private String utrNo;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "sanction_amt")
    private double sanctionAmt;

    @Column(name = "num_of_installs")
    private int numOfInstalls;

    @Column(name = "installment_amt")
    private double installmentAmt;

    @Column(name = "first_inst_date")
    private Instant firstInstDate;

    @Column(name = "second_inst_date")
    private Instant secondInstDate;

    @Column(name = "roi")
    private Double roi;

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

    @Column(name = "loan_application_no")
    private String loanApplicationNo;

    @ManyToOne
    @JsonIgnoreProperties(value = { "loanCatagory", "organisation", "ledgerAccounts" }, allowSetters = true)
    private Product product;

    @ManyToOne
    @JsonIgnoreProperties(value = { "branch", "securityPermissions", "securityRoles" }, allowSetters = true)
    private SecurityUser securityUser;

    @ManyToOne
    @JsonIgnoreProperties(value = { "loanApplications", "member", "product" }, allowSetters = true)
    private LoanAccount loanAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LoanDisbursement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDtDisbDate() {
        return this.dtDisbDate;
    }

    public LoanDisbursement dtDisbDate(Instant dtDisbDate) {
        this.setDtDisbDate(dtDisbDate);
        return this;
    }

    public void setDtDisbDate(Instant dtDisbDate) {
        this.dtDisbDate = dtDisbDate;
    }

    public String getAccountNo() {
        return this.accountNo;
    }

    public LoanDisbursement accountNo(String accountNo) {
        this.setAccountNo(accountNo);
        return this;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getIfscCode() {
        return this.ifscCode;
    }

    public LoanDisbursement ifscCode(String ifscCode) {
        this.setIfscCode(ifscCode);
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public Double getDisbAmount() {
        return this.disbAmount;
    }

    public LoanDisbursement disbAmount(Double disbAmount) {
        this.setDisbAmount(disbAmount);
        return this;
    }

    public void setDisbAmount(Double disbAmount) {
        this.disbAmount = disbAmount;
    }

    public Integer getDisbuNumber() {
        return this.disbuNumber;
    }

    public LoanDisbursement disbuNumber(Integer disbuNumber) {
        this.setDisbuNumber(disbuNumber);
        return this;
    }

    public void setDisbuNumber(Integer disbuNumber) {
        this.disbuNumber = disbuNumber;
    }

    public String getDisbursementStatus() {
        return this.disbursementStatus;
    }

    public LoanDisbursement disbursementStatus(String disbursementStatus) {
        this.setDisbursementStatus(disbursementStatus);
        return this;
    }

    public void setDisbursementStatus(String disbursementStatus) {
        this.disbursementStatus = disbursementStatus;
    }

    public PaymentMode getPaymentMode() {
        return this.paymentMode;
    }

    public LoanDisbursement paymentMode(PaymentMode paymentMode) {
        this.setPaymentMode(paymentMode);
        return this;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getUtrNo() {
        return this.utrNo;
    }

    public LoanDisbursement utrNo(String utrNo) {
        this.setUtrNo(utrNo);
        return this;
    }

    public void setUtrNo(String utrNo) {
        this.utrNo = utrNo;
    }

    public Instant getLastModified() {
        return this.lastModified;
    }

    public LoanDisbursement lastModified(Instant lastModified) {
        this.setLastModified(lastModified);
        return this;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return this.lastModifiedBy;
    }

    public LoanDisbursement lastModifiedBy(String lastModifiedBy) {
        this.setLastModifiedBy(lastModifiedBy);
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public double getSanctionAmt() {
        return sanctionAmt;
    }

    public LoanDisbursement sanctionAmt(Double sanctionAmt) {
        this.setSanctionAmt(sanctionAmt);
        return this;
    }

    public void setSanctionAmt(double sanctionAmount) {
        this.sanctionAmt = sanctionAmount;
    }

    public int getNumOfInstalls() {
        return numOfInstalls;
    }

    public LoanDisbursement numOfInstalls(Integer numOfInstalls) {
        this.setNumOfInstalls(numOfInstalls);
        return this;
    }

    public void setNumOfInstalls(int numOfInstalls) {
        this.numOfInstalls = numOfInstalls;
    }

    public double getInstallmentAmt() {
        return installmentAmt;
    }

    public void setInstallmentAmt(double installmentAmt) {
        this.installmentAmt = installmentAmt;
    }

    public LoanDisbursement installmentAmt(Double installmentAmt) {
        this.setInstallmentAmt(installmentAmt);
        return this;
    }

    public Instant getFirstInstDate() {
        return firstInstDate;
    }

    public LoanDisbursement firstInstDate(Instant firstInstDate) {
        this.setFirstInstDate(firstInstDate);
        return this;
    }

    public void setFirstInstDate(Instant firstInstDate) {
        this.firstInstDate = firstInstDate;
    }

    public Instant getSecondInstDate() {
        return secondInstDate;
    }

    public LoanDisbursement secondInstDate(Instant secondInstDate) {
        this.setSecondInstDate(secondInstDate);
        return this;
    }

    public void setSecondInstDate(Instant secondInstDate) {
        this.secondInstDate = secondInstDate;
    }

    public Double getRoi() {
        return roi;
    }

    public LoanDisbursement roi(Double roi) {
        this.setRoi(roi);
        return this;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public LoanDisbursement freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public LoanDisbursement freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public LoanDisbursement freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public String getFreeField4() {
        return this.freeField4;
    }

    public LoanDisbursement freeField4(String freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(String freeField4) {
        this.freeField4 = freeField4;
    }

    public String getFreeField5() {
        return this.freeField5;
    }

    public LoanDisbursement freeField5(String freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(String freeField5) {
        this.freeField5 = freeField5;
    }

    public String getLoanApplicationNo() {
        return this.loanApplicationNo;
    }

    public LoanDisbursement loanApplicationNo(String loanApplicationNo) {
        this.setLoanApplicationNo(loanApplicationNo);
        return this;
    }

    public void setLoanApplicationNo(String loanApplicationNo) {
        this.loanApplicationNo = loanApplicationNo;
    }

    public String getFreeField6() {
        return this.freeField6;
    }

    public LoanDisbursement freeField6(String freeField6) {
        this.setFreeField6(freeField6);
        return this;
    }

    public void setFreeField6(String freeField6) {
        this.freeField6 = freeField6;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LoanDisbursement product(Product product) {
        this.setProduct(product);
        return this;
    }

    public SecurityUser getSecurityUser() {
        return this.securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }

    public LoanDisbursement securityUser(SecurityUser securityUser) {
        this.setSecurityUser(securityUser);
        return this;
    }

    public LoanAccount getLoanAccount() {
        return this.loanAccount;
    }

    public void setLoanAccount(LoanAccount loanAccount) {
        this.loanAccount = loanAccount;
    }

    public LoanDisbursement loanAccount(LoanAccount loanAccount) {
        this.setLoanAccount(loanAccount);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanDisbursement)) {
            return false;
        }
        return id != null && id.equals(((LoanDisbursement) o).id);
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
	@Override
	public String toString() {
		return "LoanDisbursement{" + "id=" + getId() + ", dtDisbDate='" + getDtDisbDate() + "'" + ", accountNo='"
				+ getAccountNo() + "'" + ", ifscCode='" + getIfscCode() + "'" + ", disbAmount=" + getDisbAmount()
				+ ", disbuNumber=" + getDisbuNumber() + ", disbursementStatus='" + getDisbursementStatus() + "'"
				+ ", paymentMode='" + getPaymentMode() + "'" + ", utrNo='" + getUtrNo() + "'" + ", lastModified='"
				+ getLastModified() + "'" + ", lastModifiedBy='" + getLastModifiedBy() + "'" + ", sanctionAmt='"
				+ getSanctionAmt() + "'" + ", numOfInstalls=" + getNumOfInstalls() + "'" + ", installmentAmt="
				+ getInstallmentAmt() + "'" + ", firstInstDate=" + getFirstInstDate() + "'" + ", secondInstDate="
				+ getSecondInstDate() + "'" + ", roi=" + getRoi() + "'" + ", freeField1='" + getFreeField1() + "'"
				+ ", freeField2='" + getFreeField2() + "'" + ", freeField3='" + getFreeField3() + "'" + ", freeField4='"
				+ getFreeField4() + "'" + ", freeField5='" + getFreeField5() + "'" + ", freeField6='" + getFreeField6()
				+", loanApplicationNo='" + getLoanApplicationNo()				
				+ "'" + "}";
	}
}
