package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.AmortizationDetails;
import com.techvg.los.domain.LoanAccount;
import com.techvg.los.repository.AmortizationDetailsRepository;
import com.techvg.los.service.criteria.AmortizationDetailsCriteria;
import com.techvg.los.service.dto.AmortizationDetailsDTO;
import com.techvg.los.service.mapper.AmortizationDetailsMapper;
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
 * Integration tests for the {@link AmortizationDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AmortizationDetailsResourceIT {

    private static final Instant DEFAULT_INSTALLMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INSTALLMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT = 1D;
    private static final Double UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT = 2D;
    private static final Double SMALLER_TOTAL_OUTSTANDING_PRINCIPLE_AMT = 1D - 1D;

    private static final Double DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT = 1D;
    private static final Double UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT = 2D;
    private static final Double SMALLER_TOTAL_OUTSTANDNG_INTEREST_AMT = 1D - 1D;

    private static final Double DEFAULT_PAID_PRINCIPLE_AMT = 1D;
    private static final Double UPDATED_PAID_PRINCIPLE_AMT = 2D;
    private static final Double SMALLER_PAID_PRINCIPLE_AMT = 1D - 1D;

    private static final Double DEFAULT_PAID_INTEREST_AMT = 1D;
    private static final Double UPDATED_PAID_INTEREST_AMT = 2D;
    private static final Double SMALLER_PAID_INTEREST_AMT = 1D - 1D;

    private static final Double DEFAULT_BAL_PRINCIPLE_AMT = 1D;
    private static final Double UPDATED_BAL_PRINCIPLE_AMT = 2D;
    private static final Double SMALLER_BAL_PRINCIPLE_AMT = 1D - 1D;

    private static final Double DEFAULT_BAL_INTEREST_AMT = 1D;
    private static final Double UPDATED_BAL_INTEREST_AMT = 2D;
    private static final Double SMALLER_BAL_INTEREST_AMT = 1D - 1D;

    private static final Double DEFAULT_LOAN_EMI_AMT = 1D;
    private static final Double UPDATED_LOAN_EMI_AMT = 2D;
    private static final Double SMALLER_LOAN_EMI_AMT = 1D - 1D;

    private static final Double DEFAULT_PRINCIPLE_EMI = 1D;
    private static final Double UPDATED_PRINCIPLE_EMI = 2D;
    private static final Double SMALLER_PRINCIPLE_EMI = 1D - 1D;

    private static final Double DEFAULT_ROI = 1D;
    private static final Double UPDATED_ROI = 2D;
    private static final Double SMALLER_ROI = 1D - 1D;

    private static final String DEFAULT_PAYMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/amortization-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AmortizationDetailsRepository amortizationDetailsRepository;

    @Autowired
    private AmortizationDetailsMapper amortizationDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAmortizationDetailsMockMvc;

    private AmortizationDetails amortizationDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmortizationDetails createEntity(EntityManager em) {
        AmortizationDetails amortizationDetails = new AmortizationDetails()
            .installmentDate(DEFAULT_INSTALLMENT_DATE)
            .totalOutstandingPrincipleAmt(DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT)
            .totalOutstandngInterestAmt(DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT)
            .paidPrincipleAmt(DEFAULT_PAID_PRINCIPLE_AMT)
            .paidInterestAmt(DEFAULT_PAID_INTEREST_AMT)
            .balPrincipleAmt(DEFAULT_BAL_PRINCIPLE_AMT)
            .balInterestAmt(DEFAULT_BAL_INTEREST_AMT)
            .loanEmiAmt(DEFAULT_LOAN_EMI_AMT)
            .principleEMI(DEFAULT_PRINCIPLE_EMI)
            .roi(DEFAULT_ROI)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .year(DEFAULT_YEAR)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5)
            .freeField6(DEFAULT_FREE_FIELD_6);
        return amortizationDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmortizationDetails createUpdatedEntity(EntityManager em) {
        AmortizationDetails amortizationDetails = new AmortizationDetails()
            .installmentDate(UPDATED_INSTALLMENT_DATE)
            .totalOutstandingPrincipleAmt(UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT)
            .totalOutstandngInterestAmt(UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT)
            .paidPrincipleAmt(UPDATED_PAID_PRINCIPLE_AMT)
            .paidInterestAmt(UPDATED_PAID_INTEREST_AMT)
            .balPrincipleAmt(UPDATED_BAL_PRINCIPLE_AMT)
            .balInterestAmt(UPDATED_BAL_INTEREST_AMT)
            .loanEmiAmt(UPDATED_LOAN_EMI_AMT)
            .principleEMI(UPDATED_PRINCIPLE_EMI)
            .roi(UPDATED_ROI)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        return amortizationDetails;
    }

    @BeforeEach
    public void initTest() {
        amortizationDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createAmortizationDetails() throws Exception {
        int databaseSizeBeforeCreate = amortizationDetailsRepository.findAll().size();
        // Create the AmortizationDetails
        AmortizationDetailsDTO amortizationDetailsDTO = amortizationDetailsMapper.toDto(amortizationDetails);
        restAmortizationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(amortizationDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AmortizationDetails in the database
        List<AmortizationDetails> amortizationDetailsList = amortizationDetailsRepository.findAll();
        assertThat(amortizationDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        AmortizationDetails testAmortizationDetails = amortizationDetailsList.get(amortizationDetailsList.size() - 1);
        assertThat(testAmortizationDetails.getInstallmentDate()).isEqualTo(DEFAULT_INSTALLMENT_DATE);
        assertThat(testAmortizationDetails.getTotalOutstandingPrincipleAmt()).isEqualTo(DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT);
        assertThat(testAmortizationDetails.getTotalOutstandngInterestAmt()).isEqualTo(DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT);
        assertThat(testAmortizationDetails.getPaidPrincipleAmt()).isEqualTo(DEFAULT_PAID_PRINCIPLE_AMT);
        assertThat(testAmortizationDetails.getPaidInterestAmt()).isEqualTo(DEFAULT_PAID_INTEREST_AMT);
        assertThat(testAmortizationDetails.getBalPrincipleAmt()).isEqualTo(DEFAULT_BAL_PRINCIPLE_AMT);
        assertThat(testAmortizationDetails.getBalInterestAmt()).isEqualTo(DEFAULT_BAL_INTEREST_AMT);
        assertThat(testAmortizationDetails.getLoanEmiAmt()).isEqualTo(DEFAULT_LOAN_EMI_AMT);
        assertThat(testAmortizationDetails.getPrincipleEMI()).isEqualTo(DEFAULT_PRINCIPLE_EMI);
        assertThat(testAmortizationDetails.getRoi()).isEqualTo(DEFAULT_ROI);
        assertThat(testAmortizationDetails.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testAmortizationDetails.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testAmortizationDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAmortizationDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testAmortizationDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testAmortizationDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testAmortizationDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testAmortizationDetails.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testAmortizationDetails.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testAmortizationDetails.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createAmortizationDetailsWithExistingId() throws Exception {
        // Create the AmortizationDetails with an existing ID
        amortizationDetails.setId(1L);
        AmortizationDetailsDTO amortizationDetailsDTO = amortizationDetailsMapper.toDto(amortizationDetails);

        int databaseSizeBeforeCreate = amortizationDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmortizationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(amortizationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AmortizationDetails in the database
        List<AmortizationDetails> amortizationDetailsList = amortizationDetailsRepository.findAll();
        assertThat(amortizationDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAmortizationDetails() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList
        restAmortizationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].installmentDate").value(hasItem(DEFAULT_INSTALLMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalOutstandingPrincipleAmt").value(hasItem(DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalOutstandngInterestAmt").value(hasItem(DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].paidPrincipleAmt").value(hasItem(DEFAULT_PAID_PRINCIPLE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].paidInterestAmt").value(hasItem(DEFAULT_PAID_INTEREST_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].balPrincipleAmt").value(hasItem(DEFAULT_BAL_PRINCIPLE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].balInterestAmt").value(hasItem(DEFAULT_BAL_INTEREST_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanEmiAmt").value(hasItem(DEFAULT_LOAN_EMI_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].principleEMI").value(hasItem(DEFAULT_PRINCIPLE_EMI.doubleValue())))
            .andExpect(jsonPath("$.[*].roi").value(hasItem(DEFAULT_ROI.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
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
    void getAmortizationDetails() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get the amortizationDetails
        restAmortizationDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, amortizationDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(amortizationDetails.getId().intValue()))
            .andExpect(jsonPath("$.installmentDate").value(DEFAULT_INSTALLMENT_DATE.toString()))
            .andExpect(jsonPath("$.totalOutstandingPrincipleAmt").value(DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT.doubleValue()))
            .andExpect(jsonPath("$.totalOutstandngInterestAmt").value(DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT.doubleValue()))
            .andExpect(jsonPath("$.paidPrincipleAmt").value(DEFAULT_PAID_PRINCIPLE_AMT.doubleValue()))
            .andExpect(jsonPath("$.paidInterestAmt").value(DEFAULT_PAID_INTEREST_AMT.doubleValue()))
            .andExpect(jsonPath("$.balPrincipleAmt").value(DEFAULT_BAL_PRINCIPLE_AMT.doubleValue()))
            .andExpect(jsonPath("$.balInterestAmt").value(DEFAULT_BAL_INTEREST_AMT.doubleValue()))
            .andExpect(jsonPath("$.loanEmiAmt").value(DEFAULT_LOAN_EMI_AMT.doubleValue()))
            .andExpect(jsonPath("$.principleEMI").value(DEFAULT_PRINCIPLE_EMI.doubleValue()))
            .andExpect(jsonPath("$.roi").value(DEFAULT_ROI.doubleValue()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
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
    void getAmortizationDetailsByIdFiltering() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        Long id = amortizationDetails.getId();

        defaultAmortizationDetailsShouldBeFound("id.equals=" + id);
        defaultAmortizationDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultAmortizationDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAmortizationDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultAmortizationDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAmortizationDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByInstallmentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where installmentDate equals to DEFAULT_INSTALLMENT_DATE
        defaultAmortizationDetailsShouldBeFound("installmentDate.equals=" + DEFAULT_INSTALLMENT_DATE);

        // Get all the amortizationDetailsList where installmentDate equals to UPDATED_INSTALLMENT_DATE
        defaultAmortizationDetailsShouldNotBeFound("installmentDate.equals=" + UPDATED_INSTALLMENT_DATE);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByInstallmentDateIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where installmentDate in DEFAULT_INSTALLMENT_DATE or UPDATED_INSTALLMENT_DATE
        defaultAmortizationDetailsShouldBeFound("installmentDate.in=" + DEFAULT_INSTALLMENT_DATE + "," + UPDATED_INSTALLMENT_DATE);

        // Get all the amortizationDetailsList where installmentDate equals to UPDATED_INSTALLMENT_DATE
        defaultAmortizationDetailsShouldNotBeFound("installmentDate.in=" + UPDATED_INSTALLMENT_DATE);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByInstallmentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where installmentDate is not null
        defaultAmortizationDetailsShouldBeFound("installmentDate.specified=true");

        // Get all the amortizationDetailsList where installmentDate is null
        defaultAmortizationDetailsShouldNotBeFound("installmentDate.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByTotalOutstandingPrincipleAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where totalOutstandingPrincipleAmt equals to DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("totalOutstandingPrincipleAmt.equals=" + DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where totalOutstandingPrincipleAmt equals to UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("totalOutstandingPrincipleAmt.equals=" + UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByTotalOutstandingPrincipleAmtIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where totalOutstandingPrincipleAmt in DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT or UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound(
            "totalOutstandingPrincipleAmt.in=" + DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT + "," + UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        );

        // Get all the amortizationDetailsList where totalOutstandingPrincipleAmt equals to UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("totalOutstandingPrincipleAmt.in=" + UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByTotalOutstandingPrincipleAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where totalOutstandingPrincipleAmt is not null
        defaultAmortizationDetailsShouldBeFound("totalOutstandingPrincipleAmt.specified=true");

        // Get all the amortizationDetailsList where totalOutstandingPrincipleAmt is null
        defaultAmortizationDetailsShouldNotBeFound("totalOutstandingPrincipleAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByTotalOutstandingPrincipleAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where totalOutstandingPrincipleAmt is greater than or equal to DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound(
            "totalOutstandingPrincipleAmt.greaterThanOrEqual=" + DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        );

        // Get all the amortizationDetailsList where totalOutstandingPrincipleAmt is greater than or equal to UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound(
            "totalOutstandingPrincipleAmt.greaterThanOrEqual=" + UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        );
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByTotalOutstandingPrincipleAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where totalOutstandingPrincipleAmt is less than or equal to DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("totalOutstandingPrincipleAmt.lessThanOrEqual=" + DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where totalOutstandingPrincipleAmt is less than or equal to SMALLER_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound(
            "totalOutstandingPrincipleAmt.lessThanOrEqual=" + SMALLER_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        );
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByTotalOutstandingPrincipleAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where totalOutstandingPrincipleAmt is less than DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("totalOutstandingPrincipleAmt.lessThan=" + DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where totalOutstandingPrincipleAmt is less than UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("totalOutstandingPrincipleAmt.lessThan=" + UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByTotalOutstandingPrincipleAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where totalOutstandingPrincipleAmt is greater than DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("totalOutstandingPrincipleAmt.greaterThan=" + DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where totalOutstandingPrincipleAmt is greater than SMALLER_TOTAL_OUTSTANDING_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("totalOutstandingPrincipleAmt.greaterThan=" + SMALLER_TOTAL_OUTSTANDING_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByTotalOutstandngInterestAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where totalOutstandngInterestAmt equals to DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("totalOutstandngInterestAmt.equals=" + DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT);

        // Get all the amortizationDetailsList where totalOutstandngInterestAmt equals to UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("totalOutstandngInterestAmt.equals=" + UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByTotalOutstandngInterestAmtIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where totalOutstandngInterestAmt in DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT or UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound(
            "totalOutstandngInterestAmt.in=" + DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT + "," + UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT
        );

        // Get all the amortizationDetailsList where totalOutstandngInterestAmt equals to UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("totalOutstandngInterestAmt.in=" + UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByTotalOutstandngInterestAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where totalOutstandngInterestAmt is not null
        defaultAmortizationDetailsShouldBeFound("totalOutstandngInterestAmt.specified=true");

        // Get all the amortizationDetailsList where totalOutstandngInterestAmt is null
        defaultAmortizationDetailsShouldNotBeFound("totalOutstandngInterestAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByTotalOutstandngInterestAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where totalOutstandngInterestAmt is greater than or equal to DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("totalOutstandngInterestAmt.greaterThanOrEqual=" + DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT);

        // Get all the amortizationDetailsList where totalOutstandngInterestAmt is greater than or equal to UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound(
            "totalOutstandngInterestAmt.greaterThanOrEqual=" + UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT
        );
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByTotalOutstandngInterestAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where totalOutstandngInterestAmt is less than or equal to DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("totalOutstandngInterestAmt.lessThanOrEqual=" + DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT);

        // Get all the amortizationDetailsList where totalOutstandngInterestAmt is less than or equal to SMALLER_TOTAL_OUTSTANDNG_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("totalOutstandngInterestAmt.lessThanOrEqual=" + SMALLER_TOTAL_OUTSTANDNG_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByTotalOutstandngInterestAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where totalOutstandngInterestAmt is less than DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("totalOutstandngInterestAmt.lessThan=" + DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT);

        // Get all the amortizationDetailsList where totalOutstandngInterestAmt is less than UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("totalOutstandngInterestAmt.lessThan=" + UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByTotalOutstandngInterestAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where totalOutstandngInterestAmt is greater than DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("totalOutstandngInterestAmt.greaterThan=" + DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT);

        // Get all the amortizationDetailsList where totalOutstandngInterestAmt is greater than SMALLER_TOTAL_OUTSTANDNG_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("totalOutstandngInterestAmt.greaterThan=" + SMALLER_TOTAL_OUTSTANDNG_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaidPrincipleAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paidPrincipleAmt equals to DEFAULT_PAID_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("paidPrincipleAmt.equals=" + DEFAULT_PAID_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where paidPrincipleAmt equals to UPDATED_PAID_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("paidPrincipleAmt.equals=" + UPDATED_PAID_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaidPrincipleAmtIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paidPrincipleAmt in DEFAULT_PAID_PRINCIPLE_AMT or UPDATED_PAID_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("paidPrincipleAmt.in=" + DEFAULT_PAID_PRINCIPLE_AMT + "," + UPDATED_PAID_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where paidPrincipleAmt equals to UPDATED_PAID_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("paidPrincipleAmt.in=" + UPDATED_PAID_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaidPrincipleAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paidPrincipleAmt is not null
        defaultAmortizationDetailsShouldBeFound("paidPrincipleAmt.specified=true");

        // Get all the amortizationDetailsList where paidPrincipleAmt is null
        defaultAmortizationDetailsShouldNotBeFound("paidPrincipleAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaidPrincipleAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paidPrincipleAmt is greater than or equal to DEFAULT_PAID_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("paidPrincipleAmt.greaterThanOrEqual=" + DEFAULT_PAID_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where paidPrincipleAmt is greater than or equal to UPDATED_PAID_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("paidPrincipleAmt.greaterThanOrEqual=" + UPDATED_PAID_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaidPrincipleAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paidPrincipleAmt is less than or equal to DEFAULT_PAID_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("paidPrincipleAmt.lessThanOrEqual=" + DEFAULT_PAID_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where paidPrincipleAmt is less than or equal to SMALLER_PAID_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("paidPrincipleAmt.lessThanOrEqual=" + SMALLER_PAID_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaidPrincipleAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paidPrincipleAmt is less than DEFAULT_PAID_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("paidPrincipleAmt.lessThan=" + DEFAULT_PAID_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where paidPrincipleAmt is less than UPDATED_PAID_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("paidPrincipleAmt.lessThan=" + UPDATED_PAID_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaidPrincipleAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paidPrincipleAmt is greater than DEFAULT_PAID_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("paidPrincipleAmt.greaterThan=" + DEFAULT_PAID_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where paidPrincipleAmt is greater than SMALLER_PAID_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("paidPrincipleAmt.greaterThan=" + SMALLER_PAID_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaidInterestAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paidInterestAmt equals to DEFAULT_PAID_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("paidInterestAmt.equals=" + DEFAULT_PAID_INTEREST_AMT);

        // Get all the amortizationDetailsList where paidInterestAmt equals to UPDATED_PAID_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("paidInterestAmt.equals=" + UPDATED_PAID_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaidInterestAmtIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paidInterestAmt in DEFAULT_PAID_INTEREST_AMT or UPDATED_PAID_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("paidInterestAmt.in=" + DEFAULT_PAID_INTEREST_AMT + "," + UPDATED_PAID_INTEREST_AMT);

        // Get all the amortizationDetailsList where paidInterestAmt equals to UPDATED_PAID_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("paidInterestAmt.in=" + UPDATED_PAID_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaidInterestAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paidInterestAmt is not null
        defaultAmortizationDetailsShouldBeFound("paidInterestAmt.specified=true");

        // Get all the amortizationDetailsList where paidInterestAmt is null
        defaultAmortizationDetailsShouldNotBeFound("paidInterestAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaidInterestAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paidInterestAmt is greater than or equal to DEFAULT_PAID_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("paidInterestAmt.greaterThanOrEqual=" + DEFAULT_PAID_INTEREST_AMT);

        // Get all the amortizationDetailsList where paidInterestAmt is greater than or equal to UPDATED_PAID_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("paidInterestAmt.greaterThanOrEqual=" + UPDATED_PAID_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaidInterestAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paidInterestAmt is less than or equal to DEFAULT_PAID_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("paidInterestAmt.lessThanOrEqual=" + DEFAULT_PAID_INTEREST_AMT);

        // Get all the amortizationDetailsList where paidInterestAmt is less than or equal to SMALLER_PAID_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("paidInterestAmt.lessThanOrEqual=" + SMALLER_PAID_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaidInterestAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paidInterestAmt is less than DEFAULT_PAID_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("paidInterestAmt.lessThan=" + DEFAULT_PAID_INTEREST_AMT);

        // Get all the amortizationDetailsList where paidInterestAmt is less than UPDATED_PAID_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("paidInterestAmt.lessThan=" + UPDATED_PAID_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaidInterestAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paidInterestAmt is greater than DEFAULT_PAID_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("paidInterestAmt.greaterThan=" + DEFAULT_PAID_INTEREST_AMT);

        // Get all the amortizationDetailsList where paidInterestAmt is greater than SMALLER_PAID_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("paidInterestAmt.greaterThan=" + SMALLER_PAID_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByBalPrincipleAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where balPrincipleAmt equals to DEFAULT_BAL_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("balPrincipleAmt.equals=" + DEFAULT_BAL_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where balPrincipleAmt equals to UPDATED_BAL_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("balPrincipleAmt.equals=" + UPDATED_BAL_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByBalPrincipleAmtIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where balPrincipleAmt in DEFAULT_BAL_PRINCIPLE_AMT or UPDATED_BAL_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("balPrincipleAmt.in=" + DEFAULT_BAL_PRINCIPLE_AMT + "," + UPDATED_BAL_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where balPrincipleAmt equals to UPDATED_BAL_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("balPrincipleAmt.in=" + UPDATED_BAL_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByBalPrincipleAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where balPrincipleAmt is not null
        defaultAmortizationDetailsShouldBeFound("balPrincipleAmt.specified=true");

        // Get all the amortizationDetailsList where balPrincipleAmt is null
        defaultAmortizationDetailsShouldNotBeFound("balPrincipleAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByBalPrincipleAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where balPrincipleAmt is greater than or equal to DEFAULT_BAL_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("balPrincipleAmt.greaterThanOrEqual=" + DEFAULT_BAL_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where balPrincipleAmt is greater than or equal to UPDATED_BAL_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("balPrincipleAmt.greaterThanOrEqual=" + UPDATED_BAL_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByBalPrincipleAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where balPrincipleAmt is less than or equal to DEFAULT_BAL_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("balPrincipleAmt.lessThanOrEqual=" + DEFAULT_BAL_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where balPrincipleAmt is less than or equal to SMALLER_BAL_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("balPrincipleAmt.lessThanOrEqual=" + SMALLER_BAL_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByBalPrincipleAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where balPrincipleAmt is less than DEFAULT_BAL_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("balPrincipleAmt.lessThan=" + DEFAULT_BAL_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where balPrincipleAmt is less than UPDATED_BAL_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("balPrincipleAmt.lessThan=" + UPDATED_BAL_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByBalPrincipleAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where balPrincipleAmt is greater than DEFAULT_BAL_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldNotBeFound("balPrincipleAmt.greaterThan=" + DEFAULT_BAL_PRINCIPLE_AMT);

        // Get all the amortizationDetailsList where balPrincipleAmt is greater than SMALLER_BAL_PRINCIPLE_AMT
        defaultAmortizationDetailsShouldBeFound("balPrincipleAmt.greaterThan=" + SMALLER_BAL_PRINCIPLE_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByBalInterestAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where balInterestAmt equals to DEFAULT_BAL_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("balInterestAmt.equals=" + DEFAULT_BAL_INTEREST_AMT);

        // Get all the amortizationDetailsList where balInterestAmt equals to UPDATED_BAL_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("balInterestAmt.equals=" + UPDATED_BAL_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByBalInterestAmtIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where balInterestAmt in DEFAULT_BAL_INTEREST_AMT or UPDATED_BAL_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("balInterestAmt.in=" + DEFAULT_BAL_INTEREST_AMT + "," + UPDATED_BAL_INTEREST_AMT);

        // Get all the amortizationDetailsList where balInterestAmt equals to UPDATED_BAL_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("balInterestAmt.in=" + UPDATED_BAL_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByBalInterestAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where balInterestAmt is not null
        defaultAmortizationDetailsShouldBeFound("balInterestAmt.specified=true");

        // Get all the amortizationDetailsList where balInterestAmt is null
        defaultAmortizationDetailsShouldNotBeFound("balInterestAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByBalInterestAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where balInterestAmt is greater than or equal to DEFAULT_BAL_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("balInterestAmt.greaterThanOrEqual=" + DEFAULT_BAL_INTEREST_AMT);

        // Get all the amortizationDetailsList where balInterestAmt is greater than or equal to UPDATED_BAL_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("balInterestAmt.greaterThanOrEqual=" + UPDATED_BAL_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByBalInterestAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where balInterestAmt is less than or equal to DEFAULT_BAL_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("balInterestAmt.lessThanOrEqual=" + DEFAULT_BAL_INTEREST_AMT);

        // Get all the amortizationDetailsList where balInterestAmt is less than or equal to SMALLER_BAL_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("balInterestAmt.lessThanOrEqual=" + SMALLER_BAL_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByBalInterestAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where balInterestAmt is less than DEFAULT_BAL_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("balInterestAmt.lessThan=" + DEFAULT_BAL_INTEREST_AMT);

        // Get all the amortizationDetailsList where balInterestAmt is less than UPDATED_BAL_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("balInterestAmt.lessThan=" + UPDATED_BAL_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByBalInterestAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where balInterestAmt is greater than DEFAULT_BAL_INTEREST_AMT
        defaultAmortizationDetailsShouldNotBeFound("balInterestAmt.greaterThan=" + DEFAULT_BAL_INTEREST_AMT);

        // Get all the amortizationDetailsList where balInterestAmt is greater than SMALLER_BAL_INTEREST_AMT
        defaultAmortizationDetailsShouldBeFound("balInterestAmt.greaterThan=" + SMALLER_BAL_INTEREST_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLoanEmiAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where loanEmiAmt equals to DEFAULT_LOAN_EMI_AMT
        defaultAmortizationDetailsShouldBeFound("loanEmiAmt.equals=" + DEFAULT_LOAN_EMI_AMT);

        // Get all the amortizationDetailsList where loanEmiAmt equals to UPDATED_LOAN_EMI_AMT
        defaultAmortizationDetailsShouldNotBeFound("loanEmiAmt.equals=" + UPDATED_LOAN_EMI_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLoanEmiAmtIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where loanEmiAmt in DEFAULT_LOAN_EMI_AMT or UPDATED_LOAN_EMI_AMT
        defaultAmortizationDetailsShouldBeFound("loanEmiAmt.in=" + DEFAULT_LOAN_EMI_AMT + "," + UPDATED_LOAN_EMI_AMT);

        // Get all the amortizationDetailsList where loanEmiAmt equals to UPDATED_LOAN_EMI_AMT
        defaultAmortizationDetailsShouldNotBeFound("loanEmiAmt.in=" + UPDATED_LOAN_EMI_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLoanEmiAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where loanEmiAmt is not null
        defaultAmortizationDetailsShouldBeFound("loanEmiAmt.specified=true");

        // Get all the amortizationDetailsList where loanEmiAmt is null
        defaultAmortizationDetailsShouldNotBeFound("loanEmiAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLoanEmiAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where loanEmiAmt is greater than or equal to DEFAULT_LOAN_EMI_AMT
        defaultAmortizationDetailsShouldBeFound("loanEmiAmt.greaterThanOrEqual=" + DEFAULT_LOAN_EMI_AMT);

        // Get all the amortizationDetailsList where loanEmiAmt is greater than or equal to UPDATED_LOAN_EMI_AMT
        defaultAmortizationDetailsShouldNotBeFound("loanEmiAmt.greaterThanOrEqual=" + UPDATED_LOAN_EMI_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLoanEmiAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where loanEmiAmt is less than or equal to DEFAULT_LOAN_EMI_AMT
        defaultAmortizationDetailsShouldBeFound("loanEmiAmt.lessThanOrEqual=" + DEFAULT_LOAN_EMI_AMT);

        // Get all the amortizationDetailsList where loanEmiAmt is less than or equal to SMALLER_LOAN_EMI_AMT
        defaultAmortizationDetailsShouldNotBeFound("loanEmiAmt.lessThanOrEqual=" + SMALLER_LOAN_EMI_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLoanEmiAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where loanEmiAmt is less than DEFAULT_LOAN_EMI_AMT
        defaultAmortizationDetailsShouldNotBeFound("loanEmiAmt.lessThan=" + DEFAULT_LOAN_EMI_AMT);

        // Get all the amortizationDetailsList where loanEmiAmt is less than UPDATED_LOAN_EMI_AMT
        defaultAmortizationDetailsShouldBeFound("loanEmiAmt.lessThan=" + UPDATED_LOAN_EMI_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLoanEmiAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where loanEmiAmt is greater than DEFAULT_LOAN_EMI_AMT
        defaultAmortizationDetailsShouldNotBeFound("loanEmiAmt.greaterThan=" + DEFAULT_LOAN_EMI_AMT);

        // Get all the amortizationDetailsList where loanEmiAmt is greater than SMALLER_LOAN_EMI_AMT
        defaultAmortizationDetailsShouldBeFound("loanEmiAmt.greaterThan=" + SMALLER_LOAN_EMI_AMT);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPrincipleEMIIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where principleEMI equals to DEFAULT_PRINCIPLE_EMI
        defaultAmortizationDetailsShouldBeFound("principleEMI.equals=" + DEFAULT_PRINCIPLE_EMI);

        // Get all the amortizationDetailsList where principleEMI equals to UPDATED_PRINCIPLE_EMI
        defaultAmortizationDetailsShouldNotBeFound("principleEMI.equals=" + UPDATED_PRINCIPLE_EMI);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPrincipleEMIIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where principleEMI in DEFAULT_PRINCIPLE_EMI or UPDATED_PRINCIPLE_EMI
        defaultAmortizationDetailsShouldBeFound("principleEMI.in=" + DEFAULT_PRINCIPLE_EMI + "," + UPDATED_PRINCIPLE_EMI);

        // Get all the amortizationDetailsList where principleEMI equals to UPDATED_PRINCIPLE_EMI
        defaultAmortizationDetailsShouldNotBeFound("principleEMI.in=" + UPDATED_PRINCIPLE_EMI);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPrincipleEMIIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where principleEMI is not null
        defaultAmortizationDetailsShouldBeFound("principleEMI.specified=true");

        // Get all the amortizationDetailsList where principleEMI is null
        defaultAmortizationDetailsShouldNotBeFound("principleEMI.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPrincipleEMIIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where principleEMI is greater than or equal to DEFAULT_PRINCIPLE_EMI
        defaultAmortizationDetailsShouldBeFound("principleEMI.greaterThanOrEqual=" + DEFAULT_PRINCIPLE_EMI);

        // Get all the amortizationDetailsList where principleEMI is greater than or equal to UPDATED_PRINCIPLE_EMI
        defaultAmortizationDetailsShouldNotBeFound("principleEMI.greaterThanOrEqual=" + UPDATED_PRINCIPLE_EMI);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPrincipleEMIIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where principleEMI is less than or equal to DEFAULT_PRINCIPLE_EMI
        defaultAmortizationDetailsShouldBeFound("principleEMI.lessThanOrEqual=" + DEFAULT_PRINCIPLE_EMI);

        // Get all the amortizationDetailsList where principleEMI is less than or equal to SMALLER_PRINCIPLE_EMI
        defaultAmortizationDetailsShouldNotBeFound("principleEMI.lessThanOrEqual=" + SMALLER_PRINCIPLE_EMI);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPrincipleEMIIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where principleEMI is less than DEFAULT_PRINCIPLE_EMI
        defaultAmortizationDetailsShouldNotBeFound("principleEMI.lessThan=" + DEFAULT_PRINCIPLE_EMI);

        // Get all the amortizationDetailsList where principleEMI is less than UPDATED_PRINCIPLE_EMI
        defaultAmortizationDetailsShouldBeFound("principleEMI.lessThan=" + UPDATED_PRINCIPLE_EMI);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPrincipleEMIIsGreaterThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where principleEMI is greater than DEFAULT_PRINCIPLE_EMI
        defaultAmortizationDetailsShouldNotBeFound("principleEMI.greaterThan=" + DEFAULT_PRINCIPLE_EMI);

        // Get all the amortizationDetailsList where principleEMI is greater than SMALLER_PRINCIPLE_EMI
        defaultAmortizationDetailsShouldBeFound("principleEMI.greaterThan=" + SMALLER_PRINCIPLE_EMI);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByRoiIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where roi equals to DEFAULT_ROI
        defaultAmortizationDetailsShouldBeFound("roi.equals=" + DEFAULT_ROI);

        // Get all the amortizationDetailsList where roi equals to UPDATED_ROI
        defaultAmortizationDetailsShouldNotBeFound("roi.equals=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByRoiIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where roi in DEFAULT_ROI or UPDATED_ROI
        defaultAmortizationDetailsShouldBeFound("roi.in=" + DEFAULT_ROI + "," + UPDATED_ROI);

        // Get all the amortizationDetailsList where roi equals to UPDATED_ROI
        defaultAmortizationDetailsShouldNotBeFound("roi.in=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByRoiIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where roi is not null
        defaultAmortizationDetailsShouldBeFound("roi.specified=true");

        // Get all the amortizationDetailsList where roi is null
        defaultAmortizationDetailsShouldNotBeFound("roi.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByRoiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where roi is greater than or equal to DEFAULT_ROI
        defaultAmortizationDetailsShouldBeFound("roi.greaterThanOrEqual=" + DEFAULT_ROI);

        // Get all the amortizationDetailsList where roi is greater than or equal to UPDATED_ROI
        defaultAmortizationDetailsShouldNotBeFound("roi.greaterThanOrEqual=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByRoiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where roi is less than or equal to DEFAULT_ROI
        defaultAmortizationDetailsShouldBeFound("roi.lessThanOrEqual=" + DEFAULT_ROI);

        // Get all the amortizationDetailsList where roi is less than or equal to SMALLER_ROI
        defaultAmortizationDetailsShouldNotBeFound("roi.lessThanOrEqual=" + SMALLER_ROI);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByRoiIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where roi is less than DEFAULT_ROI
        defaultAmortizationDetailsShouldNotBeFound("roi.lessThan=" + DEFAULT_ROI);

        // Get all the amortizationDetailsList where roi is less than UPDATED_ROI
        defaultAmortizationDetailsShouldBeFound("roi.lessThan=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByRoiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where roi is greater than DEFAULT_ROI
        defaultAmortizationDetailsShouldNotBeFound("roi.greaterThan=" + DEFAULT_ROI);

        // Get all the amortizationDetailsList where roi is greater than SMALLER_ROI
        defaultAmortizationDetailsShouldBeFound("roi.greaterThan=" + SMALLER_ROI);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaymentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paymentStatus equals to DEFAULT_PAYMENT_STATUS
        defaultAmortizationDetailsShouldBeFound("paymentStatus.equals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the amortizationDetailsList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultAmortizationDetailsShouldNotBeFound("paymentStatus.equals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaymentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paymentStatus in DEFAULT_PAYMENT_STATUS or UPDATED_PAYMENT_STATUS
        defaultAmortizationDetailsShouldBeFound("paymentStatus.in=" + DEFAULT_PAYMENT_STATUS + "," + UPDATED_PAYMENT_STATUS);

        // Get all the amortizationDetailsList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultAmortizationDetailsShouldNotBeFound("paymentStatus.in=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaymentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paymentStatus is not null
        defaultAmortizationDetailsShouldBeFound("paymentStatus.specified=true");

        // Get all the amortizationDetailsList where paymentStatus is null
        defaultAmortizationDetailsShouldNotBeFound("paymentStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaymentStatusContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paymentStatus contains DEFAULT_PAYMENT_STATUS
        defaultAmortizationDetailsShouldBeFound("paymentStatus.contains=" + DEFAULT_PAYMENT_STATUS);

        // Get all the amortizationDetailsList where paymentStatus contains UPDATED_PAYMENT_STATUS
        defaultAmortizationDetailsShouldNotBeFound("paymentStatus.contains=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByPaymentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where paymentStatus does not contain DEFAULT_PAYMENT_STATUS
        defaultAmortizationDetailsShouldNotBeFound("paymentStatus.doesNotContain=" + DEFAULT_PAYMENT_STATUS);

        // Get all the amortizationDetailsList where paymentStatus does not contain UPDATED_PAYMENT_STATUS
        defaultAmortizationDetailsShouldBeFound("paymentStatus.doesNotContain=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where year equals to DEFAULT_YEAR
        defaultAmortizationDetailsShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the amortizationDetailsList where year equals to UPDATED_YEAR
        defaultAmortizationDetailsShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultAmortizationDetailsShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the amortizationDetailsList where year equals to UPDATED_YEAR
        defaultAmortizationDetailsShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where year is not null
        defaultAmortizationDetailsShouldBeFound("year.specified=true");

        // Get all the amortizationDetailsList where year is null
        defaultAmortizationDetailsShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByYearContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where year contains DEFAULT_YEAR
        defaultAmortizationDetailsShouldBeFound("year.contains=" + DEFAULT_YEAR);

        // Get all the amortizationDetailsList where year contains UPDATED_YEAR
        defaultAmortizationDetailsShouldNotBeFound("year.contains=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByYearNotContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where year does not contain DEFAULT_YEAR
        defaultAmortizationDetailsShouldNotBeFound("year.doesNotContain=" + DEFAULT_YEAR);

        // Get all the amortizationDetailsList where year does not contain UPDATED_YEAR
        defaultAmortizationDetailsShouldBeFound("year.doesNotContain=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAmortizationDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the amortizationDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAmortizationDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAmortizationDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the amortizationDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAmortizationDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where lastModified is not null
        defaultAmortizationDetailsShouldBeFound("lastModified.specified=true");

        // Get all the amortizationDetailsList where lastModified is null
        defaultAmortizationDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAmortizationDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the amortizationDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAmortizationDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAmortizationDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the amortizationDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAmortizationDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where lastModifiedBy is not null
        defaultAmortizationDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the amortizationDetailsList where lastModifiedBy is null
        defaultAmortizationDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAmortizationDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the amortizationDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAmortizationDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAmortizationDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the amortizationDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAmortizationDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultAmortizationDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the amortizationDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultAmortizationDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultAmortizationDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the amortizationDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultAmortizationDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField1 is not null
        defaultAmortizationDetailsShouldBeFound("freeField1.specified=true");

        // Get all the amortizationDetailsList where freeField1 is null
        defaultAmortizationDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultAmortizationDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the amortizationDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultAmortizationDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultAmortizationDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the amortizationDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultAmortizationDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultAmortizationDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the amortizationDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultAmortizationDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultAmortizationDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the amortizationDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultAmortizationDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField2 is not null
        defaultAmortizationDetailsShouldBeFound("freeField2.specified=true");

        // Get all the amortizationDetailsList where freeField2 is null
        defaultAmortizationDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultAmortizationDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the amortizationDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultAmortizationDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultAmortizationDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the amortizationDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultAmortizationDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultAmortizationDetailsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the amortizationDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultAmortizationDetailsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultAmortizationDetailsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the amortizationDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultAmortizationDetailsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField3 is not null
        defaultAmortizationDetailsShouldBeFound("freeField3.specified=true");

        // Get all the amortizationDetailsList where freeField3 is null
        defaultAmortizationDetailsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultAmortizationDetailsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the amortizationDetailsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultAmortizationDetailsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultAmortizationDetailsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the amortizationDetailsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultAmortizationDetailsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultAmortizationDetailsShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the amortizationDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultAmortizationDetailsShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultAmortizationDetailsShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the amortizationDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultAmortizationDetailsShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField4 is not null
        defaultAmortizationDetailsShouldBeFound("freeField4.specified=true");

        // Get all the amortizationDetailsList where freeField4 is null
        defaultAmortizationDetailsShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultAmortizationDetailsShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the amortizationDetailsList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultAmortizationDetailsShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultAmortizationDetailsShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the amortizationDetailsList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultAmortizationDetailsShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultAmortizationDetailsShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the amortizationDetailsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultAmortizationDetailsShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultAmortizationDetailsShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the amortizationDetailsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultAmortizationDetailsShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField5 is not null
        defaultAmortizationDetailsShouldBeFound("freeField5.specified=true");

        // Get all the amortizationDetailsList where freeField5 is null
        defaultAmortizationDetailsShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultAmortizationDetailsShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the amortizationDetailsList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultAmortizationDetailsShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultAmortizationDetailsShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the amortizationDetailsList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultAmortizationDetailsShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultAmortizationDetailsShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the amortizationDetailsList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultAmortizationDetailsShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultAmortizationDetailsShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the amortizationDetailsList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultAmortizationDetailsShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField6 is not null
        defaultAmortizationDetailsShouldBeFound("freeField6.specified=true");

        // Get all the amortizationDetailsList where freeField6 is null
        defaultAmortizationDetailsShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultAmortizationDetailsShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the amortizationDetailsList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultAmortizationDetailsShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        // Get all the amortizationDetailsList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultAmortizationDetailsShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the amortizationDetailsList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultAmortizationDetailsShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllAmortizationDetailsByLoanAccountIsEqualToSomething() throws Exception {
        LoanAccount loanAccount;
        if (TestUtil.findAll(em, LoanAccount.class).isEmpty()) {
            amortizationDetailsRepository.saveAndFlush(amortizationDetails);
            loanAccount = LoanAccountResourceIT.createEntity(em);
        } else {
            loanAccount = TestUtil.findAll(em, LoanAccount.class).get(0);
        }
        em.persist(loanAccount);
        em.flush();
        amortizationDetails.setLoanAccount(loanAccount);
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);
        Long loanAccountId = loanAccount.getId();

        // Get all the amortizationDetailsList where loanAccount equals to loanAccountId
        defaultAmortizationDetailsShouldBeFound("loanAccountId.equals=" + loanAccountId);

        // Get all the amortizationDetailsList where loanAccount equals to (loanAccountId + 1)
        defaultAmortizationDetailsShouldNotBeFound("loanAccountId.equals=" + (loanAccountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAmortizationDetailsShouldBeFound(String filter) throws Exception {
        restAmortizationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].installmentDate").value(hasItem(DEFAULT_INSTALLMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].totalOutstandingPrincipleAmt").value(hasItem(DEFAULT_TOTAL_OUTSTANDING_PRINCIPLE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalOutstandngInterestAmt").value(hasItem(DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].paidPrincipleAmt").value(hasItem(DEFAULT_PAID_PRINCIPLE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].paidInterestAmt").value(hasItem(DEFAULT_PAID_INTEREST_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].balPrincipleAmt").value(hasItem(DEFAULT_BAL_PRINCIPLE_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].balInterestAmt").value(hasItem(DEFAULT_BAL_INTEREST_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].loanEmiAmt").value(hasItem(DEFAULT_LOAN_EMI_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].principleEMI").value(hasItem(DEFAULT_PRINCIPLE_EMI.doubleValue())))
            .andExpect(jsonPath("$.[*].roi").value(hasItem(DEFAULT_ROI.doubleValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));

        // Check, that the count call also returns 1
        restAmortizationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAmortizationDetailsShouldNotBeFound(String filter) throws Exception {
        restAmortizationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAmortizationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAmortizationDetails() throws Exception {
        // Get the amortizationDetails
        restAmortizationDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAmortizationDetails() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        int databaseSizeBeforeUpdate = amortizationDetailsRepository.findAll().size();

        // Update the amortizationDetails
        AmortizationDetails updatedAmortizationDetails = amortizationDetailsRepository.findById(amortizationDetails.getId()).get();
        // Disconnect from session so that the updates on updatedAmortizationDetails are not directly saved in db
        em.detach(updatedAmortizationDetails);
        updatedAmortizationDetails
            .installmentDate(UPDATED_INSTALLMENT_DATE)
            .totalOutstandingPrincipleAmt(UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT)
            .totalOutstandngInterestAmt(UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT)
            .paidPrincipleAmt(UPDATED_PAID_PRINCIPLE_AMT)
            .paidInterestAmt(UPDATED_PAID_INTEREST_AMT)
            .balPrincipleAmt(UPDATED_BAL_PRINCIPLE_AMT)
            .balInterestAmt(UPDATED_BAL_INTEREST_AMT)
            .loanEmiAmt(UPDATED_LOAN_EMI_AMT)
            .principleEMI(UPDATED_PRINCIPLE_EMI)
            .roi(UPDATED_ROI)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        AmortizationDetailsDTO amortizationDetailsDTO = amortizationDetailsMapper.toDto(updatedAmortizationDetails);

        restAmortizationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, amortizationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(amortizationDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the AmortizationDetails in the database
        List<AmortizationDetails> amortizationDetailsList = amortizationDetailsRepository.findAll();
        assertThat(amortizationDetailsList).hasSize(databaseSizeBeforeUpdate);
        AmortizationDetails testAmortizationDetails = amortizationDetailsList.get(amortizationDetailsList.size() - 1);
        assertThat(testAmortizationDetails.getInstallmentDate()).isEqualTo(UPDATED_INSTALLMENT_DATE);
        assertThat(testAmortizationDetails.getTotalOutstandingPrincipleAmt()).isEqualTo(UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT);
        assertThat(testAmortizationDetails.getTotalOutstandngInterestAmt()).isEqualTo(UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT);
        assertThat(testAmortizationDetails.getPaidPrincipleAmt()).isEqualTo(UPDATED_PAID_PRINCIPLE_AMT);
        assertThat(testAmortizationDetails.getPaidInterestAmt()).isEqualTo(UPDATED_PAID_INTEREST_AMT);
        assertThat(testAmortizationDetails.getBalPrincipleAmt()).isEqualTo(UPDATED_BAL_PRINCIPLE_AMT);
        assertThat(testAmortizationDetails.getBalInterestAmt()).isEqualTo(UPDATED_BAL_INTEREST_AMT);
        assertThat(testAmortizationDetails.getLoanEmiAmt()).isEqualTo(UPDATED_LOAN_EMI_AMT);
        assertThat(testAmortizationDetails.getPrincipleEMI()).isEqualTo(UPDATED_PRINCIPLE_EMI);
        assertThat(testAmortizationDetails.getRoi()).isEqualTo(UPDATED_ROI);
        assertThat(testAmortizationDetails.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testAmortizationDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAmortizationDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAmortizationDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAmortizationDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testAmortizationDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testAmortizationDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testAmortizationDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testAmortizationDetails.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testAmortizationDetails.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingAmortizationDetails() throws Exception {
        int databaseSizeBeforeUpdate = amortizationDetailsRepository.findAll().size();
        amortizationDetails.setId(count.incrementAndGet());

        // Create the AmortizationDetails
        AmortizationDetailsDTO amortizationDetailsDTO = amortizationDetailsMapper.toDto(amortizationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmortizationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, amortizationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(amortizationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AmortizationDetails in the database
        List<AmortizationDetails> amortizationDetailsList = amortizationDetailsRepository.findAll();
        assertThat(amortizationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAmortizationDetails() throws Exception {
        int databaseSizeBeforeUpdate = amortizationDetailsRepository.findAll().size();
        amortizationDetails.setId(count.incrementAndGet());

        // Create the AmortizationDetails
        AmortizationDetailsDTO amortizationDetailsDTO = amortizationDetailsMapper.toDto(amortizationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmortizationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(amortizationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AmortizationDetails in the database
        List<AmortizationDetails> amortizationDetailsList = amortizationDetailsRepository.findAll();
        assertThat(amortizationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAmortizationDetails() throws Exception {
        int databaseSizeBeforeUpdate = amortizationDetailsRepository.findAll().size();
        amortizationDetails.setId(count.incrementAndGet());

        // Create the AmortizationDetails
        AmortizationDetailsDTO amortizationDetailsDTO = amortizationDetailsMapper.toDto(amortizationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmortizationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(amortizationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AmortizationDetails in the database
        List<AmortizationDetails> amortizationDetailsList = amortizationDetailsRepository.findAll();
        assertThat(amortizationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAmortizationDetailsWithPatch() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        int databaseSizeBeforeUpdate = amortizationDetailsRepository.findAll().size();

        // Update the amortizationDetails using partial update
        AmortizationDetails partialUpdatedAmortizationDetails = new AmortizationDetails();
        partialUpdatedAmortizationDetails.setId(amortizationDetails.getId());

        partialUpdatedAmortizationDetails
            .installmentDate(UPDATED_INSTALLMENT_DATE)
            .totalOutstandingPrincipleAmt(UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT)
            .paidPrincipleAmt(UPDATED_PAID_PRINCIPLE_AMT)
            .paidInterestAmt(UPDATED_PAID_INTEREST_AMT)
            .balPrincipleAmt(UPDATED_BAL_PRINCIPLE_AMT)
            .balInterestAmt(UPDATED_BAL_INTEREST_AMT)
            .loanEmiAmt(UPDATED_LOAN_EMI_AMT)
            .principleEMI(UPDATED_PRINCIPLE_EMI)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .year(UPDATED_YEAR)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restAmortizationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAmortizationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAmortizationDetails))
            )
            .andExpect(status().isOk());

        // Validate the AmortizationDetails in the database
        List<AmortizationDetails> amortizationDetailsList = amortizationDetailsRepository.findAll();
        assertThat(amortizationDetailsList).hasSize(databaseSizeBeforeUpdate);
        AmortizationDetails testAmortizationDetails = amortizationDetailsList.get(amortizationDetailsList.size() - 1);
        assertThat(testAmortizationDetails.getInstallmentDate()).isEqualTo(UPDATED_INSTALLMENT_DATE);
        assertThat(testAmortizationDetails.getTotalOutstandingPrincipleAmt()).isEqualTo(UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT);
        assertThat(testAmortizationDetails.getTotalOutstandngInterestAmt()).isEqualTo(DEFAULT_TOTAL_OUTSTANDNG_INTEREST_AMT);
        assertThat(testAmortizationDetails.getPaidPrincipleAmt()).isEqualTo(UPDATED_PAID_PRINCIPLE_AMT);
        assertThat(testAmortizationDetails.getPaidInterestAmt()).isEqualTo(UPDATED_PAID_INTEREST_AMT);
        assertThat(testAmortizationDetails.getBalPrincipleAmt()).isEqualTo(UPDATED_BAL_PRINCIPLE_AMT);
        assertThat(testAmortizationDetails.getBalInterestAmt()).isEqualTo(UPDATED_BAL_INTEREST_AMT);
        assertThat(testAmortizationDetails.getLoanEmiAmt()).isEqualTo(UPDATED_LOAN_EMI_AMT);
        assertThat(testAmortizationDetails.getPrincipleEMI()).isEqualTo(UPDATED_PRINCIPLE_EMI);
        assertThat(testAmortizationDetails.getRoi()).isEqualTo(DEFAULT_ROI);
        assertThat(testAmortizationDetails.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testAmortizationDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAmortizationDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAmortizationDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAmortizationDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testAmortizationDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testAmortizationDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testAmortizationDetails.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testAmortizationDetails.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testAmortizationDetails.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateAmortizationDetailsWithPatch() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        int databaseSizeBeforeUpdate = amortizationDetailsRepository.findAll().size();

        // Update the amortizationDetails using partial update
        AmortizationDetails partialUpdatedAmortizationDetails = new AmortizationDetails();
        partialUpdatedAmortizationDetails.setId(amortizationDetails.getId());

        partialUpdatedAmortizationDetails
            .installmentDate(UPDATED_INSTALLMENT_DATE)
            .totalOutstandingPrincipleAmt(UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT)
            .totalOutstandngInterestAmt(UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT)
            .paidPrincipleAmt(UPDATED_PAID_PRINCIPLE_AMT)
            .paidInterestAmt(UPDATED_PAID_INTEREST_AMT)
            .balPrincipleAmt(UPDATED_BAL_PRINCIPLE_AMT)
            .balInterestAmt(UPDATED_BAL_INTEREST_AMT)
            .loanEmiAmt(UPDATED_LOAN_EMI_AMT)
            .principleEMI(UPDATED_PRINCIPLE_EMI)
            .roi(UPDATED_ROI)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .year(UPDATED_YEAR)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restAmortizationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAmortizationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAmortizationDetails))
            )
            .andExpect(status().isOk());

        // Validate the AmortizationDetails in the database
        List<AmortizationDetails> amortizationDetailsList = amortizationDetailsRepository.findAll();
        assertThat(amortizationDetailsList).hasSize(databaseSizeBeforeUpdate);
        AmortizationDetails testAmortizationDetails = amortizationDetailsList.get(amortizationDetailsList.size() - 1);
        assertThat(testAmortizationDetails.getInstallmentDate()).isEqualTo(UPDATED_INSTALLMENT_DATE);
        assertThat(testAmortizationDetails.getTotalOutstandingPrincipleAmt()).isEqualTo(UPDATED_TOTAL_OUTSTANDING_PRINCIPLE_AMT);
        assertThat(testAmortizationDetails.getTotalOutstandngInterestAmt()).isEqualTo(UPDATED_TOTAL_OUTSTANDNG_INTEREST_AMT);
        assertThat(testAmortizationDetails.getPaidPrincipleAmt()).isEqualTo(UPDATED_PAID_PRINCIPLE_AMT);
        assertThat(testAmortizationDetails.getPaidInterestAmt()).isEqualTo(UPDATED_PAID_INTEREST_AMT);
        assertThat(testAmortizationDetails.getBalPrincipleAmt()).isEqualTo(UPDATED_BAL_PRINCIPLE_AMT);
        assertThat(testAmortizationDetails.getBalInterestAmt()).isEqualTo(UPDATED_BAL_INTEREST_AMT);
        assertThat(testAmortizationDetails.getLoanEmiAmt()).isEqualTo(UPDATED_LOAN_EMI_AMT);
        assertThat(testAmortizationDetails.getPrincipleEMI()).isEqualTo(UPDATED_PRINCIPLE_EMI);
        assertThat(testAmortizationDetails.getRoi()).isEqualTo(UPDATED_ROI);
        assertThat(testAmortizationDetails.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testAmortizationDetails.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAmortizationDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAmortizationDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAmortizationDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testAmortizationDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testAmortizationDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testAmortizationDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testAmortizationDetails.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testAmortizationDetails.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingAmortizationDetails() throws Exception {
        int databaseSizeBeforeUpdate = amortizationDetailsRepository.findAll().size();
        amortizationDetails.setId(count.incrementAndGet());

        // Create the AmortizationDetails
        AmortizationDetailsDTO amortizationDetailsDTO = amortizationDetailsMapper.toDto(amortizationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmortizationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, amortizationDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(amortizationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AmortizationDetails in the database
        List<AmortizationDetails> amortizationDetailsList = amortizationDetailsRepository.findAll();
        assertThat(amortizationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAmortizationDetails() throws Exception {
        int databaseSizeBeforeUpdate = amortizationDetailsRepository.findAll().size();
        amortizationDetails.setId(count.incrementAndGet());

        // Create the AmortizationDetails
        AmortizationDetailsDTO amortizationDetailsDTO = amortizationDetailsMapper.toDto(amortizationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmortizationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(amortizationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AmortizationDetails in the database
        List<AmortizationDetails> amortizationDetailsList = amortizationDetailsRepository.findAll();
        assertThat(amortizationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAmortizationDetails() throws Exception {
        int databaseSizeBeforeUpdate = amortizationDetailsRepository.findAll().size();
        amortizationDetails.setId(count.incrementAndGet());

        // Create the AmortizationDetails
        AmortizationDetailsDTO amortizationDetailsDTO = amortizationDetailsMapper.toDto(amortizationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAmortizationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(amortizationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AmortizationDetails in the database
        List<AmortizationDetails> amortizationDetailsList = amortizationDetailsRepository.findAll();
        assertThat(amortizationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAmortizationDetails() throws Exception {
        // Initialize the database
        amortizationDetailsRepository.saveAndFlush(amortizationDetails);

        int databaseSizeBeforeDelete = amortizationDetailsRepository.findAll().size();

        // Delete the amortizationDetails
        restAmortizationDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, amortizationDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AmortizationDetails> amortizationDetailsList = amortizationDetailsRepository.findAll();
        assertThat(amortizationDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
