package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.LoanApplications;
import com.techvg.los.domain.Member;
import com.techvg.los.domain.Product;
import com.techvg.los.domain.SecurityUser;
import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.domain.enumeration.StepperNumber;
import com.techvg.los.repository.LoanApplicationsRepository;
import com.techvg.los.service.criteria.LoanApplicationsCriteria;
import com.techvg.los.service.dto.LoanApplicationsDTO;
import com.techvg.los.service.mapper.LoanApplicationsMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LoanApplicationsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoanApplicationsResourceIT {

    private static final String DEFAULT_APPLICATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_NO = "BBBBBBBBBB";

    private static final Double DEFAULT_DEMAND_AMOUNT = 1D;
    private static final Double UPDATED_DEMAND_AMOUNT = 2D;
    private static final Double SMALLER_DEMAND_AMOUNT = 1D - 1D;

    private static final String DEFAULT_LOAN_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_PURPOSE = "BBBBBBBBBB";

    private static final LoanStatus DEFAULT_STATUS = LoanStatus.DRAFT;
    private static final LoanStatus UPDATED_STATUS = LoanStatus.APPLIED;

    private static final Integer DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR = 1;
    private static final Integer UPDATED_DEMANDED_LAND_AREA_IN_HECTOR = 2;
    private static final Integer SMALLER_DEMANDED_LAND_AREA_IN_HECTOR = 1 - 1;

    private static final String DEFAULT_SEASON_OF_CROP_LOAN = "AAAAAAAAAA";
    private static final String UPDATED_SEASON_OF_CROP_LOAN = "BBBBBBBBBB";

    private static final StepperNumber DEFAULT_LOAN_STEPS = StepperNumber.STEP_1;
    private static final StepperNumber UPDATED_LOAN_STEPS = StepperNumber.STEP_2;

    private static final Boolean DEFAULT_IS_INSURED = false;
    private static final Boolean UPDATED_IS_INSURED = true;

    private static final Double DEFAULT_LOAN_BENEFITING_AREA = 1D;
    private static final Double UPDATED_LOAN_BENEFITING_AREA = 2D;
    private static final Double SMALLER_LOAN_BENEFITING_AREA = 1D - 1D;

    private static final Double DEFAULT_COST_OF_INVESTMENT = 1D;
    private static final Double UPDATED_COST_OF_INVESTMENT = 2D;
    private static final Double SMALLER_COST_OF_INVESTMENT = 1D - 1D;

    private static final Long DEFAULT_MORTGAGE_DEED_NO = 1L;
    private static final Long UPDATED_MORTGAGE_DEED_NO = 2L;
    private static final Long SMALLER_MORTGAGE_DEED_NO = 1L - 1L;

    private static final Instant DEFAULT_MORTGAGE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MORTGAGE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_EXTENT_MORGAGE_VALUE = 1D;
    private static final Double UPDATED_EXTENT_MORGAGE_VALUE = 2D;
    private static final Double SMALLER_EXTENT_MORGAGE_VALUE = 1D - 1D;

    private static final Double DEFAULT_DOWN_PAYMENT_AMT = 1D;
    private static final Double UPDATED_DOWN_PAYMENT_AMT = 2D;
    private static final Double SMALLER_DOWN_PAYMENT_AMT = 1D - 1D;

    private static final Double DEFAULT_LTV_RATIO = 1D;
    private static final Double UPDATED_LTV_RATIO = 2D;
    private static final Double SMALLER_LTV_RATIO = 1D - 1D;

    private static final Double DEFAULT_PROCESSING_FEE = 1D;
    private static final Double UPDATED_PROCESSING_FEE = 2D;
    private static final Double SMALLER_PROCESSING_FEE = 1D - 1D;

    private static final Double DEFAULT_PENAL_INTEREST = 1D;
    private static final Double UPDATED_PENAL_INTEREST = 2D;
    private static final Double SMALLER_PENAL_INTEREST = 1D - 1D;

    private static final String DEFAULT_MORATORIUM = "AAAAAAAAAA";
    private static final String UPDATED_MORATORIUM = "BBBBBBBBBB";

    private static final Double DEFAULT_ROI = 1D;
    private static final Double UPDATED_ROI = 2D;
    private static final Double SMALLER_ROI = 1D - 1D;

    private static final Double DEFAULT_COMMITY_AMT = 1D;
    private static final Double UPDATED_COMMITY_AMT = 2D;
    private static final Double SMALLER_COMMITY_AMT = 1D - 1D;

    private static final Double DEFAULT_COMMITY_ROI = 1D;
    private static final Double UPDATED_COMMITY_ROI = 2D;
    private static final Double SMALLER_COMMITY_ROI = 1D - 1D;

    private static final Double DEFAULT_SECTION_AMT = 1D;
    private static final Double UPDATED_SECTION_AMT = 2D;
    private static final Double SMALLER_SECTION_AMT = 1D - 1D;

    private static final Double DEFAULT_SENCTION_ROI = 1D;
    private static final Double UPDATED_SENCTION_ROI = 2D;
    private static final Double SMALLER_SENCTION_ROI = 1D - 1D;

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final Long DEFAULT_ASSIGNED_TO = 1L;
    private static final Long UPDATED_ASSIGNED_TO = 2L;
    private static final Long SMALLER_ASSIGNED_TO = 1L - 1L;

    private static final Long DEFAULT_ASSIGNED_FROM = 1L;
    private static final Long UPDATED_ASSIGNED_FROM = 2L;
    private static final Long SMALLER_ASSIGNED_FROM = 1L - 1L;

    private static final String DEFAULT_SECURITY_DOC_URL = "AAAAAAAAAA";
    private static final String UPDATED_SECURITY_DOC_URL = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_MARGIN = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_SECURITY_OFFERED = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_4 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_5 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_5 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_6 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_6 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_7 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_7 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/loan-applications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoanApplicationsRepository loanApplicationsRepository;

    @Autowired
    private LoanApplicationsMapper loanApplicationsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoanApplicationsMockMvc;

    private LoanApplications loanApplications;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanApplications createEntity(EntityManager em) {
        LoanApplications loanApplications = new LoanApplications()
            .applicationNo(DEFAULT_APPLICATION_NO)
            .demandAmount(DEFAULT_DEMAND_AMOUNT)
            .loanPurpose(DEFAULT_LOAN_PURPOSE)
            .status(DEFAULT_STATUS)
            .demandedLandAreaInHector(DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR)
            .seasonOfCropLoan(DEFAULT_SEASON_OF_CROP_LOAN)
            .loanSteps(DEFAULT_LOAN_STEPS)
            .isInsured(DEFAULT_IS_INSURED)
            .loanBenefitingArea(DEFAULT_LOAN_BENEFITING_AREA)
            .costOfInvestment(DEFAULT_COST_OF_INVESTMENT)
            .mortgageDeedNo(DEFAULT_MORTGAGE_DEED_NO)
            .mortgageDate(DEFAULT_MORTGAGE_DATE)
            .extentMorgageValue(DEFAULT_EXTENT_MORGAGE_VALUE)
            .downPaymentAmt(DEFAULT_DOWN_PAYMENT_AMT)
            .ltvRatio(DEFAULT_LTV_RATIO)
            .processingFee(DEFAULT_PROCESSING_FEE)
            .penalInterest(DEFAULT_PENAL_INTEREST)
            .moratorium(DEFAULT_MORATORIUM)
            .roi(DEFAULT_ROI)
            .commityAmt(DEFAULT_COMMITY_AMT)
            .commityRoi(DEFAULT_COMMITY_ROI)
            .sanctionAmt(DEFAULT_SECTION_AMT)
            .sanctionRoi(DEFAULT_SENCTION_ROI)
            .year(DEFAULT_YEAR)
            //            .assignedTo(DEFAULT_ASSIGNED_TO)
            //            .assignedFrom(DEFAULT_ASSIGNED_FROM)
            .securityDocUrl(DEFAULT_SECURITY_DOC_URL)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .margin(DEFAULT_FREE_FIELD_1)
            .securityOffered(DEFAULT_FREE_FIELD_2);
        //            .freeField3(DEFAULT_FREE_FIELD_3)
        //            .freeField4(DEFAULT_FREE_FIELD_4)
        //            .freeField5(DEFAULT_FREE_FIELD_5)
        //            .freeField6(DEFAULT_FREE_FIELD_6);
        //        .sanctionDate(DEFAULT_FREE_FIELD_7);
        return loanApplications;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanApplications createUpdatedEntity(EntityManager em) {
        LoanApplications loanApplications = new LoanApplications()
            .applicationNo(UPDATED_APPLICATION_NO)
            .demandAmount(UPDATED_DEMAND_AMOUNT)
            .loanPurpose(UPDATED_LOAN_PURPOSE)
            .status(UPDATED_STATUS)
            .demandedLandAreaInHector(UPDATED_DEMANDED_LAND_AREA_IN_HECTOR)
            .seasonOfCropLoan(UPDATED_SEASON_OF_CROP_LOAN)
            .loanSteps(UPDATED_LOAN_STEPS)
            .isInsured(UPDATED_IS_INSURED)
            .loanBenefitingArea(UPDATED_LOAN_BENEFITING_AREA)
            .costOfInvestment(UPDATED_COST_OF_INVESTMENT)
            .mortgageDeedNo(UPDATED_MORTGAGE_DEED_NO)
            .mortgageDate(UPDATED_MORTGAGE_DATE)
            .extentMorgageValue(UPDATED_EXTENT_MORGAGE_VALUE)
            .downPaymentAmt(UPDATED_DOWN_PAYMENT_AMT)
            .ltvRatio(UPDATED_LTV_RATIO)
            .processingFee(UPDATED_PROCESSING_FEE)
            .penalInterest(UPDATED_PENAL_INTEREST)
            .moratorium(UPDATED_MORATORIUM)
            .roi(UPDATED_ROI)
            .commityAmt(UPDATED_COMMITY_AMT)
            .commityRoi(UPDATED_COMMITY_ROI)
            .sanctionAmt(UPDATED_SECTION_AMT)
            .sanctionRoi(UPDATED_SENCTION_ROI)
            .year(UPDATED_YEAR)
            //            .assignedTo(UPDATED_ASSIGNED_TO)
            //            .assignedFrom(UPDATED_ASSIGNED_FROM)
            .securityDocUrl(UPDATED_SECURITY_DOC_URL)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .margin(UPDATED_MARGIN)
            .securityOffered(UPDATED_SECURITY_OFFERED);
        //            .freeField3(UPDATED_FREE_FIELD_3)
        //            .freeField4(UPDATED_FREE_FIELD_4)
        //            .freeField5(UPDATED_FREE_FIELD_5)
        //            .freeField6(UPDATED_FREE_FIELD_6);
        //    .freeField7(UPDATED_FREE_FIELD_7);
        return loanApplications;
    }

    @BeforeEach
    public void initTest() {
        loanApplications = createEntity(em);
    }

    @Test
    @Transactional
    void createLoanApplications() throws Exception {
        int databaseSizeBeforeCreate = loanApplicationsRepository.findAll().size();
        // Create the LoanApplications
        LoanApplicationsDTO loanApplicationsDTO = loanApplicationsMapper.toDto(loanApplications);
        restLoanApplicationsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanApplicationsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LoanApplications in the database
        List<LoanApplications> loanApplicationsList = loanApplicationsRepository.findAll();
        assertThat(loanApplicationsList).hasSize(databaseSizeBeforeCreate + 1);
        LoanApplications testLoanApplications = loanApplicationsList.get(loanApplicationsList.size() - 1);
        assertThat(testLoanApplications.getApplicationNo()).isEqualTo(DEFAULT_APPLICATION_NO);
        assertThat(testLoanApplications.getDemandAmount()).isEqualTo(DEFAULT_DEMAND_AMOUNT);
        assertThat(testLoanApplications.getLoanPurpose()).isEqualTo(DEFAULT_LOAN_PURPOSE);
        assertThat(testLoanApplications.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLoanApplications.getDemandedLandAreaInHector()).isEqualTo(DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR);
        assertThat(testLoanApplications.getSeasonOfCropLoan()).isEqualTo(DEFAULT_SEASON_OF_CROP_LOAN);
        assertThat(testLoanApplications.getLoanSteps()).isEqualTo(DEFAULT_LOAN_STEPS);
        assertThat(testLoanApplications.getIsInsured()).isEqualTo(DEFAULT_IS_INSURED);
        assertThat(testLoanApplications.getLoanBenefitingArea()).isEqualTo(DEFAULT_LOAN_BENEFITING_AREA);
        assertThat(testLoanApplications.getCostOfInvestment()).isEqualTo(DEFAULT_COST_OF_INVESTMENT);
        assertThat(testLoanApplications.getMortgageDeedNo()).isEqualTo(DEFAULT_MORTGAGE_DEED_NO);
        assertThat(testLoanApplications.getMortgageDate()).isEqualTo(DEFAULT_MORTGAGE_DATE);
        assertThat(testLoanApplications.getExtentMorgageValue()).isEqualTo(DEFAULT_EXTENT_MORGAGE_VALUE);
        assertThat(testLoanApplications.getDownPaymentAmt()).isEqualTo(DEFAULT_DOWN_PAYMENT_AMT);
        assertThat(testLoanApplications.getLtvRatio()).isEqualTo(DEFAULT_LTV_RATIO);
        assertThat(testLoanApplications.getProcessingFee()).isEqualTo(DEFAULT_PROCESSING_FEE);
        assertThat(testLoanApplications.getPenalInterest()).isEqualTo(DEFAULT_PENAL_INTEREST);
        assertThat(testLoanApplications.getMoratorium()).isEqualTo(DEFAULT_MORATORIUM);
        assertThat(testLoanApplications.getRoi()).isEqualTo(DEFAULT_ROI);
        assertThat(testLoanApplications.getCommityAmt()).isEqualTo(DEFAULT_COMMITY_AMT);
        assertThat(testLoanApplications.getCommityRoi()).isEqualTo(DEFAULT_COMMITY_ROI);
        assertThat(testLoanApplications.getSanctionAmt()).isEqualTo(DEFAULT_SECTION_AMT);
        assertThat(testLoanApplications.getSanctionRoi()).isEqualTo(DEFAULT_SENCTION_ROI);
        assertThat(testLoanApplications.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testLoanApplications.getAssignedTo()).isEqualTo(DEFAULT_ASSIGNED_TO);
        assertThat(testLoanApplications.getAssignedFrom()).isEqualTo(DEFAULT_ASSIGNED_FROM);
        assertThat(testLoanApplications.getSecurityDocUrl()).isEqualTo(DEFAULT_SECURITY_DOC_URL);
        assertThat(testLoanApplications.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLoanApplications.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLoanApplications.getMargin()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLoanApplications.getSecurityOffered()).isEqualTo(DEFAULT_FREE_FIELD_2);
        //        assertThat(testLoanApplications.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        //        assertThat(testLoanApplications.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        //        assertThat(testLoanApplications.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        //        assertThat(testLoanApplications.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
        //   assertThat(testLoanApplications.getFreeField7()).isEqualTo(DEFAULT_FREE_FIELD_7);
    }

    @Test
    @Transactional
    void createLoanApplicationsWithExistingId() throws Exception {
        // Create the LoanApplications with an existing ID
        loanApplications.setId(1L);
        LoanApplicationsDTO loanApplicationsDTO = loanApplicationsMapper.toDto(loanApplications);

        int databaseSizeBeforeCreate = loanApplicationsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanApplicationsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanApplicationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanApplications in the database
        List<LoanApplications> loanApplicationsList = loanApplicationsRepository.findAll();
        assertThat(loanApplicationsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoanApplications() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList
        restLoanApplicationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanApplications.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationNo").value(hasItem(DEFAULT_APPLICATION_NO)))
            .andExpect(jsonPath("$.[*].demandAmount").value(hasItem(DEFAULT_DEMAND_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanPurpose").value(hasItem(DEFAULT_LOAN_PURPOSE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].demandedLandAreaInHector").value(hasItem(DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR)))
            .andExpect(jsonPath("$.[*].seasonOfCropLoan").value(hasItem(DEFAULT_SEASON_OF_CROP_LOAN)))
            .andExpect(jsonPath("$.[*].loanSteps").value(hasItem(DEFAULT_LOAN_STEPS.toString())))
            .andExpect(jsonPath("$.[*].isInsured").value(hasItem(DEFAULT_IS_INSURED.booleanValue())))
            .andExpect(jsonPath("$.[*].loanBenefitingArea").value(hasItem(DEFAULT_LOAN_BENEFITING_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].costOfInvestment").value(hasItem(DEFAULT_COST_OF_INVESTMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].mortgageDeedNo").value(hasItem(DEFAULT_MORTGAGE_DEED_NO.intValue())))
            .andExpect(jsonPath("$.[*].mortgageDate").value(hasItem(DEFAULT_MORTGAGE_DATE.toString())))
            .andExpect(jsonPath("$.[*].extentMorgageValue").value(hasItem(DEFAULT_EXTENT_MORGAGE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].downPaymentAmt").value(hasItem(DEFAULT_DOWN_PAYMENT_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].ltvRatio").value(hasItem(DEFAULT_LTV_RATIO.doubleValue())))
            .andExpect(jsonPath("$.[*].processingFee").value(hasItem(DEFAULT_PROCESSING_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].penalInterest").value(hasItem(DEFAULT_PENAL_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].moratorium").value(hasItem(DEFAULT_MORATORIUM)))
            .andExpect(jsonPath("$.[*].roi").value(hasItem(DEFAULT_ROI.doubleValue())))
            .andExpect(jsonPath("$.[*].commityAmt").value(hasItem(DEFAULT_COMMITY_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].commityRoi").value(hasItem(DEFAULT_COMMITY_ROI.doubleValue())))
            .andExpect(jsonPath("$.[*].sectionAmt").value(hasItem(DEFAULT_SECTION_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].senctionRoi").value(hasItem(DEFAULT_SENCTION_ROI.doubleValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].assignedTo").value(hasItem(DEFAULT_ASSIGNED_TO.intValue())))
            .andExpect(jsonPath("$.[*].assignedFrom").value(hasItem(DEFAULT_ASSIGNED_FROM.intValue())))
            .andExpect(jsonPath("$.[*].securityDocUrl").value(hasItem(DEFAULT_SECURITY_DOC_URL)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)))
            .andExpect(jsonPath("$.[*].freeField7").value(hasItem(DEFAULT_FREE_FIELD_7)));
    }

    @Test
    @Transactional
    void getLoanApplications() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get the loanApplications
        restLoanApplicationsMockMvc
            .perform(get(ENTITY_API_URL_ID, loanApplications.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loanApplications.getId().intValue()))
            .andExpect(jsonPath("$.applicationNo").value(DEFAULT_APPLICATION_NO))
            .andExpect(jsonPath("$.demandAmount").value(DEFAULT_DEMAND_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.loanPurpose").value(DEFAULT_LOAN_PURPOSE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.demandedLandAreaInHector").value(DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR))
            .andExpect(jsonPath("$.seasonOfCropLoan").value(DEFAULT_SEASON_OF_CROP_LOAN))
            .andExpect(jsonPath("$.loanSteps").value(DEFAULT_LOAN_STEPS.toString()))
            .andExpect(jsonPath("$.isInsured").value(DEFAULT_IS_INSURED.booleanValue()))
            .andExpect(jsonPath("$.loanBenefitingArea").value(DEFAULT_LOAN_BENEFITING_AREA.doubleValue()))
            .andExpect(jsonPath("$.costOfInvestment").value(DEFAULT_COST_OF_INVESTMENT.doubleValue()))
            .andExpect(jsonPath("$.mortgageDeedNo").value(DEFAULT_MORTGAGE_DEED_NO.intValue()))
            .andExpect(jsonPath("$.mortgageDate").value(DEFAULT_MORTGAGE_DATE.toString()))
            .andExpect(jsonPath("$.extentMorgageValue").value(DEFAULT_EXTENT_MORGAGE_VALUE.doubleValue()))
            .andExpect(jsonPath("$.downPaymentAmt").value(DEFAULT_DOWN_PAYMENT_AMT.doubleValue()))
            .andExpect(jsonPath("$.ltvRatio").value(DEFAULT_LTV_RATIO.doubleValue()))
            .andExpect(jsonPath("$.processingFee").value(DEFAULT_PROCESSING_FEE.doubleValue()))
            .andExpect(jsonPath("$.penalInterest").value(DEFAULT_PENAL_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.moratorium").value(DEFAULT_MORATORIUM))
            .andExpect(jsonPath("$.roi").value(DEFAULT_ROI.doubleValue()))
            .andExpect(jsonPath("$.commityAmt").value(DEFAULT_COMMITY_AMT.doubleValue()))
            .andExpect(jsonPath("$.commityRoi").value(DEFAULT_COMMITY_ROI.doubleValue()))
            .andExpect(jsonPath("$.sectionAmt").value(DEFAULT_SECTION_AMT.doubleValue()))
            .andExpect(jsonPath("$.senctionRoi").value(DEFAULT_SENCTION_ROI.doubleValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.assignedTo").value(DEFAULT_ASSIGNED_TO.intValue()))
            .andExpect(jsonPath("$.assignedFrom").value(DEFAULT_ASSIGNED_FROM.intValue()))
            .andExpect(jsonPath("$.securityDocUrl").value(DEFAULT_SECURITY_DOC_URL))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.freeField5").value(DEFAULT_FREE_FIELD_5))
            .andExpect(jsonPath("$.freeField6").value(DEFAULT_FREE_FIELD_6))
            .andExpect(jsonPath("$.freeField7").value(DEFAULT_FREE_FIELD_7));
    }

    @Test
    @Transactional
    void getLoanApplicationsByIdFiltering() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        Long id = loanApplications.getId();

        defaultLoanApplicationsShouldBeFound("id.equals=" + id);
        defaultLoanApplicationsShouldNotBeFound("id.notEquals=" + id);

        defaultLoanApplicationsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLoanApplicationsShouldNotBeFound("id.greaterThan=" + id);

        defaultLoanApplicationsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLoanApplicationsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByApplicationNoIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where applicationNo equals to DEFAULT_APPLICATION_NO
        defaultLoanApplicationsShouldBeFound("applicationNo.equals=" + DEFAULT_APPLICATION_NO);

        // Get all the loanApplicationsList where applicationNo equals to UPDATED_APPLICATION_NO
        defaultLoanApplicationsShouldNotBeFound("applicationNo.equals=" + UPDATED_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByApplicationNoIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where applicationNo in DEFAULT_APPLICATION_NO or UPDATED_APPLICATION_NO
        defaultLoanApplicationsShouldBeFound("applicationNo.in=" + DEFAULT_APPLICATION_NO + "," + UPDATED_APPLICATION_NO);

        // Get all the loanApplicationsList where applicationNo equals to UPDATED_APPLICATION_NO
        defaultLoanApplicationsShouldNotBeFound("applicationNo.in=" + UPDATED_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByApplicationNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where applicationNo is not null
        defaultLoanApplicationsShouldBeFound("applicationNo.specified=true");

        // Get all the loanApplicationsList where applicationNo is null
        defaultLoanApplicationsShouldNotBeFound("applicationNo.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByApplicationNoContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where applicationNo contains DEFAULT_APPLICATION_NO
        defaultLoanApplicationsShouldBeFound("applicationNo.contains=" + DEFAULT_APPLICATION_NO);

        // Get all the loanApplicationsList where applicationNo contains UPDATED_APPLICATION_NO
        defaultLoanApplicationsShouldNotBeFound("applicationNo.contains=" + UPDATED_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByApplicationNoNotContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where applicationNo does not contain DEFAULT_APPLICATION_NO
        defaultLoanApplicationsShouldNotBeFound("applicationNo.doesNotContain=" + DEFAULT_APPLICATION_NO);

        // Get all the loanApplicationsList where applicationNo does not contain UPDATED_APPLICATION_NO
        defaultLoanApplicationsShouldBeFound("applicationNo.doesNotContain=" + UPDATED_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDemandAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where demandAmount equals to DEFAULT_DEMAND_AMOUNT
        defaultLoanApplicationsShouldBeFound("demandAmount.equals=" + DEFAULT_DEMAND_AMOUNT);

        // Get all the loanApplicationsList where demandAmount equals to UPDATED_DEMAND_AMOUNT
        defaultLoanApplicationsShouldNotBeFound("demandAmount.equals=" + UPDATED_DEMAND_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDemandAmountIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where demandAmount in DEFAULT_DEMAND_AMOUNT or UPDATED_DEMAND_AMOUNT
        defaultLoanApplicationsShouldBeFound("demandAmount.in=" + DEFAULT_DEMAND_AMOUNT + "," + UPDATED_DEMAND_AMOUNT);

        // Get all the loanApplicationsList where demandAmount equals to UPDATED_DEMAND_AMOUNT
        defaultLoanApplicationsShouldNotBeFound("demandAmount.in=" + UPDATED_DEMAND_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDemandAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where demandAmount is not null
        defaultLoanApplicationsShouldBeFound("demandAmount.specified=true");

        // Get all the loanApplicationsList where demandAmount is null
        defaultLoanApplicationsShouldNotBeFound("demandAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDemandAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where demandAmount is greater than or equal to DEFAULT_DEMAND_AMOUNT
        defaultLoanApplicationsShouldBeFound("demandAmount.greaterThanOrEqual=" + DEFAULT_DEMAND_AMOUNT);

        // Get all the loanApplicationsList where demandAmount is greater than or equal to UPDATED_DEMAND_AMOUNT
        defaultLoanApplicationsShouldNotBeFound("demandAmount.greaterThanOrEqual=" + UPDATED_DEMAND_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDemandAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where demandAmount is less than or equal to DEFAULT_DEMAND_AMOUNT
        defaultLoanApplicationsShouldBeFound("demandAmount.lessThanOrEqual=" + DEFAULT_DEMAND_AMOUNT);

        // Get all the loanApplicationsList where demandAmount is less than or equal to SMALLER_DEMAND_AMOUNT
        defaultLoanApplicationsShouldNotBeFound("demandAmount.lessThanOrEqual=" + SMALLER_DEMAND_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDemandAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where demandAmount is less than DEFAULT_DEMAND_AMOUNT
        defaultLoanApplicationsShouldNotBeFound("demandAmount.lessThan=" + DEFAULT_DEMAND_AMOUNT);

        // Get all the loanApplicationsList where demandAmount is less than UPDATED_DEMAND_AMOUNT
        defaultLoanApplicationsShouldBeFound("demandAmount.lessThan=" + UPDATED_DEMAND_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDemandAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where demandAmount is greater than DEFAULT_DEMAND_AMOUNT
        defaultLoanApplicationsShouldNotBeFound("demandAmount.greaterThan=" + DEFAULT_DEMAND_AMOUNT);

        // Get all the loanApplicationsList where demandAmount is greater than SMALLER_DEMAND_AMOUNT
        defaultLoanApplicationsShouldBeFound("demandAmount.greaterThan=" + SMALLER_DEMAND_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLoanPurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where loanPurpose equals to DEFAULT_LOAN_PURPOSE
        defaultLoanApplicationsShouldBeFound("loanPurpose.equals=" + DEFAULT_LOAN_PURPOSE);

        // Get all the loanApplicationsList where loanPurpose equals to UPDATED_LOAN_PURPOSE
        defaultLoanApplicationsShouldNotBeFound("loanPurpose.equals=" + UPDATED_LOAN_PURPOSE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLoanPurposeIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where loanPurpose in DEFAULT_LOAN_PURPOSE or UPDATED_LOAN_PURPOSE
        defaultLoanApplicationsShouldBeFound("loanPurpose.in=" + DEFAULT_LOAN_PURPOSE + "," + UPDATED_LOAN_PURPOSE);

        // Get all the loanApplicationsList where loanPurpose equals to UPDATED_LOAN_PURPOSE
        defaultLoanApplicationsShouldNotBeFound("loanPurpose.in=" + UPDATED_LOAN_PURPOSE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLoanPurposeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where loanPurpose is not null
        defaultLoanApplicationsShouldBeFound("loanPurpose.specified=true");

        // Get all the loanApplicationsList where loanPurpose is null
        defaultLoanApplicationsShouldNotBeFound("loanPurpose.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLoanPurposeContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where loanPurpose contains DEFAULT_LOAN_PURPOSE
        defaultLoanApplicationsShouldBeFound("loanPurpose.contains=" + DEFAULT_LOAN_PURPOSE);

        // Get all the loanApplicationsList where loanPurpose contains UPDATED_LOAN_PURPOSE
        defaultLoanApplicationsShouldNotBeFound("loanPurpose.contains=" + UPDATED_LOAN_PURPOSE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLoanPurposeNotContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where loanPurpose does not contain DEFAULT_LOAN_PURPOSE
        defaultLoanApplicationsShouldNotBeFound("loanPurpose.doesNotContain=" + DEFAULT_LOAN_PURPOSE);

        // Get all the loanApplicationsList where loanPurpose does not contain UPDATED_LOAN_PURPOSE
        defaultLoanApplicationsShouldBeFound("loanPurpose.doesNotContain=" + UPDATED_LOAN_PURPOSE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where status equals to DEFAULT_STATUS
        defaultLoanApplicationsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the loanApplicationsList where status equals to UPDATED_STATUS
        defaultLoanApplicationsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLoanApplicationsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the loanApplicationsList where status equals to UPDATED_STATUS
        defaultLoanApplicationsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where status is not null
        defaultLoanApplicationsShouldBeFound("status.specified=true");

        // Get all the loanApplicationsList where status is null
        defaultLoanApplicationsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDemandedLandAreaInHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where demandedLandAreaInHector equals to DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanApplicationsShouldBeFound("demandedLandAreaInHector.equals=" + DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR);

        // Get all the loanApplicationsList where demandedLandAreaInHector equals to UPDATED_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanApplicationsShouldNotBeFound("demandedLandAreaInHector.equals=" + UPDATED_DEMANDED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDemandedLandAreaInHectorIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where demandedLandAreaInHector in DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR or UPDATED_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanApplicationsShouldBeFound(
            "demandedLandAreaInHector.in=" + DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR + "," + UPDATED_DEMANDED_LAND_AREA_IN_HECTOR
        );

        // Get all the loanApplicationsList where demandedLandAreaInHector equals to UPDATED_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanApplicationsShouldNotBeFound("demandedLandAreaInHector.in=" + UPDATED_DEMANDED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDemandedLandAreaInHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where demandedLandAreaInHector is not null
        defaultLoanApplicationsShouldBeFound("demandedLandAreaInHector.specified=true");

        // Get all the loanApplicationsList where demandedLandAreaInHector is null
        defaultLoanApplicationsShouldNotBeFound("demandedLandAreaInHector.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDemandedLandAreaInHectorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where demandedLandAreaInHector is greater than or equal to DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanApplicationsShouldBeFound("demandedLandAreaInHector.greaterThanOrEqual=" + DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR);

        // Get all the loanApplicationsList where demandedLandAreaInHector is greater than or equal to UPDATED_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanApplicationsShouldNotBeFound("demandedLandAreaInHector.greaterThanOrEqual=" + UPDATED_DEMANDED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDemandedLandAreaInHectorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where demandedLandAreaInHector is less than or equal to DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanApplicationsShouldBeFound("demandedLandAreaInHector.lessThanOrEqual=" + DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR);

        // Get all the loanApplicationsList where demandedLandAreaInHector is less than or equal to SMALLER_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanApplicationsShouldNotBeFound("demandedLandAreaInHector.lessThanOrEqual=" + SMALLER_DEMANDED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDemandedLandAreaInHectorIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where demandedLandAreaInHector is less than DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanApplicationsShouldNotBeFound("demandedLandAreaInHector.lessThan=" + DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR);

        // Get all the loanApplicationsList where demandedLandAreaInHector is less than UPDATED_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanApplicationsShouldBeFound("demandedLandAreaInHector.lessThan=" + UPDATED_DEMANDED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDemandedLandAreaInHectorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where demandedLandAreaInHector is greater than DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanApplicationsShouldNotBeFound("demandedLandAreaInHector.greaterThan=" + DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR);

        // Get all the loanApplicationsList where demandedLandAreaInHector is greater than SMALLER_DEMANDED_LAND_AREA_IN_HECTOR
        defaultLoanApplicationsShouldBeFound("demandedLandAreaInHector.greaterThan=" + SMALLER_DEMANDED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySeasonOfCropLoanIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where seasonOfCropLoan equals to DEFAULT_SEASON_OF_CROP_LOAN
        defaultLoanApplicationsShouldBeFound("seasonOfCropLoan.equals=" + DEFAULT_SEASON_OF_CROP_LOAN);

        // Get all the loanApplicationsList where seasonOfCropLoan equals to UPDATED_SEASON_OF_CROP_LOAN
        defaultLoanApplicationsShouldNotBeFound("seasonOfCropLoan.equals=" + UPDATED_SEASON_OF_CROP_LOAN);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySeasonOfCropLoanIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where seasonOfCropLoan in DEFAULT_SEASON_OF_CROP_LOAN or UPDATED_SEASON_OF_CROP_LOAN
        defaultLoanApplicationsShouldBeFound("seasonOfCropLoan.in=" + DEFAULT_SEASON_OF_CROP_LOAN + "," + UPDATED_SEASON_OF_CROP_LOAN);

        // Get all the loanApplicationsList where seasonOfCropLoan equals to UPDATED_SEASON_OF_CROP_LOAN
        defaultLoanApplicationsShouldNotBeFound("seasonOfCropLoan.in=" + UPDATED_SEASON_OF_CROP_LOAN);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySeasonOfCropLoanIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where seasonOfCropLoan is not null
        defaultLoanApplicationsShouldBeFound("seasonOfCropLoan.specified=true");

        // Get all the loanApplicationsList where seasonOfCropLoan is null
        defaultLoanApplicationsShouldNotBeFound("seasonOfCropLoan.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySeasonOfCropLoanContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where seasonOfCropLoan contains DEFAULT_SEASON_OF_CROP_LOAN
        defaultLoanApplicationsShouldBeFound("seasonOfCropLoan.contains=" + DEFAULT_SEASON_OF_CROP_LOAN);

        // Get all the loanApplicationsList where seasonOfCropLoan contains UPDATED_SEASON_OF_CROP_LOAN
        defaultLoanApplicationsShouldNotBeFound("seasonOfCropLoan.contains=" + UPDATED_SEASON_OF_CROP_LOAN);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySeasonOfCropLoanNotContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where seasonOfCropLoan does not contain DEFAULT_SEASON_OF_CROP_LOAN
        defaultLoanApplicationsShouldNotBeFound("seasonOfCropLoan.doesNotContain=" + DEFAULT_SEASON_OF_CROP_LOAN);

        // Get all the loanApplicationsList where seasonOfCropLoan does not contain UPDATED_SEASON_OF_CROP_LOAN
        defaultLoanApplicationsShouldBeFound("seasonOfCropLoan.doesNotContain=" + UPDATED_SEASON_OF_CROP_LOAN);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLoanStepsIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where loanSteps equals to DEFAULT_LOAN_STEPS
        defaultLoanApplicationsShouldBeFound("loanSteps.equals=" + DEFAULT_LOAN_STEPS);

        // Get all the loanApplicationsList where loanSteps equals to UPDATED_LOAN_STEPS
        defaultLoanApplicationsShouldNotBeFound("loanSteps.equals=" + UPDATED_LOAN_STEPS);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLoanStepsIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where loanSteps in DEFAULT_LOAN_STEPS or UPDATED_LOAN_STEPS
        defaultLoanApplicationsShouldBeFound("loanSteps.in=" + DEFAULT_LOAN_STEPS + "," + UPDATED_LOAN_STEPS);

        // Get all the loanApplicationsList where loanSteps equals to UPDATED_LOAN_STEPS
        defaultLoanApplicationsShouldNotBeFound("loanSteps.in=" + UPDATED_LOAN_STEPS);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLoanStepsIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where loanSteps is not null
        defaultLoanApplicationsShouldBeFound("loanSteps.specified=true");

        // Get all the loanApplicationsList where loanSteps is null
        defaultLoanApplicationsShouldNotBeFound("loanSteps.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByIsInsuredIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where isInsured equals to DEFAULT_IS_INSURED
        defaultLoanApplicationsShouldBeFound("isInsured.equals=" + DEFAULT_IS_INSURED);

        // Get all the loanApplicationsList where isInsured equals to UPDATED_IS_INSURED
        defaultLoanApplicationsShouldNotBeFound("isInsured.equals=" + UPDATED_IS_INSURED);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByIsInsuredIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where isInsured in DEFAULT_IS_INSURED or UPDATED_IS_INSURED
        defaultLoanApplicationsShouldBeFound("isInsured.in=" + DEFAULT_IS_INSURED + "," + UPDATED_IS_INSURED);

        // Get all the loanApplicationsList where isInsured equals to UPDATED_IS_INSURED
        defaultLoanApplicationsShouldNotBeFound("isInsured.in=" + UPDATED_IS_INSURED);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByIsInsuredIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where isInsured is not null
        defaultLoanApplicationsShouldBeFound("isInsured.specified=true");

        // Get all the loanApplicationsList where isInsured is null
        defaultLoanApplicationsShouldNotBeFound("isInsured.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLoanBenefitingAreaIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where loanBenefitingArea equals to DEFAULT_LOAN_BENEFITING_AREA
        defaultLoanApplicationsShouldBeFound("loanBenefitingArea.equals=" + DEFAULT_LOAN_BENEFITING_AREA);

        // Get all the loanApplicationsList where loanBenefitingArea equals to UPDATED_LOAN_BENEFITING_AREA
        defaultLoanApplicationsShouldNotBeFound("loanBenefitingArea.equals=" + UPDATED_LOAN_BENEFITING_AREA);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLoanBenefitingAreaIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where loanBenefitingArea in DEFAULT_LOAN_BENEFITING_AREA or UPDATED_LOAN_BENEFITING_AREA
        defaultLoanApplicationsShouldBeFound("loanBenefitingArea.in=" + DEFAULT_LOAN_BENEFITING_AREA + "," + UPDATED_LOAN_BENEFITING_AREA);

        // Get all the loanApplicationsList where loanBenefitingArea equals to UPDATED_LOAN_BENEFITING_AREA
        defaultLoanApplicationsShouldNotBeFound("loanBenefitingArea.in=" + UPDATED_LOAN_BENEFITING_AREA);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLoanBenefitingAreaIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where loanBenefitingArea is not null
        defaultLoanApplicationsShouldBeFound("loanBenefitingArea.specified=true");

        // Get all the loanApplicationsList where loanBenefitingArea is null
        defaultLoanApplicationsShouldNotBeFound("loanBenefitingArea.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLoanBenefitingAreaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where loanBenefitingArea is greater than or equal to DEFAULT_LOAN_BENEFITING_AREA
        defaultLoanApplicationsShouldBeFound("loanBenefitingArea.greaterThanOrEqual=" + DEFAULT_LOAN_BENEFITING_AREA);

        // Get all the loanApplicationsList where loanBenefitingArea is greater than or equal to UPDATED_LOAN_BENEFITING_AREA
        defaultLoanApplicationsShouldNotBeFound("loanBenefitingArea.greaterThanOrEqual=" + UPDATED_LOAN_BENEFITING_AREA);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLoanBenefitingAreaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where loanBenefitingArea is less than or equal to DEFAULT_LOAN_BENEFITING_AREA
        defaultLoanApplicationsShouldBeFound("loanBenefitingArea.lessThanOrEqual=" + DEFAULT_LOAN_BENEFITING_AREA);

        // Get all the loanApplicationsList where loanBenefitingArea is less than or equal to SMALLER_LOAN_BENEFITING_AREA
        defaultLoanApplicationsShouldNotBeFound("loanBenefitingArea.lessThanOrEqual=" + SMALLER_LOAN_BENEFITING_AREA);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLoanBenefitingAreaIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where loanBenefitingArea is less than DEFAULT_LOAN_BENEFITING_AREA
        defaultLoanApplicationsShouldNotBeFound("loanBenefitingArea.lessThan=" + DEFAULT_LOAN_BENEFITING_AREA);

        // Get all the loanApplicationsList where loanBenefitingArea is less than UPDATED_LOAN_BENEFITING_AREA
        defaultLoanApplicationsShouldBeFound("loanBenefitingArea.lessThan=" + UPDATED_LOAN_BENEFITING_AREA);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLoanBenefitingAreaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where loanBenefitingArea is greater than DEFAULT_LOAN_BENEFITING_AREA
        defaultLoanApplicationsShouldNotBeFound("loanBenefitingArea.greaterThan=" + DEFAULT_LOAN_BENEFITING_AREA);

        // Get all the loanApplicationsList where loanBenefitingArea is greater than SMALLER_LOAN_BENEFITING_AREA
        defaultLoanApplicationsShouldBeFound("loanBenefitingArea.greaterThan=" + SMALLER_LOAN_BENEFITING_AREA);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCostOfInvestmentIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where costOfInvestment equals to DEFAULT_COST_OF_INVESTMENT
        defaultLoanApplicationsShouldBeFound("costOfInvestment.equals=" + DEFAULT_COST_OF_INVESTMENT);

        // Get all the loanApplicationsList where costOfInvestment equals to UPDATED_COST_OF_INVESTMENT
        defaultLoanApplicationsShouldNotBeFound("costOfInvestment.equals=" + UPDATED_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCostOfInvestmentIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where costOfInvestment in DEFAULT_COST_OF_INVESTMENT or UPDATED_COST_OF_INVESTMENT
        defaultLoanApplicationsShouldBeFound("costOfInvestment.in=" + DEFAULT_COST_OF_INVESTMENT + "," + UPDATED_COST_OF_INVESTMENT);

        // Get all the loanApplicationsList where costOfInvestment equals to UPDATED_COST_OF_INVESTMENT
        defaultLoanApplicationsShouldNotBeFound("costOfInvestment.in=" + UPDATED_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCostOfInvestmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where costOfInvestment is not null
        defaultLoanApplicationsShouldBeFound("costOfInvestment.specified=true");

        // Get all the loanApplicationsList where costOfInvestment is null
        defaultLoanApplicationsShouldNotBeFound("costOfInvestment.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCostOfInvestmentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where costOfInvestment is greater than or equal to DEFAULT_COST_OF_INVESTMENT
        defaultLoanApplicationsShouldBeFound("costOfInvestment.greaterThanOrEqual=" + DEFAULT_COST_OF_INVESTMENT);

        // Get all the loanApplicationsList where costOfInvestment is greater than or equal to UPDATED_COST_OF_INVESTMENT
        defaultLoanApplicationsShouldNotBeFound("costOfInvestment.greaterThanOrEqual=" + UPDATED_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCostOfInvestmentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where costOfInvestment is less than or equal to DEFAULT_COST_OF_INVESTMENT
        defaultLoanApplicationsShouldBeFound("costOfInvestment.lessThanOrEqual=" + DEFAULT_COST_OF_INVESTMENT);

        // Get all the loanApplicationsList where costOfInvestment is less than or equal to SMALLER_COST_OF_INVESTMENT
        defaultLoanApplicationsShouldNotBeFound("costOfInvestment.lessThanOrEqual=" + SMALLER_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCostOfInvestmentIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where costOfInvestment is less than DEFAULT_COST_OF_INVESTMENT
        defaultLoanApplicationsShouldNotBeFound("costOfInvestment.lessThan=" + DEFAULT_COST_OF_INVESTMENT);

        // Get all the loanApplicationsList where costOfInvestment is less than UPDATED_COST_OF_INVESTMENT
        defaultLoanApplicationsShouldBeFound("costOfInvestment.lessThan=" + UPDATED_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCostOfInvestmentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where costOfInvestment is greater than DEFAULT_COST_OF_INVESTMENT
        defaultLoanApplicationsShouldNotBeFound("costOfInvestment.greaterThan=" + DEFAULT_COST_OF_INVESTMENT);

        // Get all the loanApplicationsList where costOfInvestment is greater than SMALLER_COST_OF_INVESTMENT
        defaultLoanApplicationsShouldBeFound("costOfInvestment.greaterThan=" + SMALLER_COST_OF_INVESTMENT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMortgageDeedNoIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where mortgageDeedNo equals to DEFAULT_MORTGAGE_DEED_NO
        defaultLoanApplicationsShouldBeFound("mortgageDeedNo.equals=" + DEFAULT_MORTGAGE_DEED_NO);

        // Get all the loanApplicationsList where mortgageDeedNo equals to UPDATED_MORTGAGE_DEED_NO
        defaultLoanApplicationsShouldNotBeFound("mortgageDeedNo.equals=" + UPDATED_MORTGAGE_DEED_NO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMortgageDeedNoIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where mortgageDeedNo in DEFAULT_MORTGAGE_DEED_NO or UPDATED_MORTGAGE_DEED_NO
        defaultLoanApplicationsShouldBeFound("mortgageDeedNo.in=" + DEFAULT_MORTGAGE_DEED_NO + "," + UPDATED_MORTGAGE_DEED_NO);

        // Get all the loanApplicationsList where mortgageDeedNo equals to UPDATED_MORTGAGE_DEED_NO
        defaultLoanApplicationsShouldNotBeFound("mortgageDeedNo.in=" + UPDATED_MORTGAGE_DEED_NO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMortgageDeedNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where mortgageDeedNo is not null
        defaultLoanApplicationsShouldBeFound("mortgageDeedNo.specified=true");

        // Get all the loanApplicationsList where mortgageDeedNo is null
        defaultLoanApplicationsShouldNotBeFound("mortgageDeedNo.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMortgageDeedNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where mortgageDeedNo is greater than or equal to DEFAULT_MORTGAGE_DEED_NO
        defaultLoanApplicationsShouldBeFound("mortgageDeedNo.greaterThanOrEqual=" + DEFAULT_MORTGAGE_DEED_NO);

        // Get all the loanApplicationsList where mortgageDeedNo is greater than or equal to UPDATED_MORTGAGE_DEED_NO
        defaultLoanApplicationsShouldNotBeFound("mortgageDeedNo.greaterThanOrEqual=" + UPDATED_MORTGAGE_DEED_NO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMortgageDeedNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where mortgageDeedNo is less than or equal to DEFAULT_MORTGAGE_DEED_NO
        defaultLoanApplicationsShouldBeFound("mortgageDeedNo.lessThanOrEqual=" + DEFAULT_MORTGAGE_DEED_NO);

        // Get all the loanApplicationsList where mortgageDeedNo is less than or equal to SMALLER_MORTGAGE_DEED_NO
        defaultLoanApplicationsShouldNotBeFound("mortgageDeedNo.lessThanOrEqual=" + SMALLER_MORTGAGE_DEED_NO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMortgageDeedNoIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where mortgageDeedNo is less than DEFAULT_MORTGAGE_DEED_NO
        defaultLoanApplicationsShouldNotBeFound("mortgageDeedNo.lessThan=" + DEFAULT_MORTGAGE_DEED_NO);

        // Get all the loanApplicationsList where mortgageDeedNo is less than UPDATED_MORTGAGE_DEED_NO
        defaultLoanApplicationsShouldBeFound("mortgageDeedNo.lessThan=" + UPDATED_MORTGAGE_DEED_NO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMortgageDeedNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where mortgageDeedNo is greater than DEFAULT_MORTGAGE_DEED_NO
        defaultLoanApplicationsShouldNotBeFound("mortgageDeedNo.greaterThan=" + DEFAULT_MORTGAGE_DEED_NO);

        // Get all the loanApplicationsList where mortgageDeedNo is greater than SMALLER_MORTGAGE_DEED_NO
        defaultLoanApplicationsShouldBeFound("mortgageDeedNo.greaterThan=" + SMALLER_MORTGAGE_DEED_NO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMortgageDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where mortgageDate equals to DEFAULT_MORTGAGE_DATE
        defaultLoanApplicationsShouldBeFound("mortgageDate.equals=" + DEFAULT_MORTGAGE_DATE);

        // Get all the loanApplicationsList where mortgageDate equals to UPDATED_MORTGAGE_DATE
        defaultLoanApplicationsShouldNotBeFound("mortgageDate.equals=" + UPDATED_MORTGAGE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMortgageDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where mortgageDate in DEFAULT_MORTGAGE_DATE or UPDATED_MORTGAGE_DATE
        defaultLoanApplicationsShouldBeFound("mortgageDate.in=" + DEFAULT_MORTGAGE_DATE + "," + UPDATED_MORTGAGE_DATE);

        // Get all the loanApplicationsList where mortgageDate equals to UPDATED_MORTGAGE_DATE
        defaultLoanApplicationsShouldNotBeFound("mortgageDate.in=" + UPDATED_MORTGAGE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMortgageDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where mortgageDate is not null
        defaultLoanApplicationsShouldBeFound("mortgageDate.specified=true");

        // Get all the loanApplicationsList where mortgageDate is null
        defaultLoanApplicationsShouldNotBeFound("mortgageDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByExtentMorgageValueIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where extentMorgageValue equals to DEFAULT_EXTENT_MORGAGE_VALUE
        defaultLoanApplicationsShouldBeFound("extentMorgageValue.equals=" + DEFAULT_EXTENT_MORGAGE_VALUE);

        // Get all the loanApplicationsList where extentMorgageValue equals to UPDATED_EXTENT_MORGAGE_VALUE
        defaultLoanApplicationsShouldNotBeFound("extentMorgageValue.equals=" + UPDATED_EXTENT_MORGAGE_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByExtentMorgageValueIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where extentMorgageValue in DEFAULT_EXTENT_MORGAGE_VALUE or UPDATED_EXTENT_MORGAGE_VALUE
        defaultLoanApplicationsShouldBeFound("extentMorgageValue.in=" + DEFAULT_EXTENT_MORGAGE_VALUE + "," + UPDATED_EXTENT_MORGAGE_VALUE);

        // Get all the loanApplicationsList where extentMorgageValue equals to UPDATED_EXTENT_MORGAGE_VALUE
        defaultLoanApplicationsShouldNotBeFound("extentMorgageValue.in=" + UPDATED_EXTENT_MORGAGE_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByExtentMorgageValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where extentMorgageValue is not null
        defaultLoanApplicationsShouldBeFound("extentMorgageValue.specified=true");

        // Get all the loanApplicationsList where extentMorgageValue is null
        defaultLoanApplicationsShouldNotBeFound("extentMorgageValue.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByExtentMorgageValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where extentMorgageValue is greater than or equal to DEFAULT_EXTENT_MORGAGE_VALUE
        defaultLoanApplicationsShouldBeFound("extentMorgageValue.greaterThanOrEqual=" + DEFAULT_EXTENT_MORGAGE_VALUE);

        // Get all the loanApplicationsList where extentMorgageValue is greater than or equal to UPDATED_EXTENT_MORGAGE_VALUE
        defaultLoanApplicationsShouldNotBeFound("extentMorgageValue.greaterThanOrEqual=" + UPDATED_EXTENT_MORGAGE_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByExtentMorgageValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where extentMorgageValue is less than or equal to DEFAULT_EXTENT_MORGAGE_VALUE
        defaultLoanApplicationsShouldBeFound("extentMorgageValue.lessThanOrEqual=" + DEFAULT_EXTENT_MORGAGE_VALUE);

        // Get all the loanApplicationsList where extentMorgageValue is less than or equal to SMALLER_EXTENT_MORGAGE_VALUE
        defaultLoanApplicationsShouldNotBeFound("extentMorgageValue.lessThanOrEqual=" + SMALLER_EXTENT_MORGAGE_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByExtentMorgageValueIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where extentMorgageValue is less than DEFAULT_EXTENT_MORGAGE_VALUE
        defaultLoanApplicationsShouldNotBeFound("extentMorgageValue.lessThan=" + DEFAULT_EXTENT_MORGAGE_VALUE);

        // Get all the loanApplicationsList where extentMorgageValue is less than UPDATED_EXTENT_MORGAGE_VALUE
        defaultLoanApplicationsShouldBeFound("extentMorgageValue.lessThan=" + UPDATED_EXTENT_MORGAGE_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByExtentMorgageValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where extentMorgageValue is greater than DEFAULT_EXTENT_MORGAGE_VALUE
        defaultLoanApplicationsShouldNotBeFound("extentMorgageValue.greaterThan=" + DEFAULT_EXTENT_MORGAGE_VALUE);

        // Get all the loanApplicationsList where extentMorgageValue is greater than SMALLER_EXTENT_MORGAGE_VALUE
        defaultLoanApplicationsShouldBeFound("extentMorgageValue.greaterThan=" + SMALLER_EXTENT_MORGAGE_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDownPaymentAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where downPaymentAmt equals to DEFAULT_DOWN_PAYMENT_AMT
        defaultLoanApplicationsShouldBeFound("downPaymentAmt.equals=" + DEFAULT_DOWN_PAYMENT_AMT);

        // Get all the loanApplicationsList where downPaymentAmt equals to UPDATED_DOWN_PAYMENT_AMT
        defaultLoanApplicationsShouldNotBeFound("downPaymentAmt.equals=" + UPDATED_DOWN_PAYMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDownPaymentAmtIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where downPaymentAmt in DEFAULT_DOWN_PAYMENT_AMT or UPDATED_DOWN_PAYMENT_AMT
        defaultLoanApplicationsShouldBeFound("downPaymentAmt.in=" + DEFAULT_DOWN_PAYMENT_AMT + "," + UPDATED_DOWN_PAYMENT_AMT);

        // Get all the loanApplicationsList where downPaymentAmt equals to UPDATED_DOWN_PAYMENT_AMT
        defaultLoanApplicationsShouldNotBeFound("downPaymentAmt.in=" + UPDATED_DOWN_PAYMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDownPaymentAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where downPaymentAmt is not null
        defaultLoanApplicationsShouldBeFound("downPaymentAmt.specified=true");

        // Get all the loanApplicationsList where downPaymentAmt is null
        defaultLoanApplicationsShouldNotBeFound("downPaymentAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDownPaymentAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where downPaymentAmt is greater than or equal to DEFAULT_DOWN_PAYMENT_AMT
        defaultLoanApplicationsShouldBeFound("downPaymentAmt.greaterThanOrEqual=" + DEFAULT_DOWN_PAYMENT_AMT);

        // Get all the loanApplicationsList where downPaymentAmt is greater than or equal to UPDATED_DOWN_PAYMENT_AMT
        defaultLoanApplicationsShouldNotBeFound("downPaymentAmt.greaterThanOrEqual=" + UPDATED_DOWN_PAYMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDownPaymentAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where downPaymentAmt is less than or equal to DEFAULT_DOWN_PAYMENT_AMT
        defaultLoanApplicationsShouldBeFound("downPaymentAmt.lessThanOrEqual=" + DEFAULT_DOWN_PAYMENT_AMT);

        // Get all the loanApplicationsList where downPaymentAmt is less than or equal to SMALLER_DOWN_PAYMENT_AMT
        defaultLoanApplicationsShouldNotBeFound("downPaymentAmt.lessThanOrEqual=" + SMALLER_DOWN_PAYMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDownPaymentAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where downPaymentAmt is less than DEFAULT_DOWN_PAYMENT_AMT
        defaultLoanApplicationsShouldNotBeFound("downPaymentAmt.lessThan=" + DEFAULT_DOWN_PAYMENT_AMT);

        // Get all the loanApplicationsList where downPaymentAmt is less than UPDATED_DOWN_PAYMENT_AMT
        defaultLoanApplicationsShouldBeFound("downPaymentAmt.lessThan=" + UPDATED_DOWN_PAYMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByDownPaymentAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where downPaymentAmt is greater than DEFAULT_DOWN_PAYMENT_AMT
        defaultLoanApplicationsShouldNotBeFound("downPaymentAmt.greaterThan=" + DEFAULT_DOWN_PAYMENT_AMT);

        // Get all the loanApplicationsList where downPaymentAmt is greater than SMALLER_DOWN_PAYMENT_AMT
        defaultLoanApplicationsShouldBeFound("downPaymentAmt.greaterThan=" + SMALLER_DOWN_PAYMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLtvRatioIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where ltvRatio equals to DEFAULT_LTV_RATIO
        defaultLoanApplicationsShouldBeFound("ltvRatio.equals=" + DEFAULT_LTV_RATIO);

        // Get all the loanApplicationsList where ltvRatio equals to UPDATED_LTV_RATIO
        defaultLoanApplicationsShouldNotBeFound("ltvRatio.equals=" + UPDATED_LTV_RATIO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLtvRatioIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where ltvRatio in DEFAULT_LTV_RATIO or UPDATED_LTV_RATIO
        defaultLoanApplicationsShouldBeFound("ltvRatio.in=" + DEFAULT_LTV_RATIO + "," + UPDATED_LTV_RATIO);

        // Get all the loanApplicationsList where ltvRatio equals to UPDATED_LTV_RATIO
        defaultLoanApplicationsShouldNotBeFound("ltvRatio.in=" + UPDATED_LTV_RATIO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLtvRatioIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where ltvRatio is not null
        defaultLoanApplicationsShouldBeFound("ltvRatio.specified=true");

        // Get all the loanApplicationsList where ltvRatio is null
        defaultLoanApplicationsShouldNotBeFound("ltvRatio.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLtvRatioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where ltvRatio is greater than or equal to DEFAULT_LTV_RATIO
        defaultLoanApplicationsShouldBeFound("ltvRatio.greaterThanOrEqual=" + DEFAULT_LTV_RATIO);

        // Get all the loanApplicationsList where ltvRatio is greater than or equal to UPDATED_LTV_RATIO
        defaultLoanApplicationsShouldNotBeFound("ltvRatio.greaterThanOrEqual=" + UPDATED_LTV_RATIO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLtvRatioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where ltvRatio is less than or equal to DEFAULT_LTV_RATIO
        defaultLoanApplicationsShouldBeFound("ltvRatio.lessThanOrEqual=" + DEFAULT_LTV_RATIO);

        // Get all the loanApplicationsList where ltvRatio is less than or equal to SMALLER_LTV_RATIO
        defaultLoanApplicationsShouldNotBeFound("ltvRatio.lessThanOrEqual=" + SMALLER_LTV_RATIO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLtvRatioIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where ltvRatio is less than DEFAULT_LTV_RATIO
        defaultLoanApplicationsShouldNotBeFound("ltvRatio.lessThan=" + DEFAULT_LTV_RATIO);

        // Get all the loanApplicationsList where ltvRatio is less than UPDATED_LTV_RATIO
        defaultLoanApplicationsShouldBeFound("ltvRatio.lessThan=" + UPDATED_LTV_RATIO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLtvRatioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where ltvRatio is greater than DEFAULT_LTV_RATIO
        defaultLoanApplicationsShouldNotBeFound("ltvRatio.greaterThan=" + DEFAULT_LTV_RATIO);

        // Get all the loanApplicationsList where ltvRatio is greater than SMALLER_LTV_RATIO
        defaultLoanApplicationsShouldBeFound("ltvRatio.greaterThan=" + SMALLER_LTV_RATIO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByProcessingFeeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where processingFee equals to DEFAULT_PROCESSING_FEE
        defaultLoanApplicationsShouldBeFound("processingFee.equals=" + DEFAULT_PROCESSING_FEE);

        // Get all the loanApplicationsList where processingFee equals to UPDATED_PROCESSING_FEE
        defaultLoanApplicationsShouldNotBeFound("processingFee.equals=" + UPDATED_PROCESSING_FEE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByProcessingFeeIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where processingFee in DEFAULT_PROCESSING_FEE or UPDATED_PROCESSING_FEE
        defaultLoanApplicationsShouldBeFound("processingFee.in=" + DEFAULT_PROCESSING_FEE + "," + UPDATED_PROCESSING_FEE);

        // Get all the loanApplicationsList where processingFee equals to UPDATED_PROCESSING_FEE
        defaultLoanApplicationsShouldNotBeFound("processingFee.in=" + UPDATED_PROCESSING_FEE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByProcessingFeeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where processingFee is not null
        defaultLoanApplicationsShouldBeFound("processingFee.specified=true");

        // Get all the loanApplicationsList where processingFee is null
        defaultLoanApplicationsShouldNotBeFound("processingFee.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByProcessingFeeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where processingFee is greater than or equal to DEFAULT_PROCESSING_FEE
        defaultLoanApplicationsShouldBeFound("processingFee.greaterThanOrEqual=" + DEFAULT_PROCESSING_FEE);

        // Get all the loanApplicationsList where processingFee is greater than or equal to UPDATED_PROCESSING_FEE
        defaultLoanApplicationsShouldNotBeFound("processingFee.greaterThanOrEqual=" + UPDATED_PROCESSING_FEE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByProcessingFeeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where processingFee is less than or equal to DEFAULT_PROCESSING_FEE
        defaultLoanApplicationsShouldBeFound("processingFee.lessThanOrEqual=" + DEFAULT_PROCESSING_FEE);

        // Get all the loanApplicationsList where processingFee is less than or equal to SMALLER_PROCESSING_FEE
        defaultLoanApplicationsShouldNotBeFound("processingFee.lessThanOrEqual=" + SMALLER_PROCESSING_FEE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByProcessingFeeIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where processingFee is less than DEFAULT_PROCESSING_FEE
        defaultLoanApplicationsShouldNotBeFound("processingFee.lessThan=" + DEFAULT_PROCESSING_FEE);

        // Get all the loanApplicationsList where processingFee is less than UPDATED_PROCESSING_FEE
        defaultLoanApplicationsShouldBeFound("processingFee.lessThan=" + UPDATED_PROCESSING_FEE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByProcessingFeeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where processingFee is greater than DEFAULT_PROCESSING_FEE
        defaultLoanApplicationsShouldNotBeFound("processingFee.greaterThan=" + DEFAULT_PROCESSING_FEE);

        // Get all the loanApplicationsList where processingFee is greater than SMALLER_PROCESSING_FEE
        defaultLoanApplicationsShouldBeFound("processingFee.greaterThan=" + SMALLER_PROCESSING_FEE);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByPenalInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where penalInterest equals to DEFAULT_PENAL_INTEREST
        defaultLoanApplicationsShouldBeFound("penalInterest.equals=" + DEFAULT_PENAL_INTEREST);

        // Get all the loanApplicationsList where penalInterest equals to UPDATED_PENAL_INTEREST
        defaultLoanApplicationsShouldNotBeFound("penalInterest.equals=" + UPDATED_PENAL_INTEREST);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByPenalInterestIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where penalInterest in DEFAULT_PENAL_INTEREST or UPDATED_PENAL_INTEREST
        defaultLoanApplicationsShouldBeFound("penalInterest.in=" + DEFAULT_PENAL_INTEREST + "," + UPDATED_PENAL_INTEREST);

        // Get all the loanApplicationsList where penalInterest equals to UPDATED_PENAL_INTEREST
        defaultLoanApplicationsShouldNotBeFound("penalInterest.in=" + UPDATED_PENAL_INTEREST);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByPenalInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where penalInterest is not null
        defaultLoanApplicationsShouldBeFound("penalInterest.specified=true");

        // Get all the loanApplicationsList where penalInterest is null
        defaultLoanApplicationsShouldNotBeFound("penalInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByPenalInterestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where penalInterest is greater than or equal to DEFAULT_PENAL_INTEREST
        defaultLoanApplicationsShouldBeFound("penalInterest.greaterThanOrEqual=" + DEFAULT_PENAL_INTEREST);

        // Get all the loanApplicationsList where penalInterest is greater than or equal to UPDATED_PENAL_INTEREST
        defaultLoanApplicationsShouldNotBeFound("penalInterest.greaterThanOrEqual=" + UPDATED_PENAL_INTEREST);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByPenalInterestIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where penalInterest is less than or equal to DEFAULT_PENAL_INTEREST
        defaultLoanApplicationsShouldBeFound("penalInterest.lessThanOrEqual=" + DEFAULT_PENAL_INTEREST);

        // Get all the loanApplicationsList where penalInterest is less than or equal to SMALLER_PENAL_INTEREST
        defaultLoanApplicationsShouldNotBeFound("penalInterest.lessThanOrEqual=" + SMALLER_PENAL_INTEREST);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByPenalInterestIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where penalInterest is less than DEFAULT_PENAL_INTEREST
        defaultLoanApplicationsShouldNotBeFound("penalInterest.lessThan=" + DEFAULT_PENAL_INTEREST);

        // Get all the loanApplicationsList where penalInterest is less than UPDATED_PENAL_INTEREST
        defaultLoanApplicationsShouldBeFound("penalInterest.lessThan=" + UPDATED_PENAL_INTEREST);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByPenalInterestIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where penalInterest is greater than DEFAULT_PENAL_INTEREST
        defaultLoanApplicationsShouldNotBeFound("penalInterest.greaterThan=" + DEFAULT_PENAL_INTEREST);

        // Get all the loanApplicationsList where penalInterest is greater than SMALLER_PENAL_INTEREST
        defaultLoanApplicationsShouldBeFound("penalInterest.greaterThan=" + SMALLER_PENAL_INTEREST);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMoratoriumIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where moratorium equals to DEFAULT_MORATORIUM
        defaultLoanApplicationsShouldBeFound("moratorium.equals=" + DEFAULT_MORATORIUM);

        // Get all the loanApplicationsList where moratorium equals to UPDATED_MORATORIUM
        defaultLoanApplicationsShouldNotBeFound("moratorium.equals=" + UPDATED_MORATORIUM);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMoratoriumIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where moratorium in DEFAULT_MORATORIUM or UPDATED_MORATORIUM
        defaultLoanApplicationsShouldBeFound("moratorium.in=" + DEFAULT_MORATORIUM + "," + UPDATED_MORATORIUM);

        // Get all the loanApplicationsList where moratorium equals to UPDATED_MORATORIUM
        defaultLoanApplicationsShouldNotBeFound("moratorium.in=" + UPDATED_MORATORIUM);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMoratoriumIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where moratorium is not null
        defaultLoanApplicationsShouldBeFound("moratorium.specified=true");

        // Get all the loanApplicationsList where moratorium is null
        defaultLoanApplicationsShouldNotBeFound("moratorium.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMoratoriumContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where moratorium contains DEFAULT_MORATORIUM
        defaultLoanApplicationsShouldBeFound("moratorium.contains=" + DEFAULT_MORATORIUM);

        // Get all the loanApplicationsList where moratorium contains UPDATED_MORATORIUM
        defaultLoanApplicationsShouldNotBeFound("moratorium.contains=" + UPDATED_MORATORIUM);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMoratoriumNotContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where moratorium does not contain DEFAULT_MORATORIUM
        defaultLoanApplicationsShouldNotBeFound("moratorium.doesNotContain=" + DEFAULT_MORATORIUM);

        // Get all the loanApplicationsList where moratorium does not contain UPDATED_MORATORIUM
        defaultLoanApplicationsShouldBeFound("moratorium.doesNotContain=" + UPDATED_MORATORIUM);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByRoiIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where roi equals to DEFAULT_ROI
        defaultLoanApplicationsShouldBeFound("roi.equals=" + DEFAULT_ROI);

        // Get all the loanApplicationsList where roi equals to UPDATED_ROI
        defaultLoanApplicationsShouldNotBeFound("roi.equals=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByRoiIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where roi in DEFAULT_ROI or UPDATED_ROI
        defaultLoanApplicationsShouldBeFound("roi.in=" + DEFAULT_ROI + "," + UPDATED_ROI);

        // Get all the loanApplicationsList where roi equals to UPDATED_ROI
        defaultLoanApplicationsShouldNotBeFound("roi.in=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByRoiIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where roi is not null
        defaultLoanApplicationsShouldBeFound("roi.specified=true");

        // Get all the loanApplicationsList where roi is null
        defaultLoanApplicationsShouldNotBeFound("roi.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByRoiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where roi is greater than or equal to DEFAULT_ROI
        defaultLoanApplicationsShouldBeFound("roi.greaterThanOrEqual=" + DEFAULT_ROI);

        // Get all the loanApplicationsList where roi is greater than or equal to UPDATED_ROI
        defaultLoanApplicationsShouldNotBeFound("roi.greaterThanOrEqual=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByRoiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where roi is less than or equal to DEFAULT_ROI
        defaultLoanApplicationsShouldBeFound("roi.lessThanOrEqual=" + DEFAULT_ROI);

        // Get all the loanApplicationsList where roi is less than or equal to SMALLER_ROI
        defaultLoanApplicationsShouldNotBeFound("roi.lessThanOrEqual=" + SMALLER_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByRoiIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where roi is less than DEFAULT_ROI
        defaultLoanApplicationsShouldNotBeFound("roi.lessThan=" + DEFAULT_ROI);

        // Get all the loanApplicationsList where roi is less than UPDATED_ROI
        defaultLoanApplicationsShouldBeFound("roi.lessThan=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByRoiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where roi is greater than DEFAULT_ROI
        defaultLoanApplicationsShouldNotBeFound("roi.greaterThan=" + DEFAULT_ROI);

        // Get all the loanApplicationsList where roi is greater than SMALLER_ROI
        defaultLoanApplicationsShouldBeFound("roi.greaterThan=" + SMALLER_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCommityAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where commityAmt equals to DEFAULT_COMMITY_AMT
        defaultLoanApplicationsShouldBeFound("commityAmt.equals=" + DEFAULT_COMMITY_AMT);

        // Get all the loanApplicationsList where commityAmt equals to UPDATED_COMMITY_AMT
        defaultLoanApplicationsShouldNotBeFound("commityAmt.equals=" + UPDATED_COMMITY_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCommityAmtIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where commityAmt in DEFAULT_COMMITY_AMT or UPDATED_COMMITY_AMT
        defaultLoanApplicationsShouldBeFound("commityAmt.in=" + DEFAULT_COMMITY_AMT + "," + UPDATED_COMMITY_AMT);

        // Get all the loanApplicationsList where commityAmt equals to UPDATED_COMMITY_AMT
        defaultLoanApplicationsShouldNotBeFound("commityAmt.in=" + UPDATED_COMMITY_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCommityAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where commityAmt is not null
        defaultLoanApplicationsShouldBeFound("commityAmt.specified=true");

        // Get all the loanApplicationsList where commityAmt is null
        defaultLoanApplicationsShouldNotBeFound("commityAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCommityAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where commityAmt is greater than or equal to DEFAULT_COMMITY_AMT
        defaultLoanApplicationsShouldBeFound("commityAmt.greaterThanOrEqual=" + DEFAULT_COMMITY_AMT);

        // Get all the loanApplicationsList where commityAmt is greater than or equal to UPDATED_COMMITY_AMT
        defaultLoanApplicationsShouldNotBeFound("commityAmt.greaterThanOrEqual=" + UPDATED_COMMITY_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCommityAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where commityAmt is less than or equal to DEFAULT_COMMITY_AMT
        defaultLoanApplicationsShouldBeFound("commityAmt.lessThanOrEqual=" + DEFAULT_COMMITY_AMT);

        // Get all the loanApplicationsList where commityAmt is less than or equal to SMALLER_COMMITY_AMT
        defaultLoanApplicationsShouldNotBeFound("commityAmt.lessThanOrEqual=" + SMALLER_COMMITY_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCommityAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where commityAmt is less than DEFAULT_COMMITY_AMT
        defaultLoanApplicationsShouldNotBeFound("commityAmt.lessThan=" + DEFAULT_COMMITY_AMT);

        // Get all the loanApplicationsList where commityAmt is less than UPDATED_COMMITY_AMT
        defaultLoanApplicationsShouldBeFound("commityAmt.lessThan=" + UPDATED_COMMITY_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCommityAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where commityAmt is greater than DEFAULT_COMMITY_AMT
        defaultLoanApplicationsShouldNotBeFound("commityAmt.greaterThan=" + DEFAULT_COMMITY_AMT);

        // Get all the loanApplicationsList where commityAmt is greater than SMALLER_COMMITY_AMT
        defaultLoanApplicationsShouldBeFound("commityAmt.greaterThan=" + SMALLER_COMMITY_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCommityRoiIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where commityRoi equals to DEFAULT_COMMITY_ROI
        defaultLoanApplicationsShouldBeFound("commityRoi.equals=" + DEFAULT_COMMITY_ROI);

        // Get all the loanApplicationsList where commityRoi equals to UPDATED_COMMITY_ROI
        defaultLoanApplicationsShouldNotBeFound("commityRoi.equals=" + UPDATED_COMMITY_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCommityRoiIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where commityRoi in DEFAULT_COMMITY_ROI or UPDATED_COMMITY_ROI
        defaultLoanApplicationsShouldBeFound("commityRoi.in=" + DEFAULT_COMMITY_ROI + "," + UPDATED_COMMITY_ROI);

        // Get all the loanApplicationsList where commityRoi equals to UPDATED_COMMITY_ROI
        defaultLoanApplicationsShouldNotBeFound("commityRoi.in=" + UPDATED_COMMITY_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCommityRoiIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where commityRoi is not null
        defaultLoanApplicationsShouldBeFound("commityRoi.specified=true");

        // Get all the loanApplicationsList where commityRoi is null
        defaultLoanApplicationsShouldNotBeFound("commityRoi.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCommityRoiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where commityRoi is greater than or equal to DEFAULT_COMMITY_ROI
        defaultLoanApplicationsShouldBeFound("commityRoi.greaterThanOrEqual=" + DEFAULT_COMMITY_ROI);

        // Get all the loanApplicationsList where commityRoi is greater than or equal to UPDATED_COMMITY_ROI
        defaultLoanApplicationsShouldNotBeFound("commityRoi.greaterThanOrEqual=" + UPDATED_COMMITY_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCommityRoiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where commityRoi is less than or equal to DEFAULT_COMMITY_ROI
        defaultLoanApplicationsShouldBeFound("commityRoi.lessThanOrEqual=" + DEFAULT_COMMITY_ROI);

        // Get all the loanApplicationsList where commityRoi is less than or equal to SMALLER_COMMITY_ROI
        defaultLoanApplicationsShouldNotBeFound("commityRoi.lessThanOrEqual=" + SMALLER_COMMITY_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCommityRoiIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where commityRoi is less than DEFAULT_COMMITY_ROI
        defaultLoanApplicationsShouldNotBeFound("commityRoi.lessThan=" + DEFAULT_COMMITY_ROI);

        // Get all the loanApplicationsList where commityRoi is less than UPDATED_COMMITY_ROI
        defaultLoanApplicationsShouldBeFound("commityRoi.lessThan=" + UPDATED_COMMITY_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByCommityRoiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where commityRoi is greater than DEFAULT_COMMITY_ROI
        defaultLoanApplicationsShouldNotBeFound("commityRoi.greaterThan=" + DEFAULT_COMMITY_ROI);

        // Get all the loanApplicationsList where commityRoi is greater than SMALLER_COMMITY_ROI
        defaultLoanApplicationsShouldBeFound("commityRoi.greaterThan=" + SMALLER_COMMITY_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySectionAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where sectionAmt equals to DEFAULT_SECTION_AMT
        defaultLoanApplicationsShouldBeFound("sectionAmt.equals=" + DEFAULT_SECTION_AMT);

        // Get all the loanApplicationsList where sectionAmt equals to UPDATED_SECTION_AMT
        defaultLoanApplicationsShouldNotBeFound("sectionAmt.equals=" + UPDATED_SECTION_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySectionAmtIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where sectionAmt in DEFAULT_SECTION_AMT or UPDATED_SECTION_AMT
        defaultLoanApplicationsShouldBeFound("sectionAmt.in=" + DEFAULT_SECTION_AMT + "," + UPDATED_SECTION_AMT);

        // Get all the loanApplicationsList where sectionAmt equals to UPDATED_SECTION_AMT
        defaultLoanApplicationsShouldNotBeFound("sectionAmt.in=" + UPDATED_SECTION_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySectionAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where sectionAmt is not null
        defaultLoanApplicationsShouldBeFound("sectionAmt.specified=true");

        // Get all the loanApplicationsList where sectionAmt is null
        defaultLoanApplicationsShouldNotBeFound("sectionAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySectionAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where sectionAmt is greater than or equal to DEFAULT_SECTION_AMT
        defaultLoanApplicationsShouldBeFound("sectionAmt.greaterThanOrEqual=" + DEFAULT_SECTION_AMT);

        // Get all the loanApplicationsList where sectionAmt is greater than or equal to UPDATED_SECTION_AMT
        defaultLoanApplicationsShouldNotBeFound("sectionAmt.greaterThanOrEqual=" + UPDATED_SECTION_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySectionAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where sectionAmt is less than or equal to DEFAULT_SECTION_AMT
        defaultLoanApplicationsShouldBeFound("sectionAmt.lessThanOrEqual=" + DEFAULT_SECTION_AMT);

        // Get all the loanApplicationsList where sectionAmt is less than or equal to SMALLER_SECTION_AMT
        defaultLoanApplicationsShouldNotBeFound("sectionAmt.lessThanOrEqual=" + SMALLER_SECTION_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySectionAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where sectionAmt is less than DEFAULT_SECTION_AMT
        defaultLoanApplicationsShouldNotBeFound("sectionAmt.lessThan=" + DEFAULT_SECTION_AMT);

        // Get all the loanApplicationsList where sectionAmt is less than UPDATED_SECTION_AMT
        defaultLoanApplicationsShouldBeFound("sectionAmt.lessThan=" + UPDATED_SECTION_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySectionAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where sectionAmt is greater than DEFAULT_SECTION_AMT
        defaultLoanApplicationsShouldNotBeFound("sectionAmt.greaterThan=" + DEFAULT_SECTION_AMT);

        // Get all the loanApplicationsList where sectionAmt is greater than SMALLER_SECTION_AMT
        defaultLoanApplicationsShouldBeFound("sectionAmt.greaterThan=" + SMALLER_SECTION_AMT);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySenctionRoiIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where senctionRoi equals to DEFAULT_SENCTION_ROI
        defaultLoanApplicationsShouldBeFound("senctionRoi.equals=" + DEFAULT_SENCTION_ROI);

        // Get all the loanApplicationsList where senctionRoi equals to UPDATED_SENCTION_ROI
        defaultLoanApplicationsShouldNotBeFound("senctionRoi.equals=" + UPDATED_SENCTION_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySenctionRoiIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where senctionRoi in DEFAULT_SENCTION_ROI or UPDATED_SENCTION_ROI
        defaultLoanApplicationsShouldBeFound("senctionRoi.in=" + DEFAULT_SENCTION_ROI + "," + UPDATED_SENCTION_ROI);

        // Get all the loanApplicationsList where senctionRoi equals to UPDATED_SENCTION_ROI
        defaultLoanApplicationsShouldNotBeFound("senctionRoi.in=" + UPDATED_SENCTION_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySenctionRoiIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where senctionRoi is not null
        defaultLoanApplicationsShouldBeFound("senctionRoi.specified=true");

        // Get all the loanApplicationsList where senctionRoi is null
        defaultLoanApplicationsShouldNotBeFound("senctionRoi.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySenctionRoiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where senctionRoi is greater than or equal to DEFAULT_SENCTION_ROI
        defaultLoanApplicationsShouldBeFound("senctionRoi.greaterThanOrEqual=" + DEFAULT_SENCTION_ROI);

        // Get all the loanApplicationsList where senctionRoi is greater than or equal to UPDATED_SENCTION_ROI
        defaultLoanApplicationsShouldNotBeFound("senctionRoi.greaterThanOrEqual=" + UPDATED_SENCTION_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySenctionRoiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where senctionRoi is less than or equal to DEFAULT_SENCTION_ROI
        defaultLoanApplicationsShouldBeFound("senctionRoi.lessThanOrEqual=" + DEFAULT_SENCTION_ROI);

        // Get all the loanApplicationsList where senctionRoi is less than or equal to SMALLER_SENCTION_ROI
        defaultLoanApplicationsShouldNotBeFound("senctionRoi.lessThanOrEqual=" + SMALLER_SENCTION_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySenctionRoiIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where senctionRoi is less than DEFAULT_SENCTION_ROI
        defaultLoanApplicationsShouldNotBeFound("senctionRoi.lessThan=" + DEFAULT_SENCTION_ROI);

        // Get all the loanApplicationsList where senctionRoi is less than UPDATED_SENCTION_ROI
        defaultLoanApplicationsShouldBeFound("senctionRoi.lessThan=" + UPDATED_SENCTION_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySenctionRoiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where senctionRoi is greater than DEFAULT_SENCTION_ROI
        defaultLoanApplicationsShouldNotBeFound("senctionRoi.greaterThan=" + DEFAULT_SENCTION_ROI);

        // Get all the loanApplicationsList where senctionRoi is greater than SMALLER_SENCTION_ROI
        defaultLoanApplicationsShouldBeFound("senctionRoi.greaterThan=" + SMALLER_SENCTION_ROI);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where year equals to DEFAULT_YEAR
        defaultLoanApplicationsShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the loanApplicationsList where year equals to UPDATED_YEAR
        defaultLoanApplicationsShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultLoanApplicationsShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the loanApplicationsList where year equals to UPDATED_YEAR
        defaultLoanApplicationsShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where year is not null
        defaultLoanApplicationsShouldBeFound("year.specified=true");

        // Get all the loanApplicationsList where year is null
        defaultLoanApplicationsShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByYearContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where year contains DEFAULT_YEAR
        defaultLoanApplicationsShouldBeFound("year.contains=" + DEFAULT_YEAR);

        // Get all the loanApplicationsList where year contains UPDATED_YEAR
        defaultLoanApplicationsShouldNotBeFound("year.contains=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByYearNotContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where year does not contain DEFAULT_YEAR
        defaultLoanApplicationsShouldNotBeFound("year.doesNotContain=" + DEFAULT_YEAR);

        // Get all the loanApplicationsList where year does not contain UPDATED_YEAR
        defaultLoanApplicationsShouldBeFound("year.doesNotContain=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByAssignedToIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where assignedTo equals to DEFAULT_ASSIGNED_TO
        defaultLoanApplicationsShouldBeFound("assignedTo.equals=" + DEFAULT_ASSIGNED_TO);

        // Get all the loanApplicationsList where assignedTo equals to UPDATED_ASSIGNED_TO
        defaultLoanApplicationsShouldNotBeFound("assignedTo.equals=" + UPDATED_ASSIGNED_TO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByAssignedToIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where assignedTo in DEFAULT_ASSIGNED_TO or UPDATED_ASSIGNED_TO
        defaultLoanApplicationsShouldBeFound("assignedTo.in=" + DEFAULT_ASSIGNED_TO + "," + UPDATED_ASSIGNED_TO);

        // Get all the loanApplicationsList where assignedTo equals to UPDATED_ASSIGNED_TO
        defaultLoanApplicationsShouldNotBeFound("assignedTo.in=" + UPDATED_ASSIGNED_TO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByAssignedToIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where assignedTo is not null
        defaultLoanApplicationsShouldBeFound("assignedTo.specified=true");

        // Get all the loanApplicationsList where assignedTo is null
        defaultLoanApplicationsShouldNotBeFound("assignedTo.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByAssignedToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where assignedTo is greater than or equal to DEFAULT_ASSIGNED_TO
        defaultLoanApplicationsShouldBeFound("assignedTo.greaterThanOrEqual=" + DEFAULT_ASSIGNED_TO);

        // Get all the loanApplicationsList where assignedTo is greater than or equal to UPDATED_ASSIGNED_TO
        defaultLoanApplicationsShouldNotBeFound("assignedTo.greaterThanOrEqual=" + UPDATED_ASSIGNED_TO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByAssignedToIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where assignedTo is less than or equal to DEFAULT_ASSIGNED_TO
        defaultLoanApplicationsShouldBeFound("assignedTo.lessThanOrEqual=" + DEFAULT_ASSIGNED_TO);

        // Get all the loanApplicationsList where assignedTo is less than or equal to SMALLER_ASSIGNED_TO
        defaultLoanApplicationsShouldNotBeFound("assignedTo.lessThanOrEqual=" + SMALLER_ASSIGNED_TO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByAssignedToIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where assignedTo is less than DEFAULT_ASSIGNED_TO
        defaultLoanApplicationsShouldNotBeFound("assignedTo.lessThan=" + DEFAULT_ASSIGNED_TO);

        // Get all the loanApplicationsList where assignedTo is less than UPDATED_ASSIGNED_TO
        defaultLoanApplicationsShouldBeFound("assignedTo.lessThan=" + UPDATED_ASSIGNED_TO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByAssignedToIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where assignedTo is greater than DEFAULT_ASSIGNED_TO
        defaultLoanApplicationsShouldNotBeFound("assignedTo.greaterThan=" + DEFAULT_ASSIGNED_TO);

        // Get all the loanApplicationsList where assignedTo is greater than SMALLER_ASSIGNED_TO
        defaultLoanApplicationsShouldBeFound("assignedTo.greaterThan=" + SMALLER_ASSIGNED_TO);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByAssignedFromIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where assignedFrom equals to DEFAULT_ASSIGNED_FROM
        defaultLoanApplicationsShouldBeFound("assignedFrom.equals=" + DEFAULT_ASSIGNED_FROM);

        // Get all the loanApplicationsList where assignedFrom equals to UPDATED_ASSIGNED_FROM
        defaultLoanApplicationsShouldNotBeFound("assignedFrom.equals=" + UPDATED_ASSIGNED_FROM);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByAssignedFromIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where assignedFrom in DEFAULT_ASSIGNED_FROM or UPDATED_ASSIGNED_FROM
        defaultLoanApplicationsShouldBeFound("assignedFrom.in=" + DEFAULT_ASSIGNED_FROM + "," + UPDATED_ASSIGNED_FROM);

        // Get all the loanApplicationsList where assignedFrom equals to UPDATED_ASSIGNED_FROM
        defaultLoanApplicationsShouldNotBeFound("assignedFrom.in=" + UPDATED_ASSIGNED_FROM);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByAssignedFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where assignedFrom is not null
        defaultLoanApplicationsShouldBeFound("assignedFrom.specified=true");

        // Get all the loanApplicationsList where assignedFrom is null
        defaultLoanApplicationsShouldNotBeFound("assignedFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByAssignedFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where assignedFrom is greater than or equal to DEFAULT_ASSIGNED_FROM
        defaultLoanApplicationsShouldBeFound("assignedFrom.greaterThanOrEqual=" + DEFAULT_ASSIGNED_FROM);

        // Get all the loanApplicationsList where assignedFrom is greater than or equal to UPDATED_ASSIGNED_FROM
        defaultLoanApplicationsShouldNotBeFound("assignedFrom.greaterThanOrEqual=" + UPDATED_ASSIGNED_FROM);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByAssignedFromIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where assignedFrom is less than or equal to DEFAULT_ASSIGNED_FROM
        defaultLoanApplicationsShouldBeFound("assignedFrom.lessThanOrEqual=" + DEFAULT_ASSIGNED_FROM);

        // Get all the loanApplicationsList where assignedFrom is less than or equal to SMALLER_ASSIGNED_FROM
        defaultLoanApplicationsShouldNotBeFound("assignedFrom.lessThanOrEqual=" + SMALLER_ASSIGNED_FROM);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByAssignedFromIsLessThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where assignedFrom is less than DEFAULT_ASSIGNED_FROM
        defaultLoanApplicationsShouldNotBeFound("assignedFrom.lessThan=" + DEFAULT_ASSIGNED_FROM);

        // Get all the loanApplicationsList where assignedFrom is less than UPDATED_ASSIGNED_FROM
        defaultLoanApplicationsShouldBeFound("assignedFrom.lessThan=" + UPDATED_ASSIGNED_FROM);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByAssignedFromIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where assignedFrom is greater than DEFAULT_ASSIGNED_FROM
        defaultLoanApplicationsShouldNotBeFound("assignedFrom.greaterThan=" + DEFAULT_ASSIGNED_FROM);

        // Get all the loanApplicationsList where assignedFrom is greater than SMALLER_ASSIGNED_FROM
        defaultLoanApplicationsShouldBeFound("assignedFrom.greaterThan=" + SMALLER_ASSIGNED_FROM);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySecurityDocUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where securityDocUrl equals to DEFAULT_SECURITY_DOC_URL
        defaultLoanApplicationsShouldBeFound("securityDocUrl.equals=" + DEFAULT_SECURITY_DOC_URL);

        // Get all the loanApplicationsList where securityDocUrl equals to UPDATED_SECURITY_DOC_URL
        defaultLoanApplicationsShouldNotBeFound("securityDocUrl.equals=" + UPDATED_SECURITY_DOC_URL);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySecurityDocUrlIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where securityDocUrl in DEFAULT_SECURITY_DOC_URL or UPDATED_SECURITY_DOC_URL
        defaultLoanApplicationsShouldBeFound("securityDocUrl.in=" + DEFAULT_SECURITY_DOC_URL + "," + UPDATED_SECURITY_DOC_URL);

        // Get all the loanApplicationsList where securityDocUrl equals to UPDATED_SECURITY_DOC_URL
        defaultLoanApplicationsShouldNotBeFound("securityDocUrl.in=" + UPDATED_SECURITY_DOC_URL);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySecurityDocUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where securityDocUrl is not null
        defaultLoanApplicationsShouldBeFound("securityDocUrl.specified=true");

        // Get all the loanApplicationsList where securityDocUrl is null
        defaultLoanApplicationsShouldNotBeFound("securityDocUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySecurityDocUrlContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where securityDocUrl contains DEFAULT_SECURITY_DOC_URL
        defaultLoanApplicationsShouldBeFound("securityDocUrl.contains=" + DEFAULT_SECURITY_DOC_URL);

        // Get all the loanApplicationsList where securityDocUrl contains UPDATED_SECURITY_DOC_URL
        defaultLoanApplicationsShouldNotBeFound("securityDocUrl.contains=" + UPDATED_SECURITY_DOC_URL);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySecurityDocUrlNotContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where securityDocUrl does not contain DEFAULT_SECURITY_DOC_URL
        defaultLoanApplicationsShouldNotBeFound("securityDocUrl.doesNotContain=" + DEFAULT_SECURITY_DOC_URL);

        // Get all the loanApplicationsList where securityDocUrl does not contain UPDATED_SECURITY_DOC_URL
        defaultLoanApplicationsShouldBeFound("securityDocUrl.doesNotContain=" + UPDATED_SECURITY_DOC_URL);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLoanApplicationsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the loanApplicationsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanApplicationsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLoanApplicationsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the loanApplicationsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanApplicationsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where lastModified is not null
        defaultLoanApplicationsShouldBeFound("lastModified.specified=true");

        // Get all the loanApplicationsList where lastModified is null
        defaultLoanApplicationsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLoanApplicationsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanApplicationsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanApplicationsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLoanApplicationsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the loanApplicationsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanApplicationsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where lastModifiedBy is not null
        defaultLoanApplicationsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the loanApplicationsList where lastModifiedBy is null
        defaultLoanApplicationsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLoanApplicationsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanApplicationsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLoanApplicationsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLoanApplicationsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanApplicationsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLoanApplicationsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultLoanApplicationsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanApplicationsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanApplicationsShouldNotBeFound("freeField1.equals=" + UPDATED_MARGIN);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultLoanApplicationsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_MARGIN);

        // Get all the loanApplicationsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanApplicationsShouldNotBeFound("freeField1.in=" + UPDATED_MARGIN);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField1 is not null
        defaultLoanApplicationsShouldBeFound("freeField1.specified=true");

        // Get all the loanApplicationsList where freeField1 is null
        defaultLoanApplicationsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultLoanApplicationsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanApplicationsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultLoanApplicationsShouldNotBeFound("freeField1.contains=" + UPDATED_MARGIN);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultLoanApplicationsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanApplicationsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultLoanApplicationsShouldBeFound("freeField1.doesNotContain=" + UPDATED_MARGIN);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultLoanApplicationsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanApplicationsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanApplicationsShouldNotBeFound("freeField2.equals=" + UPDATED_SECURITY_OFFERED);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultLoanApplicationsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_SECURITY_OFFERED);

        // Get all the loanApplicationsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanApplicationsShouldNotBeFound("freeField2.in=" + UPDATED_SECURITY_OFFERED);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField2 is not null
        defaultLoanApplicationsShouldBeFound("freeField2.specified=true");

        // Get all the loanApplicationsList where freeField2 is null
        defaultLoanApplicationsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultLoanApplicationsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanApplicationsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultLoanApplicationsShouldNotBeFound("freeField2.contains=" + UPDATED_SECURITY_OFFERED);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultLoanApplicationsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanApplicationsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultLoanApplicationsShouldBeFound("freeField2.doesNotContain=" + UPDATED_SECURITY_OFFERED);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultLoanApplicationsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanApplicationsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanApplicationsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultLoanApplicationsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the loanApplicationsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanApplicationsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField3 is not null
        defaultLoanApplicationsShouldBeFound("freeField3.specified=true");

        // Get all the loanApplicationsList where freeField3 is null
        defaultLoanApplicationsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultLoanApplicationsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanApplicationsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultLoanApplicationsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultLoanApplicationsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanApplicationsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultLoanApplicationsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultLoanApplicationsShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the loanApplicationsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultLoanApplicationsShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultLoanApplicationsShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the loanApplicationsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultLoanApplicationsShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField4 is not null
        defaultLoanApplicationsShouldBeFound("freeField4.specified=true");

        // Get all the loanApplicationsList where freeField4 is null
        defaultLoanApplicationsShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultLoanApplicationsShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the loanApplicationsList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultLoanApplicationsShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultLoanApplicationsShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the loanApplicationsList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultLoanApplicationsShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultLoanApplicationsShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the loanApplicationsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultLoanApplicationsShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultLoanApplicationsShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the loanApplicationsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultLoanApplicationsShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField5 is not null
        defaultLoanApplicationsShouldBeFound("freeField5.specified=true");

        // Get all the loanApplicationsList where freeField5 is null
        defaultLoanApplicationsShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultLoanApplicationsShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the loanApplicationsList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultLoanApplicationsShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultLoanApplicationsShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the loanApplicationsList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultLoanApplicationsShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultLoanApplicationsShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the loanApplicationsList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultLoanApplicationsShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultLoanApplicationsShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the loanApplicationsList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultLoanApplicationsShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField6 is not null
        defaultLoanApplicationsShouldBeFound("freeField6.specified=true");

        // Get all the loanApplicationsList where freeField6 is null
        defaultLoanApplicationsShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultLoanApplicationsShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the loanApplicationsList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultLoanApplicationsShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultLoanApplicationsShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the loanApplicationsList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultLoanApplicationsShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField7IsEqualToSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField7 equals to DEFAULT_FREE_FIELD_7
        defaultLoanApplicationsShouldBeFound("freeField7.equals=" + DEFAULT_FREE_FIELD_7);

        // Get all the loanApplicationsList where freeField7 equals to UPDATED_FREE_FIELD_7
        defaultLoanApplicationsShouldNotBeFound("freeField7.equals=" + UPDATED_FREE_FIELD_7);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField7IsInShouldWork() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField7 in DEFAULT_FREE_FIELD_7 or UPDATED_FREE_FIELD_7
        defaultLoanApplicationsShouldBeFound("freeField7.in=" + DEFAULT_FREE_FIELD_7 + "," + UPDATED_FREE_FIELD_7);

        // Get all the loanApplicationsList where freeField7 equals to UPDATED_FREE_FIELD_7
        defaultLoanApplicationsShouldNotBeFound("freeField7.in=" + UPDATED_FREE_FIELD_7);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField7IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField7 is not null
        defaultLoanApplicationsShouldBeFound("freeField7.specified=true");

        // Get all the loanApplicationsList where freeField7 is null
        defaultLoanApplicationsShouldNotBeFound("freeField7.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField7ContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField7 contains DEFAULT_FREE_FIELD_7
        defaultLoanApplicationsShouldBeFound("freeField7.contains=" + DEFAULT_FREE_FIELD_7);

        // Get all the loanApplicationsList where freeField7 contains UPDATED_FREE_FIELD_7
        defaultLoanApplicationsShouldNotBeFound("freeField7.contains=" + UPDATED_FREE_FIELD_7);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByFreeField7NotContainsSomething() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        // Get all the loanApplicationsList where freeField7 does not contain DEFAULT_FREE_FIELD_7
        defaultLoanApplicationsShouldNotBeFound("freeField7.doesNotContain=" + DEFAULT_FREE_FIELD_7);

        // Get all the loanApplicationsList where freeField7 does not contain UPDATED_FREE_FIELD_7
        defaultLoanApplicationsShouldBeFound("freeField7.doesNotContain=" + UPDATED_FREE_FIELD_7);
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            loanApplicationsRepository.saveAndFlush(loanApplications);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        //     loanApplications.setMember(member);
        loanApplicationsRepository.saveAndFlush(loanApplications);
        Long memberId = member.getId();

        // Get all the loanApplicationsList where member equals to memberId
        defaultLoanApplicationsShouldBeFound("memberId.equals=" + memberId);

        // Get all the loanApplicationsList where member equals to (memberId + 1)
        defaultLoanApplicationsShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    @Test
    @Transactional
    void getAllLoanApplicationsBySecurityUserIsEqualToSomething() throws Exception {
        SecurityUser securityUser;
        if (TestUtil.findAll(em, SecurityUser.class).isEmpty()) {
            loanApplicationsRepository.saveAndFlush(loanApplications);
            securityUser = SecurityUserResourceIT.createEntity(em);
        } else {
            securityUser = TestUtil.findAll(em, SecurityUser.class).get(0);
        }
        em.persist(securityUser);
        em.flush();
        loanApplications.setSecurityUser(securityUser);
        loanApplicationsRepository.saveAndFlush(loanApplications);
        Long securityUserId = securityUser.getId();

        // Get all the loanApplicationsList where securityUser equals to securityUserId
        defaultLoanApplicationsShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the loanApplicationsList where securityUser equals to (securityUserId + 1)
        defaultLoanApplicationsShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    @Test
    @Transactional
    void getAllLoanApplicationsByProductIsEqualToSomething() throws Exception {
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            loanApplicationsRepository.saveAndFlush(loanApplications);
            product = ProductResourceIT.createEntity(em);
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        loanApplications.setProduct(product);
        loanApplicationsRepository.saveAndFlush(loanApplications);
        Long productId = product.getId();

        // Get all the loanApplicationsList where product equals to productId
        defaultLoanApplicationsShouldBeFound("productId.equals=" + productId);

        // Get all the loanApplicationsList where product equals to (productId + 1)
        defaultLoanApplicationsShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLoanApplicationsShouldBeFound(String filter) throws Exception {
        restLoanApplicationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanApplications.getId().intValue())))
            .andExpect(jsonPath("$.[*].applicationNo").value(hasItem(DEFAULT_APPLICATION_NO)))
            .andExpect(jsonPath("$.[*].demandAmount").value(hasItem(DEFAULT_DEMAND_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanPurpose").value(hasItem(DEFAULT_LOAN_PURPOSE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].demandedLandAreaInHector").value(hasItem(DEFAULT_DEMANDED_LAND_AREA_IN_HECTOR)))
            .andExpect(jsonPath("$.[*].seasonOfCropLoan").value(hasItem(DEFAULT_SEASON_OF_CROP_LOAN)))
            .andExpect(jsonPath("$.[*].loanSteps").value(hasItem(DEFAULT_LOAN_STEPS.toString())))
            .andExpect(jsonPath("$.[*].isInsured").value(hasItem(DEFAULT_IS_INSURED.booleanValue())))
            .andExpect(jsonPath("$.[*].loanBenefitingArea").value(hasItem(DEFAULT_LOAN_BENEFITING_AREA.doubleValue())))
            .andExpect(jsonPath("$.[*].costOfInvestment").value(hasItem(DEFAULT_COST_OF_INVESTMENT.doubleValue())))
            .andExpect(jsonPath("$.[*].mortgageDeedNo").value(hasItem(DEFAULT_MORTGAGE_DEED_NO.intValue())))
            .andExpect(jsonPath("$.[*].mortgageDate").value(hasItem(DEFAULT_MORTGAGE_DATE.toString())))
            .andExpect(jsonPath("$.[*].extentMorgageValue").value(hasItem(DEFAULT_EXTENT_MORGAGE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].downPaymentAmt").value(hasItem(DEFAULT_DOWN_PAYMENT_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].ltvRatio").value(hasItem(DEFAULT_LTV_RATIO.doubleValue())))
            .andExpect(jsonPath("$.[*].processingFee").value(hasItem(DEFAULT_PROCESSING_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].penalInterest").value(hasItem(DEFAULT_PENAL_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].moratorium").value(hasItem(DEFAULT_MORATORIUM)))
            .andExpect(jsonPath("$.[*].roi").value(hasItem(DEFAULT_ROI.doubleValue())))
            .andExpect(jsonPath("$.[*].commityAmt").value(hasItem(DEFAULT_COMMITY_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].commityRoi").value(hasItem(DEFAULT_COMMITY_ROI.doubleValue())))
            .andExpect(jsonPath("$.[*].sectionAmt").value(hasItem(DEFAULT_SECTION_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].senctionRoi").value(hasItem(DEFAULT_SENCTION_ROI.doubleValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].assignedTo").value(hasItem(DEFAULT_ASSIGNED_TO.intValue())))
            .andExpect(jsonPath("$.[*].assignedFrom").value(hasItem(DEFAULT_ASSIGNED_FROM.intValue())))
            .andExpect(jsonPath("$.[*].securityDocUrl").value(hasItem(DEFAULT_SECURITY_DOC_URL)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)))
            .andExpect(jsonPath("$.[*].freeField7").value(hasItem(DEFAULT_FREE_FIELD_7)));

        // Check, that the count call also returns 1
        restLoanApplicationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLoanApplicationsShouldNotBeFound(String filter) throws Exception {
        restLoanApplicationsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLoanApplicationsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLoanApplications() throws Exception {
        // Get the loanApplications
        restLoanApplicationsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLoanApplications() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        int databaseSizeBeforeUpdate = loanApplicationsRepository.findAll().size();

        // Update the loanApplications
        LoanApplications updatedLoanApplications = loanApplicationsRepository.findById(loanApplications.getId()).get();
        // Disconnect from session so that the updates on updatedLoanApplications are not directly saved in db
        em.detach(updatedLoanApplications);
        updatedLoanApplications
            .applicationNo(UPDATED_APPLICATION_NO)
            .demandAmount(UPDATED_DEMAND_AMOUNT)
            .loanPurpose(UPDATED_LOAN_PURPOSE)
            .status(UPDATED_STATUS)
            .demandedLandAreaInHector(UPDATED_DEMANDED_LAND_AREA_IN_HECTOR)
            .seasonOfCropLoan(UPDATED_SEASON_OF_CROP_LOAN)
            .loanSteps(UPDATED_LOAN_STEPS)
            .isInsured(UPDATED_IS_INSURED)
            .loanBenefitingArea(UPDATED_LOAN_BENEFITING_AREA)
            .costOfInvestment(UPDATED_COST_OF_INVESTMENT)
            .mortgageDeedNo(UPDATED_MORTGAGE_DEED_NO)
            .mortgageDate(UPDATED_MORTGAGE_DATE)
            .extentMorgageValue(UPDATED_EXTENT_MORGAGE_VALUE)
            .downPaymentAmt(UPDATED_DOWN_PAYMENT_AMT)
            .ltvRatio(UPDATED_LTV_RATIO)
            .processingFee(UPDATED_PROCESSING_FEE)
            .penalInterest(UPDATED_PENAL_INTEREST)
            .moratorium(UPDATED_MORATORIUM)
            .roi(UPDATED_ROI)
            .commityAmt(UPDATED_COMMITY_AMT)
            .commityRoi(UPDATED_COMMITY_ROI)
            .sanctionAmt(UPDATED_SECTION_AMT)
            .sanctionRoi(UPDATED_SENCTION_ROI)
            .year(UPDATED_YEAR)
            //            .assignedTo(UPDATED_ASSIGNED_TO)
            //            .assignedFrom(UPDATED_ASSIGNED_FROM)
            .securityDocUrl(UPDATED_SECURITY_DOC_URL)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .margin(UPDATED_MARGIN)
            .securityOffered(UPDATED_SECURITY_OFFERED);
        //            .freeField3(UPDATED_FREE_FIELD_3)
        //            .freeField4(UPDATED_FREE_FIELD_4)
        //            .freeField5(UPDATED_FREE_FIELD_5)
        //            .freeField6(UPDATED_FREE_FIELD_6);
        //  .freeField7(UPDATED_FREE_FIELD_7);
        LoanApplicationsDTO loanApplicationsDTO = loanApplicationsMapper.toDto(updatedLoanApplications);

        restLoanApplicationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanApplicationsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanApplicationsDTO))
            )
            .andExpect(status().isOk());

        // Validate the LoanApplications in the database
        List<LoanApplications> loanApplicationsList = loanApplicationsRepository.findAll();
        assertThat(loanApplicationsList).hasSize(databaseSizeBeforeUpdate);
        LoanApplications testLoanApplications = loanApplicationsList.get(loanApplicationsList.size() - 1);
        assertThat(testLoanApplications.getApplicationNo()).isEqualTo(UPDATED_APPLICATION_NO);
        assertThat(testLoanApplications.getDemandAmount()).isEqualTo(UPDATED_DEMAND_AMOUNT);
        assertThat(testLoanApplications.getLoanPurpose()).isEqualTo(UPDATED_LOAN_PURPOSE);
        assertThat(testLoanApplications.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoanApplications.getDemandedLandAreaInHector()).isEqualTo(UPDATED_DEMANDED_LAND_AREA_IN_HECTOR);
        assertThat(testLoanApplications.getSeasonOfCropLoan()).isEqualTo(UPDATED_SEASON_OF_CROP_LOAN);
        assertThat(testLoanApplications.getLoanSteps()).isEqualTo(UPDATED_LOAN_STEPS);
        assertThat(testLoanApplications.getIsInsured()).isEqualTo(UPDATED_IS_INSURED);
        assertThat(testLoanApplications.getLoanBenefitingArea()).isEqualTo(UPDATED_LOAN_BENEFITING_AREA);
        assertThat(testLoanApplications.getCostOfInvestment()).isEqualTo(UPDATED_COST_OF_INVESTMENT);
        assertThat(testLoanApplications.getMortgageDeedNo()).isEqualTo(UPDATED_MORTGAGE_DEED_NO);
        assertThat(testLoanApplications.getMortgageDate()).isEqualTo(UPDATED_MORTGAGE_DATE);
        assertThat(testLoanApplications.getExtentMorgageValue()).isEqualTo(UPDATED_EXTENT_MORGAGE_VALUE);
        assertThat(testLoanApplications.getDownPaymentAmt()).isEqualTo(UPDATED_DOWN_PAYMENT_AMT);
        assertThat(testLoanApplications.getLtvRatio()).isEqualTo(UPDATED_LTV_RATIO);
        assertThat(testLoanApplications.getProcessingFee()).isEqualTo(UPDATED_PROCESSING_FEE);
        assertThat(testLoanApplications.getPenalInterest()).isEqualTo(UPDATED_PENAL_INTEREST);
        assertThat(testLoanApplications.getMoratorium()).isEqualTo(UPDATED_MORATORIUM);
        assertThat(testLoanApplications.getRoi()).isEqualTo(UPDATED_ROI);
        assertThat(testLoanApplications.getCommityAmt()).isEqualTo(UPDATED_COMMITY_AMT);
        assertThat(testLoanApplications.getCommityRoi()).isEqualTo(UPDATED_COMMITY_ROI);
        assertThat(testLoanApplications.getSanctionAmt()).isEqualTo(UPDATED_SECTION_AMT);
        assertThat(testLoanApplications.getSanctionRoi()).isEqualTo(UPDATED_SENCTION_ROI);
        assertThat(testLoanApplications.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLoanApplications.getAssignedTo()).isEqualTo(UPDATED_ASSIGNED_TO);
        assertThat(testLoanApplications.getAssignedFrom()).isEqualTo(UPDATED_ASSIGNED_FROM);
        assertThat(testLoanApplications.getSecurityDocUrl()).isEqualTo(UPDATED_SECURITY_DOC_URL);
        assertThat(testLoanApplications.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanApplications.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanApplications.getMargin()).isEqualTo(UPDATED_MARGIN);
        assertThat(testLoanApplications.getSecurityOffered()).isEqualTo(UPDATED_SECURITY_OFFERED);
        //        assertThat(testLoanApplications.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        //        assertThat(testLoanApplications.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        //        assertThat(testLoanApplications.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        //        assertThat(testLoanApplications.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
        //        assertThat(testLoanApplications.getFreeField7()).isEqualTo(UPDATED_FREE_FIELD_7);
    }

    @Test
    @Transactional
    void putNonExistingLoanApplications() throws Exception {
        int databaseSizeBeforeUpdate = loanApplicationsRepository.findAll().size();
        loanApplications.setId(count.incrementAndGet());

        // Create the LoanApplications
        LoanApplicationsDTO loanApplicationsDTO = loanApplicationsMapper.toDto(loanApplications);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanApplicationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanApplicationsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanApplicationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanApplications in the database
        List<LoanApplications> loanApplicationsList = loanApplicationsRepository.findAll();
        assertThat(loanApplicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoanApplications() throws Exception {
        int databaseSizeBeforeUpdate = loanApplicationsRepository.findAll().size();
        loanApplications.setId(count.incrementAndGet());

        // Create the LoanApplications
        LoanApplicationsDTO loanApplicationsDTO = loanApplicationsMapper.toDto(loanApplications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanApplicationsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanApplicationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanApplications in the database
        List<LoanApplications> loanApplicationsList = loanApplicationsRepository.findAll();
        assertThat(loanApplicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoanApplications() throws Exception {
        int databaseSizeBeforeUpdate = loanApplicationsRepository.findAll().size();
        loanApplications.setId(count.incrementAndGet());

        // Create the LoanApplications
        LoanApplicationsDTO loanApplicationsDTO = loanApplicationsMapper.toDto(loanApplications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanApplicationsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanApplicationsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanApplications in the database
        List<LoanApplications> loanApplicationsList = loanApplicationsRepository.findAll();
        assertThat(loanApplicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoanApplicationsWithPatch() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        int databaseSizeBeforeUpdate = loanApplicationsRepository.findAll().size();

        // Update the loanApplications using partial update
        LoanApplications partialUpdatedLoanApplications = new LoanApplications();
        partialUpdatedLoanApplications.setId(loanApplications.getId());

        partialUpdatedLoanApplications
            .applicationNo(UPDATED_APPLICATION_NO)
            .loanPurpose(UPDATED_LOAN_PURPOSE)
            .demandedLandAreaInHector(UPDATED_DEMANDED_LAND_AREA_IN_HECTOR)
            .seasonOfCropLoan(UPDATED_SEASON_OF_CROP_LOAN)
            .isInsured(UPDATED_IS_INSURED)
            .costOfInvestment(UPDATED_COST_OF_INVESTMENT)
            .mortgageDate(UPDATED_MORTGAGE_DATE)
            .extentMorgageValue(UPDATED_EXTENT_MORGAGE_VALUE)
            .penalInterest(UPDATED_PENAL_INTEREST)
            .moratorium(UPDATED_MORATORIUM)
            .commityRoi(UPDATED_COMMITY_ROI)
            .sanctionRoi(UPDATED_SENCTION_ROI)
            //            .assignedTo(UPDATED_ASSIGNED_TO)
            //            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .margin(UPDATED_MARGIN);
        //            .freeField4(UPDATED_FREE_FIELD_4)
        //            .freeField6(UPDATED_FREE_FIELD_6);

        restLoanApplicationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanApplications.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanApplications))
            )
            .andExpect(status().isOk());

        // Validate the LoanApplications in the database
        List<LoanApplications> loanApplicationsList = loanApplicationsRepository.findAll();
        assertThat(loanApplicationsList).hasSize(databaseSizeBeforeUpdate);
        LoanApplications testLoanApplications = loanApplicationsList.get(loanApplicationsList.size() - 1);
        assertThat(testLoanApplications.getApplicationNo()).isEqualTo(UPDATED_APPLICATION_NO);
        assertThat(testLoanApplications.getDemandAmount()).isEqualTo(DEFAULT_DEMAND_AMOUNT);
        assertThat(testLoanApplications.getLoanPurpose()).isEqualTo(UPDATED_LOAN_PURPOSE);
        assertThat(testLoanApplications.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLoanApplications.getDemandedLandAreaInHector()).isEqualTo(UPDATED_DEMANDED_LAND_AREA_IN_HECTOR);
        assertThat(testLoanApplications.getSeasonOfCropLoan()).isEqualTo(UPDATED_SEASON_OF_CROP_LOAN);
        assertThat(testLoanApplications.getLoanSteps()).isEqualTo(DEFAULT_LOAN_STEPS);
        assertThat(testLoanApplications.getIsInsured()).isEqualTo(UPDATED_IS_INSURED);
        assertThat(testLoanApplications.getLoanBenefitingArea()).isEqualTo(DEFAULT_LOAN_BENEFITING_AREA);
        assertThat(testLoanApplications.getCostOfInvestment()).isEqualTo(UPDATED_COST_OF_INVESTMENT);
        assertThat(testLoanApplications.getMortgageDeedNo()).isEqualTo(DEFAULT_MORTGAGE_DEED_NO);
        assertThat(testLoanApplications.getMortgageDate()).isEqualTo(UPDATED_MORTGAGE_DATE);
        assertThat(testLoanApplications.getExtentMorgageValue()).isEqualTo(UPDATED_EXTENT_MORGAGE_VALUE);
        assertThat(testLoanApplications.getDownPaymentAmt()).isEqualTo(DEFAULT_DOWN_PAYMENT_AMT);
        assertThat(testLoanApplications.getLtvRatio()).isEqualTo(DEFAULT_LTV_RATIO);
        assertThat(testLoanApplications.getProcessingFee()).isEqualTo(DEFAULT_PROCESSING_FEE);
        assertThat(testLoanApplications.getPenalInterest()).isEqualTo(UPDATED_PENAL_INTEREST);
        assertThat(testLoanApplications.getMoratorium()).isEqualTo(UPDATED_MORATORIUM);
        assertThat(testLoanApplications.getRoi()).isEqualTo(DEFAULT_ROI);
        assertThat(testLoanApplications.getCommityAmt()).isEqualTo(DEFAULT_COMMITY_AMT);
        assertThat(testLoanApplications.getCommityRoi()).isEqualTo(UPDATED_COMMITY_ROI);
        assertThat(testLoanApplications.getSanctionAmt()).isEqualTo(DEFAULT_SECTION_AMT);
        assertThat(testLoanApplications.getSanctionRoi()).isEqualTo(UPDATED_SENCTION_ROI);
        assertThat(testLoanApplications.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testLoanApplications.getAssignedTo()).isEqualTo(UPDATED_ASSIGNED_TO);
        assertThat(testLoanApplications.getAssignedFrom()).isEqualTo(DEFAULT_ASSIGNED_FROM);
        assertThat(testLoanApplications.getSecurityDocUrl()).isEqualTo(DEFAULT_SECURITY_DOC_URL);
        assertThat(testLoanApplications.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanApplications.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanApplications.getMargin()).isEqualTo(UPDATED_MARGIN);
        assertThat(testLoanApplications.getSecurityOffered()).isEqualTo(DEFAULT_FREE_FIELD_2);
        //        assertThat(testLoanApplications.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        //        assertThat(testLoanApplications.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        //        assertThat(testLoanApplications.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        //        assertThat(testLoanApplications.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
        //        assertThat(testLoanApplications.getFreeField7()).isEqualTo(DEFAULT_FREE_FIELD_7);
    }

    @Test
    @Transactional
    void fullUpdateLoanApplicationsWithPatch() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        int databaseSizeBeforeUpdate = loanApplicationsRepository.findAll().size();

        // Update the loanApplications using partial update
        LoanApplications partialUpdatedLoanApplications = new LoanApplications();
        partialUpdatedLoanApplications.setId(loanApplications.getId());

        partialUpdatedLoanApplications
            .applicationNo(UPDATED_APPLICATION_NO)
            .demandAmount(UPDATED_DEMAND_AMOUNT)
            .loanPurpose(UPDATED_LOAN_PURPOSE)
            .status(UPDATED_STATUS)
            .demandedLandAreaInHector(UPDATED_DEMANDED_LAND_AREA_IN_HECTOR)
            .seasonOfCropLoan(UPDATED_SEASON_OF_CROP_LOAN)
            .loanSteps(UPDATED_LOAN_STEPS)
            .isInsured(UPDATED_IS_INSURED)
            .loanBenefitingArea(UPDATED_LOAN_BENEFITING_AREA)
            .costOfInvestment(UPDATED_COST_OF_INVESTMENT)
            .mortgageDeedNo(UPDATED_MORTGAGE_DEED_NO)
            .mortgageDate(UPDATED_MORTGAGE_DATE)
            .extentMorgageValue(UPDATED_EXTENT_MORGAGE_VALUE)
            .downPaymentAmt(UPDATED_DOWN_PAYMENT_AMT)
            .ltvRatio(UPDATED_LTV_RATIO)
            .processingFee(UPDATED_PROCESSING_FEE)
            .penalInterest(UPDATED_PENAL_INTEREST)
            .moratorium(UPDATED_MORATORIUM)
            .roi(UPDATED_ROI)
            .commityAmt(UPDATED_COMMITY_AMT)
            .commityRoi(UPDATED_COMMITY_ROI)
            .sanctionAmt(UPDATED_SECTION_AMT)
            .sanctionRoi(UPDATED_SENCTION_ROI)
            .year(UPDATED_YEAR)
            //            .assignedTo(UPDATED_ASSIGNED_TO)
            //            .assignedFrom(UPDATED_ASSIGNED_FROM)
            .securityDocUrl(UPDATED_SECURITY_DOC_URL)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .margin(UPDATED_MARGIN)
            .securityOffered(UPDATED_SECURITY_OFFERED);
        //            .freeField3(UPDATED_FREE_FIELD_3)
        //            .freeField4(UPDATED_FREE_FIELD_4)
        //            .freeField5(UPDATED_FREE_FIELD_5)
        //            .freeField6(UPDATED_FREE_FIELD_6)
        //            .freeField7(UPDATED_FREE_FIELD_7);

        restLoanApplicationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanApplications.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanApplications))
            )
            .andExpect(status().isOk());

        // Validate the LoanApplications in the database
        List<LoanApplications> loanApplicationsList = loanApplicationsRepository.findAll();
        assertThat(loanApplicationsList).hasSize(databaseSizeBeforeUpdate);
        LoanApplications testLoanApplications = loanApplicationsList.get(loanApplicationsList.size() - 1);
        assertThat(testLoanApplications.getApplicationNo()).isEqualTo(UPDATED_APPLICATION_NO);
        assertThat(testLoanApplications.getDemandAmount()).isEqualTo(UPDATED_DEMAND_AMOUNT);
        assertThat(testLoanApplications.getLoanPurpose()).isEqualTo(UPDATED_LOAN_PURPOSE);
        assertThat(testLoanApplications.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoanApplications.getDemandedLandAreaInHector()).isEqualTo(UPDATED_DEMANDED_LAND_AREA_IN_HECTOR);
        assertThat(testLoanApplications.getSeasonOfCropLoan()).isEqualTo(UPDATED_SEASON_OF_CROP_LOAN);
        assertThat(testLoanApplications.getLoanSteps()).isEqualTo(UPDATED_LOAN_STEPS);
        assertThat(testLoanApplications.getIsInsured()).isEqualTo(UPDATED_IS_INSURED);
        assertThat(testLoanApplications.getLoanBenefitingArea()).isEqualTo(UPDATED_LOAN_BENEFITING_AREA);
        assertThat(testLoanApplications.getCostOfInvestment()).isEqualTo(UPDATED_COST_OF_INVESTMENT);
        assertThat(testLoanApplications.getMortgageDeedNo()).isEqualTo(UPDATED_MORTGAGE_DEED_NO);
        assertThat(testLoanApplications.getMortgageDate()).isEqualTo(UPDATED_MORTGAGE_DATE);
        assertThat(testLoanApplications.getExtentMorgageValue()).isEqualTo(UPDATED_EXTENT_MORGAGE_VALUE);
        assertThat(testLoanApplications.getDownPaymentAmt()).isEqualTo(UPDATED_DOWN_PAYMENT_AMT);
        assertThat(testLoanApplications.getLtvRatio()).isEqualTo(UPDATED_LTV_RATIO);
        assertThat(testLoanApplications.getProcessingFee()).isEqualTo(UPDATED_PROCESSING_FEE);
        assertThat(testLoanApplications.getPenalInterest()).isEqualTo(UPDATED_PENAL_INTEREST);
        assertThat(testLoanApplications.getMoratorium()).isEqualTo(UPDATED_MORATORIUM);
        assertThat(testLoanApplications.getRoi()).isEqualTo(UPDATED_ROI);
        assertThat(testLoanApplications.getCommityAmt()).isEqualTo(UPDATED_COMMITY_AMT);
        assertThat(testLoanApplications.getCommityRoi()).isEqualTo(UPDATED_COMMITY_ROI);
        assertThat(testLoanApplications.getSanctionAmt()).isEqualTo(UPDATED_SECTION_AMT);
        assertThat(testLoanApplications.getSanctionRoi()).isEqualTo(UPDATED_SENCTION_ROI);
        assertThat(testLoanApplications.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLoanApplications.getAssignedTo()).isEqualTo(UPDATED_ASSIGNED_TO);
        assertThat(testLoanApplications.getAssignedFrom()).isEqualTo(UPDATED_ASSIGNED_FROM);
        assertThat(testLoanApplications.getSecurityDocUrl()).isEqualTo(UPDATED_SECURITY_DOC_URL);
        assertThat(testLoanApplications.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanApplications.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanApplications.getMargin()).isEqualTo(UPDATED_MARGIN);
        assertThat(testLoanApplications.getSecurityOffered()).isEqualTo(UPDATED_SECURITY_OFFERED);
        //        assertThat(testLoanApplications.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        //        assertThat(testLoanApplications.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        //        assertThat(testLoanApplications.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        //        assertThat(testLoanApplications.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
        //        assertThat(testLoanApplications.getFreeField7()).isEqualTo(UPDATED_FREE_FIELD_7);
    }

    @Test
    @Transactional
    void patchNonExistingLoanApplications() throws Exception {
        int databaseSizeBeforeUpdate = loanApplicationsRepository.findAll().size();
        loanApplications.setId(count.incrementAndGet());

        // Create the LoanApplications
        LoanApplicationsDTO loanApplicationsDTO = loanApplicationsMapper.toDto(loanApplications);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanApplicationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loanApplicationsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanApplicationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanApplications in the database
        List<LoanApplications> loanApplicationsList = loanApplicationsRepository.findAll();
        assertThat(loanApplicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoanApplications() throws Exception {
        int databaseSizeBeforeUpdate = loanApplicationsRepository.findAll().size();
        loanApplications.setId(count.incrementAndGet());

        // Create the LoanApplications
        LoanApplicationsDTO loanApplicationsDTO = loanApplicationsMapper.toDto(loanApplications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanApplicationsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanApplicationsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanApplications in the database
        List<LoanApplications> loanApplicationsList = loanApplicationsRepository.findAll();
        assertThat(loanApplicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoanApplications() throws Exception {
        int databaseSizeBeforeUpdate = loanApplicationsRepository.findAll().size();
        loanApplications.setId(count.incrementAndGet());

        // Create the LoanApplications
        LoanApplicationsDTO loanApplicationsDTO = loanApplicationsMapper.toDto(loanApplications);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanApplicationsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanApplicationsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanApplications in the database
        List<LoanApplications> loanApplicationsList = loanApplicationsRepository.findAll();
        assertThat(loanApplicationsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoanApplications() throws Exception {
        // Initialize the database
        loanApplicationsRepository.saveAndFlush(loanApplications);

        int databaseSizeBeforeDelete = loanApplicationsRepository.findAll().size();

        // Delete the loanApplications
        restLoanApplicationsMockMvc
            .perform(delete(ENTITY_API_URL_ID, loanApplications.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoanApplications> loanApplicationsList = loanApplicationsRepository.findAll();
        assertThat(loanApplicationsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
