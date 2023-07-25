package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.LoanAccount;
import com.techvg.los.domain.LoanRepaymentDetails;
import com.techvg.los.domain.enumeration.PaymentMode;
import com.techvg.los.repository.LoanRepaymentDetailsRepository;
import com.techvg.los.service.criteria.LoanRepaymentDetailsCriteria;
import com.techvg.los.service.dto.LoanRepaymentDetailsDTO;
import com.techvg.los.service.mapper.LoanRepaymentDetailsMapper;
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
 * Integration tests for the {@link LoanRepaymentDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoanRepaymentDetailsResourceIT {

    private static final Instant DEFAULT_REPAYMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REPAYMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_TOTAL_REPAYMENT_AMT = 1D;
    private static final Double UPDATED_TOTAL_REPAYMENT_AMT = 2D;
    private static final Double SMALLER_TOTAL_REPAYMENT_AMT = 1D - 1D;

    private static final Integer DEFAULT_INSTALLMENT_NUMBER = 1;
    private static final Integer UPDATED_INSTALLMENT_NUMBER = 2;
    private static final Integer SMALLER_INSTALLMENT_NUMBER = 1 - 1;

    private static final Double DEFAULT_PRINCIPLE_PAID_AMT = 1D;
    private static final Double UPDATED_PRINCIPLE_PAID_AMT = 2D;
    private static final Double SMALLER_PRINCIPLE_PAID_AMT = 1D - 1D;

    private static final Double DEFAULT_INTEREST_PAID_AMT = 1D;
    private static final Double UPDATED_INTEREST_PAID_AMT = 2D;
    private static final Double SMALLER_INTEREST_PAID_AMT = 1D - 1D;

    private static final Double DEFAULT_SUR_CHARGE_AMT = 1D;
    private static final Double UPDATED_SUR_CHARGE_AMT = 2D;
    private static final Double SMALLER_SUR_CHARGE_AMT = 1D - 1D;

    private static final Double DEFAULT_OVER_DUE_AMT = 1D;
    private static final Double UPDATED_OVER_DUE_AMT = 2D;
    private static final Double SMALLER_OVER_DUE_AMT = 1D - 1D;

    private static final Double DEFAULT_BALANCE_INTEREST_AMT = 1D;
    private static final Double UPDATED_BALANCE_INTEREST_AMT = 2D;
    private static final Double SMALLER_BALANCE_INTEREST_AMT = 1D - 1D;

    private static final Double DEFAULT_BALANCE_PRINCIPLE_AMT = 1D;
    private static final Double UPDATED_BALANCE_PRINCIPLE_AMT = 2D;
    private static final Double SMALLER_BALANCE_PRINCIPLE_AMT = 1D - 1D;

    private static final PaymentMode DEFAULT_PAYMENT_MODE = PaymentMode.ONLINE;
    private static final PaymentMode UPDATED_PAYMENT_MODE = PaymentMode.CASH;

    private static final Double DEFAULT_FORE_CLOSURE_CHARGE_AMT = 1D;
    private static final Double UPDATED_FORE_CLOSURE_CHARGE_AMT = 2D;
    private static final Double SMALLER_FORE_CLOSURE_CHARGE_AMT = 1D - 1D;

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

    private static final String ENTITY_API_URL = "/api/loan-repayment-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoanRepaymentDetailsRepository loanRepaymentDetailsRepository;

    @Autowired
    private LoanRepaymentDetailsMapper loanRepaymentDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoanRepaymentDetailsMockMvc;

    private LoanRepaymentDetails loanRepaymentDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanRepaymentDetails createEntity(EntityManager em) {
        LoanRepaymentDetails loanRepaymentDetails = new LoanRepaymentDetails()
            .repaymentDate(DEFAULT_REPAYMENT_DATE)
            .totalRepaymentAmt(DEFAULT_TOTAL_REPAYMENT_AMT)
            .installmentNumber(DEFAULT_INSTALLMENT_NUMBER)
            .principlePaidAmt(DEFAULT_PRINCIPLE_PAID_AMT)
            .interestPaidAmt(DEFAULT_INTEREST_PAID_AMT)
            .surChargeAmt(DEFAULT_SUR_CHARGE_AMT)
            .overDueAmt(DEFAULT_OVER_DUE_AMT)
            .balanceInterestAmt(DEFAULT_BALANCE_INTEREST_AMT)
            .balancePrincipleAmt(DEFAULT_BALANCE_PRINCIPLE_AMT)
            .paymentMode(DEFAULT_PAYMENT_MODE)
            .foreClosureChargeAmt(DEFAULT_FORE_CLOSURE_CHARGE_AMT)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5)
            .freeField6(DEFAULT_FREE_FIELD_6);
        return loanRepaymentDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanRepaymentDetails createUpdatedEntity(EntityManager em) {
        LoanRepaymentDetails loanRepaymentDetails = new LoanRepaymentDetails()
            .repaymentDate(UPDATED_REPAYMENT_DATE)
            .totalRepaymentAmt(UPDATED_TOTAL_REPAYMENT_AMT)
            .installmentNumber(UPDATED_INSTALLMENT_NUMBER)
            .principlePaidAmt(UPDATED_PRINCIPLE_PAID_AMT)
            .interestPaidAmt(UPDATED_INTEREST_PAID_AMT)
            .surChargeAmt(UPDATED_SUR_CHARGE_AMT)
            .overDueAmt(UPDATED_OVER_DUE_AMT)
            .balanceInterestAmt(UPDATED_BALANCE_INTEREST_AMT)
            .balancePrincipleAmt(UPDATED_BALANCE_PRINCIPLE_AMT)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .foreClosureChargeAmt(UPDATED_FORE_CLOSURE_CHARGE_AMT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        return loanRepaymentDetails;
    }

    @BeforeEach
    public void initTest() {
        loanRepaymentDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createLoanRepaymentDetails() throws Exception {
        int databaseSizeBeforeCreate = loanRepaymentDetailsRepository.findAll().size();
        // Create the LoanRepaymentDetails
        LoanRepaymentDetailsDTO loanRepaymentDetailsDTO = loanRepaymentDetailsMapper.toDto(loanRepaymentDetails);
        restLoanRepaymentDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanRepaymentDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LoanRepaymentDetails in the database
        List<LoanRepaymentDetails> loanRepaymentDetailsList = loanRepaymentDetailsRepository.findAll();
        assertThat(loanRepaymentDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        LoanRepaymentDetails testLoanRepaymentDetails = loanRepaymentDetailsList.get(loanRepaymentDetailsList.size() - 1);
        assertThat(testLoanRepaymentDetails.getRepaymentDate()).isEqualTo(DEFAULT_REPAYMENT_DATE);
        assertThat(testLoanRepaymentDetails.getTotalRepaymentAmt()).isEqualTo(DEFAULT_TOTAL_REPAYMENT_AMT);
        assertThat(testLoanRepaymentDetails.getInstallmentNumber()).isEqualTo(DEFAULT_INSTALLMENT_NUMBER);
        assertThat(testLoanRepaymentDetails.getPrinciplePaidAmt()).isEqualTo(DEFAULT_PRINCIPLE_PAID_AMT);
        assertThat(testLoanRepaymentDetails.getInterestPaidAmt()).isEqualTo(DEFAULT_INTEREST_PAID_AMT);
        assertThat(testLoanRepaymentDetails.getSurChargeAmt()).isEqualTo(DEFAULT_SUR_CHARGE_AMT);
        assertThat(testLoanRepaymentDetails.getOverDueAmt()).isEqualTo(DEFAULT_OVER_DUE_AMT);
        assertThat(testLoanRepaymentDetails.getBalanceInterestAmt()).isEqualTo(DEFAULT_BALANCE_INTEREST_AMT);
        assertThat(testLoanRepaymentDetails.getBalancePrincipleAmt()).isEqualTo(DEFAULT_BALANCE_PRINCIPLE_AMT);
        assertThat(testLoanRepaymentDetails.getPaymentMode()).isEqualTo(DEFAULT_PAYMENT_MODE);
        assertThat(testLoanRepaymentDetails.getForeClosureChargeAmt()).isEqualTo(DEFAULT_FORE_CLOSURE_CHARGE_AMT);
        assertThat(testLoanRepaymentDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLoanRepaymentDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLoanRepaymentDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLoanRepaymentDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testLoanRepaymentDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testLoanRepaymentDetails.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testLoanRepaymentDetails.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testLoanRepaymentDetails.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createLoanRepaymentDetailsWithExistingId() throws Exception {
        // Create the LoanRepaymentDetails with an existing ID
        loanRepaymentDetails.setId(1L);
        LoanRepaymentDetailsDTO loanRepaymentDetailsDTO = loanRepaymentDetailsMapper.toDto(loanRepaymentDetails);

        int databaseSizeBeforeCreate = loanRepaymentDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanRepaymentDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanRepaymentDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanRepaymentDetails in the database
        List<LoanRepaymentDetails> loanRepaymentDetailsList = loanRepaymentDetailsRepository.findAll();
        assertThat(loanRepaymentDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetails() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList
        restLoanRepaymentDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanRepaymentDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].repaymentDate").value(hasItem(DEFAULT_REPAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalRepaymentAmt").value(hasItem(DEFAULT_TOTAL_REPAYMENT_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].installmentNumber").value(hasItem(DEFAULT_INSTALLMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].principlePaidAmt").value(hasItem(DEFAULT_PRINCIPLE_PAID_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].interestPaidAmt").value(hasItem(DEFAULT_INTEREST_PAID_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].surChargeAmt").value(hasItem(DEFAULT_SUR_CHARGE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].overDueAmt").value(hasItem(DEFAULT_OVER_DUE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].balanceInterestAmt").value(hasItem(DEFAULT_BALANCE_INTEREST_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].balancePrincipleAmt").value(hasItem(DEFAULT_BALANCE_PRINCIPLE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].foreClosureChargeAmt").value(hasItem(DEFAULT_FORE_CLOSURE_CHARGE_AMT.doubleValue())))
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
    void getLoanRepaymentDetails() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get the loanRepaymentDetails
        restLoanRepaymentDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, loanRepaymentDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loanRepaymentDetails.getId().intValue()))
            .andExpect(jsonPath("$.repaymentDate").value(DEFAULT_REPAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.totalRepaymentAmt").value(DEFAULT_TOTAL_REPAYMENT_AMT.doubleValue()))
            .andExpect(jsonPath("$.installmentNumber").value(DEFAULT_INSTALLMENT_NUMBER))
            .andExpect(jsonPath("$.principlePaidAmt").value(DEFAULT_PRINCIPLE_PAID_AMT.doubleValue()))
            .andExpect(jsonPath("$.interestPaidAmt").value(DEFAULT_INTEREST_PAID_AMT.doubleValue()))
            .andExpect(jsonPath("$.surChargeAmt").value(DEFAULT_SUR_CHARGE_AMT.doubleValue()))
            .andExpect(jsonPath("$.overDueAmt").value(DEFAULT_OVER_DUE_AMT.doubleValue()))
            .andExpect(jsonPath("$.balanceInterestAmt").value(DEFAULT_BALANCE_INTEREST_AMT.doubleValue()))
            .andExpect(jsonPath("$.balancePrincipleAmt").value(DEFAULT_BALANCE_PRINCIPLE_AMT.doubleValue()))
            .andExpect(jsonPath("$.paymentMode").value(DEFAULT_PAYMENT_MODE.toString()))
            .andExpect(jsonPath("$.foreClosureChargeAmt").value(DEFAULT_FORE_CLOSURE_CHARGE_AMT.doubleValue()))
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
    void getLoanRepaymentDetailsByIdFiltering() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        Long id = loanRepaymentDetails.getId();

        defaultLoanRepaymentDetailsShouldBeFound("id.equals=" + id);
        defaultLoanRepaymentDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultLoanRepaymentDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLoanRepaymentDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultLoanRepaymentDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLoanRepaymentDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByRepaymentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where repaymentDate equals to DEFAULT_REPAYMENT_DATE
        defaultLoanRepaymentDetailsShouldBeFound("repaymentDate.equals=" + DEFAULT_REPAYMENT_DATE);

        // Get all the loanRepaymentDetailsList where repaymentDate equals to UPDATED_REPAYMENT_DATE
        defaultLoanRepaymentDetailsShouldNotBeFound("repaymentDate.equals=" + UPDATED_REPAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByRepaymentDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where repaymentDate in DEFAULT_REPAYMENT_DATE or UPDATED_REPAYMENT_DATE
        defaultLoanRepaymentDetailsShouldBeFound("repaymentDate.in=" + DEFAULT_REPAYMENT_DATE + "," + UPDATED_REPAYMENT_DATE);

        // Get all the loanRepaymentDetailsList where repaymentDate equals to UPDATED_REPAYMENT_DATE
        defaultLoanRepaymentDetailsShouldNotBeFound("repaymentDate.in=" + UPDATED_REPAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByRepaymentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where repaymentDate is not null
        defaultLoanRepaymentDetailsShouldBeFound("repaymentDate.specified=true");

        // Get all the loanRepaymentDetailsList where repaymentDate is null
        defaultLoanRepaymentDetailsShouldNotBeFound("repaymentDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByTotalRepaymentAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where totalRepaymentAmt equals to DEFAULT_TOTAL_REPAYMENT_AMT
        defaultLoanRepaymentDetailsShouldBeFound("totalRepaymentAmt.equals=" + DEFAULT_TOTAL_REPAYMENT_AMT);

        // Get all the loanRepaymentDetailsList where totalRepaymentAmt equals to UPDATED_TOTAL_REPAYMENT_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("totalRepaymentAmt.equals=" + UPDATED_TOTAL_REPAYMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByTotalRepaymentAmtIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where totalRepaymentAmt in DEFAULT_TOTAL_REPAYMENT_AMT or UPDATED_TOTAL_REPAYMENT_AMT
        defaultLoanRepaymentDetailsShouldBeFound("totalRepaymentAmt.in=" + DEFAULT_TOTAL_REPAYMENT_AMT + "," + UPDATED_TOTAL_REPAYMENT_AMT);

        // Get all the loanRepaymentDetailsList where totalRepaymentAmt equals to UPDATED_TOTAL_REPAYMENT_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("totalRepaymentAmt.in=" + UPDATED_TOTAL_REPAYMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByTotalRepaymentAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where totalRepaymentAmt is not null
        defaultLoanRepaymentDetailsShouldBeFound("totalRepaymentAmt.specified=true");

        // Get all the loanRepaymentDetailsList where totalRepaymentAmt is null
        defaultLoanRepaymentDetailsShouldNotBeFound("totalRepaymentAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByTotalRepaymentAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where totalRepaymentAmt is greater than or equal to DEFAULT_TOTAL_REPAYMENT_AMT
        defaultLoanRepaymentDetailsShouldBeFound("totalRepaymentAmt.greaterThanOrEqual=" + DEFAULT_TOTAL_REPAYMENT_AMT);

        // Get all the loanRepaymentDetailsList where totalRepaymentAmt is greater than or equal to UPDATED_TOTAL_REPAYMENT_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("totalRepaymentAmt.greaterThanOrEqual=" + UPDATED_TOTAL_REPAYMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByTotalRepaymentAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where totalRepaymentAmt is less than or equal to DEFAULT_TOTAL_REPAYMENT_AMT
        defaultLoanRepaymentDetailsShouldBeFound("totalRepaymentAmt.lessThanOrEqual=" + DEFAULT_TOTAL_REPAYMENT_AMT);

        // Get all the loanRepaymentDetailsList where totalRepaymentAmt is less than or equal to SMALLER_TOTAL_REPAYMENT_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("totalRepaymentAmt.lessThanOrEqual=" + SMALLER_TOTAL_REPAYMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByTotalRepaymentAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where totalRepaymentAmt is less than DEFAULT_TOTAL_REPAYMENT_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("totalRepaymentAmt.lessThan=" + DEFAULT_TOTAL_REPAYMENT_AMT);

        // Get all the loanRepaymentDetailsList where totalRepaymentAmt is less than UPDATED_TOTAL_REPAYMENT_AMT
        defaultLoanRepaymentDetailsShouldBeFound("totalRepaymentAmt.lessThan=" + UPDATED_TOTAL_REPAYMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByTotalRepaymentAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where totalRepaymentAmt is greater than DEFAULT_TOTAL_REPAYMENT_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("totalRepaymentAmt.greaterThan=" + DEFAULT_TOTAL_REPAYMENT_AMT);

        // Get all the loanRepaymentDetailsList where totalRepaymentAmt is greater than SMALLER_TOTAL_REPAYMENT_AMT
        defaultLoanRepaymentDetailsShouldBeFound("totalRepaymentAmt.greaterThan=" + SMALLER_TOTAL_REPAYMENT_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByInstallmentNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where installmentNumber equals to DEFAULT_INSTALLMENT_NUMBER
        defaultLoanRepaymentDetailsShouldBeFound("installmentNumber.equals=" + DEFAULT_INSTALLMENT_NUMBER);

        // Get all the loanRepaymentDetailsList where installmentNumber equals to UPDATED_INSTALLMENT_NUMBER
        defaultLoanRepaymentDetailsShouldNotBeFound("installmentNumber.equals=" + UPDATED_INSTALLMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByInstallmentNumberIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where installmentNumber in DEFAULT_INSTALLMENT_NUMBER or UPDATED_INSTALLMENT_NUMBER
        defaultLoanRepaymentDetailsShouldBeFound("installmentNumber.in=" + DEFAULT_INSTALLMENT_NUMBER + "," + UPDATED_INSTALLMENT_NUMBER);

        // Get all the loanRepaymentDetailsList where installmentNumber equals to UPDATED_INSTALLMENT_NUMBER
        defaultLoanRepaymentDetailsShouldNotBeFound("installmentNumber.in=" + UPDATED_INSTALLMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByInstallmentNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where installmentNumber is not null
        defaultLoanRepaymentDetailsShouldBeFound("installmentNumber.specified=true");

        // Get all the loanRepaymentDetailsList where installmentNumber is null
        defaultLoanRepaymentDetailsShouldNotBeFound("installmentNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByInstallmentNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where installmentNumber is greater than or equal to DEFAULT_INSTALLMENT_NUMBER
        defaultLoanRepaymentDetailsShouldBeFound("installmentNumber.greaterThanOrEqual=" + DEFAULT_INSTALLMENT_NUMBER);

        // Get all the loanRepaymentDetailsList where installmentNumber is greater than or equal to UPDATED_INSTALLMENT_NUMBER
        defaultLoanRepaymentDetailsShouldNotBeFound("installmentNumber.greaterThanOrEqual=" + UPDATED_INSTALLMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByInstallmentNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where installmentNumber is less than or equal to DEFAULT_INSTALLMENT_NUMBER
        defaultLoanRepaymentDetailsShouldBeFound("installmentNumber.lessThanOrEqual=" + DEFAULT_INSTALLMENT_NUMBER);

        // Get all the loanRepaymentDetailsList where installmentNumber is less than or equal to SMALLER_INSTALLMENT_NUMBER
        defaultLoanRepaymentDetailsShouldNotBeFound("installmentNumber.lessThanOrEqual=" + SMALLER_INSTALLMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByInstallmentNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where installmentNumber is less than DEFAULT_INSTALLMENT_NUMBER
        defaultLoanRepaymentDetailsShouldNotBeFound("installmentNumber.lessThan=" + DEFAULT_INSTALLMENT_NUMBER);

        // Get all the loanRepaymentDetailsList where installmentNumber is less than UPDATED_INSTALLMENT_NUMBER
        defaultLoanRepaymentDetailsShouldBeFound("installmentNumber.lessThan=" + UPDATED_INSTALLMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByInstallmentNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where installmentNumber is greater than DEFAULT_INSTALLMENT_NUMBER
        defaultLoanRepaymentDetailsShouldNotBeFound("installmentNumber.greaterThan=" + DEFAULT_INSTALLMENT_NUMBER);

        // Get all the loanRepaymentDetailsList where installmentNumber is greater than SMALLER_INSTALLMENT_NUMBER
        defaultLoanRepaymentDetailsShouldBeFound("installmentNumber.greaterThan=" + SMALLER_INSTALLMENT_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByPrinciplePaidAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where principlePaidAmt equals to DEFAULT_PRINCIPLE_PAID_AMT
        defaultLoanRepaymentDetailsShouldBeFound("principlePaidAmt.equals=" + DEFAULT_PRINCIPLE_PAID_AMT);

        // Get all the loanRepaymentDetailsList where principlePaidAmt equals to UPDATED_PRINCIPLE_PAID_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("principlePaidAmt.equals=" + UPDATED_PRINCIPLE_PAID_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByPrinciplePaidAmtIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where principlePaidAmt in DEFAULT_PRINCIPLE_PAID_AMT or UPDATED_PRINCIPLE_PAID_AMT
        defaultLoanRepaymentDetailsShouldBeFound("principlePaidAmt.in=" + DEFAULT_PRINCIPLE_PAID_AMT + "," + UPDATED_PRINCIPLE_PAID_AMT);

        // Get all the loanRepaymentDetailsList where principlePaidAmt equals to UPDATED_PRINCIPLE_PAID_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("principlePaidAmt.in=" + UPDATED_PRINCIPLE_PAID_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByPrinciplePaidAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where principlePaidAmt is not null
        defaultLoanRepaymentDetailsShouldBeFound("principlePaidAmt.specified=true");

        // Get all the loanRepaymentDetailsList where principlePaidAmt is null
        defaultLoanRepaymentDetailsShouldNotBeFound("principlePaidAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByPrinciplePaidAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where principlePaidAmt is greater than or equal to DEFAULT_PRINCIPLE_PAID_AMT
        defaultLoanRepaymentDetailsShouldBeFound("principlePaidAmt.greaterThanOrEqual=" + DEFAULT_PRINCIPLE_PAID_AMT);

        // Get all the loanRepaymentDetailsList where principlePaidAmt is greater than or equal to UPDATED_PRINCIPLE_PAID_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("principlePaidAmt.greaterThanOrEqual=" + UPDATED_PRINCIPLE_PAID_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByPrinciplePaidAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where principlePaidAmt is less than or equal to DEFAULT_PRINCIPLE_PAID_AMT
        defaultLoanRepaymentDetailsShouldBeFound("principlePaidAmt.lessThanOrEqual=" + DEFAULT_PRINCIPLE_PAID_AMT);

        // Get all the loanRepaymentDetailsList where principlePaidAmt is less than or equal to SMALLER_PRINCIPLE_PAID_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("principlePaidAmt.lessThanOrEqual=" + SMALLER_PRINCIPLE_PAID_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByPrinciplePaidAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where principlePaidAmt is less than DEFAULT_PRINCIPLE_PAID_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("principlePaidAmt.lessThan=" + DEFAULT_PRINCIPLE_PAID_AMT);

        // Get all the loanRepaymentDetailsList where principlePaidAmt is less than UPDATED_PRINCIPLE_PAID_AMT
        defaultLoanRepaymentDetailsShouldBeFound("principlePaidAmt.lessThan=" + UPDATED_PRINCIPLE_PAID_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByPrinciplePaidAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where principlePaidAmt is greater than DEFAULT_PRINCIPLE_PAID_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("principlePaidAmt.greaterThan=" + DEFAULT_PRINCIPLE_PAID_AMT);

        // Get all the loanRepaymentDetailsList where principlePaidAmt is greater than SMALLER_PRINCIPLE_PAID_AMT
        defaultLoanRepaymentDetailsShouldBeFound("principlePaidAmt.greaterThan=" + SMALLER_PRINCIPLE_PAID_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByInterestPaidAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where interestPaidAmt equals to DEFAULT_INTEREST_PAID_AMT
        defaultLoanRepaymentDetailsShouldBeFound("interestPaidAmt.equals=" + DEFAULT_INTEREST_PAID_AMT);

        // Get all the loanRepaymentDetailsList where interestPaidAmt equals to UPDATED_INTEREST_PAID_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("interestPaidAmt.equals=" + UPDATED_INTEREST_PAID_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByInterestPaidAmtIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where interestPaidAmt in DEFAULT_INTEREST_PAID_AMT or UPDATED_INTEREST_PAID_AMT
        defaultLoanRepaymentDetailsShouldBeFound("interestPaidAmt.in=" + DEFAULT_INTEREST_PAID_AMT + "," + UPDATED_INTEREST_PAID_AMT);

        // Get all the loanRepaymentDetailsList where interestPaidAmt equals to UPDATED_INTEREST_PAID_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("interestPaidAmt.in=" + UPDATED_INTEREST_PAID_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByInterestPaidAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where interestPaidAmt is not null
        defaultLoanRepaymentDetailsShouldBeFound("interestPaidAmt.specified=true");

        // Get all the loanRepaymentDetailsList where interestPaidAmt is null
        defaultLoanRepaymentDetailsShouldNotBeFound("interestPaidAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByInterestPaidAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where interestPaidAmt is greater than or equal to DEFAULT_INTEREST_PAID_AMT
        defaultLoanRepaymentDetailsShouldBeFound("interestPaidAmt.greaterThanOrEqual=" + DEFAULT_INTEREST_PAID_AMT);

        // Get all the loanRepaymentDetailsList where interestPaidAmt is greater than or equal to UPDATED_INTEREST_PAID_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("interestPaidAmt.greaterThanOrEqual=" + UPDATED_INTEREST_PAID_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByInterestPaidAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where interestPaidAmt is less than or equal to DEFAULT_INTEREST_PAID_AMT
        defaultLoanRepaymentDetailsShouldBeFound("interestPaidAmt.lessThanOrEqual=" + DEFAULT_INTEREST_PAID_AMT);

        // Get all the loanRepaymentDetailsList where interestPaidAmt is less than or equal to SMALLER_INTEREST_PAID_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("interestPaidAmt.lessThanOrEqual=" + SMALLER_INTEREST_PAID_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByInterestPaidAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where interestPaidAmt is less than DEFAULT_INTEREST_PAID_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("interestPaidAmt.lessThan=" + DEFAULT_INTEREST_PAID_AMT);

        // Get all the loanRepaymentDetailsList where interestPaidAmt is less than UPDATED_INTEREST_PAID_AMT
        defaultLoanRepaymentDetailsShouldBeFound("interestPaidAmt.lessThan=" + UPDATED_INTEREST_PAID_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByInterestPaidAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where interestPaidAmt is greater than DEFAULT_INTEREST_PAID_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("interestPaidAmt.greaterThan=" + DEFAULT_INTEREST_PAID_AMT);

        // Get all the loanRepaymentDetailsList where interestPaidAmt is greater than SMALLER_INTEREST_PAID_AMT
        defaultLoanRepaymentDetailsShouldBeFound("interestPaidAmt.greaterThan=" + SMALLER_INTEREST_PAID_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsBySurChargeAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where surChargeAmt equals to DEFAULT_SUR_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("surChargeAmt.equals=" + DEFAULT_SUR_CHARGE_AMT);

        // Get all the loanRepaymentDetailsList where surChargeAmt equals to UPDATED_SUR_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("surChargeAmt.equals=" + UPDATED_SUR_CHARGE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsBySurChargeAmtIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where surChargeAmt in DEFAULT_SUR_CHARGE_AMT or UPDATED_SUR_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("surChargeAmt.in=" + DEFAULT_SUR_CHARGE_AMT + "," + UPDATED_SUR_CHARGE_AMT);

        // Get all the loanRepaymentDetailsList where surChargeAmt equals to UPDATED_SUR_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("surChargeAmt.in=" + UPDATED_SUR_CHARGE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsBySurChargeAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where surChargeAmt is not null
        defaultLoanRepaymentDetailsShouldBeFound("surChargeAmt.specified=true");

        // Get all the loanRepaymentDetailsList where surChargeAmt is null
        defaultLoanRepaymentDetailsShouldNotBeFound("surChargeAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsBySurChargeAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where surChargeAmt is greater than or equal to DEFAULT_SUR_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("surChargeAmt.greaterThanOrEqual=" + DEFAULT_SUR_CHARGE_AMT);

        // Get all the loanRepaymentDetailsList where surChargeAmt is greater than or equal to UPDATED_SUR_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("surChargeAmt.greaterThanOrEqual=" + UPDATED_SUR_CHARGE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsBySurChargeAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where surChargeAmt is less than or equal to DEFAULT_SUR_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("surChargeAmt.lessThanOrEqual=" + DEFAULT_SUR_CHARGE_AMT);

        // Get all the loanRepaymentDetailsList where surChargeAmt is less than or equal to SMALLER_SUR_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("surChargeAmt.lessThanOrEqual=" + SMALLER_SUR_CHARGE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsBySurChargeAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where surChargeAmt is less than DEFAULT_SUR_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("surChargeAmt.lessThan=" + DEFAULT_SUR_CHARGE_AMT);

        // Get all the loanRepaymentDetailsList where surChargeAmt is less than UPDATED_SUR_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("surChargeAmt.lessThan=" + UPDATED_SUR_CHARGE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsBySurChargeAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where surChargeAmt is greater than DEFAULT_SUR_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("surChargeAmt.greaterThan=" + DEFAULT_SUR_CHARGE_AMT);

        // Get all the loanRepaymentDetailsList where surChargeAmt is greater than SMALLER_SUR_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("surChargeAmt.greaterThan=" + SMALLER_SUR_CHARGE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByOverDueAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where overDueAmt equals to DEFAULT_OVER_DUE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("overDueAmt.equals=" + DEFAULT_OVER_DUE_AMT);

        // Get all the loanRepaymentDetailsList where overDueAmt equals to UPDATED_OVER_DUE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("overDueAmt.equals=" + UPDATED_OVER_DUE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByOverDueAmtIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where overDueAmt in DEFAULT_OVER_DUE_AMT or UPDATED_OVER_DUE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("overDueAmt.in=" + DEFAULT_OVER_DUE_AMT + "," + UPDATED_OVER_DUE_AMT);

        // Get all the loanRepaymentDetailsList where overDueAmt equals to UPDATED_OVER_DUE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("overDueAmt.in=" + UPDATED_OVER_DUE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByOverDueAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where overDueAmt is not null
        defaultLoanRepaymentDetailsShouldBeFound("overDueAmt.specified=true");

        // Get all the loanRepaymentDetailsList where overDueAmt is null
        defaultLoanRepaymentDetailsShouldNotBeFound("overDueAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByOverDueAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where overDueAmt is greater than or equal to DEFAULT_OVER_DUE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("overDueAmt.greaterThanOrEqual=" + DEFAULT_OVER_DUE_AMT);

        // Get all the loanRepaymentDetailsList where overDueAmt is greater than or equal to UPDATED_OVER_DUE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("overDueAmt.greaterThanOrEqual=" + UPDATED_OVER_DUE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByOverDueAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where overDueAmt is less than or equal to DEFAULT_OVER_DUE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("overDueAmt.lessThanOrEqual=" + DEFAULT_OVER_DUE_AMT);

        // Get all the loanRepaymentDetailsList where overDueAmt is less than or equal to SMALLER_OVER_DUE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("overDueAmt.lessThanOrEqual=" + SMALLER_OVER_DUE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByOverDueAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where overDueAmt is less than DEFAULT_OVER_DUE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("overDueAmt.lessThan=" + DEFAULT_OVER_DUE_AMT);

        // Get all the loanRepaymentDetailsList where overDueAmt is less than UPDATED_OVER_DUE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("overDueAmt.lessThan=" + UPDATED_OVER_DUE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByOverDueAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where overDueAmt is greater than DEFAULT_OVER_DUE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("overDueAmt.greaterThan=" + DEFAULT_OVER_DUE_AMT);

        // Get all the loanRepaymentDetailsList where overDueAmt is greater than SMALLER_OVER_DUE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("overDueAmt.greaterThan=" + SMALLER_OVER_DUE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByBalanceInterestAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where balanceInterestAmt equals to DEFAULT_BALANCE_INTEREST_AMT
        defaultLoanRepaymentDetailsShouldBeFound("balanceInterestAmt.equals=" + DEFAULT_BALANCE_INTEREST_AMT);

        // Get all the loanRepaymentDetailsList where balanceInterestAmt equals to UPDATED_BALANCE_INTEREST_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("balanceInterestAmt.equals=" + UPDATED_BALANCE_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByBalanceInterestAmtIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where balanceInterestAmt in DEFAULT_BALANCE_INTEREST_AMT or UPDATED_BALANCE_INTEREST_AMT
        defaultLoanRepaymentDetailsShouldBeFound(
            "balanceInterestAmt.in=" + DEFAULT_BALANCE_INTEREST_AMT + "," + UPDATED_BALANCE_INTEREST_AMT
        );

        // Get all the loanRepaymentDetailsList where balanceInterestAmt equals to UPDATED_BALANCE_INTEREST_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("balanceInterestAmt.in=" + UPDATED_BALANCE_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByBalanceInterestAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where balanceInterestAmt is not null
        defaultLoanRepaymentDetailsShouldBeFound("balanceInterestAmt.specified=true");

        // Get all the loanRepaymentDetailsList where balanceInterestAmt is null
        defaultLoanRepaymentDetailsShouldNotBeFound("balanceInterestAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByBalanceInterestAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where balanceInterestAmt is greater than or equal to DEFAULT_BALANCE_INTEREST_AMT
        defaultLoanRepaymentDetailsShouldBeFound("balanceInterestAmt.greaterThanOrEqual=" + DEFAULT_BALANCE_INTEREST_AMT);

        // Get all the loanRepaymentDetailsList where balanceInterestAmt is greater than or equal to UPDATED_BALANCE_INTEREST_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("balanceInterestAmt.greaterThanOrEqual=" + UPDATED_BALANCE_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByBalanceInterestAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where balanceInterestAmt is less than or equal to DEFAULT_BALANCE_INTEREST_AMT
        defaultLoanRepaymentDetailsShouldBeFound("balanceInterestAmt.lessThanOrEqual=" + DEFAULT_BALANCE_INTEREST_AMT);

        // Get all the loanRepaymentDetailsList where balanceInterestAmt is less than or equal to SMALLER_BALANCE_INTEREST_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("balanceInterestAmt.lessThanOrEqual=" + SMALLER_BALANCE_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByBalanceInterestAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where balanceInterestAmt is less than DEFAULT_BALANCE_INTEREST_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("balanceInterestAmt.lessThan=" + DEFAULT_BALANCE_INTEREST_AMT);

        // Get all the loanRepaymentDetailsList where balanceInterestAmt is less than UPDATED_BALANCE_INTEREST_AMT
        defaultLoanRepaymentDetailsShouldBeFound("balanceInterestAmt.lessThan=" + UPDATED_BALANCE_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByBalanceInterestAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where balanceInterestAmt is greater than DEFAULT_BALANCE_INTEREST_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("balanceInterestAmt.greaterThan=" + DEFAULT_BALANCE_INTEREST_AMT);

        // Get all the loanRepaymentDetailsList where balanceInterestAmt is greater than SMALLER_BALANCE_INTEREST_AMT
        defaultLoanRepaymentDetailsShouldBeFound("balanceInterestAmt.greaterThan=" + SMALLER_BALANCE_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByBalancePrincipleAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where balancePrincipleAmt equals to DEFAULT_BALANCE_PRINCIPLE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("balancePrincipleAmt.equals=" + DEFAULT_BALANCE_PRINCIPLE_AMT);

        // Get all the loanRepaymentDetailsList where balancePrincipleAmt equals to UPDATED_BALANCE_PRINCIPLE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("balancePrincipleAmt.equals=" + UPDATED_BALANCE_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByBalancePrincipleAmtIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where balancePrincipleAmt in DEFAULT_BALANCE_PRINCIPLE_AMT or UPDATED_BALANCE_PRINCIPLE_AMT
        defaultLoanRepaymentDetailsShouldBeFound(
            "balancePrincipleAmt.in=" + DEFAULT_BALANCE_PRINCIPLE_AMT + "," + UPDATED_BALANCE_PRINCIPLE_AMT
        );

        // Get all the loanRepaymentDetailsList where balancePrincipleAmt equals to UPDATED_BALANCE_PRINCIPLE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("balancePrincipleAmt.in=" + UPDATED_BALANCE_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByBalancePrincipleAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where balancePrincipleAmt is not null
        defaultLoanRepaymentDetailsShouldBeFound("balancePrincipleAmt.specified=true");

        // Get all the loanRepaymentDetailsList where balancePrincipleAmt is null
        defaultLoanRepaymentDetailsShouldNotBeFound("balancePrincipleAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByBalancePrincipleAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where balancePrincipleAmt is greater than or equal to DEFAULT_BALANCE_PRINCIPLE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("balancePrincipleAmt.greaterThanOrEqual=" + DEFAULT_BALANCE_PRINCIPLE_AMT);

        // Get all the loanRepaymentDetailsList where balancePrincipleAmt is greater than or equal to UPDATED_BALANCE_PRINCIPLE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("balancePrincipleAmt.greaterThanOrEqual=" + UPDATED_BALANCE_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByBalancePrincipleAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where balancePrincipleAmt is less than or equal to DEFAULT_BALANCE_PRINCIPLE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("balancePrincipleAmt.lessThanOrEqual=" + DEFAULT_BALANCE_PRINCIPLE_AMT);

        // Get all the loanRepaymentDetailsList where balancePrincipleAmt is less than or equal to SMALLER_BALANCE_PRINCIPLE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("balancePrincipleAmt.lessThanOrEqual=" + SMALLER_BALANCE_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByBalancePrincipleAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where balancePrincipleAmt is less than DEFAULT_BALANCE_PRINCIPLE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("balancePrincipleAmt.lessThan=" + DEFAULT_BALANCE_PRINCIPLE_AMT);

        // Get all the loanRepaymentDetailsList where balancePrincipleAmt is less than UPDATED_BALANCE_PRINCIPLE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("balancePrincipleAmt.lessThan=" + UPDATED_BALANCE_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByBalancePrincipleAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where balancePrincipleAmt is greater than DEFAULT_BALANCE_PRINCIPLE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("balancePrincipleAmt.greaterThan=" + DEFAULT_BALANCE_PRINCIPLE_AMT);

        // Get all the loanRepaymentDetailsList where balancePrincipleAmt is greater than SMALLER_BALANCE_PRINCIPLE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("balancePrincipleAmt.greaterThan=" + SMALLER_BALANCE_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByPaymentModeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where paymentMode equals to DEFAULT_PAYMENT_MODE
        defaultLoanRepaymentDetailsShouldBeFound("paymentMode.equals=" + DEFAULT_PAYMENT_MODE);

        // Get all the loanRepaymentDetailsList where paymentMode equals to UPDATED_PAYMENT_MODE
        defaultLoanRepaymentDetailsShouldNotBeFound("paymentMode.equals=" + UPDATED_PAYMENT_MODE);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByPaymentModeIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where paymentMode in DEFAULT_PAYMENT_MODE or UPDATED_PAYMENT_MODE
        defaultLoanRepaymentDetailsShouldBeFound("paymentMode.in=" + DEFAULT_PAYMENT_MODE + "," + UPDATED_PAYMENT_MODE);

        // Get all the loanRepaymentDetailsList where paymentMode equals to UPDATED_PAYMENT_MODE
        defaultLoanRepaymentDetailsShouldNotBeFound("paymentMode.in=" + UPDATED_PAYMENT_MODE);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByPaymentModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where paymentMode is not null
        defaultLoanRepaymentDetailsShouldBeFound("paymentMode.specified=true");

        // Get all the loanRepaymentDetailsList where paymentMode is null
        defaultLoanRepaymentDetailsShouldNotBeFound("paymentMode.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByForeClosureChargeAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where foreClosureChargeAmt equals to DEFAULT_FORE_CLOSURE_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("foreClosureChargeAmt.equals=" + DEFAULT_FORE_CLOSURE_CHARGE_AMT);

        // Get all the loanRepaymentDetailsList where foreClosureChargeAmt equals to UPDATED_FORE_CLOSURE_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("foreClosureChargeAmt.equals=" + UPDATED_FORE_CLOSURE_CHARGE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByForeClosureChargeAmtIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where foreClosureChargeAmt in DEFAULT_FORE_CLOSURE_CHARGE_AMT or UPDATED_FORE_CLOSURE_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldBeFound(
            "foreClosureChargeAmt.in=" + DEFAULT_FORE_CLOSURE_CHARGE_AMT + "," + UPDATED_FORE_CLOSURE_CHARGE_AMT
        );

        // Get all the loanRepaymentDetailsList where foreClosureChargeAmt equals to UPDATED_FORE_CLOSURE_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("foreClosureChargeAmt.in=" + UPDATED_FORE_CLOSURE_CHARGE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByForeClosureChargeAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where foreClosureChargeAmt is not null
        defaultLoanRepaymentDetailsShouldBeFound("foreClosureChargeAmt.specified=true");

        // Get all the loanRepaymentDetailsList where foreClosureChargeAmt is null
        defaultLoanRepaymentDetailsShouldNotBeFound("foreClosureChargeAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByForeClosureChargeAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where foreClosureChargeAmt is greater than or equal to DEFAULT_FORE_CLOSURE_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("foreClosureChargeAmt.greaterThanOrEqual=" + DEFAULT_FORE_CLOSURE_CHARGE_AMT);

        // Get all the loanRepaymentDetailsList where foreClosureChargeAmt is greater than or equal to UPDATED_FORE_CLOSURE_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("foreClosureChargeAmt.greaterThanOrEqual=" + UPDATED_FORE_CLOSURE_CHARGE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByForeClosureChargeAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where foreClosureChargeAmt is less than or equal to DEFAULT_FORE_CLOSURE_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("foreClosureChargeAmt.lessThanOrEqual=" + DEFAULT_FORE_CLOSURE_CHARGE_AMT);

        // Get all the loanRepaymentDetailsList where foreClosureChargeAmt is less than or equal to SMALLER_FORE_CLOSURE_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("foreClosureChargeAmt.lessThanOrEqual=" + SMALLER_FORE_CLOSURE_CHARGE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByForeClosureChargeAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where foreClosureChargeAmt is less than DEFAULT_FORE_CLOSURE_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("foreClosureChargeAmt.lessThan=" + DEFAULT_FORE_CLOSURE_CHARGE_AMT);

        // Get all the loanRepaymentDetailsList where foreClosureChargeAmt is less than UPDATED_FORE_CLOSURE_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("foreClosureChargeAmt.lessThan=" + UPDATED_FORE_CLOSURE_CHARGE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByForeClosureChargeAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where foreClosureChargeAmt is greater than DEFAULT_FORE_CLOSURE_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldNotBeFound("foreClosureChargeAmt.greaterThan=" + DEFAULT_FORE_CLOSURE_CHARGE_AMT);

        // Get all the loanRepaymentDetailsList where foreClosureChargeAmt is greater than SMALLER_FORE_CLOSURE_CHARGE_AMT
        defaultLoanRepaymentDetailsShouldBeFound("foreClosureChargeAmt.greaterThan=" + SMALLER_FORE_CLOSURE_CHARGE_AMT);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLoanRepaymentDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the loanRepaymentDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanRepaymentDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLoanRepaymentDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the loanRepaymentDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanRepaymentDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where lastModified is not null
        defaultLoanRepaymentDetailsShouldBeFound("lastModified.specified=true");

        // Get all the loanRepaymentDetailsList where lastModified is null
        defaultLoanRepaymentDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLoanRepaymentDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanRepaymentDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanRepaymentDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLoanRepaymentDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the loanRepaymentDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanRepaymentDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where lastModifiedBy is not null
        defaultLoanRepaymentDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the loanRepaymentDetailsList where lastModifiedBy is null
        defaultLoanRepaymentDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLoanRepaymentDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanRepaymentDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLoanRepaymentDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLoanRepaymentDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanRepaymentDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLoanRepaymentDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultLoanRepaymentDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanRepaymentDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultLoanRepaymentDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the loanRepaymentDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField1 is not null
        defaultLoanRepaymentDetailsShouldBeFound("freeField1.specified=true");

        // Get all the loanRepaymentDetailsList where freeField1 is null
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultLoanRepaymentDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanRepaymentDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanRepaymentDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultLoanRepaymentDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultLoanRepaymentDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanRepaymentDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultLoanRepaymentDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the loanRepaymentDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField2 is not null
        defaultLoanRepaymentDetailsShouldBeFound("freeField2.specified=true");

        // Get all the loanRepaymentDetailsList where freeField2 is null
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultLoanRepaymentDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanRepaymentDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanRepaymentDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultLoanRepaymentDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultLoanRepaymentDetailsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanRepaymentDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultLoanRepaymentDetailsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the loanRepaymentDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField3 is not null
        defaultLoanRepaymentDetailsShouldBeFound("freeField3.specified=true");

        // Get all the loanRepaymentDetailsList where freeField3 is null
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultLoanRepaymentDetailsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanRepaymentDetailsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanRepaymentDetailsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultLoanRepaymentDetailsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultLoanRepaymentDetailsShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the loanRepaymentDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultLoanRepaymentDetailsShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the loanRepaymentDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField4 is not null
        defaultLoanRepaymentDetailsShouldBeFound("freeField4.specified=true");

        // Get all the loanRepaymentDetailsList where freeField4 is null
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultLoanRepaymentDetailsShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the loanRepaymentDetailsList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the loanRepaymentDetailsList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultLoanRepaymentDetailsShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultLoanRepaymentDetailsShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the loanRepaymentDetailsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultLoanRepaymentDetailsShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the loanRepaymentDetailsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField5 is not null
        defaultLoanRepaymentDetailsShouldBeFound("freeField5.specified=true");

        // Get all the loanRepaymentDetailsList where freeField5 is null
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultLoanRepaymentDetailsShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the loanRepaymentDetailsList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the loanRepaymentDetailsList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultLoanRepaymentDetailsShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultLoanRepaymentDetailsShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the loanRepaymentDetailsList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultLoanRepaymentDetailsShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the loanRepaymentDetailsList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField6 is not null
        defaultLoanRepaymentDetailsShouldBeFound("freeField6.specified=true");

        // Get all the loanRepaymentDetailsList where freeField6 is null
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultLoanRepaymentDetailsShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the loanRepaymentDetailsList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        // Get all the loanRepaymentDetailsList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultLoanRepaymentDetailsShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the loanRepaymentDetailsList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultLoanRepaymentDetailsShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanRepaymentDetailsByLoanAccountIsEqualToSomething() throws Exception {
        LoanAccount loanAccount;
        if (TestUtil.findAll(em, LoanAccount.class).isEmpty()) {
            loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);
            loanAccount = LoanAccountResourceIT.createEntity(em);
        } else {
            loanAccount = TestUtil.findAll(em, LoanAccount.class).get(0);
        }
        em.persist(loanAccount);
        em.flush();
        loanRepaymentDetails.setLoanAccount(loanAccount);
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);
        Long loanAccountId = loanAccount.getId();

        // Get all the loanRepaymentDetailsList where loanAccount equals to loanAccountId
        defaultLoanRepaymentDetailsShouldBeFound("loanAccountId.equals=" + loanAccountId);

        // Get all the loanRepaymentDetailsList where loanAccount equals to (loanAccountId + 1)
        defaultLoanRepaymentDetailsShouldNotBeFound("loanAccountId.equals=" + (loanAccountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLoanRepaymentDetailsShouldBeFound(String filter) throws Exception {
        restLoanRepaymentDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanRepaymentDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].repaymentDate").value(hasItem(DEFAULT_REPAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalRepaymentAmt").value(hasItem(DEFAULT_TOTAL_REPAYMENT_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].installmentNumber").value(hasItem(DEFAULT_INSTALLMENT_NUMBER)))
            .andExpect(jsonPath("$.[*].principlePaidAmt").value(hasItem(DEFAULT_PRINCIPLE_PAID_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].interestPaidAmt").value(hasItem(DEFAULT_INTEREST_PAID_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].surChargeAmt").value(hasItem(DEFAULT_SUR_CHARGE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].overDueAmt").value(hasItem(DEFAULT_OVER_DUE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].balanceInterestAmt").value(hasItem(DEFAULT_BALANCE_INTEREST_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].balancePrincipleAmt").value(hasItem(DEFAULT_BALANCE_PRINCIPLE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].foreClosureChargeAmt").value(hasItem(DEFAULT_FORE_CLOSURE_CHARGE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));

        // Check, that the count call also returns 1
        restLoanRepaymentDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLoanRepaymentDetailsShouldNotBeFound(String filter) throws Exception {
        restLoanRepaymentDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLoanRepaymentDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLoanRepaymentDetails() throws Exception {
        // Get the loanRepaymentDetails
        restLoanRepaymentDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLoanRepaymentDetails() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        int databaseSizeBeforeUpdate = loanRepaymentDetailsRepository.findAll().size();

        // Update the loanRepaymentDetails
        LoanRepaymentDetails updatedLoanRepaymentDetails = loanRepaymentDetailsRepository.findById(loanRepaymentDetails.getId()).get();
        // Disconnect from session so that the updates on updatedLoanRepaymentDetails are not directly saved in db
        em.detach(updatedLoanRepaymentDetails);
        updatedLoanRepaymentDetails
            .repaymentDate(UPDATED_REPAYMENT_DATE)
            .totalRepaymentAmt(UPDATED_TOTAL_REPAYMENT_AMT)
            .installmentNumber(UPDATED_INSTALLMENT_NUMBER)
            .principlePaidAmt(UPDATED_PRINCIPLE_PAID_AMT)
            .interestPaidAmt(UPDATED_INTEREST_PAID_AMT)
            .surChargeAmt(UPDATED_SUR_CHARGE_AMT)
            .overDueAmt(UPDATED_OVER_DUE_AMT)
            .balanceInterestAmt(UPDATED_BALANCE_INTEREST_AMT)
            .balancePrincipleAmt(UPDATED_BALANCE_PRINCIPLE_AMT)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .foreClosureChargeAmt(UPDATED_FORE_CLOSURE_CHARGE_AMT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        LoanRepaymentDetailsDTO loanRepaymentDetailsDTO = loanRepaymentDetailsMapper.toDto(updatedLoanRepaymentDetails);

        restLoanRepaymentDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanRepaymentDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanRepaymentDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the LoanRepaymentDetails in the database
        List<LoanRepaymentDetails> loanRepaymentDetailsList = loanRepaymentDetailsRepository.findAll();
        assertThat(loanRepaymentDetailsList).hasSize(databaseSizeBeforeUpdate);
        LoanRepaymentDetails testLoanRepaymentDetails = loanRepaymentDetailsList.get(loanRepaymentDetailsList.size() - 1);
        assertThat(testLoanRepaymentDetails.getRepaymentDate()).isEqualTo(UPDATED_REPAYMENT_DATE);
        assertThat(testLoanRepaymentDetails.getTotalRepaymentAmt()).isEqualTo(UPDATED_TOTAL_REPAYMENT_AMT);
        assertThat(testLoanRepaymentDetails.getInstallmentNumber()).isEqualTo(UPDATED_INSTALLMENT_NUMBER);
        assertThat(testLoanRepaymentDetails.getPrinciplePaidAmt()).isEqualTo(UPDATED_PRINCIPLE_PAID_AMT);
        assertThat(testLoanRepaymentDetails.getInterestPaidAmt()).isEqualTo(UPDATED_INTEREST_PAID_AMT);
        assertThat(testLoanRepaymentDetails.getSurChargeAmt()).isEqualTo(UPDATED_SUR_CHARGE_AMT);
        assertThat(testLoanRepaymentDetails.getOverDueAmt()).isEqualTo(UPDATED_OVER_DUE_AMT);
        assertThat(testLoanRepaymentDetails.getBalanceInterestAmt()).isEqualTo(UPDATED_BALANCE_INTEREST_AMT);
        assertThat(testLoanRepaymentDetails.getBalancePrincipleAmt()).isEqualTo(UPDATED_BALANCE_PRINCIPLE_AMT);
        assertThat(testLoanRepaymentDetails.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testLoanRepaymentDetails.getForeClosureChargeAmt()).isEqualTo(UPDATED_FORE_CLOSURE_CHARGE_AMT);
        assertThat(testLoanRepaymentDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanRepaymentDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanRepaymentDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanRepaymentDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanRepaymentDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testLoanRepaymentDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testLoanRepaymentDetails.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testLoanRepaymentDetails.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingLoanRepaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanRepaymentDetailsRepository.findAll().size();
        loanRepaymentDetails.setId(count.incrementAndGet());

        // Create the LoanRepaymentDetails
        LoanRepaymentDetailsDTO loanRepaymentDetailsDTO = loanRepaymentDetailsMapper.toDto(loanRepaymentDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanRepaymentDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanRepaymentDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanRepaymentDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanRepaymentDetails in the database
        List<LoanRepaymentDetails> loanRepaymentDetailsList = loanRepaymentDetailsRepository.findAll();
        assertThat(loanRepaymentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoanRepaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanRepaymentDetailsRepository.findAll().size();
        loanRepaymentDetails.setId(count.incrementAndGet());

        // Create the LoanRepaymentDetails
        LoanRepaymentDetailsDTO loanRepaymentDetailsDTO = loanRepaymentDetailsMapper.toDto(loanRepaymentDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanRepaymentDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanRepaymentDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanRepaymentDetails in the database
        List<LoanRepaymentDetails> loanRepaymentDetailsList = loanRepaymentDetailsRepository.findAll();
        assertThat(loanRepaymentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoanRepaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanRepaymentDetailsRepository.findAll().size();
        loanRepaymentDetails.setId(count.incrementAndGet());

        // Create the LoanRepaymentDetails
        LoanRepaymentDetailsDTO loanRepaymentDetailsDTO = loanRepaymentDetailsMapper.toDto(loanRepaymentDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanRepaymentDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanRepaymentDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanRepaymentDetails in the database
        List<LoanRepaymentDetails> loanRepaymentDetailsList = loanRepaymentDetailsRepository.findAll();
        assertThat(loanRepaymentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoanRepaymentDetailsWithPatch() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        int databaseSizeBeforeUpdate = loanRepaymentDetailsRepository.findAll().size();

        // Update the loanRepaymentDetails using partial update
        LoanRepaymentDetails partialUpdatedLoanRepaymentDetails = new LoanRepaymentDetails();
        partialUpdatedLoanRepaymentDetails.setId(loanRepaymentDetails.getId());

        partialUpdatedLoanRepaymentDetails
            .totalRepaymentAmt(UPDATED_TOTAL_REPAYMENT_AMT)
            .principlePaidAmt(UPDATED_PRINCIPLE_PAID_AMT)
            .interestPaidAmt(UPDATED_INTEREST_PAID_AMT)
            .overDueAmt(UPDATED_OVER_DUE_AMT)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .foreClosureChargeAmt(UPDATED_FORE_CLOSURE_CHARGE_AMT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField6(UPDATED_FREE_FIELD_6);

        restLoanRepaymentDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanRepaymentDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanRepaymentDetails))
            )
            .andExpect(status().isOk());

        // Validate the LoanRepaymentDetails in the database
        List<LoanRepaymentDetails> loanRepaymentDetailsList = loanRepaymentDetailsRepository.findAll();
        assertThat(loanRepaymentDetailsList).hasSize(databaseSizeBeforeUpdate);
        LoanRepaymentDetails testLoanRepaymentDetails = loanRepaymentDetailsList.get(loanRepaymentDetailsList.size() - 1);
        assertThat(testLoanRepaymentDetails.getRepaymentDate()).isEqualTo(DEFAULT_REPAYMENT_DATE);
        assertThat(testLoanRepaymentDetails.getTotalRepaymentAmt()).isEqualTo(UPDATED_TOTAL_REPAYMENT_AMT);
        assertThat(testLoanRepaymentDetails.getInstallmentNumber()).isEqualTo(DEFAULT_INSTALLMENT_NUMBER);
        assertThat(testLoanRepaymentDetails.getPrinciplePaidAmt()).isEqualTo(UPDATED_PRINCIPLE_PAID_AMT);
        assertThat(testLoanRepaymentDetails.getInterestPaidAmt()).isEqualTo(UPDATED_INTEREST_PAID_AMT);
        assertThat(testLoanRepaymentDetails.getSurChargeAmt()).isEqualTo(DEFAULT_SUR_CHARGE_AMT);
        assertThat(testLoanRepaymentDetails.getOverDueAmt()).isEqualTo(UPDATED_OVER_DUE_AMT);
        assertThat(testLoanRepaymentDetails.getBalanceInterestAmt()).isEqualTo(DEFAULT_BALANCE_INTEREST_AMT);
        assertThat(testLoanRepaymentDetails.getBalancePrincipleAmt()).isEqualTo(DEFAULT_BALANCE_PRINCIPLE_AMT);
        assertThat(testLoanRepaymentDetails.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testLoanRepaymentDetails.getForeClosureChargeAmt()).isEqualTo(UPDATED_FORE_CLOSURE_CHARGE_AMT);
        assertThat(testLoanRepaymentDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanRepaymentDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLoanRepaymentDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLoanRepaymentDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testLoanRepaymentDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testLoanRepaymentDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testLoanRepaymentDetails.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testLoanRepaymentDetails.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateLoanRepaymentDetailsWithPatch() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        int databaseSizeBeforeUpdate = loanRepaymentDetailsRepository.findAll().size();

        // Update the loanRepaymentDetails using partial update
        LoanRepaymentDetails partialUpdatedLoanRepaymentDetails = new LoanRepaymentDetails();
        partialUpdatedLoanRepaymentDetails.setId(loanRepaymentDetails.getId());

        partialUpdatedLoanRepaymentDetails
            .repaymentDate(UPDATED_REPAYMENT_DATE)
            .totalRepaymentAmt(UPDATED_TOTAL_REPAYMENT_AMT)
            .installmentNumber(UPDATED_INSTALLMENT_NUMBER)
            .principlePaidAmt(UPDATED_PRINCIPLE_PAID_AMT)
            .interestPaidAmt(UPDATED_INTEREST_PAID_AMT)
            .surChargeAmt(UPDATED_SUR_CHARGE_AMT)
            .overDueAmt(UPDATED_OVER_DUE_AMT)
            .balanceInterestAmt(UPDATED_BALANCE_INTEREST_AMT)
            .balancePrincipleAmt(UPDATED_BALANCE_PRINCIPLE_AMT)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .foreClosureChargeAmt(UPDATED_FORE_CLOSURE_CHARGE_AMT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restLoanRepaymentDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanRepaymentDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanRepaymentDetails))
            )
            .andExpect(status().isOk());

        // Validate the LoanRepaymentDetails in the database
        List<LoanRepaymentDetails> loanRepaymentDetailsList = loanRepaymentDetailsRepository.findAll();
        assertThat(loanRepaymentDetailsList).hasSize(databaseSizeBeforeUpdate);
        LoanRepaymentDetails testLoanRepaymentDetails = loanRepaymentDetailsList.get(loanRepaymentDetailsList.size() - 1);
        assertThat(testLoanRepaymentDetails.getRepaymentDate()).isEqualTo(UPDATED_REPAYMENT_DATE);
        assertThat(testLoanRepaymentDetails.getTotalRepaymentAmt()).isEqualTo(UPDATED_TOTAL_REPAYMENT_AMT);
        assertThat(testLoanRepaymentDetails.getInstallmentNumber()).isEqualTo(UPDATED_INSTALLMENT_NUMBER);
        assertThat(testLoanRepaymentDetails.getPrinciplePaidAmt()).isEqualTo(UPDATED_PRINCIPLE_PAID_AMT);
        assertThat(testLoanRepaymentDetails.getInterestPaidAmt()).isEqualTo(UPDATED_INTEREST_PAID_AMT);
        assertThat(testLoanRepaymentDetails.getSurChargeAmt()).isEqualTo(UPDATED_SUR_CHARGE_AMT);
        assertThat(testLoanRepaymentDetails.getOverDueAmt()).isEqualTo(UPDATED_OVER_DUE_AMT);
        assertThat(testLoanRepaymentDetails.getBalanceInterestAmt()).isEqualTo(UPDATED_BALANCE_INTEREST_AMT);
        assertThat(testLoanRepaymentDetails.getBalancePrincipleAmt()).isEqualTo(UPDATED_BALANCE_PRINCIPLE_AMT);
        assertThat(testLoanRepaymentDetails.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testLoanRepaymentDetails.getForeClosureChargeAmt()).isEqualTo(UPDATED_FORE_CLOSURE_CHARGE_AMT);
        assertThat(testLoanRepaymentDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanRepaymentDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanRepaymentDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanRepaymentDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanRepaymentDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testLoanRepaymentDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testLoanRepaymentDetails.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testLoanRepaymentDetails.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingLoanRepaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanRepaymentDetailsRepository.findAll().size();
        loanRepaymentDetails.setId(count.incrementAndGet());

        // Create the LoanRepaymentDetails
        LoanRepaymentDetailsDTO loanRepaymentDetailsDTO = loanRepaymentDetailsMapper.toDto(loanRepaymentDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanRepaymentDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loanRepaymentDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanRepaymentDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanRepaymentDetails in the database
        List<LoanRepaymentDetails> loanRepaymentDetailsList = loanRepaymentDetailsRepository.findAll();
        assertThat(loanRepaymentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoanRepaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanRepaymentDetailsRepository.findAll().size();
        loanRepaymentDetails.setId(count.incrementAndGet());

        // Create the LoanRepaymentDetails
        LoanRepaymentDetailsDTO loanRepaymentDetailsDTO = loanRepaymentDetailsMapper.toDto(loanRepaymentDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanRepaymentDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanRepaymentDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanRepaymentDetails in the database
        List<LoanRepaymentDetails> loanRepaymentDetailsList = loanRepaymentDetailsRepository.findAll();
        assertThat(loanRepaymentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoanRepaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = loanRepaymentDetailsRepository.findAll().size();
        loanRepaymentDetails.setId(count.incrementAndGet());

        // Create the LoanRepaymentDetails
        LoanRepaymentDetailsDTO loanRepaymentDetailsDTO = loanRepaymentDetailsMapper.toDto(loanRepaymentDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanRepaymentDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanRepaymentDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanRepaymentDetails in the database
        List<LoanRepaymentDetails> loanRepaymentDetailsList = loanRepaymentDetailsRepository.findAll();
        assertThat(loanRepaymentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoanRepaymentDetails() throws Exception {
        // Initialize the database
        loanRepaymentDetailsRepository.saveAndFlush(loanRepaymentDetails);

        int databaseSizeBeforeDelete = loanRepaymentDetailsRepository.findAll().size();

        // Delete the loanRepaymentDetails
        restLoanRepaymentDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, loanRepaymentDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoanRepaymentDetails> loanRepaymentDetailsList = loanRepaymentDetailsRepository.findAll();
        assertThat(loanRepaymentDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
