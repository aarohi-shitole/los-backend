package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.Member;
import com.techvg.los.domain.MemberBank;
import com.techvg.los.repository.MemberBankRepository;
import com.techvg.los.service.criteria.MemberBankCriteria;
import com.techvg.los.service.dto.MemberBankDTO;
import com.techvg.los.service.mapper.MemberBankMapper;
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
 * Integration tests for the {@link MemberBankResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberBankResourceIT {

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ACCOUNT_NUMBER = 1L;
    private static final Long UPDATED_ACCOUNT_NUMBER = 2L;
    private static final Long SMALLER_ACCOUNT_NUMBER = 1L - 1L;

    private static final String DEFAULT_ACC_HOLDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACC_HOLDER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IFSCCODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSCCODE = "BBBBBBBBBB";

    private static final String DEFAULT_MICR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MICR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SWIFT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SWIFT_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

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

    private static final String DEFAULT_FREE_FIELD_6 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_6 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/member-banks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberBankRepository memberBankRepository;

    @Autowired
    private MemberBankMapper memberBankMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberBankMockMvc;

    private MemberBank memberBank;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberBank createEntity(EntityManager em) {
        MemberBank memberBank = new MemberBank()
            .bankName(DEFAULT_BANK_NAME)
            .branchName(DEFAULT_BRANCH_NAME)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .accHolderName(DEFAULT_ACC_HOLDER_NAME)
            .ifsccode(DEFAULT_IFSCCODE)
            .micrCode(DEFAULT_MICR_CODE)
            .swiftCode(DEFAULT_SWIFT_CODE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isActive(DEFAULT_IS_ACTIVE)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5)
            .freeField6(DEFAULT_FREE_FIELD_6);
        return memberBank;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberBank createUpdatedEntity(EntityManager em) {
        MemberBank memberBank = new MemberBank()
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accHolderName(UPDATED_ACC_HOLDER_NAME)
            .ifsccode(UPDATED_IFSCCODE)
            .micrCode(UPDATED_MICR_CODE)
            .swiftCode(UPDATED_SWIFT_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isActive(UPDATED_IS_ACTIVE)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        return memberBank;
    }

    @BeforeEach
    public void initTest() {
        memberBank = createEntity(em);
    }

    @Test
    @Transactional
    void createMemberBank() throws Exception {
        int databaseSizeBeforeCreate = memberBankRepository.findAll().size();
        // Create the MemberBank
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);
        restMemberBankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberBankDTO)))
            .andExpect(status().isCreated());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeCreate + 1);
        MemberBank testMemberBank = memberBankList.get(memberBankList.size() - 1);
        assertThat(testMemberBank.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testMemberBank.getBranchName()).isEqualTo(DEFAULT_BRANCH_NAME);
        assertThat(testMemberBank.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testMemberBank.getAccHolderName()).isEqualTo(DEFAULT_ACC_HOLDER_NAME);
        assertThat(testMemberBank.getIfsccode()).isEqualTo(DEFAULT_IFSCCODE);
        assertThat(testMemberBank.getMicrCode()).isEqualTo(DEFAULT_MICR_CODE);
        assertThat(testMemberBank.getSwiftCode()).isEqualTo(DEFAULT_SWIFT_CODE);
        assertThat(testMemberBank.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMemberBank.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMemberBank.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMemberBank.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testMemberBank.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testMemberBank.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMemberBank.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testMemberBank.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testMemberBank.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testMemberBank.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testMemberBank.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testMemberBank.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createMemberBankWithExistingId() throws Exception {
        // Create the MemberBank with an existing ID
        memberBank.setId(1L);
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);

        int databaseSizeBeforeCreate = memberBankRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberBankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberBankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMemberBanks() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList
        restMemberBankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].accHolderName").value(hasItem(DEFAULT_ACC_HOLDER_NAME)))
            .andExpect(jsonPath("$.[*].ifsccode").value(hasItem(DEFAULT_IFSCCODE)))
            .andExpect(jsonPath("$.[*].micrCode").value(hasItem(DEFAULT_MICR_CODE)))
            .andExpect(jsonPath("$.[*].swiftCode").value(hasItem(DEFAULT_SWIFT_CODE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));
    }

    @Test
    @Transactional
    void getMemberBank() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get the memberBank
        restMemberBankMockMvc
            .perform(get(ENTITY_API_URL_ID, memberBank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(memberBank.getId().intValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.intValue()))
            .andExpect(jsonPath("$.accHolderName").value(DEFAULT_ACC_HOLDER_NAME))
            .andExpect(jsonPath("$.ifsccode").value(DEFAULT_IFSCCODE))
            .andExpect(jsonPath("$.micrCode").value(DEFAULT_MICR_CODE))
            .andExpect(jsonPath("$.swiftCode").value(DEFAULT_SWIFT_CODE))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.freeField5").value(DEFAULT_FREE_FIELD_5))
            .andExpect(jsonPath("$.freeField6").value(DEFAULT_FREE_FIELD_6));
    }

    @Test
    @Transactional
    void getMemberBanksByIdFiltering() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        Long id = memberBank.getId();

        defaultMemberBankShouldBeFound("id.equals=" + id);
        defaultMemberBankShouldNotBeFound("id.notEquals=" + id);

        defaultMemberBankShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMemberBankShouldNotBeFound("id.greaterThan=" + id);

        defaultMemberBankShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMemberBankShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where bankName equals to DEFAULT_BANK_NAME
        defaultMemberBankShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the memberBankList where bankName equals to UPDATED_BANK_NAME
        defaultMemberBankShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultMemberBankShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the memberBankList where bankName equals to UPDATED_BANK_NAME
        defaultMemberBankShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where bankName is not null
        defaultMemberBankShouldBeFound("bankName.specified=true");

        // Get all the memberBankList where bankName is null
        defaultMemberBankShouldNotBeFound("bankName.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByBankNameContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where bankName contains DEFAULT_BANK_NAME
        defaultMemberBankShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the memberBankList where bankName contains UPDATED_BANK_NAME
        defaultMemberBankShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where bankName does not contain DEFAULT_BANK_NAME
        defaultMemberBankShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the memberBankList where bankName does not contain UPDATED_BANK_NAME
        defaultMemberBankShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBranchNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where branchName equals to DEFAULT_BRANCH_NAME
        defaultMemberBankShouldBeFound("branchName.equals=" + DEFAULT_BRANCH_NAME);

        // Get all the memberBankList where branchName equals to UPDATED_BRANCH_NAME
        defaultMemberBankShouldNotBeFound("branchName.equals=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBranchNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where branchName in DEFAULT_BRANCH_NAME or UPDATED_BRANCH_NAME
        defaultMemberBankShouldBeFound("branchName.in=" + DEFAULT_BRANCH_NAME + "," + UPDATED_BRANCH_NAME);

        // Get all the memberBankList where branchName equals to UPDATED_BRANCH_NAME
        defaultMemberBankShouldNotBeFound("branchName.in=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBranchNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where branchName is not null
        defaultMemberBankShouldBeFound("branchName.specified=true");

        // Get all the memberBankList where branchName is null
        defaultMemberBankShouldNotBeFound("branchName.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByBranchNameContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where branchName contains DEFAULT_BRANCH_NAME
        defaultMemberBankShouldBeFound("branchName.contains=" + DEFAULT_BRANCH_NAME);

        // Get all the memberBankList where branchName contains UPDATED_BRANCH_NAME
        defaultMemberBankShouldNotBeFound("branchName.contains=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByBranchNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where branchName does not contain DEFAULT_BRANCH_NAME
        defaultMemberBankShouldNotBeFound("branchName.doesNotContain=" + DEFAULT_BRANCH_NAME);

        // Get all the memberBankList where branchName does not contain UPDATED_BRANCH_NAME
        defaultMemberBankShouldBeFound("branchName.doesNotContain=" + UPDATED_BRANCH_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultMemberBankShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the memberBankList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultMemberBankShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultMemberBankShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the memberBankList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultMemberBankShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accountNumber is not null
        defaultMemberBankShouldBeFound("accountNumber.specified=true");

        // Get all the memberBankList where accountNumber is null
        defaultMemberBankShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccountNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accountNumber is greater than or equal to DEFAULT_ACCOUNT_NUMBER
        defaultMemberBankShouldBeFound("accountNumber.greaterThanOrEqual=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the memberBankList where accountNumber is greater than or equal to UPDATED_ACCOUNT_NUMBER
        defaultMemberBankShouldNotBeFound("accountNumber.greaterThanOrEqual=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccountNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accountNumber is less than or equal to DEFAULT_ACCOUNT_NUMBER
        defaultMemberBankShouldBeFound("accountNumber.lessThanOrEqual=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the memberBankList where accountNumber is less than or equal to SMALLER_ACCOUNT_NUMBER
        defaultMemberBankShouldNotBeFound("accountNumber.lessThanOrEqual=" + SMALLER_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccountNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accountNumber is less than DEFAULT_ACCOUNT_NUMBER
        defaultMemberBankShouldNotBeFound("accountNumber.lessThan=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the memberBankList where accountNumber is less than UPDATED_ACCOUNT_NUMBER
        defaultMemberBankShouldBeFound("accountNumber.lessThan=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccountNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accountNumber is greater than DEFAULT_ACCOUNT_NUMBER
        defaultMemberBankShouldNotBeFound("accountNumber.greaterThan=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the memberBankList where accountNumber is greater than SMALLER_ACCOUNT_NUMBER
        defaultMemberBankShouldBeFound("accountNumber.greaterThan=" + SMALLER_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccHolderNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accHolderName equals to DEFAULT_ACC_HOLDER_NAME
        defaultMemberBankShouldBeFound("accHolderName.equals=" + DEFAULT_ACC_HOLDER_NAME);

        // Get all the memberBankList where accHolderName equals to UPDATED_ACC_HOLDER_NAME
        defaultMemberBankShouldNotBeFound("accHolderName.equals=" + UPDATED_ACC_HOLDER_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccHolderNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accHolderName in DEFAULT_ACC_HOLDER_NAME or UPDATED_ACC_HOLDER_NAME
        defaultMemberBankShouldBeFound("accHolderName.in=" + DEFAULT_ACC_HOLDER_NAME + "," + UPDATED_ACC_HOLDER_NAME);

        // Get all the memberBankList where accHolderName equals to UPDATED_ACC_HOLDER_NAME
        defaultMemberBankShouldNotBeFound("accHolderName.in=" + UPDATED_ACC_HOLDER_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccHolderNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accHolderName is not null
        defaultMemberBankShouldBeFound("accHolderName.specified=true");

        // Get all the memberBankList where accHolderName is null
        defaultMemberBankShouldNotBeFound("accHolderName.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccHolderNameContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accHolderName contains DEFAULT_ACC_HOLDER_NAME
        defaultMemberBankShouldBeFound("accHolderName.contains=" + DEFAULT_ACC_HOLDER_NAME);

        // Get all the memberBankList where accHolderName contains UPDATED_ACC_HOLDER_NAME
        defaultMemberBankShouldNotBeFound("accHolderName.contains=" + UPDATED_ACC_HOLDER_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByAccHolderNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where accHolderName does not contain DEFAULT_ACC_HOLDER_NAME
        defaultMemberBankShouldNotBeFound("accHolderName.doesNotContain=" + DEFAULT_ACC_HOLDER_NAME);

        // Get all the memberBankList where accHolderName does not contain UPDATED_ACC_HOLDER_NAME
        defaultMemberBankShouldBeFound("accHolderName.doesNotContain=" + UPDATED_ACC_HOLDER_NAME);
    }

    @Test
    @Transactional
    void getAllMemberBanksByIfsccodeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where ifsccode equals to DEFAULT_IFSCCODE
        defaultMemberBankShouldBeFound("ifsccode.equals=" + DEFAULT_IFSCCODE);

        // Get all the memberBankList where ifsccode equals to UPDATED_IFSCCODE
        defaultMemberBankShouldNotBeFound("ifsccode.equals=" + UPDATED_IFSCCODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksByIfsccodeIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where ifsccode in DEFAULT_IFSCCODE or UPDATED_IFSCCODE
        defaultMemberBankShouldBeFound("ifsccode.in=" + DEFAULT_IFSCCODE + "," + UPDATED_IFSCCODE);

        // Get all the memberBankList where ifsccode equals to UPDATED_IFSCCODE
        defaultMemberBankShouldNotBeFound("ifsccode.in=" + UPDATED_IFSCCODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksByIfsccodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where ifsccode is not null
        defaultMemberBankShouldBeFound("ifsccode.specified=true");

        // Get all the memberBankList where ifsccode is null
        defaultMemberBankShouldNotBeFound("ifsccode.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByIfsccodeContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where ifsccode contains DEFAULT_IFSCCODE
        defaultMemberBankShouldBeFound("ifsccode.contains=" + DEFAULT_IFSCCODE);

        // Get all the memberBankList where ifsccode contains UPDATED_IFSCCODE
        defaultMemberBankShouldNotBeFound("ifsccode.contains=" + UPDATED_IFSCCODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksByIfsccodeNotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where ifsccode does not contain DEFAULT_IFSCCODE
        defaultMemberBankShouldNotBeFound("ifsccode.doesNotContain=" + DEFAULT_IFSCCODE);

        // Get all the memberBankList where ifsccode does not contain UPDATED_IFSCCODE
        defaultMemberBankShouldBeFound("ifsccode.doesNotContain=" + UPDATED_IFSCCODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksByMicrCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where micrCode equals to DEFAULT_MICR_CODE
        defaultMemberBankShouldBeFound("micrCode.equals=" + DEFAULT_MICR_CODE);

        // Get all the memberBankList where micrCode equals to UPDATED_MICR_CODE
        defaultMemberBankShouldNotBeFound("micrCode.equals=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksByMicrCodeIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where micrCode in DEFAULT_MICR_CODE or UPDATED_MICR_CODE
        defaultMemberBankShouldBeFound("micrCode.in=" + DEFAULT_MICR_CODE + "," + UPDATED_MICR_CODE);

        // Get all the memberBankList where micrCode equals to UPDATED_MICR_CODE
        defaultMemberBankShouldNotBeFound("micrCode.in=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksByMicrCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where micrCode is not null
        defaultMemberBankShouldBeFound("micrCode.specified=true");

        // Get all the memberBankList where micrCode is null
        defaultMemberBankShouldNotBeFound("micrCode.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByMicrCodeContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where micrCode contains DEFAULT_MICR_CODE
        defaultMemberBankShouldBeFound("micrCode.contains=" + DEFAULT_MICR_CODE);

        // Get all the memberBankList where micrCode contains UPDATED_MICR_CODE
        defaultMemberBankShouldNotBeFound("micrCode.contains=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksByMicrCodeNotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where micrCode does not contain DEFAULT_MICR_CODE
        defaultMemberBankShouldNotBeFound("micrCode.doesNotContain=" + DEFAULT_MICR_CODE);

        // Get all the memberBankList where micrCode does not contain UPDATED_MICR_CODE
        defaultMemberBankShouldBeFound("micrCode.doesNotContain=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksBySwiftCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where swiftCode equals to DEFAULT_SWIFT_CODE
        defaultMemberBankShouldBeFound("swiftCode.equals=" + DEFAULT_SWIFT_CODE);

        // Get all the memberBankList where swiftCode equals to UPDATED_SWIFT_CODE
        defaultMemberBankShouldNotBeFound("swiftCode.equals=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksBySwiftCodeIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where swiftCode in DEFAULT_SWIFT_CODE or UPDATED_SWIFT_CODE
        defaultMemberBankShouldBeFound("swiftCode.in=" + DEFAULT_SWIFT_CODE + "," + UPDATED_SWIFT_CODE);

        // Get all the memberBankList where swiftCode equals to UPDATED_SWIFT_CODE
        defaultMemberBankShouldNotBeFound("swiftCode.in=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksBySwiftCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where swiftCode is not null
        defaultMemberBankShouldBeFound("swiftCode.specified=true");

        // Get all the memberBankList where swiftCode is null
        defaultMemberBankShouldNotBeFound("swiftCode.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksBySwiftCodeContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where swiftCode contains DEFAULT_SWIFT_CODE
        defaultMemberBankShouldBeFound("swiftCode.contains=" + DEFAULT_SWIFT_CODE);

        // Get all the memberBankList where swiftCode contains UPDATED_SWIFT_CODE
        defaultMemberBankShouldNotBeFound("swiftCode.contains=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksBySwiftCodeNotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where swiftCode does not contain DEFAULT_SWIFT_CODE
        defaultMemberBankShouldNotBeFound("swiftCode.doesNotContain=" + DEFAULT_SWIFT_CODE);

        // Get all the memberBankList where swiftCode does not contain UPDATED_SWIFT_CODE
        defaultMemberBankShouldBeFound("swiftCode.doesNotContain=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultMemberBankShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the memberBankList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberBankShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultMemberBankShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the memberBankList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberBankShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModified is not null
        defaultMemberBankShouldBeFound("lastModified.specified=true");

        // Get all the memberBankList where lastModified is null
        defaultMemberBankShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultMemberBankShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberBankList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberBankShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultMemberBankShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the memberBankList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberBankShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModifiedBy is not null
        defaultMemberBankShouldBeFound("lastModifiedBy.specified=true");

        // Get all the memberBankList where lastModifiedBy is null
        defaultMemberBankShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultMemberBankShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberBankList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultMemberBankShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultMemberBankShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberBankList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultMemberBankShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdBy equals to DEFAULT_CREATED_BY
        defaultMemberBankShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the memberBankList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberBankShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultMemberBankShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the memberBankList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberBankShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdBy is not null
        defaultMemberBankShouldBeFound("createdBy.specified=true");

        // Get all the memberBankList where createdBy is null
        defaultMemberBankShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdBy contains DEFAULT_CREATED_BY
        defaultMemberBankShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the memberBankList where createdBy contains UPDATED_CREATED_BY
        defaultMemberBankShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdBy does not contain DEFAULT_CREATED_BY
        defaultMemberBankShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the memberBankList where createdBy does not contain UPDATED_CREATED_BY
        defaultMemberBankShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdOn equals to DEFAULT_CREATED_ON
        defaultMemberBankShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the memberBankList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberBankShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultMemberBankShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the memberBankList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberBankShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMemberBanksByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where createdOn is not null
        defaultMemberBankShouldBeFound("createdOn.specified=true");

        // Get all the memberBankList where createdOn is null
        defaultMemberBankShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where isActive equals to DEFAULT_IS_ACTIVE
        defaultMemberBankShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the memberBankList where isActive equals to UPDATED_IS_ACTIVE
        defaultMemberBankShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllMemberBanksByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultMemberBankShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the memberBankList where isActive equals to UPDATED_IS_ACTIVE
        defaultMemberBankShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllMemberBanksByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where isActive is not null
        defaultMemberBankShouldBeFound("isActive.specified=true");

        // Get all the memberBankList where isActive is null
        defaultMemberBankShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where isDeleted equals to DEFAULT_IS_DELETED
        defaultMemberBankShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the memberBankList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberBankShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMemberBanksByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultMemberBankShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the memberBankList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberBankShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMemberBanksByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where isDeleted is not null
        defaultMemberBankShouldBeFound("isDeleted.specified=true");

        // Get all the memberBankList where isDeleted is null
        defaultMemberBankShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultMemberBankShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberBankList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultMemberBankShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultMemberBankShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the memberBankList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultMemberBankShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField1 is not null
        defaultMemberBankShouldBeFound("freeField1.specified=true");

        // Get all the memberBankList where freeField1 is null
        defaultMemberBankShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultMemberBankShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberBankList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultMemberBankShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultMemberBankShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberBankList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultMemberBankShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultMemberBankShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberBankList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultMemberBankShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultMemberBankShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the memberBankList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultMemberBankShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField2 is not null
        defaultMemberBankShouldBeFound("freeField2.specified=true");

        // Get all the memberBankList where freeField2 is null
        defaultMemberBankShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultMemberBankShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberBankList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultMemberBankShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultMemberBankShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberBankList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultMemberBankShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultMemberBankShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberBankList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultMemberBankShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultMemberBankShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the memberBankList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultMemberBankShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField3 is not null
        defaultMemberBankShouldBeFound("freeField3.specified=true");

        // Get all the memberBankList where freeField3 is null
        defaultMemberBankShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultMemberBankShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberBankList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultMemberBankShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultMemberBankShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberBankList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultMemberBankShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultMemberBankShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the memberBankList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultMemberBankShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultMemberBankShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the memberBankList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultMemberBankShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField4 is not null
        defaultMemberBankShouldBeFound("freeField4.specified=true");

        // Get all the memberBankList where freeField4 is null
        defaultMemberBankShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultMemberBankShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the memberBankList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultMemberBankShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultMemberBankShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the memberBankList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultMemberBankShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultMemberBankShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the memberBankList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultMemberBankShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultMemberBankShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the memberBankList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultMemberBankShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField5 is not null
        defaultMemberBankShouldBeFound("freeField5.specified=true");

        // Get all the memberBankList where freeField5 is null
        defaultMemberBankShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultMemberBankShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the memberBankList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultMemberBankShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultMemberBankShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the memberBankList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultMemberBankShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultMemberBankShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the memberBankList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultMemberBankShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultMemberBankShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the memberBankList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultMemberBankShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField6 is not null
        defaultMemberBankShouldBeFound("freeField6.specified=true");

        // Get all the memberBankList where freeField6 is null
        defaultMemberBankShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultMemberBankShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the memberBankList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultMemberBankShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMemberBanksByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        // Get all the memberBankList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultMemberBankShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the memberBankList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultMemberBankShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMemberBanksByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            memberBankRepository.saveAndFlush(memberBank);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        memberBank.setMember(member);
        memberBankRepository.saveAndFlush(memberBank);
        Long memberId = member.getId();

        // Get all the memberBankList where member equals to memberId
        defaultMemberBankShouldBeFound("memberId.equals=" + memberId);

        // Get all the memberBankList where member equals to (memberId + 1)
        defaultMemberBankShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMemberBankShouldBeFound(String filter) throws Exception {
        restMemberBankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberBank.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].accHolderName").value(hasItem(DEFAULT_ACC_HOLDER_NAME)))
            .andExpect(jsonPath("$.[*].ifsccode").value(hasItem(DEFAULT_IFSCCODE)))
            .andExpect(jsonPath("$.[*].micrCode").value(hasItem(DEFAULT_MICR_CODE)))
            .andExpect(jsonPath("$.[*].swiftCode").value(hasItem(DEFAULT_SWIFT_CODE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));

        // Check, that the count call also returns 1
        restMemberBankMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMemberBankShouldNotBeFound(String filter) throws Exception {
        restMemberBankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMemberBankMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMemberBank() throws Exception {
        // Get the memberBank
        restMemberBankMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMemberBank() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();

        // Update the memberBank
        MemberBank updatedMemberBank = memberBankRepository.findById(memberBank.getId()).get();
        // Disconnect from session so that the updates on updatedMemberBank are not directly saved in db
        em.detach(updatedMemberBank);
        updatedMemberBank
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accHolderName(UPDATED_ACC_HOLDER_NAME)
            .ifsccode(UPDATED_IFSCCODE)
            .micrCode(UPDATED_MICR_CODE)
            .swiftCode(UPDATED_SWIFT_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isActive(UPDATED_IS_ACTIVE)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(updatedMemberBank);

        restMemberBankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberBankDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberBankDTO))
            )
            .andExpect(status().isOk());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
        MemberBank testMemberBank = memberBankList.get(memberBankList.size() - 1);
        assertThat(testMemberBank.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testMemberBank.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testMemberBank.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testMemberBank.getAccHolderName()).isEqualTo(UPDATED_ACC_HOLDER_NAME);
        assertThat(testMemberBank.getIfsccode()).isEqualTo(UPDATED_IFSCCODE);
        assertThat(testMemberBank.getMicrCode()).isEqualTo(UPDATED_MICR_CODE);
        assertThat(testMemberBank.getSwiftCode()).isEqualTo(UPDATED_SWIFT_CODE);
        assertThat(testMemberBank.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberBank.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberBank.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMemberBank.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMemberBank.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testMemberBank.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMemberBank.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMemberBank.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMemberBank.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMemberBank.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testMemberBank.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testMemberBank.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingMemberBank() throws Exception {
        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();
        memberBank.setId(count.incrementAndGet());

        // Create the MemberBank
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberBankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberBankDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberBankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMemberBank() throws Exception {
        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();
        memberBank.setId(count.incrementAndGet());

        // Create the MemberBank
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberBankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberBankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMemberBank() throws Exception {
        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();
        memberBank.setId(count.incrementAndGet());

        // Create the MemberBank
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberBankMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberBankDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberBankWithPatch() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();

        // Update the memberBank using partial update
        MemberBank partialUpdatedMemberBank = new MemberBank();
        partialUpdatedMemberBank.setId(memberBank.getId());

        partialUpdatedMemberBank
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accHolderName(UPDATED_ACC_HOLDER_NAME)
            .ifsccode(UPDATED_IFSCCODE)
            .micrCode(UPDATED_MICR_CODE)
            .swiftCode(UPDATED_SWIFT_CODE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restMemberBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberBank.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberBank))
            )
            .andExpect(status().isOk());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
        MemberBank testMemberBank = memberBankList.get(memberBankList.size() - 1);
        assertThat(testMemberBank.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testMemberBank.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testMemberBank.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testMemberBank.getAccHolderName()).isEqualTo(UPDATED_ACC_HOLDER_NAME);
        assertThat(testMemberBank.getIfsccode()).isEqualTo(UPDATED_IFSCCODE);
        assertThat(testMemberBank.getMicrCode()).isEqualTo(UPDATED_MICR_CODE);
        assertThat(testMemberBank.getSwiftCode()).isEqualTo(UPDATED_SWIFT_CODE);
        assertThat(testMemberBank.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMemberBank.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberBank.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMemberBank.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMemberBank.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testMemberBank.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMemberBank.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testMemberBank.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMemberBank.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMemberBank.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testMemberBank.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testMemberBank.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateMemberBankWithPatch() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();

        // Update the memberBank using partial update
        MemberBank partialUpdatedMemberBank = new MemberBank();
        partialUpdatedMemberBank.setId(memberBank.getId());

        partialUpdatedMemberBank
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accHolderName(UPDATED_ACC_HOLDER_NAME)
            .ifsccode(UPDATED_IFSCCODE)
            .micrCode(UPDATED_MICR_CODE)
            .swiftCode(UPDATED_SWIFT_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isActive(UPDATED_IS_ACTIVE)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restMemberBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberBank.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberBank))
            )
            .andExpect(status().isOk());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
        MemberBank testMemberBank = memberBankList.get(memberBankList.size() - 1);
        assertThat(testMemberBank.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testMemberBank.getBranchName()).isEqualTo(UPDATED_BRANCH_NAME);
        assertThat(testMemberBank.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testMemberBank.getAccHolderName()).isEqualTo(UPDATED_ACC_HOLDER_NAME);
        assertThat(testMemberBank.getIfsccode()).isEqualTo(UPDATED_IFSCCODE);
        assertThat(testMemberBank.getMicrCode()).isEqualTo(UPDATED_MICR_CODE);
        assertThat(testMemberBank.getSwiftCode()).isEqualTo(UPDATED_SWIFT_CODE);
        assertThat(testMemberBank.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberBank.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberBank.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMemberBank.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMemberBank.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testMemberBank.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMemberBank.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMemberBank.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMemberBank.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMemberBank.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testMemberBank.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testMemberBank.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingMemberBank() throws Exception {
        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();
        memberBank.setId(count.incrementAndGet());

        // Create the MemberBank
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, memberBankDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberBankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMemberBank() throws Exception {
        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();
        memberBank.setId(count.incrementAndGet());

        // Create the MemberBank
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberBankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMemberBank() throws Exception {
        int databaseSizeBeforeUpdate = memberBankRepository.findAll().size();
        memberBank.setId(count.incrementAndGet());

        // Create the MemberBank
        MemberBankDTO memberBankDTO = memberBankMapper.toDto(memberBank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberBankMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(memberBankDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberBank in the database
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMemberBank() throws Exception {
        // Initialize the database
        memberBankRepository.saveAndFlush(memberBank);

        int databaseSizeBeforeDelete = memberBankRepository.findAll().size();

        // Delete the memberBank
        restMemberBankMockMvc
            .perform(delete(ENTITY_API_URL_ID, memberBank.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MemberBank> memberBankList = memberBankRepository.findAll();
        assertThat(memberBankList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
