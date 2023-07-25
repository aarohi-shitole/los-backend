package com.techvg.los.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.SequenceGenerator} entity. This class is used
 * in {@link com.techvg.los.web.rest.SequenceGeneratorResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sequence-generators?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SequenceGeneratorCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter nextValMember;

    private LongFilter nextValLoanApp;

    private LongFilter nextValLoanAccount;

    private LongFilter nextValVoucher;

    private LongFilter freeField1;

    private LongFilter freeField2;

    private LongFilter freeField3;

    private LongFilter freeField4;

    private LongFilter freeField5;

    private LongFilter branchId;

    private LongFilter organisationId;

    private Boolean distinct;

    public SequenceGeneratorCriteria() {}

    public SequenceGeneratorCriteria(SequenceGeneratorCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nextValMember = other.nextValMember == null ? null : other.nextValMember.copy();
        this.nextValLoanApp = other.nextValLoanApp == null ? null : other.nextValLoanApp.copy();
        this.nextValLoanAccount = other.nextValLoanAccount == null ? null : other.nextValLoanAccount.copy();
        this.nextValVoucher = other.nextValVoucher == null ? null : other.nextValVoucher.copy();
        this.freeField1 = other.freeField1 == null ? null : other.freeField1.copy();
        this.freeField2 = other.freeField2 == null ? null : other.freeField2.copy();
        this.freeField3 = other.freeField3 == null ? null : other.freeField3.copy();
        this.freeField4 = other.freeField4 == null ? null : other.freeField4.copy();
        this.freeField5 = other.freeField5 == null ? null : other.freeField5.copy();
        this.branchId = other.branchId == null ? null : other.branchId.copy();
        this.organisationId = other.organisationId == null ? null : other.organisationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SequenceGeneratorCriteria copy() {
        return new SequenceGeneratorCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getNextValMember() {
        return nextValMember;
    }

    public LongFilter nextValMember() {
        if (nextValMember == null) {
            nextValMember = new LongFilter();
        }
        return nextValMember;
    }

    public void setNextValMember(LongFilter nextValMember) {
        this.nextValMember = nextValMember;
    }

    public LongFilter getNextValLoanApp() {
        return nextValLoanApp;
    }

    public LongFilter nextValLoanApp() {
        if (nextValLoanApp == null) {
            nextValLoanApp = new LongFilter();
        }
        return nextValLoanApp;
    }

    public void setNextValLoanApp(LongFilter nextValLoanApp) {
        this.nextValLoanApp = nextValLoanApp;
    }

    public LongFilter getNextValLoanAccount() {
        return nextValLoanAccount;
    }

    public LongFilter nextValLoanAccount() {
        if (nextValLoanAccount == null) {
            nextValLoanAccount = new LongFilter();
        }
        return nextValLoanAccount;
    }

    public void setNextValLoanAccount(LongFilter nextValLoanAccount) {
        this.nextValLoanAccount = nextValLoanAccount;
    }

    public LongFilter getNextValVoucher() {
        return nextValVoucher;
    }

    public LongFilter nextValVoucher() {
        if (nextValVoucher == null) {
            nextValVoucher = new LongFilter();
        }
        return nextValVoucher;
    }

    public void setNextValVoucher(LongFilter nextValVoucher) {
        this.nextValVoucher = nextValVoucher;
    }

    public LongFilter getFreeField1() {
        return freeField1;
    }

    public LongFilter freeField1() {
        if (freeField1 == null) {
            freeField1 = new LongFilter();
        }
        return freeField1;
    }

    public void setFreeField1(LongFilter freeField1) {
        this.freeField1 = freeField1;
    }

    public LongFilter getFreeField2() {
        return freeField2;
    }

    public LongFilter freeField2() {
        if (freeField2 == null) {
            freeField2 = new LongFilter();
        }
        return freeField2;
    }

    public void setFreeField2(LongFilter freeField2) {
        this.freeField2 = freeField2;
    }

    public LongFilter getFreeField3() {
        return freeField3;
    }

    public LongFilter freeField3() {
        if (freeField3 == null) {
            freeField3 = new LongFilter();
        }
        return freeField3;
    }

    public void setFreeField3(LongFilter freeField3) {
        this.freeField3 = freeField3;
    }

    public LongFilter getFreeField4() {
        return freeField4;
    }

    public LongFilter freeField4() {
        if (freeField4 == null) {
            freeField4 = new LongFilter();
        }
        return freeField4;
    }

    public void setFreeField4(LongFilter freeField4) {
        this.freeField4 = freeField4;
    }

    public LongFilter getFreeField5() {
        return freeField5;
    }

    public LongFilter freeField5() {
        if (freeField5 == null) {
            freeField5 = new LongFilter();
        }
        return freeField5;
    }

    public void setFreeField5(LongFilter freeField5) {
        this.freeField5 = freeField5;
    }

    public LongFilter getBranchId() {
        return branchId;
    }

    public LongFilter branchId() {
        if (branchId == null) {
            branchId = new LongFilter();
        }
        return branchId;
    }

    public void setBranchId(LongFilter branchId) {
        this.branchId = branchId;
    }

    public LongFilter getOrganisationId() {
        return organisationId;
    }

    public LongFilter organisationId() {
        if (organisationId == null) {
            organisationId = new LongFilter();
        }
        return organisationId;
    }

    public void setOrganisationId(LongFilter organisationId) {
        this.organisationId = organisationId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SequenceGeneratorCriteria that = (SequenceGeneratorCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nextValMember, that.nextValMember) &&
            Objects.equals(nextValLoanApp, that.nextValLoanApp) &&
            Objects.equals(nextValLoanAccount, that.nextValLoanAccount) &&
            Objects.equals(nextValVoucher, that.nextValVoucher) &&
            Objects.equals(freeField1, that.freeField1) &&
            Objects.equals(freeField2, that.freeField2) &&
            Objects.equals(freeField3, that.freeField3) &&
            Objects.equals(freeField4, that.freeField4) &&
            Objects.equals(freeField5, that.freeField5) &&
            Objects.equals(branchId, that.branchId) &&
            Objects.equals(organisationId, that.organisationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            nextValMember,
            nextValLoanApp,
            nextValLoanAccount,
            nextValVoucher,
            freeField1,
            freeField2,
            freeField3,
            freeField4,
            freeField5,
            branchId,
            organisationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SequenceGeneratorCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nextValMember != null ? "nextValMember=" + nextValMember + ", " : "") +
            (nextValLoanApp != null ? "nextValLoanApp=" + nextValLoanApp + ", " : "") +
            (nextValLoanAccount != null ? "nextValLoanAccount=" + nextValLoanAccount + ", " : "") +
            (nextValVoucher != null ? "nextValVoucher=" + nextValVoucher + ", " : "") +
            (freeField1 != null ? "freeField1=" + freeField1 + ", " : "") +
            (freeField2 != null ? "freeField2=" + freeField2 + ", " : "") +
            (freeField3 != null ? "freeField3=" + freeField3 + ", " : "") +
            (freeField4 != null ? "freeField4=" + freeField4 + ", " : "") +
            (freeField5 != null ? "freeField5=" + freeField5 + ", " : "") +
            (branchId != null ? "branchId=" + branchId + ", " : "") +
            (organisationId != null ? "organisationId=" + organisationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
