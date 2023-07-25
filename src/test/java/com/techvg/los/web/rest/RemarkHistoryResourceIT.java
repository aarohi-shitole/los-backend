package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.LoanApplications;
import com.techvg.los.domain.RemarkHistory;
import com.techvg.los.domain.SecurityUser;
import com.techvg.los.repository.RemarkHistoryRepository;
import com.techvg.los.service.criteria.RemarkHistoryCriteria;
import com.techvg.los.service.dto.RemarkHistoryDTO;
import com.techvg.los.service.mapper.RemarkHistoryMapper;
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
 * Integration tests for the {@link RemarkHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RemarkHistoryResourceIT {

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final Double DEFAULT_LOAN_AMT = 1D;
    private static final Double UPDATED_LOAN_AMT = 2D;
    private static final Double SMALLER_LOAN_AMT = 1D - 1D;

    private static final Double DEFAULT_MODIFIED_AMT = 1D;
    private static final Double UPDATED_MODIFIED_AMT = 2D;
    private static final Double SMALLER_MODIFIED_AMT = 1D - 1D;

    private static final Double DEFAULT_LOAN_INTEREST = 1D;
    private static final Double UPDATED_LOAN_INTEREST = 2D;
    private static final Double SMALLER_LOAN_INTEREST = 1D - 1D;

    private static final Double DEFAULT_MODIFIED_INTEREST = 1D;
    private static final Double UPDATED_MODIFIED_INTEREST = 2D;
    private static final Double SMALLER_MODIFIED_INTEREST = 1D - 1D;

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_APPLICATION_NO = "AAAAAAAAAA";
    private static final String UPDATED_APPLICATION_NO = "BBBBBBBBBB";

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

    private static final String DEFAULT_FREE_FIELD_6 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_6 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/remark-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RemarkHistoryRepository remarkHistoryRepository;

    @Autowired
    private RemarkHistoryMapper remarkHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRemarkHistoryMockMvc;

    private RemarkHistory remarkHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RemarkHistory createEntity(EntityManager em) {
        RemarkHistory remarkHistory = new RemarkHistory()
            .remark(DEFAULT_REMARK)
            .loanAmt(DEFAULT_LOAN_AMT)
            .modifiedAmt(DEFAULT_MODIFIED_AMT)
            .loanInterest(DEFAULT_LOAN_INTEREST)
            .modifiedInterest(DEFAULT_MODIFIED_INTEREST)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .branch(DEFAULT_BRANCH)
            .applicationNo(DEFAULT_APPLICATION_NO)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5);
        //   .freeField6(DEFAULT_FREE_FIELD_6);
        return remarkHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RemarkHistory createUpdatedEntity(EntityManager em) {
        RemarkHistory remarkHistory = new RemarkHistory()
            .remark(UPDATED_REMARK)
            .loanAmt(UPDATED_LOAN_AMT)
            .modifiedAmt(UPDATED_MODIFIED_AMT)
            .loanInterest(UPDATED_LOAN_INTEREST)
            .modifiedInterest(UPDATED_MODIFIED_INTEREST)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .branch(UPDATED_BRANCH)
            .applicationNo(UPDATED_APPLICATION_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);
        //  .freeField6(UPDATED_FREE_FIELD_6);
        return remarkHistory;
    }

    @BeforeEach
    public void initTest() {
        remarkHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createRemarkHistory() throws Exception {
        int databaseSizeBeforeCreate = remarkHistoryRepository.findAll().size();
        // Create the RemarkHistory
        RemarkHistoryDTO remarkHistoryDTO = remarkHistoryMapper.toDto(remarkHistory);
        restRemarkHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remarkHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RemarkHistory in the database
        List<RemarkHistory> remarkHistoryList = remarkHistoryRepository.findAll();
        assertThat(remarkHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        RemarkHistory testRemarkHistory = remarkHistoryList.get(remarkHistoryList.size() - 1);
        assertThat(testRemarkHistory.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testRemarkHistory.getLoanAmt()).isEqualTo(DEFAULT_LOAN_AMT);
        assertThat(testRemarkHistory.getModifiedAmt()).isEqualTo(DEFAULT_MODIFIED_AMT);
        assertThat(testRemarkHistory.getLoanInterest()).isEqualTo(DEFAULT_LOAN_INTEREST);
        assertThat(testRemarkHistory.getModifiedInterest()).isEqualTo(DEFAULT_MODIFIED_INTEREST);
        assertThat(testRemarkHistory.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testRemarkHistory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRemarkHistory.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testRemarkHistory.getApplicationNo()).isEqualTo(DEFAULT_APPLICATION_NO);
        assertThat(testRemarkHistory.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testRemarkHistory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testRemarkHistory.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testRemarkHistory.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testRemarkHistory.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testRemarkHistory.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testRemarkHistory.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        //  assertThat(testRemarkHistory.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createRemarkHistoryWithExistingId() throws Exception {
        // Create the RemarkHistory with an existing ID
        remarkHistory.setId(1L);
        RemarkHistoryDTO remarkHistoryDTO = remarkHistoryMapper.toDto(remarkHistory);

        int databaseSizeBeforeCreate = remarkHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRemarkHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remarkHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemarkHistory in the database
        List<RemarkHistory> remarkHistoryList = remarkHistoryRepository.findAll();
        assertThat(remarkHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRemarkHistories() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList
        restRemarkHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remarkHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].loanAmt").value(hasItem(DEFAULT_LOAN_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].modifiedAmt").value(hasItem(DEFAULT_MODIFIED_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanInterest").value(hasItem(DEFAULT_LOAN_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].modifiedInterest").value(hasItem(DEFAULT_MODIFIED_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].applicationNo").value(hasItem(DEFAULT_APPLICATION_NO)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));
    }

    @Test
    @Transactional
    void getRemarkHistory() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get the remarkHistory
        restRemarkHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, remarkHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(remarkHistory.getId().intValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.loanAmt").value(DEFAULT_LOAN_AMT.doubleValue()))
            .andExpect(jsonPath("$.modifiedAmt").value(DEFAULT_MODIFIED_AMT.doubleValue()))
            .andExpect(jsonPath("$.loanInterest").value(DEFAULT_LOAN_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.modifiedInterest").value(DEFAULT_MODIFIED_INTEREST.doubleValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH))
            .andExpect(jsonPath("$.applicationNo").value(DEFAULT_APPLICATION_NO))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.freeField5").value(DEFAULT_FREE_FIELD_5))
            .andExpect(jsonPath("$.freeField6").value(DEFAULT_FREE_FIELD_6));
    }

    @Test
    @Transactional
    void getRemarkHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        Long id = remarkHistory.getId();

        defaultRemarkHistoryShouldBeFound("id.equals=" + id);
        defaultRemarkHistoryShouldNotBeFound("id.notEquals=" + id);

        defaultRemarkHistoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRemarkHistoryShouldNotBeFound("id.greaterThan=" + id);

        defaultRemarkHistoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRemarkHistoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where remark equals to DEFAULT_REMARK
        defaultRemarkHistoryShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the remarkHistoryList where remark equals to UPDATED_REMARK
        defaultRemarkHistoryShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultRemarkHistoryShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the remarkHistoryList where remark equals to UPDATED_REMARK
        defaultRemarkHistoryShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where remark is not null
        defaultRemarkHistoryShouldBeFound("remark.specified=true");

        // Get all the remarkHistoryList where remark is null
        defaultRemarkHistoryShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByRemarkContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where remark contains DEFAULT_REMARK
        defaultRemarkHistoryShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the remarkHistoryList where remark contains UPDATED_REMARK
        defaultRemarkHistoryShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where remark does not contain DEFAULT_REMARK
        defaultRemarkHistoryShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the remarkHistoryList where remark does not contain UPDATED_REMARK
        defaultRemarkHistoryShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLoanAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where loanAmt equals to DEFAULT_LOAN_AMT
        defaultRemarkHistoryShouldBeFound("loanAmt.equals=" + DEFAULT_LOAN_AMT);

        // Get all the remarkHistoryList where loanAmt equals to UPDATED_LOAN_AMT
        defaultRemarkHistoryShouldNotBeFound("loanAmt.equals=" + UPDATED_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLoanAmtIsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where loanAmt in DEFAULT_LOAN_AMT or UPDATED_LOAN_AMT
        defaultRemarkHistoryShouldBeFound("loanAmt.in=" + DEFAULT_LOAN_AMT + "," + UPDATED_LOAN_AMT);

        // Get all the remarkHistoryList where loanAmt equals to UPDATED_LOAN_AMT
        defaultRemarkHistoryShouldNotBeFound("loanAmt.in=" + UPDATED_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLoanAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where loanAmt is not null
        defaultRemarkHistoryShouldBeFound("loanAmt.specified=true");

        // Get all the remarkHistoryList where loanAmt is null
        defaultRemarkHistoryShouldNotBeFound("loanAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLoanAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where loanAmt is greater than or equal to DEFAULT_LOAN_AMT
        defaultRemarkHistoryShouldBeFound("loanAmt.greaterThanOrEqual=" + DEFAULT_LOAN_AMT);

        // Get all the remarkHistoryList where loanAmt is greater than or equal to UPDATED_LOAN_AMT
        defaultRemarkHistoryShouldNotBeFound("loanAmt.greaterThanOrEqual=" + UPDATED_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLoanAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where loanAmt is less than or equal to DEFAULT_LOAN_AMT
        defaultRemarkHistoryShouldBeFound("loanAmt.lessThanOrEqual=" + DEFAULT_LOAN_AMT);

        // Get all the remarkHistoryList where loanAmt is less than or equal to SMALLER_LOAN_AMT
        defaultRemarkHistoryShouldNotBeFound("loanAmt.lessThanOrEqual=" + SMALLER_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLoanAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where loanAmt is less than DEFAULT_LOAN_AMT
        defaultRemarkHistoryShouldNotBeFound("loanAmt.lessThan=" + DEFAULT_LOAN_AMT);

        // Get all the remarkHistoryList where loanAmt is less than UPDATED_LOAN_AMT
        defaultRemarkHistoryShouldBeFound("loanAmt.lessThan=" + UPDATED_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLoanAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where loanAmt is greater than DEFAULT_LOAN_AMT
        defaultRemarkHistoryShouldNotBeFound("loanAmt.greaterThan=" + DEFAULT_LOAN_AMT);

        // Get all the remarkHistoryList where loanAmt is greater than SMALLER_LOAN_AMT
        defaultRemarkHistoryShouldBeFound("loanAmt.greaterThan=" + SMALLER_LOAN_AMT);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByModifiedAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where modifiedAmt equals to DEFAULT_MODIFIED_AMT
        defaultRemarkHistoryShouldBeFound("modifiedAmt.equals=" + DEFAULT_MODIFIED_AMT);

        // Get all the remarkHistoryList where modifiedAmt equals to UPDATED_MODIFIED_AMT
        defaultRemarkHistoryShouldNotBeFound("modifiedAmt.equals=" + UPDATED_MODIFIED_AMT);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByModifiedAmtIsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where modifiedAmt in DEFAULT_MODIFIED_AMT or UPDATED_MODIFIED_AMT
        defaultRemarkHistoryShouldBeFound("modifiedAmt.in=" + DEFAULT_MODIFIED_AMT + "," + UPDATED_MODIFIED_AMT);

        // Get all the remarkHistoryList where modifiedAmt equals to UPDATED_MODIFIED_AMT
        defaultRemarkHistoryShouldNotBeFound("modifiedAmt.in=" + UPDATED_MODIFIED_AMT);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByModifiedAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where modifiedAmt is not null
        defaultRemarkHistoryShouldBeFound("modifiedAmt.specified=true");

        // Get all the remarkHistoryList where modifiedAmt is null
        defaultRemarkHistoryShouldNotBeFound("modifiedAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByModifiedAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where modifiedAmt is greater than or equal to DEFAULT_MODIFIED_AMT
        defaultRemarkHistoryShouldBeFound("modifiedAmt.greaterThanOrEqual=" + DEFAULT_MODIFIED_AMT);

        // Get all the remarkHistoryList where modifiedAmt is greater than or equal to UPDATED_MODIFIED_AMT
        defaultRemarkHistoryShouldNotBeFound("modifiedAmt.greaterThanOrEqual=" + UPDATED_MODIFIED_AMT);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByModifiedAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where modifiedAmt is less than or equal to DEFAULT_MODIFIED_AMT
        defaultRemarkHistoryShouldBeFound("modifiedAmt.lessThanOrEqual=" + DEFAULT_MODIFIED_AMT);

        // Get all the remarkHistoryList where modifiedAmt is less than or equal to SMALLER_MODIFIED_AMT
        defaultRemarkHistoryShouldNotBeFound("modifiedAmt.lessThanOrEqual=" + SMALLER_MODIFIED_AMT);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByModifiedAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where modifiedAmt is less than DEFAULT_MODIFIED_AMT
        defaultRemarkHistoryShouldNotBeFound("modifiedAmt.lessThan=" + DEFAULT_MODIFIED_AMT);

        // Get all the remarkHistoryList where modifiedAmt is less than UPDATED_MODIFIED_AMT
        defaultRemarkHistoryShouldBeFound("modifiedAmt.lessThan=" + UPDATED_MODIFIED_AMT);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByModifiedAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where modifiedAmt is greater than DEFAULT_MODIFIED_AMT
        defaultRemarkHistoryShouldNotBeFound("modifiedAmt.greaterThan=" + DEFAULT_MODIFIED_AMT);

        // Get all the remarkHistoryList where modifiedAmt is greater than SMALLER_MODIFIED_AMT
        defaultRemarkHistoryShouldBeFound("modifiedAmt.greaterThan=" + SMALLER_MODIFIED_AMT);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLoanInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where loanInterest equals to DEFAULT_LOAN_INTEREST
        defaultRemarkHistoryShouldBeFound("loanInterest.equals=" + DEFAULT_LOAN_INTEREST);

        // Get all the remarkHistoryList where loanInterest equals to UPDATED_LOAN_INTEREST
        defaultRemarkHistoryShouldNotBeFound("loanInterest.equals=" + UPDATED_LOAN_INTEREST);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLoanInterestIsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where loanInterest in DEFAULT_LOAN_INTEREST or UPDATED_LOAN_INTEREST
        defaultRemarkHistoryShouldBeFound("loanInterest.in=" + DEFAULT_LOAN_INTEREST + "," + UPDATED_LOAN_INTEREST);

        // Get all the remarkHistoryList where loanInterest equals to UPDATED_LOAN_INTEREST
        defaultRemarkHistoryShouldNotBeFound("loanInterest.in=" + UPDATED_LOAN_INTEREST);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLoanInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where loanInterest is not null
        defaultRemarkHistoryShouldBeFound("loanInterest.specified=true");

        // Get all the remarkHistoryList where loanInterest is null
        defaultRemarkHistoryShouldNotBeFound("loanInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLoanInterestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where loanInterest is greater than or equal to DEFAULT_LOAN_INTEREST
        defaultRemarkHistoryShouldBeFound("loanInterest.greaterThanOrEqual=" + DEFAULT_LOAN_INTEREST);

        // Get all the remarkHistoryList where loanInterest is greater than or equal to UPDATED_LOAN_INTEREST
        defaultRemarkHistoryShouldNotBeFound("loanInterest.greaterThanOrEqual=" + UPDATED_LOAN_INTEREST);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLoanInterestIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where loanInterest is less than or equal to DEFAULT_LOAN_INTEREST
        defaultRemarkHistoryShouldBeFound("loanInterest.lessThanOrEqual=" + DEFAULT_LOAN_INTEREST);

        // Get all the remarkHistoryList where loanInterest is less than or equal to SMALLER_LOAN_INTEREST
        defaultRemarkHistoryShouldNotBeFound("loanInterest.lessThanOrEqual=" + SMALLER_LOAN_INTEREST);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLoanInterestIsLessThanSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where loanInterest is less than DEFAULT_LOAN_INTEREST
        defaultRemarkHistoryShouldNotBeFound("loanInterest.lessThan=" + DEFAULT_LOAN_INTEREST);

        // Get all the remarkHistoryList where loanInterest is less than UPDATED_LOAN_INTEREST
        defaultRemarkHistoryShouldBeFound("loanInterest.lessThan=" + UPDATED_LOAN_INTEREST);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLoanInterestIsGreaterThanSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where loanInterest is greater than DEFAULT_LOAN_INTEREST
        defaultRemarkHistoryShouldNotBeFound("loanInterest.greaterThan=" + DEFAULT_LOAN_INTEREST);

        // Get all the remarkHistoryList where loanInterest is greater than SMALLER_LOAN_INTEREST
        defaultRemarkHistoryShouldBeFound("loanInterest.greaterThan=" + SMALLER_LOAN_INTEREST);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByModifiedInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where modifiedInterest equals to DEFAULT_MODIFIED_INTEREST
        defaultRemarkHistoryShouldBeFound("modifiedInterest.equals=" + DEFAULT_MODIFIED_INTEREST);

        // Get all the remarkHistoryList where modifiedInterest equals to UPDATED_MODIFIED_INTEREST
        defaultRemarkHistoryShouldNotBeFound("modifiedInterest.equals=" + UPDATED_MODIFIED_INTEREST);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByModifiedInterestIsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where modifiedInterest in DEFAULT_MODIFIED_INTEREST or UPDATED_MODIFIED_INTEREST
        defaultRemarkHistoryShouldBeFound("modifiedInterest.in=" + DEFAULT_MODIFIED_INTEREST + "," + UPDATED_MODIFIED_INTEREST);

        // Get all the remarkHistoryList where modifiedInterest equals to UPDATED_MODIFIED_INTEREST
        defaultRemarkHistoryShouldNotBeFound("modifiedInterest.in=" + UPDATED_MODIFIED_INTEREST);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByModifiedInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where modifiedInterest is not null
        defaultRemarkHistoryShouldBeFound("modifiedInterest.specified=true");

        // Get all the remarkHistoryList where modifiedInterest is null
        defaultRemarkHistoryShouldNotBeFound("modifiedInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByModifiedInterestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where modifiedInterest is greater than or equal to DEFAULT_MODIFIED_INTEREST
        defaultRemarkHistoryShouldBeFound("modifiedInterest.greaterThanOrEqual=" + DEFAULT_MODIFIED_INTEREST);

        // Get all the remarkHistoryList where modifiedInterest is greater than or equal to UPDATED_MODIFIED_INTEREST
        defaultRemarkHistoryShouldNotBeFound("modifiedInterest.greaterThanOrEqual=" + UPDATED_MODIFIED_INTEREST);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByModifiedInterestIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where modifiedInterest is less than or equal to DEFAULT_MODIFIED_INTEREST
        defaultRemarkHistoryShouldBeFound("modifiedInterest.lessThanOrEqual=" + DEFAULT_MODIFIED_INTEREST);

        // Get all the remarkHistoryList where modifiedInterest is less than or equal to SMALLER_MODIFIED_INTEREST
        defaultRemarkHistoryShouldNotBeFound("modifiedInterest.lessThanOrEqual=" + SMALLER_MODIFIED_INTEREST);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByModifiedInterestIsLessThanSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where modifiedInterest is less than DEFAULT_MODIFIED_INTEREST
        defaultRemarkHistoryShouldNotBeFound("modifiedInterest.lessThan=" + DEFAULT_MODIFIED_INTEREST);

        // Get all the remarkHistoryList where modifiedInterest is less than UPDATED_MODIFIED_INTEREST
        defaultRemarkHistoryShouldBeFound("modifiedInterest.lessThan=" + UPDATED_MODIFIED_INTEREST);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByModifiedInterestIsGreaterThanSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where modifiedInterest is greater than DEFAULT_MODIFIED_INTEREST
        defaultRemarkHistoryShouldNotBeFound("modifiedInterest.greaterThan=" + DEFAULT_MODIFIED_INTEREST);

        // Get all the remarkHistoryList where modifiedInterest is greater than SMALLER_MODIFIED_INTEREST
        defaultRemarkHistoryShouldBeFound("modifiedInterest.greaterThan=" + SMALLER_MODIFIED_INTEREST);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where createdOn equals to DEFAULT_CREATED_ON
        defaultRemarkHistoryShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the remarkHistoryList where createdOn equals to UPDATED_CREATED_ON
        defaultRemarkHistoryShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultRemarkHistoryShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the remarkHistoryList where createdOn equals to UPDATED_CREATED_ON
        defaultRemarkHistoryShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where createdOn is not null
        defaultRemarkHistoryShouldBeFound("createdOn.specified=true");

        // Get all the remarkHistoryList where createdOn is null
        defaultRemarkHistoryShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where createdBy equals to DEFAULT_CREATED_BY
        defaultRemarkHistoryShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the remarkHistoryList where createdBy equals to UPDATED_CREATED_BY
        defaultRemarkHistoryShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultRemarkHistoryShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the remarkHistoryList where createdBy equals to UPDATED_CREATED_BY
        defaultRemarkHistoryShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where createdBy is not null
        defaultRemarkHistoryShouldBeFound("createdBy.specified=true");

        // Get all the remarkHistoryList where createdBy is null
        defaultRemarkHistoryShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where createdBy contains DEFAULT_CREATED_BY
        defaultRemarkHistoryShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the remarkHistoryList where createdBy contains UPDATED_CREATED_BY
        defaultRemarkHistoryShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where createdBy does not contain DEFAULT_CREATED_BY
        defaultRemarkHistoryShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the remarkHistoryList where createdBy does not contain UPDATED_CREATED_BY
        defaultRemarkHistoryShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where branch equals to DEFAULT_BRANCH
        defaultRemarkHistoryShouldBeFound("branch.equals=" + DEFAULT_BRANCH);

        // Get all the remarkHistoryList where branch equals to UPDATED_BRANCH
        defaultRemarkHistoryShouldNotBeFound("branch.equals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByBranchIsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where branch in DEFAULT_BRANCH or UPDATED_BRANCH
        defaultRemarkHistoryShouldBeFound("branch.in=" + DEFAULT_BRANCH + "," + UPDATED_BRANCH);

        // Get all the remarkHistoryList where branch equals to UPDATED_BRANCH
        defaultRemarkHistoryShouldNotBeFound("branch.in=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where branch is not null
        defaultRemarkHistoryShouldBeFound("branch.specified=true");

        // Get all the remarkHistoryList where branch is null
        defaultRemarkHistoryShouldNotBeFound("branch.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByBranchContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where branch contains DEFAULT_BRANCH
        defaultRemarkHistoryShouldBeFound("branch.contains=" + DEFAULT_BRANCH);

        // Get all the remarkHistoryList where branch contains UPDATED_BRANCH
        defaultRemarkHistoryShouldNotBeFound("branch.contains=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByBranchNotContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where branch does not contain DEFAULT_BRANCH
        defaultRemarkHistoryShouldNotBeFound("branch.doesNotContain=" + DEFAULT_BRANCH);

        // Get all the remarkHistoryList where branch does not contain UPDATED_BRANCH
        defaultRemarkHistoryShouldBeFound("branch.doesNotContain=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByApplicationNoIsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where applicationNo equals to DEFAULT_APPLICATION_NO
        defaultRemarkHistoryShouldBeFound("applicationNo.equals=" + DEFAULT_APPLICATION_NO);

        // Get all the remarkHistoryList where applicationNo equals to UPDATED_APPLICATION_NO
        defaultRemarkHistoryShouldNotBeFound("applicationNo.equals=" + UPDATED_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByApplicationNoIsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where applicationNo in DEFAULT_APPLICATION_NO or UPDATED_APPLICATION_NO
        defaultRemarkHistoryShouldBeFound("applicationNo.in=" + DEFAULT_APPLICATION_NO + "," + UPDATED_APPLICATION_NO);

        // Get all the remarkHistoryList where applicationNo equals to UPDATED_APPLICATION_NO
        defaultRemarkHistoryShouldNotBeFound("applicationNo.in=" + UPDATED_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByApplicationNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where applicationNo is not null
        defaultRemarkHistoryShouldBeFound("applicationNo.specified=true");

        // Get all the remarkHistoryList where applicationNo is null
        defaultRemarkHistoryShouldNotBeFound("applicationNo.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByApplicationNoContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where applicationNo contains DEFAULT_APPLICATION_NO
        defaultRemarkHistoryShouldBeFound("applicationNo.contains=" + DEFAULT_APPLICATION_NO);

        // Get all the remarkHistoryList where applicationNo contains UPDATED_APPLICATION_NO
        defaultRemarkHistoryShouldNotBeFound("applicationNo.contains=" + UPDATED_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByApplicationNoNotContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where applicationNo does not contain DEFAULT_APPLICATION_NO
        defaultRemarkHistoryShouldNotBeFound("applicationNo.doesNotContain=" + DEFAULT_APPLICATION_NO);

        // Get all the remarkHistoryList where applicationNo does not contain UPDATED_APPLICATION_NO
        defaultRemarkHistoryShouldBeFound("applicationNo.doesNotContain=" + UPDATED_APPLICATION_NO);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultRemarkHistoryShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the remarkHistoryList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRemarkHistoryShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultRemarkHistoryShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the remarkHistoryList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRemarkHistoryShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where lastModified is not null
        defaultRemarkHistoryShouldBeFound("lastModified.specified=true");

        // Get all the remarkHistoryList where lastModified is null
        defaultRemarkHistoryShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultRemarkHistoryShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the remarkHistoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRemarkHistoryShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultRemarkHistoryShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the remarkHistoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRemarkHistoryShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where lastModifiedBy is not null
        defaultRemarkHistoryShouldBeFound("lastModifiedBy.specified=true");

        // Get all the remarkHistoryList where lastModifiedBy is null
        defaultRemarkHistoryShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultRemarkHistoryShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the remarkHistoryList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultRemarkHistoryShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultRemarkHistoryShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the remarkHistoryList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultRemarkHistoryShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultRemarkHistoryShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the remarkHistoryList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultRemarkHistoryShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultRemarkHistoryShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the remarkHistoryList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultRemarkHistoryShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField1 is not null
        defaultRemarkHistoryShouldBeFound("freeField1.specified=true");

        // Get all the remarkHistoryList where freeField1 is null
        defaultRemarkHistoryShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultRemarkHistoryShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the remarkHistoryList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultRemarkHistoryShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultRemarkHistoryShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the remarkHistoryList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultRemarkHistoryShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultRemarkHistoryShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the remarkHistoryList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultRemarkHistoryShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultRemarkHistoryShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the remarkHistoryList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultRemarkHistoryShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField2 is not null
        defaultRemarkHistoryShouldBeFound("freeField2.specified=true");

        // Get all the remarkHistoryList where freeField2 is null
        defaultRemarkHistoryShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultRemarkHistoryShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the remarkHistoryList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultRemarkHistoryShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultRemarkHistoryShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the remarkHistoryList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultRemarkHistoryShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultRemarkHistoryShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the remarkHistoryList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultRemarkHistoryShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultRemarkHistoryShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the remarkHistoryList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultRemarkHistoryShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField3 is not null
        defaultRemarkHistoryShouldBeFound("freeField3.specified=true");

        // Get all the remarkHistoryList where freeField3 is null
        defaultRemarkHistoryShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultRemarkHistoryShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the remarkHistoryList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultRemarkHistoryShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultRemarkHistoryShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the remarkHistoryList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultRemarkHistoryShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultRemarkHistoryShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the remarkHistoryList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultRemarkHistoryShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultRemarkHistoryShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the remarkHistoryList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultRemarkHistoryShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField4 is not null
        defaultRemarkHistoryShouldBeFound("freeField4.specified=true");

        // Get all the remarkHistoryList where freeField4 is null
        defaultRemarkHistoryShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultRemarkHistoryShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the remarkHistoryList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultRemarkHistoryShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultRemarkHistoryShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the remarkHistoryList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultRemarkHistoryShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultRemarkHistoryShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the remarkHistoryList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultRemarkHistoryShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultRemarkHistoryShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the remarkHistoryList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultRemarkHistoryShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField5 is not null
        defaultRemarkHistoryShouldBeFound("freeField5.specified=true");

        // Get all the remarkHistoryList where freeField5 is null
        defaultRemarkHistoryShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultRemarkHistoryShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the remarkHistoryList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultRemarkHistoryShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultRemarkHistoryShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the remarkHistoryList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultRemarkHistoryShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultRemarkHistoryShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the remarkHistoryList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultRemarkHistoryShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultRemarkHistoryShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the remarkHistoryList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultRemarkHistoryShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField6 is not null
        defaultRemarkHistoryShouldBeFound("freeField6.specified=true");

        // Get all the remarkHistoryList where freeField6 is null
        defaultRemarkHistoryShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultRemarkHistoryShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the remarkHistoryList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultRemarkHistoryShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        // Get all the remarkHistoryList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultRemarkHistoryShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the remarkHistoryList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultRemarkHistoryShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesBySecurityUserIsEqualToSomething() throws Exception {
        SecurityUser securityUser;
        if (TestUtil.findAll(em, SecurityUser.class).isEmpty()) {
            remarkHistoryRepository.saveAndFlush(remarkHistory);
            securityUser = SecurityUserResourceIT.createEntity(em);
        } else {
            securityUser = TestUtil.findAll(em, SecurityUser.class).get(0);
        }
        em.persist(securityUser);
        em.flush();
        remarkHistory.setSecurityUser(securityUser);
        remarkHistoryRepository.saveAndFlush(remarkHistory);
        Long securityUserId = securityUser.getId();

        // Get all the remarkHistoryList where securityUser equals to securityUserId
        defaultRemarkHistoryShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the remarkHistoryList where securityUser equals to (securityUserId + 1)
        defaultRemarkHistoryShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    @Test
    @Transactional
    void getAllRemarkHistoriesByLoanApplicationsIsEqualToSomething() throws Exception {
        LoanApplications loanApplications;
        if (TestUtil.findAll(em, LoanApplications.class).isEmpty()) {
            remarkHistoryRepository.saveAndFlush(remarkHistory);
            loanApplications = LoanApplicationsResourceIT.createEntity(em);
        } else {
            loanApplications = TestUtil.findAll(em, LoanApplications.class).get(0);
        }
        em.persist(loanApplications);
        em.flush();
        remarkHistory.setLoanApplications(loanApplications);
        remarkHistoryRepository.saveAndFlush(remarkHistory);
        Long loanApplicationsId = loanApplications.getId();

        // Get all the remarkHistoryList where loanApplications equals to loanApplicationsId
        defaultRemarkHistoryShouldBeFound("loanApplicationsId.equals=" + loanApplicationsId);

        // Get all the remarkHistoryList where loanApplications equals to (loanApplicationsId + 1)
        defaultRemarkHistoryShouldNotBeFound("loanApplicationsId.equals=" + (loanApplicationsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRemarkHistoryShouldBeFound(String filter) throws Exception {
        restRemarkHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(remarkHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].loanAmt").value(hasItem(DEFAULT_LOAN_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].modifiedAmt").value(hasItem(DEFAULT_MODIFIED_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanInterest").value(hasItem(DEFAULT_LOAN_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].modifiedInterest").value(hasItem(DEFAULT_MODIFIED_INTEREST.doubleValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].applicationNo").value(hasItem(DEFAULT_APPLICATION_NO)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));

        // Check, that the count call also returns 1
        restRemarkHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRemarkHistoryShouldNotBeFound(String filter) throws Exception {
        restRemarkHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRemarkHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRemarkHistory() throws Exception {
        // Get the remarkHistory
        restRemarkHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRemarkHistory() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        int databaseSizeBeforeUpdate = remarkHistoryRepository.findAll().size();

        // Update the remarkHistory
        RemarkHistory updatedRemarkHistory = remarkHistoryRepository.findById(remarkHistory.getId()).get();
        // Disconnect from session so that the updates on updatedRemarkHistory are not directly saved in db
        em.detach(updatedRemarkHistory);
        updatedRemarkHistory
            .remark(UPDATED_REMARK)
            .loanAmt(UPDATED_LOAN_AMT)
            .modifiedAmt(UPDATED_MODIFIED_AMT)
            .loanInterest(UPDATED_LOAN_INTEREST)
            .modifiedInterest(UPDATED_MODIFIED_INTEREST)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .branch(UPDATED_BRANCH)
            .applicationNo(UPDATED_APPLICATION_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);
        //  .freeField6(UPDATED_FREE_FIELD_6);
        RemarkHistoryDTO remarkHistoryDTO = remarkHistoryMapper.toDto(updatedRemarkHistory);

        restRemarkHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remarkHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remarkHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the RemarkHistory in the database
        List<RemarkHistory> remarkHistoryList = remarkHistoryRepository.findAll();
        assertThat(remarkHistoryList).hasSize(databaseSizeBeforeUpdate);
        RemarkHistory testRemarkHistory = remarkHistoryList.get(remarkHistoryList.size() - 1);
        assertThat(testRemarkHistory.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testRemarkHistory.getLoanAmt()).isEqualTo(UPDATED_LOAN_AMT);
        assertThat(testRemarkHistory.getModifiedAmt()).isEqualTo(UPDATED_MODIFIED_AMT);
        assertThat(testRemarkHistory.getLoanInterest()).isEqualTo(UPDATED_LOAN_INTEREST);
        assertThat(testRemarkHistory.getModifiedInterest()).isEqualTo(UPDATED_MODIFIED_INTEREST);
        assertThat(testRemarkHistory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRemarkHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRemarkHistory.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testRemarkHistory.getApplicationNo()).isEqualTo(UPDATED_APPLICATION_NO);
        assertThat(testRemarkHistory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRemarkHistory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testRemarkHistory.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testRemarkHistory.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testRemarkHistory.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testRemarkHistory.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testRemarkHistory.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        //  assertThat(testRemarkHistory.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingRemarkHistory() throws Exception {
        int databaseSizeBeforeUpdate = remarkHistoryRepository.findAll().size();
        remarkHistory.setId(count.incrementAndGet());

        // Create the RemarkHistory
        RemarkHistoryDTO remarkHistoryDTO = remarkHistoryMapper.toDto(remarkHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemarkHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, remarkHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remarkHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemarkHistory in the database
        List<RemarkHistory> remarkHistoryList = remarkHistoryRepository.findAll();
        assertThat(remarkHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRemarkHistory() throws Exception {
        int databaseSizeBeforeUpdate = remarkHistoryRepository.findAll().size();
        remarkHistory.setId(count.incrementAndGet());

        // Create the RemarkHistory
        RemarkHistoryDTO remarkHistoryDTO = remarkHistoryMapper.toDto(remarkHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemarkHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(remarkHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemarkHistory in the database
        List<RemarkHistory> remarkHistoryList = remarkHistoryRepository.findAll();
        assertThat(remarkHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRemarkHistory() throws Exception {
        int databaseSizeBeforeUpdate = remarkHistoryRepository.findAll().size();
        remarkHistory.setId(count.incrementAndGet());

        // Create the RemarkHistory
        RemarkHistoryDTO remarkHistoryDTO = remarkHistoryMapper.toDto(remarkHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemarkHistoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(remarkHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RemarkHistory in the database
        List<RemarkHistory> remarkHistoryList = remarkHistoryRepository.findAll();
        assertThat(remarkHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRemarkHistoryWithPatch() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        int databaseSizeBeforeUpdate = remarkHistoryRepository.findAll().size();

        // Update the remarkHistory using partial update
        RemarkHistory partialUpdatedRemarkHistory = new RemarkHistory();
        partialUpdatedRemarkHistory.setId(remarkHistory.getId());

        partialUpdatedRemarkHistory
            .loanInterest(UPDATED_LOAN_INTEREST)
            .applicationNo(UPDATED_APPLICATION_NO)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField5(UPDATED_FREE_FIELD_5);

        restRemarkHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemarkHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRemarkHistory))
            )
            .andExpect(status().isOk());

        // Validate the RemarkHistory in the database
        List<RemarkHistory> remarkHistoryList = remarkHistoryRepository.findAll();
        assertThat(remarkHistoryList).hasSize(databaseSizeBeforeUpdate);
        RemarkHistory testRemarkHistory = remarkHistoryList.get(remarkHistoryList.size() - 1);
        assertThat(testRemarkHistory.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testRemarkHistory.getLoanAmt()).isEqualTo(DEFAULT_LOAN_AMT);
        assertThat(testRemarkHistory.getModifiedAmt()).isEqualTo(DEFAULT_MODIFIED_AMT);
        assertThat(testRemarkHistory.getLoanInterest()).isEqualTo(UPDATED_LOAN_INTEREST);
        assertThat(testRemarkHistory.getModifiedInterest()).isEqualTo(DEFAULT_MODIFIED_INTEREST);
        assertThat(testRemarkHistory.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testRemarkHistory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRemarkHistory.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testRemarkHistory.getApplicationNo()).isEqualTo(UPDATED_APPLICATION_NO);
        assertThat(testRemarkHistory.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testRemarkHistory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testRemarkHistory.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testRemarkHistory.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testRemarkHistory.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testRemarkHistory.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testRemarkHistory.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        //        assertThat(testRemarkHistory.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateRemarkHistoryWithPatch() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        int databaseSizeBeforeUpdate = remarkHistoryRepository.findAll().size();

        // Update the remarkHistory using partial update
        RemarkHistory partialUpdatedRemarkHistory = new RemarkHistory();
        partialUpdatedRemarkHistory.setId(remarkHistory.getId());

        partialUpdatedRemarkHistory
            .remark(UPDATED_REMARK)
            .loanAmt(UPDATED_LOAN_AMT)
            .modifiedAmt(UPDATED_MODIFIED_AMT)
            .loanInterest(UPDATED_LOAN_INTEREST)
            .modifiedInterest(UPDATED_MODIFIED_INTEREST)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .branch(UPDATED_BRANCH)
            .applicationNo(UPDATED_APPLICATION_NO)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);
        //            .freeField6(UPDATED_FREE_FIELD_6);

        restRemarkHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRemarkHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRemarkHistory))
            )
            .andExpect(status().isOk());

        // Validate the RemarkHistory in the database
        List<RemarkHistory> remarkHistoryList = remarkHistoryRepository.findAll();
        assertThat(remarkHistoryList).hasSize(databaseSizeBeforeUpdate);
        RemarkHistory testRemarkHistory = remarkHistoryList.get(remarkHistoryList.size() - 1);
        assertThat(testRemarkHistory.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testRemarkHistory.getLoanAmt()).isEqualTo(UPDATED_LOAN_AMT);
        assertThat(testRemarkHistory.getModifiedAmt()).isEqualTo(UPDATED_MODIFIED_AMT);
        assertThat(testRemarkHistory.getLoanInterest()).isEqualTo(UPDATED_LOAN_INTEREST);
        assertThat(testRemarkHistory.getModifiedInterest()).isEqualTo(UPDATED_MODIFIED_INTEREST);
        assertThat(testRemarkHistory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testRemarkHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRemarkHistory.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testRemarkHistory.getApplicationNo()).isEqualTo(UPDATED_APPLICATION_NO);
        assertThat(testRemarkHistory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRemarkHistory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testRemarkHistory.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testRemarkHistory.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testRemarkHistory.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testRemarkHistory.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testRemarkHistory.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        //        assertThat(testRemarkHistory.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingRemarkHistory() throws Exception {
        int databaseSizeBeforeUpdate = remarkHistoryRepository.findAll().size();
        remarkHistory.setId(count.incrementAndGet());

        // Create the RemarkHistory
        RemarkHistoryDTO remarkHistoryDTO = remarkHistoryMapper.toDto(remarkHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRemarkHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, remarkHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(remarkHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemarkHistory in the database
        List<RemarkHistory> remarkHistoryList = remarkHistoryRepository.findAll();
        assertThat(remarkHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRemarkHistory() throws Exception {
        int databaseSizeBeforeUpdate = remarkHistoryRepository.findAll().size();
        remarkHistory.setId(count.incrementAndGet());

        // Create the RemarkHistory
        RemarkHistoryDTO remarkHistoryDTO = remarkHistoryMapper.toDto(remarkHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemarkHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(remarkHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RemarkHistory in the database
        List<RemarkHistory> remarkHistoryList = remarkHistoryRepository.findAll();
        assertThat(remarkHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRemarkHistory() throws Exception {
        int databaseSizeBeforeUpdate = remarkHistoryRepository.findAll().size();
        remarkHistory.setId(count.incrementAndGet());

        // Create the RemarkHistory
        RemarkHistoryDTO remarkHistoryDTO = remarkHistoryMapper.toDto(remarkHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRemarkHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(remarkHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RemarkHistory in the database
        List<RemarkHistory> remarkHistoryList = remarkHistoryRepository.findAll();
        assertThat(remarkHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRemarkHistory() throws Exception {
        // Initialize the database
        remarkHistoryRepository.saveAndFlush(remarkHistory);

        int databaseSizeBeforeDelete = remarkHistoryRepository.findAll().size();

        // Delete the remarkHistory
        restRemarkHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, remarkHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RemarkHistory> remarkHistoryList = remarkHistoryRepository.findAll();
        assertThat(remarkHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
