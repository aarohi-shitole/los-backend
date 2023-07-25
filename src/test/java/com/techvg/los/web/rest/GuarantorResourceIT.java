package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.EmployementDetails;
import com.techvg.los.domain.Guarantor;
import com.techvg.los.domain.Member;
import com.techvg.los.domain.MemberAssets;
import com.techvg.los.domain.enumeration.Gender;
import com.techvg.los.domain.enumeration.MaritalStatus;
import com.techvg.los.domain.enumeration.Occupation;
import com.techvg.los.domain.enumeration.Title;
import com.techvg.los.repository.GuarantorRepository;
import com.techvg.los.service.criteria.GuarantorCriteria;
import com.techvg.los.service.dto.GuarantorDTO;
import com.techvg.los.service.mapper.GuarantorMapper;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link GuarantorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GuarantorResourceIT {

    private static final Title DEFAULT_TITLE = Title.MR;
    private static final Title UPDATED_TITLE = Title.MRS;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBERSHIP_NO = "AAAAAAAAAA";
    private static final String UPDATED_MEMBERSHIP_NO = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOB = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_HOUSE_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_HOUSE_OWNER = "BBBBBBBBBB";

    private static final Occupation DEFAULT_OCCUPATION = Occupation.SALARIED;
    private static final Occupation UPDATED_OCCUPATION = Occupation.BUSINESS;

    private static final String DEFAULT_EMPLOYER_NAME_ADD = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYER_NAME_ADD = "BBBBBBBBBB";

    private static final Double DEFAULT_SOCLIBIL_AMT = 1D;
    private static final Double UPDATED_SOCLIBIL_AMT = 2D;
    private static final Double SMALLER_SOCLIBIL_AMT = 1D - 1D;

    private static final String DEFAULT_SOCLIBIL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SOCLIBIL_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_OTHERLIBIL_AMT = 1D;
    private static final Double UPDATED_OTHERLIBIL_AMT = 2D;
    private static final Double SMALLER_OTHERLIBIL_AMT = 1D - 1D;

    private static final String DEFAULT_OTHERLIBIL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_OTHERLIBIL_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR_CARD_NO = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR_CARD_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PAN_CARD = "AAAAAAAAAA";
    private static final String UPDATED_PAN_CARD = "BBBBBBBBBB";

    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.MARRIED;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.SINGLE;

    private static final Boolean DEFAULT_HAS_ADHAR_VERIFIED = false;
    private static final Boolean UPDATED_HAS_ADHAR_VERIFIED = true;

    private static final Boolean DEFAULT_HAS_PAN_VERIFIED = false;
    private static final Boolean UPDATED_HAS_PAN_VERIFIED = true;

    private static final Integer DEFAULT_NUMBER_OF_ASSETS = 1;
    private static final Integer UPDATED_NUMBER_OF_ASSETS = 2;
    private static final Integer SMALLER_NUMBER_OF_ASSETS = 1 - 1;

    private static final Double DEFAULT_GROSS_ANNUAL_INC = 1D;
    private static final Double UPDATED_GROSS_ANNUAL_INC = 2D;
    private static final Double SMALLER_GROSS_ANNUAL_INC = 1D - 1D;

    private static final Double DEFAULT_NET_INCOME = 1D;
    private static final Double UPDATED_NET_INCOME = 2D;
    private static final Double SMALLER_NET_INCOME = 1D - 1D;

    private static final Boolean DEFAULT_IS_INCOME_TAX_PAYER = false;
    private static final Boolean UPDATED_IS_INCOME_TAX_PAYER = true;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/guarantors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GuarantorRepository guarantorRepository;

    @Autowired
    private GuarantorMapper guarantorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGuarantorMockMvc;

    private Guarantor guarantor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Guarantor createEntity(EntityManager em) {
        Guarantor guarantor = new Guarantor()
            .title(DEFAULT_TITLE)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .membershipNo(DEFAULT_MEMBERSHIP_NO)
            .gender(DEFAULT_GENDER)
            .dob(DEFAULT_DOB)
            .email(DEFAULT_EMAIL)
            .mobileNo(DEFAULT_MOBILE_NO)
            .houseOwner(DEFAULT_HOUSE_OWNER)
            .occupation(DEFAULT_OCCUPATION)
            .employerNameAdd(DEFAULT_EMPLOYER_NAME_ADD)
            .soclibilAmt(DEFAULT_SOCLIBIL_AMT)
            .soclibilType(DEFAULT_SOCLIBIL_TYPE)
            .otherlibilAmt(DEFAULT_OTHERLIBIL_AMT)
            .otherlibilType(DEFAULT_OTHERLIBIL_TYPE)
            .aadharCardNo(DEFAULT_AADHAR_CARD_NO)
            .panCard(DEFAULT_PAN_CARD)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .hasAdharVerified(DEFAULT_HAS_ADHAR_VERIFIED)
            .hasPanVerified(DEFAULT_HAS_PAN_VERIFIED)
            .numberOfAssets(DEFAULT_NUMBER_OF_ASSETS)
            .grossAnnualInc(DEFAULT_GROSS_ANNUAL_INC)
            .netIncome(DEFAULT_NET_INCOME)
            .isIncomeTaxPayer(DEFAULT_IS_INCOME_TAX_PAYER)
            .isActive(DEFAULT_IS_ACTIVE)
            .isDeleted(DEFAULT_IS_DELETED)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .address(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .profileUrl(DEFAULT_FREE_FIELD_5)
            .freeField6(DEFAULT_FREE_FIELD_6);
        return guarantor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Guarantor createUpdatedEntity(EntityManager em) {
        Guarantor guarantor = new Guarantor()
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .membershipNo(UPDATED_MEMBERSHIP_NO)
            .gender(UPDATED_GENDER)
            .dob(UPDATED_DOB)
            .email(UPDATED_EMAIL)
            .mobileNo(UPDATED_MOBILE_NO)
            .houseOwner(UPDATED_HOUSE_OWNER)
            .occupation(UPDATED_OCCUPATION)
            .employerNameAdd(UPDATED_EMPLOYER_NAME_ADD)
            .soclibilAmt(UPDATED_SOCLIBIL_AMT)
            .soclibilType(UPDATED_SOCLIBIL_TYPE)
            .otherlibilAmt(UPDATED_OTHERLIBIL_AMT)
            .otherlibilType(UPDATED_OTHERLIBIL_TYPE)
            .aadharCardNo(UPDATED_AADHAR_CARD_NO)
            .panCard(UPDATED_PAN_CARD)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .hasAdharVerified(UPDATED_HAS_ADHAR_VERIFIED)
            .hasPanVerified(UPDATED_HAS_PAN_VERIFIED)
            .numberOfAssets(UPDATED_NUMBER_OF_ASSETS)
            .grossAnnualInc(UPDATED_GROSS_ANNUAL_INC)
            .netIncome(UPDATED_NET_INCOME)
            .isIncomeTaxPayer(UPDATED_IS_INCOME_TAX_PAYER)
            .isActive(UPDATED_IS_ACTIVE)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .address(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .profileUrl(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        return guarantor;
    }

    @BeforeEach
    public void initTest() {
        guarantor = createEntity(em);
    }

    @Test
    @Transactional
    void createGuarantor() throws Exception {
        int databaseSizeBeforeCreate = guarantorRepository.findAll().size();
        // Create the Guarantor
        GuarantorDTO guarantorDTO = guarantorMapper.toDto(guarantor);
        restGuarantorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guarantorDTO)))
            .andExpect(status().isCreated());

        // Validate the Guarantor in the database
        List<Guarantor> guarantorList = guarantorRepository.findAll();
        assertThat(guarantorList).hasSize(databaseSizeBeforeCreate + 1);
        Guarantor testGuarantor = guarantorList.get(guarantorList.size() - 1);
        assertThat(testGuarantor.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testGuarantor.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testGuarantor.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testGuarantor.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testGuarantor.getMembershipNo()).isEqualTo(DEFAULT_MEMBERSHIP_NO);
        assertThat(testGuarantor.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testGuarantor.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testGuarantor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testGuarantor.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testGuarantor.getHouseOwner()).isEqualTo(DEFAULT_HOUSE_OWNER);
        assertThat(testGuarantor.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testGuarantor.getEmployerNameAdd()).isEqualTo(DEFAULT_EMPLOYER_NAME_ADD);
        assertThat(testGuarantor.getSoclibilAmt()).isEqualTo(DEFAULT_SOCLIBIL_AMT);
        assertThat(testGuarantor.getSoclibilType()).isEqualTo(DEFAULT_SOCLIBIL_TYPE);
        assertThat(testGuarantor.getOtherlibilAmt()).isEqualTo(DEFAULT_OTHERLIBIL_AMT);
        assertThat(testGuarantor.getOtherlibilType()).isEqualTo(DEFAULT_OTHERLIBIL_TYPE);
        assertThat(testGuarantor.getAadharCardNo()).isEqualTo(DEFAULT_AADHAR_CARD_NO);
        assertThat(testGuarantor.getPanCard()).isEqualTo(DEFAULT_PAN_CARD);
        assertThat(testGuarantor.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testGuarantor.getHasAdharVerified()).isEqualTo(DEFAULT_HAS_ADHAR_VERIFIED);
        assertThat(testGuarantor.getHasPanVerified()).isEqualTo(DEFAULT_HAS_PAN_VERIFIED);
        assertThat(testGuarantor.getNumberOfAssets()).isEqualTo(DEFAULT_NUMBER_OF_ASSETS);
        assertThat(testGuarantor.getGrossAnnualInc()).isEqualTo(DEFAULT_GROSS_ANNUAL_INC);
        assertThat(testGuarantor.getNetIncome()).isEqualTo(DEFAULT_NET_INCOME);
        assertThat(testGuarantor.getIsIncomeTaxPayer()).isEqualTo(DEFAULT_IS_INCOME_TAX_PAYER);
        assertThat(testGuarantor.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testGuarantor.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testGuarantor.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testGuarantor.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testGuarantor.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testGuarantor.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testGuarantor.getAddress()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testGuarantor.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testGuarantor.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testGuarantor.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testGuarantor.getProfileUrl()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testGuarantor.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createGuarantorWithExistingId() throws Exception {
        // Create the Guarantor with an existing ID
        guarantor.setId(1L);
        GuarantorDTO guarantorDTO = guarantorMapper.toDto(guarantor);

        int databaseSizeBeforeCreate = guarantorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuarantorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guarantorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Guarantor in the database
        List<Guarantor> guarantorList = guarantorRepository.findAll();
        assertThat(guarantorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGuarantors() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList
        restGuarantorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guarantor.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].membershipNo").value(hasItem(DEFAULT_MEMBERSHIP_NO)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].houseOwner").value(hasItem(DEFAULT_HOUSE_OWNER)))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
            .andExpect(jsonPath("$.[*].employerNameAdd").value(hasItem(DEFAULT_EMPLOYER_NAME_ADD)))
            .andExpect(jsonPath("$.[*].soclibilAmt").value(hasItem(DEFAULT_SOCLIBIL_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].soclibilType").value(hasItem(DEFAULT_SOCLIBIL_TYPE)))
            .andExpect(jsonPath("$.[*].otherlibilAmt").value(hasItem(DEFAULT_OTHERLIBIL_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].otherlibilType").value(hasItem(DEFAULT_OTHERLIBIL_TYPE)))
            .andExpect(jsonPath("$.[*].aadharCardNo").value(hasItem(DEFAULT_AADHAR_CARD_NO)))
            .andExpect(jsonPath("$.[*].panCard").value(hasItem(DEFAULT_PAN_CARD)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].hasAdharVerified").value(hasItem(DEFAULT_HAS_ADHAR_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].hasPanVerified").value(hasItem(DEFAULT_HAS_PAN_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].numberOfAssets").value(hasItem(DEFAULT_NUMBER_OF_ASSETS)))
            .andExpect(jsonPath("$.[*].grossAnnualInc").value(hasItem(DEFAULT_GROSS_ANNUAL_INC.doubleValue())))
            .andExpect(jsonPath("$.[*].netIncome").value(hasItem(DEFAULT_NET_INCOME.doubleValue())))
            .andExpect(jsonPath("$.[*].isIncomeTaxPayer").value(hasItem(DEFAULT_IS_INCOME_TAX_PAYER.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].profileUrl").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));
    }

    @Test
    @Transactional
    void getGuarantor() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get the guarantor
        restGuarantorMockMvc
            .perform(get(ENTITY_API_URL_ID, guarantor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(guarantor.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.membershipNo").value(DEFAULT_MEMBERSHIP_NO))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.houseOwner").value(DEFAULT_HOUSE_OWNER))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION.toString()))
            .andExpect(jsonPath("$.employerNameAdd").value(DEFAULT_EMPLOYER_NAME_ADD))
            .andExpect(jsonPath("$.soclibilAmt").value(DEFAULT_SOCLIBIL_AMT.doubleValue()))
            .andExpect(jsonPath("$.soclibilType").value(DEFAULT_SOCLIBIL_TYPE))
            .andExpect(jsonPath("$.otherlibilAmt").value(DEFAULT_OTHERLIBIL_AMT.doubleValue()))
            .andExpect(jsonPath("$.otherlibilType").value(DEFAULT_OTHERLIBIL_TYPE))
            .andExpect(jsonPath("$.aadharCardNo").value(DEFAULT_AADHAR_CARD_NO))
            .andExpect(jsonPath("$.panCard").value(DEFAULT_PAN_CARD))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.hasAdharVerified").value(DEFAULT_HAS_ADHAR_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.hasPanVerified").value(DEFAULT_HAS_PAN_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.numberOfAssets").value(DEFAULT_NUMBER_OF_ASSETS))
            .andExpect(jsonPath("$.grossAnnualInc").value(DEFAULT_GROSS_ANNUAL_INC.doubleValue()))
            .andExpect(jsonPath("$.netIncome").value(DEFAULT_NET_INCOME.doubleValue()))
            .andExpect(jsonPath("$.isIncomeTaxPayer").value(DEFAULT_IS_INCOME_TAX_PAYER.booleanValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.profileUrl").value(DEFAULT_FREE_FIELD_5))
            .andExpect(jsonPath("$.freeField6").value(DEFAULT_FREE_FIELD_6));
    }

    @Test
    @Transactional
    void getGuarantorsByIdFiltering() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        Long id = guarantor.getId();

        defaultGuarantorShouldBeFound("id.equals=" + id);
        defaultGuarantorShouldNotBeFound("id.notEquals=" + id);

        defaultGuarantorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGuarantorShouldNotBeFound("id.greaterThan=" + id);

        defaultGuarantorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGuarantorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllGuarantorsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where title equals to DEFAULT_TITLE
        defaultGuarantorShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the guarantorList where title equals to UPDATED_TITLE
        defaultGuarantorShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllGuarantorsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultGuarantorShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the guarantorList where title equals to UPDATED_TITLE
        defaultGuarantorShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllGuarantorsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where title is not null
        defaultGuarantorShouldBeFound("title.specified=true");

        // Get all the guarantorList where title is null
        defaultGuarantorShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where firstName equals to DEFAULT_FIRST_NAME
        defaultGuarantorShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the guarantorList where firstName equals to UPDATED_FIRST_NAME
        defaultGuarantorShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultGuarantorShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the guarantorList where firstName equals to UPDATED_FIRST_NAME
        defaultGuarantorShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where firstName is not null
        defaultGuarantorShouldBeFound("firstName.specified=true");

        // Get all the guarantorList where firstName is null
        defaultGuarantorShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where firstName contains DEFAULT_FIRST_NAME
        defaultGuarantorShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the guarantorList where firstName contains UPDATED_FIRST_NAME
        defaultGuarantorShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where firstName does not contain DEFAULT_FIRST_NAME
        defaultGuarantorShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the guarantorList where firstName does not contain UPDATED_FIRST_NAME
        defaultGuarantorShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultGuarantorShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the guarantorList where middleName equals to UPDATED_MIDDLE_NAME
        defaultGuarantorShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultGuarantorShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the guarantorList where middleName equals to UPDATED_MIDDLE_NAME
        defaultGuarantorShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where middleName is not null
        defaultGuarantorShouldBeFound("middleName.specified=true");

        // Get all the guarantorList where middleName is null
        defaultGuarantorShouldNotBeFound("middleName.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where middleName contains DEFAULT_MIDDLE_NAME
        defaultGuarantorShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the guarantorList where middleName contains UPDATED_MIDDLE_NAME
        defaultGuarantorShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultGuarantorShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the guarantorList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultGuarantorShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where lastName equals to DEFAULT_LAST_NAME
        defaultGuarantorShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the guarantorList where lastName equals to UPDATED_LAST_NAME
        defaultGuarantorShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultGuarantorShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the guarantorList where lastName equals to UPDATED_LAST_NAME
        defaultGuarantorShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where lastName is not null
        defaultGuarantorShouldBeFound("lastName.specified=true");

        // Get all the guarantorList where lastName is null
        defaultGuarantorShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByLastNameContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where lastName contains DEFAULT_LAST_NAME
        defaultGuarantorShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the guarantorList where lastName contains UPDATED_LAST_NAME
        defaultGuarantorShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where lastName does not contain DEFAULT_LAST_NAME
        defaultGuarantorShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the guarantorList where lastName does not contain UPDATED_LAST_NAME
        defaultGuarantorShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMembershipNoIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where membershipNo equals to DEFAULT_MEMBERSHIP_NO
        defaultGuarantorShouldBeFound("membershipNo.equals=" + DEFAULT_MEMBERSHIP_NO);

        // Get all the guarantorList where membershipNo equals to UPDATED_MEMBERSHIP_NO
        defaultGuarantorShouldNotBeFound("membershipNo.equals=" + UPDATED_MEMBERSHIP_NO);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMembershipNoIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where membershipNo in DEFAULT_MEMBERSHIP_NO or UPDATED_MEMBERSHIP_NO
        defaultGuarantorShouldBeFound("membershipNo.in=" + DEFAULT_MEMBERSHIP_NO + "," + UPDATED_MEMBERSHIP_NO);

        // Get all the guarantorList where membershipNo equals to UPDATED_MEMBERSHIP_NO
        defaultGuarantorShouldNotBeFound("membershipNo.in=" + UPDATED_MEMBERSHIP_NO);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMembershipNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where membershipNo is not null
        defaultGuarantorShouldBeFound("membershipNo.specified=true");

        // Get all the guarantorList where membershipNo is null
        defaultGuarantorShouldNotBeFound("membershipNo.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByMembershipNoContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where membershipNo contains DEFAULT_MEMBERSHIP_NO
        defaultGuarantorShouldBeFound("membershipNo.contains=" + DEFAULT_MEMBERSHIP_NO);

        // Get all the guarantorList where membershipNo contains UPDATED_MEMBERSHIP_NO
        defaultGuarantorShouldNotBeFound("membershipNo.contains=" + UPDATED_MEMBERSHIP_NO);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMembershipNoNotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where membershipNo does not contain DEFAULT_MEMBERSHIP_NO
        defaultGuarantorShouldNotBeFound("membershipNo.doesNotContain=" + DEFAULT_MEMBERSHIP_NO);

        // Get all the guarantorList where membershipNo does not contain UPDATED_MEMBERSHIP_NO
        defaultGuarantorShouldBeFound("membershipNo.doesNotContain=" + UPDATED_MEMBERSHIP_NO);
    }

    @Test
    @Transactional
    void getAllGuarantorsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where gender equals to DEFAULT_GENDER
        defaultGuarantorShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the guarantorList where gender equals to UPDATED_GENDER
        defaultGuarantorShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllGuarantorsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultGuarantorShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the guarantorList where gender equals to UPDATED_GENDER
        defaultGuarantorShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllGuarantorsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where gender is not null
        defaultGuarantorShouldBeFound("gender.specified=true");

        // Get all the guarantorList where gender is null
        defaultGuarantorShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where dob equals to DEFAULT_DOB
        defaultGuarantorShouldBeFound("dob.equals=" + DEFAULT_DOB);

        // Get all the guarantorList where dob equals to UPDATED_DOB
        defaultGuarantorShouldNotBeFound("dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllGuarantorsByDobIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where dob in DEFAULT_DOB or UPDATED_DOB
        defaultGuarantorShouldBeFound("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB);

        // Get all the guarantorList where dob equals to UPDATED_DOB
        defaultGuarantorShouldNotBeFound("dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllGuarantorsByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where dob is not null
        defaultGuarantorShouldBeFound("dob.specified=true");

        // Get all the guarantorList where dob is null
        defaultGuarantorShouldNotBeFound("dob.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByDobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where dob is greater than or equal to DEFAULT_DOB
        defaultGuarantorShouldBeFound("dob.greaterThanOrEqual=" + DEFAULT_DOB);

        // Get all the guarantorList where dob is greater than or equal to UPDATED_DOB
        defaultGuarantorShouldNotBeFound("dob.greaterThanOrEqual=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllGuarantorsByDobIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where dob is less than or equal to DEFAULT_DOB
        defaultGuarantorShouldBeFound("dob.lessThanOrEqual=" + DEFAULT_DOB);

        // Get all the guarantorList where dob is less than or equal to SMALLER_DOB
        defaultGuarantorShouldNotBeFound("dob.lessThanOrEqual=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllGuarantorsByDobIsLessThanSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where dob is less than DEFAULT_DOB
        defaultGuarantorShouldNotBeFound("dob.lessThan=" + DEFAULT_DOB);

        // Get all the guarantorList where dob is less than UPDATED_DOB
        defaultGuarantorShouldBeFound("dob.lessThan=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllGuarantorsByDobIsGreaterThanSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where dob is greater than DEFAULT_DOB
        defaultGuarantorShouldNotBeFound("dob.greaterThan=" + DEFAULT_DOB);

        // Get all the guarantorList where dob is greater than SMALLER_DOB
        defaultGuarantorShouldBeFound("dob.greaterThan=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllGuarantorsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where email equals to DEFAULT_EMAIL
        defaultGuarantorShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the guarantorList where email equals to UPDATED_EMAIL
        defaultGuarantorShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllGuarantorsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultGuarantorShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the guarantorList where email equals to UPDATED_EMAIL
        defaultGuarantorShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllGuarantorsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where email is not null
        defaultGuarantorShouldBeFound("email.specified=true");

        // Get all the guarantorList where email is null
        defaultGuarantorShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByEmailContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where email contains DEFAULT_EMAIL
        defaultGuarantorShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the guarantorList where email contains UPDATED_EMAIL
        defaultGuarantorShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllGuarantorsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where email does not contain DEFAULT_EMAIL
        defaultGuarantorShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the guarantorList where email does not contain UPDATED_EMAIL
        defaultGuarantorShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultGuarantorShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the guarantorList where mobileNo equals to UPDATED_MOBILE_NO
        defaultGuarantorShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultGuarantorShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the guarantorList where mobileNo equals to UPDATED_MOBILE_NO
        defaultGuarantorShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where mobileNo is not null
        defaultGuarantorShouldBeFound("mobileNo.specified=true");

        // Get all the guarantorList where mobileNo is null
        defaultGuarantorShouldNotBeFound("mobileNo.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByMobileNoContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where mobileNo contains DEFAULT_MOBILE_NO
        defaultGuarantorShouldBeFound("mobileNo.contains=" + DEFAULT_MOBILE_NO);

        // Get all the guarantorList where mobileNo contains UPDATED_MOBILE_NO
        defaultGuarantorShouldNotBeFound("mobileNo.contains=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMobileNoNotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where mobileNo does not contain DEFAULT_MOBILE_NO
        defaultGuarantorShouldNotBeFound("mobileNo.doesNotContain=" + DEFAULT_MOBILE_NO);

        // Get all the guarantorList where mobileNo does not contain UPDATED_MOBILE_NO
        defaultGuarantorShouldBeFound("mobileNo.doesNotContain=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllGuarantorsByHouseOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where houseOwner equals to DEFAULT_HOUSE_OWNER
        defaultGuarantorShouldBeFound("houseOwner.equals=" + DEFAULT_HOUSE_OWNER);

        // Get all the guarantorList where houseOwner equals to UPDATED_HOUSE_OWNER
        defaultGuarantorShouldNotBeFound("houseOwner.equals=" + UPDATED_HOUSE_OWNER);
    }

    @Test
    @Transactional
    void getAllGuarantorsByHouseOwnerIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where houseOwner in DEFAULT_HOUSE_OWNER or UPDATED_HOUSE_OWNER
        defaultGuarantorShouldBeFound("houseOwner.in=" + DEFAULT_HOUSE_OWNER + "," + UPDATED_HOUSE_OWNER);

        // Get all the guarantorList where houseOwner equals to UPDATED_HOUSE_OWNER
        defaultGuarantorShouldNotBeFound("houseOwner.in=" + UPDATED_HOUSE_OWNER);
    }

    @Test
    @Transactional
    void getAllGuarantorsByHouseOwnerIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where houseOwner is not null
        defaultGuarantorShouldBeFound("houseOwner.specified=true");

        // Get all the guarantorList where houseOwner is null
        defaultGuarantorShouldNotBeFound("houseOwner.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByHouseOwnerContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where houseOwner contains DEFAULT_HOUSE_OWNER
        defaultGuarantorShouldBeFound("houseOwner.contains=" + DEFAULT_HOUSE_OWNER);

        // Get all the guarantorList where houseOwner contains UPDATED_HOUSE_OWNER
        defaultGuarantorShouldNotBeFound("houseOwner.contains=" + UPDATED_HOUSE_OWNER);
    }

    @Test
    @Transactional
    void getAllGuarantorsByHouseOwnerNotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where houseOwner does not contain DEFAULT_HOUSE_OWNER
        defaultGuarantorShouldNotBeFound("houseOwner.doesNotContain=" + DEFAULT_HOUSE_OWNER);

        // Get all the guarantorList where houseOwner does not contain UPDATED_HOUSE_OWNER
        defaultGuarantorShouldBeFound("houseOwner.doesNotContain=" + UPDATED_HOUSE_OWNER);
    }

    @Test
    @Transactional
    void getAllGuarantorsByOccupationIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where occupation equals to DEFAULT_OCCUPATION
        defaultGuarantorShouldBeFound("occupation.equals=" + DEFAULT_OCCUPATION);

        // Get all the guarantorList where occupation equals to UPDATED_OCCUPATION
        defaultGuarantorShouldNotBeFound("occupation.equals=" + UPDATED_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllGuarantorsByOccupationIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where occupation in DEFAULT_OCCUPATION or UPDATED_OCCUPATION
        defaultGuarantorShouldBeFound("occupation.in=" + DEFAULT_OCCUPATION + "," + UPDATED_OCCUPATION);

        // Get all the guarantorList where occupation equals to UPDATED_OCCUPATION
        defaultGuarantorShouldNotBeFound("occupation.in=" + UPDATED_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllGuarantorsByOccupationIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where occupation is not null
        defaultGuarantorShouldBeFound("occupation.specified=true");

        // Get all the guarantorList where occupation is null
        defaultGuarantorShouldNotBeFound("occupation.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByEmployerNameAddIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where employerNameAdd equals to DEFAULT_EMPLOYER_NAME_ADD
        defaultGuarantorShouldBeFound("employerNameAdd.equals=" + DEFAULT_EMPLOYER_NAME_ADD);

        // Get all the guarantorList where employerNameAdd equals to UPDATED_EMPLOYER_NAME_ADD
        defaultGuarantorShouldNotBeFound("employerNameAdd.equals=" + UPDATED_EMPLOYER_NAME_ADD);
    }

    @Test
    @Transactional
    void getAllGuarantorsByEmployerNameAddIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where employerNameAdd in DEFAULT_EMPLOYER_NAME_ADD or UPDATED_EMPLOYER_NAME_ADD
        defaultGuarantorShouldBeFound("employerNameAdd.in=" + DEFAULT_EMPLOYER_NAME_ADD + "," + UPDATED_EMPLOYER_NAME_ADD);

        // Get all the guarantorList where employerNameAdd equals to UPDATED_EMPLOYER_NAME_ADD
        defaultGuarantorShouldNotBeFound("employerNameAdd.in=" + UPDATED_EMPLOYER_NAME_ADD);
    }

    @Test
    @Transactional
    void getAllGuarantorsByEmployerNameAddIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where employerNameAdd is not null
        defaultGuarantorShouldBeFound("employerNameAdd.specified=true");

        // Get all the guarantorList where employerNameAdd is null
        defaultGuarantorShouldNotBeFound("employerNameAdd.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByEmployerNameAddContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where employerNameAdd contains DEFAULT_EMPLOYER_NAME_ADD
        defaultGuarantorShouldBeFound("employerNameAdd.contains=" + DEFAULT_EMPLOYER_NAME_ADD);

        // Get all the guarantorList where employerNameAdd contains UPDATED_EMPLOYER_NAME_ADD
        defaultGuarantorShouldNotBeFound("employerNameAdd.contains=" + UPDATED_EMPLOYER_NAME_ADD);
    }

    @Test
    @Transactional
    void getAllGuarantorsByEmployerNameAddNotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where employerNameAdd does not contain DEFAULT_EMPLOYER_NAME_ADD
        defaultGuarantorShouldNotBeFound("employerNameAdd.doesNotContain=" + DEFAULT_EMPLOYER_NAME_ADD);

        // Get all the guarantorList where employerNameAdd does not contain UPDATED_EMPLOYER_NAME_ADD
        defaultGuarantorShouldBeFound("employerNameAdd.doesNotContain=" + UPDATED_EMPLOYER_NAME_ADD);
    }

    @Test
    @Transactional
    void getAllGuarantorsBySoclibilAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where soclibilAmt equals to DEFAULT_SOCLIBIL_AMT
        defaultGuarantorShouldBeFound("soclibilAmt.equals=" + DEFAULT_SOCLIBIL_AMT);

        // Get all the guarantorList where soclibilAmt equals to UPDATED_SOCLIBIL_AMT
        defaultGuarantorShouldNotBeFound("soclibilAmt.equals=" + UPDATED_SOCLIBIL_AMT);
    }

    @Test
    @Transactional
    void getAllGuarantorsBySoclibilAmtIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where soclibilAmt in DEFAULT_SOCLIBIL_AMT or UPDATED_SOCLIBIL_AMT
        defaultGuarantorShouldBeFound("soclibilAmt.in=" + DEFAULT_SOCLIBIL_AMT + "," + UPDATED_SOCLIBIL_AMT);

        // Get all the guarantorList where soclibilAmt equals to UPDATED_SOCLIBIL_AMT
        defaultGuarantorShouldNotBeFound("soclibilAmt.in=" + UPDATED_SOCLIBIL_AMT);
    }

    @Test
    @Transactional
    void getAllGuarantorsBySoclibilAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where soclibilAmt is not null
        defaultGuarantorShouldBeFound("soclibilAmt.specified=true");

        // Get all the guarantorList where soclibilAmt is null
        defaultGuarantorShouldNotBeFound("soclibilAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsBySoclibilAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where soclibilAmt is greater than or equal to DEFAULT_SOCLIBIL_AMT
        defaultGuarantorShouldBeFound("soclibilAmt.greaterThanOrEqual=" + DEFAULT_SOCLIBIL_AMT);

        // Get all the guarantorList where soclibilAmt is greater than or equal to UPDATED_SOCLIBIL_AMT
        defaultGuarantorShouldNotBeFound("soclibilAmt.greaterThanOrEqual=" + UPDATED_SOCLIBIL_AMT);
    }

    @Test
    @Transactional
    void getAllGuarantorsBySoclibilAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where soclibilAmt is less than or equal to DEFAULT_SOCLIBIL_AMT
        defaultGuarantorShouldBeFound("soclibilAmt.lessThanOrEqual=" + DEFAULT_SOCLIBIL_AMT);

        // Get all the guarantorList where soclibilAmt is less than or equal to SMALLER_SOCLIBIL_AMT
        defaultGuarantorShouldNotBeFound("soclibilAmt.lessThanOrEqual=" + SMALLER_SOCLIBIL_AMT);
    }

    @Test
    @Transactional
    void getAllGuarantorsBySoclibilAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where soclibilAmt is less than DEFAULT_SOCLIBIL_AMT
        defaultGuarantorShouldNotBeFound("soclibilAmt.lessThan=" + DEFAULT_SOCLIBIL_AMT);

        // Get all the guarantorList where soclibilAmt is less than UPDATED_SOCLIBIL_AMT
        defaultGuarantorShouldBeFound("soclibilAmt.lessThan=" + UPDATED_SOCLIBIL_AMT);
    }

    @Test
    @Transactional
    void getAllGuarantorsBySoclibilAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where soclibilAmt is greater than DEFAULT_SOCLIBIL_AMT
        defaultGuarantorShouldNotBeFound("soclibilAmt.greaterThan=" + DEFAULT_SOCLIBIL_AMT);

        // Get all the guarantorList where soclibilAmt is greater than SMALLER_SOCLIBIL_AMT
        defaultGuarantorShouldBeFound("soclibilAmt.greaterThan=" + SMALLER_SOCLIBIL_AMT);
    }

    @Test
    @Transactional
    void getAllGuarantorsBySoclibilTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where soclibilType equals to DEFAULT_SOCLIBIL_TYPE
        defaultGuarantorShouldBeFound("soclibilType.equals=" + DEFAULT_SOCLIBIL_TYPE);

        // Get all the guarantorList where soclibilType equals to UPDATED_SOCLIBIL_TYPE
        defaultGuarantorShouldNotBeFound("soclibilType.equals=" + UPDATED_SOCLIBIL_TYPE);
    }

    @Test
    @Transactional
    void getAllGuarantorsBySoclibilTypeIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where soclibilType in DEFAULT_SOCLIBIL_TYPE or UPDATED_SOCLIBIL_TYPE
        defaultGuarantorShouldBeFound("soclibilType.in=" + DEFAULT_SOCLIBIL_TYPE + "," + UPDATED_SOCLIBIL_TYPE);

        // Get all the guarantorList where soclibilType equals to UPDATED_SOCLIBIL_TYPE
        defaultGuarantorShouldNotBeFound("soclibilType.in=" + UPDATED_SOCLIBIL_TYPE);
    }

    @Test
    @Transactional
    void getAllGuarantorsBySoclibilTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where soclibilType is not null
        defaultGuarantorShouldBeFound("soclibilType.specified=true");

        // Get all the guarantorList where soclibilType is null
        defaultGuarantorShouldNotBeFound("soclibilType.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsBySoclibilTypeContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where soclibilType contains DEFAULT_SOCLIBIL_TYPE
        defaultGuarantorShouldBeFound("soclibilType.contains=" + DEFAULT_SOCLIBIL_TYPE);

        // Get all the guarantorList where soclibilType contains UPDATED_SOCLIBIL_TYPE
        defaultGuarantorShouldNotBeFound("soclibilType.contains=" + UPDATED_SOCLIBIL_TYPE);
    }

    @Test
    @Transactional
    void getAllGuarantorsBySoclibilTypeNotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where soclibilType does not contain DEFAULT_SOCLIBIL_TYPE
        defaultGuarantorShouldNotBeFound("soclibilType.doesNotContain=" + DEFAULT_SOCLIBIL_TYPE);

        // Get all the guarantorList where soclibilType does not contain UPDATED_SOCLIBIL_TYPE
        defaultGuarantorShouldBeFound("soclibilType.doesNotContain=" + UPDATED_SOCLIBIL_TYPE);
    }

    @Test
    @Transactional
    void getAllGuarantorsByOtherlibilAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where otherlibilAmt equals to DEFAULT_OTHERLIBIL_AMT
        defaultGuarantorShouldBeFound("otherlibilAmt.equals=" + DEFAULT_OTHERLIBIL_AMT);

        // Get all the guarantorList where otherlibilAmt equals to UPDATED_OTHERLIBIL_AMT
        defaultGuarantorShouldNotBeFound("otherlibilAmt.equals=" + UPDATED_OTHERLIBIL_AMT);
    }

    @Test
    @Transactional
    void getAllGuarantorsByOtherlibilAmtIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where otherlibilAmt in DEFAULT_OTHERLIBIL_AMT or UPDATED_OTHERLIBIL_AMT
        defaultGuarantorShouldBeFound("otherlibilAmt.in=" + DEFAULT_OTHERLIBIL_AMT + "," + UPDATED_OTHERLIBIL_AMT);

        // Get all the guarantorList where otherlibilAmt equals to UPDATED_OTHERLIBIL_AMT
        defaultGuarantorShouldNotBeFound("otherlibilAmt.in=" + UPDATED_OTHERLIBIL_AMT);
    }

    @Test
    @Transactional
    void getAllGuarantorsByOtherlibilAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where otherlibilAmt is not null
        defaultGuarantorShouldBeFound("otherlibilAmt.specified=true");

        // Get all the guarantorList where otherlibilAmt is null
        defaultGuarantorShouldNotBeFound("otherlibilAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByOtherlibilAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where otherlibilAmt is greater than or equal to DEFAULT_OTHERLIBIL_AMT
        defaultGuarantorShouldBeFound("otherlibilAmt.greaterThanOrEqual=" + DEFAULT_OTHERLIBIL_AMT);

        // Get all the guarantorList where otherlibilAmt is greater than or equal to UPDATED_OTHERLIBIL_AMT
        defaultGuarantorShouldNotBeFound("otherlibilAmt.greaterThanOrEqual=" + UPDATED_OTHERLIBIL_AMT);
    }

    @Test
    @Transactional
    void getAllGuarantorsByOtherlibilAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where otherlibilAmt is less than or equal to DEFAULT_OTHERLIBIL_AMT
        defaultGuarantorShouldBeFound("otherlibilAmt.lessThanOrEqual=" + DEFAULT_OTHERLIBIL_AMT);

        // Get all the guarantorList where otherlibilAmt is less than or equal to SMALLER_OTHERLIBIL_AMT
        defaultGuarantorShouldNotBeFound("otherlibilAmt.lessThanOrEqual=" + SMALLER_OTHERLIBIL_AMT);
    }

    @Test
    @Transactional
    void getAllGuarantorsByOtherlibilAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where otherlibilAmt is less than DEFAULT_OTHERLIBIL_AMT
        defaultGuarantorShouldNotBeFound("otherlibilAmt.lessThan=" + DEFAULT_OTHERLIBIL_AMT);

        // Get all the guarantorList where otherlibilAmt is less than UPDATED_OTHERLIBIL_AMT
        defaultGuarantorShouldBeFound("otherlibilAmt.lessThan=" + UPDATED_OTHERLIBIL_AMT);
    }

    @Test
    @Transactional
    void getAllGuarantorsByOtherlibilAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where otherlibilAmt is greater than DEFAULT_OTHERLIBIL_AMT
        defaultGuarantorShouldNotBeFound("otherlibilAmt.greaterThan=" + DEFAULT_OTHERLIBIL_AMT);

        // Get all the guarantorList where otherlibilAmt is greater than SMALLER_OTHERLIBIL_AMT
        defaultGuarantorShouldBeFound("otherlibilAmt.greaterThan=" + SMALLER_OTHERLIBIL_AMT);
    }

    @Test
    @Transactional
    void getAllGuarantorsByOtherlibilTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where otherlibilType equals to DEFAULT_OTHERLIBIL_TYPE
        defaultGuarantorShouldBeFound("otherlibilType.equals=" + DEFAULT_OTHERLIBIL_TYPE);

        // Get all the guarantorList where otherlibilType equals to UPDATED_OTHERLIBIL_TYPE
        defaultGuarantorShouldNotBeFound("otherlibilType.equals=" + UPDATED_OTHERLIBIL_TYPE);
    }

    @Test
    @Transactional
    void getAllGuarantorsByOtherlibilTypeIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where otherlibilType in DEFAULT_OTHERLIBIL_TYPE or UPDATED_OTHERLIBIL_TYPE
        defaultGuarantorShouldBeFound("otherlibilType.in=" + DEFAULT_OTHERLIBIL_TYPE + "," + UPDATED_OTHERLIBIL_TYPE);

        // Get all the guarantorList where otherlibilType equals to UPDATED_OTHERLIBIL_TYPE
        defaultGuarantorShouldNotBeFound("otherlibilType.in=" + UPDATED_OTHERLIBIL_TYPE);
    }

    @Test
    @Transactional
    void getAllGuarantorsByOtherlibilTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where otherlibilType is not null
        defaultGuarantorShouldBeFound("otherlibilType.specified=true");

        // Get all the guarantorList where otherlibilType is null
        defaultGuarantorShouldNotBeFound("otherlibilType.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByOtherlibilTypeContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where otherlibilType contains DEFAULT_OTHERLIBIL_TYPE
        defaultGuarantorShouldBeFound("otherlibilType.contains=" + DEFAULT_OTHERLIBIL_TYPE);

        // Get all the guarantorList where otherlibilType contains UPDATED_OTHERLIBIL_TYPE
        defaultGuarantorShouldNotBeFound("otherlibilType.contains=" + UPDATED_OTHERLIBIL_TYPE);
    }

    @Test
    @Transactional
    void getAllGuarantorsByOtherlibilTypeNotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where otherlibilType does not contain DEFAULT_OTHERLIBIL_TYPE
        defaultGuarantorShouldNotBeFound("otherlibilType.doesNotContain=" + DEFAULT_OTHERLIBIL_TYPE);

        // Get all the guarantorList where otherlibilType does not contain UPDATED_OTHERLIBIL_TYPE
        defaultGuarantorShouldBeFound("otherlibilType.doesNotContain=" + UPDATED_OTHERLIBIL_TYPE);
    }

    @Test
    @Transactional
    void getAllGuarantorsByAadharCardNoIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where aadharCardNo equals to DEFAULT_AADHAR_CARD_NO
        defaultGuarantorShouldBeFound("aadharCardNo.equals=" + DEFAULT_AADHAR_CARD_NO);

        // Get all the guarantorList where aadharCardNo equals to UPDATED_AADHAR_CARD_NO
        defaultGuarantorShouldNotBeFound("aadharCardNo.equals=" + UPDATED_AADHAR_CARD_NO);
    }

    @Test
    @Transactional
    void getAllGuarantorsByAadharCardNoIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where aadharCardNo in DEFAULT_AADHAR_CARD_NO or UPDATED_AADHAR_CARD_NO
        defaultGuarantorShouldBeFound("aadharCardNo.in=" + DEFAULT_AADHAR_CARD_NO + "," + UPDATED_AADHAR_CARD_NO);

        // Get all the guarantorList where aadharCardNo equals to UPDATED_AADHAR_CARD_NO
        defaultGuarantorShouldNotBeFound("aadharCardNo.in=" + UPDATED_AADHAR_CARD_NO);
    }

    @Test
    @Transactional
    void getAllGuarantorsByAadharCardNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where aadharCardNo is not null
        defaultGuarantorShouldBeFound("aadharCardNo.specified=true");

        // Get all the guarantorList where aadharCardNo is null
        defaultGuarantorShouldNotBeFound("aadharCardNo.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByAadharCardNoContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where aadharCardNo contains DEFAULT_AADHAR_CARD_NO
        defaultGuarantorShouldBeFound("aadharCardNo.contains=" + DEFAULT_AADHAR_CARD_NO);

        // Get all the guarantorList where aadharCardNo contains UPDATED_AADHAR_CARD_NO
        defaultGuarantorShouldNotBeFound("aadharCardNo.contains=" + UPDATED_AADHAR_CARD_NO);
    }

    @Test
    @Transactional
    void getAllGuarantorsByAadharCardNoNotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where aadharCardNo does not contain DEFAULT_AADHAR_CARD_NO
        defaultGuarantorShouldNotBeFound("aadharCardNo.doesNotContain=" + DEFAULT_AADHAR_CARD_NO);

        // Get all the guarantorList where aadharCardNo does not contain UPDATED_AADHAR_CARD_NO
        defaultGuarantorShouldBeFound("aadharCardNo.doesNotContain=" + UPDATED_AADHAR_CARD_NO);
    }

    @Test
    @Transactional
    void getAllGuarantorsByPanCardIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where panCard equals to DEFAULT_PAN_CARD
        defaultGuarantorShouldBeFound("panCard.equals=" + DEFAULT_PAN_CARD);

        // Get all the guarantorList where panCard equals to UPDATED_PAN_CARD
        defaultGuarantorShouldNotBeFound("panCard.equals=" + UPDATED_PAN_CARD);
    }

    @Test
    @Transactional
    void getAllGuarantorsByPanCardIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where panCard in DEFAULT_PAN_CARD or UPDATED_PAN_CARD
        defaultGuarantorShouldBeFound("panCard.in=" + DEFAULT_PAN_CARD + "," + UPDATED_PAN_CARD);

        // Get all the guarantorList where panCard equals to UPDATED_PAN_CARD
        defaultGuarantorShouldNotBeFound("panCard.in=" + UPDATED_PAN_CARD);
    }

    @Test
    @Transactional
    void getAllGuarantorsByPanCardIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where panCard is not null
        defaultGuarantorShouldBeFound("panCard.specified=true");

        // Get all the guarantorList where panCard is null
        defaultGuarantorShouldNotBeFound("panCard.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByPanCardContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where panCard contains DEFAULT_PAN_CARD
        defaultGuarantorShouldBeFound("panCard.contains=" + DEFAULT_PAN_CARD);

        // Get all the guarantorList where panCard contains UPDATED_PAN_CARD
        defaultGuarantorShouldNotBeFound("panCard.contains=" + UPDATED_PAN_CARD);
    }

    @Test
    @Transactional
    void getAllGuarantorsByPanCardNotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where panCard does not contain DEFAULT_PAN_CARD
        defaultGuarantorShouldNotBeFound("panCard.doesNotContain=" + DEFAULT_PAN_CARD);

        // Get all the guarantorList where panCard does not contain UPDATED_PAN_CARD
        defaultGuarantorShouldBeFound("panCard.doesNotContain=" + UPDATED_PAN_CARD);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where maritalStatus equals to DEFAULT_MARITAL_STATUS
        defaultGuarantorShouldBeFound("maritalStatus.equals=" + DEFAULT_MARITAL_STATUS);

        // Get all the guarantorList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultGuarantorShouldNotBeFound("maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMaritalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where maritalStatus in DEFAULT_MARITAL_STATUS or UPDATED_MARITAL_STATUS
        defaultGuarantorShouldBeFound("maritalStatus.in=" + DEFAULT_MARITAL_STATUS + "," + UPDATED_MARITAL_STATUS);

        // Get all the guarantorList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultGuarantorShouldNotBeFound("maritalStatus.in=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMaritalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where maritalStatus is not null
        defaultGuarantorShouldBeFound("maritalStatus.specified=true");

        // Get all the guarantorList where maritalStatus is null
        defaultGuarantorShouldNotBeFound("maritalStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByHasAdharVerifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where hasAdharVerified equals to DEFAULT_HAS_ADHAR_VERIFIED
        defaultGuarantorShouldBeFound("hasAdharVerified.equals=" + DEFAULT_HAS_ADHAR_VERIFIED);

        // Get all the guarantorList where hasAdharVerified equals to UPDATED_HAS_ADHAR_VERIFIED
        defaultGuarantorShouldNotBeFound("hasAdharVerified.equals=" + UPDATED_HAS_ADHAR_VERIFIED);
    }

    @Test
    @Transactional
    void getAllGuarantorsByHasAdharVerifiedIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where hasAdharVerified in DEFAULT_HAS_ADHAR_VERIFIED or UPDATED_HAS_ADHAR_VERIFIED
        defaultGuarantorShouldBeFound("hasAdharVerified.in=" + DEFAULT_HAS_ADHAR_VERIFIED + "," + UPDATED_HAS_ADHAR_VERIFIED);

        // Get all the guarantorList where hasAdharVerified equals to UPDATED_HAS_ADHAR_VERIFIED
        defaultGuarantorShouldNotBeFound("hasAdharVerified.in=" + UPDATED_HAS_ADHAR_VERIFIED);
    }

    @Test
    @Transactional
    void getAllGuarantorsByHasAdharVerifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where hasAdharVerified is not null
        defaultGuarantorShouldBeFound("hasAdharVerified.specified=true");

        // Get all the guarantorList where hasAdharVerified is null
        defaultGuarantorShouldNotBeFound("hasAdharVerified.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByHasPanVerifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where hasPanVerified equals to DEFAULT_HAS_PAN_VERIFIED
        defaultGuarantorShouldBeFound("hasPanVerified.equals=" + DEFAULT_HAS_PAN_VERIFIED);

        // Get all the guarantorList where hasPanVerified equals to UPDATED_HAS_PAN_VERIFIED
        defaultGuarantorShouldNotBeFound("hasPanVerified.equals=" + UPDATED_HAS_PAN_VERIFIED);
    }

    @Test
    @Transactional
    void getAllGuarantorsByHasPanVerifiedIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where hasPanVerified in DEFAULT_HAS_PAN_VERIFIED or UPDATED_HAS_PAN_VERIFIED
        defaultGuarantorShouldBeFound("hasPanVerified.in=" + DEFAULT_HAS_PAN_VERIFIED + "," + UPDATED_HAS_PAN_VERIFIED);

        // Get all the guarantorList where hasPanVerified equals to UPDATED_HAS_PAN_VERIFIED
        defaultGuarantorShouldNotBeFound("hasPanVerified.in=" + UPDATED_HAS_PAN_VERIFIED);
    }

    @Test
    @Transactional
    void getAllGuarantorsByHasPanVerifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where hasPanVerified is not null
        defaultGuarantorShouldBeFound("hasPanVerified.specified=true");

        // Get all the guarantorList where hasPanVerified is null
        defaultGuarantorShouldNotBeFound("hasPanVerified.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByNumberOfAssetsIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where numberOfAssets equals to DEFAULT_NUMBER_OF_ASSETS
        defaultGuarantorShouldBeFound("numberOfAssets.equals=" + DEFAULT_NUMBER_OF_ASSETS);

        // Get all the guarantorList where numberOfAssets equals to UPDATED_NUMBER_OF_ASSETS
        defaultGuarantorShouldNotBeFound("numberOfAssets.equals=" + UPDATED_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllGuarantorsByNumberOfAssetsIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where numberOfAssets in DEFAULT_NUMBER_OF_ASSETS or UPDATED_NUMBER_OF_ASSETS
        defaultGuarantorShouldBeFound("numberOfAssets.in=" + DEFAULT_NUMBER_OF_ASSETS + "," + UPDATED_NUMBER_OF_ASSETS);

        // Get all the guarantorList where numberOfAssets equals to UPDATED_NUMBER_OF_ASSETS
        defaultGuarantorShouldNotBeFound("numberOfAssets.in=" + UPDATED_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllGuarantorsByNumberOfAssetsIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where numberOfAssets is not null
        defaultGuarantorShouldBeFound("numberOfAssets.specified=true");

        // Get all the guarantorList where numberOfAssets is null
        defaultGuarantorShouldNotBeFound("numberOfAssets.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByNumberOfAssetsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where numberOfAssets is greater than or equal to DEFAULT_NUMBER_OF_ASSETS
        defaultGuarantorShouldBeFound("numberOfAssets.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_ASSETS);

        // Get all the guarantorList where numberOfAssets is greater than or equal to UPDATED_NUMBER_OF_ASSETS
        defaultGuarantorShouldNotBeFound("numberOfAssets.greaterThanOrEqual=" + UPDATED_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllGuarantorsByNumberOfAssetsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where numberOfAssets is less than or equal to DEFAULT_NUMBER_OF_ASSETS
        defaultGuarantorShouldBeFound("numberOfAssets.lessThanOrEqual=" + DEFAULT_NUMBER_OF_ASSETS);

        // Get all the guarantorList where numberOfAssets is less than or equal to SMALLER_NUMBER_OF_ASSETS
        defaultGuarantorShouldNotBeFound("numberOfAssets.lessThanOrEqual=" + SMALLER_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllGuarantorsByNumberOfAssetsIsLessThanSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where numberOfAssets is less than DEFAULT_NUMBER_OF_ASSETS
        defaultGuarantorShouldNotBeFound("numberOfAssets.lessThan=" + DEFAULT_NUMBER_OF_ASSETS);

        // Get all the guarantorList where numberOfAssets is less than UPDATED_NUMBER_OF_ASSETS
        defaultGuarantorShouldBeFound("numberOfAssets.lessThan=" + UPDATED_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllGuarantorsByNumberOfAssetsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where numberOfAssets is greater than DEFAULT_NUMBER_OF_ASSETS
        defaultGuarantorShouldNotBeFound("numberOfAssets.greaterThan=" + DEFAULT_NUMBER_OF_ASSETS);

        // Get all the guarantorList where numberOfAssets is greater than SMALLER_NUMBER_OF_ASSETS
        defaultGuarantorShouldBeFound("numberOfAssets.greaterThan=" + SMALLER_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllGuarantorsByGrossAnnualIncIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where grossAnnualInc equals to DEFAULT_GROSS_ANNUAL_INC
        defaultGuarantorShouldBeFound("grossAnnualInc.equals=" + DEFAULT_GROSS_ANNUAL_INC);

        // Get all the guarantorList where grossAnnualInc equals to UPDATED_GROSS_ANNUAL_INC
        defaultGuarantorShouldNotBeFound("grossAnnualInc.equals=" + UPDATED_GROSS_ANNUAL_INC);
    }

    @Test
    @Transactional
    void getAllGuarantorsByGrossAnnualIncIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where grossAnnualInc in DEFAULT_GROSS_ANNUAL_INC or UPDATED_GROSS_ANNUAL_INC
        defaultGuarantorShouldBeFound("grossAnnualInc.in=" + DEFAULT_GROSS_ANNUAL_INC + "," + UPDATED_GROSS_ANNUAL_INC);

        // Get all the guarantorList where grossAnnualInc equals to UPDATED_GROSS_ANNUAL_INC
        defaultGuarantorShouldNotBeFound("grossAnnualInc.in=" + UPDATED_GROSS_ANNUAL_INC);
    }

    @Test
    @Transactional
    void getAllGuarantorsByGrossAnnualIncIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where grossAnnualInc is not null
        defaultGuarantorShouldBeFound("grossAnnualInc.specified=true");

        // Get all the guarantorList where grossAnnualInc is null
        defaultGuarantorShouldNotBeFound("grossAnnualInc.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByGrossAnnualIncIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where grossAnnualInc is greater than or equal to DEFAULT_GROSS_ANNUAL_INC
        defaultGuarantorShouldBeFound("grossAnnualInc.greaterThanOrEqual=" + DEFAULT_GROSS_ANNUAL_INC);

        // Get all the guarantorList where grossAnnualInc is greater than or equal to UPDATED_GROSS_ANNUAL_INC
        defaultGuarantorShouldNotBeFound("grossAnnualInc.greaterThanOrEqual=" + UPDATED_GROSS_ANNUAL_INC);
    }

    @Test
    @Transactional
    void getAllGuarantorsByGrossAnnualIncIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where grossAnnualInc is less than or equal to DEFAULT_GROSS_ANNUAL_INC
        defaultGuarantorShouldBeFound("grossAnnualInc.lessThanOrEqual=" + DEFAULT_GROSS_ANNUAL_INC);

        // Get all the guarantorList where grossAnnualInc is less than or equal to SMALLER_GROSS_ANNUAL_INC
        defaultGuarantorShouldNotBeFound("grossAnnualInc.lessThanOrEqual=" + SMALLER_GROSS_ANNUAL_INC);
    }

    @Test
    @Transactional
    void getAllGuarantorsByGrossAnnualIncIsLessThanSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where grossAnnualInc is less than DEFAULT_GROSS_ANNUAL_INC
        defaultGuarantorShouldNotBeFound("grossAnnualInc.lessThan=" + DEFAULT_GROSS_ANNUAL_INC);

        // Get all the guarantorList where grossAnnualInc is less than UPDATED_GROSS_ANNUAL_INC
        defaultGuarantorShouldBeFound("grossAnnualInc.lessThan=" + UPDATED_GROSS_ANNUAL_INC);
    }

    @Test
    @Transactional
    void getAllGuarantorsByGrossAnnualIncIsGreaterThanSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where grossAnnualInc is greater than DEFAULT_GROSS_ANNUAL_INC
        defaultGuarantorShouldNotBeFound("grossAnnualInc.greaterThan=" + DEFAULT_GROSS_ANNUAL_INC);

        // Get all the guarantorList where grossAnnualInc is greater than SMALLER_GROSS_ANNUAL_INC
        defaultGuarantorShouldBeFound("grossAnnualInc.greaterThan=" + SMALLER_GROSS_ANNUAL_INC);
    }

    @Test
    @Transactional
    void getAllGuarantorsByNetIncomeIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where netIncome equals to DEFAULT_NET_INCOME
        defaultGuarantorShouldBeFound("netIncome.equals=" + DEFAULT_NET_INCOME);

        // Get all the guarantorList where netIncome equals to UPDATED_NET_INCOME
        defaultGuarantorShouldNotBeFound("netIncome.equals=" + UPDATED_NET_INCOME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByNetIncomeIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where netIncome in DEFAULT_NET_INCOME or UPDATED_NET_INCOME
        defaultGuarantorShouldBeFound("netIncome.in=" + DEFAULT_NET_INCOME + "," + UPDATED_NET_INCOME);

        // Get all the guarantorList where netIncome equals to UPDATED_NET_INCOME
        defaultGuarantorShouldNotBeFound("netIncome.in=" + UPDATED_NET_INCOME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByNetIncomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where netIncome is not null
        defaultGuarantorShouldBeFound("netIncome.specified=true");

        // Get all the guarantorList where netIncome is null
        defaultGuarantorShouldNotBeFound("netIncome.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByNetIncomeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where netIncome is greater than or equal to DEFAULT_NET_INCOME
        defaultGuarantorShouldBeFound("netIncome.greaterThanOrEqual=" + DEFAULT_NET_INCOME);

        // Get all the guarantorList where netIncome is greater than or equal to UPDATED_NET_INCOME
        defaultGuarantorShouldNotBeFound("netIncome.greaterThanOrEqual=" + UPDATED_NET_INCOME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByNetIncomeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where netIncome is less than or equal to DEFAULT_NET_INCOME
        defaultGuarantorShouldBeFound("netIncome.lessThanOrEqual=" + DEFAULT_NET_INCOME);

        // Get all the guarantorList where netIncome is less than or equal to SMALLER_NET_INCOME
        defaultGuarantorShouldNotBeFound("netIncome.lessThanOrEqual=" + SMALLER_NET_INCOME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByNetIncomeIsLessThanSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where netIncome is less than DEFAULT_NET_INCOME
        defaultGuarantorShouldNotBeFound("netIncome.lessThan=" + DEFAULT_NET_INCOME);

        // Get all the guarantorList where netIncome is less than UPDATED_NET_INCOME
        defaultGuarantorShouldBeFound("netIncome.lessThan=" + UPDATED_NET_INCOME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByNetIncomeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where netIncome is greater than DEFAULT_NET_INCOME
        defaultGuarantorShouldNotBeFound("netIncome.greaterThan=" + DEFAULT_NET_INCOME);

        // Get all the guarantorList where netIncome is greater than SMALLER_NET_INCOME
        defaultGuarantorShouldBeFound("netIncome.greaterThan=" + SMALLER_NET_INCOME);
    }

    @Test
    @Transactional
    void getAllGuarantorsByIsIncomeTaxPayerIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where isIncomeTaxPayer equals to DEFAULT_IS_INCOME_TAX_PAYER
        defaultGuarantorShouldBeFound("isIncomeTaxPayer.equals=" + DEFAULT_IS_INCOME_TAX_PAYER);

        // Get all the guarantorList where isIncomeTaxPayer equals to UPDATED_IS_INCOME_TAX_PAYER
        defaultGuarantorShouldNotBeFound("isIncomeTaxPayer.equals=" + UPDATED_IS_INCOME_TAX_PAYER);
    }

    @Test
    @Transactional
    void getAllGuarantorsByIsIncomeTaxPayerIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where isIncomeTaxPayer in DEFAULT_IS_INCOME_TAX_PAYER or UPDATED_IS_INCOME_TAX_PAYER
        defaultGuarantorShouldBeFound("isIncomeTaxPayer.in=" + DEFAULT_IS_INCOME_TAX_PAYER + "," + UPDATED_IS_INCOME_TAX_PAYER);

        // Get all the guarantorList where isIncomeTaxPayer equals to UPDATED_IS_INCOME_TAX_PAYER
        defaultGuarantorShouldNotBeFound("isIncomeTaxPayer.in=" + UPDATED_IS_INCOME_TAX_PAYER);
    }

    @Test
    @Transactional
    void getAllGuarantorsByIsIncomeTaxPayerIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where isIncomeTaxPayer is not null
        defaultGuarantorShouldBeFound("isIncomeTaxPayer.specified=true");

        // Get all the guarantorList where isIncomeTaxPayer is null
        defaultGuarantorShouldNotBeFound("isIncomeTaxPayer.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where isActive equals to DEFAULT_IS_ACTIVE
        defaultGuarantorShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the guarantorList where isActive equals to UPDATED_IS_ACTIVE
        defaultGuarantorShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllGuarantorsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultGuarantorShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the guarantorList where isActive equals to UPDATED_IS_ACTIVE
        defaultGuarantorShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllGuarantorsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where isActive is not null
        defaultGuarantorShouldBeFound("isActive.specified=true");

        // Get all the guarantorList where isActive is null
        defaultGuarantorShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where isDeleted equals to DEFAULT_IS_DELETED
        defaultGuarantorShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the guarantorList where isDeleted equals to UPDATED_IS_DELETED
        defaultGuarantorShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllGuarantorsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultGuarantorShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the guarantorList where isDeleted equals to UPDATED_IS_DELETED
        defaultGuarantorShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllGuarantorsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where isDeleted is not null
        defaultGuarantorShouldBeFound("isDeleted.specified=true");

        // Get all the guarantorList where isDeleted is null
        defaultGuarantorShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultGuarantorShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the guarantorList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultGuarantorShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllGuarantorsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultGuarantorShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the guarantorList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultGuarantorShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllGuarantorsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where lastModified is not null
        defaultGuarantorShouldBeFound("lastModified.specified=true");

        // Get all the guarantorList where lastModified is null
        defaultGuarantorShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultGuarantorShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the guarantorList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultGuarantorShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllGuarantorsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultGuarantorShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the guarantorList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultGuarantorShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllGuarantorsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where lastModifiedBy is not null
        defaultGuarantorShouldBeFound("lastModifiedBy.specified=true");

        // Get all the guarantorList where lastModifiedBy is null
        defaultGuarantorShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultGuarantorShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the guarantorList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultGuarantorShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllGuarantorsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultGuarantorShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the guarantorList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultGuarantorShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllGuarantorsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where createdBy equals to DEFAULT_CREATED_BY
        defaultGuarantorShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the guarantorList where createdBy equals to UPDATED_CREATED_BY
        defaultGuarantorShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllGuarantorsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultGuarantorShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the guarantorList where createdBy equals to UPDATED_CREATED_BY
        defaultGuarantorShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllGuarantorsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where createdBy is not null
        defaultGuarantorShouldBeFound("createdBy.specified=true");

        // Get all the guarantorList where createdBy is null
        defaultGuarantorShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where createdBy contains DEFAULT_CREATED_BY
        defaultGuarantorShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the guarantorList where createdBy contains UPDATED_CREATED_BY
        defaultGuarantorShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllGuarantorsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where createdBy does not contain DEFAULT_CREATED_BY
        defaultGuarantorShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the guarantorList where createdBy does not contain UPDATED_CREATED_BY
        defaultGuarantorShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllGuarantorsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where createdOn equals to DEFAULT_CREATED_ON
        defaultGuarantorShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the guarantorList where createdOn equals to UPDATED_CREATED_ON
        defaultGuarantorShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllGuarantorsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultGuarantorShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the guarantorList where createdOn equals to UPDATED_CREATED_ON
        defaultGuarantorShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllGuarantorsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where createdOn is not null
        defaultGuarantorShouldBeFound("createdOn.specified=true");

        // Get all the guarantorList where createdOn is null
        defaultGuarantorShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultGuarantorShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the guarantorList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultGuarantorShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultGuarantorShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the guarantorList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultGuarantorShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField1 is not null
        defaultGuarantorShouldBeFound("freeField1.specified=true");

        // Get all the guarantorList where freeField1 is null
        defaultGuarantorShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultGuarantorShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the guarantorList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultGuarantorShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultGuarantorShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the guarantorList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultGuarantorShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultGuarantorShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the guarantorList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultGuarantorShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultGuarantorShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the guarantorList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultGuarantorShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField2 is not null
        defaultGuarantorShouldBeFound("freeField2.specified=true");

        // Get all the guarantorList where freeField2 is null
        defaultGuarantorShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultGuarantorShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the guarantorList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultGuarantorShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultGuarantorShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the guarantorList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultGuarantorShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultGuarantorShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the guarantorList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultGuarantorShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultGuarantorShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the guarantorList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultGuarantorShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField3 is not null
        defaultGuarantorShouldBeFound("freeField3.specified=true");

        // Get all the guarantorList where freeField3 is null
        defaultGuarantorShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultGuarantorShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the guarantorList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultGuarantorShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultGuarantorShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the guarantorList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultGuarantorShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultGuarantorShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the guarantorList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultGuarantorShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultGuarantorShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the guarantorList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultGuarantorShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField4 is not null
        defaultGuarantorShouldBeFound("freeField4.specified=true");

        // Get all the guarantorList where freeField4 is null
        defaultGuarantorShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultGuarantorShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the guarantorList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultGuarantorShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultGuarantorShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the guarantorList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultGuarantorShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllGuarantorsByProfileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where profileUrl equals to DEFAULT_FREE_FIELD_5
        defaultGuarantorShouldBeFound("profileUrl.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the guarantorList where profileUrl equals to UPDATED_FREE_FIELD_5
        defaultGuarantorShouldNotBeFound("profileUrl.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllGuarantorsByProfileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where profileUrl in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultGuarantorShouldBeFound("profileUrl.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the guarantorList where profileUrl equals to UPDATED_FREE_FIELD_5
        defaultGuarantorShouldNotBeFound("profileUrl.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllGuarantorsByProfileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where profileUrl is not null
        defaultGuarantorShouldBeFound("profileUrl.specified=true");

        // Get all the guarantorList where profileUrl is null
        defaultGuarantorShouldNotBeFound("profileUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByProfileUrlContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where profileUrl contains DEFAULT_FREE_FIELD_5
        defaultGuarantorShouldBeFound("profileUrl.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the guarantorList where profileUrl contains UPDATED_FREE_FIELD_5
        defaultGuarantorShouldNotBeFound("profileUrl.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllGuarantorsByProfileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where profileUrl does not contain DEFAULT_FREE_FIELD_5
        defaultGuarantorShouldNotBeFound("profileUrl.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the guarantorList where profileUrl does not contain UPDATED_FREE_FIELD_5
        defaultGuarantorShouldBeFound("profileUrl.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultGuarantorShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the guarantorList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultGuarantorShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultGuarantorShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the guarantorList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultGuarantorShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField6 is not null
        defaultGuarantorShouldBeFound("freeField6.specified=true");

        // Get all the guarantorList where freeField6 is null
        defaultGuarantorShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultGuarantorShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the guarantorList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultGuarantorShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllGuarantorsByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        // Get all the guarantorList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultGuarantorShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the guarantorList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultGuarantorShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllGuarantorsByMemberAssetsIsEqualToSomething() throws Exception {
        MemberAssets memberAssets;
        if (TestUtil.findAll(em, MemberAssets.class).isEmpty()) {
            guarantorRepository.saveAndFlush(guarantor);
            memberAssets = MemberAssetsResourceIT.createEntity(em);
        } else {
            memberAssets = TestUtil.findAll(em, MemberAssets.class).get(0);
        }
        em.persist(memberAssets);
        em.flush();
        //  guarantor.setMemberAssets(memberAssets);
        guarantorRepository.saveAndFlush(guarantor);
        Long memberAssetsId = memberAssets.getId();

        // Get all the guarantorList where memberAssets equals to memberAssetsId
        defaultGuarantorShouldBeFound("memberAssetsId.equals=" + memberAssetsId);

        // Get all the guarantorList where memberAssets equals to (memberAssetsId + 1)
        defaultGuarantorShouldNotBeFound("memberAssetsId.equals=" + (memberAssetsId + 1));
    }

    @Test
    @Transactional
    void getAllGuarantorsByEmployementDetailsIsEqualToSomething() throws Exception {
        EmployementDetails employementDetails;
        if (TestUtil.findAll(em, EmployementDetails.class).isEmpty()) {
            guarantorRepository.saveAndFlush(guarantor);
            employementDetails = EmployementDetailsResourceIT.createEntity(em);
        } else {
            employementDetails = TestUtil.findAll(em, EmployementDetails.class).get(0);
        }
        em.persist(employementDetails);
        em.flush();
        //      guarantor.setEmployementDetails(employementDetails);
        guarantorRepository.saveAndFlush(guarantor);
        Long employementDetailsId = employementDetails.getId();

        // Get all the guarantorList where employementDetails equals to employementDetailsId
        defaultGuarantorShouldBeFound("employementDetailsId.equals=" + employementDetailsId);

        // Get all the guarantorList where employementDetails equals to (employementDetailsId + 1)
        defaultGuarantorShouldNotBeFound("employementDetailsId.equals=" + (employementDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllGuarantorsByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            guarantorRepository.saveAndFlush(guarantor);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        guarantor.setMember(member);
        guarantorRepository.saveAndFlush(guarantor);
        Long memberId = member.getId();

        // Get all the guarantorList where member equals to memberId
        defaultGuarantorShouldBeFound("memberId.equals=" + memberId);

        // Get all the guarantorList where member equals to (memberId + 1)
        defaultGuarantorShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGuarantorShouldBeFound(String filter) throws Exception {
        restGuarantorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guarantor.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].membershipNo").value(hasItem(DEFAULT_MEMBERSHIP_NO)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].houseOwner").value(hasItem(DEFAULT_HOUSE_OWNER)))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
            .andExpect(jsonPath("$.[*].employerNameAdd").value(hasItem(DEFAULT_EMPLOYER_NAME_ADD)))
            .andExpect(jsonPath("$.[*].soclibilAmt").value(hasItem(DEFAULT_SOCLIBIL_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].soclibilType").value(hasItem(DEFAULT_SOCLIBIL_TYPE)))
            .andExpect(jsonPath("$.[*].otherlibilAmt").value(hasItem(DEFAULT_OTHERLIBIL_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].otherlibilType").value(hasItem(DEFAULT_OTHERLIBIL_TYPE)))
            .andExpect(jsonPath("$.[*].aadharCardNo").value(hasItem(DEFAULT_AADHAR_CARD_NO)))
            .andExpect(jsonPath("$.[*].panCard").value(hasItem(DEFAULT_PAN_CARD)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].hasAdharVerified").value(hasItem(DEFAULT_HAS_ADHAR_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].hasPanVerified").value(hasItem(DEFAULT_HAS_PAN_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].numberOfAssets").value(hasItem(DEFAULT_NUMBER_OF_ASSETS)))
            .andExpect(jsonPath("$.[*].grossAnnualInc").value(hasItem(DEFAULT_GROSS_ANNUAL_INC.doubleValue())))
            .andExpect(jsonPath("$.[*].netIncome").value(hasItem(DEFAULT_NET_INCOME.doubleValue())))
            .andExpect(jsonPath("$.[*].isIncomeTaxPayer").value(hasItem(DEFAULT_IS_INCOME_TAX_PAYER.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].profileUrl").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));

        // Check, that the count call also returns 1
        restGuarantorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGuarantorShouldNotBeFound(String filter) throws Exception {
        restGuarantorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGuarantorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingGuarantor() throws Exception {
        // Get the guarantor
        restGuarantorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGuarantor() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        int databaseSizeBeforeUpdate = guarantorRepository.findAll().size();

        // Update the guarantor
        Guarantor updatedGuarantor = guarantorRepository.findById(guarantor.getId()).get();
        // Disconnect from session so that the updates on updatedGuarantor are not directly saved in db
        em.detach(updatedGuarantor);
        updatedGuarantor
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .membershipNo(UPDATED_MEMBERSHIP_NO)
            .gender(UPDATED_GENDER)
            .dob(UPDATED_DOB)
            .email(UPDATED_EMAIL)
            .mobileNo(UPDATED_MOBILE_NO)
            .houseOwner(UPDATED_HOUSE_OWNER)
            .occupation(UPDATED_OCCUPATION)
            .employerNameAdd(UPDATED_EMPLOYER_NAME_ADD)
            .soclibilAmt(UPDATED_SOCLIBIL_AMT)
            .soclibilType(UPDATED_SOCLIBIL_TYPE)
            .otherlibilAmt(UPDATED_OTHERLIBIL_AMT)
            .otherlibilType(UPDATED_OTHERLIBIL_TYPE)
            .aadharCardNo(UPDATED_AADHAR_CARD_NO)
            .panCard(UPDATED_PAN_CARD)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .hasAdharVerified(UPDATED_HAS_ADHAR_VERIFIED)
            .hasPanVerified(UPDATED_HAS_PAN_VERIFIED)
            .numberOfAssets(UPDATED_NUMBER_OF_ASSETS)
            .grossAnnualInc(UPDATED_GROSS_ANNUAL_INC)
            .netIncome(UPDATED_NET_INCOME)
            .isIncomeTaxPayer(UPDATED_IS_INCOME_TAX_PAYER)
            .isActive(UPDATED_IS_ACTIVE)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .address(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .profileUrl(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        GuarantorDTO guarantorDTO = guarantorMapper.toDto(updatedGuarantor);

        restGuarantorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guarantorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guarantorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Guarantor in the database
        List<Guarantor> guarantorList = guarantorRepository.findAll();
        assertThat(guarantorList).hasSize(databaseSizeBeforeUpdate);
        Guarantor testGuarantor = guarantorList.get(guarantorList.size() - 1);
        assertThat(testGuarantor.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGuarantor.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testGuarantor.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testGuarantor.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testGuarantor.getMembershipNo()).isEqualTo(UPDATED_MEMBERSHIP_NO);
        assertThat(testGuarantor.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testGuarantor.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testGuarantor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGuarantor.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testGuarantor.getHouseOwner()).isEqualTo(UPDATED_HOUSE_OWNER);
        assertThat(testGuarantor.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testGuarantor.getEmployerNameAdd()).isEqualTo(UPDATED_EMPLOYER_NAME_ADD);
        assertThat(testGuarantor.getSoclibilAmt()).isEqualTo(UPDATED_SOCLIBIL_AMT);
        assertThat(testGuarantor.getSoclibilType()).isEqualTo(UPDATED_SOCLIBIL_TYPE);
        assertThat(testGuarantor.getOtherlibilAmt()).isEqualTo(UPDATED_OTHERLIBIL_AMT);
        assertThat(testGuarantor.getOtherlibilType()).isEqualTo(UPDATED_OTHERLIBIL_TYPE);
        assertThat(testGuarantor.getAadharCardNo()).isEqualTo(UPDATED_AADHAR_CARD_NO);
        assertThat(testGuarantor.getPanCard()).isEqualTo(UPDATED_PAN_CARD);
        assertThat(testGuarantor.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testGuarantor.getHasAdharVerified()).isEqualTo(UPDATED_HAS_ADHAR_VERIFIED);
        assertThat(testGuarantor.getHasPanVerified()).isEqualTo(UPDATED_HAS_PAN_VERIFIED);
        assertThat(testGuarantor.getNumberOfAssets()).isEqualTo(UPDATED_NUMBER_OF_ASSETS);
        assertThat(testGuarantor.getGrossAnnualInc()).isEqualTo(UPDATED_GROSS_ANNUAL_INC);
        assertThat(testGuarantor.getNetIncome()).isEqualTo(UPDATED_NET_INCOME);
        assertThat(testGuarantor.getIsIncomeTaxPayer()).isEqualTo(UPDATED_IS_INCOME_TAX_PAYER);
        assertThat(testGuarantor.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testGuarantor.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testGuarantor.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testGuarantor.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testGuarantor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testGuarantor.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testGuarantor.getAddress()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testGuarantor.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testGuarantor.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testGuarantor.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testGuarantor.getProfileUrl()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testGuarantor.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingGuarantor() throws Exception {
        int databaseSizeBeforeUpdate = guarantorRepository.findAll().size();
        guarantor.setId(count.incrementAndGet());

        // Create the Guarantor
        GuarantorDTO guarantorDTO = guarantorMapper.toDto(guarantor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuarantorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guarantorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guarantorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Guarantor in the database
        List<Guarantor> guarantorList = guarantorRepository.findAll();
        assertThat(guarantorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGuarantor() throws Exception {
        int databaseSizeBeforeUpdate = guarantorRepository.findAll().size();
        guarantor.setId(count.incrementAndGet());

        // Create the Guarantor
        GuarantorDTO guarantorDTO = guarantorMapper.toDto(guarantor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuarantorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guarantorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Guarantor in the database
        List<Guarantor> guarantorList = guarantorRepository.findAll();
        assertThat(guarantorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGuarantor() throws Exception {
        int databaseSizeBeforeUpdate = guarantorRepository.findAll().size();
        guarantor.setId(count.incrementAndGet());

        // Create the Guarantor
        GuarantorDTO guarantorDTO = guarantorMapper.toDto(guarantor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuarantorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guarantorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Guarantor in the database
        List<Guarantor> guarantorList = guarantorRepository.findAll();
        assertThat(guarantorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGuarantorWithPatch() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        int databaseSizeBeforeUpdate = guarantorRepository.findAll().size();

        // Update the guarantor using partial update
        Guarantor partialUpdatedGuarantor = new Guarantor();
        partialUpdatedGuarantor.setId(guarantor.getId());

        partialUpdatedGuarantor
            .title(UPDATED_TITLE)
            .membershipNo(UPDATED_MEMBERSHIP_NO)
            .gender(UPDATED_GENDER)
            .houseOwner(UPDATED_HOUSE_OWNER)
            .occupation(UPDATED_OCCUPATION)
            .soclibilType(UPDATED_SOCLIBIL_TYPE)
            .otherlibilType(UPDATED_OTHERLIBIL_TYPE)
            .aadharCardNo(UPDATED_AADHAR_CARD_NO)
            .hasAdharVerified(UPDATED_HAS_ADHAR_VERIFIED)
            .grossAnnualInc(UPDATED_GROSS_ANNUAL_INC)
            .isIncomeTaxPayer(UPDATED_IS_INCOME_TAX_PAYER)
            .isActive(UPDATED_IS_ACTIVE)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .address(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField4(UPDATED_FREE_FIELD_4);

        restGuarantorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuarantor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuarantor))
            )
            .andExpect(status().isOk());

        // Validate the Guarantor in the database
        List<Guarantor> guarantorList = guarantorRepository.findAll();
        assertThat(guarantorList).hasSize(databaseSizeBeforeUpdate);
        Guarantor testGuarantor = guarantorList.get(guarantorList.size() - 1);
        assertThat(testGuarantor.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGuarantor.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testGuarantor.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testGuarantor.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testGuarantor.getMembershipNo()).isEqualTo(UPDATED_MEMBERSHIP_NO);
        assertThat(testGuarantor.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testGuarantor.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testGuarantor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testGuarantor.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testGuarantor.getHouseOwner()).isEqualTo(UPDATED_HOUSE_OWNER);
        assertThat(testGuarantor.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testGuarantor.getEmployerNameAdd()).isEqualTo(DEFAULT_EMPLOYER_NAME_ADD);
        assertThat(testGuarantor.getSoclibilAmt()).isEqualTo(DEFAULT_SOCLIBIL_AMT);
        assertThat(testGuarantor.getSoclibilType()).isEqualTo(UPDATED_SOCLIBIL_TYPE);
        assertThat(testGuarantor.getOtherlibilAmt()).isEqualTo(DEFAULT_OTHERLIBIL_AMT);
        assertThat(testGuarantor.getOtherlibilType()).isEqualTo(UPDATED_OTHERLIBIL_TYPE);
        assertThat(testGuarantor.getAadharCardNo()).isEqualTo(UPDATED_AADHAR_CARD_NO);
        assertThat(testGuarantor.getPanCard()).isEqualTo(DEFAULT_PAN_CARD);
        assertThat(testGuarantor.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testGuarantor.getHasAdharVerified()).isEqualTo(UPDATED_HAS_ADHAR_VERIFIED);
        assertThat(testGuarantor.getHasPanVerified()).isEqualTo(DEFAULT_HAS_PAN_VERIFIED);
        assertThat(testGuarantor.getNumberOfAssets()).isEqualTo(DEFAULT_NUMBER_OF_ASSETS);
        assertThat(testGuarantor.getGrossAnnualInc()).isEqualTo(UPDATED_GROSS_ANNUAL_INC);
        assertThat(testGuarantor.getNetIncome()).isEqualTo(DEFAULT_NET_INCOME);
        assertThat(testGuarantor.getIsIncomeTaxPayer()).isEqualTo(UPDATED_IS_INCOME_TAX_PAYER);
        assertThat(testGuarantor.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testGuarantor.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testGuarantor.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testGuarantor.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testGuarantor.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testGuarantor.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testGuarantor.getAddress()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testGuarantor.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testGuarantor.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testGuarantor.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testGuarantor.getProfileUrl()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testGuarantor.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateGuarantorWithPatch() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        int databaseSizeBeforeUpdate = guarantorRepository.findAll().size();

        // Update the guarantor using partial update
        Guarantor partialUpdatedGuarantor = new Guarantor();
        partialUpdatedGuarantor.setId(guarantor.getId());

        partialUpdatedGuarantor
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .membershipNo(UPDATED_MEMBERSHIP_NO)
            .gender(UPDATED_GENDER)
            .dob(UPDATED_DOB)
            .email(UPDATED_EMAIL)
            .mobileNo(UPDATED_MOBILE_NO)
            .houseOwner(UPDATED_HOUSE_OWNER)
            .occupation(UPDATED_OCCUPATION)
            .employerNameAdd(UPDATED_EMPLOYER_NAME_ADD)
            .soclibilAmt(UPDATED_SOCLIBIL_AMT)
            .soclibilType(UPDATED_SOCLIBIL_TYPE)
            .otherlibilAmt(UPDATED_OTHERLIBIL_AMT)
            .otherlibilType(UPDATED_OTHERLIBIL_TYPE)
            .aadharCardNo(UPDATED_AADHAR_CARD_NO)
            .panCard(UPDATED_PAN_CARD)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .hasAdharVerified(UPDATED_HAS_ADHAR_VERIFIED)
            .hasPanVerified(UPDATED_HAS_PAN_VERIFIED)
            .numberOfAssets(UPDATED_NUMBER_OF_ASSETS)
            .grossAnnualInc(UPDATED_GROSS_ANNUAL_INC)
            .netIncome(UPDATED_NET_INCOME)
            .isIncomeTaxPayer(UPDATED_IS_INCOME_TAX_PAYER)
            .isActive(UPDATED_IS_ACTIVE)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .address(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .profileUrl(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restGuarantorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuarantor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuarantor))
            )
            .andExpect(status().isOk());

        // Validate the Guarantor in the database
        List<Guarantor> guarantorList = guarantorRepository.findAll();
        assertThat(guarantorList).hasSize(databaseSizeBeforeUpdate);
        Guarantor testGuarantor = guarantorList.get(guarantorList.size() - 1);
        assertThat(testGuarantor.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGuarantor.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testGuarantor.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testGuarantor.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testGuarantor.getMembershipNo()).isEqualTo(UPDATED_MEMBERSHIP_NO);
        assertThat(testGuarantor.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testGuarantor.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testGuarantor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGuarantor.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testGuarantor.getHouseOwner()).isEqualTo(UPDATED_HOUSE_OWNER);
        assertThat(testGuarantor.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testGuarantor.getEmployerNameAdd()).isEqualTo(UPDATED_EMPLOYER_NAME_ADD);
        assertThat(testGuarantor.getSoclibilAmt()).isEqualTo(UPDATED_SOCLIBIL_AMT);
        assertThat(testGuarantor.getSoclibilType()).isEqualTo(UPDATED_SOCLIBIL_TYPE);
        assertThat(testGuarantor.getOtherlibilAmt()).isEqualTo(UPDATED_OTHERLIBIL_AMT);
        assertThat(testGuarantor.getOtherlibilType()).isEqualTo(UPDATED_OTHERLIBIL_TYPE);
        assertThat(testGuarantor.getAadharCardNo()).isEqualTo(UPDATED_AADHAR_CARD_NO);
        assertThat(testGuarantor.getPanCard()).isEqualTo(UPDATED_PAN_CARD);
        assertThat(testGuarantor.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testGuarantor.getHasAdharVerified()).isEqualTo(UPDATED_HAS_ADHAR_VERIFIED);
        assertThat(testGuarantor.getHasPanVerified()).isEqualTo(UPDATED_HAS_PAN_VERIFIED);
        assertThat(testGuarantor.getNumberOfAssets()).isEqualTo(UPDATED_NUMBER_OF_ASSETS);
        assertThat(testGuarantor.getGrossAnnualInc()).isEqualTo(UPDATED_GROSS_ANNUAL_INC);
        assertThat(testGuarantor.getNetIncome()).isEqualTo(UPDATED_NET_INCOME);
        assertThat(testGuarantor.getIsIncomeTaxPayer()).isEqualTo(UPDATED_IS_INCOME_TAX_PAYER);
        assertThat(testGuarantor.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testGuarantor.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testGuarantor.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testGuarantor.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testGuarantor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testGuarantor.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testGuarantor.getAddress()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testGuarantor.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testGuarantor.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testGuarantor.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testGuarantor.getProfileUrl()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testGuarantor.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingGuarantor() throws Exception {
        int databaseSizeBeforeUpdate = guarantorRepository.findAll().size();
        guarantor.setId(count.incrementAndGet());

        // Create the Guarantor
        GuarantorDTO guarantorDTO = guarantorMapper.toDto(guarantor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuarantorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, guarantorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guarantorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Guarantor in the database
        List<Guarantor> guarantorList = guarantorRepository.findAll();
        assertThat(guarantorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGuarantor() throws Exception {
        int databaseSizeBeforeUpdate = guarantorRepository.findAll().size();
        guarantor.setId(count.incrementAndGet());

        // Create the Guarantor
        GuarantorDTO guarantorDTO = guarantorMapper.toDto(guarantor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuarantorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guarantorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Guarantor in the database
        List<Guarantor> guarantorList = guarantorRepository.findAll();
        assertThat(guarantorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGuarantor() throws Exception {
        int databaseSizeBeforeUpdate = guarantorRepository.findAll().size();
        guarantor.setId(count.incrementAndGet());

        // Create the Guarantor
        GuarantorDTO guarantorDTO = guarantorMapper.toDto(guarantor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuarantorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(guarantorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Guarantor in the database
        List<Guarantor> guarantorList = guarantorRepository.findAll();
        assertThat(guarantorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGuarantor() throws Exception {
        // Initialize the database
        guarantorRepository.saveAndFlush(guarantor);

        int databaseSizeBeforeDelete = guarantorRepository.findAll().size();

        // Delete the guarantor
        restGuarantorMockMvc
            .perform(delete(ENTITY_API_URL_ID, guarantor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Guarantor> guarantorList = guarantorRepository.findAll();
        assertThat(guarantorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
