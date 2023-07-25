package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.LoanAccount;
import com.techvg.los.domain.LoanApplications;
import com.techvg.los.domain.Member;
import com.techvg.los.domain.Product;
import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.domain.enumeration.NpaClassification;
import com.techvg.los.repository.LoanAccountRepository;
import com.techvg.los.service.criteria.LoanAccountCriteria;
import com.techvg.los.service.dto.LoanAccountDTO;
import com.techvg.los.service.mapper.LoanAccountMapper;
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
 * Integration tests for the {@link LoanAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoanAccountResourceIT {

    private static final Double DEFAULT_LOAN_AMOUNT = 1D;
    private static final Double UPDATED_LOAN_AMOUNT = 2D;
    private static final Double SMALLER_LOAN_AMOUNT = 1D - 1D;

    private static final String DEFAULT_APPLICATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_NO = "BBBBBBBBBB";

    private static final LoanStatus DEFAULT_STATUS = LoanStatus.DRAFT;
    private static final LoanStatus UPDATED_STATUS = LoanStatus.APPLIED;

    private static final Instant DEFAULT_LOAN_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOAN_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LOAN_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOAN_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LOAN_PLANNED_CLOSURE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOAN_PLANNED_CLOSURE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LOAN_CLOSER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LOAN_CLOSER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_EMI_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EMI_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final NpaClassification DEFAULT_LOAN_NPA_CLASS = NpaClassification.SUB_STANDARD_ASSESTS;
    private static final NpaClassification UPDATED_LOAN_NPA_CLASS = NpaClassification.DOUBTFUL_1;

    private static final String DEFAULT_PARENT_ACC_HEAD_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_ACC_HEAD_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LOAN_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOAN_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_DISBURSEMENT_AMT = 1D;
    private static final Double UPDATED_DISBURSEMENT_AMT = 2D;
    private static final Double SMALLER_DISBURSEMENT_AMT = 1D - 1D;

    private static final String DEFAULT_DISBURSEMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DISBURSEMENT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_REPAYMENT_PERIOD = "AAAAAAAAAA";
    private static final String UPDATED_REPAYMENT_PERIOD = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final Double DEFAULT_PROCESSING_FEE = 1D;
    private static final Double UPDATED_PROCESSING_FEE = 2D;
    private static final Double SMALLER_PROCESSING_FEE = 1D - 1D;

    private static final String DEFAULT_MORATORIUM = "AAAAAAAAAA";
    private static final String UPDATED_MORATORIUM = "BBBBBBBBBB";

    private static final Double DEFAULT_ROI = 1D;
    private static final Double UPDATED_ROI = 2D;
    private static final Double SMALLER_ROI = 1D - 1D;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_4 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_5 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_5 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_6 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_6 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/loan-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoanAccountRepository loanAccountRepository;

    @Autowired
    private LoanAccountMapper loanAccountMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoanAccountMockMvc;

    private LoanAccount loanAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanAccount createEntity(EntityManager em) {
        LoanAccount loanAccount = new LoanAccount()
            .loanAmount(DEFAULT_LOAN_AMOUNT)
            //            .applicationNo(DEFAULT_APPLICATION_NO)
            .status(DEFAULT_STATUS)
            .loanStartDate(DEFAULT_LOAN_START_DATE)
            .loanEndDate(DEFAULT_LOAN_END_DATE)
            .loanPlannedClosureDate(DEFAULT_LOAN_PLANNED_CLOSURE_DATE)
            .loanCloserDate(DEFAULT_LOAN_CLOSER_DATE)
            .emiStartDate(DEFAULT_EMI_START_DATE)
            .loanNpaClass(DEFAULT_LOAN_NPA_CLASS)
            .parentAccHeadCode(DEFAULT_PARENT_ACC_HEAD_CODE)
            .loanAccountName(DEFAULT_LOAN_ACCOUNT_NAME)
            .disbursementAmt(DEFAULT_DISBURSEMENT_AMT)
            .disbursementStatus(DEFAULT_DISBURSEMENT_STATUS)
            .repaymentPeriod(DEFAULT_REPAYMENT_PERIOD)
            .year(DEFAULT_YEAR)
            .processingFee(DEFAULT_PROCESSING_FEE)
            .moratorium(DEFAULT_MORATORIUM)
            .roi(DEFAULT_ROI)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5)
            .freeField6(DEFAULT_FREE_FIELD_6);
        return loanAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanAccount createUpdatedEntity(EntityManager em) {
        LoanAccount loanAccount = new LoanAccount()
            .loanAmount(UPDATED_LOAN_AMOUNT)
            //            .applicationNo(UPDATED_APPLICATION_NO)
            .status(UPDATED_STATUS)
            .loanStartDate(UPDATED_LOAN_START_DATE)
            .loanEndDate(UPDATED_LOAN_END_DATE)
            .loanPlannedClosureDate(UPDATED_LOAN_PLANNED_CLOSURE_DATE)
            .loanCloserDate(UPDATED_LOAN_CLOSER_DATE)
            .emiStartDate(UPDATED_EMI_START_DATE)
            .loanNpaClass(UPDATED_LOAN_NPA_CLASS)
            .parentAccHeadCode(UPDATED_PARENT_ACC_HEAD_CODE)
            .loanAccountName(UPDATED_LOAN_ACCOUNT_NAME)
            .disbursementAmt(UPDATED_DISBURSEMENT_AMT)
            .disbursementStatus(UPDATED_DISBURSEMENT_STATUS)
            .repaymentPeriod(UPDATED_REPAYMENT_PERIOD)
            .year(UPDATED_YEAR)
            .processingFee(UPDATED_PROCESSING_FEE)
            .moratorium(UPDATED_MORATORIUM)
            .roi(UPDATED_ROI)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        return loanAccount;
    }

    @BeforeEach
    public void initTest() {
        loanAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createLoanAccount() throws Exception {
        int databaseSizeBeforeCreate = loanAccountRepository.findAll().size();
        // Create the LoanAccount
        LoanAccountDTO loanAccountDTO = loanAccountMapper.toDto(loanAccount);
        restLoanAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanAccountDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LoanAccount in the database
        List<LoanAccount> loanAccountList = loanAccountRepository.findAll();
        assertThat(loanAccountList).hasSize(databaseSizeBeforeCreate + 1);
        LoanAccount testLoanAccount = loanAccountList.get(loanAccountList.size() - 1);
        assertThat(testLoanAccount.getLoanAmount()).isEqualTo(DEFAULT_LOAN_AMOUNT);
        //        assertThat(testLoanAccount.getApplicationNo()).isEqualTo(DEFAULT_APPLICATION_NO);
        assertThat(testLoanAccount.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testLoanAccount.getLoanStartDate()).isEqualTo(DEFAULT_LOAN_START_DATE);
        assertThat(testLoanAccount.getLoanEndDate()).isEqualTo(DEFAULT_LOAN_END_DATE);
        assertThat(testLoanAccount.getLoanPlannedClosureDate()).isEqualTo(DEFAULT_LOAN_PLANNED_CLOSURE_DATE);
        assertThat(testLoanAccount.getLoanCloserDate()).isEqualTo(DEFAULT_LOAN_CLOSER_DATE);
        assertThat(testLoanAccount.getEmiStartDate()).isEqualTo(DEFAULT_EMI_START_DATE);
        assertThat(testLoanAccount.getLoanNpaClass()).isEqualTo(DEFAULT_LOAN_NPA_CLASS);
        assertThat(testLoanAccount.getParentAccHeadCode()).isEqualTo(DEFAULT_PARENT_ACC_HEAD_CODE);
        assertThat(testLoanAccount.getLoanAccountName()).isEqualTo(DEFAULT_LOAN_ACCOUNT_NAME);
        assertThat(testLoanAccount.getDisbursementAmt()).isEqualTo(DEFAULT_DISBURSEMENT_AMT);
        assertThat(testLoanAccount.getDisbursementStatus()).isEqualTo(DEFAULT_DISBURSEMENT_STATUS);
        assertThat(testLoanAccount.getRepaymentPeriod()).isEqualTo(DEFAULT_REPAYMENT_PERIOD);
        assertThat(testLoanAccount.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testLoanAccount.getProcessingFee()).isEqualTo(DEFAULT_PROCESSING_FEE);
        assertThat(testLoanAccount.getMoratorium()).isEqualTo(DEFAULT_MORATORIUM);
        assertThat(testLoanAccount.getRoi()).isEqualTo(DEFAULT_ROI);
        assertThat(testLoanAccount.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLoanAccount.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLoanAccount.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLoanAccount.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testLoanAccount.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testLoanAccount.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testLoanAccount.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testLoanAccount.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createLoanAccountWithExistingId() throws Exception {
        // Create the LoanAccount with an existing ID
        loanAccount.setId(1L);
        LoanAccountDTO loanAccountDTO = loanAccountMapper.toDto(loanAccount);

        int databaseSizeBeforeCreate = loanAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanAccount in the database
        List<LoanAccount> loanAccountList = loanAccountRepository.findAll();
        assertThat(loanAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoanAccounts() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList
        restLoanAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].applicationNo").value(hasItem(DEFAULT_APPLICATION_NO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].loanStartDate").value(hasItem(DEFAULT_LOAN_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanEndDate").value(hasItem(DEFAULT_LOAN_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanPlannedClosureDate").value(hasItem(DEFAULT_LOAN_PLANNED_CLOSURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanCloserDate").value(hasItem(DEFAULT_LOAN_CLOSER_DATE.toString())))
            .andExpect(jsonPath("$.[*].emiStartDate").value(hasItem(DEFAULT_EMI_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanNpaClass").value(hasItem(DEFAULT_LOAN_NPA_CLASS.toString())))
            .andExpect(jsonPath("$.[*].parentAccHeadCode").value(hasItem(DEFAULT_PARENT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].loanAccountName").value(hasItem(DEFAULT_LOAN_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].disbursementAmt").value(hasItem(DEFAULT_DISBURSEMENT_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].disbursementStatus").value(hasItem(DEFAULT_DISBURSEMENT_STATUS)))
            .andExpect(jsonPath("$.[*].repaymentPeriod").value(hasItem(DEFAULT_REPAYMENT_PERIOD)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].processingFee").value(hasItem(DEFAULT_PROCESSING_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].moratorium").value(hasItem(DEFAULT_MORATORIUM)))
            .andExpect(jsonPath("$.[*].roi").value(hasItem(DEFAULT_ROI.doubleValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));
    }

    @Test
    @Transactional
    void getLoanAccount() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get the loanAccount
        restLoanAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, loanAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loanAccount.getId().intValue()))
            .andExpect(jsonPath("$.loanAmount").value(DEFAULT_LOAN_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.applicationNo").value(DEFAULT_APPLICATION_NO))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.loanStartDate").value(DEFAULT_LOAN_START_DATE.toString()))
            .andExpect(jsonPath("$.loanEndDate").value(DEFAULT_LOAN_END_DATE.toString()))
            .andExpect(jsonPath("$.loanPlannedClosureDate").value(DEFAULT_LOAN_PLANNED_CLOSURE_DATE.toString()))
            .andExpect(jsonPath("$.loanCloserDate").value(DEFAULT_LOAN_CLOSER_DATE.toString()))
            .andExpect(jsonPath("$.emiStartDate").value(DEFAULT_EMI_START_DATE.toString()))
            .andExpect(jsonPath("$.loanNpaClass").value(DEFAULT_LOAN_NPA_CLASS.toString()))
            .andExpect(jsonPath("$.parentAccHeadCode").value(DEFAULT_PARENT_ACC_HEAD_CODE))
            .andExpect(jsonPath("$.loanAccountName").value(DEFAULT_LOAN_ACCOUNT_NAME))
            .andExpect(jsonPath("$.disbursementAmt").value(DEFAULT_DISBURSEMENT_AMT.doubleValue()))
            .andExpect(jsonPath("$.disbursementStatus").value(DEFAULT_DISBURSEMENT_STATUS))
            .andExpect(jsonPath("$.repaymentPeriod").value(DEFAULT_REPAYMENT_PERIOD))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.processingFee").value(DEFAULT_PROCESSING_FEE.doubleValue()))
            .andExpect(jsonPath("$.moratorium").value(DEFAULT_MORATORIUM))
            .andExpect(jsonPath("$.roi").value(DEFAULT_ROI.doubleValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.freeField5").value(DEFAULT_FREE_FIELD_5))
            .andExpect(jsonPath("$.freeField6").value(DEFAULT_FREE_FIELD_6));
    }

    @Test
    @Transactional
    void getLoanAccountsByIdFiltering() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        Long id = loanAccount.getId();

        defaultLoanAccountShouldBeFound("id.equals=" + id);
        defaultLoanAccountShouldNotBeFound("id.notEquals=" + id);

        defaultLoanAccountShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLoanAccountShouldNotBeFound("id.greaterThan=" + id);

        defaultLoanAccountShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLoanAccountShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanAmount equals to DEFAULT_LOAN_AMOUNT
        defaultLoanAccountShouldBeFound("loanAmount.equals=" + DEFAULT_LOAN_AMOUNT);

        // Get all the loanAccountList where loanAmount equals to UPDATED_LOAN_AMOUNT
        defaultLoanAccountShouldNotBeFound("loanAmount.equals=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanAmountIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanAmount in DEFAULT_LOAN_AMOUNT or UPDATED_LOAN_AMOUNT
        defaultLoanAccountShouldBeFound("loanAmount.in=" + DEFAULT_LOAN_AMOUNT + "," + UPDATED_LOAN_AMOUNT);

        // Get all the loanAccountList where loanAmount equals to UPDATED_LOAN_AMOUNT
        defaultLoanAccountShouldNotBeFound("loanAmount.in=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanAmount is not null
        defaultLoanAccountShouldBeFound("loanAmount.specified=true");

        // Get all the loanAccountList where loanAmount is null
        defaultLoanAccountShouldNotBeFound("loanAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanAmount is greater than or equal to DEFAULT_LOAN_AMOUNT
        defaultLoanAccountShouldBeFound("loanAmount.greaterThanOrEqual=" + DEFAULT_LOAN_AMOUNT);

        // Get all the loanAccountList where loanAmount is greater than or equal to UPDATED_LOAN_AMOUNT
        defaultLoanAccountShouldNotBeFound("loanAmount.greaterThanOrEqual=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanAmount is less than or equal to DEFAULT_LOAN_AMOUNT
        defaultLoanAccountShouldBeFound("loanAmount.lessThanOrEqual=" + DEFAULT_LOAN_AMOUNT);

        // Get all the loanAccountList where loanAmount is less than or equal to SMALLER_LOAN_AMOUNT
        defaultLoanAccountShouldNotBeFound("loanAmount.lessThanOrEqual=" + SMALLER_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanAmount is less than DEFAULT_LOAN_AMOUNT
        defaultLoanAccountShouldNotBeFound("loanAmount.lessThan=" + DEFAULT_LOAN_AMOUNT);

        // Get all the loanAccountList where loanAmount is less than UPDATED_LOAN_AMOUNT
        defaultLoanAccountShouldBeFound("loanAmount.lessThan=" + UPDATED_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanAmount is greater than DEFAULT_LOAN_AMOUNT
        defaultLoanAccountShouldNotBeFound("loanAmount.greaterThan=" + DEFAULT_LOAN_AMOUNT);

        // Get all the loanAccountList where loanAmount is greater than SMALLER_LOAN_AMOUNT
        defaultLoanAccountShouldBeFound("loanAmount.greaterThan=" + SMALLER_LOAN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByApplicationNoIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where applicationNo equals to DEFAULT_APPLICATION_NO
        defaultLoanAccountShouldBeFound("applicationNo.equals=" + DEFAULT_APPLICATION_NO);

        // Get all the loanAccountList where applicationNo equals to UPDATED_APPLICATION_NO
        defaultLoanAccountShouldNotBeFound("applicationNo.equals=" + UPDATED_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByApplicationNoIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where applicationNo in DEFAULT_APPLICATION_NO or UPDATED_APPLICATION_NO
        defaultLoanAccountShouldBeFound("applicationNo.in=" + DEFAULT_APPLICATION_NO + "," + UPDATED_APPLICATION_NO);

        // Get all the loanAccountList where applicationNo equals to UPDATED_APPLICATION_NO
        defaultLoanAccountShouldNotBeFound("applicationNo.in=" + UPDATED_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByApplicationNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where applicationNo is not null
        defaultLoanAccountShouldBeFound("applicationNo.specified=true");

        // Get all the loanAccountList where applicationNo is null
        defaultLoanAccountShouldNotBeFound("applicationNo.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByApplicationNoContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where applicationNo contains DEFAULT_APPLICATION_NO
        defaultLoanAccountShouldBeFound("applicationNo.contains=" + DEFAULT_APPLICATION_NO);

        // Get all the loanAccountList where applicationNo contains UPDATED_APPLICATION_NO
        defaultLoanAccountShouldNotBeFound("applicationNo.contains=" + UPDATED_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByApplicationNoNotContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where applicationNo does not contain DEFAULT_APPLICATION_NO
        defaultLoanAccountShouldNotBeFound("applicationNo.doesNotContain=" + DEFAULT_APPLICATION_NO);

        // Get all the loanAccountList where applicationNo does not contain UPDATED_APPLICATION_NO
        defaultLoanAccountShouldBeFound("applicationNo.doesNotContain=" + UPDATED_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where status equals to DEFAULT_STATUS
        defaultLoanAccountShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the loanAccountList where status equals to UPDATED_STATUS
        defaultLoanAccountShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultLoanAccountShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the loanAccountList where status equals to UPDATED_STATUS
        defaultLoanAccountShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where status is not null
        defaultLoanAccountShouldBeFound("status.specified=true");

        // Get all the loanAccountList where status is null
        defaultLoanAccountShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanStartDate equals to DEFAULT_LOAN_START_DATE
        defaultLoanAccountShouldBeFound("loanStartDate.equals=" + DEFAULT_LOAN_START_DATE);

        // Get all the loanAccountList where loanStartDate equals to UPDATED_LOAN_START_DATE
        defaultLoanAccountShouldNotBeFound("loanStartDate.equals=" + UPDATED_LOAN_START_DATE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanStartDate in DEFAULT_LOAN_START_DATE or UPDATED_LOAN_START_DATE
        defaultLoanAccountShouldBeFound("loanStartDate.in=" + DEFAULT_LOAN_START_DATE + "," + UPDATED_LOAN_START_DATE);

        // Get all the loanAccountList where loanStartDate equals to UPDATED_LOAN_START_DATE
        defaultLoanAccountShouldNotBeFound("loanStartDate.in=" + UPDATED_LOAN_START_DATE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanStartDate is not null
        defaultLoanAccountShouldBeFound("loanStartDate.specified=true");

        // Get all the loanAccountList where loanStartDate is null
        defaultLoanAccountShouldNotBeFound("loanStartDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanEndDate equals to DEFAULT_LOAN_END_DATE
        defaultLoanAccountShouldBeFound("loanEndDate.equals=" + DEFAULT_LOAN_END_DATE);

        // Get all the loanAccountList where loanEndDate equals to UPDATED_LOAN_END_DATE
        defaultLoanAccountShouldNotBeFound("loanEndDate.equals=" + UPDATED_LOAN_END_DATE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanEndDate in DEFAULT_LOAN_END_DATE or UPDATED_LOAN_END_DATE
        defaultLoanAccountShouldBeFound("loanEndDate.in=" + DEFAULT_LOAN_END_DATE + "," + UPDATED_LOAN_END_DATE);

        // Get all the loanAccountList where loanEndDate equals to UPDATED_LOAN_END_DATE
        defaultLoanAccountShouldNotBeFound("loanEndDate.in=" + UPDATED_LOAN_END_DATE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanEndDate is not null
        defaultLoanAccountShouldBeFound("loanEndDate.specified=true");

        // Get all the loanAccountList where loanEndDate is null
        defaultLoanAccountShouldNotBeFound("loanEndDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanPlannedClosureDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanPlannedClosureDate equals to DEFAULT_LOAN_PLANNED_CLOSURE_DATE
        defaultLoanAccountShouldBeFound("loanPlannedClosureDate.equals=" + DEFAULT_LOAN_PLANNED_CLOSURE_DATE);

        // Get all the loanAccountList where loanPlannedClosureDate equals to UPDATED_LOAN_PLANNED_CLOSURE_DATE
        defaultLoanAccountShouldNotBeFound("loanPlannedClosureDate.equals=" + UPDATED_LOAN_PLANNED_CLOSURE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanPlannedClosureDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanPlannedClosureDate in DEFAULT_LOAN_PLANNED_CLOSURE_DATE or UPDATED_LOAN_PLANNED_CLOSURE_DATE
        defaultLoanAccountShouldBeFound(
            "loanPlannedClosureDate.in=" + DEFAULT_LOAN_PLANNED_CLOSURE_DATE + "," + UPDATED_LOAN_PLANNED_CLOSURE_DATE
        );

        // Get all the loanAccountList where loanPlannedClosureDate equals to UPDATED_LOAN_PLANNED_CLOSURE_DATE
        defaultLoanAccountShouldNotBeFound("loanPlannedClosureDate.in=" + UPDATED_LOAN_PLANNED_CLOSURE_DATE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanPlannedClosureDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanPlannedClosureDate is not null
        defaultLoanAccountShouldBeFound("loanPlannedClosureDate.specified=true");

        // Get all the loanAccountList where loanPlannedClosureDate is null
        defaultLoanAccountShouldNotBeFound("loanPlannedClosureDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanCloserDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanCloserDate equals to DEFAULT_LOAN_CLOSER_DATE
        defaultLoanAccountShouldBeFound("loanCloserDate.equals=" + DEFAULT_LOAN_CLOSER_DATE);

        // Get all the loanAccountList where loanCloserDate equals to UPDATED_LOAN_CLOSER_DATE
        defaultLoanAccountShouldNotBeFound("loanCloserDate.equals=" + UPDATED_LOAN_CLOSER_DATE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanCloserDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanCloserDate in DEFAULT_LOAN_CLOSER_DATE or UPDATED_LOAN_CLOSER_DATE
        defaultLoanAccountShouldBeFound("loanCloserDate.in=" + DEFAULT_LOAN_CLOSER_DATE + "," + UPDATED_LOAN_CLOSER_DATE);

        // Get all the loanAccountList where loanCloserDate equals to UPDATED_LOAN_CLOSER_DATE
        defaultLoanAccountShouldNotBeFound("loanCloserDate.in=" + UPDATED_LOAN_CLOSER_DATE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanCloserDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanCloserDate is not null
        defaultLoanAccountShouldBeFound("loanCloserDate.specified=true");

        // Get all the loanAccountList where loanCloserDate is null
        defaultLoanAccountShouldNotBeFound("loanCloserDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByEmiStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where emiStartDate equals to DEFAULT_EMI_START_DATE
        defaultLoanAccountShouldBeFound("emiStartDate.equals=" + DEFAULT_EMI_START_DATE);

        // Get all the loanAccountList where emiStartDate equals to UPDATED_EMI_START_DATE
        defaultLoanAccountShouldNotBeFound("emiStartDate.equals=" + UPDATED_EMI_START_DATE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByEmiStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where emiStartDate in DEFAULT_EMI_START_DATE or UPDATED_EMI_START_DATE
        defaultLoanAccountShouldBeFound("emiStartDate.in=" + DEFAULT_EMI_START_DATE + "," + UPDATED_EMI_START_DATE);

        // Get all the loanAccountList where emiStartDate equals to UPDATED_EMI_START_DATE
        defaultLoanAccountShouldNotBeFound("emiStartDate.in=" + UPDATED_EMI_START_DATE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByEmiStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where emiStartDate is not null
        defaultLoanAccountShouldBeFound("emiStartDate.specified=true");

        // Get all the loanAccountList where emiStartDate is null
        defaultLoanAccountShouldNotBeFound("emiStartDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanNpaClassIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanNpaClass equals to DEFAULT_LOAN_NPA_CLASS
        defaultLoanAccountShouldBeFound("loanNpaClass.equals=" + DEFAULT_LOAN_NPA_CLASS);

        // Get all the loanAccountList where loanNpaClass equals to UPDATED_LOAN_NPA_CLASS
        defaultLoanAccountShouldNotBeFound("loanNpaClass.equals=" + UPDATED_LOAN_NPA_CLASS);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanNpaClassIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanNpaClass in DEFAULT_LOAN_NPA_CLASS or UPDATED_LOAN_NPA_CLASS
        defaultLoanAccountShouldBeFound("loanNpaClass.in=" + DEFAULT_LOAN_NPA_CLASS + "," + UPDATED_LOAN_NPA_CLASS);

        // Get all the loanAccountList where loanNpaClass equals to UPDATED_LOAN_NPA_CLASS
        defaultLoanAccountShouldNotBeFound("loanNpaClass.in=" + UPDATED_LOAN_NPA_CLASS);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanNpaClassIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanNpaClass is not null
        defaultLoanAccountShouldBeFound("loanNpaClass.specified=true");

        // Get all the loanAccountList where loanNpaClass is null
        defaultLoanAccountShouldNotBeFound("loanNpaClass.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByParentAccHeadCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where parentAccHeadCode equals to DEFAULT_PARENT_ACC_HEAD_CODE
        defaultLoanAccountShouldBeFound("parentAccHeadCode.equals=" + DEFAULT_PARENT_ACC_HEAD_CODE);

        // Get all the loanAccountList where parentAccHeadCode equals to UPDATED_PARENT_ACC_HEAD_CODE
        defaultLoanAccountShouldNotBeFound("parentAccHeadCode.equals=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByParentAccHeadCodeIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where parentAccHeadCode in DEFAULT_PARENT_ACC_HEAD_CODE or UPDATED_PARENT_ACC_HEAD_CODE
        defaultLoanAccountShouldBeFound("parentAccHeadCode.in=" + DEFAULT_PARENT_ACC_HEAD_CODE + "," + UPDATED_PARENT_ACC_HEAD_CODE);

        // Get all the loanAccountList where parentAccHeadCode equals to UPDATED_PARENT_ACC_HEAD_CODE
        defaultLoanAccountShouldNotBeFound("parentAccHeadCode.in=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByParentAccHeadCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where parentAccHeadCode is not null
        defaultLoanAccountShouldBeFound("parentAccHeadCode.specified=true");

        // Get all the loanAccountList where parentAccHeadCode is null
        defaultLoanAccountShouldNotBeFound("parentAccHeadCode.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByParentAccHeadCodeContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where parentAccHeadCode contains DEFAULT_PARENT_ACC_HEAD_CODE
        defaultLoanAccountShouldBeFound("parentAccHeadCode.contains=" + DEFAULT_PARENT_ACC_HEAD_CODE);

        // Get all the loanAccountList where parentAccHeadCode contains UPDATED_PARENT_ACC_HEAD_CODE
        defaultLoanAccountShouldNotBeFound("parentAccHeadCode.contains=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByParentAccHeadCodeNotContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where parentAccHeadCode does not contain DEFAULT_PARENT_ACC_HEAD_CODE
        defaultLoanAccountShouldNotBeFound("parentAccHeadCode.doesNotContain=" + DEFAULT_PARENT_ACC_HEAD_CODE);

        // Get all the loanAccountList where parentAccHeadCode does not contain UPDATED_PARENT_ACC_HEAD_CODE
        defaultLoanAccountShouldBeFound("parentAccHeadCode.doesNotContain=" + UPDATED_PARENT_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanAccountName equals to DEFAULT_LOAN_ACCOUNT_NAME
        defaultLoanAccountShouldBeFound("loanAccountName.equals=" + DEFAULT_LOAN_ACCOUNT_NAME);

        // Get all the loanAccountList where loanAccountName equals to UPDATED_LOAN_ACCOUNT_NAME
        defaultLoanAccountShouldNotBeFound("loanAccountName.equals=" + UPDATED_LOAN_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanAccountName in DEFAULT_LOAN_ACCOUNT_NAME or UPDATED_LOAN_ACCOUNT_NAME
        defaultLoanAccountShouldBeFound("loanAccountName.in=" + DEFAULT_LOAN_ACCOUNT_NAME + "," + UPDATED_LOAN_ACCOUNT_NAME);

        // Get all the loanAccountList where loanAccountName equals to UPDATED_LOAN_ACCOUNT_NAME
        defaultLoanAccountShouldNotBeFound("loanAccountName.in=" + UPDATED_LOAN_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanAccountName is not null
        defaultLoanAccountShouldBeFound("loanAccountName.specified=true");

        // Get all the loanAccountList where loanAccountName is null
        defaultLoanAccountShouldNotBeFound("loanAccountName.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanAccountNameContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanAccountName contains DEFAULT_LOAN_ACCOUNT_NAME
        defaultLoanAccountShouldBeFound("loanAccountName.contains=" + DEFAULT_LOAN_ACCOUNT_NAME);

        // Get all the loanAccountList where loanAccountName contains UPDATED_LOAN_ACCOUNT_NAME
        defaultLoanAccountShouldNotBeFound("loanAccountName.contains=" + UPDATED_LOAN_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanAccountNameNotContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where loanAccountName does not contain DEFAULT_LOAN_ACCOUNT_NAME
        defaultLoanAccountShouldNotBeFound("loanAccountName.doesNotContain=" + DEFAULT_LOAN_ACCOUNT_NAME);

        // Get all the loanAccountList where loanAccountName does not contain UPDATED_LOAN_ACCOUNT_NAME
        defaultLoanAccountShouldBeFound("loanAccountName.doesNotContain=" + UPDATED_LOAN_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByDisbursementAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where disbursementAmt equals to DEFAULT_DISBURSEMENT_AMT
        defaultLoanAccountShouldBeFound("disbursementAmt.equals=" + DEFAULT_DISBURSEMENT_AMT);

        // Get all the loanAccountList where disbursementAmt equals to UPDATED_DISBURSEMENT_AMT
        defaultLoanAccountShouldNotBeFound("disbursementAmt.equals=" + UPDATED_DISBURSEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByDisbursementAmtIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where disbursementAmt in DEFAULT_DISBURSEMENT_AMT or UPDATED_DISBURSEMENT_AMT
        defaultLoanAccountShouldBeFound("disbursementAmt.in=" + DEFAULT_DISBURSEMENT_AMT + "," + UPDATED_DISBURSEMENT_AMT);

        // Get all the loanAccountList where disbursementAmt equals to UPDATED_DISBURSEMENT_AMT
        defaultLoanAccountShouldNotBeFound("disbursementAmt.in=" + UPDATED_DISBURSEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByDisbursementAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where disbursementAmt is not null
        defaultLoanAccountShouldBeFound("disbursementAmt.specified=true");

        // Get all the loanAccountList where disbursementAmt is null
        defaultLoanAccountShouldNotBeFound("disbursementAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByDisbursementAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where disbursementAmt is greater than or equal to DEFAULT_DISBURSEMENT_AMT
        defaultLoanAccountShouldBeFound("disbursementAmt.greaterThanOrEqual=" + DEFAULT_DISBURSEMENT_AMT);

        // Get all the loanAccountList where disbursementAmt is greater than or equal to UPDATED_DISBURSEMENT_AMT
        defaultLoanAccountShouldNotBeFound("disbursementAmt.greaterThanOrEqual=" + UPDATED_DISBURSEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByDisbursementAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where disbursementAmt is less than or equal to DEFAULT_DISBURSEMENT_AMT
        defaultLoanAccountShouldBeFound("disbursementAmt.lessThanOrEqual=" + DEFAULT_DISBURSEMENT_AMT);

        // Get all the loanAccountList where disbursementAmt is less than or equal to SMALLER_DISBURSEMENT_AMT
        defaultLoanAccountShouldNotBeFound("disbursementAmt.lessThanOrEqual=" + SMALLER_DISBURSEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByDisbursementAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where disbursementAmt is less than DEFAULT_DISBURSEMENT_AMT
        defaultLoanAccountShouldNotBeFound("disbursementAmt.lessThan=" + DEFAULT_DISBURSEMENT_AMT);

        // Get all the loanAccountList where disbursementAmt is less than UPDATED_DISBURSEMENT_AMT
        defaultLoanAccountShouldBeFound("disbursementAmt.lessThan=" + UPDATED_DISBURSEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByDisbursementAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where disbursementAmt is greater than DEFAULT_DISBURSEMENT_AMT
        defaultLoanAccountShouldNotBeFound("disbursementAmt.greaterThan=" + DEFAULT_DISBURSEMENT_AMT);

        // Get all the loanAccountList where disbursementAmt is greater than SMALLER_DISBURSEMENT_AMT
        defaultLoanAccountShouldBeFound("disbursementAmt.greaterThan=" + SMALLER_DISBURSEMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByDisbursementStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where disbursementStatus equals to DEFAULT_DISBURSEMENT_STATUS
        defaultLoanAccountShouldBeFound("disbursementStatus.equals=" + DEFAULT_DISBURSEMENT_STATUS);

        // Get all the loanAccountList where disbursementStatus equals to UPDATED_DISBURSEMENT_STATUS
        defaultLoanAccountShouldNotBeFound("disbursementStatus.equals=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByDisbursementStatusIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where disbursementStatus in DEFAULT_DISBURSEMENT_STATUS or UPDATED_DISBURSEMENT_STATUS
        defaultLoanAccountShouldBeFound("disbursementStatus.in=" + DEFAULT_DISBURSEMENT_STATUS + "," + UPDATED_DISBURSEMENT_STATUS);

        // Get all the loanAccountList where disbursementStatus equals to UPDATED_DISBURSEMENT_STATUS
        defaultLoanAccountShouldNotBeFound("disbursementStatus.in=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByDisbursementStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where disbursementStatus is not null
        defaultLoanAccountShouldBeFound("disbursementStatus.specified=true");

        // Get all the loanAccountList where disbursementStatus is null
        defaultLoanAccountShouldNotBeFound("disbursementStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByDisbursementStatusContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where disbursementStatus contains DEFAULT_DISBURSEMENT_STATUS
        defaultLoanAccountShouldBeFound("disbursementStatus.contains=" + DEFAULT_DISBURSEMENT_STATUS);

        // Get all the loanAccountList where disbursementStatus contains UPDATED_DISBURSEMENT_STATUS
        defaultLoanAccountShouldNotBeFound("disbursementStatus.contains=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByDisbursementStatusNotContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where disbursementStatus does not contain DEFAULT_DISBURSEMENT_STATUS
        defaultLoanAccountShouldNotBeFound("disbursementStatus.doesNotContain=" + DEFAULT_DISBURSEMENT_STATUS);

        // Get all the loanAccountList where disbursementStatus does not contain UPDATED_DISBURSEMENT_STATUS
        defaultLoanAccountShouldBeFound("disbursementStatus.doesNotContain=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByRepaymentPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where repaymentPeriod equals to DEFAULT_REPAYMENT_PERIOD
        defaultLoanAccountShouldBeFound("repaymentPeriod.equals=" + DEFAULT_REPAYMENT_PERIOD);

        // Get all the loanAccountList where repaymentPeriod equals to UPDATED_REPAYMENT_PERIOD
        defaultLoanAccountShouldNotBeFound("repaymentPeriod.equals=" + UPDATED_REPAYMENT_PERIOD);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByRepaymentPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where repaymentPeriod in DEFAULT_REPAYMENT_PERIOD or UPDATED_REPAYMENT_PERIOD
        defaultLoanAccountShouldBeFound("repaymentPeriod.in=" + DEFAULT_REPAYMENT_PERIOD + "," + UPDATED_REPAYMENT_PERIOD);

        // Get all the loanAccountList where repaymentPeriod equals to UPDATED_REPAYMENT_PERIOD
        defaultLoanAccountShouldNotBeFound("repaymentPeriod.in=" + UPDATED_REPAYMENT_PERIOD);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByRepaymentPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where repaymentPeriod is not null
        defaultLoanAccountShouldBeFound("repaymentPeriod.specified=true");

        // Get all the loanAccountList where repaymentPeriod is null
        defaultLoanAccountShouldNotBeFound("repaymentPeriod.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByRepaymentPeriodContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where repaymentPeriod contains DEFAULT_REPAYMENT_PERIOD
        defaultLoanAccountShouldBeFound("repaymentPeriod.contains=" + DEFAULT_REPAYMENT_PERIOD);

        // Get all the loanAccountList where repaymentPeriod contains UPDATED_REPAYMENT_PERIOD
        defaultLoanAccountShouldNotBeFound("repaymentPeriod.contains=" + UPDATED_REPAYMENT_PERIOD);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByRepaymentPeriodNotContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where repaymentPeriod does not contain DEFAULT_REPAYMENT_PERIOD
        defaultLoanAccountShouldNotBeFound("repaymentPeriod.doesNotContain=" + DEFAULT_REPAYMENT_PERIOD);

        // Get all the loanAccountList where repaymentPeriod does not contain UPDATED_REPAYMENT_PERIOD
        defaultLoanAccountShouldBeFound("repaymentPeriod.doesNotContain=" + UPDATED_REPAYMENT_PERIOD);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where year equals to DEFAULT_YEAR
        defaultLoanAccountShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the loanAccountList where year equals to UPDATED_YEAR
        defaultLoanAccountShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultLoanAccountShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the loanAccountList where year equals to UPDATED_YEAR
        defaultLoanAccountShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where year is not null
        defaultLoanAccountShouldBeFound("year.specified=true");

        // Get all the loanAccountList where year is null
        defaultLoanAccountShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByYearContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where year contains DEFAULT_YEAR
        defaultLoanAccountShouldBeFound("year.contains=" + DEFAULT_YEAR);

        // Get all the loanAccountList where year contains UPDATED_YEAR
        defaultLoanAccountShouldNotBeFound("year.contains=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByYearNotContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where year does not contain DEFAULT_YEAR
        defaultLoanAccountShouldNotBeFound("year.doesNotContain=" + DEFAULT_YEAR);

        // Get all the loanAccountList where year does not contain UPDATED_YEAR
        defaultLoanAccountShouldBeFound("year.doesNotContain=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByProcessingFeeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where processingFee equals to DEFAULT_PROCESSING_FEE
        defaultLoanAccountShouldBeFound("processingFee.equals=" + DEFAULT_PROCESSING_FEE);

        // Get all the loanAccountList where processingFee equals to UPDATED_PROCESSING_FEE
        defaultLoanAccountShouldNotBeFound("processingFee.equals=" + UPDATED_PROCESSING_FEE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByProcessingFeeIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where processingFee in DEFAULT_PROCESSING_FEE or UPDATED_PROCESSING_FEE
        defaultLoanAccountShouldBeFound("processingFee.in=" + DEFAULT_PROCESSING_FEE + "," + UPDATED_PROCESSING_FEE);

        // Get all the loanAccountList where processingFee equals to UPDATED_PROCESSING_FEE
        defaultLoanAccountShouldNotBeFound("processingFee.in=" + UPDATED_PROCESSING_FEE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByProcessingFeeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where processingFee is not null
        defaultLoanAccountShouldBeFound("processingFee.specified=true");

        // Get all the loanAccountList where processingFee is null
        defaultLoanAccountShouldNotBeFound("processingFee.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByProcessingFeeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where processingFee is greater than or equal to DEFAULT_PROCESSING_FEE
        defaultLoanAccountShouldBeFound("processingFee.greaterThanOrEqual=" + DEFAULT_PROCESSING_FEE);

        // Get all the loanAccountList where processingFee is greater than or equal to UPDATED_PROCESSING_FEE
        defaultLoanAccountShouldNotBeFound("processingFee.greaterThanOrEqual=" + UPDATED_PROCESSING_FEE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByProcessingFeeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where processingFee is less than or equal to DEFAULT_PROCESSING_FEE
        defaultLoanAccountShouldBeFound("processingFee.lessThanOrEqual=" + DEFAULT_PROCESSING_FEE);

        // Get all the loanAccountList where processingFee is less than or equal to SMALLER_PROCESSING_FEE
        defaultLoanAccountShouldNotBeFound("processingFee.lessThanOrEqual=" + SMALLER_PROCESSING_FEE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByProcessingFeeIsLessThanSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where processingFee is less than DEFAULT_PROCESSING_FEE
        defaultLoanAccountShouldNotBeFound("processingFee.lessThan=" + DEFAULT_PROCESSING_FEE);

        // Get all the loanAccountList where processingFee is less than UPDATED_PROCESSING_FEE
        defaultLoanAccountShouldBeFound("processingFee.lessThan=" + UPDATED_PROCESSING_FEE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByProcessingFeeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where processingFee is greater than DEFAULT_PROCESSING_FEE
        defaultLoanAccountShouldNotBeFound("processingFee.greaterThan=" + DEFAULT_PROCESSING_FEE);

        // Get all the loanAccountList where processingFee is greater than SMALLER_PROCESSING_FEE
        defaultLoanAccountShouldBeFound("processingFee.greaterThan=" + SMALLER_PROCESSING_FEE);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByMoratoriumIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where moratorium equals to DEFAULT_MORATORIUM
        defaultLoanAccountShouldBeFound("moratorium.equals=" + DEFAULT_MORATORIUM);

        // Get all the loanAccountList where moratorium equals to UPDATED_MORATORIUM
        defaultLoanAccountShouldNotBeFound("moratorium.equals=" + UPDATED_MORATORIUM);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByMoratoriumIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where moratorium in DEFAULT_MORATORIUM or UPDATED_MORATORIUM
        defaultLoanAccountShouldBeFound("moratorium.in=" + DEFAULT_MORATORIUM + "," + UPDATED_MORATORIUM);

        // Get all the loanAccountList where moratorium equals to UPDATED_MORATORIUM
        defaultLoanAccountShouldNotBeFound("moratorium.in=" + UPDATED_MORATORIUM);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByMoratoriumIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where moratorium is not null
        defaultLoanAccountShouldBeFound("moratorium.specified=true");

        // Get all the loanAccountList where moratorium is null
        defaultLoanAccountShouldNotBeFound("moratorium.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByMoratoriumContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where moratorium contains DEFAULT_MORATORIUM
        defaultLoanAccountShouldBeFound("moratorium.contains=" + DEFAULT_MORATORIUM);

        // Get all the loanAccountList where moratorium contains UPDATED_MORATORIUM
        defaultLoanAccountShouldNotBeFound("moratorium.contains=" + UPDATED_MORATORIUM);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByMoratoriumNotContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where moratorium does not contain DEFAULT_MORATORIUM
        defaultLoanAccountShouldNotBeFound("moratorium.doesNotContain=" + DEFAULT_MORATORIUM);

        // Get all the loanAccountList where moratorium does not contain UPDATED_MORATORIUM
        defaultLoanAccountShouldBeFound("moratorium.doesNotContain=" + UPDATED_MORATORIUM);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByRoiIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where roi equals to DEFAULT_ROI
        defaultLoanAccountShouldBeFound("roi.equals=" + DEFAULT_ROI);

        // Get all the loanAccountList where roi equals to UPDATED_ROI
        defaultLoanAccountShouldNotBeFound("roi.equals=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByRoiIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where roi in DEFAULT_ROI or UPDATED_ROI
        defaultLoanAccountShouldBeFound("roi.in=" + DEFAULT_ROI + "," + UPDATED_ROI);

        // Get all the loanAccountList where roi equals to UPDATED_ROI
        defaultLoanAccountShouldNotBeFound("roi.in=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByRoiIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where roi is not null
        defaultLoanAccountShouldBeFound("roi.specified=true");

        // Get all the loanAccountList where roi is null
        defaultLoanAccountShouldNotBeFound("roi.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByRoiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where roi is greater than or equal to DEFAULT_ROI
        defaultLoanAccountShouldBeFound("roi.greaterThanOrEqual=" + DEFAULT_ROI);

        // Get all the loanAccountList where roi is greater than or equal to UPDATED_ROI
        defaultLoanAccountShouldNotBeFound("roi.greaterThanOrEqual=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByRoiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where roi is less than or equal to DEFAULT_ROI
        defaultLoanAccountShouldBeFound("roi.lessThanOrEqual=" + DEFAULT_ROI);

        // Get all the loanAccountList where roi is less than or equal to SMALLER_ROI
        defaultLoanAccountShouldNotBeFound("roi.lessThanOrEqual=" + SMALLER_ROI);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByRoiIsLessThanSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where roi is less than DEFAULT_ROI
        defaultLoanAccountShouldNotBeFound("roi.lessThan=" + DEFAULT_ROI);

        // Get all the loanAccountList where roi is less than UPDATED_ROI
        defaultLoanAccountShouldBeFound("roi.lessThan=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByRoiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where roi is greater than DEFAULT_ROI
        defaultLoanAccountShouldNotBeFound("roi.greaterThan=" + DEFAULT_ROI);

        // Get all the loanAccountList where roi is greater than SMALLER_ROI
        defaultLoanAccountShouldBeFound("roi.greaterThan=" + SMALLER_ROI);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLoanAccountShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the loanAccountList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanAccountShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLoanAccountShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the loanAccountList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanAccountShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where lastModified is not null
        defaultLoanAccountShouldBeFound("lastModified.specified=true");

        // Get all the loanAccountList where lastModified is null
        defaultLoanAccountShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLoanAccountShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanAccountList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanAccountShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLoanAccountShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the loanAccountList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanAccountShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where lastModifiedBy is not null
        defaultLoanAccountShouldBeFound("lastModifiedBy.specified=true");

        // Get all the loanAccountList where lastModifiedBy is null
        defaultLoanAccountShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLoanAccountShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanAccountList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLoanAccountShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLoanAccountShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanAccountList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLoanAccountShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultLoanAccountShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanAccountList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanAccountShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultLoanAccountShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the loanAccountList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanAccountShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField1 is not null
        defaultLoanAccountShouldBeFound("freeField1.specified=true");

        // Get all the loanAccountList where freeField1 is null
        defaultLoanAccountShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultLoanAccountShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanAccountList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultLoanAccountShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultLoanAccountShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanAccountList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultLoanAccountShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultLoanAccountShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanAccountList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanAccountShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultLoanAccountShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the loanAccountList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanAccountShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField2 is not null
        defaultLoanAccountShouldBeFound("freeField2.specified=true");

        // Get all the loanAccountList where freeField2 is null
        defaultLoanAccountShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultLoanAccountShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanAccountList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultLoanAccountShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultLoanAccountShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanAccountList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultLoanAccountShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultLoanAccountShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanAccountList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanAccountShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultLoanAccountShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the loanAccountList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanAccountShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField3 is not null
        defaultLoanAccountShouldBeFound("freeField3.specified=true");

        // Get all the loanAccountList where freeField3 is null
        defaultLoanAccountShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultLoanAccountShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanAccountList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultLoanAccountShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultLoanAccountShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanAccountList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultLoanAccountShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultLoanAccountShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the loanAccountList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultLoanAccountShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultLoanAccountShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the loanAccountList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultLoanAccountShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField4 is not null
        defaultLoanAccountShouldBeFound("freeField4.specified=true");

        // Get all the loanAccountList where freeField4 is null
        defaultLoanAccountShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultLoanAccountShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the loanAccountList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultLoanAccountShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultLoanAccountShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the loanAccountList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultLoanAccountShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultLoanAccountShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the loanAccountList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultLoanAccountShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultLoanAccountShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the loanAccountList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultLoanAccountShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField5 is not null
        defaultLoanAccountShouldBeFound("freeField5.specified=true");

        // Get all the loanAccountList where freeField5 is null
        defaultLoanAccountShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultLoanAccountShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the loanAccountList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultLoanAccountShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultLoanAccountShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the loanAccountList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultLoanAccountShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultLoanAccountShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the loanAccountList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultLoanAccountShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultLoanAccountShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the loanAccountList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultLoanAccountShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField6 is not null
        defaultLoanAccountShouldBeFound("freeField6.specified=true");

        // Get all the loanAccountList where freeField6 is null
        defaultLoanAccountShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultLoanAccountShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the loanAccountList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultLoanAccountShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        // Get all the loanAccountList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultLoanAccountShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the loanAccountList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultLoanAccountShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanAccountsByLoanApplicationsIsEqualToSomething() throws Exception {
        LoanApplications loanApplications;
        if (TestUtil.findAll(em, LoanApplications.class).isEmpty()) {
            loanAccountRepository.saveAndFlush(loanAccount);
            loanApplications = LoanApplicationsResourceIT.createEntity(em);
        } else {
            loanApplications = TestUtil.findAll(em, LoanApplications.class).get(0);
        }
        em.persist(loanApplications);
        em.flush();
        loanAccount.setLoanApplications(loanApplications);
        loanAccountRepository.saveAndFlush(loanAccount);
        Long loanApplicationsId = loanApplications.getId();

        // Get all the loanAccountList where loanApplications equals to loanApplicationsId
        defaultLoanAccountShouldBeFound("loanApplicationsId.equals=" + loanApplicationsId);

        // Get all the loanAccountList where loanApplications equals to (loanApplicationsId + 1)
        defaultLoanAccountShouldNotBeFound("loanApplicationsId.equals=" + (loanApplicationsId + 1));
    }

    @Test
    @Transactional
    void getAllLoanAccountsByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            loanAccountRepository.saveAndFlush(loanAccount);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        loanAccount.setMember(member);
        loanAccountRepository.saveAndFlush(loanAccount);
        Long memberId = member.getId();

        // Get all the loanAccountList where member equals to memberId
        defaultLoanAccountShouldBeFound("memberId.equals=" + memberId);

        // Get all the loanAccountList where member equals to (memberId + 1)
        defaultLoanAccountShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    @Test
    @Transactional
    void getAllLoanAccountsByProductIsEqualToSomething() throws Exception {
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            loanAccountRepository.saveAndFlush(loanAccount);
            product = ProductResourceIT.createEntity(em);
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        loanAccount.setProduct(product);
        loanAccountRepository.saveAndFlush(loanAccount);
        Long productId = product.getId();

        // Get all the loanAccountList where product equals to productId
        defaultLoanAccountShouldBeFound("productId.equals=" + productId);

        // Get all the loanAccountList where product equals to (productId + 1)
        defaultLoanAccountShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLoanAccountShouldBeFound(String filter) throws Exception {
        restLoanAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].loanAmount").value(hasItem(DEFAULT_LOAN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].applicationNo").value(hasItem(DEFAULT_APPLICATION_NO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].loanStartDate").value(hasItem(DEFAULT_LOAN_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanEndDate").value(hasItem(DEFAULT_LOAN_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanPlannedClosureDate").value(hasItem(DEFAULT_LOAN_PLANNED_CLOSURE_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanCloserDate").value(hasItem(DEFAULT_LOAN_CLOSER_DATE.toString())))
            .andExpect(jsonPath("$.[*].emiStartDate").value(hasItem(DEFAULT_EMI_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].loanNpaClass").value(hasItem(DEFAULT_LOAN_NPA_CLASS.toString())))
            .andExpect(jsonPath("$.[*].parentAccHeadCode").value(hasItem(DEFAULT_PARENT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].loanAccountName").value(hasItem(DEFAULT_LOAN_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].disbursementAmt").value(hasItem(DEFAULT_DISBURSEMENT_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].disbursementStatus").value(hasItem(DEFAULT_DISBURSEMENT_STATUS)))
            .andExpect(jsonPath("$.[*].repaymentPeriod").value(hasItem(DEFAULT_REPAYMENT_PERIOD)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].processingFee").value(hasItem(DEFAULT_PROCESSING_FEE.doubleValue())))
            .andExpect(jsonPath("$.[*].moratorium").value(hasItem(DEFAULT_MORATORIUM)))
            .andExpect(jsonPath("$.[*].roi").value(hasItem(DEFAULT_ROI.doubleValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));

        // Check, that the count call also returns 1
        restLoanAccountMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLoanAccountShouldNotBeFound(String filter) throws Exception {
        restLoanAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLoanAccountMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLoanAccount() throws Exception {
        // Get the loanAccount
        restLoanAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLoanAccount() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        int databaseSizeBeforeUpdate = loanAccountRepository.findAll().size();

        // Update the loanAccount
        LoanAccount updatedLoanAccount = loanAccountRepository.findById(loanAccount.getId()).get();
        // Disconnect from session so that the updates on updatedLoanAccount are not directly saved in db
        em.detach(updatedLoanAccount);
        updatedLoanAccount
            .loanAmount(UPDATED_LOAN_AMOUNT)
            //            .applicationNo(UPDATED_APPLICATION_NO)
            .status(UPDATED_STATUS)
            .loanStartDate(UPDATED_LOAN_START_DATE)
            .loanEndDate(UPDATED_LOAN_END_DATE)
            .loanPlannedClosureDate(UPDATED_LOAN_PLANNED_CLOSURE_DATE)
            .loanCloserDate(UPDATED_LOAN_CLOSER_DATE)
            .emiStartDate(UPDATED_EMI_START_DATE)
            .loanNpaClass(UPDATED_LOAN_NPA_CLASS)
            .parentAccHeadCode(UPDATED_PARENT_ACC_HEAD_CODE)
            .loanAccountName(UPDATED_LOAN_ACCOUNT_NAME)
            .disbursementAmt(UPDATED_DISBURSEMENT_AMT)
            .disbursementStatus(UPDATED_DISBURSEMENT_STATUS)
            .repaymentPeriod(UPDATED_REPAYMENT_PERIOD)
            .year(UPDATED_YEAR)
            .processingFee(UPDATED_PROCESSING_FEE)
            .moratorium(UPDATED_MORATORIUM)
            .roi(UPDATED_ROI)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        LoanAccountDTO loanAccountDTO = loanAccountMapper.toDto(updatedLoanAccount);

        restLoanAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanAccountDTO))
            )
            .andExpect(status().isOk());

        // Validate the LoanAccount in the database
        List<LoanAccount> loanAccountList = loanAccountRepository.findAll();
        assertThat(loanAccountList).hasSize(databaseSizeBeforeUpdate);
        LoanAccount testLoanAccount = loanAccountList.get(loanAccountList.size() - 1);
        assertThat(testLoanAccount.getLoanAmount()).isEqualTo(UPDATED_LOAN_AMOUNT);
        //        assertThat(testLoanAccount.getApplicationNo()).isEqualTo(UPDATED_APPLICATION_NO);
        assertThat(testLoanAccount.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoanAccount.getLoanStartDate()).isEqualTo(UPDATED_LOAN_START_DATE);
        assertThat(testLoanAccount.getLoanEndDate()).isEqualTo(UPDATED_LOAN_END_DATE);
        assertThat(testLoanAccount.getLoanPlannedClosureDate()).isEqualTo(UPDATED_LOAN_PLANNED_CLOSURE_DATE);
        assertThat(testLoanAccount.getLoanCloserDate()).isEqualTo(UPDATED_LOAN_CLOSER_DATE);
        assertThat(testLoanAccount.getEmiStartDate()).isEqualTo(UPDATED_EMI_START_DATE);
        assertThat(testLoanAccount.getLoanNpaClass()).isEqualTo(UPDATED_LOAN_NPA_CLASS);
        assertThat(testLoanAccount.getParentAccHeadCode()).isEqualTo(UPDATED_PARENT_ACC_HEAD_CODE);
        assertThat(testLoanAccount.getLoanAccountName()).isEqualTo(UPDATED_LOAN_ACCOUNT_NAME);
        assertThat(testLoanAccount.getDisbursementAmt()).isEqualTo(UPDATED_DISBURSEMENT_AMT);
        assertThat(testLoanAccount.getDisbursementStatus()).isEqualTo(UPDATED_DISBURSEMENT_STATUS);
        assertThat(testLoanAccount.getRepaymentPeriod()).isEqualTo(UPDATED_REPAYMENT_PERIOD);
        assertThat(testLoanAccount.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLoanAccount.getProcessingFee()).isEqualTo(UPDATED_PROCESSING_FEE);
        assertThat(testLoanAccount.getMoratorium()).isEqualTo(UPDATED_MORATORIUM);
        assertThat(testLoanAccount.getRoi()).isEqualTo(UPDATED_ROI);
        assertThat(testLoanAccount.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanAccount.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanAccount.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanAccount.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanAccount.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testLoanAccount.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testLoanAccount.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testLoanAccount.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingLoanAccount() throws Exception {
        int databaseSizeBeforeUpdate = loanAccountRepository.findAll().size();
        loanAccount.setId(count.incrementAndGet());

        // Create the LoanAccount
        LoanAccountDTO loanAccountDTO = loanAccountMapper.toDto(loanAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanAccount in the database
        List<LoanAccount> loanAccountList = loanAccountRepository.findAll();
        assertThat(loanAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoanAccount() throws Exception {
        int databaseSizeBeforeUpdate = loanAccountRepository.findAll().size();
        loanAccount.setId(count.incrementAndGet());

        // Create the LoanAccount
        LoanAccountDTO loanAccountDTO = loanAccountMapper.toDto(loanAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanAccount in the database
        List<LoanAccount> loanAccountList = loanAccountRepository.findAll();
        assertThat(loanAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoanAccount() throws Exception {
        int databaseSizeBeforeUpdate = loanAccountRepository.findAll().size();
        loanAccount.setId(count.incrementAndGet());

        // Create the LoanAccount
        LoanAccountDTO loanAccountDTO = loanAccountMapper.toDto(loanAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanAccountMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanAccountDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanAccount in the database
        List<LoanAccount> loanAccountList = loanAccountRepository.findAll();
        assertThat(loanAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoanAccountWithPatch() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        int databaseSizeBeforeUpdate = loanAccountRepository.findAll().size();

        // Update the loanAccount using partial update
        LoanAccount partialUpdatedLoanAccount = new LoanAccount();
        partialUpdatedLoanAccount.setId(loanAccount.getId());

        partialUpdatedLoanAccount
            .loanAmount(UPDATED_LOAN_AMOUNT)
            .status(UPDATED_STATUS)
            .loanEndDate(UPDATED_LOAN_END_DATE)
            .loanPlannedClosureDate(UPDATED_LOAN_PLANNED_CLOSURE_DATE)
            .disbursementAmt(UPDATED_DISBURSEMENT_AMT)
            .disbursementStatus(UPDATED_DISBURSEMENT_STATUS)
            .repaymentPeriod(UPDATED_REPAYMENT_PERIOD)
            .moratorium(UPDATED_MORATORIUM)
            .lastModified(UPDATED_LAST_MODIFIED)
            .freeField4(UPDATED_FREE_FIELD_4);

        restLoanAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanAccount))
            )
            .andExpect(status().isOk());

        // Validate the LoanAccount in the database
        List<LoanAccount> loanAccountList = loanAccountRepository.findAll();
        assertThat(loanAccountList).hasSize(databaseSizeBeforeUpdate);
        LoanAccount testLoanAccount = loanAccountList.get(loanAccountList.size() - 1);
        assertThat(testLoanAccount.getLoanAmount()).isEqualTo(UPDATED_LOAN_AMOUNT);
        //        assertThat(testLoanAccount.getApplicationNo()).isEqualTo(DEFAULT_APPLICATION_NO);
        assertThat(testLoanAccount.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoanAccount.getLoanStartDate()).isEqualTo(DEFAULT_LOAN_START_DATE);
        assertThat(testLoanAccount.getLoanEndDate()).isEqualTo(UPDATED_LOAN_END_DATE);
        assertThat(testLoanAccount.getLoanPlannedClosureDate()).isEqualTo(UPDATED_LOAN_PLANNED_CLOSURE_DATE);
        assertThat(testLoanAccount.getLoanCloserDate()).isEqualTo(DEFAULT_LOAN_CLOSER_DATE);
        assertThat(testLoanAccount.getEmiStartDate()).isEqualTo(DEFAULT_EMI_START_DATE);
        assertThat(testLoanAccount.getLoanNpaClass()).isEqualTo(DEFAULT_LOAN_NPA_CLASS);
        assertThat(testLoanAccount.getParentAccHeadCode()).isEqualTo(DEFAULT_PARENT_ACC_HEAD_CODE);
        assertThat(testLoanAccount.getLoanAccountName()).isEqualTo(DEFAULT_LOAN_ACCOUNT_NAME);
        assertThat(testLoanAccount.getDisbursementAmt()).isEqualTo(UPDATED_DISBURSEMENT_AMT);
        assertThat(testLoanAccount.getDisbursementStatus()).isEqualTo(UPDATED_DISBURSEMENT_STATUS);
        assertThat(testLoanAccount.getRepaymentPeriod()).isEqualTo(UPDATED_REPAYMENT_PERIOD);
        assertThat(testLoanAccount.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testLoanAccount.getProcessingFee()).isEqualTo(DEFAULT_PROCESSING_FEE);
        assertThat(testLoanAccount.getMoratorium()).isEqualTo(UPDATED_MORATORIUM);
        assertThat(testLoanAccount.getRoi()).isEqualTo(DEFAULT_ROI);
        assertThat(testLoanAccount.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanAccount.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLoanAccount.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLoanAccount.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testLoanAccount.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testLoanAccount.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testLoanAccount.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testLoanAccount.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateLoanAccountWithPatch() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        int databaseSizeBeforeUpdate = loanAccountRepository.findAll().size();

        // Update the loanAccount using partial update
        LoanAccount partialUpdatedLoanAccount = new LoanAccount();
        partialUpdatedLoanAccount.setId(loanAccount.getId());

        partialUpdatedLoanAccount
            .loanAmount(UPDATED_LOAN_AMOUNT)
            //            .applicationNo(UPDATED_APPLICATION_NO)
            .status(UPDATED_STATUS)
            .loanStartDate(UPDATED_LOAN_START_DATE)
            .loanEndDate(UPDATED_LOAN_END_DATE)
            .loanPlannedClosureDate(UPDATED_LOAN_PLANNED_CLOSURE_DATE)
            .loanCloserDate(UPDATED_LOAN_CLOSER_DATE)
            .emiStartDate(UPDATED_EMI_START_DATE)
            .loanNpaClass(UPDATED_LOAN_NPA_CLASS)
            .parentAccHeadCode(UPDATED_PARENT_ACC_HEAD_CODE)
            .loanAccountName(UPDATED_LOAN_ACCOUNT_NAME)
            .disbursementAmt(UPDATED_DISBURSEMENT_AMT)
            .disbursementStatus(UPDATED_DISBURSEMENT_STATUS)
            .repaymentPeriod(UPDATED_REPAYMENT_PERIOD)
            .year(UPDATED_YEAR)
            .processingFee(UPDATED_PROCESSING_FEE)
            .moratorium(UPDATED_MORATORIUM)
            .roi(UPDATED_ROI)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restLoanAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanAccount))
            )
            .andExpect(status().isOk());

        // Validate the LoanAccount in the database
        List<LoanAccount> loanAccountList = loanAccountRepository.findAll();
        assertThat(loanAccountList).hasSize(databaseSizeBeforeUpdate);
        LoanAccount testLoanAccount = loanAccountList.get(loanAccountList.size() - 1);
        assertThat(testLoanAccount.getLoanAmount()).isEqualTo(UPDATED_LOAN_AMOUNT);
        //        assertThat(testLoanAccount.getApplicationNo()).isEqualTo(UPDATED_APPLICATION_NO);
        assertThat(testLoanAccount.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testLoanAccount.getLoanStartDate()).isEqualTo(UPDATED_LOAN_START_DATE);
        assertThat(testLoanAccount.getLoanEndDate()).isEqualTo(UPDATED_LOAN_END_DATE);
        assertThat(testLoanAccount.getLoanPlannedClosureDate()).isEqualTo(UPDATED_LOAN_PLANNED_CLOSURE_DATE);
        assertThat(testLoanAccount.getLoanCloserDate()).isEqualTo(UPDATED_LOAN_CLOSER_DATE);
        assertThat(testLoanAccount.getEmiStartDate()).isEqualTo(UPDATED_EMI_START_DATE);
        assertThat(testLoanAccount.getLoanNpaClass()).isEqualTo(UPDATED_LOAN_NPA_CLASS);
        assertThat(testLoanAccount.getParentAccHeadCode()).isEqualTo(UPDATED_PARENT_ACC_HEAD_CODE);
        assertThat(testLoanAccount.getLoanAccountName()).isEqualTo(UPDATED_LOAN_ACCOUNT_NAME);
        assertThat(testLoanAccount.getDisbursementAmt()).isEqualTo(UPDATED_DISBURSEMENT_AMT);
        assertThat(testLoanAccount.getDisbursementStatus()).isEqualTo(UPDATED_DISBURSEMENT_STATUS);
        assertThat(testLoanAccount.getRepaymentPeriod()).isEqualTo(UPDATED_REPAYMENT_PERIOD);
        assertThat(testLoanAccount.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLoanAccount.getProcessingFee()).isEqualTo(UPDATED_PROCESSING_FEE);
        assertThat(testLoanAccount.getMoratorium()).isEqualTo(UPDATED_MORATORIUM);
        assertThat(testLoanAccount.getRoi()).isEqualTo(UPDATED_ROI);
        assertThat(testLoanAccount.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanAccount.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanAccount.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanAccount.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanAccount.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testLoanAccount.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testLoanAccount.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testLoanAccount.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingLoanAccount() throws Exception {
        int databaseSizeBeforeUpdate = loanAccountRepository.findAll().size();
        loanAccount.setId(count.incrementAndGet());

        // Create the LoanAccount
        LoanAccountDTO loanAccountDTO = loanAccountMapper.toDto(loanAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loanAccountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanAccount in the database
        List<LoanAccount> loanAccountList = loanAccountRepository.findAll();
        assertThat(loanAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoanAccount() throws Exception {
        int databaseSizeBeforeUpdate = loanAccountRepository.findAll().size();
        loanAccount.setId(count.incrementAndGet());

        // Create the LoanAccount
        LoanAccountDTO loanAccountDTO = loanAccountMapper.toDto(loanAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanAccount in the database
        List<LoanAccount> loanAccountList = loanAccountRepository.findAll();
        assertThat(loanAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoanAccount() throws Exception {
        int databaseSizeBeforeUpdate = loanAccountRepository.findAll().size();
        loanAccount.setId(count.incrementAndGet());

        // Create the LoanAccount
        LoanAccountDTO loanAccountDTO = loanAccountMapper.toDto(loanAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanAccountMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(loanAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanAccount in the database
        List<LoanAccount> loanAccountList = loanAccountRepository.findAll();
        assertThat(loanAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoanAccount() throws Exception {
        // Initialize the database
        loanAccountRepository.saveAndFlush(loanAccount);

        int databaseSizeBeforeDelete = loanAccountRepository.findAll().size();

        // Delete the loanAccount
        restLoanAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, loanAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoanAccount> loanAccountList = loanAccountRepository.findAll();
        assertThat(loanAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
