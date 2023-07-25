package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.Epay;
import com.techvg.los.domain.enumeration.RepaymentFreqency;
import com.techvg.los.repository.EpayRepository;
import com.techvg.los.service.criteria.EpayCriteria;
import com.techvg.los.service.dto.EpayDTO;
import com.techvg.los.service.mapper.EpayMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link EpayResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EpayResourceIT {

    private static final String DEFAULT_INSTRUMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUMENT_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_FROM_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DT_FROM_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DT_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_TO_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DT_TO_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_BANK_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_BRANCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_BRANCH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NO = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NO = "BBBBBBBBBB";

    private static final Double DEFAULT_MAX_CEIL_AMOUNT = 1D;
    private static final Double UPDATED_MAX_CEIL_AMOUNT = 2D;
    private static final Double SMALLER_MAX_CEIL_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_INSTALLMENT_AMOUNT = 1D;
    private static final Double UPDATED_INSTALLMENT_AMOUNT = 2D;
    private static final Double SMALLER_INSTALLMENT_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_MAX_INSTALLMENT_AMOUNT = 1D;
    private static final Double UPDATED_MAX_INSTALLMENT_AMOUNT = 2D;
    private static final Double SMALLER_MAX_INSTALLMENT_AMOUNT = 1D - 1D;

    private static final String DEFAULT_MANDATE_REF_NO = "AAAAAAAAAA";
    private static final String UPDATED_MANDATE_REF_NO = "BBBBBBBBBB";

    private static final String DEFAULT_DEPOSIT_BANK = "AAAAAAAAAA";
    private static final String UPDATED_DEPOSIT_BANK = "BBBBBBBBBB";

    private static final String DEFAULT_MANDATE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MANDATE_TYPE = "BBBBBBBBBB";

    private static final RepaymentFreqency DEFAULT_FREQUENCY = RepaymentFreqency.MONTHLY;
    private static final RepaymentFreqency UPDATED_FREQUENCY = RepaymentFreqency.QUARTERLY;

    private static final String DEFAULT_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_UTR_NO = "AAAAAAAAAA";
    private static final String UPDATED_UTR_NO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

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

    private static final String ENTITY_API_URL = "/api/epays";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EpayRepository epayRepository;

    @Autowired
    private EpayMapper epayMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEpayMockMvc;

    private Epay epay;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Epay createEntity(EntityManager em) {
        Epay epay = new Epay()
            .instrumentType(DEFAULT_INSTRUMENT_TYPE)
            .dtFromDate(DEFAULT_DT_FROM_DATE)
            .dtToDate(DEFAULT_DT_TO_DATE)
            .bankCode(DEFAULT_BANK_CODE)
            .bankBranchCode(DEFAULT_BANK_BRANCH_CODE)
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .accountNo(DEFAULT_ACCOUNT_NO)
            .maxCeilAmount(DEFAULT_MAX_CEIL_AMOUNT)
            .installmentAmount(DEFAULT_INSTALLMENT_AMOUNT)
            .maxInstallmentAmount(DEFAULT_MAX_INSTALLMENT_AMOUNT)
            .mandateRefNo(DEFAULT_MANDATE_REF_NO)
            .depositBank(DEFAULT_DEPOSIT_BANK)
            .mandateType(DEFAULT_MANDATE_TYPE)
            .frequency(DEFAULT_FREQUENCY)
            .ifscCode(DEFAULT_IFSC_CODE)
            .utrNo(DEFAULT_UTR_NO)
            .isDeleted(DEFAULT_IS_DELETED)
            .isActive(DEFAULT_IS_ACTIVE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4);
        return epay;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Epay createUpdatedEntity(EntityManager em) {
        Epay epay = new Epay()
            .instrumentType(UPDATED_INSTRUMENT_TYPE)
            .dtFromDate(UPDATED_DT_FROM_DATE)
            .dtToDate(UPDATED_DT_TO_DATE)
            .bankCode(UPDATED_BANK_CODE)
            .bankBranchCode(UPDATED_BANK_BRANCH_CODE)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountNo(UPDATED_ACCOUNT_NO)
            .maxCeilAmount(UPDATED_MAX_CEIL_AMOUNT)
            .installmentAmount(UPDATED_INSTALLMENT_AMOUNT)
            .maxInstallmentAmount(UPDATED_MAX_INSTALLMENT_AMOUNT)
            .mandateRefNo(UPDATED_MANDATE_REF_NO)
            .depositBank(UPDATED_DEPOSIT_BANK)
            .mandateType(UPDATED_MANDATE_TYPE)
            .frequency(UPDATED_FREQUENCY)
            .ifscCode(UPDATED_IFSC_CODE)
            .utrNo(UPDATED_UTR_NO)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        return epay;
    }

    @BeforeEach
    public void initTest() {
        epay = createEntity(em);
    }

    @Test
    @Transactional
    void createEpay() throws Exception {
        int databaseSizeBeforeCreate = epayRepository.findAll().size();
        // Create the Epay
        EpayDTO epayDTO = epayMapper.toDto(epay);
        restEpayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(epayDTO)))
            .andExpect(status().isCreated());

        // Validate the Epay in the database
        List<Epay> epayList = epayRepository.findAll();
        assertThat(epayList).hasSize(databaseSizeBeforeCreate + 1);
        Epay testEpay = epayList.get(epayList.size() - 1);
        assertThat(testEpay.getInstrumentType()).isEqualTo(DEFAULT_INSTRUMENT_TYPE);
        assertThat(testEpay.getDtFromDate()).isEqualTo(DEFAULT_DT_FROM_DATE);
        assertThat(testEpay.getDtToDate()).isEqualTo(DEFAULT_DT_TO_DATE);
        assertThat(testEpay.getBankCode()).isEqualTo(DEFAULT_BANK_CODE);
        assertThat(testEpay.getBankBranchCode()).isEqualTo(DEFAULT_BANK_BRANCH_CODE);
        assertThat(testEpay.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testEpay.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testEpay.getMaxCeilAmount()).isEqualTo(DEFAULT_MAX_CEIL_AMOUNT);
        assertThat(testEpay.getInstallmentAmount()).isEqualTo(DEFAULT_INSTALLMENT_AMOUNT);
        assertThat(testEpay.getMaxInstallmentAmount()).isEqualTo(DEFAULT_MAX_INSTALLMENT_AMOUNT);
        assertThat(testEpay.getMandateRefNo()).isEqualTo(DEFAULT_MANDATE_REF_NO);
        assertThat(testEpay.getDepositBank()).isEqualTo(DEFAULT_DEPOSIT_BANK);
        assertThat(testEpay.getMandateType()).isEqualTo(DEFAULT_MANDATE_TYPE);
        assertThat(testEpay.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
        assertThat(testEpay.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testEpay.getUtrNo()).isEqualTo(DEFAULT_UTR_NO);
        assertThat(testEpay.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testEpay.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testEpay.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testEpay.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testEpay.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testEpay.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testEpay.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testEpay.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void createEpayWithExistingId() throws Exception {
        // Create the Epay with an existing ID
        epay.setId(1L);
        EpayDTO epayDTO = epayMapper.toDto(epay);

        int databaseSizeBeforeCreate = epayRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEpayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(epayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Epay in the database
        List<Epay> epayList = epayRepository.findAll();
        assertThat(epayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEpays() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList
        restEpayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(epay.getId().intValue())))
            .andExpect(jsonPath("$.[*].instrumentType").value(hasItem(DEFAULT_INSTRUMENT_TYPE)))
            .andExpect(jsonPath("$.[*].dtFromDate").value(hasItem(DEFAULT_DT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].dtToDate").value(hasItem(DEFAULT_DT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankCode").value(hasItem(DEFAULT_BANK_CODE)))
            .andExpect(jsonPath("$.[*].bankBranchCode").value(hasItem(DEFAULT_BANK_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].maxCeilAmount").value(hasItem(DEFAULT_MAX_CEIL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].installmentAmount").value(hasItem(DEFAULT_INSTALLMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].maxInstallmentAmount").value(hasItem(DEFAULT_MAX_INSTALLMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].mandateRefNo").value(hasItem(DEFAULT_MANDATE_REF_NO)))
            .andExpect(jsonPath("$.[*].depositBank").value(hasItem(DEFAULT_DEPOSIT_BANK)))
            .andExpect(jsonPath("$.[*].mandateType").value(hasItem(DEFAULT_MANDATE_TYPE)))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].utrNo").value(hasItem(DEFAULT_UTR_NO)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));
    }

    @Test
    @Transactional
    void getEpay() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get the epay
        restEpayMockMvc
            .perform(get(ENTITY_API_URL_ID, epay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(epay.getId().intValue()))
            .andExpect(jsonPath("$.instrumentType").value(DEFAULT_INSTRUMENT_TYPE))
            .andExpect(jsonPath("$.dtFromDate").value(DEFAULT_DT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.dtToDate").value(DEFAULT_DT_TO_DATE.toString()))
            .andExpect(jsonPath("$.bankCode").value(DEFAULT_BANK_CODE))
            .andExpect(jsonPath("$.bankBranchCode").value(DEFAULT_BANK_BRANCH_CODE))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO))
            .andExpect(jsonPath("$.maxCeilAmount").value(DEFAULT_MAX_CEIL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.installmentAmount").value(DEFAULT_INSTALLMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.maxInstallmentAmount").value(DEFAULT_MAX_INSTALLMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.mandateRefNo").value(DEFAULT_MANDATE_REF_NO))
            .andExpect(jsonPath("$.depositBank").value(DEFAULT_DEPOSIT_BANK))
            .andExpect(jsonPath("$.mandateType").value(DEFAULT_MANDATE_TYPE))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY.toString()))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE))
            .andExpect(jsonPath("$.utrNo").value(DEFAULT_UTR_NO))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4));
    }

    @Test
    @Transactional
    void getEpaysByIdFiltering() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        Long id = epay.getId();

        defaultEpayShouldBeFound("id.equals=" + id);
        defaultEpayShouldNotBeFound("id.notEquals=" + id);

        defaultEpayShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEpayShouldNotBeFound("id.greaterThan=" + id);

        defaultEpayShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEpayShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEpaysByInstrumentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where instrumentType equals to DEFAULT_INSTRUMENT_TYPE
        defaultEpayShouldBeFound("instrumentType.equals=" + DEFAULT_INSTRUMENT_TYPE);

        // Get all the epayList where instrumentType equals to UPDATED_INSTRUMENT_TYPE
        defaultEpayShouldNotBeFound("instrumentType.equals=" + UPDATED_INSTRUMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllEpaysByInstrumentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where instrumentType in DEFAULT_INSTRUMENT_TYPE or UPDATED_INSTRUMENT_TYPE
        defaultEpayShouldBeFound("instrumentType.in=" + DEFAULT_INSTRUMENT_TYPE + "," + UPDATED_INSTRUMENT_TYPE);

        // Get all the epayList where instrumentType equals to UPDATED_INSTRUMENT_TYPE
        defaultEpayShouldNotBeFound("instrumentType.in=" + UPDATED_INSTRUMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllEpaysByInstrumentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where instrumentType is not null
        defaultEpayShouldBeFound("instrumentType.specified=true");

        // Get all the epayList where instrumentType is null
        defaultEpayShouldNotBeFound("instrumentType.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByInstrumentTypeContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where instrumentType contains DEFAULT_INSTRUMENT_TYPE
        defaultEpayShouldBeFound("instrumentType.contains=" + DEFAULT_INSTRUMENT_TYPE);

        // Get all the epayList where instrumentType contains UPDATED_INSTRUMENT_TYPE
        defaultEpayShouldNotBeFound("instrumentType.contains=" + UPDATED_INSTRUMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllEpaysByInstrumentTypeNotContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where instrumentType does not contain DEFAULT_INSTRUMENT_TYPE
        defaultEpayShouldNotBeFound("instrumentType.doesNotContain=" + DEFAULT_INSTRUMENT_TYPE);

        // Get all the epayList where instrumentType does not contain UPDATED_INSTRUMENT_TYPE
        defaultEpayShouldBeFound("instrumentType.doesNotContain=" + UPDATED_INSTRUMENT_TYPE);
    }

    @Test
    @Transactional
    void getAllEpaysByDtFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where dtFromDate equals to DEFAULT_DT_FROM_DATE
        defaultEpayShouldBeFound("dtFromDate.equals=" + DEFAULT_DT_FROM_DATE);

        // Get all the epayList where dtFromDate equals to UPDATED_DT_FROM_DATE
        defaultEpayShouldNotBeFound("dtFromDate.equals=" + UPDATED_DT_FROM_DATE);
    }

    @Test
    @Transactional
    void getAllEpaysByDtFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where dtFromDate in DEFAULT_DT_FROM_DATE or UPDATED_DT_FROM_DATE
        defaultEpayShouldBeFound("dtFromDate.in=" + DEFAULT_DT_FROM_DATE + "," + UPDATED_DT_FROM_DATE);

        // Get all the epayList where dtFromDate equals to UPDATED_DT_FROM_DATE
        defaultEpayShouldNotBeFound("dtFromDate.in=" + UPDATED_DT_FROM_DATE);
    }

    @Test
    @Transactional
    void getAllEpaysByDtFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where dtFromDate is not null
        defaultEpayShouldBeFound("dtFromDate.specified=true");

        // Get all the epayList where dtFromDate is null
        defaultEpayShouldNotBeFound("dtFromDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByDtFromDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where dtFromDate is greater than or equal to DEFAULT_DT_FROM_DATE
        defaultEpayShouldBeFound("dtFromDate.greaterThanOrEqual=" + DEFAULT_DT_FROM_DATE);

        // Get all the epayList where dtFromDate is greater than or equal to UPDATED_DT_FROM_DATE
        defaultEpayShouldNotBeFound("dtFromDate.greaterThanOrEqual=" + UPDATED_DT_FROM_DATE);
    }

    @Test
    @Transactional
    void getAllEpaysByDtFromDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where dtFromDate is less than or equal to DEFAULT_DT_FROM_DATE
        defaultEpayShouldBeFound("dtFromDate.lessThanOrEqual=" + DEFAULT_DT_FROM_DATE);

        // Get all the epayList where dtFromDate is less than or equal to SMALLER_DT_FROM_DATE
        defaultEpayShouldNotBeFound("dtFromDate.lessThanOrEqual=" + SMALLER_DT_FROM_DATE);
    }

    @Test
    @Transactional
    void getAllEpaysByDtFromDateIsLessThanSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where dtFromDate is less than DEFAULT_DT_FROM_DATE
        defaultEpayShouldNotBeFound("dtFromDate.lessThan=" + DEFAULT_DT_FROM_DATE);

        // Get all the epayList where dtFromDate is less than UPDATED_DT_FROM_DATE
        defaultEpayShouldBeFound("dtFromDate.lessThan=" + UPDATED_DT_FROM_DATE);
    }

    @Test
    @Transactional
    void getAllEpaysByDtFromDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where dtFromDate is greater than DEFAULT_DT_FROM_DATE
        defaultEpayShouldNotBeFound("dtFromDate.greaterThan=" + DEFAULT_DT_FROM_DATE);

        // Get all the epayList where dtFromDate is greater than SMALLER_DT_FROM_DATE
        defaultEpayShouldBeFound("dtFromDate.greaterThan=" + SMALLER_DT_FROM_DATE);
    }

    @Test
    @Transactional
    void getAllEpaysByDtToDateIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where dtToDate equals to DEFAULT_DT_TO_DATE
        defaultEpayShouldBeFound("dtToDate.equals=" + DEFAULT_DT_TO_DATE);

        // Get all the epayList where dtToDate equals to UPDATED_DT_TO_DATE
        defaultEpayShouldNotBeFound("dtToDate.equals=" + UPDATED_DT_TO_DATE);
    }

    @Test
    @Transactional
    void getAllEpaysByDtToDateIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where dtToDate in DEFAULT_DT_TO_DATE or UPDATED_DT_TO_DATE
        defaultEpayShouldBeFound("dtToDate.in=" + DEFAULT_DT_TO_DATE + "," + UPDATED_DT_TO_DATE);

        // Get all the epayList where dtToDate equals to UPDATED_DT_TO_DATE
        defaultEpayShouldNotBeFound("dtToDate.in=" + UPDATED_DT_TO_DATE);
    }

    @Test
    @Transactional
    void getAllEpaysByDtToDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where dtToDate is not null
        defaultEpayShouldBeFound("dtToDate.specified=true");

        // Get all the epayList where dtToDate is null
        defaultEpayShouldNotBeFound("dtToDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByDtToDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where dtToDate is greater than or equal to DEFAULT_DT_TO_DATE
        defaultEpayShouldBeFound("dtToDate.greaterThanOrEqual=" + DEFAULT_DT_TO_DATE);

        // Get all the epayList where dtToDate is greater than or equal to UPDATED_DT_TO_DATE
        defaultEpayShouldNotBeFound("dtToDate.greaterThanOrEqual=" + UPDATED_DT_TO_DATE);
    }

    @Test
    @Transactional
    void getAllEpaysByDtToDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where dtToDate is less than or equal to DEFAULT_DT_TO_DATE
        defaultEpayShouldBeFound("dtToDate.lessThanOrEqual=" + DEFAULT_DT_TO_DATE);

        // Get all the epayList where dtToDate is less than or equal to SMALLER_DT_TO_DATE
        defaultEpayShouldNotBeFound("dtToDate.lessThanOrEqual=" + SMALLER_DT_TO_DATE);
    }

    @Test
    @Transactional
    void getAllEpaysByDtToDateIsLessThanSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where dtToDate is less than DEFAULT_DT_TO_DATE
        defaultEpayShouldNotBeFound("dtToDate.lessThan=" + DEFAULT_DT_TO_DATE);

        // Get all the epayList where dtToDate is less than UPDATED_DT_TO_DATE
        defaultEpayShouldBeFound("dtToDate.lessThan=" + UPDATED_DT_TO_DATE);
    }

    @Test
    @Transactional
    void getAllEpaysByDtToDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where dtToDate is greater than DEFAULT_DT_TO_DATE
        defaultEpayShouldNotBeFound("dtToDate.greaterThan=" + DEFAULT_DT_TO_DATE);

        // Get all the epayList where dtToDate is greater than SMALLER_DT_TO_DATE
        defaultEpayShouldBeFound("dtToDate.greaterThan=" + SMALLER_DT_TO_DATE);
    }

    @Test
    @Transactional
    void getAllEpaysByBankCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where bankCode equals to DEFAULT_BANK_CODE
        defaultEpayShouldBeFound("bankCode.equals=" + DEFAULT_BANK_CODE);

        // Get all the epayList where bankCode equals to UPDATED_BANK_CODE
        defaultEpayShouldNotBeFound("bankCode.equals=" + UPDATED_BANK_CODE);
    }

    @Test
    @Transactional
    void getAllEpaysByBankCodeIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where bankCode in DEFAULT_BANK_CODE or UPDATED_BANK_CODE
        defaultEpayShouldBeFound("bankCode.in=" + DEFAULT_BANK_CODE + "," + UPDATED_BANK_CODE);

        // Get all the epayList where bankCode equals to UPDATED_BANK_CODE
        defaultEpayShouldNotBeFound("bankCode.in=" + UPDATED_BANK_CODE);
    }

    @Test
    @Transactional
    void getAllEpaysByBankCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where bankCode is not null
        defaultEpayShouldBeFound("bankCode.specified=true");

        // Get all the epayList where bankCode is null
        defaultEpayShouldNotBeFound("bankCode.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByBankCodeContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where bankCode contains DEFAULT_BANK_CODE
        defaultEpayShouldBeFound("bankCode.contains=" + DEFAULT_BANK_CODE);

        // Get all the epayList where bankCode contains UPDATED_BANK_CODE
        defaultEpayShouldNotBeFound("bankCode.contains=" + UPDATED_BANK_CODE);
    }

    @Test
    @Transactional
    void getAllEpaysByBankCodeNotContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where bankCode does not contain DEFAULT_BANK_CODE
        defaultEpayShouldNotBeFound("bankCode.doesNotContain=" + DEFAULT_BANK_CODE);

        // Get all the epayList where bankCode does not contain UPDATED_BANK_CODE
        defaultEpayShouldBeFound("bankCode.doesNotContain=" + UPDATED_BANK_CODE);
    }

    @Test
    @Transactional
    void getAllEpaysByBankBranchCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where bankBranchCode equals to DEFAULT_BANK_BRANCH_CODE
        defaultEpayShouldBeFound("bankBranchCode.equals=" + DEFAULT_BANK_BRANCH_CODE);

        // Get all the epayList where bankBranchCode equals to UPDATED_BANK_BRANCH_CODE
        defaultEpayShouldNotBeFound("bankBranchCode.equals=" + UPDATED_BANK_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllEpaysByBankBranchCodeIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where bankBranchCode in DEFAULT_BANK_BRANCH_CODE or UPDATED_BANK_BRANCH_CODE
        defaultEpayShouldBeFound("bankBranchCode.in=" + DEFAULT_BANK_BRANCH_CODE + "," + UPDATED_BANK_BRANCH_CODE);

        // Get all the epayList where bankBranchCode equals to UPDATED_BANK_BRANCH_CODE
        defaultEpayShouldNotBeFound("bankBranchCode.in=" + UPDATED_BANK_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllEpaysByBankBranchCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where bankBranchCode is not null
        defaultEpayShouldBeFound("bankBranchCode.specified=true");

        // Get all the epayList where bankBranchCode is null
        defaultEpayShouldNotBeFound("bankBranchCode.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByBankBranchCodeContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where bankBranchCode contains DEFAULT_BANK_BRANCH_CODE
        defaultEpayShouldBeFound("bankBranchCode.contains=" + DEFAULT_BANK_BRANCH_CODE);

        // Get all the epayList where bankBranchCode contains UPDATED_BANK_BRANCH_CODE
        defaultEpayShouldNotBeFound("bankBranchCode.contains=" + UPDATED_BANK_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllEpaysByBankBranchCodeNotContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where bankBranchCode does not contain DEFAULT_BANK_BRANCH_CODE
        defaultEpayShouldNotBeFound("bankBranchCode.doesNotContain=" + DEFAULT_BANK_BRANCH_CODE);

        // Get all the epayList where bankBranchCode does not contain UPDATED_BANK_BRANCH_CODE
        defaultEpayShouldBeFound("bankBranchCode.doesNotContain=" + UPDATED_BANK_BRANCH_CODE);
    }

    @Test
    @Transactional
    void getAllEpaysByAccountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where accountType equals to DEFAULT_ACCOUNT_TYPE
        defaultEpayShouldBeFound("accountType.equals=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the epayList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultEpayShouldNotBeFound("accountType.equals=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllEpaysByAccountTypeIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where accountType in DEFAULT_ACCOUNT_TYPE or UPDATED_ACCOUNT_TYPE
        defaultEpayShouldBeFound("accountType.in=" + DEFAULT_ACCOUNT_TYPE + "," + UPDATED_ACCOUNT_TYPE);

        // Get all the epayList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultEpayShouldNotBeFound("accountType.in=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllEpaysByAccountTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where accountType is not null
        defaultEpayShouldBeFound("accountType.specified=true");

        // Get all the epayList where accountType is null
        defaultEpayShouldNotBeFound("accountType.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByAccountTypeContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where accountType contains DEFAULT_ACCOUNT_TYPE
        defaultEpayShouldBeFound("accountType.contains=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the epayList where accountType contains UPDATED_ACCOUNT_TYPE
        defaultEpayShouldNotBeFound("accountType.contains=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllEpaysByAccountTypeNotContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where accountType does not contain DEFAULT_ACCOUNT_TYPE
        defaultEpayShouldNotBeFound("accountType.doesNotContain=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the epayList where accountType does not contain UPDATED_ACCOUNT_TYPE
        defaultEpayShouldBeFound("accountType.doesNotContain=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllEpaysByAccountNoIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where accountNo equals to DEFAULT_ACCOUNT_NO
        defaultEpayShouldBeFound("accountNo.equals=" + DEFAULT_ACCOUNT_NO);

        // Get all the epayList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultEpayShouldNotBeFound("accountNo.equals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllEpaysByAccountNoIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where accountNo in DEFAULT_ACCOUNT_NO or UPDATED_ACCOUNT_NO
        defaultEpayShouldBeFound("accountNo.in=" + DEFAULT_ACCOUNT_NO + "," + UPDATED_ACCOUNT_NO);

        // Get all the epayList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultEpayShouldNotBeFound("accountNo.in=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllEpaysByAccountNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where accountNo is not null
        defaultEpayShouldBeFound("accountNo.specified=true");

        // Get all the epayList where accountNo is null
        defaultEpayShouldNotBeFound("accountNo.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByAccountNoContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where accountNo contains DEFAULT_ACCOUNT_NO
        defaultEpayShouldBeFound("accountNo.contains=" + DEFAULT_ACCOUNT_NO);

        // Get all the epayList where accountNo contains UPDATED_ACCOUNT_NO
        defaultEpayShouldNotBeFound("accountNo.contains=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllEpaysByAccountNoNotContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where accountNo does not contain DEFAULT_ACCOUNT_NO
        defaultEpayShouldNotBeFound("accountNo.doesNotContain=" + DEFAULT_ACCOUNT_NO);

        // Get all the epayList where accountNo does not contain UPDATED_ACCOUNT_NO
        defaultEpayShouldBeFound("accountNo.doesNotContain=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllEpaysByMaxCeilAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where maxCeilAmount equals to DEFAULT_MAX_CEIL_AMOUNT
        defaultEpayShouldBeFound("maxCeilAmount.equals=" + DEFAULT_MAX_CEIL_AMOUNT);

        // Get all the epayList where maxCeilAmount equals to UPDATED_MAX_CEIL_AMOUNT
        defaultEpayShouldNotBeFound("maxCeilAmount.equals=" + UPDATED_MAX_CEIL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByMaxCeilAmountIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where maxCeilAmount in DEFAULT_MAX_CEIL_AMOUNT or UPDATED_MAX_CEIL_AMOUNT
        defaultEpayShouldBeFound("maxCeilAmount.in=" + DEFAULT_MAX_CEIL_AMOUNT + "," + UPDATED_MAX_CEIL_AMOUNT);

        // Get all the epayList where maxCeilAmount equals to UPDATED_MAX_CEIL_AMOUNT
        defaultEpayShouldNotBeFound("maxCeilAmount.in=" + UPDATED_MAX_CEIL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByMaxCeilAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where maxCeilAmount is not null
        defaultEpayShouldBeFound("maxCeilAmount.specified=true");

        // Get all the epayList where maxCeilAmount is null
        defaultEpayShouldNotBeFound("maxCeilAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByMaxCeilAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where maxCeilAmount is greater than or equal to DEFAULT_MAX_CEIL_AMOUNT
        defaultEpayShouldBeFound("maxCeilAmount.greaterThanOrEqual=" + DEFAULT_MAX_CEIL_AMOUNT);

        // Get all the epayList where maxCeilAmount is greater than or equal to UPDATED_MAX_CEIL_AMOUNT
        defaultEpayShouldNotBeFound("maxCeilAmount.greaterThanOrEqual=" + UPDATED_MAX_CEIL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByMaxCeilAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where maxCeilAmount is less than or equal to DEFAULT_MAX_CEIL_AMOUNT
        defaultEpayShouldBeFound("maxCeilAmount.lessThanOrEqual=" + DEFAULT_MAX_CEIL_AMOUNT);

        // Get all the epayList where maxCeilAmount is less than or equal to SMALLER_MAX_CEIL_AMOUNT
        defaultEpayShouldNotBeFound("maxCeilAmount.lessThanOrEqual=" + SMALLER_MAX_CEIL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByMaxCeilAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where maxCeilAmount is less than DEFAULT_MAX_CEIL_AMOUNT
        defaultEpayShouldNotBeFound("maxCeilAmount.lessThan=" + DEFAULT_MAX_CEIL_AMOUNT);

        // Get all the epayList where maxCeilAmount is less than UPDATED_MAX_CEIL_AMOUNT
        defaultEpayShouldBeFound("maxCeilAmount.lessThan=" + UPDATED_MAX_CEIL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByMaxCeilAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where maxCeilAmount is greater than DEFAULT_MAX_CEIL_AMOUNT
        defaultEpayShouldNotBeFound("maxCeilAmount.greaterThan=" + DEFAULT_MAX_CEIL_AMOUNT);

        // Get all the epayList where maxCeilAmount is greater than SMALLER_MAX_CEIL_AMOUNT
        defaultEpayShouldBeFound("maxCeilAmount.greaterThan=" + SMALLER_MAX_CEIL_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByInstallmentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where installmentAmount equals to DEFAULT_INSTALLMENT_AMOUNT
        defaultEpayShouldBeFound("installmentAmount.equals=" + DEFAULT_INSTALLMENT_AMOUNT);

        // Get all the epayList where installmentAmount equals to UPDATED_INSTALLMENT_AMOUNT
        defaultEpayShouldNotBeFound("installmentAmount.equals=" + UPDATED_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByInstallmentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where installmentAmount in DEFAULT_INSTALLMENT_AMOUNT or UPDATED_INSTALLMENT_AMOUNT
        defaultEpayShouldBeFound("installmentAmount.in=" + DEFAULT_INSTALLMENT_AMOUNT + "," + UPDATED_INSTALLMENT_AMOUNT);

        // Get all the epayList where installmentAmount equals to UPDATED_INSTALLMENT_AMOUNT
        defaultEpayShouldNotBeFound("installmentAmount.in=" + UPDATED_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByInstallmentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where installmentAmount is not null
        defaultEpayShouldBeFound("installmentAmount.specified=true");

        // Get all the epayList where installmentAmount is null
        defaultEpayShouldNotBeFound("installmentAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByInstallmentAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where installmentAmount is greater than or equal to DEFAULT_INSTALLMENT_AMOUNT
        defaultEpayShouldBeFound("installmentAmount.greaterThanOrEqual=" + DEFAULT_INSTALLMENT_AMOUNT);

        // Get all the epayList where installmentAmount is greater than or equal to UPDATED_INSTALLMENT_AMOUNT
        defaultEpayShouldNotBeFound("installmentAmount.greaterThanOrEqual=" + UPDATED_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByInstallmentAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where installmentAmount is less than or equal to DEFAULT_INSTALLMENT_AMOUNT
        defaultEpayShouldBeFound("installmentAmount.lessThanOrEqual=" + DEFAULT_INSTALLMENT_AMOUNT);

        // Get all the epayList where installmentAmount is less than or equal to SMALLER_INSTALLMENT_AMOUNT
        defaultEpayShouldNotBeFound("installmentAmount.lessThanOrEqual=" + SMALLER_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByInstallmentAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where installmentAmount is less than DEFAULT_INSTALLMENT_AMOUNT
        defaultEpayShouldNotBeFound("installmentAmount.lessThan=" + DEFAULT_INSTALLMENT_AMOUNT);

        // Get all the epayList where installmentAmount is less than UPDATED_INSTALLMENT_AMOUNT
        defaultEpayShouldBeFound("installmentAmount.lessThan=" + UPDATED_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByInstallmentAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where installmentAmount is greater than DEFAULT_INSTALLMENT_AMOUNT
        defaultEpayShouldNotBeFound("installmentAmount.greaterThan=" + DEFAULT_INSTALLMENT_AMOUNT);

        // Get all the epayList where installmentAmount is greater than SMALLER_INSTALLMENT_AMOUNT
        defaultEpayShouldBeFound("installmentAmount.greaterThan=" + SMALLER_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByMaxInstallmentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where maxInstallmentAmount equals to DEFAULT_MAX_INSTALLMENT_AMOUNT
        defaultEpayShouldBeFound("maxInstallmentAmount.equals=" + DEFAULT_MAX_INSTALLMENT_AMOUNT);

        // Get all the epayList where maxInstallmentAmount equals to UPDATED_MAX_INSTALLMENT_AMOUNT
        defaultEpayShouldNotBeFound("maxInstallmentAmount.equals=" + UPDATED_MAX_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByMaxInstallmentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where maxInstallmentAmount in DEFAULT_MAX_INSTALLMENT_AMOUNT or UPDATED_MAX_INSTALLMENT_AMOUNT
        defaultEpayShouldBeFound("maxInstallmentAmount.in=" + DEFAULT_MAX_INSTALLMENT_AMOUNT + "," + UPDATED_MAX_INSTALLMENT_AMOUNT);

        // Get all the epayList where maxInstallmentAmount equals to UPDATED_MAX_INSTALLMENT_AMOUNT
        defaultEpayShouldNotBeFound("maxInstallmentAmount.in=" + UPDATED_MAX_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByMaxInstallmentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where maxInstallmentAmount is not null
        defaultEpayShouldBeFound("maxInstallmentAmount.specified=true");

        // Get all the epayList where maxInstallmentAmount is null
        defaultEpayShouldNotBeFound("maxInstallmentAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByMaxInstallmentAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where maxInstallmentAmount is greater than or equal to DEFAULT_MAX_INSTALLMENT_AMOUNT
        defaultEpayShouldBeFound("maxInstallmentAmount.greaterThanOrEqual=" + DEFAULT_MAX_INSTALLMENT_AMOUNT);

        // Get all the epayList where maxInstallmentAmount is greater than or equal to UPDATED_MAX_INSTALLMENT_AMOUNT
        defaultEpayShouldNotBeFound("maxInstallmentAmount.greaterThanOrEqual=" + UPDATED_MAX_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByMaxInstallmentAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where maxInstallmentAmount is less than or equal to DEFAULT_MAX_INSTALLMENT_AMOUNT
        defaultEpayShouldBeFound("maxInstallmentAmount.lessThanOrEqual=" + DEFAULT_MAX_INSTALLMENT_AMOUNT);

        // Get all the epayList where maxInstallmentAmount is less than or equal to SMALLER_MAX_INSTALLMENT_AMOUNT
        defaultEpayShouldNotBeFound("maxInstallmentAmount.lessThanOrEqual=" + SMALLER_MAX_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByMaxInstallmentAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where maxInstallmentAmount is less than DEFAULT_MAX_INSTALLMENT_AMOUNT
        defaultEpayShouldNotBeFound("maxInstallmentAmount.lessThan=" + DEFAULT_MAX_INSTALLMENT_AMOUNT);

        // Get all the epayList where maxInstallmentAmount is less than UPDATED_MAX_INSTALLMENT_AMOUNT
        defaultEpayShouldBeFound("maxInstallmentAmount.lessThan=" + UPDATED_MAX_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByMaxInstallmentAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where maxInstallmentAmount is greater than DEFAULT_MAX_INSTALLMENT_AMOUNT
        defaultEpayShouldNotBeFound("maxInstallmentAmount.greaterThan=" + DEFAULT_MAX_INSTALLMENT_AMOUNT);

        // Get all the epayList where maxInstallmentAmount is greater than SMALLER_MAX_INSTALLMENT_AMOUNT
        defaultEpayShouldBeFound("maxInstallmentAmount.greaterThan=" + SMALLER_MAX_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEpaysByMandateRefNoIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where mandateRefNo equals to DEFAULT_MANDATE_REF_NO
        defaultEpayShouldBeFound("mandateRefNo.equals=" + DEFAULT_MANDATE_REF_NO);

        // Get all the epayList where mandateRefNo equals to UPDATED_MANDATE_REF_NO
        defaultEpayShouldNotBeFound("mandateRefNo.equals=" + UPDATED_MANDATE_REF_NO);
    }

    @Test
    @Transactional
    void getAllEpaysByMandateRefNoIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where mandateRefNo in DEFAULT_MANDATE_REF_NO or UPDATED_MANDATE_REF_NO
        defaultEpayShouldBeFound("mandateRefNo.in=" + DEFAULT_MANDATE_REF_NO + "," + UPDATED_MANDATE_REF_NO);

        // Get all the epayList where mandateRefNo equals to UPDATED_MANDATE_REF_NO
        defaultEpayShouldNotBeFound("mandateRefNo.in=" + UPDATED_MANDATE_REF_NO);
    }

    @Test
    @Transactional
    void getAllEpaysByMandateRefNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where mandateRefNo is not null
        defaultEpayShouldBeFound("mandateRefNo.specified=true");

        // Get all the epayList where mandateRefNo is null
        defaultEpayShouldNotBeFound("mandateRefNo.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByMandateRefNoContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where mandateRefNo contains DEFAULT_MANDATE_REF_NO
        defaultEpayShouldBeFound("mandateRefNo.contains=" + DEFAULT_MANDATE_REF_NO);

        // Get all the epayList where mandateRefNo contains UPDATED_MANDATE_REF_NO
        defaultEpayShouldNotBeFound("mandateRefNo.contains=" + UPDATED_MANDATE_REF_NO);
    }

    @Test
    @Transactional
    void getAllEpaysByMandateRefNoNotContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where mandateRefNo does not contain DEFAULT_MANDATE_REF_NO
        defaultEpayShouldNotBeFound("mandateRefNo.doesNotContain=" + DEFAULT_MANDATE_REF_NO);

        // Get all the epayList where mandateRefNo does not contain UPDATED_MANDATE_REF_NO
        defaultEpayShouldBeFound("mandateRefNo.doesNotContain=" + UPDATED_MANDATE_REF_NO);
    }

    @Test
    @Transactional
    void getAllEpaysByDepositBankIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where depositBank equals to DEFAULT_DEPOSIT_BANK
        defaultEpayShouldBeFound("depositBank.equals=" + DEFAULT_DEPOSIT_BANK);

        // Get all the epayList where depositBank equals to UPDATED_DEPOSIT_BANK
        defaultEpayShouldNotBeFound("depositBank.equals=" + UPDATED_DEPOSIT_BANK);
    }

    @Test
    @Transactional
    void getAllEpaysByDepositBankIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where depositBank in DEFAULT_DEPOSIT_BANK or UPDATED_DEPOSIT_BANK
        defaultEpayShouldBeFound("depositBank.in=" + DEFAULT_DEPOSIT_BANK + "," + UPDATED_DEPOSIT_BANK);

        // Get all the epayList where depositBank equals to UPDATED_DEPOSIT_BANK
        defaultEpayShouldNotBeFound("depositBank.in=" + UPDATED_DEPOSIT_BANK);
    }

    @Test
    @Transactional
    void getAllEpaysByDepositBankIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where depositBank is not null
        defaultEpayShouldBeFound("depositBank.specified=true");

        // Get all the epayList where depositBank is null
        defaultEpayShouldNotBeFound("depositBank.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByDepositBankContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where depositBank contains DEFAULT_DEPOSIT_BANK
        defaultEpayShouldBeFound("depositBank.contains=" + DEFAULT_DEPOSIT_BANK);

        // Get all the epayList where depositBank contains UPDATED_DEPOSIT_BANK
        defaultEpayShouldNotBeFound("depositBank.contains=" + UPDATED_DEPOSIT_BANK);
    }

    @Test
    @Transactional
    void getAllEpaysByDepositBankNotContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where depositBank does not contain DEFAULT_DEPOSIT_BANK
        defaultEpayShouldNotBeFound("depositBank.doesNotContain=" + DEFAULT_DEPOSIT_BANK);

        // Get all the epayList where depositBank does not contain UPDATED_DEPOSIT_BANK
        defaultEpayShouldBeFound("depositBank.doesNotContain=" + UPDATED_DEPOSIT_BANK);
    }

    @Test
    @Transactional
    void getAllEpaysByMandateTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where mandateType equals to DEFAULT_MANDATE_TYPE
        defaultEpayShouldBeFound("mandateType.equals=" + DEFAULT_MANDATE_TYPE);

        // Get all the epayList where mandateType equals to UPDATED_MANDATE_TYPE
        defaultEpayShouldNotBeFound("mandateType.equals=" + UPDATED_MANDATE_TYPE);
    }

    @Test
    @Transactional
    void getAllEpaysByMandateTypeIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where mandateType in DEFAULT_MANDATE_TYPE or UPDATED_MANDATE_TYPE
        defaultEpayShouldBeFound("mandateType.in=" + DEFAULT_MANDATE_TYPE + "," + UPDATED_MANDATE_TYPE);

        // Get all the epayList where mandateType equals to UPDATED_MANDATE_TYPE
        defaultEpayShouldNotBeFound("mandateType.in=" + UPDATED_MANDATE_TYPE);
    }

    @Test
    @Transactional
    void getAllEpaysByMandateTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where mandateType is not null
        defaultEpayShouldBeFound("mandateType.specified=true");

        // Get all the epayList where mandateType is null
        defaultEpayShouldNotBeFound("mandateType.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByMandateTypeContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where mandateType contains DEFAULT_MANDATE_TYPE
        defaultEpayShouldBeFound("mandateType.contains=" + DEFAULT_MANDATE_TYPE);

        // Get all the epayList where mandateType contains UPDATED_MANDATE_TYPE
        defaultEpayShouldNotBeFound("mandateType.contains=" + UPDATED_MANDATE_TYPE);
    }

    @Test
    @Transactional
    void getAllEpaysByMandateTypeNotContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where mandateType does not contain DEFAULT_MANDATE_TYPE
        defaultEpayShouldNotBeFound("mandateType.doesNotContain=" + DEFAULT_MANDATE_TYPE);

        // Get all the epayList where mandateType does not contain UPDATED_MANDATE_TYPE
        defaultEpayShouldBeFound("mandateType.doesNotContain=" + UPDATED_MANDATE_TYPE);
    }

    @Test
    @Transactional
    void getAllEpaysByFrequencyIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where frequency equals to DEFAULT_FREQUENCY
        defaultEpayShouldBeFound("frequency.equals=" + DEFAULT_FREQUENCY);

        // Get all the epayList where frequency equals to UPDATED_FREQUENCY
        defaultEpayShouldNotBeFound("frequency.equals=" + UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    void getAllEpaysByFrequencyIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where frequency in DEFAULT_FREQUENCY or UPDATED_FREQUENCY
        defaultEpayShouldBeFound("frequency.in=" + DEFAULT_FREQUENCY + "," + UPDATED_FREQUENCY);

        // Get all the epayList where frequency equals to UPDATED_FREQUENCY
        defaultEpayShouldNotBeFound("frequency.in=" + UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    void getAllEpaysByFrequencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where frequency is not null
        defaultEpayShouldBeFound("frequency.specified=true");

        // Get all the epayList where frequency is null
        defaultEpayShouldNotBeFound("frequency.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByIfscCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where ifscCode equals to DEFAULT_IFSC_CODE
        defaultEpayShouldBeFound("ifscCode.equals=" + DEFAULT_IFSC_CODE);

        // Get all the epayList where ifscCode equals to UPDATED_IFSC_CODE
        defaultEpayShouldNotBeFound("ifscCode.equals=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllEpaysByIfscCodeIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where ifscCode in DEFAULT_IFSC_CODE or UPDATED_IFSC_CODE
        defaultEpayShouldBeFound("ifscCode.in=" + DEFAULT_IFSC_CODE + "," + UPDATED_IFSC_CODE);

        // Get all the epayList where ifscCode equals to UPDATED_IFSC_CODE
        defaultEpayShouldNotBeFound("ifscCode.in=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllEpaysByIfscCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where ifscCode is not null
        defaultEpayShouldBeFound("ifscCode.specified=true");

        // Get all the epayList where ifscCode is null
        defaultEpayShouldNotBeFound("ifscCode.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByIfscCodeContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where ifscCode contains DEFAULT_IFSC_CODE
        defaultEpayShouldBeFound("ifscCode.contains=" + DEFAULT_IFSC_CODE);

        // Get all the epayList where ifscCode contains UPDATED_IFSC_CODE
        defaultEpayShouldNotBeFound("ifscCode.contains=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllEpaysByIfscCodeNotContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where ifscCode does not contain DEFAULT_IFSC_CODE
        defaultEpayShouldNotBeFound("ifscCode.doesNotContain=" + DEFAULT_IFSC_CODE);

        // Get all the epayList where ifscCode does not contain UPDATED_IFSC_CODE
        defaultEpayShouldBeFound("ifscCode.doesNotContain=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllEpaysByUtrNoIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where utrNo equals to DEFAULT_UTR_NO
        defaultEpayShouldBeFound("utrNo.equals=" + DEFAULT_UTR_NO);

        // Get all the epayList where utrNo equals to UPDATED_UTR_NO
        defaultEpayShouldNotBeFound("utrNo.equals=" + UPDATED_UTR_NO);
    }

    @Test
    @Transactional
    void getAllEpaysByUtrNoIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where utrNo in DEFAULT_UTR_NO or UPDATED_UTR_NO
        defaultEpayShouldBeFound("utrNo.in=" + DEFAULT_UTR_NO + "," + UPDATED_UTR_NO);

        // Get all the epayList where utrNo equals to UPDATED_UTR_NO
        defaultEpayShouldNotBeFound("utrNo.in=" + UPDATED_UTR_NO);
    }

    @Test
    @Transactional
    void getAllEpaysByUtrNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where utrNo is not null
        defaultEpayShouldBeFound("utrNo.specified=true");

        // Get all the epayList where utrNo is null
        defaultEpayShouldNotBeFound("utrNo.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByUtrNoContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where utrNo contains DEFAULT_UTR_NO
        defaultEpayShouldBeFound("utrNo.contains=" + DEFAULT_UTR_NO);

        // Get all the epayList where utrNo contains UPDATED_UTR_NO
        defaultEpayShouldNotBeFound("utrNo.contains=" + UPDATED_UTR_NO);
    }

    @Test
    @Transactional
    void getAllEpaysByUtrNoNotContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where utrNo does not contain DEFAULT_UTR_NO
        defaultEpayShouldNotBeFound("utrNo.doesNotContain=" + DEFAULT_UTR_NO);

        // Get all the epayList where utrNo does not contain UPDATED_UTR_NO
        defaultEpayShouldBeFound("utrNo.doesNotContain=" + UPDATED_UTR_NO);
    }

    @Test
    @Transactional
    void getAllEpaysByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where isDeleted equals to DEFAULT_IS_DELETED
        defaultEpayShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the epayList where isDeleted equals to UPDATED_IS_DELETED
        defaultEpayShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllEpaysByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultEpayShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the epayList where isDeleted equals to UPDATED_IS_DELETED
        defaultEpayShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllEpaysByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where isDeleted is not null
        defaultEpayShouldBeFound("isDeleted.specified=true");

        // Get all the epayList where isDeleted is null
        defaultEpayShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where isActive equals to DEFAULT_IS_ACTIVE
        defaultEpayShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the epayList where isActive equals to UPDATED_IS_ACTIVE
        defaultEpayShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEpaysByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultEpayShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the epayList where isActive equals to UPDATED_IS_ACTIVE
        defaultEpayShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEpaysByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where isActive is not null
        defaultEpayShouldBeFound("isActive.specified=true");

        // Get all the epayList where isActive is null
        defaultEpayShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultEpayShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the epayList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEpayShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEpaysByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultEpayShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the epayList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEpayShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEpaysByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where lastModified is not null
        defaultEpayShouldBeFound("lastModified.specified=true");

        // Get all the epayList where lastModified is null
        defaultEpayShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultEpayShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the epayList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEpayShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEpaysByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultEpayShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the epayList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEpayShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEpaysByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where lastModifiedBy is not null
        defaultEpayShouldBeFound("lastModifiedBy.specified=true");

        // Get all the epayList where lastModifiedBy is null
        defaultEpayShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultEpayShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the epayList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultEpayShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEpaysByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultEpayShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the epayList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultEpayShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultEpayShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the epayList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultEpayShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultEpayShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the epayList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultEpayShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField1 is not null
        defaultEpayShouldBeFound("freeField1.specified=true");

        // Get all the epayList where freeField1 is null
        defaultEpayShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultEpayShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the epayList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultEpayShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultEpayShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the epayList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultEpayShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultEpayShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the epayList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultEpayShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultEpayShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the epayList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultEpayShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField2 is not null
        defaultEpayShouldBeFound("freeField2.specified=true");

        // Get all the epayList where freeField2 is null
        defaultEpayShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultEpayShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the epayList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultEpayShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultEpayShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the epayList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultEpayShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultEpayShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the epayList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultEpayShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultEpayShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the epayList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultEpayShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField3 is not null
        defaultEpayShouldBeFound("freeField3.specified=true");

        // Get all the epayList where freeField3 is null
        defaultEpayShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultEpayShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the epayList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultEpayShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultEpayShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the epayList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultEpayShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultEpayShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the epayList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultEpayShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultEpayShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the epayList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultEpayShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField4 is not null
        defaultEpayShouldBeFound("freeField4.specified=true");

        // Get all the epayList where freeField4 is null
        defaultEpayShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultEpayShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the epayList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultEpayShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllEpaysByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        // Get all the epayList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultEpayShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the epayList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultEpayShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEpayShouldBeFound(String filter) throws Exception {
        restEpayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(epay.getId().intValue())))
            .andExpect(jsonPath("$.[*].instrumentType").value(hasItem(DEFAULT_INSTRUMENT_TYPE)))
            .andExpect(jsonPath("$.[*].dtFromDate").value(hasItem(DEFAULT_DT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].dtToDate").value(hasItem(DEFAULT_DT_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].bankCode").value(hasItem(DEFAULT_BANK_CODE)))
            .andExpect(jsonPath("$.[*].bankBranchCode").value(hasItem(DEFAULT_BANK_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO)))
            .andExpect(jsonPath("$.[*].maxCeilAmount").value(hasItem(DEFAULT_MAX_CEIL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].installmentAmount").value(hasItem(DEFAULT_INSTALLMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].maxInstallmentAmount").value(hasItem(DEFAULT_MAX_INSTALLMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].mandateRefNo").value(hasItem(DEFAULT_MANDATE_REF_NO)))
            .andExpect(jsonPath("$.[*].depositBank").value(hasItem(DEFAULT_DEPOSIT_BANK)))
            .andExpect(jsonPath("$.[*].mandateType").value(hasItem(DEFAULT_MANDATE_TYPE)))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].utrNo").value(hasItem(DEFAULT_UTR_NO)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));

        // Check, that the count call also returns 1
        restEpayMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEpayShouldNotBeFound(String filter) throws Exception {
        restEpayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEpayMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEpay() throws Exception {
        // Get the epay
        restEpayMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEpay() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        int databaseSizeBeforeUpdate = epayRepository.findAll().size();

        // Update the epay
        Epay updatedEpay = epayRepository.findById(epay.getId()).get();
        // Disconnect from session so that the updates on updatedEpay are not directly saved in db
        em.detach(updatedEpay);
        updatedEpay
            .instrumentType(UPDATED_INSTRUMENT_TYPE)
            .dtFromDate(UPDATED_DT_FROM_DATE)
            .dtToDate(UPDATED_DT_TO_DATE)
            .bankCode(UPDATED_BANK_CODE)
            .bankBranchCode(UPDATED_BANK_BRANCH_CODE)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountNo(UPDATED_ACCOUNT_NO)
            .maxCeilAmount(UPDATED_MAX_CEIL_AMOUNT)
            .installmentAmount(UPDATED_INSTALLMENT_AMOUNT)
            .maxInstallmentAmount(UPDATED_MAX_INSTALLMENT_AMOUNT)
            .mandateRefNo(UPDATED_MANDATE_REF_NO)
            .depositBank(UPDATED_DEPOSIT_BANK)
            .mandateType(UPDATED_MANDATE_TYPE)
            .frequency(UPDATED_FREQUENCY)
            .ifscCode(UPDATED_IFSC_CODE)
            .utrNo(UPDATED_UTR_NO)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        EpayDTO epayDTO = epayMapper.toDto(updatedEpay);

        restEpayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, epayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(epayDTO))
            )
            .andExpect(status().isOk());

        // Validate the Epay in the database
        List<Epay> epayList = epayRepository.findAll();
        assertThat(epayList).hasSize(databaseSizeBeforeUpdate);
        Epay testEpay = epayList.get(epayList.size() - 1);
        assertThat(testEpay.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testEpay.getDtFromDate()).isEqualTo(UPDATED_DT_FROM_DATE);
        assertThat(testEpay.getDtToDate()).isEqualTo(UPDATED_DT_TO_DATE);
        assertThat(testEpay.getBankCode()).isEqualTo(UPDATED_BANK_CODE);
        assertThat(testEpay.getBankBranchCode()).isEqualTo(UPDATED_BANK_BRANCH_CODE);
        assertThat(testEpay.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testEpay.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testEpay.getMaxCeilAmount()).isEqualTo(UPDATED_MAX_CEIL_AMOUNT);
        assertThat(testEpay.getInstallmentAmount()).isEqualTo(UPDATED_INSTALLMENT_AMOUNT);
        assertThat(testEpay.getMaxInstallmentAmount()).isEqualTo(UPDATED_MAX_INSTALLMENT_AMOUNT);
        assertThat(testEpay.getMandateRefNo()).isEqualTo(UPDATED_MANDATE_REF_NO);
        assertThat(testEpay.getDepositBank()).isEqualTo(UPDATED_DEPOSIT_BANK);
        assertThat(testEpay.getMandateType()).isEqualTo(UPDATED_MANDATE_TYPE);
        assertThat(testEpay.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testEpay.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testEpay.getUtrNo()).isEqualTo(UPDATED_UTR_NO);
        assertThat(testEpay.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEpay.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testEpay.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEpay.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEpay.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testEpay.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testEpay.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testEpay.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void putNonExistingEpay() throws Exception {
        int databaseSizeBeforeUpdate = epayRepository.findAll().size();
        epay.setId(count.incrementAndGet());

        // Create the Epay
        EpayDTO epayDTO = epayMapper.toDto(epay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEpayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, epayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(epayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Epay in the database
        List<Epay> epayList = epayRepository.findAll();
        assertThat(epayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEpay() throws Exception {
        int databaseSizeBeforeUpdate = epayRepository.findAll().size();
        epay.setId(count.incrementAndGet());

        // Create the Epay
        EpayDTO epayDTO = epayMapper.toDto(epay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEpayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(epayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Epay in the database
        List<Epay> epayList = epayRepository.findAll();
        assertThat(epayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEpay() throws Exception {
        int databaseSizeBeforeUpdate = epayRepository.findAll().size();
        epay.setId(count.incrementAndGet());

        // Create the Epay
        EpayDTO epayDTO = epayMapper.toDto(epay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEpayMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(epayDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Epay in the database
        List<Epay> epayList = epayRepository.findAll();
        assertThat(epayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEpayWithPatch() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        int databaseSizeBeforeUpdate = epayRepository.findAll().size();

        // Update the epay using partial update
        Epay partialUpdatedEpay = new Epay();
        partialUpdatedEpay.setId(epay.getId());

        partialUpdatedEpay
            .instrumentType(UPDATED_INSTRUMENT_TYPE)
            .dtFromDate(UPDATED_DT_FROM_DATE)
            .dtToDate(UPDATED_DT_TO_DATE)
            .bankCode(UPDATED_BANK_CODE)
            .maxCeilAmount(UPDATED_MAX_CEIL_AMOUNT)
            .installmentAmount(UPDATED_INSTALLMENT_AMOUNT)
            .maxInstallmentAmount(UPDATED_MAX_INSTALLMENT_AMOUNT)
            .mandateRefNo(UPDATED_MANDATE_REF_NO)
            .depositBank(UPDATED_DEPOSIT_BANK)
            .frequency(UPDATED_FREQUENCY)
            .utrNo(UPDATED_UTR_NO)
            .isActive(UPDATED_IS_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField3(UPDATED_FREE_FIELD_3);

        restEpayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEpay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEpay))
            )
            .andExpect(status().isOk());

        // Validate the Epay in the database
        List<Epay> epayList = epayRepository.findAll();
        assertThat(epayList).hasSize(databaseSizeBeforeUpdate);
        Epay testEpay = epayList.get(epayList.size() - 1);
        assertThat(testEpay.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testEpay.getDtFromDate()).isEqualTo(UPDATED_DT_FROM_DATE);
        assertThat(testEpay.getDtToDate()).isEqualTo(UPDATED_DT_TO_DATE);
        assertThat(testEpay.getBankCode()).isEqualTo(UPDATED_BANK_CODE);
        assertThat(testEpay.getBankBranchCode()).isEqualTo(DEFAULT_BANK_BRANCH_CODE);
        assertThat(testEpay.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testEpay.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testEpay.getMaxCeilAmount()).isEqualTo(UPDATED_MAX_CEIL_AMOUNT);
        assertThat(testEpay.getInstallmentAmount()).isEqualTo(UPDATED_INSTALLMENT_AMOUNT);
        assertThat(testEpay.getMaxInstallmentAmount()).isEqualTo(UPDATED_MAX_INSTALLMENT_AMOUNT);
        assertThat(testEpay.getMandateRefNo()).isEqualTo(UPDATED_MANDATE_REF_NO);
        assertThat(testEpay.getDepositBank()).isEqualTo(UPDATED_DEPOSIT_BANK);
        assertThat(testEpay.getMandateType()).isEqualTo(DEFAULT_MANDATE_TYPE);
        assertThat(testEpay.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testEpay.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testEpay.getUtrNo()).isEqualTo(UPDATED_UTR_NO);
        assertThat(testEpay.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testEpay.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testEpay.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEpay.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEpay.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testEpay.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testEpay.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testEpay.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void fullUpdateEpayWithPatch() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        int databaseSizeBeforeUpdate = epayRepository.findAll().size();

        // Update the epay using partial update
        Epay partialUpdatedEpay = new Epay();
        partialUpdatedEpay.setId(epay.getId());

        partialUpdatedEpay
            .instrumentType(UPDATED_INSTRUMENT_TYPE)
            .dtFromDate(UPDATED_DT_FROM_DATE)
            .dtToDate(UPDATED_DT_TO_DATE)
            .bankCode(UPDATED_BANK_CODE)
            .bankBranchCode(UPDATED_BANK_BRANCH_CODE)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .accountNo(UPDATED_ACCOUNT_NO)
            .maxCeilAmount(UPDATED_MAX_CEIL_AMOUNT)
            .installmentAmount(UPDATED_INSTALLMENT_AMOUNT)
            .maxInstallmentAmount(UPDATED_MAX_INSTALLMENT_AMOUNT)
            .mandateRefNo(UPDATED_MANDATE_REF_NO)
            .depositBank(UPDATED_DEPOSIT_BANK)
            .mandateType(UPDATED_MANDATE_TYPE)
            .frequency(UPDATED_FREQUENCY)
            .ifscCode(UPDATED_IFSC_CODE)
            .utrNo(UPDATED_UTR_NO)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restEpayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEpay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEpay))
            )
            .andExpect(status().isOk());

        // Validate the Epay in the database
        List<Epay> epayList = epayRepository.findAll();
        assertThat(epayList).hasSize(databaseSizeBeforeUpdate);
        Epay testEpay = epayList.get(epayList.size() - 1);
        assertThat(testEpay.getInstrumentType()).isEqualTo(UPDATED_INSTRUMENT_TYPE);
        assertThat(testEpay.getDtFromDate()).isEqualTo(UPDATED_DT_FROM_DATE);
        assertThat(testEpay.getDtToDate()).isEqualTo(UPDATED_DT_TO_DATE);
        assertThat(testEpay.getBankCode()).isEqualTo(UPDATED_BANK_CODE);
        assertThat(testEpay.getBankBranchCode()).isEqualTo(UPDATED_BANK_BRANCH_CODE);
        assertThat(testEpay.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testEpay.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testEpay.getMaxCeilAmount()).isEqualTo(UPDATED_MAX_CEIL_AMOUNT);
        assertThat(testEpay.getInstallmentAmount()).isEqualTo(UPDATED_INSTALLMENT_AMOUNT);
        assertThat(testEpay.getMaxInstallmentAmount()).isEqualTo(UPDATED_MAX_INSTALLMENT_AMOUNT);
        assertThat(testEpay.getMandateRefNo()).isEqualTo(UPDATED_MANDATE_REF_NO);
        assertThat(testEpay.getDepositBank()).isEqualTo(UPDATED_DEPOSIT_BANK);
        assertThat(testEpay.getMandateType()).isEqualTo(UPDATED_MANDATE_TYPE);
        assertThat(testEpay.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testEpay.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testEpay.getUtrNo()).isEqualTo(UPDATED_UTR_NO);
        assertThat(testEpay.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEpay.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testEpay.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEpay.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEpay.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testEpay.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testEpay.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testEpay.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void patchNonExistingEpay() throws Exception {
        int databaseSizeBeforeUpdate = epayRepository.findAll().size();
        epay.setId(count.incrementAndGet());

        // Create the Epay
        EpayDTO epayDTO = epayMapper.toDto(epay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEpayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, epayDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(epayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Epay in the database
        List<Epay> epayList = epayRepository.findAll();
        assertThat(epayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEpay() throws Exception {
        int databaseSizeBeforeUpdate = epayRepository.findAll().size();
        epay.setId(count.incrementAndGet());

        // Create the Epay
        EpayDTO epayDTO = epayMapper.toDto(epay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEpayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(epayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Epay in the database
        List<Epay> epayList = epayRepository.findAll();
        assertThat(epayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEpay() throws Exception {
        int databaseSizeBeforeUpdate = epayRepository.findAll().size();
        epay.setId(count.incrementAndGet());

        // Create the Epay
        EpayDTO epayDTO = epayMapper.toDto(epay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEpayMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(epayDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Epay in the database
        List<Epay> epayList = epayRepository.findAll();
        assertThat(epayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEpay() throws Exception {
        // Initialize the database
        epayRepository.saveAndFlush(epay);

        int databaseSizeBeforeDelete = epayRepository.findAll().size();

        // Delete the epay
        restEpayMockMvc
            .perform(delete(ENTITY_API_URL_ID, epay.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Epay> epayList = epayRepository.findAll();
        assertThat(epayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
