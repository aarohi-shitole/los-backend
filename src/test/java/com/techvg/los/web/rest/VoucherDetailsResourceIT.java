package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.VoucherDetails;
import com.techvg.los.repository.VoucherDetailsRepository;
import com.techvg.los.service.criteria.VoucherDetailsCriteria;
import com.techvg.los.service.dto.VoucherDetailsDTO;
import com.techvg.los.service.mapper.VoucherDetailsMapper;
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
 * Integration tests for the {@link VoucherDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VoucherDetailsResourceIT {

    private static final String DEFAULT_ACC_HEAD_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ACC_HEAD_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_DEBIT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_DEBIT_ACCOUNT = "BBBBBBBBBB";

    private static final Double DEFAULT_TRANSFER_AMT = 1D;
    private static final Double UPDATED_TRANSFER_AMT = 2D;
    private static final Double SMALLER_TRANSFER_AMT = 1D - 1D;

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

    private static final String ENTITY_API_URL = "/api/voucher-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VoucherDetailsRepository voucherDetailsRepository;

    @Autowired
    private VoucherDetailsMapper voucherDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVoucherDetailsMockMvc;

    private VoucherDetails voucherDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoucherDetails createEntity(EntityManager em) {
        VoucherDetails voucherDetails = new VoucherDetails()
            .accHeadCode(DEFAULT_ACC_HEAD_CODE)
            .creditAccount(DEFAULT_CREDIT_ACCOUNT)
            .debitAccount(DEFAULT_DEBIT_ACCOUNT)
            .transferAmt(DEFAULT_TRANSFER_AMT)
            .isDeleted(DEFAULT_IS_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5)
            .freeField6(DEFAULT_FREE_FIELD_6);
        return voucherDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VoucherDetails createUpdatedEntity(EntityManager em) {
        VoucherDetails voucherDetails = new VoucherDetails()
            .accHeadCode(UPDATED_ACC_HEAD_CODE)
            .creditAccount(UPDATED_CREDIT_ACCOUNT)
            .debitAccount(UPDATED_DEBIT_ACCOUNT)
            .transferAmt(UPDATED_TRANSFER_AMT)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        return voucherDetails;
    }

    @BeforeEach
    public void initTest() {
        voucherDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createVoucherDetails() throws Exception {
        int databaseSizeBeforeCreate = voucherDetailsRepository.findAll().size();
        // Create the VoucherDetails
        VoucherDetailsDTO voucherDetailsDTO = voucherDetailsMapper.toDto(voucherDetails);
        restVoucherDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucherDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VoucherDetails in the database
        List<VoucherDetails> voucherDetailsList = voucherDetailsRepository.findAll();
        assertThat(voucherDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        VoucherDetails testVoucherDetails = voucherDetailsList.get(voucherDetailsList.size() - 1);
        assertThat(testVoucherDetails.getAccHeadCode()).isEqualTo(DEFAULT_ACC_HEAD_CODE);
        assertThat(testVoucherDetails.getCreditAccount()).isEqualTo(DEFAULT_CREDIT_ACCOUNT);
        assertThat(testVoucherDetails.getDebitAccount()).isEqualTo(DEFAULT_DEBIT_ACCOUNT);
        assertThat(testVoucherDetails.getTransferAmt()).isEqualTo(DEFAULT_TRANSFER_AMT);
        assertThat(testVoucherDetails.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testVoucherDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testVoucherDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testVoucherDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testVoucherDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testVoucherDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testVoucherDetails.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testVoucherDetails.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testVoucherDetails.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createVoucherDetailsWithExistingId() throws Exception {
        // Create the VoucherDetails with an existing ID
        voucherDetails.setId(1L);
        VoucherDetailsDTO voucherDetailsDTO = voucherDetailsMapper.toDto(voucherDetails);

        int databaseSizeBeforeCreate = voucherDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoucherDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucherDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherDetails in the database
        List<VoucherDetails> voucherDetailsList = voucherDetailsRepository.findAll();
        assertThat(voucherDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVoucherDetails() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList
        restVoucherDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].accHeadCode").value(hasItem(DEFAULT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].creditAccount").value(hasItem(DEFAULT_CREDIT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].debitAccount").value(hasItem(DEFAULT_DEBIT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].transferAmt").value(hasItem(DEFAULT_TRANSFER_AMT.doubleValue())))
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
    void getVoucherDetails() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get the voucherDetails
        restVoucherDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, voucherDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(voucherDetails.getId().intValue()))
            .andExpect(jsonPath("$.accHeadCode").value(DEFAULT_ACC_HEAD_CODE))
            .andExpect(jsonPath("$.creditAccount").value(DEFAULT_CREDIT_ACCOUNT))
            .andExpect(jsonPath("$.debitAccount").value(DEFAULT_DEBIT_ACCOUNT))
            .andExpect(jsonPath("$.transferAmt").value(DEFAULT_TRANSFER_AMT.doubleValue()))
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
    void getVoucherDetailsByIdFiltering() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        Long id = voucherDetails.getId();

        defaultVoucherDetailsShouldBeFound("id.equals=" + id);
        defaultVoucherDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultVoucherDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVoucherDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultVoucherDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVoucherDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByAccHeadCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where accHeadCode equals to DEFAULT_ACC_HEAD_CODE
        defaultVoucherDetailsShouldBeFound("accHeadCode.equals=" + DEFAULT_ACC_HEAD_CODE);

        // Get all the voucherDetailsList where accHeadCode equals to UPDATED_ACC_HEAD_CODE
        defaultVoucherDetailsShouldNotBeFound("accHeadCode.equals=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByAccHeadCodeIsInShouldWork() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where accHeadCode in DEFAULT_ACC_HEAD_CODE or UPDATED_ACC_HEAD_CODE
        defaultVoucherDetailsShouldBeFound("accHeadCode.in=" + DEFAULT_ACC_HEAD_CODE + "," + UPDATED_ACC_HEAD_CODE);

        // Get all the voucherDetailsList where accHeadCode equals to UPDATED_ACC_HEAD_CODE
        defaultVoucherDetailsShouldNotBeFound("accHeadCode.in=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByAccHeadCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where accHeadCode is not null
        defaultVoucherDetailsShouldBeFound("accHeadCode.specified=true");

        // Get all the voucherDetailsList where accHeadCode is null
        defaultVoucherDetailsShouldNotBeFound("accHeadCode.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByAccHeadCodeContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where accHeadCode contains DEFAULT_ACC_HEAD_CODE
        defaultVoucherDetailsShouldBeFound("accHeadCode.contains=" + DEFAULT_ACC_HEAD_CODE);

        // Get all the voucherDetailsList where accHeadCode contains UPDATED_ACC_HEAD_CODE
        defaultVoucherDetailsShouldNotBeFound("accHeadCode.contains=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByAccHeadCodeNotContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where accHeadCode does not contain DEFAULT_ACC_HEAD_CODE
        defaultVoucherDetailsShouldNotBeFound("accHeadCode.doesNotContain=" + DEFAULT_ACC_HEAD_CODE);

        // Get all the voucherDetailsList where accHeadCode does not contain UPDATED_ACC_HEAD_CODE
        defaultVoucherDetailsShouldBeFound("accHeadCode.doesNotContain=" + UPDATED_ACC_HEAD_CODE);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByCreditAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where creditAccount equals to DEFAULT_CREDIT_ACCOUNT
        defaultVoucherDetailsShouldBeFound("creditAccount.equals=" + DEFAULT_CREDIT_ACCOUNT);

        // Get all the voucherDetailsList where creditAccount equals to UPDATED_CREDIT_ACCOUNT
        defaultVoucherDetailsShouldNotBeFound("creditAccount.equals=" + UPDATED_CREDIT_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByCreditAccountIsInShouldWork() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where creditAccount in DEFAULT_CREDIT_ACCOUNT or UPDATED_CREDIT_ACCOUNT
        defaultVoucherDetailsShouldBeFound("creditAccount.in=" + DEFAULT_CREDIT_ACCOUNT + "," + UPDATED_CREDIT_ACCOUNT);

        // Get all the voucherDetailsList where creditAccount equals to UPDATED_CREDIT_ACCOUNT
        defaultVoucherDetailsShouldNotBeFound("creditAccount.in=" + UPDATED_CREDIT_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByCreditAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where creditAccount is not null
        defaultVoucherDetailsShouldBeFound("creditAccount.specified=true");

        // Get all the voucherDetailsList where creditAccount is null
        defaultVoucherDetailsShouldNotBeFound("creditAccount.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByCreditAccountContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where creditAccount contains DEFAULT_CREDIT_ACCOUNT
        defaultVoucherDetailsShouldBeFound("creditAccount.contains=" + DEFAULT_CREDIT_ACCOUNT);

        // Get all the voucherDetailsList where creditAccount contains UPDATED_CREDIT_ACCOUNT
        defaultVoucherDetailsShouldNotBeFound("creditAccount.contains=" + UPDATED_CREDIT_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByCreditAccountNotContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where creditAccount does not contain DEFAULT_CREDIT_ACCOUNT
        defaultVoucherDetailsShouldNotBeFound("creditAccount.doesNotContain=" + DEFAULT_CREDIT_ACCOUNT);

        // Get all the voucherDetailsList where creditAccount does not contain UPDATED_CREDIT_ACCOUNT
        defaultVoucherDetailsShouldBeFound("creditAccount.doesNotContain=" + UPDATED_CREDIT_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByDebitAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where debitAccount equals to DEFAULT_DEBIT_ACCOUNT
        defaultVoucherDetailsShouldBeFound("debitAccount.equals=" + DEFAULT_DEBIT_ACCOUNT);

        // Get all the voucherDetailsList where debitAccount equals to UPDATED_DEBIT_ACCOUNT
        defaultVoucherDetailsShouldNotBeFound("debitAccount.equals=" + UPDATED_DEBIT_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByDebitAccountIsInShouldWork() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where debitAccount in DEFAULT_DEBIT_ACCOUNT or UPDATED_DEBIT_ACCOUNT
        defaultVoucherDetailsShouldBeFound("debitAccount.in=" + DEFAULT_DEBIT_ACCOUNT + "," + UPDATED_DEBIT_ACCOUNT);

        // Get all the voucherDetailsList where debitAccount equals to UPDATED_DEBIT_ACCOUNT
        defaultVoucherDetailsShouldNotBeFound("debitAccount.in=" + UPDATED_DEBIT_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByDebitAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where debitAccount is not null
        defaultVoucherDetailsShouldBeFound("debitAccount.specified=true");

        // Get all the voucherDetailsList where debitAccount is null
        defaultVoucherDetailsShouldNotBeFound("debitAccount.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByDebitAccountContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where debitAccount contains DEFAULT_DEBIT_ACCOUNT
        defaultVoucherDetailsShouldBeFound("debitAccount.contains=" + DEFAULT_DEBIT_ACCOUNT);

        // Get all the voucherDetailsList where debitAccount contains UPDATED_DEBIT_ACCOUNT
        defaultVoucherDetailsShouldNotBeFound("debitAccount.contains=" + UPDATED_DEBIT_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByDebitAccountNotContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where debitAccount does not contain DEFAULT_DEBIT_ACCOUNT
        defaultVoucherDetailsShouldNotBeFound("debitAccount.doesNotContain=" + DEFAULT_DEBIT_ACCOUNT);

        // Get all the voucherDetailsList where debitAccount does not contain UPDATED_DEBIT_ACCOUNT
        defaultVoucherDetailsShouldBeFound("debitAccount.doesNotContain=" + UPDATED_DEBIT_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByTransferAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where transferAmt equals to DEFAULT_TRANSFER_AMT
        defaultVoucherDetailsShouldBeFound("transferAmt.equals=" + DEFAULT_TRANSFER_AMT);

        // Get all the voucherDetailsList where transferAmt equals to UPDATED_TRANSFER_AMT
        defaultVoucherDetailsShouldNotBeFound("transferAmt.equals=" + UPDATED_TRANSFER_AMT);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByTransferAmtIsInShouldWork() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where transferAmt in DEFAULT_TRANSFER_AMT or UPDATED_TRANSFER_AMT
        defaultVoucherDetailsShouldBeFound("transferAmt.in=" + DEFAULT_TRANSFER_AMT + "," + UPDATED_TRANSFER_AMT);

        // Get all the voucherDetailsList where transferAmt equals to UPDATED_TRANSFER_AMT
        defaultVoucherDetailsShouldNotBeFound("transferAmt.in=" + UPDATED_TRANSFER_AMT);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByTransferAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where transferAmt is not null
        defaultVoucherDetailsShouldBeFound("transferAmt.specified=true");

        // Get all the voucherDetailsList where transferAmt is null
        defaultVoucherDetailsShouldNotBeFound("transferAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByTransferAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where transferAmt is greater than or equal to DEFAULT_TRANSFER_AMT
        defaultVoucherDetailsShouldBeFound("transferAmt.greaterThanOrEqual=" + DEFAULT_TRANSFER_AMT);

        // Get all the voucherDetailsList where transferAmt is greater than or equal to UPDATED_TRANSFER_AMT
        defaultVoucherDetailsShouldNotBeFound("transferAmt.greaterThanOrEqual=" + UPDATED_TRANSFER_AMT);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByTransferAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where transferAmt is less than or equal to DEFAULT_TRANSFER_AMT
        defaultVoucherDetailsShouldBeFound("transferAmt.lessThanOrEqual=" + DEFAULT_TRANSFER_AMT);

        // Get all the voucherDetailsList where transferAmt is less than or equal to SMALLER_TRANSFER_AMT
        defaultVoucherDetailsShouldNotBeFound("transferAmt.lessThanOrEqual=" + SMALLER_TRANSFER_AMT);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByTransferAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where transferAmt is less than DEFAULT_TRANSFER_AMT
        defaultVoucherDetailsShouldNotBeFound("transferAmt.lessThan=" + DEFAULT_TRANSFER_AMT);

        // Get all the voucherDetailsList where transferAmt is less than UPDATED_TRANSFER_AMT
        defaultVoucherDetailsShouldBeFound("transferAmt.lessThan=" + UPDATED_TRANSFER_AMT);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByTransferAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where transferAmt is greater than DEFAULT_TRANSFER_AMT
        defaultVoucherDetailsShouldNotBeFound("transferAmt.greaterThan=" + DEFAULT_TRANSFER_AMT);

        // Get all the voucherDetailsList where transferAmt is greater than SMALLER_TRANSFER_AMT
        defaultVoucherDetailsShouldBeFound("transferAmt.greaterThan=" + SMALLER_TRANSFER_AMT);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultVoucherDetailsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the voucherDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultVoucherDetailsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultVoucherDetailsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the voucherDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultVoucherDetailsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where isDeleted is not null
        defaultVoucherDetailsShouldBeFound("isDeleted.specified=true");

        // Get all the voucherDetailsList where isDeleted is null
        defaultVoucherDetailsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultVoucherDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the voucherDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultVoucherDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultVoucherDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the voucherDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultVoucherDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where lastModified is not null
        defaultVoucherDetailsShouldBeFound("lastModified.specified=true");

        // Get all the voucherDetailsList where lastModified is null
        defaultVoucherDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultVoucherDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the voucherDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultVoucherDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultVoucherDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the voucherDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultVoucherDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where lastModifiedBy is not null
        defaultVoucherDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the voucherDetailsList where lastModifiedBy is null
        defaultVoucherDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultVoucherDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the voucherDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultVoucherDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultVoucherDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the voucherDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultVoucherDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultVoucherDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the voucherDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultVoucherDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultVoucherDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the voucherDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultVoucherDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField1 is not null
        defaultVoucherDetailsShouldBeFound("freeField1.specified=true");

        // Get all the voucherDetailsList where freeField1 is null
        defaultVoucherDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultVoucherDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the voucherDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultVoucherDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultVoucherDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the voucherDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultVoucherDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultVoucherDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the voucherDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultVoucherDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultVoucherDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the voucherDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultVoucherDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField2 is not null
        defaultVoucherDetailsShouldBeFound("freeField2.specified=true");

        // Get all the voucherDetailsList where freeField2 is null
        defaultVoucherDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultVoucherDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the voucherDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultVoucherDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultVoucherDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the voucherDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultVoucherDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultVoucherDetailsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the voucherDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultVoucherDetailsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultVoucherDetailsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the voucherDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultVoucherDetailsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField3 is not null
        defaultVoucherDetailsShouldBeFound("freeField3.specified=true");

        // Get all the voucherDetailsList where freeField3 is null
        defaultVoucherDetailsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultVoucherDetailsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the voucherDetailsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultVoucherDetailsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultVoucherDetailsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the voucherDetailsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultVoucherDetailsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultVoucherDetailsShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the voucherDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultVoucherDetailsShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultVoucherDetailsShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the voucherDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultVoucherDetailsShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField4 is not null
        defaultVoucherDetailsShouldBeFound("freeField4.specified=true");

        // Get all the voucherDetailsList where freeField4 is null
        defaultVoucherDetailsShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultVoucherDetailsShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the voucherDetailsList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultVoucherDetailsShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultVoucherDetailsShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the voucherDetailsList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultVoucherDetailsShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultVoucherDetailsShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the voucherDetailsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultVoucherDetailsShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultVoucherDetailsShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the voucherDetailsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultVoucherDetailsShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField5 is not null
        defaultVoucherDetailsShouldBeFound("freeField5.specified=true");

        // Get all the voucherDetailsList where freeField5 is null
        defaultVoucherDetailsShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultVoucherDetailsShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the voucherDetailsList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultVoucherDetailsShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultVoucherDetailsShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the voucherDetailsList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultVoucherDetailsShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultVoucherDetailsShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the voucherDetailsList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultVoucherDetailsShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultVoucherDetailsShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the voucherDetailsList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultVoucherDetailsShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField6 is not null
        defaultVoucherDetailsShouldBeFound("freeField6.specified=true");

        // Get all the voucherDetailsList where freeField6 is null
        defaultVoucherDetailsShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultVoucherDetailsShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the voucherDetailsList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultVoucherDetailsShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllVoucherDetailsByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        // Get all the voucherDetailsList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultVoucherDetailsShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the voucherDetailsList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultVoucherDetailsShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVoucherDetailsShouldBeFound(String filter) throws Exception {
        restVoucherDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(voucherDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].accHeadCode").value(hasItem(DEFAULT_ACC_HEAD_CODE)))
            .andExpect(jsonPath("$.[*].creditAccount").value(hasItem(DEFAULT_CREDIT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].debitAccount").value(hasItem(DEFAULT_DEBIT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].transferAmt").value(hasItem(DEFAULT_TRANSFER_AMT.doubleValue())))
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
        restVoucherDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVoucherDetailsShouldNotBeFound(String filter) throws Exception {
        restVoucherDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVoucherDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingVoucherDetails() throws Exception {
        // Get the voucherDetails
        restVoucherDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingVoucherDetails() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        int databaseSizeBeforeUpdate = voucherDetailsRepository.findAll().size();

        // Update the voucherDetails
        VoucherDetails updatedVoucherDetails = voucherDetailsRepository.findById(voucherDetails.getId()).get();
        // Disconnect from session so that the updates on updatedVoucherDetails are not directly saved in db
        em.detach(updatedVoucherDetails);
        updatedVoucherDetails
            .accHeadCode(UPDATED_ACC_HEAD_CODE)
            .creditAccount(UPDATED_CREDIT_ACCOUNT)
            .debitAccount(UPDATED_DEBIT_ACCOUNT)
            .transferAmt(UPDATED_TRANSFER_AMT)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        VoucherDetailsDTO voucherDetailsDTO = voucherDetailsMapper.toDto(updatedVoucherDetails);

        restVoucherDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voucherDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucherDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the VoucherDetails in the database
        List<VoucherDetails> voucherDetailsList = voucherDetailsRepository.findAll();
        assertThat(voucherDetailsList).hasSize(databaseSizeBeforeUpdate);
        VoucherDetails testVoucherDetails = voucherDetailsList.get(voucherDetailsList.size() - 1);
        assertThat(testVoucherDetails.getAccHeadCode()).isEqualTo(UPDATED_ACC_HEAD_CODE);
        assertThat(testVoucherDetails.getCreditAccount()).isEqualTo(UPDATED_CREDIT_ACCOUNT);
        assertThat(testVoucherDetails.getDebitAccount()).isEqualTo(UPDATED_DEBIT_ACCOUNT);
        assertThat(testVoucherDetails.getTransferAmt()).isEqualTo(UPDATED_TRANSFER_AMT);
        assertThat(testVoucherDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testVoucherDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testVoucherDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testVoucherDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testVoucherDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testVoucherDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testVoucherDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testVoucherDetails.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testVoucherDetails.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingVoucherDetails() throws Exception {
        int databaseSizeBeforeUpdate = voucherDetailsRepository.findAll().size();
        voucherDetails.setId(count.incrementAndGet());

        // Create the VoucherDetails
        VoucherDetailsDTO voucherDetailsDTO = voucherDetailsMapper.toDto(voucherDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, voucherDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucherDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherDetails in the database
        List<VoucherDetails> voucherDetailsList = voucherDetailsRepository.findAll();
        assertThat(voucherDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVoucherDetails() throws Exception {
        int databaseSizeBeforeUpdate = voucherDetailsRepository.findAll().size();
        voucherDetails.setId(count.incrementAndGet());

        // Create the VoucherDetails
        VoucherDetailsDTO voucherDetailsDTO = voucherDetailsMapper.toDto(voucherDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(voucherDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherDetails in the database
        List<VoucherDetails> voucherDetailsList = voucherDetailsRepository.findAll();
        assertThat(voucherDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVoucherDetails() throws Exception {
        int databaseSizeBeforeUpdate = voucherDetailsRepository.findAll().size();
        voucherDetails.setId(count.incrementAndGet());

        // Create the VoucherDetails
        VoucherDetailsDTO voucherDetailsDTO = voucherDetailsMapper.toDto(voucherDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(voucherDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VoucherDetails in the database
        List<VoucherDetails> voucherDetailsList = voucherDetailsRepository.findAll();
        assertThat(voucherDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVoucherDetailsWithPatch() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        int databaseSizeBeforeUpdate = voucherDetailsRepository.findAll().size();

        // Update the voucherDetails using partial update
        VoucherDetails partialUpdatedVoucherDetails = new VoucherDetails();
        partialUpdatedVoucherDetails.setId(voucherDetails.getId());

        partialUpdatedVoucherDetails
            .accHeadCode(UPDATED_ACC_HEAD_CODE)
            .debitAccount(UPDATED_DEBIT_ACCOUNT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restVoucherDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucherDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoucherDetails))
            )
            .andExpect(status().isOk());

        // Validate the VoucherDetails in the database
        List<VoucherDetails> voucherDetailsList = voucherDetailsRepository.findAll();
        assertThat(voucherDetailsList).hasSize(databaseSizeBeforeUpdate);
        VoucherDetails testVoucherDetails = voucherDetailsList.get(voucherDetailsList.size() - 1);
        assertThat(testVoucherDetails.getAccHeadCode()).isEqualTo(UPDATED_ACC_HEAD_CODE);
        assertThat(testVoucherDetails.getCreditAccount()).isEqualTo(DEFAULT_CREDIT_ACCOUNT);
        assertThat(testVoucherDetails.getDebitAccount()).isEqualTo(UPDATED_DEBIT_ACCOUNT);
        assertThat(testVoucherDetails.getTransferAmt()).isEqualTo(DEFAULT_TRANSFER_AMT);
        assertThat(testVoucherDetails.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testVoucherDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testVoucherDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testVoucherDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testVoucherDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testVoucherDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testVoucherDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testVoucherDetails.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testVoucherDetails.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateVoucherDetailsWithPatch() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        int databaseSizeBeforeUpdate = voucherDetailsRepository.findAll().size();

        // Update the voucherDetails using partial update
        VoucherDetails partialUpdatedVoucherDetails = new VoucherDetails();
        partialUpdatedVoucherDetails.setId(voucherDetails.getId());

        partialUpdatedVoucherDetails
            .accHeadCode(UPDATED_ACC_HEAD_CODE)
            .creditAccount(UPDATED_CREDIT_ACCOUNT)
            .debitAccount(UPDATED_DEBIT_ACCOUNT)
            .transferAmt(UPDATED_TRANSFER_AMT)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restVoucherDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVoucherDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVoucherDetails))
            )
            .andExpect(status().isOk());

        // Validate the VoucherDetails in the database
        List<VoucherDetails> voucherDetailsList = voucherDetailsRepository.findAll();
        assertThat(voucherDetailsList).hasSize(databaseSizeBeforeUpdate);
        VoucherDetails testVoucherDetails = voucherDetailsList.get(voucherDetailsList.size() - 1);
        assertThat(testVoucherDetails.getAccHeadCode()).isEqualTo(UPDATED_ACC_HEAD_CODE);
        assertThat(testVoucherDetails.getCreditAccount()).isEqualTo(UPDATED_CREDIT_ACCOUNT);
        assertThat(testVoucherDetails.getDebitAccount()).isEqualTo(UPDATED_DEBIT_ACCOUNT);
        assertThat(testVoucherDetails.getTransferAmt()).isEqualTo(UPDATED_TRANSFER_AMT);
        assertThat(testVoucherDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testVoucherDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testVoucherDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testVoucherDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testVoucherDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testVoucherDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testVoucherDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testVoucherDetails.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testVoucherDetails.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingVoucherDetails() throws Exception {
        int databaseSizeBeforeUpdate = voucherDetailsRepository.findAll().size();
        voucherDetails.setId(count.incrementAndGet());

        // Create the VoucherDetails
        VoucherDetailsDTO voucherDetailsDTO = voucherDetailsMapper.toDto(voucherDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoucherDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, voucherDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voucherDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherDetails in the database
        List<VoucherDetails> voucherDetailsList = voucherDetailsRepository.findAll();
        assertThat(voucherDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVoucherDetails() throws Exception {
        int databaseSizeBeforeUpdate = voucherDetailsRepository.findAll().size();
        voucherDetails.setId(count.incrementAndGet());

        // Create the VoucherDetails
        VoucherDetailsDTO voucherDetailsDTO = voucherDetailsMapper.toDto(voucherDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voucherDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VoucherDetails in the database
        List<VoucherDetails> voucherDetailsList = voucherDetailsRepository.findAll();
        assertThat(voucherDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVoucherDetails() throws Exception {
        int databaseSizeBeforeUpdate = voucherDetailsRepository.findAll().size();
        voucherDetails.setId(count.incrementAndGet());

        // Create the VoucherDetails
        VoucherDetailsDTO voucherDetailsDTO = voucherDetailsMapper.toDto(voucherDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVoucherDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(voucherDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VoucherDetails in the database
        List<VoucherDetails> voucherDetailsList = voucherDetailsRepository.findAll();
        assertThat(voucherDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVoucherDetails() throws Exception {
        // Initialize the database
        voucherDetailsRepository.saveAndFlush(voucherDetails);

        int databaseSizeBeforeDelete = voucherDetailsRepository.findAll().size();

        // Delete the voucherDetails
        restVoucherDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, voucherDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VoucherDetails> voucherDetailsList = voucherDetailsRepository.findAll();
        assertThat(voucherDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
