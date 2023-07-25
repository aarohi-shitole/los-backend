package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.IncomeDetails;
import com.techvg.los.domain.Member;
import com.techvg.los.domain.enumeration.IncomeType;
import com.techvg.los.repository.IncomeDetailsRepository;
import com.techvg.los.service.criteria.IncomeDetailsCriteria;
import com.techvg.los.service.dto.IncomeDetailsDTO;
import com.techvg.los.service.mapper.IncomeDetailsMapper;
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
 * Integration tests for the {@link IncomeDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IncomeDetailsResourceIT {

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final Double DEFAULT_GROSS_INCOME = 1D;
    private static final Double UPDATED_GROSS_INCOME = 2D;
    private static final Double SMALLER_GROSS_INCOME = 1D - 1D;

    private static final Double DEFAULT_EXPENSES = 1D;
    private static final Double UPDATED_EXPENSES = 2D;
    private static final Double SMALLER_EXPENSES = 1D - 1D;

    private static final Double DEFAULT_NET_INCOME = 1D;
    private static final Double UPDATED_NET_INCOME = 2D;
    private static final Double SMALLER_NET_INCOME = 1D - 1D;

    private static final Double DEFAULT_PAID_TAXES = 1D;
    private static final Double UPDATED_PAID_TAXES = 2D;
    private static final Double SMALLER_PAID_TAXES = 1D - 1D;

    private static final Double DEFAULT_CASH_SURPLUS = 1D;
    private static final Double UPDATED_CASH_SURPLUS = 2D;
    private static final Double SMALLER_CASH_SURPLUS = 1D - 1D;

    private static final IncomeType DEFAULT_INCOME_TYPE = IncomeType.PRINCIPAL_SOURCE;
    private static final IncomeType UPDATED_INCOME_TYPE = IncomeType.OTHER_SOURCE;

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/income-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IncomeDetailsRepository incomeDetailsRepository;

    @Autowired
    private IncomeDetailsMapper incomeDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncomeDetailsMockMvc;

    private IncomeDetails incomeDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomeDetails createEntity(EntityManager em) {
        IncomeDetails incomeDetails = new IncomeDetails()
            .year(DEFAULT_YEAR)
            .grossIncome(DEFAULT_GROSS_INCOME)
            .expenses(DEFAULT_EXPENSES)
            .netIncome(DEFAULT_NET_INCOME)
            .paidTaxes(DEFAULT_PAID_TAXES)
            .cashSurplus(DEFAULT_CASH_SURPLUS)
            .incomeType(DEFAULT_INCOME_TYPE)
            .isDeleted(DEFAULT_IS_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .otherType(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5)
            .freeField6(DEFAULT_FREE_FIELD_6);
        return incomeDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncomeDetails createUpdatedEntity(EntityManager em) {
        IncomeDetails incomeDetails = new IncomeDetails()
            .year(UPDATED_YEAR)
            .grossIncome(UPDATED_GROSS_INCOME)
            .expenses(UPDATED_EXPENSES)
            .netIncome(UPDATED_NET_INCOME)
            .paidTaxes(UPDATED_PAID_TAXES)
            .cashSurplus(UPDATED_CASH_SURPLUS)
            .incomeType(UPDATED_INCOME_TYPE)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .otherType(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        return incomeDetails;
    }

    @BeforeEach
    public void initTest() {
        incomeDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createIncomeDetails() throws Exception {
        int databaseSizeBeforeCreate = incomeDetailsRepository.findAll().size();
        // Create the IncomeDetails
        IncomeDetailsDTO incomeDetailsDTO = incomeDetailsMapper.toDto(incomeDetails);
        restIncomeDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(incomeDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IncomeDetails in the database
        List<IncomeDetails> incomeDetailsList = incomeDetailsRepository.findAll();
        assertThat(incomeDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        IncomeDetails testIncomeDetails = incomeDetailsList.get(incomeDetailsList.size() - 1);
        assertThat(testIncomeDetails.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testIncomeDetails.getGrossIncome()).isEqualTo(DEFAULT_GROSS_INCOME);
        assertThat(testIncomeDetails.getExpenses()).isEqualTo(DEFAULT_EXPENSES);
        assertThat(testIncomeDetails.getNetIncome()).isEqualTo(DEFAULT_NET_INCOME);
        assertThat(testIncomeDetails.getPaidTaxes()).isEqualTo(DEFAULT_PAID_TAXES);
        assertThat(testIncomeDetails.getCashSurplus()).isEqualTo(DEFAULT_CASH_SURPLUS);
        assertThat(testIncomeDetails.getIncomeType()).isEqualTo(DEFAULT_INCOME_TYPE);
        assertThat(testIncomeDetails.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testIncomeDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testIncomeDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testIncomeDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testIncomeDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testIncomeDetails.getOtherType()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testIncomeDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testIncomeDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testIncomeDetails.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testIncomeDetails.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testIncomeDetails.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createIncomeDetailsWithExistingId() throws Exception {
        // Create the IncomeDetails with an existing ID
        incomeDetails.setId(1L);
        IncomeDetailsDTO incomeDetailsDTO = incomeDetailsMapper.toDto(incomeDetails);

        int databaseSizeBeforeCreate = incomeDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncomeDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(incomeDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncomeDetails in the database
        List<IncomeDetails> incomeDetailsList = incomeDetailsRepository.findAll();
        assertThat(incomeDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIncomeDetails() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList
        restIncomeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomeDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].grossIncome").value(hasItem(DEFAULT_GROSS_INCOME.doubleValue())))
            .andExpect(jsonPath("$.[*].expenses").value(hasItem(DEFAULT_EXPENSES.doubleValue())))
            .andExpect(jsonPath("$.[*].netIncome").value(hasItem(DEFAULT_NET_INCOME.doubleValue())))
            .andExpect(jsonPath("$.[*].paidTaxes").value(hasItem(DEFAULT_PAID_TAXES.doubleValue())))
            .andExpect(jsonPath("$.[*].cashSurplus").value(hasItem(DEFAULT_CASH_SURPLUS.doubleValue())))
            .andExpect(jsonPath("$.[*].incomeType").value(hasItem(DEFAULT_INCOME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].otherType").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));
    }

    @Test
    @Transactional
    void getIncomeDetails() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get the incomeDetails
        restIncomeDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, incomeDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incomeDetails.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.grossIncome").value(DEFAULT_GROSS_INCOME.doubleValue()))
            .andExpect(jsonPath("$.expenses").value(DEFAULT_EXPENSES.doubleValue()))
            .andExpect(jsonPath("$.netIncome").value(DEFAULT_NET_INCOME.doubleValue()))
            .andExpect(jsonPath("$.paidTaxes").value(DEFAULT_PAID_TAXES.doubleValue()))
            .andExpect(jsonPath("$.cashSurplus").value(DEFAULT_CASH_SURPLUS.doubleValue()))
            .andExpect(jsonPath("$.incomeType").value(DEFAULT_INCOME_TYPE.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.otherType").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.freeField5").value(DEFAULT_FREE_FIELD_5))
            .andExpect(jsonPath("$.freeField6").value(DEFAULT_FREE_FIELD_6));
    }

    @Test
    @Transactional
    void getIncomeDetailsByIdFiltering() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        Long id = incomeDetails.getId();

        defaultIncomeDetailsShouldBeFound("id.equals=" + id);
        defaultIncomeDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultIncomeDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultIncomeDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultIncomeDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultIncomeDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where year equals to DEFAULT_YEAR
        defaultIncomeDetailsShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the incomeDetailsList where year equals to UPDATED_YEAR
        defaultIncomeDetailsShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultIncomeDetailsShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the incomeDetailsList where year equals to UPDATED_YEAR
        defaultIncomeDetailsShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where year is not null
        defaultIncomeDetailsShouldBeFound("year.specified=true");

        // Get all the incomeDetailsList where year is null
        defaultIncomeDetailsShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByYearContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where year contains DEFAULT_YEAR
        defaultIncomeDetailsShouldBeFound("year.contains=" + DEFAULT_YEAR);

        // Get all the incomeDetailsList where year contains UPDATED_YEAR
        defaultIncomeDetailsShouldNotBeFound("year.contains=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByYearNotContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where year does not contain DEFAULT_YEAR
        defaultIncomeDetailsShouldNotBeFound("year.doesNotContain=" + DEFAULT_YEAR);

        // Get all the incomeDetailsList where year does not contain UPDATED_YEAR
        defaultIncomeDetailsShouldBeFound("year.doesNotContain=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByGrossIncomeIsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where grossIncome equals to DEFAULT_GROSS_INCOME
        defaultIncomeDetailsShouldBeFound("grossIncome.equals=" + DEFAULT_GROSS_INCOME);

        // Get all the incomeDetailsList where grossIncome equals to UPDATED_GROSS_INCOME
        defaultIncomeDetailsShouldNotBeFound("grossIncome.equals=" + UPDATED_GROSS_INCOME);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByGrossIncomeIsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where grossIncome in DEFAULT_GROSS_INCOME or UPDATED_GROSS_INCOME
        defaultIncomeDetailsShouldBeFound("grossIncome.in=" + DEFAULT_GROSS_INCOME + "," + UPDATED_GROSS_INCOME);

        // Get all the incomeDetailsList where grossIncome equals to UPDATED_GROSS_INCOME
        defaultIncomeDetailsShouldNotBeFound("grossIncome.in=" + UPDATED_GROSS_INCOME);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByGrossIncomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where grossIncome is not null
        defaultIncomeDetailsShouldBeFound("grossIncome.specified=true");

        // Get all the incomeDetailsList where grossIncome is null
        defaultIncomeDetailsShouldNotBeFound("grossIncome.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByGrossIncomeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where grossIncome is greater than or equal to DEFAULT_GROSS_INCOME
        defaultIncomeDetailsShouldBeFound("grossIncome.greaterThanOrEqual=" + DEFAULT_GROSS_INCOME);

        // Get all the incomeDetailsList where grossIncome is greater than or equal to UPDATED_GROSS_INCOME
        defaultIncomeDetailsShouldNotBeFound("grossIncome.greaterThanOrEqual=" + UPDATED_GROSS_INCOME);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByGrossIncomeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where grossIncome is less than or equal to DEFAULT_GROSS_INCOME
        defaultIncomeDetailsShouldBeFound("grossIncome.lessThanOrEqual=" + DEFAULT_GROSS_INCOME);

        // Get all the incomeDetailsList where grossIncome is less than or equal to SMALLER_GROSS_INCOME
        defaultIncomeDetailsShouldNotBeFound("grossIncome.lessThanOrEqual=" + SMALLER_GROSS_INCOME);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByGrossIncomeIsLessThanSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where grossIncome is less than DEFAULT_GROSS_INCOME
        defaultIncomeDetailsShouldNotBeFound("grossIncome.lessThan=" + DEFAULT_GROSS_INCOME);

        // Get all the incomeDetailsList where grossIncome is less than UPDATED_GROSS_INCOME
        defaultIncomeDetailsShouldBeFound("grossIncome.lessThan=" + UPDATED_GROSS_INCOME);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByGrossIncomeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where grossIncome is greater than DEFAULT_GROSS_INCOME
        defaultIncomeDetailsShouldNotBeFound("grossIncome.greaterThan=" + DEFAULT_GROSS_INCOME);

        // Get all the incomeDetailsList where grossIncome is greater than SMALLER_GROSS_INCOME
        defaultIncomeDetailsShouldBeFound("grossIncome.greaterThan=" + SMALLER_GROSS_INCOME);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByExpensesIsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where expenses equals to DEFAULT_EXPENSES
        defaultIncomeDetailsShouldBeFound("expenses.equals=" + DEFAULT_EXPENSES);

        // Get all the incomeDetailsList where expenses equals to UPDATED_EXPENSES
        defaultIncomeDetailsShouldNotBeFound("expenses.equals=" + UPDATED_EXPENSES);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByExpensesIsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where expenses in DEFAULT_EXPENSES or UPDATED_EXPENSES
        defaultIncomeDetailsShouldBeFound("expenses.in=" + DEFAULT_EXPENSES + "," + UPDATED_EXPENSES);

        // Get all the incomeDetailsList where expenses equals to UPDATED_EXPENSES
        defaultIncomeDetailsShouldNotBeFound("expenses.in=" + UPDATED_EXPENSES);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByExpensesIsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where expenses is not null
        defaultIncomeDetailsShouldBeFound("expenses.specified=true");

        // Get all the incomeDetailsList where expenses is null
        defaultIncomeDetailsShouldNotBeFound("expenses.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByExpensesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where expenses is greater than or equal to DEFAULT_EXPENSES
        defaultIncomeDetailsShouldBeFound("expenses.greaterThanOrEqual=" + DEFAULT_EXPENSES);

        // Get all the incomeDetailsList where expenses is greater than or equal to UPDATED_EXPENSES
        defaultIncomeDetailsShouldNotBeFound("expenses.greaterThanOrEqual=" + UPDATED_EXPENSES);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByExpensesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where expenses is less than or equal to DEFAULT_EXPENSES
        defaultIncomeDetailsShouldBeFound("expenses.lessThanOrEqual=" + DEFAULT_EXPENSES);

        // Get all the incomeDetailsList where expenses is less than or equal to SMALLER_EXPENSES
        defaultIncomeDetailsShouldNotBeFound("expenses.lessThanOrEqual=" + SMALLER_EXPENSES);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByExpensesIsLessThanSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where expenses is less than DEFAULT_EXPENSES
        defaultIncomeDetailsShouldNotBeFound("expenses.lessThan=" + DEFAULT_EXPENSES);

        // Get all the incomeDetailsList where expenses is less than UPDATED_EXPENSES
        defaultIncomeDetailsShouldBeFound("expenses.lessThan=" + UPDATED_EXPENSES);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByExpensesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where expenses is greater than DEFAULT_EXPENSES
        defaultIncomeDetailsShouldNotBeFound("expenses.greaterThan=" + DEFAULT_EXPENSES);

        // Get all the incomeDetailsList where expenses is greater than SMALLER_EXPENSES
        defaultIncomeDetailsShouldBeFound("expenses.greaterThan=" + SMALLER_EXPENSES);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByNetIncomeIsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where netIncome equals to DEFAULT_NET_INCOME
        defaultIncomeDetailsShouldBeFound("netIncome.equals=" + DEFAULT_NET_INCOME);

        // Get all the incomeDetailsList where netIncome equals to UPDATED_NET_INCOME
        defaultIncomeDetailsShouldNotBeFound("netIncome.equals=" + UPDATED_NET_INCOME);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByNetIncomeIsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where netIncome in DEFAULT_NET_INCOME or UPDATED_NET_INCOME
        defaultIncomeDetailsShouldBeFound("netIncome.in=" + DEFAULT_NET_INCOME + "," + UPDATED_NET_INCOME);

        // Get all the incomeDetailsList where netIncome equals to UPDATED_NET_INCOME
        defaultIncomeDetailsShouldNotBeFound("netIncome.in=" + UPDATED_NET_INCOME);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByNetIncomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where netIncome is not null
        defaultIncomeDetailsShouldBeFound("netIncome.specified=true");

        // Get all the incomeDetailsList where netIncome is null
        defaultIncomeDetailsShouldNotBeFound("netIncome.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByNetIncomeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where netIncome is greater than or equal to DEFAULT_NET_INCOME
        defaultIncomeDetailsShouldBeFound("netIncome.greaterThanOrEqual=" + DEFAULT_NET_INCOME);

        // Get all the incomeDetailsList where netIncome is greater than or equal to UPDATED_NET_INCOME
        defaultIncomeDetailsShouldNotBeFound("netIncome.greaterThanOrEqual=" + UPDATED_NET_INCOME);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByNetIncomeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where netIncome is less than or equal to DEFAULT_NET_INCOME
        defaultIncomeDetailsShouldBeFound("netIncome.lessThanOrEqual=" + DEFAULT_NET_INCOME);

        // Get all the incomeDetailsList where netIncome is less than or equal to SMALLER_NET_INCOME
        defaultIncomeDetailsShouldNotBeFound("netIncome.lessThanOrEqual=" + SMALLER_NET_INCOME);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByNetIncomeIsLessThanSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where netIncome is less than DEFAULT_NET_INCOME
        defaultIncomeDetailsShouldNotBeFound("netIncome.lessThan=" + DEFAULT_NET_INCOME);

        // Get all the incomeDetailsList where netIncome is less than UPDATED_NET_INCOME
        defaultIncomeDetailsShouldBeFound("netIncome.lessThan=" + UPDATED_NET_INCOME);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByNetIncomeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where netIncome is greater than DEFAULT_NET_INCOME
        defaultIncomeDetailsShouldNotBeFound("netIncome.greaterThan=" + DEFAULT_NET_INCOME);

        // Get all the incomeDetailsList where netIncome is greater than SMALLER_NET_INCOME
        defaultIncomeDetailsShouldBeFound("netIncome.greaterThan=" + SMALLER_NET_INCOME);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByPaidTaxesIsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where paidTaxes equals to DEFAULT_PAID_TAXES
        defaultIncomeDetailsShouldBeFound("paidTaxes.equals=" + DEFAULT_PAID_TAXES);

        // Get all the incomeDetailsList where paidTaxes equals to UPDATED_PAID_TAXES
        defaultIncomeDetailsShouldNotBeFound("paidTaxes.equals=" + UPDATED_PAID_TAXES);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByPaidTaxesIsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where paidTaxes in DEFAULT_PAID_TAXES or UPDATED_PAID_TAXES
        defaultIncomeDetailsShouldBeFound("paidTaxes.in=" + DEFAULT_PAID_TAXES + "," + UPDATED_PAID_TAXES);

        // Get all the incomeDetailsList where paidTaxes equals to UPDATED_PAID_TAXES
        defaultIncomeDetailsShouldNotBeFound("paidTaxes.in=" + UPDATED_PAID_TAXES);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByPaidTaxesIsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where paidTaxes is not null
        defaultIncomeDetailsShouldBeFound("paidTaxes.specified=true");

        // Get all the incomeDetailsList where paidTaxes is null
        defaultIncomeDetailsShouldNotBeFound("paidTaxes.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByPaidTaxesIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where paidTaxes is greater than or equal to DEFAULT_PAID_TAXES
        defaultIncomeDetailsShouldBeFound("paidTaxes.greaterThanOrEqual=" + DEFAULT_PAID_TAXES);

        // Get all the incomeDetailsList where paidTaxes is greater than or equal to UPDATED_PAID_TAXES
        defaultIncomeDetailsShouldNotBeFound("paidTaxes.greaterThanOrEqual=" + UPDATED_PAID_TAXES);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByPaidTaxesIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where paidTaxes is less than or equal to DEFAULT_PAID_TAXES
        defaultIncomeDetailsShouldBeFound("paidTaxes.lessThanOrEqual=" + DEFAULT_PAID_TAXES);

        // Get all the incomeDetailsList where paidTaxes is less than or equal to SMALLER_PAID_TAXES
        defaultIncomeDetailsShouldNotBeFound("paidTaxes.lessThanOrEqual=" + SMALLER_PAID_TAXES);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByPaidTaxesIsLessThanSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where paidTaxes is less than DEFAULT_PAID_TAXES
        defaultIncomeDetailsShouldNotBeFound("paidTaxes.lessThan=" + DEFAULT_PAID_TAXES);

        // Get all the incomeDetailsList where paidTaxes is less than UPDATED_PAID_TAXES
        defaultIncomeDetailsShouldBeFound("paidTaxes.lessThan=" + UPDATED_PAID_TAXES);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByPaidTaxesIsGreaterThanSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where paidTaxes is greater than DEFAULT_PAID_TAXES
        defaultIncomeDetailsShouldNotBeFound("paidTaxes.greaterThan=" + DEFAULT_PAID_TAXES);

        // Get all the incomeDetailsList where paidTaxes is greater than SMALLER_PAID_TAXES
        defaultIncomeDetailsShouldBeFound("paidTaxes.greaterThan=" + SMALLER_PAID_TAXES);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByCashSurplusIsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where cashSurplus equals to DEFAULT_CASH_SURPLUS
        defaultIncomeDetailsShouldBeFound("cashSurplus.equals=" + DEFAULT_CASH_SURPLUS);

        // Get all the incomeDetailsList where cashSurplus equals to UPDATED_CASH_SURPLUS
        defaultIncomeDetailsShouldNotBeFound("cashSurplus.equals=" + UPDATED_CASH_SURPLUS);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByCashSurplusIsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where cashSurplus in DEFAULT_CASH_SURPLUS or UPDATED_CASH_SURPLUS
        defaultIncomeDetailsShouldBeFound("cashSurplus.in=" + DEFAULT_CASH_SURPLUS + "," + UPDATED_CASH_SURPLUS);

        // Get all the incomeDetailsList where cashSurplus equals to UPDATED_CASH_SURPLUS
        defaultIncomeDetailsShouldNotBeFound("cashSurplus.in=" + UPDATED_CASH_SURPLUS);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByCashSurplusIsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where cashSurplus is not null
        defaultIncomeDetailsShouldBeFound("cashSurplus.specified=true");

        // Get all the incomeDetailsList where cashSurplus is null
        defaultIncomeDetailsShouldNotBeFound("cashSurplus.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByCashSurplusIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where cashSurplus is greater than or equal to DEFAULT_CASH_SURPLUS
        defaultIncomeDetailsShouldBeFound("cashSurplus.greaterThanOrEqual=" + DEFAULT_CASH_SURPLUS);

        // Get all the incomeDetailsList where cashSurplus is greater than or equal to UPDATED_CASH_SURPLUS
        defaultIncomeDetailsShouldNotBeFound("cashSurplus.greaterThanOrEqual=" + UPDATED_CASH_SURPLUS);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByCashSurplusIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where cashSurplus is less than or equal to DEFAULT_CASH_SURPLUS
        defaultIncomeDetailsShouldBeFound("cashSurplus.lessThanOrEqual=" + DEFAULT_CASH_SURPLUS);

        // Get all the incomeDetailsList where cashSurplus is less than or equal to SMALLER_CASH_SURPLUS
        defaultIncomeDetailsShouldNotBeFound("cashSurplus.lessThanOrEqual=" + SMALLER_CASH_SURPLUS);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByCashSurplusIsLessThanSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where cashSurplus is less than DEFAULT_CASH_SURPLUS
        defaultIncomeDetailsShouldNotBeFound("cashSurplus.lessThan=" + DEFAULT_CASH_SURPLUS);

        // Get all the incomeDetailsList where cashSurplus is less than UPDATED_CASH_SURPLUS
        defaultIncomeDetailsShouldBeFound("cashSurplus.lessThan=" + UPDATED_CASH_SURPLUS);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByCashSurplusIsGreaterThanSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where cashSurplus is greater than DEFAULT_CASH_SURPLUS
        defaultIncomeDetailsShouldNotBeFound("cashSurplus.greaterThan=" + DEFAULT_CASH_SURPLUS);

        // Get all the incomeDetailsList where cashSurplus is greater than SMALLER_CASH_SURPLUS
        defaultIncomeDetailsShouldBeFound("cashSurplus.greaterThan=" + SMALLER_CASH_SURPLUS);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByIncomeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where incomeType equals to DEFAULT_INCOME_TYPE
        defaultIncomeDetailsShouldBeFound("incomeType.equals=" + DEFAULT_INCOME_TYPE);

        // Get all the incomeDetailsList where incomeType equals to UPDATED_INCOME_TYPE
        defaultIncomeDetailsShouldNotBeFound("incomeType.equals=" + UPDATED_INCOME_TYPE);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByIncomeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where incomeType in DEFAULT_INCOME_TYPE or UPDATED_INCOME_TYPE
        defaultIncomeDetailsShouldBeFound("incomeType.in=" + DEFAULT_INCOME_TYPE + "," + UPDATED_INCOME_TYPE);

        // Get all the incomeDetailsList where incomeType equals to UPDATED_INCOME_TYPE
        defaultIncomeDetailsShouldNotBeFound("incomeType.in=" + UPDATED_INCOME_TYPE);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByIncomeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where incomeType is not null
        defaultIncomeDetailsShouldBeFound("incomeType.specified=true");

        // Get all the incomeDetailsList where incomeType is null
        defaultIncomeDetailsShouldNotBeFound("incomeType.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultIncomeDetailsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the incomeDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultIncomeDetailsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultIncomeDetailsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the incomeDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultIncomeDetailsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where isDeleted is not null
        defaultIncomeDetailsShouldBeFound("isDeleted.specified=true");

        // Get all the incomeDetailsList where isDeleted is null
        defaultIncomeDetailsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultIncomeDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the incomeDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultIncomeDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultIncomeDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the incomeDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultIncomeDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where lastModified is not null
        defaultIncomeDetailsShouldBeFound("lastModified.specified=true");

        // Get all the incomeDetailsList where lastModified is null
        defaultIncomeDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultIncomeDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the incomeDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultIncomeDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultIncomeDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the incomeDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultIncomeDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where lastModifiedBy is not null
        defaultIncomeDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the incomeDetailsList where lastModifiedBy is null
        defaultIncomeDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultIncomeDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the incomeDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultIncomeDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultIncomeDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the incomeDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultIncomeDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where createdBy equals to DEFAULT_CREATED_BY
        defaultIncomeDetailsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the incomeDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultIncomeDetailsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultIncomeDetailsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the incomeDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultIncomeDetailsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where createdBy is not null
        defaultIncomeDetailsShouldBeFound("createdBy.specified=true");

        // Get all the incomeDetailsList where createdBy is null
        defaultIncomeDetailsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where createdBy contains DEFAULT_CREATED_BY
        defaultIncomeDetailsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the incomeDetailsList where createdBy contains UPDATED_CREATED_BY
        defaultIncomeDetailsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultIncomeDetailsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the incomeDetailsList where createdBy does not contain UPDATED_CREATED_BY
        defaultIncomeDetailsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where createdOn equals to DEFAULT_CREATED_ON
        defaultIncomeDetailsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the incomeDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultIncomeDetailsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultIncomeDetailsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the incomeDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultIncomeDetailsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where createdOn is not null
        defaultIncomeDetailsShouldBeFound("createdOn.specified=true");

        // Get all the incomeDetailsList where createdOn is null
        defaultIncomeDetailsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByOtherTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where otherType equals to DEFAULT_FREE_FIELD_1
        defaultIncomeDetailsShouldBeFound("otherType.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the incomeDetailsList where otherType equals to UPDATED_FREE_FIELD_1
        defaultIncomeDetailsShouldNotBeFound("otherType.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByOtherTypeIsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where otherType in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultIncomeDetailsShouldBeFound("otherType.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the incomeDetailsList where otherType equals to UPDATED_FREE_FIELD_1
        defaultIncomeDetailsShouldNotBeFound("otherType.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByOtherTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where otherType is not null
        defaultIncomeDetailsShouldBeFound("otherType.specified=true");

        // Get all the incomeDetailsList where otherType is null
        defaultIncomeDetailsShouldNotBeFound("otherType.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByOtherTypeContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where otherType contains DEFAULT_FREE_FIELD_1
        defaultIncomeDetailsShouldBeFound("otherType.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the incomeDetailsList where otherType contains UPDATED_FREE_FIELD_1
        defaultIncomeDetailsShouldNotBeFound("otherType.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByOtherTypeNotContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where otherType does not contain DEFAULT_FREE_FIELD_1
        defaultIncomeDetailsShouldNotBeFound("otherType.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the incomeDetailsList where otherType does not contain UPDATED_FREE_FIELD_1
        defaultIncomeDetailsShouldBeFound("otherType.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultIncomeDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the incomeDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultIncomeDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultIncomeDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the incomeDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultIncomeDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField2 is not null
        defaultIncomeDetailsShouldBeFound("freeField2.specified=true");

        // Get all the incomeDetailsList where freeField2 is null
        defaultIncomeDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultIncomeDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the incomeDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultIncomeDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultIncomeDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the incomeDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultIncomeDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultIncomeDetailsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the incomeDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultIncomeDetailsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultIncomeDetailsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the incomeDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultIncomeDetailsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField3 is not null
        defaultIncomeDetailsShouldBeFound("freeField3.specified=true");

        // Get all the incomeDetailsList where freeField3 is null
        defaultIncomeDetailsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultIncomeDetailsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the incomeDetailsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultIncomeDetailsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultIncomeDetailsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the incomeDetailsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultIncomeDetailsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultIncomeDetailsShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the incomeDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultIncomeDetailsShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultIncomeDetailsShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the incomeDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultIncomeDetailsShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField4 is not null
        defaultIncomeDetailsShouldBeFound("freeField4.specified=true");

        // Get all the incomeDetailsList where freeField4 is null
        defaultIncomeDetailsShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultIncomeDetailsShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the incomeDetailsList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultIncomeDetailsShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultIncomeDetailsShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the incomeDetailsList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultIncomeDetailsShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultIncomeDetailsShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the incomeDetailsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultIncomeDetailsShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultIncomeDetailsShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the incomeDetailsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultIncomeDetailsShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField5 is not null
        defaultIncomeDetailsShouldBeFound("freeField5.specified=true");

        // Get all the incomeDetailsList where freeField5 is null
        defaultIncomeDetailsShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultIncomeDetailsShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the incomeDetailsList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultIncomeDetailsShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultIncomeDetailsShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the incomeDetailsList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultIncomeDetailsShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultIncomeDetailsShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the incomeDetailsList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultIncomeDetailsShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultIncomeDetailsShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the incomeDetailsList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultIncomeDetailsShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField6 is not null
        defaultIncomeDetailsShouldBeFound("freeField6.specified=true");

        // Get all the incomeDetailsList where freeField6 is null
        defaultIncomeDetailsShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultIncomeDetailsShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the incomeDetailsList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultIncomeDetailsShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        // Get all the incomeDetailsList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultIncomeDetailsShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the incomeDetailsList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultIncomeDetailsShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllIncomeDetailsByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            incomeDetailsRepository.saveAndFlush(incomeDetails);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        incomeDetails.setMember(member);
        incomeDetailsRepository.saveAndFlush(incomeDetails);
        Long memberId = member.getId();

        // Get all the incomeDetailsList where member equals to memberId
        defaultIncomeDetailsShouldBeFound("memberId.equals=" + memberId);

        // Get all the incomeDetailsList where member equals to (memberId + 1)
        defaultIncomeDetailsShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultIncomeDetailsShouldBeFound(String filter) throws Exception {
        restIncomeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incomeDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].grossIncome").value(hasItem(DEFAULT_GROSS_INCOME.doubleValue())))
            .andExpect(jsonPath("$.[*].expenses").value(hasItem(DEFAULT_EXPENSES.doubleValue())))
            .andExpect(jsonPath("$.[*].netIncome").value(hasItem(DEFAULT_NET_INCOME.doubleValue())))
            .andExpect(jsonPath("$.[*].paidTaxes").value(hasItem(DEFAULT_PAID_TAXES.doubleValue())))
            .andExpect(jsonPath("$.[*].cashSurplus").value(hasItem(DEFAULT_CASH_SURPLUS.doubleValue())))
            .andExpect(jsonPath("$.[*].incomeType").value(hasItem(DEFAULT_INCOME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].otherType").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));

        // Check, that the count call also returns 1
        restIncomeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultIncomeDetailsShouldNotBeFound(String filter) throws Exception {
        restIncomeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIncomeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingIncomeDetails() throws Exception {
        // Get the incomeDetails
        restIncomeDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIncomeDetails() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        int databaseSizeBeforeUpdate = incomeDetailsRepository.findAll().size();

        // Update the incomeDetails
        IncomeDetails updatedIncomeDetails = incomeDetailsRepository.findById(incomeDetails.getId()).get();
        // Disconnect from session so that the updates on updatedIncomeDetails are not directly saved in db
        em.detach(updatedIncomeDetails);
        updatedIncomeDetails
            .year(UPDATED_YEAR)
            .grossIncome(UPDATED_GROSS_INCOME)
            .expenses(UPDATED_EXPENSES)
            .netIncome(UPDATED_NET_INCOME)
            .paidTaxes(UPDATED_PAID_TAXES)
            .cashSurplus(UPDATED_CASH_SURPLUS)
            .incomeType(UPDATED_INCOME_TYPE)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .otherType(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        IncomeDetailsDTO incomeDetailsDTO = incomeDetailsMapper.toDto(updatedIncomeDetails);

        restIncomeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, incomeDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(incomeDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the IncomeDetails in the database
        List<IncomeDetails> incomeDetailsList = incomeDetailsRepository.findAll();
        assertThat(incomeDetailsList).hasSize(databaseSizeBeforeUpdate);
        IncomeDetails testIncomeDetails = incomeDetailsList.get(incomeDetailsList.size() - 1);
        assertThat(testIncomeDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testIncomeDetails.getGrossIncome()).isEqualTo(UPDATED_GROSS_INCOME);
        assertThat(testIncomeDetails.getExpenses()).isEqualTo(UPDATED_EXPENSES);
        assertThat(testIncomeDetails.getNetIncome()).isEqualTo(UPDATED_NET_INCOME);
        assertThat(testIncomeDetails.getPaidTaxes()).isEqualTo(UPDATED_PAID_TAXES);
        assertThat(testIncomeDetails.getCashSurplus()).isEqualTo(UPDATED_CASH_SURPLUS);
        assertThat(testIncomeDetails.getIncomeType()).isEqualTo(UPDATED_INCOME_TYPE);
        assertThat(testIncomeDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testIncomeDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testIncomeDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testIncomeDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testIncomeDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testIncomeDetails.getOtherType()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testIncomeDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testIncomeDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testIncomeDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testIncomeDetails.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testIncomeDetails.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingIncomeDetails() throws Exception {
        int databaseSizeBeforeUpdate = incomeDetailsRepository.findAll().size();
        incomeDetails.setId(count.incrementAndGet());

        // Create the IncomeDetails
        IncomeDetailsDTO incomeDetailsDTO = incomeDetailsMapper.toDto(incomeDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, incomeDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(incomeDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncomeDetails in the database
        List<IncomeDetails> incomeDetailsList = incomeDetailsRepository.findAll();
        assertThat(incomeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIncomeDetails() throws Exception {
        int databaseSizeBeforeUpdate = incomeDetailsRepository.findAll().size();
        incomeDetails.setId(count.incrementAndGet());

        // Create the IncomeDetails
        IncomeDetailsDTO incomeDetailsDTO = incomeDetailsMapper.toDto(incomeDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncomeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(incomeDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncomeDetails in the database
        List<IncomeDetails> incomeDetailsList = incomeDetailsRepository.findAll();
        assertThat(incomeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIncomeDetails() throws Exception {
        int databaseSizeBeforeUpdate = incomeDetailsRepository.findAll().size();
        incomeDetails.setId(count.incrementAndGet());

        // Create the IncomeDetails
        IncomeDetailsDTO incomeDetailsDTO = incomeDetailsMapper.toDto(incomeDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncomeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(incomeDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IncomeDetails in the database
        List<IncomeDetails> incomeDetailsList = incomeDetailsRepository.findAll();
        assertThat(incomeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIncomeDetailsWithPatch() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        int databaseSizeBeforeUpdate = incomeDetailsRepository.findAll().size();

        // Update the incomeDetails using partial update
        IncomeDetails partialUpdatedIncomeDetails = new IncomeDetails();
        partialUpdatedIncomeDetails.setId(incomeDetails.getId());

        partialUpdatedIncomeDetails
            .year(UPDATED_YEAR)
            .grossIncome(UPDATED_GROSS_INCOME)
            .expenses(UPDATED_EXPENSES)
            .netIncome(UPDATED_NET_INCOME)
            .incomeType(UPDATED_INCOME_TYPE)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .otherType(UPDATED_FREE_FIELD_1)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restIncomeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIncomeDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIncomeDetails))
            )
            .andExpect(status().isOk());

        // Validate the IncomeDetails in the database
        List<IncomeDetails> incomeDetailsList = incomeDetailsRepository.findAll();
        assertThat(incomeDetailsList).hasSize(databaseSizeBeforeUpdate);
        IncomeDetails testIncomeDetails = incomeDetailsList.get(incomeDetailsList.size() - 1);
        assertThat(testIncomeDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testIncomeDetails.getGrossIncome()).isEqualTo(UPDATED_GROSS_INCOME);
        assertThat(testIncomeDetails.getExpenses()).isEqualTo(UPDATED_EXPENSES);
        assertThat(testIncomeDetails.getNetIncome()).isEqualTo(UPDATED_NET_INCOME);
        assertThat(testIncomeDetails.getPaidTaxes()).isEqualTo(DEFAULT_PAID_TAXES);
        assertThat(testIncomeDetails.getCashSurplus()).isEqualTo(DEFAULT_CASH_SURPLUS);
        assertThat(testIncomeDetails.getIncomeType()).isEqualTo(UPDATED_INCOME_TYPE);
        assertThat(testIncomeDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testIncomeDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testIncomeDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testIncomeDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testIncomeDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testIncomeDetails.getOtherType()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testIncomeDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testIncomeDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testIncomeDetails.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testIncomeDetails.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testIncomeDetails.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateIncomeDetailsWithPatch() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        int databaseSizeBeforeUpdate = incomeDetailsRepository.findAll().size();

        // Update the incomeDetails using partial update
        IncomeDetails partialUpdatedIncomeDetails = new IncomeDetails();
        partialUpdatedIncomeDetails.setId(incomeDetails.getId());

        partialUpdatedIncomeDetails
            .year(UPDATED_YEAR)
            .grossIncome(UPDATED_GROSS_INCOME)
            .expenses(UPDATED_EXPENSES)
            .netIncome(UPDATED_NET_INCOME)
            .paidTaxes(UPDATED_PAID_TAXES)
            .cashSurplus(UPDATED_CASH_SURPLUS)
            .incomeType(UPDATED_INCOME_TYPE)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .otherType(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restIncomeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIncomeDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIncomeDetails))
            )
            .andExpect(status().isOk());

        // Validate the IncomeDetails in the database
        List<IncomeDetails> incomeDetailsList = incomeDetailsRepository.findAll();
        assertThat(incomeDetailsList).hasSize(databaseSizeBeforeUpdate);
        IncomeDetails testIncomeDetails = incomeDetailsList.get(incomeDetailsList.size() - 1);
        assertThat(testIncomeDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testIncomeDetails.getGrossIncome()).isEqualTo(UPDATED_GROSS_INCOME);
        assertThat(testIncomeDetails.getExpenses()).isEqualTo(UPDATED_EXPENSES);
        assertThat(testIncomeDetails.getNetIncome()).isEqualTo(UPDATED_NET_INCOME);
        assertThat(testIncomeDetails.getPaidTaxes()).isEqualTo(UPDATED_PAID_TAXES);
        assertThat(testIncomeDetails.getCashSurplus()).isEqualTo(UPDATED_CASH_SURPLUS);
        assertThat(testIncomeDetails.getIncomeType()).isEqualTo(UPDATED_INCOME_TYPE);
        assertThat(testIncomeDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testIncomeDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testIncomeDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testIncomeDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testIncomeDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testIncomeDetails.getOtherType()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testIncomeDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testIncomeDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testIncomeDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testIncomeDetails.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testIncomeDetails.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingIncomeDetails() throws Exception {
        int databaseSizeBeforeUpdate = incomeDetailsRepository.findAll().size();
        incomeDetails.setId(count.incrementAndGet());

        // Create the IncomeDetails
        IncomeDetailsDTO incomeDetailsDTO = incomeDetailsMapper.toDto(incomeDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncomeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, incomeDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(incomeDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncomeDetails in the database
        List<IncomeDetails> incomeDetailsList = incomeDetailsRepository.findAll();
        assertThat(incomeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIncomeDetails() throws Exception {
        int databaseSizeBeforeUpdate = incomeDetailsRepository.findAll().size();
        incomeDetails.setId(count.incrementAndGet());

        // Create the IncomeDetails
        IncomeDetailsDTO incomeDetailsDTO = incomeDetailsMapper.toDto(incomeDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncomeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(incomeDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncomeDetails in the database
        List<IncomeDetails> incomeDetailsList = incomeDetailsRepository.findAll();
        assertThat(incomeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIncomeDetails() throws Exception {
        int databaseSizeBeforeUpdate = incomeDetailsRepository.findAll().size();
        incomeDetails.setId(count.incrementAndGet());

        // Create the IncomeDetails
        IncomeDetailsDTO incomeDetailsDTO = incomeDetailsMapper.toDto(incomeDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncomeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(incomeDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IncomeDetails in the database
        List<IncomeDetails> incomeDetailsList = incomeDetailsRepository.findAll();
        assertThat(incomeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIncomeDetails() throws Exception {
        // Initialize the database
        incomeDetailsRepository.saveAndFlush(incomeDetails);

        int databaseSizeBeforeDelete = incomeDetailsRepository.findAll().size();

        // Delete the incomeDetails
        restIncomeDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, incomeDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IncomeDetails> incomeDetailsList = incomeDetailsRepository.findAll();
        assertThat(incomeDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
