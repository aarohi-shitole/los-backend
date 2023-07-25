package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.AccountHeadCode;
import com.techvg.los.domain.LedgerAccounts;
import com.techvg.los.domain.enumeration.MappingType;
import com.techvg.los.repository.AccountHeadCodeRepository;
import com.techvg.los.service.criteria.AccountHeadCodeCriteria;
import com.techvg.los.service.dto.AccountHeadCodeDTO;
import com.techvg.los.service.mapper.AccountHeadCodeMapper;
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
 * Integration tests for the {@link AccountHeadCodeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccountHeadCodeResourceIT {

    private static final MappingType DEFAULT_TYPE = MappingType.HEADOFFICE;
    private static final MappingType UPDATED_TYPE = MappingType.SHARE;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_HEAD_CODE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_HEAD_CODE_NAME = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/account-head-codes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccountHeadCodeRepository accountHeadCodeRepository;

    @Autowired
    private AccountHeadCodeMapper accountHeadCodeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountHeadCodeMockMvc;

    private AccountHeadCode accountHeadCode;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountHeadCode createEntity(EntityManager em) {
        AccountHeadCode accountHeadCode = new AccountHeadCode()
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE)
            .headCodeName(DEFAULT_HEAD_CODE_NAME)
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
        return accountHeadCode;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountHeadCode createUpdatedEntity(EntityManager em) {
        AccountHeadCode accountHeadCode = new AccountHeadCode()
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .headCodeName(UPDATED_HEAD_CODE_NAME)
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
        return accountHeadCode;
    }

    @BeforeEach
    public void initTest() {
        accountHeadCode = createEntity(em);
    }

    @Test
    @Transactional
    void createAccountHeadCode() throws Exception {
        int databaseSizeBeforeCreate = accountHeadCodeRepository.findAll().size();
        // Create the AccountHeadCode
        AccountHeadCodeDTO accountHeadCodeDTO = accountHeadCodeMapper.toDto(accountHeadCode);
        restAccountHeadCodeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountHeadCodeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AccountHeadCode in the database
        List<AccountHeadCode> accountHeadCodeList = accountHeadCodeRepository.findAll();
        assertThat(accountHeadCodeList).hasSize(databaseSizeBeforeCreate + 1);
        AccountHeadCode testAccountHeadCode = accountHeadCodeList.get(accountHeadCodeList.size() - 1);
        assertThat(testAccountHeadCode.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAccountHeadCode.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAccountHeadCode.getHeadCodeName()).isEqualTo(DEFAULT_HEAD_CODE_NAME);
        assertThat(testAccountHeadCode.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAccountHeadCode.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testAccountHeadCode.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAccountHeadCode.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAccountHeadCode.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testAccountHeadCode.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testAccountHeadCode.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testAccountHeadCode.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testAccountHeadCode.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testAccountHeadCode.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void createAccountHeadCodeWithExistingId() throws Exception {
        // Create the AccountHeadCode with an existing ID
        accountHeadCode.setId(1L);
        AccountHeadCodeDTO accountHeadCodeDTO = accountHeadCodeMapper.toDto(accountHeadCode);

        int databaseSizeBeforeCreate = accountHeadCodeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountHeadCodeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountHeadCodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountHeadCode in the database
        List<AccountHeadCode> accountHeadCodeList = accountHeadCodeRepository.findAll();
        assertThat(accountHeadCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodes() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList
        restAccountHeadCodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountHeadCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].headCodeName").value(hasItem(DEFAULT_HEAD_CODE_NAME)))
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
    void getAccountHeadCode() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get the accountHeadCode
        restAccountHeadCodeMockMvc
            .perform(get(ENTITY_API_URL_ID, accountHeadCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountHeadCode.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.headCodeName").value(DEFAULT_HEAD_CODE_NAME))
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
    void getAccountHeadCodesByIdFiltering() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        Long id = accountHeadCode.getId();

        defaultAccountHeadCodeShouldBeFound("id.equals=" + id);
        defaultAccountHeadCodeShouldNotBeFound("id.notEquals=" + id);

        defaultAccountHeadCodeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAccountHeadCodeShouldNotBeFound("id.greaterThan=" + id);

        defaultAccountHeadCodeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAccountHeadCodeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where type equals to DEFAULT_TYPE
        defaultAccountHeadCodeShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the accountHeadCodeList where type equals to UPDATED_TYPE
        defaultAccountHeadCodeShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultAccountHeadCodeShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the accountHeadCodeList where type equals to UPDATED_TYPE
        defaultAccountHeadCodeShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where type is not null
        defaultAccountHeadCodeShouldBeFound("type.specified=true");

        // Get all the accountHeadCodeList where type is null
        defaultAccountHeadCodeShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where value equals to DEFAULT_VALUE
        defaultAccountHeadCodeShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the accountHeadCodeList where value equals to UPDATED_VALUE
        defaultAccountHeadCodeShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultAccountHeadCodeShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the accountHeadCodeList where value equals to UPDATED_VALUE
        defaultAccountHeadCodeShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where value is not null
        defaultAccountHeadCodeShouldBeFound("value.specified=true");

        // Get all the accountHeadCodeList where value is null
        defaultAccountHeadCodeShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByValueContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where value contains DEFAULT_VALUE
        defaultAccountHeadCodeShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the accountHeadCodeList where value contains UPDATED_VALUE
        defaultAccountHeadCodeShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByValueNotContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where value does not contain DEFAULT_VALUE
        defaultAccountHeadCodeShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the accountHeadCodeList where value does not contain UPDATED_VALUE
        defaultAccountHeadCodeShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByHeadCodeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where headCodeName equals to DEFAULT_HEAD_CODE_NAME
        defaultAccountHeadCodeShouldBeFound("headCodeName.equals=" + DEFAULT_HEAD_CODE_NAME);

        // Get all the accountHeadCodeList where headCodeName equals to UPDATED_HEAD_CODE_NAME
        defaultAccountHeadCodeShouldNotBeFound("headCodeName.equals=" + UPDATED_HEAD_CODE_NAME);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByHeadCodeNameIsInShouldWork() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where headCodeName in DEFAULT_HEAD_CODE_NAME or UPDATED_HEAD_CODE_NAME
        defaultAccountHeadCodeShouldBeFound("headCodeName.in=" + DEFAULT_HEAD_CODE_NAME + "," + UPDATED_HEAD_CODE_NAME);

        // Get all the accountHeadCodeList where headCodeName equals to UPDATED_HEAD_CODE_NAME
        defaultAccountHeadCodeShouldNotBeFound("headCodeName.in=" + UPDATED_HEAD_CODE_NAME);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByHeadCodeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where headCodeName is not null
        defaultAccountHeadCodeShouldBeFound("headCodeName.specified=true");

        // Get all the accountHeadCodeList where headCodeName is null
        defaultAccountHeadCodeShouldNotBeFound("headCodeName.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByHeadCodeNameContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where headCodeName contains DEFAULT_HEAD_CODE_NAME
        defaultAccountHeadCodeShouldBeFound("headCodeName.contains=" + DEFAULT_HEAD_CODE_NAME);

        // Get all the accountHeadCodeList where headCodeName contains UPDATED_HEAD_CODE_NAME
        defaultAccountHeadCodeShouldNotBeFound("headCodeName.contains=" + UPDATED_HEAD_CODE_NAME);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByHeadCodeNameNotContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where headCodeName does not contain DEFAULT_HEAD_CODE_NAME
        defaultAccountHeadCodeShouldNotBeFound("headCodeName.doesNotContain=" + DEFAULT_HEAD_CODE_NAME);

        // Get all the accountHeadCodeList where headCodeName does not contain UPDATED_HEAD_CODE_NAME
        defaultAccountHeadCodeShouldBeFound("headCodeName.doesNotContain=" + UPDATED_HEAD_CODE_NAME);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAccountHeadCodeShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the accountHeadCodeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAccountHeadCodeShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAccountHeadCodeShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the accountHeadCodeList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAccountHeadCodeShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where lastModified is not null
        defaultAccountHeadCodeShouldBeFound("lastModified.specified=true");

        // Get all the accountHeadCodeList where lastModified is null
        defaultAccountHeadCodeShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAccountHeadCodeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the accountHeadCodeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAccountHeadCodeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAccountHeadCodeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the accountHeadCodeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAccountHeadCodeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where lastModifiedBy is not null
        defaultAccountHeadCodeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the accountHeadCodeList where lastModifiedBy is null
        defaultAccountHeadCodeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAccountHeadCodeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the accountHeadCodeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAccountHeadCodeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAccountHeadCodeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the accountHeadCodeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAccountHeadCodeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where createdBy equals to DEFAULT_CREATED_BY
        defaultAccountHeadCodeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the accountHeadCodeList where createdBy equals to UPDATED_CREATED_BY
        defaultAccountHeadCodeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultAccountHeadCodeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the accountHeadCodeList where createdBy equals to UPDATED_CREATED_BY
        defaultAccountHeadCodeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where createdBy is not null
        defaultAccountHeadCodeShouldBeFound("createdBy.specified=true");

        // Get all the accountHeadCodeList where createdBy is null
        defaultAccountHeadCodeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where createdBy contains DEFAULT_CREATED_BY
        defaultAccountHeadCodeShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the accountHeadCodeList where createdBy contains UPDATED_CREATED_BY
        defaultAccountHeadCodeShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where createdBy does not contain DEFAULT_CREATED_BY
        defaultAccountHeadCodeShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the accountHeadCodeList where createdBy does not contain UPDATED_CREATED_BY
        defaultAccountHeadCodeShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where createdOn equals to DEFAULT_CREATED_ON
        defaultAccountHeadCodeShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the accountHeadCodeList where createdOn equals to UPDATED_CREATED_ON
        defaultAccountHeadCodeShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultAccountHeadCodeShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the accountHeadCodeList where createdOn equals to UPDATED_CREATED_ON
        defaultAccountHeadCodeShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where createdOn is not null
        defaultAccountHeadCodeShouldBeFound("createdOn.specified=true");

        // Get all the accountHeadCodeList where createdOn is null
        defaultAccountHeadCodeShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where isDeleted equals to DEFAULT_IS_DELETED
        defaultAccountHeadCodeShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the accountHeadCodeList where isDeleted equals to UPDATED_IS_DELETED
        defaultAccountHeadCodeShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultAccountHeadCodeShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the accountHeadCodeList where isDeleted equals to UPDATED_IS_DELETED
        defaultAccountHeadCodeShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where isDeleted is not null
        defaultAccountHeadCodeShouldBeFound("isDeleted.specified=true");

        // Get all the accountHeadCodeList where isDeleted is null
        defaultAccountHeadCodeShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultAccountHeadCodeShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the accountHeadCodeList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultAccountHeadCodeShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultAccountHeadCodeShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the accountHeadCodeList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultAccountHeadCodeShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField1 is not null
        defaultAccountHeadCodeShouldBeFound("freeField1.specified=true");

        // Get all the accountHeadCodeList where freeField1 is null
        defaultAccountHeadCodeShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultAccountHeadCodeShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the accountHeadCodeList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultAccountHeadCodeShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultAccountHeadCodeShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the accountHeadCodeList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultAccountHeadCodeShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultAccountHeadCodeShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the accountHeadCodeList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultAccountHeadCodeShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultAccountHeadCodeShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the accountHeadCodeList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultAccountHeadCodeShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField2 is not null
        defaultAccountHeadCodeShouldBeFound("freeField2.specified=true");

        // Get all the accountHeadCodeList where freeField2 is null
        defaultAccountHeadCodeShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultAccountHeadCodeShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the accountHeadCodeList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultAccountHeadCodeShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultAccountHeadCodeShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the accountHeadCodeList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultAccountHeadCodeShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultAccountHeadCodeShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the accountHeadCodeList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultAccountHeadCodeShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultAccountHeadCodeShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the accountHeadCodeList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultAccountHeadCodeShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField3 is not null
        defaultAccountHeadCodeShouldBeFound("freeField3.specified=true");

        // Get all the accountHeadCodeList where freeField3 is null
        defaultAccountHeadCodeShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultAccountHeadCodeShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the accountHeadCodeList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultAccountHeadCodeShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultAccountHeadCodeShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the accountHeadCodeList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultAccountHeadCodeShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultAccountHeadCodeShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the accountHeadCodeList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultAccountHeadCodeShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultAccountHeadCodeShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the accountHeadCodeList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultAccountHeadCodeShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField4 is not null
        defaultAccountHeadCodeShouldBeFound("freeField4.specified=true");

        // Get all the accountHeadCodeList where freeField4 is null
        defaultAccountHeadCodeShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultAccountHeadCodeShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the accountHeadCodeList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultAccountHeadCodeShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultAccountHeadCodeShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the accountHeadCodeList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultAccountHeadCodeShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultAccountHeadCodeShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the accountHeadCodeList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultAccountHeadCodeShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultAccountHeadCodeShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the accountHeadCodeList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultAccountHeadCodeShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField5 is not null
        defaultAccountHeadCodeShouldBeFound("freeField5.specified=true");

        // Get all the accountHeadCodeList where freeField5 is null
        defaultAccountHeadCodeShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultAccountHeadCodeShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the accountHeadCodeList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultAccountHeadCodeShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        // Get all the accountHeadCodeList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultAccountHeadCodeShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the accountHeadCodeList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultAccountHeadCodeShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllAccountHeadCodesByLedgerAccountsIsEqualToSomething() throws Exception {
        LedgerAccounts ledgerAccounts;
        if (TestUtil.findAll(em, LedgerAccounts.class).isEmpty()) {
            accountHeadCodeRepository.saveAndFlush(accountHeadCode);
            ledgerAccounts = LedgerAccountsResourceIT.createEntity(em);
        } else {
            ledgerAccounts = TestUtil.findAll(em, LedgerAccounts.class).get(0);
        }
        em.persist(ledgerAccounts);
        em.flush();
        accountHeadCode.setLedgerAccounts(ledgerAccounts);
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);
        Long ledgerAccountsId = ledgerAccounts.getId();

        // Get all the accountHeadCodeList where ledgerAccounts equals to ledgerAccountsId
        defaultAccountHeadCodeShouldBeFound("ledgerAccountsId.equals=" + ledgerAccountsId);

        // Get all the accountHeadCodeList where ledgerAccounts equals to (ledgerAccountsId + 1)
        defaultAccountHeadCodeShouldNotBeFound("ledgerAccountsId.equals=" + (ledgerAccountsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccountHeadCodeShouldBeFound(String filter) throws Exception {
        restAccountHeadCodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountHeadCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].headCodeName").value(hasItem(DEFAULT_HEAD_CODE_NAME)))
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
        restAccountHeadCodeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccountHeadCodeShouldNotBeFound(String filter) throws Exception {
        restAccountHeadCodeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccountHeadCodeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAccountHeadCode() throws Exception {
        // Get the accountHeadCode
        restAccountHeadCodeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAccountHeadCode() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        int databaseSizeBeforeUpdate = accountHeadCodeRepository.findAll().size();

        // Update the accountHeadCode
        AccountHeadCode updatedAccountHeadCode = accountHeadCodeRepository.findById(accountHeadCode.getId()).get();
        // Disconnect from session so that the updates on updatedAccountHeadCode are not directly saved in db
        em.detach(updatedAccountHeadCode);
        updatedAccountHeadCode
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .headCodeName(UPDATED_HEAD_CODE_NAME)
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
        AccountHeadCodeDTO accountHeadCodeDTO = accountHeadCodeMapper.toDto(updatedAccountHeadCode);

        restAccountHeadCodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountHeadCodeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountHeadCodeDTO))
            )
            .andExpect(status().isOk());

        // Validate the AccountHeadCode in the database
        List<AccountHeadCode> accountHeadCodeList = accountHeadCodeRepository.findAll();
        assertThat(accountHeadCodeList).hasSize(databaseSizeBeforeUpdate);
        AccountHeadCode testAccountHeadCode = accountHeadCodeList.get(accountHeadCodeList.size() - 1);
        assertThat(testAccountHeadCode.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAccountHeadCode.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAccountHeadCode.getHeadCodeName()).isEqualTo(UPDATED_HEAD_CODE_NAME);
        assertThat(testAccountHeadCode.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAccountHeadCode.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAccountHeadCode.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAccountHeadCode.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAccountHeadCode.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testAccountHeadCode.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testAccountHeadCode.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testAccountHeadCode.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testAccountHeadCode.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testAccountHeadCode.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void putNonExistingAccountHeadCode() throws Exception {
        int databaseSizeBeforeUpdate = accountHeadCodeRepository.findAll().size();
        accountHeadCode.setId(count.incrementAndGet());

        // Create the AccountHeadCode
        AccountHeadCodeDTO accountHeadCodeDTO = accountHeadCodeMapper.toDto(accountHeadCode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountHeadCodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountHeadCodeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountHeadCodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountHeadCode in the database
        List<AccountHeadCode> accountHeadCodeList = accountHeadCodeRepository.findAll();
        assertThat(accountHeadCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccountHeadCode() throws Exception {
        int databaseSizeBeforeUpdate = accountHeadCodeRepository.findAll().size();
        accountHeadCode.setId(count.incrementAndGet());

        // Create the AccountHeadCode
        AccountHeadCodeDTO accountHeadCodeDTO = accountHeadCodeMapper.toDto(accountHeadCode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountHeadCodeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountHeadCodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountHeadCode in the database
        List<AccountHeadCode> accountHeadCodeList = accountHeadCodeRepository.findAll();
        assertThat(accountHeadCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccountHeadCode() throws Exception {
        int databaseSizeBeforeUpdate = accountHeadCodeRepository.findAll().size();
        accountHeadCode.setId(count.incrementAndGet());

        // Create the AccountHeadCode
        AccountHeadCodeDTO accountHeadCodeDTO = accountHeadCodeMapper.toDto(accountHeadCode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountHeadCodeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountHeadCodeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountHeadCode in the database
        List<AccountHeadCode> accountHeadCodeList = accountHeadCodeRepository.findAll();
        assertThat(accountHeadCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccountHeadCodeWithPatch() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        int databaseSizeBeforeUpdate = accountHeadCodeRepository.findAll().size();

        // Update the accountHeadCode using partial update
        AccountHeadCode partialUpdatedAccountHeadCode = new AccountHeadCode();
        partialUpdatedAccountHeadCode.setId(accountHeadCode.getId());

        partialUpdatedAccountHeadCode
            .value(UPDATED_VALUE)
            .headCodeName(UPDATED_HEAD_CODE_NAME)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField3(UPDATED_FREE_FIELD_3);

        restAccountHeadCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountHeadCode.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountHeadCode))
            )
            .andExpect(status().isOk());

        // Validate the AccountHeadCode in the database
        List<AccountHeadCode> accountHeadCodeList = accountHeadCodeRepository.findAll();
        assertThat(accountHeadCodeList).hasSize(databaseSizeBeforeUpdate);
        AccountHeadCode testAccountHeadCode = accountHeadCodeList.get(accountHeadCodeList.size() - 1);
        assertThat(testAccountHeadCode.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAccountHeadCode.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAccountHeadCode.getHeadCodeName()).isEqualTo(UPDATED_HEAD_CODE_NAME);
        assertThat(testAccountHeadCode.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAccountHeadCode.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAccountHeadCode.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAccountHeadCode.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAccountHeadCode.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testAccountHeadCode.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testAccountHeadCode.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testAccountHeadCode.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testAccountHeadCode.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testAccountHeadCode.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void fullUpdateAccountHeadCodeWithPatch() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        int databaseSizeBeforeUpdate = accountHeadCodeRepository.findAll().size();

        // Update the accountHeadCode using partial update
        AccountHeadCode partialUpdatedAccountHeadCode = new AccountHeadCode();
        partialUpdatedAccountHeadCode.setId(accountHeadCode.getId());

        partialUpdatedAccountHeadCode
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .headCodeName(UPDATED_HEAD_CODE_NAME)
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

        restAccountHeadCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountHeadCode.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountHeadCode))
            )
            .andExpect(status().isOk());

        // Validate the AccountHeadCode in the database
        List<AccountHeadCode> accountHeadCodeList = accountHeadCodeRepository.findAll();
        assertThat(accountHeadCodeList).hasSize(databaseSizeBeforeUpdate);
        AccountHeadCode testAccountHeadCode = accountHeadCodeList.get(accountHeadCodeList.size() - 1);
        assertThat(testAccountHeadCode.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAccountHeadCode.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAccountHeadCode.getHeadCodeName()).isEqualTo(UPDATED_HEAD_CODE_NAME);
        assertThat(testAccountHeadCode.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAccountHeadCode.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAccountHeadCode.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAccountHeadCode.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAccountHeadCode.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testAccountHeadCode.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testAccountHeadCode.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testAccountHeadCode.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testAccountHeadCode.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testAccountHeadCode.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void patchNonExistingAccountHeadCode() throws Exception {
        int databaseSizeBeforeUpdate = accountHeadCodeRepository.findAll().size();
        accountHeadCode.setId(count.incrementAndGet());

        // Create the AccountHeadCode
        AccountHeadCodeDTO accountHeadCodeDTO = accountHeadCodeMapper.toDto(accountHeadCode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountHeadCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accountHeadCodeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountHeadCodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountHeadCode in the database
        List<AccountHeadCode> accountHeadCodeList = accountHeadCodeRepository.findAll();
        assertThat(accountHeadCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccountHeadCode() throws Exception {
        int databaseSizeBeforeUpdate = accountHeadCodeRepository.findAll().size();
        accountHeadCode.setId(count.incrementAndGet());

        // Create the AccountHeadCode
        AccountHeadCodeDTO accountHeadCodeDTO = accountHeadCodeMapper.toDto(accountHeadCode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountHeadCodeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountHeadCodeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountHeadCode in the database
        List<AccountHeadCode> accountHeadCodeList = accountHeadCodeRepository.findAll();
        assertThat(accountHeadCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccountHeadCode() throws Exception {
        int databaseSizeBeforeUpdate = accountHeadCodeRepository.findAll().size();
        accountHeadCode.setId(count.incrementAndGet());

        // Create the AccountHeadCode
        AccountHeadCodeDTO accountHeadCodeDTO = accountHeadCodeMapper.toDto(accountHeadCode);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountHeadCodeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountHeadCodeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountHeadCode in the database
        List<AccountHeadCode> accountHeadCodeList = accountHeadCodeRepository.findAll();
        assertThat(accountHeadCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccountHeadCode() throws Exception {
        // Initialize the database
        accountHeadCodeRepository.saveAndFlush(accountHeadCode);

        int databaseSizeBeforeDelete = accountHeadCodeRepository.findAll().size();

        // Delete the accountHeadCode
        restAccountHeadCodeMockMvc
            .perform(delete(ENTITY_API_URL_ID, accountHeadCode.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountHeadCode> accountHeadCodeList = accountHeadCodeRepository.findAll();
        assertThat(accountHeadCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
