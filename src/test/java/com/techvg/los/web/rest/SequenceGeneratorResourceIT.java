package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.Branch;
import com.techvg.los.domain.SequenceGenerator;
import com.techvg.los.repository.SequenceGeneratorRepository;
import com.techvg.los.service.criteria.SequenceGeneratorCriteria;
import com.techvg.los.service.dto.SequenceGeneratorDTO;
import com.techvg.los.service.mapper.SequenceGeneratorMapper;
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
 * Integration tests for the {@link SequenceGeneratorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SequenceGeneratorResourceIT {

    private static final Long DEFAULT_NEXT_VAL_MEMBER = 1L;
    private static final Long UPDATED_NEXT_VAL_MEMBER = 2L;
    private static final Long SMALLER_NEXT_VAL_MEMBER = 1L - 1L;

    private static final Long DEFAULT_NEXT_VAL_LOAN_APP = 1L;
    private static final Long UPDATED_NEXT_VAL_LOAN_APP = 2L;
    private static final Long SMALLER_NEXT_VAL_LOAN_APP = 1L - 1L;

    private static final Long DEFAULT_NEXT_VAL_LOAN_ACCOUNT = 1L;
    private static final Long UPDATED_NEXT_VAL_LOAN_ACCOUNT = 2L;
    private static final Long SMALLER_NEXT_VAL_LOAN_ACCOUNT = 1L - 1L;

    private static final Long DEFAULT_NEXT_VAL_VOUCHER = 1L;
    private static final Long UPDATED_NEXT_VAL_VOUCHER = 2L;
    private static final Long SMALLER_NEXT_VAL_VOUCHER = 1L - 1L;

    private static final Long DEFAULT_FREE_FIELD_1 = 1L;
    private static final Long UPDATED_FREE_FIELD_1 = 2L;
    private static final Long SMALLER_FREE_FIELD_1 = 1L - 1L;

    private static final Long DEFAULT_FREE_FIELD_2 = 1L;
    private static final Long UPDATED_FREE_FIELD_2 = 2L;
    private static final Long SMALLER_FREE_FIELD_2 = 1L - 1L;

    private static final Long DEFAULT_FREE_FIELD_3 = 1L;
    private static final Long UPDATED_FREE_FIELD_3 = 2L;
    private static final Long SMALLER_FREE_FIELD_3 = 1L - 1L;

    private static final Long DEFAULT_FREE_FIELD_4 = 1L;
    private static final Long UPDATED_FREE_FIELD_4 = 2L;
    private static final Long SMALLER_FREE_FIELD_4 = 1L - 1L;

    private static final Long DEFAULT_FREE_FIELD_5 = 1L;
    private static final Long UPDATED_FREE_FIELD_5 = 2L;
    private static final Long SMALLER_FREE_FIELD_5 = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/sequence-generators";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SequenceGeneratorRepository sequenceGeneratorRepository;

    @Autowired
    private SequenceGeneratorMapper sequenceGeneratorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSequenceGeneratorMockMvc;

    private SequenceGenerator sequenceGenerator;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenceGenerator createEntity(EntityManager em) {
        SequenceGenerator sequenceGenerator = new SequenceGenerator()
            .nextValMember(DEFAULT_NEXT_VAL_MEMBER)
            .nextValLoanApp(DEFAULT_NEXT_VAL_LOAN_APP)
            .nextValLoanAccount(DEFAULT_NEXT_VAL_LOAN_ACCOUNT)
            .nextValVoucher(DEFAULT_NEXT_VAL_VOUCHER)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5);
        return sequenceGenerator;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SequenceGenerator createUpdatedEntity(EntityManager em) {
        SequenceGenerator sequenceGenerator = new SequenceGenerator()
            .nextValMember(UPDATED_NEXT_VAL_MEMBER)
            .nextValLoanApp(UPDATED_NEXT_VAL_LOAN_APP)
            .nextValLoanAccount(UPDATED_NEXT_VAL_LOAN_ACCOUNT)
            .nextValVoucher(UPDATED_NEXT_VAL_VOUCHER)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);
        return sequenceGenerator;
    }

    @BeforeEach
    public void initTest() {
        sequenceGenerator = createEntity(em);
    }

    @Test
    @Transactional
    void createSequenceGenerator() throws Exception {
        int databaseSizeBeforeCreate = sequenceGeneratorRepository.findAll().size();
        // Create the SequenceGenerator
        SequenceGeneratorDTO sequenceGeneratorDTO = sequenceGeneratorMapper.toDto(sequenceGenerator);
        restSequenceGeneratorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenceGeneratorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SequenceGenerator in the database
        List<SequenceGenerator> sequenceGeneratorList = sequenceGeneratorRepository.findAll();
        assertThat(sequenceGeneratorList).hasSize(databaseSizeBeforeCreate + 1);
        SequenceGenerator testSequenceGenerator = sequenceGeneratorList.get(sequenceGeneratorList.size() - 1);
        assertThat(testSequenceGenerator.getNextValMember()).isEqualTo(DEFAULT_NEXT_VAL_MEMBER);
        assertThat(testSequenceGenerator.getNextValLoanApp()).isEqualTo(DEFAULT_NEXT_VAL_LOAN_APP);
        assertThat(testSequenceGenerator.getNextValLoanAccount()).isEqualTo(DEFAULT_NEXT_VAL_LOAN_ACCOUNT);
        assertThat(testSequenceGenerator.getNextValVoucher()).isEqualTo(DEFAULT_NEXT_VAL_VOUCHER);
        assertThat(testSequenceGenerator.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testSequenceGenerator.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testSequenceGenerator.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testSequenceGenerator.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testSequenceGenerator.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void createSequenceGeneratorWithExistingId() throws Exception {
        // Create the SequenceGenerator with an existing ID
        sequenceGenerator.setId(1L);
        SequenceGeneratorDTO sequenceGeneratorDTO = sequenceGeneratorMapper.toDto(sequenceGenerator);

        int databaseSizeBeforeCreate = sequenceGeneratorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSequenceGeneratorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenceGeneratorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenceGenerator in the database
        List<SequenceGenerator> sequenceGeneratorList = sequenceGeneratorRepository.findAll();
        assertThat(sequenceGeneratorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSequenceGenerators() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList
        restSequenceGeneratorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenceGenerator.getId().intValue())))
            .andExpect(jsonPath("$.[*].nextValMember").value(hasItem(DEFAULT_NEXT_VAL_MEMBER.intValue())))
            .andExpect(jsonPath("$.[*].nextValLoanApp").value(hasItem(DEFAULT_NEXT_VAL_LOAN_APP.intValue())))
            .andExpect(jsonPath("$.[*].nextValLoanAccount").value(hasItem(DEFAULT_NEXT_VAL_LOAN_ACCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].nextValVoucher").value(hasItem(DEFAULT_NEXT_VAL_VOUCHER.intValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1.intValue())))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2.intValue())))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3.intValue())))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4.intValue())))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5.intValue())));
    }

    @Test
    @Transactional
    void getSequenceGenerator() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get the sequenceGenerator
        restSequenceGeneratorMockMvc
            .perform(get(ENTITY_API_URL_ID, sequenceGenerator.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sequenceGenerator.getId().intValue()))
            .andExpect(jsonPath("$.nextValMember").value(DEFAULT_NEXT_VAL_MEMBER.intValue()))
            .andExpect(jsonPath("$.nextValLoanApp").value(DEFAULT_NEXT_VAL_LOAN_APP.intValue()))
            .andExpect(jsonPath("$.nextValLoanAccount").value(DEFAULT_NEXT_VAL_LOAN_ACCOUNT.intValue()))
            .andExpect(jsonPath("$.nextValVoucher").value(DEFAULT_NEXT_VAL_VOUCHER.intValue()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1.intValue()))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2.intValue()))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3.intValue()))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4.intValue()))
            .andExpect(jsonPath("$.freeField5").value(DEFAULT_FREE_FIELD_5.intValue()));
    }

    @Test
    @Transactional
    void getSequenceGeneratorsByIdFiltering() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        Long id = sequenceGenerator.getId();

        defaultSequenceGeneratorShouldBeFound("id.equals=" + id);
        defaultSequenceGeneratorShouldNotBeFound("id.notEquals=" + id);

        defaultSequenceGeneratorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSequenceGeneratorShouldNotBeFound("id.greaterThan=" + id);

        defaultSequenceGeneratorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSequenceGeneratorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValMemberIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValMember equals to DEFAULT_NEXT_VAL_MEMBER
        defaultSequenceGeneratorShouldBeFound("nextValMember.equals=" + DEFAULT_NEXT_VAL_MEMBER);

        // Get all the sequenceGeneratorList where nextValMember equals to UPDATED_NEXT_VAL_MEMBER
        defaultSequenceGeneratorShouldNotBeFound("nextValMember.equals=" + UPDATED_NEXT_VAL_MEMBER);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValMemberIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValMember in DEFAULT_NEXT_VAL_MEMBER or UPDATED_NEXT_VAL_MEMBER
        defaultSequenceGeneratorShouldBeFound("nextValMember.in=" + DEFAULT_NEXT_VAL_MEMBER + "," + UPDATED_NEXT_VAL_MEMBER);

        // Get all the sequenceGeneratorList where nextValMember equals to UPDATED_NEXT_VAL_MEMBER
        defaultSequenceGeneratorShouldNotBeFound("nextValMember.in=" + UPDATED_NEXT_VAL_MEMBER);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValMemberIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValMember is not null
        defaultSequenceGeneratorShouldBeFound("nextValMember.specified=true");

        // Get all the sequenceGeneratorList where nextValMember is null
        defaultSequenceGeneratorShouldNotBeFound("nextValMember.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValMemberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValMember is greater than or equal to DEFAULT_NEXT_VAL_MEMBER
        defaultSequenceGeneratorShouldBeFound("nextValMember.greaterThanOrEqual=" + DEFAULT_NEXT_VAL_MEMBER);

        // Get all the sequenceGeneratorList where nextValMember is greater than or equal to UPDATED_NEXT_VAL_MEMBER
        defaultSequenceGeneratorShouldNotBeFound("nextValMember.greaterThanOrEqual=" + UPDATED_NEXT_VAL_MEMBER);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValMemberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValMember is less than or equal to DEFAULT_NEXT_VAL_MEMBER
        defaultSequenceGeneratorShouldBeFound("nextValMember.lessThanOrEqual=" + DEFAULT_NEXT_VAL_MEMBER);

        // Get all the sequenceGeneratorList where nextValMember is less than or equal to SMALLER_NEXT_VAL_MEMBER
        defaultSequenceGeneratorShouldNotBeFound("nextValMember.lessThanOrEqual=" + SMALLER_NEXT_VAL_MEMBER);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValMemberIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValMember is less than DEFAULT_NEXT_VAL_MEMBER
        defaultSequenceGeneratorShouldNotBeFound("nextValMember.lessThan=" + DEFAULT_NEXT_VAL_MEMBER);

        // Get all the sequenceGeneratorList where nextValMember is less than UPDATED_NEXT_VAL_MEMBER
        defaultSequenceGeneratorShouldBeFound("nextValMember.lessThan=" + UPDATED_NEXT_VAL_MEMBER);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValMemberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValMember is greater than DEFAULT_NEXT_VAL_MEMBER
        defaultSequenceGeneratorShouldNotBeFound("nextValMember.greaterThan=" + DEFAULT_NEXT_VAL_MEMBER);

        // Get all the sequenceGeneratorList where nextValMember is greater than SMALLER_NEXT_VAL_MEMBER
        defaultSequenceGeneratorShouldBeFound("nextValMember.greaterThan=" + SMALLER_NEXT_VAL_MEMBER);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValLoanAppIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValLoanApp equals to DEFAULT_NEXT_VAL_LOAN_APP
        defaultSequenceGeneratorShouldBeFound("nextValLoanApp.equals=" + DEFAULT_NEXT_VAL_LOAN_APP);

        // Get all the sequenceGeneratorList where nextValLoanApp equals to UPDATED_NEXT_VAL_LOAN_APP
        defaultSequenceGeneratorShouldNotBeFound("nextValLoanApp.equals=" + UPDATED_NEXT_VAL_LOAN_APP);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValLoanAppIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValLoanApp in DEFAULT_NEXT_VAL_LOAN_APP or UPDATED_NEXT_VAL_LOAN_APP
        defaultSequenceGeneratorShouldBeFound("nextValLoanApp.in=" + DEFAULT_NEXT_VAL_LOAN_APP + "," + UPDATED_NEXT_VAL_LOAN_APP);

        // Get all the sequenceGeneratorList where nextValLoanApp equals to UPDATED_NEXT_VAL_LOAN_APP
        defaultSequenceGeneratorShouldNotBeFound("nextValLoanApp.in=" + UPDATED_NEXT_VAL_LOAN_APP);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValLoanAppIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValLoanApp is not null
        defaultSequenceGeneratorShouldBeFound("nextValLoanApp.specified=true");

        // Get all the sequenceGeneratorList where nextValLoanApp is null
        defaultSequenceGeneratorShouldNotBeFound("nextValLoanApp.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValLoanAppIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValLoanApp is greater than or equal to DEFAULT_NEXT_VAL_LOAN_APP
        defaultSequenceGeneratorShouldBeFound("nextValLoanApp.greaterThanOrEqual=" + DEFAULT_NEXT_VAL_LOAN_APP);

        // Get all the sequenceGeneratorList where nextValLoanApp is greater than or equal to UPDATED_NEXT_VAL_LOAN_APP
        defaultSequenceGeneratorShouldNotBeFound("nextValLoanApp.greaterThanOrEqual=" + UPDATED_NEXT_VAL_LOAN_APP);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValLoanAppIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValLoanApp is less than or equal to DEFAULT_NEXT_VAL_LOAN_APP
        defaultSequenceGeneratorShouldBeFound("nextValLoanApp.lessThanOrEqual=" + DEFAULT_NEXT_VAL_LOAN_APP);

        // Get all the sequenceGeneratorList where nextValLoanApp is less than or equal to SMALLER_NEXT_VAL_LOAN_APP
        defaultSequenceGeneratorShouldNotBeFound("nextValLoanApp.lessThanOrEqual=" + SMALLER_NEXT_VAL_LOAN_APP);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValLoanAppIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValLoanApp is less than DEFAULT_NEXT_VAL_LOAN_APP
        defaultSequenceGeneratorShouldNotBeFound("nextValLoanApp.lessThan=" + DEFAULT_NEXT_VAL_LOAN_APP);

        // Get all the sequenceGeneratorList where nextValLoanApp is less than UPDATED_NEXT_VAL_LOAN_APP
        defaultSequenceGeneratorShouldBeFound("nextValLoanApp.lessThan=" + UPDATED_NEXT_VAL_LOAN_APP);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValLoanAppIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValLoanApp is greater than DEFAULT_NEXT_VAL_LOAN_APP
        defaultSequenceGeneratorShouldNotBeFound("nextValLoanApp.greaterThan=" + DEFAULT_NEXT_VAL_LOAN_APP);

        // Get all the sequenceGeneratorList where nextValLoanApp is greater than SMALLER_NEXT_VAL_LOAN_APP
        defaultSequenceGeneratorShouldBeFound("nextValLoanApp.greaterThan=" + SMALLER_NEXT_VAL_LOAN_APP);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValLoanAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValLoanAccount equals to DEFAULT_NEXT_VAL_LOAN_ACCOUNT
        defaultSequenceGeneratorShouldBeFound("nextValLoanAccount.equals=" + DEFAULT_NEXT_VAL_LOAN_ACCOUNT);

        // Get all the sequenceGeneratorList where nextValLoanAccount equals to UPDATED_NEXT_VAL_LOAN_ACCOUNT
        defaultSequenceGeneratorShouldNotBeFound("nextValLoanAccount.equals=" + UPDATED_NEXT_VAL_LOAN_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValLoanAccountIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValLoanAccount in DEFAULT_NEXT_VAL_LOAN_ACCOUNT or UPDATED_NEXT_VAL_LOAN_ACCOUNT
        defaultSequenceGeneratorShouldBeFound(
            "nextValLoanAccount.in=" + DEFAULT_NEXT_VAL_LOAN_ACCOUNT + "," + UPDATED_NEXT_VAL_LOAN_ACCOUNT
        );

        // Get all the sequenceGeneratorList where nextValLoanAccount equals to UPDATED_NEXT_VAL_LOAN_ACCOUNT
        defaultSequenceGeneratorShouldNotBeFound("nextValLoanAccount.in=" + UPDATED_NEXT_VAL_LOAN_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValLoanAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValLoanAccount is not null
        defaultSequenceGeneratorShouldBeFound("nextValLoanAccount.specified=true");

        // Get all the sequenceGeneratorList where nextValLoanAccount is null
        defaultSequenceGeneratorShouldNotBeFound("nextValLoanAccount.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValLoanAccountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValLoanAccount is greater than or equal to DEFAULT_NEXT_VAL_LOAN_ACCOUNT
        defaultSequenceGeneratorShouldBeFound("nextValLoanAccount.greaterThanOrEqual=" + DEFAULT_NEXT_VAL_LOAN_ACCOUNT);

        // Get all the sequenceGeneratorList where nextValLoanAccount is greater than or equal to UPDATED_NEXT_VAL_LOAN_ACCOUNT
        defaultSequenceGeneratorShouldNotBeFound("nextValLoanAccount.greaterThanOrEqual=" + UPDATED_NEXT_VAL_LOAN_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValLoanAccountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValLoanAccount is less than or equal to DEFAULT_NEXT_VAL_LOAN_ACCOUNT
        defaultSequenceGeneratorShouldBeFound("nextValLoanAccount.lessThanOrEqual=" + DEFAULT_NEXT_VAL_LOAN_ACCOUNT);

        // Get all the sequenceGeneratorList where nextValLoanAccount is less than or equal to SMALLER_NEXT_VAL_LOAN_ACCOUNT
        defaultSequenceGeneratorShouldNotBeFound("nextValLoanAccount.lessThanOrEqual=" + SMALLER_NEXT_VAL_LOAN_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValLoanAccountIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValLoanAccount is less than DEFAULT_NEXT_VAL_LOAN_ACCOUNT
        defaultSequenceGeneratorShouldNotBeFound("nextValLoanAccount.lessThan=" + DEFAULT_NEXT_VAL_LOAN_ACCOUNT);

        // Get all the sequenceGeneratorList where nextValLoanAccount is less than UPDATED_NEXT_VAL_LOAN_ACCOUNT
        defaultSequenceGeneratorShouldBeFound("nextValLoanAccount.lessThan=" + UPDATED_NEXT_VAL_LOAN_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValLoanAccountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValLoanAccount is greater than DEFAULT_NEXT_VAL_LOAN_ACCOUNT
        defaultSequenceGeneratorShouldNotBeFound("nextValLoanAccount.greaterThan=" + DEFAULT_NEXT_VAL_LOAN_ACCOUNT);

        // Get all the sequenceGeneratorList where nextValLoanAccount is greater than SMALLER_NEXT_VAL_LOAN_ACCOUNT
        defaultSequenceGeneratorShouldBeFound("nextValLoanAccount.greaterThan=" + SMALLER_NEXT_VAL_LOAN_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValVoucherIsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValVoucher equals to DEFAULT_NEXT_VAL_VOUCHER
        defaultSequenceGeneratorShouldBeFound("nextValVoucher.equals=" + DEFAULT_NEXT_VAL_VOUCHER);

        // Get all the sequenceGeneratorList where nextValVoucher equals to UPDATED_NEXT_VAL_VOUCHER
        defaultSequenceGeneratorShouldNotBeFound("nextValVoucher.equals=" + UPDATED_NEXT_VAL_VOUCHER);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValVoucherIsInShouldWork() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValVoucher in DEFAULT_NEXT_VAL_VOUCHER or UPDATED_NEXT_VAL_VOUCHER
        defaultSequenceGeneratorShouldBeFound("nextValVoucher.in=" + DEFAULT_NEXT_VAL_VOUCHER + "," + UPDATED_NEXT_VAL_VOUCHER);

        // Get all the sequenceGeneratorList where nextValVoucher equals to UPDATED_NEXT_VAL_VOUCHER
        defaultSequenceGeneratorShouldNotBeFound("nextValVoucher.in=" + UPDATED_NEXT_VAL_VOUCHER);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValVoucherIsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValVoucher is not null
        defaultSequenceGeneratorShouldBeFound("nextValVoucher.specified=true");

        // Get all the sequenceGeneratorList where nextValVoucher is null
        defaultSequenceGeneratorShouldNotBeFound("nextValVoucher.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValVoucherIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValVoucher is greater than or equal to DEFAULT_NEXT_VAL_VOUCHER
        defaultSequenceGeneratorShouldBeFound("nextValVoucher.greaterThanOrEqual=" + DEFAULT_NEXT_VAL_VOUCHER);

        // Get all the sequenceGeneratorList where nextValVoucher is greater than or equal to UPDATED_NEXT_VAL_VOUCHER
        defaultSequenceGeneratorShouldNotBeFound("nextValVoucher.greaterThanOrEqual=" + UPDATED_NEXT_VAL_VOUCHER);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValVoucherIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValVoucher is less than or equal to DEFAULT_NEXT_VAL_VOUCHER
        defaultSequenceGeneratorShouldBeFound("nextValVoucher.lessThanOrEqual=" + DEFAULT_NEXT_VAL_VOUCHER);

        // Get all the sequenceGeneratorList where nextValVoucher is less than or equal to SMALLER_NEXT_VAL_VOUCHER
        defaultSequenceGeneratorShouldNotBeFound("nextValVoucher.lessThanOrEqual=" + SMALLER_NEXT_VAL_VOUCHER);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValVoucherIsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValVoucher is less than DEFAULT_NEXT_VAL_VOUCHER
        defaultSequenceGeneratorShouldNotBeFound("nextValVoucher.lessThan=" + DEFAULT_NEXT_VAL_VOUCHER);

        // Get all the sequenceGeneratorList where nextValVoucher is less than UPDATED_NEXT_VAL_VOUCHER
        defaultSequenceGeneratorShouldBeFound("nextValVoucher.lessThan=" + UPDATED_NEXT_VAL_VOUCHER);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByNextValVoucherIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where nextValVoucher is greater than DEFAULT_NEXT_VAL_VOUCHER
        defaultSequenceGeneratorShouldNotBeFound("nextValVoucher.greaterThan=" + DEFAULT_NEXT_VAL_VOUCHER);

        // Get all the sequenceGeneratorList where nextValVoucher is greater than SMALLER_NEXT_VAL_VOUCHER
        defaultSequenceGeneratorShouldBeFound("nextValVoucher.greaterThan=" + SMALLER_NEXT_VAL_VOUCHER);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultSequenceGeneratorShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the sequenceGeneratorList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSequenceGeneratorShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultSequenceGeneratorShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the sequenceGeneratorList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultSequenceGeneratorShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField1 is not null
        defaultSequenceGeneratorShouldBeFound("freeField1.specified=true");

        // Get all the sequenceGeneratorList where freeField1 is null
        defaultSequenceGeneratorShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField1IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField1 is greater than or equal to DEFAULT_FREE_FIELD_1
        defaultSequenceGeneratorShouldBeFound("freeField1.greaterThanOrEqual=" + DEFAULT_FREE_FIELD_1);

        // Get all the sequenceGeneratorList where freeField1 is greater than or equal to UPDATED_FREE_FIELD_1
        defaultSequenceGeneratorShouldNotBeFound("freeField1.greaterThanOrEqual=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField1IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField1 is less than or equal to DEFAULT_FREE_FIELD_1
        defaultSequenceGeneratorShouldBeFound("freeField1.lessThanOrEqual=" + DEFAULT_FREE_FIELD_1);

        // Get all the sequenceGeneratorList where freeField1 is less than or equal to SMALLER_FREE_FIELD_1
        defaultSequenceGeneratorShouldNotBeFound("freeField1.lessThanOrEqual=" + SMALLER_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField1IsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField1 is less than DEFAULT_FREE_FIELD_1
        defaultSequenceGeneratorShouldNotBeFound("freeField1.lessThan=" + DEFAULT_FREE_FIELD_1);

        // Get all the sequenceGeneratorList where freeField1 is less than UPDATED_FREE_FIELD_1
        defaultSequenceGeneratorShouldBeFound("freeField1.lessThan=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField1IsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField1 is greater than DEFAULT_FREE_FIELD_1
        defaultSequenceGeneratorShouldNotBeFound("freeField1.greaterThan=" + DEFAULT_FREE_FIELD_1);

        // Get all the sequenceGeneratorList where freeField1 is greater than SMALLER_FREE_FIELD_1
        defaultSequenceGeneratorShouldBeFound("freeField1.greaterThan=" + SMALLER_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultSequenceGeneratorShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the sequenceGeneratorList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSequenceGeneratorShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultSequenceGeneratorShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the sequenceGeneratorList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultSequenceGeneratorShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField2 is not null
        defaultSequenceGeneratorShouldBeFound("freeField2.specified=true");

        // Get all the sequenceGeneratorList where freeField2 is null
        defaultSequenceGeneratorShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField2 is greater than or equal to DEFAULT_FREE_FIELD_2
        defaultSequenceGeneratorShouldBeFound("freeField2.greaterThanOrEqual=" + DEFAULT_FREE_FIELD_2);

        // Get all the sequenceGeneratorList where freeField2 is greater than or equal to UPDATED_FREE_FIELD_2
        defaultSequenceGeneratorShouldNotBeFound("freeField2.greaterThanOrEqual=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField2 is less than or equal to DEFAULT_FREE_FIELD_2
        defaultSequenceGeneratorShouldBeFound("freeField2.lessThanOrEqual=" + DEFAULT_FREE_FIELD_2);

        // Get all the sequenceGeneratorList where freeField2 is less than or equal to SMALLER_FREE_FIELD_2
        defaultSequenceGeneratorShouldNotBeFound("freeField2.lessThanOrEqual=" + SMALLER_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField2IsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField2 is less than DEFAULT_FREE_FIELD_2
        defaultSequenceGeneratorShouldNotBeFound("freeField2.lessThan=" + DEFAULT_FREE_FIELD_2);

        // Get all the sequenceGeneratorList where freeField2 is less than UPDATED_FREE_FIELD_2
        defaultSequenceGeneratorShouldBeFound("freeField2.lessThan=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField2 is greater than DEFAULT_FREE_FIELD_2
        defaultSequenceGeneratorShouldNotBeFound("freeField2.greaterThan=" + DEFAULT_FREE_FIELD_2);

        // Get all the sequenceGeneratorList where freeField2 is greater than SMALLER_FREE_FIELD_2
        defaultSequenceGeneratorShouldBeFound("freeField2.greaterThan=" + SMALLER_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultSequenceGeneratorShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the sequenceGeneratorList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSequenceGeneratorShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultSequenceGeneratorShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the sequenceGeneratorList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultSequenceGeneratorShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField3 is not null
        defaultSequenceGeneratorShouldBeFound("freeField3.specified=true");

        // Get all the sequenceGeneratorList where freeField3 is null
        defaultSequenceGeneratorShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField3IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField3 is greater than or equal to DEFAULT_FREE_FIELD_3
        defaultSequenceGeneratorShouldBeFound("freeField3.greaterThanOrEqual=" + DEFAULT_FREE_FIELD_3);

        // Get all the sequenceGeneratorList where freeField3 is greater than or equal to UPDATED_FREE_FIELD_3
        defaultSequenceGeneratorShouldNotBeFound("freeField3.greaterThanOrEqual=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField3IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField3 is less than or equal to DEFAULT_FREE_FIELD_3
        defaultSequenceGeneratorShouldBeFound("freeField3.lessThanOrEqual=" + DEFAULT_FREE_FIELD_3);

        // Get all the sequenceGeneratorList where freeField3 is less than or equal to SMALLER_FREE_FIELD_3
        defaultSequenceGeneratorShouldNotBeFound("freeField3.lessThanOrEqual=" + SMALLER_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField3IsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField3 is less than DEFAULT_FREE_FIELD_3
        defaultSequenceGeneratorShouldNotBeFound("freeField3.lessThan=" + DEFAULT_FREE_FIELD_3);

        // Get all the sequenceGeneratorList where freeField3 is less than UPDATED_FREE_FIELD_3
        defaultSequenceGeneratorShouldBeFound("freeField3.lessThan=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField3IsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField3 is greater than DEFAULT_FREE_FIELD_3
        defaultSequenceGeneratorShouldNotBeFound("freeField3.greaterThan=" + DEFAULT_FREE_FIELD_3);

        // Get all the sequenceGeneratorList where freeField3 is greater than SMALLER_FREE_FIELD_3
        defaultSequenceGeneratorShouldBeFound("freeField3.greaterThan=" + SMALLER_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultSequenceGeneratorShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the sequenceGeneratorList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultSequenceGeneratorShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultSequenceGeneratorShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the sequenceGeneratorList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultSequenceGeneratorShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField4 is not null
        defaultSequenceGeneratorShouldBeFound("freeField4.specified=true");

        // Get all the sequenceGeneratorList where freeField4 is null
        defaultSequenceGeneratorShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField4IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField4 is greater than or equal to DEFAULT_FREE_FIELD_4
        defaultSequenceGeneratorShouldBeFound("freeField4.greaterThanOrEqual=" + DEFAULT_FREE_FIELD_4);

        // Get all the sequenceGeneratorList where freeField4 is greater than or equal to UPDATED_FREE_FIELD_4
        defaultSequenceGeneratorShouldNotBeFound("freeField4.greaterThanOrEqual=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField4IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField4 is less than or equal to DEFAULT_FREE_FIELD_4
        defaultSequenceGeneratorShouldBeFound("freeField4.lessThanOrEqual=" + DEFAULT_FREE_FIELD_4);

        // Get all the sequenceGeneratorList where freeField4 is less than or equal to SMALLER_FREE_FIELD_4
        defaultSequenceGeneratorShouldNotBeFound("freeField4.lessThanOrEqual=" + SMALLER_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField4IsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField4 is less than DEFAULT_FREE_FIELD_4
        defaultSequenceGeneratorShouldNotBeFound("freeField4.lessThan=" + DEFAULT_FREE_FIELD_4);

        // Get all the sequenceGeneratorList where freeField4 is less than UPDATED_FREE_FIELD_4
        defaultSequenceGeneratorShouldBeFound("freeField4.lessThan=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField4IsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField4 is greater than DEFAULT_FREE_FIELD_4
        defaultSequenceGeneratorShouldNotBeFound("freeField4.greaterThan=" + DEFAULT_FREE_FIELD_4);

        // Get all the sequenceGeneratorList where freeField4 is greater than SMALLER_FREE_FIELD_4
        defaultSequenceGeneratorShouldBeFound("freeField4.greaterThan=" + SMALLER_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultSequenceGeneratorShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the sequenceGeneratorList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultSequenceGeneratorShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultSequenceGeneratorShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the sequenceGeneratorList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultSequenceGeneratorShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField5 is not null
        defaultSequenceGeneratorShouldBeFound("freeField5.specified=true");

        // Get all the sequenceGeneratorList where freeField5 is null
        defaultSequenceGeneratorShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField5IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField5 is greater than or equal to DEFAULT_FREE_FIELD_5
        defaultSequenceGeneratorShouldBeFound("freeField5.greaterThanOrEqual=" + DEFAULT_FREE_FIELD_5);

        // Get all the sequenceGeneratorList where freeField5 is greater than or equal to UPDATED_FREE_FIELD_5
        defaultSequenceGeneratorShouldNotBeFound("freeField5.greaterThanOrEqual=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField5IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField5 is less than or equal to DEFAULT_FREE_FIELD_5
        defaultSequenceGeneratorShouldBeFound("freeField5.lessThanOrEqual=" + DEFAULT_FREE_FIELD_5);

        // Get all the sequenceGeneratorList where freeField5 is less than or equal to SMALLER_FREE_FIELD_5
        defaultSequenceGeneratorShouldNotBeFound("freeField5.lessThanOrEqual=" + SMALLER_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField5IsLessThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField5 is less than DEFAULT_FREE_FIELD_5
        defaultSequenceGeneratorShouldNotBeFound("freeField5.lessThan=" + DEFAULT_FREE_FIELD_5);

        // Get all the sequenceGeneratorList where freeField5 is less than UPDATED_FREE_FIELD_5
        defaultSequenceGeneratorShouldBeFound("freeField5.lessThan=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByFreeField5IsGreaterThanSomething() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        // Get all the sequenceGeneratorList where freeField5 is greater than DEFAULT_FREE_FIELD_5
        defaultSequenceGeneratorShouldNotBeFound("freeField5.greaterThan=" + DEFAULT_FREE_FIELD_5);

        // Get all the sequenceGeneratorList where freeField5 is greater than SMALLER_FREE_FIELD_5
        defaultSequenceGeneratorShouldBeFound("freeField5.greaterThan=" + SMALLER_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllSequenceGeneratorsByBranchIsEqualToSomething() throws Exception {
        Branch branch;
        if (TestUtil.findAll(em, Branch.class).isEmpty()) {
            sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);
            branch = BranchResourceIT.createEntity(em);
        } else {
            branch = TestUtil.findAll(em, Branch.class).get(0);
        }
        em.persist(branch);
        em.flush();
        sequenceGenerator.setBranch(branch);
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);
        Long branchId = branch.getId();

        // Get all the sequenceGeneratorList where branch equals to branchId
        defaultSequenceGeneratorShouldBeFound("branchId.equals=" + branchId);

        // Get all the sequenceGeneratorList where branch equals to (branchId + 1)
        defaultSequenceGeneratorShouldNotBeFound("branchId.equals=" + (branchId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSequenceGeneratorShouldBeFound(String filter) throws Exception {
        restSequenceGeneratorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sequenceGenerator.getId().intValue())))
            .andExpect(jsonPath("$.[*].nextValMember").value(hasItem(DEFAULT_NEXT_VAL_MEMBER.intValue())))
            .andExpect(jsonPath("$.[*].nextValLoanApp").value(hasItem(DEFAULT_NEXT_VAL_LOAN_APP.intValue())))
            .andExpect(jsonPath("$.[*].nextValLoanAccount").value(hasItem(DEFAULT_NEXT_VAL_LOAN_ACCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].nextValVoucher").value(hasItem(DEFAULT_NEXT_VAL_VOUCHER.intValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1.intValue())))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2.intValue())))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3.intValue())))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4.intValue())))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5.intValue())));

        // Check, that the count call also returns 1
        restSequenceGeneratorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSequenceGeneratorShouldNotBeFound(String filter) throws Exception {
        restSequenceGeneratorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSequenceGeneratorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSequenceGenerator() throws Exception {
        // Get the sequenceGenerator
        restSequenceGeneratorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSequenceGenerator() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        int databaseSizeBeforeUpdate = sequenceGeneratorRepository.findAll().size();

        // Update the sequenceGenerator
        SequenceGenerator updatedSequenceGenerator = sequenceGeneratorRepository.findById(sequenceGenerator.getId()).get();
        // Disconnect from session so that the updates on updatedSequenceGenerator are not directly saved in db
        em.detach(updatedSequenceGenerator);
        updatedSequenceGenerator
            .nextValMember(UPDATED_NEXT_VAL_MEMBER)
            .nextValLoanApp(UPDATED_NEXT_VAL_LOAN_APP)
            .nextValLoanAccount(UPDATED_NEXT_VAL_LOAN_ACCOUNT)
            .nextValVoucher(UPDATED_NEXT_VAL_VOUCHER)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);
        SequenceGeneratorDTO sequenceGeneratorDTO = sequenceGeneratorMapper.toDto(updatedSequenceGenerator);

        restSequenceGeneratorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sequenceGeneratorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenceGeneratorDTO))
            )
            .andExpect(status().isOk());

        // Validate the SequenceGenerator in the database
        List<SequenceGenerator> sequenceGeneratorList = sequenceGeneratorRepository.findAll();
        assertThat(sequenceGeneratorList).hasSize(databaseSizeBeforeUpdate);
        SequenceGenerator testSequenceGenerator = sequenceGeneratorList.get(sequenceGeneratorList.size() - 1);
        assertThat(testSequenceGenerator.getNextValMember()).isEqualTo(UPDATED_NEXT_VAL_MEMBER);
        assertThat(testSequenceGenerator.getNextValLoanApp()).isEqualTo(UPDATED_NEXT_VAL_LOAN_APP);
        assertThat(testSequenceGenerator.getNextValLoanAccount()).isEqualTo(UPDATED_NEXT_VAL_LOAN_ACCOUNT);
        assertThat(testSequenceGenerator.getNextValVoucher()).isEqualTo(UPDATED_NEXT_VAL_VOUCHER);
        assertThat(testSequenceGenerator.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSequenceGenerator.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSequenceGenerator.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testSequenceGenerator.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testSequenceGenerator.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void putNonExistingSequenceGenerator() throws Exception {
        int databaseSizeBeforeUpdate = sequenceGeneratorRepository.findAll().size();
        sequenceGenerator.setId(count.incrementAndGet());

        // Create the SequenceGenerator
        SequenceGeneratorDTO sequenceGeneratorDTO = sequenceGeneratorMapper.toDto(sequenceGenerator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequenceGeneratorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sequenceGeneratorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenceGeneratorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenceGenerator in the database
        List<SequenceGenerator> sequenceGeneratorList = sequenceGeneratorRepository.findAll();
        assertThat(sequenceGeneratorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSequenceGenerator() throws Exception {
        int databaseSizeBeforeUpdate = sequenceGeneratorRepository.findAll().size();
        sequenceGenerator.setId(count.incrementAndGet());

        // Create the SequenceGenerator
        SequenceGeneratorDTO sequenceGeneratorDTO = sequenceGeneratorMapper.toDto(sequenceGenerator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceGeneratorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sequenceGeneratorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenceGenerator in the database
        List<SequenceGenerator> sequenceGeneratorList = sequenceGeneratorRepository.findAll();
        assertThat(sequenceGeneratorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSequenceGenerator() throws Exception {
        int databaseSizeBeforeUpdate = sequenceGeneratorRepository.findAll().size();
        sequenceGenerator.setId(count.incrementAndGet());

        // Create the SequenceGenerator
        SequenceGeneratorDTO sequenceGeneratorDTO = sequenceGeneratorMapper.toDto(sequenceGenerator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceGeneratorMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sequenceGeneratorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SequenceGenerator in the database
        List<SequenceGenerator> sequenceGeneratorList = sequenceGeneratorRepository.findAll();
        assertThat(sequenceGeneratorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSequenceGeneratorWithPatch() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        int databaseSizeBeforeUpdate = sequenceGeneratorRepository.findAll().size();

        // Update the sequenceGenerator using partial update
        SequenceGenerator partialUpdatedSequenceGenerator = new SequenceGenerator();
        partialUpdatedSequenceGenerator.setId(sequenceGenerator.getId());

        partialUpdatedSequenceGenerator.freeField1(UPDATED_FREE_FIELD_1).freeField2(UPDATED_FREE_FIELD_2).freeField3(UPDATED_FREE_FIELD_3);

        restSequenceGeneratorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSequenceGenerator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSequenceGenerator))
            )
            .andExpect(status().isOk());

        // Validate the SequenceGenerator in the database
        List<SequenceGenerator> sequenceGeneratorList = sequenceGeneratorRepository.findAll();
        assertThat(sequenceGeneratorList).hasSize(databaseSizeBeforeUpdate);
        SequenceGenerator testSequenceGenerator = sequenceGeneratorList.get(sequenceGeneratorList.size() - 1);
        assertThat(testSequenceGenerator.getNextValMember()).isEqualTo(DEFAULT_NEXT_VAL_MEMBER);
        assertThat(testSequenceGenerator.getNextValLoanApp()).isEqualTo(DEFAULT_NEXT_VAL_LOAN_APP);
        assertThat(testSequenceGenerator.getNextValLoanAccount()).isEqualTo(DEFAULT_NEXT_VAL_LOAN_ACCOUNT);
        assertThat(testSequenceGenerator.getNextValVoucher()).isEqualTo(DEFAULT_NEXT_VAL_VOUCHER);
        assertThat(testSequenceGenerator.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSequenceGenerator.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSequenceGenerator.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testSequenceGenerator.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testSequenceGenerator.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void fullUpdateSequenceGeneratorWithPatch() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        int databaseSizeBeforeUpdate = sequenceGeneratorRepository.findAll().size();

        // Update the sequenceGenerator using partial update
        SequenceGenerator partialUpdatedSequenceGenerator = new SequenceGenerator();
        partialUpdatedSequenceGenerator.setId(sequenceGenerator.getId());

        partialUpdatedSequenceGenerator
            .nextValMember(UPDATED_NEXT_VAL_MEMBER)
            .nextValLoanApp(UPDATED_NEXT_VAL_LOAN_APP)
            .nextValLoanAccount(UPDATED_NEXT_VAL_LOAN_ACCOUNT)
            .nextValVoucher(UPDATED_NEXT_VAL_VOUCHER)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);

        restSequenceGeneratorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSequenceGenerator.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSequenceGenerator))
            )
            .andExpect(status().isOk());

        // Validate the SequenceGenerator in the database
        List<SequenceGenerator> sequenceGeneratorList = sequenceGeneratorRepository.findAll();
        assertThat(sequenceGeneratorList).hasSize(databaseSizeBeforeUpdate);
        SequenceGenerator testSequenceGenerator = sequenceGeneratorList.get(sequenceGeneratorList.size() - 1);
        assertThat(testSequenceGenerator.getNextValMember()).isEqualTo(UPDATED_NEXT_VAL_MEMBER);
        assertThat(testSequenceGenerator.getNextValLoanApp()).isEqualTo(UPDATED_NEXT_VAL_LOAN_APP);
        assertThat(testSequenceGenerator.getNextValLoanAccount()).isEqualTo(UPDATED_NEXT_VAL_LOAN_ACCOUNT);
        assertThat(testSequenceGenerator.getNextValVoucher()).isEqualTo(UPDATED_NEXT_VAL_VOUCHER);
        assertThat(testSequenceGenerator.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testSequenceGenerator.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testSequenceGenerator.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testSequenceGenerator.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testSequenceGenerator.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void patchNonExistingSequenceGenerator() throws Exception {
        int databaseSizeBeforeUpdate = sequenceGeneratorRepository.findAll().size();
        sequenceGenerator.setId(count.incrementAndGet());

        // Create the SequenceGenerator
        SequenceGeneratorDTO sequenceGeneratorDTO = sequenceGeneratorMapper.toDto(sequenceGenerator);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSequenceGeneratorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sequenceGeneratorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequenceGeneratorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenceGenerator in the database
        List<SequenceGenerator> sequenceGeneratorList = sequenceGeneratorRepository.findAll();
        assertThat(sequenceGeneratorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSequenceGenerator() throws Exception {
        int databaseSizeBeforeUpdate = sequenceGeneratorRepository.findAll().size();
        sequenceGenerator.setId(count.incrementAndGet());

        // Create the SequenceGenerator
        SequenceGeneratorDTO sequenceGeneratorDTO = sequenceGeneratorMapper.toDto(sequenceGenerator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceGeneratorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequenceGeneratorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SequenceGenerator in the database
        List<SequenceGenerator> sequenceGeneratorList = sequenceGeneratorRepository.findAll();
        assertThat(sequenceGeneratorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSequenceGenerator() throws Exception {
        int databaseSizeBeforeUpdate = sequenceGeneratorRepository.findAll().size();
        sequenceGenerator.setId(count.incrementAndGet());

        // Create the SequenceGenerator
        SequenceGeneratorDTO sequenceGeneratorDTO = sequenceGeneratorMapper.toDto(sequenceGenerator);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSequenceGeneratorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sequenceGeneratorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SequenceGenerator in the database
        List<SequenceGenerator> sequenceGeneratorList = sequenceGeneratorRepository.findAll();
        assertThat(sequenceGeneratorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSequenceGenerator() throws Exception {
        // Initialize the database
        sequenceGeneratorRepository.saveAndFlush(sequenceGenerator);

        int databaseSizeBeforeDelete = sequenceGeneratorRepository.findAll().size();

        // Delete the sequenceGenerator
        restSequenceGeneratorMockMvc
            .perform(delete(ENTITY_API_URL_ID, sequenceGenerator.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SequenceGenerator> sequenceGeneratorList = sequenceGeneratorRepository.findAll();
        assertThat(sequenceGeneratorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
