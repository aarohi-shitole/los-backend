package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.City;
import com.techvg.los.domain.District;
import com.techvg.los.domain.EnquiryDetails;
import com.techvg.los.domain.Product;
import com.techvg.los.domain.State;
import com.techvg.los.domain.Taluka;
import com.techvg.los.repository.EnquiryDetailsRepository;
import com.techvg.los.service.criteria.EnquiryDetailsCriteria;
import com.techvg.los.service.dto.EnquiryDetailsDTO;
import com.techvg.los.service.mapper.EnquiryDetailsMapper;
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
 * Integration tests for the {@link EnquiryDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnquiryDetailsResourceIT {

    private static final String DEFAULT_CUSTOMER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUB_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PURPOSE = "AAAAAAAAAA";
    private static final String UPDATED_PURPOSE = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final String DEFAULT_REFRENCE_NO = "AAAAAAAAAA";
    private static final String UPDATED_REFRENCE_NO = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/enquiry-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EnquiryDetailsRepository enquiryDetailsRepository;

    @Autowired
    private EnquiryDetailsMapper enquiryDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnquiryDetailsMockMvc;

    private EnquiryDetails enquiryDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnquiryDetails createEntity(EntityManager em) {
        EnquiryDetails enquiryDetails = new EnquiryDetails()
            .customerName(DEFAULT_CUSTOMER_NAME)
            .subName(DEFAULT_SUB_NAME)
            .purpose(DEFAULT_PURPOSE)
            .mobileNo(DEFAULT_MOBILE_NO)
            .amount(DEFAULT_AMOUNT)
            .refrenceNo(DEFAULT_REFRENCE_NO)
            .isDeleted(DEFAULT_IS_DELETED)
            .isActive(DEFAULT_IS_ACTIVE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4);
        return enquiryDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnquiryDetails createUpdatedEntity(EntityManager em) {
        EnquiryDetails enquiryDetails = new EnquiryDetails()
            .customerName(UPDATED_CUSTOMER_NAME)
            .subName(UPDATED_SUB_NAME)
            .purpose(UPDATED_PURPOSE)
            .mobileNo(UPDATED_MOBILE_NO)
            .amount(UPDATED_AMOUNT)
            .refrenceNo(UPDATED_REFRENCE_NO)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        return enquiryDetails;
    }

    @BeforeEach
    public void initTest() {
        enquiryDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createEnquiryDetails() throws Exception {
        int databaseSizeBeforeCreate = enquiryDetailsRepository.findAll().size();
        // Create the EnquiryDetails
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);
        restEnquiryDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EnquiryDetails in the database
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        EnquiryDetails testEnquiryDetails = enquiryDetailsList.get(enquiryDetailsList.size() - 1);
        assertThat(testEnquiryDetails.getCustomerName()).isEqualTo(DEFAULT_CUSTOMER_NAME);
        assertThat(testEnquiryDetails.getSubName()).isEqualTo(DEFAULT_SUB_NAME);
        assertThat(testEnquiryDetails.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testEnquiryDetails.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testEnquiryDetails.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testEnquiryDetails.getRefrenceNo()).isEqualTo(DEFAULT_REFRENCE_NO);
        assertThat(testEnquiryDetails.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testEnquiryDetails.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testEnquiryDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testEnquiryDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testEnquiryDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testEnquiryDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testEnquiryDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testEnquiryDetails.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void createEnquiryDetailsWithExistingId() throws Exception {
        // Create the EnquiryDetails with an existing ID
        enquiryDetails.setId(1L);
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        int databaseSizeBeforeCreate = enquiryDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnquiryDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnquiryDetails in the database
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEnquiryDetails() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList
        restEnquiryDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enquiryDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].subName").value(hasItem(DEFAULT_SUB_NAME)))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].refrenceNo").value(hasItem(DEFAULT_REFRENCE_NO)))
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
    void getEnquiryDetails() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get the enquiryDetails
        restEnquiryDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, enquiryDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enquiryDetails.getId().intValue()))
            .andExpect(jsonPath("$.customerName").value(DEFAULT_CUSTOMER_NAME))
            .andExpect(jsonPath("$.subName").value(DEFAULT_SUB_NAME))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.refrenceNo").value(DEFAULT_REFRENCE_NO))
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
    void getEnquiryDetailsByIdFiltering() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        Long id = enquiryDetails.getId();

        defaultEnquiryDetailsShouldBeFound("id.equals=" + id);
        defaultEnquiryDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultEnquiryDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEnquiryDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultEnquiryDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEnquiryDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByCustomerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where customerName equals to DEFAULT_CUSTOMER_NAME
        defaultEnquiryDetailsShouldBeFound("customerName.equals=" + DEFAULT_CUSTOMER_NAME);

        // Get all the enquiryDetailsList where customerName equals to UPDATED_CUSTOMER_NAME
        defaultEnquiryDetailsShouldNotBeFound("customerName.equals=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByCustomerNameIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where customerName in DEFAULT_CUSTOMER_NAME or UPDATED_CUSTOMER_NAME
        defaultEnquiryDetailsShouldBeFound("customerName.in=" + DEFAULT_CUSTOMER_NAME + "," + UPDATED_CUSTOMER_NAME);

        // Get all the enquiryDetailsList where customerName equals to UPDATED_CUSTOMER_NAME
        defaultEnquiryDetailsShouldNotBeFound("customerName.in=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByCustomerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where customerName is not null
        defaultEnquiryDetailsShouldBeFound("customerName.specified=true");

        // Get all the enquiryDetailsList where customerName is null
        defaultEnquiryDetailsShouldNotBeFound("customerName.specified=false");
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByCustomerNameContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where customerName contains DEFAULT_CUSTOMER_NAME
        defaultEnquiryDetailsShouldBeFound("customerName.contains=" + DEFAULT_CUSTOMER_NAME);

        // Get all the enquiryDetailsList where customerName contains UPDATED_CUSTOMER_NAME
        defaultEnquiryDetailsShouldNotBeFound("customerName.contains=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByCustomerNameNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where customerName does not contain DEFAULT_CUSTOMER_NAME
        defaultEnquiryDetailsShouldNotBeFound("customerName.doesNotContain=" + DEFAULT_CUSTOMER_NAME);

        // Get all the enquiryDetailsList where customerName does not contain UPDATED_CUSTOMER_NAME
        defaultEnquiryDetailsShouldBeFound("customerName.doesNotContain=" + UPDATED_CUSTOMER_NAME);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsBySubNameIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where subName equals to DEFAULT_SUB_NAME
        defaultEnquiryDetailsShouldBeFound("subName.equals=" + DEFAULT_SUB_NAME);

        // Get all the enquiryDetailsList where subName equals to UPDATED_SUB_NAME
        defaultEnquiryDetailsShouldNotBeFound("subName.equals=" + UPDATED_SUB_NAME);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsBySubNameIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where subName in DEFAULT_SUB_NAME or UPDATED_SUB_NAME
        defaultEnquiryDetailsShouldBeFound("subName.in=" + DEFAULT_SUB_NAME + "," + UPDATED_SUB_NAME);

        // Get all the enquiryDetailsList where subName equals to UPDATED_SUB_NAME
        defaultEnquiryDetailsShouldNotBeFound("subName.in=" + UPDATED_SUB_NAME);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsBySubNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where subName is not null
        defaultEnquiryDetailsShouldBeFound("subName.specified=true");

        // Get all the enquiryDetailsList where subName is null
        defaultEnquiryDetailsShouldNotBeFound("subName.specified=false");
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsBySubNameContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where subName contains DEFAULT_SUB_NAME
        defaultEnquiryDetailsShouldBeFound("subName.contains=" + DEFAULT_SUB_NAME);

        // Get all the enquiryDetailsList where subName contains UPDATED_SUB_NAME
        defaultEnquiryDetailsShouldNotBeFound("subName.contains=" + UPDATED_SUB_NAME);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsBySubNameNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where subName does not contain DEFAULT_SUB_NAME
        defaultEnquiryDetailsShouldNotBeFound("subName.doesNotContain=" + DEFAULT_SUB_NAME);

        // Get all the enquiryDetailsList where subName does not contain UPDATED_SUB_NAME
        defaultEnquiryDetailsShouldBeFound("subName.doesNotContain=" + UPDATED_SUB_NAME);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByPurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where purpose equals to DEFAULT_PURPOSE
        defaultEnquiryDetailsShouldBeFound("purpose.equals=" + DEFAULT_PURPOSE);

        // Get all the enquiryDetailsList where purpose equals to UPDATED_PURPOSE
        defaultEnquiryDetailsShouldNotBeFound("purpose.equals=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByPurposeIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where purpose in DEFAULT_PURPOSE or UPDATED_PURPOSE
        defaultEnquiryDetailsShouldBeFound("purpose.in=" + DEFAULT_PURPOSE + "," + UPDATED_PURPOSE);

        // Get all the enquiryDetailsList where purpose equals to UPDATED_PURPOSE
        defaultEnquiryDetailsShouldNotBeFound("purpose.in=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByPurposeIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where purpose is not null
        defaultEnquiryDetailsShouldBeFound("purpose.specified=true");

        // Get all the enquiryDetailsList where purpose is null
        defaultEnquiryDetailsShouldNotBeFound("purpose.specified=false");
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByPurposeContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where purpose contains DEFAULT_PURPOSE
        defaultEnquiryDetailsShouldBeFound("purpose.contains=" + DEFAULT_PURPOSE);

        // Get all the enquiryDetailsList where purpose contains UPDATED_PURPOSE
        defaultEnquiryDetailsShouldNotBeFound("purpose.contains=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByPurposeNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where purpose does not contain DEFAULT_PURPOSE
        defaultEnquiryDetailsShouldNotBeFound("purpose.doesNotContain=" + DEFAULT_PURPOSE);

        // Get all the enquiryDetailsList where purpose does not contain UPDATED_PURPOSE
        defaultEnquiryDetailsShouldBeFound("purpose.doesNotContain=" + UPDATED_PURPOSE);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultEnquiryDetailsShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the enquiryDetailsList where mobileNo equals to UPDATED_MOBILE_NO
        defaultEnquiryDetailsShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultEnquiryDetailsShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the enquiryDetailsList where mobileNo equals to UPDATED_MOBILE_NO
        defaultEnquiryDetailsShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where mobileNo is not null
        defaultEnquiryDetailsShouldBeFound("mobileNo.specified=true");

        // Get all the enquiryDetailsList where mobileNo is null
        defaultEnquiryDetailsShouldNotBeFound("mobileNo.specified=false");
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByMobileNoContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where mobileNo contains DEFAULT_MOBILE_NO
        defaultEnquiryDetailsShouldBeFound("mobileNo.contains=" + DEFAULT_MOBILE_NO);

        // Get all the enquiryDetailsList where mobileNo contains UPDATED_MOBILE_NO
        defaultEnquiryDetailsShouldNotBeFound("mobileNo.contains=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByMobileNoNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where mobileNo does not contain DEFAULT_MOBILE_NO
        defaultEnquiryDetailsShouldNotBeFound("mobileNo.doesNotContain=" + DEFAULT_MOBILE_NO);

        // Get all the enquiryDetailsList where mobileNo does not contain UPDATED_MOBILE_NO
        defaultEnquiryDetailsShouldBeFound("mobileNo.doesNotContain=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where amount equals to DEFAULT_AMOUNT
        defaultEnquiryDetailsShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the enquiryDetailsList where amount equals to UPDATED_AMOUNT
        defaultEnquiryDetailsShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultEnquiryDetailsShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the enquiryDetailsList where amount equals to UPDATED_AMOUNT
        defaultEnquiryDetailsShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where amount is not null
        defaultEnquiryDetailsShouldBeFound("amount.specified=true");

        // Get all the enquiryDetailsList where amount is null
        defaultEnquiryDetailsShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultEnquiryDetailsShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the enquiryDetailsList where amount is greater than or equal to UPDATED_AMOUNT
        defaultEnquiryDetailsShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where amount is less than or equal to DEFAULT_AMOUNT
        defaultEnquiryDetailsShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the enquiryDetailsList where amount is less than or equal to SMALLER_AMOUNT
        defaultEnquiryDetailsShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where amount is less than DEFAULT_AMOUNT
        defaultEnquiryDetailsShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the enquiryDetailsList where amount is less than UPDATED_AMOUNT
        defaultEnquiryDetailsShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where amount is greater than DEFAULT_AMOUNT
        defaultEnquiryDetailsShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the enquiryDetailsList where amount is greater than SMALLER_AMOUNT
        defaultEnquiryDetailsShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByRefrenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where refrenceNo equals to DEFAULT_REFRENCE_NO
        defaultEnquiryDetailsShouldBeFound("refrenceNo.equals=" + DEFAULT_REFRENCE_NO);

        // Get all the enquiryDetailsList where refrenceNo equals to UPDATED_REFRENCE_NO
        defaultEnquiryDetailsShouldNotBeFound("refrenceNo.equals=" + UPDATED_REFRENCE_NO);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByRefrenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where refrenceNo in DEFAULT_REFRENCE_NO or UPDATED_REFRENCE_NO
        defaultEnquiryDetailsShouldBeFound("refrenceNo.in=" + DEFAULT_REFRENCE_NO + "," + UPDATED_REFRENCE_NO);

        // Get all the enquiryDetailsList where refrenceNo equals to UPDATED_REFRENCE_NO
        defaultEnquiryDetailsShouldNotBeFound("refrenceNo.in=" + UPDATED_REFRENCE_NO);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByRefrenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where refrenceNo is not null
        defaultEnquiryDetailsShouldBeFound("refrenceNo.specified=true");

        // Get all the enquiryDetailsList where refrenceNo is null
        defaultEnquiryDetailsShouldNotBeFound("refrenceNo.specified=false");
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByRefrenceNoContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where refrenceNo contains DEFAULT_REFRENCE_NO
        defaultEnquiryDetailsShouldBeFound("refrenceNo.contains=" + DEFAULT_REFRENCE_NO);

        // Get all the enquiryDetailsList where refrenceNo contains UPDATED_REFRENCE_NO
        defaultEnquiryDetailsShouldNotBeFound("refrenceNo.contains=" + UPDATED_REFRENCE_NO);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByRefrenceNoNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where refrenceNo does not contain DEFAULT_REFRENCE_NO
        defaultEnquiryDetailsShouldNotBeFound("refrenceNo.doesNotContain=" + DEFAULT_REFRENCE_NO);

        // Get all the enquiryDetailsList where refrenceNo does not contain UPDATED_REFRENCE_NO
        defaultEnquiryDetailsShouldBeFound("refrenceNo.doesNotContain=" + UPDATED_REFRENCE_NO);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultEnquiryDetailsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the enquiryDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultEnquiryDetailsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultEnquiryDetailsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the enquiryDetailsList where isDeleted equals to UPDATED_IS_DELETED
        defaultEnquiryDetailsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where isDeleted is not null
        defaultEnquiryDetailsShouldBeFound("isDeleted.specified=true");

        // Get all the enquiryDetailsList where isDeleted is null
        defaultEnquiryDetailsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where isActive equals to DEFAULT_IS_ACTIVE
        defaultEnquiryDetailsShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the enquiryDetailsList where isActive equals to UPDATED_IS_ACTIVE
        defaultEnquiryDetailsShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultEnquiryDetailsShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the enquiryDetailsList where isActive equals to UPDATED_IS_ACTIVE
        defaultEnquiryDetailsShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where isActive is not null
        defaultEnquiryDetailsShouldBeFound("isActive.specified=true");

        // Get all the enquiryDetailsList where isActive is null
        defaultEnquiryDetailsShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultEnquiryDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the enquiryDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEnquiryDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultEnquiryDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the enquiryDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEnquiryDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where lastModified is not null
        defaultEnquiryDetailsShouldBeFound("lastModified.specified=true");

        // Get all the enquiryDetailsList where lastModified is null
        defaultEnquiryDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultEnquiryDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the enquiryDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEnquiryDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultEnquiryDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the enquiryDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEnquiryDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where lastModifiedBy is not null
        defaultEnquiryDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the enquiryDetailsList where lastModifiedBy is null
        defaultEnquiryDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultEnquiryDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the enquiryDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultEnquiryDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultEnquiryDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the enquiryDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultEnquiryDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultEnquiryDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the enquiryDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultEnquiryDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultEnquiryDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the enquiryDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultEnquiryDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField1 is not null
        defaultEnquiryDetailsShouldBeFound("freeField1.specified=true");

        // Get all the enquiryDetailsList where freeField1 is null
        defaultEnquiryDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultEnquiryDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the enquiryDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultEnquiryDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultEnquiryDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the enquiryDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultEnquiryDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultEnquiryDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the enquiryDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultEnquiryDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultEnquiryDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the enquiryDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultEnquiryDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField2 is not null
        defaultEnquiryDetailsShouldBeFound("freeField2.specified=true");

        // Get all the enquiryDetailsList where freeField2 is null
        defaultEnquiryDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultEnquiryDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the enquiryDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultEnquiryDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultEnquiryDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the enquiryDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultEnquiryDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultEnquiryDetailsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the enquiryDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultEnquiryDetailsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultEnquiryDetailsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the enquiryDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultEnquiryDetailsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField3 is not null
        defaultEnquiryDetailsShouldBeFound("freeField3.specified=true");

        // Get all the enquiryDetailsList where freeField3 is null
        defaultEnquiryDetailsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultEnquiryDetailsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the enquiryDetailsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultEnquiryDetailsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultEnquiryDetailsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the enquiryDetailsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultEnquiryDetailsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultEnquiryDetailsShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the enquiryDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultEnquiryDetailsShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultEnquiryDetailsShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the enquiryDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultEnquiryDetailsShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField4 is not null
        defaultEnquiryDetailsShouldBeFound("freeField4.specified=true");

        // Get all the enquiryDetailsList where freeField4 is null
        defaultEnquiryDetailsShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultEnquiryDetailsShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the enquiryDetailsList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultEnquiryDetailsShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultEnquiryDetailsShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the enquiryDetailsList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultEnquiryDetailsShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByStateIsEqualToSomething() throws Exception {
        State state;
        if (TestUtil.findAll(em, State.class).isEmpty()) {
            enquiryDetailsRepository.saveAndFlush(enquiryDetails);
            state = StateResourceIT.createEntity(em);
        } else {
            state = TestUtil.findAll(em, State.class).get(0);
        }
        em.persist(state);
        em.flush();
        enquiryDetails.setState(state);
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);
        Long stateId = state.getId();

        // Get all the enquiryDetailsList where state equals to stateId
        defaultEnquiryDetailsShouldBeFound("stateId.equals=" + stateId);

        // Get all the enquiryDetailsList where state equals to (stateId + 1)
        defaultEnquiryDetailsShouldNotBeFound("stateId.equals=" + (stateId + 1));
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByDistrictIsEqualToSomething() throws Exception {
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            enquiryDetailsRepository.saveAndFlush(enquiryDetails);
            district = DistrictResourceIT.createEntity(em);
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        em.persist(district);
        em.flush();
        enquiryDetails.setDistrict(district);
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);
        Long districtId = district.getId();

        // Get all the enquiryDetailsList where district equals to districtId
        defaultEnquiryDetailsShouldBeFound("districtId.equals=" + districtId);

        // Get all the enquiryDetailsList where district equals to (districtId + 1)
        defaultEnquiryDetailsShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByTalukaIsEqualToSomething() throws Exception {
        Taluka taluka;
        if (TestUtil.findAll(em, Taluka.class).isEmpty()) {
            enquiryDetailsRepository.saveAndFlush(enquiryDetails);
            taluka = TalukaResourceIT.createEntity(em);
        } else {
            taluka = TestUtil.findAll(em, Taluka.class).get(0);
        }
        em.persist(taluka);
        em.flush();
        enquiryDetails.setTaluka(taluka);
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);
        Long talukaId = taluka.getId();

        // Get all the enquiryDetailsList where taluka equals to talukaId
        defaultEnquiryDetailsShouldBeFound("talukaId.equals=" + talukaId);

        // Get all the enquiryDetailsList where taluka equals to (talukaId + 1)
        defaultEnquiryDetailsShouldNotBeFound("talukaId.equals=" + (talukaId + 1));
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByCityIsEqualToSomething() throws Exception {
        City city;
        if (TestUtil.findAll(em, City.class).isEmpty()) {
            enquiryDetailsRepository.saveAndFlush(enquiryDetails);
            city = CityResourceIT.createEntity(em);
        } else {
            city = TestUtil.findAll(em, City.class).get(0);
        }
        em.persist(city);
        em.flush();
        enquiryDetails.setCity(city);
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);
        Long cityId = city.getId();

        // Get all the enquiryDetailsList where city equals to cityId
        defaultEnquiryDetailsShouldBeFound("cityId.equals=" + cityId);

        // Get all the enquiryDetailsList where city equals to (cityId + 1)
        defaultEnquiryDetailsShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    @Test
    @Transactional
    void getAllEnquiryDetailsByProductIsEqualToSomething() throws Exception {
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            enquiryDetailsRepository.saveAndFlush(enquiryDetails);
            product = ProductResourceIT.createEntity(em);
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        enquiryDetails.setProduct(product);
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);
        Long productId = product.getId();

        // Get all the enquiryDetailsList where product equals to productId
        defaultEnquiryDetailsShouldBeFound("productId.equals=" + productId);

        // Get all the enquiryDetailsList where product equals to (productId + 1)
        defaultEnquiryDetailsShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEnquiryDetailsShouldBeFound(String filter) throws Exception {
        restEnquiryDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enquiryDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].customerName").value(hasItem(DEFAULT_CUSTOMER_NAME)))
            .andExpect(jsonPath("$.[*].subName").value(hasItem(DEFAULT_SUB_NAME)))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].refrenceNo").value(hasItem(DEFAULT_REFRENCE_NO)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));

        // Check, that the count call also returns 1
        restEnquiryDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEnquiryDetailsShouldNotBeFound(String filter) throws Exception {
        restEnquiryDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnquiryDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEnquiryDetails() throws Exception {
        // Get the enquiryDetails
        restEnquiryDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEnquiryDetails() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        int databaseSizeBeforeUpdate = enquiryDetailsRepository.findAll().size();

        // Update the enquiryDetails
        EnquiryDetails updatedEnquiryDetails = enquiryDetailsRepository.findById(enquiryDetails.getId()).get();
        // Disconnect from session so that the updates on updatedEnquiryDetails are not directly saved in db
        em.detach(updatedEnquiryDetails);
        updatedEnquiryDetails
            .customerName(UPDATED_CUSTOMER_NAME)
            .subName(UPDATED_SUB_NAME)
            .purpose(UPDATED_PURPOSE)
            .mobileNo(UPDATED_MOBILE_NO)
            .amount(UPDATED_AMOUNT)
            .refrenceNo(UPDATED_REFRENCE_NO)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(updatedEnquiryDetails);

        restEnquiryDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enquiryDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the EnquiryDetails in the database
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeUpdate);
        EnquiryDetails testEnquiryDetails = enquiryDetailsList.get(enquiryDetailsList.size() - 1);
        assertThat(testEnquiryDetails.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testEnquiryDetails.getSubName()).isEqualTo(UPDATED_SUB_NAME);
        assertThat(testEnquiryDetails.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testEnquiryDetails.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testEnquiryDetails.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testEnquiryDetails.getRefrenceNo()).isEqualTo(UPDATED_REFRENCE_NO);
        assertThat(testEnquiryDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEnquiryDetails.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testEnquiryDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEnquiryDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEnquiryDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testEnquiryDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testEnquiryDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testEnquiryDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void putNonExistingEnquiryDetails() throws Exception {
        int databaseSizeBeforeUpdate = enquiryDetailsRepository.findAll().size();
        enquiryDetails.setId(count.incrementAndGet());

        // Create the EnquiryDetails
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnquiryDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, enquiryDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnquiryDetails in the database
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnquiryDetails() throws Exception {
        int databaseSizeBeforeUpdate = enquiryDetailsRepository.findAll().size();
        enquiryDetails.setId(count.incrementAndGet());

        // Create the EnquiryDetails
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnquiryDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnquiryDetails in the database
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnquiryDetails() throws Exception {
        int databaseSizeBeforeUpdate = enquiryDetailsRepository.findAll().size();
        enquiryDetails.setId(count.incrementAndGet());

        // Create the EnquiryDetails
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnquiryDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnquiryDetails in the database
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnquiryDetailsWithPatch() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        int databaseSizeBeforeUpdate = enquiryDetailsRepository.findAll().size();

        // Update the enquiryDetails using partial update
        EnquiryDetails partialUpdatedEnquiryDetails = new EnquiryDetails();
        partialUpdatedEnquiryDetails.setId(enquiryDetails.getId());

        partialUpdatedEnquiryDetails
            .customerName(UPDATED_CUSTOMER_NAME)
            .subName(UPDATED_SUB_NAME)
            .amount(UPDATED_AMOUNT)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);

        restEnquiryDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnquiryDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnquiryDetails))
            )
            .andExpect(status().isOk());

        // Validate the EnquiryDetails in the database
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeUpdate);
        EnquiryDetails testEnquiryDetails = enquiryDetailsList.get(enquiryDetailsList.size() - 1);
        assertThat(testEnquiryDetails.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testEnquiryDetails.getSubName()).isEqualTo(UPDATED_SUB_NAME);
        assertThat(testEnquiryDetails.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testEnquiryDetails.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testEnquiryDetails.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testEnquiryDetails.getRefrenceNo()).isEqualTo(DEFAULT_REFRENCE_NO);
        assertThat(testEnquiryDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEnquiryDetails.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testEnquiryDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testEnquiryDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testEnquiryDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testEnquiryDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testEnquiryDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testEnquiryDetails.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void fullUpdateEnquiryDetailsWithPatch() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        int databaseSizeBeforeUpdate = enquiryDetailsRepository.findAll().size();

        // Update the enquiryDetails using partial update
        EnquiryDetails partialUpdatedEnquiryDetails = new EnquiryDetails();
        partialUpdatedEnquiryDetails.setId(enquiryDetails.getId());

        partialUpdatedEnquiryDetails
            .customerName(UPDATED_CUSTOMER_NAME)
            .subName(UPDATED_SUB_NAME)
            .purpose(UPDATED_PURPOSE)
            .mobileNo(UPDATED_MOBILE_NO)
            .amount(UPDATED_AMOUNT)
            .refrenceNo(UPDATED_REFRENCE_NO)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restEnquiryDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnquiryDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnquiryDetails))
            )
            .andExpect(status().isOk());

        // Validate the EnquiryDetails in the database
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeUpdate);
        EnquiryDetails testEnquiryDetails = enquiryDetailsList.get(enquiryDetailsList.size() - 1);
        assertThat(testEnquiryDetails.getCustomerName()).isEqualTo(UPDATED_CUSTOMER_NAME);
        assertThat(testEnquiryDetails.getSubName()).isEqualTo(UPDATED_SUB_NAME);
        assertThat(testEnquiryDetails.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testEnquiryDetails.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testEnquiryDetails.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testEnquiryDetails.getRefrenceNo()).isEqualTo(UPDATED_REFRENCE_NO);
        assertThat(testEnquiryDetails.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEnquiryDetails.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testEnquiryDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEnquiryDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEnquiryDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testEnquiryDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testEnquiryDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testEnquiryDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void patchNonExistingEnquiryDetails() throws Exception {
        int databaseSizeBeforeUpdate = enquiryDetailsRepository.findAll().size();
        enquiryDetails.setId(count.incrementAndGet());

        // Create the EnquiryDetails
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnquiryDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, enquiryDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnquiryDetails in the database
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnquiryDetails() throws Exception {
        int databaseSizeBeforeUpdate = enquiryDetailsRepository.findAll().size();
        enquiryDetails.setId(count.incrementAndGet());

        // Create the EnquiryDetails
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnquiryDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EnquiryDetails in the database
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnquiryDetails() throws Exception {
        int databaseSizeBeforeUpdate = enquiryDetailsRepository.findAll().size();
        enquiryDetails.setId(count.incrementAndGet());

        // Create the EnquiryDetails
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnquiryDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EnquiryDetails in the database
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnquiryDetails() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        int databaseSizeBeforeDelete = enquiryDetailsRepository.findAll().size();

        // Delete the enquiryDetails
        restEnquiryDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, enquiryDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
