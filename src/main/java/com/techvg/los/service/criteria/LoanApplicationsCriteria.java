package com.techvg.los.service.criteria;

import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.domain.enumeration.StepperNumber;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.techvg.los.domain.LoanApplications} entity. This class is used
 * in {@link com.techvg.los.web.rest.LoanApplicationsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /loan-applications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LoanApplicationsCriteria implements Serializable, Criteria {

    /**
     * Class for filtering LoanStatus
     */
    public static class LoanStatusFilter extends Filter<LoanStatus> {

        public LoanStatusFilter() {}

        public LoanStatusFilter(LoanStatusFilter filter) {
            super(filter);
        }

        @Override
        public LoanStatusFilter copy() {
            return new LoanStatusFilter(this);
        }
    }

    /**
     * Class for filtering StepperNumber
     */
    public static class StepperNumberFilter extends Filter<StepperNumber> {

        public StepperNumberFilter() {}

        public StepperNumberFilter(StepperNumberFilter filter) {
            super(filter);
        }

        @Override
        public StepperNumberFilter copy() {
            return new StepperNumberFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter applicationNo;

    private DoubleFilter demandAmount;

    private StringFilter loanPurpose;

    private LoanStatusFilter status;

    private IntegerFilter demandedLandAreaInHector;

    private StringFilter seasonOfCropLoan;

    private StepperNumberFilter loanSteps;

    private BooleanFilter isInsured;

    private DoubleFilter loanBenefitingArea;

    private DoubleFilter costOfInvestment;

    private LongFilter mortgageDeedNo;

    private LongFilter resolutionNo;

    private InstantFilter mortgageDate;

    private InstantFilter createdOn;

    private DoubleFilter extentMorgageValue;

    private DoubleFilter downPaymentAmt;

    private DoubleFilter ltvRatio;

    private DoubleFilter processingFee;

    private DoubleFilter penalInterest;

    private StringFilter moratorium;

    private DoubleFilter roi;

    private DoubleFilter commityAmt;

    private DoubleFilter commityRoi;

    private DoubleFilter sanctionAmt;

    private DoubleFilter sanctionRoi;

    private StringFilter year;

    private StringFilter remark;

    private LongFilter assignedTo;

    private LongFilter assignedFrom;

    private StringFilter securityDocUrl;

    private InstantFilter lastModified;

    private StringFilter lastModifiedBy;

    private StringFilter margin;

    private StringFilter securityOffered;

    private InstantFilter directorMeetDate;

    private InstantFilter commityMeetDate;

    private DoubleFilter directorAmount;

    private DoubleFilter directorRoi;

    private InstantFilter sanctionDate;

    private LongFilter memberId;

    private LongFilter securityUserId;

    private LongFilter productId;

    private LongFilter branchId;

    private BooleanFilter hasSubmitLegal;

    private BooleanFilter hasReceiveLegal;

    private BooleanFilter hasRequiredDirRec;

    private Boolean distinct;

    public LoanApplicationsCriteria() {}

    public LoanApplicationsCriteria(LoanApplicationsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.applicationNo = other.applicationNo == null ? null : other.applicationNo.copy();
        this.demandAmount = other.demandAmount == null ? null : other.demandAmount.copy();
        this.loanPurpose = other.loanPurpose == null ? null : other.loanPurpose.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.demandedLandAreaInHector = other.demandedLandAreaInHector == null ? null : other.demandedLandAreaInHector.copy();
        this.seasonOfCropLoan = other.seasonOfCropLoan == null ? null : other.seasonOfCropLoan.copy();
        this.loanSteps = other.loanSteps == null ? null : other.loanSteps.copy();
        this.isInsured = other.isInsured == null ? null : other.isInsured.copy();
        this.loanBenefitingArea = other.loanBenefitingArea == null ? null : other.loanBenefitingArea.copy();
        this.costOfInvestment = other.costOfInvestment == null ? null : other.costOfInvestment.copy();
        this.mortgageDeedNo = other.mortgageDeedNo == null ? null : other.mortgageDeedNo.copy();
        this.resolutionNo = other.resolutionNo == null ? null : other.resolutionNo.copy();
        this.mortgageDate = other.mortgageDate == null ? null : other.mortgageDate.copy();
        this.createdOn = other.createdOn == null ? null : other.createdOn.copy();
        this.extentMorgageValue = other.extentMorgageValue == null ? null : other.extentMorgageValue.copy();
        this.downPaymentAmt = other.downPaymentAmt == null ? null : other.downPaymentAmt.copy();
        this.ltvRatio = other.ltvRatio == null ? null : other.ltvRatio.copy();
        this.processingFee = other.processingFee == null ? null : other.processingFee.copy();
        this.penalInterest = other.penalInterest == null ? null : other.penalInterest.copy();
        this.moratorium = other.moratorium == null ? null : other.moratorium.copy();
        this.roi = other.roi == null ? null : other.roi.copy();
        this.commityAmt = other.commityAmt == null ? null : other.commityAmt.copy();
        this.commityRoi = other.commityRoi == null ? null : other.commityRoi.copy();
        this.sanctionAmt = other.sanctionAmt == null ? null : other.sanctionAmt.copy();
        this.sanctionRoi = other.sanctionRoi == null ? null : other.sanctionRoi.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.assignedTo = other.assignedTo == null ? null : other.assignedTo.copy();
        this.assignedFrom = other.assignedFrom == null ? null : other.assignedFrom.copy();
        this.securityDocUrl = other.securityDocUrl == null ? null : other.securityDocUrl.copy();
        this.lastModified = other.lastModified == null ? null : other.lastModified.copy();
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy();
        this.margin = other.margin == null ? null : other.margin.copy();
        this.securityOffered = other.securityOffered == null ? null : other.securityOffered.copy();
        this.directorMeetDate = other.directorMeetDate == null ? null : other.directorMeetDate.copy();
        this.commityMeetDate = other.commityMeetDate == null ? null : other.commityMeetDate.copy();
        this.directorAmount = other.directorAmount == null ? null : other.directorAmount.copy();
        this.directorRoi = other.directorRoi == null ? null : other.directorRoi.copy();
        this.sanctionDate = other.sanctionDate == null ? null : other.sanctionDate.copy();
        this.memberId = other.memberId == null ? null : other.memberId.copy();
        this.securityUserId = other.securityUserId == null ? null : other.securityUserId.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.branchId = other.branchId == null ? null : other.branchId.copy();
        this.hasSubmitLegal = other.hasSubmitLegal == null ? null : other.hasSubmitLegal.copy();
        this.hasReceiveLegal = other.hasReceiveLegal == null ? null : other.hasReceiveLegal.copy();
        this.hasRequiredDirRec = other.hasRequiredDirRec == null ? null : other.hasRequiredDirRec.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LoanApplicationsCriteria copy() {
        return new LoanApplicationsCriteria(this);
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

    public StringFilter getApplicationNo() {
        return applicationNo;
    }

    public StringFilter applicationNo() {
        if (applicationNo == null) {
            applicationNo = new StringFilter();
        }
        return applicationNo;
    }

    public void setApplicationNo(StringFilter applicationNo) {
        this.applicationNo = applicationNo;
    }

    public DoubleFilter getDemandAmount() {
        return demandAmount;
    }

    public DoubleFilter demandAmount() {
        if (demandAmount == null) {
            demandAmount = new DoubleFilter();
        }
        return demandAmount;
    }

    public void setDemandAmount(DoubleFilter demandAmount) {
        this.demandAmount = demandAmount;
    }

    public StringFilter getLoanPurpose() {
        return loanPurpose;
    }

    public StringFilter loanPurpose() {
        if (loanPurpose == null) {
            loanPurpose = new StringFilter();
        }
        return loanPurpose;
    }

    public void setLoanPurpose(StringFilter loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public LoanStatusFilter getStatus() {
        return status;
    }

    public LoanStatusFilter status() {
        if (status == null) {
            status = new LoanStatusFilter();
        }
        return status;
    }

    public void setStatus(LoanStatusFilter status) {
        this.status = status;
    }

    public IntegerFilter getDemandedLandAreaInHector() {
        return demandedLandAreaInHector;
    }

    public IntegerFilter demandedLandAreaInHector() {
        if (demandedLandAreaInHector == null) {
            demandedLandAreaInHector = new IntegerFilter();
        }
        return demandedLandAreaInHector;
    }

    public void setDemandedLandAreaInHector(IntegerFilter demandedLandAreaInHector) {
        this.demandedLandAreaInHector = demandedLandAreaInHector;
    }

    public StringFilter getSeasonOfCropLoan() {
        return seasonOfCropLoan;
    }

    public StringFilter seasonOfCropLoan() {
        if (seasonOfCropLoan == null) {
            seasonOfCropLoan = new StringFilter();
        }
        return seasonOfCropLoan;
    }

    public void setSeasonOfCropLoan(StringFilter seasonOfCropLoan) {
        this.seasonOfCropLoan = seasonOfCropLoan;
    }

    public StepperNumberFilter getLoanSteps() {
        return loanSteps;
    }

    public StepperNumberFilter loanSteps() {
        if (loanSteps == null) {
            loanSteps = new StepperNumberFilter();
        }
        return loanSteps;
    }

    public void setLoanSteps(StepperNumberFilter loanSteps) {
        this.loanSteps = loanSteps;
    }

    public BooleanFilter getIsInsured() {
        return isInsured;
    }

    public BooleanFilter isInsured() {
        if (isInsured == null) {
            isInsured = new BooleanFilter();
        }
        return isInsured;
    }

    public void setIsInsured(BooleanFilter isInsured) {
        this.isInsured = isInsured;
    }

    public DoubleFilter getLoanBenefitingArea() {
        return loanBenefitingArea;
    }

    public DoubleFilter loanBenefitingArea() {
        if (loanBenefitingArea == null) {
            loanBenefitingArea = new DoubleFilter();
        }
        return loanBenefitingArea;
    }

    public void setLoanBenefitingArea(DoubleFilter loanBenefitingArea) {
        this.loanBenefitingArea = loanBenefitingArea;
    }

    public DoubleFilter getCostOfInvestment() {
        return costOfInvestment;
    }

    public DoubleFilter costOfInvestment() {
        if (costOfInvestment == null) {
            costOfInvestment = new DoubleFilter();
        }
        return costOfInvestment;
    }

    public void setCostOfInvestment(DoubleFilter costOfInvestment) {
        this.costOfInvestment = costOfInvestment;
    }

    public LongFilter getMortgageDeedNo() {
        return mortgageDeedNo;
    }

    public LongFilter mortgageDeedNo() {
        if (mortgageDeedNo == null) {
            mortgageDeedNo = new LongFilter();
        }
        return mortgageDeedNo;
    }

    public void setMortgageDeedNo(LongFilter mortgageDeedNo) {
        this.mortgageDeedNo = mortgageDeedNo;
    }

    public LongFilter getResolutionNo() {
        return resolutionNo;
    }

    public LongFilter resolutionNo() {
        if (resolutionNo == null) {
            resolutionNo = new LongFilter();
        }
        return resolutionNo;
    }

    public void setResolutionNo(LongFilter resolutionNo) {
        this.resolutionNo = resolutionNo;
    }

    public InstantFilter getMortgageDate() {
        return mortgageDate;
    }

    public InstantFilter mortgageDate() {
        if (mortgageDate == null) {
            mortgageDate = new InstantFilter();
        }
        return mortgageDate;
    }

    public void setMortgageDate(InstantFilter mortgageDate) {
        this.mortgageDate = mortgageDate;
    }

    public InstantFilter getCreatedOn() {
        return createdOn;
    }

    public InstantFilter createdOn() {
        if (createdOn == null) {
            createdOn = new InstantFilter();
        }
        return createdOn;
    }

    public void setCreatedOn(InstantFilter createdOn) {
        this.createdOn = createdOn;
    }

    public DoubleFilter getExtentMorgageValue() {
        return extentMorgageValue;
    }

    public DoubleFilter extentMorgageValue() {
        if (extentMorgageValue == null) {
            extentMorgageValue = new DoubleFilter();
        }
        return extentMorgageValue;
    }

    public void setExtentMorgageValue(DoubleFilter extentMorgageValue) {
        this.extentMorgageValue = extentMorgageValue;
    }

    public DoubleFilter getDownPaymentAmt() {
        return downPaymentAmt;
    }

    public DoubleFilter downPaymentAmt() {
        if (downPaymentAmt == null) {
            downPaymentAmt = new DoubleFilter();
        }
        return downPaymentAmt;
    }

    public void setDownPaymentAmt(DoubleFilter downPaymentAmt) {
        this.downPaymentAmt = downPaymentAmt;
    }

    public DoubleFilter getLtvRatio() {
        return ltvRatio;
    }

    public DoubleFilter ltvRatio() {
        if (ltvRatio == null) {
            ltvRatio = new DoubleFilter();
        }
        return ltvRatio;
    }

    public void setLtvRatio(DoubleFilter ltvRatio) {
        this.ltvRatio = ltvRatio;
    }

    public DoubleFilter getProcessingFee() {
        return processingFee;
    }

    public DoubleFilter processingFee() {
        if (processingFee == null) {
            processingFee = new DoubleFilter();
        }
        return processingFee;
    }

    public void setProcessingFee(DoubleFilter processingFee) {
        this.processingFee = processingFee;
    }

    public DoubleFilter getPenalInterest() {
        return penalInterest;
    }

    public DoubleFilter penalInterest() {
        if (penalInterest == null) {
            penalInterest = new DoubleFilter();
        }
        return penalInterest;
    }

    public void setPenalInterest(DoubleFilter penalInterest) {
        this.penalInterest = penalInterest;
    }

    public StringFilter getMoratorium() {
        return moratorium;
    }

    public StringFilter moratorium() {
        if (moratorium == null) {
            moratorium = new StringFilter();
        }
        return moratorium;
    }

    public void setMoratorium(StringFilter moratorium) {
        this.moratorium = moratorium;
    }

    public DoubleFilter getRoi() {
        return roi;
    }

    public DoubleFilter roi() {
        if (roi == null) {
            roi = new DoubleFilter();
        }
        return roi;
    }

    public void setRoi(DoubleFilter roi) {
        this.roi = roi;
    }

    public DoubleFilter getCommityAmt() {
        return commityAmt;
    }

    public DoubleFilter commityAmt() {
        if (commityAmt == null) {
            commityAmt = new DoubleFilter();
        }
        return commityAmt;
    }

    public void setCommityAmt(DoubleFilter commityAmt) {
        this.commityAmt = commityAmt;
    }

    public DoubleFilter getCommityRoi() {
        return commityRoi;
    }

    public DoubleFilter commityRoi() {
        if (commityRoi == null) {
            commityRoi = new DoubleFilter();
        }
        return commityRoi;
    }

    public void setCommityRoi(DoubleFilter commityRoi) {
        this.commityRoi = commityRoi;
    }

    public DoubleFilter getSanctionAmt() {
        return sanctionAmt;
    }

    public DoubleFilter sanctionAmt() {
        if (sanctionAmt == null) {
            sanctionAmt = new DoubleFilter();
        }
        return sanctionAmt;
    }

    public void setSanctionAmt(DoubleFilter sanctionAmt) {
        this.sanctionAmt = sanctionAmt;
    }

    public DoubleFilter getSanctionRoi() {
        return sanctionRoi;
    }

    public DoubleFilter sanctionRoi() {
        if (sanctionRoi == null) {
            sanctionRoi = new DoubleFilter();
        }
        return sanctionRoi;
    }

    public void setSanctionRoi(DoubleFilter sanctionRoi) {
        this.sanctionRoi = sanctionRoi;
    }

    public StringFilter getYear() {
        return year;
    }

    public StringFilter year() {
        if (year == null) {
            year = new StringFilter();
        }
        return year;
    }

    public void setYear(StringFilter year) {
        this.year = year;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public StringFilter remark() {
        if (remark == null) {
            remark = new StringFilter();
        }
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
    }

    public LongFilter getAssignedTo() {
        return assignedTo;
    }

    public LongFilter assignedTo() {
        if (assignedTo == null) {
            assignedTo = new LongFilter();
        }
        return assignedTo;
    }

    public void setAssignedTo(LongFilter assignedTo) {
        this.assignedTo = assignedTo;
    }

    public LongFilter getAssignedFrom() {
        return assignedFrom;
    }

    public LongFilter assignedFrom() {
        if (assignedFrom == null) {
            assignedFrom = new LongFilter();
        }
        return assignedFrom;
    }

    public void setAssignedFrom(LongFilter assignedFrom) {
        this.assignedFrom = assignedFrom;
    }

    public StringFilter getSecurityDocUrl() {
        return securityDocUrl;
    }

    public StringFilter securityDocUrl() {
        if (securityDocUrl == null) {
            securityDocUrl = new StringFilter();
        }
        return securityDocUrl;
    }

    public void setSecurityDocUrl(StringFilter securityDocUrl) {
        this.securityDocUrl = securityDocUrl;
    }

    public InstantFilter getLastModified() {
        return lastModified;
    }

    public InstantFilter lastModified() {
        if (lastModified == null) {
            lastModified = new InstantFilter();
        }
        return lastModified;
    }

    public void setLastModified(InstantFilter lastModified) {
        this.lastModified = lastModified;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public StringFilter lastModifiedBy() {
        if (lastModifiedBy == null) {
            lastModifiedBy = new StringFilter();
        }
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public StringFilter getMargin() {
        return margin;
    }

    public StringFilter margin() {
        if (margin == null) {
            margin = new StringFilter();
        }
        return margin;
    }

    public void setMargin(StringFilter margin) {
        this.margin = margin;
    }

    public StringFilter getSecurityOffered() {
        return securityOffered;
    }

    public StringFilter securityOffered() {
        if (securityOffered == null) {
            securityOffered = new StringFilter();
        }
        return securityOffered;
    }

    public void setSecurityOffered(StringFilter securityOffered) {
        this.securityOffered = securityOffered;
    }

    public InstantFilter getDirectorMeetDate() {
        return directorMeetDate;
    }

    public InstantFilter directorMeetDate() {
        if (directorMeetDate == null) {
            directorMeetDate = new InstantFilter();
        }
        return directorMeetDate;
    }

    public void setDirectorMeetDate(InstantFilter directorMeetDate) {
        this.directorMeetDate = directorMeetDate;
    }

    public InstantFilter getCommityMeetDate() {
        return commityMeetDate;
    }

    public InstantFilter commityMeetDate() {
        if (commityMeetDate == null) {
            commityMeetDate = new InstantFilter();
        }
        return commityMeetDate;
    }

    public void setCommityMeetDate(InstantFilter commityMeetDate) {
        this.commityMeetDate = commityMeetDate;
    }

    public DoubleFilter getDirectorAmount() {
        return directorAmount;
    }

    public DoubleFilter directorAmount() {
        if (directorAmount == null) {
            directorAmount = new DoubleFilter();
        }
        return directorAmount;
    }

    public void setDirectorAmount(DoubleFilter directorAmount) {
        this.directorAmount = directorAmount;
    }

    public DoubleFilter getDirectorRoi() {
        return directorRoi;
    }

    public DoubleFilter directorRoi() {
        if (directorRoi == null) {
            directorRoi = new DoubleFilter();
        }
        return directorRoi;
    }

    public void setDirectorRoi(DoubleFilter directorRoi) {
        this.directorRoi = directorRoi;
    }

    public InstantFilter getSanctionDate() {
        return sanctionDate;
    }

    public InstantFilter sanctionDate() {
        if (sanctionDate == null) {
            sanctionDate = new InstantFilter();
        }
        return sanctionDate;
    }

    public void setSanctionDate(InstantFilter sanctionDate) {
        this.sanctionDate = sanctionDate;
    }

    public LongFilter getMemberId() {
        return memberId;
    }

    public LongFilter memberId() {
        if (memberId == null) {
            memberId = new LongFilter();
        }
        return memberId;
    }

    public void setMemberId(LongFilter memberId) {
        this.memberId = memberId;
    }

    public LongFilter getSecurityUserId() {
        return securityUserId;
    }

    public LongFilter securityUserId() {
        if (securityUserId == null) {
            securityUserId = new LongFilter();
        }
        return securityUserId;
    }

    public void setSecurityUserId(LongFilter securityUserId) {
        this.securityUserId = securityUserId;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public LongFilter productId() {
        if (productId == null) {
            productId = new LongFilter();
        }
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
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

    public BooleanFilter getHasSubmitLegal() {
        return hasSubmitLegal;
    }

    public BooleanFilter hasSubmitLegal() {
        if (hasSubmitLegal == null) {
            hasSubmitLegal = new BooleanFilter();
        }
        return hasSubmitLegal;
    }

    public void setHasSubmitLegal(BooleanFilter hasSubmitLegal) {
        this.hasSubmitLegal = hasSubmitLegal;
    }

    public BooleanFilter getHasReceiveLegal() {
        return hasReceiveLegal;
    }

    public BooleanFilter hasReceiveLegal() {
        if (hasReceiveLegal == null) {
            hasReceiveLegal = new BooleanFilter();
        }
        return hasReceiveLegal;
    }

    public void setHasReceiveLegal(BooleanFilter hasReceiveLegal) {
        this.hasReceiveLegal = hasReceiveLegal;
    }

    public BooleanFilter getHasRequiredDirRec() {
        return hasRequiredDirRec;
    }

    public BooleanFilter hasRequiredDirRec() {
        if (hasRequiredDirRec == null) {
            hasRequiredDirRec = new BooleanFilter();
        }
        return hasRequiredDirRec;
    }

    public void setHasRequiredDirRec(BooleanFilter hasRequiredDirRec) {
        this.hasRequiredDirRec = hasRequiredDirRec;
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
        final LoanApplicationsCriteria that = (LoanApplicationsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(applicationNo, that.applicationNo) &&
            Objects.equals(demandAmount, that.demandAmount) &&
            Objects.equals(loanPurpose, that.loanPurpose) &&
            Objects.equals(status, that.status) &&
            Objects.equals(demandedLandAreaInHector, that.demandedLandAreaInHector) &&
            Objects.equals(seasonOfCropLoan, that.seasonOfCropLoan) &&
            Objects.equals(loanSteps, that.loanSteps) &&
            Objects.equals(isInsured, that.isInsured) &&
            Objects.equals(loanBenefitingArea, that.loanBenefitingArea) &&
            Objects.equals(costOfInvestment, that.costOfInvestment) &&
            Objects.equals(mortgageDeedNo, that.mortgageDeedNo) &&
            Objects.equals(resolutionNo, that.resolutionNo) &&
            Objects.equals(mortgageDate, that.mortgageDate) &&
            Objects.equals(createdOn, that.createdOn) &&
            Objects.equals(extentMorgageValue, that.extentMorgageValue) &&
            Objects.equals(downPaymentAmt, that.downPaymentAmt) &&
            Objects.equals(ltvRatio, that.ltvRatio) &&
            Objects.equals(processingFee, that.processingFee) &&
            Objects.equals(penalInterest, that.penalInterest) &&
            Objects.equals(moratorium, that.moratorium) &&
            Objects.equals(roi, that.roi) &&
            Objects.equals(commityAmt, that.commityAmt) &&
            Objects.equals(commityRoi, that.commityRoi) &&
            Objects.equals(sanctionAmt, that.sanctionAmt) &&
            Objects.equals(sanctionRoi, that.sanctionRoi) &&
            Objects.equals(year, that.year) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(assignedTo, that.assignedTo) &&
            Objects.equals(assignedFrom, that.assignedFrom) &&
            Objects.equals(securityDocUrl, that.securityDocUrl) &&
            Objects.equals(lastModified, that.lastModified) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(margin, that.margin) &&
            Objects.equals(securityOffered, that.securityOffered) &&
            Objects.equals(directorMeetDate, that.directorMeetDate) &&
            Objects.equals(commityMeetDate, that.commityMeetDate) &&
            Objects.equals(directorAmount, that.directorAmount) &&
            Objects.equals(directorRoi, that.directorRoi) &&
            Objects.equals(sanctionDate, that.sanctionDate) &&
            Objects.equals(memberId, that.memberId) &&
            Objects.equals(securityUserId, that.securityUserId) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(branchId, that.branchId) &&
            Objects.equals(hasSubmitLegal, that.hasSubmitLegal) &&
            Objects.equals(hasReceiveLegal, that.hasReceiveLegal) &&
            Objects.equals(hasRequiredDirRec, that.hasRequiredDirRec) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            applicationNo,
            demandAmount,
            loanPurpose,
            status,
            demandedLandAreaInHector,
            seasonOfCropLoan,
            loanSteps,
            isInsured,
            loanBenefitingArea,
            costOfInvestment,
            mortgageDeedNo,
            resolutionNo,
            mortgageDate,
            createdOn,
            extentMorgageValue,
            downPaymentAmt,
            ltvRatio,
            processingFee,
            penalInterest,
            moratorium,
            roi,
            commityAmt,
            commityRoi,
            sanctionAmt,
            sanctionRoi,
            year,
            remark,
            assignedTo,
            assignedFrom,
            securityDocUrl,
            lastModified,
            lastModifiedBy,
            margin,
            securityOffered,
            directorMeetDate,
            commityMeetDate,
            directorAmount,
            directorRoi,
            sanctionDate,
            memberId,
            securityUserId,
            productId,
            branchId,
            hasSubmitLegal,
            hasReceiveLegal,
            hasRequiredDirRec,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LoanApplicationsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (applicationNo != null ? "applicationNo=" + applicationNo + ", " : "") +
            (demandAmount != null ? "demandAmount=" + demandAmount + ", " : "") +
            (loanPurpose != null ? "loanPurpose=" + loanPurpose + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (demandedLandAreaInHector != null ? "demandedLandAreaInHector=" + demandedLandAreaInHector + ", " : "") +
            (seasonOfCropLoan != null ? "seasonOfCropLoan=" + seasonOfCropLoan + ", " : "") +
            (loanSteps != null ? "loanSteps=" + loanSteps + ", " : "") +
            (isInsured != null ? "isInsured=" + isInsured + ", " : "") +
            (loanBenefitingArea != null ? "loanBenefitingArea=" + loanBenefitingArea + ", " : "") +
            (costOfInvestment != null ? "costOfInvestment=" + costOfInvestment + ", " : "") +
            (mortgageDeedNo != null ? "mortgageDeedNo=" + mortgageDeedNo + ", " : "") +
            (resolutionNo != null ? "resolutionNo=" + resolutionNo + ", " : "") +
            (mortgageDate != null ? "mortgageDate=" + mortgageDate + ", " : "") +
            (createdOn != null ? "createdOn=" + createdOn + ", " : "") +
            (extentMorgageValue != null ? "extentMorgageValue=" + extentMorgageValue + ", " : "") +
            (downPaymentAmt != null ? "downPaymentAmt=" + downPaymentAmt + ", " : "") +
            (ltvRatio != null ? "ltvRatio=" + ltvRatio + ", " : "") +
            (processingFee != null ? "processingFee=" + processingFee + ", " : "") +
            (penalInterest != null ? "penalInterest=" + penalInterest + ", " : "") +
            (moratorium != null ? "moratorium=" + moratorium + ", " : "") +
            (roi != null ? "roi=" + roi + ", " : "") +
            (commityAmt != null ? "commityAmt=" + commityAmt + ", " : "") +
            (commityRoi != null ? "commityRoi=" + commityRoi + ", " : "") +
            (sanctionAmt != null ? "sanctionAmt=" + sanctionAmt + ", " : "") +
            (sanctionRoi != null ? "sanctionRoi=" + sanctionRoi + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            (assignedTo != null ? "assignedTo=" + assignedTo + ", " : "") +
            (assignedFrom != null ? "assignedFrom=" + assignedFrom + ", " : "") +
            (securityDocUrl != null ? "securityDocUrl=" + securityDocUrl + ", " : "") +
            (lastModified != null ? "lastModified=" + lastModified + ", " : "") +
            (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
            (margin != null ? "margin=" + margin + ", " : "") +
            (securityOffered != null ? "securityOffered=" + securityOffered + ", " : "") +
            (directorMeetDate != null ? "directorMeetDate=" + directorMeetDate + ", " : "") +
            (commityMeetDate != null ? "commityMeetDate=" + commityMeetDate + ", " : "") +
            (directorAmount != null ? "directorAmount=" + directorAmount + ", " : "") +
            (directorRoi != null ? "directorRoi=" + directorRoi + ", " : "") +
            (sanctionDate != null ? "sanctionDate=" + sanctionDate + ", " : "") +
            (memberId != null ? "memberId=" + memberId + ", " : "") +
            (securityUserId != null ? "securityUserId=" + securityUserId + ", " : "") +
            (productId != null ? "productId=" + productId + ", " : "") +
            (branchId != null ? "branchId=" + branchId + ", " : "") +
            (hasSubmitLegal != null ? "hasSubmitLegal=" + hasSubmitLegal + ", " : "") +
            (hasReceiveLegal != null ? "hasReceiveLegal=" + hasReceiveLegal + ", " : "") +
            (hasRequiredDirRec != null ? "hasRequiredDirRec=" + hasRequiredDirRec + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
