package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.LoanCatagory;
import com.techvg.los.repository.LoanCatagoryRepository;
import com.techvg.los.service.criteria.LoanCatagoryCriteria;
import com.techvg.los.service.dto.LoanCatagoryDTO;
import com.techvg.los.service.mapper.LoanCatagoryMapper;
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
 * Integration tests for the {@link LoanCatagoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LoanCatagoryResourceIT {

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DECRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DECRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_OFFER = "AAAAAAAAAA";
    private static final String UPDATED_OFFER = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/loan-catagories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LoanCatagoryRepository loanCatagoryRepository;

    @Autowired
    private LoanCatagoryMapper loanCatagoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLoanCatagoryMockMvc;

    private LoanCatagory loanCatagory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanCatagory createEntity(EntityManager em) {
        LoanCatagory loanCatagory = new LoanCatagory()
            .productName(DEFAULT_PRODUCT_NAME)
            .decription(DEFAULT_DECRIPTION)
            .value(DEFAULT_VALUE)
            .code(DEFAULT_CODE)
            .offer(DEFAULT_OFFER)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4);
        return loanCatagory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LoanCatagory createUpdatedEntity(EntityManager em) {
        LoanCatagory loanCatagory = new LoanCatagory()
            .productName(UPDATED_PRODUCT_NAME)
            .decription(UPDATED_DECRIPTION)
            .value(UPDATED_VALUE)
            .code(UPDATED_CODE)
            .offer(UPDATED_OFFER)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        return loanCatagory;
    }

    @BeforeEach
    public void initTest() {
        loanCatagory = createEntity(em);
    }

    @Test
    @Transactional
    void createLoanCatagory() throws Exception {
        int databaseSizeBeforeCreate = loanCatagoryRepository.findAll().size();
        // Create the LoanCatagory
        LoanCatagoryDTO loanCatagoryDTO = loanCatagoryMapper.toDto(loanCatagory);
        restLoanCatagoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanCatagoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LoanCatagory in the database
        List<LoanCatagory> loanCatagoryList = loanCatagoryRepository.findAll();
        assertThat(loanCatagoryList).hasSize(databaseSizeBeforeCreate + 1);
        LoanCatagory testLoanCatagory = loanCatagoryList.get(loanCatagoryList.size() - 1);
        assertThat(testLoanCatagory.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testLoanCatagory.getDecription()).isEqualTo(DEFAULT_DECRIPTION);
        assertThat(testLoanCatagory.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testLoanCatagory.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testLoanCatagory.getOffer()).isEqualTo(DEFAULT_OFFER);
        assertThat(testLoanCatagory.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testLoanCatagory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testLoanCatagory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLoanCatagory.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testLoanCatagory.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testLoanCatagory.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLoanCatagory.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testLoanCatagory.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testLoanCatagory.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void createLoanCatagoryWithExistingId() throws Exception {
        // Create the LoanCatagory with an existing ID
        loanCatagory.setId(1L);
        LoanCatagoryDTO loanCatagoryDTO = loanCatagoryMapper.toDto(loanCatagory);

        int databaseSizeBeforeCreate = loanCatagoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoanCatagoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanCatagoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanCatagory in the database
        List<LoanCatagory> loanCatagoryList = loanCatagoryRepository.findAll();
        assertThat(loanCatagoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllLoanCatagories() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList
        restLoanCatagoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanCatagory.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].offer").value(hasItem(DEFAULT_OFFER)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));
    }

    @Test
    @Transactional
    void getLoanCatagory() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get the loanCatagory
        restLoanCatagoryMockMvc
            .perform(get(ENTITY_API_URL_ID, loanCatagory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(loanCatagory.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME))
            .andExpect(jsonPath("$.decription").value(DEFAULT_DECRIPTION))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.offer").value(DEFAULT_OFFER))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4));
    }

    @Test
    @Transactional
    void getLoanCatagoriesByIdFiltering() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        Long id = loanCatagory.getId();

        defaultLoanCatagoryShouldBeFound("id.equals=" + id);
        defaultLoanCatagoryShouldNotBeFound("id.notEquals=" + id);

        defaultLoanCatagoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLoanCatagoryShouldNotBeFound("id.greaterThan=" + id);

        defaultLoanCatagoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLoanCatagoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByProductNameIsEqualToSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where productName equals to DEFAULT_PRODUCT_NAME
        defaultLoanCatagoryShouldBeFound("productName.equals=" + DEFAULT_PRODUCT_NAME);

        // Get all the loanCatagoryList where productName equals to UPDATED_PRODUCT_NAME
        defaultLoanCatagoryShouldNotBeFound("productName.equals=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByProductNameIsInShouldWork() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where productName in DEFAULT_PRODUCT_NAME or UPDATED_PRODUCT_NAME
        defaultLoanCatagoryShouldBeFound("productName.in=" + DEFAULT_PRODUCT_NAME + "," + UPDATED_PRODUCT_NAME);

        // Get all the loanCatagoryList where productName equals to UPDATED_PRODUCT_NAME
        defaultLoanCatagoryShouldNotBeFound("productName.in=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByProductNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where productName is not null
        defaultLoanCatagoryShouldBeFound("productName.specified=true");

        // Get all the loanCatagoryList where productName is null
        defaultLoanCatagoryShouldNotBeFound("productName.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByProductNameContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where productName contains DEFAULT_PRODUCT_NAME
        defaultLoanCatagoryShouldBeFound("productName.contains=" + DEFAULT_PRODUCT_NAME);

        // Get all the loanCatagoryList where productName contains UPDATED_PRODUCT_NAME
        defaultLoanCatagoryShouldNotBeFound("productName.contains=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByProductNameNotContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where productName does not contain DEFAULT_PRODUCT_NAME
        defaultLoanCatagoryShouldNotBeFound("productName.doesNotContain=" + DEFAULT_PRODUCT_NAME);

        // Get all the loanCatagoryList where productName does not contain UPDATED_PRODUCT_NAME
        defaultLoanCatagoryShouldBeFound("productName.doesNotContain=" + UPDATED_PRODUCT_NAME);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByDecriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where decription equals to DEFAULT_DECRIPTION
        defaultLoanCatagoryShouldBeFound("decription.equals=" + DEFAULT_DECRIPTION);

        // Get all the loanCatagoryList where decription equals to UPDATED_DECRIPTION
        defaultLoanCatagoryShouldNotBeFound("decription.equals=" + UPDATED_DECRIPTION);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByDecriptionIsInShouldWork() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where decription in DEFAULT_DECRIPTION or UPDATED_DECRIPTION
        defaultLoanCatagoryShouldBeFound("decription.in=" + DEFAULT_DECRIPTION + "," + UPDATED_DECRIPTION);

        // Get all the loanCatagoryList where decription equals to UPDATED_DECRIPTION
        defaultLoanCatagoryShouldNotBeFound("decription.in=" + UPDATED_DECRIPTION);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByDecriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where decription is not null
        defaultLoanCatagoryShouldBeFound("decription.specified=true");

        // Get all the loanCatagoryList where decription is null
        defaultLoanCatagoryShouldNotBeFound("decription.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByDecriptionContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where decription contains DEFAULT_DECRIPTION
        defaultLoanCatagoryShouldBeFound("decription.contains=" + DEFAULT_DECRIPTION);

        // Get all the loanCatagoryList where decription contains UPDATED_DECRIPTION
        defaultLoanCatagoryShouldNotBeFound("decription.contains=" + UPDATED_DECRIPTION);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByDecriptionNotContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where decription does not contain DEFAULT_DECRIPTION
        defaultLoanCatagoryShouldNotBeFound("decription.doesNotContain=" + DEFAULT_DECRIPTION);

        // Get all the loanCatagoryList where decription does not contain UPDATED_DECRIPTION
        defaultLoanCatagoryShouldBeFound("decription.doesNotContain=" + UPDATED_DECRIPTION);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where value equals to DEFAULT_VALUE
        defaultLoanCatagoryShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the loanCatagoryList where value equals to UPDATED_VALUE
        defaultLoanCatagoryShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultLoanCatagoryShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the loanCatagoryList where value equals to UPDATED_VALUE
        defaultLoanCatagoryShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where value is not null
        defaultLoanCatagoryShouldBeFound("value.specified=true");

        // Get all the loanCatagoryList where value is null
        defaultLoanCatagoryShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByValueContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where value contains DEFAULT_VALUE
        defaultLoanCatagoryShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the loanCatagoryList where value contains UPDATED_VALUE
        defaultLoanCatagoryShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByValueNotContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where value does not contain DEFAULT_VALUE
        defaultLoanCatagoryShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the loanCatagoryList where value does not contain UPDATED_VALUE
        defaultLoanCatagoryShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where code equals to DEFAULT_CODE
        defaultLoanCatagoryShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the loanCatagoryList where code equals to UPDATED_CODE
        defaultLoanCatagoryShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where code in DEFAULT_CODE or UPDATED_CODE
        defaultLoanCatagoryShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the loanCatagoryList where code equals to UPDATED_CODE
        defaultLoanCatagoryShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where code is not null
        defaultLoanCatagoryShouldBeFound("code.specified=true");

        // Get all the loanCatagoryList where code is null
        defaultLoanCatagoryShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByCodeContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where code contains DEFAULT_CODE
        defaultLoanCatagoryShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the loanCatagoryList where code contains UPDATED_CODE
        defaultLoanCatagoryShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where code does not contain DEFAULT_CODE
        defaultLoanCatagoryShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the loanCatagoryList where code does not contain UPDATED_CODE
        defaultLoanCatagoryShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByOfferIsEqualToSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where offer equals to DEFAULT_OFFER
        defaultLoanCatagoryShouldBeFound("offer.equals=" + DEFAULT_OFFER);

        // Get all the loanCatagoryList where offer equals to UPDATED_OFFER
        defaultLoanCatagoryShouldNotBeFound("offer.equals=" + UPDATED_OFFER);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByOfferIsInShouldWork() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where offer in DEFAULT_OFFER or UPDATED_OFFER
        defaultLoanCatagoryShouldBeFound("offer.in=" + DEFAULT_OFFER + "," + UPDATED_OFFER);

        // Get all the loanCatagoryList where offer equals to UPDATED_OFFER
        defaultLoanCatagoryShouldNotBeFound("offer.in=" + UPDATED_OFFER);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByOfferIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where offer is not null
        defaultLoanCatagoryShouldBeFound("offer.specified=true");

        // Get all the loanCatagoryList where offer is null
        defaultLoanCatagoryShouldNotBeFound("offer.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByOfferContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where offer contains DEFAULT_OFFER
        defaultLoanCatagoryShouldBeFound("offer.contains=" + DEFAULT_OFFER);

        // Get all the loanCatagoryList where offer contains UPDATED_OFFER
        defaultLoanCatagoryShouldNotBeFound("offer.contains=" + UPDATED_OFFER);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByOfferNotContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where offer does not contain DEFAULT_OFFER
        defaultLoanCatagoryShouldNotBeFound("offer.doesNotContain=" + DEFAULT_OFFER);

        // Get all the loanCatagoryList where offer does not contain UPDATED_OFFER
        defaultLoanCatagoryShouldBeFound("offer.doesNotContain=" + UPDATED_OFFER);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultLoanCatagoryShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the loanCatagoryList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanCatagoryShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultLoanCatagoryShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the loanCatagoryList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultLoanCatagoryShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where lastModified is not null
        defaultLoanCatagoryShouldBeFound("lastModified.specified=true");

        // Get all the loanCatagoryList where lastModified is null
        defaultLoanCatagoryShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultLoanCatagoryShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanCatagoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanCatagoryShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultLoanCatagoryShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the loanCatagoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultLoanCatagoryShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where lastModifiedBy is not null
        defaultLoanCatagoryShouldBeFound("lastModifiedBy.specified=true");

        // Get all the loanCatagoryList where lastModifiedBy is null
        defaultLoanCatagoryShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultLoanCatagoryShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanCatagoryList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultLoanCatagoryShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultLoanCatagoryShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the loanCatagoryList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultLoanCatagoryShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where createdBy equals to DEFAULT_CREATED_BY
        defaultLoanCatagoryShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the loanCatagoryList where createdBy equals to UPDATED_CREATED_BY
        defaultLoanCatagoryShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultLoanCatagoryShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the loanCatagoryList where createdBy equals to UPDATED_CREATED_BY
        defaultLoanCatagoryShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where createdBy is not null
        defaultLoanCatagoryShouldBeFound("createdBy.specified=true");

        // Get all the loanCatagoryList where createdBy is null
        defaultLoanCatagoryShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where createdBy contains DEFAULT_CREATED_BY
        defaultLoanCatagoryShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the loanCatagoryList where createdBy contains UPDATED_CREATED_BY
        defaultLoanCatagoryShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where createdBy does not contain DEFAULT_CREATED_BY
        defaultLoanCatagoryShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the loanCatagoryList where createdBy does not contain UPDATED_CREATED_BY
        defaultLoanCatagoryShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where createdOn equals to DEFAULT_CREATED_ON
        defaultLoanCatagoryShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the loanCatagoryList where createdOn equals to UPDATED_CREATED_ON
        defaultLoanCatagoryShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultLoanCatagoryShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the loanCatagoryList where createdOn equals to UPDATED_CREATED_ON
        defaultLoanCatagoryShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where createdOn is not null
        defaultLoanCatagoryShouldBeFound("createdOn.specified=true");

        // Get all the loanCatagoryList where createdOn is null
        defaultLoanCatagoryShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where isDeleted equals to DEFAULT_IS_DELETED
        defaultLoanCatagoryShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the loanCatagoryList where isDeleted equals to UPDATED_IS_DELETED
        defaultLoanCatagoryShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultLoanCatagoryShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the loanCatagoryList where isDeleted equals to UPDATED_IS_DELETED
        defaultLoanCatagoryShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where isDeleted is not null
        defaultLoanCatagoryShouldBeFound("isDeleted.specified=true");

        // Get all the loanCatagoryList where isDeleted is null
        defaultLoanCatagoryShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultLoanCatagoryShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanCatagoryList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanCatagoryShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultLoanCatagoryShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the loanCatagoryList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultLoanCatagoryShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField1 is not null
        defaultLoanCatagoryShouldBeFound("freeField1.specified=true");

        // Get all the loanCatagoryList where freeField1 is null
        defaultLoanCatagoryShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultLoanCatagoryShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanCatagoryList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultLoanCatagoryShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultLoanCatagoryShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the loanCatagoryList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultLoanCatagoryShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultLoanCatagoryShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanCatagoryList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanCatagoryShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultLoanCatagoryShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the loanCatagoryList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultLoanCatagoryShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField2 is not null
        defaultLoanCatagoryShouldBeFound("freeField2.specified=true");

        // Get all the loanCatagoryList where freeField2 is null
        defaultLoanCatagoryShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultLoanCatagoryShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanCatagoryList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultLoanCatagoryShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultLoanCatagoryShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the loanCatagoryList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultLoanCatagoryShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultLoanCatagoryShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanCatagoryList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanCatagoryShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultLoanCatagoryShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the loanCatagoryList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultLoanCatagoryShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField3 is not null
        defaultLoanCatagoryShouldBeFound("freeField3.specified=true");

        // Get all the loanCatagoryList where freeField3 is null
        defaultLoanCatagoryShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultLoanCatagoryShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanCatagoryList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultLoanCatagoryShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultLoanCatagoryShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the loanCatagoryList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultLoanCatagoryShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultLoanCatagoryShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the loanCatagoryList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultLoanCatagoryShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultLoanCatagoryShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the loanCatagoryList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultLoanCatagoryShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField4 is not null
        defaultLoanCatagoryShouldBeFound("freeField4.specified=true");

        // Get all the loanCatagoryList where freeField4 is null
        defaultLoanCatagoryShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultLoanCatagoryShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the loanCatagoryList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultLoanCatagoryShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllLoanCatagoriesByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        // Get all the loanCatagoryList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultLoanCatagoryShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the loanCatagoryList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultLoanCatagoryShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLoanCatagoryShouldBeFound(String filter) throws Exception {
        restLoanCatagoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(loanCatagory.getId().intValue())))
            .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].decription").value(hasItem(DEFAULT_DECRIPTION)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].offer").value(hasItem(DEFAULT_OFFER)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));

        // Check, that the count call also returns 1
        restLoanCatagoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLoanCatagoryShouldNotBeFound(String filter) throws Exception {
        restLoanCatagoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLoanCatagoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingLoanCatagory() throws Exception {
        // Get the loanCatagory
        restLoanCatagoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLoanCatagory() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        int databaseSizeBeforeUpdate = loanCatagoryRepository.findAll().size();

        // Update the loanCatagory
        LoanCatagory updatedLoanCatagory = loanCatagoryRepository.findById(loanCatagory.getId()).get();
        // Disconnect from session so that the updates on updatedLoanCatagory are not directly saved in db
        em.detach(updatedLoanCatagory);
        updatedLoanCatagory
            .productName(UPDATED_PRODUCT_NAME)
            .decription(UPDATED_DECRIPTION)
            .value(UPDATED_VALUE)
            .code(UPDATED_CODE)
            .offer(UPDATED_OFFER)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        LoanCatagoryDTO loanCatagoryDTO = loanCatagoryMapper.toDto(updatedLoanCatagory);

        restLoanCatagoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanCatagoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanCatagoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the LoanCatagory in the database
        List<LoanCatagory> loanCatagoryList = loanCatagoryRepository.findAll();
        assertThat(loanCatagoryList).hasSize(databaseSizeBeforeUpdate);
        LoanCatagory testLoanCatagory = loanCatagoryList.get(loanCatagoryList.size() - 1);
        assertThat(testLoanCatagory.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testLoanCatagory.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testLoanCatagory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testLoanCatagory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLoanCatagory.getOffer()).isEqualTo(UPDATED_OFFER);
        assertThat(testLoanCatagory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanCatagory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanCatagory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLoanCatagory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLoanCatagory.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testLoanCatagory.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanCatagory.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanCatagory.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testLoanCatagory.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void putNonExistingLoanCatagory() throws Exception {
        int databaseSizeBeforeUpdate = loanCatagoryRepository.findAll().size();
        loanCatagory.setId(count.incrementAndGet());

        // Create the LoanCatagory
        LoanCatagoryDTO loanCatagoryDTO = loanCatagoryMapper.toDto(loanCatagory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanCatagoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, loanCatagoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanCatagoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanCatagory in the database
        List<LoanCatagory> loanCatagoryList = loanCatagoryRepository.findAll();
        assertThat(loanCatagoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLoanCatagory() throws Exception {
        int databaseSizeBeforeUpdate = loanCatagoryRepository.findAll().size();
        loanCatagory.setId(count.incrementAndGet());

        // Create the LoanCatagory
        LoanCatagoryDTO loanCatagoryDTO = loanCatagoryMapper.toDto(loanCatagory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanCatagoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(loanCatagoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanCatagory in the database
        List<LoanCatagory> loanCatagoryList = loanCatagoryRepository.findAll();
        assertThat(loanCatagoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLoanCatagory() throws Exception {
        int databaseSizeBeforeUpdate = loanCatagoryRepository.findAll().size();
        loanCatagory.setId(count.incrementAndGet());

        // Create the LoanCatagory
        LoanCatagoryDTO loanCatagoryDTO = loanCatagoryMapper.toDto(loanCatagory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanCatagoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(loanCatagoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanCatagory in the database
        List<LoanCatagory> loanCatagoryList = loanCatagoryRepository.findAll();
        assertThat(loanCatagoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLoanCatagoryWithPatch() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        int databaseSizeBeforeUpdate = loanCatagoryRepository.findAll().size();

        // Update the loanCatagory using partial update
        LoanCatagory partialUpdatedLoanCatagory = new LoanCatagory();
        partialUpdatedLoanCatagory.setId(loanCatagory.getId());

        partialUpdatedLoanCatagory
            .productName(UPDATED_PRODUCT_NAME)
            .decription(UPDATED_DECRIPTION)
            .value(UPDATED_VALUE)
            .code(UPDATED_CODE)
            .offer(UPDATED_OFFER)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restLoanCatagoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanCatagory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanCatagory))
            )
            .andExpect(status().isOk());

        // Validate the LoanCatagory in the database
        List<LoanCatagory> loanCatagoryList = loanCatagoryRepository.findAll();
        assertThat(loanCatagoryList).hasSize(databaseSizeBeforeUpdate);
        LoanCatagory testLoanCatagory = loanCatagoryList.get(loanCatagoryList.size() - 1);
        assertThat(testLoanCatagory.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testLoanCatagory.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testLoanCatagory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testLoanCatagory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLoanCatagory.getOffer()).isEqualTo(UPDATED_OFFER);
        assertThat(testLoanCatagory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanCatagory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanCatagory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testLoanCatagory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLoanCatagory.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testLoanCatagory.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testLoanCatagory.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testLoanCatagory.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testLoanCatagory.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void fullUpdateLoanCatagoryWithPatch() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        int databaseSizeBeforeUpdate = loanCatagoryRepository.findAll().size();

        // Update the loanCatagory using partial update
        LoanCatagory partialUpdatedLoanCatagory = new LoanCatagory();
        partialUpdatedLoanCatagory.setId(loanCatagory.getId());

        partialUpdatedLoanCatagory
            .productName(UPDATED_PRODUCT_NAME)
            .decription(UPDATED_DECRIPTION)
            .value(UPDATED_VALUE)
            .code(UPDATED_CODE)
            .offer(UPDATED_OFFER)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restLoanCatagoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLoanCatagory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLoanCatagory))
            )
            .andExpect(status().isOk());

        // Validate the LoanCatagory in the database
        List<LoanCatagory> loanCatagoryList = loanCatagoryRepository.findAll();
        assertThat(loanCatagoryList).hasSize(databaseSizeBeforeUpdate);
        LoanCatagory testLoanCatagory = loanCatagoryList.get(loanCatagoryList.size() - 1);
        assertThat(testLoanCatagory.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testLoanCatagory.getDecription()).isEqualTo(UPDATED_DECRIPTION);
        assertThat(testLoanCatagory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testLoanCatagory.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testLoanCatagory.getOffer()).isEqualTo(UPDATED_OFFER);
        assertThat(testLoanCatagory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testLoanCatagory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testLoanCatagory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testLoanCatagory.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testLoanCatagory.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testLoanCatagory.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testLoanCatagory.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testLoanCatagory.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testLoanCatagory.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void patchNonExistingLoanCatagory() throws Exception {
        int databaseSizeBeforeUpdate = loanCatagoryRepository.findAll().size();
        loanCatagory.setId(count.incrementAndGet());

        // Create the LoanCatagory
        LoanCatagoryDTO loanCatagoryDTO = loanCatagoryMapper.toDto(loanCatagory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLoanCatagoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, loanCatagoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanCatagoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanCatagory in the database
        List<LoanCatagory> loanCatagoryList = loanCatagoryRepository.findAll();
        assertThat(loanCatagoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLoanCatagory() throws Exception {
        int databaseSizeBeforeUpdate = loanCatagoryRepository.findAll().size();
        loanCatagory.setId(count.incrementAndGet());

        // Create the LoanCatagory
        LoanCatagoryDTO loanCatagoryDTO = loanCatagoryMapper.toDto(loanCatagory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanCatagoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanCatagoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LoanCatagory in the database
        List<LoanCatagory> loanCatagoryList = loanCatagoryRepository.findAll();
        assertThat(loanCatagoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLoanCatagory() throws Exception {
        int databaseSizeBeforeUpdate = loanCatagoryRepository.findAll().size();
        loanCatagory.setId(count.incrementAndGet());

        // Create the LoanCatagory
        LoanCatagoryDTO loanCatagoryDTO = loanCatagoryMapper.toDto(loanCatagory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLoanCatagoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(loanCatagoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LoanCatagory in the database
        List<LoanCatagory> loanCatagoryList = loanCatagoryRepository.findAll();
        assertThat(loanCatagoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLoanCatagory() throws Exception {
        // Initialize the database
        loanCatagoryRepository.saveAndFlush(loanCatagory);

        int databaseSizeBeforeDelete = loanCatagoryRepository.findAll().size();

        // Delete the loanCatagory
        restLoanCatagoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, loanCatagory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LoanCatagory> loanCatagoryList = loanCatagoryRepository.findAll();
        assertThat(loanCatagoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
