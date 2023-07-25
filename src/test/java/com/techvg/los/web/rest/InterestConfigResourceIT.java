package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.InterestConfig;
import com.techvg.los.domain.Product;
import com.techvg.los.domain.enumeration.InterestType;
import com.techvg.los.domain.enumeration.RepaymentFreqency;
import com.techvg.los.repository.InterestConfigRepository;
import com.techvg.los.service.criteria.InterestConfigCriteria;
import com.techvg.los.service.dto.InterestConfigDTO;
import com.techvg.los.service.mapper.InterestConfigMapper;
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
 * Integration tests for the {@link InterestConfigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InterestConfigResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_INTEREST_BASIS = "AAAAAAAAAA";
    private static final String UPDATED_INTEREST_BASIS = "BBBBBBBBBB";

    private static final String DEFAULT_EMI_BASIS = "AAAAAAAAAA";
    private static final String UPDATED_EMI_BASIS = "BBBBBBBBBB";

    private static final InterestType DEFAULT_INTEREST_TYPE = InterestType.FIXED;
    private static final InterestType UPDATED_INTEREST_TYPE = InterestType.FLOATING;

    private static final RepaymentFreqency DEFAULT_INTR_ACCRUAL_FREQ = RepaymentFreqency.MONTHLY;
    private static final RepaymentFreqency UPDATED_INTR_ACCRUAL_FREQ = RepaymentFreqency.QUARTERLY;

    private static final Double DEFAULT_PENAL_INTEREST_RATE = 1D;
    private static final Double UPDATED_PENAL_INTEREST_RATE = 2D;
    private static final Double SMALLER_PENAL_INTEREST_RATE = 1D - 1D;

    private static final String DEFAULT_PENAL_INTEREST_BASIS = "AAAAAAAAAA";
    private static final String UPDATED_PENAL_INTEREST_BASIS = "BBBBBBBBBB";

    private static final String DEFAULT_PENAL_ACCOUNTING_BASIS = "AAAAAAAAAA";
    private static final String UPDATED_PENAL_ACCOUNTING_BASIS = "BBBBBBBBBB";

    private static final Double DEFAULT_MIN_INTEREST_RATE = 1D;
    private static final Double UPDATED_MIN_INTEREST_RATE = 2D;
    private static final Double SMALLER_MIN_INTEREST_RATE = 1D - 1D;

    private static final Double DEFAULT_MAX_INTEREST_RATE = 1D;
    private static final Double UPDATED_MAX_INTEREST_RATE = 2D;
    private static final Double SMALLER_MAX_INTEREST_RATE = 1D - 1D;

    private static final Double DEFAULT_EXTENDED_INTEREST_RATE = 1D;
    private static final Double UPDATED_EXTENDED_INTEREST_RATE = 2D;
    private static final Double SMALLER_EXTENDED_INTEREST_RATE = 1D - 1D;

    private static final Double DEFAULT_SURCHARGE_RATE = 1D;
    private static final Double UPDATED_SURCHARGE_RATE = 2D;
    private static final Double SMALLER_SURCHARGE_RATE = 1D - 1D;

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

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

    private static final String ENTITY_API_URL = "/api/interest-configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InterestConfigRepository interestConfigRepository;

    @Autowired
    private InterestConfigMapper interestConfigMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInterestConfigMockMvc;

    private InterestConfig interestConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InterestConfig createEntity(EntityManager em) {
        InterestConfig interestConfig = new InterestConfig()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .interestBasis(DEFAULT_INTEREST_BASIS)
            .emiBasis(DEFAULT_EMI_BASIS)
            .interestType(DEFAULT_INTEREST_TYPE)
            .intrAccrualFreq(DEFAULT_INTR_ACCRUAL_FREQ)
            .penalInterestRate(DEFAULT_PENAL_INTEREST_RATE)
            .penalInterestBasis(DEFAULT_PENAL_INTEREST_BASIS)
            .penalAccountingBasis(DEFAULT_PENAL_ACCOUNTING_BASIS)
            .minInterestRate(DEFAULT_MIN_INTEREST_RATE)
            .maxInterestRate(DEFAULT_MAX_INTEREST_RATE)
            .extendedInterestRate(DEFAULT_EXTENDED_INTEREST_RATE)
            .surchargeRate(DEFAULT_SURCHARGE_RATE)
            .isDeleted(DEFAULT_IS_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5)
            .freeField6(DEFAULT_FREE_FIELD_6);
        return interestConfig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InterestConfig createUpdatedEntity(EntityManager em) {
        InterestConfig interestConfig = new InterestConfig()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .interestBasis(UPDATED_INTEREST_BASIS)
            .emiBasis(UPDATED_EMI_BASIS)
            .interestType(UPDATED_INTEREST_TYPE)
            .intrAccrualFreq(UPDATED_INTR_ACCRUAL_FREQ)
            .penalInterestRate(UPDATED_PENAL_INTEREST_RATE)
            .penalInterestBasis(UPDATED_PENAL_INTEREST_BASIS)
            .penalAccountingBasis(UPDATED_PENAL_ACCOUNTING_BASIS)
            .minInterestRate(UPDATED_MIN_INTEREST_RATE)
            .maxInterestRate(UPDATED_MAX_INTEREST_RATE)
            .extendedInterestRate(UPDATED_EXTENDED_INTEREST_RATE)
            .surchargeRate(UPDATED_SURCHARGE_RATE)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        return interestConfig;
    }

    @BeforeEach
    public void initTest() {
        interestConfig = createEntity(em);
    }

    @Test
    @Transactional
    void createInterestConfig() throws Exception {
        int databaseSizeBeforeCreate = interestConfigRepository.findAll().size();
        // Create the InterestConfig
        InterestConfigDTO interestConfigDTO = interestConfigMapper.toDto(interestConfig);
        restInterestConfigMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interestConfigDTO))
            )
            .andExpect(status().isCreated());

        // Validate the InterestConfig in the database
        List<InterestConfig> interestConfigList = interestConfigRepository.findAll();
        assertThat(interestConfigList).hasSize(databaseSizeBeforeCreate + 1);
        InterestConfig testInterestConfig = interestConfigList.get(interestConfigList.size() - 1);
        assertThat(testInterestConfig.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testInterestConfig.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testInterestConfig.getInterestBasis()).isEqualTo(DEFAULT_INTEREST_BASIS);
        assertThat(testInterestConfig.getEmiBasis()).isEqualTo(DEFAULT_EMI_BASIS);
        assertThat(testInterestConfig.getInterestType()).isEqualTo(DEFAULT_INTEREST_TYPE);
        assertThat(testInterestConfig.getIntrAccrualFreq()).isEqualTo(DEFAULT_INTR_ACCRUAL_FREQ);
        assertThat(testInterestConfig.getPenalInterestRate()).isEqualTo(DEFAULT_PENAL_INTEREST_RATE);
        assertThat(testInterestConfig.getPenalInterestBasis()).isEqualTo(DEFAULT_PENAL_INTEREST_BASIS);
        assertThat(testInterestConfig.getPenalAccountingBasis()).isEqualTo(DEFAULT_PENAL_ACCOUNTING_BASIS);
        assertThat(testInterestConfig.getMinInterestRate()).isEqualTo(DEFAULT_MIN_INTEREST_RATE);
        assertThat(testInterestConfig.getMaxInterestRate()).isEqualTo(DEFAULT_MAX_INTEREST_RATE);
        assertThat(testInterestConfig.getExtendedInterestRate()).isEqualTo(DEFAULT_EXTENDED_INTEREST_RATE);
        assertThat(testInterestConfig.getSurchargeRate()).isEqualTo(DEFAULT_SURCHARGE_RATE);
        assertThat(testInterestConfig.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testInterestConfig.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testInterestConfig.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testInterestConfig.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testInterestConfig.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testInterestConfig.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testInterestConfig.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testInterestConfig.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testInterestConfig.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createInterestConfigWithExistingId() throws Exception {
        // Create the InterestConfig with an existing ID
        interestConfig.setId(1L);
        InterestConfigDTO interestConfigDTO = interestConfigMapper.toDto(interestConfig);

        int databaseSizeBeforeCreate = interestConfigRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterestConfigMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interestConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InterestConfig in the database
        List<InterestConfig> interestConfigList = interestConfigRepository.findAll();
        assertThat(interestConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllInterestConfigs() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList
        restInterestConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interestConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].interestBasis").value(hasItem(DEFAULT_INTEREST_BASIS)))
            .andExpect(jsonPath("$.[*].emiBasis").value(hasItem(DEFAULT_EMI_BASIS)))
            .andExpect(jsonPath("$.[*].interestType").value(hasItem(DEFAULT_INTEREST_TYPE.toString())))
            .andExpect(jsonPath("$.[*].intrAccrualFreq").value(hasItem(DEFAULT_INTR_ACCRUAL_FREQ.toString())))
            .andExpect(jsonPath("$.[*].penalInterestRate").value(hasItem(DEFAULT_PENAL_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].penalInterestBasis").value(hasItem(DEFAULT_PENAL_INTEREST_BASIS)))
            .andExpect(jsonPath("$.[*].penalAccountingBasis").value(hasItem(DEFAULT_PENAL_ACCOUNTING_BASIS)))
            .andExpect(jsonPath("$.[*].minInterestRate").value(hasItem(DEFAULT_MIN_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxInterestRate").value(hasItem(DEFAULT_MAX_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].extendedInterestRate").value(hasItem(DEFAULT_EXTENDED_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].surchargeRate").value(hasItem(DEFAULT_SURCHARGE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
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
    void getInterestConfig() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get the interestConfig
        restInterestConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, interestConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interestConfig.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.interestBasis").value(DEFAULT_INTEREST_BASIS))
            .andExpect(jsonPath("$.emiBasis").value(DEFAULT_EMI_BASIS))
            .andExpect(jsonPath("$.interestType").value(DEFAULT_INTEREST_TYPE.toString()))
            .andExpect(jsonPath("$.intrAccrualFreq").value(DEFAULT_INTR_ACCRUAL_FREQ.toString()))
            .andExpect(jsonPath("$.penalInterestRate").value(DEFAULT_PENAL_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.penalInterestBasis").value(DEFAULT_PENAL_INTEREST_BASIS))
            .andExpect(jsonPath("$.penalAccountingBasis").value(DEFAULT_PENAL_ACCOUNTING_BASIS))
            .andExpect(jsonPath("$.minInterestRate").value(DEFAULT_MIN_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.maxInterestRate").value(DEFAULT_MAX_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.extendedInterestRate").value(DEFAULT_EXTENDED_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.surchargeRate").value(DEFAULT_SURCHARGE_RATE.doubleValue()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
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
    void getInterestConfigsByIdFiltering() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        Long id = interestConfig.getId();

        defaultInterestConfigShouldBeFound("id.equals=" + id);
        defaultInterestConfigShouldNotBeFound("id.notEquals=" + id);

        defaultInterestConfigShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInterestConfigShouldNotBeFound("id.greaterThan=" + id);

        defaultInterestConfigShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInterestConfigShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where startDate equals to DEFAULT_START_DATE
        defaultInterestConfigShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the interestConfigList where startDate equals to UPDATED_START_DATE
        defaultInterestConfigShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultInterestConfigShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the interestConfigList where startDate equals to UPDATED_START_DATE
        defaultInterestConfigShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where startDate is not null
        defaultInterestConfigShouldBeFound("startDate.specified=true");

        // Get all the interestConfigList where startDate is null
        defaultInterestConfigShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where endDate equals to DEFAULT_END_DATE
        defaultInterestConfigShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the interestConfigList where endDate equals to UPDATED_END_DATE
        defaultInterestConfigShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultInterestConfigShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the interestConfigList where endDate equals to UPDATED_END_DATE
        defaultInterestConfigShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where endDate is not null
        defaultInterestConfigShouldBeFound("endDate.specified=true");

        // Get all the interestConfigList where endDate is null
        defaultInterestConfigShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByInterestBasisIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where interestBasis equals to DEFAULT_INTEREST_BASIS
        defaultInterestConfigShouldBeFound("interestBasis.equals=" + DEFAULT_INTEREST_BASIS);

        // Get all the interestConfigList where interestBasis equals to UPDATED_INTEREST_BASIS
        defaultInterestConfigShouldNotBeFound("interestBasis.equals=" + UPDATED_INTEREST_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByInterestBasisIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where interestBasis in DEFAULT_INTEREST_BASIS or UPDATED_INTEREST_BASIS
        defaultInterestConfigShouldBeFound("interestBasis.in=" + DEFAULT_INTEREST_BASIS + "," + UPDATED_INTEREST_BASIS);

        // Get all the interestConfigList where interestBasis equals to UPDATED_INTEREST_BASIS
        defaultInterestConfigShouldNotBeFound("interestBasis.in=" + UPDATED_INTEREST_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByInterestBasisIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where interestBasis is not null
        defaultInterestConfigShouldBeFound("interestBasis.specified=true");

        // Get all the interestConfigList where interestBasis is null
        defaultInterestConfigShouldNotBeFound("interestBasis.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByInterestBasisContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where interestBasis contains DEFAULT_INTEREST_BASIS
        defaultInterestConfigShouldBeFound("interestBasis.contains=" + DEFAULT_INTEREST_BASIS);

        // Get all the interestConfigList where interestBasis contains UPDATED_INTEREST_BASIS
        defaultInterestConfigShouldNotBeFound("interestBasis.contains=" + UPDATED_INTEREST_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByInterestBasisNotContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where interestBasis does not contain DEFAULT_INTEREST_BASIS
        defaultInterestConfigShouldNotBeFound("interestBasis.doesNotContain=" + DEFAULT_INTEREST_BASIS);

        // Get all the interestConfigList where interestBasis does not contain UPDATED_INTEREST_BASIS
        defaultInterestConfigShouldBeFound("interestBasis.doesNotContain=" + UPDATED_INTEREST_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByEmiBasisIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where emiBasis equals to DEFAULT_EMI_BASIS
        defaultInterestConfigShouldBeFound("emiBasis.equals=" + DEFAULT_EMI_BASIS);

        // Get all the interestConfigList where emiBasis equals to UPDATED_EMI_BASIS
        defaultInterestConfigShouldNotBeFound("emiBasis.equals=" + UPDATED_EMI_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByEmiBasisIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where emiBasis in DEFAULT_EMI_BASIS or UPDATED_EMI_BASIS
        defaultInterestConfigShouldBeFound("emiBasis.in=" + DEFAULT_EMI_BASIS + "," + UPDATED_EMI_BASIS);

        // Get all the interestConfigList where emiBasis equals to UPDATED_EMI_BASIS
        defaultInterestConfigShouldNotBeFound("emiBasis.in=" + UPDATED_EMI_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByEmiBasisIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where emiBasis is not null
        defaultInterestConfigShouldBeFound("emiBasis.specified=true");

        // Get all the interestConfigList where emiBasis is null
        defaultInterestConfigShouldNotBeFound("emiBasis.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByEmiBasisContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where emiBasis contains DEFAULT_EMI_BASIS
        defaultInterestConfigShouldBeFound("emiBasis.contains=" + DEFAULT_EMI_BASIS);

        // Get all the interestConfigList where emiBasis contains UPDATED_EMI_BASIS
        defaultInterestConfigShouldNotBeFound("emiBasis.contains=" + UPDATED_EMI_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByEmiBasisNotContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where emiBasis does not contain DEFAULT_EMI_BASIS
        defaultInterestConfigShouldNotBeFound("emiBasis.doesNotContain=" + DEFAULT_EMI_BASIS);

        // Get all the interestConfigList where emiBasis does not contain UPDATED_EMI_BASIS
        defaultInterestConfigShouldBeFound("emiBasis.doesNotContain=" + UPDATED_EMI_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByInterestTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where interestType equals to DEFAULT_INTEREST_TYPE
        defaultInterestConfigShouldBeFound("interestType.equals=" + DEFAULT_INTEREST_TYPE);

        // Get all the interestConfigList where interestType equals to UPDATED_INTEREST_TYPE
        defaultInterestConfigShouldNotBeFound("interestType.equals=" + UPDATED_INTEREST_TYPE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByInterestTypeIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where interestType in DEFAULT_INTEREST_TYPE or UPDATED_INTEREST_TYPE
        defaultInterestConfigShouldBeFound("interestType.in=" + DEFAULT_INTEREST_TYPE + "," + UPDATED_INTEREST_TYPE);

        // Get all the interestConfigList where interestType equals to UPDATED_INTEREST_TYPE
        defaultInterestConfigShouldNotBeFound("interestType.in=" + UPDATED_INTEREST_TYPE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByInterestTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where interestType is not null
        defaultInterestConfigShouldBeFound("interestType.specified=true");

        // Get all the interestConfigList where interestType is null
        defaultInterestConfigShouldNotBeFound("interestType.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByIntrAccrualFreqIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where intrAccrualFreq equals to DEFAULT_INTR_ACCRUAL_FREQ
        defaultInterestConfigShouldBeFound("intrAccrualFreq.equals=" + DEFAULT_INTR_ACCRUAL_FREQ);

        // Get all the interestConfigList where intrAccrualFreq equals to UPDATED_INTR_ACCRUAL_FREQ
        defaultInterestConfigShouldNotBeFound("intrAccrualFreq.equals=" + UPDATED_INTR_ACCRUAL_FREQ);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByIntrAccrualFreqIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where intrAccrualFreq in DEFAULT_INTR_ACCRUAL_FREQ or UPDATED_INTR_ACCRUAL_FREQ
        defaultInterestConfigShouldBeFound("intrAccrualFreq.in=" + DEFAULT_INTR_ACCRUAL_FREQ + "," + UPDATED_INTR_ACCRUAL_FREQ);

        // Get all the interestConfigList where intrAccrualFreq equals to UPDATED_INTR_ACCRUAL_FREQ
        defaultInterestConfigShouldNotBeFound("intrAccrualFreq.in=" + UPDATED_INTR_ACCRUAL_FREQ);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByIntrAccrualFreqIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where intrAccrualFreq is not null
        defaultInterestConfigShouldBeFound("intrAccrualFreq.specified=true");

        // Get all the interestConfigList where intrAccrualFreq is null
        defaultInterestConfigShouldNotBeFound("intrAccrualFreq.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalInterestRateIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalInterestRate equals to DEFAULT_PENAL_INTEREST_RATE
        defaultInterestConfigShouldBeFound("penalInterestRate.equals=" + DEFAULT_PENAL_INTEREST_RATE);

        // Get all the interestConfigList where penalInterestRate equals to UPDATED_PENAL_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("penalInterestRate.equals=" + UPDATED_PENAL_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalInterestRateIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalInterestRate in DEFAULT_PENAL_INTEREST_RATE or UPDATED_PENAL_INTEREST_RATE
        defaultInterestConfigShouldBeFound("penalInterestRate.in=" + DEFAULT_PENAL_INTEREST_RATE + "," + UPDATED_PENAL_INTEREST_RATE);

        // Get all the interestConfigList where penalInterestRate equals to UPDATED_PENAL_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("penalInterestRate.in=" + UPDATED_PENAL_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalInterestRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalInterestRate is not null
        defaultInterestConfigShouldBeFound("penalInterestRate.specified=true");

        // Get all the interestConfigList where penalInterestRate is null
        defaultInterestConfigShouldNotBeFound("penalInterestRate.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalInterestRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalInterestRate is greater than or equal to DEFAULT_PENAL_INTEREST_RATE
        defaultInterestConfigShouldBeFound("penalInterestRate.greaterThanOrEqual=" + DEFAULT_PENAL_INTEREST_RATE);

        // Get all the interestConfigList where penalInterestRate is greater than or equal to UPDATED_PENAL_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("penalInterestRate.greaterThanOrEqual=" + UPDATED_PENAL_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalInterestRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalInterestRate is less than or equal to DEFAULT_PENAL_INTEREST_RATE
        defaultInterestConfigShouldBeFound("penalInterestRate.lessThanOrEqual=" + DEFAULT_PENAL_INTEREST_RATE);

        // Get all the interestConfigList where penalInterestRate is less than or equal to SMALLER_PENAL_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("penalInterestRate.lessThanOrEqual=" + SMALLER_PENAL_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalInterestRateIsLessThanSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalInterestRate is less than DEFAULT_PENAL_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("penalInterestRate.lessThan=" + DEFAULT_PENAL_INTEREST_RATE);

        // Get all the interestConfigList where penalInterestRate is less than UPDATED_PENAL_INTEREST_RATE
        defaultInterestConfigShouldBeFound("penalInterestRate.lessThan=" + UPDATED_PENAL_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalInterestRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalInterestRate is greater than DEFAULT_PENAL_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("penalInterestRate.greaterThan=" + DEFAULT_PENAL_INTEREST_RATE);

        // Get all the interestConfigList where penalInterestRate is greater than SMALLER_PENAL_INTEREST_RATE
        defaultInterestConfigShouldBeFound("penalInterestRate.greaterThan=" + SMALLER_PENAL_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalInterestBasisIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalInterestBasis equals to DEFAULT_PENAL_INTEREST_BASIS
        defaultInterestConfigShouldBeFound("penalInterestBasis.equals=" + DEFAULT_PENAL_INTEREST_BASIS);

        // Get all the interestConfigList where penalInterestBasis equals to UPDATED_PENAL_INTEREST_BASIS
        defaultInterestConfigShouldNotBeFound("penalInterestBasis.equals=" + UPDATED_PENAL_INTEREST_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalInterestBasisIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalInterestBasis in DEFAULT_PENAL_INTEREST_BASIS or UPDATED_PENAL_INTEREST_BASIS
        defaultInterestConfigShouldBeFound("penalInterestBasis.in=" + DEFAULT_PENAL_INTEREST_BASIS + "," + UPDATED_PENAL_INTEREST_BASIS);

        // Get all the interestConfigList where penalInterestBasis equals to UPDATED_PENAL_INTEREST_BASIS
        defaultInterestConfigShouldNotBeFound("penalInterestBasis.in=" + UPDATED_PENAL_INTEREST_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalInterestBasisIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalInterestBasis is not null
        defaultInterestConfigShouldBeFound("penalInterestBasis.specified=true");

        // Get all the interestConfigList where penalInterestBasis is null
        defaultInterestConfigShouldNotBeFound("penalInterestBasis.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalInterestBasisContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalInterestBasis contains DEFAULT_PENAL_INTEREST_BASIS
        defaultInterestConfigShouldBeFound("penalInterestBasis.contains=" + DEFAULT_PENAL_INTEREST_BASIS);

        // Get all the interestConfigList where penalInterestBasis contains UPDATED_PENAL_INTEREST_BASIS
        defaultInterestConfigShouldNotBeFound("penalInterestBasis.contains=" + UPDATED_PENAL_INTEREST_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalInterestBasisNotContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalInterestBasis does not contain DEFAULT_PENAL_INTEREST_BASIS
        defaultInterestConfigShouldNotBeFound("penalInterestBasis.doesNotContain=" + DEFAULT_PENAL_INTEREST_BASIS);

        // Get all the interestConfigList where penalInterestBasis does not contain UPDATED_PENAL_INTEREST_BASIS
        defaultInterestConfigShouldBeFound("penalInterestBasis.doesNotContain=" + UPDATED_PENAL_INTEREST_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalAccountingBasisIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalAccountingBasis equals to DEFAULT_PENAL_ACCOUNTING_BASIS
        defaultInterestConfigShouldBeFound("penalAccountingBasis.equals=" + DEFAULT_PENAL_ACCOUNTING_BASIS);

        // Get all the interestConfigList where penalAccountingBasis equals to UPDATED_PENAL_ACCOUNTING_BASIS
        defaultInterestConfigShouldNotBeFound("penalAccountingBasis.equals=" + UPDATED_PENAL_ACCOUNTING_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalAccountingBasisIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalAccountingBasis in DEFAULT_PENAL_ACCOUNTING_BASIS or UPDATED_PENAL_ACCOUNTING_BASIS
        defaultInterestConfigShouldBeFound(
            "penalAccountingBasis.in=" + DEFAULT_PENAL_ACCOUNTING_BASIS + "," + UPDATED_PENAL_ACCOUNTING_BASIS
        );

        // Get all the interestConfigList where penalAccountingBasis equals to UPDATED_PENAL_ACCOUNTING_BASIS
        defaultInterestConfigShouldNotBeFound("penalAccountingBasis.in=" + UPDATED_PENAL_ACCOUNTING_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalAccountingBasisIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalAccountingBasis is not null
        defaultInterestConfigShouldBeFound("penalAccountingBasis.specified=true");

        // Get all the interestConfigList where penalAccountingBasis is null
        defaultInterestConfigShouldNotBeFound("penalAccountingBasis.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalAccountingBasisContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalAccountingBasis contains DEFAULT_PENAL_ACCOUNTING_BASIS
        defaultInterestConfigShouldBeFound("penalAccountingBasis.contains=" + DEFAULT_PENAL_ACCOUNTING_BASIS);

        // Get all the interestConfigList where penalAccountingBasis contains UPDATED_PENAL_ACCOUNTING_BASIS
        defaultInterestConfigShouldNotBeFound("penalAccountingBasis.contains=" + UPDATED_PENAL_ACCOUNTING_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByPenalAccountingBasisNotContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where penalAccountingBasis does not contain DEFAULT_PENAL_ACCOUNTING_BASIS
        defaultInterestConfigShouldNotBeFound("penalAccountingBasis.doesNotContain=" + DEFAULT_PENAL_ACCOUNTING_BASIS);

        // Get all the interestConfigList where penalAccountingBasis does not contain UPDATED_PENAL_ACCOUNTING_BASIS
        defaultInterestConfigShouldBeFound("penalAccountingBasis.doesNotContain=" + UPDATED_PENAL_ACCOUNTING_BASIS);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByMinInterestRateIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where minInterestRate equals to DEFAULT_MIN_INTEREST_RATE
        defaultInterestConfigShouldBeFound("minInterestRate.equals=" + DEFAULT_MIN_INTEREST_RATE);

        // Get all the interestConfigList where minInterestRate equals to UPDATED_MIN_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("minInterestRate.equals=" + UPDATED_MIN_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByMinInterestRateIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where minInterestRate in DEFAULT_MIN_INTEREST_RATE or UPDATED_MIN_INTEREST_RATE
        defaultInterestConfigShouldBeFound("minInterestRate.in=" + DEFAULT_MIN_INTEREST_RATE + "," + UPDATED_MIN_INTEREST_RATE);

        // Get all the interestConfigList where minInterestRate equals to UPDATED_MIN_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("minInterestRate.in=" + UPDATED_MIN_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByMinInterestRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where minInterestRate is not null
        defaultInterestConfigShouldBeFound("minInterestRate.specified=true");

        // Get all the interestConfigList where minInterestRate is null
        defaultInterestConfigShouldNotBeFound("minInterestRate.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByMinInterestRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where minInterestRate is greater than or equal to DEFAULT_MIN_INTEREST_RATE
        defaultInterestConfigShouldBeFound("minInterestRate.greaterThanOrEqual=" + DEFAULT_MIN_INTEREST_RATE);

        // Get all the interestConfigList where minInterestRate is greater than or equal to UPDATED_MIN_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("minInterestRate.greaterThanOrEqual=" + UPDATED_MIN_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByMinInterestRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where minInterestRate is less than or equal to DEFAULT_MIN_INTEREST_RATE
        defaultInterestConfigShouldBeFound("minInterestRate.lessThanOrEqual=" + DEFAULT_MIN_INTEREST_RATE);

        // Get all the interestConfigList where minInterestRate is less than or equal to SMALLER_MIN_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("minInterestRate.lessThanOrEqual=" + SMALLER_MIN_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByMinInterestRateIsLessThanSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where minInterestRate is less than DEFAULT_MIN_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("minInterestRate.lessThan=" + DEFAULT_MIN_INTEREST_RATE);

        // Get all the interestConfigList where minInterestRate is less than UPDATED_MIN_INTEREST_RATE
        defaultInterestConfigShouldBeFound("minInterestRate.lessThan=" + UPDATED_MIN_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByMinInterestRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where minInterestRate is greater than DEFAULT_MIN_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("minInterestRate.greaterThan=" + DEFAULT_MIN_INTEREST_RATE);

        // Get all the interestConfigList where minInterestRate is greater than SMALLER_MIN_INTEREST_RATE
        defaultInterestConfigShouldBeFound("minInterestRate.greaterThan=" + SMALLER_MIN_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByMaxInterestRateIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where maxInterestRate equals to DEFAULT_MAX_INTEREST_RATE
        defaultInterestConfigShouldBeFound("maxInterestRate.equals=" + DEFAULT_MAX_INTEREST_RATE);

        // Get all the interestConfigList where maxInterestRate equals to UPDATED_MAX_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("maxInterestRate.equals=" + UPDATED_MAX_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByMaxInterestRateIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where maxInterestRate in DEFAULT_MAX_INTEREST_RATE or UPDATED_MAX_INTEREST_RATE
        defaultInterestConfigShouldBeFound("maxInterestRate.in=" + DEFAULT_MAX_INTEREST_RATE + "," + UPDATED_MAX_INTEREST_RATE);

        // Get all the interestConfigList where maxInterestRate equals to UPDATED_MAX_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("maxInterestRate.in=" + UPDATED_MAX_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByMaxInterestRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where maxInterestRate is not null
        defaultInterestConfigShouldBeFound("maxInterestRate.specified=true");

        // Get all the interestConfigList where maxInterestRate is null
        defaultInterestConfigShouldNotBeFound("maxInterestRate.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByMaxInterestRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where maxInterestRate is greater than or equal to DEFAULT_MAX_INTEREST_RATE
        defaultInterestConfigShouldBeFound("maxInterestRate.greaterThanOrEqual=" + DEFAULT_MAX_INTEREST_RATE);

        // Get all the interestConfigList where maxInterestRate is greater than or equal to UPDATED_MAX_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("maxInterestRate.greaterThanOrEqual=" + UPDATED_MAX_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByMaxInterestRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where maxInterestRate is less than or equal to DEFAULT_MAX_INTEREST_RATE
        defaultInterestConfigShouldBeFound("maxInterestRate.lessThanOrEqual=" + DEFAULT_MAX_INTEREST_RATE);

        // Get all the interestConfigList where maxInterestRate is less than or equal to SMALLER_MAX_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("maxInterestRate.lessThanOrEqual=" + SMALLER_MAX_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByMaxInterestRateIsLessThanSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where maxInterestRate is less than DEFAULT_MAX_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("maxInterestRate.lessThan=" + DEFAULT_MAX_INTEREST_RATE);

        // Get all the interestConfigList where maxInterestRate is less than UPDATED_MAX_INTEREST_RATE
        defaultInterestConfigShouldBeFound("maxInterestRate.lessThan=" + UPDATED_MAX_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByMaxInterestRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where maxInterestRate is greater than DEFAULT_MAX_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("maxInterestRate.greaterThan=" + DEFAULT_MAX_INTEREST_RATE);

        // Get all the interestConfigList where maxInterestRate is greater than SMALLER_MAX_INTEREST_RATE
        defaultInterestConfigShouldBeFound("maxInterestRate.greaterThan=" + SMALLER_MAX_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByExtendedInterestRateIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where extendedInterestRate equals to DEFAULT_EXTENDED_INTEREST_RATE
        defaultInterestConfigShouldBeFound("extendedInterestRate.equals=" + DEFAULT_EXTENDED_INTEREST_RATE);

        // Get all the interestConfigList where extendedInterestRate equals to UPDATED_EXTENDED_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("extendedInterestRate.equals=" + UPDATED_EXTENDED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByExtendedInterestRateIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where extendedInterestRate in DEFAULT_EXTENDED_INTEREST_RATE or UPDATED_EXTENDED_INTEREST_RATE
        defaultInterestConfigShouldBeFound(
            "extendedInterestRate.in=" + DEFAULT_EXTENDED_INTEREST_RATE + "," + UPDATED_EXTENDED_INTEREST_RATE
        );

        // Get all the interestConfigList where extendedInterestRate equals to UPDATED_EXTENDED_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("extendedInterestRate.in=" + UPDATED_EXTENDED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByExtendedInterestRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where extendedInterestRate is not null
        defaultInterestConfigShouldBeFound("extendedInterestRate.specified=true");

        // Get all the interestConfigList where extendedInterestRate is null
        defaultInterestConfigShouldNotBeFound("extendedInterestRate.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByExtendedInterestRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where extendedInterestRate is greater than or equal to DEFAULT_EXTENDED_INTEREST_RATE
        defaultInterestConfigShouldBeFound("extendedInterestRate.greaterThanOrEqual=" + DEFAULT_EXTENDED_INTEREST_RATE);

        // Get all the interestConfigList where extendedInterestRate is greater than or equal to UPDATED_EXTENDED_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("extendedInterestRate.greaterThanOrEqual=" + UPDATED_EXTENDED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByExtendedInterestRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where extendedInterestRate is less than or equal to DEFAULT_EXTENDED_INTEREST_RATE
        defaultInterestConfigShouldBeFound("extendedInterestRate.lessThanOrEqual=" + DEFAULT_EXTENDED_INTEREST_RATE);

        // Get all the interestConfigList where extendedInterestRate is less than or equal to SMALLER_EXTENDED_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("extendedInterestRate.lessThanOrEqual=" + SMALLER_EXTENDED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByExtendedInterestRateIsLessThanSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where extendedInterestRate is less than DEFAULT_EXTENDED_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("extendedInterestRate.lessThan=" + DEFAULT_EXTENDED_INTEREST_RATE);

        // Get all the interestConfigList where extendedInterestRate is less than UPDATED_EXTENDED_INTEREST_RATE
        defaultInterestConfigShouldBeFound("extendedInterestRate.lessThan=" + UPDATED_EXTENDED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByExtendedInterestRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where extendedInterestRate is greater than DEFAULT_EXTENDED_INTEREST_RATE
        defaultInterestConfigShouldNotBeFound("extendedInterestRate.greaterThan=" + DEFAULT_EXTENDED_INTEREST_RATE);

        // Get all the interestConfigList where extendedInterestRate is greater than SMALLER_EXTENDED_INTEREST_RATE
        defaultInterestConfigShouldBeFound("extendedInterestRate.greaterThan=" + SMALLER_EXTENDED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsBySurchargeRateIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where surchargeRate equals to DEFAULT_SURCHARGE_RATE
        defaultInterestConfigShouldBeFound("surchargeRate.equals=" + DEFAULT_SURCHARGE_RATE);

        // Get all the interestConfigList where surchargeRate equals to UPDATED_SURCHARGE_RATE
        defaultInterestConfigShouldNotBeFound("surchargeRate.equals=" + UPDATED_SURCHARGE_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsBySurchargeRateIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where surchargeRate in DEFAULT_SURCHARGE_RATE or UPDATED_SURCHARGE_RATE
        defaultInterestConfigShouldBeFound("surchargeRate.in=" + DEFAULT_SURCHARGE_RATE + "," + UPDATED_SURCHARGE_RATE);

        // Get all the interestConfigList where surchargeRate equals to UPDATED_SURCHARGE_RATE
        defaultInterestConfigShouldNotBeFound("surchargeRate.in=" + UPDATED_SURCHARGE_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsBySurchargeRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where surchargeRate is not null
        defaultInterestConfigShouldBeFound("surchargeRate.specified=true");

        // Get all the interestConfigList where surchargeRate is null
        defaultInterestConfigShouldNotBeFound("surchargeRate.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsBySurchargeRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where surchargeRate is greater than or equal to DEFAULT_SURCHARGE_RATE
        defaultInterestConfigShouldBeFound("surchargeRate.greaterThanOrEqual=" + DEFAULT_SURCHARGE_RATE);

        // Get all the interestConfigList where surchargeRate is greater than or equal to UPDATED_SURCHARGE_RATE
        defaultInterestConfigShouldNotBeFound("surchargeRate.greaterThanOrEqual=" + UPDATED_SURCHARGE_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsBySurchargeRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where surchargeRate is less than or equal to DEFAULT_SURCHARGE_RATE
        defaultInterestConfigShouldBeFound("surchargeRate.lessThanOrEqual=" + DEFAULT_SURCHARGE_RATE);

        // Get all the interestConfigList where surchargeRate is less than or equal to SMALLER_SURCHARGE_RATE
        defaultInterestConfigShouldNotBeFound("surchargeRate.lessThanOrEqual=" + SMALLER_SURCHARGE_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsBySurchargeRateIsLessThanSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where surchargeRate is less than DEFAULT_SURCHARGE_RATE
        defaultInterestConfigShouldNotBeFound("surchargeRate.lessThan=" + DEFAULT_SURCHARGE_RATE);

        // Get all the interestConfigList where surchargeRate is less than UPDATED_SURCHARGE_RATE
        defaultInterestConfigShouldBeFound("surchargeRate.lessThan=" + UPDATED_SURCHARGE_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsBySurchargeRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where surchargeRate is greater than DEFAULT_SURCHARGE_RATE
        defaultInterestConfigShouldNotBeFound("surchargeRate.greaterThan=" + DEFAULT_SURCHARGE_RATE);

        // Get all the interestConfigList where surchargeRate is greater than SMALLER_SURCHARGE_RATE
        defaultInterestConfigShouldBeFound("surchargeRate.greaterThan=" + SMALLER_SURCHARGE_RATE);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where isDeleted equals to DEFAULT_IS_DELETED
        defaultInterestConfigShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the interestConfigList where isDeleted equals to UPDATED_IS_DELETED
        defaultInterestConfigShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultInterestConfigShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the interestConfigList where isDeleted equals to UPDATED_IS_DELETED
        defaultInterestConfigShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where isDeleted is not null
        defaultInterestConfigShouldBeFound("isDeleted.specified=true");

        // Get all the interestConfigList where isDeleted is null
        defaultInterestConfigShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultInterestConfigShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the interestConfigList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultInterestConfigShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultInterestConfigShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the interestConfigList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultInterestConfigShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where lastModified is not null
        defaultInterestConfigShouldBeFound("lastModified.specified=true");

        // Get all the interestConfigList where lastModified is null
        defaultInterestConfigShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultInterestConfigShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the interestConfigList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultInterestConfigShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultInterestConfigShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the interestConfigList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultInterestConfigShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where lastModifiedBy is not null
        defaultInterestConfigShouldBeFound("lastModifiedBy.specified=true");

        // Get all the interestConfigList where lastModifiedBy is null
        defaultInterestConfigShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultInterestConfigShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the interestConfigList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultInterestConfigShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultInterestConfigShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the interestConfigList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultInterestConfigShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultInterestConfigShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the interestConfigList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultInterestConfigShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultInterestConfigShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the interestConfigList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultInterestConfigShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField1 is not null
        defaultInterestConfigShouldBeFound("freeField1.specified=true");

        // Get all the interestConfigList where freeField1 is null
        defaultInterestConfigShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultInterestConfigShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the interestConfigList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultInterestConfigShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultInterestConfigShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the interestConfigList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultInterestConfigShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultInterestConfigShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the interestConfigList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultInterestConfigShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultInterestConfigShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the interestConfigList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultInterestConfigShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField2 is not null
        defaultInterestConfigShouldBeFound("freeField2.specified=true");

        // Get all the interestConfigList where freeField2 is null
        defaultInterestConfigShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultInterestConfigShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the interestConfigList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultInterestConfigShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultInterestConfigShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the interestConfigList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultInterestConfigShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultInterestConfigShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the interestConfigList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultInterestConfigShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultInterestConfigShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the interestConfigList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultInterestConfigShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField3 is not null
        defaultInterestConfigShouldBeFound("freeField3.specified=true");

        // Get all the interestConfigList where freeField3 is null
        defaultInterestConfigShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultInterestConfigShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the interestConfigList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultInterestConfigShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultInterestConfigShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the interestConfigList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultInterestConfigShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultInterestConfigShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the interestConfigList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultInterestConfigShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultInterestConfigShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the interestConfigList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultInterestConfigShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField4 is not null
        defaultInterestConfigShouldBeFound("freeField4.specified=true");

        // Get all the interestConfigList where freeField4 is null
        defaultInterestConfigShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultInterestConfigShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the interestConfigList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultInterestConfigShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultInterestConfigShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the interestConfigList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultInterestConfigShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultInterestConfigShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the interestConfigList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultInterestConfigShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultInterestConfigShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the interestConfigList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultInterestConfigShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField5 is not null
        defaultInterestConfigShouldBeFound("freeField5.specified=true");

        // Get all the interestConfigList where freeField5 is null
        defaultInterestConfigShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultInterestConfigShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the interestConfigList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultInterestConfigShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultInterestConfigShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the interestConfigList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultInterestConfigShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultInterestConfigShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the interestConfigList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultInterestConfigShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultInterestConfigShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the interestConfigList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultInterestConfigShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField6 is not null
        defaultInterestConfigShouldBeFound("freeField6.specified=true");

        // Get all the interestConfigList where freeField6 is null
        defaultInterestConfigShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultInterestConfigShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the interestConfigList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultInterestConfigShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        // Get all the interestConfigList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultInterestConfigShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the interestConfigList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultInterestConfigShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllInterestConfigsByProductIsEqualToSomething() throws Exception {
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            interestConfigRepository.saveAndFlush(interestConfig);
            product = ProductResourceIT.createEntity(em);
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        interestConfig.setProduct(product);
        interestConfigRepository.saveAndFlush(interestConfig);
        Long productId = product.getId();

        // Get all the interestConfigList where product equals to productId
        defaultInterestConfigShouldBeFound("productId.equals=" + productId);

        // Get all the interestConfigList where product equals to (productId + 1)
        defaultInterestConfigShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInterestConfigShouldBeFound(String filter) throws Exception {
        restInterestConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interestConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].interestBasis").value(hasItem(DEFAULT_INTEREST_BASIS)))
            .andExpect(jsonPath("$.[*].emiBasis").value(hasItem(DEFAULT_EMI_BASIS)))
            .andExpect(jsonPath("$.[*].interestType").value(hasItem(DEFAULT_INTEREST_TYPE.toString())))
            .andExpect(jsonPath("$.[*].intrAccrualFreq").value(hasItem(DEFAULT_INTR_ACCRUAL_FREQ.toString())))
            .andExpect(jsonPath("$.[*].penalInterestRate").value(hasItem(DEFAULT_PENAL_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].penalInterestBasis").value(hasItem(DEFAULT_PENAL_INTEREST_BASIS)))
            .andExpect(jsonPath("$.[*].penalAccountingBasis").value(hasItem(DEFAULT_PENAL_ACCOUNTING_BASIS)))
            .andExpect(jsonPath("$.[*].minInterestRate").value(hasItem(DEFAULT_MIN_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxInterestRate").value(hasItem(DEFAULT_MAX_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].extendedInterestRate").value(hasItem(DEFAULT_EXTENDED_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].surchargeRate").value(hasItem(DEFAULT_SURCHARGE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));

        // Check, that the count call also returns 1
        restInterestConfigMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInterestConfigShouldNotBeFound(String filter) throws Exception {
        restInterestConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInterestConfigMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingInterestConfig() throws Exception {
        // Get the interestConfig
        restInterestConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInterestConfig() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        int databaseSizeBeforeUpdate = interestConfigRepository.findAll().size();

        // Update the interestConfig
        InterestConfig updatedInterestConfig = interestConfigRepository.findById(interestConfig.getId()).get();
        // Disconnect from session so that the updates on updatedInterestConfig are not directly saved in db
        em.detach(updatedInterestConfig);
        updatedInterestConfig
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .interestBasis(UPDATED_INTEREST_BASIS)
            .emiBasis(UPDATED_EMI_BASIS)
            .interestType(UPDATED_INTEREST_TYPE)
            .intrAccrualFreq(UPDATED_INTR_ACCRUAL_FREQ)
            .penalInterestRate(UPDATED_PENAL_INTEREST_RATE)
            .penalInterestBasis(UPDATED_PENAL_INTEREST_BASIS)
            .penalAccountingBasis(UPDATED_PENAL_ACCOUNTING_BASIS)
            .minInterestRate(UPDATED_MIN_INTEREST_RATE)
            .maxInterestRate(UPDATED_MAX_INTEREST_RATE)
            .extendedInterestRate(UPDATED_EXTENDED_INTEREST_RATE)
            .surchargeRate(UPDATED_SURCHARGE_RATE)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        InterestConfigDTO interestConfigDTO = interestConfigMapper.toDto(updatedInterestConfig);

        restInterestConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, interestConfigDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interestConfigDTO))
            )
            .andExpect(status().isOk());

        // Validate the InterestConfig in the database
        List<InterestConfig> interestConfigList = interestConfigRepository.findAll();
        assertThat(interestConfigList).hasSize(databaseSizeBeforeUpdate);
        InterestConfig testInterestConfig = interestConfigList.get(interestConfigList.size() - 1);
        assertThat(testInterestConfig.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testInterestConfig.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testInterestConfig.getInterestBasis()).isEqualTo(UPDATED_INTEREST_BASIS);
        assertThat(testInterestConfig.getEmiBasis()).isEqualTo(UPDATED_EMI_BASIS);
        assertThat(testInterestConfig.getInterestType()).isEqualTo(UPDATED_INTEREST_TYPE);
        assertThat(testInterestConfig.getIntrAccrualFreq()).isEqualTo(UPDATED_INTR_ACCRUAL_FREQ);
        assertThat(testInterestConfig.getPenalInterestRate()).isEqualTo(UPDATED_PENAL_INTEREST_RATE);
        assertThat(testInterestConfig.getPenalInterestBasis()).isEqualTo(UPDATED_PENAL_INTEREST_BASIS);
        assertThat(testInterestConfig.getPenalAccountingBasis()).isEqualTo(UPDATED_PENAL_ACCOUNTING_BASIS);
        assertThat(testInterestConfig.getMinInterestRate()).isEqualTo(UPDATED_MIN_INTEREST_RATE);
        assertThat(testInterestConfig.getMaxInterestRate()).isEqualTo(UPDATED_MAX_INTEREST_RATE);
        assertThat(testInterestConfig.getExtendedInterestRate()).isEqualTo(UPDATED_EXTENDED_INTEREST_RATE);
        assertThat(testInterestConfig.getSurchargeRate()).isEqualTo(UPDATED_SURCHARGE_RATE);
        assertThat(testInterestConfig.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testInterestConfig.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testInterestConfig.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testInterestConfig.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testInterestConfig.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testInterestConfig.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testInterestConfig.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testInterestConfig.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testInterestConfig.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingInterestConfig() throws Exception {
        int databaseSizeBeforeUpdate = interestConfigRepository.findAll().size();
        interestConfig.setId(count.incrementAndGet());

        // Create the InterestConfig
        InterestConfigDTO interestConfigDTO = interestConfigMapper.toDto(interestConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterestConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, interestConfigDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interestConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InterestConfig in the database
        List<InterestConfig> interestConfigList = interestConfigRepository.findAll();
        assertThat(interestConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInterestConfig() throws Exception {
        int databaseSizeBeforeUpdate = interestConfigRepository.findAll().size();
        interestConfig.setId(count.incrementAndGet());

        // Create the InterestConfig
        InterestConfigDTO interestConfigDTO = interestConfigMapper.toDto(interestConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(interestConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InterestConfig in the database
        List<InterestConfig> interestConfigList = interestConfigRepository.findAll();
        assertThat(interestConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInterestConfig() throws Exception {
        int databaseSizeBeforeUpdate = interestConfigRepository.findAll().size();
        interestConfig.setId(count.incrementAndGet());

        // Create the InterestConfig
        InterestConfigDTO interestConfigDTO = interestConfigMapper.toDto(interestConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestConfigMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(interestConfigDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InterestConfig in the database
        List<InterestConfig> interestConfigList = interestConfigRepository.findAll();
        assertThat(interestConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInterestConfigWithPatch() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        int databaseSizeBeforeUpdate = interestConfigRepository.findAll().size();

        // Update the interestConfig using partial update
        InterestConfig partialUpdatedInterestConfig = new InterestConfig();
        partialUpdatedInterestConfig.setId(interestConfig.getId());

        partialUpdatedInterestConfig
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .interestBasis(UPDATED_INTEREST_BASIS)
            .emiBasis(UPDATED_EMI_BASIS)
            .interestType(UPDATED_INTEREST_TYPE)
            .intrAccrualFreq(UPDATED_INTR_ACCRUAL_FREQ)
            .penalInterestRate(UPDATED_PENAL_INTEREST_RATE)
            .penalAccountingBasis(UPDATED_PENAL_ACCOUNTING_BASIS)
            .minInterestRate(UPDATED_MIN_INTEREST_RATE)
            .extendedInterestRate(UPDATED_EXTENDED_INTEREST_RATE)
            .surchargeRate(UPDATED_SURCHARGE_RATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);

        restInterestConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInterestConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInterestConfig))
            )
            .andExpect(status().isOk());

        // Validate the InterestConfig in the database
        List<InterestConfig> interestConfigList = interestConfigRepository.findAll();
        assertThat(interestConfigList).hasSize(databaseSizeBeforeUpdate);
        InterestConfig testInterestConfig = interestConfigList.get(interestConfigList.size() - 1);
        assertThat(testInterestConfig.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testInterestConfig.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testInterestConfig.getInterestBasis()).isEqualTo(UPDATED_INTEREST_BASIS);
        assertThat(testInterestConfig.getEmiBasis()).isEqualTo(UPDATED_EMI_BASIS);
        assertThat(testInterestConfig.getInterestType()).isEqualTo(UPDATED_INTEREST_TYPE);
        assertThat(testInterestConfig.getIntrAccrualFreq()).isEqualTo(UPDATED_INTR_ACCRUAL_FREQ);
        assertThat(testInterestConfig.getPenalInterestRate()).isEqualTo(UPDATED_PENAL_INTEREST_RATE);
        assertThat(testInterestConfig.getPenalInterestBasis()).isEqualTo(DEFAULT_PENAL_INTEREST_BASIS);
        assertThat(testInterestConfig.getPenalAccountingBasis()).isEqualTo(UPDATED_PENAL_ACCOUNTING_BASIS);
        assertThat(testInterestConfig.getMinInterestRate()).isEqualTo(UPDATED_MIN_INTEREST_RATE);
        assertThat(testInterestConfig.getMaxInterestRate()).isEqualTo(DEFAULT_MAX_INTEREST_RATE);
        assertThat(testInterestConfig.getExtendedInterestRate()).isEqualTo(UPDATED_EXTENDED_INTEREST_RATE);
        assertThat(testInterestConfig.getSurchargeRate()).isEqualTo(UPDATED_SURCHARGE_RATE);
        assertThat(testInterestConfig.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testInterestConfig.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testInterestConfig.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testInterestConfig.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testInterestConfig.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testInterestConfig.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testInterestConfig.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testInterestConfig.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testInterestConfig.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateInterestConfigWithPatch() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        int databaseSizeBeforeUpdate = interestConfigRepository.findAll().size();

        // Update the interestConfig using partial update
        InterestConfig partialUpdatedInterestConfig = new InterestConfig();
        partialUpdatedInterestConfig.setId(interestConfig.getId());

        partialUpdatedInterestConfig
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .interestBasis(UPDATED_INTEREST_BASIS)
            .emiBasis(UPDATED_EMI_BASIS)
            .interestType(UPDATED_INTEREST_TYPE)
            .intrAccrualFreq(UPDATED_INTR_ACCRUAL_FREQ)
            .penalInterestRate(UPDATED_PENAL_INTEREST_RATE)
            .penalInterestBasis(UPDATED_PENAL_INTEREST_BASIS)
            .penalAccountingBasis(UPDATED_PENAL_ACCOUNTING_BASIS)
            .minInterestRate(UPDATED_MIN_INTEREST_RATE)
            .maxInterestRate(UPDATED_MAX_INTEREST_RATE)
            .extendedInterestRate(UPDATED_EXTENDED_INTEREST_RATE)
            .surchargeRate(UPDATED_SURCHARGE_RATE)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restInterestConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInterestConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInterestConfig))
            )
            .andExpect(status().isOk());

        // Validate the InterestConfig in the database
        List<InterestConfig> interestConfigList = interestConfigRepository.findAll();
        assertThat(interestConfigList).hasSize(databaseSizeBeforeUpdate);
        InterestConfig testInterestConfig = interestConfigList.get(interestConfigList.size() - 1);
        assertThat(testInterestConfig.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testInterestConfig.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testInterestConfig.getInterestBasis()).isEqualTo(UPDATED_INTEREST_BASIS);
        assertThat(testInterestConfig.getEmiBasis()).isEqualTo(UPDATED_EMI_BASIS);
        assertThat(testInterestConfig.getInterestType()).isEqualTo(UPDATED_INTEREST_TYPE);
        assertThat(testInterestConfig.getIntrAccrualFreq()).isEqualTo(UPDATED_INTR_ACCRUAL_FREQ);
        assertThat(testInterestConfig.getPenalInterestRate()).isEqualTo(UPDATED_PENAL_INTEREST_RATE);
        assertThat(testInterestConfig.getPenalInterestBasis()).isEqualTo(UPDATED_PENAL_INTEREST_BASIS);
        assertThat(testInterestConfig.getPenalAccountingBasis()).isEqualTo(UPDATED_PENAL_ACCOUNTING_BASIS);
        assertThat(testInterestConfig.getMinInterestRate()).isEqualTo(UPDATED_MIN_INTEREST_RATE);
        assertThat(testInterestConfig.getMaxInterestRate()).isEqualTo(UPDATED_MAX_INTEREST_RATE);
        assertThat(testInterestConfig.getExtendedInterestRate()).isEqualTo(UPDATED_EXTENDED_INTEREST_RATE);
        assertThat(testInterestConfig.getSurchargeRate()).isEqualTo(UPDATED_SURCHARGE_RATE);
        assertThat(testInterestConfig.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testInterestConfig.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testInterestConfig.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testInterestConfig.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testInterestConfig.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testInterestConfig.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testInterestConfig.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testInterestConfig.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testInterestConfig.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingInterestConfig() throws Exception {
        int databaseSizeBeforeUpdate = interestConfigRepository.findAll().size();
        interestConfig.setId(count.incrementAndGet());

        // Create the InterestConfig
        InterestConfigDTO interestConfigDTO = interestConfigMapper.toDto(interestConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterestConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, interestConfigDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(interestConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InterestConfig in the database
        List<InterestConfig> interestConfigList = interestConfigRepository.findAll();
        assertThat(interestConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInterestConfig() throws Exception {
        int databaseSizeBeforeUpdate = interestConfigRepository.findAll().size();
        interestConfig.setId(count.incrementAndGet());

        // Create the InterestConfig
        InterestConfigDTO interestConfigDTO = interestConfigMapper.toDto(interestConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(interestConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the InterestConfig in the database
        List<InterestConfig> interestConfigList = interestConfigRepository.findAll();
        assertThat(interestConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInterestConfig() throws Exception {
        int databaseSizeBeforeUpdate = interestConfigRepository.findAll().size();
        interestConfig.setId(count.incrementAndGet());

        // Create the InterestConfig
        InterestConfigDTO interestConfigDTO = interestConfigMapper.toDto(interestConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInterestConfigMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(interestConfigDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the InterestConfig in the database
        List<InterestConfig> interestConfigList = interestConfigRepository.findAll();
        assertThat(interestConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInterestConfig() throws Exception {
        // Initialize the database
        interestConfigRepository.saveAndFlush(interestConfig);

        int databaseSizeBeforeDelete = interestConfigRepository.findAll().size();

        // Delete the interestConfig
        restInterestConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, interestConfig.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InterestConfig> interestConfigList = interestConfigRepository.findAll();
        assertThat(interestConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
