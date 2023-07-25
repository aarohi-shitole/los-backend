package com.techvg.los.service.dto;

import com.techvg.los.domain.enumeration.PaymentMode;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.LoanDisbursement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanDisbursementDTO implements Serializable {

    private Long id;

    private Instant dtDisbDate;

    private String accountNo;

    private String ifscCode;

    private Double disbAmount;

    private Integer disbuNumber;

    private String disbursementStatus;

    private PaymentMode paymentMode;

    private String utrNo;

    private Instant lastModified;

    private String lastModifiedBy;

    private Double sanctionAmt;

    private Integer numOfInstalls;

    private Double installmentAmt;

    private Instant firstInstDate;

    private Instant secondInstDate;

    private Double roi;

    private String freeField1;

    private String freeField2;

    private String freeField3;

    private String freeField4;

    private String freeField5;

    private String freeField6;

    private String loanApplicationNo;

    private ProductDTO product;

    private Long securityUserId;

    private LoanAccountDTO loanAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDtDisbDate() {
        return dtDisbDate;
    }

    public void setDtDisbDate(Instant dtDisbDate) {
        this.dtDisbDate = dtDisbDate;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public Double getDisbAmount() {
        return disbAmount;
    }

    public void setDisbAmount(Double disbAmount) {
        this.disbAmount = disbAmount;
    }

    public Integer getDisbuNumber() {
        return disbuNumber;
    }

    public void setDisbuNumber(Integer disbuNumber) {
        this.disbuNumber = disbuNumber;
    }

    public String getDisbursementStatus() {
        return disbursementStatus;
    }

    public void setDisbursementStatus(String disbursementStatus) {
        this.disbursementStatus = disbursementStatus;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getUtrNo() {
        return utrNo;
    }

    public void setUtrNo(String utrNo) {
        this.utrNo = utrNo;
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

    public Double getSanctionAmt() {
        return sanctionAmt;
    }

    public void setSanctionAmt(Double sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public Integer getNumOfInstalls() {
        return numOfInstalls;
    }

    public void setNumOfInstalls(Integer numOfInstalls) {
        this.numOfInstalls = numOfInstalls;
    }

    public Double getInstallmentAmt() {
        return installmentAmt;
    }

    public void setInstallmentAmt(Double installmentAmt) {
        this.installmentAmt = installmentAmt;
    }

    public Instant getFirstInstDate() {
        return firstInstDate;
    }

    public void setFirstInstDate(Instant firstInstDate) {
        this.firstInstDate = firstInstDate;
    }

    public Instant getSecondInstDate() {
        return secondInstDate;
    }

    public void setSecondInstDate(Instant secondInstDate) {
        this.secondInstDate = secondInstDate;
    }

    public Double getRoi() {
        return roi;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }

    public String getFreeField1() {
        return freeField1;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
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

    public String getLoanApplicationNo() {
        return loanApplicationNo;
    }

    public void setLoanApplicationNo(String loanApplicationNo) {
        this.loanApplicationNo = loanApplicationNo;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public Long getSecurityUserId() {
        return securityUserId;
    }

    public void setSecurityUserId(Long securityUserId) {
        this.securityUserId = securityUserId;
    }

    public LoanAccountDTO getLoanAccount() {
        return loanAccount;
    }

    public void setLoanAccount(LoanAccountDTO loanAccount) {
        this.loanAccount = loanAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanDisbursementDTO)) {
            return false;
        }

        LoanDisbursementDTO loanDisbursementDTO = (LoanDisbursementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, loanDisbursementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanDisbursementDTO{" +
            "id=" + getId() +
            ", dtDisbDate='" + getDtDisbDate() + "'" +
            ", accountNo='" + getAccountNo() + "'" +
            ", ifscCode='" + getIfscCode() + "'" +
            ", disbAmount=" + getDisbAmount() +
            ", disbuNumber=" + getDisbuNumber() +
            ", disbursementStatus='" + getDisbursementStatus() + "'" +
            ", paymentMode='" + getPaymentMode() + "'" +
            ", utrNo='" + getUtrNo() + "'" +
            ", lastModified='" + getLastModified() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            ", sanctionAmt="+ getSanctionAmt()+"'"+
            ", numOfInstalls="+ getNumOfInstalls()+"'"+
            ", installmentAmt="+ getInstallmentAmt()+"'"+
            ", firstInstDate="+ getFirstInstDate()+"'"+
            ", secondInstDate="+ getSecondInstDate()+"'"+
            ", roi="+ getRoi()+"'"+
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            ", freeField4='" + getFreeField4() + "'" +
            ", freeField5='" + getFreeField5() + "'" +
            ", freeField6='" + getFreeField6() + "'" +
            ", loanApplicationNo='" + getLoanApplicationNo() + "'" +
            ", product=" + getProduct() +
            ", securityUserId=" + getSecurityUserId() +
            ", loanAccount=" + getLoanAccount() +
            "}";
    }
}
