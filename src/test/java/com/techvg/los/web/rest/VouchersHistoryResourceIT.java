package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.VouchersHistory;
import com.techvg.los.domain.enumeration.VoucherCode;
import com.techvg.los.repository.VouchersHistoryRepository;
import com.techvg.los.service.criteria.VouchersHistoryCriteria;
import com.techvg.los.service.dto.VouchersHistoryDTO;
import com.techvg.los.service.mapper.VouchersHistoryMapper;
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
 * Integration tests for the {@link VouchersHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VouchersHistoryResourceIT {

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_VOUCHER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VOUCHER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final VoucherCode DEFAULT_CODE = VoucherCode.LOAN;
    private static final VoucherCode UPDATED_CODE = VoucherCode.DEPOSIT;

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

    private static final String ENTITY_API_URL = "/api/vouchers-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VouchersHistoryRepository vouchersHistoryRepository;

    @Autowired
    private VouchersHistoryMapper vouchersHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVouchersHistoryMockMvc;

    private VouchersHistory vouchersHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VouchersHistory createEntity(EntityManager em) {
        VouchersHistory vouchersHistory = new VouchersHistory()
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .voucherDate(DEFAULT_VOUCHER_DATE)
            .code(DEFAULT_CODE)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5)
            .freeField6(DEFAULT_FREE_FIELD_6);
        return vouchersHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VouchersHistory createUpdatedEntity(EntityManager em) {
        VouchersHistory vouchersHistory = new VouchersHistory()
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .voucherDate(UPDATED_VOUCHER_DATE)
            .code(UPDATED_CODE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        return vouchersHistory;
    }

    @BeforeEach
    public void initTest() {
        vouchersHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createVouchersHistory() throws Exception {
        int databaseSizeBeforeCreate = vouchersHistoryRepository.findAll().size();
        // Create the VouchersHistory
        VouchersHistoryDTO vouchersHistoryDTO = vouchersHistoryMapper.toDto(vouchersHistory);
        restVouchersHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vouchersHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VouchersHistory in the database
        List<VouchersHistory> vouchersHistoryList = vouchersHistoryRepository.findAll();
        assertThat(vouchersHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        VouchersHistory testVouchersHistory = vouchersHistoryList.get(vouchersHistoryList.size() - 1);
        assertThat(testVouchersHistory.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testVouchersHistory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testVouchersHistory.getVoucherDate()).isEqualTo(DEFAULT_VOUCHER_DATE);
        assertThat(testVouchersHistory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testVouchersHistory.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testVouchersHistory.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testVouchersHistory.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testVouchersHistory.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testVouchersHistory.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testVouchersHistory.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createVouchersHistoryWithExistingId() throws Exception {
        // Create the VouchersHistory with an existing ID
        vouchersHistory.setId(1L);
        VouchersHistoryDTO vouchersHistoryDTO = vouchersHistoryMapper.toDto(vouchersHistory);

        int databaseSizeBeforeCreate = vouchersHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVouchersHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vouchersHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VouchersHistory in the database
        List<VouchersHistory> vouchersHistoryList = vouchersHistoryRepository.findAll();
        assertThat(vouchersHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVouchersHistories() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList
        restVouchersHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vouchersHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));
    }

    @Test
    @Transactional
    void getVouchersHistory() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get the vouchersHistory
        restVouchersHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, vouchersHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vouchersHistory.getId().intValue()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.voucherDate").value(DEFAULT_VOUCHER_DATE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.freeField5").value(DEFAULT_FREE_FIELD_5))
            .andExpect(jsonPath("$.freeField6").value(DEFAULT_FREE_FIELD_6));
    }

    @Test
    @Transactional
    void getVouchersHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        Long id = vouchersHistory.getId();

        defaultVouchersHistoryShouldBeFound("id.equals=" + id);
        defaultVouchersHistoryShouldNotBeFound("id.notEquals=" + id);

        defaultVouchersHistoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVouchersHistoryShouldNotBeFound("id.greaterThan=" + id);

        defaultVouchersHistoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVouchersHistoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where createdOn equals to DEFAULT_CREATED_ON
        defaultVouchersHistoryShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the vouchersHistoryList where createdOn equals to UPDATED_CREATED_ON
        defaultVouchersHistoryShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultVouchersHistoryShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the vouchersHistoryList where createdOn equals to UPDATED_CREATED_ON
        defaultVouchersHistoryShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where createdOn is not null
        defaultVouchersHistoryShouldBeFound("createdOn.specified=true");

        // Get all the vouchersHistoryList where createdOn is null
        defaultVouchersHistoryShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where createdBy equals to DEFAULT_CREATED_BY
        defaultVouchersHistoryShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the vouchersHistoryList where createdBy equals to UPDATED_CREATED_BY
        defaultVouchersHistoryShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultVouchersHistoryShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the vouchersHistoryList where createdBy equals to UPDATED_CREATED_BY
        defaultVouchersHistoryShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where createdBy is not null
        defaultVouchersHistoryShouldBeFound("createdBy.specified=true");

        // Get all the vouchersHistoryList where createdBy is null
        defaultVouchersHistoryShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where createdBy contains DEFAULT_CREATED_BY
        defaultVouchersHistoryShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the vouchersHistoryList where createdBy contains UPDATED_CREATED_BY
        defaultVouchersHistoryShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where createdBy does not contain DEFAULT_CREATED_BY
        defaultVouchersHistoryShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the vouchersHistoryList where createdBy does not contain UPDATED_CREATED_BY
        defaultVouchersHistoryShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByVoucherDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where voucherDate equals to DEFAULT_VOUCHER_DATE
        defaultVouchersHistoryShouldBeFound("voucherDate.equals=" + DEFAULT_VOUCHER_DATE);

        // Get all the vouchersHistoryList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultVouchersHistoryShouldNotBeFound("voucherDate.equals=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByVoucherDateIsInShouldWork() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where voucherDate in DEFAULT_VOUCHER_DATE or UPDATED_VOUCHER_DATE
        defaultVouchersHistoryShouldBeFound("voucherDate.in=" + DEFAULT_VOUCHER_DATE + "," + UPDATED_VOUCHER_DATE);

        // Get all the vouchersHistoryList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultVouchersHistoryShouldNotBeFound("voucherDate.in=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByVoucherDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where voucherDate is not null
        defaultVouchersHistoryShouldBeFound("voucherDate.specified=true");

        // Get all the vouchersHistoryList where voucherDate is null
        defaultVouchersHistoryShouldNotBeFound("voucherDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where code equals to DEFAULT_CODE
        defaultVouchersHistoryShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the vouchersHistoryList where code equals to UPDATED_CODE
        defaultVouchersHistoryShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where code in DEFAULT_CODE or UPDATED_CODE
        defaultVouchersHistoryShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the vouchersHistoryList where code equals to UPDATED_CODE
        defaultVouchersHistoryShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where code is not null
        defaultVouchersHistoryShouldBeFound("code.specified=true");

        // Get all the vouchersHistoryList where code is null
        defaultVouchersHistoryShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultVouchersHistoryShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the vouchersHistoryList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultVouchersHistoryShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultVouchersHistoryShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the vouchersHistoryList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultVouchersHistoryShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField1 is not null
        defaultVouchersHistoryShouldBeFound("freeField1.specified=true");

        // Get all the vouchersHistoryList where freeField1 is null
        defaultVouchersHistoryShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultVouchersHistoryShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the vouchersHistoryList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultVouchersHistoryShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultVouchersHistoryShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the vouchersHistoryList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultVouchersHistoryShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultVouchersHistoryShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the vouchersHistoryList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultVouchersHistoryShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultVouchersHistoryShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the vouchersHistoryList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultVouchersHistoryShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField2 is not null
        defaultVouchersHistoryShouldBeFound("freeField2.specified=true");

        // Get all the vouchersHistoryList where freeField2 is null
        defaultVouchersHistoryShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultVouchersHistoryShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the vouchersHistoryList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultVouchersHistoryShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultVouchersHistoryShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the vouchersHistoryList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultVouchersHistoryShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultVouchersHistoryShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the vouchersHistoryList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultVouchersHistoryShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultVouchersHistoryShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the vouchersHistoryList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultVouchersHistoryShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField3 is not null
        defaultVouchersHistoryShouldBeFound("freeField3.specified=true");

        // Get all the vouchersHistoryList where freeField3 is null
        defaultVouchersHistoryShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultVouchersHistoryShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the vouchersHistoryList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultVouchersHistoryShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultVouchersHistoryShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the vouchersHistoryList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultVouchersHistoryShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultVouchersHistoryShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the vouchersHistoryList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultVouchersHistoryShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultVouchersHistoryShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the vouchersHistoryList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultVouchersHistoryShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField4 is not null
        defaultVouchersHistoryShouldBeFound("freeField4.specified=true");

        // Get all the vouchersHistoryList where freeField4 is null
        defaultVouchersHistoryShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultVouchersHistoryShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the vouchersHistoryList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultVouchersHistoryShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultVouchersHistoryShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the vouchersHistoryList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultVouchersHistoryShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultVouchersHistoryShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the vouchersHistoryList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultVouchersHistoryShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultVouchersHistoryShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the vouchersHistoryList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultVouchersHistoryShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField5 is not null
        defaultVouchersHistoryShouldBeFound("freeField5.specified=true");

        // Get all the vouchersHistoryList where freeField5 is null
        defaultVouchersHistoryShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultVouchersHistoryShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the vouchersHistoryList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultVouchersHistoryShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultVouchersHistoryShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the vouchersHistoryList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultVouchersHistoryShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultVouchersHistoryShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the vouchersHistoryList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultVouchersHistoryShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultVouchersHistoryShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the vouchersHistoryList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultVouchersHistoryShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField6 is not null
        defaultVouchersHistoryShouldBeFound("freeField6.specified=true");

        // Get all the vouchersHistoryList where freeField6 is null
        defaultVouchersHistoryShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultVouchersHistoryShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the vouchersHistoryList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultVouchersHistoryShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllVouchersHistoriesByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        // Get all the vouchersHistoryList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultVouchersHistoryShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the vouchersHistoryList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultVouchersHistoryShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVouchersHistoryShouldBeFound(String filter) throws Exception {
        restVouchersHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vouchersHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));

        // Check, that the count call also returns 1
        restVouchersHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVouchersHistoryShouldNotBeFound(String filter) throws Exception {
        restVouchersHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVouchersHistoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVouchersHistory() throws Exception {
        // Get the vouchersHistory
        restVouchersHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVouchersHistory() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        int databaseSizeBeforeUpdate = vouchersHistoryRepository.findAll().size();

        // Update the vouchersHistory
        VouchersHistory updatedVouchersHistory = vouchersHistoryRepository.findById(vouchersHistory.getId()).get();
        // Disconnect from session so that the updates on updatedVouchersHistory are not directly saved in db
        em.detach(updatedVouchersHistory);
        updatedVouchersHistory
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .voucherDate(UPDATED_VOUCHER_DATE)
            .code(UPDATED_CODE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        VouchersHistoryDTO vouchersHistoryDTO = vouchersHistoryMapper.toDto(updatedVouchersHistory);

        restVouchersHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vouchersHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vouchersHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the VouchersHistory in the database
        List<VouchersHistory> vouchersHistoryList = vouchersHistoryRepository.findAll();
        assertThat(vouchersHistoryList).hasSize(databaseSizeBeforeUpdate);
        VouchersHistory testVouchersHistory = vouchersHistoryList.get(vouchersHistoryList.size() - 1);
        assertThat(testVouchersHistory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testVouchersHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testVouchersHistory.getVoucherDate()).isEqualTo(UPDATED_VOUCHER_DATE);
        assertThat(testVouchersHistory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVouchersHistory.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testVouchersHistory.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testVouchersHistory.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testVouchersHistory.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testVouchersHistory.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testVouchersHistory.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingVouchersHistory() throws Exception {
        int databaseSizeBeforeUpdate = vouchersHistoryRepository.findAll().size();
        vouchersHistory.setId(count.incrementAndGet());

        // Create the VouchersHistory
        VouchersHistoryDTO vouchersHistoryDTO = vouchersHistoryMapper.toDto(vouchersHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVouchersHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vouchersHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vouchersHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VouchersHistory in the database
        List<VouchersHistory> vouchersHistoryList = vouchersHistoryRepository.findAll();
        assertThat(vouchersHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVouchersHistory() throws Exception {
        int databaseSizeBeforeUpdate = vouchersHistoryRepository.findAll().size();
        vouchersHistory.setId(count.incrementAndGet());

        // Create the VouchersHistory
        VouchersHistoryDTO vouchersHistoryDTO = vouchersHistoryMapper.toDto(vouchersHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVouchersHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vouchersHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VouchersHistory in the database
        List<VouchersHistory> vouchersHistoryList = vouchersHistoryRepository.findAll();
        assertThat(vouchersHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVouchersHistory() throws Exception {
        int databaseSizeBeforeUpdate = vouchersHistoryRepository.findAll().size();
        vouchersHistory.setId(count.incrementAndGet());

        // Create the VouchersHistory
        VouchersHistoryDTO vouchersHistoryDTO = vouchersHistoryMapper.toDto(vouchersHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVouchersHistoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vouchersHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VouchersHistory in the database
        List<VouchersHistory> vouchersHistoryList = vouchersHistoryRepository.findAll();
        assertThat(vouchersHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVouchersHistoryWithPatch() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        int databaseSizeBeforeUpdate = vouchersHistoryRepository.findAll().size();

        // Update the vouchersHistory using partial update
        VouchersHistory partialUpdatedVouchersHistory = new VouchersHistory();
        partialUpdatedVouchersHistory.setId(vouchersHistory.getId());

        partialUpdatedVouchersHistory
            .createdBy(UPDATED_CREATED_BY)
            .voucherDate(UPDATED_VOUCHER_DATE)
            .code(UPDATED_CODE)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField5(UPDATED_FREE_FIELD_5);

        restVouchersHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVouchersHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVouchersHistory))
            )
            .andExpect(status().isOk());

        // Validate the VouchersHistory in the database
        List<VouchersHistory> vouchersHistoryList = vouchersHistoryRepository.findAll();
        assertThat(vouchersHistoryList).hasSize(databaseSizeBeforeUpdate);
        VouchersHistory testVouchersHistory = vouchersHistoryList.get(vouchersHistoryList.size() - 1);
        assertThat(testVouchersHistory.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testVouchersHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testVouchersHistory.getVoucherDate()).isEqualTo(UPDATED_VOUCHER_DATE);
        assertThat(testVouchersHistory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVouchersHistory.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testVouchersHistory.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testVouchersHistory.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testVouchersHistory.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testVouchersHistory.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testVouchersHistory.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateVouchersHistoryWithPatch() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        int databaseSizeBeforeUpdate = vouchersHistoryRepository.findAll().size();

        // Update the vouchersHistory using partial update
        VouchersHistory partialUpdatedVouchersHistory = new VouchersHistory();
        partialUpdatedVouchersHistory.setId(vouchersHistory.getId());

        partialUpdatedVouchersHistory
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .voucherDate(UPDATED_VOUCHER_DATE)
            .code(UPDATED_CODE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restVouchersHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVouchersHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVouchersHistory))
            )
            .andExpect(status().isOk());

        // Validate the VouchersHistory in the database
        List<VouchersHistory> vouchersHistoryList = vouchersHistoryRepository.findAll();
        assertThat(vouchersHistoryList).hasSize(databaseSizeBeforeUpdate);
        VouchersHistory testVouchersHistory = vouchersHistoryList.get(vouchersHistoryList.size() - 1);
        assertThat(testVouchersHistory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testVouchersHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testVouchersHistory.getVoucherDate()).isEqualTo(UPDATED_VOUCHER_DATE);
        assertThat(testVouchersHistory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVouchersHistory.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testVouchersHistory.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testVouchersHistory.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testVouchersHistory.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testVouchersHistory.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testVouchersHistory.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingVouchersHistory() throws Exception {
        int databaseSizeBeforeUpdate = vouchersHistoryRepository.findAll().size();
        vouchersHistory.setId(count.incrementAndGet());

        // Create the VouchersHistory
        VouchersHistoryDTO vouchersHistoryDTO = vouchersHistoryMapper.toDto(vouchersHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVouchersHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vouchersHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vouchersHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VouchersHistory in the database
        List<VouchersHistory> vouchersHistoryList = vouchersHistoryRepository.findAll();
        assertThat(vouchersHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVouchersHistory() throws Exception {
        int databaseSizeBeforeUpdate = vouchersHistoryRepository.findAll().size();
        vouchersHistory.setId(count.incrementAndGet());

        // Create the VouchersHistory
        VouchersHistoryDTO vouchersHistoryDTO = vouchersHistoryMapper.toDto(vouchersHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVouchersHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vouchersHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VouchersHistory in the database
        List<VouchersHistory> vouchersHistoryList = vouchersHistoryRepository.findAll();
        assertThat(vouchersHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVouchersHistory() throws Exception {
        int databaseSizeBeforeUpdate = vouchersHistoryRepository.findAll().size();
        vouchersHistory.setId(count.incrementAndGet());

        // Create the VouchersHistory
        VouchersHistoryDTO vouchersHistoryDTO = vouchersHistoryMapper.toDto(vouchersHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVouchersHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vouchersHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VouchersHistory in the database
        List<VouchersHistory> vouchersHistoryList = vouchersHistoryRepository.findAll();
        assertThat(vouchersHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVouchersHistory() throws Exception {
        // Initialize the database
        vouchersHistoryRepository.saveAndFlush(vouchersHistory);

        int databaseSizeBeforeDelete = vouchersHistoryRepository.findAll().size();

        // Delete the vouchersHistory
        restVouchersHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, vouchersHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VouchersHistory> vouchersHistoryList = vouchersHistoryRepository.findAll();
        assertThat(vouchersHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
