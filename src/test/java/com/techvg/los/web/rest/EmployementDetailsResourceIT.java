package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.EmployementDetails;
import com.techvg.los.domain.Member;
import com.techvg.los.domain.enumeration.CompanyType;
import com.techvg.los.domain.enumeration.ConstitutionType;
import com.techvg.los.domain.enumeration.EmployementStatus;
import com.techvg.los.domain.enumeration.IndustryType;
import com.techvg.los.domain.enumeration.Occupation;
import com.techvg.los.repository.EmployementDetailsRepository;
import com.techvg.los.service.criteria.EmployementDetailsCriteria;
import com.techvg.los.service.dto.EmployementDetailsDTO;
import com.techvg.los.service.mapper.EmployementDetailsMapper;
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
 * Integration tests for the {@link EmployementDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployementDetailsResourceIT {

    private static final Occupation DEFAULT_TYPE = Occupation.SALARIED;
    private static final Occupation UPDATED_TYPE = Occupation.BUSINESS;

    private static final String DEFAULT_EMPLOYER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYER_NAME = "BBBBBBBBBB";

    private static final EmployementStatus DEFAULT_STATUS = EmployementStatus.REGULAR;
    private static final EmployementStatus UPDATED_STATUS = EmployementStatus.TEMPORARY;

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_DURATION = "AAAAAAAAAA";
    private static final String UPDATED_DURATION = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYER_ADD = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYER_ADD = "BBBBBBBBBB";

    private static final String DEFAULT_PREV_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PREV_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PREV_COMPANYDURATION = "AAAAAAAAAA";
    private static final String UPDATED_PREV_COMPANYDURATION = "BBBBBBBBBB";

    private static final CompanyType DEFAULT_ORG_TYPE = CompanyType.GOVERNMENT_SECTOR;
    private static final CompanyType UPDATED_ORG_TYPE = CompanyType.PUBLIC_SECTOR;

    private static final ConstitutionType DEFAULT_CONSTITUTION_TYPE = ConstitutionType.SOLE_PROPRIETOR;
    private static final ConstitutionType UPDATED_CONSTITUTION_TYPE = ConstitutionType.INDIVIDUAL;

    private static final IndustryType DEFAULT_INDUSTRY_TYPE = IndustryType.MANUFACTURING;
    private static final IndustryType UPDATED_INDUSTRY_TYPE = IndustryType.SERVICE_INDUSTRY;

    private static final String DEFAULT_BUSINESS_REG_NO = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_REG_NO = "BBBBBBBBBB";

    private static final Double DEFAULT_COMP_OWNER_SHIP = 1D;
    private static final Double UPDATED_COMP_OWNER_SHIP = 2D;
    private static final Double SMALLER_COMP_OWNER_SHIP = 1D - 1D;

    private static final String DEFAULT_PARTNER_NAME_1 = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_NAME_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_NAME_2 = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_NAME_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PARTNER_NAME_3 = "AAAAAAAAAA";
    private static final String UPDATED_PARTNER_NAME_3 = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/employement-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployementDetailsRepository employementDetailsRepository;

    @Autowired
    private EmployementDetailsMapper employementDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployementDetailsMockMvc;

    private EmployementDetails employementDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployementDetails createEntity(EntityManager em) {
        EmployementDetails employementDetails = new EmployementDetails()
            .type(DEFAULT_TYPE)
            .employerName(DEFAULT_EMPLOYER_NAME)
            .status(DEFAULT_STATUS)
            .designation(DEFAULT_DESIGNATION)
            .duration(DEFAULT_DURATION)
            .employerAdd(DEFAULT_EMPLOYER_ADD)
            .prevCompanyName(DEFAULT_PREV_COMPANY_NAME)
            .prevCompanyduration(DEFAULT_PREV_COMPANYDURATION)
            .orgType(DEFAULT_ORG_TYPE)
            .constitutionType(DEFAULT_CONSTITUTION_TYPE)
            .industryType(DEFAULT_INDUSTRY_TYPE)
            .businessRegNo(DEFAULT_BUSINESS_REG_NO)
            .compOwnerShip(DEFAULT_COMP_OWNER_SHIP)
            .partnerName1(DEFAULT_PARTNER_NAME_1)
            .partnerName2(DEFAULT_PARTNER_NAME_2)
            .partnerName3(DEFAULT_PARTNER_NAME_3)
            .isDeleted(DEFAULT_IS_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5)
            .freeField6(DEFAULT_FREE_FIELD_6);
        return employementDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployementDetails createUpdatedEntity(EntityManager em) {
        EmployementDetails employementDetails = new EmployementDetails()
            .type(UPDATED_TYPE)
            .employerName(UPDATED_EMPLOYER_NAME)
            .status(UPDATED_STATUS)
            .designation(UPDATED_DESIGNATION)
            .duration(UPDATED_DURATION)
            .employerAdd(UPDATED_EMPLOYER_ADD)
            .prevCompanyName(UPDATED_PREV_COMPANY_NAME)
            .prevCompanyduration(UPDATED_PREV_COMPANYDURATION)
            .orgType(UPDATED_ORG_TYPE)
            .constitutionType(UPDATED_CONSTITUTION_TYPE)
            .industryType(UPDATED_INDUSTRY_TYPE)
            .businessRegNo(UPDATED_BUSINESS_REG_NO)
            .compOwnerShip(UPDATED_COMP_OWNER_SHIP)
            .partnerName1(UPDATED_PARTNER_NAME_1)
            .partnerName2(UPDATED_PARTNER_NAME_2)
            .partnerName3(UPDATED_PARTNER_NAME_3)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        return employementDetails;
    }

    @BeforeEach
    public void initTest() {
        employementDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployementDetails() throws Exception {
        int databaseSizeBeforeCreate = employementDetailsRepository.findAll().size();
        // Create the EmployementDetails
        EmployementDetailsDTO employementDetailsDTO = employementDetailsMapper.toDto(employementDetails);
        restEmployementDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employementDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmployementDetails in the database
        List<EmployementDetails> employementDetailsList = employementDetailsRepository.findAll();
        assertThat(employementDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployementDetails testEmployementDetails = employementDetailsList.get(employementDetailsList.size() - 1);
        assertThat(testEmployementDetails.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEmployementDetails.getEmployerName()).isEqualTo(DEFAULT_EMPLOYER_NAME);
        assertThat(testEmployementDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployementDetails.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testEmployementDetails.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testEmployementDetails.getEmployerAdd()).isEqualTo(DEFAULT_EMPLOYER_ADD);
        assertThat(testEmployementDetails.getPrevCompanyName()).isEqualTo(DEFAULT_PREV_COMPANY_NAME);
        assertThat(testEmployementDetails.getPrevCompanyduration()).isEqualTo(DEFAULT_PREV_COMPANYDURATION);
        assertThat(testEmployementDetails.getOrgType()).isEqualTo(DEFAULT_ORG_TYPE);
        assertThat(testEmployementDetails.getConstitutionType()).isEqualTo(DEFAULT_CONSTITUTION_TYPE);
        assertThat(testEmployementDetails.getIndustryType()).isEqualTo(DEFAULT_INDUSTRY_TYPE);
        assertThat(testEmployementDetails.getBusinessRegNo()).isEqualTo(DEFAULT_BUSINESS_REG_NO);
        assertThat(testEmployementDetails.getCompOwnerShip()).isEqualTo(DEFAULT_COMP_OWNER_SHIP);
        assertThat(testEmployementDetails.getPartnerName1()).isEqualTo(DEFAULT_PARTNER_NAME_1);
        assertThat(testEmployementDetails.getPartnerName2()).isEqualTo(DEFAULT_PARTNER_NAME_2);
        assertThat(testEmployementDetails.getPartnerName3()).isEqualTo(DEFAULT_PARTNER_NAME_3);
        assertThat(testEmployementDetails.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testEmployementDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testEmployementDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testEmployementDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmployementDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEmployementDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testEmployementDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testEmployementDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testEmployementDetails.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testEmployementDetails.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testEmployementDetails.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createEmployementDetailsWithExistingId() throws Exception {
        // Create the EmployementDetails with an existing ID
        employementDetails.setId(1L);
        EmployementDetailsDTO employementDetailsDTO = employementDetailsMapper.toDto(employementDetails);

        int databaseSizeBeforeCreate = employementDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployementDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employementDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployementDetails in the database
        List<EmployementDetails> employementDetailsList = employementDetailsRepository.findAll();
        assertThat(employementDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployementDetails() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList
        restEmployementDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employementDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].employerName").value(hasItem(DEFAULT_EMPLOYER_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].employerAdd").value(hasItem(DEFAULT_EMPLOYER_ADD)))
            .andExpect(jsonPath("$.[*].prevCompanyName").value(hasItem(DEFAULT_PREV_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].prevCompanyduration").value(hasItem(DEFAULT_PREV_COMPANYDURATION)))
            .andExpect(jsonPath("$.[*].orgType").value(hasItem(DEFAULT_ORG_TYPE.toString())))
            .andExpect(jsonPath("$.[*].constitutionType").value(hasItem(DEFAULT_CONSTITUTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].industryType").value(hasItem(DEFAULT_INDUSTRY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].businessRegNo").value(hasItem(DEFAULT_BUSINESS_REG_NO)))
            .andExpect(jsonPath("$.[*].compOwnerShip").value(hasItem(DEFAULT_COMP_OWNER_SHIP.doubleValue())))
            .andExpect(jsonPath("$.[*].partnerName1").value(hasItem(DEFAULT_PARTNER_NAME_1)))
            .andExpect(jsonPath("$.[*].partnerName2").value(hasItem(DEFAULT_PARTNER_NAME_2)))
            .andExpect(jsonPath("$.[*].partnerName3").value(hasItem(DEFAULT_PARTNER_NAME_3)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));
    }

    @Test
    @Transactional
    void getEmployementDetails() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get the employementDetails
        restEmployementDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, employementDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employementDetails.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.employerName").value(DEFAULT_EMPLOYER_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.employerAdd").value(DEFAULT_EMPLOYER_ADD))
            .andExpect(jsonPath("$.prevCompanyName").value(DEFAULT_PREV_COMPANY_NAME))
            .andExpect(jsonPath("$.prevCompanyduration").value(DEFAULT_PREV_COMPANYDURATION))
            .andExpect(jsonPath("$.orgType").value(DEFAULT_ORG_TYPE.toString()))
            .andExpect(jsonPath("$.constitutionType").value(DEFAULT_CONSTITUTION_TYPE.toString()))
            .andExpect(jsonPath("$.industryType").value(DEFAULT_INDUSTRY_TYPE.toString()))
            .andExpect(jsonPath("$.businessRegNo").value(DEFAULT_BUSINESS_REG_NO))
            .andExpect(jsonPath("$.compOwnerShip").value(DEFAULT_COMP_OWNER_SHIP.doubleValue()))
            .andExpect(jsonPath("$.partnerName1").value(DEFAULT_PARTNER_NAME_1))
            .andExpect(jsonPath("$.partnerName2").value(DEFAULT_PARTNER_NAME_2))
            .andExpect(jsonPath("$.partnerName3").value(DEFAULT_PARTNER_NAME_3))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.freeField5").value(DEFAULT_FREE_FIELD_5))
            .andExpect(jsonPath("$.freeField6").value(DEFAULT_FREE_FIELD_6));
    }

    @Test
    @Transactional
    void getEmployementDetailsByIdFiltering() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        Long id = employementDetails.getId();

        defaultEmployementDetailsShouldBeFound("id.equals=" + id);
        defaultEmployementDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultEmployementDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployementDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployementDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployementDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where type equals to DEFAULT_TYPE
        defaultEmployementDetailsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the employementDetailsList where type equals to UPDATED_TYPE
        defaultEmployementDetailsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultEmployementDetailsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the employementDetailsList where type equals to UPDATED_TYPE
        defaultEmployementDetailsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where type is not null
        defaultEmployementDetailsShouldBeFound("type.specified=true");

        // Get all the employementDetailsList where type is null
        defaultEmployementDetailsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByEmployerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where employerName equals to DEFAULT_EMPLOYER_NAME
        defaultEmployementDetailsShouldBeFound("employerName.equals=" + DEFAULT_EMPLOYER_NAME);

        // Get all the employementDetailsList where employerName equals to UPDATED_EMPLOYER_NAME
        defaultEmployementDetailsShouldNotBeFound("employerName.equals=" + UPDATED_EMPLOYER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByEmployerNameIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where employerName in DEFAULT_EMPLOYER_NAME or UPDATED_EMPLOYER_NAME
        defaultEmployementDetailsShouldBeFound("employerName.in=" + DEFAULT_EMPLOYER_NAME + "," + UPDATED_EMPLOYER_NAME);

        // Get all the employementDetailsList where employerName equals to UPDATED_EMPLOYER_NAME
        defaultEmployementDetailsShouldNotBeFound("employerName.in=" + UPDATED_EMPLOYER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByEmployerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where employerName is not null
        defaultEmployementDetailsShouldBeFound("employerName.specified=true");

        // Get all the employementDetailsList where employerName is null
        defaultEmployementDetailsShouldNotBeFound("employerName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByEmployerNameContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where employerName contains DEFAULT_EMPLOYER_NAME
        defaultEmployementDetailsShouldBeFound("employerName.contains=" + DEFAULT_EMPLOYER_NAME);

        // Get all the employementDetailsList where employerName contains UPDATED_EMPLOYER_NAME
        defaultEmployementDetailsShouldNotBeFound("employerName.contains=" + UPDATED_EMPLOYER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByEmployerNameNotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where employerName does not contain DEFAULT_EMPLOYER_NAME
        defaultEmployementDetailsShouldNotBeFound("employerName.doesNotContain=" + DEFAULT_EMPLOYER_NAME);

        // Get all the employementDetailsList where employerName does not contain UPDATED_EMPLOYER_NAME
        defaultEmployementDetailsShouldBeFound("employerName.doesNotContain=" + UPDATED_EMPLOYER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where status equals to DEFAULT_STATUS
        defaultEmployementDetailsShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the employementDetailsList where status equals to UPDATED_STATUS
        defaultEmployementDetailsShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEmployementDetailsShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the employementDetailsList where status equals to UPDATED_STATUS
        defaultEmployementDetailsShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where status is not null
        defaultEmployementDetailsShouldBeFound("status.specified=true");

        // Get all the employementDetailsList where status is null
        defaultEmployementDetailsShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where designation equals to DEFAULT_DESIGNATION
        defaultEmployementDetailsShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the employementDetailsList where designation equals to UPDATED_DESIGNATION
        defaultEmployementDetailsShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultEmployementDetailsShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the employementDetailsList where designation equals to UPDATED_DESIGNATION
        defaultEmployementDetailsShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where designation is not null
        defaultEmployementDetailsShouldBeFound("designation.specified=true");

        // Get all the employementDetailsList where designation is null
        defaultEmployementDetailsShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByDesignationContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where designation contains DEFAULT_DESIGNATION
        defaultEmployementDetailsShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the employementDetailsList where designation contains UPDATED_DESIGNATION
        defaultEmployementDetailsShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where designation does not contain DEFAULT_DESIGNATION
        defaultEmployementDetailsShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the employementDetailsList where designation does not contain UPDATED_DESIGNATION
        defaultEmployementDetailsShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where duration equals to DEFAULT_DURATION
        defaultEmployementDetailsShouldBeFound("duration.equals=" + DEFAULT_DURATION);

        // Get all the employementDetailsList where duration equals to UPDATED_DURATION
        defaultEmployementDetailsShouldNotBeFound("duration.equals=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByDurationIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where duration in DEFAULT_DURATION or UPDATED_DURATION
        defaultEmployementDetailsShouldBeFound("duration.in=" + DEFAULT_DURATION + "," + UPDATED_DURATION);

        // Get all the employementDetailsList where duration equals to UPDATED_DURATION
        defaultEmployementDetailsShouldNotBeFound("duration.in=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where duration is not null
        defaultEmployementDetailsShouldBeFound("duration.specified=true");

        // Get all the employementDetailsList where duration is null
        defaultEmployementDetailsShouldNotBeFound("duration.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByDurationContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where duration contains DEFAULT_DURATION
        defaultEmployementDetailsShouldBeFound("duration.contains=" + DEFAULT_DURATION);

        // Get all the employementDetailsList where duration contains UPDATED_DURATION
        defaultEmployementDetailsShouldNotBeFound("duration.contains=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByDurationNotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where duration does not contain DEFAULT_DURATION
        defaultEmployementDetailsShouldNotBeFound("duration.doesNotContain=" + DEFAULT_DURATION);

        // Get all the employementDetailsList where duration does not contain UPDATED_DURATION
        defaultEmployementDetailsShouldBeFound("duration.doesNotContain=" + UPDATED_DURATION);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByEmployerAddIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where employerAdd equals to DEFAULT_EMPLOYER_ADD
        defaultEmployementDetailsShouldBeFound("employerAdd.equals=" + DEFAULT_EMPLOYER_ADD);

        // Get all the employementDetailsList where employerAdd equals to UPDATED_EMPLOYER_ADD
        defaultEmployementDetailsShouldNotBeFound("employerAdd.equals=" + UPDATED_EMPLOYER_ADD);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByEmployerAddIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where employerAdd in DEFAULT_EMPLOYER_ADD or UPDATED_EMPLOYER_ADD
        defaultEmployementDetailsShouldBeFound("employerAdd.in=" + DEFAULT_EMPLOYER_ADD + "," + UPDATED_EMPLOYER_ADD);

        // Get all the employementDetailsList where employerAdd equals to UPDATED_EMPLOYER_ADD
        defaultEmployementDetailsShouldNotBeFound("employerAdd.in=" + UPDATED_EMPLOYER_ADD);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByEmployerAddIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where employerAdd is not null
        defaultEmployementDetailsShouldBeFound("employerAdd.specified=true");

        // Get all the employementDetailsList where employerAdd is null
        defaultEmployementDetailsShouldNotBeFound("employerAdd.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByEmployerAddContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where employerAdd contains DEFAULT_EMPLOYER_ADD
        defaultEmployementDetailsShouldBeFound("employerAdd.contains=" + DEFAULT_EMPLOYER_ADD);

        // Get all the employementDetailsList where employerAdd contains UPDATED_EMPLOYER_ADD
        defaultEmployementDetailsShouldNotBeFound("employerAdd.contains=" + UPDATED_EMPLOYER_ADD);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByEmployerAddNotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where employerAdd does not contain DEFAULT_EMPLOYER_ADD
        defaultEmployementDetailsShouldNotBeFound("employerAdd.doesNotContain=" + DEFAULT_EMPLOYER_ADD);

        // Get all the employementDetailsList where employerAdd does not contain UPDATED_EMPLOYER_ADD
        defaultEmployementDetailsShouldBeFound("employerAdd.doesNotContain=" + UPDATED_EMPLOYER_ADD);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPrevCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where prevCompanyName equals to DEFAULT_PREV_COMPANY_NAME
        defaultEmployementDetailsShouldBeFound("prevCompanyName.equals=" + DEFAULT_PREV_COMPANY_NAME);

        // Get all the employementDetailsList where prevCompanyName equals to UPDATED_PREV_COMPANY_NAME
        defaultEmployementDetailsShouldNotBeFound("prevCompanyName.equals=" + UPDATED_PREV_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPrevCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where prevCompanyName in DEFAULT_PREV_COMPANY_NAME or UPDATED_PREV_COMPANY_NAME
        defaultEmployementDetailsShouldBeFound("prevCompanyName.in=" + DEFAULT_PREV_COMPANY_NAME + "," + UPDATED_PREV_COMPANY_NAME);

        // Get all the employementDetailsList where prevCompanyName equals to UPDATED_PREV_COMPANY_NAME
        defaultEmployementDetailsShouldNotBeFound("prevCompanyName.in=" + UPDATED_PREV_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPrevCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where prevCompanyName is not null
        defaultEmployementDetailsShouldBeFound("prevCompanyName.specified=true");

        // Get all the employementDetailsList where prevCompanyName is null
        defaultEmployementDetailsShouldNotBeFound("prevCompanyName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPrevCompanyNameContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where prevCompanyName contains DEFAULT_PREV_COMPANY_NAME
        defaultEmployementDetailsShouldBeFound("prevCompanyName.contains=" + DEFAULT_PREV_COMPANY_NAME);

        // Get all the employementDetailsList where prevCompanyName contains UPDATED_PREV_COMPANY_NAME
        defaultEmployementDetailsShouldNotBeFound("prevCompanyName.contains=" + UPDATED_PREV_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPrevCompanyNameNotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where prevCompanyName does not contain DEFAULT_PREV_COMPANY_NAME
        defaultEmployementDetailsShouldNotBeFound("prevCompanyName.doesNotContain=" + DEFAULT_PREV_COMPANY_NAME);

        // Get all the employementDetailsList where prevCompanyName does not contain UPDATED_PREV_COMPANY_NAME
        defaultEmployementDetailsShouldBeFound("prevCompanyName.doesNotContain=" + UPDATED_PREV_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPrevCompanydurationIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where prevCompanyduration equals to DEFAULT_PREV_COMPANYDURATION
        defaultEmployementDetailsShouldBeFound("prevCompanyduration.equals=" + DEFAULT_PREV_COMPANYDURATION);

        // Get all the employementDetailsList where prevCompanyduration equals to UPDATED_PREV_COMPANYDURATION
        defaultEmployementDetailsShouldNotBeFound("prevCompanyduration.equals=" + UPDATED_PREV_COMPANYDURATION);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPrevCompanydurationIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where prevCompanyduration in DEFAULT_PREV_COMPANYDURATION or UPDATED_PREV_COMPANYDURATION
        defaultEmployementDetailsShouldBeFound(
            "prevCompanyduration.in=" + DEFAULT_PREV_COMPANYDURATION + "," + UPDATED_PREV_COMPANYDURATION
        );

        // Get all the employementDetailsList where prevCompanyduration equals to UPDATED_PREV_COMPANYDURATION
        defaultEmployementDetailsShouldNotBeFound("prevCompanyduration.in=" + UPDATED_PREV_COMPANYDURATION);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPrevCompanydurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where prevCompanyduration is not null
        defaultEmployementDetailsShouldBeFound("prevCompanyduration.specified=true");

        // Get all the employementDetailsList where prevCompanyduration is null
        defaultEmployementDetailsShouldNotBeFound("prevCompanyduration.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPrevCompanydurationContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where prevCompanyduration contains DEFAULT_PREV_COMPANYDURATION
        defaultEmployementDetailsShouldBeFound("prevCompanyduration.contains=" + DEFAULT_PREV_COMPANYDURATION);

        // Get all the employementDetailsList where prevCompanyduration contains UPDATED_PREV_COMPANYDURATION
        defaultEmployementDetailsShouldNotBeFound("prevCompanyduration.contains=" + UPDATED_PREV_COMPANYDURATION);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPrevCompanydurationNotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where prevCompanyduration does not contain DEFAULT_PREV_COMPANYDURATION
        defaultEmployementDetailsShouldNotBeFound("prevCompanyduration.doesNotContain=" + DEFAULT_PREV_COMPANYDURATION);

        // Get all the employementDetailsList where prevCompanyduration does not contain UPDATED_PREV_COMPANYDURATION
        defaultEmployementDetailsShouldBeFound("prevCompanyduration.doesNotContain=" + UPDATED_PREV_COMPANYDURATION);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByOrgTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where orgType equals to DEFAULT_ORG_TYPE
        defaultEmployementDetailsShouldBeFound("orgType.equals=" + DEFAULT_ORG_TYPE);

        // Get all the employementDetailsList where orgType equals to UPDATED_ORG_TYPE
        defaultEmployementDetailsShouldNotBeFound("orgType.equals=" + UPDATED_ORG_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByOrgTypeIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where orgType in DEFAULT_ORG_TYPE or UPDATED_ORG_TYPE
        defaultEmployementDetailsShouldBeFound("orgType.in=" + DEFAULT_ORG_TYPE + "," + UPDATED_ORG_TYPE);

        // Get all the employementDetailsList where orgType equals to UPDATED_ORG_TYPE
        defaultEmployementDetailsShouldNotBeFound("orgType.in=" + UPDATED_ORG_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByOrgTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where orgType is not null
        defaultEmployementDetailsShouldBeFound("orgType.specified=true");

        // Get all the employementDetailsList where orgType is null
        defaultEmployementDetailsShouldNotBeFound("orgType.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByConstitutionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where constitutionType equals to DEFAULT_CONSTITUTION_TYPE
        defaultEmployementDetailsShouldBeFound("constitutionType.equals=" + DEFAULT_CONSTITUTION_TYPE);

        // Get all the employementDetailsList where constitutionType equals to UPDATED_CONSTITUTION_TYPE
        defaultEmployementDetailsShouldNotBeFound("constitutionType.equals=" + UPDATED_CONSTITUTION_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByConstitutionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where constitutionType in DEFAULT_CONSTITUTION_TYPE or UPDATED_CONSTITUTION_TYPE
        defaultEmployementDetailsShouldBeFound("constitutionType.in=" + DEFAULT_CONSTITUTION_TYPE + "," + UPDATED_CONSTITUTION_TYPE);

        // Get all the employementDetailsList where constitutionType equals to UPDATED_CONSTITUTION_TYPE
        defaultEmployementDetailsShouldNotBeFound("constitutionType.in=" + UPDATED_CONSTITUTION_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByConstitutionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where constitutionType is not null
        defaultEmployementDetailsShouldBeFound("constitutionType.specified=true");

        // Get all the employementDetailsList where constitutionType is null
        defaultEmployementDetailsShouldNotBeFound("constitutionType.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByIndustryTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where industryType equals to DEFAULT_INDUSTRY_TYPE
        defaultEmployementDetailsShouldBeFound("industryType.equals=" + DEFAULT_INDUSTRY_TYPE);

        // Get all the employementDetailsList where industryType equals to UPDATED_INDUSTRY_TYPE
        defaultEmployementDetailsShouldNotBeFound("industryType.equals=" + UPDATED_INDUSTRY_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByIndustryTypeIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where industryType in DEFAULT_INDUSTRY_TYPE or UPDATED_INDUSTRY_TYPE
        defaultEmployementDetailsShouldBeFound("industryType.in=" + DEFAULT_INDUSTRY_TYPE + "," + UPDATED_INDUSTRY_TYPE);

        // Get all the employementDetailsList where industryType equals to UPDATED_INDUSTRY_TYPE
        defaultEmployementDetailsShouldNotBeFound("industryType.in=" + UPDATED_INDUSTRY_TYPE);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByIndustryTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where industryType is not null
        defaultEmployementDetailsShouldBeFound("industryType.specified=true");

        // Get all the employementDetailsList where industryType is null
        defaultEmployementDetailsShouldNotBeFound("industryType.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByBusinessRegNoIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where businessRegNo equals to DEFAULT_BUSINESS_REG_NO
        defaultEmployementDetailsShouldBeFound("businessRegNo.equals=" + DEFAULT_BUSINESS_REG_NO);

        // Get all the employementDetailsList where businessRegNo equals to UPDATED_BUSINESS_REG_NO
        defaultEmployementDetailsShouldNotBeFound("businessRegNo.equals=" + UPDATED_BUSINESS_REG_NO);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByBusinessRegNoIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where businessRegNo in DEFAULT_BUSINESS_REG_NO or UPDATED_BUSINESS_REG_NO
        defaultEmployementDetailsShouldBeFound("businessRegNo.in=" + DEFAULT_BUSINESS_REG_NO + "," + UPDATED_BUSINESS_REG_NO);

        // Get all the employementDetailsList where businessRegNo equals to UPDATED_BUSINESS_REG_NO
        defaultEmployementDetailsShouldNotBeFound("businessRegNo.in=" + UPDATED_BUSINESS_REG_NO);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByBusinessRegNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where businessRegNo is not null
        defaultEmployementDetailsShouldBeFound("businessRegNo.specified=true");

        // Get all the employementDetailsList where businessRegNo is null
        defaultEmployementDetailsShouldNotBeFound("businessRegNo.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByBusinessRegNoContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where businessRegNo contains DEFAULT_BUSINESS_REG_NO
        defaultEmployementDetailsShouldBeFound("businessRegNo.contains=" + DEFAULT_BUSINESS_REG_NO);

        // Get all the employementDetailsList where businessRegNo contains UPDATED_BUSINESS_REG_NO
        defaultEmployementDetailsShouldNotBeFound("businessRegNo.contains=" + UPDATED_BUSINESS_REG_NO);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByBusinessRegNoNotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where businessRegNo does not contain DEFAULT_BUSINESS_REG_NO
        defaultEmployementDetailsShouldNotBeFound("businessRegNo.doesNotContain=" + DEFAULT_BUSINESS_REG_NO);

        // Get all the employementDetailsList where businessRegNo does not contain UPDATED_BUSINESS_REG_NO
        defaultEmployementDetailsShouldBeFound("businessRegNo.doesNotContain=" + UPDATED_BUSINESS_REG_NO);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByCompOwnerShipIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where compOwnerShip equals to DEFAULT_COMP_OWNER_SHIP
        defaultEmployementDetailsShouldBeFound("compOwnerShip.equals=" + DEFAULT_COMP_OWNER_SHIP);

        // Get all the employementDetailsList where compOwnerShip equals to UPDATED_COMP_OWNER_SHIP
        defaultEmployementDetailsShouldNotBeFound("compOwnerShip.equals=" + UPDATED_COMP_OWNER_SHIP);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByCompOwnerShipIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where compOwnerShip in DEFAULT_COMP_OWNER_SHIP or UPDATED_COMP_OWNER_SHIP
        defaultEmployementDetailsShouldBeFound("compOwnerShip.in=" + DEFAULT_COMP_OWNER_SHIP + "," + UPDATED_COMP_OWNER_SHIP);

        // Get all the employementDetailsList where compOwnerShip equals to UPDATED_COMP_OWNER_SHIP
        defaultEmployementDetailsShouldNotBeFound("compOwnerShip.in=" + UPDATED_COMP_OWNER_SHIP);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByCompOwnerShipIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where compOwnerShip is not null
        defaultEmployementDetailsShouldBeFound("compOwnerShip.specified=true");

        // Get all the employementDetailsList where compOwnerShip is null
        defaultEmployementDetailsShouldNotBeFound("compOwnerShip.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByCompOwnerShipIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where compOwnerShip is greater than or equal to DEFAULT_COMP_OWNER_SHIP
        defaultEmployementDetailsShouldBeFound("compOwnerShip.greaterThanOrEqual=" + DEFAULT_COMP_OWNER_SHIP);

        // Get all the employementDetailsList where compOwnerShip is greater than or equal to UPDATED_COMP_OWNER_SHIP
        defaultEmployementDetailsShouldNotBeFound("compOwnerShip.greaterThanOrEqual=" + UPDATED_COMP_OWNER_SHIP);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByCompOwnerShipIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where compOwnerShip is less than or equal to DEFAULT_COMP_OWNER_SHIP
        defaultEmployementDetailsShouldBeFound("compOwnerShip.lessThanOrEqual=" + DEFAULT_COMP_OWNER_SHIP);

        // Get all the employementDetailsList where compOwnerShip is less than or equal to SMALLER_COMP_OWNER_SHIP
        defaultEmployementDetailsShouldNotBeFound("compOwnerShip.lessThanOrEqual=" + SMALLER_COMP_OWNER_SHIP);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByCompOwnerShipIsLessThanSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where compOwnerShip is less than DEFAULT_COMP_OWNER_SHIP
        defaultEmployementDetailsShouldNotBeFound("compOwnerShip.lessThan=" + DEFAULT_COMP_OWNER_SHIP);

        // Get all the employementDetailsList where compOwnerShip is less than UPDATED_COMP_OWNER_SHIP
        defaultEmployementDetailsShouldBeFound("compOwnerShip.lessThan=" + UPDATED_COMP_OWNER_SHIP);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByCompOwnerShipIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where compOwnerShip is greater than DEFAULT_COMP_OWNER_SHIP
        defaultEmployementDetailsShouldNotBeFound("compOwnerShip.greaterThan=" + DEFAULT_COMP_OWNER_SHIP);

        // Get all the employementDetailsList where compOwnerShip is greater than SMALLER_COMP_OWNER_SHIP
        defaultEmployementDetailsShouldBeFound("compOwnerShip.greaterThan=" + SMALLER_COMP_OWNER_SHIP);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPartnerName1IsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where partnerName1 equals to DEFAULT_PARTNER_NAME_1
        defaultEmployementDetailsShouldBeFound("partnerName1.equals=" + DEFAULT_PARTNER_NAME_1);

        // Get all the employementDetailsList where partnerName1 equals to UPDATED_PARTNER_NAME_1
        defaultEmployementDetailsShouldNotBeFound("partnerName1.equals=" + UPDATED_PARTNER_NAME_1);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPartnerName1IsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where partnerName1 in DEFAULT_PARTNER_NAME_1 or UPDATED_PARTNER_NAME_1
        defaultEmployementDetailsShouldBeFound("partnerName1.in=" + DEFAULT_PARTNER_NAME_1 + "," + UPDATED_PARTNER_NAME_1);

        // Get all the employementDetailsList where partnerName1 equals to UPDATED_PARTNER_NAME_1
        defaultEmployementDetailsShouldNotBeFound("partnerName1.in=" + UPDATED_PARTNER_NAME_1);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPartnerName1IsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where partnerName1 is not null
        defaultEmployementDetailsShouldBeFound("partnerName1.specified=true");

        // Get all the employementDetailsList where partnerName1 is null
        defaultEmployementDetailsShouldNotBeFound("partnerName1.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPartnerName1ContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where partnerName1 contains DEFAULT_PARTNER_NAME_1
        defaultEmployementDetailsShouldBeFound("partnerName1.contains=" + DEFAULT_PARTNER_NAME_1);

        // Get all the employementDetailsList where partnerName1 contains UPDATED_PARTNER_NAME_1
        defaultEmployementDetailsShouldNotBeFound("partnerName1.contains=" + UPDATED_PARTNER_NAME_1);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPartnerName1NotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where partnerName1 does not contain DEFAULT_PARTNER_NAME_1
        defaultEmployementDetailsShouldNotBeFound("partnerName1.doesNotContain=" + DEFAULT_PARTNER_NAME_1);

        // Get all the employementDetailsList where partnerName1 does not contain UPDATED_PARTNER_NAME_1
        defaultEmployementDetailsShouldBeFound("partnerName1.doesNotContain=" + UPDATED_PARTNER_NAME_1);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPartnerName2IsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where partnerName2 equals to DEFAULT_PARTNER_NAME_2
        defaultEmployementDetailsShouldBeFound("partnerName2.equals=" + DEFAULT_PARTNER_NAME_2);

        // Get all the employementDetailsList where partnerName2 equals to UPDATED_PARTNER_NAME_2
        defaultEmployementDetailsShouldNotBeFound("partnerName2.equals=" + UPDATED_PARTNER_NAME_2);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPartnerName2IsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where partnerName2 in DEFAULT_PARTNER_NAME_2 or UPDATED_PARTNER_NAME_2
        defaultEmployementDetailsShouldBeFound("partnerName2.in=" + DEFAULT_PARTNER_NAME_2 + "," + UPDATED_PARTNER_NAME_2);

        // Get all the employementDetailsList where partnerName2 equals to UPDATED_PARTNER_NAME_2
        defaultEmployementDetailsShouldNotBeFound("partnerName2.in=" + UPDATED_PARTNER_NAME_2);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPartnerName2IsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where partnerName2 is not null
        defaultEmployementDetailsShouldBeFound("partnerName2.specified=true");

        // Get all the employementDetailsList where partnerName2 is null
        defaultEmployementDetailsShouldNotBeFound("partnerName2.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPartnerName2ContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where partnerName2 contains DEFAULT_PARTNER_NAME_2
        defaultEmployementDetailsShouldBeFound("partnerName2.contains=" + DEFAULT_PARTNER_NAME_2);

        // Get all the employementDetailsList where partnerName2 contains UPDATED_PARTNER_NAME_2
        defaultEmployementDetailsShouldNotBeFound("partnerName2.contains=" + UPDATED_PARTNER_NAME_2);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPartnerName2NotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where partnerName2 does not contain DEFAULT_PARTNER_NAME_2
        defaultEmployementDetailsShouldNotBeFound("partnerName2.doesNotContain=" + DEFAULT_PARTNER_NAME_2);

        // Get all the employementDetailsList where partnerName2 does not contain UPDATED_PARTNER_NAME_2
        defaultEmployementDetailsShouldBeFound("partnerName2.doesNotContain=" + UPDATED_PARTNER_NAME_2);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPartnerName3IsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where partnerName3 equals to DEFAULT_PARTNER_NAME_3
        defaultEmployementDetailsShouldBeFound("partnerName3.equals=" + DEFAULT_PARTNER_NAME_3);

        // Get all the employementDetailsList where partnerName3 equals to UPDATED_PARTNER_NAME_3
        defaultEmployementDetailsShouldNotBeFound("partnerName3.equals=" + UPDATED_PARTNER_NAME_3);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPartnerName3IsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where partnerName3 in DEFAULT_PARTNER_NAME_3 or UPDATED_PARTNER_NAME_3
        defaultEmployementDetailsShouldBeFound("partnerName3.in=" + DEFAULT_PARTNER_NAME_3 + "," + UPDATED_PARTNER_NAME_3);

        // Get all the employementDetailsList where partnerName3 equals to UPDATED_PARTNER_NAME_3
        defaultEmployementDetailsShouldNotBeFound("partnerName3.in=" + UPDATED_PARTNER_NAME_3);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPartnerName3IsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where partnerName3 is not null
        defaultEmployementDetailsShouldBeFound("partnerName3.specified=true");

        // Get all the employementDetailsList where partnerName3 is null
        defaultEmployementDetailsShouldNotBeFound("partnerName3.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPartnerName3ContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where partnerName3 contains DEFAULT_PARTNER_NAME_3
        defaultEmployementDetailsShouldBeFound("partnerName3.contains=" + DEFAULT_PARTNER_NAME_3);

        // Get all the employementDetailsList where partnerName3 contains UPDATED_PARTNER_NAME_3
        defaultEmployementDetailsShouldNotBeFound("partnerName3.contains=" + UPDATED_PARTNER_NAME_3);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByPartnerName3NotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where partnerName3 does not contain DEFAULT_PARTNER_NAME_3
        defaultEmployementDetailsShouldNotBeFound("partnerName3.doesNotContain=" + DEFAULT_PARTNER_NAME_3);

        // Get all the employementDetailsList where partnerName3 does not contain UPDATED_PARTNER_NAME_3
        defaultEmployementDetailsShouldBeFound("partnerName3.doesNotContain=" + UPDATED_PARTNER_NAME_3);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultEmployementDetailsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the employementDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultEmployementDetailsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultEmployementDetailsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the employementDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultEmployementDetailsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where isDeleted is not null
        defaultEmployementDetailsShouldBeFound("isDeleted.specified=true");

        // Get all the employementDetailsList where isDeleted is null
        defaultEmployementDetailsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultEmployementDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the employementDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEmployementDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultEmployementDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the employementDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEmployementDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where lastModified is not null
        defaultEmployementDetailsShouldBeFound("lastModified.specified=true");

        // Get all the employementDetailsList where lastModified is null
        defaultEmployementDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultEmployementDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employementDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmployementDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultEmployementDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the employementDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmployementDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where lastModifiedBy is not null
        defaultEmployementDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the employementDetailsList where lastModifiedBy is null
        defaultEmployementDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultEmployementDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employementDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultEmployementDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultEmployementDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employementDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultEmployementDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where createdBy equals to DEFAULT_CREATED_BY
        defaultEmployementDetailsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the employementDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultEmployementDetailsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultEmployementDetailsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the employementDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultEmployementDetailsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where createdBy is not null
        defaultEmployementDetailsShouldBeFound("createdBy.specified=true");

        // Get all the employementDetailsList where createdBy is null
        defaultEmployementDetailsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where createdBy contains DEFAULT_CREATED_BY
        defaultEmployementDetailsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the employementDetailsList where createdBy contains UPDATED_CREATED_BY
        defaultEmployementDetailsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultEmployementDetailsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the employementDetailsList where createdBy does not contain UPDATED_CREATED_BY
        defaultEmployementDetailsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where createdOn equals to DEFAULT_CREATED_ON
        defaultEmployementDetailsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the employementDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultEmployementDetailsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultEmployementDetailsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the employementDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultEmployementDetailsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where createdOn is not null
        defaultEmployementDetailsShouldBeFound("createdOn.specified=true");

        // Get all the employementDetailsList where createdOn is null
        defaultEmployementDetailsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultEmployementDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the employementDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultEmployementDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultEmployementDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the employementDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultEmployementDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField1 is not null
        defaultEmployementDetailsShouldBeFound("freeField1.specified=true");

        // Get all the employementDetailsList where freeField1 is null
        defaultEmployementDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultEmployementDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the employementDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultEmployementDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultEmployementDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the employementDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultEmployementDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultEmployementDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the employementDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultEmployementDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultEmployementDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the employementDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultEmployementDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField2 is not null
        defaultEmployementDetailsShouldBeFound("freeField2.specified=true");

        // Get all the employementDetailsList where freeField2 is null
        defaultEmployementDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultEmployementDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the employementDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultEmployementDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultEmployementDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the employementDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultEmployementDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultEmployementDetailsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the employementDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultEmployementDetailsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultEmployementDetailsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the employementDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultEmployementDetailsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField3 is not null
        defaultEmployementDetailsShouldBeFound("freeField3.specified=true");

        // Get all the employementDetailsList where freeField3 is null
        defaultEmployementDetailsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultEmployementDetailsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the employementDetailsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultEmployementDetailsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultEmployementDetailsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the employementDetailsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultEmployementDetailsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultEmployementDetailsShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the employementDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultEmployementDetailsShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultEmployementDetailsShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the employementDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultEmployementDetailsShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField4 is not null
        defaultEmployementDetailsShouldBeFound("freeField4.specified=true");

        // Get all the employementDetailsList where freeField4 is null
        defaultEmployementDetailsShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultEmployementDetailsShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the employementDetailsList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultEmployementDetailsShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultEmployementDetailsShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the employementDetailsList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultEmployementDetailsShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultEmployementDetailsShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the employementDetailsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultEmployementDetailsShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultEmployementDetailsShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the employementDetailsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultEmployementDetailsShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField5 is not null
        defaultEmployementDetailsShouldBeFound("freeField5.specified=true");

        // Get all the employementDetailsList where freeField5 is null
        defaultEmployementDetailsShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultEmployementDetailsShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the employementDetailsList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultEmployementDetailsShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultEmployementDetailsShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the employementDetailsList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultEmployementDetailsShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultEmployementDetailsShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the employementDetailsList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultEmployementDetailsShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultEmployementDetailsShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the employementDetailsList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultEmployementDetailsShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField6 is not null
        defaultEmployementDetailsShouldBeFound("freeField6.specified=true");

        // Get all the employementDetailsList where freeField6 is null
        defaultEmployementDetailsShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultEmployementDetailsShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the employementDetailsList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultEmployementDetailsShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        // Get all the employementDetailsList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultEmployementDetailsShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the employementDetailsList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultEmployementDetailsShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllEmployementDetailsByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            employementDetailsRepository.saveAndFlush(employementDetails);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        employementDetails.setMember(member);
        employementDetailsRepository.saveAndFlush(employementDetails);
        Long memberId = member.getId();

        // Get all the employementDetailsList where member equals to memberId
        defaultEmployementDetailsShouldBeFound("memberId.equals=" + memberId);

        // Get all the employementDetailsList where member equals to (memberId + 1)
        defaultEmployementDetailsShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployementDetailsShouldBeFound(String filter) throws Exception {
        restEmployementDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employementDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].employerName").value(hasItem(DEFAULT_EMPLOYER_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].employerAdd").value(hasItem(DEFAULT_EMPLOYER_ADD)))
            .andExpect(jsonPath("$.[*].prevCompanyName").value(hasItem(DEFAULT_PREV_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].prevCompanyduration").value(hasItem(DEFAULT_PREV_COMPANYDURATION)))
            .andExpect(jsonPath("$.[*].orgType").value(hasItem(DEFAULT_ORG_TYPE.toString())))
            .andExpect(jsonPath("$.[*].constitutionType").value(hasItem(DEFAULT_CONSTITUTION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].industryType").value(hasItem(DEFAULT_INDUSTRY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].businessRegNo").value(hasItem(DEFAULT_BUSINESS_REG_NO)))
            .andExpect(jsonPath("$.[*].compOwnerShip").value(hasItem(DEFAULT_COMP_OWNER_SHIP.doubleValue())))
            .andExpect(jsonPath("$.[*].partnerName1").value(hasItem(DEFAULT_PARTNER_NAME_1)))
            .andExpect(jsonPath("$.[*].partnerName2").value(hasItem(DEFAULT_PARTNER_NAME_2)))
            .andExpect(jsonPath("$.[*].partnerName3").value(hasItem(DEFAULT_PARTNER_NAME_3)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));

        // Check, that the count call also returns 1
        restEmployementDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployementDetailsShouldNotBeFound(String filter) throws Exception {
        restEmployementDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployementDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployementDetails() throws Exception {
        // Get the employementDetails
        restEmployementDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployementDetails() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        int databaseSizeBeforeUpdate = employementDetailsRepository.findAll().size();

        // Update the employementDetails
        EmployementDetails updatedEmployementDetails = employementDetailsRepository.findById(employementDetails.getId()).get();
        // Disconnect from session so that the updates on updatedEmployementDetails are not directly saved in db
        em.detach(updatedEmployementDetails);
        updatedEmployementDetails
            .type(UPDATED_TYPE)
            .employerName(UPDATED_EMPLOYER_NAME)
            .status(UPDATED_STATUS)
            .designation(UPDATED_DESIGNATION)
            .duration(UPDATED_DURATION)
            .employerAdd(UPDATED_EMPLOYER_ADD)
            .prevCompanyName(UPDATED_PREV_COMPANY_NAME)
            .prevCompanyduration(UPDATED_PREV_COMPANYDURATION)
            .orgType(UPDATED_ORG_TYPE)
            .constitutionType(UPDATED_CONSTITUTION_TYPE)
            .industryType(UPDATED_INDUSTRY_TYPE)
            .businessRegNo(UPDATED_BUSINESS_REG_NO)
            .compOwnerShip(UPDATED_COMP_OWNER_SHIP)
            .partnerName1(UPDATED_PARTNER_NAME_1)
            .partnerName2(UPDATED_PARTNER_NAME_2)
            .partnerName3(UPDATED_PARTNER_NAME_3)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        EmployementDetailsDTO employementDetailsDTO = employementDetailsMapper.toDto(updatedEmployementDetails);

        restEmployementDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employementDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employementDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmployementDetails in the database
        List<EmployementDetails> employementDetailsList = employementDetailsRepository.findAll();
        assertThat(employementDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmployementDetails testEmployementDetails = employementDetailsList.get(employementDetailsList.size() - 1);
        assertThat(testEmployementDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmployementDetails.getEmployerName()).isEqualTo(UPDATED_EMPLOYER_NAME);
        assertThat(testEmployementDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployementDetails.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testEmployementDetails.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testEmployementDetails.getEmployerAdd()).isEqualTo(UPDATED_EMPLOYER_ADD);
        assertThat(testEmployementDetails.getPrevCompanyName()).isEqualTo(UPDATED_PREV_COMPANY_NAME);
        assertThat(testEmployementDetails.getPrevCompanyduration()).isEqualTo(UPDATED_PREV_COMPANYDURATION);
        assertThat(testEmployementDetails.getOrgType()).isEqualTo(UPDATED_ORG_TYPE);
        assertThat(testEmployementDetails.getConstitutionType()).isEqualTo(UPDATED_CONSTITUTION_TYPE);
        assertThat(testEmployementDetails.getIndustryType()).isEqualTo(UPDATED_INDUSTRY_TYPE);
        assertThat(testEmployementDetails.getBusinessRegNo()).isEqualTo(UPDATED_BUSINESS_REG_NO);
        assertThat(testEmployementDetails.getCompOwnerShip()).isEqualTo(UPDATED_COMP_OWNER_SHIP);
        assertThat(testEmployementDetails.getPartnerName1()).isEqualTo(UPDATED_PARTNER_NAME_1);
        assertThat(testEmployementDetails.getPartnerName2()).isEqualTo(UPDATED_PARTNER_NAME_2);
        assertThat(testEmployementDetails.getPartnerName3()).isEqualTo(UPDATED_PARTNER_NAME_3);
        assertThat(testEmployementDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEmployementDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmployementDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEmployementDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployementDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmployementDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testEmployementDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testEmployementDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testEmployementDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testEmployementDetails.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testEmployementDetails.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingEmployementDetails() throws Exception {
        int databaseSizeBeforeUpdate = employementDetailsRepository.findAll().size();
        employementDetails.setId(count.incrementAndGet());

        // Create the EmployementDetails
        EmployementDetailsDTO employementDetailsDTO = employementDetailsMapper.toDto(employementDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployementDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employementDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employementDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployementDetails in the database
        List<EmployementDetails> employementDetailsList = employementDetailsRepository.findAll();
        assertThat(employementDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployementDetails() throws Exception {
        int databaseSizeBeforeUpdate = employementDetailsRepository.findAll().size();
        employementDetails.setId(count.incrementAndGet());

        // Create the EmployementDetails
        EmployementDetailsDTO employementDetailsDTO = employementDetailsMapper.toDto(employementDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployementDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employementDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployementDetails in the database
        List<EmployementDetails> employementDetailsList = employementDetailsRepository.findAll();
        assertThat(employementDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployementDetails() throws Exception {
        int databaseSizeBeforeUpdate = employementDetailsRepository.findAll().size();
        employementDetails.setId(count.incrementAndGet());

        // Create the EmployementDetails
        EmployementDetailsDTO employementDetailsDTO = employementDetailsMapper.toDto(employementDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployementDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employementDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployementDetails in the database
        List<EmployementDetails> employementDetailsList = employementDetailsRepository.findAll();
        assertThat(employementDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployementDetailsWithPatch() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        int databaseSizeBeforeUpdate = employementDetailsRepository.findAll().size();

        // Update the employementDetails using partial update
        EmployementDetails partialUpdatedEmployementDetails = new EmployementDetails();
        partialUpdatedEmployementDetails.setId(employementDetails.getId());

        partialUpdatedEmployementDetails
            .type(UPDATED_TYPE)
            .employerName(UPDATED_EMPLOYER_NAME)
            .designation(UPDATED_DESIGNATION)
            .employerAdd(UPDATED_EMPLOYER_ADD)
            .constitutionType(UPDATED_CONSTITUTION_TYPE)
            .compOwnerShip(UPDATED_COMP_OWNER_SHIP)
            .partnerName1(UPDATED_PARTNER_NAME_1)
            .partnerName2(UPDATED_PARTNER_NAME_2)
            .partnerName3(UPDATED_PARTNER_NAME_3)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField6(UPDATED_FREE_FIELD_6);

        restEmployementDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployementDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployementDetails))
            )
            .andExpect(status().isOk());

        // Validate the EmployementDetails in the database
        List<EmployementDetails> employementDetailsList = employementDetailsRepository.findAll();
        assertThat(employementDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmployementDetails testEmployementDetails = employementDetailsList.get(employementDetailsList.size() - 1);
        assertThat(testEmployementDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmployementDetails.getEmployerName()).isEqualTo(UPDATED_EMPLOYER_NAME);
        assertThat(testEmployementDetails.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testEmployementDetails.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testEmployementDetails.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testEmployementDetails.getEmployerAdd()).isEqualTo(UPDATED_EMPLOYER_ADD);
        assertThat(testEmployementDetails.getPrevCompanyName()).isEqualTo(DEFAULT_PREV_COMPANY_NAME);
        assertThat(testEmployementDetails.getPrevCompanyduration()).isEqualTo(DEFAULT_PREV_COMPANYDURATION);
        assertThat(testEmployementDetails.getOrgType()).isEqualTo(DEFAULT_ORG_TYPE);
        assertThat(testEmployementDetails.getConstitutionType()).isEqualTo(UPDATED_CONSTITUTION_TYPE);
        assertThat(testEmployementDetails.getIndustryType()).isEqualTo(DEFAULT_INDUSTRY_TYPE);
        assertThat(testEmployementDetails.getBusinessRegNo()).isEqualTo(DEFAULT_BUSINESS_REG_NO);
        assertThat(testEmployementDetails.getCompOwnerShip()).isEqualTo(UPDATED_COMP_OWNER_SHIP);
        assertThat(testEmployementDetails.getPartnerName1()).isEqualTo(UPDATED_PARTNER_NAME_1);
        assertThat(testEmployementDetails.getPartnerName2()).isEqualTo(UPDATED_PARTNER_NAME_2);
        assertThat(testEmployementDetails.getPartnerName3()).isEqualTo(UPDATED_PARTNER_NAME_3);
        assertThat(testEmployementDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEmployementDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmployementDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testEmployementDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployementDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmployementDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testEmployementDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testEmployementDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testEmployementDetails.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testEmployementDetails.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testEmployementDetails.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateEmployementDetailsWithPatch() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        int databaseSizeBeforeUpdate = employementDetailsRepository.findAll().size();

        // Update the employementDetails using partial update
        EmployementDetails partialUpdatedEmployementDetails = new EmployementDetails();
        partialUpdatedEmployementDetails.setId(employementDetails.getId());

        partialUpdatedEmployementDetails
            .type(UPDATED_TYPE)
            .employerName(UPDATED_EMPLOYER_NAME)
            .status(UPDATED_STATUS)
            .designation(UPDATED_DESIGNATION)
            .duration(UPDATED_DURATION)
            .employerAdd(UPDATED_EMPLOYER_ADD)
            .prevCompanyName(UPDATED_PREV_COMPANY_NAME)
            .prevCompanyduration(UPDATED_PREV_COMPANYDURATION)
            .orgType(UPDATED_ORG_TYPE)
            .constitutionType(UPDATED_CONSTITUTION_TYPE)
            .industryType(UPDATED_INDUSTRY_TYPE)
            .businessRegNo(UPDATED_BUSINESS_REG_NO)
            .compOwnerShip(UPDATED_COMP_OWNER_SHIP)
            .partnerName1(UPDATED_PARTNER_NAME_1)
            .partnerName2(UPDATED_PARTNER_NAME_2)
            .partnerName3(UPDATED_PARTNER_NAME_3)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restEmployementDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployementDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployementDetails))
            )
            .andExpect(status().isOk());

        // Validate the EmployementDetails in the database
        List<EmployementDetails> employementDetailsList = employementDetailsRepository.findAll();
        assertThat(employementDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmployementDetails testEmployementDetails = employementDetailsList.get(employementDetailsList.size() - 1);
        assertThat(testEmployementDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmployementDetails.getEmployerName()).isEqualTo(UPDATED_EMPLOYER_NAME);
        assertThat(testEmployementDetails.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testEmployementDetails.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testEmployementDetails.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testEmployementDetails.getEmployerAdd()).isEqualTo(UPDATED_EMPLOYER_ADD);
        assertThat(testEmployementDetails.getPrevCompanyName()).isEqualTo(UPDATED_PREV_COMPANY_NAME);
        assertThat(testEmployementDetails.getPrevCompanyduration()).isEqualTo(UPDATED_PREV_COMPANYDURATION);
        assertThat(testEmployementDetails.getOrgType()).isEqualTo(UPDATED_ORG_TYPE);
        assertThat(testEmployementDetails.getConstitutionType()).isEqualTo(UPDATED_CONSTITUTION_TYPE);
        assertThat(testEmployementDetails.getIndustryType()).isEqualTo(UPDATED_INDUSTRY_TYPE);
        assertThat(testEmployementDetails.getBusinessRegNo()).isEqualTo(UPDATED_BUSINESS_REG_NO);
        assertThat(testEmployementDetails.getCompOwnerShip()).isEqualTo(UPDATED_COMP_OWNER_SHIP);
        assertThat(testEmployementDetails.getPartnerName1()).isEqualTo(UPDATED_PARTNER_NAME_1);
        assertThat(testEmployementDetails.getPartnerName2()).isEqualTo(UPDATED_PARTNER_NAME_2);
        assertThat(testEmployementDetails.getPartnerName3()).isEqualTo(UPDATED_PARTNER_NAME_3);
        assertThat(testEmployementDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEmployementDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmployementDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEmployementDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployementDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmployementDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testEmployementDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testEmployementDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testEmployementDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testEmployementDetails.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testEmployementDetails.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingEmployementDetails() throws Exception {
        int databaseSizeBeforeUpdate = employementDetailsRepository.findAll().size();
        employementDetails.setId(count.incrementAndGet());

        // Create the EmployementDetails
        EmployementDetailsDTO employementDetailsDTO = employementDetailsMapper.toDto(employementDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployementDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employementDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employementDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployementDetails in the database
        List<EmployementDetails> employementDetailsList = employementDetailsRepository.findAll();
        assertThat(employementDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployementDetails() throws Exception {
        int databaseSizeBeforeUpdate = employementDetailsRepository.findAll().size();
        employementDetails.setId(count.incrementAndGet());

        // Create the EmployementDetails
        EmployementDetailsDTO employementDetailsDTO = employementDetailsMapper.toDto(employementDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployementDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employementDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployementDetails in the database
        List<EmployementDetails> employementDetailsList = employementDetailsRepository.findAll();
        assertThat(employementDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployementDetails() throws Exception {
        int databaseSizeBeforeUpdate = employementDetailsRepository.findAll().size();
        employementDetails.setId(count.incrementAndGet());

        // Create the EmployementDetails
        EmployementDetailsDTO employementDetailsDTO = employementDetailsMapper.toDto(employementDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployementDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employementDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployementDetails in the database
        List<EmployementDetails> employementDetailsList = employementDetailsRepository.findAll();
        assertThat(employementDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployementDetails() throws Exception {
        // Initialize the database
        employementDetailsRepository.saveAndFlush(employementDetails);

        int databaseSizeBeforeDelete = employementDetailsRepository.findAll().size();

        // Delete the employementDetails
        restEmployementDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, employementDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployementDetails> employementDetailsList = employementDetailsRepository.findAll();
        assertThat(employementDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
