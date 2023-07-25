package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.MemberLimit;
import com.techvg.los.repository.MemberLimitRepository;
import com.techvg.los.service.criteria.MemberLimitCriteria;
import com.techvg.los.service.dto.MemberLimitDTO;
import com.techvg.los.service.mapper.MemberLimitMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link MemberLimitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberLimitResourceIT {

    private static final Double DEFAULT_LIMIT_SANCTION_AMOUNT = 1D;
    private static final Double UPDATED_LIMIT_SANCTION_AMOUNT = 2D;
    private static final Double SMALLER_LIMIT_SANCTION_AMOUNT = 1D - 1D;

    private static final LocalDate DEFAULT_DT_LIMIT_SANCTIONED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_LIMIT_SANCTIONED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DT_LIMIT_SANCTIONED = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DT_LIMIT_EXPIRED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DT_LIMIT_EXPIRED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DT_LIMIT_EXPIRED = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

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

    private static final String ENTITY_API_URL = "/api/member-limits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberLimitRepository memberLimitRepository;

    @Autowired
    private MemberLimitMapper memberLimitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberLimitMockMvc;

    private MemberLimit memberLimit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberLimit createEntity(EntityManager em) {
        MemberLimit memberLimit = new MemberLimit()
            .limitSanctionAmount(DEFAULT_LIMIT_SANCTION_AMOUNT)
            .dtLimitSanctioned(DEFAULT_DT_LIMIT_SANCTIONED)
            .dtLimitExpired(DEFAULT_DT_LIMIT_EXPIRED)
            .purpose(DEFAULT_PURPOSE)
            .isDeleted(DEFAULT_IS_DELETED)
            .isActive(DEFAULT_IS_ACTIVE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4);
        return memberLimit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberLimit createUpdatedEntity(EntityManager em) {
        MemberLimit memberLimit = new MemberLimit()
            .limitSanctionAmount(UPDATED_LIMIT_SANCTION_AMOUNT)
            .dtLimitSanctioned(UPDATED_DT_LIMIT_SANCTIONED)
            .dtLimitExpired(UPDATED_DT_LIMIT_EXPIRED)
            .purpose(UPDATED_PURPOSE)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        return memberLimit;
    }

    @BeforeEach
    public void initTest() {
        memberLimit = createEntity(em);
    }

    @Test
    @Transactional
    void createMemberLimit() throws Exception {
        int databaseSizeBeforeCreate = memberLimitRepository.findAll().size();
        // Create the MemberLimit
        MemberLimitDTO memberLimitDTO = memberLimitMapper.toDto(memberLimit);
        restMemberLimitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberLimitDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MemberLimit in the database
        List<MemberLimit> memberLimitList = memberLimitRepository.findAll();
        assertThat(memberLimitList).hasSize(databaseSizeBeforeCreate + 1);
        MemberLimit testMemberLimit = memberLimitList.get(memberLimitList.size() - 1);
        assertThat(testMemberLimit.getLimitSanctionAmount()).isEqualTo(DEFAULT_LIMIT_SANCTION_AMOUNT);
        assertThat(testMemberLimit.getDtLimitSanctioned()).isEqualTo(DEFAULT_DT_LIMIT_SANCTIONED);
        assertThat(testMemberLimit.getDtLimitExpired()).isEqualTo(DEFAULT_DT_LIMIT_EXPIRED);
        assertThat(testMemberLimit.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testMemberLimit.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMemberLimit.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testMemberLimit.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMemberLimit.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMemberLimit.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testMemberLimit.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testMemberLimit.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testMemberLimit.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void createMemberLimitWithExistingId() throws Exception {
        // Create the MemberLimit with an existing ID
        memberLimit.setId(1L);
        MemberLimitDTO memberLimitDTO = memberLimitMapper.toDto(memberLimit);

        int databaseSizeBeforeCreate = memberLimitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberLimitMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberLimitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberLimit in the database
        List<MemberLimit> memberLimitList = memberLimitRepository.findAll();
        assertThat(memberLimitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMemberLimits() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList
        restMemberLimitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberLimit.getId().intValue())))
            .andExpect(jsonPath("$.[*].limitSanctionAmount").value(hasItem(DEFAULT_LIMIT_SANCTION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dtLimitSanctioned").value(hasItem(DEFAULT_DT_LIMIT_SANCTIONED.toString())))
            .andExpect(jsonPath("$.[*].dtLimitExpired").value(hasItem(DEFAULT_DT_LIMIT_EXPIRED.toString())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));
    }

    @Test
    @Transactional
    void getMemberLimit() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get the memberLimit
        restMemberLimitMockMvc
            .perform(get(ENTITY_API_URL_ID, memberLimit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(memberLimit.getId().intValue()))
            .andExpect(jsonPath("$.limitSanctionAmount").value(DEFAULT_LIMIT_SANCTION_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.dtLimitSanctioned").value(DEFAULT_DT_LIMIT_SANCTIONED.toString()))
            .andExpect(jsonPath("$.dtLimitExpired").value(DEFAULT_DT_LIMIT_EXPIRED.toString()))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4));
    }

    @Test
    @Transactional
    void getMemberLimitsByIdFiltering() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        Long id = memberLimit.getId();

        defaultMemberLimitShouldBeFound("id.equals=" + id);
        defaultMemberLimitShouldNotBeFound("id.notEquals=" + id);

        defaultMemberLimitShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMemberLimitShouldNotBeFound("id.greaterThan=" + id);

        defaultMemberLimitShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMemberLimitShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByLimitSanctionAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where limitSanctionAmount equals to DEFAULT_LIMIT_SANCTION_AMOUNT
        defaultMemberLimitShouldBeFound("limitSanctionAmount.equals=" + DEFAULT_LIMIT_SANCTION_AMOUNT);

        // Get all the memberLimitList where limitSanctionAmount equals to UPDATED_LIMIT_SANCTION_AMOUNT
        defaultMemberLimitShouldNotBeFound("limitSanctionAmount.equals=" + UPDATED_LIMIT_SANCTION_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByLimitSanctionAmountIsInShouldWork() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where limitSanctionAmount in DEFAULT_LIMIT_SANCTION_AMOUNT or UPDATED_LIMIT_SANCTION_AMOUNT
        defaultMemberLimitShouldBeFound("limitSanctionAmount.in=" + DEFAULT_LIMIT_SANCTION_AMOUNT + "," + UPDATED_LIMIT_SANCTION_AMOUNT);

        // Get all the memberLimitList where limitSanctionAmount equals to UPDATED_LIMIT_SANCTION_AMOUNT
        defaultMemberLimitShouldNotBeFound("limitSanctionAmount.in=" + UPDATED_LIMIT_SANCTION_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByLimitSanctionAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where limitSanctionAmount is not null
        defaultMemberLimitShouldBeFound("limitSanctionAmount.specified=true");

        // Get all the memberLimitList where limitSanctionAmount is null
        defaultMemberLimitShouldNotBeFound("limitSanctionAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLimitsByLimitSanctionAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where limitSanctionAmount is greater than or equal to DEFAULT_LIMIT_SANCTION_AMOUNT
        defaultMemberLimitShouldBeFound("limitSanctionAmount.greaterThanOrEqual=" + DEFAULT_LIMIT_SANCTION_AMOUNT);

        // Get all the memberLimitList where limitSanctionAmount is greater than or equal to UPDATED_LIMIT_SANCTION_AMOUNT
        defaultMemberLimitShouldNotBeFound("limitSanctionAmount.greaterThanOrEqual=" + UPDATED_LIMIT_SANCTION_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByLimitSanctionAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where limitSanctionAmount is less than or equal to DEFAULT_LIMIT_SANCTION_AMOUNT
        defaultMemberLimitShouldBeFound("limitSanctionAmount.lessThanOrEqual=" + DEFAULT_LIMIT_SANCTION_AMOUNT);

        // Get all the memberLimitList where limitSanctionAmount is less than or equal to SMALLER_LIMIT_SANCTION_AMOUNT
        defaultMemberLimitShouldNotBeFound("limitSanctionAmount.lessThanOrEqual=" + SMALLER_LIMIT_SANCTION_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByLimitSanctionAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where limitSanctionAmount is less than DEFAULT_LIMIT_SANCTION_AMOUNT
        defaultMemberLimitShouldNotBeFound("limitSanctionAmount.lessThan=" + DEFAULT_LIMIT_SANCTION_AMOUNT);

        // Get all the memberLimitList where limitSanctionAmount is less than UPDATED_LIMIT_SANCTION_AMOUNT
        defaultMemberLimitShouldBeFound("limitSanctionAmount.lessThan=" + UPDATED_LIMIT_SANCTION_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByLimitSanctionAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where limitSanctionAmount is greater than DEFAULT_LIMIT_SANCTION_AMOUNT
        defaultMemberLimitShouldNotBeFound("limitSanctionAmount.greaterThan=" + DEFAULT_LIMIT_SANCTION_AMOUNT);

        // Get all the memberLimitList where limitSanctionAmount is greater than SMALLER_LIMIT_SANCTION_AMOUNT
        defaultMemberLimitShouldBeFound("limitSanctionAmount.greaterThan=" + SMALLER_LIMIT_SANCTION_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByDtLimitSanctionedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where dtLimitSanctioned equals to DEFAULT_DT_LIMIT_SANCTIONED
        defaultMemberLimitShouldBeFound("dtLimitSanctioned.equals=" + DEFAULT_DT_LIMIT_SANCTIONED);

        // Get all the memberLimitList where dtLimitSanctioned equals to UPDATED_DT_LIMIT_SANCTIONED
        defaultMemberLimitShouldNotBeFound("dtLimitSanctioned.equals=" + UPDATED_DT_LIMIT_SANCTIONED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByDtLimitSanctionedIsInShouldWork() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where dtLimitSanctioned in DEFAULT_DT_LIMIT_SANCTIONED or UPDATED_DT_LIMIT_SANCTIONED
        defaultMemberLimitShouldBeFound("dtLimitSanctioned.in=" + DEFAULT_DT_LIMIT_SANCTIONED + "," + UPDATED_DT_LIMIT_SANCTIONED);

        // Get all the memberLimitList where dtLimitSanctioned equals to UPDATED_DT_LIMIT_SANCTIONED
        defaultMemberLimitShouldNotBeFound("dtLimitSanctioned.in=" + UPDATED_DT_LIMIT_SANCTIONED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByDtLimitSanctionedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where dtLimitSanctioned is not null
        defaultMemberLimitShouldBeFound("dtLimitSanctioned.specified=true");

        // Get all the memberLimitList where dtLimitSanctioned is null
        defaultMemberLimitShouldNotBeFound("dtLimitSanctioned.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLimitsByDtLimitSanctionedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where dtLimitSanctioned is greater than or equal to DEFAULT_DT_LIMIT_SANCTIONED
        defaultMemberLimitShouldBeFound("dtLimitSanctioned.greaterThanOrEqual=" + DEFAULT_DT_LIMIT_SANCTIONED);

        // Get all the memberLimitList where dtLimitSanctioned is greater than or equal to UPDATED_DT_LIMIT_SANCTIONED
        defaultMemberLimitShouldNotBeFound("dtLimitSanctioned.greaterThanOrEqual=" + UPDATED_DT_LIMIT_SANCTIONED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByDtLimitSanctionedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where dtLimitSanctioned is less than or equal to DEFAULT_DT_LIMIT_SANCTIONED
        defaultMemberLimitShouldBeFound("dtLimitSanctioned.lessThanOrEqual=" + DEFAULT_DT_LIMIT_SANCTIONED);

        // Get all the memberLimitList where dtLimitSanctioned is less than or equal to SMALLER_DT_LIMIT_SANCTIONED
        defaultMemberLimitShouldNotBeFound("dtLimitSanctioned.lessThanOrEqual=" + SMALLER_DT_LIMIT_SANCTIONED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByDtLimitSanctionedIsLessThanSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where dtLimitSanctioned is less than DEFAULT_DT_LIMIT_SANCTIONED
        defaultMemberLimitShouldNotBeFound("dtLimitSanctioned.lessThan=" + DEFAULT_DT_LIMIT_SANCTIONED);

        // Get all the memberLimitList where dtLimitSanctioned is less than UPDATED_DT_LIMIT_SANCTIONED
        defaultMemberLimitShouldBeFound("dtLimitSanctioned.lessThan=" + UPDATED_DT_LIMIT_SANCTIONED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByDtLimitSanctionedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where dtLimitSanctioned is greater than DEFAULT_DT_LIMIT_SANCTIONED
        defaultMemberLimitShouldNotBeFound("dtLimitSanctioned.greaterThan=" + DEFAULT_DT_LIMIT_SANCTIONED);

        // Get all the memberLimitList where dtLimitSanctioned is greater than SMALLER_DT_LIMIT_SANCTIONED
        defaultMemberLimitShouldBeFound("dtLimitSanctioned.greaterThan=" + SMALLER_DT_LIMIT_SANCTIONED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByDtLimitExpiredIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where dtLimitExpired equals to DEFAULT_DT_LIMIT_EXPIRED
        defaultMemberLimitShouldBeFound("dtLimitExpired.equals=" + DEFAULT_DT_LIMIT_EXPIRED);

        // Get all the memberLimitList where dtLimitExpired equals to UPDATED_DT_LIMIT_EXPIRED
        defaultMemberLimitShouldNotBeFound("dtLimitExpired.equals=" + UPDATED_DT_LIMIT_EXPIRED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByDtLimitExpiredIsInShouldWork() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where dtLimitExpired in DEFAULT_DT_LIMIT_EXPIRED or UPDATED_DT_LIMIT_EXPIRED
        defaultMemberLimitShouldBeFound("dtLimitExpired.in=" + DEFAULT_DT_LIMIT_EXPIRED + "," + UPDATED_DT_LIMIT_EXPIRED);

        // Get all the memberLimitList where dtLimitExpired equals to UPDATED_DT_LIMIT_EXPIRED
        defaultMemberLimitShouldNotBeFound("dtLimitExpired.in=" + UPDATED_DT_LIMIT_EXPIRED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByDtLimitExpiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where dtLimitExpired is not null
        defaultMemberLimitShouldBeFound("dtLimitExpired.specified=true");

        // Get all the memberLimitList where dtLimitExpired is null
        defaultMemberLimitShouldNotBeFound("dtLimitExpired.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLimitsByDtLimitExpiredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where dtLimitExpired is greater than or equal to DEFAULT_DT_LIMIT_EXPIRED
        defaultMemberLimitShouldBeFound("dtLimitExpired.greaterThanOrEqual=" + DEFAULT_DT_LIMIT_EXPIRED);

        // Get all the memberLimitList where dtLimitExpired is greater than or equal to UPDATED_DT_LIMIT_EXPIRED
        defaultMemberLimitShouldNotBeFound("dtLimitExpired.greaterThanOrEqual=" + UPDATED_DT_LIMIT_EXPIRED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByDtLimitExpiredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where dtLimitExpired is less than or equal to DEFAULT_DT_LIMIT_EXPIRED
        defaultMemberLimitShouldBeFound("dtLimitExpired.lessThanOrEqual=" + DEFAULT_DT_LIMIT_EXPIRED);

        // Get all the memberLimitList where dtLimitExpired is less than or equal to SMALLER_DT_LIMIT_EXPIRED
        defaultMemberLimitShouldNotBeFound("dtLimitExpired.lessThanOrEqual=" + SMALLER_DT_LIMIT_EXPIRED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByDtLimitExpiredIsLessThanSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where dtLimitExpired is less than DEFAULT_DT_LIMIT_EXPIRED
        defaultMemberLimitShouldNotBeFound("dtLimitExpired.lessThan=" + DEFAULT_DT_LIMIT_EXPIRED);

        // Get all the memberLimitList where dtLimitExpired is less than UPDATED_DT_LIMIT_EXPIRED
        defaultMemberLimitShouldBeFound("dtLimitExpired.lessThan=" + UPDATED_DT_LIMIT_EXPIRED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByDtLimitExpiredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where dtLimitExpired is greater than DEFAULT_DT_LIMIT_EXPIRED
        defaultMemberLimitShouldNotBeFound("dtLimitExpired.greaterThan=" + DEFAULT_DT_LIMIT_EXPIRED);

        // Get all the memberLimitList where dtLimitExpired is greater than SMALLER_DT_LIMIT_EXPIRED
        defaultMemberLimitShouldBeFound("dtLimitExpired.greaterThan=" + SMALLER_DT_LIMIT_EXPIRED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByPurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where purpose equals to DEFAULT_PURPOSE
        defaultMemberLimitShouldBeFound("purpose.equals=" + DEFAULT_PURPOSE);

        // Get all the memberLimitList where purpose equals to UPDATED_PURPOSE
        defaultMemberLimitShouldNotBeFound("purpose.equals=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByPurposeIsInShouldWork() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where purpose in DEFAULT_PURPOSE or UPDATED_PURPOSE
        defaultMemberLimitShouldBeFound("purpose.in=" + DEFAULT_PURPOSE + "," + UPDATED_PURPOSE);

        // Get all the memberLimitList where purpose equals to UPDATED_PURPOSE
        defaultMemberLimitShouldNotBeFound("purpose.in=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByPurposeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where purpose is not null
        defaultMemberLimitShouldBeFound("purpose.specified=true");

        // Get all the memberLimitList where purpose is null
        defaultMemberLimitShouldNotBeFound("purpose.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLimitsByPurposeContainsSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where purpose contains DEFAULT_PURPOSE
        defaultMemberLimitShouldBeFound("purpose.contains=" + DEFAULT_PURPOSE);

        // Get all the memberLimitList where purpose contains UPDATED_PURPOSE
        defaultMemberLimitShouldNotBeFound("purpose.contains=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByPurposeNotContainsSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where purpose does not contain DEFAULT_PURPOSE
        defaultMemberLimitShouldNotBeFound("purpose.doesNotContain=" + DEFAULT_PURPOSE);

        // Get all the memberLimitList where purpose does not contain UPDATED_PURPOSE
        defaultMemberLimitShouldBeFound("purpose.doesNotContain=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where isDeleted equals to DEFAULT_IS_DELETED
        defaultMemberLimitShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the memberLimitList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberLimitShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultMemberLimitShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the memberLimitList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberLimitShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where isDeleted is not null
        defaultMemberLimitShouldBeFound("isDeleted.specified=true");

        // Get all the memberLimitList where isDeleted is null
        defaultMemberLimitShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLimitsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where isActive equals to DEFAULT_IS_ACTIVE
        defaultMemberLimitShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the memberLimitList where isActive equals to UPDATED_IS_ACTIVE
        defaultMemberLimitShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultMemberLimitShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the memberLimitList where isActive equals to UPDATED_IS_ACTIVE
        defaultMemberLimitShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where isActive is not null
        defaultMemberLimitShouldBeFound("isActive.specified=true");

        // Get all the memberLimitList where isActive is null
        defaultMemberLimitShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLimitsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultMemberLimitShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the memberLimitList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberLimitShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultMemberLimitShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the memberLimitList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberLimitShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where lastModified is not null
        defaultMemberLimitShouldBeFound("lastModified.specified=true");

        // Get all the memberLimitList where lastModified is null
        defaultMemberLimitShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLimitsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultMemberLimitShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberLimitList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberLimitShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultMemberLimitShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the memberLimitList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberLimitShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where lastModifiedBy is not null
        defaultMemberLimitShouldBeFound("lastModifiedBy.specified=true");

        // Get all the memberLimitList where lastModifiedBy is null
        defaultMemberLimitShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLimitsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultMemberLimitShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberLimitList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultMemberLimitShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultMemberLimitShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberLimitList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultMemberLimitShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultMemberLimitShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberLimitList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultMemberLimitShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultMemberLimitShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the memberLimitList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultMemberLimitShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField1 is not null
        defaultMemberLimitShouldBeFound("freeField1.specified=true");

        // Get all the memberLimitList where freeField1 is null
        defaultMemberLimitShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultMemberLimitShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberLimitList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultMemberLimitShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultMemberLimitShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberLimitList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultMemberLimitShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultMemberLimitShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberLimitList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultMemberLimitShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultMemberLimitShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the memberLimitList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultMemberLimitShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField2 is not null
        defaultMemberLimitShouldBeFound("freeField2.specified=true");

        // Get all the memberLimitList where freeField2 is null
        defaultMemberLimitShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultMemberLimitShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberLimitList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultMemberLimitShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultMemberLimitShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberLimitList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultMemberLimitShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultMemberLimitShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberLimitList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultMemberLimitShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultMemberLimitShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the memberLimitList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultMemberLimitShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField3 is not null
        defaultMemberLimitShouldBeFound("freeField3.specified=true");

        // Get all the memberLimitList where freeField3 is null
        defaultMemberLimitShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultMemberLimitShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberLimitList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultMemberLimitShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultMemberLimitShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberLimitList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultMemberLimitShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultMemberLimitShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the memberLimitList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultMemberLimitShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultMemberLimitShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the memberLimitList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultMemberLimitShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField4 is not null
        defaultMemberLimitShouldBeFound("freeField4.specified=true");

        // Get all the memberLimitList where freeField4 is null
        defaultMemberLimitShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultMemberLimitShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the memberLimitList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultMemberLimitShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMemberLimitsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        // Get all the memberLimitList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultMemberLimitShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the memberLimitList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultMemberLimitShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMemberLimitShouldBeFound(String filter) throws Exception {
        restMemberLimitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberLimit.getId().intValue())))
            .andExpect(jsonPath("$.[*].limitSanctionAmount").value(hasItem(DEFAULT_LIMIT_SANCTION_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dtLimitSanctioned").value(hasItem(DEFAULT_DT_LIMIT_SANCTIONED.toString())))
            .andExpect(jsonPath("$.[*].dtLimitExpired").value(hasItem(DEFAULT_DT_LIMIT_EXPIRED.toString())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));

        // Check, that the count call also returns 1
        restMemberLimitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMemberLimitShouldNotBeFound(String filter) throws Exception {
        restMemberLimitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMemberLimitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMemberLimit() throws Exception {
        // Get the memberLimit
        restMemberLimitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMemberLimit() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        int databaseSizeBeforeUpdate = memberLimitRepository.findAll().size();

        // Update the memberLimit
        MemberLimit updatedMemberLimit = memberLimitRepository.findById(memberLimit.getId()).get();
        // Disconnect from session so that the updates on updatedMemberLimit are not directly saved in db
        em.detach(updatedMemberLimit);
        updatedMemberLimit
            .limitSanctionAmount(UPDATED_LIMIT_SANCTION_AMOUNT)
            .dtLimitSanctioned(UPDATED_DT_LIMIT_SANCTIONED)
            .dtLimitExpired(UPDATED_DT_LIMIT_EXPIRED)
            .purpose(UPDATED_PURPOSE)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        MemberLimitDTO memberLimitDTO = memberLimitMapper.toDto(updatedMemberLimit);

        restMemberLimitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberLimitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberLimitDTO))
            )
            .andExpect(status().isOk());

        // Validate the MemberLimit in the database
        List<MemberLimit> memberLimitList = memberLimitRepository.findAll();
        assertThat(memberLimitList).hasSize(databaseSizeBeforeUpdate);
        MemberLimit testMemberLimit = memberLimitList.get(memberLimitList.size() - 1);
        assertThat(testMemberLimit.getLimitSanctionAmount()).isEqualTo(UPDATED_LIMIT_SANCTION_AMOUNT);
        assertThat(testMemberLimit.getDtLimitSanctioned()).isEqualTo(UPDATED_DT_LIMIT_SANCTIONED);
        assertThat(testMemberLimit.getDtLimitExpired()).isEqualTo(UPDATED_DT_LIMIT_EXPIRED);
        assertThat(testMemberLimit.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testMemberLimit.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMemberLimit.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testMemberLimit.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberLimit.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberLimit.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMemberLimit.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMemberLimit.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMemberLimit.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void putNonExistingMemberLimit() throws Exception {
        int databaseSizeBeforeUpdate = memberLimitRepository.findAll().size();
        memberLimit.setId(count.incrementAndGet());

        // Create the MemberLimit
        MemberLimitDTO memberLimitDTO = memberLimitMapper.toDto(memberLimit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberLimitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberLimitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberLimitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberLimit in the database
        List<MemberLimit> memberLimitList = memberLimitRepository.findAll();
        assertThat(memberLimitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMemberLimit() throws Exception {
        int databaseSizeBeforeUpdate = memberLimitRepository.findAll().size();
        memberLimit.setId(count.incrementAndGet());

        // Create the MemberLimit
        MemberLimitDTO memberLimitDTO = memberLimitMapper.toDto(memberLimit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberLimitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberLimitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberLimit in the database
        List<MemberLimit> memberLimitList = memberLimitRepository.findAll();
        assertThat(memberLimitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMemberLimit() throws Exception {
        int databaseSizeBeforeUpdate = memberLimitRepository.findAll().size();
        memberLimit.setId(count.incrementAndGet());

        // Create the MemberLimit
        MemberLimitDTO memberLimitDTO = memberLimitMapper.toDto(memberLimit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberLimitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberLimitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberLimit in the database
        List<MemberLimit> memberLimitList = memberLimitRepository.findAll();
        assertThat(memberLimitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberLimitWithPatch() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        int databaseSizeBeforeUpdate = memberLimitRepository.findAll().size();

        // Update the memberLimit using partial update
        MemberLimit partialUpdatedMemberLimit = new MemberLimit();
        partialUpdatedMemberLimit.setId(memberLimit.getId());

        partialUpdatedMemberLimit
            .limitSanctionAmount(UPDATED_LIMIT_SANCTION_AMOUNT)
            .dtLimitExpired(UPDATED_DT_LIMIT_EXPIRED)
            .purpose(UPDATED_PURPOSE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .freeField3(UPDATED_FREE_FIELD_3);

        restMemberLimitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberLimit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberLimit))
            )
            .andExpect(status().isOk());

        // Validate the MemberLimit in the database
        List<MemberLimit> memberLimitList = memberLimitRepository.findAll();
        assertThat(memberLimitList).hasSize(databaseSizeBeforeUpdate);
        MemberLimit testMemberLimit = memberLimitList.get(memberLimitList.size() - 1);
        assertThat(testMemberLimit.getLimitSanctionAmount()).isEqualTo(UPDATED_LIMIT_SANCTION_AMOUNT);
        assertThat(testMemberLimit.getDtLimitSanctioned()).isEqualTo(DEFAULT_DT_LIMIT_SANCTIONED);
        assertThat(testMemberLimit.getDtLimitExpired()).isEqualTo(UPDATED_DT_LIMIT_EXPIRED);
        assertThat(testMemberLimit.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testMemberLimit.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMemberLimit.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testMemberLimit.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberLimit.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMemberLimit.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testMemberLimit.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testMemberLimit.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMemberLimit.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void fullUpdateMemberLimitWithPatch() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        int databaseSizeBeforeUpdate = memberLimitRepository.findAll().size();

        // Update the memberLimit using partial update
        MemberLimit partialUpdatedMemberLimit = new MemberLimit();
        partialUpdatedMemberLimit.setId(memberLimit.getId());

        partialUpdatedMemberLimit
            .limitSanctionAmount(UPDATED_LIMIT_SANCTION_AMOUNT)
            .dtLimitSanctioned(UPDATED_DT_LIMIT_SANCTIONED)
            .dtLimitExpired(UPDATED_DT_LIMIT_EXPIRED)
            .purpose(UPDATED_PURPOSE)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restMemberLimitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberLimit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberLimit))
            )
            .andExpect(status().isOk());

        // Validate the MemberLimit in the database
        List<MemberLimit> memberLimitList = memberLimitRepository.findAll();
        assertThat(memberLimitList).hasSize(databaseSizeBeforeUpdate);
        MemberLimit testMemberLimit = memberLimitList.get(memberLimitList.size() - 1);
        assertThat(testMemberLimit.getLimitSanctionAmount()).isEqualTo(UPDATED_LIMIT_SANCTION_AMOUNT);
        assertThat(testMemberLimit.getDtLimitSanctioned()).isEqualTo(UPDATED_DT_LIMIT_SANCTIONED);
        assertThat(testMemberLimit.getDtLimitExpired()).isEqualTo(UPDATED_DT_LIMIT_EXPIRED);
        assertThat(testMemberLimit.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testMemberLimit.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMemberLimit.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testMemberLimit.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberLimit.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberLimit.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMemberLimit.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMemberLimit.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMemberLimit.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void patchNonExistingMemberLimit() throws Exception {
        int databaseSizeBeforeUpdate = memberLimitRepository.findAll().size();
        memberLimit.setId(count.incrementAndGet());

        // Create the MemberLimit
        MemberLimitDTO memberLimitDTO = memberLimitMapper.toDto(memberLimit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberLimitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, memberLimitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberLimitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberLimit in the database
        List<MemberLimit> memberLimitList = memberLimitRepository.findAll();
        assertThat(memberLimitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMemberLimit() throws Exception {
        int databaseSizeBeforeUpdate = memberLimitRepository.findAll().size();
        memberLimit.setId(count.incrementAndGet());

        // Create the MemberLimit
        MemberLimitDTO memberLimitDTO = memberLimitMapper.toDto(memberLimit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberLimitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberLimitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberLimit in the database
        List<MemberLimit> memberLimitList = memberLimitRepository.findAll();
        assertThat(memberLimitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMemberLimit() throws Exception {
        int databaseSizeBeforeUpdate = memberLimitRepository.findAll().size();
        memberLimit.setId(count.incrementAndGet());

        // Create the MemberLimit
        MemberLimitDTO memberLimitDTO = memberLimitMapper.toDto(memberLimit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberLimitMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(memberLimitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberLimit in the database
        List<MemberLimit> memberLimitList = memberLimitRepository.findAll();
        assertThat(memberLimitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMemberLimit() throws Exception {
        // Initialize the database
        memberLimitRepository.saveAndFlush(memberLimit);

        int databaseSizeBeforeDelete = memberLimitRepository.findAll().size();

        // Delete the memberLimit
        restMemberLimitMockMvc
            .perform(delete(ENTITY_API_URL_ID, memberLimit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MemberLimit> memberLimitList = memberLimitRepository.findAll();
        assertThat(memberLimitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
