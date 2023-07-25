package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.LoanAccount;
import com.techvg.los.domain.LoanDisbursement;
import com.techvg.los.domain.Product;
import com.techvg.los.domain.SecurityUser;
import com.techvg.los.domain.enumeration.PaymentMode;
import com.techvg.los.repository.LoanDisbursementRepository;
import com.techvg.los.service.criteria.LoanDisbursementCriteria;
import com.techvg.los.service.dto.LoanDisbursementDTO;
import com.techvg.los.service.mapper.LoanDisbursementMapper;
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
 * Integration tests for the {@link LoanDisbursementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoanDisbursementResourceIT {

    private static final Instant DEFAULT_DT_DISB_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DT_DISB_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ACCOUNT_NO = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSC_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_DISB_AMOUNT = 1D;
    private static final Double UPDATED_DISB_AMOUNT = 2D;
    private static final Double SMALLER_DISB_AMOUNT = 1D - 1D;

    private static final Integer DEFAULT_DISBU_NUMBER = 1;
    private static final Integer UPDATED_DISBU_NUMBER = 2;
    private static final Integer SMALLER_DISBU_NUMBER = 1 - 1;

    private static final String DEFAULT_DISBURSEMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_DISBURSEMENT_STATUS = "BBBBBBBBBB";

    private static final PaymentMode DEFAULT_PAYMENT_MODE = PaymentMode.ONLINE;
    private static final PaymentMode UPDATED_PAYMENT_MODE = PaymentMode.CASH;

    private static final String DEFAULT_UTR_NO = "AAAAAAAAAA";
    private static final String UPDATED_UTR_NO = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/loan-disbursements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoanDisbursementRepository loanDisbursementRepository;

    @Autowired
    private LoanDisbursementMapper loanDisbursementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoanDisbursementMockMvc;

    private LoanDisbursement loanDisbursement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanDisbursement createEntity(EntityManager em) {
        LoanDisbursement loanDisbursement = new LoanDisbursement()
            .dtDisbDate(DEFAULT_DT_DISB_DATE)
            .accountNo(DEFAULT_ACCOUNT_NO)
            .ifscCode(DEFAULT_IFSC_CODE)
            .disbAmount(DEFAULT_DISB_AMOUNT)
            .disbuNumber(DEFAULT_DISBU_NUMBER)
            .disbursementStatus(DEFAULT_DISBURSEMENT_STATUS)
            .paymentMode(DEFAULT_PAYMENT_MODE)
            .utrNo(DEFAULT_UTR_NO)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5)
            .freeField6(DEFAULT_FREE_FIELD_6);
        return loanDisbursement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanDisbursement createUpdatedEntity(EntityManager em) {
        LoanDisbursement loanDisbursement = new LoanDisbursement()
            .dtDisbDate(UPDATED_DT_DISB_DATE)
            .accountNo(UPDATED_ACCOUNT_NO)
            .ifscCode(UPDATED_IFSC_CODE)
            .disbAmount(UPDATED_DISB_AMOUNT)
            .disbuNumber(UPDATED_DISBU_NUMBER)
            .disbursementStatus(UPDATED_DISBURSEMENT_STATUS)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .utrNo(UPDATED_UTR_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        return loanDisbursement;
    }

    @BeforeEach
    public void initTest() {
        loanDisbursement = createEntity(em);
    }

    @Test
    @Transactional
    void createLoanDisbursement() throws Exception {
        int databaseSizeBeforeCreate = loanDisbursementRepository.findAll().size();
        // Create the LoanDisbursement
        LoanDisbursementDTO loanDisbursementDTO = loanDisbursementMapper.toDto(loanDisbursement);
        restLoanDisbursementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanDisbursementDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LoanDisbursement in the database
        List<LoanDisbursement> loanDisbursementList = loanDisbursementRepository.findAll();
        assertThat(loanDisbursementList).hasSize(databaseSizeBeforeCreate + 1);
        LoanDisbursement testLoanDisbursement = loanDisbursementList.get(loanDisbursementList.size() - 1);
        assertThat(testLoanDisbursement.getDtDisbDate()).isEqualTo(DEFAULT_DT_DISB_DATE);
        assertThat(testLoanDisbursement.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testLoanDisbursement.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testLoanDisbursement.getDisbAmount()).isEqualTo(DEFAULT_DISB_AMOUNT);
        assertThat(testLoanDisbursement.getDisbuNumber()).isEqualTo(DEFAULT_DISBU_NUMBER);
        assertThat(testLoanDisbursement.getDisbursementStatus()).isEqualTo(DEFAULT_DISBURSEMENT_STATUS);
        assertThat(testLoanDisbursement.getPaymentMode()).isEqualTo(DEFAULT_PAYMENT_MODE);
        assertThat(testLoanDisbursement.getUtrNo()).isEqualTo(DEFAULT_UTR_NO);
        assertThat(testLoanDisbursement.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLoanDisbursement.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLoanDisbursement.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLoanDisbursement.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testLoanDisbursement.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testLoanDisbursement.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testLoanDisbursement.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testLoanDisbursement.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createLoanDisbursementWithExistingId() throws Exception {
        // Create the LoanDisbursement with an existing ID
        loanDisbursement.setId(1L);
        LoanDisbursementDTO loanDisbursementDTO = loanDisbursementMapper.toDto(loanDisbursement);

        int databaseSizeBeforeCreate = loanDisbursementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanDisbursementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanDisbursementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDisbursement in the database
        List<LoanDisbursement> loanDisbursementList = loanDisbursementRepository.findAll();
        assertThat(loanDisbursementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoanDisbursements() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList
        restLoanDisbursementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanDisbursement.getId().intValue())))
            .andExpect(jsonPath("$.[*].dtDisbDate").value(hasItem(DEFAULT_DT_DISB_DATE.toString())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].disbAmount").value(hasItem(DEFAULT_DISB_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].disbuNumber").value(hasItem(DEFAULT_DISBU_NUMBER)))
            .andExpect(jsonPath("$.[*].disbursementStatus").value(hasItem(DEFAULT_DISBURSEMENT_STATUS)))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].utrNo").value(hasItem(DEFAULT_UTR_NO)))
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
    void getLoanDisbursement() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get the loanDisbursement
        restLoanDisbursementMockMvc
            .perform(get(ENTITY_API_URL_ID, loanDisbursement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loanDisbursement.getId().intValue()))
            .andExpect(jsonPath("$.dtDisbDate").value(DEFAULT_DT_DISB_DATE.toString()))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE))
            .andExpect(jsonPath("$.disbAmount").value(DEFAULT_DISB_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.disbuNumber").value(DEFAULT_DISBU_NUMBER))
            .andExpect(jsonPath("$.disbursementStatus").value(DEFAULT_DISBURSEMENT_STATUS))
            .andExpect(jsonPath("$.paymentMode").value(DEFAULT_PAYMENT_MODE.toString()))
            .andExpect(jsonPath("$.utrNo").value(DEFAULT_UTR_NO))
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
    void getLoanDisbursementsByIdFiltering() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        Long id = loanDisbursement.getId();

        defaultLoanDisbursementShouldBeFound("id.equals=" + id);
        defaultLoanDisbursementShouldNotBeFound("id.notEquals=" + id);

        defaultLoanDisbursementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLoanDisbursementShouldNotBeFound("id.greaterThan=" + id);

        defaultLoanDisbursementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLoanDisbursementShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDtDisbDateIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where dtDisbDate equals to DEFAULT_DT_DISB_DATE
        defaultLoanDisbursementShouldBeFound("dtDisbDate.equals=" + DEFAULT_DT_DISB_DATE);

        // Get all the loanDisbursementList where dtDisbDate equals to UPDATED_DT_DISB_DATE
        defaultLoanDisbursementShouldNotBeFound("dtDisbDate.equals=" + UPDATED_DT_DISB_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDtDisbDateIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where dtDisbDate in DEFAULT_DT_DISB_DATE or UPDATED_DT_DISB_DATE
        defaultLoanDisbursementShouldBeFound("dtDisbDate.in=" + DEFAULT_DT_DISB_DATE + "," + UPDATED_DT_DISB_DATE);

        // Get all the loanDisbursementList where dtDisbDate equals to UPDATED_DT_DISB_DATE
        defaultLoanDisbursementShouldNotBeFound("dtDisbDate.in=" + UPDATED_DT_DISB_DATE);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDtDisbDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where dtDisbDate is not null
        defaultLoanDisbursementShouldBeFound("dtDisbDate.specified=true");

        // Get all the loanDisbursementList where dtDisbDate is null
        defaultLoanDisbursementShouldNotBeFound("dtDisbDate.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByAccountNoIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where accountNo equals to DEFAULT_ACCOUNT_NO
        defaultLoanDisbursementShouldBeFound("accountNo.equals=" + DEFAULT_ACCOUNT_NO);

        // Get all the loanDisbursementList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultLoanDisbursementShouldNotBeFound("accountNo.equals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByAccountNoIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where accountNo in DEFAULT_ACCOUNT_NO or UPDATED_ACCOUNT_NO
        defaultLoanDisbursementShouldBeFound("accountNo.in=" + DEFAULT_ACCOUNT_NO + "," + UPDATED_ACCOUNT_NO);

        // Get all the loanDisbursementList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultLoanDisbursementShouldNotBeFound("accountNo.in=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByAccountNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where accountNo is not null
        defaultLoanDisbursementShouldBeFound("accountNo.specified=true");

        // Get all the loanDisbursementList where accountNo is null
        defaultLoanDisbursementShouldNotBeFound("accountNo.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByAccountNoContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where accountNo contains DEFAULT_ACCOUNT_NO
        defaultLoanDisbursementShouldBeFound("accountNo.contains=" + DEFAULT_ACCOUNT_NO);

        // Get all the loanDisbursementList where accountNo contains UPDATED_ACCOUNT_NO
        defaultLoanDisbursementShouldNotBeFound("accountNo.contains=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByAccountNoNotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where accountNo does not contain DEFAULT_ACCOUNT_NO
        defaultLoanDisbursementShouldNotBeFound("accountNo.doesNotContain=" + DEFAULT_ACCOUNT_NO);

        // Get all the loanDisbursementList where accountNo does not contain UPDATED_ACCOUNT_NO
        defaultLoanDisbursementShouldBeFound("accountNo.doesNotContain=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByIfscCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where ifscCode equals to DEFAULT_IFSC_CODE
        defaultLoanDisbursementShouldBeFound("ifscCode.equals=" + DEFAULT_IFSC_CODE);

        // Get all the loanDisbursementList where ifscCode equals to UPDATED_IFSC_CODE
        defaultLoanDisbursementShouldNotBeFound("ifscCode.equals=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByIfscCodeIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where ifscCode in DEFAULT_IFSC_CODE or UPDATED_IFSC_CODE
        defaultLoanDisbursementShouldBeFound("ifscCode.in=" + DEFAULT_IFSC_CODE + "," + UPDATED_IFSC_CODE);

        // Get all the loanDisbursementList where ifscCode equals to UPDATED_IFSC_CODE
        defaultLoanDisbursementShouldNotBeFound("ifscCode.in=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByIfscCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where ifscCode is not null
        defaultLoanDisbursementShouldBeFound("ifscCode.specified=true");

        // Get all the loanDisbursementList where ifscCode is null
        defaultLoanDisbursementShouldNotBeFound("ifscCode.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByIfscCodeContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where ifscCode contains DEFAULT_IFSC_CODE
        defaultLoanDisbursementShouldBeFound("ifscCode.contains=" + DEFAULT_IFSC_CODE);

        // Get all the loanDisbursementList where ifscCode contains UPDATED_IFSC_CODE
        defaultLoanDisbursementShouldNotBeFound("ifscCode.contains=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByIfscCodeNotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where ifscCode does not contain DEFAULT_IFSC_CODE
        defaultLoanDisbursementShouldNotBeFound("ifscCode.doesNotContain=" + DEFAULT_IFSC_CODE);

        // Get all the loanDisbursementList where ifscCode does not contain UPDATED_IFSC_CODE
        defaultLoanDisbursementShouldBeFound("ifscCode.doesNotContain=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbAmount equals to DEFAULT_DISB_AMOUNT
        defaultLoanDisbursementShouldBeFound("disbAmount.equals=" + DEFAULT_DISB_AMOUNT);

        // Get all the loanDisbursementList where disbAmount equals to UPDATED_DISB_AMOUNT
        defaultLoanDisbursementShouldNotBeFound("disbAmount.equals=" + UPDATED_DISB_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbAmountIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbAmount in DEFAULT_DISB_AMOUNT or UPDATED_DISB_AMOUNT
        defaultLoanDisbursementShouldBeFound("disbAmount.in=" + DEFAULT_DISB_AMOUNT + "," + UPDATED_DISB_AMOUNT);

        // Get all the loanDisbursementList where disbAmount equals to UPDATED_DISB_AMOUNT
        defaultLoanDisbursementShouldNotBeFound("disbAmount.in=" + UPDATED_DISB_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbAmount is not null
        defaultLoanDisbursementShouldBeFound("disbAmount.specified=true");

        // Get all the loanDisbursementList where disbAmount is null
        defaultLoanDisbursementShouldNotBeFound("disbAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbAmount is greater than or equal to DEFAULT_DISB_AMOUNT
        defaultLoanDisbursementShouldBeFound("disbAmount.greaterThanOrEqual=" + DEFAULT_DISB_AMOUNT);

        // Get all the loanDisbursementList where disbAmount is greater than or equal to UPDATED_DISB_AMOUNT
        defaultLoanDisbursementShouldNotBeFound("disbAmount.greaterThanOrEqual=" + UPDATED_DISB_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbAmount is less than or equal to DEFAULT_DISB_AMOUNT
        defaultLoanDisbursementShouldBeFound("disbAmount.lessThanOrEqual=" + DEFAULT_DISB_AMOUNT);

        // Get all the loanDisbursementList where disbAmount is less than or equal to SMALLER_DISB_AMOUNT
        defaultLoanDisbursementShouldNotBeFound("disbAmount.lessThanOrEqual=" + SMALLER_DISB_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbAmount is less than DEFAULT_DISB_AMOUNT
        defaultLoanDisbursementShouldNotBeFound("disbAmount.lessThan=" + DEFAULT_DISB_AMOUNT);

        // Get all the loanDisbursementList where disbAmount is less than UPDATED_DISB_AMOUNT
        defaultLoanDisbursementShouldBeFound("disbAmount.lessThan=" + UPDATED_DISB_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbAmount is greater than DEFAULT_DISB_AMOUNT
        defaultLoanDisbursementShouldNotBeFound("disbAmount.greaterThan=" + DEFAULT_DISB_AMOUNT);

        // Get all the loanDisbursementList where disbAmount is greater than SMALLER_DISB_AMOUNT
        defaultLoanDisbursementShouldBeFound("disbAmount.greaterThan=" + SMALLER_DISB_AMOUNT);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbuNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbuNumber equals to DEFAULT_DISBU_NUMBER
        defaultLoanDisbursementShouldBeFound("disbuNumber.equals=" + DEFAULT_DISBU_NUMBER);

        // Get all the loanDisbursementList where disbuNumber equals to UPDATED_DISBU_NUMBER
        defaultLoanDisbursementShouldNotBeFound("disbuNumber.equals=" + UPDATED_DISBU_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbuNumberIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbuNumber in DEFAULT_DISBU_NUMBER or UPDATED_DISBU_NUMBER
        defaultLoanDisbursementShouldBeFound("disbuNumber.in=" + DEFAULT_DISBU_NUMBER + "," + UPDATED_DISBU_NUMBER);

        // Get all the loanDisbursementList where disbuNumber equals to UPDATED_DISBU_NUMBER
        defaultLoanDisbursementShouldNotBeFound("disbuNumber.in=" + UPDATED_DISBU_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbuNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbuNumber is not null
        defaultLoanDisbursementShouldBeFound("disbuNumber.specified=true");

        // Get all the loanDisbursementList where disbuNumber is null
        defaultLoanDisbursementShouldNotBeFound("disbuNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbuNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbuNumber is greater than or equal to DEFAULT_DISBU_NUMBER
        defaultLoanDisbursementShouldBeFound("disbuNumber.greaterThanOrEqual=" + DEFAULT_DISBU_NUMBER);

        // Get all the loanDisbursementList where disbuNumber is greater than or equal to UPDATED_DISBU_NUMBER
        defaultLoanDisbursementShouldNotBeFound("disbuNumber.greaterThanOrEqual=" + UPDATED_DISBU_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbuNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbuNumber is less than or equal to DEFAULT_DISBU_NUMBER
        defaultLoanDisbursementShouldBeFound("disbuNumber.lessThanOrEqual=" + DEFAULT_DISBU_NUMBER);

        // Get all the loanDisbursementList where disbuNumber is less than or equal to SMALLER_DISBU_NUMBER
        defaultLoanDisbursementShouldNotBeFound("disbuNumber.lessThanOrEqual=" + SMALLER_DISBU_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbuNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbuNumber is less than DEFAULT_DISBU_NUMBER
        defaultLoanDisbursementShouldNotBeFound("disbuNumber.lessThan=" + DEFAULT_DISBU_NUMBER);

        // Get all the loanDisbursementList where disbuNumber is less than UPDATED_DISBU_NUMBER
        defaultLoanDisbursementShouldBeFound("disbuNumber.lessThan=" + UPDATED_DISBU_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbuNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbuNumber is greater than DEFAULT_DISBU_NUMBER
        defaultLoanDisbursementShouldNotBeFound("disbuNumber.greaterThan=" + DEFAULT_DISBU_NUMBER);

        // Get all the loanDisbursementList where disbuNumber is greater than SMALLER_DISBU_NUMBER
        defaultLoanDisbursementShouldBeFound("disbuNumber.greaterThan=" + SMALLER_DISBU_NUMBER);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbursementStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbursementStatus equals to DEFAULT_DISBURSEMENT_STATUS
        defaultLoanDisbursementShouldBeFound("disbursementStatus.equals=" + DEFAULT_DISBURSEMENT_STATUS);

        // Get all the loanDisbursementList where disbursementStatus equals to UPDATED_DISBURSEMENT_STATUS
        defaultLoanDisbursementShouldNotBeFound("disbursementStatus.equals=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbursementStatusIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbursementStatus in DEFAULT_DISBURSEMENT_STATUS or UPDATED_DISBURSEMENT_STATUS
        defaultLoanDisbursementShouldBeFound("disbursementStatus.in=" + DEFAULT_DISBURSEMENT_STATUS + "," + UPDATED_DISBURSEMENT_STATUS);

        // Get all the loanDisbursementList where disbursementStatus equals to UPDATED_DISBURSEMENT_STATUS
        defaultLoanDisbursementShouldNotBeFound("disbursementStatus.in=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbursementStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbursementStatus is not null
        defaultLoanDisbursementShouldBeFound("disbursementStatus.specified=true");

        // Get all the loanDisbursementList where disbursementStatus is null
        defaultLoanDisbursementShouldNotBeFound("disbursementStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbursementStatusContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbursementStatus contains DEFAULT_DISBURSEMENT_STATUS
        defaultLoanDisbursementShouldBeFound("disbursementStatus.contains=" + DEFAULT_DISBURSEMENT_STATUS);

        // Get all the loanDisbursementList where disbursementStatus contains UPDATED_DISBURSEMENT_STATUS
        defaultLoanDisbursementShouldNotBeFound("disbursementStatus.contains=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByDisbursementStatusNotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where disbursementStatus does not contain DEFAULT_DISBURSEMENT_STATUS
        defaultLoanDisbursementShouldNotBeFound("disbursementStatus.doesNotContain=" + DEFAULT_DISBURSEMENT_STATUS);

        // Get all the loanDisbursementList where disbursementStatus does not contain UPDATED_DISBURSEMENT_STATUS
        defaultLoanDisbursementShouldBeFound("disbursementStatus.doesNotContain=" + UPDATED_DISBURSEMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByPaymentModeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where paymentMode equals to DEFAULT_PAYMENT_MODE
        defaultLoanDisbursementShouldBeFound("paymentMode.equals=" + DEFAULT_PAYMENT_MODE);

        // Get all the loanDisbursementList where paymentMode equals to UPDATED_PAYMENT_MODE
        defaultLoanDisbursementShouldNotBeFound("paymentMode.equals=" + UPDATED_PAYMENT_MODE);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByPaymentModeIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where paymentMode in DEFAULT_PAYMENT_MODE or UPDATED_PAYMENT_MODE
        defaultLoanDisbursementShouldBeFound("paymentMode.in=" + DEFAULT_PAYMENT_MODE + "," + UPDATED_PAYMENT_MODE);

        // Get all the loanDisbursementList where paymentMode equals to UPDATED_PAYMENT_MODE
        defaultLoanDisbursementShouldNotBeFound("paymentMode.in=" + UPDATED_PAYMENT_MODE);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByPaymentModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where paymentMode is not null
        defaultLoanDisbursementShouldBeFound("paymentMode.specified=true");

        // Get all the loanDisbursementList where paymentMode is null
        defaultLoanDisbursementShouldNotBeFound("paymentMode.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByUtrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where utrNo equals to DEFAULT_UTR_NO
        defaultLoanDisbursementShouldBeFound("utrNo.equals=" + DEFAULT_UTR_NO);

        // Get all the loanDisbursementList where utrNo equals to UPDATED_UTR_NO
        defaultLoanDisbursementShouldNotBeFound("utrNo.equals=" + UPDATED_UTR_NO);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByUtrNoIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where utrNo in DEFAULT_UTR_NO or UPDATED_UTR_NO
        defaultLoanDisbursementShouldBeFound("utrNo.in=" + DEFAULT_UTR_NO + "," + UPDATED_UTR_NO);

        // Get all the loanDisbursementList where utrNo equals to UPDATED_UTR_NO
        defaultLoanDisbursementShouldNotBeFound("utrNo.in=" + UPDATED_UTR_NO);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByUtrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where utrNo is not null
        defaultLoanDisbursementShouldBeFound("utrNo.specified=true");

        // Get all the loanDisbursementList where utrNo is null
        defaultLoanDisbursementShouldNotBeFound("utrNo.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByUtrNoContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where utrNo contains DEFAULT_UTR_NO
        defaultLoanDisbursementShouldBeFound("utrNo.contains=" + DEFAULT_UTR_NO);

        // Get all the loanDisbursementList where utrNo contains UPDATED_UTR_NO
        defaultLoanDisbursementShouldNotBeFound("utrNo.contains=" + UPDATED_UTR_NO);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByUtrNoNotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where utrNo does not contain DEFAULT_UTR_NO
        defaultLoanDisbursementShouldNotBeFound("utrNo.doesNotContain=" + DEFAULT_UTR_NO);

        // Get all the loanDisbursementList where utrNo does not contain UPDATED_UTR_NO
        defaultLoanDisbursementShouldBeFound("utrNo.doesNotContain=" + UPDATED_UTR_NO);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLoanDisbursementShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the loanDisbursementList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanDisbursementShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLoanDisbursementShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the loanDisbursementList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanDisbursementShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where lastModified is not null
        defaultLoanDisbursementShouldBeFound("lastModified.specified=true");

        // Get all the loanDisbursementList where lastModified is null
        defaultLoanDisbursementShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLoanDisbursementShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanDisbursementList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanDisbursementShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLoanDisbursementShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the loanDisbursementList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanDisbursementShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where lastModifiedBy is not null
        defaultLoanDisbursementShouldBeFound("lastModifiedBy.specified=true");

        // Get all the loanDisbursementList where lastModifiedBy is null
        defaultLoanDisbursementShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLoanDisbursementShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanDisbursementList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLoanDisbursementShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLoanDisbursementShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanDisbursementList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLoanDisbursementShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultLoanDisbursementShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanDisbursementList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanDisbursementShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultLoanDisbursementShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the loanDisbursementList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanDisbursementShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField1 is not null
        defaultLoanDisbursementShouldBeFound("freeField1.specified=true");

        // Get all the loanDisbursementList where freeField1 is null
        defaultLoanDisbursementShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultLoanDisbursementShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanDisbursementList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultLoanDisbursementShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultLoanDisbursementShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanDisbursementList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultLoanDisbursementShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultLoanDisbursementShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanDisbursementList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanDisbursementShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultLoanDisbursementShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the loanDisbursementList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanDisbursementShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField2 is not null
        defaultLoanDisbursementShouldBeFound("freeField2.specified=true");

        // Get all the loanDisbursementList where freeField2 is null
        defaultLoanDisbursementShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultLoanDisbursementShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanDisbursementList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultLoanDisbursementShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultLoanDisbursementShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanDisbursementList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultLoanDisbursementShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultLoanDisbursementShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanDisbursementList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanDisbursementShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultLoanDisbursementShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the loanDisbursementList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanDisbursementShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField3 is not null
        defaultLoanDisbursementShouldBeFound("freeField3.specified=true");

        // Get all the loanDisbursementList where freeField3 is null
        defaultLoanDisbursementShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultLoanDisbursementShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanDisbursementList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultLoanDisbursementShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultLoanDisbursementShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanDisbursementList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultLoanDisbursementShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultLoanDisbursementShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the loanDisbursementList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultLoanDisbursementShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultLoanDisbursementShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the loanDisbursementList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultLoanDisbursementShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField4 is not null
        defaultLoanDisbursementShouldBeFound("freeField4.specified=true");

        // Get all the loanDisbursementList where freeField4 is null
        defaultLoanDisbursementShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultLoanDisbursementShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the loanDisbursementList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultLoanDisbursementShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultLoanDisbursementShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the loanDisbursementList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultLoanDisbursementShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultLoanDisbursementShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the loanDisbursementList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultLoanDisbursementShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultLoanDisbursementShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the loanDisbursementList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultLoanDisbursementShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField5 is not null
        defaultLoanDisbursementShouldBeFound("freeField5.specified=true");

        // Get all the loanDisbursementList where freeField5 is null
        defaultLoanDisbursementShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultLoanDisbursementShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the loanDisbursementList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultLoanDisbursementShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultLoanDisbursementShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the loanDisbursementList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultLoanDisbursementShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultLoanDisbursementShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the loanDisbursementList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultLoanDisbursementShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultLoanDisbursementShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the loanDisbursementList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultLoanDisbursementShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField6 is not null
        defaultLoanDisbursementShouldBeFound("freeField6.specified=true");

        // Get all the loanDisbursementList where freeField6 is null
        defaultLoanDisbursementShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultLoanDisbursementShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the loanDisbursementList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultLoanDisbursementShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        // Get all the loanDisbursementList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultLoanDisbursementShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the loanDisbursementList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultLoanDisbursementShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByProductIsEqualToSomething() throws Exception {
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            loanDisbursementRepository.saveAndFlush(loanDisbursement);
            product = ProductResourceIT.createEntity(em);
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        loanDisbursement.setProduct(product);
        loanDisbursementRepository.saveAndFlush(loanDisbursement);
        Long productId = product.getId();

        // Get all the loanDisbursementList where product equals to productId
        defaultLoanDisbursementShouldBeFound("productId.equals=" + productId);

        // Get all the loanDisbursementList where product equals to (productId + 1)
        defaultLoanDisbursementShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsBySecurityUserIsEqualToSomething() throws Exception {
        SecurityUser securityUser;
        if (TestUtil.findAll(em, SecurityUser.class).isEmpty()) {
            loanDisbursementRepository.saveAndFlush(loanDisbursement);
            securityUser = SecurityUserResourceIT.createEntity(em);
        } else {
            securityUser = TestUtil.findAll(em, SecurityUser.class).get(0);
        }
        em.persist(securityUser);
        em.flush();
        loanDisbursement.setSecurityUser(securityUser);
        loanDisbursementRepository.saveAndFlush(loanDisbursement);
        Long securityUserId = securityUser.getId();

        // Get all the loanDisbursementList where securityUser equals to securityUserId
        defaultLoanDisbursementShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the loanDisbursementList where securityUser equals to (securityUserId + 1)
        defaultLoanDisbursementShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    @Test
    @Transactional
    void getAllLoanDisbursementsByLoanAccountIsEqualToSomething() throws Exception {
        LoanAccount loanAccount;
        if (TestUtil.findAll(em, LoanAccount.class).isEmpty()) {
            loanDisbursementRepository.saveAndFlush(loanDisbursement);
            loanAccount = LoanAccountResourceIT.createEntity(em);
        } else {
            loanAccount = TestUtil.findAll(em, LoanAccount.class).get(0);
        }
        em.persist(loanAccount);
        em.flush();
        loanDisbursement.setLoanAccount(loanAccount);
        loanDisbursementRepository.saveAndFlush(loanDisbursement);
        Long loanAccountId = loanAccount.getId();

        // Get all the loanDisbursementList where loanAccount equals to loanAccountId
        defaultLoanDisbursementShouldBeFound("loanAccountId.equals=" + loanAccountId);

        // Get all the loanDisbursementList where loanAccount equals to (loanAccountId + 1)
        defaultLoanDisbursementShouldNotBeFound("loanAccountId.equals=" + (loanAccountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLoanDisbursementShouldBeFound(String filter) throws Exception {
        restLoanDisbursementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanDisbursement.getId().intValue())))
            .andExpect(jsonPath("$.[*].dtDisbDate").value(hasItem(DEFAULT_DT_DISB_DATE.toString())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].disbAmount").value(hasItem(DEFAULT_DISB_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].disbuNumber").value(hasItem(DEFAULT_DISBU_NUMBER)))
            .andExpect(jsonPath("$.[*].disbursementStatus").value(hasItem(DEFAULT_DISBURSEMENT_STATUS)))
            .andExpect(jsonPath("$.[*].paymentMode").value(hasItem(DEFAULT_PAYMENT_MODE.toString())))
            .andExpect(jsonPath("$.[*].utrNo").value(hasItem(DEFAULT_UTR_NO)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));

        // Check, that the count call also returns 1
        restLoanDisbursementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLoanDisbursementShouldNotBeFound(String filter) throws Exception {
        restLoanDisbursementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLoanDisbursementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLoanDisbursement() throws Exception {
        // Get the loanDisbursement
        restLoanDisbursementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLoanDisbursement() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        int databaseSizeBeforeUpdate = loanDisbursementRepository.findAll().size();

        // Update the loanDisbursement
        LoanDisbursement updatedLoanDisbursement = loanDisbursementRepository.findById(loanDisbursement.getId()).get();
        // Disconnect from session so that the updates on updatedLoanDisbursement are not directly saved in db
        em.detach(updatedLoanDisbursement);
        updatedLoanDisbursement
            .dtDisbDate(UPDATED_DT_DISB_DATE)
            .accountNo(UPDATED_ACCOUNT_NO)
            .ifscCode(UPDATED_IFSC_CODE)
            .disbAmount(UPDATED_DISB_AMOUNT)
            .disbuNumber(UPDATED_DISBU_NUMBER)
            .disbursementStatus(UPDATED_DISBURSEMENT_STATUS)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .utrNo(UPDATED_UTR_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        LoanDisbursementDTO loanDisbursementDTO = loanDisbursementMapper.toDto(updatedLoanDisbursement);

        restLoanDisbursementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanDisbursementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDisbursementDTO))
            )
            .andExpect(status().isOk());

        // Validate the LoanDisbursement in the database
        List<LoanDisbursement> loanDisbursementList = loanDisbursementRepository.findAll();
        assertThat(loanDisbursementList).hasSize(databaseSizeBeforeUpdate);
        LoanDisbursement testLoanDisbursement = loanDisbursementList.get(loanDisbursementList.size() - 1);
        assertThat(testLoanDisbursement.getDtDisbDate()).isEqualTo(UPDATED_DT_DISB_DATE);
        assertThat(testLoanDisbursement.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testLoanDisbursement.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testLoanDisbursement.getDisbAmount()).isEqualTo(UPDATED_DISB_AMOUNT);
        assertThat(testLoanDisbursement.getDisbuNumber()).isEqualTo(UPDATED_DISBU_NUMBER);
        assertThat(testLoanDisbursement.getDisbursementStatus()).isEqualTo(UPDATED_DISBURSEMENT_STATUS);
        assertThat(testLoanDisbursement.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testLoanDisbursement.getUtrNo()).isEqualTo(UPDATED_UTR_NO);
        assertThat(testLoanDisbursement.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanDisbursement.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanDisbursement.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanDisbursement.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanDisbursement.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testLoanDisbursement.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testLoanDisbursement.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testLoanDisbursement.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingLoanDisbursement() throws Exception {
        int databaseSizeBeforeUpdate = loanDisbursementRepository.findAll().size();
        loanDisbursement.setId(count.incrementAndGet());

        // Create the LoanDisbursement
        LoanDisbursementDTO loanDisbursementDTO = loanDisbursementMapper.toDto(loanDisbursement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanDisbursementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanDisbursementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDisbursementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDisbursement in the database
        List<LoanDisbursement> loanDisbursementList = loanDisbursementRepository.findAll();
        assertThat(loanDisbursementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoanDisbursement() throws Exception {
        int databaseSizeBeforeUpdate = loanDisbursementRepository.findAll().size();
        loanDisbursement.setId(count.incrementAndGet());

        // Create the LoanDisbursement
        LoanDisbursementDTO loanDisbursementDTO = loanDisbursementMapper.toDto(loanDisbursement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDisbursementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanDisbursementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDisbursement in the database
        List<LoanDisbursement> loanDisbursementList = loanDisbursementRepository.findAll();
        assertThat(loanDisbursementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoanDisbursement() throws Exception {
        int databaseSizeBeforeUpdate = loanDisbursementRepository.findAll().size();
        loanDisbursement.setId(count.incrementAndGet());

        // Create the LoanDisbursement
        LoanDisbursementDTO loanDisbursementDTO = loanDisbursementMapper.toDto(loanDisbursement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDisbursementMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanDisbursementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanDisbursement in the database
        List<LoanDisbursement> loanDisbursementList = loanDisbursementRepository.findAll();
        assertThat(loanDisbursementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoanDisbursementWithPatch() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        int databaseSizeBeforeUpdate = loanDisbursementRepository.findAll().size();

        // Update the loanDisbursement using partial update
        LoanDisbursement partialUpdatedLoanDisbursement = new LoanDisbursement();
        partialUpdatedLoanDisbursement.setId(loanDisbursement.getId());

        partialUpdatedLoanDisbursement
            .dtDisbDate(UPDATED_DT_DISB_DATE)
            .accountNo(UPDATED_ACCOUNT_NO)
            .ifscCode(UPDATED_IFSC_CODE)
            .disbAmount(UPDATED_DISB_AMOUNT)
            .disbuNumber(UPDATED_DISBU_NUMBER)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .utrNo(UPDATED_UTR_NO)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restLoanDisbursementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanDisbursement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanDisbursement))
            )
            .andExpect(status().isOk());

        // Validate the LoanDisbursement in the database
        List<LoanDisbursement> loanDisbursementList = loanDisbursementRepository.findAll();
        assertThat(loanDisbursementList).hasSize(databaseSizeBeforeUpdate);
        LoanDisbursement testLoanDisbursement = loanDisbursementList.get(loanDisbursementList.size() - 1);
        assertThat(testLoanDisbursement.getDtDisbDate()).isEqualTo(UPDATED_DT_DISB_DATE);
        assertThat(testLoanDisbursement.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testLoanDisbursement.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testLoanDisbursement.getDisbAmount()).isEqualTo(UPDATED_DISB_AMOUNT);
        assertThat(testLoanDisbursement.getDisbuNumber()).isEqualTo(UPDATED_DISBU_NUMBER);
        assertThat(testLoanDisbursement.getDisbursementStatus()).isEqualTo(DEFAULT_DISBURSEMENT_STATUS);
        assertThat(testLoanDisbursement.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testLoanDisbursement.getUtrNo()).isEqualTo(UPDATED_UTR_NO);
        assertThat(testLoanDisbursement.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLoanDisbursement.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanDisbursement.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanDisbursement.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanDisbursement.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testLoanDisbursement.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testLoanDisbursement.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testLoanDisbursement.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateLoanDisbursementWithPatch() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        int databaseSizeBeforeUpdate = loanDisbursementRepository.findAll().size();

        // Update the loanDisbursement using partial update
        LoanDisbursement partialUpdatedLoanDisbursement = new LoanDisbursement();
        partialUpdatedLoanDisbursement.setId(loanDisbursement.getId());

        partialUpdatedLoanDisbursement
            .dtDisbDate(UPDATED_DT_DISB_DATE)
            .accountNo(UPDATED_ACCOUNT_NO)
            .ifscCode(UPDATED_IFSC_CODE)
            .disbAmount(UPDATED_DISB_AMOUNT)
            .disbuNumber(UPDATED_DISBU_NUMBER)
            .disbursementStatus(UPDATED_DISBURSEMENT_STATUS)
            .paymentMode(UPDATED_PAYMENT_MODE)
            .utrNo(UPDATED_UTR_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restLoanDisbursementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanDisbursement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanDisbursement))
            )
            .andExpect(status().isOk());

        // Validate the LoanDisbursement in the database
        List<LoanDisbursement> loanDisbursementList = loanDisbursementRepository.findAll();
        assertThat(loanDisbursementList).hasSize(databaseSizeBeforeUpdate);
        LoanDisbursement testLoanDisbursement = loanDisbursementList.get(loanDisbursementList.size() - 1);
        assertThat(testLoanDisbursement.getDtDisbDate()).isEqualTo(UPDATED_DT_DISB_DATE);
        assertThat(testLoanDisbursement.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testLoanDisbursement.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testLoanDisbursement.getDisbAmount()).isEqualTo(UPDATED_DISB_AMOUNT);
        assertThat(testLoanDisbursement.getDisbuNumber()).isEqualTo(UPDATED_DISBU_NUMBER);
        assertThat(testLoanDisbursement.getDisbursementStatus()).isEqualTo(UPDATED_DISBURSEMENT_STATUS);
        assertThat(testLoanDisbursement.getPaymentMode()).isEqualTo(UPDATED_PAYMENT_MODE);
        assertThat(testLoanDisbursement.getUtrNo()).isEqualTo(UPDATED_UTR_NO);
        assertThat(testLoanDisbursement.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanDisbursement.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanDisbursement.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanDisbursement.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanDisbursement.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testLoanDisbursement.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testLoanDisbursement.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testLoanDisbursement.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingLoanDisbursement() throws Exception {
        int databaseSizeBeforeUpdate = loanDisbursementRepository.findAll().size();
        loanDisbursement.setId(count.incrementAndGet());

        // Create the LoanDisbursement
        LoanDisbursementDTO loanDisbursementDTO = loanDisbursementMapper.toDto(loanDisbursement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanDisbursementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loanDisbursementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanDisbursementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDisbursement in the database
        List<LoanDisbursement> loanDisbursementList = loanDisbursementRepository.findAll();
        assertThat(loanDisbursementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoanDisbursement() throws Exception {
        int databaseSizeBeforeUpdate = loanDisbursementRepository.findAll().size();
        loanDisbursement.setId(count.incrementAndGet());

        // Create the LoanDisbursement
        LoanDisbursementDTO loanDisbursementDTO = loanDisbursementMapper.toDto(loanDisbursement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDisbursementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanDisbursementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanDisbursement in the database
        List<LoanDisbursement> loanDisbursementList = loanDisbursementRepository.findAll();
        assertThat(loanDisbursementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoanDisbursement() throws Exception {
        int databaseSizeBeforeUpdate = loanDisbursementRepository.findAll().size();
        loanDisbursement.setId(count.incrementAndGet());

        // Create the LoanDisbursement
        LoanDisbursementDTO loanDisbursementDTO = loanDisbursementMapper.toDto(loanDisbursement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanDisbursementMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanDisbursementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanDisbursement in the database
        List<LoanDisbursement> loanDisbursementList = loanDisbursementRepository.findAll();
        assertThat(loanDisbursementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoanDisbursement() throws Exception {
        // Initialize the database
        loanDisbursementRepository.saveAndFlush(loanDisbursement);

        int databaseSizeBeforeDelete = loanDisbursementRepository.findAll().size();

        // Delete the loanDisbursement
        restLoanDisbursementMockMvc
            .perform(delete(ENTITY_API_URL_ID, loanDisbursement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoanDisbursement> loanDisbursementList = loanDisbursementRepository.findAll();
        assertThat(loanDisbursementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
