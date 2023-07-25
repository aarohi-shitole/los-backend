package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.MasterAgreement;
import com.techvg.los.domain.enumeration.RepaymentFreqency;
import com.techvg.los.repository.MasterAgreementRepository;
import com.techvg.los.service.criteria.MasterAgreementCriteria;
import com.techvg.los.service.dto.MasterAgreementDTO;
import com.techvg.los.service.mapper.MasterAgreementMapper;
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
 * Integration tests for the {@link MasterAgreementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MasterAgreementResourceIT {

    private static final String DEFAULT_MEMBER_ID = "AAAAAAAAAA";
    private static final String UPDATED_MEMBER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PORTFOLIO_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PORTFOLIO_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_HOME_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_SERV_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_SERV_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_HOME_STATE = "AAAAAAAAAA";
    private static final String UPDATED_HOME_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_SERV_STATE = "AAAAAAAAAA";
    private static final String UPDATED_SERV_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_GST_EXEMPTED = "AAAAAAAAAA";
    private static final String UPDATED_GST_EXEMPTED = "BBBBBBBBBB";

    private static final String DEFAULT_PREF_REPAY_MODE = "AAAAAAAAAA";
    private static final String UPDATED_PREF_REPAY_MODE = "BBBBBBBBBB";

    private static final String DEFAULT_TDS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TDS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TDS_RATE = "AAAAAAAAAA";
    private static final String UPDATED_TDS_RATE = "BBBBBBBBBB";

    private static final Double DEFAULT_SANCTIONED_AMOUNT = 1D;
    private static final Double UPDATED_SANCTIONED_AMOUNT = 2D;
    private static final Double SMALLER_SANCTIONED_AMOUNT = 1D - 1D;

    private static final String DEFAULT_ORIGINATION_APPLN_NO = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINATION_APPLN_NO = "BBBBBBBBBB";

    private static final Long DEFAULT_CYCLE_DAY = 1L;
    private static final Long UPDATED_CYCLE_DAY = 2L;
    private static final Long SMALLER_CYCLE_DAY = 1L - 1L;

    private static final String DEFAULT_TENOR = "AAAAAAAAAA";
    private static final String UPDATED_TENOR = "BBBBBBBBBB";

    private static final Double DEFAULT_INTEREST_RATE = 1D;
    private static final Double UPDATED_INTEREST_RATE = 2D;
    private static final Double SMALLER_INTEREST_RATE = 1D - 1D;

    private static final RepaymentFreqency DEFAULT_REPAY_FREQ = RepaymentFreqency.MONTHLY;
    private static final RepaymentFreqency UPDATED_REPAY_FREQ = RepaymentFreqency.QUARTERLY;

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

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

    private static final String ENTITY_API_URL = "/api/master-agreements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MasterAgreementRepository masterAgreementRepository;

    @Autowired
    private MasterAgreementMapper masterAgreementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMasterAgreementMockMvc;

    private MasterAgreement masterAgreement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MasterAgreement createEntity(EntityManager em) {
        MasterAgreement masterAgreement = new MasterAgreement()
            .memberId(DEFAULT_MEMBER_ID)
            .portfolioCode(DEFAULT_PORTFOLIO_CODE)
            .productCode(DEFAULT_PRODUCT_CODE)
            .homeBranch(DEFAULT_HOME_BRANCH)
            .servBranch(DEFAULT_SERV_BRANCH)
            .homeState(DEFAULT_HOME_STATE)
            .servState(DEFAULT_SERV_STATE)
            .gstExempted(DEFAULT_GST_EXEMPTED)
            .prefRepayMode(DEFAULT_PREF_REPAY_MODE)
            .tdsCode(DEFAULT_TDS_CODE)
            .tdsRate(DEFAULT_TDS_RATE)
            .sanctionedAmount(DEFAULT_SANCTIONED_AMOUNT)
            .originationApplnNo(DEFAULT_ORIGINATION_APPLN_NO)
            .cycleDay(DEFAULT_CYCLE_DAY)
            .tenor(DEFAULT_TENOR)
            .interestRate(DEFAULT_INTEREST_RATE)
            .repayFreq(DEFAULT_REPAY_FREQ)
            .isDeleted(DEFAULT_IS_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4);
        return masterAgreement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MasterAgreement createUpdatedEntity(EntityManager em) {
        MasterAgreement masterAgreement = new MasterAgreement()
            .memberId(UPDATED_MEMBER_ID)
            .portfolioCode(UPDATED_PORTFOLIO_CODE)
            .productCode(UPDATED_PRODUCT_CODE)
            .homeBranch(UPDATED_HOME_BRANCH)
            .servBranch(UPDATED_SERV_BRANCH)
            .homeState(UPDATED_HOME_STATE)
            .servState(UPDATED_SERV_STATE)
            .gstExempted(UPDATED_GST_EXEMPTED)
            .prefRepayMode(UPDATED_PREF_REPAY_MODE)
            .tdsCode(UPDATED_TDS_CODE)
            .tdsRate(UPDATED_TDS_RATE)
            .sanctionedAmount(UPDATED_SANCTIONED_AMOUNT)
            .originationApplnNo(UPDATED_ORIGINATION_APPLN_NO)
            .cycleDay(UPDATED_CYCLE_DAY)
            .tenor(UPDATED_TENOR)
            .interestRate(UPDATED_INTEREST_RATE)
            .repayFreq(UPDATED_REPAY_FREQ)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        return masterAgreement;
    }

    @BeforeEach
    public void initTest() {
        masterAgreement = createEntity(em);
    }

    @Test
    @Transactional
    void createMasterAgreement() throws Exception {
        int databaseSizeBeforeCreate = masterAgreementRepository.findAll().size();
        // Create the MasterAgreement
        MasterAgreementDTO masterAgreementDTO = masterAgreementMapper.toDto(masterAgreement);
        restMasterAgreementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(masterAgreementDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MasterAgreement in the database
        List<MasterAgreement> masterAgreementList = masterAgreementRepository.findAll();
        assertThat(masterAgreementList).hasSize(databaseSizeBeforeCreate + 1);
        MasterAgreement testMasterAgreement = masterAgreementList.get(masterAgreementList.size() - 1);
        assertThat(testMasterAgreement.getMemberId()).isEqualTo(DEFAULT_MEMBER_ID);
        assertThat(testMasterAgreement.getPortfolioCode()).isEqualTo(DEFAULT_PORTFOLIO_CODE);
        assertThat(testMasterAgreement.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testMasterAgreement.getHomeBranch()).isEqualTo(DEFAULT_HOME_BRANCH);
        assertThat(testMasterAgreement.getServBranch()).isEqualTo(DEFAULT_SERV_BRANCH);
        assertThat(testMasterAgreement.getHomeState()).isEqualTo(DEFAULT_HOME_STATE);
        assertThat(testMasterAgreement.getServState()).isEqualTo(DEFAULT_SERV_STATE);
        assertThat(testMasterAgreement.getGstExempted()).isEqualTo(DEFAULT_GST_EXEMPTED);
        assertThat(testMasterAgreement.getPrefRepayMode()).isEqualTo(DEFAULT_PREF_REPAY_MODE);
        assertThat(testMasterAgreement.getTdsCode()).isEqualTo(DEFAULT_TDS_CODE);
        assertThat(testMasterAgreement.getTdsRate()).isEqualTo(DEFAULT_TDS_RATE);
        assertThat(testMasterAgreement.getSanctionedAmount()).isEqualTo(DEFAULT_SANCTIONED_AMOUNT);
        assertThat(testMasterAgreement.getOriginationApplnNo()).isEqualTo(DEFAULT_ORIGINATION_APPLN_NO);
        assertThat(testMasterAgreement.getCycleDay()).isEqualTo(DEFAULT_CYCLE_DAY);
        assertThat(testMasterAgreement.getTenor()).isEqualTo(DEFAULT_TENOR);
        assertThat(testMasterAgreement.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testMasterAgreement.getRepayFreq()).isEqualTo(DEFAULT_REPAY_FREQ);
        assertThat(testMasterAgreement.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMasterAgreement.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMasterAgreement.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMasterAgreement.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testMasterAgreement.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testMasterAgreement.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testMasterAgreement.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void createMasterAgreementWithExistingId() throws Exception {
        // Create the MasterAgreement with an existing ID
        masterAgreement.setId(1L);
        MasterAgreementDTO masterAgreementDTO = masterAgreementMapper.toDto(masterAgreement);

        int databaseSizeBeforeCreate = masterAgreementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMasterAgreementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(masterAgreementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterAgreement in the database
        List<MasterAgreement> masterAgreementList = masterAgreementRepository.findAll();
        assertThat(masterAgreementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMasterAgreements() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList
        restMasterAgreementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(masterAgreement.getId().intValue())))
            .andExpect(jsonPath("$.[*].memberId").value(hasItem(DEFAULT_MEMBER_ID)))
            .andExpect(jsonPath("$.[*].portfolioCode").value(hasItem(DEFAULT_PORTFOLIO_CODE)))
            .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE)))
            .andExpect(jsonPath("$.[*].homeBranch").value(hasItem(DEFAULT_HOME_BRANCH)))
            .andExpect(jsonPath("$.[*].servBranch").value(hasItem(DEFAULT_SERV_BRANCH)))
            .andExpect(jsonPath("$.[*].homeState").value(hasItem(DEFAULT_HOME_STATE)))
            .andExpect(jsonPath("$.[*].servState").value(hasItem(DEFAULT_SERV_STATE)))
            .andExpect(jsonPath("$.[*].gstExempted").value(hasItem(DEFAULT_GST_EXEMPTED)))
            .andExpect(jsonPath("$.[*].prefRepayMode").value(hasItem(DEFAULT_PREF_REPAY_MODE)))
            .andExpect(jsonPath("$.[*].tdsCode").value(hasItem(DEFAULT_TDS_CODE)))
            .andExpect(jsonPath("$.[*].tdsRate").value(hasItem(DEFAULT_TDS_RATE)))
            .andExpect(jsonPath("$.[*].sanctionedAmount").value(hasItem(DEFAULT_SANCTIONED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].originationApplnNo").value(hasItem(DEFAULT_ORIGINATION_APPLN_NO)))
            .andExpect(jsonPath("$.[*].cycleDay").value(hasItem(DEFAULT_CYCLE_DAY.intValue())))
            .andExpect(jsonPath("$.[*].tenor").value(hasItem(DEFAULT_TENOR)))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].repayFreq").value(hasItem(DEFAULT_REPAY_FREQ.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));
    }

    @Test
    @Transactional
    void getMasterAgreement() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get the masterAgreement
        restMasterAgreementMockMvc
            .perform(get(ENTITY_API_URL_ID, masterAgreement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(masterAgreement.getId().intValue()))
            .andExpect(jsonPath("$.memberId").value(DEFAULT_MEMBER_ID))
            .andExpect(jsonPath("$.portfolioCode").value(DEFAULT_PORTFOLIO_CODE))
            .andExpect(jsonPath("$.productCode").value(DEFAULT_PRODUCT_CODE))
            .andExpect(jsonPath("$.homeBranch").value(DEFAULT_HOME_BRANCH))
            .andExpect(jsonPath("$.servBranch").value(DEFAULT_SERV_BRANCH))
            .andExpect(jsonPath("$.homeState").value(DEFAULT_HOME_STATE))
            .andExpect(jsonPath("$.servState").value(DEFAULT_SERV_STATE))
            .andExpect(jsonPath("$.gstExempted").value(DEFAULT_GST_EXEMPTED))
            .andExpect(jsonPath("$.prefRepayMode").value(DEFAULT_PREF_REPAY_MODE))
            .andExpect(jsonPath("$.tdsCode").value(DEFAULT_TDS_CODE))
            .andExpect(jsonPath("$.tdsRate").value(DEFAULT_TDS_RATE))
            .andExpect(jsonPath("$.sanctionedAmount").value(DEFAULT_SANCTIONED_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.originationApplnNo").value(DEFAULT_ORIGINATION_APPLN_NO))
            .andExpect(jsonPath("$.cycleDay").value(DEFAULT_CYCLE_DAY.intValue()))
            .andExpect(jsonPath("$.tenor").value(DEFAULT_TENOR))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.repayFreq").value(DEFAULT_REPAY_FREQ.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4));
    }

    @Test
    @Transactional
    void getMasterAgreementsByIdFiltering() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        Long id = masterAgreement.getId();

        defaultMasterAgreementShouldBeFound("id.equals=" + id);
        defaultMasterAgreementShouldNotBeFound("id.notEquals=" + id);

        defaultMasterAgreementShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMasterAgreementShouldNotBeFound("id.greaterThan=" + id);

        defaultMasterAgreementShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMasterAgreementShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByMemberIdIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where memberId equals to DEFAULT_MEMBER_ID
        defaultMasterAgreementShouldBeFound("memberId.equals=" + DEFAULT_MEMBER_ID);

        // Get all the masterAgreementList where memberId equals to UPDATED_MEMBER_ID
        defaultMasterAgreementShouldNotBeFound("memberId.equals=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByMemberIdIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where memberId in DEFAULT_MEMBER_ID or UPDATED_MEMBER_ID
        defaultMasterAgreementShouldBeFound("memberId.in=" + DEFAULT_MEMBER_ID + "," + UPDATED_MEMBER_ID);

        // Get all the masterAgreementList where memberId equals to UPDATED_MEMBER_ID
        defaultMasterAgreementShouldNotBeFound("memberId.in=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByMemberIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where memberId is not null
        defaultMasterAgreementShouldBeFound("memberId.specified=true");

        // Get all the masterAgreementList where memberId is null
        defaultMasterAgreementShouldNotBeFound("memberId.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByMemberIdContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where memberId contains DEFAULT_MEMBER_ID
        defaultMasterAgreementShouldBeFound("memberId.contains=" + DEFAULT_MEMBER_ID);

        // Get all the masterAgreementList where memberId contains UPDATED_MEMBER_ID
        defaultMasterAgreementShouldNotBeFound("memberId.contains=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByMemberIdNotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where memberId does not contain DEFAULT_MEMBER_ID
        defaultMasterAgreementShouldNotBeFound("memberId.doesNotContain=" + DEFAULT_MEMBER_ID);

        // Get all the masterAgreementList where memberId does not contain UPDATED_MEMBER_ID
        defaultMasterAgreementShouldBeFound("memberId.doesNotContain=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByPortfolioCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where portfolioCode equals to DEFAULT_PORTFOLIO_CODE
        defaultMasterAgreementShouldBeFound("portfolioCode.equals=" + DEFAULT_PORTFOLIO_CODE);

        // Get all the masterAgreementList where portfolioCode equals to UPDATED_PORTFOLIO_CODE
        defaultMasterAgreementShouldNotBeFound("portfolioCode.equals=" + UPDATED_PORTFOLIO_CODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByPortfolioCodeIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where portfolioCode in DEFAULT_PORTFOLIO_CODE or UPDATED_PORTFOLIO_CODE
        defaultMasterAgreementShouldBeFound("portfolioCode.in=" + DEFAULT_PORTFOLIO_CODE + "," + UPDATED_PORTFOLIO_CODE);

        // Get all the masterAgreementList where portfolioCode equals to UPDATED_PORTFOLIO_CODE
        defaultMasterAgreementShouldNotBeFound("portfolioCode.in=" + UPDATED_PORTFOLIO_CODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByPortfolioCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where portfolioCode is not null
        defaultMasterAgreementShouldBeFound("portfolioCode.specified=true");

        // Get all the masterAgreementList where portfolioCode is null
        defaultMasterAgreementShouldNotBeFound("portfolioCode.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByPortfolioCodeContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where portfolioCode contains DEFAULT_PORTFOLIO_CODE
        defaultMasterAgreementShouldBeFound("portfolioCode.contains=" + DEFAULT_PORTFOLIO_CODE);

        // Get all the masterAgreementList where portfolioCode contains UPDATED_PORTFOLIO_CODE
        defaultMasterAgreementShouldNotBeFound("portfolioCode.contains=" + UPDATED_PORTFOLIO_CODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByPortfolioCodeNotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where portfolioCode does not contain DEFAULT_PORTFOLIO_CODE
        defaultMasterAgreementShouldNotBeFound("portfolioCode.doesNotContain=" + DEFAULT_PORTFOLIO_CODE);

        // Get all the masterAgreementList where portfolioCode does not contain UPDATED_PORTFOLIO_CODE
        defaultMasterAgreementShouldBeFound("portfolioCode.doesNotContain=" + UPDATED_PORTFOLIO_CODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByProductCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where productCode equals to DEFAULT_PRODUCT_CODE
        defaultMasterAgreementShouldBeFound("productCode.equals=" + DEFAULT_PRODUCT_CODE);

        // Get all the masterAgreementList where productCode equals to UPDATED_PRODUCT_CODE
        defaultMasterAgreementShouldNotBeFound("productCode.equals=" + UPDATED_PRODUCT_CODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByProductCodeIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where productCode in DEFAULT_PRODUCT_CODE or UPDATED_PRODUCT_CODE
        defaultMasterAgreementShouldBeFound("productCode.in=" + DEFAULT_PRODUCT_CODE + "," + UPDATED_PRODUCT_CODE);

        // Get all the masterAgreementList where productCode equals to UPDATED_PRODUCT_CODE
        defaultMasterAgreementShouldNotBeFound("productCode.in=" + UPDATED_PRODUCT_CODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByProductCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where productCode is not null
        defaultMasterAgreementShouldBeFound("productCode.specified=true");

        // Get all the masterAgreementList where productCode is null
        defaultMasterAgreementShouldNotBeFound("productCode.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByProductCodeContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where productCode contains DEFAULT_PRODUCT_CODE
        defaultMasterAgreementShouldBeFound("productCode.contains=" + DEFAULT_PRODUCT_CODE);

        // Get all the masterAgreementList where productCode contains UPDATED_PRODUCT_CODE
        defaultMasterAgreementShouldNotBeFound("productCode.contains=" + UPDATED_PRODUCT_CODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByProductCodeNotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where productCode does not contain DEFAULT_PRODUCT_CODE
        defaultMasterAgreementShouldNotBeFound("productCode.doesNotContain=" + DEFAULT_PRODUCT_CODE);

        // Get all the masterAgreementList where productCode does not contain UPDATED_PRODUCT_CODE
        defaultMasterAgreementShouldBeFound("productCode.doesNotContain=" + UPDATED_PRODUCT_CODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByHomeBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where homeBranch equals to DEFAULT_HOME_BRANCH
        defaultMasterAgreementShouldBeFound("homeBranch.equals=" + DEFAULT_HOME_BRANCH);

        // Get all the masterAgreementList where homeBranch equals to UPDATED_HOME_BRANCH
        defaultMasterAgreementShouldNotBeFound("homeBranch.equals=" + UPDATED_HOME_BRANCH);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByHomeBranchIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where homeBranch in DEFAULT_HOME_BRANCH or UPDATED_HOME_BRANCH
        defaultMasterAgreementShouldBeFound("homeBranch.in=" + DEFAULT_HOME_BRANCH + "," + UPDATED_HOME_BRANCH);

        // Get all the masterAgreementList where homeBranch equals to UPDATED_HOME_BRANCH
        defaultMasterAgreementShouldNotBeFound("homeBranch.in=" + UPDATED_HOME_BRANCH);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByHomeBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where homeBranch is not null
        defaultMasterAgreementShouldBeFound("homeBranch.specified=true");

        // Get all the masterAgreementList where homeBranch is null
        defaultMasterAgreementShouldNotBeFound("homeBranch.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByHomeBranchContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where homeBranch contains DEFAULT_HOME_BRANCH
        defaultMasterAgreementShouldBeFound("homeBranch.contains=" + DEFAULT_HOME_BRANCH);

        // Get all the masterAgreementList where homeBranch contains UPDATED_HOME_BRANCH
        defaultMasterAgreementShouldNotBeFound("homeBranch.contains=" + UPDATED_HOME_BRANCH);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByHomeBranchNotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where homeBranch does not contain DEFAULT_HOME_BRANCH
        defaultMasterAgreementShouldNotBeFound("homeBranch.doesNotContain=" + DEFAULT_HOME_BRANCH);

        // Get all the masterAgreementList where homeBranch does not contain UPDATED_HOME_BRANCH
        defaultMasterAgreementShouldBeFound("homeBranch.doesNotContain=" + UPDATED_HOME_BRANCH);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByServBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where servBranch equals to DEFAULT_SERV_BRANCH
        defaultMasterAgreementShouldBeFound("servBranch.equals=" + DEFAULT_SERV_BRANCH);

        // Get all the masterAgreementList where servBranch equals to UPDATED_SERV_BRANCH
        defaultMasterAgreementShouldNotBeFound("servBranch.equals=" + UPDATED_SERV_BRANCH);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByServBranchIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where servBranch in DEFAULT_SERV_BRANCH or UPDATED_SERV_BRANCH
        defaultMasterAgreementShouldBeFound("servBranch.in=" + DEFAULT_SERV_BRANCH + "," + UPDATED_SERV_BRANCH);

        // Get all the masterAgreementList where servBranch equals to UPDATED_SERV_BRANCH
        defaultMasterAgreementShouldNotBeFound("servBranch.in=" + UPDATED_SERV_BRANCH);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByServBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where servBranch is not null
        defaultMasterAgreementShouldBeFound("servBranch.specified=true");

        // Get all the masterAgreementList where servBranch is null
        defaultMasterAgreementShouldNotBeFound("servBranch.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByServBranchContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where servBranch contains DEFAULT_SERV_BRANCH
        defaultMasterAgreementShouldBeFound("servBranch.contains=" + DEFAULT_SERV_BRANCH);

        // Get all the masterAgreementList where servBranch contains UPDATED_SERV_BRANCH
        defaultMasterAgreementShouldNotBeFound("servBranch.contains=" + UPDATED_SERV_BRANCH);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByServBranchNotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where servBranch does not contain DEFAULT_SERV_BRANCH
        defaultMasterAgreementShouldNotBeFound("servBranch.doesNotContain=" + DEFAULT_SERV_BRANCH);

        // Get all the masterAgreementList where servBranch does not contain UPDATED_SERV_BRANCH
        defaultMasterAgreementShouldBeFound("servBranch.doesNotContain=" + UPDATED_SERV_BRANCH);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByHomeStateIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where homeState equals to DEFAULT_HOME_STATE
        defaultMasterAgreementShouldBeFound("homeState.equals=" + DEFAULT_HOME_STATE);

        // Get all the masterAgreementList where homeState equals to UPDATED_HOME_STATE
        defaultMasterAgreementShouldNotBeFound("homeState.equals=" + UPDATED_HOME_STATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByHomeStateIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where homeState in DEFAULT_HOME_STATE or UPDATED_HOME_STATE
        defaultMasterAgreementShouldBeFound("homeState.in=" + DEFAULT_HOME_STATE + "," + UPDATED_HOME_STATE);

        // Get all the masterAgreementList where homeState equals to UPDATED_HOME_STATE
        defaultMasterAgreementShouldNotBeFound("homeState.in=" + UPDATED_HOME_STATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByHomeStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where homeState is not null
        defaultMasterAgreementShouldBeFound("homeState.specified=true");

        // Get all the masterAgreementList where homeState is null
        defaultMasterAgreementShouldNotBeFound("homeState.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByHomeStateContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where homeState contains DEFAULT_HOME_STATE
        defaultMasterAgreementShouldBeFound("homeState.contains=" + DEFAULT_HOME_STATE);

        // Get all the masterAgreementList where homeState contains UPDATED_HOME_STATE
        defaultMasterAgreementShouldNotBeFound("homeState.contains=" + UPDATED_HOME_STATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByHomeStateNotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where homeState does not contain DEFAULT_HOME_STATE
        defaultMasterAgreementShouldNotBeFound("homeState.doesNotContain=" + DEFAULT_HOME_STATE);

        // Get all the masterAgreementList where homeState does not contain UPDATED_HOME_STATE
        defaultMasterAgreementShouldBeFound("homeState.doesNotContain=" + UPDATED_HOME_STATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByServStateIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where servState equals to DEFAULT_SERV_STATE
        defaultMasterAgreementShouldBeFound("servState.equals=" + DEFAULT_SERV_STATE);

        // Get all the masterAgreementList where servState equals to UPDATED_SERV_STATE
        defaultMasterAgreementShouldNotBeFound("servState.equals=" + UPDATED_SERV_STATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByServStateIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where servState in DEFAULT_SERV_STATE or UPDATED_SERV_STATE
        defaultMasterAgreementShouldBeFound("servState.in=" + DEFAULT_SERV_STATE + "," + UPDATED_SERV_STATE);

        // Get all the masterAgreementList where servState equals to UPDATED_SERV_STATE
        defaultMasterAgreementShouldNotBeFound("servState.in=" + UPDATED_SERV_STATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByServStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where servState is not null
        defaultMasterAgreementShouldBeFound("servState.specified=true");

        // Get all the masterAgreementList where servState is null
        defaultMasterAgreementShouldNotBeFound("servState.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByServStateContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where servState contains DEFAULT_SERV_STATE
        defaultMasterAgreementShouldBeFound("servState.contains=" + DEFAULT_SERV_STATE);

        // Get all the masterAgreementList where servState contains UPDATED_SERV_STATE
        defaultMasterAgreementShouldNotBeFound("servState.contains=" + UPDATED_SERV_STATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByServStateNotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where servState does not contain DEFAULT_SERV_STATE
        defaultMasterAgreementShouldNotBeFound("servState.doesNotContain=" + DEFAULT_SERV_STATE);

        // Get all the masterAgreementList where servState does not contain UPDATED_SERV_STATE
        defaultMasterAgreementShouldBeFound("servState.doesNotContain=" + UPDATED_SERV_STATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByGstExemptedIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where gstExempted equals to DEFAULT_GST_EXEMPTED
        defaultMasterAgreementShouldBeFound("gstExempted.equals=" + DEFAULT_GST_EXEMPTED);

        // Get all the masterAgreementList where gstExempted equals to UPDATED_GST_EXEMPTED
        defaultMasterAgreementShouldNotBeFound("gstExempted.equals=" + UPDATED_GST_EXEMPTED);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByGstExemptedIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where gstExempted in DEFAULT_GST_EXEMPTED or UPDATED_GST_EXEMPTED
        defaultMasterAgreementShouldBeFound("gstExempted.in=" + DEFAULT_GST_EXEMPTED + "," + UPDATED_GST_EXEMPTED);

        // Get all the masterAgreementList where gstExempted equals to UPDATED_GST_EXEMPTED
        defaultMasterAgreementShouldNotBeFound("gstExempted.in=" + UPDATED_GST_EXEMPTED);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByGstExemptedIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where gstExempted is not null
        defaultMasterAgreementShouldBeFound("gstExempted.specified=true");

        // Get all the masterAgreementList where gstExempted is null
        defaultMasterAgreementShouldNotBeFound("gstExempted.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByGstExemptedContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where gstExempted contains DEFAULT_GST_EXEMPTED
        defaultMasterAgreementShouldBeFound("gstExempted.contains=" + DEFAULT_GST_EXEMPTED);

        // Get all the masterAgreementList where gstExempted contains UPDATED_GST_EXEMPTED
        defaultMasterAgreementShouldNotBeFound("gstExempted.contains=" + UPDATED_GST_EXEMPTED);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByGstExemptedNotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where gstExempted does not contain DEFAULT_GST_EXEMPTED
        defaultMasterAgreementShouldNotBeFound("gstExempted.doesNotContain=" + DEFAULT_GST_EXEMPTED);

        // Get all the masterAgreementList where gstExempted does not contain UPDATED_GST_EXEMPTED
        defaultMasterAgreementShouldBeFound("gstExempted.doesNotContain=" + UPDATED_GST_EXEMPTED);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByPrefRepayModeIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where prefRepayMode equals to DEFAULT_PREF_REPAY_MODE
        defaultMasterAgreementShouldBeFound("prefRepayMode.equals=" + DEFAULT_PREF_REPAY_MODE);

        // Get all the masterAgreementList where prefRepayMode equals to UPDATED_PREF_REPAY_MODE
        defaultMasterAgreementShouldNotBeFound("prefRepayMode.equals=" + UPDATED_PREF_REPAY_MODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByPrefRepayModeIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where prefRepayMode in DEFAULT_PREF_REPAY_MODE or UPDATED_PREF_REPAY_MODE
        defaultMasterAgreementShouldBeFound("prefRepayMode.in=" + DEFAULT_PREF_REPAY_MODE + "," + UPDATED_PREF_REPAY_MODE);

        // Get all the masterAgreementList where prefRepayMode equals to UPDATED_PREF_REPAY_MODE
        defaultMasterAgreementShouldNotBeFound("prefRepayMode.in=" + UPDATED_PREF_REPAY_MODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByPrefRepayModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where prefRepayMode is not null
        defaultMasterAgreementShouldBeFound("prefRepayMode.specified=true");

        // Get all the masterAgreementList where prefRepayMode is null
        defaultMasterAgreementShouldNotBeFound("prefRepayMode.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByPrefRepayModeContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where prefRepayMode contains DEFAULT_PREF_REPAY_MODE
        defaultMasterAgreementShouldBeFound("prefRepayMode.contains=" + DEFAULT_PREF_REPAY_MODE);

        // Get all the masterAgreementList where prefRepayMode contains UPDATED_PREF_REPAY_MODE
        defaultMasterAgreementShouldNotBeFound("prefRepayMode.contains=" + UPDATED_PREF_REPAY_MODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByPrefRepayModeNotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where prefRepayMode does not contain DEFAULT_PREF_REPAY_MODE
        defaultMasterAgreementShouldNotBeFound("prefRepayMode.doesNotContain=" + DEFAULT_PREF_REPAY_MODE);

        // Get all the masterAgreementList where prefRepayMode does not contain UPDATED_PREF_REPAY_MODE
        defaultMasterAgreementShouldBeFound("prefRepayMode.doesNotContain=" + UPDATED_PREF_REPAY_MODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByTdsCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where tdsCode equals to DEFAULT_TDS_CODE
        defaultMasterAgreementShouldBeFound("tdsCode.equals=" + DEFAULT_TDS_CODE);

        // Get all the masterAgreementList where tdsCode equals to UPDATED_TDS_CODE
        defaultMasterAgreementShouldNotBeFound("tdsCode.equals=" + UPDATED_TDS_CODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByTdsCodeIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where tdsCode in DEFAULT_TDS_CODE or UPDATED_TDS_CODE
        defaultMasterAgreementShouldBeFound("tdsCode.in=" + DEFAULT_TDS_CODE + "," + UPDATED_TDS_CODE);

        // Get all the masterAgreementList where tdsCode equals to UPDATED_TDS_CODE
        defaultMasterAgreementShouldNotBeFound("tdsCode.in=" + UPDATED_TDS_CODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByTdsCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where tdsCode is not null
        defaultMasterAgreementShouldBeFound("tdsCode.specified=true");

        // Get all the masterAgreementList where tdsCode is null
        defaultMasterAgreementShouldNotBeFound("tdsCode.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByTdsCodeContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where tdsCode contains DEFAULT_TDS_CODE
        defaultMasterAgreementShouldBeFound("tdsCode.contains=" + DEFAULT_TDS_CODE);

        // Get all the masterAgreementList where tdsCode contains UPDATED_TDS_CODE
        defaultMasterAgreementShouldNotBeFound("tdsCode.contains=" + UPDATED_TDS_CODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByTdsCodeNotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where tdsCode does not contain DEFAULT_TDS_CODE
        defaultMasterAgreementShouldNotBeFound("tdsCode.doesNotContain=" + DEFAULT_TDS_CODE);

        // Get all the masterAgreementList where tdsCode does not contain UPDATED_TDS_CODE
        defaultMasterAgreementShouldBeFound("tdsCode.doesNotContain=" + UPDATED_TDS_CODE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByTdsRateIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where tdsRate equals to DEFAULT_TDS_RATE
        defaultMasterAgreementShouldBeFound("tdsRate.equals=" + DEFAULT_TDS_RATE);

        // Get all the masterAgreementList where tdsRate equals to UPDATED_TDS_RATE
        defaultMasterAgreementShouldNotBeFound("tdsRate.equals=" + UPDATED_TDS_RATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByTdsRateIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where tdsRate in DEFAULT_TDS_RATE or UPDATED_TDS_RATE
        defaultMasterAgreementShouldBeFound("tdsRate.in=" + DEFAULT_TDS_RATE + "," + UPDATED_TDS_RATE);

        // Get all the masterAgreementList where tdsRate equals to UPDATED_TDS_RATE
        defaultMasterAgreementShouldNotBeFound("tdsRate.in=" + UPDATED_TDS_RATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByTdsRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where tdsRate is not null
        defaultMasterAgreementShouldBeFound("tdsRate.specified=true");

        // Get all the masterAgreementList where tdsRate is null
        defaultMasterAgreementShouldNotBeFound("tdsRate.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByTdsRateContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where tdsRate contains DEFAULT_TDS_RATE
        defaultMasterAgreementShouldBeFound("tdsRate.contains=" + DEFAULT_TDS_RATE);

        // Get all the masterAgreementList where tdsRate contains UPDATED_TDS_RATE
        defaultMasterAgreementShouldNotBeFound("tdsRate.contains=" + UPDATED_TDS_RATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByTdsRateNotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where tdsRate does not contain DEFAULT_TDS_RATE
        defaultMasterAgreementShouldNotBeFound("tdsRate.doesNotContain=" + DEFAULT_TDS_RATE);

        // Get all the masterAgreementList where tdsRate does not contain UPDATED_TDS_RATE
        defaultMasterAgreementShouldBeFound("tdsRate.doesNotContain=" + UPDATED_TDS_RATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsBySanctionedAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where sanctionedAmount equals to DEFAULT_SANCTIONED_AMOUNT
        defaultMasterAgreementShouldBeFound("sanctionedAmount.equals=" + DEFAULT_SANCTIONED_AMOUNT);

        // Get all the masterAgreementList where sanctionedAmount equals to UPDATED_SANCTIONED_AMOUNT
        defaultMasterAgreementShouldNotBeFound("sanctionedAmount.equals=" + UPDATED_SANCTIONED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsBySanctionedAmountIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where sanctionedAmount in DEFAULT_SANCTIONED_AMOUNT or UPDATED_SANCTIONED_AMOUNT
        defaultMasterAgreementShouldBeFound("sanctionedAmount.in=" + DEFAULT_SANCTIONED_AMOUNT + "," + UPDATED_SANCTIONED_AMOUNT);

        // Get all the masterAgreementList where sanctionedAmount equals to UPDATED_SANCTIONED_AMOUNT
        defaultMasterAgreementShouldNotBeFound("sanctionedAmount.in=" + UPDATED_SANCTIONED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsBySanctionedAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where sanctionedAmount is not null
        defaultMasterAgreementShouldBeFound("sanctionedAmount.specified=true");

        // Get all the masterAgreementList where sanctionedAmount is null
        defaultMasterAgreementShouldNotBeFound("sanctionedAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsBySanctionedAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where sanctionedAmount is greater than or equal to DEFAULT_SANCTIONED_AMOUNT
        defaultMasterAgreementShouldBeFound("sanctionedAmount.greaterThanOrEqual=" + DEFAULT_SANCTIONED_AMOUNT);

        // Get all the masterAgreementList where sanctionedAmount is greater than or equal to UPDATED_SANCTIONED_AMOUNT
        defaultMasterAgreementShouldNotBeFound("sanctionedAmount.greaterThanOrEqual=" + UPDATED_SANCTIONED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsBySanctionedAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where sanctionedAmount is less than or equal to DEFAULT_SANCTIONED_AMOUNT
        defaultMasterAgreementShouldBeFound("sanctionedAmount.lessThanOrEqual=" + DEFAULT_SANCTIONED_AMOUNT);

        // Get all the masterAgreementList where sanctionedAmount is less than or equal to SMALLER_SANCTIONED_AMOUNT
        defaultMasterAgreementShouldNotBeFound("sanctionedAmount.lessThanOrEqual=" + SMALLER_SANCTIONED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsBySanctionedAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where sanctionedAmount is less than DEFAULT_SANCTIONED_AMOUNT
        defaultMasterAgreementShouldNotBeFound("sanctionedAmount.lessThan=" + DEFAULT_SANCTIONED_AMOUNT);

        // Get all the masterAgreementList where sanctionedAmount is less than UPDATED_SANCTIONED_AMOUNT
        defaultMasterAgreementShouldBeFound("sanctionedAmount.lessThan=" + UPDATED_SANCTIONED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsBySanctionedAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where sanctionedAmount is greater than DEFAULT_SANCTIONED_AMOUNT
        defaultMasterAgreementShouldNotBeFound("sanctionedAmount.greaterThan=" + DEFAULT_SANCTIONED_AMOUNT);

        // Get all the masterAgreementList where sanctionedAmount is greater than SMALLER_SANCTIONED_AMOUNT
        defaultMasterAgreementShouldBeFound("sanctionedAmount.greaterThan=" + SMALLER_SANCTIONED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByOriginationApplnNoIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where originationApplnNo equals to DEFAULT_ORIGINATION_APPLN_NO
        defaultMasterAgreementShouldBeFound("originationApplnNo.equals=" + DEFAULT_ORIGINATION_APPLN_NO);

        // Get all the masterAgreementList where originationApplnNo equals to UPDATED_ORIGINATION_APPLN_NO
        defaultMasterAgreementShouldNotBeFound("originationApplnNo.equals=" + UPDATED_ORIGINATION_APPLN_NO);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByOriginationApplnNoIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where originationApplnNo in DEFAULT_ORIGINATION_APPLN_NO or UPDATED_ORIGINATION_APPLN_NO
        defaultMasterAgreementShouldBeFound("originationApplnNo.in=" + DEFAULT_ORIGINATION_APPLN_NO + "," + UPDATED_ORIGINATION_APPLN_NO);

        // Get all the masterAgreementList where originationApplnNo equals to UPDATED_ORIGINATION_APPLN_NO
        defaultMasterAgreementShouldNotBeFound("originationApplnNo.in=" + UPDATED_ORIGINATION_APPLN_NO);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByOriginationApplnNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where originationApplnNo is not null
        defaultMasterAgreementShouldBeFound("originationApplnNo.specified=true");

        // Get all the masterAgreementList where originationApplnNo is null
        defaultMasterAgreementShouldNotBeFound("originationApplnNo.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByOriginationApplnNoContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where originationApplnNo contains DEFAULT_ORIGINATION_APPLN_NO
        defaultMasterAgreementShouldBeFound("originationApplnNo.contains=" + DEFAULT_ORIGINATION_APPLN_NO);

        // Get all the masterAgreementList where originationApplnNo contains UPDATED_ORIGINATION_APPLN_NO
        defaultMasterAgreementShouldNotBeFound("originationApplnNo.contains=" + UPDATED_ORIGINATION_APPLN_NO);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByOriginationApplnNoNotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where originationApplnNo does not contain DEFAULT_ORIGINATION_APPLN_NO
        defaultMasterAgreementShouldNotBeFound("originationApplnNo.doesNotContain=" + DEFAULT_ORIGINATION_APPLN_NO);

        // Get all the masterAgreementList where originationApplnNo does not contain UPDATED_ORIGINATION_APPLN_NO
        defaultMasterAgreementShouldBeFound("originationApplnNo.doesNotContain=" + UPDATED_ORIGINATION_APPLN_NO);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByCycleDayIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where cycleDay equals to DEFAULT_CYCLE_DAY
        defaultMasterAgreementShouldBeFound("cycleDay.equals=" + DEFAULT_CYCLE_DAY);

        // Get all the masterAgreementList where cycleDay equals to UPDATED_CYCLE_DAY
        defaultMasterAgreementShouldNotBeFound("cycleDay.equals=" + UPDATED_CYCLE_DAY);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByCycleDayIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where cycleDay in DEFAULT_CYCLE_DAY or UPDATED_CYCLE_DAY
        defaultMasterAgreementShouldBeFound("cycleDay.in=" + DEFAULT_CYCLE_DAY + "," + UPDATED_CYCLE_DAY);

        // Get all the masterAgreementList where cycleDay equals to UPDATED_CYCLE_DAY
        defaultMasterAgreementShouldNotBeFound("cycleDay.in=" + UPDATED_CYCLE_DAY);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByCycleDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where cycleDay is not null
        defaultMasterAgreementShouldBeFound("cycleDay.specified=true");

        // Get all the masterAgreementList where cycleDay is null
        defaultMasterAgreementShouldNotBeFound("cycleDay.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByCycleDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where cycleDay is greater than or equal to DEFAULT_CYCLE_DAY
        defaultMasterAgreementShouldBeFound("cycleDay.greaterThanOrEqual=" + DEFAULT_CYCLE_DAY);

        // Get all the masterAgreementList where cycleDay is greater than or equal to UPDATED_CYCLE_DAY
        defaultMasterAgreementShouldNotBeFound("cycleDay.greaterThanOrEqual=" + UPDATED_CYCLE_DAY);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByCycleDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where cycleDay is less than or equal to DEFAULT_CYCLE_DAY
        defaultMasterAgreementShouldBeFound("cycleDay.lessThanOrEqual=" + DEFAULT_CYCLE_DAY);

        // Get all the masterAgreementList where cycleDay is less than or equal to SMALLER_CYCLE_DAY
        defaultMasterAgreementShouldNotBeFound("cycleDay.lessThanOrEqual=" + SMALLER_CYCLE_DAY);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByCycleDayIsLessThanSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where cycleDay is less than DEFAULT_CYCLE_DAY
        defaultMasterAgreementShouldNotBeFound("cycleDay.lessThan=" + DEFAULT_CYCLE_DAY);

        // Get all the masterAgreementList where cycleDay is less than UPDATED_CYCLE_DAY
        defaultMasterAgreementShouldBeFound("cycleDay.lessThan=" + UPDATED_CYCLE_DAY);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByCycleDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where cycleDay is greater than DEFAULT_CYCLE_DAY
        defaultMasterAgreementShouldNotBeFound("cycleDay.greaterThan=" + DEFAULT_CYCLE_DAY);

        // Get all the masterAgreementList where cycleDay is greater than SMALLER_CYCLE_DAY
        defaultMasterAgreementShouldBeFound("cycleDay.greaterThan=" + SMALLER_CYCLE_DAY);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByTenorIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where tenor equals to DEFAULT_TENOR
        defaultMasterAgreementShouldBeFound("tenor.equals=" + DEFAULT_TENOR);

        // Get all the masterAgreementList where tenor equals to UPDATED_TENOR
        defaultMasterAgreementShouldNotBeFound("tenor.equals=" + UPDATED_TENOR);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByTenorIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where tenor in DEFAULT_TENOR or UPDATED_TENOR
        defaultMasterAgreementShouldBeFound("tenor.in=" + DEFAULT_TENOR + "," + UPDATED_TENOR);

        // Get all the masterAgreementList where tenor equals to UPDATED_TENOR
        defaultMasterAgreementShouldNotBeFound("tenor.in=" + UPDATED_TENOR);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByTenorIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where tenor is not null
        defaultMasterAgreementShouldBeFound("tenor.specified=true");

        // Get all the masterAgreementList where tenor is null
        defaultMasterAgreementShouldNotBeFound("tenor.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByTenorContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where tenor contains DEFAULT_TENOR
        defaultMasterAgreementShouldBeFound("tenor.contains=" + DEFAULT_TENOR);

        // Get all the masterAgreementList where tenor contains UPDATED_TENOR
        defaultMasterAgreementShouldNotBeFound("tenor.contains=" + UPDATED_TENOR);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByTenorNotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where tenor does not contain DEFAULT_TENOR
        defaultMasterAgreementShouldNotBeFound("tenor.doesNotContain=" + DEFAULT_TENOR);

        // Get all the masterAgreementList where tenor does not contain UPDATED_TENOR
        defaultMasterAgreementShouldBeFound("tenor.doesNotContain=" + UPDATED_TENOR);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByInterestRateIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where interestRate equals to DEFAULT_INTEREST_RATE
        defaultMasterAgreementShouldBeFound("interestRate.equals=" + DEFAULT_INTEREST_RATE);

        // Get all the masterAgreementList where interestRate equals to UPDATED_INTEREST_RATE
        defaultMasterAgreementShouldNotBeFound("interestRate.equals=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByInterestRateIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where interestRate in DEFAULT_INTEREST_RATE or UPDATED_INTEREST_RATE
        defaultMasterAgreementShouldBeFound("interestRate.in=" + DEFAULT_INTEREST_RATE + "," + UPDATED_INTEREST_RATE);

        // Get all the masterAgreementList where interestRate equals to UPDATED_INTEREST_RATE
        defaultMasterAgreementShouldNotBeFound("interestRate.in=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByInterestRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where interestRate is not null
        defaultMasterAgreementShouldBeFound("interestRate.specified=true");

        // Get all the masterAgreementList where interestRate is null
        defaultMasterAgreementShouldNotBeFound("interestRate.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByInterestRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where interestRate is greater than or equal to DEFAULT_INTEREST_RATE
        defaultMasterAgreementShouldBeFound("interestRate.greaterThanOrEqual=" + DEFAULT_INTEREST_RATE);

        // Get all the masterAgreementList where interestRate is greater than or equal to UPDATED_INTEREST_RATE
        defaultMasterAgreementShouldNotBeFound("interestRate.greaterThanOrEqual=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByInterestRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where interestRate is less than or equal to DEFAULT_INTEREST_RATE
        defaultMasterAgreementShouldBeFound("interestRate.lessThanOrEqual=" + DEFAULT_INTEREST_RATE);

        // Get all the masterAgreementList where interestRate is less than or equal to SMALLER_INTEREST_RATE
        defaultMasterAgreementShouldNotBeFound("interestRate.lessThanOrEqual=" + SMALLER_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByInterestRateIsLessThanSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where interestRate is less than DEFAULT_INTEREST_RATE
        defaultMasterAgreementShouldNotBeFound("interestRate.lessThan=" + DEFAULT_INTEREST_RATE);

        // Get all the masterAgreementList where interestRate is less than UPDATED_INTEREST_RATE
        defaultMasterAgreementShouldBeFound("interestRate.lessThan=" + UPDATED_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByInterestRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where interestRate is greater than DEFAULT_INTEREST_RATE
        defaultMasterAgreementShouldNotBeFound("interestRate.greaterThan=" + DEFAULT_INTEREST_RATE);

        // Get all the masterAgreementList where interestRate is greater than SMALLER_INTEREST_RATE
        defaultMasterAgreementShouldBeFound("interestRate.greaterThan=" + SMALLER_INTEREST_RATE);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByRepayFreqIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where repayFreq equals to DEFAULT_REPAY_FREQ
        defaultMasterAgreementShouldBeFound("repayFreq.equals=" + DEFAULT_REPAY_FREQ);

        // Get all the masterAgreementList where repayFreq equals to UPDATED_REPAY_FREQ
        defaultMasterAgreementShouldNotBeFound("repayFreq.equals=" + UPDATED_REPAY_FREQ);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByRepayFreqIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where repayFreq in DEFAULT_REPAY_FREQ or UPDATED_REPAY_FREQ
        defaultMasterAgreementShouldBeFound("repayFreq.in=" + DEFAULT_REPAY_FREQ + "," + UPDATED_REPAY_FREQ);

        // Get all the masterAgreementList where repayFreq equals to UPDATED_REPAY_FREQ
        defaultMasterAgreementShouldNotBeFound("repayFreq.in=" + UPDATED_REPAY_FREQ);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByRepayFreqIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where repayFreq is not null
        defaultMasterAgreementShouldBeFound("repayFreq.specified=true");

        // Get all the masterAgreementList where repayFreq is null
        defaultMasterAgreementShouldNotBeFound("repayFreq.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where isDeleted equals to DEFAULT_IS_DELETED
        defaultMasterAgreementShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the masterAgreementList where isDeleted equals to UPDATED_IS_DELETED
        defaultMasterAgreementShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultMasterAgreementShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the masterAgreementList where isDeleted equals to UPDATED_IS_DELETED
        defaultMasterAgreementShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where isDeleted is not null
        defaultMasterAgreementShouldBeFound("isDeleted.specified=true");

        // Get all the masterAgreementList where isDeleted is null
        defaultMasterAgreementShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultMasterAgreementShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the masterAgreementList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMasterAgreementShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultMasterAgreementShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the masterAgreementList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMasterAgreementShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where lastModified is not null
        defaultMasterAgreementShouldBeFound("lastModified.specified=true");

        // Get all the masterAgreementList where lastModified is null
        defaultMasterAgreementShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultMasterAgreementShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the masterAgreementList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMasterAgreementShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultMasterAgreementShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the masterAgreementList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMasterAgreementShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where lastModifiedBy is not null
        defaultMasterAgreementShouldBeFound("lastModifiedBy.specified=true");

        // Get all the masterAgreementList where lastModifiedBy is null
        defaultMasterAgreementShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultMasterAgreementShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the masterAgreementList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultMasterAgreementShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultMasterAgreementShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the masterAgreementList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultMasterAgreementShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultMasterAgreementShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the masterAgreementList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultMasterAgreementShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultMasterAgreementShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the masterAgreementList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultMasterAgreementShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField1 is not null
        defaultMasterAgreementShouldBeFound("freeField1.specified=true");

        // Get all the masterAgreementList where freeField1 is null
        defaultMasterAgreementShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultMasterAgreementShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the masterAgreementList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultMasterAgreementShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultMasterAgreementShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the masterAgreementList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultMasterAgreementShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultMasterAgreementShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the masterAgreementList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultMasterAgreementShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultMasterAgreementShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the masterAgreementList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultMasterAgreementShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField2 is not null
        defaultMasterAgreementShouldBeFound("freeField2.specified=true");

        // Get all the masterAgreementList where freeField2 is null
        defaultMasterAgreementShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultMasterAgreementShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the masterAgreementList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultMasterAgreementShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultMasterAgreementShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the masterAgreementList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultMasterAgreementShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultMasterAgreementShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the masterAgreementList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultMasterAgreementShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultMasterAgreementShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the masterAgreementList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultMasterAgreementShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField3 is not null
        defaultMasterAgreementShouldBeFound("freeField3.specified=true");

        // Get all the masterAgreementList where freeField3 is null
        defaultMasterAgreementShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultMasterAgreementShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the masterAgreementList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultMasterAgreementShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultMasterAgreementShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the masterAgreementList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultMasterAgreementShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultMasterAgreementShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the masterAgreementList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultMasterAgreementShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultMasterAgreementShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the masterAgreementList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultMasterAgreementShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField4 is not null
        defaultMasterAgreementShouldBeFound("freeField4.specified=true");

        // Get all the masterAgreementList where freeField4 is null
        defaultMasterAgreementShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultMasterAgreementShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the masterAgreementList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultMasterAgreementShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMasterAgreementsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        // Get all the masterAgreementList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultMasterAgreementShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the masterAgreementList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultMasterAgreementShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMasterAgreementShouldBeFound(String filter) throws Exception {
        restMasterAgreementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(masterAgreement.getId().intValue())))
            .andExpect(jsonPath("$.[*].memberId").value(hasItem(DEFAULT_MEMBER_ID)))
            .andExpect(jsonPath("$.[*].portfolioCode").value(hasItem(DEFAULT_PORTFOLIO_CODE)))
            .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE)))
            .andExpect(jsonPath("$.[*].homeBranch").value(hasItem(DEFAULT_HOME_BRANCH)))
            .andExpect(jsonPath("$.[*].servBranch").value(hasItem(DEFAULT_SERV_BRANCH)))
            .andExpect(jsonPath("$.[*].homeState").value(hasItem(DEFAULT_HOME_STATE)))
            .andExpect(jsonPath("$.[*].servState").value(hasItem(DEFAULT_SERV_STATE)))
            .andExpect(jsonPath("$.[*].gstExempted").value(hasItem(DEFAULT_GST_EXEMPTED)))
            .andExpect(jsonPath("$.[*].prefRepayMode").value(hasItem(DEFAULT_PREF_REPAY_MODE)))
            .andExpect(jsonPath("$.[*].tdsCode").value(hasItem(DEFAULT_TDS_CODE)))
            .andExpect(jsonPath("$.[*].tdsRate").value(hasItem(DEFAULT_TDS_RATE)))
            .andExpect(jsonPath("$.[*].sanctionedAmount").value(hasItem(DEFAULT_SANCTIONED_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].originationApplnNo").value(hasItem(DEFAULT_ORIGINATION_APPLN_NO)))
            .andExpect(jsonPath("$.[*].cycleDay").value(hasItem(DEFAULT_CYCLE_DAY.intValue())))
            .andExpect(jsonPath("$.[*].tenor").value(hasItem(DEFAULT_TENOR)))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].repayFreq").value(hasItem(DEFAULT_REPAY_FREQ.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));

        // Check, that the count call also returns 1
        restMasterAgreementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMasterAgreementShouldNotBeFound(String filter) throws Exception {
        restMasterAgreementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMasterAgreementMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMasterAgreement() throws Exception {
        // Get the masterAgreement
        restMasterAgreementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMasterAgreement() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        int databaseSizeBeforeUpdate = masterAgreementRepository.findAll().size();

        // Update the masterAgreement
        MasterAgreement updatedMasterAgreement = masterAgreementRepository.findById(masterAgreement.getId()).get();
        // Disconnect from session so that the updates on updatedMasterAgreement are not directly saved in db
        em.detach(updatedMasterAgreement);
        updatedMasterAgreement
            .memberId(UPDATED_MEMBER_ID)
            .portfolioCode(UPDATED_PORTFOLIO_CODE)
            .productCode(UPDATED_PRODUCT_CODE)
            .homeBranch(UPDATED_HOME_BRANCH)
            .servBranch(UPDATED_SERV_BRANCH)
            .homeState(UPDATED_HOME_STATE)
            .servState(UPDATED_SERV_STATE)
            .gstExempted(UPDATED_GST_EXEMPTED)
            .prefRepayMode(UPDATED_PREF_REPAY_MODE)
            .tdsCode(UPDATED_TDS_CODE)
            .tdsRate(UPDATED_TDS_RATE)
            .sanctionedAmount(UPDATED_SANCTIONED_AMOUNT)
            .originationApplnNo(UPDATED_ORIGINATION_APPLN_NO)
            .cycleDay(UPDATED_CYCLE_DAY)
            .tenor(UPDATED_TENOR)
            .interestRate(UPDATED_INTEREST_RATE)
            .repayFreq(UPDATED_REPAY_FREQ)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        MasterAgreementDTO masterAgreementDTO = masterAgreementMapper.toDto(updatedMasterAgreement);

        restMasterAgreementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, masterAgreementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(masterAgreementDTO))
            )
            .andExpect(status().isOk());

        // Validate the MasterAgreement in the database
        List<MasterAgreement> masterAgreementList = masterAgreementRepository.findAll();
        assertThat(masterAgreementList).hasSize(databaseSizeBeforeUpdate);
        MasterAgreement testMasterAgreement = masterAgreementList.get(masterAgreementList.size() - 1);
        assertThat(testMasterAgreement.getMemberId()).isEqualTo(UPDATED_MEMBER_ID);
        assertThat(testMasterAgreement.getPortfolioCode()).isEqualTo(UPDATED_PORTFOLIO_CODE);
        assertThat(testMasterAgreement.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testMasterAgreement.getHomeBranch()).isEqualTo(UPDATED_HOME_BRANCH);
        assertThat(testMasterAgreement.getServBranch()).isEqualTo(UPDATED_SERV_BRANCH);
        assertThat(testMasterAgreement.getHomeState()).isEqualTo(UPDATED_HOME_STATE);
        assertThat(testMasterAgreement.getServState()).isEqualTo(UPDATED_SERV_STATE);
        assertThat(testMasterAgreement.getGstExempted()).isEqualTo(UPDATED_GST_EXEMPTED);
        assertThat(testMasterAgreement.getPrefRepayMode()).isEqualTo(UPDATED_PREF_REPAY_MODE);
        assertThat(testMasterAgreement.getTdsCode()).isEqualTo(UPDATED_TDS_CODE);
        assertThat(testMasterAgreement.getTdsRate()).isEqualTo(UPDATED_TDS_RATE);
        assertThat(testMasterAgreement.getSanctionedAmount()).isEqualTo(UPDATED_SANCTIONED_AMOUNT);
        assertThat(testMasterAgreement.getOriginationApplnNo()).isEqualTo(UPDATED_ORIGINATION_APPLN_NO);
        assertThat(testMasterAgreement.getCycleDay()).isEqualTo(UPDATED_CYCLE_DAY);
        assertThat(testMasterAgreement.getTenor()).isEqualTo(UPDATED_TENOR);
        assertThat(testMasterAgreement.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testMasterAgreement.getRepayFreq()).isEqualTo(UPDATED_REPAY_FREQ);
        assertThat(testMasterAgreement.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMasterAgreement.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMasterAgreement.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMasterAgreement.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMasterAgreement.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMasterAgreement.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMasterAgreement.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void putNonExistingMasterAgreement() throws Exception {
        int databaseSizeBeforeUpdate = masterAgreementRepository.findAll().size();
        masterAgreement.setId(count.incrementAndGet());

        // Create the MasterAgreement
        MasterAgreementDTO masterAgreementDTO = masterAgreementMapper.toDto(masterAgreement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMasterAgreementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, masterAgreementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(masterAgreementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterAgreement in the database
        List<MasterAgreement> masterAgreementList = masterAgreementRepository.findAll();
        assertThat(masterAgreementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMasterAgreement() throws Exception {
        int databaseSizeBeforeUpdate = masterAgreementRepository.findAll().size();
        masterAgreement.setId(count.incrementAndGet());

        // Create the MasterAgreement
        MasterAgreementDTO masterAgreementDTO = masterAgreementMapper.toDto(masterAgreement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMasterAgreementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(masterAgreementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterAgreement in the database
        List<MasterAgreement> masterAgreementList = masterAgreementRepository.findAll();
        assertThat(masterAgreementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMasterAgreement() throws Exception {
        int databaseSizeBeforeUpdate = masterAgreementRepository.findAll().size();
        masterAgreement.setId(count.incrementAndGet());

        // Create the MasterAgreement
        MasterAgreementDTO masterAgreementDTO = masterAgreementMapper.toDto(masterAgreement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMasterAgreementMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(masterAgreementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MasterAgreement in the database
        List<MasterAgreement> masterAgreementList = masterAgreementRepository.findAll();
        assertThat(masterAgreementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMasterAgreementWithPatch() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        int databaseSizeBeforeUpdate = masterAgreementRepository.findAll().size();

        // Update the masterAgreement using partial update
        MasterAgreement partialUpdatedMasterAgreement = new MasterAgreement();
        partialUpdatedMasterAgreement.setId(masterAgreement.getId());

        partialUpdatedMasterAgreement
            .productCode(UPDATED_PRODUCT_CODE)
            .servBranch(UPDATED_SERV_BRANCH)
            .homeState(UPDATED_HOME_STATE)
            .tdsCode(UPDATED_TDS_CODE)
            .sanctionedAmount(UPDATED_SANCTIONED_AMOUNT)
            .cycleDay(UPDATED_CYCLE_DAY)
            .tenor(UPDATED_TENOR)
            .interestRate(UPDATED_INTEREST_RATE)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restMasterAgreementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMasterAgreement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMasterAgreement))
            )
            .andExpect(status().isOk());

        // Validate the MasterAgreement in the database
        List<MasterAgreement> masterAgreementList = masterAgreementRepository.findAll();
        assertThat(masterAgreementList).hasSize(databaseSizeBeforeUpdate);
        MasterAgreement testMasterAgreement = masterAgreementList.get(masterAgreementList.size() - 1);
        assertThat(testMasterAgreement.getMemberId()).isEqualTo(DEFAULT_MEMBER_ID);
        assertThat(testMasterAgreement.getPortfolioCode()).isEqualTo(DEFAULT_PORTFOLIO_CODE);
        assertThat(testMasterAgreement.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testMasterAgreement.getHomeBranch()).isEqualTo(DEFAULT_HOME_BRANCH);
        assertThat(testMasterAgreement.getServBranch()).isEqualTo(UPDATED_SERV_BRANCH);
        assertThat(testMasterAgreement.getHomeState()).isEqualTo(UPDATED_HOME_STATE);
        assertThat(testMasterAgreement.getServState()).isEqualTo(DEFAULT_SERV_STATE);
        assertThat(testMasterAgreement.getGstExempted()).isEqualTo(DEFAULT_GST_EXEMPTED);
        assertThat(testMasterAgreement.getPrefRepayMode()).isEqualTo(DEFAULT_PREF_REPAY_MODE);
        assertThat(testMasterAgreement.getTdsCode()).isEqualTo(UPDATED_TDS_CODE);
        assertThat(testMasterAgreement.getTdsRate()).isEqualTo(DEFAULT_TDS_RATE);
        assertThat(testMasterAgreement.getSanctionedAmount()).isEqualTo(UPDATED_SANCTIONED_AMOUNT);
        assertThat(testMasterAgreement.getOriginationApplnNo()).isEqualTo(DEFAULT_ORIGINATION_APPLN_NO);
        assertThat(testMasterAgreement.getCycleDay()).isEqualTo(UPDATED_CYCLE_DAY);
        assertThat(testMasterAgreement.getTenor()).isEqualTo(UPDATED_TENOR);
        assertThat(testMasterAgreement.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testMasterAgreement.getRepayFreq()).isEqualTo(DEFAULT_REPAY_FREQ);
        assertThat(testMasterAgreement.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMasterAgreement.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMasterAgreement.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMasterAgreement.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testMasterAgreement.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMasterAgreement.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMasterAgreement.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void fullUpdateMasterAgreementWithPatch() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        int databaseSizeBeforeUpdate = masterAgreementRepository.findAll().size();

        // Update the masterAgreement using partial update
        MasterAgreement partialUpdatedMasterAgreement = new MasterAgreement();
        partialUpdatedMasterAgreement.setId(masterAgreement.getId());

        partialUpdatedMasterAgreement
            .memberId(UPDATED_MEMBER_ID)
            .portfolioCode(UPDATED_PORTFOLIO_CODE)
            .productCode(UPDATED_PRODUCT_CODE)
            .homeBranch(UPDATED_HOME_BRANCH)
            .servBranch(UPDATED_SERV_BRANCH)
            .homeState(UPDATED_HOME_STATE)
            .servState(UPDATED_SERV_STATE)
            .gstExempted(UPDATED_GST_EXEMPTED)
            .prefRepayMode(UPDATED_PREF_REPAY_MODE)
            .tdsCode(UPDATED_TDS_CODE)
            .tdsRate(UPDATED_TDS_RATE)
            .sanctionedAmount(UPDATED_SANCTIONED_AMOUNT)
            .originationApplnNo(UPDATED_ORIGINATION_APPLN_NO)
            .cycleDay(UPDATED_CYCLE_DAY)
            .tenor(UPDATED_TENOR)
            .interestRate(UPDATED_INTEREST_RATE)
            .repayFreq(UPDATED_REPAY_FREQ)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restMasterAgreementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMasterAgreement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMasterAgreement))
            )
            .andExpect(status().isOk());

        // Validate the MasterAgreement in the database
        List<MasterAgreement> masterAgreementList = masterAgreementRepository.findAll();
        assertThat(masterAgreementList).hasSize(databaseSizeBeforeUpdate);
        MasterAgreement testMasterAgreement = masterAgreementList.get(masterAgreementList.size() - 1);
        assertThat(testMasterAgreement.getMemberId()).isEqualTo(UPDATED_MEMBER_ID);
        assertThat(testMasterAgreement.getPortfolioCode()).isEqualTo(UPDATED_PORTFOLIO_CODE);
        assertThat(testMasterAgreement.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testMasterAgreement.getHomeBranch()).isEqualTo(UPDATED_HOME_BRANCH);
        assertThat(testMasterAgreement.getServBranch()).isEqualTo(UPDATED_SERV_BRANCH);
        assertThat(testMasterAgreement.getHomeState()).isEqualTo(UPDATED_HOME_STATE);
        assertThat(testMasterAgreement.getServState()).isEqualTo(UPDATED_SERV_STATE);
        assertThat(testMasterAgreement.getGstExempted()).isEqualTo(UPDATED_GST_EXEMPTED);
        assertThat(testMasterAgreement.getPrefRepayMode()).isEqualTo(UPDATED_PREF_REPAY_MODE);
        assertThat(testMasterAgreement.getTdsCode()).isEqualTo(UPDATED_TDS_CODE);
        assertThat(testMasterAgreement.getTdsRate()).isEqualTo(UPDATED_TDS_RATE);
        assertThat(testMasterAgreement.getSanctionedAmount()).isEqualTo(UPDATED_SANCTIONED_AMOUNT);
        assertThat(testMasterAgreement.getOriginationApplnNo()).isEqualTo(UPDATED_ORIGINATION_APPLN_NO);
        assertThat(testMasterAgreement.getCycleDay()).isEqualTo(UPDATED_CYCLE_DAY);
        assertThat(testMasterAgreement.getTenor()).isEqualTo(UPDATED_TENOR);
        assertThat(testMasterAgreement.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testMasterAgreement.getRepayFreq()).isEqualTo(UPDATED_REPAY_FREQ);
        assertThat(testMasterAgreement.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMasterAgreement.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMasterAgreement.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMasterAgreement.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMasterAgreement.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMasterAgreement.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMasterAgreement.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void patchNonExistingMasterAgreement() throws Exception {
        int databaseSizeBeforeUpdate = masterAgreementRepository.findAll().size();
        masterAgreement.setId(count.incrementAndGet());

        // Create the MasterAgreement
        MasterAgreementDTO masterAgreementDTO = masterAgreementMapper.toDto(masterAgreement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMasterAgreementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, masterAgreementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(masterAgreementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterAgreement in the database
        List<MasterAgreement> masterAgreementList = masterAgreementRepository.findAll();
        assertThat(masterAgreementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMasterAgreement() throws Exception {
        int databaseSizeBeforeUpdate = masterAgreementRepository.findAll().size();
        masterAgreement.setId(count.incrementAndGet());

        // Create the MasterAgreement
        MasterAgreementDTO masterAgreementDTO = masterAgreementMapper.toDto(masterAgreement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMasterAgreementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(masterAgreementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MasterAgreement in the database
        List<MasterAgreement> masterAgreementList = masterAgreementRepository.findAll();
        assertThat(masterAgreementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMasterAgreement() throws Exception {
        int databaseSizeBeforeUpdate = masterAgreementRepository.findAll().size();
        masterAgreement.setId(count.incrementAndGet());

        // Create the MasterAgreement
        MasterAgreementDTO masterAgreementDTO = masterAgreementMapper.toDto(masterAgreement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMasterAgreementMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(masterAgreementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MasterAgreement in the database
        List<MasterAgreement> masterAgreementList = masterAgreementRepository.findAll();
        assertThat(masterAgreementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMasterAgreement() throws Exception {
        // Initialize the database
        masterAgreementRepository.saveAndFlush(masterAgreement);

        int databaseSizeBeforeDelete = masterAgreementRepository.findAll().size();

        // Delete the masterAgreement
        restMasterAgreementMockMvc
            .perform(delete(ENTITY_API_URL_ID, masterAgreement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MasterAgreement> masterAgreementList = masterAgreementRepository.findAll();
        assertThat(masterAgreementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
