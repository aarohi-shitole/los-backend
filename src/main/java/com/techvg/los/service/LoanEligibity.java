package com.techvg.los.service;

import com.techvg.los.domain.ApplicantEligibility;
import com.techvg.los.domain.enumeration.DocumentHelper;
import com.techvg.los.domain.enumeration.LoanElibilityConstants;
import com.techvg.los.service.criteria.IncomeDetailsCriteria;
import com.techvg.los.service.criteria.MemberExistingfacilityCriteria;
import com.techvg.los.service.criteria.RemarkHistoryCriteria;
import com.techvg.los.service.criteria.RemarkHistoryCriteria.DocumentHelperFilter;
import com.techvg.los.service.dto.IncomeDetailsDTO;
import com.techvg.los.service.dto.LoanApplicationsDTO;
import com.techvg.los.service.dto.MemberExistingfacilityDTO;
import com.techvg.los.service.dto.RemarkHistoryDTO;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.io.Console;
import java.text.DecimalFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

@Service
@Transactional
public class LoanEligibity {

    private static final String ENTITY_NAME = "loanApplications";

    @Autowired
    private IncomeDetailsQueryService incomeDetailsQueryService;

    @Autowired
    private RemarkHistoryQueryService remarkHistoryQueryService;

    @Autowired
    private MemberExistingfacilityQueryService memberExistingfacilityQueryService;

    private final Logger log = LoggerFactory.getLogger(LoanEligibity.class);

    public ApplicantEligibility checkEligibleAmt(Long loanApplicationId, LoanApplicationsDTO loanApplicationDTO) {
        ApplicantEligibility applicantEligibility = new ApplicantEligibility();

        double outstandingAmt = 0.0;
        double netIncome = 0.0;
        double loanEligibleAmountAgainstMortgageProperty = 0.0;
        // ---------------------------------------------------------------------------------------
        // First Criteria- To find Valuation of asset

        double loanRequestedAmount = loanApplicationDTO.getDemandAmount();
        applicantEligibility.setDememdedAmt(loanRequestedAmount);

        double assetValuationOfMortagetedProperty = findAssetValuationOfMortagetedProperty(loanApplicationId);
        applicantEligibility.setPropertyValuation(assetValuationOfMortagetedProperty);

        loanEligibleAmountAgainstMortgageProperty =
            assetValuationOfMortagetedProperty * (LoanElibilityConstants.APPROVAL_PERSANTAGE_FOR_MORTGAGE_PROPERTY / 100);
        //loanEligibleAmountAgainstMortgageProperty=round(loanEligibleAmountAgainstMortgageProperty);

        applicantEligibility.setLoanEligibleAmtAgainstMortProperty(loanEligibleAmountAgainstMortgageProperty);

        String message = null;
        if (loanRequestedAmount > loanEligibleAmountAgainstMortgageProperty) {
            message = "Demand is more than Mortgage value";
        } else {
            message = "Mortgage value is more than Demand";
        }

        applicantEligibility.setRemarkBasedOnValuation(message);

        // ----------------------------------------------------------------------------------------
        // Second Criteria- To find EMI Capacity
        long memberId = loanApplicationDTO.getMemberId();

        double memberEMICapacityBasedOnIncome = this.findMemberEMICapacityOnIncome(memberId);

        // --------------------------------------------------------------------------------------
        // We need substract Monthly EMI as well from Monthly In hand Salary or Amount.

        double totalYearlyExisingEMIAmount = findEMIExistingLoanOfMember(memberId) * 12;

        double memberEMICapacity = (memberEMICapacityBasedOnIncome - totalYearlyExisingEMIAmount) / 12;
        // double EMIMonthlyCapacity = memberEMICapacity / 12;
        // -----------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------------------

        double rateOfInterest = loanApplicationDTO.getRoi();

        double productMaxTenure = loanApplicationDTO.getProduct().getMaxTenor();

        EMICalculator calculator = new EMICalculator();

        //        double requiredEMIhasToPay = calculator.emi_calculator_in_arrears(loanRequestedAmount, rateOfInterest, productMaxTenure);
        double requiredEMIhasToPay = calculator.emi_calculator_in_arrears(
            loanEligibleAmountAgainstMortgageProperty,
            rateOfInterest,
            productMaxTenure
        );

        requiredEMIhasToPay = round(requiredEMIhasToPay);

        applicantEligibility.setRequiredEMIhasToPay(requiredEMIhasToPay);
        applicantEligibility.setRequiredEMIhasToPay(requiredEMIhasToPay);

        log.debug("RequiredEMIhasToPay: " + requiredEMIhasToPay);

        double memberLaonCapacity = 0.0;

        String EmiCapacityRemark;

        if (requiredEMIhasToPay > memberEMICapacity) {
            memberLaonCapacity = (memberEMICapacity * productMaxTenure * 12) * (100 - rateOfInterest) / 100;
            log.debug("EligibleAmt: " + memberLaonCapacity);

            EmiCapacityRemark = "EMI is not within his/her capacity";
        } else {
            memberLaonCapacity = loanEligibleAmountAgainstMortgageProperty;
            log.debug("EligibleAmt: " + memberLaonCapacity);

            EmiCapacityRemark = "EMI is within his/her capacity";
        }

        outstandingAmt = findExistingOusStandingLoanAmountOfMember(memberId);

        netIncome = findMemberNetIncome(memberId);
        applicantEligibility.setNetIncome(netIncome);
        applicantEligibility.setMemberEMICapacity(memberEMICapacity);
        // applicantEligibility.setEMIMonthlyCapacity(EMIMonthlyCapacity);
        applicantEligibility.setEmiCapacityRemark(EmiCapacityRemark);
        applicantEligibility.setEligibleAmt(memberLaonCapacity);
        applicantEligibility.setOutstanding(outstandingAmt);
        applicantEligibility.setEmiCapacityOnIncome(memberEMICapacityBasedOnIncome);

        return applicantEligibility;
    }

    private double findAssetValuationOfMortagetedProperty(Long loanApplicationId) {
        double assetValuationOfMortagetedProperty = 0.0;
        // --------------------Check eligibility According mortgage valuation
        // ---------------------------
        RemarkHistoryCriteria remarkCriteria = new RemarkHistoryCriteria();
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(loanApplicationId);
        remarkCriteria.setLoanApplicationsId(longFilter);

        DocumentHelperFilter tagFilter = new DocumentHelperFilter();
        tagFilter.setEquals(DocumentHelper.LOAN_APPRAIZAL);
        remarkCriteria.setTag(tagFilter);

        StringFilter remarkSubType = new StringFilter();
        remarkSubType.setEquals(DocumentHelper.LOAN_VALUATION.toString());
        remarkCriteria.setRemarkSubType(remarkSubType);

        StringFilter remarkType = new StringFilter();
        remarkType.setEquals(DocumentHelper.LOAN_VALUATION.toString());
        remarkCriteria.setRemarkType(remarkType);

        List<RemarkHistoryDTO> remarkobj = remarkHistoryQueryService.findByCriteria(remarkCriteria);

        if (!remarkobj.isEmpty()) {
            for (RemarkHistoryDTO remarkHistoryDTO : remarkobj) {
                assetValuationOfMortagetedProperty += remarkHistoryDTO.getModifiedAmt() != null ? remarkHistoryDTO.getModifiedAmt() : 0.0;
            }
        }
        assetValuationOfMortagetedProperty = round(assetValuationOfMortagetedProperty);
        return assetValuationOfMortagetedProperty;
    }

    private double findMemberEMICapacityOnIncome(long memberId) {
        double emiCapacity = 0.0;
        double netIncome = 0.0;

        // --------------------Check eligibility According to income
        // ---------------------------
        IncomeDetailsCriteria criteria = new IncomeDetailsCriteria();
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(memberId);
        criteria.setMemberId(longFilter);
        List<IncomeDetailsDTO> incomeList = incomeDetailsQueryService.findByCriteria(criteria);

        if (!incomeList.isEmpty()) {
            log.debug("In if Income list : {}", incomeList);
            for (IncomeDetailsDTO incomeObj : incomeList) {
                log.debug("In if..for Income object : {}", incomeObj);
                if (incomeObj.getExpenses() != null) {
                    log.debug("In if..for..if income expense : {}", incomeObj.getExpenses());
                    netIncome += incomeObj.getGrossIncome() - incomeObj.getExpenses();
                } else {
                    netIncome += incomeObj.getNetIncome();
                }
            }
            log.debug("In netIncome : {}", netIncome);

            emiCapacity = netIncome * (LoanElibilityConstants.LOAN_ELIGIBALE_PERSANTAGE / 100);
        } else {
            throw new BadRequestAlertException(
                "IncomeDetails should present for member..Please! Enter required Income details ",
                ENTITY_NAME,
                "IncomeDetails not exist"
            );
        }
        // ---------------------------------------------------------------------------------------

        return emiCapacity;
    }

    private double findMemberNetIncome(long memberId) {
        double netIncome = 0.0;

        // --------------------Find income for member
        IncomeDetailsCriteria criteria = new IncomeDetailsCriteria();
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(memberId);
        criteria.setMemberId(longFilter);
        List<IncomeDetailsDTO> incomeList = incomeDetailsQueryService.findByCriteria(criteria);

        if (!incomeList.isEmpty()) {
            log.debug("In if Income list : {}", incomeList);
            for (IncomeDetailsDTO incomeObj : incomeList) {
                log.debug("In if..for Income object : {}", incomeObj);
                if (incomeObj.getExpenses() != null) {
                    log.debug("In if..for..if income expense : {}", incomeObj.getExpenses());
                    netIncome += incomeObj.getGrossIncome() - incomeObj.getExpenses();
                } else {
                    netIncome += incomeObj.getNetIncome();
                }
            }
        } else {
            throw new BadRequestAlertException(
                "IncomeDetails should present for member..Please! Enter required Income details ",
                ENTITY_NAME,
                "IncomeDetails not exist"
            );
        }
        // ---------------------------------------------------------------------------------------

        return netIncome;
    }

    public double findExistingOusStandingLoanAmountOfMember(Long memberId) {
        double outstandingAmt = 0.0;

        MemberExistingfacilityCriteria facilitiesCriteria = new MemberExistingfacilityCriteria();
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(memberId);
        facilitiesCriteria.setMemberId(longFilter);

        List<MemberExistingfacilityDTO> facilitiesList = memberExistingfacilityQueryService.findByCriteria(facilitiesCriteria);
        log.debug("In findExistingOusStandingLoanAmountOfMember function in facilitiesList" + facilitiesList);
        if (!facilitiesList.isEmpty()) {
            log.debug("In findExistingOusStandingLoanAmountOfMember function in !facilitiesList.isEmpty()");
            for (MemberExistingfacilityDTO facilityObj : facilitiesList) {
                if (facilityObj.getOutstandingInLac() != null) {
                    log.debug("In findExistingOusStandingLoanAmountOfMember function in MemberExistingfacilityDTO");
                    outstandingAmt += facilityObj.getOutstandingInLac();
                }
            }
        }

        return outstandingAmt;
    }

    public double findEMIExistingLoanOfMember(Long memberId) {
        double monthlyEMIAmount = 0.0;

        MemberExistingfacilityCriteria facilitiesCriteria = new MemberExistingfacilityCriteria();
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(memberId);
        facilitiesCriteria.setMemberId(longFilter);

        List<MemberExistingfacilityDTO> facilitiesList = memberExistingfacilityQueryService.findByCriteria(facilitiesCriteria);

        if (!facilitiesList.isEmpty()) {
            for (MemberExistingfacilityDTO facilityObj : facilitiesList) {
                if (facilityObj.getEmiAmt() != null) {
                    monthlyEMIAmount += facilityObj.getEmiAmt();
                }
            }
        }

        return monthlyEMIAmount;
    }

    double round(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        value = Double.valueOf(df.format(value));

        System.out.println(value); // 200.35
        return value;
    }
}
