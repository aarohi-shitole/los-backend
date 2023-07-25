package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.Address;
import com.techvg.los.domain.Organisation;
import com.techvg.los.domain.enumeration.OrganisationType;
import com.techvg.los.repository.OrganisationRepository;
import com.techvg.los.service.criteria.OrganisationCriteria;
import com.techvg.los.service.dto.OrganisationDTO;
import com.techvg.los.service.mapper.OrganisationMapper;
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
 * Integration tests for the {@link OrganisationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganisationResourceIT {

    private static final String DEFAULT_ORG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_REG_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REG_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_GSTIN = "AAAAAAAAAA";
    private static final String UPDATED_GSTIN = "BBBBBBBBBB";

    private static final String DEFAULT_PAN = "AAAAAAAAAA";
    private static final String UPDATED_PAN = "BBBBBBBBBB";

    private static final String DEFAULT_TAN = "AAAAAAAAAA";
    private static final String UPDATED_TAN = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_SITE = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVATE = false;
    private static final Boolean UPDATED_IS_ACTIVATE = true;

    private static final OrganisationType DEFAULT_ORG_TYPE = OrganisationType.SOCIETY;
    private static final OrganisationType UPDATED_ORG_TYPE = OrganisationType.CO_SOCIETY;

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_IS_DELETED = "AAAAAAAAAA";
    private static final String UPDATED_IS_DELETED = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/organisations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private OrganisationMapper organisationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganisationMockMvc;

    private Organisation organisation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organisation createEntity(EntityManager em) {
        Organisation organisation = new Organisation()
            .orgName(DEFAULT_ORG_NAME)
            .description(DEFAULT_DESCRIPTION)
            .regNumber(DEFAULT_REG_NUMBER)
            .gstin(DEFAULT_GSTIN)
            .pan(DEFAULT_PAN)
            .tan(DEFAULT_TAN)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .webSite(DEFAULT_WEB_SITE)
            .fax(DEFAULT_FAX)
            .isActivate(DEFAULT_IS_ACTIVATE)
            .orgType(DEFAULT_ORG_TYPE)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .isDeleted(DEFAULT_IS_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5);
        return organisation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organisation createUpdatedEntity(EntityManager em) {
        Organisation organisation = new Organisation()
            .orgName(UPDATED_ORG_NAME)
            .description(UPDATED_DESCRIPTION)
            .regNumber(UPDATED_REG_NUMBER)
            .gstin(UPDATED_GSTIN)
            .pan(UPDATED_PAN)
            .tan(UPDATED_TAN)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .webSite(UPDATED_WEB_SITE)
            .fax(UPDATED_FAX)
            .isActivate(UPDATED_IS_ACTIVATE)
            .orgType(UPDATED_ORG_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);
        return organisation;
    }

    @BeforeEach
    public void initTest() {
        organisation = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganisation() throws Exception {
        int databaseSizeBeforeCreate = organisationRepository.findAll().size();
        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);
        restOrganisationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeCreate + 1);
        Organisation testOrganisation = organisationList.get(organisationList.size() - 1);
        assertThat(testOrganisation.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testOrganisation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrganisation.getRegNumber()).isEqualTo(DEFAULT_REG_NUMBER);
        assertThat(testOrganisation.getGstin()).isEqualTo(DEFAULT_GSTIN);
        assertThat(testOrganisation.getPan()).isEqualTo(DEFAULT_PAN);
        assertThat(testOrganisation.getTan()).isEqualTo(DEFAULT_TAN);
        assertThat(testOrganisation.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testOrganisation.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrganisation.getWebSite()).isEqualTo(DEFAULT_WEB_SITE);
        assertThat(testOrganisation.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testOrganisation.getIsActivate()).isEqualTo(DEFAULT_IS_ACTIVATE);
        assertThat(testOrganisation.getOrgType()).isEqualTo(DEFAULT_ORG_TYPE);
        assertThat(testOrganisation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testOrganisation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrganisation.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testOrganisation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testOrganisation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testOrganisation.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testOrganisation.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testOrganisation.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testOrganisation.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testOrganisation.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void createOrganisationWithExistingId() throws Exception {
        // Create the Organisation with an existing ID
        organisation.setId(1L);
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        int databaseSizeBeforeCreate = organisationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganisationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOrgNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationRepository.findAll().size();
        // set the field null
        organisation.setOrgName(null);

        // Create the Organisation, which fails.
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        restOrganisationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRegNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationRepository.findAll().size();
        // set the field null
        organisation.setRegNumber(null);

        // Create the Organisation, which fails.
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        restOrganisationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGstinIsRequired() throws Exception {
        int databaseSizeBeforeTest = organisationRepository.findAll().size();
        // set the field null
        organisation.setGstin(null);

        // Create the Organisation, which fails.
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        restOrganisationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganisations() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList
        restOrganisationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].regNumber").value(hasItem(DEFAULT_REG_NUMBER)))
            .andExpect(jsonPath("$.[*].gstin").value(hasItem(DEFAULT_GSTIN)))
            .andExpect(jsonPath("$.[*].pan").value(hasItem(DEFAULT_PAN)))
            .andExpect(jsonPath("$.[*].tan").value(hasItem(DEFAULT_TAN)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].isActivate").value(hasItem(DEFAULT_IS_ACTIVATE.booleanValue())))
            .andExpect(jsonPath("$.[*].orgType").value(hasItem(DEFAULT_ORG_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)));
    }

    @Test
    @Transactional
    void getOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get the organisation
        restOrganisationMockMvc
            .perform(get(ENTITY_API_URL_ID, organisation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organisation.getId().intValue()))
            .andExpect(jsonPath("$.orgName").value(DEFAULT_ORG_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.regNumber").value(DEFAULT_REG_NUMBER))
            .andExpect(jsonPath("$.gstin").value(DEFAULT_GSTIN))
            .andExpect(jsonPath("$.pan").value(DEFAULT_PAN))
            .andExpect(jsonPath("$.tan").value(DEFAULT_TAN))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.webSite").value(DEFAULT_WEB_SITE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.isActivate").value(DEFAULT_IS_ACTIVATE.booleanValue()))
            .andExpect(jsonPath("$.orgType").value(DEFAULT_ORG_TYPE.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.freeField5").value(DEFAULT_FREE_FIELD_5));
    }

    @Test
    @Transactional
    void getOrganisationsByIdFiltering() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        Long id = organisation.getId();

        defaultOrganisationShouldBeFound("id.equals=" + id);
        defaultOrganisationShouldNotBeFound("id.notEquals=" + id);

        defaultOrganisationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrganisationShouldNotBeFound("id.greaterThan=" + id);

        defaultOrganisationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrganisationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrganisationsByOrgNameIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where orgName equals to DEFAULT_ORG_NAME
        defaultOrganisationShouldBeFound("orgName.equals=" + DEFAULT_ORG_NAME);

        // Get all the organisationList where orgName equals to UPDATED_ORG_NAME
        defaultOrganisationShouldNotBeFound("orgName.equals=" + UPDATED_ORG_NAME);
    }

    @Test
    @Transactional
    void getAllOrganisationsByOrgNameIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where orgName in DEFAULT_ORG_NAME or UPDATED_ORG_NAME
        defaultOrganisationShouldBeFound("orgName.in=" + DEFAULT_ORG_NAME + "," + UPDATED_ORG_NAME);

        // Get all the organisationList where orgName equals to UPDATED_ORG_NAME
        defaultOrganisationShouldNotBeFound("orgName.in=" + UPDATED_ORG_NAME);
    }

    @Test
    @Transactional
    void getAllOrganisationsByOrgNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where orgName is not null
        defaultOrganisationShouldBeFound("orgName.specified=true");

        // Get all the organisationList where orgName is null
        defaultOrganisationShouldNotBeFound("orgName.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByOrgNameContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where orgName contains DEFAULT_ORG_NAME
        defaultOrganisationShouldBeFound("orgName.contains=" + DEFAULT_ORG_NAME);

        // Get all the organisationList where orgName contains UPDATED_ORG_NAME
        defaultOrganisationShouldNotBeFound("orgName.contains=" + UPDATED_ORG_NAME);
    }

    @Test
    @Transactional
    void getAllOrganisationsByOrgNameNotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where orgName does not contain DEFAULT_ORG_NAME
        defaultOrganisationShouldNotBeFound("orgName.doesNotContain=" + DEFAULT_ORG_NAME);

        // Get all the organisationList where orgName does not contain UPDATED_ORG_NAME
        defaultOrganisationShouldBeFound("orgName.doesNotContain=" + UPDATED_ORG_NAME);
    }

    @Test
    @Transactional
    void getAllOrganisationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where description equals to DEFAULT_DESCRIPTION
        defaultOrganisationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the organisationList where description equals to UPDATED_DESCRIPTION
        defaultOrganisationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrganisationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultOrganisationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the organisationList where description equals to UPDATED_DESCRIPTION
        defaultOrganisationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrganisationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where description is not null
        defaultOrganisationShouldBeFound("description.specified=true");

        // Get all the organisationList where description is null
        defaultOrganisationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where description contains DEFAULT_DESCRIPTION
        defaultOrganisationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the organisationList where description contains UPDATED_DESCRIPTION
        defaultOrganisationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrganisationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where description does not contain DEFAULT_DESCRIPTION
        defaultOrganisationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the organisationList where description does not contain UPDATED_DESCRIPTION
        defaultOrganisationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrganisationsByRegNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where regNumber equals to DEFAULT_REG_NUMBER
        defaultOrganisationShouldBeFound("regNumber.equals=" + DEFAULT_REG_NUMBER);

        // Get all the organisationList where regNumber equals to UPDATED_REG_NUMBER
        defaultOrganisationShouldNotBeFound("regNumber.equals=" + UPDATED_REG_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganisationsByRegNumberIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where regNumber in DEFAULT_REG_NUMBER or UPDATED_REG_NUMBER
        defaultOrganisationShouldBeFound("regNumber.in=" + DEFAULT_REG_NUMBER + "," + UPDATED_REG_NUMBER);

        // Get all the organisationList where regNumber equals to UPDATED_REG_NUMBER
        defaultOrganisationShouldNotBeFound("regNumber.in=" + UPDATED_REG_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganisationsByRegNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where regNumber is not null
        defaultOrganisationShouldBeFound("regNumber.specified=true");

        // Get all the organisationList where regNumber is null
        defaultOrganisationShouldNotBeFound("regNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByRegNumberContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where regNumber contains DEFAULT_REG_NUMBER
        defaultOrganisationShouldBeFound("regNumber.contains=" + DEFAULT_REG_NUMBER);

        // Get all the organisationList where regNumber contains UPDATED_REG_NUMBER
        defaultOrganisationShouldNotBeFound("regNumber.contains=" + UPDATED_REG_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganisationsByRegNumberNotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where regNumber does not contain DEFAULT_REG_NUMBER
        defaultOrganisationShouldNotBeFound("regNumber.doesNotContain=" + DEFAULT_REG_NUMBER);

        // Get all the organisationList where regNumber does not contain UPDATED_REG_NUMBER
        defaultOrganisationShouldBeFound("regNumber.doesNotContain=" + UPDATED_REG_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganisationsByGstinIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where gstin equals to DEFAULT_GSTIN
        defaultOrganisationShouldBeFound("gstin.equals=" + DEFAULT_GSTIN);

        // Get all the organisationList where gstin equals to UPDATED_GSTIN
        defaultOrganisationShouldNotBeFound("gstin.equals=" + UPDATED_GSTIN);
    }

    @Test
    @Transactional
    void getAllOrganisationsByGstinIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where gstin in DEFAULT_GSTIN or UPDATED_GSTIN
        defaultOrganisationShouldBeFound("gstin.in=" + DEFAULT_GSTIN + "," + UPDATED_GSTIN);

        // Get all the organisationList where gstin equals to UPDATED_GSTIN
        defaultOrganisationShouldNotBeFound("gstin.in=" + UPDATED_GSTIN);
    }

    @Test
    @Transactional
    void getAllOrganisationsByGstinIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where gstin is not null
        defaultOrganisationShouldBeFound("gstin.specified=true");

        // Get all the organisationList where gstin is null
        defaultOrganisationShouldNotBeFound("gstin.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByGstinContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where gstin contains DEFAULT_GSTIN
        defaultOrganisationShouldBeFound("gstin.contains=" + DEFAULT_GSTIN);

        // Get all the organisationList where gstin contains UPDATED_GSTIN
        defaultOrganisationShouldNotBeFound("gstin.contains=" + UPDATED_GSTIN);
    }

    @Test
    @Transactional
    void getAllOrganisationsByGstinNotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where gstin does not contain DEFAULT_GSTIN
        defaultOrganisationShouldNotBeFound("gstin.doesNotContain=" + DEFAULT_GSTIN);

        // Get all the organisationList where gstin does not contain UPDATED_GSTIN
        defaultOrganisationShouldBeFound("gstin.doesNotContain=" + UPDATED_GSTIN);
    }

    @Test
    @Transactional
    void getAllOrganisationsByPanIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where pan equals to DEFAULT_PAN
        defaultOrganisationShouldBeFound("pan.equals=" + DEFAULT_PAN);

        // Get all the organisationList where pan equals to UPDATED_PAN
        defaultOrganisationShouldNotBeFound("pan.equals=" + UPDATED_PAN);
    }

    @Test
    @Transactional
    void getAllOrganisationsByPanIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where pan in DEFAULT_PAN or UPDATED_PAN
        defaultOrganisationShouldBeFound("pan.in=" + DEFAULT_PAN + "," + UPDATED_PAN);

        // Get all the organisationList where pan equals to UPDATED_PAN
        defaultOrganisationShouldNotBeFound("pan.in=" + UPDATED_PAN);
    }

    @Test
    @Transactional
    void getAllOrganisationsByPanIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where pan is not null
        defaultOrganisationShouldBeFound("pan.specified=true");

        // Get all the organisationList where pan is null
        defaultOrganisationShouldNotBeFound("pan.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByPanContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where pan contains DEFAULT_PAN
        defaultOrganisationShouldBeFound("pan.contains=" + DEFAULT_PAN);

        // Get all the organisationList where pan contains UPDATED_PAN
        defaultOrganisationShouldNotBeFound("pan.contains=" + UPDATED_PAN);
    }

    @Test
    @Transactional
    void getAllOrganisationsByPanNotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where pan does not contain DEFAULT_PAN
        defaultOrganisationShouldNotBeFound("pan.doesNotContain=" + DEFAULT_PAN);

        // Get all the organisationList where pan does not contain UPDATED_PAN
        defaultOrganisationShouldBeFound("pan.doesNotContain=" + UPDATED_PAN);
    }

    @Test
    @Transactional
    void getAllOrganisationsByTanIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where tan equals to DEFAULT_TAN
        defaultOrganisationShouldBeFound("tan.equals=" + DEFAULT_TAN);

        // Get all the organisationList where tan equals to UPDATED_TAN
        defaultOrganisationShouldNotBeFound("tan.equals=" + UPDATED_TAN);
    }

    @Test
    @Transactional
    void getAllOrganisationsByTanIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where tan in DEFAULT_TAN or UPDATED_TAN
        defaultOrganisationShouldBeFound("tan.in=" + DEFAULT_TAN + "," + UPDATED_TAN);

        // Get all the organisationList where tan equals to UPDATED_TAN
        defaultOrganisationShouldNotBeFound("tan.in=" + UPDATED_TAN);
    }

    @Test
    @Transactional
    void getAllOrganisationsByTanIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where tan is not null
        defaultOrganisationShouldBeFound("tan.specified=true");

        // Get all the organisationList where tan is null
        defaultOrganisationShouldNotBeFound("tan.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByTanContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where tan contains DEFAULT_TAN
        defaultOrganisationShouldBeFound("tan.contains=" + DEFAULT_TAN);

        // Get all the organisationList where tan contains UPDATED_TAN
        defaultOrganisationShouldNotBeFound("tan.contains=" + UPDATED_TAN);
    }

    @Test
    @Transactional
    void getAllOrganisationsByTanNotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where tan does not contain DEFAULT_TAN
        defaultOrganisationShouldNotBeFound("tan.doesNotContain=" + DEFAULT_TAN);

        // Get all the organisationList where tan does not contain UPDATED_TAN
        defaultOrganisationShouldBeFound("tan.doesNotContain=" + UPDATED_TAN);
    }

    @Test
    @Transactional
    void getAllOrganisationsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where phone equals to DEFAULT_PHONE
        defaultOrganisationShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the organisationList where phone equals to UPDATED_PHONE
        defaultOrganisationShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllOrganisationsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultOrganisationShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the organisationList where phone equals to UPDATED_PHONE
        defaultOrganisationShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllOrganisationsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where phone is not null
        defaultOrganisationShouldBeFound("phone.specified=true");

        // Get all the organisationList where phone is null
        defaultOrganisationShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where phone contains DEFAULT_PHONE
        defaultOrganisationShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the organisationList where phone contains UPDATED_PHONE
        defaultOrganisationShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllOrganisationsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where phone does not contain DEFAULT_PHONE
        defaultOrganisationShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the organisationList where phone does not contain UPDATED_PHONE
        defaultOrganisationShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllOrganisationsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where email equals to DEFAULT_EMAIL
        defaultOrganisationShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the organisationList where email equals to UPDATED_EMAIL
        defaultOrganisationShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllOrganisationsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultOrganisationShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the organisationList where email equals to UPDATED_EMAIL
        defaultOrganisationShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllOrganisationsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where email is not null
        defaultOrganisationShouldBeFound("email.specified=true");

        // Get all the organisationList where email is null
        defaultOrganisationShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByEmailContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where email contains DEFAULT_EMAIL
        defaultOrganisationShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the organisationList where email contains UPDATED_EMAIL
        defaultOrganisationShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllOrganisationsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where email does not contain DEFAULT_EMAIL
        defaultOrganisationShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the organisationList where email does not contain UPDATED_EMAIL
        defaultOrganisationShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllOrganisationsByWebSiteIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where webSite equals to DEFAULT_WEB_SITE
        defaultOrganisationShouldBeFound("webSite.equals=" + DEFAULT_WEB_SITE);

        // Get all the organisationList where webSite equals to UPDATED_WEB_SITE
        defaultOrganisationShouldNotBeFound("webSite.equals=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllOrganisationsByWebSiteIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where webSite in DEFAULT_WEB_SITE or UPDATED_WEB_SITE
        defaultOrganisationShouldBeFound("webSite.in=" + DEFAULT_WEB_SITE + "," + UPDATED_WEB_SITE);

        // Get all the organisationList where webSite equals to UPDATED_WEB_SITE
        defaultOrganisationShouldNotBeFound("webSite.in=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllOrganisationsByWebSiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where webSite is not null
        defaultOrganisationShouldBeFound("webSite.specified=true");

        // Get all the organisationList where webSite is null
        defaultOrganisationShouldNotBeFound("webSite.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByWebSiteContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where webSite contains DEFAULT_WEB_SITE
        defaultOrganisationShouldBeFound("webSite.contains=" + DEFAULT_WEB_SITE);

        // Get all the organisationList where webSite contains UPDATED_WEB_SITE
        defaultOrganisationShouldNotBeFound("webSite.contains=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllOrganisationsByWebSiteNotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where webSite does not contain DEFAULT_WEB_SITE
        defaultOrganisationShouldNotBeFound("webSite.doesNotContain=" + DEFAULT_WEB_SITE);

        // Get all the organisationList where webSite does not contain UPDATED_WEB_SITE
        defaultOrganisationShouldBeFound("webSite.doesNotContain=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where fax equals to DEFAULT_FAX
        defaultOrganisationShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the organisationList where fax equals to UPDATED_FAX
        defaultOrganisationShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultOrganisationShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the organisationList where fax equals to UPDATED_FAX
        defaultOrganisationShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where fax is not null
        defaultOrganisationShouldBeFound("fax.specified=true");

        // Get all the organisationList where fax is null
        defaultOrganisationShouldNotBeFound("fax.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByFaxContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where fax contains DEFAULT_FAX
        defaultOrganisationShouldBeFound("fax.contains=" + DEFAULT_FAX);

        // Get all the organisationList where fax contains UPDATED_FAX
        defaultOrganisationShouldNotBeFound("fax.contains=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFaxNotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where fax does not contain DEFAULT_FAX
        defaultOrganisationShouldNotBeFound("fax.doesNotContain=" + DEFAULT_FAX);

        // Get all the organisationList where fax does not contain UPDATED_FAX
        defaultOrganisationShouldBeFound("fax.doesNotContain=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllOrganisationsByIsActivateIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where isActivate equals to DEFAULT_IS_ACTIVATE
        defaultOrganisationShouldBeFound("isActivate.equals=" + DEFAULT_IS_ACTIVATE);

        // Get all the organisationList where isActivate equals to UPDATED_IS_ACTIVATE
        defaultOrganisationShouldNotBeFound("isActivate.equals=" + UPDATED_IS_ACTIVATE);
    }

    @Test
    @Transactional
    void getAllOrganisationsByIsActivateIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where isActivate in DEFAULT_IS_ACTIVATE or UPDATED_IS_ACTIVATE
        defaultOrganisationShouldBeFound("isActivate.in=" + DEFAULT_IS_ACTIVATE + "," + UPDATED_IS_ACTIVATE);

        // Get all the organisationList where isActivate equals to UPDATED_IS_ACTIVATE
        defaultOrganisationShouldNotBeFound("isActivate.in=" + UPDATED_IS_ACTIVATE);
    }

    @Test
    @Transactional
    void getAllOrganisationsByIsActivateIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where isActivate is not null
        defaultOrganisationShouldBeFound("isActivate.specified=true");

        // Get all the organisationList where isActivate is null
        defaultOrganisationShouldNotBeFound("isActivate.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByOrgTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where orgType equals to DEFAULT_ORG_TYPE
        defaultOrganisationShouldBeFound("orgType.equals=" + DEFAULT_ORG_TYPE);

        // Get all the organisationList where orgType equals to UPDATED_ORG_TYPE
        defaultOrganisationShouldNotBeFound("orgType.equals=" + UPDATED_ORG_TYPE);
    }

    @Test
    @Transactional
    void getAllOrganisationsByOrgTypeIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where orgType in DEFAULT_ORG_TYPE or UPDATED_ORG_TYPE
        defaultOrganisationShouldBeFound("orgType.in=" + DEFAULT_ORG_TYPE + "," + UPDATED_ORG_TYPE);

        // Get all the organisationList where orgType equals to UPDATED_ORG_TYPE
        defaultOrganisationShouldNotBeFound("orgType.in=" + UPDATED_ORG_TYPE);
    }

    @Test
    @Transactional
    void getAllOrganisationsByOrgTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where orgType is not null
        defaultOrganisationShouldBeFound("orgType.specified=true");

        // Get all the organisationList where orgType is null
        defaultOrganisationShouldNotBeFound("orgType.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where createdOn equals to DEFAULT_CREATED_ON
        defaultOrganisationShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the organisationList where createdOn equals to UPDATED_CREATED_ON
        defaultOrganisationShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllOrganisationsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultOrganisationShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the organisationList where createdOn equals to UPDATED_CREATED_ON
        defaultOrganisationShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllOrganisationsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where createdOn is not null
        defaultOrganisationShouldBeFound("createdOn.specified=true");

        // Get all the organisationList where createdOn is null
        defaultOrganisationShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where createdBy equals to DEFAULT_CREATED_BY
        defaultOrganisationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the organisationList where createdBy equals to UPDATED_CREATED_BY
        defaultOrganisationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrganisationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultOrganisationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the organisationList where createdBy equals to UPDATED_CREATED_BY
        defaultOrganisationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrganisationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where createdBy is not null
        defaultOrganisationShouldBeFound("createdBy.specified=true");

        // Get all the organisationList where createdBy is null
        defaultOrganisationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where createdBy contains DEFAULT_CREATED_BY
        defaultOrganisationShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the organisationList where createdBy contains UPDATED_CREATED_BY
        defaultOrganisationShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrganisationsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where createdBy does not contain DEFAULT_CREATED_BY
        defaultOrganisationShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the organisationList where createdBy does not contain UPDATED_CREATED_BY
        defaultOrganisationShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrganisationsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where isDeleted equals to DEFAULT_IS_DELETED
        defaultOrganisationShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the organisationList where isDeleted equals to UPDATED_IS_DELETED
        defaultOrganisationShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllOrganisationsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultOrganisationShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the organisationList where isDeleted equals to UPDATED_IS_DELETED
        defaultOrganisationShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllOrganisationsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where isDeleted is not null
        defaultOrganisationShouldBeFound("isDeleted.specified=true");

        // Get all the organisationList where isDeleted is null
        defaultOrganisationShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByIsDeletedContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where isDeleted contains DEFAULT_IS_DELETED
        defaultOrganisationShouldBeFound("isDeleted.contains=" + DEFAULT_IS_DELETED);

        // Get all the organisationList where isDeleted contains UPDATED_IS_DELETED
        defaultOrganisationShouldNotBeFound("isDeleted.contains=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllOrganisationsByIsDeletedNotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where isDeleted does not contain DEFAULT_IS_DELETED
        defaultOrganisationShouldNotBeFound("isDeleted.doesNotContain=" + DEFAULT_IS_DELETED);

        // Get all the organisationList where isDeleted does not contain UPDATED_IS_DELETED
        defaultOrganisationShouldBeFound("isDeleted.doesNotContain=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllOrganisationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultOrganisationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the organisationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultOrganisationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllOrganisationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultOrganisationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the organisationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultOrganisationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllOrganisationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where lastModified is not null
        defaultOrganisationShouldBeFound("lastModified.specified=true");

        // Get all the organisationList where lastModified is null
        defaultOrganisationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultOrganisationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the organisationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOrganisationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrganisationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultOrganisationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the organisationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOrganisationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrganisationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where lastModifiedBy is not null
        defaultOrganisationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the organisationList where lastModifiedBy is null
        defaultOrganisationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultOrganisationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the organisationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultOrganisationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrganisationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultOrganisationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the organisationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultOrganisationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultOrganisationShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the organisationList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultOrganisationShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultOrganisationShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the organisationList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultOrganisationShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField1 is not null
        defaultOrganisationShouldBeFound("freeField1.specified=true");

        // Get all the organisationList where freeField1 is null
        defaultOrganisationShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultOrganisationShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the organisationList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultOrganisationShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultOrganisationShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the organisationList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultOrganisationShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultOrganisationShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the organisationList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultOrganisationShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultOrganisationShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the organisationList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultOrganisationShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField2 is not null
        defaultOrganisationShouldBeFound("freeField2.specified=true");

        // Get all the organisationList where freeField2 is null
        defaultOrganisationShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultOrganisationShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the organisationList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultOrganisationShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultOrganisationShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the organisationList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultOrganisationShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultOrganisationShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the organisationList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultOrganisationShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultOrganisationShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the organisationList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultOrganisationShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField3 is not null
        defaultOrganisationShouldBeFound("freeField3.specified=true");

        // Get all the organisationList where freeField3 is null
        defaultOrganisationShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultOrganisationShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the organisationList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultOrganisationShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultOrganisationShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the organisationList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultOrganisationShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultOrganisationShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the organisationList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultOrganisationShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultOrganisationShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the organisationList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultOrganisationShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField4 is not null
        defaultOrganisationShouldBeFound("freeField4.specified=true");

        // Get all the organisationList where freeField4 is null
        defaultOrganisationShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultOrganisationShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the organisationList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultOrganisationShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultOrganisationShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the organisationList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultOrganisationShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultOrganisationShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the organisationList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultOrganisationShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultOrganisationShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the organisationList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultOrganisationShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField5 is not null
        defaultOrganisationShouldBeFound("freeField5.specified=true");

        // Get all the organisationList where freeField5 is null
        defaultOrganisationShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultOrganisationShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the organisationList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultOrganisationShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllOrganisationsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        // Get all the organisationList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultOrganisationShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the organisationList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultOrganisationShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllOrganisationsByAddressIsEqualToSomething() throws Exception {
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            organisationRepository.saveAndFlush(organisation);
            address = AddressResourceIT.createEntity(em);
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        em.persist(address);
        em.flush();
        organisation.setAddress(address);
        organisationRepository.saveAndFlush(organisation);
        Long addressId = address.getId();

        // Get all the organisationList where address equals to addressId
        defaultOrganisationShouldBeFound("addressId.equals=" + addressId);

        // Get all the organisationList where address equals to (addressId + 1)
        defaultOrganisationShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrganisationShouldBeFound(String filter) throws Exception {
        restOrganisationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organisation.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].regNumber").value(hasItem(DEFAULT_REG_NUMBER)))
            .andExpect(jsonPath("$.[*].gstin").value(hasItem(DEFAULT_GSTIN)))
            .andExpect(jsonPath("$.[*].pan").value(hasItem(DEFAULT_PAN)))
            .andExpect(jsonPath("$.[*].tan").value(hasItem(DEFAULT_TAN)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].isActivate").value(hasItem(DEFAULT_IS_ACTIVATE.booleanValue())))
            .andExpect(jsonPath("$.[*].orgType").value(hasItem(DEFAULT_ORG_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)));

        // Check, that the count call also returns 1
        restOrganisationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrganisationShouldNotBeFound(String filter) throws Exception {
        restOrganisationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrganisationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrganisation() throws Exception {
        // Get the organisation
        restOrganisationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();

        // Update the organisation
        Organisation updatedOrganisation = organisationRepository.findById(organisation.getId()).get();
        // Disconnect from session so that the updates on updatedOrganisation are not directly saved in db
        em.detach(updatedOrganisation);
        updatedOrganisation
            .orgName(UPDATED_ORG_NAME)
            .description(UPDATED_DESCRIPTION)
            .regNumber(UPDATED_REG_NUMBER)
            .gstin(UPDATED_GSTIN)
            .pan(UPDATED_PAN)
            .tan(UPDATED_TAN)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .webSite(UPDATED_WEB_SITE)
            .fax(UPDATED_FAX)
            .isActivate(UPDATED_IS_ACTIVATE)
            .orgType(UPDATED_ORG_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);
        OrganisationDTO organisationDTO = organisationMapper.toDto(updatedOrganisation);

        restOrganisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organisationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
        Organisation testOrganisation = organisationList.get(organisationList.size() - 1);
        assertThat(testOrganisation.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testOrganisation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrganisation.getRegNumber()).isEqualTo(UPDATED_REG_NUMBER);
        assertThat(testOrganisation.getGstin()).isEqualTo(UPDATED_GSTIN);
        assertThat(testOrganisation.getPan()).isEqualTo(UPDATED_PAN);
        assertThat(testOrganisation.getTan()).isEqualTo(UPDATED_TAN);
        assertThat(testOrganisation.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOrganisation.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrganisation.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testOrganisation.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testOrganisation.getIsActivate()).isEqualTo(UPDATED_IS_ACTIVATE);
        assertThat(testOrganisation.getOrgType()).isEqualTo(UPDATED_ORG_TYPE);
        assertThat(testOrganisation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testOrganisation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrganisation.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testOrganisation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testOrganisation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOrganisation.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testOrganisation.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testOrganisation.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testOrganisation.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testOrganisation.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void putNonExistingOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();
        organisation.setId(count.incrementAndGet());

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organisationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();
        organisation.setId(count.incrementAndGet());

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();
        organisation.setId(count.incrementAndGet());

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganisationWithPatch() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();

        // Update the organisation using partial update
        Organisation partialUpdatedOrganisation = new Organisation();
        partialUpdatedOrganisation.setId(organisation.getId());

        partialUpdatedOrganisation
            .description(UPDATED_DESCRIPTION)
            .regNumber(UPDATED_REG_NUMBER)
            .tan(UPDATED_TAN)
            .fax(UPDATED_FAX)
            .isActivate(UPDATED_IS_ACTIVATE)
            .orgType(UPDATED_ORG_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField4(UPDATED_FREE_FIELD_4);

        restOrganisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganisation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganisation))
            )
            .andExpect(status().isOk());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
        Organisation testOrganisation = organisationList.get(organisationList.size() - 1);
        assertThat(testOrganisation.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testOrganisation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrganisation.getRegNumber()).isEqualTo(UPDATED_REG_NUMBER);
        assertThat(testOrganisation.getGstin()).isEqualTo(DEFAULT_GSTIN);
        assertThat(testOrganisation.getPan()).isEqualTo(DEFAULT_PAN);
        assertThat(testOrganisation.getTan()).isEqualTo(UPDATED_TAN);
        assertThat(testOrganisation.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testOrganisation.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrganisation.getWebSite()).isEqualTo(DEFAULT_WEB_SITE);
        assertThat(testOrganisation.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testOrganisation.getIsActivate()).isEqualTo(UPDATED_IS_ACTIVATE);
        assertThat(testOrganisation.getOrgType()).isEqualTo(UPDATED_ORG_TYPE);
        assertThat(testOrganisation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testOrganisation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrganisation.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testOrganisation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testOrganisation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testOrganisation.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testOrganisation.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testOrganisation.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testOrganisation.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testOrganisation.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void fullUpdateOrganisationWithPatch() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();

        // Update the organisation using partial update
        Organisation partialUpdatedOrganisation = new Organisation();
        partialUpdatedOrganisation.setId(organisation.getId());

        partialUpdatedOrganisation
            .orgName(UPDATED_ORG_NAME)
            .description(UPDATED_DESCRIPTION)
            .regNumber(UPDATED_REG_NUMBER)
            .gstin(UPDATED_GSTIN)
            .pan(UPDATED_PAN)
            .tan(UPDATED_TAN)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .webSite(UPDATED_WEB_SITE)
            .fax(UPDATED_FAX)
            .isActivate(UPDATED_IS_ACTIVATE)
            .orgType(UPDATED_ORG_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);

        restOrganisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganisation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganisation))
            )
            .andExpect(status().isOk());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
        Organisation testOrganisation = organisationList.get(organisationList.size() - 1);
        assertThat(testOrganisation.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testOrganisation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrganisation.getRegNumber()).isEqualTo(UPDATED_REG_NUMBER);
        assertThat(testOrganisation.getGstin()).isEqualTo(UPDATED_GSTIN);
        assertThat(testOrganisation.getPan()).isEqualTo(UPDATED_PAN);
        assertThat(testOrganisation.getTan()).isEqualTo(UPDATED_TAN);
        assertThat(testOrganisation.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOrganisation.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrganisation.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testOrganisation.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testOrganisation.getIsActivate()).isEqualTo(UPDATED_IS_ACTIVATE);
        assertThat(testOrganisation.getOrgType()).isEqualTo(UPDATED_ORG_TYPE);
        assertThat(testOrganisation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testOrganisation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrganisation.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testOrganisation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testOrganisation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOrganisation.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testOrganisation.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testOrganisation.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testOrganisation.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testOrganisation.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void patchNonExistingOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();
        organisation.setId(count.incrementAndGet());

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organisationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();
        organisation.setId(count.incrementAndGet());

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganisation() throws Exception {
        int databaseSizeBeforeUpdate = organisationRepository.findAll().size();
        organisation.setId(count.incrementAndGet());

        // Create the Organisation
        OrganisationDTO organisationDTO = organisationMapper.toDto(organisation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganisationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organisationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organisation in the database
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganisation() throws Exception {
        // Initialize the database
        organisationRepository.saveAndFlush(organisation);

        int databaseSizeBeforeDelete = organisationRepository.findAll().size();

        // Delete the organisation
        restOrganisationMockMvc
            .perform(delete(ENTITY_API_URL_ID, organisation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Organisation> organisationList = organisationRepository.findAll();
        assertThat(organisationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
