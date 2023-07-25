package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.Vouchers;
import com.techvg.los.domain.enumeration.PaymentMode;
import com.techvg.los.domain.enumeration.VoucherCode;
import com.techvg.los.repository.VouchersRepository;
import com.techvg.los.service.criteria.VouchersCriteria;
import com.techvg.los.service.dto.VouchersDTO;
import com.techvg.los.service.mapper.VouchersMapper;
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
 * Integration tests for the {@link VouchersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VouchersResourceIT {

    private static final Instant DEFAULT_VOUCHER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VOUCHER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_VOUCHER_NO = "AAAAAAAAAA";
    private static final String UPDATED_VOUCHER_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PREPARED_BY = "AAAAAAAAAA";
    private static final String UPDATED_PREPARED_BY = "BBBBBBBBBB";

    private static final VoucherCode DEFAULT_CODE = VoucherCode.LOAN;
    private static final VoucherCode UPDATED_CODE = VoucherCode.DEPOSIT;

    private static final String DEFAULT_NARRATION = "AAAAAAAAAA";
    private static final String UPDATED_NARRATION = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHORISED_BY = "AAAAAAAAAA";
    private static final String UPDATED_AUTHORISED_BY = "BBBBBBBBBB";

    private static final PaymentMode DEFAULT_MODE = PaymentMode.ONLINE;
    private static final PaymentMode UPDATED_MODE = PaymentMode.CASH;

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

    private static final String DEFAULT_FREE_FIELD_5 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_5 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_6 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_6 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/vouchers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VouchersRepository vouchersRepository;

    @Autowired
    private VouchersMapper vouchersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVouchersMockMvc;

    private Vouchers vouchers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vouchers createEntity(EntityManager em) {
        Vouchers vouchers = new Vouchers()
            .voucherDate(DEFAULT_VOUCHER_DATE)
            .voucherNo(DEFAULT_VOUCHER_NO)
            .preparedBy(DEFAULT_PREPARED_BY)
            .code(DEFAULT_CODE)
            .narration(DEFAULT_NARRATION)
            .authorisedBy(DEFAULT_AUTHORISED_BY)
            .mode(DEFAULT_MODE)
            .isDeleted(DEFAULT_IS_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5)
            .freeField6(DEFAULT_FREE_FIELD_6);
        return vouchers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vouchers createUpdatedEntity(EntityManager em) {
        Vouchers vouchers = new Vouchers()
            .voucherDate(UPDATED_VOUCHER_DATE)
            .voucherNo(UPDATED_VOUCHER_NO)
            .preparedBy(UPDATED_PREPARED_BY)
            .code(UPDATED_CODE)
            .narration(UPDATED_NARRATION)
            .authorisedBy(UPDATED_AUTHORISED_BY)
            .mode(UPDATED_MODE)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        return vouchers;
    }

    @BeforeEach
    public void initTest() {
        vouchers = createEntity(em);
    }

    @Test
    @Transactional
    void createVouchers() throws Exception {
        int databaseSizeBeforeCreate = vouchersRepository.findAll().size();
        // Create the Vouchers
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);
        restVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vouchersDTO)))
            .andExpect(status().isCreated());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeCreate + 1);
        Vouchers testVouchers = vouchersList.get(vouchersList.size() - 1);
        assertThat(testVouchers.getVoucherDate()).isEqualTo(DEFAULT_VOUCHER_DATE);
        assertThat(testVouchers.getVoucherNo()).isEqualTo(DEFAULT_VOUCHER_NO);
        assertThat(testVouchers.getPreparedBy()).isEqualTo(DEFAULT_PREPARED_BY);
        assertThat(testVouchers.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testVouchers.getNarration()).isEqualTo(DEFAULT_NARRATION);
        assertThat(testVouchers.getAuthorisedBy()).isEqualTo(DEFAULT_AUTHORISED_BY);
        assertThat(testVouchers.getMode()).isEqualTo(DEFAULT_MODE);
        assertThat(testVouchers.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testVouchers.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testVouchers.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testVouchers.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testVouchers.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testVouchers.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testVouchers.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testVouchers.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testVouchers.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createVouchersWithExistingId() throws Exception {
        // Create the Vouchers with an existing ID
        vouchers.setId(1L);
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);

        int databaseSizeBeforeCreate = vouchersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vouchersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVouchers() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList
        restVouchersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vouchers.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].preparedBy").value(hasItem(DEFAULT_PREPARED_BY)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].narration").value(hasItem(DEFAULT_NARRATION)))
            .andExpect(jsonPath("$.[*].authorisedBy").value(hasItem(DEFAULT_AUTHORISED_BY)))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
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
    void getVouchers() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get the vouchers
        restVouchersMockMvc
            .perform(get(ENTITY_API_URL_ID, vouchers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vouchers.getId().intValue()))
            .andExpect(jsonPath("$.voucherDate").value(DEFAULT_VOUCHER_DATE.toString()))
            .andExpect(jsonPath("$.voucherNo").value(DEFAULT_VOUCHER_NO))
            .andExpect(jsonPath("$.preparedBy").value(DEFAULT_PREPARED_BY))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.narration").value(DEFAULT_NARRATION))
            .andExpect(jsonPath("$.authorisedBy").value(DEFAULT_AUTHORISED_BY))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
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
    void getVouchersByIdFiltering() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        Long id = vouchers.getId();

        defaultVouchersShouldBeFound("id.equals=" + id);
        defaultVouchersShouldNotBeFound("id.notEquals=" + id);

        defaultVouchersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVouchersShouldNotBeFound("id.greaterThan=" + id);

        defaultVouchersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVouchersShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherDateIsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where voucherDate equals to DEFAULT_VOUCHER_DATE
        defaultVouchersShouldBeFound("voucherDate.equals=" + DEFAULT_VOUCHER_DATE);

        // Get all the vouchersList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultVouchersShouldNotBeFound("voucherDate.equals=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherDateIsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where voucherDate in DEFAULT_VOUCHER_DATE or UPDATED_VOUCHER_DATE
        defaultVouchersShouldBeFound("voucherDate.in=" + DEFAULT_VOUCHER_DATE + "," + UPDATED_VOUCHER_DATE);

        // Get all the vouchersList where voucherDate equals to UPDATED_VOUCHER_DATE
        defaultVouchersShouldNotBeFound("voucherDate.in=" + UPDATED_VOUCHER_DATE);
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where voucherDate is not null
        defaultVouchersShouldBeFound("voucherDate.specified=true");

        // Get all the vouchersList where voucherDate is null
        defaultVouchersShouldNotBeFound("voucherDate.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherNoIsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where voucherNo equals to DEFAULT_VOUCHER_NO
        defaultVouchersShouldBeFound("voucherNo.equals=" + DEFAULT_VOUCHER_NO);

        // Get all the vouchersList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultVouchersShouldNotBeFound("voucherNo.equals=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherNoIsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where voucherNo in DEFAULT_VOUCHER_NO or UPDATED_VOUCHER_NO
        defaultVouchersShouldBeFound("voucherNo.in=" + DEFAULT_VOUCHER_NO + "," + UPDATED_VOUCHER_NO);

        // Get all the vouchersList where voucherNo equals to UPDATED_VOUCHER_NO
        defaultVouchersShouldNotBeFound("voucherNo.in=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where voucherNo is not null
        defaultVouchersShouldBeFound("voucherNo.specified=true");

        // Get all the vouchersList where voucherNo is null
        defaultVouchersShouldNotBeFound("voucherNo.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherNoContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where voucherNo contains DEFAULT_VOUCHER_NO
        defaultVouchersShouldBeFound("voucherNo.contains=" + DEFAULT_VOUCHER_NO);

        // Get all the vouchersList where voucherNo contains UPDATED_VOUCHER_NO
        defaultVouchersShouldNotBeFound("voucherNo.contains=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    void getAllVouchersByVoucherNoNotContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where voucherNo does not contain DEFAULT_VOUCHER_NO
        defaultVouchersShouldNotBeFound("voucherNo.doesNotContain=" + DEFAULT_VOUCHER_NO);

        // Get all the vouchersList where voucherNo does not contain UPDATED_VOUCHER_NO
        defaultVouchersShouldBeFound("voucherNo.doesNotContain=" + UPDATED_VOUCHER_NO);
    }

    @Test
    @Transactional
    void getAllVouchersByPreparedByIsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where preparedBy equals to DEFAULT_PREPARED_BY
        defaultVouchersShouldBeFound("preparedBy.equals=" + DEFAULT_PREPARED_BY);

        // Get all the vouchersList where preparedBy equals to UPDATED_PREPARED_BY
        defaultVouchersShouldNotBeFound("preparedBy.equals=" + UPDATED_PREPARED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByPreparedByIsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where preparedBy in DEFAULT_PREPARED_BY or UPDATED_PREPARED_BY
        defaultVouchersShouldBeFound("preparedBy.in=" + DEFAULT_PREPARED_BY + "," + UPDATED_PREPARED_BY);

        // Get all the vouchersList where preparedBy equals to UPDATED_PREPARED_BY
        defaultVouchersShouldNotBeFound("preparedBy.in=" + UPDATED_PREPARED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByPreparedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where preparedBy is not null
        defaultVouchersShouldBeFound("preparedBy.specified=true");

        // Get all the vouchersList where preparedBy is null
        defaultVouchersShouldNotBeFound("preparedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByPreparedByContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where preparedBy contains DEFAULT_PREPARED_BY
        defaultVouchersShouldBeFound("preparedBy.contains=" + DEFAULT_PREPARED_BY);

        // Get all the vouchersList where preparedBy contains UPDATED_PREPARED_BY
        defaultVouchersShouldNotBeFound("preparedBy.contains=" + UPDATED_PREPARED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByPreparedByNotContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where preparedBy does not contain DEFAULT_PREPARED_BY
        defaultVouchersShouldNotBeFound("preparedBy.doesNotContain=" + DEFAULT_PREPARED_BY);

        // Get all the vouchersList where preparedBy does not contain UPDATED_PREPARED_BY
        defaultVouchersShouldBeFound("preparedBy.doesNotContain=" + UPDATED_PREPARED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where code equals to DEFAULT_CODE
        defaultVouchersShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the vouchersList where code equals to UPDATED_CODE
        defaultVouchersShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVouchersByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where code in DEFAULT_CODE or UPDATED_CODE
        defaultVouchersShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the vouchersList where code equals to UPDATED_CODE
        defaultVouchersShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllVouchersByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where code is not null
        defaultVouchersShouldBeFound("code.specified=true");

        // Get all the vouchersList where code is null
        defaultVouchersShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByNarrationIsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where narration equals to DEFAULT_NARRATION
        defaultVouchersShouldBeFound("narration.equals=" + DEFAULT_NARRATION);

        // Get all the vouchersList where narration equals to UPDATED_NARRATION
        defaultVouchersShouldNotBeFound("narration.equals=" + UPDATED_NARRATION);
    }

    @Test
    @Transactional
    void getAllVouchersByNarrationIsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where narration in DEFAULT_NARRATION or UPDATED_NARRATION
        defaultVouchersShouldBeFound("narration.in=" + DEFAULT_NARRATION + "," + UPDATED_NARRATION);

        // Get all the vouchersList where narration equals to UPDATED_NARRATION
        defaultVouchersShouldNotBeFound("narration.in=" + UPDATED_NARRATION);
    }

    @Test
    @Transactional
    void getAllVouchersByNarrationIsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where narration is not null
        defaultVouchersShouldBeFound("narration.specified=true");

        // Get all the vouchersList where narration is null
        defaultVouchersShouldNotBeFound("narration.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByNarrationContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where narration contains DEFAULT_NARRATION
        defaultVouchersShouldBeFound("narration.contains=" + DEFAULT_NARRATION);

        // Get all the vouchersList where narration contains UPDATED_NARRATION
        defaultVouchersShouldNotBeFound("narration.contains=" + UPDATED_NARRATION);
    }

    @Test
    @Transactional
    void getAllVouchersByNarrationNotContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where narration does not contain DEFAULT_NARRATION
        defaultVouchersShouldNotBeFound("narration.doesNotContain=" + DEFAULT_NARRATION);

        // Get all the vouchersList where narration does not contain UPDATED_NARRATION
        defaultVouchersShouldBeFound("narration.doesNotContain=" + UPDATED_NARRATION);
    }

    @Test
    @Transactional
    void getAllVouchersByAuthorisedByIsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where authorisedBy equals to DEFAULT_AUTHORISED_BY
        defaultVouchersShouldBeFound("authorisedBy.equals=" + DEFAULT_AUTHORISED_BY);

        // Get all the vouchersList where authorisedBy equals to UPDATED_AUTHORISED_BY
        defaultVouchersShouldNotBeFound("authorisedBy.equals=" + UPDATED_AUTHORISED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByAuthorisedByIsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where authorisedBy in DEFAULT_AUTHORISED_BY or UPDATED_AUTHORISED_BY
        defaultVouchersShouldBeFound("authorisedBy.in=" + DEFAULT_AUTHORISED_BY + "," + UPDATED_AUTHORISED_BY);

        // Get all the vouchersList where authorisedBy equals to UPDATED_AUTHORISED_BY
        defaultVouchersShouldNotBeFound("authorisedBy.in=" + UPDATED_AUTHORISED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByAuthorisedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where authorisedBy is not null
        defaultVouchersShouldBeFound("authorisedBy.specified=true");

        // Get all the vouchersList where authorisedBy is null
        defaultVouchersShouldNotBeFound("authorisedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByAuthorisedByContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where authorisedBy contains DEFAULT_AUTHORISED_BY
        defaultVouchersShouldBeFound("authorisedBy.contains=" + DEFAULT_AUTHORISED_BY);

        // Get all the vouchersList where authorisedBy contains UPDATED_AUTHORISED_BY
        defaultVouchersShouldNotBeFound("authorisedBy.contains=" + UPDATED_AUTHORISED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByAuthorisedByNotContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where authorisedBy does not contain DEFAULT_AUTHORISED_BY
        defaultVouchersShouldNotBeFound("authorisedBy.doesNotContain=" + DEFAULT_AUTHORISED_BY);

        // Get all the vouchersList where authorisedBy does not contain UPDATED_AUTHORISED_BY
        defaultVouchersShouldBeFound("authorisedBy.doesNotContain=" + UPDATED_AUTHORISED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByModeIsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where mode equals to DEFAULT_MODE
        defaultVouchersShouldBeFound("mode.equals=" + DEFAULT_MODE);

        // Get all the vouchersList where mode equals to UPDATED_MODE
        defaultVouchersShouldNotBeFound("mode.equals=" + UPDATED_MODE);
    }

    @Test
    @Transactional
    void getAllVouchersByModeIsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where mode in DEFAULT_MODE or UPDATED_MODE
        defaultVouchersShouldBeFound("mode.in=" + DEFAULT_MODE + "," + UPDATED_MODE);

        // Get all the vouchersList where mode equals to UPDATED_MODE
        defaultVouchersShouldNotBeFound("mode.in=" + UPDATED_MODE);
    }

    @Test
    @Transactional
    void getAllVouchersByModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where mode is not null
        defaultVouchersShouldBeFound("mode.specified=true");

        // Get all the vouchersList where mode is null
        defaultVouchersShouldNotBeFound("mode.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where isDeleted equals to DEFAULT_IS_DELETED
        defaultVouchersShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the vouchersList where isDeleted equals to UPDATED_IS_DELETED
        defaultVouchersShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllVouchersByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultVouchersShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the vouchersList where isDeleted equals to UPDATED_IS_DELETED
        defaultVouchersShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllVouchersByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where isDeleted is not null
        defaultVouchersShouldBeFound("isDeleted.specified=true");

        // Get all the vouchersList where isDeleted is null
        defaultVouchersShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultVouchersShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the vouchersList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultVouchersShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllVouchersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultVouchersShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the vouchersList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultVouchersShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllVouchersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where lastModified is not null
        defaultVouchersShouldBeFound("lastModified.specified=true");

        // Get all the vouchersList where lastModified is null
        defaultVouchersShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultVouchersShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the vouchersList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultVouchersShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultVouchersShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the vouchersList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultVouchersShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where lastModifiedBy is not null
        defaultVouchersShouldBeFound("lastModifiedBy.specified=true");

        // Get all the vouchersList where lastModifiedBy is null
        defaultVouchersShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultVouchersShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the vouchersList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultVouchersShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultVouchersShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the vouchersList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultVouchersShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultVouchersShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the vouchersList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultVouchersShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultVouchersShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the vouchersList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultVouchersShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField1 is not null
        defaultVouchersShouldBeFound("freeField1.specified=true");

        // Get all the vouchersList where freeField1 is null
        defaultVouchersShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultVouchersShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the vouchersList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultVouchersShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultVouchersShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the vouchersList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultVouchersShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultVouchersShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the vouchersList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultVouchersShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultVouchersShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the vouchersList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultVouchersShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField2 is not null
        defaultVouchersShouldBeFound("freeField2.specified=true");

        // Get all the vouchersList where freeField2 is null
        defaultVouchersShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultVouchersShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the vouchersList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultVouchersShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultVouchersShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the vouchersList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultVouchersShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultVouchersShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the vouchersList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultVouchersShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultVouchersShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the vouchersList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultVouchersShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField3 is not null
        defaultVouchersShouldBeFound("freeField3.specified=true");

        // Get all the vouchersList where freeField3 is null
        defaultVouchersShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultVouchersShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the vouchersList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultVouchersShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultVouchersShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the vouchersList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultVouchersShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultVouchersShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the vouchersList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultVouchersShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultVouchersShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the vouchersList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultVouchersShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField4 is not null
        defaultVouchersShouldBeFound("freeField4.specified=true");

        // Get all the vouchersList where freeField4 is null
        defaultVouchersShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultVouchersShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the vouchersList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultVouchersShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultVouchersShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the vouchersList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultVouchersShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultVouchersShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the vouchersList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultVouchersShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultVouchersShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the vouchersList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultVouchersShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField5 is not null
        defaultVouchersShouldBeFound("freeField5.specified=true");

        // Get all the vouchersList where freeField5 is null
        defaultVouchersShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultVouchersShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the vouchersList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultVouchersShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultVouchersShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the vouchersList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultVouchersShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultVouchersShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the vouchersList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultVouchersShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultVouchersShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the vouchersList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultVouchersShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField6 is not null
        defaultVouchersShouldBeFound("freeField6.specified=true");

        // Get all the vouchersList where freeField6 is null
        defaultVouchersShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultVouchersShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the vouchersList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultVouchersShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllVouchersByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultVouchersShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the vouchersList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultVouchersShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVouchersShouldBeFound(String filter) throws Exception {
        restVouchersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vouchers.getId().intValue())))
            .andExpect(jsonPath("$.[*].voucherDate").value(hasItem(DEFAULT_VOUCHER_DATE.toString())))
            .andExpect(jsonPath("$.[*].voucherNo").value(hasItem(DEFAULT_VOUCHER_NO)))
            .andExpect(jsonPath("$.[*].preparedBy").value(hasItem(DEFAULT_PREPARED_BY)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].narration").value(hasItem(DEFAULT_NARRATION)))
            .andExpect(jsonPath("$.[*].authorisedBy").value(hasItem(DEFAULT_AUTHORISED_BY)))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));

        // Check, that the count call also returns 1
        restVouchersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVouchersShouldNotBeFound(String filter) throws Exception {
        restVouchersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVouchersMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVouchers() throws Exception {
        // Get the vouchers
        restVouchersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVouchers() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();

        // Update the vouchers
        Vouchers updatedVouchers = vouchersRepository.findById(vouchers.getId()).get();
        // Disconnect from session so that the updates on updatedVouchers are not directly saved in db
        em.detach(updatedVouchers);
        updatedVouchers
            .voucherDate(UPDATED_VOUCHER_DATE)
            .voucherNo(UPDATED_VOUCHER_NO)
            .preparedBy(UPDATED_PREPARED_BY)
            .code(UPDATED_CODE)
            .narration(UPDATED_NARRATION)
            .authorisedBy(UPDATED_AUTHORISED_BY)
            .mode(UPDATED_MODE)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        VouchersDTO vouchersDTO = vouchersMapper.toDto(updatedVouchers);

        restVouchersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vouchersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vouchersDTO))
            )
            .andExpect(status().isOk());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
        Vouchers testVouchers = vouchersList.get(vouchersList.size() - 1);
        assertThat(testVouchers.getVoucherDate()).isEqualTo(UPDATED_VOUCHER_DATE);
        assertThat(testVouchers.getVoucherNo()).isEqualTo(UPDATED_VOUCHER_NO);
        assertThat(testVouchers.getPreparedBy()).isEqualTo(UPDATED_PREPARED_BY);
        assertThat(testVouchers.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVouchers.getNarration()).isEqualTo(UPDATED_NARRATION);
        assertThat(testVouchers.getAuthorisedBy()).isEqualTo(UPDATED_AUTHORISED_BY);
        assertThat(testVouchers.getMode()).isEqualTo(UPDATED_MODE);
        assertThat(testVouchers.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testVouchers.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testVouchers.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testVouchers.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testVouchers.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testVouchers.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testVouchers.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testVouchers.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testVouchers.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();
        vouchers.setId(count.incrementAndGet());

        // Create the Vouchers
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVouchersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vouchersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vouchersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();
        vouchers.setId(count.incrementAndGet());

        // Create the Vouchers
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVouchersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vouchersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();
        vouchers.setId(count.incrementAndGet());

        // Create the Vouchers
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVouchersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vouchersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVouchersWithPatch() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();

        // Update the vouchers using partial update
        Vouchers partialUpdatedVouchers = new Vouchers();
        partialUpdatedVouchers.setId(vouchers.getId());

        partialUpdatedVouchers
            .voucherDate(UPDATED_VOUCHER_DATE)
            .authorisedBy(UPDATED_AUTHORISED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField6(UPDATED_FREE_FIELD_6);

        restVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVouchers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVouchers))
            )
            .andExpect(status().isOk());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
        Vouchers testVouchers = vouchersList.get(vouchersList.size() - 1);
        assertThat(testVouchers.getVoucherDate()).isEqualTo(UPDATED_VOUCHER_DATE);
        assertThat(testVouchers.getVoucherNo()).isEqualTo(DEFAULT_VOUCHER_NO);
        assertThat(testVouchers.getPreparedBy()).isEqualTo(DEFAULT_PREPARED_BY);
        assertThat(testVouchers.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testVouchers.getNarration()).isEqualTo(DEFAULT_NARRATION);
        assertThat(testVouchers.getAuthorisedBy()).isEqualTo(UPDATED_AUTHORISED_BY);
        assertThat(testVouchers.getMode()).isEqualTo(DEFAULT_MODE);
        assertThat(testVouchers.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testVouchers.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testVouchers.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testVouchers.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testVouchers.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testVouchers.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testVouchers.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testVouchers.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testVouchers.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateVouchersWithPatch() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();

        // Update the vouchers using partial update
        Vouchers partialUpdatedVouchers = new Vouchers();
        partialUpdatedVouchers.setId(vouchers.getId());

        partialUpdatedVouchers
            .voucherDate(UPDATED_VOUCHER_DATE)
            .voucherNo(UPDATED_VOUCHER_NO)
            .preparedBy(UPDATED_PREPARED_BY)
            .code(UPDATED_CODE)
            .narration(UPDATED_NARRATION)
            .authorisedBy(UPDATED_AUTHORISED_BY)
            .mode(UPDATED_MODE)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVouchers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVouchers))
            )
            .andExpect(status().isOk());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
        Vouchers testVouchers = vouchersList.get(vouchersList.size() - 1);
        assertThat(testVouchers.getVoucherDate()).isEqualTo(UPDATED_VOUCHER_DATE);
        assertThat(testVouchers.getVoucherNo()).isEqualTo(UPDATED_VOUCHER_NO);
        assertThat(testVouchers.getPreparedBy()).isEqualTo(UPDATED_PREPARED_BY);
        assertThat(testVouchers.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testVouchers.getNarration()).isEqualTo(UPDATED_NARRATION);
        assertThat(testVouchers.getAuthorisedBy()).isEqualTo(UPDATED_AUTHORISED_BY);
        assertThat(testVouchers.getMode()).isEqualTo(UPDATED_MODE);
        assertThat(testVouchers.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testVouchers.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testVouchers.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testVouchers.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testVouchers.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testVouchers.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testVouchers.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testVouchers.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testVouchers.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();
        vouchers.setId(count.incrementAndGet());

        // Create the Vouchers
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vouchersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vouchersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();
        vouchers.setId(count.incrementAndGet());

        // Create the Vouchers
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vouchersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();
        vouchers.setId(count.incrementAndGet());

        // Create the Vouchers
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vouchersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVouchers() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        int databaseSizeBeforeDelete = vouchersRepository.findAll().size();

        // Delete the vouchers
        restVouchersMockMvc
            .perform(delete(ENTITY_API_URL_ID, vouchers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
