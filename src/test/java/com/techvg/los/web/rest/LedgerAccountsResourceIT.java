package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.Branch;
import com.techvg.los.domain.LedgerAccounts;
import com.techvg.los.domain.LedgerAccounts;
import com.techvg.los.domain.enumeration.LedgerClassification;
import com.techvg.los.repository.LedgerAccountsRepository;
import com.techvg.los.service.criteria.LedgerAccountsCriteria;
import com.techvg.los.service.dto.LedgerAccountsDTO;
import com.techvg.los.service.mapper.LedgerAccountsMapper;
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
 * Integration tests for the {@link LedgerAccountsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LedgerAccountsResourceIT {

    private static final Long DEFAULT_ACCOUNT_NO = 1L;
    private static final Long UPDATED_ACCOUNT_NO = 2L;
    private static final Long SMALLER_ACCOUNT_NO = 1L - 1L;

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_ACC_BALANCE = 1D;
    private static final Double UPDATED_ACC_BALANCE = 2D;
    private static final Double SMALLER_ACC_BALANCE = 1D - 1D;

    private static final String DEFAULT_ACC_HEAD_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACC_HEAD_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LEDGER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LEDGER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_APP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_APP_CODE = "BBBBBBBBBB";

    private static final LedgerClassification DEFAULT_LEDGER_CLASSIFICATION = LedgerClassification.BALANCE_SHEET;
    private static final LedgerClassification UPDATED_LEDGER_CLASSIFICATION = LedgerClassification.TRADING_ACCOUNT;

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;
    private static final Integer SMALLER_LEVEL = 1 - 1;

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

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

    private static final String ENTITY_API_URL = "/api/ledger-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LedgerAccountsRepository ledgerAccountsRepository;

    @Autowired
    private LedgerAccountsMapper ledgerAccountsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLedgerAccountsMockMvc;

    private LedgerAccounts ledgerAccounts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LedgerAccounts createEntity(EntityManager em) {
        LedgerAccounts ledgerAccounts = new LedgerAccounts()
            .accountNo(DEFAULT_ACCOUNT_NO)
            .accountName(DEFAULT_ACCOUNT_NAME)
            .accBalance(DEFAULT_ACC_BALANCE)
            .accHeadCode(DEFAULT_ACC_HEAD_CODE)
            .ledgerCode(DEFAULT_LEDGER_CODE)
            .appCode(DEFAULT_APP_CODE)
            .ledgerClassification(DEFAULT_LEDGER_CLASSIFICATION)
            .level(DEFAULT_LEVEL)
            .year(DEFAULT_YEAR)
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5);
        return ledgerAccounts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LedgerAccounts createUpdatedEntity(EntityManager em) {
        LedgerAccounts ledgerAccounts = new LedgerAccounts()
            .accountNo(UPDATED_ACCOUNT_NO)
            .accountName(UPDATED_ACCOUNT_NAME)
            .accBalance(UPDATED_ACC_BALANCE)
            .accHeadCode(UPDATED_ACC_HEAD_CODE)
            .ledgerCode(UPDATED_LEDGER_CODE)
            .appCode(UPDATED_APP_CODE)
            .ledgerClassification(UPDATED_LEDGER_CLASSIFICATION)
            .level(UPDATED_LEVEL)
            .year(UPDATED_YEAR)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);
        return ledgerAccounts;
    }

    @BeforeEach
    public void initTest() {
        ledgerAccounts = createEntity(em);
    }

    @Test
    @Transactional
    void createLedgerAccounts() throws Exception {
        int databaseSizeBeforeCreate = ledgerAccountsRepository.findAll().size();
        // Create the LedgerAccounts
        LedgerAccountsDTO ledgerAccountsDTO = ledgerAccountsMapper.toDto(ledgerAccounts);
        restLedgerAccountsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ledgerAccountsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LedgerAccounts in the database
        List<LedgerAccounts> ledgerAccountsList = ledgerAccountsRepository.findAll();
        assertThat(ledgerAccountsList).hasSize(databaseSizeBeforeCreate + 1);
        LedgerAccounts testLedgerAccounts = ledgerAccountsList.get(ledgerAccountsList.size() - 1);
        assertThat(testLedgerAccounts.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testLedgerAccounts.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testLedgerAccounts.getAccBalance()).isEqualTo(DEFAULT_ACC_BALANCE);
        assertThat(testLedgerAccounts.getAccHeadCode()).isEqualTo(DEFAULT_ACC_HEAD_CODE);
        assertThat(testLedgerAccounts.getLedgerCode()).isEqualTo(DEFAULT_LEDGER_CODE);
        assertThat(testLedgerAccounts.getAppCode()).isEqualTo(DEFAULT_APP_CODE);
        assertThat(testLedgerAccounts.getLedgerClassification()).isEqualTo(DEFAULT_LEDGER_CLASSIFICATION);
        assertThat(testLedgerAccounts.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testLedgerAccounts.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testLedgerAccounts.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testLedgerAccounts.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLedgerAccounts.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLedgerAccounts.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLedgerAccounts.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testLedgerAccounts.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testLedgerAccounts.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLedgerAccounts.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testLedgerAccounts.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testLedgerAccounts.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testLedgerAccounts.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void createLedgerAccountsWithExistingId() throws Exception {
        // Create the LedgerAccounts with an existing ID
        ledgerAccounts.setId(1L);
        LedgerAccountsDTO ledgerAccountsDTO = ledgerAccountsMapper.toDto(ledgerAccounts);

        int databaseSizeBeforeCreate = ledgerAccountsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLedgerAccountsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ledgerAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LedgerAccounts in the database
        List<LedgerAccounts> ledgerAccountsList = ledgerAccountsRepository.findAll();
        assertThat(ledgerAccountsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLedgerAccounts() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList
        restLedgerAccountsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ledgerAccounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO.intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].accBalance").value(hasItem(DEFAULT_ACC_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].accHeadCode").value(hasItem(DEFAULT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].ledgerCode").value(hasItem(DEFAULT_LEDGER_CODE)))
            .andExpect(jsonPath("$.[*].appCode").value(hasItem(DEFAULT_APP_CODE)))
            .andExpect(jsonPath("$.[*].ledgerClassification").value(hasItem(DEFAULT_LEDGER_CLASSIFICATION.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)));
    }

    @Test
    @Transactional
    void getLedgerAccounts() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get the ledgerAccounts
        restLedgerAccountsMockMvc
            .perform(get(ENTITY_API_URL_ID, ledgerAccounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ledgerAccounts.getId().intValue()))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO.intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME))
            .andExpect(jsonPath("$.accBalance").value(DEFAULT_ACC_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.accHeadCode").value(DEFAULT_ACC_HEAD_CODE))
            .andExpect(jsonPath("$.ledgerCode").value(DEFAULT_LEDGER_CODE))
            .andExpect(jsonPath("$.appCode").value(DEFAULT_APP_CODE))
            .andExpect(jsonPath("$.ledgerClassification").value(DEFAULT_LEDGER_CLASSIFICATION.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.freeField5").value(DEFAULT_FREE_FIELD_5));
    }

    @Test
    @Transactional
    void getLedgerAccountsByIdFiltering() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        Long id = ledgerAccounts.getId();

        defaultLedgerAccountsShouldBeFound("id.equals=" + id);
        defaultLedgerAccountsShouldNotBeFound("id.notEquals=" + id);

        defaultLedgerAccountsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLedgerAccountsShouldNotBeFound("id.greaterThan=" + id);

        defaultLedgerAccountsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLedgerAccountsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountNoIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountNo equals to DEFAULT_ACCOUNT_NO
        defaultLedgerAccountsShouldBeFound("accountNo.equals=" + DEFAULT_ACCOUNT_NO);

        // Get all the ledgerAccountsList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultLedgerAccountsShouldNotBeFound("accountNo.equals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountNoIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountNo in DEFAULT_ACCOUNT_NO or UPDATED_ACCOUNT_NO
        defaultLedgerAccountsShouldBeFound("accountNo.in=" + DEFAULT_ACCOUNT_NO + "," + UPDATED_ACCOUNT_NO);

        // Get all the ledgerAccountsList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultLedgerAccountsShouldNotBeFound("accountNo.in=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountNo is not null
        defaultLedgerAccountsShouldBeFound("accountNo.specified=true");

        // Get all the ledgerAccountsList where accountNo is null
        defaultLedgerAccountsShouldNotBeFound("accountNo.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountNo is greater than or equal to DEFAULT_ACCOUNT_NO
        defaultLedgerAccountsShouldBeFound("accountNo.greaterThanOrEqual=" + DEFAULT_ACCOUNT_NO);

        // Get all the ledgerAccountsList where accountNo is greater than or equal to UPDATED_ACCOUNT_NO
        defaultLedgerAccountsShouldNotBeFound("accountNo.greaterThanOrEqual=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountNo is less than or equal to DEFAULT_ACCOUNT_NO
        defaultLedgerAccountsShouldBeFound("accountNo.lessThanOrEqual=" + DEFAULT_ACCOUNT_NO);

        // Get all the ledgerAccountsList where accountNo is less than or equal to SMALLER_ACCOUNT_NO
        defaultLedgerAccountsShouldNotBeFound("accountNo.lessThanOrEqual=" + SMALLER_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountNoIsLessThanSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountNo is less than DEFAULT_ACCOUNT_NO
        defaultLedgerAccountsShouldNotBeFound("accountNo.lessThan=" + DEFAULT_ACCOUNT_NO);

        // Get all the ledgerAccountsList where accountNo is less than UPDATED_ACCOUNT_NO
        defaultLedgerAccountsShouldBeFound("accountNo.lessThan=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountNo is greater than DEFAULT_ACCOUNT_NO
        defaultLedgerAccountsShouldNotBeFound("accountNo.greaterThan=" + DEFAULT_ACCOUNT_NO);

        // Get all the ledgerAccountsList where accountNo is greater than SMALLER_ACCOUNT_NO
        defaultLedgerAccountsShouldBeFound("accountNo.greaterThan=" + SMALLER_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountName equals to DEFAULT_ACCOUNT_NAME
        defaultLedgerAccountsShouldBeFound("accountName.equals=" + DEFAULT_ACCOUNT_NAME);

        // Get all the ledgerAccountsList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultLedgerAccountsShouldNotBeFound("accountName.equals=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountName in DEFAULT_ACCOUNT_NAME or UPDATED_ACCOUNT_NAME
        defaultLedgerAccountsShouldBeFound("accountName.in=" + DEFAULT_ACCOUNT_NAME + "," + UPDATED_ACCOUNT_NAME);

        // Get all the ledgerAccountsList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultLedgerAccountsShouldNotBeFound("accountName.in=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountName is not null
        defaultLedgerAccountsShouldBeFound("accountName.specified=true");

        // Get all the ledgerAccountsList where accountName is null
        defaultLedgerAccountsShouldNotBeFound("accountName.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountNameContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountName contains DEFAULT_ACCOUNT_NAME
        defaultLedgerAccountsShouldBeFound("accountName.contains=" + DEFAULT_ACCOUNT_NAME);

        // Get all the ledgerAccountsList where accountName contains UPDATED_ACCOUNT_NAME
        defaultLedgerAccountsShouldNotBeFound("accountName.contains=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountNameNotContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountName does not contain DEFAULT_ACCOUNT_NAME
        defaultLedgerAccountsShouldNotBeFound("accountName.doesNotContain=" + DEFAULT_ACCOUNT_NAME);

        // Get all the ledgerAccountsList where accountName does not contain UPDATED_ACCOUNT_NAME
        defaultLedgerAccountsShouldBeFound("accountName.doesNotContain=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accBalance equals to DEFAULT_ACC_BALANCE
        defaultLedgerAccountsShouldBeFound("accBalance.equals=" + DEFAULT_ACC_BALANCE);

        // Get all the ledgerAccountsList where accBalance equals to UPDATED_ACC_BALANCE
        defaultLedgerAccountsShouldNotBeFound("accBalance.equals=" + UPDATED_ACC_BALANCE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accBalance in DEFAULT_ACC_BALANCE or UPDATED_ACC_BALANCE
        defaultLedgerAccountsShouldBeFound("accBalance.in=" + DEFAULT_ACC_BALANCE + "," + UPDATED_ACC_BALANCE);

        // Get all the ledgerAccountsList where accBalance equals to UPDATED_ACC_BALANCE
        defaultLedgerAccountsShouldNotBeFound("accBalance.in=" + UPDATED_ACC_BALANCE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accBalance is not null
        defaultLedgerAccountsShouldBeFound("accBalance.specified=true");

        // Get all the ledgerAccountsList where accBalance is null
        defaultLedgerAccountsShouldNotBeFound("accBalance.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccBalanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accBalance is greater than or equal to DEFAULT_ACC_BALANCE
        defaultLedgerAccountsShouldBeFound("accBalance.greaterThanOrEqual=" + DEFAULT_ACC_BALANCE);

        // Get all the ledgerAccountsList where accBalance is greater than or equal to UPDATED_ACC_BALANCE
        defaultLedgerAccountsShouldNotBeFound("accBalance.greaterThanOrEqual=" + UPDATED_ACC_BALANCE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccBalanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accBalance is less than or equal to DEFAULT_ACC_BALANCE
        defaultLedgerAccountsShouldBeFound("accBalance.lessThanOrEqual=" + DEFAULT_ACC_BALANCE);

        // Get all the ledgerAccountsList where accBalance is less than or equal to SMALLER_ACC_BALANCE
        defaultLedgerAccountsShouldNotBeFound("accBalance.lessThanOrEqual=" + SMALLER_ACC_BALANCE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccBalanceIsLessThanSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accBalance is less than DEFAULT_ACC_BALANCE
        defaultLedgerAccountsShouldNotBeFound("accBalance.lessThan=" + DEFAULT_ACC_BALANCE);

        // Get all the ledgerAccountsList where accBalance is less than UPDATED_ACC_BALANCE
        defaultLedgerAccountsShouldBeFound("accBalance.lessThan=" + UPDATED_ACC_BALANCE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccBalanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accBalance is greater than DEFAULT_ACC_BALANCE
        defaultLedgerAccountsShouldNotBeFound("accBalance.greaterThan=" + DEFAULT_ACC_BALANCE);

        // Get all the ledgerAccountsList where accBalance is greater than SMALLER_ACC_BALANCE
        defaultLedgerAccountsShouldBeFound("accBalance.greaterThan=" + SMALLER_ACC_BALANCE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccHeadCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accHeadCode equals to DEFAULT_ACC_HEAD_CODE
        defaultLedgerAccountsShouldBeFound("accHeadCode.equals=" + DEFAULT_ACC_HEAD_CODE);

        // Get all the ledgerAccountsList where accHeadCode equals to UPDATED_ACC_HEAD_CODE
        defaultLedgerAccountsShouldNotBeFound("accHeadCode.equals=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccHeadCodeIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accHeadCode in DEFAULT_ACC_HEAD_CODE or UPDATED_ACC_HEAD_CODE
        defaultLedgerAccountsShouldBeFound("accHeadCode.in=" + DEFAULT_ACC_HEAD_CODE + "," + UPDATED_ACC_HEAD_CODE);

        // Get all the ledgerAccountsList where accHeadCode equals to UPDATED_ACC_HEAD_CODE
        defaultLedgerAccountsShouldNotBeFound("accHeadCode.in=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccHeadCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accHeadCode is not null
        defaultLedgerAccountsShouldBeFound("accHeadCode.specified=true");

        // Get all the ledgerAccountsList where accHeadCode is null
        defaultLedgerAccountsShouldNotBeFound("accHeadCode.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccHeadCodeContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accHeadCode contains DEFAULT_ACC_HEAD_CODE
        defaultLedgerAccountsShouldBeFound("accHeadCode.contains=" + DEFAULT_ACC_HEAD_CODE);

        // Get all the ledgerAccountsList where accHeadCode contains UPDATED_ACC_HEAD_CODE
        defaultLedgerAccountsShouldNotBeFound("accHeadCode.contains=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccHeadCodeNotContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accHeadCode does not contain DEFAULT_ACC_HEAD_CODE
        defaultLedgerAccountsShouldNotBeFound("accHeadCode.doesNotContain=" + DEFAULT_ACC_HEAD_CODE);

        // Get all the ledgerAccountsList where accHeadCode does not contain UPDATED_ACC_HEAD_CODE
        defaultLedgerAccountsShouldBeFound("accHeadCode.doesNotContain=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLedgerCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where ledgerCode equals to DEFAULT_LEDGER_CODE
        defaultLedgerAccountsShouldBeFound("ledgerCode.equals=" + DEFAULT_LEDGER_CODE);

        // Get all the ledgerAccountsList where ledgerCode equals to UPDATED_LEDGER_CODE
        defaultLedgerAccountsShouldNotBeFound("ledgerCode.equals=" + UPDATED_LEDGER_CODE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLedgerCodeIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where ledgerCode in DEFAULT_LEDGER_CODE or UPDATED_LEDGER_CODE
        defaultLedgerAccountsShouldBeFound("ledgerCode.in=" + DEFAULT_LEDGER_CODE + "," + UPDATED_LEDGER_CODE);

        // Get all the ledgerAccountsList where ledgerCode equals to UPDATED_LEDGER_CODE
        defaultLedgerAccountsShouldNotBeFound("ledgerCode.in=" + UPDATED_LEDGER_CODE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLedgerCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where ledgerCode is not null
        defaultLedgerAccountsShouldBeFound("ledgerCode.specified=true");

        // Get all the ledgerAccountsList where ledgerCode is null
        defaultLedgerAccountsShouldNotBeFound("ledgerCode.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLedgerCodeContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where ledgerCode contains DEFAULT_LEDGER_CODE
        defaultLedgerAccountsShouldBeFound("ledgerCode.contains=" + DEFAULT_LEDGER_CODE);

        // Get all the ledgerAccountsList where ledgerCode contains UPDATED_LEDGER_CODE
        defaultLedgerAccountsShouldNotBeFound("ledgerCode.contains=" + UPDATED_LEDGER_CODE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLedgerCodeNotContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where ledgerCode does not contain DEFAULT_LEDGER_CODE
        defaultLedgerAccountsShouldNotBeFound("ledgerCode.doesNotContain=" + DEFAULT_LEDGER_CODE);

        // Get all the ledgerAccountsList where ledgerCode does not contain UPDATED_LEDGER_CODE
        defaultLedgerAccountsShouldBeFound("ledgerCode.doesNotContain=" + UPDATED_LEDGER_CODE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAppCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where appCode equals to DEFAULT_APP_CODE
        defaultLedgerAccountsShouldBeFound("appCode.equals=" + DEFAULT_APP_CODE);

        // Get all the ledgerAccountsList where appCode equals to UPDATED_APP_CODE
        defaultLedgerAccountsShouldNotBeFound("appCode.equals=" + UPDATED_APP_CODE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAppCodeIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where appCode in DEFAULT_APP_CODE or UPDATED_APP_CODE
        defaultLedgerAccountsShouldBeFound("appCode.in=" + DEFAULT_APP_CODE + "," + UPDATED_APP_CODE);

        // Get all the ledgerAccountsList where appCode equals to UPDATED_APP_CODE
        defaultLedgerAccountsShouldNotBeFound("appCode.in=" + UPDATED_APP_CODE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAppCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where appCode is not null
        defaultLedgerAccountsShouldBeFound("appCode.specified=true");

        // Get all the ledgerAccountsList where appCode is null
        defaultLedgerAccountsShouldNotBeFound("appCode.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAppCodeContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where appCode contains DEFAULT_APP_CODE
        defaultLedgerAccountsShouldBeFound("appCode.contains=" + DEFAULT_APP_CODE);

        // Get all the ledgerAccountsList where appCode contains UPDATED_APP_CODE
        defaultLedgerAccountsShouldNotBeFound("appCode.contains=" + UPDATED_APP_CODE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAppCodeNotContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where appCode does not contain DEFAULT_APP_CODE
        defaultLedgerAccountsShouldNotBeFound("appCode.doesNotContain=" + DEFAULT_APP_CODE);

        // Get all the ledgerAccountsList where appCode does not contain UPDATED_APP_CODE
        defaultLedgerAccountsShouldBeFound("appCode.doesNotContain=" + UPDATED_APP_CODE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLedgerClassificationIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where ledgerClassification equals to DEFAULT_LEDGER_CLASSIFICATION
        defaultLedgerAccountsShouldBeFound("ledgerClassification.equals=" + DEFAULT_LEDGER_CLASSIFICATION);

        // Get all the ledgerAccountsList where ledgerClassification equals to UPDATED_LEDGER_CLASSIFICATION
        defaultLedgerAccountsShouldNotBeFound("ledgerClassification.equals=" + UPDATED_LEDGER_CLASSIFICATION);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLedgerClassificationIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where ledgerClassification in DEFAULT_LEDGER_CLASSIFICATION or UPDATED_LEDGER_CLASSIFICATION
        defaultLedgerAccountsShouldBeFound(
            "ledgerClassification.in=" + DEFAULT_LEDGER_CLASSIFICATION + "," + UPDATED_LEDGER_CLASSIFICATION
        );

        // Get all the ledgerAccountsList where ledgerClassification equals to UPDATED_LEDGER_CLASSIFICATION
        defaultLedgerAccountsShouldNotBeFound("ledgerClassification.in=" + UPDATED_LEDGER_CLASSIFICATION);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLedgerClassificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where ledgerClassification is not null
        defaultLedgerAccountsShouldBeFound("ledgerClassification.specified=true");

        // Get all the ledgerAccountsList where ledgerClassification is null
        defaultLedgerAccountsShouldNotBeFound("ledgerClassification.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where level equals to DEFAULT_LEVEL
        defaultLedgerAccountsShouldBeFound("level.equals=" + DEFAULT_LEVEL);

        // Get all the ledgerAccountsList where level equals to UPDATED_LEVEL
        defaultLedgerAccountsShouldNotBeFound("level.equals=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLevelIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where level in DEFAULT_LEVEL or UPDATED_LEVEL
        defaultLedgerAccountsShouldBeFound("level.in=" + DEFAULT_LEVEL + "," + UPDATED_LEVEL);

        // Get all the ledgerAccountsList where level equals to UPDATED_LEVEL
        defaultLedgerAccountsShouldNotBeFound("level.in=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where level is not null
        defaultLedgerAccountsShouldBeFound("level.specified=true");

        // Get all the ledgerAccountsList where level is null
        defaultLedgerAccountsShouldNotBeFound("level.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where level is greater than or equal to DEFAULT_LEVEL
        defaultLedgerAccountsShouldBeFound("level.greaterThanOrEqual=" + DEFAULT_LEVEL);

        // Get all the ledgerAccountsList where level is greater than or equal to UPDATED_LEVEL
        defaultLedgerAccountsShouldNotBeFound("level.greaterThanOrEqual=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where level is less than or equal to DEFAULT_LEVEL
        defaultLedgerAccountsShouldBeFound("level.lessThanOrEqual=" + DEFAULT_LEVEL);

        // Get all the ledgerAccountsList where level is less than or equal to SMALLER_LEVEL
        defaultLedgerAccountsShouldNotBeFound("level.lessThanOrEqual=" + SMALLER_LEVEL);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where level is less than DEFAULT_LEVEL
        defaultLedgerAccountsShouldNotBeFound("level.lessThan=" + DEFAULT_LEVEL);

        // Get all the ledgerAccountsList where level is less than UPDATED_LEVEL
        defaultLedgerAccountsShouldBeFound("level.lessThan=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where level is greater than DEFAULT_LEVEL
        defaultLedgerAccountsShouldNotBeFound("level.greaterThan=" + DEFAULT_LEVEL);

        // Get all the ledgerAccountsList where level is greater than SMALLER_LEVEL
        defaultLedgerAccountsShouldBeFound("level.greaterThan=" + SMALLER_LEVEL);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where year equals to DEFAULT_YEAR
        defaultLedgerAccountsShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the ledgerAccountsList where year equals to UPDATED_YEAR
        defaultLedgerAccountsShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultLedgerAccountsShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the ledgerAccountsList where year equals to UPDATED_YEAR
        defaultLedgerAccountsShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where year is not null
        defaultLedgerAccountsShouldBeFound("year.specified=true");

        // Get all the ledgerAccountsList where year is null
        defaultLedgerAccountsShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByYearContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where year contains DEFAULT_YEAR
        defaultLedgerAccountsShouldBeFound("year.contains=" + DEFAULT_YEAR);

        // Get all the ledgerAccountsList where year contains UPDATED_YEAR
        defaultLedgerAccountsShouldNotBeFound("year.contains=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByYearNotContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where year does not contain DEFAULT_YEAR
        defaultLedgerAccountsShouldNotBeFound("year.doesNotContain=" + DEFAULT_YEAR);

        // Get all the ledgerAccountsList where year does not contain UPDATED_YEAR
        defaultLedgerAccountsShouldBeFound("year.doesNotContain=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountType equals to DEFAULT_ACCOUNT_TYPE
        defaultLedgerAccountsShouldBeFound("accountType.equals=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the ledgerAccountsList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultLedgerAccountsShouldNotBeFound("accountType.equals=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountTypeIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountType in DEFAULT_ACCOUNT_TYPE or UPDATED_ACCOUNT_TYPE
        defaultLedgerAccountsShouldBeFound("accountType.in=" + DEFAULT_ACCOUNT_TYPE + "," + UPDATED_ACCOUNT_TYPE);

        // Get all the ledgerAccountsList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultLedgerAccountsShouldNotBeFound("accountType.in=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountType is not null
        defaultLedgerAccountsShouldBeFound("accountType.specified=true");

        // Get all the ledgerAccountsList where accountType is null
        defaultLedgerAccountsShouldNotBeFound("accountType.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountTypeContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountType contains DEFAULT_ACCOUNT_TYPE
        defaultLedgerAccountsShouldBeFound("accountType.contains=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the ledgerAccountsList where accountType contains UPDATED_ACCOUNT_TYPE
        defaultLedgerAccountsShouldNotBeFound("accountType.contains=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByAccountTypeNotContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where accountType does not contain DEFAULT_ACCOUNT_TYPE
        defaultLedgerAccountsShouldNotBeFound("accountType.doesNotContain=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the ledgerAccountsList where accountType does not contain UPDATED_ACCOUNT_TYPE
        defaultLedgerAccountsShouldBeFound("accountType.doesNotContain=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLedgerAccountsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the ledgerAccountsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLedgerAccountsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLedgerAccountsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the ledgerAccountsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLedgerAccountsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where lastModified is not null
        defaultLedgerAccountsShouldBeFound("lastModified.specified=true");

        // Get all the ledgerAccountsList where lastModified is null
        defaultLedgerAccountsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLedgerAccountsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the ledgerAccountsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLedgerAccountsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLedgerAccountsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the ledgerAccountsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLedgerAccountsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where lastModifiedBy is not null
        defaultLedgerAccountsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the ledgerAccountsList where lastModifiedBy is null
        defaultLedgerAccountsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLedgerAccountsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the ledgerAccountsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLedgerAccountsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLedgerAccountsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the ledgerAccountsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLedgerAccountsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where createdBy equals to DEFAULT_CREATED_BY
        defaultLedgerAccountsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the ledgerAccountsList where createdBy equals to UPDATED_CREATED_BY
        defaultLedgerAccountsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultLedgerAccountsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the ledgerAccountsList where createdBy equals to UPDATED_CREATED_BY
        defaultLedgerAccountsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where createdBy is not null
        defaultLedgerAccountsShouldBeFound("createdBy.specified=true");

        // Get all the ledgerAccountsList where createdBy is null
        defaultLedgerAccountsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where createdBy contains DEFAULT_CREATED_BY
        defaultLedgerAccountsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the ledgerAccountsList where createdBy contains UPDATED_CREATED_BY
        defaultLedgerAccountsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultLedgerAccountsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the ledgerAccountsList where createdBy does not contain UPDATED_CREATED_BY
        defaultLedgerAccountsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where createdOn equals to DEFAULT_CREATED_ON
        defaultLedgerAccountsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the ledgerAccountsList where createdOn equals to UPDATED_CREATED_ON
        defaultLedgerAccountsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultLedgerAccountsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the ledgerAccountsList where createdOn equals to UPDATED_CREATED_ON
        defaultLedgerAccountsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where createdOn is not null
        defaultLedgerAccountsShouldBeFound("createdOn.specified=true");

        // Get all the ledgerAccountsList where createdOn is null
        defaultLedgerAccountsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultLedgerAccountsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the ledgerAccountsList where isDeleted equals to UPDATED_IS_DELETED
        defaultLedgerAccountsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultLedgerAccountsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the ledgerAccountsList where isDeleted equals to UPDATED_IS_DELETED
        defaultLedgerAccountsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where isDeleted is not null
        defaultLedgerAccountsShouldBeFound("isDeleted.specified=true");

        // Get all the ledgerAccountsList where isDeleted is null
        defaultLedgerAccountsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultLedgerAccountsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the ledgerAccountsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLedgerAccountsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultLedgerAccountsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the ledgerAccountsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLedgerAccountsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField1 is not null
        defaultLedgerAccountsShouldBeFound("freeField1.specified=true");

        // Get all the ledgerAccountsList where freeField1 is null
        defaultLedgerAccountsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultLedgerAccountsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the ledgerAccountsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultLedgerAccountsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultLedgerAccountsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the ledgerAccountsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultLedgerAccountsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultLedgerAccountsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the ledgerAccountsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLedgerAccountsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultLedgerAccountsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the ledgerAccountsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLedgerAccountsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField2 is not null
        defaultLedgerAccountsShouldBeFound("freeField2.specified=true");

        // Get all the ledgerAccountsList where freeField2 is null
        defaultLedgerAccountsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultLedgerAccountsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the ledgerAccountsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultLedgerAccountsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultLedgerAccountsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the ledgerAccountsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultLedgerAccountsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultLedgerAccountsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the ledgerAccountsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLedgerAccountsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultLedgerAccountsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the ledgerAccountsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLedgerAccountsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField3 is not null
        defaultLedgerAccountsShouldBeFound("freeField3.specified=true");

        // Get all the ledgerAccountsList where freeField3 is null
        defaultLedgerAccountsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultLedgerAccountsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the ledgerAccountsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultLedgerAccountsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultLedgerAccountsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the ledgerAccountsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultLedgerAccountsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultLedgerAccountsShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the ledgerAccountsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultLedgerAccountsShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultLedgerAccountsShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the ledgerAccountsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultLedgerAccountsShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField4 is not null
        defaultLedgerAccountsShouldBeFound("freeField4.specified=true");

        // Get all the ledgerAccountsList where freeField4 is null
        defaultLedgerAccountsShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultLedgerAccountsShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the ledgerAccountsList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultLedgerAccountsShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultLedgerAccountsShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the ledgerAccountsList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultLedgerAccountsShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultLedgerAccountsShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the ledgerAccountsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultLedgerAccountsShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultLedgerAccountsShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the ledgerAccountsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultLedgerAccountsShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField5 is not null
        defaultLedgerAccountsShouldBeFound("freeField5.specified=true");

        // Get all the ledgerAccountsList where freeField5 is null
        defaultLedgerAccountsShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultLedgerAccountsShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the ledgerAccountsList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultLedgerAccountsShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        // Get all the ledgerAccountsList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultLedgerAccountsShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the ledgerAccountsList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultLedgerAccountsShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByBranchIsEqualToSomething() throws Exception {
        Branch branch;
        if (TestUtil.findAll(em, Branch.class).isEmpty()) {
            ledgerAccountsRepository.saveAndFlush(ledgerAccounts);
            branch = BranchResourceIT.createEntity(em);
        } else {
            branch = TestUtil.findAll(em, Branch.class).get(0);
        }
        em.persist(branch);
        em.flush();
        ledgerAccounts.setBranch(branch);
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);
        Long branchId = branch.getId();

        // Get all the ledgerAccountsList where branch equals to branchId
        defaultLedgerAccountsShouldBeFound("branchId.equals=" + branchId);

        // Get all the ledgerAccountsList where branch equals to (branchId + 1)
        defaultLedgerAccountsShouldNotBeFound("branchId.equals=" + (branchId + 1));
    }

    @Test
    @Transactional
    void getAllLedgerAccountsByLedgerAccountsIsEqualToSomething() throws Exception {
        LedgerAccounts ledgerAccounts;
        if (TestUtil.findAll(em, LedgerAccounts.class).isEmpty()) {
            //       ledgerAccountsRepository.saveAndFlush(ledgerAccounts);
            ledgerAccounts = LedgerAccountsResourceIT.createEntity(em);
        } else {
            ledgerAccounts = TestUtil.findAll(em, LedgerAccounts.class).get(0);
        }
        em.persist(ledgerAccounts);
        em.flush();
        ledgerAccounts.setLedgerAccounts(ledgerAccounts);
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);
        Long ledgerAccountsId = ledgerAccounts.getId();

        // Get all the ledgerAccountsList where ledgerAccounts equals to ledgerAccountsId
        defaultLedgerAccountsShouldBeFound("ledgerAccountsId.equals=" + ledgerAccountsId);

        // Get all the ledgerAccountsList where ledgerAccounts equals to (ledgerAccountsId + 1)
        defaultLedgerAccountsShouldNotBeFound("ledgerAccountsId.equals=" + (ledgerAccountsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLedgerAccountsShouldBeFound(String filter) throws Exception {
        restLedgerAccountsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ledgerAccounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO.intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].accBalance").value(hasItem(DEFAULT_ACC_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].accHeadCode").value(hasItem(DEFAULT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].ledgerCode").value(hasItem(DEFAULT_LEDGER_CODE)))
            .andExpect(jsonPath("$.[*].appCode").value(hasItem(DEFAULT_APP_CODE)))
            .andExpect(jsonPath("$.[*].ledgerClassification").value(hasItem(DEFAULT_LEDGER_CLASSIFICATION.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)));

        // Check, that the count call also returns 1
        restLedgerAccountsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLedgerAccountsShouldNotBeFound(String filter) throws Exception {
        restLedgerAccountsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLedgerAccountsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLedgerAccounts() throws Exception {
        // Get the ledgerAccounts
        restLedgerAccountsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLedgerAccounts() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        int databaseSizeBeforeUpdate = ledgerAccountsRepository.findAll().size();

        // Update the ledgerAccounts
        LedgerAccounts updatedLedgerAccounts = ledgerAccountsRepository.findById(ledgerAccounts.getId()).get();
        // Disconnect from session so that the updates on updatedLedgerAccounts are not directly saved in db
        em.detach(updatedLedgerAccounts);
        updatedLedgerAccounts
            .accountNo(UPDATED_ACCOUNT_NO)
            .accountName(UPDATED_ACCOUNT_NAME)
            .accBalance(UPDATED_ACC_BALANCE)
            .accHeadCode(UPDATED_ACC_HEAD_CODE)
            .ledgerCode(UPDATED_LEDGER_CODE)
            .appCode(UPDATED_APP_CODE)
            .ledgerClassification(UPDATED_LEDGER_CLASSIFICATION)
            .level(UPDATED_LEVEL)
            .year(UPDATED_YEAR)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);
        LedgerAccountsDTO ledgerAccountsDTO = ledgerAccountsMapper.toDto(updatedLedgerAccounts);

        restLedgerAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ledgerAccountsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ledgerAccountsDTO))
            )
            .andExpect(status().isOk());

        // Validate the LedgerAccounts in the database
        List<LedgerAccounts> ledgerAccountsList = ledgerAccountsRepository.findAll();
        assertThat(ledgerAccountsList).hasSize(databaseSizeBeforeUpdate);
        LedgerAccounts testLedgerAccounts = ledgerAccountsList.get(ledgerAccountsList.size() - 1);
        assertThat(testLedgerAccounts.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testLedgerAccounts.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testLedgerAccounts.getAccBalance()).isEqualTo(UPDATED_ACC_BALANCE);
        assertThat(testLedgerAccounts.getAccHeadCode()).isEqualTo(UPDATED_ACC_HEAD_CODE);
        assertThat(testLedgerAccounts.getLedgerCode()).isEqualTo(UPDATED_LEDGER_CODE);
        assertThat(testLedgerAccounts.getAppCode()).isEqualTo(UPDATED_APP_CODE);
        assertThat(testLedgerAccounts.getLedgerClassification()).isEqualTo(UPDATED_LEDGER_CLASSIFICATION);
        assertThat(testLedgerAccounts.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testLedgerAccounts.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLedgerAccounts.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testLedgerAccounts.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLedgerAccounts.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLedgerAccounts.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLedgerAccounts.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLedgerAccounts.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testLedgerAccounts.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLedgerAccounts.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLedgerAccounts.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testLedgerAccounts.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testLedgerAccounts.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void putNonExistingLedgerAccounts() throws Exception {
        int databaseSizeBeforeUpdate = ledgerAccountsRepository.findAll().size();
        ledgerAccounts.setId(count.incrementAndGet());

        // Create the LedgerAccounts
        LedgerAccountsDTO ledgerAccountsDTO = ledgerAccountsMapper.toDto(ledgerAccounts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLedgerAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ledgerAccountsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ledgerAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LedgerAccounts in the database
        List<LedgerAccounts> ledgerAccountsList = ledgerAccountsRepository.findAll();
        assertThat(ledgerAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLedgerAccounts() throws Exception {
        int databaseSizeBeforeUpdate = ledgerAccountsRepository.findAll().size();
        ledgerAccounts.setId(count.incrementAndGet());

        // Create the LedgerAccounts
        LedgerAccountsDTO ledgerAccountsDTO = ledgerAccountsMapper.toDto(ledgerAccounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLedgerAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ledgerAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LedgerAccounts in the database
        List<LedgerAccounts> ledgerAccountsList = ledgerAccountsRepository.findAll();
        assertThat(ledgerAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLedgerAccounts() throws Exception {
        int databaseSizeBeforeUpdate = ledgerAccountsRepository.findAll().size();
        ledgerAccounts.setId(count.incrementAndGet());

        // Create the LedgerAccounts
        LedgerAccountsDTO ledgerAccountsDTO = ledgerAccountsMapper.toDto(ledgerAccounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLedgerAccountsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ledgerAccountsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LedgerAccounts in the database
        List<LedgerAccounts> ledgerAccountsList = ledgerAccountsRepository.findAll();
        assertThat(ledgerAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLedgerAccountsWithPatch() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        int databaseSizeBeforeUpdate = ledgerAccountsRepository.findAll().size();

        // Update the ledgerAccounts using partial update
        LedgerAccounts partialUpdatedLedgerAccounts = new LedgerAccounts();
        partialUpdatedLedgerAccounts.setId(ledgerAccounts.getId());

        partialUpdatedLedgerAccounts
            .accountNo(UPDATED_ACCOUNT_NO)
            .accBalance(UPDATED_ACC_BALANCE)
            .accHeadCode(UPDATED_ACC_HEAD_CODE)
            .level(UPDATED_LEVEL)
            .year(UPDATED_YEAR)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);

        restLedgerAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLedgerAccounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLedgerAccounts))
            )
            .andExpect(status().isOk());

        // Validate the LedgerAccounts in the database
        List<LedgerAccounts> ledgerAccountsList = ledgerAccountsRepository.findAll();
        assertThat(ledgerAccountsList).hasSize(databaseSizeBeforeUpdate);
        LedgerAccounts testLedgerAccounts = ledgerAccountsList.get(ledgerAccountsList.size() - 1);
        assertThat(testLedgerAccounts.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testLedgerAccounts.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testLedgerAccounts.getAccBalance()).isEqualTo(UPDATED_ACC_BALANCE);
        assertThat(testLedgerAccounts.getAccHeadCode()).isEqualTo(UPDATED_ACC_HEAD_CODE);
        assertThat(testLedgerAccounts.getLedgerCode()).isEqualTo(DEFAULT_LEDGER_CODE);
        assertThat(testLedgerAccounts.getAppCode()).isEqualTo(DEFAULT_APP_CODE);
        assertThat(testLedgerAccounts.getLedgerClassification()).isEqualTo(DEFAULT_LEDGER_CLASSIFICATION);
        assertThat(testLedgerAccounts.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testLedgerAccounts.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLedgerAccounts.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testLedgerAccounts.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLedgerAccounts.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLedgerAccounts.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLedgerAccounts.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testLedgerAccounts.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testLedgerAccounts.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLedgerAccounts.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLedgerAccounts.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testLedgerAccounts.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testLedgerAccounts.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void fullUpdateLedgerAccountsWithPatch() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        int databaseSizeBeforeUpdate = ledgerAccountsRepository.findAll().size();

        // Update the ledgerAccounts using partial update
        LedgerAccounts partialUpdatedLedgerAccounts = new LedgerAccounts();
        partialUpdatedLedgerAccounts.setId(ledgerAccounts.getId());

        partialUpdatedLedgerAccounts
            .accountNo(UPDATED_ACCOUNT_NO)
            .accountName(UPDATED_ACCOUNT_NAME)
            .accBalance(UPDATED_ACC_BALANCE)
            .accHeadCode(UPDATED_ACC_HEAD_CODE)
            .ledgerCode(UPDATED_LEDGER_CODE)
            .appCode(UPDATED_APP_CODE)
            .ledgerClassification(UPDATED_LEDGER_CLASSIFICATION)
            .level(UPDATED_LEVEL)
            .year(UPDATED_YEAR)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);

        restLedgerAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLedgerAccounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLedgerAccounts))
            )
            .andExpect(status().isOk());

        // Validate the LedgerAccounts in the database
        List<LedgerAccounts> ledgerAccountsList = ledgerAccountsRepository.findAll();
        assertThat(ledgerAccountsList).hasSize(databaseSizeBeforeUpdate);
        LedgerAccounts testLedgerAccounts = ledgerAccountsList.get(ledgerAccountsList.size() - 1);
        assertThat(testLedgerAccounts.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testLedgerAccounts.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testLedgerAccounts.getAccBalance()).isEqualTo(UPDATED_ACC_BALANCE);
        assertThat(testLedgerAccounts.getAccHeadCode()).isEqualTo(UPDATED_ACC_HEAD_CODE);
        assertThat(testLedgerAccounts.getLedgerCode()).isEqualTo(UPDATED_LEDGER_CODE);
        assertThat(testLedgerAccounts.getAppCode()).isEqualTo(UPDATED_APP_CODE);
        assertThat(testLedgerAccounts.getLedgerClassification()).isEqualTo(UPDATED_LEDGER_CLASSIFICATION);
        assertThat(testLedgerAccounts.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testLedgerAccounts.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testLedgerAccounts.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testLedgerAccounts.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLedgerAccounts.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLedgerAccounts.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLedgerAccounts.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLedgerAccounts.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testLedgerAccounts.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLedgerAccounts.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLedgerAccounts.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testLedgerAccounts.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testLedgerAccounts.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void patchNonExistingLedgerAccounts() throws Exception {
        int databaseSizeBeforeUpdate = ledgerAccountsRepository.findAll().size();
        ledgerAccounts.setId(count.incrementAndGet());

        // Create the LedgerAccounts
        LedgerAccountsDTO ledgerAccountsDTO = ledgerAccountsMapper.toDto(ledgerAccounts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLedgerAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ledgerAccountsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ledgerAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LedgerAccounts in the database
        List<LedgerAccounts> ledgerAccountsList = ledgerAccountsRepository.findAll();
        assertThat(ledgerAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLedgerAccounts() throws Exception {
        int databaseSizeBeforeUpdate = ledgerAccountsRepository.findAll().size();
        ledgerAccounts.setId(count.incrementAndGet());

        // Create the LedgerAccounts
        LedgerAccountsDTO ledgerAccountsDTO = ledgerAccountsMapper.toDto(ledgerAccounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLedgerAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ledgerAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LedgerAccounts in the database
        List<LedgerAccounts> ledgerAccountsList = ledgerAccountsRepository.findAll();
        assertThat(ledgerAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLedgerAccounts() throws Exception {
        int databaseSizeBeforeUpdate = ledgerAccountsRepository.findAll().size();
        ledgerAccounts.setId(count.incrementAndGet());

        // Create the LedgerAccounts
        LedgerAccountsDTO ledgerAccountsDTO = ledgerAccountsMapper.toDto(ledgerAccounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLedgerAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ledgerAccountsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LedgerAccounts in the database
        List<LedgerAccounts> ledgerAccountsList = ledgerAccountsRepository.findAll();
        assertThat(ledgerAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLedgerAccounts() throws Exception {
        // Initialize the database
        ledgerAccountsRepository.saveAndFlush(ledgerAccounts);

        int databaseSizeBeforeDelete = ledgerAccountsRepository.findAll().size();

        // Delete the ledgerAccounts
        restLedgerAccountsMockMvc
            .perform(delete(ENTITY_API_URL_ID, ledgerAccounts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LedgerAccounts> ledgerAccountsList = ledgerAccountsRepository.findAll();
        assertThat(ledgerAccountsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
