package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.LedgerAccounts;
import com.techvg.los.domain.LoanCatagory;
import com.techvg.los.domain.Organisation;
import com.techvg.los.domain.Product;
import com.techvg.los.repository.ProductRepository;
import com.techvg.los.service.criteria.ProductCriteria;
import com.techvg.los.service.dto.ProductDTO;
import com.techvg.los.service.mapper.ProductMapper;
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
 * Integration tests for the {@link ProductResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductResourceIT {

    private static final String DEFAULT_PROD_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROD_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BPI_TREATMENT_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_BPI_TREATMENT_FLAG = "BBBBBBBBBB";

    private static final String DEFAULT_AMORTIZATION_METHOD = "AAAAAAAAAA";
    private static final String UPDATED_AMORTIZATION_METHOD = "BBBBBBBBBB";

    private static final String DEFAULT_AMORTIZATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_AMORTIZATION_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPOUNDING_FREQ = "AAAAAAAAAA";
    private static final String UPDATED_COMPOUNDING_FREQ = "BBBBBBBBBB";

    private static final String DEFAULT_EMI_ROUNDING = "AAAAAAAAAA";
    private static final String UPDATED_EMI_ROUNDING = "BBBBBBBBBB";

    private static final Double DEFAULT_LAST_ROW_EMI_THRESHOLD = 1D;
    private static final Double UPDATED_LAST_ROW_EMI_THRESHOLD = 2D;
    private static final Double SMALLER_LAST_ROW_EMI_THRESHOLD = 1D - 1D;

    private static final Long DEFAULT_GRACE_DAYS = 1L;
    private static final Long UPDATED_GRACE_DAYS = 2L;
    private static final Long SMALLER_GRACE_DAYS = 1L - 1L;

    private static final Long DEFAULT_RESCH_LOCKIN_PERIOD = 1L;
    private static final Long UPDATED_RESCH_LOCKIN_PERIOD = 2L;
    private static final Long SMALLER_RESCH_LOCKIN_PERIOD = 1L - 1L;

    private static final Long DEFAULT_PREPAY_AFTER_INST_NO = 1L;
    private static final Long UPDATED_PREPAY_AFTER_INST_NO = 2L;
    private static final Long SMALLER_PREPAY_AFTER_INST_NO = 1L - 1L;

    private static final Long DEFAULT_PREPAY_BEFORE_INST_NO = 1L;
    private static final Long UPDATED_PREPAY_BEFORE_INST_NO = 2L;
    private static final Long SMALLER_PREPAY_BEFORE_INST_NO = 1L - 1L;

    private static final Long DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY = 1L;
    private static final Long UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY = 2L;
    private static final Long SMALLER_MIN_INSTALLMENT_GAP_BET_PREPAY = 1L - 1L;

    private static final Double DEFAULT_MIN_PREPAY_AMOUNT = 1D;
    private static final Double UPDATED_MIN_PREPAY_AMOUNT = 2D;
    private static final Double SMALLER_MIN_PREPAY_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_FORECLOSE_AFTER_INST_NO = 1D;
    private static final Double UPDATED_FORECLOSE_AFTER_INST_NO = 2D;
    private static final Double SMALLER_FORECLOSE_AFTER_INST_NO = 1D - 1D;

    private static final Double DEFAULT_FORECLOSE_BEFORE_INSTA_NO = 1D;
    private static final Double UPDATED_FORECLOSE_BEFORE_INSTA_NO = 2D;
    private static final Double SMALLER_FORECLOSE_BEFORE_INSTA_NO = 1D - 1D;

    private static final Double DEFAULT_MIN_TENOR = 1D;
    private static final Double UPDATED_MIN_TENOR = 2D;
    private static final Double SMALLER_MIN_TENOR = 1D - 1D;

    private static final Double DEFAULT_MAX_TENOR = 1D;
    private static final Double UPDATED_MAX_TENOR = 2D;
    private static final Double SMALLER_MAX_TENOR = 1D - 1D;

    private static final Double DEFAULT_MIN_INSTALLMENT_AMOUNT = 1D;
    private static final Double UPDATED_MIN_INSTALLMENT_AMOUNT = 2D;
    private static final Double SMALLER_MIN_INSTALLMENT_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_MAX_INSTALLMENT_AMOUNT = 1D;
    private static final Double UPDATED_MAX_INSTALLMENT_AMOUNT = 2D;
    private static final Double SMALLER_MAX_INSTALLMENT_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_DROP_LINE_AMOUNT = 1D;
    private static final Double UPDATED_DROP_LINE_AMOUNT = 2D;
    private static final Double SMALLER_DROP_LINE_AMOUNT = 1D - 1D;

    private static final String DEFAULT_DROP_LINE_ODYN = "AAAAAAAAAA";
    private static final String UPDATED_DROP_LINE_ODYN = "BBBBBBBBBB";

    private static final Long DEFAULT_DROP_LINE_PERC = 1L;
    private static final Long UPDATED_DROP_LINE_PERC = 2L;
    private static final Long SMALLER_DROP_LINE_PERC = 1L - 1L;

    private static final String DEFAULT_DROP_MODE = "AAAAAAAAAA";
    private static final String UPDATED_DROP_MODE = "BBBBBBBBBB";

    private static final String DEFAULT_DROP_L_INE_FREQ = "AAAAAAAAAA";
    private static final String UPDATED_DROP_L_INE_FREQ = "BBBBBBBBBB";

    private static final Long DEFAULT_DROP_LINE_CYCLE_DAY = 1L;
    private static final Long UPDATED_DROP_LINE_CYCLE_DAY = 2L;
    private static final Long SMALLER_DROP_LINE_CYCLE_DAY = 1L - 1L;

    private static final Double DEFAULT_ROI = 1D;
    private static final Double UPDATED_ROI = 2D;
    private static final Double SMALLER_ROI = 1D - 1D;

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

    private static final String ENTITY_API_URL = "/api/products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .prodCode(DEFAULT_PROD_CODE)
            .prodName(DEFAULT_PROD_NAME)
            .bpiTreatmentFlag(DEFAULT_BPI_TREATMENT_FLAG)
            .amortizationMethod(DEFAULT_AMORTIZATION_METHOD)
            .amortizationType(DEFAULT_AMORTIZATION_TYPE)
            .compoundingFreq(DEFAULT_COMPOUNDING_FREQ)
            .emiRounding(DEFAULT_EMI_ROUNDING)
            .lastRowEMIThreshold(DEFAULT_LAST_ROW_EMI_THRESHOLD)
            .graceDays(DEFAULT_GRACE_DAYS)
            .reschLockinPeriod(DEFAULT_RESCH_LOCKIN_PERIOD)
            .prepayAfterInstNo(DEFAULT_PREPAY_AFTER_INST_NO)
            .prepayBeforeInstNo(DEFAULT_PREPAY_BEFORE_INST_NO)
            .minInstallmentGapBetPrepay(DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY)
            .minPrepayAmount(DEFAULT_MIN_PREPAY_AMOUNT)
            .forecloseAfterInstNo(DEFAULT_FORECLOSE_AFTER_INST_NO)
            .forecloseBeforeInstaNo(DEFAULT_FORECLOSE_BEFORE_INSTA_NO)
            .minTenor(DEFAULT_MIN_TENOR)
            .maxTenor(DEFAULT_MAX_TENOR)
            .minInstallmentAmount(DEFAULT_MIN_INSTALLMENT_AMOUNT)
            .maxInstallmentAmount(DEFAULT_MAX_INSTALLMENT_AMOUNT)
            .dropLineAmount(DEFAULT_DROP_LINE_AMOUNT)
            .dropLineODYN(DEFAULT_DROP_LINE_ODYN)
            .dropLinePerc(DEFAULT_DROP_LINE_PERC)
            .dropMode(DEFAULT_DROP_MODE)
            .dropLIneFreq(DEFAULT_DROP_L_INE_FREQ)
            .dropLineCycleDay(DEFAULT_DROP_LINE_CYCLE_DAY)
            .roi(DEFAULT_ROI)
            .isDeleted(DEFAULT_IS_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .prodColour(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5)
            .prodIconUrl(DEFAULT_FREE_FIELD_6);
        return product;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .prodCode(UPDATED_PROD_CODE)
            .prodName(UPDATED_PROD_NAME)
            .bpiTreatmentFlag(UPDATED_BPI_TREATMENT_FLAG)
            .amortizationMethod(UPDATED_AMORTIZATION_METHOD)
            .amortizationType(UPDATED_AMORTIZATION_TYPE)
            .compoundingFreq(UPDATED_COMPOUNDING_FREQ)
            .emiRounding(UPDATED_EMI_ROUNDING)
            .lastRowEMIThreshold(UPDATED_LAST_ROW_EMI_THRESHOLD)
            .graceDays(UPDATED_GRACE_DAYS)
            .reschLockinPeriod(UPDATED_RESCH_LOCKIN_PERIOD)
            .prepayAfterInstNo(UPDATED_PREPAY_AFTER_INST_NO)
            .prepayBeforeInstNo(UPDATED_PREPAY_BEFORE_INST_NO)
            .minInstallmentGapBetPrepay(UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY)
            .minPrepayAmount(UPDATED_MIN_PREPAY_AMOUNT)
            .forecloseAfterInstNo(UPDATED_FORECLOSE_AFTER_INST_NO)
            .forecloseBeforeInstaNo(UPDATED_FORECLOSE_BEFORE_INSTA_NO)
            .minTenor(UPDATED_MIN_TENOR)
            .maxTenor(UPDATED_MAX_TENOR)
            .minInstallmentAmount(UPDATED_MIN_INSTALLMENT_AMOUNT)
            .maxInstallmentAmount(UPDATED_MAX_INSTALLMENT_AMOUNT)
            .dropLineAmount(UPDATED_DROP_LINE_AMOUNT)
            .dropLineODYN(UPDATED_DROP_LINE_ODYN)
            .dropLinePerc(UPDATED_DROP_LINE_PERC)
            .dropMode(UPDATED_DROP_MODE)
            .dropLIneFreq(UPDATED_DROP_L_INE_FREQ)
            .dropLineCycleDay(UPDATED_DROP_LINE_CYCLE_DAY)
            .roi(UPDATED_ROI)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .prodColour(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .prodIconUrl(UPDATED_FREE_FIELD_6);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);
        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProdCode()).isEqualTo(DEFAULT_PROD_CODE);
        assertThat(testProduct.getProdName()).isEqualTo(DEFAULT_PROD_NAME);
        assertThat(testProduct.getBpiTreatmentFlag()).isEqualTo(DEFAULT_BPI_TREATMENT_FLAG);
        assertThat(testProduct.getAmortizationMethod()).isEqualTo(DEFAULT_AMORTIZATION_METHOD);
        assertThat(testProduct.getAmortizationType()).isEqualTo(DEFAULT_AMORTIZATION_TYPE);
        assertThat(testProduct.getCompoundingFreq()).isEqualTo(DEFAULT_COMPOUNDING_FREQ);
        assertThat(testProduct.getEmiRounding()).isEqualTo(DEFAULT_EMI_ROUNDING);
        assertThat(testProduct.getLastRowEMIThreshold()).isEqualTo(DEFAULT_LAST_ROW_EMI_THRESHOLD);
        assertThat(testProduct.getGraceDays()).isEqualTo(DEFAULT_GRACE_DAYS);
        assertThat(testProduct.getReschLockinPeriod()).isEqualTo(DEFAULT_RESCH_LOCKIN_PERIOD);
        assertThat(testProduct.getPrepayAfterInstNo()).isEqualTo(DEFAULT_PREPAY_AFTER_INST_NO);
        assertThat(testProduct.getPrepayBeforeInstNo()).isEqualTo(DEFAULT_PREPAY_BEFORE_INST_NO);
        assertThat(testProduct.getMinInstallmentGapBetPrepay()).isEqualTo(DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY);
        assertThat(testProduct.getMinPrepayAmount()).isEqualTo(DEFAULT_MIN_PREPAY_AMOUNT);
        assertThat(testProduct.getForecloseAfterInstNo()).isEqualTo(DEFAULT_FORECLOSE_AFTER_INST_NO);
        assertThat(testProduct.getForecloseBeforeInstaNo()).isEqualTo(DEFAULT_FORECLOSE_BEFORE_INSTA_NO);
        assertThat(testProduct.getMinTenor()).isEqualTo(DEFAULT_MIN_TENOR);
        assertThat(testProduct.getMaxTenor()).isEqualTo(DEFAULT_MAX_TENOR);
        assertThat(testProduct.getMinInstallmentAmount()).isEqualTo(DEFAULT_MIN_INSTALLMENT_AMOUNT);
        assertThat(testProduct.getMaxInstallmentAmount()).isEqualTo(DEFAULT_MAX_INSTALLMENT_AMOUNT);
        assertThat(testProduct.getDropLineAmount()).isEqualTo(DEFAULT_DROP_LINE_AMOUNT);
        assertThat(testProduct.getDropLineODYN()).isEqualTo(DEFAULT_DROP_LINE_ODYN);
        assertThat(testProduct.getDropLinePerc()).isEqualTo(DEFAULT_DROP_LINE_PERC);
        assertThat(testProduct.getDropMode()).isEqualTo(DEFAULT_DROP_MODE);
        assertThat(testProduct.getDropLIneFreq()).isEqualTo(DEFAULT_DROP_L_INE_FREQ);
        assertThat(testProduct.getDropLineCycleDay()).isEqualTo(DEFAULT_DROP_LINE_CYCLE_DAY);
        assertThat(testProduct.getRoi()).isEqualTo(DEFAULT_ROI);
        assertThat(testProduct.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testProduct.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testProduct.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProduct.getProdColour()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testProduct.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testProduct.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testProduct.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testProduct.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testProduct.getProdIconUrl()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createProductWithExistingId() throws Exception {
        // Create the Product with an existing ID
        product.setId(1L);
        ProductDTO productDTO = productMapper.toDto(product);

        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].prodCode").value(hasItem(DEFAULT_PROD_CODE)))
            .andExpect(jsonPath("$.[*].prodName").value(hasItem(DEFAULT_PROD_NAME)))
            .andExpect(jsonPath("$.[*].bpiTreatmentFlag").value(hasItem(DEFAULT_BPI_TREATMENT_FLAG)))
            .andExpect(jsonPath("$.[*].amortizationMethod").value(hasItem(DEFAULT_AMORTIZATION_METHOD)))
            .andExpect(jsonPath("$.[*].amortizationType").value(hasItem(DEFAULT_AMORTIZATION_TYPE)))
            .andExpect(jsonPath("$.[*].compoundingFreq").value(hasItem(DEFAULT_COMPOUNDING_FREQ)))
            .andExpect(jsonPath("$.[*].emiRounding").value(hasItem(DEFAULT_EMI_ROUNDING)))
            .andExpect(jsonPath("$.[*].lastRowEMIThreshold").value(hasItem(DEFAULT_LAST_ROW_EMI_THRESHOLD.doubleValue())))
            .andExpect(jsonPath("$.[*].graceDays").value(hasItem(DEFAULT_GRACE_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].reschLockinPeriod").value(hasItem(DEFAULT_RESCH_LOCKIN_PERIOD.intValue())))
            .andExpect(jsonPath("$.[*].prepayAfterInstNo").value(hasItem(DEFAULT_PREPAY_AFTER_INST_NO.intValue())))
            .andExpect(jsonPath("$.[*].prepayBeforeInstNo").value(hasItem(DEFAULT_PREPAY_BEFORE_INST_NO.intValue())))
            .andExpect(jsonPath("$.[*].minInstallmentGapBetPrepay").value(hasItem(DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY.intValue())))
            .andExpect(jsonPath("$.[*].minPrepayAmount").value(hasItem(DEFAULT_MIN_PREPAY_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].forecloseAfterInstNo").value(hasItem(DEFAULT_FORECLOSE_AFTER_INST_NO.doubleValue())))
            .andExpect(jsonPath("$.[*].forecloseBeforeInstaNo").value(hasItem(DEFAULT_FORECLOSE_BEFORE_INSTA_NO.doubleValue())))
            .andExpect(jsonPath("$.[*].minTenor").value(hasItem(DEFAULT_MIN_TENOR.doubleValue())))
            .andExpect(jsonPath("$.[*].maxTenor").value(hasItem(DEFAULT_MAX_TENOR.doubleValue())))
            .andExpect(jsonPath("$.[*].minInstallmentAmount").value(hasItem(DEFAULT_MIN_INSTALLMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].maxInstallmentAmount").value(hasItem(DEFAULT_MAX_INSTALLMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dropLineAmount").value(hasItem(DEFAULT_DROP_LINE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dropLineODYN").value(hasItem(DEFAULT_DROP_LINE_ODYN)))
            .andExpect(jsonPath("$.[*].dropLinePerc").value(hasItem(DEFAULT_DROP_LINE_PERC.intValue())))
            .andExpect(jsonPath("$.[*].dropMode").value(hasItem(DEFAULT_DROP_MODE)))
            .andExpect(jsonPath("$.[*].dropLIneFreq").value(hasItem(DEFAULT_DROP_L_INE_FREQ)))
            .andExpect(jsonPath("$.[*].dropLineCycleDay").value(hasItem(DEFAULT_DROP_LINE_CYCLE_DAY.intValue())))
            .andExpect(jsonPath("$.[*].roi").value(hasItem(DEFAULT_ROI.doubleValue())))
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
    void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc
            .perform(get(ENTITY_API_URL_ID, product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.prodCode").value(DEFAULT_PROD_CODE))
            .andExpect(jsonPath("$.prodName").value(DEFAULT_PROD_NAME))
            .andExpect(jsonPath("$.bpiTreatmentFlag").value(DEFAULT_BPI_TREATMENT_FLAG))
            .andExpect(jsonPath("$.amortizationMethod").value(DEFAULT_AMORTIZATION_METHOD))
            .andExpect(jsonPath("$.amortizationType").value(DEFAULT_AMORTIZATION_TYPE))
            .andExpect(jsonPath("$.compoundingFreq").value(DEFAULT_COMPOUNDING_FREQ))
            .andExpect(jsonPath("$.emiRounding").value(DEFAULT_EMI_ROUNDING))
            .andExpect(jsonPath("$.lastRowEMIThreshold").value(DEFAULT_LAST_ROW_EMI_THRESHOLD.doubleValue()))
            .andExpect(jsonPath("$.graceDays").value(DEFAULT_GRACE_DAYS.intValue()))
            .andExpect(jsonPath("$.reschLockinPeriod").value(DEFAULT_RESCH_LOCKIN_PERIOD.intValue()))
            .andExpect(jsonPath("$.prepayAfterInstNo").value(DEFAULT_PREPAY_AFTER_INST_NO.intValue()))
            .andExpect(jsonPath("$.prepayBeforeInstNo").value(DEFAULT_PREPAY_BEFORE_INST_NO.intValue()))
            .andExpect(jsonPath("$.minInstallmentGapBetPrepay").value(DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY.intValue()))
            .andExpect(jsonPath("$.minPrepayAmount").value(DEFAULT_MIN_PREPAY_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.forecloseAfterInstNo").value(DEFAULT_FORECLOSE_AFTER_INST_NO.doubleValue()))
            .andExpect(jsonPath("$.forecloseBeforeInstaNo").value(DEFAULT_FORECLOSE_BEFORE_INSTA_NO.doubleValue()))
            .andExpect(jsonPath("$.minTenor").value(DEFAULT_MIN_TENOR.doubleValue()))
            .andExpect(jsonPath("$.maxTenor").value(DEFAULT_MAX_TENOR.doubleValue()))
            .andExpect(jsonPath("$.minInstallmentAmount").value(DEFAULT_MIN_INSTALLMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.maxInstallmentAmount").value(DEFAULT_MAX_INSTALLMENT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.dropLineAmount").value(DEFAULT_DROP_LINE_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.dropLineODYN").value(DEFAULT_DROP_LINE_ODYN))
            .andExpect(jsonPath("$.dropLinePerc").value(DEFAULT_DROP_LINE_PERC.intValue()))
            .andExpect(jsonPath("$.dropMode").value(DEFAULT_DROP_MODE))
            .andExpect(jsonPath("$.dropLIneFreq").value(DEFAULT_DROP_L_INE_FREQ))
            .andExpect(jsonPath("$.dropLineCycleDay").value(DEFAULT_DROP_LINE_CYCLE_DAY.intValue()))
            .andExpect(jsonPath("$.roi").value(DEFAULT_ROI.doubleValue()))
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
    void getProductsByIdFiltering() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        Long id = product.getId();

        defaultProductShouldBeFound("id.equals=" + id);
        defaultProductShouldNotBeFound("id.notEquals=" + id);

        defaultProductShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.greaterThan=" + id);

        defaultProductShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductsByProdCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prodCode equals to DEFAULT_PROD_CODE
        defaultProductShouldBeFound("prodCode.equals=" + DEFAULT_PROD_CODE);

        // Get all the productList where prodCode equals to UPDATED_PROD_CODE
        defaultProductShouldNotBeFound("prodCode.equals=" + UPDATED_PROD_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByProdCodeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prodCode in DEFAULT_PROD_CODE or UPDATED_PROD_CODE
        defaultProductShouldBeFound("prodCode.in=" + DEFAULT_PROD_CODE + "," + UPDATED_PROD_CODE);

        // Get all the productList where prodCode equals to UPDATED_PROD_CODE
        defaultProductShouldNotBeFound("prodCode.in=" + UPDATED_PROD_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByProdCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prodCode is not null
        defaultProductShouldBeFound("prodCode.specified=true");

        // Get all the productList where prodCode is null
        defaultProductShouldNotBeFound("prodCode.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByProdCodeContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prodCode contains DEFAULT_PROD_CODE
        defaultProductShouldBeFound("prodCode.contains=" + DEFAULT_PROD_CODE);

        // Get all the productList where prodCode contains UPDATED_PROD_CODE
        defaultProductShouldNotBeFound("prodCode.contains=" + UPDATED_PROD_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByProdCodeNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prodCode does not contain DEFAULT_PROD_CODE
        defaultProductShouldNotBeFound("prodCode.doesNotContain=" + DEFAULT_PROD_CODE);

        // Get all the productList where prodCode does not contain UPDATED_PROD_CODE
        defaultProductShouldBeFound("prodCode.doesNotContain=" + UPDATED_PROD_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByProdNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prodName equals to DEFAULT_PROD_NAME
        defaultProductShouldBeFound("prodName.equals=" + DEFAULT_PROD_NAME);

        // Get all the productList where prodName equals to UPDATED_PROD_NAME
        defaultProductShouldNotBeFound("prodName.equals=" + UPDATED_PROD_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProdNameIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prodName in DEFAULT_PROD_NAME or UPDATED_PROD_NAME
        defaultProductShouldBeFound("prodName.in=" + DEFAULT_PROD_NAME + "," + UPDATED_PROD_NAME);

        // Get all the productList where prodName equals to UPDATED_PROD_NAME
        defaultProductShouldNotBeFound("prodName.in=" + UPDATED_PROD_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProdNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prodName is not null
        defaultProductShouldBeFound("prodName.specified=true");

        // Get all the productList where prodName is null
        defaultProductShouldNotBeFound("prodName.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByProdNameContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prodName contains DEFAULT_PROD_NAME
        defaultProductShouldBeFound("prodName.contains=" + DEFAULT_PROD_NAME);

        // Get all the productList where prodName contains UPDATED_PROD_NAME
        defaultProductShouldNotBeFound("prodName.contains=" + UPDATED_PROD_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByProdNameNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prodName does not contain DEFAULT_PROD_NAME
        defaultProductShouldNotBeFound("prodName.doesNotContain=" + DEFAULT_PROD_NAME);

        // Get all the productList where prodName does not contain UPDATED_PROD_NAME
        defaultProductShouldBeFound("prodName.doesNotContain=" + UPDATED_PROD_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByBpiTreatmentFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where bpiTreatmentFlag equals to DEFAULT_BPI_TREATMENT_FLAG
        defaultProductShouldBeFound("bpiTreatmentFlag.equals=" + DEFAULT_BPI_TREATMENT_FLAG);

        // Get all the productList where bpiTreatmentFlag equals to UPDATED_BPI_TREATMENT_FLAG
        defaultProductShouldNotBeFound("bpiTreatmentFlag.equals=" + UPDATED_BPI_TREATMENT_FLAG);
    }

    @Test
    @Transactional
    void getAllProductsByBpiTreatmentFlagIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where bpiTreatmentFlag in DEFAULT_BPI_TREATMENT_FLAG or UPDATED_BPI_TREATMENT_FLAG
        defaultProductShouldBeFound("bpiTreatmentFlag.in=" + DEFAULT_BPI_TREATMENT_FLAG + "," + UPDATED_BPI_TREATMENT_FLAG);

        // Get all the productList where bpiTreatmentFlag equals to UPDATED_BPI_TREATMENT_FLAG
        defaultProductShouldNotBeFound("bpiTreatmentFlag.in=" + UPDATED_BPI_TREATMENT_FLAG);
    }

    @Test
    @Transactional
    void getAllProductsByBpiTreatmentFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where bpiTreatmentFlag is not null
        defaultProductShouldBeFound("bpiTreatmentFlag.specified=true");

        // Get all the productList where bpiTreatmentFlag is null
        defaultProductShouldNotBeFound("bpiTreatmentFlag.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByBpiTreatmentFlagContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where bpiTreatmentFlag contains DEFAULT_BPI_TREATMENT_FLAG
        defaultProductShouldBeFound("bpiTreatmentFlag.contains=" + DEFAULT_BPI_TREATMENT_FLAG);

        // Get all the productList where bpiTreatmentFlag contains UPDATED_BPI_TREATMENT_FLAG
        defaultProductShouldNotBeFound("bpiTreatmentFlag.contains=" + UPDATED_BPI_TREATMENT_FLAG);
    }

    @Test
    @Transactional
    void getAllProductsByBpiTreatmentFlagNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where bpiTreatmentFlag does not contain DEFAULT_BPI_TREATMENT_FLAG
        defaultProductShouldNotBeFound("bpiTreatmentFlag.doesNotContain=" + DEFAULT_BPI_TREATMENT_FLAG);

        // Get all the productList where bpiTreatmentFlag does not contain UPDATED_BPI_TREATMENT_FLAG
        defaultProductShouldBeFound("bpiTreatmentFlag.doesNotContain=" + UPDATED_BPI_TREATMENT_FLAG);
    }

    @Test
    @Transactional
    void getAllProductsByAmortizationMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where amortizationMethod equals to DEFAULT_AMORTIZATION_METHOD
        defaultProductShouldBeFound("amortizationMethod.equals=" + DEFAULT_AMORTIZATION_METHOD);

        // Get all the productList where amortizationMethod equals to UPDATED_AMORTIZATION_METHOD
        defaultProductShouldNotBeFound("amortizationMethod.equals=" + UPDATED_AMORTIZATION_METHOD);
    }

    @Test
    @Transactional
    void getAllProductsByAmortizationMethodIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where amortizationMethod in DEFAULT_AMORTIZATION_METHOD or UPDATED_AMORTIZATION_METHOD
        defaultProductShouldBeFound("amortizationMethod.in=" + DEFAULT_AMORTIZATION_METHOD + "," + UPDATED_AMORTIZATION_METHOD);

        // Get all the productList where amortizationMethod equals to UPDATED_AMORTIZATION_METHOD
        defaultProductShouldNotBeFound("amortizationMethod.in=" + UPDATED_AMORTIZATION_METHOD);
    }

    @Test
    @Transactional
    void getAllProductsByAmortizationMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where amortizationMethod is not null
        defaultProductShouldBeFound("amortizationMethod.specified=true");

        // Get all the productList where amortizationMethod is null
        defaultProductShouldNotBeFound("amortizationMethod.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByAmortizationMethodContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where amortizationMethod contains DEFAULT_AMORTIZATION_METHOD
        defaultProductShouldBeFound("amortizationMethod.contains=" + DEFAULT_AMORTIZATION_METHOD);

        // Get all the productList where amortizationMethod contains UPDATED_AMORTIZATION_METHOD
        defaultProductShouldNotBeFound("amortizationMethod.contains=" + UPDATED_AMORTIZATION_METHOD);
    }

    @Test
    @Transactional
    void getAllProductsByAmortizationMethodNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where amortizationMethod does not contain DEFAULT_AMORTIZATION_METHOD
        defaultProductShouldNotBeFound("amortizationMethod.doesNotContain=" + DEFAULT_AMORTIZATION_METHOD);

        // Get all the productList where amortizationMethod does not contain UPDATED_AMORTIZATION_METHOD
        defaultProductShouldBeFound("amortizationMethod.doesNotContain=" + UPDATED_AMORTIZATION_METHOD);
    }

    @Test
    @Transactional
    void getAllProductsByAmortizationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where amortizationType equals to DEFAULT_AMORTIZATION_TYPE
        defaultProductShouldBeFound("amortizationType.equals=" + DEFAULT_AMORTIZATION_TYPE);

        // Get all the productList where amortizationType equals to UPDATED_AMORTIZATION_TYPE
        defaultProductShouldNotBeFound("amortizationType.equals=" + UPDATED_AMORTIZATION_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsByAmortizationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where amortizationType in DEFAULT_AMORTIZATION_TYPE or UPDATED_AMORTIZATION_TYPE
        defaultProductShouldBeFound("amortizationType.in=" + DEFAULT_AMORTIZATION_TYPE + "," + UPDATED_AMORTIZATION_TYPE);

        // Get all the productList where amortizationType equals to UPDATED_AMORTIZATION_TYPE
        defaultProductShouldNotBeFound("amortizationType.in=" + UPDATED_AMORTIZATION_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsByAmortizationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where amortizationType is not null
        defaultProductShouldBeFound("amortizationType.specified=true");

        // Get all the productList where amortizationType is null
        defaultProductShouldNotBeFound("amortizationType.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByAmortizationTypeContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where amortizationType contains DEFAULT_AMORTIZATION_TYPE
        defaultProductShouldBeFound("amortizationType.contains=" + DEFAULT_AMORTIZATION_TYPE);

        // Get all the productList where amortizationType contains UPDATED_AMORTIZATION_TYPE
        defaultProductShouldNotBeFound("amortizationType.contains=" + UPDATED_AMORTIZATION_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsByAmortizationTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where amortizationType does not contain DEFAULT_AMORTIZATION_TYPE
        defaultProductShouldNotBeFound("amortizationType.doesNotContain=" + DEFAULT_AMORTIZATION_TYPE);

        // Get all the productList where amortizationType does not contain UPDATED_AMORTIZATION_TYPE
        defaultProductShouldBeFound("amortizationType.doesNotContain=" + UPDATED_AMORTIZATION_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsByCompoundingFreqIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where compoundingFreq equals to DEFAULT_COMPOUNDING_FREQ
        defaultProductShouldBeFound("compoundingFreq.equals=" + DEFAULT_COMPOUNDING_FREQ);

        // Get all the productList where compoundingFreq equals to UPDATED_COMPOUNDING_FREQ
        defaultProductShouldNotBeFound("compoundingFreq.equals=" + UPDATED_COMPOUNDING_FREQ);
    }

    @Test
    @Transactional
    void getAllProductsByCompoundingFreqIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where compoundingFreq in DEFAULT_COMPOUNDING_FREQ or UPDATED_COMPOUNDING_FREQ
        defaultProductShouldBeFound("compoundingFreq.in=" + DEFAULT_COMPOUNDING_FREQ + "," + UPDATED_COMPOUNDING_FREQ);

        // Get all the productList where compoundingFreq equals to UPDATED_COMPOUNDING_FREQ
        defaultProductShouldNotBeFound("compoundingFreq.in=" + UPDATED_COMPOUNDING_FREQ);
    }

    @Test
    @Transactional
    void getAllProductsByCompoundingFreqIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where compoundingFreq is not null
        defaultProductShouldBeFound("compoundingFreq.specified=true");

        // Get all the productList where compoundingFreq is null
        defaultProductShouldNotBeFound("compoundingFreq.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByCompoundingFreqContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where compoundingFreq contains DEFAULT_COMPOUNDING_FREQ
        defaultProductShouldBeFound("compoundingFreq.contains=" + DEFAULT_COMPOUNDING_FREQ);

        // Get all the productList where compoundingFreq contains UPDATED_COMPOUNDING_FREQ
        defaultProductShouldNotBeFound("compoundingFreq.contains=" + UPDATED_COMPOUNDING_FREQ);
    }

    @Test
    @Transactional
    void getAllProductsByCompoundingFreqNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where compoundingFreq does not contain DEFAULT_COMPOUNDING_FREQ
        defaultProductShouldNotBeFound("compoundingFreq.doesNotContain=" + DEFAULT_COMPOUNDING_FREQ);

        // Get all the productList where compoundingFreq does not contain UPDATED_COMPOUNDING_FREQ
        defaultProductShouldBeFound("compoundingFreq.doesNotContain=" + UPDATED_COMPOUNDING_FREQ);
    }

    @Test
    @Transactional
    void getAllProductsByEmiRoundingIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where emiRounding equals to DEFAULT_EMI_ROUNDING
        defaultProductShouldBeFound("emiRounding.equals=" + DEFAULT_EMI_ROUNDING);

        // Get all the productList where emiRounding equals to UPDATED_EMI_ROUNDING
        defaultProductShouldNotBeFound("emiRounding.equals=" + UPDATED_EMI_ROUNDING);
    }

    @Test
    @Transactional
    void getAllProductsByEmiRoundingIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where emiRounding in DEFAULT_EMI_ROUNDING or UPDATED_EMI_ROUNDING
        defaultProductShouldBeFound("emiRounding.in=" + DEFAULT_EMI_ROUNDING + "," + UPDATED_EMI_ROUNDING);

        // Get all the productList where emiRounding equals to UPDATED_EMI_ROUNDING
        defaultProductShouldNotBeFound("emiRounding.in=" + UPDATED_EMI_ROUNDING);
    }

    @Test
    @Transactional
    void getAllProductsByEmiRoundingIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where emiRounding is not null
        defaultProductShouldBeFound("emiRounding.specified=true");

        // Get all the productList where emiRounding is null
        defaultProductShouldNotBeFound("emiRounding.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByEmiRoundingContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where emiRounding contains DEFAULT_EMI_ROUNDING
        defaultProductShouldBeFound("emiRounding.contains=" + DEFAULT_EMI_ROUNDING);

        // Get all the productList where emiRounding contains UPDATED_EMI_ROUNDING
        defaultProductShouldNotBeFound("emiRounding.contains=" + UPDATED_EMI_ROUNDING);
    }

    @Test
    @Transactional
    void getAllProductsByEmiRoundingNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where emiRounding does not contain DEFAULT_EMI_ROUNDING
        defaultProductShouldNotBeFound("emiRounding.doesNotContain=" + DEFAULT_EMI_ROUNDING);

        // Get all the productList where emiRounding does not contain UPDATED_EMI_ROUNDING
        defaultProductShouldBeFound("emiRounding.doesNotContain=" + UPDATED_EMI_ROUNDING);
    }

    @Test
    @Transactional
    void getAllProductsByLastRowEMIThresholdIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastRowEMIThreshold equals to DEFAULT_LAST_ROW_EMI_THRESHOLD
        defaultProductShouldBeFound("lastRowEMIThreshold.equals=" + DEFAULT_LAST_ROW_EMI_THRESHOLD);

        // Get all the productList where lastRowEMIThreshold equals to UPDATED_LAST_ROW_EMI_THRESHOLD
        defaultProductShouldNotBeFound("lastRowEMIThreshold.equals=" + UPDATED_LAST_ROW_EMI_THRESHOLD);
    }

    @Test
    @Transactional
    void getAllProductsByLastRowEMIThresholdIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastRowEMIThreshold in DEFAULT_LAST_ROW_EMI_THRESHOLD or UPDATED_LAST_ROW_EMI_THRESHOLD
        defaultProductShouldBeFound("lastRowEMIThreshold.in=" + DEFAULT_LAST_ROW_EMI_THRESHOLD + "," + UPDATED_LAST_ROW_EMI_THRESHOLD);

        // Get all the productList where lastRowEMIThreshold equals to UPDATED_LAST_ROW_EMI_THRESHOLD
        defaultProductShouldNotBeFound("lastRowEMIThreshold.in=" + UPDATED_LAST_ROW_EMI_THRESHOLD);
    }

    @Test
    @Transactional
    void getAllProductsByLastRowEMIThresholdIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastRowEMIThreshold is not null
        defaultProductShouldBeFound("lastRowEMIThreshold.specified=true");

        // Get all the productList where lastRowEMIThreshold is null
        defaultProductShouldNotBeFound("lastRowEMIThreshold.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByLastRowEMIThresholdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastRowEMIThreshold is greater than or equal to DEFAULT_LAST_ROW_EMI_THRESHOLD
        defaultProductShouldBeFound("lastRowEMIThreshold.greaterThanOrEqual=" + DEFAULT_LAST_ROW_EMI_THRESHOLD);

        // Get all the productList where lastRowEMIThreshold is greater than or equal to UPDATED_LAST_ROW_EMI_THRESHOLD
        defaultProductShouldNotBeFound("lastRowEMIThreshold.greaterThanOrEqual=" + UPDATED_LAST_ROW_EMI_THRESHOLD);
    }

    @Test
    @Transactional
    void getAllProductsByLastRowEMIThresholdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastRowEMIThreshold is less than or equal to DEFAULT_LAST_ROW_EMI_THRESHOLD
        defaultProductShouldBeFound("lastRowEMIThreshold.lessThanOrEqual=" + DEFAULT_LAST_ROW_EMI_THRESHOLD);

        // Get all the productList where lastRowEMIThreshold is less than or equal to SMALLER_LAST_ROW_EMI_THRESHOLD
        defaultProductShouldNotBeFound("lastRowEMIThreshold.lessThanOrEqual=" + SMALLER_LAST_ROW_EMI_THRESHOLD);
    }

    @Test
    @Transactional
    void getAllProductsByLastRowEMIThresholdIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastRowEMIThreshold is less than DEFAULT_LAST_ROW_EMI_THRESHOLD
        defaultProductShouldNotBeFound("lastRowEMIThreshold.lessThan=" + DEFAULT_LAST_ROW_EMI_THRESHOLD);

        // Get all the productList where lastRowEMIThreshold is less than UPDATED_LAST_ROW_EMI_THRESHOLD
        defaultProductShouldBeFound("lastRowEMIThreshold.lessThan=" + UPDATED_LAST_ROW_EMI_THRESHOLD);
    }

    @Test
    @Transactional
    void getAllProductsByLastRowEMIThresholdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastRowEMIThreshold is greater than DEFAULT_LAST_ROW_EMI_THRESHOLD
        defaultProductShouldNotBeFound("lastRowEMIThreshold.greaterThan=" + DEFAULT_LAST_ROW_EMI_THRESHOLD);

        // Get all the productList where lastRowEMIThreshold is greater than SMALLER_LAST_ROW_EMI_THRESHOLD
        defaultProductShouldBeFound("lastRowEMIThreshold.greaterThan=" + SMALLER_LAST_ROW_EMI_THRESHOLD);
    }

    @Test
    @Transactional
    void getAllProductsByGraceDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where graceDays equals to DEFAULT_GRACE_DAYS
        defaultProductShouldBeFound("graceDays.equals=" + DEFAULT_GRACE_DAYS);

        // Get all the productList where graceDays equals to UPDATED_GRACE_DAYS
        defaultProductShouldNotBeFound("graceDays.equals=" + UPDATED_GRACE_DAYS);
    }

    @Test
    @Transactional
    void getAllProductsByGraceDaysIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where graceDays in DEFAULT_GRACE_DAYS or UPDATED_GRACE_DAYS
        defaultProductShouldBeFound("graceDays.in=" + DEFAULT_GRACE_DAYS + "," + UPDATED_GRACE_DAYS);

        // Get all the productList where graceDays equals to UPDATED_GRACE_DAYS
        defaultProductShouldNotBeFound("graceDays.in=" + UPDATED_GRACE_DAYS);
    }

    @Test
    @Transactional
    void getAllProductsByGraceDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where graceDays is not null
        defaultProductShouldBeFound("graceDays.specified=true");

        // Get all the productList where graceDays is null
        defaultProductShouldNotBeFound("graceDays.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByGraceDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where graceDays is greater than or equal to DEFAULT_GRACE_DAYS
        defaultProductShouldBeFound("graceDays.greaterThanOrEqual=" + DEFAULT_GRACE_DAYS);

        // Get all the productList where graceDays is greater than or equal to UPDATED_GRACE_DAYS
        defaultProductShouldNotBeFound("graceDays.greaterThanOrEqual=" + UPDATED_GRACE_DAYS);
    }

    @Test
    @Transactional
    void getAllProductsByGraceDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where graceDays is less than or equal to DEFAULT_GRACE_DAYS
        defaultProductShouldBeFound("graceDays.lessThanOrEqual=" + DEFAULT_GRACE_DAYS);

        // Get all the productList where graceDays is less than or equal to SMALLER_GRACE_DAYS
        defaultProductShouldNotBeFound("graceDays.lessThanOrEqual=" + SMALLER_GRACE_DAYS);
    }

    @Test
    @Transactional
    void getAllProductsByGraceDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where graceDays is less than DEFAULT_GRACE_DAYS
        defaultProductShouldNotBeFound("graceDays.lessThan=" + DEFAULT_GRACE_DAYS);

        // Get all the productList where graceDays is less than UPDATED_GRACE_DAYS
        defaultProductShouldBeFound("graceDays.lessThan=" + UPDATED_GRACE_DAYS);
    }

    @Test
    @Transactional
    void getAllProductsByGraceDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where graceDays is greater than DEFAULT_GRACE_DAYS
        defaultProductShouldNotBeFound("graceDays.greaterThan=" + DEFAULT_GRACE_DAYS);

        // Get all the productList where graceDays is greater than SMALLER_GRACE_DAYS
        defaultProductShouldBeFound("graceDays.greaterThan=" + SMALLER_GRACE_DAYS);
    }

    @Test
    @Transactional
    void getAllProductsByReschLockinPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reschLockinPeriod equals to DEFAULT_RESCH_LOCKIN_PERIOD
        defaultProductShouldBeFound("reschLockinPeriod.equals=" + DEFAULT_RESCH_LOCKIN_PERIOD);

        // Get all the productList where reschLockinPeriod equals to UPDATED_RESCH_LOCKIN_PERIOD
        defaultProductShouldNotBeFound("reschLockinPeriod.equals=" + UPDATED_RESCH_LOCKIN_PERIOD);
    }

    @Test
    @Transactional
    void getAllProductsByReschLockinPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reschLockinPeriod in DEFAULT_RESCH_LOCKIN_PERIOD or UPDATED_RESCH_LOCKIN_PERIOD
        defaultProductShouldBeFound("reschLockinPeriod.in=" + DEFAULT_RESCH_LOCKIN_PERIOD + "," + UPDATED_RESCH_LOCKIN_PERIOD);

        // Get all the productList where reschLockinPeriod equals to UPDATED_RESCH_LOCKIN_PERIOD
        defaultProductShouldNotBeFound("reschLockinPeriod.in=" + UPDATED_RESCH_LOCKIN_PERIOD);
    }

    @Test
    @Transactional
    void getAllProductsByReschLockinPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reschLockinPeriod is not null
        defaultProductShouldBeFound("reschLockinPeriod.specified=true");

        // Get all the productList where reschLockinPeriod is null
        defaultProductShouldNotBeFound("reschLockinPeriod.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByReschLockinPeriodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reschLockinPeriod is greater than or equal to DEFAULT_RESCH_LOCKIN_PERIOD
        defaultProductShouldBeFound("reschLockinPeriod.greaterThanOrEqual=" + DEFAULT_RESCH_LOCKIN_PERIOD);

        // Get all the productList where reschLockinPeriod is greater than or equal to UPDATED_RESCH_LOCKIN_PERIOD
        defaultProductShouldNotBeFound("reschLockinPeriod.greaterThanOrEqual=" + UPDATED_RESCH_LOCKIN_PERIOD);
    }

    @Test
    @Transactional
    void getAllProductsByReschLockinPeriodIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reschLockinPeriod is less than or equal to DEFAULT_RESCH_LOCKIN_PERIOD
        defaultProductShouldBeFound("reschLockinPeriod.lessThanOrEqual=" + DEFAULT_RESCH_LOCKIN_PERIOD);

        // Get all the productList where reschLockinPeriod is less than or equal to SMALLER_RESCH_LOCKIN_PERIOD
        defaultProductShouldNotBeFound("reschLockinPeriod.lessThanOrEqual=" + SMALLER_RESCH_LOCKIN_PERIOD);
    }

    @Test
    @Transactional
    void getAllProductsByReschLockinPeriodIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reschLockinPeriod is less than DEFAULT_RESCH_LOCKIN_PERIOD
        defaultProductShouldNotBeFound("reschLockinPeriod.lessThan=" + DEFAULT_RESCH_LOCKIN_PERIOD);

        // Get all the productList where reschLockinPeriod is less than UPDATED_RESCH_LOCKIN_PERIOD
        defaultProductShouldBeFound("reschLockinPeriod.lessThan=" + UPDATED_RESCH_LOCKIN_PERIOD);
    }

    @Test
    @Transactional
    void getAllProductsByReschLockinPeriodIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reschLockinPeriod is greater than DEFAULT_RESCH_LOCKIN_PERIOD
        defaultProductShouldNotBeFound("reschLockinPeriod.greaterThan=" + DEFAULT_RESCH_LOCKIN_PERIOD);

        // Get all the productList where reschLockinPeriod is greater than SMALLER_RESCH_LOCKIN_PERIOD
        defaultProductShouldBeFound("reschLockinPeriod.greaterThan=" + SMALLER_RESCH_LOCKIN_PERIOD);
    }

    @Test
    @Transactional
    void getAllProductsByPrepayAfterInstNoIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepayAfterInstNo equals to DEFAULT_PREPAY_AFTER_INST_NO
        defaultProductShouldBeFound("prepayAfterInstNo.equals=" + DEFAULT_PREPAY_AFTER_INST_NO);

        // Get all the productList where prepayAfterInstNo equals to UPDATED_PREPAY_AFTER_INST_NO
        defaultProductShouldNotBeFound("prepayAfterInstNo.equals=" + UPDATED_PREPAY_AFTER_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByPrepayAfterInstNoIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepayAfterInstNo in DEFAULT_PREPAY_AFTER_INST_NO or UPDATED_PREPAY_AFTER_INST_NO
        defaultProductShouldBeFound("prepayAfterInstNo.in=" + DEFAULT_PREPAY_AFTER_INST_NO + "," + UPDATED_PREPAY_AFTER_INST_NO);

        // Get all the productList where prepayAfterInstNo equals to UPDATED_PREPAY_AFTER_INST_NO
        defaultProductShouldNotBeFound("prepayAfterInstNo.in=" + UPDATED_PREPAY_AFTER_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByPrepayAfterInstNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepayAfterInstNo is not null
        defaultProductShouldBeFound("prepayAfterInstNo.specified=true");

        // Get all the productList where prepayAfterInstNo is null
        defaultProductShouldNotBeFound("prepayAfterInstNo.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByPrepayAfterInstNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepayAfterInstNo is greater than or equal to DEFAULT_PREPAY_AFTER_INST_NO
        defaultProductShouldBeFound("prepayAfterInstNo.greaterThanOrEqual=" + DEFAULT_PREPAY_AFTER_INST_NO);

        // Get all the productList where prepayAfterInstNo is greater than or equal to UPDATED_PREPAY_AFTER_INST_NO
        defaultProductShouldNotBeFound("prepayAfterInstNo.greaterThanOrEqual=" + UPDATED_PREPAY_AFTER_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByPrepayAfterInstNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepayAfterInstNo is less than or equal to DEFAULT_PREPAY_AFTER_INST_NO
        defaultProductShouldBeFound("prepayAfterInstNo.lessThanOrEqual=" + DEFAULT_PREPAY_AFTER_INST_NO);

        // Get all the productList where prepayAfterInstNo is less than or equal to SMALLER_PREPAY_AFTER_INST_NO
        defaultProductShouldNotBeFound("prepayAfterInstNo.lessThanOrEqual=" + SMALLER_PREPAY_AFTER_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByPrepayAfterInstNoIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepayAfterInstNo is less than DEFAULT_PREPAY_AFTER_INST_NO
        defaultProductShouldNotBeFound("prepayAfterInstNo.lessThan=" + DEFAULT_PREPAY_AFTER_INST_NO);

        // Get all the productList where prepayAfterInstNo is less than UPDATED_PREPAY_AFTER_INST_NO
        defaultProductShouldBeFound("prepayAfterInstNo.lessThan=" + UPDATED_PREPAY_AFTER_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByPrepayAfterInstNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepayAfterInstNo is greater than DEFAULT_PREPAY_AFTER_INST_NO
        defaultProductShouldNotBeFound("prepayAfterInstNo.greaterThan=" + DEFAULT_PREPAY_AFTER_INST_NO);

        // Get all the productList where prepayAfterInstNo is greater than SMALLER_PREPAY_AFTER_INST_NO
        defaultProductShouldBeFound("prepayAfterInstNo.greaterThan=" + SMALLER_PREPAY_AFTER_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByPrepayBeforeInstNoIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepayBeforeInstNo equals to DEFAULT_PREPAY_BEFORE_INST_NO
        defaultProductShouldBeFound("prepayBeforeInstNo.equals=" + DEFAULT_PREPAY_BEFORE_INST_NO);

        // Get all the productList where prepayBeforeInstNo equals to UPDATED_PREPAY_BEFORE_INST_NO
        defaultProductShouldNotBeFound("prepayBeforeInstNo.equals=" + UPDATED_PREPAY_BEFORE_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByPrepayBeforeInstNoIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepayBeforeInstNo in DEFAULT_PREPAY_BEFORE_INST_NO or UPDATED_PREPAY_BEFORE_INST_NO
        defaultProductShouldBeFound("prepayBeforeInstNo.in=" + DEFAULT_PREPAY_BEFORE_INST_NO + "," + UPDATED_PREPAY_BEFORE_INST_NO);

        // Get all the productList where prepayBeforeInstNo equals to UPDATED_PREPAY_BEFORE_INST_NO
        defaultProductShouldNotBeFound("prepayBeforeInstNo.in=" + UPDATED_PREPAY_BEFORE_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByPrepayBeforeInstNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepayBeforeInstNo is not null
        defaultProductShouldBeFound("prepayBeforeInstNo.specified=true");

        // Get all the productList where prepayBeforeInstNo is null
        defaultProductShouldNotBeFound("prepayBeforeInstNo.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByPrepayBeforeInstNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepayBeforeInstNo is greater than or equal to DEFAULT_PREPAY_BEFORE_INST_NO
        defaultProductShouldBeFound("prepayBeforeInstNo.greaterThanOrEqual=" + DEFAULT_PREPAY_BEFORE_INST_NO);

        // Get all the productList where prepayBeforeInstNo is greater than or equal to UPDATED_PREPAY_BEFORE_INST_NO
        defaultProductShouldNotBeFound("prepayBeforeInstNo.greaterThanOrEqual=" + UPDATED_PREPAY_BEFORE_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByPrepayBeforeInstNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepayBeforeInstNo is less than or equal to DEFAULT_PREPAY_BEFORE_INST_NO
        defaultProductShouldBeFound("prepayBeforeInstNo.lessThanOrEqual=" + DEFAULT_PREPAY_BEFORE_INST_NO);

        // Get all the productList where prepayBeforeInstNo is less than or equal to SMALLER_PREPAY_BEFORE_INST_NO
        defaultProductShouldNotBeFound("prepayBeforeInstNo.lessThanOrEqual=" + SMALLER_PREPAY_BEFORE_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByPrepayBeforeInstNoIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepayBeforeInstNo is less than DEFAULT_PREPAY_BEFORE_INST_NO
        defaultProductShouldNotBeFound("prepayBeforeInstNo.lessThan=" + DEFAULT_PREPAY_BEFORE_INST_NO);

        // Get all the productList where prepayBeforeInstNo is less than UPDATED_PREPAY_BEFORE_INST_NO
        defaultProductShouldBeFound("prepayBeforeInstNo.lessThan=" + UPDATED_PREPAY_BEFORE_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByPrepayBeforeInstNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepayBeforeInstNo is greater than DEFAULT_PREPAY_BEFORE_INST_NO
        defaultProductShouldNotBeFound("prepayBeforeInstNo.greaterThan=" + DEFAULT_PREPAY_BEFORE_INST_NO);

        // Get all the productList where prepayBeforeInstNo is greater than SMALLER_PREPAY_BEFORE_INST_NO
        defaultProductShouldBeFound("prepayBeforeInstNo.greaterThan=" + SMALLER_PREPAY_BEFORE_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByMinInstallmentGapBetPrepayIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minInstallmentGapBetPrepay equals to DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY
        defaultProductShouldBeFound("minInstallmentGapBetPrepay.equals=" + DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY);

        // Get all the productList where minInstallmentGapBetPrepay equals to UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY
        defaultProductShouldNotBeFound("minInstallmentGapBetPrepay.equals=" + UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY);
    }

    @Test
    @Transactional
    void getAllProductsByMinInstallmentGapBetPrepayIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minInstallmentGapBetPrepay in DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY or UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY
        defaultProductShouldBeFound(
            "minInstallmentGapBetPrepay.in=" + DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY + "," + UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY
        );

        // Get all the productList where minInstallmentGapBetPrepay equals to UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY
        defaultProductShouldNotBeFound("minInstallmentGapBetPrepay.in=" + UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY);
    }

    @Test
    @Transactional
    void getAllProductsByMinInstallmentGapBetPrepayIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minInstallmentGapBetPrepay is not null
        defaultProductShouldBeFound("minInstallmentGapBetPrepay.specified=true");

        // Get all the productList where minInstallmentGapBetPrepay is null
        defaultProductShouldNotBeFound("minInstallmentGapBetPrepay.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByMinInstallmentGapBetPrepayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minInstallmentGapBetPrepay is greater than or equal to DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY
        defaultProductShouldBeFound("minInstallmentGapBetPrepay.greaterThanOrEqual=" + DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY);

        // Get all the productList where minInstallmentGapBetPrepay is greater than or equal to UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY
        defaultProductShouldNotBeFound("minInstallmentGapBetPrepay.greaterThanOrEqual=" + UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY);
    }

    @Test
    @Transactional
    void getAllProductsByMinInstallmentGapBetPrepayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minInstallmentGapBetPrepay is less than or equal to DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY
        defaultProductShouldBeFound("minInstallmentGapBetPrepay.lessThanOrEqual=" + DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY);

        // Get all the productList where minInstallmentGapBetPrepay is less than or equal to SMALLER_MIN_INSTALLMENT_GAP_BET_PREPAY
        defaultProductShouldNotBeFound("minInstallmentGapBetPrepay.lessThanOrEqual=" + SMALLER_MIN_INSTALLMENT_GAP_BET_PREPAY);
    }

    @Test
    @Transactional
    void getAllProductsByMinInstallmentGapBetPrepayIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minInstallmentGapBetPrepay is less than DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY
        defaultProductShouldNotBeFound("minInstallmentGapBetPrepay.lessThan=" + DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY);

        // Get all the productList where minInstallmentGapBetPrepay is less than UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY
        defaultProductShouldBeFound("minInstallmentGapBetPrepay.lessThan=" + UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY);
    }

    @Test
    @Transactional
    void getAllProductsByMinInstallmentGapBetPrepayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minInstallmentGapBetPrepay is greater than DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY
        defaultProductShouldNotBeFound("minInstallmentGapBetPrepay.greaterThan=" + DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY);

        // Get all the productList where minInstallmentGapBetPrepay is greater than SMALLER_MIN_INSTALLMENT_GAP_BET_PREPAY
        defaultProductShouldBeFound("minInstallmentGapBetPrepay.greaterThan=" + SMALLER_MIN_INSTALLMENT_GAP_BET_PREPAY);
    }

    @Test
    @Transactional
    void getAllProductsByMinPrepayAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minPrepayAmount equals to DEFAULT_MIN_PREPAY_AMOUNT
        defaultProductShouldBeFound("minPrepayAmount.equals=" + DEFAULT_MIN_PREPAY_AMOUNT);

        // Get all the productList where minPrepayAmount equals to UPDATED_MIN_PREPAY_AMOUNT
        defaultProductShouldNotBeFound("minPrepayAmount.equals=" + UPDATED_MIN_PREPAY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinPrepayAmountIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minPrepayAmount in DEFAULT_MIN_PREPAY_AMOUNT or UPDATED_MIN_PREPAY_AMOUNT
        defaultProductShouldBeFound("minPrepayAmount.in=" + DEFAULT_MIN_PREPAY_AMOUNT + "," + UPDATED_MIN_PREPAY_AMOUNT);

        // Get all the productList where minPrepayAmount equals to UPDATED_MIN_PREPAY_AMOUNT
        defaultProductShouldNotBeFound("minPrepayAmount.in=" + UPDATED_MIN_PREPAY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinPrepayAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minPrepayAmount is not null
        defaultProductShouldBeFound("minPrepayAmount.specified=true");

        // Get all the productList where minPrepayAmount is null
        defaultProductShouldNotBeFound("minPrepayAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByMinPrepayAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minPrepayAmount is greater than or equal to DEFAULT_MIN_PREPAY_AMOUNT
        defaultProductShouldBeFound("minPrepayAmount.greaterThanOrEqual=" + DEFAULT_MIN_PREPAY_AMOUNT);

        // Get all the productList where minPrepayAmount is greater than or equal to UPDATED_MIN_PREPAY_AMOUNT
        defaultProductShouldNotBeFound("minPrepayAmount.greaterThanOrEqual=" + UPDATED_MIN_PREPAY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinPrepayAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minPrepayAmount is less than or equal to DEFAULT_MIN_PREPAY_AMOUNT
        defaultProductShouldBeFound("minPrepayAmount.lessThanOrEqual=" + DEFAULT_MIN_PREPAY_AMOUNT);

        // Get all the productList where minPrepayAmount is less than or equal to SMALLER_MIN_PREPAY_AMOUNT
        defaultProductShouldNotBeFound("minPrepayAmount.lessThanOrEqual=" + SMALLER_MIN_PREPAY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinPrepayAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minPrepayAmount is less than DEFAULT_MIN_PREPAY_AMOUNT
        defaultProductShouldNotBeFound("minPrepayAmount.lessThan=" + DEFAULT_MIN_PREPAY_AMOUNT);

        // Get all the productList where minPrepayAmount is less than UPDATED_MIN_PREPAY_AMOUNT
        defaultProductShouldBeFound("minPrepayAmount.lessThan=" + UPDATED_MIN_PREPAY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinPrepayAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minPrepayAmount is greater than DEFAULT_MIN_PREPAY_AMOUNT
        defaultProductShouldNotBeFound("minPrepayAmount.greaterThan=" + DEFAULT_MIN_PREPAY_AMOUNT);

        // Get all the productList where minPrepayAmount is greater than SMALLER_MIN_PREPAY_AMOUNT
        defaultProductShouldBeFound("minPrepayAmount.greaterThan=" + SMALLER_MIN_PREPAY_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByForecloseAfterInstNoIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where forecloseAfterInstNo equals to DEFAULT_FORECLOSE_AFTER_INST_NO
        defaultProductShouldBeFound("forecloseAfterInstNo.equals=" + DEFAULT_FORECLOSE_AFTER_INST_NO);

        // Get all the productList where forecloseAfterInstNo equals to UPDATED_FORECLOSE_AFTER_INST_NO
        defaultProductShouldNotBeFound("forecloseAfterInstNo.equals=" + UPDATED_FORECLOSE_AFTER_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByForecloseAfterInstNoIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where forecloseAfterInstNo in DEFAULT_FORECLOSE_AFTER_INST_NO or UPDATED_FORECLOSE_AFTER_INST_NO
        defaultProductShouldBeFound("forecloseAfterInstNo.in=" + DEFAULT_FORECLOSE_AFTER_INST_NO + "," + UPDATED_FORECLOSE_AFTER_INST_NO);

        // Get all the productList where forecloseAfterInstNo equals to UPDATED_FORECLOSE_AFTER_INST_NO
        defaultProductShouldNotBeFound("forecloseAfterInstNo.in=" + UPDATED_FORECLOSE_AFTER_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByForecloseAfterInstNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where forecloseAfterInstNo is not null
        defaultProductShouldBeFound("forecloseAfterInstNo.specified=true");

        // Get all the productList where forecloseAfterInstNo is null
        defaultProductShouldNotBeFound("forecloseAfterInstNo.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByForecloseAfterInstNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where forecloseAfterInstNo is greater than or equal to DEFAULT_FORECLOSE_AFTER_INST_NO
        defaultProductShouldBeFound("forecloseAfterInstNo.greaterThanOrEqual=" + DEFAULT_FORECLOSE_AFTER_INST_NO);

        // Get all the productList where forecloseAfterInstNo is greater than or equal to UPDATED_FORECLOSE_AFTER_INST_NO
        defaultProductShouldNotBeFound("forecloseAfterInstNo.greaterThanOrEqual=" + UPDATED_FORECLOSE_AFTER_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByForecloseAfterInstNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where forecloseAfterInstNo is less than or equal to DEFAULT_FORECLOSE_AFTER_INST_NO
        defaultProductShouldBeFound("forecloseAfterInstNo.lessThanOrEqual=" + DEFAULT_FORECLOSE_AFTER_INST_NO);

        // Get all the productList where forecloseAfterInstNo is less than or equal to SMALLER_FORECLOSE_AFTER_INST_NO
        defaultProductShouldNotBeFound("forecloseAfterInstNo.lessThanOrEqual=" + SMALLER_FORECLOSE_AFTER_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByForecloseAfterInstNoIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where forecloseAfterInstNo is less than DEFAULT_FORECLOSE_AFTER_INST_NO
        defaultProductShouldNotBeFound("forecloseAfterInstNo.lessThan=" + DEFAULT_FORECLOSE_AFTER_INST_NO);

        // Get all the productList where forecloseAfterInstNo is less than UPDATED_FORECLOSE_AFTER_INST_NO
        defaultProductShouldBeFound("forecloseAfterInstNo.lessThan=" + UPDATED_FORECLOSE_AFTER_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByForecloseAfterInstNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where forecloseAfterInstNo is greater than DEFAULT_FORECLOSE_AFTER_INST_NO
        defaultProductShouldNotBeFound("forecloseAfterInstNo.greaterThan=" + DEFAULT_FORECLOSE_AFTER_INST_NO);

        // Get all the productList where forecloseAfterInstNo is greater than SMALLER_FORECLOSE_AFTER_INST_NO
        defaultProductShouldBeFound("forecloseAfterInstNo.greaterThan=" + SMALLER_FORECLOSE_AFTER_INST_NO);
    }

    @Test
    @Transactional
    void getAllProductsByForecloseBeforeInstaNoIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where forecloseBeforeInstaNo equals to DEFAULT_FORECLOSE_BEFORE_INSTA_NO
        defaultProductShouldBeFound("forecloseBeforeInstaNo.equals=" + DEFAULT_FORECLOSE_BEFORE_INSTA_NO);

        // Get all the productList where forecloseBeforeInstaNo equals to UPDATED_FORECLOSE_BEFORE_INSTA_NO
        defaultProductShouldNotBeFound("forecloseBeforeInstaNo.equals=" + UPDATED_FORECLOSE_BEFORE_INSTA_NO);
    }

    @Test
    @Transactional
    void getAllProductsByForecloseBeforeInstaNoIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where forecloseBeforeInstaNo in DEFAULT_FORECLOSE_BEFORE_INSTA_NO or UPDATED_FORECLOSE_BEFORE_INSTA_NO
        defaultProductShouldBeFound(
            "forecloseBeforeInstaNo.in=" + DEFAULT_FORECLOSE_BEFORE_INSTA_NO + "," + UPDATED_FORECLOSE_BEFORE_INSTA_NO
        );

        // Get all the productList where forecloseBeforeInstaNo equals to UPDATED_FORECLOSE_BEFORE_INSTA_NO
        defaultProductShouldNotBeFound("forecloseBeforeInstaNo.in=" + UPDATED_FORECLOSE_BEFORE_INSTA_NO);
    }

    @Test
    @Transactional
    void getAllProductsByForecloseBeforeInstaNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where forecloseBeforeInstaNo is not null
        defaultProductShouldBeFound("forecloseBeforeInstaNo.specified=true");

        // Get all the productList where forecloseBeforeInstaNo is null
        defaultProductShouldNotBeFound("forecloseBeforeInstaNo.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByForecloseBeforeInstaNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where forecloseBeforeInstaNo is greater than or equal to DEFAULT_FORECLOSE_BEFORE_INSTA_NO
        defaultProductShouldBeFound("forecloseBeforeInstaNo.greaterThanOrEqual=" + DEFAULT_FORECLOSE_BEFORE_INSTA_NO);

        // Get all the productList where forecloseBeforeInstaNo is greater than or equal to UPDATED_FORECLOSE_BEFORE_INSTA_NO
        defaultProductShouldNotBeFound("forecloseBeforeInstaNo.greaterThanOrEqual=" + UPDATED_FORECLOSE_BEFORE_INSTA_NO);
    }

    @Test
    @Transactional
    void getAllProductsByForecloseBeforeInstaNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where forecloseBeforeInstaNo is less than or equal to DEFAULT_FORECLOSE_BEFORE_INSTA_NO
        defaultProductShouldBeFound("forecloseBeforeInstaNo.lessThanOrEqual=" + DEFAULT_FORECLOSE_BEFORE_INSTA_NO);

        // Get all the productList where forecloseBeforeInstaNo is less than or equal to SMALLER_FORECLOSE_BEFORE_INSTA_NO
        defaultProductShouldNotBeFound("forecloseBeforeInstaNo.lessThanOrEqual=" + SMALLER_FORECLOSE_BEFORE_INSTA_NO);
    }

    @Test
    @Transactional
    void getAllProductsByForecloseBeforeInstaNoIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where forecloseBeforeInstaNo is less than DEFAULT_FORECLOSE_BEFORE_INSTA_NO
        defaultProductShouldNotBeFound("forecloseBeforeInstaNo.lessThan=" + DEFAULT_FORECLOSE_BEFORE_INSTA_NO);

        // Get all the productList where forecloseBeforeInstaNo is less than UPDATED_FORECLOSE_BEFORE_INSTA_NO
        defaultProductShouldBeFound("forecloseBeforeInstaNo.lessThan=" + UPDATED_FORECLOSE_BEFORE_INSTA_NO);
    }

    @Test
    @Transactional
    void getAllProductsByForecloseBeforeInstaNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where forecloseBeforeInstaNo is greater than DEFAULT_FORECLOSE_BEFORE_INSTA_NO
        defaultProductShouldNotBeFound("forecloseBeforeInstaNo.greaterThan=" + DEFAULT_FORECLOSE_BEFORE_INSTA_NO);

        // Get all the productList where forecloseBeforeInstaNo is greater than SMALLER_FORECLOSE_BEFORE_INSTA_NO
        defaultProductShouldBeFound("forecloseBeforeInstaNo.greaterThan=" + SMALLER_FORECLOSE_BEFORE_INSTA_NO);
    }

    @Test
    @Transactional
    void getAllProductsByMinTenorIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minTenor equals to DEFAULT_MIN_TENOR
        defaultProductShouldBeFound("minTenor.equals=" + DEFAULT_MIN_TENOR);

        // Get all the productList where minTenor equals to UPDATED_MIN_TENOR
        defaultProductShouldNotBeFound("minTenor.equals=" + UPDATED_MIN_TENOR);
    }

    @Test
    @Transactional
    void getAllProductsByMinTenorIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minTenor in DEFAULT_MIN_TENOR or UPDATED_MIN_TENOR
        defaultProductShouldBeFound("minTenor.in=" + DEFAULT_MIN_TENOR + "," + UPDATED_MIN_TENOR);

        // Get all the productList where minTenor equals to UPDATED_MIN_TENOR
        defaultProductShouldNotBeFound("minTenor.in=" + UPDATED_MIN_TENOR);
    }

    @Test
    @Transactional
    void getAllProductsByMinTenorIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minTenor is not null
        defaultProductShouldBeFound("minTenor.specified=true");

        // Get all the productList where minTenor is null
        defaultProductShouldNotBeFound("minTenor.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByMinTenorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minTenor is greater than or equal to DEFAULT_MIN_TENOR
        defaultProductShouldBeFound("minTenor.greaterThanOrEqual=" + DEFAULT_MIN_TENOR);

        // Get all the productList where minTenor is greater than or equal to UPDATED_MIN_TENOR
        defaultProductShouldNotBeFound("minTenor.greaterThanOrEqual=" + UPDATED_MIN_TENOR);
    }

    @Test
    @Transactional
    void getAllProductsByMinTenorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minTenor is less than or equal to DEFAULT_MIN_TENOR
        defaultProductShouldBeFound("minTenor.lessThanOrEqual=" + DEFAULT_MIN_TENOR);

        // Get all the productList where minTenor is less than or equal to SMALLER_MIN_TENOR
        defaultProductShouldNotBeFound("minTenor.lessThanOrEqual=" + SMALLER_MIN_TENOR);
    }

    @Test
    @Transactional
    void getAllProductsByMinTenorIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minTenor is less than DEFAULT_MIN_TENOR
        defaultProductShouldNotBeFound("minTenor.lessThan=" + DEFAULT_MIN_TENOR);

        // Get all the productList where minTenor is less than UPDATED_MIN_TENOR
        defaultProductShouldBeFound("minTenor.lessThan=" + UPDATED_MIN_TENOR);
    }

    @Test
    @Transactional
    void getAllProductsByMinTenorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minTenor is greater than DEFAULT_MIN_TENOR
        defaultProductShouldNotBeFound("minTenor.greaterThan=" + DEFAULT_MIN_TENOR);

        // Get all the productList where minTenor is greater than SMALLER_MIN_TENOR
        defaultProductShouldBeFound("minTenor.greaterThan=" + SMALLER_MIN_TENOR);
    }

    @Test
    @Transactional
    void getAllProductsByMaxTenorIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where maxTenor equals to DEFAULT_MAX_TENOR
        defaultProductShouldBeFound("maxTenor.equals=" + DEFAULT_MAX_TENOR);

        // Get all the productList where maxTenor equals to UPDATED_MAX_TENOR
        defaultProductShouldNotBeFound("maxTenor.equals=" + UPDATED_MAX_TENOR);
    }

    @Test
    @Transactional
    void getAllProductsByMaxTenorIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where maxTenor in DEFAULT_MAX_TENOR or UPDATED_MAX_TENOR
        defaultProductShouldBeFound("maxTenor.in=" + DEFAULT_MAX_TENOR + "," + UPDATED_MAX_TENOR);

        // Get all the productList where maxTenor equals to UPDATED_MAX_TENOR
        defaultProductShouldNotBeFound("maxTenor.in=" + UPDATED_MAX_TENOR);
    }

    @Test
    @Transactional
    void getAllProductsByMaxTenorIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where maxTenor is not null
        defaultProductShouldBeFound("maxTenor.specified=true");

        // Get all the productList where maxTenor is null
        defaultProductShouldNotBeFound("maxTenor.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByMaxTenorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where maxTenor is greater than or equal to DEFAULT_MAX_TENOR
        defaultProductShouldBeFound("maxTenor.greaterThanOrEqual=" + DEFAULT_MAX_TENOR);

        // Get all the productList where maxTenor is greater than or equal to UPDATED_MAX_TENOR
        defaultProductShouldNotBeFound("maxTenor.greaterThanOrEqual=" + UPDATED_MAX_TENOR);
    }

    @Test
    @Transactional
    void getAllProductsByMaxTenorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where maxTenor is less than or equal to DEFAULT_MAX_TENOR
        defaultProductShouldBeFound("maxTenor.lessThanOrEqual=" + DEFAULT_MAX_TENOR);

        // Get all the productList where maxTenor is less than or equal to SMALLER_MAX_TENOR
        defaultProductShouldNotBeFound("maxTenor.lessThanOrEqual=" + SMALLER_MAX_TENOR);
    }

    @Test
    @Transactional
    void getAllProductsByMaxTenorIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where maxTenor is less than DEFAULT_MAX_TENOR
        defaultProductShouldNotBeFound("maxTenor.lessThan=" + DEFAULT_MAX_TENOR);

        // Get all the productList where maxTenor is less than UPDATED_MAX_TENOR
        defaultProductShouldBeFound("maxTenor.lessThan=" + UPDATED_MAX_TENOR);
    }

    @Test
    @Transactional
    void getAllProductsByMaxTenorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where maxTenor is greater than DEFAULT_MAX_TENOR
        defaultProductShouldNotBeFound("maxTenor.greaterThan=" + DEFAULT_MAX_TENOR);

        // Get all the productList where maxTenor is greater than SMALLER_MAX_TENOR
        defaultProductShouldBeFound("maxTenor.greaterThan=" + SMALLER_MAX_TENOR);
    }

    @Test
    @Transactional
    void getAllProductsByMinInstallmentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minInstallmentAmount equals to DEFAULT_MIN_INSTALLMENT_AMOUNT
        defaultProductShouldBeFound("minInstallmentAmount.equals=" + DEFAULT_MIN_INSTALLMENT_AMOUNT);

        // Get all the productList where minInstallmentAmount equals to UPDATED_MIN_INSTALLMENT_AMOUNT
        defaultProductShouldNotBeFound("minInstallmentAmount.equals=" + UPDATED_MIN_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinInstallmentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minInstallmentAmount in DEFAULT_MIN_INSTALLMENT_AMOUNT or UPDATED_MIN_INSTALLMENT_AMOUNT
        defaultProductShouldBeFound("minInstallmentAmount.in=" + DEFAULT_MIN_INSTALLMENT_AMOUNT + "," + UPDATED_MIN_INSTALLMENT_AMOUNT);

        // Get all the productList where minInstallmentAmount equals to UPDATED_MIN_INSTALLMENT_AMOUNT
        defaultProductShouldNotBeFound("minInstallmentAmount.in=" + UPDATED_MIN_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinInstallmentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minInstallmentAmount is not null
        defaultProductShouldBeFound("minInstallmentAmount.specified=true");

        // Get all the productList where minInstallmentAmount is null
        defaultProductShouldNotBeFound("minInstallmentAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByMinInstallmentAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minInstallmentAmount is greater than or equal to DEFAULT_MIN_INSTALLMENT_AMOUNT
        defaultProductShouldBeFound("minInstallmentAmount.greaterThanOrEqual=" + DEFAULT_MIN_INSTALLMENT_AMOUNT);

        // Get all the productList where minInstallmentAmount is greater than or equal to UPDATED_MIN_INSTALLMENT_AMOUNT
        defaultProductShouldNotBeFound("minInstallmentAmount.greaterThanOrEqual=" + UPDATED_MIN_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinInstallmentAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minInstallmentAmount is less than or equal to DEFAULT_MIN_INSTALLMENT_AMOUNT
        defaultProductShouldBeFound("minInstallmentAmount.lessThanOrEqual=" + DEFAULT_MIN_INSTALLMENT_AMOUNT);

        // Get all the productList where minInstallmentAmount is less than or equal to SMALLER_MIN_INSTALLMENT_AMOUNT
        defaultProductShouldNotBeFound("minInstallmentAmount.lessThanOrEqual=" + SMALLER_MIN_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinInstallmentAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minInstallmentAmount is less than DEFAULT_MIN_INSTALLMENT_AMOUNT
        defaultProductShouldNotBeFound("minInstallmentAmount.lessThan=" + DEFAULT_MIN_INSTALLMENT_AMOUNT);

        // Get all the productList where minInstallmentAmount is less than UPDATED_MIN_INSTALLMENT_AMOUNT
        defaultProductShouldBeFound("minInstallmentAmount.lessThan=" + UPDATED_MIN_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinInstallmentAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minInstallmentAmount is greater than DEFAULT_MIN_INSTALLMENT_AMOUNT
        defaultProductShouldNotBeFound("minInstallmentAmount.greaterThan=" + DEFAULT_MIN_INSTALLMENT_AMOUNT);

        // Get all the productList where minInstallmentAmount is greater than SMALLER_MIN_INSTALLMENT_AMOUNT
        defaultProductShouldBeFound("minInstallmentAmount.greaterThan=" + SMALLER_MIN_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMaxInstallmentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where maxInstallmentAmount equals to DEFAULT_MAX_INSTALLMENT_AMOUNT
        defaultProductShouldBeFound("maxInstallmentAmount.equals=" + DEFAULT_MAX_INSTALLMENT_AMOUNT);

        // Get all the productList where maxInstallmentAmount equals to UPDATED_MAX_INSTALLMENT_AMOUNT
        defaultProductShouldNotBeFound("maxInstallmentAmount.equals=" + UPDATED_MAX_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMaxInstallmentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where maxInstallmentAmount in DEFAULT_MAX_INSTALLMENT_AMOUNT or UPDATED_MAX_INSTALLMENT_AMOUNT
        defaultProductShouldBeFound("maxInstallmentAmount.in=" + DEFAULT_MAX_INSTALLMENT_AMOUNT + "," + UPDATED_MAX_INSTALLMENT_AMOUNT);

        // Get all the productList where maxInstallmentAmount equals to UPDATED_MAX_INSTALLMENT_AMOUNT
        defaultProductShouldNotBeFound("maxInstallmentAmount.in=" + UPDATED_MAX_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMaxInstallmentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where maxInstallmentAmount is not null
        defaultProductShouldBeFound("maxInstallmentAmount.specified=true");

        // Get all the productList where maxInstallmentAmount is null
        defaultProductShouldNotBeFound("maxInstallmentAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByMaxInstallmentAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where maxInstallmentAmount is greater than or equal to DEFAULT_MAX_INSTALLMENT_AMOUNT
        defaultProductShouldBeFound("maxInstallmentAmount.greaterThanOrEqual=" + DEFAULT_MAX_INSTALLMENT_AMOUNT);

        // Get all the productList where maxInstallmentAmount is greater than or equal to UPDATED_MAX_INSTALLMENT_AMOUNT
        defaultProductShouldNotBeFound("maxInstallmentAmount.greaterThanOrEqual=" + UPDATED_MAX_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMaxInstallmentAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where maxInstallmentAmount is less than or equal to DEFAULT_MAX_INSTALLMENT_AMOUNT
        defaultProductShouldBeFound("maxInstallmentAmount.lessThanOrEqual=" + DEFAULT_MAX_INSTALLMENT_AMOUNT);

        // Get all the productList where maxInstallmentAmount is less than or equal to SMALLER_MAX_INSTALLMENT_AMOUNT
        defaultProductShouldNotBeFound("maxInstallmentAmount.lessThanOrEqual=" + SMALLER_MAX_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMaxInstallmentAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where maxInstallmentAmount is less than DEFAULT_MAX_INSTALLMENT_AMOUNT
        defaultProductShouldNotBeFound("maxInstallmentAmount.lessThan=" + DEFAULT_MAX_INSTALLMENT_AMOUNT);

        // Get all the productList where maxInstallmentAmount is less than UPDATED_MAX_INSTALLMENT_AMOUNT
        defaultProductShouldBeFound("maxInstallmentAmount.lessThan=" + UPDATED_MAX_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMaxInstallmentAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where maxInstallmentAmount is greater than DEFAULT_MAX_INSTALLMENT_AMOUNT
        defaultProductShouldNotBeFound("maxInstallmentAmount.greaterThan=" + DEFAULT_MAX_INSTALLMENT_AMOUNT);

        // Get all the productList where maxInstallmentAmount is greater than SMALLER_MAX_INSTALLMENT_AMOUNT
        defaultProductShouldBeFound("maxInstallmentAmount.greaterThan=" + SMALLER_MAX_INSTALLMENT_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineAmount equals to DEFAULT_DROP_LINE_AMOUNT
        defaultProductShouldBeFound("dropLineAmount.equals=" + DEFAULT_DROP_LINE_AMOUNT);

        // Get all the productList where dropLineAmount equals to UPDATED_DROP_LINE_AMOUNT
        defaultProductShouldNotBeFound("dropLineAmount.equals=" + UPDATED_DROP_LINE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineAmountIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineAmount in DEFAULT_DROP_LINE_AMOUNT or UPDATED_DROP_LINE_AMOUNT
        defaultProductShouldBeFound("dropLineAmount.in=" + DEFAULT_DROP_LINE_AMOUNT + "," + UPDATED_DROP_LINE_AMOUNT);

        // Get all the productList where dropLineAmount equals to UPDATED_DROP_LINE_AMOUNT
        defaultProductShouldNotBeFound("dropLineAmount.in=" + UPDATED_DROP_LINE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineAmount is not null
        defaultProductShouldBeFound("dropLineAmount.specified=true");

        // Get all the productList where dropLineAmount is null
        defaultProductShouldNotBeFound("dropLineAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDropLineAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineAmount is greater than or equal to DEFAULT_DROP_LINE_AMOUNT
        defaultProductShouldBeFound("dropLineAmount.greaterThanOrEqual=" + DEFAULT_DROP_LINE_AMOUNT);

        // Get all the productList where dropLineAmount is greater than or equal to UPDATED_DROP_LINE_AMOUNT
        defaultProductShouldNotBeFound("dropLineAmount.greaterThanOrEqual=" + UPDATED_DROP_LINE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineAmount is less than or equal to DEFAULT_DROP_LINE_AMOUNT
        defaultProductShouldBeFound("dropLineAmount.lessThanOrEqual=" + DEFAULT_DROP_LINE_AMOUNT);

        // Get all the productList where dropLineAmount is less than or equal to SMALLER_DROP_LINE_AMOUNT
        defaultProductShouldNotBeFound("dropLineAmount.lessThanOrEqual=" + SMALLER_DROP_LINE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineAmount is less than DEFAULT_DROP_LINE_AMOUNT
        defaultProductShouldNotBeFound("dropLineAmount.lessThan=" + DEFAULT_DROP_LINE_AMOUNT);

        // Get all the productList where dropLineAmount is less than UPDATED_DROP_LINE_AMOUNT
        defaultProductShouldBeFound("dropLineAmount.lessThan=" + UPDATED_DROP_LINE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineAmount is greater than DEFAULT_DROP_LINE_AMOUNT
        defaultProductShouldNotBeFound("dropLineAmount.greaterThan=" + DEFAULT_DROP_LINE_AMOUNT);

        // Get all the productList where dropLineAmount is greater than SMALLER_DROP_LINE_AMOUNT
        defaultProductShouldBeFound("dropLineAmount.greaterThan=" + SMALLER_DROP_LINE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineODYNIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineODYN equals to DEFAULT_DROP_LINE_ODYN
        defaultProductShouldBeFound("dropLineODYN.equals=" + DEFAULT_DROP_LINE_ODYN);

        // Get all the productList where dropLineODYN equals to UPDATED_DROP_LINE_ODYN
        defaultProductShouldNotBeFound("dropLineODYN.equals=" + UPDATED_DROP_LINE_ODYN);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineODYNIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineODYN in DEFAULT_DROP_LINE_ODYN or UPDATED_DROP_LINE_ODYN
        defaultProductShouldBeFound("dropLineODYN.in=" + DEFAULT_DROP_LINE_ODYN + "," + UPDATED_DROP_LINE_ODYN);

        // Get all the productList where dropLineODYN equals to UPDATED_DROP_LINE_ODYN
        defaultProductShouldNotBeFound("dropLineODYN.in=" + UPDATED_DROP_LINE_ODYN);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineODYNIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineODYN is not null
        defaultProductShouldBeFound("dropLineODYN.specified=true");

        // Get all the productList where dropLineODYN is null
        defaultProductShouldNotBeFound("dropLineODYN.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDropLineODYNContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineODYN contains DEFAULT_DROP_LINE_ODYN
        defaultProductShouldBeFound("dropLineODYN.contains=" + DEFAULT_DROP_LINE_ODYN);

        // Get all the productList where dropLineODYN contains UPDATED_DROP_LINE_ODYN
        defaultProductShouldNotBeFound("dropLineODYN.contains=" + UPDATED_DROP_LINE_ODYN);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineODYNNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineODYN does not contain DEFAULT_DROP_LINE_ODYN
        defaultProductShouldNotBeFound("dropLineODYN.doesNotContain=" + DEFAULT_DROP_LINE_ODYN);

        // Get all the productList where dropLineODYN does not contain UPDATED_DROP_LINE_ODYN
        defaultProductShouldBeFound("dropLineODYN.doesNotContain=" + UPDATED_DROP_LINE_ODYN);
    }

    @Test
    @Transactional
    void getAllProductsByDropLinePercIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLinePerc equals to DEFAULT_DROP_LINE_PERC
        defaultProductShouldBeFound("dropLinePerc.equals=" + DEFAULT_DROP_LINE_PERC);

        // Get all the productList where dropLinePerc equals to UPDATED_DROP_LINE_PERC
        defaultProductShouldNotBeFound("dropLinePerc.equals=" + UPDATED_DROP_LINE_PERC);
    }

    @Test
    @Transactional
    void getAllProductsByDropLinePercIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLinePerc in DEFAULT_DROP_LINE_PERC or UPDATED_DROP_LINE_PERC
        defaultProductShouldBeFound("dropLinePerc.in=" + DEFAULT_DROP_LINE_PERC + "," + UPDATED_DROP_LINE_PERC);

        // Get all the productList where dropLinePerc equals to UPDATED_DROP_LINE_PERC
        defaultProductShouldNotBeFound("dropLinePerc.in=" + UPDATED_DROP_LINE_PERC);
    }

    @Test
    @Transactional
    void getAllProductsByDropLinePercIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLinePerc is not null
        defaultProductShouldBeFound("dropLinePerc.specified=true");

        // Get all the productList where dropLinePerc is null
        defaultProductShouldNotBeFound("dropLinePerc.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDropLinePercIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLinePerc is greater than or equal to DEFAULT_DROP_LINE_PERC
        defaultProductShouldBeFound("dropLinePerc.greaterThanOrEqual=" + DEFAULT_DROP_LINE_PERC);

        // Get all the productList where dropLinePerc is greater than or equal to UPDATED_DROP_LINE_PERC
        defaultProductShouldNotBeFound("dropLinePerc.greaterThanOrEqual=" + UPDATED_DROP_LINE_PERC);
    }

    @Test
    @Transactional
    void getAllProductsByDropLinePercIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLinePerc is less than or equal to DEFAULT_DROP_LINE_PERC
        defaultProductShouldBeFound("dropLinePerc.lessThanOrEqual=" + DEFAULT_DROP_LINE_PERC);

        // Get all the productList where dropLinePerc is less than or equal to SMALLER_DROP_LINE_PERC
        defaultProductShouldNotBeFound("dropLinePerc.lessThanOrEqual=" + SMALLER_DROP_LINE_PERC);
    }

    @Test
    @Transactional
    void getAllProductsByDropLinePercIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLinePerc is less than DEFAULT_DROP_LINE_PERC
        defaultProductShouldNotBeFound("dropLinePerc.lessThan=" + DEFAULT_DROP_LINE_PERC);

        // Get all the productList where dropLinePerc is less than UPDATED_DROP_LINE_PERC
        defaultProductShouldBeFound("dropLinePerc.lessThan=" + UPDATED_DROP_LINE_PERC);
    }

    @Test
    @Transactional
    void getAllProductsByDropLinePercIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLinePerc is greater than DEFAULT_DROP_LINE_PERC
        defaultProductShouldNotBeFound("dropLinePerc.greaterThan=" + DEFAULT_DROP_LINE_PERC);

        // Get all the productList where dropLinePerc is greater than SMALLER_DROP_LINE_PERC
        defaultProductShouldBeFound("dropLinePerc.greaterThan=" + SMALLER_DROP_LINE_PERC);
    }

    @Test
    @Transactional
    void getAllProductsByDropModeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropMode equals to DEFAULT_DROP_MODE
        defaultProductShouldBeFound("dropMode.equals=" + DEFAULT_DROP_MODE);

        // Get all the productList where dropMode equals to UPDATED_DROP_MODE
        defaultProductShouldNotBeFound("dropMode.equals=" + UPDATED_DROP_MODE);
    }

    @Test
    @Transactional
    void getAllProductsByDropModeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropMode in DEFAULT_DROP_MODE or UPDATED_DROP_MODE
        defaultProductShouldBeFound("dropMode.in=" + DEFAULT_DROP_MODE + "," + UPDATED_DROP_MODE);

        // Get all the productList where dropMode equals to UPDATED_DROP_MODE
        defaultProductShouldNotBeFound("dropMode.in=" + UPDATED_DROP_MODE);
    }

    @Test
    @Transactional
    void getAllProductsByDropModeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropMode is not null
        defaultProductShouldBeFound("dropMode.specified=true");

        // Get all the productList where dropMode is null
        defaultProductShouldNotBeFound("dropMode.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDropModeContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropMode contains DEFAULT_DROP_MODE
        defaultProductShouldBeFound("dropMode.contains=" + DEFAULT_DROP_MODE);

        // Get all the productList where dropMode contains UPDATED_DROP_MODE
        defaultProductShouldNotBeFound("dropMode.contains=" + UPDATED_DROP_MODE);
    }

    @Test
    @Transactional
    void getAllProductsByDropModeNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropMode does not contain DEFAULT_DROP_MODE
        defaultProductShouldNotBeFound("dropMode.doesNotContain=" + DEFAULT_DROP_MODE);

        // Get all the productList where dropMode does not contain UPDATED_DROP_MODE
        defaultProductShouldBeFound("dropMode.doesNotContain=" + UPDATED_DROP_MODE);
    }

    @Test
    @Transactional
    void getAllProductsByDropLIneFreqIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLIneFreq equals to DEFAULT_DROP_L_INE_FREQ
        defaultProductShouldBeFound("dropLIneFreq.equals=" + DEFAULT_DROP_L_INE_FREQ);

        // Get all the productList where dropLIneFreq equals to UPDATED_DROP_L_INE_FREQ
        defaultProductShouldNotBeFound("dropLIneFreq.equals=" + UPDATED_DROP_L_INE_FREQ);
    }

    @Test
    @Transactional
    void getAllProductsByDropLIneFreqIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLIneFreq in DEFAULT_DROP_L_INE_FREQ or UPDATED_DROP_L_INE_FREQ
        defaultProductShouldBeFound("dropLIneFreq.in=" + DEFAULT_DROP_L_INE_FREQ + "," + UPDATED_DROP_L_INE_FREQ);

        // Get all the productList where dropLIneFreq equals to UPDATED_DROP_L_INE_FREQ
        defaultProductShouldNotBeFound("dropLIneFreq.in=" + UPDATED_DROP_L_INE_FREQ);
    }

    @Test
    @Transactional
    void getAllProductsByDropLIneFreqIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLIneFreq is not null
        defaultProductShouldBeFound("dropLIneFreq.specified=true");

        // Get all the productList where dropLIneFreq is null
        defaultProductShouldNotBeFound("dropLIneFreq.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDropLIneFreqContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLIneFreq contains DEFAULT_DROP_L_INE_FREQ
        defaultProductShouldBeFound("dropLIneFreq.contains=" + DEFAULT_DROP_L_INE_FREQ);

        // Get all the productList where dropLIneFreq contains UPDATED_DROP_L_INE_FREQ
        defaultProductShouldNotBeFound("dropLIneFreq.contains=" + UPDATED_DROP_L_INE_FREQ);
    }

    @Test
    @Transactional
    void getAllProductsByDropLIneFreqNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLIneFreq does not contain DEFAULT_DROP_L_INE_FREQ
        defaultProductShouldNotBeFound("dropLIneFreq.doesNotContain=" + DEFAULT_DROP_L_INE_FREQ);

        // Get all the productList where dropLIneFreq does not contain UPDATED_DROP_L_INE_FREQ
        defaultProductShouldBeFound("dropLIneFreq.doesNotContain=" + UPDATED_DROP_L_INE_FREQ);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineCycleDayIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineCycleDay equals to DEFAULT_DROP_LINE_CYCLE_DAY
        defaultProductShouldBeFound("dropLineCycleDay.equals=" + DEFAULT_DROP_LINE_CYCLE_DAY);

        // Get all the productList where dropLineCycleDay equals to UPDATED_DROP_LINE_CYCLE_DAY
        defaultProductShouldNotBeFound("dropLineCycleDay.equals=" + UPDATED_DROP_LINE_CYCLE_DAY);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineCycleDayIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineCycleDay in DEFAULT_DROP_LINE_CYCLE_DAY or UPDATED_DROP_LINE_CYCLE_DAY
        defaultProductShouldBeFound("dropLineCycleDay.in=" + DEFAULT_DROP_LINE_CYCLE_DAY + "," + UPDATED_DROP_LINE_CYCLE_DAY);

        // Get all the productList where dropLineCycleDay equals to UPDATED_DROP_LINE_CYCLE_DAY
        defaultProductShouldNotBeFound("dropLineCycleDay.in=" + UPDATED_DROP_LINE_CYCLE_DAY);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineCycleDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineCycleDay is not null
        defaultProductShouldBeFound("dropLineCycleDay.specified=true");

        // Get all the productList where dropLineCycleDay is null
        defaultProductShouldNotBeFound("dropLineCycleDay.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDropLineCycleDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineCycleDay is greater than or equal to DEFAULT_DROP_LINE_CYCLE_DAY
        defaultProductShouldBeFound("dropLineCycleDay.greaterThanOrEqual=" + DEFAULT_DROP_LINE_CYCLE_DAY);

        // Get all the productList where dropLineCycleDay is greater than or equal to UPDATED_DROP_LINE_CYCLE_DAY
        defaultProductShouldNotBeFound("dropLineCycleDay.greaterThanOrEqual=" + UPDATED_DROP_LINE_CYCLE_DAY);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineCycleDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineCycleDay is less than or equal to DEFAULT_DROP_LINE_CYCLE_DAY
        defaultProductShouldBeFound("dropLineCycleDay.lessThanOrEqual=" + DEFAULT_DROP_LINE_CYCLE_DAY);

        // Get all the productList where dropLineCycleDay is less than or equal to SMALLER_DROP_LINE_CYCLE_DAY
        defaultProductShouldNotBeFound("dropLineCycleDay.lessThanOrEqual=" + SMALLER_DROP_LINE_CYCLE_DAY);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineCycleDayIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineCycleDay is less than DEFAULT_DROP_LINE_CYCLE_DAY
        defaultProductShouldNotBeFound("dropLineCycleDay.lessThan=" + DEFAULT_DROP_LINE_CYCLE_DAY);

        // Get all the productList where dropLineCycleDay is less than UPDATED_DROP_LINE_CYCLE_DAY
        defaultProductShouldBeFound("dropLineCycleDay.lessThan=" + UPDATED_DROP_LINE_CYCLE_DAY);
    }

    @Test
    @Transactional
    void getAllProductsByDropLineCycleDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where dropLineCycleDay is greater than DEFAULT_DROP_LINE_CYCLE_DAY
        defaultProductShouldNotBeFound("dropLineCycleDay.greaterThan=" + DEFAULT_DROP_LINE_CYCLE_DAY);

        // Get all the productList where dropLineCycleDay is greater than SMALLER_DROP_LINE_CYCLE_DAY
        defaultProductShouldBeFound("dropLineCycleDay.greaterThan=" + SMALLER_DROP_LINE_CYCLE_DAY);
    }

    @Test
    @Transactional
    void getAllProductsByRoiIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where roi equals to DEFAULT_ROI
        defaultProductShouldBeFound("roi.equals=" + DEFAULT_ROI);

        // Get all the productList where roi equals to UPDATED_ROI
        defaultProductShouldNotBeFound("roi.equals=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllProductsByRoiIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where roi in DEFAULT_ROI or UPDATED_ROI
        defaultProductShouldBeFound("roi.in=" + DEFAULT_ROI + "," + UPDATED_ROI);

        // Get all the productList where roi equals to UPDATED_ROI
        defaultProductShouldNotBeFound("roi.in=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllProductsByRoiIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where roi is not null
        defaultProductShouldBeFound("roi.specified=true");

        // Get all the productList where roi is null
        defaultProductShouldNotBeFound("roi.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByRoiIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where roi is greater than or equal to DEFAULT_ROI
        defaultProductShouldBeFound("roi.greaterThanOrEqual=" + DEFAULT_ROI);

        // Get all the productList where roi is greater than or equal to UPDATED_ROI
        defaultProductShouldNotBeFound("roi.greaterThanOrEqual=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllProductsByRoiIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where roi is less than or equal to DEFAULT_ROI
        defaultProductShouldBeFound("roi.lessThanOrEqual=" + DEFAULT_ROI);

        // Get all the productList where roi is less than or equal to SMALLER_ROI
        defaultProductShouldNotBeFound("roi.lessThanOrEqual=" + SMALLER_ROI);
    }

    @Test
    @Transactional
    void getAllProductsByRoiIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where roi is less than DEFAULT_ROI
        defaultProductShouldNotBeFound("roi.lessThan=" + DEFAULT_ROI);

        // Get all the productList where roi is less than UPDATED_ROI
        defaultProductShouldBeFound("roi.lessThan=" + UPDATED_ROI);
    }

    @Test
    @Transactional
    void getAllProductsByRoiIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where roi is greater than DEFAULT_ROI
        defaultProductShouldNotBeFound("roi.greaterThan=" + DEFAULT_ROI);

        // Get all the productList where roi is greater than SMALLER_ROI
        defaultProductShouldBeFound("roi.greaterThan=" + SMALLER_ROI);
    }

    @Test
    @Transactional
    void getAllProductsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isDeleted equals to DEFAULT_IS_DELETED
        defaultProductShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the productList where isDeleted equals to UPDATED_IS_DELETED
        defaultProductShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllProductsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultProductShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the productList where isDeleted equals to UPDATED_IS_DELETED
        defaultProductShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllProductsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isDeleted is not null
        defaultProductShouldBeFound("isDeleted.specified=true");

        // Get all the productList where isDeleted is null
        defaultProductShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultProductShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the productList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProductShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultProductShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the productList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProductShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModified is not null
        defaultProductShouldBeFound("lastModified.specified=true");

        // Get all the productList where lastModified is null
        defaultProductShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModifiedBy is not null
        defaultProductShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productList where lastModifiedBy is null
        defaultProductShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultProductShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the productList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultProductShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultProductShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the productList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultProductShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField1 is not null
        defaultProductShouldBeFound("freeField1.specified=true");

        // Get all the productList where freeField1 is null
        defaultProductShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultProductShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the productList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultProductShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultProductShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the productList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultProductShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultProductShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the productList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultProductShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultProductShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the productList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultProductShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField2 is not null
        defaultProductShouldBeFound("freeField2.specified=true");

        // Get all the productList where freeField2 is null
        defaultProductShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultProductShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the productList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultProductShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultProductShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the productList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultProductShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultProductShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the productList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultProductShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultProductShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the productList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultProductShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField3 is not null
        defaultProductShouldBeFound("freeField3.specified=true");

        // Get all the productList where freeField3 is null
        defaultProductShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultProductShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the productList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultProductShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultProductShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the productList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultProductShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultProductShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the productList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultProductShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultProductShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the productList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultProductShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField4 is not null
        defaultProductShouldBeFound("freeField4.specified=true");

        // Get all the productList where freeField4 is null
        defaultProductShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultProductShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the productList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultProductShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultProductShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the productList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultProductShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultProductShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the productList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultProductShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultProductShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the productList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultProductShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField5 is not null
        defaultProductShouldBeFound("freeField5.specified=true");

        // Get all the productList where freeField5 is null
        defaultProductShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultProductShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the productList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultProductShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultProductShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the productList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultProductShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultProductShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the productList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultProductShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultProductShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the productList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultProductShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField6 is not null
        defaultProductShouldBeFound("freeField6.specified=true");

        // Get all the productList where freeField6 is null
        defaultProductShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultProductShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the productList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultProductShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllProductsByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultProductShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the productList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultProductShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllProductsByLoanCatagoryIsEqualToSomething() throws Exception {
        LoanCatagory loanCatagory;
        if (TestUtil.findAll(em, LoanCatagory.class).isEmpty()) {
            productRepository.saveAndFlush(product);
            loanCatagory = LoanCatagoryResourceIT.createEntity(em);
        } else {
            loanCatagory = TestUtil.findAll(em, LoanCatagory.class).get(0);
        }
        em.persist(loanCatagory);
        em.flush();
        product.setLoanCatagory(loanCatagory);
        productRepository.saveAndFlush(product);
        Long loanCatagoryId = loanCatagory.getId();

        // Get all the productList where loanCatagory equals to loanCatagoryId
        defaultProductShouldBeFound("loanCatagoryId.equals=" + loanCatagoryId);

        // Get all the productList where loanCatagory equals to (loanCatagoryId + 1)
        defaultProductShouldNotBeFound("loanCatagoryId.equals=" + (loanCatagoryId + 1));
    }

    @Test
    @Transactional
    void getAllProductsByOrganisationIsEqualToSomething() throws Exception {
        Organisation organisation;
        if (TestUtil.findAll(em, Organisation.class).isEmpty()) {
            productRepository.saveAndFlush(product);
            organisation = OrganisationResourceIT.createEntity(em);
        } else {
            organisation = TestUtil.findAll(em, Organisation.class).get(0);
        }
        em.persist(organisation);
        em.flush();
        product.setOrganisation(organisation);
        productRepository.saveAndFlush(product);
        Long organisationId = organisation.getId();

        // Get all the productList where organisation equals to organisationId
        defaultProductShouldBeFound("organisationId.equals=" + organisationId);

        // Get all the productList where organisation equals to (organisationId + 1)
        defaultProductShouldNotBeFound("organisationId.equals=" + (organisationId + 1));
    }

    @Test
    @Transactional
    void getAllProductsByLedgerAccountsIsEqualToSomething() throws Exception {
        LedgerAccounts ledgerAccounts;
        if (TestUtil.findAll(em, LedgerAccounts.class).isEmpty()) {
            productRepository.saveAndFlush(product);
            ledgerAccounts = LedgerAccountsResourceIT.createEntity(em);
        } else {
            ledgerAccounts = TestUtil.findAll(em, LedgerAccounts.class).get(0);
        }
        em.persist(ledgerAccounts);
        em.flush();
        product.setLedgerAccounts(ledgerAccounts);
        productRepository.saveAndFlush(product);
        Long ledgerAccountsId = ledgerAccounts.getId();

        // Get all the productList where ledgerAccounts equals to ledgerAccountsId
        defaultProductShouldBeFound("ledgerAccountsId.equals=" + ledgerAccountsId);

        // Get all the productList where ledgerAccounts equals to (ledgerAccountsId + 1)
        defaultProductShouldNotBeFound("ledgerAccountsId.equals=" + (ledgerAccountsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductShouldBeFound(String filter) throws Exception {
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].prodCode").value(hasItem(DEFAULT_PROD_CODE)))
            .andExpect(jsonPath("$.[*].prodName").value(hasItem(DEFAULT_PROD_NAME)))
            .andExpect(jsonPath("$.[*].bpiTreatmentFlag").value(hasItem(DEFAULT_BPI_TREATMENT_FLAG)))
            .andExpect(jsonPath("$.[*].amortizationMethod").value(hasItem(DEFAULT_AMORTIZATION_METHOD)))
            .andExpect(jsonPath("$.[*].amortizationType").value(hasItem(DEFAULT_AMORTIZATION_TYPE)))
            .andExpect(jsonPath("$.[*].compoundingFreq").value(hasItem(DEFAULT_COMPOUNDING_FREQ)))
            .andExpect(jsonPath("$.[*].emiRounding").value(hasItem(DEFAULT_EMI_ROUNDING)))
            .andExpect(jsonPath("$.[*].lastRowEMIThreshold").value(hasItem(DEFAULT_LAST_ROW_EMI_THRESHOLD.doubleValue())))
            .andExpect(jsonPath("$.[*].graceDays").value(hasItem(DEFAULT_GRACE_DAYS.intValue())))
            .andExpect(jsonPath("$.[*].reschLockinPeriod").value(hasItem(DEFAULT_RESCH_LOCKIN_PERIOD.intValue())))
            .andExpect(jsonPath("$.[*].prepayAfterInstNo").value(hasItem(DEFAULT_PREPAY_AFTER_INST_NO.intValue())))
            .andExpect(jsonPath("$.[*].prepayBeforeInstNo").value(hasItem(DEFAULT_PREPAY_BEFORE_INST_NO.intValue())))
            .andExpect(jsonPath("$.[*].minInstallmentGapBetPrepay").value(hasItem(DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY.intValue())))
            .andExpect(jsonPath("$.[*].minPrepayAmount").value(hasItem(DEFAULT_MIN_PREPAY_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].forecloseAfterInstNo").value(hasItem(DEFAULT_FORECLOSE_AFTER_INST_NO.doubleValue())))
            .andExpect(jsonPath("$.[*].forecloseBeforeInstaNo").value(hasItem(DEFAULT_FORECLOSE_BEFORE_INSTA_NO.doubleValue())))
            .andExpect(jsonPath("$.[*].minTenor").value(hasItem(DEFAULT_MIN_TENOR.doubleValue())))
            .andExpect(jsonPath("$.[*].maxTenor").value(hasItem(DEFAULT_MAX_TENOR.doubleValue())))
            .andExpect(jsonPath("$.[*].minInstallmentAmount").value(hasItem(DEFAULT_MIN_INSTALLMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].maxInstallmentAmount").value(hasItem(DEFAULT_MAX_INSTALLMENT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dropLineAmount").value(hasItem(DEFAULT_DROP_LINE_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dropLineODYN").value(hasItem(DEFAULT_DROP_LINE_ODYN)))
            .andExpect(jsonPath("$.[*].dropLinePerc").value(hasItem(DEFAULT_DROP_LINE_PERC.intValue())))
            .andExpect(jsonPath("$.[*].dropMode").value(hasItem(DEFAULT_DROP_MODE)))
            .andExpect(jsonPath("$.[*].dropLIneFreq").value(hasItem(DEFAULT_DROP_L_INE_FREQ)))
            .andExpect(jsonPath("$.[*].dropLineCycleDay").value(hasItem(DEFAULT_DROP_LINE_CYCLE_DAY.intValue())))
            .andExpect(jsonPath("$.[*].roi").value(hasItem(DEFAULT_ROI.doubleValue())))
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
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductShouldNotBeFound(String filter) throws Exception {
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .prodCode(UPDATED_PROD_CODE)
            .prodName(UPDATED_PROD_NAME)
            .bpiTreatmentFlag(UPDATED_BPI_TREATMENT_FLAG)
            .amortizationMethod(UPDATED_AMORTIZATION_METHOD)
            .amortizationType(UPDATED_AMORTIZATION_TYPE)
            .compoundingFreq(UPDATED_COMPOUNDING_FREQ)
            .emiRounding(UPDATED_EMI_ROUNDING)
            .lastRowEMIThreshold(UPDATED_LAST_ROW_EMI_THRESHOLD)
            .graceDays(UPDATED_GRACE_DAYS)
            .reschLockinPeriod(UPDATED_RESCH_LOCKIN_PERIOD)
            .prepayAfterInstNo(UPDATED_PREPAY_AFTER_INST_NO)
            .prepayBeforeInstNo(UPDATED_PREPAY_BEFORE_INST_NO)
            .minInstallmentGapBetPrepay(UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY)
            .minPrepayAmount(UPDATED_MIN_PREPAY_AMOUNT)
            .forecloseAfterInstNo(UPDATED_FORECLOSE_AFTER_INST_NO)
            .forecloseBeforeInstaNo(UPDATED_FORECLOSE_BEFORE_INSTA_NO)
            .minTenor(UPDATED_MIN_TENOR)
            .maxTenor(UPDATED_MAX_TENOR)
            .minInstallmentAmount(UPDATED_MIN_INSTALLMENT_AMOUNT)
            .maxInstallmentAmount(UPDATED_MAX_INSTALLMENT_AMOUNT)
            .dropLineAmount(UPDATED_DROP_LINE_AMOUNT)
            .dropLineODYN(UPDATED_DROP_LINE_ODYN)
            .dropLinePerc(UPDATED_DROP_LINE_PERC)
            .dropMode(UPDATED_DROP_MODE)
            .dropLIneFreq(UPDATED_DROP_L_INE_FREQ)
            .dropLineCycleDay(UPDATED_DROP_LINE_CYCLE_DAY)
            .roi(UPDATED_ROI)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .prodColour(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .prodIconUrl(UPDATED_FREE_FIELD_6);
        ProductDTO productDTO = productMapper.toDto(updatedProduct);

        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProdCode()).isEqualTo(UPDATED_PROD_CODE);
        assertThat(testProduct.getProdName()).isEqualTo(UPDATED_PROD_NAME);
        assertThat(testProduct.getBpiTreatmentFlag()).isEqualTo(UPDATED_BPI_TREATMENT_FLAG);
        assertThat(testProduct.getAmortizationMethod()).isEqualTo(UPDATED_AMORTIZATION_METHOD);
        assertThat(testProduct.getAmortizationType()).isEqualTo(UPDATED_AMORTIZATION_TYPE);
        assertThat(testProduct.getCompoundingFreq()).isEqualTo(UPDATED_COMPOUNDING_FREQ);
        assertThat(testProduct.getEmiRounding()).isEqualTo(UPDATED_EMI_ROUNDING);
        assertThat(testProduct.getLastRowEMIThreshold()).isEqualTo(UPDATED_LAST_ROW_EMI_THRESHOLD);
        assertThat(testProduct.getGraceDays()).isEqualTo(UPDATED_GRACE_DAYS);
        assertThat(testProduct.getReschLockinPeriod()).isEqualTo(UPDATED_RESCH_LOCKIN_PERIOD);
        assertThat(testProduct.getPrepayAfterInstNo()).isEqualTo(UPDATED_PREPAY_AFTER_INST_NO);
        assertThat(testProduct.getPrepayBeforeInstNo()).isEqualTo(UPDATED_PREPAY_BEFORE_INST_NO);
        assertThat(testProduct.getMinInstallmentGapBetPrepay()).isEqualTo(UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY);
        assertThat(testProduct.getMinPrepayAmount()).isEqualTo(UPDATED_MIN_PREPAY_AMOUNT);
        assertThat(testProduct.getForecloseAfterInstNo()).isEqualTo(UPDATED_FORECLOSE_AFTER_INST_NO);
        assertThat(testProduct.getForecloseBeforeInstaNo()).isEqualTo(UPDATED_FORECLOSE_BEFORE_INSTA_NO);
        assertThat(testProduct.getMinTenor()).isEqualTo(UPDATED_MIN_TENOR);
        assertThat(testProduct.getMaxTenor()).isEqualTo(UPDATED_MAX_TENOR);
        assertThat(testProduct.getMinInstallmentAmount()).isEqualTo(UPDATED_MIN_INSTALLMENT_AMOUNT);
        assertThat(testProduct.getMaxInstallmentAmount()).isEqualTo(UPDATED_MAX_INSTALLMENT_AMOUNT);
        assertThat(testProduct.getDropLineAmount()).isEqualTo(UPDATED_DROP_LINE_AMOUNT);
        assertThat(testProduct.getDropLineODYN()).isEqualTo(UPDATED_DROP_LINE_ODYN);
        assertThat(testProduct.getDropLinePerc()).isEqualTo(UPDATED_DROP_LINE_PERC);
        assertThat(testProduct.getDropMode()).isEqualTo(UPDATED_DROP_MODE);
        assertThat(testProduct.getDropLIneFreq()).isEqualTo(UPDATED_DROP_L_INE_FREQ);
        assertThat(testProduct.getDropLineCycleDay()).isEqualTo(UPDATED_DROP_LINE_CYCLE_DAY);
        assertThat(testProduct.getRoi()).isEqualTo(UPDATED_ROI);
        assertThat(testProduct.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testProduct.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProduct.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProduct.getProdColour()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testProduct.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testProduct.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testProduct.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testProduct.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testProduct.getProdIconUrl()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .amortizationType(UPDATED_AMORTIZATION_TYPE)
            .compoundingFreq(UPDATED_COMPOUNDING_FREQ)
            .prepayAfterInstNo(UPDATED_PREPAY_AFTER_INST_NO)
            .minPrepayAmount(UPDATED_MIN_PREPAY_AMOUNT)
            .maxTenor(UPDATED_MAX_TENOR)
            .minInstallmentAmount(UPDATED_MIN_INSTALLMENT_AMOUNT)
            .maxInstallmentAmount(UPDATED_MAX_INSTALLMENT_AMOUNT)
            .dropLineODYN(UPDATED_DROP_LINE_ODYN)
            .dropMode(UPDATED_DROP_MODE)
            .dropLineCycleDay(UPDATED_DROP_LINE_CYCLE_DAY)
            .roi(UPDATED_ROI)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField5(UPDATED_FREE_FIELD_5)
            .prodIconUrl(UPDATED_FREE_FIELD_6);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProdCode()).isEqualTo(DEFAULT_PROD_CODE);
        assertThat(testProduct.getProdName()).isEqualTo(DEFAULT_PROD_NAME);
        assertThat(testProduct.getBpiTreatmentFlag()).isEqualTo(DEFAULT_BPI_TREATMENT_FLAG);
        assertThat(testProduct.getAmortizationMethod()).isEqualTo(DEFAULT_AMORTIZATION_METHOD);
        assertThat(testProduct.getAmortizationType()).isEqualTo(UPDATED_AMORTIZATION_TYPE);
        assertThat(testProduct.getCompoundingFreq()).isEqualTo(UPDATED_COMPOUNDING_FREQ);
        assertThat(testProduct.getEmiRounding()).isEqualTo(DEFAULT_EMI_ROUNDING);
        assertThat(testProduct.getLastRowEMIThreshold()).isEqualTo(DEFAULT_LAST_ROW_EMI_THRESHOLD);
        assertThat(testProduct.getGraceDays()).isEqualTo(DEFAULT_GRACE_DAYS);
        assertThat(testProduct.getReschLockinPeriod()).isEqualTo(DEFAULT_RESCH_LOCKIN_PERIOD);
        assertThat(testProduct.getPrepayAfterInstNo()).isEqualTo(UPDATED_PREPAY_AFTER_INST_NO);
        assertThat(testProduct.getPrepayBeforeInstNo()).isEqualTo(DEFAULT_PREPAY_BEFORE_INST_NO);
        assertThat(testProduct.getMinInstallmentGapBetPrepay()).isEqualTo(DEFAULT_MIN_INSTALLMENT_GAP_BET_PREPAY);
        assertThat(testProduct.getMinPrepayAmount()).isEqualTo(UPDATED_MIN_PREPAY_AMOUNT);
        assertThat(testProduct.getForecloseAfterInstNo()).isEqualTo(DEFAULT_FORECLOSE_AFTER_INST_NO);
        assertThat(testProduct.getForecloseBeforeInstaNo()).isEqualTo(DEFAULT_FORECLOSE_BEFORE_INSTA_NO);
        assertThat(testProduct.getMinTenor()).isEqualTo(DEFAULT_MIN_TENOR);
        assertThat(testProduct.getMaxTenor()).isEqualTo(UPDATED_MAX_TENOR);
        assertThat(testProduct.getMinInstallmentAmount()).isEqualTo(UPDATED_MIN_INSTALLMENT_AMOUNT);
        assertThat(testProduct.getMaxInstallmentAmount()).isEqualTo(UPDATED_MAX_INSTALLMENT_AMOUNT);
        assertThat(testProduct.getDropLineAmount()).isEqualTo(DEFAULT_DROP_LINE_AMOUNT);
        assertThat(testProduct.getDropLineODYN()).isEqualTo(UPDATED_DROP_LINE_ODYN);
        assertThat(testProduct.getDropLinePerc()).isEqualTo(DEFAULT_DROP_LINE_PERC);
        assertThat(testProduct.getDropMode()).isEqualTo(UPDATED_DROP_MODE);
        assertThat(testProduct.getDropLIneFreq()).isEqualTo(DEFAULT_DROP_L_INE_FREQ);
        assertThat(testProduct.getDropLineCycleDay()).isEqualTo(UPDATED_DROP_LINE_CYCLE_DAY);
        assertThat(testProduct.getRoi()).isEqualTo(UPDATED_ROI);
        assertThat(testProduct.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testProduct.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProduct.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProduct.getProdColour()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testProduct.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testProduct.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testProduct.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testProduct.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testProduct.getProdIconUrl()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .prodCode(UPDATED_PROD_CODE)
            .prodName(UPDATED_PROD_NAME)
            .bpiTreatmentFlag(UPDATED_BPI_TREATMENT_FLAG)
            .amortizationMethod(UPDATED_AMORTIZATION_METHOD)
            .amortizationType(UPDATED_AMORTIZATION_TYPE)
            .compoundingFreq(UPDATED_COMPOUNDING_FREQ)
            .emiRounding(UPDATED_EMI_ROUNDING)
            .lastRowEMIThreshold(UPDATED_LAST_ROW_EMI_THRESHOLD)
            .graceDays(UPDATED_GRACE_DAYS)
            .reschLockinPeriod(UPDATED_RESCH_LOCKIN_PERIOD)
            .prepayAfterInstNo(UPDATED_PREPAY_AFTER_INST_NO)
            .prepayBeforeInstNo(UPDATED_PREPAY_BEFORE_INST_NO)
            .minInstallmentGapBetPrepay(UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY)
            .minPrepayAmount(UPDATED_MIN_PREPAY_AMOUNT)
            .forecloseAfterInstNo(UPDATED_FORECLOSE_AFTER_INST_NO)
            .forecloseBeforeInstaNo(UPDATED_FORECLOSE_BEFORE_INSTA_NO)
            .minTenor(UPDATED_MIN_TENOR)
            .maxTenor(UPDATED_MAX_TENOR)
            .minInstallmentAmount(UPDATED_MIN_INSTALLMENT_AMOUNT)
            .maxInstallmentAmount(UPDATED_MAX_INSTALLMENT_AMOUNT)
            .dropLineAmount(UPDATED_DROP_LINE_AMOUNT)
            .dropLineODYN(UPDATED_DROP_LINE_ODYN)
            .dropLinePerc(UPDATED_DROP_LINE_PERC)
            .dropMode(UPDATED_DROP_MODE)
            .dropLIneFreq(UPDATED_DROP_L_INE_FREQ)
            .dropLineCycleDay(UPDATED_DROP_LINE_CYCLE_DAY)
            .roi(UPDATED_ROI)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .prodColour(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .prodIconUrl(UPDATED_FREE_FIELD_6);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProdCode()).isEqualTo(UPDATED_PROD_CODE);
        assertThat(testProduct.getProdName()).isEqualTo(UPDATED_PROD_NAME);
        assertThat(testProduct.getBpiTreatmentFlag()).isEqualTo(UPDATED_BPI_TREATMENT_FLAG);
        assertThat(testProduct.getAmortizationMethod()).isEqualTo(UPDATED_AMORTIZATION_METHOD);
        assertThat(testProduct.getAmortizationType()).isEqualTo(UPDATED_AMORTIZATION_TYPE);
        assertThat(testProduct.getCompoundingFreq()).isEqualTo(UPDATED_COMPOUNDING_FREQ);
        assertThat(testProduct.getEmiRounding()).isEqualTo(UPDATED_EMI_ROUNDING);
        assertThat(testProduct.getLastRowEMIThreshold()).isEqualTo(UPDATED_LAST_ROW_EMI_THRESHOLD);
        assertThat(testProduct.getGraceDays()).isEqualTo(UPDATED_GRACE_DAYS);
        assertThat(testProduct.getReschLockinPeriod()).isEqualTo(UPDATED_RESCH_LOCKIN_PERIOD);
        assertThat(testProduct.getPrepayAfterInstNo()).isEqualTo(UPDATED_PREPAY_AFTER_INST_NO);
        assertThat(testProduct.getPrepayBeforeInstNo()).isEqualTo(UPDATED_PREPAY_BEFORE_INST_NO);
        assertThat(testProduct.getMinInstallmentGapBetPrepay()).isEqualTo(UPDATED_MIN_INSTALLMENT_GAP_BET_PREPAY);
        assertThat(testProduct.getMinPrepayAmount()).isEqualTo(UPDATED_MIN_PREPAY_AMOUNT);
        assertThat(testProduct.getForecloseAfterInstNo()).isEqualTo(UPDATED_FORECLOSE_AFTER_INST_NO);
        assertThat(testProduct.getForecloseBeforeInstaNo()).isEqualTo(UPDATED_FORECLOSE_BEFORE_INSTA_NO);
        assertThat(testProduct.getMinTenor()).isEqualTo(UPDATED_MIN_TENOR);
        assertThat(testProduct.getMaxTenor()).isEqualTo(UPDATED_MAX_TENOR);
        assertThat(testProduct.getMinInstallmentAmount()).isEqualTo(UPDATED_MIN_INSTALLMENT_AMOUNT);
        assertThat(testProduct.getMaxInstallmentAmount()).isEqualTo(UPDATED_MAX_INSTALLMENT_AMOUNT);
        assertThat(testProduct.getDropLineAmount()).isEqualTo(UPDATED_DROP_LINE_AMOUNT);
        assertThat(testProduct.getDropLineODYN()).isEqualTo(UPDATED_DROP_LINE_ODYN);
        assertThat(testProduct.getDropLinePerc()).isEqualTo(UPDATED_DROP_LINE_PERC);
        assertThat(testProduct.getDropMode()).isEqualTo(UPDATED_DROP_MODE);
        assertThat(testProduct.getDropLIneFreq()).isEqualTo(UPDATED_DROP_L_INE_FREQ);
        assertThat(testProduct.getDropLineCycleDay()).isEqualTo(UPDATED_DROP_LINE_CYCLE_DAY);
        assertThat(testProduct.getRoi()).isEqualTo(UPDATED_ROI);
        assertThat(testProduct.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testProduct.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProduct.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProduct.getProdColour()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testProduct.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testProduct.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testProduct.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testProduct.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testProduct.getProdIconUrl()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, product.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
