package com.techvg.los.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SequenceGenerator.
 */
@Entity
@Table(name = "sequence_generator")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SequenceGenerator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "next_val_member")
    private Long nextValMember;

    @Column(name = "next_val_loan_app")
    private Long nextValLoanApp;

    @Column(name = "next_val_loan_account")
    private Long nextValLoanAccount;

    @Column(name = "next_val_voucher")
    private Long nextValVoucher;

    @Column(name = "free_field_1")
    private Long freeField1;

    @Column(name = "free_field_2")
    private Long freeField2;

    @Column(name = "free_field_3")
    private Long freeField3;

    @Column(name = "free_field_4")
    private Long freeField4;

    @Column(name = "free_field_5")
    private Long freeField5;

    @JsonIgnoreProperties(value = { "address", "organisation", "branch" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Branch branch;

    @JsonIgnoreProperties(value = { "address" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Organisation organisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SequenceGenerator id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNextValMember() {
        return this.nextValMember;
    }

    public SequenceGenerator nextValMember(Long nextValMember) {
        this.setNextValMember(nextValMember);
        return this;
    }

    public void setNextValMember(Long nextValMember) {
        this.nextValMember = nextValMember;
    }

    public Long getNextValLoanApp() {
        return this.nextValLoanApp;
    }

    public SequenceGenerator nextValLoanApp(Long nextValLoanApp) {
        this.setNextValLoanApp(nextValLoanApp);
        return this;
    }

    public void setNextValLoanApp(Long nextValLoanApp) {
        this.nextValLoanApp = nextValLoanApp;
    }

    public Long getNextValLoanAccount() {
        return this.nextValLoanAccount;
    }

    public SequenceGenerator nextValLoanAccount(Long nextValLoanAccount) {
        this.setNextValLoanAccount(nextValLoanAccount);
        return this;
    }

    public void setNextValLoanAccount(Long nextValLoanAccount) {
        this.nextValLoanAccount = nextValLoanAccount;
    }

    public Long getNextValVoucher() {
        return this.nextValVoucher;
    }

    public SequenceGenerator nextValVoucher(Long nextValVoucher) {
        this.setNextValVoucher(nextValVoucher);
        return this;
    }

    public void setNextValVoucher(Long nextValVoucher) {
        this.nextValVoucher = nextValVoucher;
    }

    public Long getFreeField1() {
        return this.freeField1;
    }

    public SequenceGenerator freeField1(Long freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(Long freeField1) {
        this.freeField1 = freeField1;
    }

    public Long getFreeField2() {
        return this.freeField2;
    }

    public SequenceGenerator freeField2(Long freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(Long freeField2) {
        this.freeField2 = freeField2;
    }

    public Long getFreeField3() {
        return this.freeField3;
    }

    public SequenceGenerator freeField3(Long freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(Long freeField3) {
        this.freeField3 = freeField3;
    }

    public Long getFreeField4() {
        return this.freeField4;
    }

    public SequenceGenerator freeField4(Long freeField4) {
        this.setFreeField4(freeField4);
        return this;
    }

    public void setFreeField4(Long freeField4) {
        this.freeField4 = freeField4;
    }

    public Long getFreeField5() {
        return this.freeField5;
    }

    public SequenceGenerator freeField5(Long freeField5) {
        this.setFreeField5(freeField5);
        return this;
    }

    public void setFreeField5(Long freeField5) {
        this.freeField5 = freeField5;
    }

    public Branch getBranch() {
        return this.branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public SequenceGenerator branch(Branch branch) {
        this.setBranch(branch);
        return this;
    }

    public Organisation getOrganisation() {
        return this.organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public SequenceGenerator organisation(Organisation organisation) {
        this.setOrganisation(organisation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SequenceGenerator)) {
            return false;
        }
        return id != null && id.equals(((SequenceGenerator) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SequenceGenerator{" +
            "id=" + getId() +
            ", nextValMember=" + getNextValMember() +
            ", nextValLoanApp=" + getNextValLoanApp() +
            ", nextValLoanAccount=" + getNextValLoanAccount() +
            ", nextValVoucher=" + getNextValVoucher() +
            ", freeField1=" + getFreeField1() +
            ", freeField2=" + getFreeField2() +
            ", freeField3=" + getFreeField3() +
            ", freeField4=" + getFreeField4() +
            ", freeField5=" + getFreeField5() +
            "}";
    }
}
