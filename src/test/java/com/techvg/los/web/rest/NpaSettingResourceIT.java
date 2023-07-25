package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.NpaSetting;
import com.techvg.los.domain.Organisation;
import com.techvg.los.domain.enumeration.NpaClassification;
import com.techvg.los.repository.NpaSettingRepository;
import com.techvg.los.service.criteria.NpaSettingCriteria;
import com.techvg.los.service.dto.NpaSettingDTO;
import com.techvg.los.service.mapper.NpaSettingMapper;
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
 * Integration tests for the {@link NpaSettingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NpaSettingResourceIT {

    private static final NpaClassification DEFAULT_NPA_CLASSIFICATION = NpaClassification.SUB_STANDARD_ASSESTS;
    private static final NpaClassification UPDATED_NPA_CLASSIFICATION = NpaClassification.DOUBTFUL_1;

    private static final Integer DEFAULT_DURATION_START = 1;
    private static final Integer UPDATED_DURATION_START = 2;
    private static final Integer SMALLER_DURATION_START = 1 - 1;

    private static final Integer DEFAULT_DURATION_END = 1;
    private static final Integer UPDATED_DURATION_END = 2;
    private static final Integer SMALLER_DURATION_END = 1 - 1;

    private static final String DEFAULT_PROVISION = "AAAAAAAAAA";
    private static final String UPDATED_PROVISION = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final Integer DEFAULT_INTEREST_RATE = 1;
    private static final Integer UPDATED_INTEREST_RATE = 2;
    private static final Integer SMALLER_INTEREST_RATE = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/npa-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NpaSettingRepository npaSettingRepository;

    @Autowired
    private NpaSettingMapper npaSettingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNpaSettingMockMvc;

    private NpaSetting npaSetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NpaSetting createEntity(EntityManager em) {
        NpaSetting npaSetting = new NpaSetting()
            .npaClassification(DEFAULT_NPA_CLASSIFICATION)
            .durationStart(DEFAULT_DURATION_START)
            .durationEnd(DEFAULT_DURATION_END)
            .provision(DEFAULT_PROVISION)
            .year(DEFAULT_YEAR)
            .interestRate(DEFAULT_INTEREST_RATE)
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
        return npaSetting;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NpaSetting createUpdatedEntity(EntityManager em) {
        NpaSetting npaSetting = new NpaSetting()
            .npaClassification(UPDATED_NPA_CLASSIFICATION)
            .durationStart(UPDATED_DURATION_START)
            .durationEnd(UPDATED_DURATION_END)
            .provision(UPDATED_PROVISION)
            .year(UPDATED_YEAR)
            .interestRate(UPDATED_INTEREST_RATE)
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
        return npaSetting;
    }

    @BeforeEach
    public void initTest() {
        npaSetting = createEntity(em);
    }

    @Test
    @Transactional
    void createNpaSetting() throws Exception {
        int databaseSizeBeforeCreate = npaSettingRepository.findAll().size();
        // Create the NpaSetting
        NpaSettingDTO npaSettingDTO = npaSettingMapper.toDto(npaSetting);
        restNpaSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(npaSettingDTO)))
            .andExpect(status().isCreated());

        // Validate the NpaSetting in the database
        List<NpaSetting> npaSettingList = npaSettingRepository.findAll();
        assertThat(npaSettingList).hasSize(databaseSizeBeforeCreate + 1);
        NpaSetting testNpaSetting = npaSettingList.get(npaSettingList.size() - 1);
        assertThat(testNpaSetting.getNpaClassification()).isEqualTo(DEFAULT_NPA_CLASSIFICATION);
        assertThat(testNpaSetting.getDurationStart()).isEqualTo(DEFAULT_DURATION_START);
        assertThat(testNpaSetting.getDurationEnd()).isEqualTo(DEFAULT_DURATION_END);
        assertThat(testNpaSetting.getProvision()).isEqualTo(DEFAULT_PROVISION);
        assertThat(testNpaSetting.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testNpaSetting.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testNpaSetting.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testNpaSetting.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testNpaSetting.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testNpaSetting.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testNpaSetting.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testNpaSetting.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testNpaSetting.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testNpaSetting.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testNpaSetting.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testNpaSetting.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void createNpaSettingWithExistingId() throws Exception {
        // Create the NpaSetting with an existing ID
        npaSetting.setId(1L);
        NpaSettingDTO npaSettingDTO = npaSettingMapper.toDto(npaSetting);

        int databaseSizeBeforeCreate = npaSettingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNpaSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(npaSettingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NpaSetting in the database
        List<NpaSetting> npaSettingList = npaSettingRepository.findAll();
        assertThat(npaSettingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNpaSettings() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList
        restNpaSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(npaSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].npaClassification").value(hasItem(DEFAULT_NPA_CLASSIFICATION.toString())))
            .andExpect(jsonPath("$.[*].durationStart").value(hasItem(DEFAULT_DURATION_START)))
            .andExpect(jsonPath("$.[*].durationEnd").value(hasItem(DEFAULT_DURATION_END)))
            .andExpect(jsonPath("$.[*].provision").value(hasItem(DEFAULT_PROVISION)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE)))
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
    void getNpaSetting() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get the npaSetting
        restNpaSettingMockMvc
            .perform(get(ENTITY_API_URL_ID, npaSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(npaSetting.getId().intValue()))
            .andExpect(jsonPath("$.npaClassification").value(DEFAULT_NPA_CLASSIFICATION.toString()))
            .andExpect(jsonPath("$.durationStart").value(DEFAULT_DURATION_START))
            .andExpect(jsonPath("$.durationEnd").value(DEFAULT_DURATION_END))
            .andExpect(jsonPath("$.provision").value(DEFAULT_PROVISION))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE))
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
    void getNpaSettingsByIdFiltering() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        Long id = npaSetting.getId();

        defaultNpaSettingShouldBeFound("id.equals=" + id);
        defaultNpaSettingShouldNotBeFound("id.notEquals=" + id);

        defaultNpaSettingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNpaSettingShouldNotBeFound("id.greaterThan=" + id);

        defaultNpaSettingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNpaSettingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByNpaClassificationIsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where npaClassification equals to DEFAULT_NPA_CLASSIFICATION
        defaultNpaSettingShouldBeFound("npaClassification.equals=" + DEFAULT_NPA_CLASSIFICATION);

        // Get all the npaSettingList where npaClassification equals to UPDATED_NPA_CLASSIFICATION
        defaultNpaSettingShouldNotBeFound("npaClassification.equals=" + UPDATED_NPA_CLASSIFICATION);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByNpaClassificationIsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where npaClassification in DEFAULT_NPA_CLASSIFICATION or UPDATED_NPA_CLASSIFICATION
        defaultNpaSettingShouldBeFound("npaClassification.in=" + DEFAULT_NPA_CLASSIFICATION + "," + UPDATED_NPA_CLASSIFICATION);

        // Get all the npaSettingList where npaClassification equals to UPDATED_NPA_CLASSIFICATION
        defaultNpaSettingShouldNotBeFound("npaClassification.in=" + UPDATED_NPA_CLASSIFICATION);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByNpaClassificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where npaClassification is not null
        defaultNpaSettingShouldBeFound("npaClassification.specified=true");

        // Get all the npaSettingList where npaClassification is null
        defaultNpaSettingShouldNotBeFound("npaClassification.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByDurationStartIsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where durationStart equals to DEFAULT_DURATION_START
        defaultNpaSettingShouldBeFound("durationStart.equals=" + DEFAULT_DURATION_START);

        // Get all the npaSettingList where durationStart equals to UPDATED_DURATION_START
        defaultNpaSettingShouldNotBeFound("durationStart.equals=" + UPDATED_DURATION_START);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByDurationStartIsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where durationStart in DEFAULT_DURATION_START or UPDATED_DURATION_START
        defaultNpaSettingShouldBeFound("durationStart.in=" + DEFAULT_DURATION_START + "," + UPDATED_DURATION_START);

        // Get all the npaSettingList where durationStart equals to UPDATED_DURATION_START
        defaultNpaSettingShouldNotBeFound("durationStart.in=" + UPDATED_DURATION_START);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByDurationStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where durationStart is not null
        defaultNpaSettingShouldBeFound("durationStart.specified=true");

        // Get all the npaSettingList where durationStart is null
        defaultNpaSettingShouldNotBeFound("durationStart.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByDurationStartIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where durationStart is greater than or equal to DEFAULT_DURATION_START
        defaultNpaSettingShouldBeFound("durationStart.greaterThanOrEqual=" + DEFAULT_DURATION_START);

        // Get all the npaSettingList where durationStart is greater than or equal to UPDATED_DURATION_START
        defaultNpaSettingShouldNotBeFound("durationStart.greaterThanOrEqual=" + UPDATED_DURATION_START);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByDurationStartIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where durationStart is less than or equal to DEFAULT_DURATION_START
        defaultNpaSettingShouldBeFound("durationStart.lessThanOrEqual=" + DEFAULT_DURATION_START);

        // Get all the npaSettingList where durationStart is less than or equal to SMALLER_DURATION_START
        defaultNpaSettingShouldNotBeFound("durationStart.lessThanOrEqual=" + SMALLER_DURATION_START);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByDurationStartIsLessThanSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where durationStart is less than DEFAULT_DURATION_START
        defaultNpaSettingShouldNotBeFound("durationStart.lessThan=" + DEFAULT_DURATION_START);

        // Get all the npaSettingList where durationStart is less than UPDATED_DURATION_START
        defaultNpaSettingShouldBeFound("durationStart.lessThan=" + UPDATED_DURATION_START);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByDurationStartIsGreaterThanSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where durationStart is greater than DEFAULT_DURATION_START
        defaultNpaSettingShouldNotBeFound("durationStart.greaterThan=" + DEFAULT_DURATION_START);

        // Get all the npaSettingList where durationStart is greater than SMALLER_DURATION_START
        defaultNpaSettingShouldBeFound("durationStart.greaterThan=" + SMALLER_DURATION_START);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByDurationEndIsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where durationEnd equals to DEFAULT_DURATION_END
        defaultNpaSettingShouldBeFound("durationEnd.equals=" + DEFAULT_DURATION_END);

        // Get all the npaSettingList where durationEnd equals to UPDATED_DURATION_END
        defaultNpaSettingShouldNotBeFound("durationEnd.equals=" + UPDATED_DURATION_END);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByDurationEndIsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where durationEnd in DEFAULT_DURATION_END or UPDATED_DURATION_END
        defaultNpaSettingShouldBeFound("durationEnd.in=" + DEFAULT_DURATION_END + "," + UPDATED_DURATION_END);

        // Get all the npaSettingList where durationEnd equals to UPDATED_DURATION_END
        defaultNpaSettingShouldNotBeFound("durationEnd.in=" + UPDATED_DURATION_END);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByDurationEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where durationEnd is not null
        defaultNpaSettingShouldBeFound("durationEnd.specified=true");

        // Get all the npaSettingList where durationEnd is null
        defaultNpaSettingShouldNotBeFound("durationEnd.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByDurationEndIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where durationEnd is greater than or equal to DEFAULT_DURATION_END
        defaultNpaSettingShouldBeFound("durationEnd.greaterThanOrEqual=" + DEFAULT_DURATION_END);

        // Get all the npaSettingList where durationEnd is greater than or equal to UPDATED_DURATION_END
        defaultNpaSettingShouldNotBeFound("durationEnd.greaterThanOrEqual=" + UPDATED_DURATION_END);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByDurationEndIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where durationEnd is less than or equal to DEFAULT_DURATION_END
        defaultNpaSettingShouldBeFound("durationEnd.lessThanOrEqual=" + DEFAULT_DURATION_END);

        // Get all the npaSettingList where durationEnd is less than or equal to SMALLER_DURATION_END
        defaultNpaSettingShouldNotBeFound("durationEnd.lessThanOrEqual=" + SMALLER_DURATION_END);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByDurationEndIsLessThanSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where durationEnd is less than DEFAULT_DURATION_END
        defaultNpaSettingShouldNotBeFound("durationEnd.lessThan=" + DEFAULT_DURATION_END);

        // Get all the npaSettingList where durationEnd is less than UPDATED_DURATION_END
        defaultNpaSettingShouldBeFound("durationEnd.lessThan=" + UPDATED_DURATION_END);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByDurationEndIsGreaterThanSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where durationEnd is greater than DEFAULT_DURATION_END
        defaultNpaSettingShouldNotBeFound("durationEnd.greaterThan=" + DEFAULT_DURATION_END);

        // Get all the npaSettingList where durationEnd is greater than SMALLER_DURATION_END
        defaultNpaSettingShouldBeFound("durationEnd.greaterThan=" + SMALLER_DURATION_END);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByProvisionIsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where provision equals to DEFAULT_PROVISION
        defaultNpaSettingShouldBeFound("provision.equals=" + DEFAULT_PROVISION);

        // Get all the npaSettingList where provision equals to UPDATED_PROVISION
        defaultNpaSettingShouldNotBeFound("provision.equals=" + UPDATED_PROVISION);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByProvisionIsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where provision in DEFAULT_PROVISION or UPDATED_PROVISION
        defaultNpaSettingShouldBeFound("provision.in=" + DEFAULT_PROVISION + "," + UPDATED_PROVISION);

        // Get all the npaSettingList where provision equals to UPDATED_PROVISION
        defaultNpaSettingShouldNotBeFound("provision.in=" + UPDATED_PROVISION);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByProvisionIsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where provision is not null
        defaultNpaSettingShouldBeFound("provision.specified=true");

        // Get all the npaSettingList where provision is null
        defaultNpaSettingShouldNotBeFound("provision.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByProvisionContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where provision contains DEFAULT_PROVISION
        defaultNpaSettingShouldBeFound("provision.contains=" + DEFAULT_PROVISION);

        // Get all the npaSettingList where provision contains UPDATED_PROVISION
        defaultNpaSettingShouldNotBeFound("provision.contains=" + UPDATED_PROVISION);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByProvisionNotContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where provision does not contain DEFAULT_PROVISION
        defaultNpaSettingShouldNotBeFound("provision.doesNotContain=" + DEFAULT_PROVISION);

        // Get all the npaSettingList where provision does not contain UPDATED_PROVISION
        defaultNpaSettingShouldBeFound("provision.doesNotContain=" + UPDATED_PROVISION);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where year equals to DEFAULT_YEAR
        defaultNpaSettingShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the npaSettingList where year equals to UPDATED_YEAR
        defaultNpaSettingShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultNpaSettingShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the npaSettingList where year equals to UPDATED_YEAR
        defaultNpaSettingShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where year is not null
        defaultNpaSettingShouldBeFound("year.specified=true");

        // Get all the npaSettingList where year is null
        defaultNpaSettingShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where year is greater than or equal to DEFAULT_YEAR
        defaultNpaSettingShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the npaSettingList where year is greater than or equal to UPDATED_YEAR
        defaultNpaSettingShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where year is less than or equal to DEFAULT_YEAR
        defaultNpaSettingShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the npaSettingList where year is less than or equal to SMALLER_YEAR
        defaultNpaSettingShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where year is less than DEFAULT_YEAR
        defaultNpaSettingShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the npaSettingList where year is less than UPDATED_YEAR
        defaultNpaSettingShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where year is greater than DEFAULT_YEAR
        defaultNpaSettingShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the npaSettingList where year is greater than SMALLER_YEAR
        defaultNpaSettingShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByInterestRateIsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where interestRate equals to DEFAULT_INTEREST_RATE
        defaultNpaSettingShouldBeFound("interestRate.equals=" + DEFAULT_INTEREST_RATE);

        // Get all the npaSettingList where interestRate equals to UPDATED_INTEREST_RATE
        defaultNpaSettingShouldNotBeFound("interestRate.equals=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByInterestRateIsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where interestRate in DEFAULT_INTEREST_RATE or UPDATED_INTEREST_RATE
        defaultNpaSettingShouldBeFound("interestRate.in=" + DEFAULT_INTEREST_RATE + "," + UPDATED_INTEREST_RATE);

        // Get all the npaSettingList where interestRate equals to UPDATED_INTEREST_RATE
        defaultNpaSettingShouldNotBeFound("interestRate.in=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByInterestRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where interestRate is not null
        defaultNpaSettingShouldBeFound("interestRate.specified=true");

        // Get all the npaSettingList where interestRate is null
        defaultNpaSettingShouldNotBeFound("interestRate.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByInterestRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where interestRate is greater than or equal to DEFAULT_INTEREST_RATE
        defaultNpaSettingShouldBeFound("interestRate.greaterThanOrEqual=" + DEFAULT_INTEREST_RATE);

        // Get all the npaSettingList where interestRate is greater than or equal to UPDATED_INTEREST_RATE
        defaultNpaSettingShouldNotBeFound("interestRate.greaterThanOrEqual=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByInterestRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where interestRate is less than or equal to DEFAULT_INTEREST_RATE
        defaultNpaSettingShouldBeFound("interestRate.lessThanOrEqual=" + DEFAULT_INTEREST_RATE);

        // Get all the npaSettingList where interestRate is less than or equal to SMALLER_INTEREST_RATE
        defaultNpaSettingShouldNotBeFound("interestRate.lessThanOrEqual=" + SMALLER_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByInterestRateIsLessThanSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where interestRate is less than DEFAULT_INTEREST_RATE
        defaultNpaSettingShouldNotBeFound("interestRate.lessThan=" + DEFAULT_INTEREST_RATE);

        // Get all the npaSettingList where interestRate is less than UPDATED_INTEREST_RATE
        defaultNpaSettingShouldBeFound("interestRate.lessThan=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByInterestRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where interestRate is greater than DEFAULT_INTEREST_RATE
        defaultNpaSettingShouldNotBeFound("interestRate.greaterThan=" + DEFAULT_INTEREST_RATE);

        // Get all the npaSettingList where interestRate is greater than SMALLER_INTEREST_RATE
        defaultNpaSettingShouldBeFound("interestRate.greaterThan=" + SMALLER_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultNpaSettingShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the npaSettingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultNpaSettingShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultNpaSettingShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the npaSettingList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultNpaSettingShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where lastModified is not null
        defaultNpaSettingShouldBeFound("lastModified.specified=true");

        // Get all the npaSettingList where lastModified is null
        defaultNpaSettingShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultNpaSettingShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the npaSettingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultNpaSettingShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultNpaSettingShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the npaSettingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultNpaSettingShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where lastModifiedBy is not null
        defaultNpaSettingShouldBeFound("lastModifiedBy.specified=true");

        // Get all the npaSettingList where lastModifiedBy is null
        defaultNpaSettingShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultNpaSettingShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the npaSettingList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultNpaSettingShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultNpaSettingShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the npaSettingList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultNpaSettingShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where createdBy equals to DEFAULT_CREATED_BY
        defaultNpaSettingShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the npaSettingList where createdBy equals to UPDATED_CREATED_BY
        defaultNpaSettingShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultNpaSettingShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the npaSettingList where createdBy equals to UPDATED_CREATED_BY
        defaultNpaSettingShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where createdBy is not null
        defaultNpaSettingShouldBeFound("createdBy.specified=true");

        // Get all the npaSettingList where createdBy is null
        defaultNpaSettingShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where createdBy contains DEFAULT_CREATED_BY
        defaultNpaSettingShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the npaSettingList where createdBy contains UPDATED_CREATED_BY
        defaultNpaSettingShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where createdBy does not contain DEFAULT_CREATED_BY
        defaultNpaSettingShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the npaSettingList where createdBy does not contain UPDATED_CREATED_BY
        defaultNpaSettingShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where createdOn equals to DEFAULT_CREATED_ON
        defaultNpaSettingShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the npaSettingList where createdOn equals to UPDATED_CREATED_ON
        defaultNpaSettingShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultNpaSettingShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the npaSettingList where createdOn equals to UPDATED_CREATED_ON
        defaultNpaSettingShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where createdOn is not null
        defaultNpaSettingShouldBeFound("createdOn.specified=true");

        // Get all the npaSettingList where createdOn is null
        defaultNpaSettingShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where isDeleted equals to DEFAULT_IS_DELETED
        defaultNpaSettingShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the npaSettingList where isDeleted equals to UPDATED_IS_DELETED
        defaultNpaSettingShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultNpaSettingShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the npaSettingList where isDeleted equals to UPDATED_IS_DELETED
        defaultNpaSettingShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where isDeleted is not null
        defaultNpaSettingShouldBeFound("isDeleted.specified=true");

        // Get all the npaSettingList where isDeleted is null
        defaultNpaSettingShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultNpaSettingShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the npaSettingList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultNpaSettingShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultNpaSettingShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the npaSettingList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultNpaSettingShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField1 is not null
        defaultNpaSettingShouldBeFound("freeField1.specified=true");

        // Get all the npaSettingList where freeField1 is null
        defaultNpaSettingShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultNpaSettingShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the npaSettingList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultNpaSettingShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultNpaSettingShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the npaSettingList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultNpaSettingShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultNpaSettingShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the npaSettingList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultNpaSettingShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultNpaSettingShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the npaSettingList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultNpaSettingShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField2 is not null
        defaultNpaSettingShouldBeFound("freeField2.specified=true");

        // Get all the npaSettingList where freeField2 is null
        defaultNpaSettingShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultNpaSettingShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the npaSettingList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultNpaSettingShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultNpaSettingShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the npaSettingList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultNpaSettingShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultNpaSettingShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the npaSettingList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultNpaSettingShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultNpaSettingShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the npaSettingList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultNpaSettingShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField3 is not null
        defaultNpaSettingShouldBeFound("freeField3.specified=true");

        // Get all the npaSettingList where freeField3 is null
        defaultNpaSettingShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultNpaSettingShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the npaSettingList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultNpaSettingShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultNpaSettingShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the npaSettingList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultNpaSettingShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultNpaSettingShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the npaSettingList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultNpaSettingShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultNpaSettingShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the npaSettingList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultNpaSettingShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField4 is not null
        defaultNpaSettingShouldBeFound("freeField4.specified=true");

        // Get all the npaSettingList where freeField4 is null
        defaultNpaSettingShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultNpaSettingShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the npaSettingList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultNpaSettingShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultNpaSettingShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the npaSettingList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultNpaSettingShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultNpaSettingShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the npaSettingList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultNpaSettingShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultNpaSettingShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the npaSettingList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultNpaSettingShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField5 is not null
        defaultNpaSettingShouldBeFound("freeField5.specified=true");

        // Get all the npaSettingList where freeField5 is null
        defaultNpaSettingShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultNpaSettingShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the npaSettingList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultNpaSettingShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        // Get all the npaSettingList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultNpaSettingShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the npaSettingList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultNpaSettingShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllNpaSettingsByOrganisationIsEqualToSomething() throws Exception {
        Organisation organisation;
        if (TestUtil.findAll(em, Organisation.class).isEmpty()) {
            npaSettingRepository.saveAndFlush(npaSetting);
            organisation = OrganisationResourceIT.createEntity(em);
        } else {
            organisation = TestUtil.findAll(em, Organisation.class).get(0);
        }
        em.persist(organisation);
        em.flush();
        npaSetting.setOrganisation(organisation);
        npaSettingRepository.saveAndFlush(npaSetting);
        Long organisationId = organisation.getId();

        // Get all the npaSettingList where organisation equals to organisationId
        defaultNpaSettingShouldBeFound("organisationId.equals=" + organisationId);

        // Get all the npaSettingList where organisation equals to (organisationId + 1)
        defaultNpaSettingShouldNotBeFound("organisationId.equals=" + (organisationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNpaSettingShouldBeFound(String filter) throws Exception {
        restNpaSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(npaSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].npaClassification").value(hasItem(DEFAULT_NPA_CLASSIFICATION.toString())))
            .andExpect(jsonPath("$.[*].durationStart").value(hasItem(DEFAULT_DURATION_START)))
            .andExpect(jsonPath("$.[*].durationEnd").value(hasItem(DEFAULT_DURATION_END)))
            .andExpect(jsonPath("$.[*].provision").value(hasItem(DEFAULT_PROVISION)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE)))
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
        restNpaSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNpaSettingShouldNotBeFound(String filter) throws Exception {
        restNpaSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNpaSettingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingNpaSetting() throws Exception {
        // Get the npaSetting
        restNpaSettingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNpaSetting() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        int databaseSizeBeforeUpdate = npaSettingRepository.findAll().size();

        // Update the npaSetting
        NpaSetting updatedNpaSetting = npaSettingRepository.findById(npaSetting.getId()).get();
        // Disconnect from session so that the updates on updatedNpaSetting are not directly saved in db
        em.detach(updatedNpaSetting);
        updatedNpaSetting
            .npaClassification(UPDATED_NPA_CLASSIFICATION)
            .durationStart(UPDATED_DURATION_START)
            .durationEnd(UPDATED_DURATION_END)
            .provision(UPDATED_PROVISION)
            .year(UPDATED_YEAR)
            .interestRate(UPDATED_INTEREST_RATE)
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
        NpaSettingDTO npaSettingDTO = npaSettingMapper.toDto(updatedNpaSetting);

        restNpaSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, npaSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(npaSettingDTO))
            )
            .andExpect(status().isOk());

        // Validate the NpaSetting in the database
        List<NpaSetting> npaSettingList = npaSettingRepository.findAll();
        assertThat(npaSettingList).hasSize(databaseSizeBeforeUpdate);
        NpaSetting testNpaSetting = npaSettingList.get(npaSettingList.size() - 1);
        assertThat(testNpaSetting.getNpaClassification()).isEqualTo(UPDATED_NPA_CLASSIFICATION);
        assertThat(testNpaSetting.getDurationStart()).isEqualTo(UPDATED_DURATION_START);
        assertThat(testNpaSetting.getDurationEnd()).isEqualTo(UPDATED_DURATION_END);
        assertThat(testNpaSetting.getProvision()).isEqualTo(UPDATED_PROVISION);
        assertThat(testNpaSetting.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testNpaSetting.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testNpaSetting.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testNpaSetting.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testNpaSetting.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNpaSetting.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testNpaSetting.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNpaSetting.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testNpaSetting.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testNpaSetting.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testNpaSetting.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testNpaSetting.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void putNonExistingNpaSetting() throws Exception {
        int databaseSizeBeforeUpdate = npaSettingRepository.findAll().size();
        npaSetting.setId(count.incrementAndGet());

        // Create the NpaSetting
        NpaSettingDTO npaSettingDTO = npaSettingMapper.toDto(npaSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNpaSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, npaSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(npaSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NpaSetting in the database
        List<NpaSetting> npaSettingList = npaSettingRepository.findAll();
        assertThat(npaSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNpaSetting() throws Exception {
        int databaseSizeBeforeUpdate = npaSettingRepository.findAll().size();
        npaSetting.setId(count.incrementAndGet());

        // Create the NpaSetting
        NpaSettingDTO npaSettingDTO = npaSettingMapper.toDto(npaSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNpaSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(npaSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NpaSetting in the database
        List<NpaSetting> npaSettingList = npaSettingRepository.findAll();
        assertThat(npaSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNpaSetting() throws Exception {
        int databaseSizeBeforeUpdate = npaSettingRepository.findAll().size();
        npaSetting.setId(count.incrementAndGet());

        // Create the NpaSetting
        NpaSettingDTO npaSettingDTO = npaSettingMapper.toDto(npaSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNpaSettingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(npaSettingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NpaSetting in the database
        List<NpaSetting> npaSettingList = npaSettingRepository.findAll();
        assertThat(npaSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNpaSettingWithPatch() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        int databaseSizeBeforeUpdate = npaSettingRepository.findAll().size();

        // Update the npaSetting using partial update
        NpaSetting partialUpdatedNpaSetting = new NpaSetting();
        partialUpdatedNpaSetting.setId(npaSetting.getId());

        partialUpdatedNpaSetting
            .npaClassification(UPDATED_NPA_CLASSIFICATION)
            .durationEnd(UPDATED_DURATION_END)
            .provision(UPDATED_PROVISION)
            .interestRate(UPDATED_INTEREST_RATE)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1);

        restNpaSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNpaSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNpaSetting))
            )
            .andExpect(status().isOk());

        // Validate the NpaSetting in the database
        List<NpaSetting> npaSettingList = npaSettingRepository.findAll();
        assertThat(npaSettingList).hasSize(databaseSizeBeforeUpdate);
        NpaSetting testNpaSetting = npaSettingList.get(npaSettingList.size() - 1);
        assertThat(testNpaSetting.getNpaClassification()).isEqualTo(UPDATED_NPA_CLASSIFICATION);
        assertThat(testNpaSetting.getDurationStart()).isEqualTo(DEFAULT_DURATION_START);
        assertThat(testNpaSetting.getDurationEnd()).isEqualTo(UPDATED_DURATION_END);
        assertThat(testNpaSetting.getProvision()).isEqualTo(UPDATED_PROVISION);
        assertThat(testNpaSetting.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testNpaSetting.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testNpaSetting.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testNpaSetting.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testNpaSetting.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testNpaSetting.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testNpaSetting.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNpaSetting.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testNpaSetting.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testNpaSetting.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testNpaSetting.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testNpaSetting.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void fullUpdateNpaSettingWithPatch() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        int databaseSizeBeforeUpdate = npaSettingRepository.findAll().size();

        // Update the npaSetting using partial update
        NpaSetting partialUpdatedNpaSetting = new NpaSetting();
        partialUpdatedNpaSetting.setId(npaSetting.getId());

        partialUpdatedNpaSetting
            .npaClassification(UPDATED_NPA_CLASSIFICATION)
            .durationStart(UPDATED_DURATION_START)
            .durationEnd(UPDATED_DURATION_END)
            .provision(UPDATED_PROVISION)
            .year(UPDATED_YEAR)
            .interestRate(UPDATED_INTEREST_RATE)
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

        restNpaSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNpaSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNpaSetting))
            )
            .andExpect(status().isOk());

        // Validate the NpaSetting in the database
        List<NpaSetting> npaSettingList = npaSettingRepository.findAll();
        assertThat(npaSettingList).hasSize(databaseSizeBeforeUpdate);
        NpaSetting testNpaSetting = npaSettingList.get(npaSettingList.size() - 1);
        assertThat(testNpaSetting.getNpaClassification()).isEqualTo(UPDATED_NPA_CLASSIFICATION);
        assertThat(testNpaSetting.getDurationStart()).isEqualTo(UPDATED_DURATION_START);
        assertThat(testNpaSetting.getDurationEnd()).isEqualTo(UPDATED_DURATION_END);
        assertThat(testNpaSetting.getProvision()).isEqualTo(UPDATED_PROVISION);
        assertThat(testNpaSetting.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testNpaSetting.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testNpaSetting.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testNpaSetting.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testNpaSetting.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNpaSetting.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testNpaSetting.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNpaSetting.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testNpaSetting.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testNpaSetting.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testNpaSetting.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testNpaSetting.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void patchNonExistingNpaSetting() throws Exception {
        int databaseSizeBeforeUpdate = npaSettingRepository.findAll().size();
        npaSetting.setId(count.incrementAndGet());

        // Create the NpaSetting
        NpaSettingDTO npaSettingDTO = npaSettingMapper.toDto(npaSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNpaSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, npaSettingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(npaSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NpaSetting in the database
        List<NpaSetting> npaSettingList = npaSettingRepository.findAll();
        assertThat(npaSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNpaSetting() throws Exception {
        int databaseSizeBeforeUpdate = npaSettingRepository.findAll().size();
        npaSetting.setId(count.incrementAndGet());

        // Create the NpaSetting
        NpaSettingDTO npaSettingDTO = npaSettingMapper.toDto(npaSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNpaSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(npaSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NpaSetting in the database
        List<NpaSetting> npaSettingList = npaSettingRepository.findAll();
        assertThat(npaSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNpaSetting() throws Exception {
        int databaseSizeBeforeUpdate = npaSettingRepository.findAll().size();
        npaSetting.setId(count.incrementAndGet());

        // Create the NpaSetting
        NpaSettingDTO npaSettingDTO = npaSettingMapper.toDto(npaSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNpaSettingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(npaSettingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NpaSetting in the database
        List<NpaSetting> npaSettingList = npaSettingRepository.findAll();
        assertThat(npaSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNpaSetting() throws Exception {
        // Initialize the database
        npaSettingRepository.saveAndFlush(npaSetting);

        int databaseSizeBeforeDelete = npaSettingRepository.findAll().size();

        // Delete the npaSetting
        restNpaSettingMockMvc
            .perform(delete(ENTITY_API_URL_ID, npaSetting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NpaSetting> npaSettingList = npaSettingRepository.findAll();
        assertThat(npaSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
