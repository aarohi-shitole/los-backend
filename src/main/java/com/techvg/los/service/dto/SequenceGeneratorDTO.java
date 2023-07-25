package com.techvg.los.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.techvg.los.domain.SequenceGenerator} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SequenceGeneratorDTO implements Serializable {

    private Long id;

    private Long nextValMember;

    private Long nextValLoanApp;

    private Long nextValLoanAccount;

    private Long nextValVoucher;

    private Long freeField1;

    private Long freeField2;

    private Long freeField3;

    private Long freeField4;

    private Long freeField5;

    private BranchDTO branch;

    private OrganisationDTO organisation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNextValMember() {
        return nextValMember;
    }

    public void setNextValMember(Long nextValMember) {
        this.nextValMember = nextValMember;
    }

    public Long getNextValLoanApp() {
        return nextValLoanApp;
    }

    public void setNextValLoanApp(Long nextValLoanApp) {
        this.nextValLoanApp = nextValLoanApp;
    }

    public Long getNextValLoanAccount() {
        return nextValLoanAccount;
    }

    public void setNextValLoanAccount(Long nextValLoanAccount) {
        this.nextValLoanAccount = nextValLoanAccount;
    }

    public Long getNextValVoucher() {
        return nextValVoucher;
    }

    public void setNextValVoucher(Long nextValVoucher) {
        this.nextValVoucher = nextValVoucher;
    }

    public Long getFreeField1() {
        return freeField1;
    }

    public void setFreeField1(Long freeField1) {
        this.freeField1 = freeField1;
    }

    public Long getFreeField2() {
        return freeField2;
    }

    public void setFreeField2(Long freeField2) {
        this.freeField2 = freeField2;
    }

    public Long getFreeField3() {
        return freeField3;
    }

    public void setFreeField3(Long freeField3) {
        this.freeField3 = freeField3;
    }

    public Long getFreeField4() {
        return freeField4;
    }

    public void setFreeField4(Long freeField4) {
        this.freeField4 = freeField4;
    }

    public Long getFreeField5() {
        return freeField5;
    }

    public void setFreeField5(Long freeField5) {
        this.freeField5 = freeField5;
    }

    public BranchDTO getBranch() {
        return branch;
    }

    public void setBranch(BranchDTO branch) {
        this.branch = branch;
    }

    public OrganisationDTO getOrganisation() {
        return organisation;
    }

    public void setOrganisation(OrganisationDTO organisation) {
        this.organisation = organisation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SequenceGeneratorDTO)) {
            return false;
        }

        SequenceGeneratorDTO sequenceGeneratorDTO = (SequenceGeneratorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sequenceGeneratorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SequenceGeneratorDTO{" +
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
            ", branch=" + getBranch() +
            ", organisation=" + getOrganisation() +
            "}";
    }
}
