package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.District;
import com.techvg.los.domain.Taluka;
import com.techvg.los.repository.TalukaRepository;
import com.techvg.los.service.criteria.TalukaCriteria;
import com.techvg.los.service.dto.TalukaDTO;
import com.techvg.los.service.mapper.TalukaMapper;
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
 * Integration tests for the {@link TalukaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TalukaResourceIT {

    private static final String DEFAULT_TALUKA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TALUKA_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Long DEFAULT_LGD_CODE = 1L;
    private static final Long UPDATED_LGD_CODE = 2L;
    private static final Long SMALLER_LGD_CODE = 1L - 1L;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/talukas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TalukaRepository talukaRepository;

    @Autowired
    private TalukaMapper talukaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTalukaMockMvc;

    private Taluka taluka;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taluka createEntity(EntityManager em) {
        Taluka taluka = new Taluka()
            .talukaName(DEFAULT_TALUKA_NAME)
            .isDeleted(DEFAULT_IS_DELETED)
            .lgdCode(DEFAULT_LGD_CODE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return taluka;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Taluka createUpdatedEntity(EntityManager em) {
        Taluka taluka = new Taluka()
            .talukaName(UPDATED_TALUKA_NAME)
            .isDeleted(UPDATED_IS_DELETED)
            .lgdCode(UPDATED_LGD_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return taluka;
    }

    @BeforeEach
    public void initTest() {
        taluka = createEntity(em);
    }

    @Test
    @Transactional
    void createTaluka() throws Exception {
        int databaseSizeBeforeCreate = talukaRepository.findAll().size();
        // Create the Taluka
        TalukaDTO talukaDTO = talukaMapper.toDto(taluka);
        restTalukaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(talukaDTO)))
            .andExpect(status().isCreated());

        // Validate the Taluka in the database
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeCreate + 1);
        Taluka testTaluka = talukaList.get(talukaList.size() - 1);
        assertThat(testTaluka.getTalukaName()).isEqualTo(DEFAULT_TALUKA_NAME);
        assertThat(testTaluka.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testTaluka.getLgdCode()).isEqualTo(DEFAULT_LGD_CODE);
        assertThat(testTaluka.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTaluka.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createTalukaWithExistingId() throws Exception {
        // Create the Taluka with an existing ID
        taluka.setId(1L);
        TalukaDTO talukaDTO = talukaMapper.toDto(taluka);

        int databaseSizeBeforeCreate = talukaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTalukaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(talukaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Taluka in the database
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTalukaNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = talukaRepository.findAll().size();
        // set the field null
        taluka.setTalukaName(null);

        // Create the Taluka, which fails.
        TalukaDTO talukaDTO = talukaMapper.toDto(taluka);

        restTalukaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(talukaDTO)))
            .andExpect(status().isBadRequest());

        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTalukas() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList
        restTalukaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taluka.getId().intValue())))
            .andExpect(jsonPath("$.[*].talukaName").value(hasItem(DEFAULT_TALUKA_NAME)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lgdCode").value(hasItem(DEFAULT_LGD_CODE.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getTaluka() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get the taluka
        restTalukaMockMvc
            .perform(get(ENTITY_API_URL_ID, taluka.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taluka.getId().intValue()))
            .andExpect(jsonPath("$.talukaName").value(DEFAULT_TALUKA_NAME))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.lgdCode").value(DEFAULT_LGD_CODE.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getTalukasByIdFiltering() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        Long id = taluka.getId();

        defaultTalukaShouldBeFound("id.equals=" + id);
        defaultTalukaShouldNotBeFound("id.notEquals=" + id);

        defaultTalukaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTalukaShouldNotBeFound("id.greaterThan=" + id);

        defaultTalukaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTalukaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTalukasByTalukaNameIsEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where talukaName equals to DEFAULT_TALUKA_NAME
        defaultTalukaShouldBeFound("talukaName.equals=" + DEFAULT_TALUKA_NAME);

        // Get all the talukaList where talukaName equals to UPDATED_TALUKA_NAME
        defaultTalukaShouldNotBeFound("talukaName.equals=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllTalukasByTalukaNameIsInShouldWork() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where talukaName in DEFAULT_TALUKA_NAME or UPDATED_TALUKA_NAME
        defaultTalukaShouldBeFound("talukaName.in=" + DEFAULT_TALUKA_NAME + "," + UPDATED_TALUKA_NAME);

        // Get all the talukaList where talukaName equals to UPDATED_TALUKA_NAME
        defaultTalukaShouldNotBeFound("talukaName.in=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllTalukasByTalukaNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where talukaName is not null
        defaultTalukaShouldBeFound("talukaName.specified=true");

        // Get all the talukaList where talukaName is null
        defaultTalukaShouldNotBeFound("talukaName.specified=false");
    }

    @Test
    @Transactional
    void getAllTalukasByTalukaNameContainsSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where talukaName contains DEFAULT_TALUKA_NAME
        defaultTalukaShouldBeFound("talukaName.contains=" + DEFAULT_TALUKA_NAME);

        // Get all the talukaList where talukaName contains UPDATED_TALUKA_NAME
        defaultTalukaShouldNotBeFound("talukaName.contains=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllTalukasByTalukaNameNotContainsSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where talukaName does not contain DEFAULT_TALUKA_NAME
        defaultTalukaShouldNotBeFound("talukaName.doesNotContain=" + DEFAULT_TALUKA_NAME);

        // Get all the talukaList where talukaName does not contain UPDATED_TALUKA_NAME
        defaultTalukaShouldBeFound("talukaName.doesNotContain=" + UPDATED_TALUKA_NAME);
    }

    @Test
    @Transactional
    void getAllTalukasByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where isDeleted equals to DEFAULT_IS_DELETED
        defaultTalukaShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the talukaList where isDeleted equals to UPDATED_IS_DELETED
        defaultTalukaShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTalukasByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultTalukaShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the talukaList where isDeleted equals to UPDATED_IS_DELETED
        defaultTalukaShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTalukasByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where isDeleted is not null
        defaultTalukaShouldBeFound("isDeleted.specified=true");

        // Get all the talukaList where isDeleted is null
        defaultTalukaShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllTalukasByLgdCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lgdCode equals to DEFAULT_LGD_CODE
        defaultTalukaShouldBeFound("lgdCode.equals=" + DEFAULT_LGD_CODE);

        // Get all the talukaList where lgdCode equals to UPDATED_LGD_CODE
        defaultTalukaShouldNotBeFound("lgdCode.equals=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllTalukasByLgdCodeIsInShouldWork() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lgdCode in DEFAULT_LGD_CODE or UPDATED_LGD_CODE
        defaultTalukaShouldBeFound("lgdCode.in=" + DEFAULT_LGD_CODE + "," + UPDATED_LGD_CODE);

        // Get all the talukaList where lgdCode equals to UPDATED_LGD_CODE
        defaultTalukaShouldNotBeFound("lgdCode.in=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllTalukasByLgdCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lgdCode is not null
        defaultTalukaShouldBeFound("lgdCode.specified=true");

        // Get all the talukaList where lgdCode is null
        defaultTalukaShouldNotBeFound("lgdCode.specified=false");
    }

    @Test
    @Transactional
    void getAllTalukasByLgdCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lgdCode is greater than or equal to DEFAULT_LGD_CODE
        defaultTalukaShouldBeFound("lgdCode.greaterThanOrEqual=" + DEFAULT_LGD_CODE);

        // Get all the talukaList where lgdCode is greater than or equal to UPDATED_LGD_CODE
        defaultTalukaShouldNotBeFound("lgdCode.greaterThanOrEqual=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllTalukasByLgdCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lgdCode is less than or equal to DEFAULT_LGD_CODE
        defaultTalukaShouldBeFound("lgdCode.lessThanOrEqual=" + DEFAULT_LGD_CODE);

        // Get all the talukaList where lgdCode is less than or equal to SMALLER_LGD_CODE
        defaultTalukaShouldNotBeFound("lgdCode.lessThanOrEqual=" + SMALLER_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllTalukasByLgdCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lgdCode is less than DEFAULT_LGD_CODE
        defaultTalukaShouldNotBeFound("lgdCode.lessThan=" + DEFAULT_LGD_CODE);

        // Get all the talukaList where lgdCode is less than UPDATED_LGD_CODE
        defaultTalukaShouldBeFound("lgdCode.lessThan=" + UPDATED_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllTalukasByLgdCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lgdCode is greater than DEFAULT_LGD_CODE
        defaultTalukaShouldNotBeFound("lgdCode.greaterThan=" + DEFAULT_LGD_CODE);

        // Get all the talukaList where lgdCode is greater than SMALLER_LGD_CODE
        defaultTalukaShouldBeFound("lgdCode.greaterThan=" + SMALLER_LGD_CODE);
    }

    @Test
    @Transactional
    void getAllTalukasByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTalukaShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the talukaList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTalukaShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTalukasByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTalukaShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the talukaList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTalukaShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTalukasByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModified is not null
        defaultTalukaShouldBeFound("lastModified.specified=true");

        // Get all the talukaList where lastModified is null
        defaultTalukaShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTalukasByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTalukaShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the talukaList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTalukaShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTalukasByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTalukaShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the talukaList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTalukaShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTalukasByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModifiedBy is not null
        defaultTalukaShouldBeFound("lastModifiedBy.specified=true");

        // Get all the talukaList where lastModifiedBy is null
        defaultTalukaShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTalukasByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTalukaShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the talukaList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTalukaShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTalukasByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        // Get all the talukaList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTalukaShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the talukaList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTalukaShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTalukasByDistrictIsEqualToSomething() throws Exception {
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            talukaRepository.saveAndFlush(taluka);
            district = DistrictResourceIT.createEntity(em);
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        em.persist(district);
        em.flush();
        taluka.setDistrict(district);
        talukaRepository.saveAndFlush(taluka);
        Long districtId = district.getId();

        // Get all the talukaList where district equals to districtId
        defaultTalukaShouldBeFound("districtId.equals=" + districtId);

        // Get all the talukaList where district equals to (districtId + 1)
        defaultTalukaShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTalukaShouldBeFound(String filter) throws Exception {
        restTalukaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taluka.getId().intValue())))
            .andExpect(jsonPath("$.[*].talukaName").value(hasItem(DEFAULT_TALUKA_NAME)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lgdCode").value(hasItem(DEFAULT_LGD_CODE.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restTalukaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTalukaShouldNotBeFound(String filter) throws Exception {
        restTalukaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTalukaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTaluka() throws Exception {
        // Get the taluka
        restTalukaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTaluka() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        int databaseSizeBeforeUpdate = talukaRepository.findAll().size();

        // Update the taluka
        Taluka updatedTaluka = talukaRepository.findById(taluka.getId()).get();
        // Disconnect from session so that the updates on updatedTaluka are not directly saved in db
        em.detach(updatedTaluka);
        updatedTaluka
            .talukaName(UPDATED_TALUKA_NAME)
            .isDeleted(UPDATED_IS_DELETED)
            .lgdCode(UPDATED_LGD_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        TalukaDTO talukaDTO = talukaMapper.toDto(updatedTaluka);

        restTalukaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, talukaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(talukaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Taluka in the database
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeUpdate);
        Taluka testTaluka = talukaList.get(talukaList.size() - 1);
        assertThat(testTaluka.getTalukaName()).isEqualTo(UPDATED_TALUKA_NAME);
        assertThat(testTaluka.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTaluka.getLgdCode()).isEqualTo(UPDATED_LGD_CODE);
        assertThat(testTaluka.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTaluka.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingTaluka() throws Exception {
        int databaseSizeBeforeUpdate = talukaRepository.findAll().size();
        taluka.setId(count.incrementAndGet());

        // Create the Taluka
        TalukaDTO talukaDTO = talukaMapper.toDto(taluka);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTalukaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, talukaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(talukaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taluka in the database
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTaluka() throws Exception {
        int databaseSizeBeforeUpdate = talukaRepository.findAll().size();
        taluka.setId(count.incrementAndGet());

        // Create the Taluka
        TalukaDTO talukaDTO = talukaMapper.toDto(taluka);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTalukaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(talukaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taluka in the database
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTaluka() throws Exception {
        int databaseSizeBeforeUpdate = talukaRepository.findAll().size();
        taluka.setId(count.incrementAndGet());

        // Create the Taluka
        TalukaDTO talukaDTO = talukaMapper.toDto(taluka);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTalukaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(talukaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Taluka in the database
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTalukaWithPatch() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        int databaseSizeBeforeUpdate = talukaRepository.findAll().size();

        // Update the taluka using partial update
        Taluka partialUpdatedTaluka = new Taluka();
        partialUpdatedTaluka.setId(taluka.getId());

        partialUpdatedTaluka
            .talukaName(UPDATED_TALUKA_NAME)
            .isDeleted(UPDATED_IS_DELETED)
            .lgdCode(UPDATED_LGD_CODE)
            .lastModified(UPDATED_LAST_MODIFIED);

        restTalukaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaluka.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaluka))
            )
            .andExpect(status().isOk());

        // Validate the Taluka in the database
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeUpdate);
        Taluka testTaluka = talukaList.get(talukaList.size() - 1);
        assertThat(testTaluka.getTalukaName()).isEqualTo(UPDATED_TALUKA_NAME);
        assertThat(testTaluka.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTaluka.getLgdCode()).isEqualTo(UPDATED_LGD_CODE);
        assertThat(testTaluka.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTaluka.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateTalukaWithPatch() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        int databaseSizeBeforeUpdate = talukaRepository.findAll().size();

        // Update the taluka using partial update
        Taluka partialUpdatedTaluka = new Taluka();
        partialUpdatedTaluka.setId(taluka.getId());

        partialUpdatedTaluka
            .talukaName(UPDATED_TALUKA_NAME)
            .isDeleted(UPDATED_IS_DELETED)
            .lgdCode(UPDATED_LGD_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restTalukaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTaluka.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTaluka))
            )
            .andExpect(status().isOk());

        // Validate the Taluka in the database
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeUpdate);
        Taluka testTaluka = talukaList.get(talukaList.size() - 1);
        assertThat(testTaluka.getTalukaName()).isEqualTo(UPDATED_TALUKA_NAME);
        assertThat(testTaluka.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTaluka.getLgdCode()).isEqualTo(UPDATED_LGD_CODE);
        assertThat(testTaluka.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTaluka.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingTaluka() throws Exception {
        int databaseSizeBeforeUpdate = talukaRepository.findAll().size();
        taluka.setId(count.incrementAndGet());

        // Create the Taluka
        TalukaDTO talukaDTO = talukaMapper.toDto(taluka);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTalukaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, talukaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(talukaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taluka in the database
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTaluka() throws Exception {
        int databaseSizeBeforeUpdate = talukaRepository.findAll().size();
        taluka.setId(count.incrementAndGet());

        // Create the Taluka
        TalukaDTO talukaDTO = talukaMapper.toDto(taluka);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTalukaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(talukaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Taluka in the database
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTaluka() throws Exception {
        int databaseSizeBeforeUpdate = talukaRepository.findAll().size();
        taluka.setId(count.incrementAndGet());

        // Create the Taluka
        TalukaDTO talukaDTO = talukaMapper.toDto(taluka);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTalukaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(talukaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Taluka in the database
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTaluka() throws Exception {
        // Initialize the database
        talukaRepository.saveAndFlush(taluka);

        int databaseSizeBeforeDelete = talukaRepository.findAll().size();

        // Delete the taluka
        restTalukaMockMvc
            .perform(delete(ENTITY_API_URL_ID, taluka.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Taluka> talukaList = talukaRepository.findAll();
        assertThat(talukaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
