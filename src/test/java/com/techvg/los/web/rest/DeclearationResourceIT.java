package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.Declearation;
import com.techvg.los.domain.Organisation;
import com.techvg.los.domain.enumeration.DeclearationType;
import com.techvg.los.repository.DeclearationRepository;
import com.techvg.los.service.criteria.DeclearationCriteria;
import com.techvg.los.service.dto.DeclearationDTO;
import com.techvg.los.service.mapper.DeclearationMapper;
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
 * Integration tests for the {@link DeclearationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DeclearationResourceIT {

    private static final String DEFAULT_TILTLE = "AAAAAAAAAA";
    private static final String UPDATED_TILTLE = "BBBBBBBBBB";

    private static final DeclearationType DEFAULT_TYPE = DeclearationType.BOR_DECLARATION;
    private static final DeclearationType UPDATED_TYPE = DeclearationType.GURE_DECLARATION;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TAG = "AAAAAAAAAA";
    private static final String UPDATED_TAG = "BBBBBBBBBB";

    private static final Long DEFAULT_SUB_TYPE = 1L;
    private static final Long UPDATED_SUB_TYPE = 2L;
    private static final Long SMALLER_SUB_TYPE = 1L - 1L;

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

    private static final String ENTITY_API_URL = "/api/declearations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeclearationRepository declearationRepository;

    @Autowired
    private DeclearationMapper declearationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeclearationMockMvc;

    private Declearation declearation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Declearation createEntity(EntityManager em) {
        Declearation declearation = new Declearation()
            .tiltle(DEFAULT_TILTLE)
            .type(DEFAULT_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .tag(DEFAULT_TAG)
            .subType(DEFAULT_SUB_TYPE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4);
        return declearation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Declearation createUpdatedEntity(EntityManager em) {
        Declearation declearation = new Declearation()
            .tiltle(UPDATED_TILTLE)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .tag(UPDATED_TAG)
            .subType(UPDATED_SUB_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        return declearation;
    }

    @BeforeEach
    public void initTest() {
        declearation = createEntity(em);
    }

    @Test
    @Transactional
    void createDeclearation() throws Exception {
        int databaseSizeBeforeCreate = declearationRepository.findAll().size();
        // Create the Declearation
        DeclearationDTO declearationDTO = declearationMapper.toDto(declearation);
        restDeclearationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(declearationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Declearation in the database
        List<Declearation> declearationList = declearationRepository.findAll();
        assertThat(declearationList).hasSize(databaseSizeBeforeCreate + 1);
        Declearation testDeclearation = declearationList.get(declearationList.size() - 1);
        assertThat(testDeclearation.getTiltle()).isEqualTo(DEFAULT_TILTLE);
        assertThat(testDeclearation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDeclearation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDeclearation.getTag()).isEqualTo(DEFAULT_TAG);
        assertThat(testDeclearation.getSubType()).isEqualTo(DEFAULT_SUB_TYPE);
        assertThat(testDeclearation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testDeclearation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testDeclearation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDeclearation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDeclearation.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testDeclearation.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testDeclearation.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testDeclearation.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testDeclearation.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void createDeclearationWithExistingId() throws Exception {
        // Create the Declearation with an existing ID
        declearation.setId(1L);
        DeclearationDTO declearationDTO = declearationMapper.toDto(declearation);

        int databaseSizeBeforeCreate = declearationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeclearationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(declearationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declearation in the database
        List<Declearation> declearationList = declearationRepository.findAll();
        assertThat(declearationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDeclearations() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList
        restDeclearationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(declearation.getId().intValue())))
            .andExpect(jsonPath("$.[*].tiltle").value(hasItem(DEFAULT_TILTLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)))
            .andExpect(jsonPath("$.[*].subType").value(hasItem(DEFAULT_SUB_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));
    }

    @Test
    @Transactional
    void getDeclearation() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get the declearation
        restDeclearationMockMvc
            .perform(get(ENTITY_API_URL_ID, declearation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(declearation.getId().intValue()))
            .andExpect(jsonPath("$.tiltle").value(DEFAULT_TILTLE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.tag").value(DEFAULT_TAG))
            .andExpect(jsonPath("$.subType").value(DEFAULT_SUB_TYPE.intValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4));
    }

    @Test
    @Transactional
    void getDeclearationsByIdFiltering() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        Long id = declearation.getId();

        defaultDeclearationShouldBeFound("id.equals=" + id);
        defaultDeclearationShouldNotBeFound("id.notEquals=" + id);

        defaultDeclearationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDeclearationShouldNotBeFound("id.greaterThan=" + id);

        defaultDeclearationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDeclearationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDeclearationsByTiltleIsEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where tiltle equals to DEFAULT_TILTLE
        defaultDeclearationShouldBeFound("tiltle.equals=" + DEFAULT_TILTLE);

        // Get all the declearationList where tiltle equals to UPDATED_TILTLE
        defaultDeclearationShouldNotBeFound("tiltle.equals=" + UPDATED_TILTLE);
    }

    @Test
    @Transactional
    void getAllDeclearationsByTiltleIsInShouldWork() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where tiltle in DEFAULT_TILTLE or UPDATED_TILTLE
        defaultDeclearationShouldBeFound("tiltle.in=" + DEFAULT_TILTLE + "," + UPDATED_TILTLE);

        // Get all the declearationList where tiltle equals to UPDATED_TILTLE
        defaultDeclearationShouldNotBeFound("tiltle.in=" + UPDATED_TILTLE);
    }

    @Test
    @Transactional
    void getAllDeclearationsByTiltleIsNullOrNotNull() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where tiltle is not null
        defaultDeclearationShouldBeFound("tiltle.specified=true");

        // Get all the declearationList where tiltle is null
        defaultDeclearationShouldNotBeFound("tiltle.specified=false");
    }

    @Test
    @Transactional
    void getAllDeclearationsByTiltleContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where tiltle contains DEFAULT_TILTLE
        defaultDeclearationShouldBeFound("tiltle.contains=" + DEFAULT_TILTLE);

        // Get all the declearationList where tiltle contains UPDATED_TILTLE
        defaultDeclearationShouldNotBeFound("tiltle.contains=" + UPDATED_TILTLE);
    }

    @Test
    @Transactional
    void getAllDeclearationsByTiltleNotContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where tiltle does not contain DEFAULT_TILTLE
        defaultDeclearationShouldNotBeFound("tiltle.doesNotContain=" + DEFAULT_TILTLE);

        // Get all the declearationList where tiltle does not contain UPDATED_TILTLE
        defaultDeclearationShouldBeFound("tiltle.doesNotContain=" + UPDATED_TILTLE);
    }

    @Test
    @Transactional
    void getAllDeclearationsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where type equals to DEFAULT_TYPE
        defaultDeclearationShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the declearationList where type equals to UPDATED_TYPE
        defaultDeclearationShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDeclearationsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultDeclearationShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the declearationList where type equals to UPDATED_TYPE
        defaultDeclearationShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDeclearationsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where type is not null
        defaultDeclearationShouldBeFound("type.specified=true");

        // Get all the declearationList where type is null
        defaultDeclearationShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllDeclearationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where description equals to DEFAULT_DESCRIPTION
        defaultDeclearationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the declearationList where description equals to UPDATED_DESCRIPTION
        defaultDeclearationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDeclearationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDeclearationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the declearationList where description equals to UPDATED_DESCRIPTION
        defaultDeclearationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDeclearationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where description is not null
        defaultDeclearationShouldBeFound("description.specified=true");

        // Get all the declearationList where description is null
        defaultDeclearationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllDeclearationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where description contains DEFAULT_DESCRIPTION
        defaultDeclearationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the declearationList where description contains UPDATED_DESCRIPTION
        defaultDeclearationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDeclearationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where description does not contain DEFAULT_DESCRIPTION
        defaultDeclearationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the declearationList where description does not contain UPDATED_DESCRIPTION
        defaultDeclearationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllDeclearationsByTagIsEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where tag equals to DEFAULT_TAG
        defaultDeclearationShouldBeFound("tag.equals=" + DEFAULT_TAG);

        // Get all the declearationList where tag equals to UPDATED_TAG
        defaultDeclearationShouldNotBeFound("tag.equals=" + UPDATED_TAG);
    }

    @Test
    @Transactional
    void getAllDeclearationsByTagIsInShouldWork() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where tag in DEFAULT_TAG or UPDATED_TAG
        defaultDeclearationShouldBeFound("tag.in=" + DEFAULT_TAG + "," + UPDATED_TAG);

        // Get all the declearationList where tag equals to UPDATED_TAG
        defaultDeclearationShouldNotBeFound("tag.in=" + UPDATED_TAG);
    }

    @Test
    @Transactional
    void getAllDeclearationsByTagIsNullOrNotNull() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where tag is not null
        defaultDeclearationShouldBeFound("tag.specified=true");

        // Get all the declearationList where tag is null
        defaultDeclearationShouldNotBeFound("tag.specified=false");
    }

    @Test
    @Transactional
    void getAllDeclearationsByTagContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where tag contains DEFAULT_TAG
        defaultDeclearationShouldBeFound("tag.contains=" + DEFAULT_TAG);

        // Get all the declearationList where tag contains UPDATED_TAG
        defaultDeclearationShouldNotBeFound("tag.contains=" + UPDATED_TAG);
    }

    @Test
    @Transactional
    void getAllDeclearationsByTagNotContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where tag does not contain DEFAULT_TAG
        defaultDeclearationShouldNotBeFound("tag.doesNotContain=" + DEFAULT_TAG);

        // Get all the declearationList where tag does not contain UPDATED_TAG
        defaultDeclearationShouldBeFound("tag.doesNotContain=" + UPDATED_TAG);
    }

    @Test
    @Transactional
    void getAllDeclearationsBySubTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where subType equals to DEFAULT_SUB_TYPE
        defaultDeclearationShouldBeFound("subType.equals=" + DEFAULT_SUB_TYPE);

        // Get all the declearationList where subType equals to UPDATED_SUB_TYPE
        defaultDeclearationShouldNotBeFound("subType.equals=" + UPDATED_SUB_TYPE);
    }

    @Test
    @Transactional
    void getAllDeclearationsBySubTypeIsInShouldWork() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where subType in DEFAULT_SUB_TYPE or UPDATED_SUB_TYPE
        defaultDeclearationShouldBeFound("subType.in=" + DEFAULT_SUB_TYPE + "," + UPDATED_SUB_TYPE);

        // Get all the declearationList where subType equals to UPDATED_SUB_TYPE
        defaultDeclearationShouldNotBeFound("subType.in=" + UPDATED_SUB_TYPE);
    }

    @Test
    @Transactional
    void getAllDeclearationsBySubTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where subType is not null
        defaultDeclearationShouldBeFound("subType.specified=true");

        // Get all the declearationList where subType is null
        defaultDeclearationShouldNotBeFound("subType.specified=false");
    }

    @Test
    @Transactional
    void getAllDeclearationsBySubTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where subType is greater than or equal to DEFAULT_SUB_TYPE
        defaultDeclearationShouldBeFound("subType.greaterThanOrEqual=" + DEFAULT_SUB_TYPE);

        // Get all the declearationList where subType is greater than or equal to UPDATED_SUB_TYPE
        defaultDeclearationShouldNotBeFound("subType.greaterThanOrEqual=" + UPDATED_SUB_TYPE);
    }

    @Test
    @Transactional
    void getAllDeclearationsBySubTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where subType is less than or equal to DEFAULT_SUB_TYPE
        defaultDeclearationShouldBeFound("subType.lessThanOrEqual=" + DEFAULT_SUB_TYPE);

        // Get all the declearationList where subType is less than or equal to SMALLER_SUB_TYPE
        defaultDeclearationShouldNotBeFound("subType.lessThanOrEqual=" + SMALLER_SUB_TYPE);
    }

    @Test
    @Transactional
    void getAllDeclearationsBySubTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where subType is less than DEFAULT_SUB_TYPE
        defaultDeclearationShouldNotBeFound("subType.lessThan=" + DEFAULT_SUB_TYPE);

        // Get all the declearationList where subType is less than UPDATED_SUB_TYPE
        defaultDeclearationShouldBeFound("subType.lessThan=" + UPDATED_SUB_TYPE);
    }

    @Test
    @Transactional
    void getAllDeclearationsBySubTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where subType is greater than DEFAULT_SUB_TYPE
        defaultDeclearationShouldNotBeFound("subType.greaterThan=" + DEFAULT_SUB_TYPE);

        // Get all the declearationList where subType is greater than SMALLER_SUB_TYPE
        defaultDeclearationShouldBeFound("subType.greaterThan=" + SMALLER_SUB_TYPE);
    }

    @Test
    @Transactional
    void getAllDeclearationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultDeclearationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the declearationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDeclearationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDeclearationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultDeclearationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the declearationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDeclearationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDeclearationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where lastModified is not null
        defaultDeclearationShouldBeFound("lastModified.specified=true");

        // Get all the declearationList where lastModified is null
        defaultDeclearationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllDeclearationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultDeclearationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the declearationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDeclearationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDeclearationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultDeclearationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the declearationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDeclearationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDeclearationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where lastModifiedBy is not null
        defaultDeclearationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the declearationList where lastModifiedBy is null
        defaultDeclearationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDeclearationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultDeclearationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the declearationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultDeclearationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDeclearationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultDeclearationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the declearationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultDeclearationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDeclearationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where createdBy equals to DEFAULT_CREATED_BY
        defaultDeclearationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the declearationList where createdBy equals to UPDATED_CREATED_BY
        defaultDeclearationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDeclearationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultDeclearationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the declearationList where createdBy equals to UPDATED_CREATED_BY
        defaultDeclearationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDeclearationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where createdBy is not null
        defaultDeclearationShouldBeFound("createdBy.specified=true");

        // Get all the declearationList where createdBy is null
        defaultDeclearationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDeclearationsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where createdBy contains DEFAULT_CREATED_BY
        defaultDeclearationShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the declearationList where createdBy contains UPDATED_CREATED_BY
        defaultDeclearationShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDeclearationsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where createdBy does not contain DEFAULT_CREATED_BY
        defaultDeclearationShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the declearationList where createdBy does not contain UPDATED_CREATED_BY
        defaultDeclearationShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDeclearationsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where createdOn equals to DEFAULT_CREATED_ON
        defaultDeclearationShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the declearationList where createdOn equals to UPDATED_CREATED_ON
        defaultDeclearationShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllDeclearationsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultDeclearationShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the declearationList where createdOn equals to UPDATED_CREATED_ON
        defaultDeclearationShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllDeclearationsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where createdOn is not null
        defaultDeclearationShouldBeFound("createdOn.specified=true");

        // Get all the declearationList where createdOn is null
        defaultDeclearationShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllDeclearationsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where isDeleted equals to DEFAULT_IS_DELETED
        defaultDeclearationShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the declearationList where isDeleted equals to UPDATED_IS_DELETED
        defaultDeclearationShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllDeclearationsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultDeclearationShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the declearationList where isDeleted equals to UPDATED_IS_DELETED
        defaultDeclearationShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllDeclearationsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where isDeleted is not null
        defaultDeclearationShouldBeFound("isDeleted.specified=true");

        // Get all the declearationList where isDeleted is null
        defaultDeclearationShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultDeclearationShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the declearationList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultDeclearationShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultDeclearationShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the declearationList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultDeclearationShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField1 is not null
        defaultDeclearationShouldBeFound("freeField1.specified=true");

        // Get all the declearationList where freeField1 is null
        defaultDeclearationShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultDeclearationShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the declearationList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultDeclearationShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultDeclearationShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the declearationList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultDeclearationShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultDeclearationShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the declearationList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultDeclearationShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultDeclearationShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the declearationList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultDeclearationShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField2 is not null
        defaultDeclearationShouldBeFound("freeField2.specified=true");

        // Get all the declearationList where freeField2 is null
        defaultDeclearationShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultDeclearationShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the declearationList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultDeclearationShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultDeclearationShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the declearationList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultDeclearationShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultDeclearationShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the declearationList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultDeclearationShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultDeclearationShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the declearationList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultDeclearationShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField3 is not null
        defaultDeclearationShouldBeFound("freeField3.specified=true");

        // Get all the declearationList where freeField3 is null
        defaultDeclearationShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultDeclearationShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the declearationList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultDeclearationShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultDeclearationShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the declearationList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultDeclearationShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultDeclearationShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the declearationList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultDeclearationShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultDeclearationShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the declearationList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultDeclearationShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField4 is not null
        defaultDeclearationShouldBeFound("freeField4.specified=true");

        // Get all the declearationList where freeField4 is null
        defaultDeclearationShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultDeclearationShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the declearationList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultDeclearationShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllDeclearationsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        // Get all the declearationList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultDeclearationShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the declearationList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultDeclearationShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllDeclearationsByOrganisationIsEqualToSomething() throws Exception {
        Organisation organisation;
        if (TestUtil.findAll(em, Organisation.class).isEmpty()) {
            declearationRepository.saveAndFlush(declearation);
            organisation = OrganisationResourceIT.createEntity(em);
        } else {
            organisation = TestUtil.findAll(em, Organisation.class).get(0);
        }
        em.persist(organisation);
        em.flush();
        declearation.setOrganisation(organisation);
        declearationRepository.saveAndFlush(declearation);
        Long organisationId = organisation.getId();

        // Get all the declearationList where organisation equals to organisationId
        defaultDeclearationShouldBeFound("organisationId.equals=" + organisationId);

        // Get all the declearationList where organisation equals to (organisationId + 1)
        defaultDeclearationShouldNotBeFound("organisationId.equals=" + (organisationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDeclearationShouldBeFound(String filter) throws Exception {
        restDeclearationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(declearation.getId().intValue())))
            .andExpect(jsonPath("$.[*].tiltle").value(hasItem(DEFAULT_TILTLE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].tag").value(hasItem(DEFAULT_TAG)))
            .andExpect(jsonPath("$.[*].subType").value(hasItem(DEFAULT_SUB_TYPE.intValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));

        // Check, that the count call also returns 1
        restDeclearationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDeclearationShouldNotBeFound(String filter) throws Exception {
        restDeclearationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeclearationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDeclearation() throws Exception {
        // Get the declearation
        restDeclearationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDeclearation() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        int databaseSizeBeforeUpdate = declearationRepository.findAll().size();

        // Update the declearation
        Declearation updatedDeclearation = declearationRepository.findById(declearation.getId()).get();
        // Disconnect from session so that the updates on updatedDeclearation are not directly saved in db
        em.detach(updatedDeclearation);
        updatedDeclearation
            .tiltle(UPDATED_TILTLE)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .tag(UPDATED_TAG)
            .subType(UPDATED_SUB_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        DeclearationDTO declearationDTO = declearationMapper.toDto(updatedDeclearation);

        restDeclearationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, declearationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(declearationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Declearation in the database
        List<Declearation> declearationList = declearationRepository.findAll();
        assertThat(declearationList).hasSize(databaseSizeBeforeUpdate);
        Declearation testDeclearation = declearationList.get(declearationList.size() - 1);
        assertThat(testDeclearation.getTiltle()).isEqualTo(UPDATED_TILTLE);
        assertThat(testDeclearation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDeclearation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDeclearation.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testDeclearation.getSubType()).isEqualTo(UPDATED_SUB_TYPE);
        assertThat(testDeclearation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDeclearation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testDeclearation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDeclearation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDeclearation.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDeclearation.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testDeclearation.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testDeclearation.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testDeclearation.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void putNonExistingDeclearation() throws Exception {
        int databaseSizeBeforeUpdate = declearationRepository.findAll().size();
        declearation.setId(count.incrementAndGet());

        // Create the Declearation
        DeclearationDTO declearationDTO = declearationMapper.toDto(declearation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeclearationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, declearationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(declearationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declearation in the database
        List<Declearation> declearationList = declearationRepository.findAll();
        assertThat(declearationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDeclearation() throws Exception {
        int databaseSizeBeforeUpdate = declearationRepository.findAll().size();
        declearation.setId(count.incrementAndGet());

        // Create the Declearation
        DeclearationDTO declearationDTO = declearationMapper.toDto(declearation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclearationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(declearationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declearation in the database
        List<Declearation> declearationList = declearationRepository.findAll();
        assertThat(declearationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDeclearation() throws Exception {
        int databaseSizeBeforeUpdate = declearationRepository.findAll().size();
        declearation.setId(count.incrementAndGet());

        // Create the Declearation
        DeclearationDTO declearationDTO = declearationMapper.toDto(declearation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclearationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(declearationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Declearation in the database
        List<Declearation> declearationList = declearationRepository.findAll();
        assertThat(declearationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeclearationWithPatch() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        int databaseSizeBeforeUpdate = declearationRepository.findAll().size();

        // Update the declearation using partial update
        Declearation partialUpdatedDeclearation = new Declearation();
        partialUpdatedDeclearation.setId(declearation.getId());

        partialUpdatedDeclearation
            .description(UPDATED_DESCRIPTION)
            .tag(UPDATED_TAG)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField4(UPDATED_FREE_FIELD_4);

        restDeclearationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeclearation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeclearation))
            )
            .andExpect(status().isOk());

        // Validate the Declearation in the database
        List<Declearation> declearationList = declearationRepository.findAll();
        assertThat(declearationList).hasSize(databaseSizeBeforeUpdate);
        Declearation testDeclearation = declearationList.get(declearationList.size() - 1);
        assertThat(testDeclearation.getTiltle()).isEqualTo(DEFAULT_TILTLE);
        assertThat(testDeclearation.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDeclearation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDeclearation.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testDeclearation.getSubType()).isEqualTo(DEFAULT_SUB_TYPE);
        assertThat(testDeclearation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDeclearation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testDeclearation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDeclearation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDeclearation.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testDeclearation.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testDeclearation.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testDeclearation.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testDeclearation.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void fullUpdateDeclearationWithPatch() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        int databaseSizeBeforeUpdate = declearationRepository.findAll().size();

        // Update the declearation using partial update
        Declearation partialUpdatedDeclearation = new Declearation();
        partialUpdatedDeclearation.setId(declearation.getId());

        partialUpdatedDeclearation
            .tiltle(UPDATED_TILTLE)
            .type(UPDATED_TYPE)
            .description(UPDATED_DESCRIPTION)
            .tag(UPDATED_TAG)
            .subType(UPDATED_SUB_TYPE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restDeclearationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDeclearation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDeclearation))
            )
            .andExpect(status().isOk());

        // Validate the Declearation in the database
        List<Declearation> declearationList = declearationRepository.findAll();
        assertThat(declearationList).hasSize(databaseSizeBeforeUpdate);
        Declearation testDeclearation = declearationList.get(declearationList.size() - 1);
        assertThat(testDeclearation.getTiltle()).isEqualTo(UPDATED_TILTLE);
        assertThat(testDeclearation.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDeclearation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDeclearation.getTag()).isEqualTo(UPDATED_TAG);
        assertThat(testDeclearation.getSubType()).isEqualTo(UPDATED_SUB_TYPE);
        assertThat(testDeclearation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDeclearation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testDeclearation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDeclearation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDeclearation.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDeclearation.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testDeclearation.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testDeclearation.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testDeclearation.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void patchNonExistingDeclearation() throws Exception {
        int databaseSizeBeforeUpdate = declearationRepository.findAll().size();
        declearation.setId(count.incrementAndGet());

        // Create the Declearation
        DeclearationDTO declearationDTO = declearationMapper.toDto(declearation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeclearationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, declearationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(declearationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declearation in the database
        List<Declearation> declearationList = declearationRepository.findAll();
        assertThat(declearationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDeclearation() throws Exception {
        int databaseSizeBeforeUpdate = declearationRepository.findAll().size();
        declearation.setId(count.incrementAndGet());

        // Create the Declearation
        DeclearationDTO declearationDTO = declearationMapper.toDto(declearation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclearationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(declearationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Declearation in the database
        List<Declearation> declearationList = declearationRepository.findAll();
        assertThat(declearationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDeclearation() throws Exception {
        int databaseSizeBeforeUpdate = declearationRepository.findAll().size();
        declearation.setId(count.incrementAndGet());

        // Create the Declearation
        DeclearationDTO declearationDTO = declearationMapper.toDto(declearation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeclearationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(declearationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Declearation in the database
        List<Declearation> declearationList = declearationRepository.findAll();
        assertThat(declearationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDeclearation() throws Exception {
        // Initialize the database
        declearationRepository.saveAndFlush(declearation);

        int databaseSizeBeforeDelete = declearationRepository.findAll().size();

        // Delete the declearation
        restDeclearationMockMvc
            .perform(delete(ENTITY_API_URL_ID, declearation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Declearation> declearationList = declearationRepository.findAll();
        assertThat(declearationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
