package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.Organisation;
import com.techvg.los.domain.SchemesDetails;
import com.techvg.los.repository.SchemesDetailsRepository;
import com.techvg.los.service.criteria.SchemesDetailsCriteria;
import com.techvg.los.service.dto.SchemesDetailsDTO;
import com.techvg.los.service.mapper.SchemesDetailsMapper;
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
 * Integration tests for the {@link SchemesDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SchemesDetailsResourceIT {

    private static final Integer DEFAULT_FD_DURATION_DAYS = 1;
    private static final Integer UPDATED_FD_DURATION_DAYS = 2;
    private static final Integer SMALLER_FD_DURATION_DAYS = 1 - 1;

    private static final Double DEFAULT_INTEREST = 1D;
    private static final Double UPDATED_INTEREST = 2D;
    private static final Double SMALLER_INTEREST = 1D - 1D;

    private static final Integer DEFAULT_LOCK_IN_PERIOD = 1;
    private static final Integer UPDATED_LOCK_IN_PERIOD = 2;
    private static final Integer SMALLER_LOCK_IN_PERIOD = 1 - 1;

    private static final String DEFAULT_SCHEME_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCHEME_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_RD_DURATION_MONTHS = 1;
    private static final Integer UPDATED_RD_DURATION_MONTHS = 2;
    private static final Integer SMALLER_RD_DURATION_MONTHS = 1 - 1;

    private static final Boolean DEFAULT_REINVEST_INTEREST = false;
    private static final Boolean UPDATED_REINVEST_INTEREST = true;

    private static final Double DEFAULT_MIN_AMOUNT = 1D;
    private static final Double UPDATED_MIN_AMOUNT = 2D;
    private static final Double SMALLER_MIN_AMOUNT = 1D - 1D;

    private static final Instant DEFAULT_LAST_DAY_OF_SCHEAM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_DAY_OF_SCHEAM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/schemes-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SchemesDetailsRepository schemesDetailsRepository;

    @Autowired
    private SchemesDetailsMapper schemesDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSchemesDetailsMockMvc;

    private SchemesDetails schemesDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchemesDetails createEntity(EntityManager em) {
        SchemesDetails schemesDetails = new SchemesDetails()
            .fdDurationDays(DEFAULT_FD_DURATION_DAYS)
            .interest(DEFAULT_INTEREST)
            .lockInPeriod(DEFAULT_LOCK_IN_PERIOD)
            .schemeName(DEFAULT_SCHEME_NAME)
            .rdDurationMonths(DEFAULT_RD_DURATION_MONTHS)
            .reinvestInterest(DEFAULT_REINVEST_INTEREST)
            .minAmount(DEFAULT_MIN_AMOUNT)
            .lastDayOfScheam(DEFAULT_LAST_DAY_OF_SCHEAM)
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
        return schemesDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SchemesDetails createUpdatedEntity(EntityManager em) {
        SchemesDetails schemesDetails = new SchemesDetails()
            .fdDurationDays(UPDATED_FD_DURATION_DAYS)
            .interest(UPDATED_INTEREST)
            .lockInPeriod(UPDATED_LOCK_IN_PERIOD)
            .schemeName(UPDATED_SCHEME_NAME)
            .rdDurationMonths(UPDATED_RD_DURATION_MONTHS)
            .reinvestInterest(UPDATED_REINVEST_INTEREST)
            .minAmount(UPDATED_MIN_AMOUNT)
            .lastDayOfScheam(UPDATED_LAST_DAY_OF_SCHEAM)
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
        return schemesDetails;
    }

    @BeforeEach
    public void initTest() {
        schemesDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createSchemesDetails() throws Exception {
        int databaseSizeBeforeCreate = schemesDetailsRepository.findAll().size();
        // Create the SchemesDetails
        SchemesDetailsDTO schemesDetailsDTO = schemesDetailsMapper.toDto(schemesDetails);
        restSchemesDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schemesDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SchemesDetails in the database
        List<SchemesDetails> schemesDetailsList = schemesDetailsRepository.findAll();
        assertThat(schemesDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        SchemesDetails testSchemesDetails = schemesDetailsList.get(schemesDetailsList.size() - 1);
        assertThat(testSchemesDetails.getFdDurationDays()).isEqualTo(DEFAULT_FD_DURATION_DAYS);
        assertThat(testSchemesDetails.getInterest()).isEqualTo(DEFAULT_INTEREST);
        assertThat(testSchemesDetails.getLockInPeriod()).isEqualTo(DEFAULT_LOCK_IN_PERIOD);
        assertThat(testSchemesDetails.getSchemeName()).isEqualTo(DEFAULT_SCHEME_NAME);
        assertThat(testSchemesDetails.getRdDurationMonths()).isEqualTo(DEFAULT_RD_DURATION_MONTHS);
        assertThat(testSchemesDetails.getReinvestInterest()).isEqualTo(DEFAULT_REINVEST_INTEREST);
        assertThat(testSchemesDetails.getMinAmount()).isEqualTo(DEFAULT_MIN_AMOUNT);
        assertThat(testSchemesDetails.getLastDayOfScheam()).isEqualTo(DEFAULT_LAST_DAY_OF_SCHEAM);
        assertThat(testSchemesDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSchemesDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testSchemesDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSchemesDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSchemesDetails.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSchemesDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testSchemesDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSchemesDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testSchemesDetails.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testSchemesDetails.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void createSchemesDetailsWithExistingId() throws Exception {
        // Create the SchemesDetails with an existing ID
        schemesDetails.setId(1L);
        SchemesDetailsDTO schemesDetailsDTO = schemesDetailsMapper.toDto(schemesDetails);

        int databaseSizeBeforeCreate = schemesDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchemesDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schemesDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchemesDetails in the database
        List<SchemesDetails> schemesDetailsList = schemesDetailsRepository.findAll();
        assertThat(schemesDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSchemesDetails() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList
        restSchemesDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schemesDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].fdDurationDays").value(hasItem(DEFAULT_FD_DURATION_DAYS)))
            .andExpect(jsonPath("$.[*].interest").value(hasItem(DEFAULT_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].lockInPeriod").value(hasItem(DEFAULT_LOCK_IN_PERIOD)))
            .andExpect(jsonPath("$.[*].schemeName").value(hasItem(DEFAULT_SCHEME_NAME)))
            .andExpect(jsonPath("$.[*].rdDurationMonths").value(hasItem(DEFAULT_RD_DURATION_MONTHS)))
            .andExpect(jsonPath("$.[*].reinvestInterest").value(hasItem(DEFAULT_REINVEST_INTEREST.booleanValue())))
            .andExpect(jsonPath("$.[*].minAmount").value(hasItem(DEFAULT_MIN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].lastDayOfScheam").value(hasItem(DEFAULT_LAST_DAY_OF_SCHEAM.toString())))
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
    void getSchemesDetails() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get the schemesDetails
        restSchemesDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, schemesDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(schemesDetails.getId().intValue()))
            .andExpect(jsonPath("$.fdDurationDays").value(DEFAULT_FD_DURATION_DAYS))
            .andExpect(jsonPath("$.interest").value(DEFAULT_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.lockInPeriod").value(DEFAULT_LOCK_IN_PERIOD))
            .andExpect(jsonPath("$.schemeName").value(DEFAULT_SCHEME_NAME))
            .andExpect(jsonPath("$.rdDurationMonths").value(DEFAULT_RD_DURATION_MONTHS))
            .andExpect(jsonPath("$.reinvestInterest").value(DEFAULT_REINVEST_INTEREST.booleanValue()))
            .andExpect(jsonPath("$.minAmount").value(DEFAULT_MIN_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.lastDayOfScheam").value(DEFAULT_LAST_DAY_OF_SCHEAM.toString()))
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
    void getSchemesDetailsByIdFiltering() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        Long id = schemesDetails.getId();

        defaultSchemesDetailsShouldBeFound("id.equals=" + id);
        defaultSchemesDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultSchemesDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSchemesDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultSchemesDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSchemesDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFdDurationDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where fdDurationDays equals to DEFAULT_FD_DURATION_DAYS
        defaultSchemesDetailsShouldBeFound("fdDurationDays.equals=" + DEFAULT_FD_DURATION_DAYS);

        // Get all the schemesDetailsList where fdDurationDays equals to UPDATED_FD_DURATION_DAYS
        defaultSchemesDetailsShouldNotBeFound("fdDurationDays.equals=" + UPDATED_FD_DURATION_DAYS);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFdDurationDaysIsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where fdDurationDays in DEFAULT_FD_DURATION_DAYS or UPDATED_FD_DURATION_DAYS
        defaultSchemesDetailsShouldBeFound("fdDurationDays.in=" + DEFAULT_FD_DURATION_DAYS + "," + UPDATED_FD_DURATION_DAYS);

        // Get all the schemesDetailsList where fdDurationDays equals to UPDATED_FD_DURATION_DAYS
        defaultSchemesDetailsShouldNotBeFound("fdDurationDays.in=" + UPDATED_FD_DURATION_DAYS);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFdDurationDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where fdDurationDays is not null
        defaultSchemesDetailsShouldBeFound("fdDurationDays.specified=true");

        // Get all the schemesDetailsList where fdDurationDays is null
        defaultSchemesDetailsShouldNotBeFound("fdDurationDays.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFdDurationDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where fdDurationDays is greater than or equal to DEFAULT_FD_DURATION_DAYS
        defaultSchemesDetailsShouldBeFound("fdDurationDays.greaterThanOrEqual=" + DEFAULT_FD_DURATION_DAYS);

        // Get all the schemesDetailsList where fdDurationDays is greater than or equal to UPDATED_FD_DURATION_DAYS
        defaultSchemesDetailsShouldNotBeFound("fdDurationDays.greaterThanOrEqual=" + UPDATED_FD_DURATION_DAYS);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFdDurationDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where fdDurationDays is less than or equal to DEFAULT_FD_DURATION_DAYS
        defaultSchemesDetailsShouldBeFound("fdDurationDays.lessThanOrEqual=" + DEFAULT_FD_DURATION_DAYS);

        // Get all the schemesDetailsList where fdDurationDays is less than or equal to SMALLER_FD_DURATION_DAYS
        defaultSchemesDetailsShouldNotBeFound("fdDurationDays.lessThanOrEqual=" + SMALLER_FD_DURATION_DAYS);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFdDurationDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where fdDurationDays is less than DEFAULT_FD_DURATION_DAYS
        defaultSchemesDetailsShouldNotBeFound("fdDurationDays.lessThan=" + DEFAULT_FD_DURATION_DAYS);

        // Get all the schemesDetailsList where fdDurationDays is less than UPDATED_FD_DURATION_DAYS
        defaultSchemesDetailsShouldBeFound("fdDurationDays.lessThan=" + UPDATED_FD_DURATION_DAYS);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFdDurationDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where fdDurationDays is greater than DEFAULT_FD_DURATION_DAYS
        defaultSchemesDetailsShouldNotBeFound("fdDurationDays.greaterThan=" + DEFAULT_FD_DURATION_DAYS);

        // Get all the schemesDetailsList where fdDurationDays is greater than SMALLER_FD_DURATION_DAYS
        defaultSchemesDetailsShouldBeFound("fdDurationDays.greaterThan=" + SMALLER_FD_DURATION_DAYS);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where interest equals to DEFAULT_INTEREST
        defaultSchemesDetailsShouldBeFound("interest.equals=" + DEFAULT_INTEREST);

        // Get all the schemesDetailsList where interest equals to UPDATED_INTEREST
        defaultSchemesDetailsShouldNotBeFound("interest.equals=" + UPDATED_INTEREST);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByInterestIsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where interest in DEFAULT_INTEREST or UPDATED_INTEREST
        defaultSchemesDetailsShouldBeFound("interest.in=" + DEFAULT_INTEREST + "," + UPDATED_INTEREST);

        // Get all the schemesDetailsList where interest equals to UPDATED_INTEREST
        defaultSchemesDetailsShouldNotBeFound("interest.in=" + UPDATED_INTEREST);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where interest is not null
        defaultSchemesDetailsShouldBeFound("interest.specified=true");

        // Get all the schemesDetailsList where interest is null
        defaultSchemesDetailsShouldNotBeFound("interest.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByInterestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where interest is greater than or equal to DEFAULT_INTEREST
        defaultSchemesDetailsShouldBeFound("interest.greaterThanOrEqual=" + DEFAULT_INTEREST);

        // Get all the schemesDetailsList where interest is greater than or equal to UPDATED_INTEREST
        defaultSchemesDetailsShouldNotBeFound("interest.greaterThanOrEqual=" + UPDATED_INTEREST);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByInterestIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where interest is less than or equal to DEFAULT_INTEREST
        defaultSchemesDetailsShouldBeFound("interest.lessThanOrEqual=" + DEFAULT_INTEREST);

        // Get all the schemesDetailsList where interest is less than or equal to SMALLER_INTEREST
        defaultSchemesDetailsShouldNotBeFound("interest.lessThanOrEqual=" + SMALLER_INTEREST);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByInterestIsLessThanSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where interest is less than DEFAULT_INTEREST
        defaultSchemesDetailsShouldNotBeFound("interest.lessThan=" + DEFAULT_INTEREST);

        // Get all the schemesDetailsList where interest is less than UPDATED_INTEREST
        defaultSchemesDetailsShouldBeFound("interest.lessThan=" + UPDATED_INTEREST);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByInterestIsGreaterThanSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where interest is greater than DEFAULT_INTEREST
        defaultSchemesDetailsShouldNotBeFound("interest.greaterThan=" + DEFAULT_INTEREST);

        // Get all the schemesDetailsList where interest is greater than SMALLER_INTEREST
        defaultSchemesDetailsShouldBeFound("interest.greaterThan=" + SMALLER_INTEREST);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLockInPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lockInPeriod equals to DEFAULT_LOCK_IN_PERIOD
        defaultSchemesDetailsShouldBeFound("lockInPeriod.equals=" + DEFAULT_LOCK_IN_PERIOD);

        // Get all the schemesDetailsList where lockInPeriod equals to UPDATED_LOCK_IN_PERIOD
        defaultSchemesDetailsShouldNotBeFound("lockInPeriod.equals=" + UPDATED_LOCK_IN_PERIOD);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLockInPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lockInPeriod in DEFAULT_LOCK_IN_PERIOD or UPDATED_LOCK_IN_PERIOD
        defaultSchemesDetailsShouldBeFound("lockInPeriod.in=" + DEFAULT_LOCK_IN_PERIOD + "," + UPDATED_LOCK_IN_PERIOD);

        // Get all the schemesDetailsList where lockInPeriod equals to UPDATED_LOCK_IN_PERIOD
        defaultSchemesDetailsShouldNotBeFound("lockInPeriod.in=" + UPDATED_LOCK_IN_PERIOD);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLockInPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lockInPeriod is not null
        defaultSchemesDetailsShouldBeFound("lockInPeriod.specified=true");

        // Get all the schemesDetailsList where lockInPeriod is null
        defaultSchemesDetailsShouldNotBeFound("lockInPeriod.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLockInPeriodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lockInPeriod is greater than or equal to DEFAULT_LOCK_IN_PERIOD
        defaultSchemesDetailsShouldBeFound("lockInPeriod.greaterThanOrEqual=" + DEFAULT_LOCK_IN_PERIOD);

        // Get all the schemesDetailsList where lockInPeriod is greater than or equal to UPDATED_LOCK_IN_PERIOD
        defaultSchemesDetailsShouldNotBeFound("lockInPeriod.greaterThanOrEqual=" + UPDATED_LOCK_IN_PERIOD);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLockInPeriodIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lockInPeriod is less than or equal to DEFAULT_LOCK_IN_PERIOD
        defaultSchemesDetailsShouldBeFound("lockInPeriod.lessThanOrEqual=" + DEFAULT_LOCK_IN_PERIOD);

        // Get all the schemesDetailsList where lockInPeriod is less than or equal to SMALLER_LOCK_IN_PERIOD
        defaultSchemesDetailsShouldNotBeFound("lockInPeriod.lessThanOrEqual=" + SMALLER_LOCK_IN_PERIOD);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLockInPeriodIsLessThanSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lockInPeriod is less than DEFAULT_LOCK_IN_PERIOD
        defaultSchemesDetailsShouldNotBeFound("lockInPeriod.lessThan=" + DEFAULT_LOCK_IN_PERIOD);

        // Get all the schemesDetailsList where lockInPeriod is less than UPDATED_LOCK_IN_PERIOD
        defaultSchemesDetailsShouldBeFound("lockInPeriod.lessThan=" + UPDATED_LOCK_IN_PERIOD);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLockInPeriodIsGreaterThanSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lockInPeriod is greater than DEFAULT_LOCK_IN_PERIOD
        defaultSchemesDetailsShouldNotBeFound("lockInPeriod.greaterThan=" + DEFAULT_LOCK_IN_PERIOD);

        // Get all the schemesDetailsList where lockInPeriod is greater than SMALLER_LOCK_IN_PERIOD
        defaultSchemesDetailsShouldBeFound("lockInPeriod.greaterThan=" + SMALLER_LOCK_IN_PERIOD);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsBySchemeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where schemeName equals to DEFAULT_SCHEME_NAME
        defaultSchemesDetailsShouldBeFound("schemeName.equals=" + DEFAULT_SCHEME_NAME);

        // Get all the schemesDetailsList where schemeName equals to UPDATED_SCHEME_NAME
        defaultSchemesDetailsShouldNotBeFound("schemeName.equals=" + UPDATED_SCHEME_NAME);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsBySchemeNameIsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where schemeName in DEFAULT_SCHEME_NAME or UPDATED_SCHEME_NAME
        defaultSchemesDetailsShouldBeFound("schemeName.in=" + DEFAULT_SCHEME_NAME + "," + UPDATED_SCHEME_NAME);

        // Get all the schemesDetailsList where schemeName equals to UPDATED_SCHEME_NAME
        defaultSchemesDetailsShouldNotBeFound("schemeName.in=" + UPDATED_SCHEME_NAME);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsBySchemeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where schemeName is not null
        defaultSchemesDetailsShouldBeFound("schemeName.specified=true");

        // Get all the schemesDetailsList where schemeName is null
        defaultSchemesDetailsShouldNotBeFound("schemeName.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsBySchemeNameContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where schemeName contains DEFAULT_SCHEME_NAME
        defaultSchemesDetailsShouldBeFound("schemeName.contains=" + DEFAULT_SCHEME_NAME);

        // Get all the schemesDetailsList where schemeName contains UPDATED_SCHEME_NAME
        defaultSchemesDetailsShouldNotBeFound("schemeName.contains=" + UPDATED_SCHEME_NAME);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsBySchemeNameNotContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where schemeName does not contain DEFAULT_SCHEME_NAME
        defaultSchemesDetailsShouldNotBeFound("schemeName.doesNotContain=" + DEFAULT_SCHEME_NAME);

        // Get all the schemesDetailsList where schemeName does not contain UPDATED_SCHEME_NAME
        defaultSchemesDetailsShouldBeFound("schemeName.doesNotContain=" + UPDATED_SCHEME_NAME);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByRdDurationMonthsIsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where rdDurationMonths equals to DEFAULT_RD_DURATION_MONTHS
        defaultSchemesDetailsShouldBeFound("rdDurationMonths.equals=" + DEFAULT_RD_DURATION_MONTHS);

        // Get all the schemesDetailsList where rdDurationMonths equals to UPDATED_RD_DURATION_MONTHS
        defaultSchemesDetailsShouldNotBeFound("rdDurationMonths.equals=" + UPDATED_RD_DURATION_MONTHS);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByRdDurationMonthsIsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where rdDurationMonths in DEFAULT_RD_DURATION_MONTHS or UPDATED_RD_DURATION_MONTHS
        defaultSchemesDetailsShouldBeFound("rdDurationMonths.in=" + DEFAULT_RD_DURATION_MONTHS + "," + UPDATED_RD_DURATION_MONTHS);

        // Get all the schemesDetailsList where rdDurationMonths equals to UPDATED_RD_DURATION_MONTHS
        defaultSchemesDetailsShouldNotBeFound("rdDurationMonths.in=" + UPDATED_RD_DURATION_MONTHS);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByRdDurationMonthsIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where rdDurationMonths is not null
        defaultSchemesDetailsShouldBeFound("rdDurationMonths.specified=true");

        // Get all the schemesDetailsList where rdDurationMonths is null
        defaultSchemesDetailsShouldNotBeFound("rdDurationMonths.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByRdDurationMonthsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where rdDurationMonths is greater than or equal to DEFAULT_RD_DURATION_MONTHS
        defaultSchemesDetailsShouldBeFound("rdDurationMonths.greaterThanOrEqual=" + DEFAULT_RD_DURATION_MONTHS);

        // Get all the schemesDetailsList where rdDurationMonths is greater than or equal to UPDATED_RD_DURATION_MONTHS
        defaultSchemesDetailsShouldNotBeFound("rdDurationMonths.greaterThanOrEqual=" + UPDATED_RD_DURATION_MONTHS);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByRdDurationMonthsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where rdDurationMonths is less than or equal to DEFAULT_RD_DURATION_MONTHS
        defaultSchemesDetailsShouldBeFound("rdDurationMonths.lessThanOrEqual=" + DEFAULT_RD_DURATION_MONTHS);

        // Get all the schemesDetailsList where rdDurationMonths is less than or equal to SMALLER_RD_DURATION_MONTHS
        defaultSchemesDetailsShouldNotBeFound("rdDurationMonths.lessThanOrEqual=" + SMALLER_RD_DURATION_MONTHS);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByRdDurationMonthsIsLessThanSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where rdDurationMonths is less than DEFAULT_RD_DURATION_MONTHS
        defaultSchemesDetailsShouldNotBeFound("rdDurationMonths.lessThan=" + DEFAULT_RD_DURATION_MONTHS);

        // Get all the schemesDetailsList where rdDurationMonths is less than UPDATED_RD_DURATION_MONTHS
        defaultSchemesDetailsShouldBeFound("rdDurationMonths.lessThan=" + UPDATED_RD_DURATION_MONTHS);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByRdDurationMonthsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where rdDurationMonths is greater than DEFAULT_RD_DURATION_MONTHS
        defaultSchemesDetailsShouldNotBeFound("rdDurationMonths.greaterThan=" + DEFAULT_RD_DURATION_MONTHS);

        // Get all the schemesDetailsList where rdDurationMonths is greater than SMALLER_RD_DURATION_MONTHS
        defaultSchemesDetailsShouldBeFound("rdDurationMonths.greaterThan=" + SMALLER_RD_DURATION_MONTHS);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByReinvestInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where reinvestInterest equals to DEFAULT_REINVEST_INTEREST
        defaultSchemesDetailsShouldBeFound("reinvestInterest.equals=" + DEFAULT_REINVEST_INTEREST);

        // Get all the schemesDetailsList where reinvestInterest equals to UPDATED_REINVEST_INTEREST
        defaultSchemesDetailsShouldNotBeFound("reinvestInterest.equals=" + UPDATED_REINVEST_INTEREST);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByReinvestInterestIsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where reinvestInterest in DEFAULT_REINVEST_INTEREST or UPDATED_REINVEST_INTEREST
        defaultSchemesDetailsShouldBeFound("reinvestInterest.in=" + DEFAULT_REINVEST_INTEREST + "," + UPDATED_REINVEST_INTEREST);

        // Get all the schemesDetailsList where reinvestInterest equals to UPDATED_REINVEST_INTEREST
        defaultSchemesDetailsShouldNotBeFound("reinvestInterest.in=" + UPDATED_REINVEST_INTEREST);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByReinvestInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where reinvestInterest is not null
        defaultSchemesDetailsShouldBeFound("reinvestInterest.specified=true");

        // Get all the schemesDetailsList where reinvestInterest is null
        defaultSchemesDetailsShouldNotBeFound("reinvestInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByMinAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where minAmount equals to DEFAULT_MIN_AMOUNT
        defaultSchemesDetailsShouldBeFound("minAmount.equals=" + DEFAULT_MIN_AMOUNT);

        // Get all the schemesDetailsList where minAmount equals to UPDATED_MIN_AMOUNT
        defaultSchemesDetailsShouldNotBeFound("minAmount.equals=" + UPDATED_MIN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByMinAmountIsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where minAmount in DEFAULT_MIN_AMOUNT or UPDATED_MIN_AMOUNT
        defaultSchemesDetailsShouldBeFound("minAmount.in=" + DEFAULT_MIN_AMOUNT + "," + UPDATED_MIN_AMOUNT);

        // Get all the schemesDetailsList where minAmount equals to UPDATED_MIN_AMOUNT
        defaultSchemesDetailsShouldNotBeFound("minAmount.in=" + UPDATED_MIN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByMinAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where minAmount is not null
        defaultSchemesDetailsShouldBeFound("minAmount.specified=true");

        // Get all the schemesDetailsList where minAmount is null
        defaultSchemesDetailsShouldNotBeFound("minAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByMinAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where minAmount is greater than or equal to DEFAULT_MIN_AMOUNT
        defaultSchemesDetailsShouldBeFound("minAmount.greaterThanOrEqual=" + DEFAULT_MIN_AMOUNT);

        // Get all the schemesDetailsList where minAmount is greater than or equal to UPDATED_MIN_AMOUNT
        defaultSchemesDetailsShouldNotBeFound("minAmount.greaterThanOrEqual=" + UPDATED_MIN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByMinAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where minAmount is less than or equal to DEFAULT_MIN_AMOUNT
        defaultSchemesDetailsShouldBeFound("minAmount.lessThanOrEqual=" + DEFAULT_MIN_AMOUNT);

        // Get all the schemesDetailsList where minAmount is less than or equal to SMALLER_MIN_AMOUNT
        defaultSchemesDetailsShouldNotBeFound("minAmount.lessThanOrEqual=" + SMALLER_MIN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByMinAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where minAmount is less than DEFAULT_MIN_AMOUNT
        defaultSchemesDetailsShouldNotBeFound("minAmount.lessThan=" + DEFAULT_MIN_AMOUNT);

        // Get all the schemesDetailsList where minAmount is less than UPDATED_MIN_AMOUNT
        defaultSchemesDetailsShouldBeFound("minAmount.lessThan=" + UPDATED_MIN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByMinAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where minAmount is greater than DEFAULT_MIN_AMOUNT
        defaultSchemesDetailsShouldNotBeFound("minAmount.greaterThan=" + DEFAULT_MIN_AMOUNT);

        // Get all the schemesDetailsList where minAmount is greater than SMALLER_MIN_AMOUNT
        defaultSchemesDetailsShouldBeFound("minAmount.greaterThan=" + SMALLER_MIN_AMOUNT);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLastDayOfScheamIsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lastDayOfScheam equals to DEFAULT_LAST_DAY_OF_SCHEAM
        defaultSchemesDetailsShouldBeFound("lastDayOfScheam.equals=" + DEFAULT_LAST_DAY_OF_SCHEAM);

        // Get all the schemesDetailsList where lastDayOfScheam equals to UPDATED_LAST_DAY_OF_SCHEAM
        defaultSchemesDetailsShouldNotBeFound("lastDayOfScheam.equals=" + UPDATED_LAST_DAY_OF_SCHEAM);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLastDayOfScheamIsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lastDayOfScheam in DEFAULT_LAST_DAY_OF_SCHEAM or UPDATED_LAST_DAY_OF_SCHEAM
        defaultSchemesDetailsShouldBeFound("lastDayOfScheam.in=" + DEFAULT_LAST_DAY_OF_SCHEAM + "," + UPDATED_LAST_DAY_OF_SCHEAM);

        // Get all the schemesDetailsList where lastDayOfScheam equals to UPDATED_LAST_DAY_OF_SCHEAM
        defaultSchemesDetailsShouldNotBeFound("lastDayOfScheam.in=" + UPDATED_LAST_DAY_OF_SCHEAM);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLastDayOfScheamIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lastDayOfScheam is not null
        defaultSchemesDetailsShouldBeFound("lastDayOfScheam.specified=true");

        // Get all the schemesDetailsList where lastDayOfScheam is null
        defaultSchemesDetailsShouldNotBeFound("lastDayOfScheam.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultSchemesDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the schemesDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSchemesDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultSchemesDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the schemesDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultSchemesDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lastModified is not null
        defaultSchemesDetailsShouldBeFound("lastModified.specified=true");

        // Get all the schemesDetailsList where lastModified is null
        defaultSchemesDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultSchemesDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the schemesDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSchemesDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultSchemesDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the schemesDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultSchemesDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lastModifiedBy is not null
        defaultSchemesDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the schemesDetailsList where lastModifiedBy is null
        defaultSchemesDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultSchemesDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the schemesDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultSchemesDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultSchemesDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the schemesDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultSchemesDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where createdBy equals to DEFAULT_CREATED_BY
        defaultSchemesDetailsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the schemesDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultSchemesDetailsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSchemesDetailsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the schemesDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultSchemesDetailsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where createdBy is not null
        defaultSchemesDetailsShouldBeFound("createdBy.specified=true");

        // Get all the schemesDetailsList where createdBy is null
        defaultSchemesDetailsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where createdBy contains DEFAULT_CREATED_BY
        defaultSchemesDetailsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the schemesDetailsList where createdBy contains UPDATED_CREATED_BY
        defaultSchemesDetailsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultSchemesDetailsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the schemesDetailsList where createdBy does not contain UPDATED_CREATED_BY
        defaultSchemesDetailsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where createdOn equals to DEFAULT_CREATED_ON
        defaultSchemesDetailsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the schemesDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultSchemesDetailsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultSchemesDetailsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the schemesDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultSchemesDetailsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where createdOn is not null
        defaultSchemesDetailsShouldBeFound("createdOn.specified=true");

        // Get all the schemesDetailsList where createdOn is null
        defaultSchemesDetailsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultSchemesDetailsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the schemesDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultSchemesDetailsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultSchemesDetailsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the schemesDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultSchemesDetailsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where isDeleted is not null
        defaultSchemesDetailsShouldBeFound("isDeleted.specified=true");

        // Get all the schemesDetailsList where isDeleted is null
        defaultSchemesDetailsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultSchemesDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the schemesDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSchemesDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultSchemesDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the schemesDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSchemesDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField1 is not null
        defaultSchemesDetailsShouldBeFound("freeField1.specified=true");

        // Get all the schemesDetailsList where freeField1 is null
        defaultSchemesDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultSchemesDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the schemesDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultSchemesDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultSchemesDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the schemesDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultSchemesDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultSchemesDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the schemesDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSchemesDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultSchemesDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the schemesDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSchemesDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField2 is not null
        defaultSchemesDetailsShouldBeFound("freeField2.specified=true");

        // Get all the schemesDetailsList where freeField2 is null
        defaultSchemesDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultSchemesDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the schemesDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultSchemesDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultSchemesDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the schemesDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultSchemesDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultSchemesDetailsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the schemesDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSchemesDetailsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultSchemesDetailsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the schemesDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSchemesDetailsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField3 is not null
        defaultSchemesDetailsShouldBeFound("freeField3.specified=true");

        // Get all the schemesDetailsList where freeField3 is null
        defaultSchemesDetailsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultSchemesDetailsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the schemesDetailsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultSchemesDetailsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultSchemesDetailsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the schemesDetailsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultSchemesDetailsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultSchemesDetailsShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the schemesDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultSchemesDetailsShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultSchemesDetailsShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the schemesDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultSchemesDetailsShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField4 is not null
        defaultSchemesDetailsShouldBeFound("freeField4.specified=true");

        // Get all the schemesDetailsList where freeField4 is null
        defaultSchemesDetailsShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultSchemesDetailsShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the schemesDetailsList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultSchemesDetailsShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultSchemesDetailsShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the schemesDetailsList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultSchemesDetailsShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultSchemesDetailsShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the schemesDetailsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultSchemesDetailsShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultSchemesDetailsShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the schemesDetailsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultSchemesDetailsShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField5 is not null
        defaultSchemesDetailsShouldBeFound("freeField5.specified=true");

        // Get all the schemesDetailsList where freeField5 is null
        defaultSchemesDetailsShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultSchemesDetailsShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the schemesDetailsList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultSchemesDetailsShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        // Get all the schemesDetailsList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultSchemesDetailsShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the schemesDetailsList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultSchemesDetailsShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllSchemesDetailsByOrganisationIsEqualToSomething() throws Exception {
        Organisation organisation;
        if (TestUtil.findAll(em, Organisation.class).isEmpty()) {
            schemesDetailsRepository.saveAndFlush(schemesDetails);
            organisation = OrganisationResourceIT.createEntity(em);
        } else {
            organisation = TestUtil.findAll(em, Organisation.class).get(0);
        }
        em.persist(organisation);
        em.flush();
        schemesDetails.setOrganisation(organisation);
        schemesDetailsRepository.saveAndFlush(schemesDetails);
        Long organisationId = organisation.getId();

        // Get all the schemesDetailsList where organisation equals to organisationId
        defaultSchemesDetailsShouldBeFound("organisationId.equals=" + organisationId);

        // Get all the schemesDetailsList where organisation equals to (organisationId + 1)
        defaultSchemesDetailsShouldNotBeFound("organisationId.equals=" + (organisationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSchemesDetailsShouldBeFound(String filter) throws Exception {
        restSchemesDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schemesDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].fdDurationDays").value(hasItem(DEFAULT_FD_DURATION_DAYS)))
            .andExpect(jsonPath("$.[*].interest").value(hasItem(DEFAULT_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].lockInPeriod").value(hasItem(DEFAULT_LOCK_IN_PERIOD)))
            .andExpect(jsonPath("$.[*].schemeName").value(hasItem(DEFAULT_SCHEME_NAME)))
            .andExpect(jsonPath("$.[*].rdDurationMonths").value(hasItem(DEFAULT_RD_DURATION_MONTHS)))
            .andExpect(jsonPath("$.[*].reinvestInterest").value(hasItem(DEFAULT_REINVEST_INTEREST.booleanValue())))
            .andExpect(jsonPath("$.[*].minAmount").value(hasItem(DEFAULT_MIN_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].lastDayOfScheam").value(hasItem(DEFAULT_LAST_DAY_OF_SCHEAM.toString())))
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
        restSchemesDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSchemesDetailsShouldNotBeFound(String filter) throws Exception {
        restSchemesDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSchemesDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSchemesDetails() throws Exception {
        // Get the schemesDetails
        restSchemesDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSchemesDetails() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        int databaseSizeBeforeUpdate = schemesDetailsRepository.findAll().size();

        // Update the schemesDetails
        SchemesDetails updatedSchemesDetails = schemesDetailsRepository.findById(schemesDetails.getId()).get();
        // Disconnect from session so that the updates on updatedSchemesDetails are not directly saved in db
        em.detach(updatedSchemesDetails);
        updatedSchemesDetails
            .fdDurationDays(UPDATED_FD_DURATION_DAYS)
            .interest(UPDATED_INTEREST)
            .lockInPeriod(UPDATED_LOCK_IN_PERIOD)
            .schemeName(UPDATED_SCHEME_NAME)
            .rdDurationMonths(UPDATED_RD_DURATION_MONTHS)
            .reinvestInterest(UPDATED_REINVEST_INTEREST)
            .minAmount(UPDATED_MIN_AMOUNT)
            .lastDayOfScheam(UPDATED_LAST_DAY_OF_SCHEAM)
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
        SchemesDetailsDTO schemesDetailsDTO = schemesDetailsMapper.toDto(updatedSchemesDetails);

        restSchemesDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schemesDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schemesDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the SchemesDetails in the database
        List<SchemesDetails> schemesDetailsList = schemesDetailsRepository.findAll();
        assertThat(schemesDetailsList).hasSize(databaseSizeBeforeUpdate);
        SchemesDetails testSchemesDetails = schemesDetailsList.get(schemesDetailsList.size() - 1);
        assertThat(testSchemesDetails.getFdDurationDays()).isEqualTo(UPDATED_FD_DURATION_DAYS);
        assertThat(testSchemesDetails.getInterest()).isEqualTo(UPDATED_INTEREST);
        assertThat(testSchemesDetails.getLockInPeriod()).isEqualTo(UPDATED_LOCK_IN_PERIOD);
        assertThat(testSchemesDetails.getSchemeName()).isEqualTo(UPDATED_SCHEME_NAME);
        assertThat(testSchemesDetails.getRdDurationMonths()).isEqualTo(UPDATED_RD_DURATION_MONTHS);
        assertThat(testSchemesDetails.getReinvestInterest()).isEqualTo(UPDATED_REINVEST_INTEREST);
        assertThat(testSchemesDetails.getMinAmount()).isEqualTo(UPDATED_MIN_AMOUNT);
        assertThat(testSchemesDetails.getLastDayOfScheam()).isEqualTo(UPDATED_LAST_DAY_OF_SCHEAM);
        assertThat(testSchemesDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSchemesDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSchemesDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSchemesDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSchemesDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSchemesDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSchemesDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSchemesDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testSchemesDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testSchemesDetails.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void putNonExistingSchemesDetails() throws Exception {
        int databaseSizeBeforeUpdate = schemesDetailsRepository.findAll().size();
        schemesDetails.setId(count.incrementAndGet());

        // Create the SchemesDetails
        SchemesDetailsDTO schemesDetailsDTO = schemesDetailsMapper.toDto(schemesDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchemesDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, schemesDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schemesDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchemesDetails in the database
        List<SchemesDetails> schemesDetailsList = schemesDetailsRepository.findAll();
        assertThat(schemesDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSchemesDetails() throws Exception {
        int databaseSizeBeforeUpdate = schemesDetailsRepository.findAll().size();
        schemesDetails.setId(count.incrementAndGet());

        // Create the SchemesDetails
        SchemesDetailsDTO schemesDetailsDTO = schemesDetailsMapper.toDto(schemesDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchemesDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(schemesDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchemesDetails in the database
        List<SchemesDetails> schemesDetailsList = schemesDetailsRepository.findAll();
        assertThat(schemesDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSchemesDetails() throws Exception {
        int databaseSizeBeforeUpdate = schemesDetailsRepository.findAll().size();
        schemesDetails.setId(count.incrementAndGet());

        // Create the SchemesDetails
        SchemesDetailsDTO schemesDetailsDTO = schemesDetailsMapper.toDto(schemesDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchemesDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(schemesDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SchemesDetails in the database
        List<SchemesDetails> schemesDetailsList = schemesDetailsRepository.findAll();
        assertThat(schemesDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSchemesDetailsWithPatch() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        int databaseSizeBeforeUpdate = schemesDetailsRepository.findAll().size();

        // Update the schemesDetails using partial update
        SchemesDetails partialUpdatedSchemesDetails = new SchemesDetails();
        partialUpdatedSchemesDetails.setId(schemesDetails.getId());

        partialUpdatedSchemesDetails
            .fdDurationDays(UPDATED_FD_DURATION_DAYS)
            .interest(UPDATED_INTEREST)
            .schemeName(UPDATED_SCHEME_NAME)
            .reinvestInterest(UPDATED_REINVEST_INTEREST)
            .minAmount(UPDATED_MIN_AMOUNT)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restSchemesDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchemesDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchemesDetails))
            )
            .andExpect(status().isOk());

        // Validate the SchemesDetails in the database
        List<SchemesDetails> schemesDetailsList = schemesDetailsRepository.findAll();
        assertThat(schemesDetailsList).hasSize(databaseSizeBeforeUpdate);
        SchemesDetails testSchemesDetails = schemesDetailsList.get(schemesDetailsList.size() - 1);
        assertThat(testSchemesDetails.getFdDurationDays()).isEqualTo(UPDATED_FD_DURATION_DAYS);
        assertThat(testSchemesDetails.getInterest()).isEqualTo(UPDATED_INTEREST);
        assertThat(testSchemesDetails.getLockInPeriod()).isEqualTo(DEFAULT_LOCK_IN_PERIOD);
        assertThat(testSchemesDetails.getSchemeName()).isEqualTo(UPDATED_SCHEME_NAME);
        assertThat(testSchemesDetails.getRdDurationMonths()).isEqualTo(DEFAULT_RD_DURATION_MONTHS);
        assertThat(testSchemesDetails.getReinvestInterest()).isEqualTo(UPDATED_REINVEST_INTEREST);
        assertThat(testSchemesDetails.getMinAmount()).isEqualTo(UPDATED_MIN_AMOUNT);
        assertThat(testSchemesDetails.getLastDayOfScheam()).isEqualTo(DEFAULT_LAST_DAY_OF_SCHEAM);
        assertThat(testSchemesDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testSchemesDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSchemesDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSchemesDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testSchemesDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSchemesDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testSchemesDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSchemesDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testSchemesDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testSchemesDetails.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void fullUpdateSchemesDetailsWithPatch() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        int databaseSizeBeforeUpdate = schemesDetailsRepository.findAll().size();

        // Update the schemesDetails using partial update
        SchemesDetails partialUpdatedSchemesDetails = new SchemesDetails();
        partialUpdatedSchemesDetails.setId(schemesDetails.getId());

        partialUpdatedSchemesDetails
            .fdDurationDays(UPDATED_FD_DURATION_DAYS)
            .interest(UPDATED_INTEREST)
            .lockInPeriod(UPDATED_LOCK_IN_PERIOD)
            .schemeName(UPDATED_SCHEME_NAME)
            .rdDurationMonths(UPDATED_RD_DURATION_MONTHS)
            .reinvestInterest(UPDATED_REINVEST_INTEREST)
            .minAmount(UPDATED_MIN_AMOUNT)
            .lastDayOfScheam(UPDATED_LAST_DAY_OF_SCHEAM)
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

        restSchemesDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSchemesDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSchemesDetails))
            )
            .andExpect(status().isOk());

        // Validate the SchemesDetails in the database
        List<SchemesDetails> schemesDetailsList = schemesDetailsRepository.findAll();
        assertThat(schemesDetailsList).hasSize(databaseSizeBeforeUpdate);
        SchemesDetails testSchemesDetails = schemesDetailsList.get(schemesDetailsList.size() - 1);
        assertThat(testSchemesDetails.getFdDurationDays()).isEqualTo(UPDATED_FD_DURATION_DAYS);
        assertThat(testSchemesDetails.getInterest()).isEqualTo(UPDATED_INTEREST);
        assertThat(testSchemesDetails.getLockInPeriod()).isEqualTo(UPDATED_LOCK_IN_PERIOD);
        assertThat(testSchemesDetails.getSchemeName()).isEqualTo(UPDATED_SCHEME_NAME);
        assertThat(testSchemesDetails.getRdDurationMonths()).isEqualTo(UPDATED_RD_DURATION_MONTHS);
        assertThat(testSchemesDetails.getReinvestInterest()).isEqualTo(UPDATED_REINVEST_INTEREST);
        assertThat(testSchemesDetails.getMinAmount()).isEqualTo(UPDATED_MIN_AMOUNT);
        assertThat(testSchemesDetails.getLastDayOfScheam()).isEqualTo(UPDATED_LAST_DAY_OF_SCHEAM);
        assertThat(testSchemesDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testSchemesDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testSchemesDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSchemesDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testSchemesDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSchemesDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSchemesDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSchemesDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testSchemesDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testSchemesDetails.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void patchNonExistingSchemesDetails() throws Exception {
        int databaseSizeBeforeUpdate = schemesDetailsRepository.findAll().size();
        schemesDetails.setId(count.incrementAndGet());

        // Create the SchemesDetails
        SchemesDetailsDTO schemesDetailsDTO = schemesDetailsMapper.toDto(schemesDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchemesDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, schemesDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schemesDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchemesDetails in the database
        List<SchemesDetails> schemesDetailsList = schemesDetailsRepository.findAll();
        assertThat(schemesDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSchemesDetails() throws Exception {
        int databaseSizeBeforeUpdate = schemesDetailsRepository.findAll().size();
        schemesDetails.setId(count.incrementAndGet());

        // Create the SchemesDetails
        SchemesDetailsDTO schemesDetailsDTO = schemesDetailsMapper.toDto(schemesDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchemesDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schemesDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SchemesDetails in the database
        List<SchemesDetails> schemesDetailsList = schemesDetailsRepository.findAll();
        assertThat(schemesDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSchemesDetails() throws Exception {
        int databaseSizeBeforeUpdate = schemesDetailsRepository.findAll().size();
        schemesDetails.setId(count.incrementAndGet());

        // Create the SchemesDetails
        SchemesDetailsDTO schemesDetailsDTO = schemesDetailsMapper.toDto(schemesDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSchemesDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(schemesDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SchemesDetails in the database
        List<SchemesDetails> schemesDetailsList = schemesDetailsRepository.findAll();
        assertThat(schemesDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSchemesDetails() throws Exception {
        // Initialize the database
        schemesDetailsRepository.saveAndFlush(schemesDetails);

        int databaseSizeBeforeDelete = schemesDetailsRepository.findAll().size();

        // Delete the schemesDetails
        restSchemesDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, schemesDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SchemesDetails> schemesDetailsList = schemesDetailsRepository.findAll();
        assertThat(schemesDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
