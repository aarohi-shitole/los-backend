package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.Member;
import com.techvg.los.domain.MemberExistingfacility;
import com.techvg.los.domain.enumeration.CreditRating;
import com.techvg.los.domain.enumeration.FacilityStatus;
import com.techvg.los.repository.MemberExistingfacilityRepository;
import com.techvg.los.service.criteria.MemberExistingfacilityCriteria;
import com.techvg.los.service.dto.MemberExistingfacilityDTO;
import com.techvg.los.service.mapper.MemberExistingfacilityMapper;
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
 * Integration tests for the {@link MemberExistingfacilityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberExistingfacilityResourceIT {

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;
    private static final Integer SMALLER_YEAR = 1 - 1;

    private static final String DEFAULT_FACILITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FACILITY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FACILITATED_FROM = "AAAAAAAAAA";
    private static final String UPDATED_FACILITATED_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_NATURE = "AAAAAAAAAA";
    private static final String UPDATED_NATURE = "BBBBBBBBBB";

    private static final Double DEFAULT_AMT_IN_LAC = 1D;
    private static final Double UPDATED_AMT_IN_LAC = 2D;
    private static final Double SMALLER_AMT_IN_LAC = 1D - 1D;

    private static final String DEFAULT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE = "BBBBBBBBBB";

    private static final Instant DEFAULT_SECTION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SECTION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_OUTSTANDING_IN_LAC = 1D;
    private static final Double UPDATED_OUTSTANDING_IN_LAC = 2D;
    private static final Double SMALLER_OUTSTANDING_IN_LAC = 1D - 1D;

    private static final FacilityStatus DEFAULT_STATUS = FacilityStatus.REGULAR;
    private static final FacilityStatus UPDATED_STATUS = FacilityStatus.OVERDUE;

    private static final CreditRating DEFAULT_RATING = CreditRating.OUTSTANDING;
    private static final CreditRating UPDATED_RATING = CreditRating.GOOD;

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

    private static final String ENTITY_API_URL = "/api/member-existingfacilities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberExistingfacilityRepository memberExistingfacilityRepository;

    @Autowired
    private MemberExistingfacilityMapper memberExistingfacilityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberExistingfacilityMockMvc;

    private MemberExistingfacility memberExistingfacility;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberExistingfacility createEntity(EntityManager em) {
        MemberExistingfacility memberExistingfacility = new MemberExistingfacility()
            .year(DEFAULT_YEAR)
            .facilityName(DEFAULT_FACILITY_NAME)
            .facilitatedFrom(DEFAULT_FACILITATED_FROM)
            .nature(DEFAULT_NATURE)
            .amtInLac(DEFAULT_AMT_IN_LAC)
            .purpose(DEFAULT_PURPOSE)
            .sectionDate(DEFAULT_SECTION_DATE)
            .outstandingInLac(DEFAULT_OUTSTANDING_IN_LAC)
            .status(DEFAULT_STATUS)
            .rating(DEFAULT_RATING)
            .isDeleted(DEFAULT_IS_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5);
        //            .freeField6(DEFAULT_FREE_FIELD_6);
        return memberExistingfacility;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberExistingfacility createUpdatedEntity(EntityManager em) {
        MemberExistingfacility memberExistingfacility = new MemberExistingfacility()
            .year(UPDATED_YEAR)
            .facilityName(UPDATED_FACILITY_NAME)
            .facilitatedFrom(UPDATED_FACILITATED_FROM)
            .nature(UPDATED_NATURE)
            .amtInLac(UPDATED_AMT_IN_LAC)
            .purpose(UPDATED_PURPOSE)
            .sectionDate(UPDATED_SECTION_DATE)
            .outstandingInLac(UPDATED_OUTSTANDING_IN_LAC)
            .status(UPDATED_STATUS)
            .rating(UPDATED_RATING)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);
        //            .freeField6(UPDATED_FREE_FIELD_6);
        return memberExistingfacility;
    }

    @BeforeEach
    public void initTest() {
        memberExistingfacility = createEntity(em);
    }

    @Test
    @Transactional
    void createMemberExistingfacility() throws Exception {
        int databaseSizeBeforeCreate = memberExistingfacilityRepository.findAll().size();
        // Create the MemberExistingfacility
        MemberExistingfacilityDTO memberExistingfacilityDTO = memberExistingfacilityMapper.toDto(memberExistingfacility);
        restMemberExistingfacilityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberExistingfacilityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MemberExistingfacility in the database
        List<MemberExistingfacility> memberExistingfacilityList = memberExistingfacilityRepository.findAll();
        assertThat(memberExistingfacilityList).hasSize(databaseSizeBeforeCreate + 1);
        MemberExistingfacility testMemberExistingfacility = memberExistingfacilityList.get(memberExistingfacilityList.size() - 1);
        assertThat(testMemberExistingfacility.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testMemberExistingfacility.getFacilityName()).isEqualTo(DEFAULT_FACILITY_NAME);
        assertThat(testMemberExistingfacility.getFacilitatedFrom()).isEqualTo(DEFAULT_FACILITATED_FROM);
        assertThat(testMemberExistingfacility.getNature()).isEqualTo(DEFAULT_NATURE);
        assertThat(testMemberExistingfacility.getAmtInLac()).isEqualTo(DEFAULT_AMT_IN_LAC);
        assertThat(testMemberExistingfacility.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testMemberExistingfacility.getSectionDate()).isEqualTo(DEFAULT_SECTION_DATE);
        assertThat(testMemberExistingfacility.getOutstandingInLac()).isEqualTo(DEFAULT_OUTSTANDING_IN_LAC);
        assertThat(testMemberExistingfacility.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMemberExistingfacility.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testMemberExistingfacility.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMemberExistingfacility.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMemberExistingfacility.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMemberExistingfacility.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMemberExistingfacility.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testMemberExistingfacility.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testMemberExistingfacility.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testMemberExistingfacility.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testMemberExistingfacility.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testMemberExistingfacility.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        //       assertThat(testMemberExistingfacility.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createMemberExistingfacilityWithExistingId() throws Exception {
        // Create the MemberExistingfacility with an existing ID
        memberExistingfacility.setId(1L);
        MemberExistingfacilityDTO memberExistingfacilityDTO = memberExistingfacilityMapper.toDto(memberExistingfacility);

        int databaseSizeBeforeCreate = memberExistingfacilityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberExistingfacilityMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberExistingfacilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberExistingfacility in the database
        List<MemberExistingfacility> memberExistingfacilityList = memberExistingfacilityRepository.findAll();
        assertThat(memberExistingfacilityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilities() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList
        restMemberExistingfacilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberExistingfacility.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].facilityName").value(hasItem(DEFAULT_FACILITY_NAME)))
            .andExpect(jsonPath("$.[*].facilitatedFrom").value(hasItem(DEFAULT_FACILITATED_FROM)))
            .andExpect(jsonPath("$.[*].nature").value(hasItem(DEFAULT_NATURE)))
            .andExpect(jsonPath("$.[*].amtInLac").value(hasItem(DEFAULT_AMT_IN_LAC.doubleValue())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].sectionDate").value(hasItem(DEFAULT_SECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].outstandingInLac").value(hasItem(DEFAULT_OUTSTANDING_IN_LAC.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.toString())))
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
    void getMemberExistingfacility() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get the memberExistingfacility
        restMemberExistingfacilityMockMvc
            .perform(get(ENTITY_API_URL_ID, memberExistingfacility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(memberExistingfacility.getId().intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.facilityName").value(DEFAULT_FACILITY_NAME))
            .andExpect(jsonPath("$.facilitatedFrom").value(DEFAULT_FACILITATED_FROM))
            .andExpect(jsonPath("$.nature").value(DEFAULT_NATURE))
            .andExpect(jsonPath("$.amtInLac").value(DEFAULT_AMT_IN_LAC.doubleValue()))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE))
            .andExpect(jsonPath("$.sectionDate").value(DEFAULT_SECTION_DATE.toString()))
            .andExpect(jsonPath("$.outstandingInLac").value(DEFAULT_OUTSTANDING_IN_LAC.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.toString()))
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
    void getMemberExistingfacilitiesByIdFiltering() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        Long id = memberExistingfacility.getId();

        defaultMemberExistingfacilityShouldBeFound("id.equals=" + id);
        defaultMemberExistingfacilityShouldNotBeFound("id.notEquals=" + id);

        defaultMemberExistingfacilityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMemberExistingfacilityShouldNotBeFound("id.greaterThan=" + id);

        defaultMemberExistingfacilityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMemberExistingfacilityShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where year equals to DEFAULT_YEAR
        defaultMemberExistingfacilityShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the memberExistingfacilityList where year equals to UPDATED_YEAR
        defaultMemberExistingfacilityShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByYearIsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultMemberExistingfacilityShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the memberExistingfacilityList where year equals to UPDATED_YEAR
        defaultMemberExistingfacilityShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where year is not null
        defaultMemberExistingfacilityShouldBeFound("year.specified=true");

        // Get all the memberExistingfacilityList where year is null
        defaultMemberExistingfacilityShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where year is greater than or equal to DEFAULT_YEAR
        defaultMemberExistingfacilityShouldBeFound("year.greaterThanOrEqual=" + DEFAULT_YEAR);

        // Get all the memberExistingfacilityList where year is greater than or equal to UPDATED_YEAR
        defaultMemberExistingfacilityShouldNotBeFound("year.greaterThanOrEqual=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where year is less than or equal to DEFAULT_YEAR
        defaultMemberExistingfacilityShouldBeFound("year.lessThanOrEqual=" + DEFAULT_YEAR);

        // Get all the memberExistingfacilityList where year is less than or equal to SMALLER_YEAR
        defaultMemberExistingfacilityShouldNotBeFound("year.lessThanOrEqual=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByYearIsLessThanSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where year is less than DEFAULT_YEAR
        defaultMemberExistingfacilityShouldNotBeFound("year.lessThan=" + DEFAULT_YEAR);

        // Get all the memberExistingfacilityList where year is less than UPDATED_YEAR
        defaultMemberExistingfacilityShouldBeFound("year.lessThan=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where year is greater than DEFAULT_YEAR
        defaultMemberExistingfacilityShouldNotBeFound("year.greaterThan=" + DEFAULT_YEAR);

        // Get all the memberExistingfacilityList where year is greater than SMALLER_YEAR
        defaultMemberExistingfacilityShouldBeFound("year.greaterThan=" + SMALLER_YEAR);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFacilityNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where facilityName equals to DEFAULT_FACILITY_NAME
        defaultMemberExistingfacilityShouldBeFound("facilityName.equals=" + DEFAULT_FACILITY_NAME);

        // Get all the memberExistingfacilityList where facilityName equals to UPDATED_FACILITY_NAME
        defaultMemberExistingfacilityShouldNotBeFound("facilityName.equals=" + UPDATED_FACILITY_NAME);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFacilityNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where facilityName in DEFAULT_FACILITY_NAME or UPDATED_FACILITY_NAME
        defaultMemberExistingfacilityShouldBeFound("facilityName.in=" + DEFAULT_FACILITY_NAME + "," + UPDATED_FACILITY_NAME);

        // Get all the memberExistingfacilityList where facilityName equals to UPDATED_FACILITY_NAME
        defaultMemberExistingfacilityShouldNotBeFound("facilityName.in=" + UPDATED_FACILITY_NAME);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFacilityNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where facilityName is not null
        defaultMemberExistingfacilityShouldBeFound("facilityName.specified=true");

        // Get all the memberExistingfacilityList where facilityName is null
        defaultMemberExistingfacilityShouldNotBeFound("facilityName.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFacilityNameContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where facilityName contains DEFAULT_FACILITY_NAME
        defaultMemberExistingfacilityShouldBeFound("facilityName.contains=" + DEFAULT_FACILITY_NAME);

        // Get all the memberExistingfacilityList where facilityName contains UPDATED_FACILITY_NAME
        defaultMemberExistingfacilityShouldNotBeFound("facilityName.contains=" + UPDATED_FACILITY_NAME);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFacilityNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where facilityName does not contain DEFAULT_FACILITY_NAME
        defaultMemberExistingfacilityShouldNotBeFound("facilityName.doesNotContain=" + DEFAULT_FACILITY_NAME);

        // Get all the memberExistingfacilityList where facilityName does not contain UPDATED_FACILITY_NAME
        defaultMemberExistingfacilityShouldBeFound("facilityName.doesNotContain=" + UPDATED_FACILITY_NAME);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFacilitatedFromIsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where facilitatedFrom equals to DEFAULT_FACILITATED_FROM
        defaultMemberExistingfacilityShouldBeFound("facilitatedFrom.equals=" + DEFAULT_FACILITATED_FROM);

        // Get all the memberExistingfacilityList where facilitatedFrom equals to UPDATED_FACILITATED_FROM
        defaultMemberExistingfacilityShouldNotBeFound("facilitatedFrom.equals=" + UPDATED_FACILITATED_FROM);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFacilitatedFromIsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where facilitatedFrom in DEFAULT_FACILITATED_FROM or UPDATED_FACILITATED_FROM
        defaultMemberExistingfacilityShouldBeFound("facilitatedFrom.in=" + DEFAULT_FACILITATED_FROM + "," + UPDATED_FACILITATED_FROM);

        // Get all the memberExistingfacilityList where facilitatedFrom equals to UPDATED_FACILITATED_FROM
        defaultMemberExistingfacilityShouldNotBeFound("facilitatedFrom.in=" + UPDATED_FACILITATED_FROM);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFacilitatedFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where facilitatedFrom is not null
        defaultMemberExistingfacilityShouldBeFound("facilitatedFrom.specified=true");

        // Get all the memberExistingfacilityList where facilitatedFrom is null
        defaultMemberExistingfacilityShouldNotBeFound("facilitatedFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFacilitatedFromContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where facilitatedFrom contains DEFAULT_FACILITATED_FROM
        defaultMemberExistingfacilityShouldBeFound("facilitatedFrom.contains=" + DEFAULT_FACILITATED_FROM);

        // Get all the memberExistingfacilityList where facilitatedFrom contains UPDATED_FACILITATED_FROM
        defaultMemberExistingfacilityShouldNotBeFound("facilitatedFrom.contains=" + UPDATED_FACILITATED_FROM);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFacilitatedFromNotContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where facilitatedFrom does not contain DEFAULT_FACILITATED_FROM
        defaultMemberExistingfacilityShouldNotBeFound("facilitatedFrom.doesNotContain=" + DEFAULT_FACILITATED_FROM);

        // Get all the memberExistingfacilityList where facilitatedFrom does not contain UPDATED_FACILITATED_FROM
        defaultMemberExistingfacilityShouldBeFound("facilitatedFrom.doesNotContain=" + UPDATED_FACILITATED_FROM);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByNatureIsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where nature equals to DEFAULT_NATURE
        defaultMemberExistingfacilityShouldBeFound("nature.equals=" + DEFAULT_NATURE);

        // Get all the memberExistingfacilityList where nature equals to UPDATED_NATURE
        defaultMemberExistingfacilityShouldNotBeFound("nature.equals=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByNatureIsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where nature in DEFAULT_NATURE or UPDATED_NATURE
        defaultMemberExistingfacilityShouldBeFound("nature.in=" + DEFAULT_NATURE + "," + UPDATED_NATURE);

        // Get all the memberExistingfacilityList where nature equals to UPDATED_NATURE
        defaultMemberExistingfacilityShouldNotBeFound("nature.in=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByNatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where nature is not null
        defaultMemberExistingfacilityShouldBeFound("nature.specified=true");

        // Get all the memberExistingfacilityList where nature is null
        defaultMemberExistingfacilityShouldNotBeFound("nature.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByNatureContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where nature contains DEFAULT_NATURE
        defaultMemberExistingfacilityShouldBeFound("nature.contains=" + DEFAULT_NATURE);

        // Get all the memberExistingfacilityList where nature contains UPDATED_NATURE
        defaultMemberExistingfacilityShouldNotBeFound("nature.contains=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByNatureNotContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where nature does not contain DEFAULT_NATURE
        defaultMemberExistingfacilityShouldNotBeFound("nature.doesNotContain=" + DEFAULT_NATURE);

        // Get all the memberExistingfacilityList where nature does not contain UPDATED_NATURE
        defaultMemberExistingfacilityShouldBeFound("nature.doesNotContain=" + UPDATED_NATURE);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByAmtInLacIsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where amtInLac equals to DEFAULT_AMT_IN_LAC
        defaultMemberExistingfacilityShouldBeFound("amtInLac.equals=" + DEFAULT_AMT_IN_LAC);

        // Get all the memberExistingfacilityList where amtInLac equals to UPDATED_AMT_IN_LAC
        defaultMemberExistingfacilityShouldNotBeFound("amtInLac.equals=" + UPDATED_AMT_IN_LAC);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByAmtInLacIsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where amtInLac in DEFAULT_AMT_IN_LAC or UPDATED_AMT_IN_LAC
        defaultMemberExistingfacilityShouldBeFound("amtInLac.in=" + DEFAULT_AMT_IN_LAC + "," + UPDATED_AMT_IN_LAC);

        // Get all the memberExistingfacilityList where amtInLac equals to UPDATED_AMT_IN_LAC
        defaultMemberExistingfacilityShouldNotBeFound("amtInLac.in=" + UPDATED_AMT_IN_LAC);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByAmtInLacIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where amtInLac is not null
        defaultMemberExistingfacilityShouldBeFound("amtInLac.specified=true");

        // Get all the memberExistingfacilityList where amtInLac is null
        defaultMemberExistingfacilityShouldNotBeFound("amtInLac.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByAmtInLacIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where amtInLac is greater than or equal to DEFAULT_AMT_IN_LAC
        defaultMemberExistingfacilityShouldBeFound("amtInLac.greaterThanOrEqual=" + DEFAULT_AMT_IN_LAC);

        // Get all the memberExistingfacilityList where amtInLac is greater than or equal to UPDATED_AMT_IN_LAC
        defaultMemberExistingfacilityShouldNotBeFound("amtInLac.greaterThanOrEqual=" + UPDATED_AMT_IN_LAC);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByAmtInLacIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where amtInLac is less than or equal to DEFAULT_AMT_IN_LAC
        defaultMemberExistingfacilityShouldBeFound("amtInLac.lessThanOrEqual=" + DEFAULT_AMT_IN_LAC);

        // Get all the memberExistingfacilityList where amtInLac is less than or equal to SMALLER_AMT_IN_LAC
        defaultMemberExistingfacilityShouldNotBeFound("amtInLac.lessThanOrEqual=" + SMALLER_AMT_IN_LAC);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByAmtInLacIsLessThanSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where amtInLac is less than DEFAULT_AMT_IN_LAC
        defaultMemberExistingfacilityShouldNotBeFound("amtInLac.lessThan=" + DEFAULT_AMT_IN_LAC);

        // Get all the memberExistingfacilityList where amtInLac is less than UPDATED_AMT_IN_LAC
        defaultMemberExistingfacilityShouldBeFound("amtInLac.lessThan=" + UPDATED_AMT_IN_LAC);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByAmtInLacIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where amtInLac is greater than DEFAULT_AMT_IN_LAC
        defaultMemberExistingfacilityShouldNotBeFound("amtInLac.greaterThan=" + DEFAULT_AMT_IN_LAC);

        // Get all the memberExistingfacilityList where amtInLac is greater than SMALLER_AMT_IN_LAC
        defaultMemberExistingfacilityShouldBeFound("amtInLac.greaterThan=" + SMALLER_AMT_IN_LAC);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByPurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where purpose equals to DEFAULT_PURPOSE
        defaultMemberExistingfacilityShouldBeFound("purpose.equals=" + DEFAULT_PURPOSE);

        // Get all the memberExistingfacilityList where purpose equals to UPDATED_PURPOSE
        defaultMemberExistingfacilityShouldNotBeFound("purpose.equals=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByPurposeIsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where purpose in DEFAULT_PURPOSE or UPDATED_PURPOSE
        defaultMemberExistingfacilityShouldBeFound("purpose.in=" + DEFAULT_PURPOSE + "," + UPDATED_PURPOSE);

        // Get all the memberExistingfacilityList where purpose equals to UPDATED_PURPOSE
        defaultMemberExistingfacilityShouldNotBeFound("purpose.in=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByPurposeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where purpose is not null
        defaultMemberExistingfacilityShouldBeFound("purpose.specified=true");

        // Get all the memberExistingfacilityList where purpose is null
        defaultMemberExistingfacilityShouldNotBeFound("purpose.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByPurposeContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where purpose contains DEFAULT_PURPOSE
        defaultMemberExistingfacilityShouldBeFound("purpose.contains=" + DEFAULT_PURPOSE);

        // Get all the memberExistingfacilityList where purpose contains UPDATED_PURPOSE
        defaultMemberExistingfacilityShouldNotBeFound("purpose.contains=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByPurposeNotContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where purpose does not contain DEFAULT_PURPOSE
        defaultMemberExistingfacilityShouldNotBeFound("purpose.doesNotContain=" + DEFAULT_PURPOSE);

        // Get all the memberExistingfacilityList where purpose does not contain UPDATED_PURPOSE
        defaultMemberExistingfacilityShouldBeFound("purpose.doesNotContain=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesBySectionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where sectionDate equals to DEFAULT_SECTION_DATE
        defaultMemberExistingfacilityShouldBeFound("sectionDate.equals=" + DEFAULT_SECTION_DATE);

        // Get all the memberExistingfacilityList where sectionDate equals to UPDATED_SECTION_DATE
        defaultMemberExistingfacilityShouldNotBeFound("sectionDate.equals=" + UPDATED_SECTION_DATE);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesBySectionDateIsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where sectionDate in DEFAULT_SECTION_DATE or UPDATED_SECTION_DATE
        defaultMemberExistingfacilityShouldBeFound("sectionDate.in=" + DEFAULT_SECTION_DATE + "," + UPDATED_SECTION_DATE);

        // Get all the memberExistingfacilityList where sectionDate equals to UPDATED_SECTION_DATE
        defaultMemberExistingfacilityShouldNotBeFound("sectionDate.in=" + UPDATED_SECTION_DATE);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesBySectionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where sectionDate is not null
        defaultMemberExistingfacilityShouldBeFound("sectionDate.specified=true");

        // Get all the memberExistingfacilityList where sectionDate is null
        defaultMemberExistingfacilityShouldNotBeFound("sectionDate.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByOutstandingInLacIsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where outstandingInLac equals to DEFAULT_OUTSTANDING_IN_LAC
        defaultMemberExistingfacilityShouldBeFound("outstandingInLac.equals=" + DEFAULT_OUTSTANDING_IN_LAC);

        // Get all the memberExistingfacilityList where outstandingInLac equals to UPDATED_OUTSTANDING_IN_LAC
        defaultMemberExistingfacilityShouldNotBeFound("outstandingInLac.equals=" + UPDATED_OUTSTANDING_IN_LAC);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByOutstandingInLacIsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where outstandingInLac in DEFAULT_OUTSTANDING_IN_LAC or UPDATED_OUTSTANDING_IN_LAC
        defaultMemberExistingfacilityShouldBeFound("outstandingInLac.in=" + DEFAULT_OUTSTANDING_IN_LAC + "," + UPDATED_OUTSTANDING_IN_LAC);

        // Get all the memberExistingfacilityList where outstandingInLac equals to UPDATED_OUTSTANDING_IN_LAC
        defaultMemberExistingfacilityShouldNotBeFound("outstandingInLac.in=" + UPDATED_OUTSTANDING_IN_LAC);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByOutstandingInLacIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where outstandingInLac is not null
        defaultMemberExistingfacilityShouldBeFound("outstandingInLac.specified=true");

        // Get all the memberExistingfacilityList where outstandingInLac is null
        defaultMemberExistingfacilityShouldNotBeFound("outstandingInLac.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByOutstandingInLacIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where outstandingInLac is greater than or equal to DEFAULT_OUTSTANDING_IN_LAC
        defaultMemberExistingfacilityShouldBeFound("outstandingInLac.greaterThanOrEqual=" + DEFAULT_OUTSTANDING_IN_LAC);

        // Get all the memberExistingfacilityList where outstandingInLac is greater than or equal to UPDATED_OUTSTANDING_IN_LAC
        defaultMemberExistingfacilityShouldNotBeFound("outstandingInLac.greaterThanOrEqual=" + UPDATED_OUTSTANDING_IN_LAC);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByOutstandingInLacIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where outstandingInLac is less than or equal to DEFAULT_OUTSTANDING_IN_LAC
        defaultMemberExistingfacilityShouldBeFound("outstandingInLac.lessThanOrEqual=" + DEFAULT_OUTSTANDING_IN_LAC);

        // Get all the memberExistingfacilityList where outstandingInLac is less than or equal to SMALLER_OUTSTANDING_IN_LAC
        defaultMemberExistingfacilityShouldNotBeFound("outstandingInLac.lessThanOrEqual=" + SMALLER_OUTSTANDING_IN_LAC);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByOutstandingInLacIsLessThanSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where outstandingInLac is less than DEFAULT_OUTSTANDING_IN_LAC
        defaultMemberExistingfacilityShouldNotBeFound("outstandingInLac.lessThan=" + DEFAULT_OUTSTANDING_IN_LAC);

        // Get all the memberExistingfacilityList where outstandingInLac is less than UPDATED_OUTSTANDING_IN_LAC
        defaultMemberExistingfacilityShouldBeFound("outstandingInLac.lessThan=" + UPDATED_OUTSTANDING_IN_LAC);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByOutstandingInLacIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where outstandingInLac is greater than DEFAULT_OUTSTANDING_IN_LAC
        defaultMemberExistingfacilityShouldNotBeFound("outstandingInLac.greaterThan=" + DEFAULT_OUTSTANDING_IN_LAC);

        // Get all the memberExistingfacilityList where outstandingInLac is greater than SMALLER_OUTSTANDING_IN_LAC
        defaultMemberExistingfacilityShouldBeFound("outstandingInLac.greaterThan=" + SMALLER_OUTSTANDING_IN_LAC);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where status equals to DEFAULT_STATUS
        defaultMemberExistingfacilityShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the memberExistingfacilityList where status equals to UPDATED_STATUS
        defaultMemberExistingfacilityShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMemberExistingfacilityShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the memberExistingfacilityList where status equals to UPDATED_STATUS
        defaultMemberExistingfacilityShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where status is not null
        defaultMemberExistingfacilityShouldBeFound("status.specified=true");

        // Get all the memberExistingfacilityList where status is null
        defaultMemberExistingfacilityShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where rating equals to DEFAULT_RATING
        defaultMemberExistingfacilityShouldBeFound("rating.equals=" + DEFAULT_RATING);

        // Get all the memberExistingfacilityList where rating equals to UPDATED_RATING
        defaultMemberExistingfacilityShouldNotBeFound("rating.equals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByRatingIsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where rating in DEFAULT_RATING or UPDATED_RATING
        defaultMemberExistingfacilityShouldBeFound("rating.in=" + DEFAULT_RATING + "," + UPDATED_RATING);

        // Get all the memberExistingfacilityList where rating equals to UPDATED_RATING
        defaultMemberExistingfacilityShouldNotBeFound("rating.in=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where rating is not null
        defaultMemberExistingfacilityShouldBeFound("rating.specified=true");

        // Get all the memberExistingfacilityList where rating is null
        defaultMemberExistingfacilityShouldNotBeFound("rating.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where isDeleted equals to DEFAULT_IS_DELETED
        defaultMemberExistingfacilityShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the memberExistingfacilityList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberExistingfacilityShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultMemberExistingfacilityShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the memberExistingfacilityList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberExistingfacilityShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where isDeleted is not null
        defaultMemberExistingfacilityShouldBeFound("isDeleted.specified=true");

        // Get all the memberExistingfacilityList where isDeleted is null
        defaultMemberExistingfacilityShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultMemberExistingfacilityShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the memberExistingfacilityList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberExistingfacilityShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultMemberExistingfacilityShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the memberExistingfacilityList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberExistingfacilityShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where lastModified is not null
        defaultMemberExistingfacilityShouldBeFound("lastModified.specified=true");

        // Get all the memberExistingfacilityList where lastModified is null
        defaultMemberExistingfacilityShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultMemberExistingfacilityShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberExistingfacilityList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberExistingfacilityShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultMemberExistingfacilityShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the memberExistingfacilityList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberExistingfacilityShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where lastModifiedBy is not null
        defaultMemberExistingfacilityShouldBeFound("lastModifiedBy.specified=true");

        // Get all the memberExistingfacilityList where lastModifiedBy is null
        defaultMemberExistingfacilityShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultMemberExistingfacilityShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberExistingfacilityList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultMemberExistingfacilityShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultMemberExistingfacilityShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberExistingfacilityList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultMemberExistingfacilityShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where createdBy equals to DEFAULT_CREATED_BY
        defaultMemberExistingfacilityShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the memberExistingfacilityList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberExistingfacilityShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultMemberExistingfacilityShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the memberExistingfacilityList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberExistingfacilityShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where createdBy is not null
        defaultMemberExistingfacilityShouldBeFound("createdBy.specified=true");

        // Get all the memberExistingfacilityList where createdBy is null
        defaultMemberExistingfacilityShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where createdBy contains DEFAULT_CREATED_BY
        defaultMemberExistingfacilityShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the memberExistingfacilityList where createdBy contains UPDATED_CREATED_BY
        defaultMemberExistingfacilityShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where createdBy does not contain DEFAULT_CREATED_BY
        defaultMemberExistingfacilityShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the memberExistingfacilityList where createdBy does not contain UPDATED_CREATED_BY
        defaultMemberExistingfacilityShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where createdOn equals to DEFAULT_CREATED_ON
        defaultMemberExistingfacilityShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the memberExistingfacilityList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberExistingfacilityShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultMemberExistingfacilityShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the memberExistingfacilityList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberExistingfacilityShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where createdOn is not null
        defaultMemberExistingfacilityShouldBeFound("createdOn.specified=true");

        // Get all the memberExistingfacilityList where createdOn is null
        defaultMemberExistingfacilityShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultMemberExistingfacilityShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberExistingfacilityList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultMemberExistingfacilityShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultMemberExistingfacilityShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the memberExistingfacilityList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultMemberExistingfacilityShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField1 is not null
        defaultMemberExistingfacilityShouldBeFound("freeField1.specified=true");

        // Get all the memberExistingfacilityList where freeField1 is null
        defaultMemberExistingfacilityShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultMemberExistingfacilityShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberExistingfacilityList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultMemberExistingfacilityShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultMemberExistingfacilityShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberExistingfacilityList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultMemberExistingfacilityShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultMemberExistingfacilityShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberExistingfacilityList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultMemberExistingfacilityShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultMemberExistingfacilityShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the memberExistingfacilityList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultMemberExistingfacilityShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField2 is not null
        defaultMemberExistingfacilityShouldBeFound("freeField2.specified=true");

        // Get all the memberExistingfacilityList where freeField2 is null
        defaultMemberExistingfacilityShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultMemberExistingfacilityShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberExistingfacilityList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultMemberExistingfacilityShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultMemberExistingfacilityShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberExistingfacilityList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultMemberExistingfacilityShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultMemberExistingfacilityShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberExistingfacilityList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultMemberExistingfacilityShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultMemberExistingfacilityShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the memberExistingfacilityList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultMemberExistingfacilityShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField3 is not null
        defaultMemberExistingfacilityShouldBeFound("freeField3.specified=true");

        // Get all the memberExistingfacilityList where freeField3 is null
        defaultMemberExistingfacilityShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultMemberExistingfacilityShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberExistingfacilityList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultMemberExistingfacilityShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultMemberExistingfacilityShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberExistingfacilityList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultMemberExistingfacilityShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultMemberExistingfacilityShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the memberExistingfacilityList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultMemberExistingfacilityShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultMemberExistingfacilityShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the memberExistingfacilityList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultMemberExistingfacilityShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField4 is not null
        defaultMemberExistingfacilityShouldBeFound("freeField4.specified=true");

        // Get all the memberExistingfacilityList where freeField4 is null
        defaultMemberExistingfacilityShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultMemberExistingfacilityShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the memberExistingfacilityList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultMemberExistingfacilityShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultMemberExistingfacilityShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the memberExistingfacilityList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultMemberExistingfacilityShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultMemberExistingfacilityShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the memberExistingfacilityList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultMemberExistingfacilityShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultMemberExistingfacilityShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the memberExistingfacilityList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultMemberExistingfacilityShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField5 is not null
        defaultMemberExistingfacilityShouldBeFound("freeField5.specified=true");

        // Get all the memberExistingfacilityList where freeField5 is null
        defaultMemberExistingfacilityShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultMemberExistingfacilityShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the memberExistingfacilityList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultMemberExistingfacilityShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultMemberExistingfacilityShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the memberExistingfacilityList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultMemberExistingfacilityShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultMemberExistingfacilityShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the memberExistingfacilityList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultMemberExistingfacilityShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultMemberExistingfacilityShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the memberExistingfacilityList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultMemberExistingfacilityShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField6 is not null
        defaultMemberExistingfacilityShouldBeFound("freeField6.specified=true");

        // Get all the memberExistingfacilityList where freeField6 is null
        defaultMemberExistingfacilityShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultMemberExistingfacilityShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the memberExistingfacilityList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultMemberExistingfacilityShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        // Get all the memberExistingfacilityList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultMemberExistingfacilityShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the memberExistingfacilityList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultMemberExistingfacilityShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMemberExistingfacilitiesByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        memberExistingfacility.setMember(member);
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);
        Long memberId = member.getId();

        // Get all the memberExistingfacilityList where member equals to memberId
        defaultMemberExistingfacilityShouldBeFound("memberId.equals=" + memberId);

        // Get all the memberExistingfacilityList where member equals to (memberId + 1)
        defaultMemberExistingfacilityShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMemberExistingfacilityShouldBeFound(String filter) throws Exception {
        restMemberExistingfacilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberExistingfacility.getId().intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].facilityName").value(hasItem(DEFAULT_FACILITY_NAME)))
            .andExpect(jsonPath("$.[*].facilitatedFrom").value(hasItem(DEFAULT_FACILITATED_FROM)))
            .andExpect(jsonPath("$.[*].nature").value(hasItem(DEFAULT_NATURE)))
            .andExpect(jsonPath("$.[*].amtInLac").value(hasItem(DEFAULT_AMT_IN_LAC.doubleValue())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].sectionDate").value(hasItem(DEFAULT_SECTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].outstandingInLac").value(hasItem(DEFAULT_OUTSTANDING_IN_LAC.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.toString())))
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
        restMemberExistingfacilityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMemberExistingfacilityShouldNotBeFound(String filter) throws Exception {
        restMemberExistingfacilityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMemberExistingfacilityMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMemberExistingfacility() throws Exception {
        // Get the memberExistingfacility
        restMemberExistingfacilityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMemberExistingfacility() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        int databaseSizeBeforeUpdate = memberExistingfacilityRepository.findAll().size();

        // Update the memberExistingfacility
        MemberExistingfacility updatedMemberExistingfacility = memberExistingfacilityRepository
            .findById(memberExistingfacility.getId())
            .get();
        // Disconnect from session so that the updates on updatedMemberExistingfacility are not directly saved in db
        em.detach(updatedMemberExistingfacility);
        updatedMemberExistingfacility
            .year(UPDATED_YEAR)
            .facilityName(UPDATED_FACILITY_NAME)
            .facilitatedFrom(UPDATED_FACILITATED_FROM)
            .nature(UPDATED_NATURE)
            .amtInLac(UPDATED_AMT_IN_LAC)
            .purpose(UPDATED_PURPOSE)
            .sectionDate(UPDATED_SECTION_DATE)
            .outstandingInLac(UPDATED_OUTSTANDING_IN_LAC)
            .status(UPDATED_STATUS)
            .rating(UPDATED_RATING)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);
        //           .freeField6(UPDATED_FREE_FIELD_6);
        MemberExistingfacilityDTO memberExistingfacilityDTO = memberExistingfacilityMapper.toDto(updatedMemberExistingfacility);

        restMemberExistingfacilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberExistingfacilityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberExistingfacilityDTO))
            )
            .andExpect(status().isOk());

        // Validate the MemberExistingfacility in the database
        List<MemberExistingfacility> memberExistingfacilityList = memberExistingfacilityRepository.findAll();
        assertThat(memberExistingfacilityList).hasSize(databaseSizeBeforeUpdate);
        MemberExistingfacility testMemberExistingfacility = memberExistingfacilityList.get(memberExistingfacilityList.size() - 1);
        assertThat(testMemberExistingfacility.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testMemberExistingfacility.getFacilityName()).isEqualTo(UPDATED_FACILITY_NAME);
        assertThat(testMemberExistingfacility.getFacilitatedFrom()).isEqualTo(UPDATED_FACILITATED_FROM);
        assertThat(testMemberExistingfacility.getNature()).isEqualTo(UPDATED_NATURE);
        assertThat(testMemberExistingfacility.getAmtInLac()).isEqualTo(UPDATED_AMT_IN_LAC);
        assertThat(testMemberExistingfacility.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testMemberExistingfacility.getSectionDate()).isEqualTo(UPDATED_SECTION_DATE);
        assertThat(testMemberExistingfacility.getOutstandingInLac()).isEqualTo(UPDATED_OUTSTANDING_IN_LAC);
        assertThat(testMemberExistingfacility.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMemberExistingfacility.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testMemberExistingfacility.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMemberExistingfacility.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberExistingfacility.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberExistingfacility.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMemberExistingfacility.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMemberExistingfacility.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMemberExistingfacility.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMemberExistingfacility.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMemberExistingfacility.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testMemberExistingfacility.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        //       assertThat(testMemberExistingfacility.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingMemberExistingfacility() throws Exception {
        int databaseSizeBeforeUpdate = memberExistingfacilityRepository.findAll().size();
        memberExistingfacility.setId(count.incrementAndGet());

        // Create the MemberExistingfacility
        MemberExistingfacilityDTO memberExistingfacilityDTO = memberExistingfacilityMapper.toDto(memberExistingfacility);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberExistingfacilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberExistingfacilityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberExistingfacilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberExistingfacility in the database
        List<MemberExistingfacility> memberExistingfacilityList = memberExistingfacilityRepository.findAll();
        assertThat(memberExistingfacilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMemberExistingfacility() throws Exception {
        int databaseSizeBeforeUpdate = memberExistingfacilityRepository.findAll().size();
        memberExistingfacility.setId(count.incrementAndGet());

        // Create the MemberExistingfacility
        MemberExistingfacilityDTO memberExistingfacilityDTO = memberExistingfacilityMapper.toDto(memberExistingfacility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberExistingfacilityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberExistingfacilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberExistingfacility in the database
        List<MemberExistingfacility> memberExistingfacilityList = memberExistingfacilityRepository.findAll();
        assertThat(memberExistingfacilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMemberExistingfacility() throws Exception {
        int databaseSizeBeforeUpdate = memberExistingfacilityRepository.findAll().size();
        memberExistingfacility.setId(count.incrementAndGet());

        // Create the MemberExistingfacility
        MemberExistingfacilityDTO memberExistingfacilityDTO = memberExistingfacilityMapper.toDto(memberExistingfacility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberExistingfacilityMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberExistingfacilityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberExistingfacility in the database
        List<MemberExistingfacility> memberExistingfacilityList = memberExistingfacilityRepository.findAll();
        assertThat(memberExistingfacilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberExistingfacilityWithPatch() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        int databaseSizeBeforeUpdate = memberExistingfacilityRepository.findAll().size();

        // Update the memberExistingfacility using partial update
        MemberExistingfacility partialUpdatedMemberExistingfacility = new MemberExistingfacility();
        partialUpdatedMemberExistingfacility.setId(memberExistingfacility.getId());

        partialUpdatedMemberExistingfacility
            .year(UPDATED_YEAR)
            .facilityName(UPDATED_FACILITY_NAME)
            .facilitatedFrom(UPDATED_FACILITATED_FROM)
            .amtInLac(UPDATED_AMT_IN_LAC)
            .purpose(UPDATED_PURPOSE)
            .sectionDate(UPDATED_SECTION_DATE)
            .outstandingInLac(UPDATED_OUTSTANDING_IN_LAC)
            .rating(UPDATED_RATING)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField5(UPDATED_FREE_FIELD_5);
        //            .freeField6(UPDATED_FREE_FIELD_6);

        restMemberExistingfacilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberExistingfacility.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberExistingfacility))
            )
            .andExpect(status().isOk());

        // Validate the MemberExistingfacility in the database
        List<MemberExistingfacility> memberExistingfacilityList = memberExistingfacilityRepository.findAll();
        assertThat(memberExistingfacilityList).hasSize(databaseSizeBeforeUpdate);
        MemberExistingfacility testMemberExistingfacility = memberExistingfacilityList.get(memberExistingfacilityList.size() - 1);
        assertThat(testMemberExistingfacility.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testMemberExistingfacility.getFacilityName()).isEqualTo(UPDATED_FACILITY_NAME);
        assertThat(testMemberExistingfacility.getFacilitatedFrom()).isEqualTo(UPDATED_FACILITATED_FROM);
        assertThat(testMemberExistingfacility.getNature()).isEqualTo(DEFAULT_NATURE);
        assertThat(testMemberExistingfacility.getAmtInLac()).isEqualTo(UPDATED_AMT_IN_LAC);
        assertThat(testMemberExistingfacility.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testMemberExistingfacility.getSectionDate()).isEqualTo(UPDATED_SECTION_DATE);
        assertThat(testMemberExistingfacility.getOutstandingInLac()).isEqualTo(UPDATED_OUTSTANDING_IN_LAC);
        assertThat(testMemberExistingfacility.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMemberExistingfacility.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testMemberExistingfacility.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMemberExistingfacility.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMemberExistingfacility.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberExistingfacility.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMemberExistingfacility.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMemberExistingfacility.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMemberExistingfacility.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testMemberExistingfacility.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMemberExistingfacility.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testMemberExistingfacility.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        //    assertThat(testMemberExistingfacility.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateMemberExistingfacilityWithPatch() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        int databaseSizeBeforeUpdate = memberExistingfacilityRepository.findAll().size();

        // Update the memberExistingfacility using partial update
        MemberExistingfacility partialUpdatedMemberExistingfacility = new MemberExistingfacility();
        partialUpdatedMemberExistingfacility.setId(memberExistingfacility.getId());

        partialUpdatedMemberExistingfacility
            .year(UPDATED_YEAR)
            .facilityName(UPDATED_FACILITY_NAME)
            .facilitatedFrom(UPDATED_FACILITATED_FROM)
            .nature(UPDATED_NATURE)
            .amtInLac(UPDATED_AMT_IN_LAC)
            .purpose(UPDATED_PURPOSE)
            .sectionDate(UPDATED_SECTION_DATE)
            .outstandingInLac(UPDATED_OUTSTANDING_IN_LAC)
            .status(UPDATED_STATUS)
            .rating(UPDATED_RATING)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);
        //            .freeField6(UPDATED_FREE_FIELD_6);

        restMemberExistingfacilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberExistingfacility.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberExistingfacility))
            )
            .andExpect(status().isOk());

        // Validate the MemberExistingfacility in the database
        List<MemberExistingfacility> memberExistingfacilityList = memberExistingfacilityRepository.findAll();
        assertThat(memberExistingfacilityList).hasSize(databaseSizeBeforeUpdate);
        MemberExistingfacility testMemberExistingfacility = memberExistingfacilityList.get(memberExistingfacilityList.size() - 1);
        assertThat(testMemberExistingfacility.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testMemberExistingfacility.getFacilityName()).isEqualTo(UPDATED_FACILITY_NAME);
        assertThat(testMemberExistingfacility.getFacilitatedFrom()).isEqualTo(UPDATED_FACILITATED_FROM);
        assertThat(testMemberExistingfacility.getNature()).isEqualTo(UPDATED_NATURE);
        assertThat(testMemberExistingfacility.getAmtInLac()).isEqualTo(UPDATED_AMT_IN_LAC);
        assertThat(testMemberExistingfacility.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testMemberExistingfacility.getSectionDate()).isEqualTo(UPDATED_SECTION_DATE);
        assertThat(testMemberExistingfacility.getOutstandingInLac()).isEqualTo(UPDATED_OUTSTANDING_IN_LAC);
        assertThat(testMemberExistingfacility.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMemberExistingfacility.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testMemberExistingfacility.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMemberExistingfacility.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberExistingfacility.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberExistingfacility.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMemberExistingfacility.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMemberExistingfacility.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMemberExistingfacility.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMemberExistingfacility.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMemberExistingfacility.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testMemberExistingfacility.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        //        assertThat(testMemberExistingfacility.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingMemberExistingfacility() throws Exception {
        int databaseSizeBeforeUpdate = memberExistingfacilityRepository.findAll().size();
        memberExistingfacility.setId(count.incrementAndGet());

        // Create the MemberExistingfacility
        MemberExistingfacilityDTO memberExistingfacilityDTO = memberExistingfacilityMapper.toDto(memberExistingfacility);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberExistingfacilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, memberExistingfacilityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberExistingfacilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberExistingfacility in the database
        List<MemberExistingfacility> memberExistingfacilityList = memberExistingfacilityRepository.findAll();
        assertThat(memberExistingfacilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMemberExistingfacility() throws Exception {
        int databaseSizeBeforeUpdate = memberExistingfacilityRepository.findAll().size();
        memberExistingfacility.setId(count.incrementAndGet());

        // Create the MemberExistingfacility
        MemberExistingfacilityDTO memberExistingfacilityDTO = memberExistingfacilityMapper.toDto(memberExistingfacility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberExistingfacilityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberExistingfacilityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberExistingfacility in the database
        List<MemberExistingfacility> memberExistingfacilityList = memberExistingfacilityRepository.findAll();
        assertThat(memberExistingfacilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMemberExistingfacility() throws Exception {
        int databaseSizeBeforeUpdate = memberExistingfacilityRepository.findAll().size();
        memberExistingfacility.setId(count.incrementAndGet());

        // Create the MemberExistingfacility
        MemberExistingfacilityDTO memberExistingfacilityDTO = memberExistingfacilityMapper.toDto(memberExistingfacility);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberExistingfacilityMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberExistingfacilityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberExistingfacility in the database
        List<MemberExistingfacility> memberExistingfacilityList = memberExistingfacilityRepository.findAll();
        assertThat(memberExistingfacilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMemberExistingfacility() throws Exception {
        // Initialize the database
        memberExistingfacilityRepository.saveAndFlush(memberExistingfacility);

        int databaseSizeBeforeDelete = memberExistingfacilityRepository.findAll().size();

        // Delete the memberExistingfacility
        restMemberExistingfacilityMockMvc
            .perform(delete(ENTITY_API_URL_ID, memberExistingfacility.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MemberExistingfacility> memberExistingfacilityList = memberExistingfacilityRepository.findAll();
        assertThat(memberExistingfacilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
