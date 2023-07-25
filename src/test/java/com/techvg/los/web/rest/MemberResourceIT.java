package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.Branch;
import com.techvg.los.domain.EnquiryDetails;
import com.techvg.los.domain.Member;
import com.techvg.los.domain.Member;
import com.techvg.los.domain.SecurityUser;
import com.techvg.los.domain.enumeration.Gender;
import com.techvg.los.domain.enumeration.LoanStatus;
import com.techvg.los.domain.enumeration.MaritalStatus;
import com.techvg.los.domain.enumeration.Occupation;
import com.techvg.los.domain.enumeration.ResidentalStatus;
import com.techvg.los.domain.enumeration.Status;
import com.techvg.los.domain.enumeration.StepperNumber;
import com.techvg.los.domain.enumeration.Title;
import com.techvg.los.repository.MemberRepository;
import com.techvg.los.service.criteria.MemberCriteria;
import com.techvg.los.service.dto.MemberDTO;
import com.techvg.los.service.mapper.MemberMapper;
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
 * Integration tests for the {@link MemberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberResourceIT {

    private static final Title DEFAULT_TITLE = Title.MR;
    private static final Title UPDATED_TITLE = Title.MRS;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MEMBERSHIP_NO = "AAAAAAAAAA";
    private static final String UPDATED_MEMBERSHIP_NO = "BBBBBBBBBB";

    private static final String DEFAULT_FATHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DOB = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ALTERNATE_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_ALTERNATE_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_TIME_FROM = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_TIME_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_TIME_TO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_TIME_TO = "BBBBBBBBBB";

    private static final String DEFAULT_RELIGION = "AAAAAAAAAA";
    private static final String UPDATED_RELIGION = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CUST_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_CAST = "AAAAAAAAAA";
    private static final String UPDATED_CAST = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR_CARD_NO = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR_CARD_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PAN_CARD = "AAAAAAAAAA";
    private static final String UPDATED_PAN_CARD = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORT_NO = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PASSPORT_EXPIRY = "AAAAAAAAAA";
    private static final String UPDATED_PASSPORT_EXPIRY = "BBBBBBBBBB";

    private static final String DEFAULT_RATION_CARD = "AAAAAAAAAA";
    private static final String UPDATED_RATION_CARD = "BBBBBBBBBB";

    private static final ResidentalStatus DEFAULT_RESIDENCE_STATUS = ResidentalStatus.RESIDENT;
    private static final ResidentalStatus UPDATED_RESIDENCE_STATUS = ResidentalStatus.NON_RESIDENT;

    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.MARRIED;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.SINGLE;

    private static final Long DEFAULT_FAMILY_MEMBER_COUNT = 1L;
    private static final Long UPDATED_FAMILY_MEMBER_COUNT = 2L;
    private static final Long SMALLER_FAMILY_MEMBER_COUNT = 1L - 1L;

    private static final Occupation DEFAULT_OCCUPATION = Occupation.SALARIED;
    private static final Occupation UPDATED_OCCUPATION = Occupation.BUSINESS;

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final Long DEFAULT_NO_OF_DEPENDENTS = 1L;
    private static final Long UPDATED_NO_OF_DEPENDENTS = 2L;
    private static final Long SMALLER_NO_OF_DEPENDENTS = 1L - 1L;

    private static final Instant DEFAULT_APPLICATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPLICATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Status DEFAULT_STATUS = Status.DRAFT;
    private static final Status UPDATED_STATUS = Status.CREATED;

    private static final String DEFAULT_HIGHEST_QUALIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_HIGHEST_QUALIFICATION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_ADHAR_CARD_VERIFIED = false;
    private static final Boolean UPDATED_HAS_ADHAR_CARD_VERIFIED = true;

    private static final Boolean DEFAULT_HAS_PAN_CARD_VERIFIED = false;
    private static final Boolean UPDATED_HAS_PAN_CARD_VERIFIED = true;

    private static final LoanStatus DEFAULT_LOAN_STATUS = LoanStatus.DRAFT;
    private static final LoanStatus UPDATED_LOAN_STATUS = LoanStatus.APPLIED;

    private static final String DEFAULT_ENQ_REFRENCE_NO = "AAAAAAAAAA";
    private static final String UPDATED_ENQ_REFRENCE_NO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_ASSETS = 1;
    private static final Integer UPDATED_NUMBER_OF_ASSETS = 2;
    private static final Integer SMALLER_NUMBER_OF_ASSETS = 1 - 1;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final StepperNumber DEFAULT_PROFILE_STEPPER = StepperNumber.STEP_1;
    private static final StepperNumber UPDATED_PROFILE_STEPPER = StepperNumber.STEP_2;

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

    private static final String ENTITY_API_URL = "/api/members";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberMockMvc;

    private Member member;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createEntity(EntityManager em) {
        Member member = new Member()
            .title(DEFAULT_TITLE)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .customerId(DEFAULT_CUSTOMER_ID)
            .membershipNo(DEFAULT_MEMBERSHIP_NO)
            .fatherName(DEFAULT_FATHER_NAME)
            .motherName(DEFAULT_MOTHER_NAME)
            .gender(DEFAULT_GENDER)
            .dob(DEFAULT_DOB)
            .email(DEFAULT_EMAIL)
            .mobileNo(DEFAULT_MOBILE_NO)
            .alternateMobile(DEFAULT_ALTERNATE_MOBILE)
            .fax(DEFAULT_FAX)
            .contactTimeFrom(DEFAULT_CONTACT_TIME_FROM)
            .contactTimeTo(DEFAULT_CONTACT_TIME_TO)
            .religion(DEFAULT_RELIGION)
            .custCategory(DEFAULT_CUST_CATEGORY)
            .cast(DEFAULT_CAST)
            .aadharCardNo(DEFAULT_AADHAR_CARD_NO)
            .panCard(DEFAULT_PAN_CARD)
            .passportNo(DEFAULT_PASSPORT_NO)
            .passportExpiry(DEFAULT_PASSPORT_EXPIRY)
            .rationCard(DEFAULT_RATION_CARD)
            .residenceStatus(DEFAULT_RESIDENCE_STATUS)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .familyMemberCount(DEFAULT_FAMILY_MEMBER_COUNT)
            .occupation(DEFAULT_OCCUPATION)
            .nationality(DEFAULT_NATIONALITY)
            .noOfDependents(DEFAULT_NO_OF_DEPENDENTS)
            .applicationDate(DEFAULT_APPLICATION_DATE)
            .status(DEFAULT_STATUS)
            .highestQualification(DEFAULT_HIGHEST_QUALIFICATION)
            .hasAdharCardVerified(DEFAULT_HAS_ADHAR_CARD_VERIFIED)
            .hasPanCardVerified(DEFAULT_HAS_PAN_CARD_VERIFIED)
            .loanStatus(DEFAULT_LOAN_STATUS)
            .enqRefrenceNo(DEFAULT_ENQ_REFRENCE_NO)
            .numberOfAssets(DEFAULT_NUMBER_OF_ASSETS)
            .isActive(DEFAULT_IS_ACTIVE)
            .isDeleted(DEFAULT_IS_DELETED)
            .profileStepper(DEFAULT_PROFILE_STEPPER)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .residence(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5)
            .freeField6(DEFAULT_FREE_FIELD_6);
        return member;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Member createUpdatedEntity(EntityManager em) {
        Member member = new Member()
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .customerId(UPDATED_CUSTOMER_ID)
            .membershipNo(UPDATED_MEMBERSHIP_NO)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .gender(UPDATED_GENDER)
            .dob(UPDATED_DOB)
            .email(UPDATED_EMAIL)
            .mobileNo(UPDATED_MOBILE_NO)
            .alternateMobile(UPDATED_ALTERNATE_MOBILE)
            .fax(UPDATED_FAX)
            .contactTimeFrom(UPDATED_CONTACT_TIME_FROM)
            .contactTimeTo(UPDATED_CONTACT_TIME_TO)
            .religion(UPDATED_RELIGION)
            .custCategory(UPDATED_CUST_CATEGORY)
            .cast(UPDATED_CAST)
            .aadharCardNo(UPDATED_AADHAR_CARD_NO)
            .panCard(UPDATED_PAN_CARD)
            .passportNo(UPDATED_PASSPORT_NO)
            .passportExpiry(UPDATED_PASSPORT_EXPIRY)
            .rationCard(UPDATED_RATION_CARD)
            .residenceStatus(UPDATED_RESIDENCE_STATUS)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .familyMemberCount(UPDATED_FAMILY_MEMBER_COUNT)
            .occupation(UPDATED_OCCUPATION)
            .nationality(UPDATED_NATIONALITY)
            .noOfDependents(UPDATED_NO_OF_DEPENDENTS)
            .applicationDate(UPDATED_APPLICATION_DATE)
            .status(UPDATED_STATUS)
            .highestQualification(UPDATED_HIGHEST_QUALIFICATION)
            .hasAdharCardVerified(UPDATED_HAS_ADHAR_CARD_VERIFIED)
            .hasPanCardVerified(UPDATED_HAS_PAN_CARD_VERIFIED)
            .loanStatus(UPDATED_LOAN_STATUS)
            .enqRefrenceNo(UPDATED_ENQ_REFRENCE_NO)
            .numberOfAssets(UPDATED_NUMBER_OF_ASSETS)
            .isActive(UPDATED_IS_ACTIVE)
            .isDeleted(UPDATED_IS_DELETED)
            .profileStepper(UPDATED_PROFILE_STEPPER)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .residence(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        return member;
    }

    @BeforeEach
    public void initTest() {
        member = createEntity(em);
    }

    @Test
    @Transactional
    void createMember() throws Exception {
        int databaseSizeBeforeCreate = memberRepository.findAll().size();
        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);
        restMemberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isCreated());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate + 1);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testMember.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testMember.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testMember.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMember.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testMember.getMembershipNo()).isEqualTo(DEFAULT_MEMBERSHIP_NO);
        assertThat(testMember.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testMember.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testMember.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testMember.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testMember.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMember.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testMember.getAlternateMobile()).isEqualTo(DEFAULT_ALTERNATE_MOBILE);
        assertThat(testMember.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testMember.getContactTimeFrom()).isEqualTo(DEFAULT_CONTACT_TIME_FROM);
        assertThat(testMember.getContactTimeTo()).isEqualTo(DEFAULT_CONTACT_TIME_TO);
        assertThat(testMember.getReligion()).isEqualTo(DEFAULT_RELIGION);
        assertThat(testMember.getCustCategory()).isEqualTo(DEFAULT_CUST_CATEGORY);
        assertThat(testMember.getCast()).isEqualTo(DEFAULT_CAST);
        assertThat(testMember.getAadharCardNo()).isEqualTo(DEFAULT_AADHAR_CARD_NO);
        assertThat(testMember.getPanCard()).isEqualTo(DEFAULT_PAN_CARD);
        assertThat(testMember.getPassportNo()).isEqualTo(DEFAULT_PASSPORT_NO);
        assertThat(testMember.getPassportExpiry()).isEqualTo(DEFAULT_PASSPORT_EXPIRY);
        assertThat(testMember.getRationCard()).isEqualTo(DEFAULT_RATION_CARD);
        assertThat(testMember.getResidenceStatus()).isEqualTo(DEFAULT_RESIDENCE_STATUS);
        assertThat(testMember.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testMember.getFamilyMemberCount()).isEqualTo(DEFAULT_FAMILY_MEMBER_COUNT);
        assertThat(testMember.getOccupation()).isEqualTo(DEFAULT_OCCUPATION);
        assertThat(testMember.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testMember.getNoOfDependents()).isEqualTo(DEFAULT_NO_OF_DEPENDENTS);
        assertThat(testMember.getApplicationDate()).isEqualTo(DEFAULT_APPLICATION_DATE);
        assertThat(testMember.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMember.getHighestQualification()).isEqualTo(DEFAULT_HIGHEST_QUALIFICATION);
        assertThat(testMember.getHasAdharCardVerified()).isEqualTo(DEFAULT_HAS_ADHAR_CARD_VERIFIED);
        assertThat(testMember.getHasPanCardVerified()).isEqualTo(DEFAULT_HAS_PAN_CARD_VERIFIED);
        assertThat(testMember.getLoanStatus()).isEqualTo(DEFAULT_LOAN_STATUS);
        assertThat(testMember.getEnqRefrenceNo()).isEqualTo(DEFAULT_ENQ_REFRENCE_NO);
        assertThat(testMember.getNumberOfAssets()).isEqualTo(DEFAULT_NUMBER_OF_ASSETS);
        assertThat(testMember.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testMember.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMember.getProfileStepper()).isEqualTo(DEFAULT_PROFILE_STEPPER);
        assertThat(testMember.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMember.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMember.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMember.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testMember.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testMember.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testMember.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testMember.getResidence()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testMember.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testMember.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createMemberWithExistingId() throws Exception {
        // Create the Member with an existing ID
        member.setId(1L);
        MemberDTO memberDTO = memberMapper.toDto(member);

        int databaseSizeBeforeCreate = memberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMembers() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].membershipNo").value(hasItem(DEFAULT_MEMBERSHIP_NO)))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].alternateMobile").value(hasItem(DEFAULT_ALTERNATE_MOBILE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].contactTimeFrom").value(hasItem(DEFAULT_CONTACT_TIME_FROM)))
            .andExpect(jsonPath("$.[*].contactTimeTo").value(hasItem(DEFAULT_CONTACT_TIME_TO)))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION)))
            .andExpect(jsonPath("$.[*].custCategory").value(hasItem(DEFAULT_CUST_CATEGORY)))
            .andExpect(jsonPath("$.[*].cast").value(hasItem(DEFAULT_CAST)))
            .andExpect(jsonPath("$.[*].aadharCardNo").value(hasItem(DEFAULT_AADHAR_CARD_NO)))
            .andExpect(jsonPath("$.[*].panCard").value(hasItem(DEFAULT_PAN_CARD)))
            .andExpect(jsonPath("$.[*].passportNo").value(hasItem(DEFAULT_PASSPORT_NO)))
            .andExpect(jsonPath("$.[*].passportExpiry").value(hasItem(DEFAULT_PASSPORT_EXPIRY)))
            .andExpect(jsonPath("$.[*].rationCard").value(hasItem(DEFAULT_RATION_CARD)))
            .andExpect(jsonPath("$.[*].residenceStatus").value(hasItem(DEFAULT_RESIDENCE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].familyMemberCount").value(hasItem(DEFAULT_FAMILY_MEMBER_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].noOfDependents").value(hasItem(DEFAULT_NO_OF_DEPENDENTS.intValue())))
            .andExpect(jsonPath("$.[*].applicationDate").value(hasItem(DEFAULT_APPLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].highestQualification").value(hasItem(DEFAULT_HIGHEST_QUALIFICATION)))
            .andExpect(jsonPath("$.[*].hasAdharCardVerified").value(hasItem(DEFAULT_HAS_ADHAR_CARD_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].hasPanCardVerified").value(hasItem(DEFAULT_HAS_PAN_CARD_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].loanStatus").value(hasItem(DEFAULT_LOAN_STATUS.toString())))
            .andExpect(jsonPath("$.[*].enqRefrenceNo").value(hasItem(DEFAULT_ENQ_REFRENCE_NO)))
            .andExpect(jsonPath("$.[*].numberOfAssets").value(hasItem(DEFAULT_NUMBER_OF_ASSETS)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].profileStepper").value(hasItem(DEFAULT_PROFILE_STEPPER.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].residence").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));
    }

    @Test
    @Transactional
    void getMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get the member
        restMemberMockMvc
            .perform(get(ENTITY_API_URL_ID, member.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(member.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.membershipNo").value(DEFAULT_MEMBERSHIP_NO))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.alternateMobile").value(DEFAULT_ALTERNATE_MOBILE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.contactTimeFrom").value(DEFAULT_CONTACT_TIME_FROM))
            .andExpect(jsonPath("$.contactTimeTo").value(DEFAULT_CONTACT_TIME_TO))
            .andExpect(jsonPath("$.religion").value(DEFAULT_RELIGION))
            .andExpect(jsonPath("$.custCategory").value(DEFAULT_CUST_CATEGORY))
            .andExpect(jsonPath("$.cast").value(DEFAULT_CAST))
            .andExpect(jsonPath("$.aadharCardNo").value(DEFAULT_AADHAR_CARD_NO))
            .andExpect(jsonPath("$.panCard").value(DEFAULT_PAN_CARD))
            .andExpect(jsonPath("$.passportNo").value(DEFAULT_PASSPORT_NO))
            .andExpect(jsonPath("$.passportExpiry").value(DEFAULT_PASSPORT_EXPIRY))
            .andExpect(jsonPath("$.rationCard").value(DEFAULT_RATION_CARD))
            .andExpect(jsonPath("$.residenceStatus").value(DEFAULT_RESIDENCE_STATUS.toString()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.familyMemberCount").value(DEFAULT_FAMILY_MEMBER_COUNT.intValue()))
            .andExpect(jsonPath("$.occupation").value(DEFAULT_OCCUPATION.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.noOfDependents").value(DEFAULT_NO_OF_DEPENDENTS.intValue()))
            .andExpect(jsonPath("$.applicationDate").value(DEFAULT_APPLICATION_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.highestQualification").value(DEFAULT_HIGHEST_QUALIFICATION))
            .andExpect(jsonPath("$.hasAdharCardVerified").value(DEFAULT_HAS_ADHAR_CARD_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.hasPanCardVerified").value(DEFAULT_HAS_PAN_CARD_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.loanStatus").value(DEFAULT_LOAN_STATUS.toString()))
            .andExpect(jsonPath("$.enqRefrenceNo").value(DEFAULT_ENQ_REFRENCE_NO))
            .andExpect(jsonPath("$.numberOfAssets").value(DEFAULT_NUMBER_OF_ASSETS))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.profileStepper").value(DEFAULT_PROFILE_STEPPER.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.residence").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.freeField5").value(DEFAULT_FREE_FIELD_5))
            .andExpect(jsonPath("$.freeField6").value(DEFAULT_FREE_FIELD_6));
    }

    @Test
    @Transactional
    void getMembersByIdFiltering() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        Long id = member.getId();

        defaultMemberShouldBeFound("id.equals=" + id);
        defaultMemberShouldNotBeFound("id.notEquals=" + id);

        defaultMemberShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMemberShouldNotBeFound("id.greaterThan=" + id);

        defaultMemberShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMemberShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMembersByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where title equals to DEFAULT_TITLE
        defaultMemberShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the memberList where title equals to UPDATED_TITLE
        defaultMemberShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllMembersByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultMemberShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the memberList where title equals to UPDATED_TITLE
        defaultMemberShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllMembersByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where title is not null
        defaultMemberShouldBeFound("title.specified=true");

        // Get all the memberList where title is null
        defaultMemberShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where firstName equals to DEFAULT_FIRST_NAME
        defaultMemberShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the memberList where firstName equals to UPDATED_FIRST_NAME
        defaultMemberShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultMemberShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the memberList where firstName equals to UPDATED_FIRST_NAME
        defaultMemberShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where firstName is not null
        defaultMemberShouldBeFound("firstName.specified=true");

        // Get all the memberList where firstName is null
        defaultMemberShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where firstName contains DEFAULT_FIRST_NAME
        defaultMemberShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the memberList where firstName contains UPDATED_FIRST_NAME
        defaultMemberShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where firstName does not contain DEFAULT_FIRST_NAME
        defaultMemberShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the memberList where firstName does not contain UPDATED_FIRST_NAME
        defaultMemberShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultMemberShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the memberList where middleName equals to UPDATED_MIDDLE_NAME
        defaultMemberShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultMemberShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the memberList where middleName equals to UPDATED_MIDDLE_NAME
        defaultMemberShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where middleName is not null
        defaultMemberShouldBeFound("middleName.specified=true");

        // Get all the memberList where middleName is null
        defaultMemberShouldNotBeFound("middleName.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where middleName contains DEFAULT_MIDDLE_NAME
        defaultMemberShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the memberList where middleName contains UPDATED_MIDDLE_NAME
        defaultMemberShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultMemberShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the memberList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultMemberShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastName equals to DEFAULT_LAST_NAME
        defaultMemberShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the memberList where lastName equals to UPDATED_LAST_NAME
        defaultMemberShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultMemberShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the memberList where lastName equals to UPDATED_LAST_NAME
        defaultMemberShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastName is not null
        defaultMemberShouldBeFound("lastName.specified=true");

        // Get all the memberList where lastName is null
        defaultMemberShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByLastNameContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastName contains DEFAULT_LAST_NAME
        defaultMemberShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the memberList where lastName contains UPDATED_LAST_NAME
        defaultMemberShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastName does not contain DEFAULT_LAST_NAME
        defaultMemberShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the memberList where lastName does not contain UPDATED_LAST_NAME
        defaultMemberShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    //    @Test
    //    @Transactional
    //    void getAllMembersByMemberIdIsEqualToSomething() throws Exception {
    //        // Initialize the database
    //        memberRepository.saveAndFlush(member);
    //
    //        // Get all the memberList where memberId equals to DEFAULT_MEMBER_ID
    //        defaultMemberShouldBeFound("memberId.equals=" + DEFAULT_MEMBER_ID);
    //
    //        // Get all the memberList where memberId equals to UPDATED_MEMBER_ID
    //        defaultMemberShouldNotBeFound("memberId.equals=" + UPDATED_MEMBER_ID);
    //    }

    @Test
    @Transactional
    void getAllMembersByMemberIdIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where memberId in DEFAULT_MEMBER_ID or UPDATED_MEMBER_ID
        defaultMemberShouldBeFound("customerId.in=" + DEFAULT_CUSTOMER_ID + "," + UPDATED_CUSTOMER_ID);

        // Get all the memberList where memberId equals to UPDATED_MEMBER_ID
        defaultMemberShouldNotBeFound("customerId.in=" + UPDATED_CUSTOMER_ID);
    }

    @Test
    @Transactional
    void getAllMembersByMemberIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where memberId is not null
        defaultMemberShouldBeFound("memberId.specified=true");

        // Get all the memberList where memberId is null
        defaultMemberShouldNotBeFound("memberId.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByMemberIdContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);
        // Get all the memberList where memberId contains DEFAULT_MEMBER_ID
        //       defaultMemberShouldBeFound("memberId.contains=" + DEFAULT_MEMBER_ID);

        // Get all the memberList where memberId contains UPDATED_MEMBER_ID
        //       defaultMemberShouldNotBeFound("memberId.contains=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllMembersByMemberIdNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);
        // Get all the memberList where memberId does not contain DEFAULT_MEMBER_ID
        //     defaultMemberShouldNotBeFound("memberId.doesNotContain=" + DEFAULT_MEMBER_ID);

        // Get all the memberList where memberId does not contain UPDATED_MEMBER_ID
        //     defaultMemberShouldBeFound("memberId.doesNotContain=" + UPDATED_MEMBER_ID);
    }

    @Test
    @Transactional
    void getAllMembersByMembershipNoIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where membershipNo equals to DEFAULT_MEMBERSHIP_NO
        defaultMemberShouldBeFound("membershipNo.equals=" + DEFAULT_MEMBERSHIP_NO);

        // Get all the memberList where membershipNo equals to UPDATED_MEMBERSHIP_NO
        defaultMemberShouldNotBeFound("membershipNo.equals=" + UPDATED_MEMBERSHIP_NO);
    }

    @Test
    @Transactional
    void getAllMembersByMembershipNoIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where membershipNo in DEFAULT_MEMBERSHIP_NO or UPDATED_MEMBERSHIP_NO
        defaultMemberShouldBeFound("membershipNo.in=" + DEFAULT_MEMBERSHIP_NO + "," + UPDATED_MEMBERSHIP_NO);

        // Get all the memberList where membershipNo equals to UPDATED_MEMBERSHIP_NO
        defaultMemberShouldNotBeFound("membershipNo.in=" + UPDATED_MEMBERSHIP_NO);
    }

    @Test
    @Transactional
    void getAllMembersByMembershipNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where membershipNo is not null
        defaultMemberShouldBeFound("membershipNo.specified=true");

        // Get all the memberList where membershipNo is null
        defaultMemberShouldNotBeFound("membershipNo.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByMembershipNoContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where membershipNo contains DEFAULT_MEMBERSHIP_NO
        defaultMemberShouldBeFound("membershipNo.contains=" + DEFAULT_MEMBERSHIP_NO);

        // Get all the memberList where membershipNo contains UPDATED_MEMBERSHIP_NO
        defaultMemberShouldNotBeFound("membershipNo.contains=" + UPDATED_MEMBERSHIP_NO);
    }

    @Test
    @Transactional
    void getAllMembersByMembershipNoNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where membershipNo does not contain DEFAULT_MEMBERSHIP_NO
        defaultMemberShouldNotBeFound("membershipNo.doesNotContain=" + DEFAULT_MEMBERSHIP_NO);

        // Get all the memberList where membershipNo does not contain UPDATED_MEMBERSHIP_NO
        defaultMemberShouldBeFound("membershipNo.doesNotContain=" + UPDATED_MEMBERSHIP_NO);
    }

    @Test
    @Transactional
    void getAllMembersByFatherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where fatherName equals to DEFAULT_FATHER_NAME
        defaultMemberShouldBeFound("fatherName.equals=" + DEFAULT_FATHER_NAME);

        // Get all the memberList where fatherName equals to UPDATED_FATHER_NAME
        defaultMemberShouldNotBeFound("fatherName.equals=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByFatherNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where fatherName in DEFAULT_FATHER_NAME or UPDATED_FATHER_NAME
        defaultMemberShouldBeFound("fatherName.in=" + DEFAULT_FATHER_NAME + "," + UPDATED_FATHER_NAME);

        // Get all the memberList where fatherName equals to UPDATED_FATHER_NAME
        defaultMemberShouldNotBeFound("fatherName.in=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByFatherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where fatherName is not null
        defaultMemberShouldBeFound("fatherName.specified=true");

        // Get all the memberList where fatherName is null
        defaultMemberShouldNotBeFound("fatherName.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByFatherNameContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where fatherName contains DEFAULT_FATHER_NAME
        defaultMemberShouldBeFound("fatherName.contains=" + DEFAULT_FATHER_NAME);

        // Get all the memberList where fatherName contains UPDATED_FATHER_NAME
        defaultMemberShouldNotBeFound("fatherName.contains=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByFatherNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where fatherName does not contain DEFAULT_FATHER_NAME
        defaultMemberShouldNotBeFound("fatherName.doesNotContain=" + DEFAULT_FATHER_NAME);

        // Get all the memberList where fatherName does not contain UPDATED_FATHER_NAME
        defaultMemberShouldBeFound("fatherName.doesNotContain=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMotherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where motherName equals to DEFAULT_MOTHER_NAME
        defaultMemberShouldBeFound("motherName.equals=" + DEFAULT_MOTHER_NAME);

        // Get all the memberList where motherName equals to UPDATED_MOTHER_NAME
        defaultMemberShouldNotBeFound("motherName.equals=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMotherNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where motherName in DEFAULT_MOTHER_NAME or UPDATED_MOTHER_NAME
        defaultMemberShouldBeFound("motherName.in=" + DEFAULT_MOTHER_NAME + "," + UPDATED_MOTHER_NAME);

        // Get all the memberList where motherName equals to UPDATED_MOTHER_NAME
        defaultMemberShouldNotBeFound("motherName.in=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMotherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where motherName is not null
        defaultMemberShouldBeFound("motherName.specified=true");

        // Get all the memberList where motherName is null
        defaultMemberShouldNotBeFound("motherName.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByMotherNameContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where motherName contains DEFAULT_MOTHER_NAME
        defaultMemberShouldBeFound("motherName.contains=" + DEFAULT_MOTHER_NAME);

        // Get all the memberList where motherName contains UPDATED_MOTHER_NAME
        defaultMemberShouldNotBeFound("motherName.contains=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByMotherNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where motherName does not contain DEFAULT_MOTHER_NAME
        defaultMemberShouldNotBeFound("motherName.doesNotContain=" + DEFAULT_MOTHER_NAME);

        // Get all the memberList where motherName does not contain UPDATED_MOTHER_NAME
        defaultMemberShouldBeFound("motherName.doesNotContain=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllMembersByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where gender equals to DEFAULT_GENDER
        defaultMemberShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the memberList where gender equals to UPDATED_GENDER
        defaultMemberShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllMembersByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultMemberShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the memberList where gender equals to UPDATED_GENDER
        defaultMemberShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    void getAllMembersByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where gender is not null
        defaultMemberShouldBeFound("gender.specified=true");

        // Get all the memberList where gender is null
        defaultMemberShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByDobIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where dob equals to DEFAULT_DOB
        defaultMemberShouldBeFound("dob.equals=" + DEFAULT_DOB);

        // Get all the memberList where dob equals to UPDATED_DOB
        defaultMemberShouldNotBeFound("dob.equals=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllMembersByDobIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where dob in DEFAULT_DOB or UPDATED_DOB
        defaultMemberShouldBeFound("dob.in=" + DEFAULT_DOB + "," + UPDATED_DOB);

        // Get all the memberList where dob equals to UPDATED_DOB
        defaultMemberShouldNotBeFound("dob.in=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllMembersByDobIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where dob is not null
        defaultMemberShouldBeFound("dob.specified=true");

        // Get all the memberList where dob is null
        defaultMemberShouldNotBeFound("dob.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByDobIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where dob is greater than or equal to DEFAULT_DOB
        defaultMemberShouldBeFound("dob.greaterThanOrEqual=" + DEFAULT_DOB);

        // Get all the memberList where dob is greater than or equal to UPDATED_DOB
        defaultMemberShouldNotBeFound("dob.greaterThanOrEqual=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllMembersByDobIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where dob is less than or equal to DEFAULT_DOB
        defaultMemberShouldBeFound("dob.lessThanOrEqual=" + DEFAULT_DOB);

        // Get all the memberList where dob is less than or equal to SMALLER_DOB
        defaultMemberShouldNotBeFound("dob.lessThanOrEqual=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllMembersByDobIsLessThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where dob is less than DEFAULT_DOB
        defaultMemberShouldNotBeFound("dob.lessThan=" + DEFAULT_DOB);

        // Get all the memberList where dob is less than UPDATED_DOB
        defaultMemberShouldBeFound("dob.lessThan=" + UPDATED_DOB);
    }

    @Test
    @Transactional
    void getAllMembersByDobIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where dob is greater than DEFAULT_DOB
        defaultMemberShouldNotBeFound("dob.greaterThan=" + DEFAULT_DOB);

        // Get all the memberList where dob is greater than SMALLER_DOB
        defaultMemberShouldBeFound("dob.greaterThan=" + SMALLER_DOB);
    }

    @Test
    @Transactional
    void getAllMembersByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where email equals to DEFAULT_EMAIL
        defaultMemberShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the memberList where email equals to UPDATED_EMAIL
        defaultMemberShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllMembersByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultMemberShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the memberList where email equals to UPDATED_EMAIL
        defaultMemberShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllMembersByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where email is not null
        defaultMemberShouldBeFound("email.specified=true");

        // Get all the memberList where email is null
        defaultMemberShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByEmailContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where email contains DEFAULT_EMAIL
        defaultMemberShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the memberList where email contains UPDATED_EMAIL
        defaultMemberShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllMembersByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where email does not contain DEFAULT_EMAIL
        defaultMemberShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the memberList where email does not contain UPDATED_EMAIL
        defaultMemberShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllMembersByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultMemberShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the memberList where mobileNo equals to UPDATED_MOBILE_NO
        defaultMemberShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllMembersByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultMemberShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the memberList where mobileNo equals to UPDATED_MOBILE_NO
        defaultMemberShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllMembersByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where mobileNo is not null
        defaultMemberShouldBeFound("mobileNo.specified=true");

        // Get all the memberList where mobileNo is null
        defaultMemberShouldNotBeFound("mobileNo.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByMobileNoContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where mobileNo contains DEFAULT_MOBILE_NO
        defaultMemberShouldBeFound("mobileNo.contains=" + DEFAULT_MOBILE_NO);

        // Get all the memberList where mobileNo contains UPDATED_MOBILE_NO
        defaultMemberShouldNotBeFound("mobileNo.contains=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllMembersByMobileNoNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where mobileNo does not contain DEFAULT_MOBILE_NO
        defaultMemberShouldNotBeFound("mobileNo.doesNotContain=" + DEFAULT_MOBILE_NO);

        // Get all the memberList where mobileNo does not contain UPDATED_MOBILE_NO
        defaultMemberShouldBeFound("mobileNo.doesNotContain=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllMembersByAlternateMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where alternateMobile equals to DEFAULT_ALTERNATE_MOBILE
        defaultMemberShouldBeFound("alternateMobile.equals=" + DEFAULT_ALTERNATE_MOBILE);

        // Get all the memberList where alternateMobile equals to UPDATED_ALTERNATE_MOBILE
        defaultMemberShouldNotBeFound("alternateMobile.equals=" + UPDATED_ALTERNATE_MOBILE);
    }

    @Test
    @Transactional
    void getAllMembersByAlternateMobileIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where alternateMobile in DEFAULT_ALTERNATE_MOBILE or UPDATED_ALTERNATE_MOBILE
        defaultMemberShouldBeFound("alternateMobile.in=" + DEFAULT_ALTERNATE_MOBILE + "," + UPDATED_ALTERNATE_MOBILE);

        // Get all the memberList where alternateMobile equals to UPDATED_ALTERNATE_MOBILE
        defaultMemberShouldNotBeFound("alternateMobile.in=" + UPDATED_ALTERNATE_MOBILE);
    }

    @Test
    @Transactional
    void getAllMembersByAlternateMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where alternateMobile is not null
        defaultMemberShouldBeFound("alternateMobile.specified=true");

        // Get all the memberList where alternateMobile is null
        defaultMemberShouldNotBeFound("alternateMobile.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByAlternateMobileContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where alternateMobile contains DEFAULT_ALTERNATE_MOBILE
        defaultMemberShouldBeFound("alternateMobile.contains=" + DEFAULT_ALTERNATE_MOBILE);

        // Get all the memberList where alternateMobile contains UPDATED_ALTERNATE_MOBILE
        defaultMemberShouldNotBeFound("alternateMobile.contains=" + UPDATED_ALTERNATE_MOBILE);
    }

    @Test
    @Transactional
    void getAllMembersByAlternateMobileNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where alternateMobile does not contain DEFAULT_ALTERNATE_MOBILE
        defaultMemberShouldNotBeFound("alternateMobile.doesNotContain=" + DEFAULT_ALTERNATE_MOBILE);

        // Get all the memberList where alternateMobile does not contain UPDATED_ALTERNATE_MOBILE
        defaultMemberShouldBeFound("alternateMobile.doesNotContain=" + UPDATED_ALTERNATE_MOBILE);
    }

    @Test
    @Transactional
    void getAllMembersByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where fax equals to DEFAULT_FAX
        defaultMemberShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the memberList where fax equals to UPDATED_FAX
        defaultMemberShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllMembersByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultMemberShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the memberList where fax equals to UPDATED_FAX
        defaultMemberShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllMembersByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where fax is not null
        defaultMemberShouldBeFound("fax.specified=true");

        // Get all the memberList where fax is null
        defaultMemberShouldNotBeFound("fax.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByFaxContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where fax contains DEFAULT_FAX
        defaultMemberShouldBeFound("fax.contains=" + DEFAULT_FAX);

        // Get all the memberList where fax contains UPDATED_FAX
        defaultMemberShouldNotBeFound("fax.contains=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllMembersByFaxNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where fax does not contain DEFAULT_FAX
        defaultMemberShouldNotBeFound("fax.doesNotContain=" + DEFAULT_FAX);

        // Get all the memberList where fax does not contain UPDATED_FAX
        defaultMemberShouldBeFound("fax.doesNotContain=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllMembersByContactTimeFromIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactTimeFrom equals to DEFAULT_CONTACT_TIME_FROM
        defaultMemberShouldBeFound("contactTimeFrom.equals=" + DEFAULT_CONTACT_TIME_FROM);

        // Get all the memberList where contactTimeFrom equals to UPDATED_CONTACT_TIME_FROM
        defaultMemberShouldNotBeFound("contactTimeFrom.equals=" + UPDATED_CONTACT_TIME_FROM);
    }

    @Test
    @Transactional
    void getAllMembersByContactTimeFromIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactTimeFrom in DEFAULT_CONTACT_TIME_FROM or UPDATED_CONTACT_TIME_FROM
        defaultMemberShouldBeFound("contactTimeFrom.in=" + DEFAULT_CONTACT_TIME_FROM + "," + UPDATED_CONTACT_TIME_FROM);

        // Get all the memberList where contactTimeFrom equals to UPDATED_CONTACT_TIME_FROM
        defaultMemberShouldNotBeFound("contactTimeFrom.in=" + UPDATED_CONTACT_TIME_FROM);
    }

    @Test
    @Transactional
    void getAllMembersByContactTimeFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactTimeFrom is not null
        defaultMemberShouldBeFound("contactTimeFrom.specified=true");

        // Get all the memberList where contactTimeFrom is null
        defaultMemberShouldNotBeFound("contactTimeFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByContactTimeFromContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactTimeFrom contains DEFAULT_CONTACT_TIME_FROM
        defaultMemberShouldBeFound("contactTimeFrom.contains=" + DEFAULT_CONTACT_TIME_FROM);

        // Get all the memberList where contactTimeFrom contains UPDATED_CONTACT_TIME_FROM
        defaultMemberShouldNotBeFound("contactTimeFrom.contains=" + UPDATED_CONTACT_TIME_FROM);
    }

    @Test
    @Transactional
    void getAllMembersByContactTimeFromNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactTimeFrom does not contain DEFAULT_CONTACT_TIME_FROM
        defaultMemberShouldNotBeFound("contactTimeFrom.doesNotContain=" + DEFAULT_CONTACT_TIME_FROM);

        // Get all the memberList where contactTimeFrom does not contain UPDATED_CONTACT_TIME_FROM
        defaultMemberShouldBeFound("contactTimeFrom.doesNotContain=" + UPDATED_CONTACT_TIME_FROM);
    }

    @Test
    @Transactional
    void getAllMembersByContactTimeToIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactTimeTo equals to DEFAULT_CONTACT_TIME_TO
        defaultMemberShouldBeFound("contactTimeTo.equals=" + DEFAULT_CONTACT_TIME_TO);

        // Get all the memberList where contactTimeTo equals to UPDATED_CONTACT_TIME_TO
        defaultMemberShouldNotBeFound("contactTimeTo.equals=" + UPDATED_CONTACT_TIME_TO);
    }

    @Test
    @Transactional
    void getAllMembersByContactTimeToIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactTimeTo in DEFAULT_CONTACT_TIME_TO or UPDATED_CONTACT_TIME_TO
        defaultMemberShouldBeFound("contactTimeTo.in=" + DEFAULT_CONTACT_TIME_TO + "," + UPDATED_CONTACT_TIME_TO);

        // Get all the memberList where contactTimeTo equals to UPDATED_CONTACT_TIME_TO
        defaultMemberShouldNotBeFound("contactTimeTo.in=" + UPDATED_CONTACT_TIME_TO);
    }

    @Test
    @Transactional
    void getAllMembersByContactTimeToIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactTimeTo is not null
        defaultMemberShouldBeFound("contactTimeTo.specified=true");

        // Get all the memberList where contactTimeTo is null
        defaultMemberShouldNotBeFound("contactTimeTo.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByContactTimeToContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactTimeTo contains DEFAULT_CONTACT_TIME_TO
        defaultMemberShouldBeFound("contactTimeTo.contains=" + DEFAULT_CONTACT_TIME_TO);

        // Get all the memberList where contactTimeTo contains UPDATED_CONTACT_TIME_TO
        defaultMemberShouldNotBeFound("contactTimeTo.contains=" + UPDATED_CONTACT_TIME_TO);
    }

    @Test
    @Transactional
    void getAllMembersByContactTimeToNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where contactTimeTo does not contain DEFAULT_CONTACT_TIME_TO
        defaultMemberShouldNotBeFound("contactTimeTo.doesNotContain=" + DEFAULT_CONTACT_TIME_TO);

        // Get all the memberList where contactTimeTo does not contain UPDATED_CONTACT_TIME_TO
        defaultMemberShouldBeFound("contactTimeTo.doesNotContain=" + UPDATED_CONTACT_TIME_TO);
    }

    @Test
    @Transactional
    void getAllMembersByReligionIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where religion equals to DEFAULT_RELIGION
        defaultMemberShouldBeFound("religion.equals=" + DEFAULT_RELIGION);

        // Get all the memberList where religion equals to UPDATED_RELIGION
        defaultMemberShouldNotBeFound("religion.equals=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllMembersByReligionIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where religion in DEFAULT_RELIGION or UPDATED_RELIGION
        defaultMemberShouldBeFound("religion.in=" + DEFAULT_RELIGION + "," + UPDATED_RELIGION);

        // Get all the memberList where religion equals to UPDATED_RELIGION
        defaultMemberShouldNotBeFound("religion.in=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllMembersByReligionIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where religion is not null
        defaultMemberShouldBeFound("religion.specified=true");

        // Get all the memberList where religion is null
        defaultMemberShouldNotBeFound("religion.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByReligionContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where religion contains DEFAULT_RELIGION
        defaultMemberShouldBeFound("religion.contains=" + DEFAULT_RELIGION);

        // Get all the memberList where religion contains UPDATED_RELIGION
        defaultMemberShouldNotBeFound("religion.contains=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllMembersByReligionNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where religion does not contain DEFAULT_RELIGION
        defaultMemberShouldNotBeFound("religion.doesNotContain=" + DEFAULT_RELIGION);

        // Get all the memberList where religion does not contain UPDATED_RELIGION
        defaultMemberShouldBeFound("religion.doesNotContain=" + UPDATED_RELIGION);
    }

    @Test
    @Transactional
    void getAllMembersByCustCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where custCategory equals to DEFAULT_CUST_CATEGORY
        defaultMemberShouldBeFound("custCategory.equals=" + DEFAULT_CUST_CATEGORY);

        // Get all the memberList where custCategory equals to UPDATED_CUST_CATEGORY
        defaultMemberShouldNotBeFound("custCategory.equals=" + UPDATED_CUST_CATEGORY);
    }

    @Test
    @Transactional
    void getAllMembersByCustCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where custCategory in DEFAULT_CUST_CATEGORY or UPDATED_CUST_CATEGORY
        defaultMemberShouldBeFound("custCategory.in=" + DEFAULT_CUST_CATEGORY + "," + UPDATED_CUST_CATEGORY);

        // Get all the memberList where custCategory equals to UPDATED_CUST_CATEGORY
        defaultMemberShouldNotBeFound("custCategory.in=" + UPDATED_CUST_CATEGORY);
    }

    @Test
    @Transactional
    void getAllMembersByCustCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where custCategory is not null
        defaultMemberShouldBeFound("custCategory.specified=true");

        // Get all the memberList where custCategory is null
        defaultMemberShouldNotBeFound("custCategory.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByCustCategoryContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where custCategory contains DEFAULT_CUST_CATEGORY
        defaultMemberShouldBeFound("custCategory.contains=" + DEFAULT_CUST_CATEGORY);

        // Get all the memberList where custCategory contains UPDATED_CUST_CATEGORY
        defaultMemberShouldNotBeFound("custCategory.contains=" + UPDATED_CUST_CATEGORY);
    }

    @Test
    @Transactional
    void getAllMembersByCustCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where custCategory does not contain DEFAULT_CUST_CATEGORY
        defaultMemberShouldNotBeFound("custCategory.doesNotContain=" + DEFAULT_CUST_CATEGORY);

        // Get all the memberList where custCategory does not contain UPDATED_CUST_CATEGORY
        defaultMemberShouldBeFound("custCategory.doesNotContain=" + UPDATED_CUST_CATEGORY);
    }

    @Test
    @Transactional
    void getAllMembersByCastIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where cast equals to DEFAULT_CAST
        defaultMemberShouldBeFound("cast.equals=" + DEFAULT_CAST);

        // Get all the memberList where cast equals to UPDATED_CAST
        defaultMemberShouldNotBeFound("cast.equals=" + UPDATED_CAST);
    }

    @Test
    @Transactional
    void getAllMembersByCastIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where cast in DEFAULT_CAST or UPDATED_CAST
        defaultMemberShouldBeFound("cast.in=" + DEFAULT_CAST + "," + UPDATED_CAST);

        // Get all the memberList where cast equals to UPDATED_CAST
        defaultMemberShouldNotBeFound("cast.in=" + UPDATED_CAST);
    }

    @Test
    @Transactional
    void getAllMembersByCastIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where cast is not null
        defaultMemberShouldBeFound("cast.specified=true");

        // Get all the memberList where cast is null
        defaultMemberShouldNotBeFound("cast.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByCastContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where cast contains DEFAULT_CAST
        defaultMemberShouldBeFound("cast.contains=" + DEFAULT_CAST);

        // Get all the memberList where cast contains UPDATED_CAST
        defaultMemberShouldNotBeFound("cast.contains=" + UPDATED_CAST);
    }

    @Test
    @Transactional
    void getAllMembersByCastNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where cast does not contain DEFAULT_CAST
        defaultMemberShouldNotBeFound("cast.doesNotContain=" + DEFAULT_CAST);

        // Get all the memberList where cast does not contain UPDATED_CAST
        defaultMemberShouldBeFound("cast.doesNotContain=" + UPDATED_CAST);
    }

    @Test
    @Transactional
    void getAllMembersByAadharCardNoIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where aadharCardNo equals to DEFAULT_AADHAR_CARD_NO
        defaultMemberShouldBeFound("aadharCardNo.equals=" + DEFAULT_AADHAR_CARD_NO);

        // Get all the memberList where aadharCardNo equals to UPDATED_AADHAR_CARD_NO
        defaultMemberShouldNotBeFound("aadharCardNo.equals=" + UPDATED_AADHAR_CARD_NO);
    }

    @Test
    @Transactional
    void getAllMembersByAadharCardNoIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where aadharCardNo in DEFAULT_AADHAR_CARD_NO or UPDATED_AADHAR_CARD_NO
        defaultMemberShouldBeFound("aadharCardNo.in=" + DEFAULT_AADHAR_CARD_NO + "," + UPDATED_AADHAR_CARD_NO);

        // Get all the memberList where aadharCardNo equals to UPDATED_AADHAR_CARD_NO
        defaultMemberShouldNotBeFound("aadharCardNo.in=" + UPDATED_AADHAR_CARD_NO);
    }

    @Test
    @Transactional
    void getAllMembersByAadharCardNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where aadharCardNo is not null
        defaultMemberShouldBeFound("aadharCardNo.specified=true");

        // Get all the memberList where aadharCardNo is null
        defaultMemberShouldNotBeFound("aadharCardNo.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByAadharCardNoContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where aadharCardNo contains DEFAULT_AADHAR_CARD_NO
        defaultMemberShouldBeFound("aadharCardNo.contains=" + DEFAULT_AADHAR_CARD_NO);

        // Get all the memberList where aadharCardNo contains UPDATED_AADHAR_CARD_NO
        defaultMemberShouldNotBeFound("aadharCardNo.contains=" + UPDATED_AADHAR_CARD_NO);
    }

    @Test
    @Transactional
    void getAllMembersByAadharCardNoNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where aadharCardNo does not contain DEFAULT_AADHAR_CARD_NO
        defaultMemberShouldNotBeFound("aadharCardNo.doesNotContain=" + DEFAULT_AADHAR_CARD_NO);

        // Get all the memberList where aadharCardNo does not contain UPDATED_AADHAR_CARD_NO
        defaultMemberShouldBeFound("aadharCardNo.doesNotContain=" + UPDATED_AADHAR_CARD_NO);
    }

    @Test
    @Transactional
    void getAllMembersByPanCardIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where panCard equals to DEFAULT_PAN_CARD
        defaultMemberShouldBeFound("panCard.equals=" + DEFAULT_PAN_CARD);

        // Get all the memberList where panCard equals to UPDATED_PAN_CARD
        defaultMemberShouldNotBeFound("panCard.equals=" + UPDATED_PAN_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByPanCardIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where panCard in DEFAULT_PAN_CARD or UPDATED_PAN_CARD
        defaultMemberShouldBeFound("panCard.in=" + DEFAULT_PAN_CARD + "," + UPDATED_PAN_CARD);

        // Get all the memberList where panCard equals to UPDATED_PAN_CARD
        defaultMemberShouldNotBeFound("panCard.in=" + UPDATED_PAN_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByPanCardIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where panCard is not null
        defaultMemberShouldBeFound("panCard.specified=true");

        // Get all the memberList where panCard is null
        defaultMemberShouldNotBeFound("panCard.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByPanCardContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where panCard contains DEFAULT_PAN_CARD
        defaultMemberShouldBeFound("panCard.contains=" + DEFAULT_PAN_CARD);

        // Get all the memberList where panCard contains UPDATED_PAN_CARD
        defaultMemberShouldNotBeFound("panCard.contains=" + UPDATED_PAN_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByPanCardNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where panCard does not contain DEFAULT_PAN_CARD
        defaultMemberShouldNotBeFound("panCard.doesNotContain=" + DEFAULT_PAN_CARD);

        // Get all the memberList where panCard does not contain UPDATED_PAN_CARD
        defaultMemberShouldBeFound("panCard.doesNotContain=" + UPDATED_PAN_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByPassportNoIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where passportNo equals to DEFAULT_PASSPORT_NO
        defaultMemberShouldBeFound("passportNo.equals=" + DEFAULT_PASSPORT_NO);

        // Get all the memberList where passportNo equals to UPDATED_PASSPORT_NO
        defaultMemberShouldNotBeFound("passportNo.equals=" + UPDATED_PASSPORT_NO);
    }

    @Test
    @Transactional
    void getAllMembersByPassportNoIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where passportNo in DEFAULT_PASSPORT_NO or UPDATED_PASSPORT_NO
        defaultMemberShouldBeFound("passportNo.in=" + DEFAULT_PASSPORT_NO + "," + UPDATED_PASSPORT_NO);

        // Get all the memberList where passportNo equals to UPDATED_PASSPORT_NO
        defaultMemberShouldNotBeFound("passportNo.in=" + UPDATED_PASSPORT_NO);
    }

    @Test
    @Transactional
    void getAllMembersByPassportNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where passportNo is not null
        defaultMemberShouldBeFound("passportNo.specified=true");

        // Get all the memberList where passportNo is null
        defaultMemberShouldNotBeFound("passportNo.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByPassportNoContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where passportNo contains DEFAULT_PASSPORT_NO
        defaultMemberShouldBeFound("passportNo.contains=" + DEFAULT_PASSPORT_NO);

        // Get all the memberList where passportNo contains UPDATED_PASSPORT_NO
        defaultMemberShouldNotBeFound("passportNo.contains=" + UPDATED_PASSPORT_NO);
    }

    @Test
    @Transactional
    void getAllMembersByPassportNoNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where passportNo does not contain DEFAULT_PASSPORT_NO
        defaultMemberShouldNotBeFound("passportNo.doesNotContain=" + DEFAULT_PASSPORT_NO);

        // Get all the memberList where passportNo does not contain UPDATED_PASSPORT_NO
        defaultMemberShouldBeFound("passportNo.doesNotContain=" + UPDATED_PASSPORT_NO);
    }

    @Test
    @Transactional
    void getAllMembersByPassportExpiryIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where passportExpiry equals to DEFAULT_PASSPORT_EXPIRY
        defaultMemberShouldBeFound("passportExpiry.equals=" + DEFAULT_PASSPORT_EXPIRY);

        // Get all the memberList where passportExpiry equals to UPDATED_PASSPORT_EXPIRY
        defaultMemberShouldNotBeFound("passportExpiry.equals=" + UPDATED_PASSPORT_EXPIRY);
    }

    @Test
    @Transactional
    void getAllMembersByPassportExpiryIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where passportExpiry in DEFAULT_PASSPORT_EXPIRY or UPDATED_PASSPORT_EXPIRY
        defaultMemberShouldBeFound("passportExpiry.in=" + DEFAULT_PASSPORT_EXPIRY + "," + UPDATED_PASSPORT_EXPIRY);

        // Get all the memberList where passportExpiry equals to UPDATED_PASSPORT_EXPIRY
        defaultMemberShouldNotBeFound("passportExpiry.in=" + UPDATED_PASSPORT_EXPIRY);
    }

    @Test
    @Transactional
    void getAllMembersByPassportExpiryIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where passportExpiry is not null
        defaultMemberShouldBeFound("passportExpiry.specified=true");

        // Get all the memberList where passportExpiry is null
        defaultMemberShouldNotBeFound("passportExpiry.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByPassportExpiryContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where passportExpiry contains DEFAULT_PASSPORT_EXPIRY
        defaultMemberShouldBeFound("passportExpiry.contains=" + DEFAULT_PASSPORT_EXPIRY);

        // Get all the memberList where passportExpiry contains UPDATED_PASSPORT_EXPIRY
        defaultMemberShouldNotBeFound("passportExpiry.contains=" + UPDATED_PASSPORT_EXPIRY);
    }

    @Test
    @Transactional
    void getAllMembersByPassportExpiryNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where passportExpiry does not contain DEFAULT_PASSPORT_EXPIRY
        defaultMemberShouldNotBeFound("passportExpiry.doesNotContain=" + DEFAULT_PASSPORT_EXPIRY);

        // Get all the memberList where passportExpiry does not contain UPDATED_PASSPORT_EXPIRY
        defaultMemberShouldBeFound("passportExpiry.doesNotContain=" + UPDATED_PASSPORT_EXPIRY);
    }

    @Test
    @Transactional
    void getAllMembersByRationCardIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where rationCard equals to DEFAULT_RATION_CARD
        defaultMemberShouldBeFound("rationCard.equals=" + DEFAULT_RATION_CARD);

        // Get all the memberList where rationCard equals to UPDATED_RATION_CARD
        defaultMemberShouldNotBeFound("rationCard.equals=" + UPDATED_RATION_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByRationCardIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where rationCard in DEFAULT_RATION_CARD or UPDATED_RATION_CARD
        defaultMemberShouldBeFound("rationCard.in=" + DEFAULT_RATION_CARD + "," + UPDATED_RATION_CARD);

        // Get all the memberList where rationCard equals to UPDATED_RATION_CARD
        defaultMemberShouldNotBeFound("rationCard.in=" + UPDATED_RATION_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByRationCardIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where rationCard is not null
        defaultMemberShouldBeFound("rationCard.specified=true");

        // Get all the memberList where rationCard is null
        defaultMemberShouldNotBeFound("rationCard.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByRationCardContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where rationCard contains DEFAULT_RATION_CARD
        defaultMemberShouldBeFound("rationCard.contains=" + DEFAULT_RATION_CARD);

        // Get all the memberList where rationCard contains UPDATED_RATION_CARD
        defaultMemberShouldNotBeFound("rationCard.contains=" + UPDATED_RATION_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByRationCardNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where rationCard does not contain DEFAULT_RATION_CARD
        defaultMemberShouldNotBeFound("rationCard.doesNotContain=" + DEFAULT_RATION_CARD);

        // Get all the memberList where rationCard does not contain UPDATED_RATION_CARD
        defaultMemberShouldBeFound("rationCard.doesNotContain=" + UPDATED_RATION_CARD);
    }

    @Test
    @Transactional
    void getAllMembersByResidenceStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where residenceStatus equals to DEFAULT_RESIDENCE_STATUS
        defaultMemberShouldBeFound("residenceStatus.equals=" + DEFAULT_RESIDENCE_STATUS);

        // Get all the memberList where residenceStatus equals to UPDATED_RESIDENCE_STATUS
        defaultMemberShouldNotBeFound("residenceStatus.equals=" + UPDATED_RESIDENCE_STATUS);
    }

    @Test
    @Transactional
    void getAllMembersByResidenceStatusIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where residenceStatus in DEFAULT_RESIDENCE_STATUS or UPDATED_RESIDENCE_STATUS
        defaultMemberShouldBeFound("residenceStatus.in=" + DEFAULT_RESIDENCE_STATUS + "," + UPDATED_RESIDENCE_STATUS);

        // Get all the memberList where residenceStatus equals to UPDATED_RESIDENCE_STATUS
        defaultMemberShouldNotBeFound("residenceStatus.in=" + UPDATED_RESIDENCE_STATUS);
    }

    @Test
    @Transactional
    void getAllMembersByResidenceStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where residenceStatus is not null
        defaultMemberShouldBeFound("residenceStatus.specified=true");

        // Get all the memberList where residenceStatus is null
        defaultMemberShouldNotBeFound("residenceStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where maritalStatus equals to DEFAULT_MARITAL_STATUS
        defaultMemberShouldBeFound("maritalStatus.equals=" + DEFAULT_MARITAL_STATUS);

        // Get all the memberList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultMemberShouldNotBeFound("maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllMembersByMaritalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where maritalStatus in DEFAULT_MARITAL_STATUS or UPDATED_MARITAL_STATUS
        defaultMemberShouldBeFound("maritalStatus.in=" + DEFAULT_MARITAL_STATUS + "," + UPDATED_MARITAL_STATUS);

        // Get all the memberList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultMemberShouldNotBeFound("maritalStatus.in=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllMembersByMaritalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where maritalStatus is not null
        defaultMemberShouldBeFound("maritalStatus.specified=true");

        // Get all the memberList where maritalStatus is null
        defaultMemberShouldNotBeFound("maritalStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByFamilyMemberCountIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where familyMemberCount equals to DEFAULT_FAMILY_MEMBER_COUNT
        defaultMemberShouldBeFound("familyMemberCount.equals=" + DEFAULT_FAMILY_MEMBER_COUNT);

        // Get all the memberList where familyMemberCount equals to UPDATED_FAMILY_MEMBER_COUNT
        defaultMemberShouldNotBeFound("familyMemberCount.equals=" + UPDATED_FAMILY_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllMembersByFamilyMemberCountIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where familyMemberCount in DEFAULT_FAMILY_MEMBER_COUNT or UPDATED_FAMILY_MEMBER_COUNT
        defaultMemberShouldBeFound("familyMemberCount.in=" + DEFAULT_FAMILY_MEMBER_COUNT + "," + UPDATED_FAMILY_MEMBER_COUNT);

        // Get all the memberList where familyMemberCount equals to UPDATED_FAMILY_MEMBER_COUNT
        defaultMemberShouldNotBeFound("familyMemberCount.in=" + UPDATED_FAMILY_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllMembersByFamilyMemberCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where familyMemberCount is not null
        defaultMemberShouldBeFound("familyMemberCount.specified=true");

        // Get all the memberList where familyMemberCount is null
        defaultMemberShouldNotBeFound("familyMemberCount.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByFamilyMemberCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where familyMemberCount is greater than or equal to DEFAULT_FAMILY_MEMBER_COUNT
        defaultMemberShouldBeFound("familyMemberCount.greaterThanOrEqual=" + DEFAULT_FAMILY_MEMBER_COUNT);

        // Get all the memberList where familyMemberCount is greater than or equal to UPDATED_FAMILY_MEMBER_COUNT
        defaultMemberShouldNotBeFound("familyMemberCount.greaterThanOrEqual=" + UPDATED_FAMILY_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllMembersByFamilyMemberCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where familyMemberCount is less than or equal to DEFAULT_FAMILY_MEMBER_COUNT
        defaultMemberShouldBeFound("familyMemberCount.lessThanOrEqual=" + DEFAULT_FAMILY_MEMBER_COUNT);

        // Get all the memberList where familyMemberCount is less than or equal to SMALLER_FAMILY_MEMBER_COUNT
        defaultMemberShouldNotBeFound("familyMemberCount.lessThanOrEqual=" + SMALLER_FAMILY_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllMembersByFamilyMemberCountIsLessThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where familyMemberCount is less than DEFAULT_FAMILY_MEMBER_COUNT
        defaultMemberShouldNotBeFound("familyMemberCount.lessThan=" + DEFAULT_FAMILY_MEMBER_COUNT);

        // Get all the memberList where familyMemberCount is less than UPDATED_FAMILY_MEMBER_COUNT
        defaultMemberShouldBeFound("familyMemberCount.lessThan=" + UPDATED_FAMILY_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllMembersByFamilyMemberCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where familyMemberCount is greater than DEFAULT_FAMILY_MEMBER_COUNT
        defaultMemberShouldNotBeFound("familyMemberCount.greaterThan=" + DEFAULT_FAMILY_MEMBER_COUNT);

        // Get all the memberList where familyMemberCount is greater than SMALLER_FAMILY_MEMBER_COUNT
        defaultMemberShouldBeFound("familyMemberCount.greaterThan=" + SMALLER_FAMILY_MEMBER_COUNT);
    }

    @Test
    @Transactional
    void getAllMembersByOccupationIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where occupation equals to DEFAULT_OCCUPATION
        defaultMemberShouldBeFound("occupation.equals=" + DEFAULT_OCCUPATION);

        // Get all the memberList where occupation equals to UPDATED_OCCUPATION
        defaultMemberShouldNotBeFound("occupation.equals=" + UPDATED_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllMembersByOccupationIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where occupation in DEFAULT_OCCUPATION or UPDATED_OCCUPATION
        defaultMemberShouldBeFound("occupation.in=" + DEFAULT_OCCUPATION + "," + UPDATED_OCCUPATION);

        // Get all the memberList where occupation equals to UPDATED_OCCUPATION
        defaultMemberShouldNotBeFound("occupation.in=" + UPDATED_OCCUPATION);
    }

    @Test
    @Transactional
    void getAllMembersByOccupationIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where occupation is not null
        defaultMemberShouldBeFound("occupation.specified=true");

        // Get all the memberList where occupation is null
        defaultMemberShouldNotBeFound("occupation.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByNationalityIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where nationality equals to DEFAULT_NATIONALITY
        defaultMemberShouldBeFound("nationality.equals=" + DEFAULT_NATIONALITY);

        // Get all the memberList where nationality equals to UPDATED_NATIONALITY
        defaultMemberShouldNotBeFound("nationality.equals=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllMembersByNationalityIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where nationality in DEFAULT_NATIONALITY or UPDATED_NATIONALITY
        defaultMemberShouldBeFound("nationality.in=" + DEFAULT_NATIONALITY + "," + UPDATED_NATIONALITY);

        // Get all the memberList where nationality equals to UPDATED_NATIONALITY
        defaultMemberShouldNotBeFound("nationality.in=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllMembersByNationalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where nationality is not null
        defaultMemberShouldBeFound("nationality.specified=true");

        // Get all the memberList where nationality is null
        defaultMemberShouldNotBeFound("nationality.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByNationalityContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where nationality contains DEFAULT_NATIONALITY
        defaultMemberShouldBeFound("nationality.contains=" + DEFAULT_NATIONALITY);

        // Get all the memberList where nationality contains UPDATED_NATIONALITY
        defaultMemberShouldNotBeFound("nationality.contains=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllMembersByNationalityNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where nationality does not contain DEFAULT_NATIONALITY
        defaultMemberShouldNotBeFound("nationality.doesNotContain=" + DEFAULT_NATIONALITY);

        // Get all the memberList where nationality does not contain UPDATED_NATIONALITY
        defaultMemberShouldBeFound("nationality.doesNotContain=" + UPDATED_NATIONALITY);
    }

    @Test
    @Transactional
    void getAllMembersByNoOfDependentsIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where noOfDependents equals to DEFAULT_NO_OF_DEPENDENTS
        defaultMemberShouldBeFound("noOfDependents.equals=" + DEFAULT_NO_OF_DEPENDENTS);

        // Get all the memberList where noOfDependents equals to UPDATED_NO_OF_DEPENDENTS
        defaultMemberShouldNotBeFound("noOfDependents.equals=" + UPDATED_NO_OF_DEPENDENTS);
    }

    @Test
    @Transactional
    void getAllMembersByNoOfDependentsIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where noOfDependents in DEFAULT_NO_OF_DEPENDENTS or UPDATED_NO_OF_DEPENDENTS
        defaultMemberShouldBeFound("noOfDependents.in=" + DEFAULT_NO_OF_DEPENDENTS + "," + UPDATED_NO_OF_DEPENDENTS);

        // Get all the memberList where noOfDependents equals to UPDATED_NO_OF_DEPENDENTS
        defaultMemberShouldNotBeFound("noOfDependents.in=" + UPDATED_NO_OF_DEPENDENTS);
    }

    @Test
    @Transactional
    void getAllMembersByNoOfDependentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where noOfDependents is not null
        defaultMemberShouldBeFound("noOfDependents.specified=true");

        // Get all the memberList where noOfDependents is null
        defaultMemberShouldNotBeFound("noOfDependents.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByNoOfDependentsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where noOfDependents is greater than or equal to DEFAULT_NO_OF_DEPENDENTS
        defaultMemberShouldBeFound("noOfDependents.greaterThanOrEqual=" + DEFAULT_NO_OF_DEPENDENTS);

        // Get all the memberList where noOfDependents is greater than or equal to UPDATED_NO_OF_DEPENDENTS
        defaultMemberShouldNotBeFound("noOfDependents.greaterThanOrEqual=" + UPDATED_NO_OF_DEPENDENTS);
    }

    @Test
    @Transactional
    void getAllMembersByNoOfDependentsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where noOfDependents is less than or equal to DEFAULT_NO_OF_DEPENDENTS
        defaultMemberShouldBeFound("noOfDependents.lessThanOrEqual=" + DEFAULT_NO_OF_DEPENDENTS);

        // Get all the memberList where noOfDependents is less than or equal to SMALLER_NO_OF_DEPENDENTS
        defaultMemberShouldNotBeFound("noOfDependents.lessThanOrEqual=" + SMALLER_NO_OF_DEPENDENTS);
    }

    @Test
    @Transactional
    void getAllMembersByNoOfDependentsIsLessThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where noOfDependents is less than DEFAULT_NO_OF_DEPENDENTS
        defaultMemberShouldNotBeFound("noOfDependents.lessThan=" + DEFAULT_NO_OF_DEPENDENTS);

        // Get all the memberList where noOfDependents is less than UPDATED_NO_OF_DEPENDENTS
        defaultMemberShouldBeFound("noOfDependents.lessThan=" + UPDATED_NO_OF_DEPENDENTS);
    }

    @Test
    @Transactional
    void getAllMembersByNoOfDependentsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where noOfDependents is greater than DEFAULT_NO_OF_DEPENDENTS
        defaultMemberShouldNotBeFound("noOfDependents.greaterThan=" + DEFAULT_NO_OF_DEPENDENTS);

        // Get all the memberList where noOfDependents is greater than SMALLER_NO_OF_DEPENDENTS
        defaultMemberShouldBeFound("noOfDependents.greaterThan=" + SMALLER_NO_OF_DEPENDENTS);
    }

    @Test
    @Transactional
    void getAllMembersByApplicationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where applicationDate equals to DEFAULT_APPLICATION_DATE
        defaultMemberShouldBeFound("applicationDate.equals=" + DEFAULT_APPLICATION_DATE);

        // Get all the memberList where applicationDate equals to UPDATED_APPLICATION_DATE
        defaultMemberShouldNotBeFound("applicationDate.equals=" + UPDATED_APPLICATION_DATE);
    }

    @Test
    @Transactional
    void getAllMembersByApplicationDateIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where applicationDate in DEFAULT_APPLICATION_DATE or UPDATED_APPLICATION_DATE
        defaultMemberShouldBeFound("applicationDate.in=" + DEFAULT_APPLICATION_DATE + "," + UPDATED_APPLICATION_DATE);

        // Get all the memberList where applicationDate equals to UPDATED_APPLICATION_DATE
        defaultMemberShouldNotBeFound("applicationDate.in=" + UPDATED_APPLICATION_DATE);
    }

    @Test
    @Transactional
    void getAllMembersByApplicationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where applicationDate is not null
        defaultMemberShouldBeFound("applicationDate.specified=true");

        // Get all the memberList where applicationDate is null
        defaultMemberShouldNotBeFound("applicationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where status equals to DEFAULT_STATUS
        defaultMemberShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the memberList where status equals to UPDATED_STATUS
        defaultMemberShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMembersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultMemberShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the memberList where status equals to UPDATED_STATUS
        defaultMemberShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllMembersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where status is not null
        defaultMemberShouldBeFound("status.specified=true");

        // Get all the memberList where status is null
        defaultMemberShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByHighestQualificationIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where highestQualification equals to DEFAULT_HIGHEST_QUALIFICATION
        defaultMemberShouldBeFound("highestQualification.equals=" + DEFAULT_HIGHEST_QUALIFICATION);

        // Get all the memberList where highestQualification equals to UPDATED_HIGHEST_QUALIFICATION
        defaultMemberShouldNotBeFound("highestQualification.equals=" + UPDATED_HIGHEST_QUALIFICATION);
    }

    @Test
    @Transactional
    void getAllMembersByHighestQualificationIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where highestQualification in DEFAULT_HIGHEST_QUALIFICATION or UPDATED_HIGHEST_QUALIFICATION
        defaultMemberShouldBeFound("highestQualification.in=" + DEFAULT_HIGHEST_QUALIFICATION + "," + UPDATED_HIGHEST_QUALIFICATION);

        // Get all the memberList where highestQualification equals to UPDATED_HIGHEST_QUALIFICATION
        defaultMemberShouldNotBeFound("highestQualification.in=" + UPDATED_HIGHEST_QUALIFICATION);
    }

    @Test
    @Transactional
    void getAllMembersByHighestQualificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where highestQualification is not null
        defaultMemberShouldBeFound("highestQualification.specified=true");

        // Get all the memberList where highestQualification is null
        defaultMemberShouldNotBeFound("highestQualification.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByHighestQualificationContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where highestQualification contains DEFAULT_HIGHEST_QUALIFICATION
        defaultMemberShouldBeFound("highestQualification.contains=" + DEFAULT_HIGHEST_QUALIFICATION);

        // Get all the memberList where highestQualification contains UPDATED_HIGHEST_QUALIFICATION
        defaultMemberShouldNotBeFound("highestQualification.contains=" + UPDATED_HIGHEST_QUALIFICATION);
    }

    @Test
    @Transactional
    void getAllMembersByHighestQualificationNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where highestQualification does not contain DEFAULT_HIGHEST_QUALIFICATION
        defaultMemberShouldNotBeFound("highestQualification.doesNotContain=" + DEFAULT_HIGHEST_QUALIFICATION);

        // Get all the memberList where highestQualification does not contain UPDATED_HIGHEST_QUALIFICATION
        defaultMemberShouldBeFound("highestQualification.doesNotContain=" + UPDATED_HIGHEST_QUALIFICATION);
    }

    @Test
    @Transactional
    void getAllMembersByHasAdharCardVerifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where hasAdharCardVerified equals to DEFAULT_HAS_ADHAR_CARD_VERIFIED
        defaultMemberShouldBeFound("hasAdharCardVerified.equals=" + DEFAULT_HAS_ADHAR_CARD_VERIFIED);

        // Get all the memberList where hasAdharCardVerified equals to UPDATED_HAS_ADHAR_CARD_VERIFIED
        defaultMemberShouldNotBeFound("hasAdharCardVerified.equals=" + UPDATED_HAS_ADHAR_CARD_VERIFIED);
    }

    @Test
    @Transactional
    void getAllMembersByHasAdharCardVerifiedIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where hasAdharCardVerified in DEFAULT_HAS_ADHAR_CARD_VERIFIED or UPDATED_HAS_ADHAR_CARD_VERIFIED
        defaultMemberShouldBeFound("hasAdharCardVerified.in=" + DEFAULT_HAS_ADHAR_CARD_VERIFIED + "," + UPDATED_HAS_ADHAR_CARD_VERIFIED);

        // Get all the memberList where hasAdharCardVerified equals to UPDATED_HAS_ADHAR_CARD_VERIFIED
        defaultMemberShouldNotBeFound("hasAdharCardVerified.in=" + UPDATED_HAS_ADHAR_CARD_VERIFIED);
    }

    @Test
    @Transactional
    void getAllMembersByHasAdharCardVerifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where hasAdharCardVerified is not null
        defaultMemberShouldBeFound("hasAdharCardVerified.specified=true");

        // Get all the memberList where hasAdharCardVerified is null
        defaultMemberShouldNotBeFound("hasAdharCardVerified.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByHasPanCardVerifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where hasPanCardVerified equals to DEFAULT_HAS_PAN_CARD_VERIFIED
        defaultMemberShouldBeFound("hasPanCardVerified.equals=" + DEFAULT_HAS_PAN_CARD_VERIFIED);

        // Get all the memberList where hasPanCardVerified equals to UPDATED_HAS_PAN_CARD_VERIFIED
        defaultMemberShouldNotBeFound("hasPanCardVerified.equals=" + UPDATED_HAS_PAN_CARD_VERIFIED);
    }

    @Test
    @Transactional
    void getAllMembersByHasPanCardVerifiedIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where hasPanCardVerified in DEFAULT_HAS_PAN_CARD_VERIFIED or UPDATED_HAS_PAN_CARD_VERIFIED
        defaultMemberShouldBeFound("hasPanCardVerified.in=" + DEFAULT_HAS_PAN_CARD_VERIFIED + "," + UPDATED_HAS_PAN_CARD_VERIFIED);

        // Get all the memberList where hasPanCardVerified equals to UPDATED_HAS_PAN_CARD_VERIFIED
        defaultMemberShouldNotBeFound("hasPanCardVerified.in=" + UPDATED_HAS_PAN_CARD_VERIFIED);
    }

    @Test
    @Transactional
    void getAllMembersByHasPanCardVerifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where hasPanCardVerified is not null
        defaultMemberShouldBeFound("hasPanCardVerified.specified=true");

        // Get all the memberList where hasPanCardVerified is null
        defaultMemberShouldNotBeFound("hasPanCardVerified.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByLoanStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where loanStatus equals to DEFAULT_LOAN_STATUS
        defaultMemberShouldBeFound("loanStatus.equals=" + DEFAULT_LOAN_STATUS);

        // Get all the memberList where loanStatus equals to UPDATED_LOAN_STATUS
        defaultMemberShouldNotBeFound("loanStatus.equals=" + UPDATED_LOAN_STATUS);
    }

    @Test
    @Transactional
    void getAllMembersByLoanStatusIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where loanStatus in DEFAULT_LOAN_STATUS or UPDATED_LOAN_STATUS
        defaultMemberShouldBeFound("loanStatus.in=" + DEFAULT_LOAN_STATUS + "," + UPDATED_LOAN_STATUS);

        // Get all the memberList where loanStatus equals to UPDATED_LOAN_STATUS
        defaultMemberShouldNotBeFound("loanStatus.in=" + UPDATED_LOAN_STATUS);
    }

    @Test
    @Transactional
    void getAllMembersByLoanStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where loanStatus is not null
        defaultMemberShouldBeFound("loanStatus.specified=true");

        // Get all the memberList where loanStatus is null
        defaultMemberShouldNotBeFound("loanStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByEnqRefrenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where enqRefrenceNo equals to DEFAULT_ENQ_REFRENCE_NO
        defaultMemberShouldBeFound("enqRefrenceNo.equals=" + DEFAULT_ENQ_REFRENCE_NO);

        // Get all the memberList where enqRefrenceNo equals to UPDATED_ENQ_REFRENCE_NO
        defaultMemberShouldNotBeFound("enqRefrenceNo.equals=" + UPDATED_ENQ_REFRENCE_NO);
    }

    @Test
    @Transactional
    void getAllMembersByEnqRefrenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where enqRefrenceNo in DEFAULT_ENQ_REFRENCE_NO or UPDATED_ENQ_REFRENCE_NO
        defaultMemberShouldBeFound("enqRefrenceNo.in=" + DEFAULT_ENQ_REFRENCE_NO + "," + UPDATED_ENQ_REFRENCE_NO);

        // Get all the memberList where enqRefrenceNo equals to UPDATED_ENQ_REFRENCE_NO
        defaultMemberShouldNotBeFound("enqRefrenceNo.in=" + UPDATED_ENQ_REFRENCE_NO);
    }

    @Test
    @Transactional
    void getAllMembersByEnqRefrenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where enqRefrenceNo is not null
        defaultMemberShouldBeFound("enqRefrenceNo.specified=true");

        // Get all the memberList where enqRefrenceNo is null
        defaultMemberShouldNotBeFound("enqRefrenceNo.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByEnqRefrenceNoContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where enqRefrenceNo contains DEFAULT_ENQ_REFRENCE_NO
        defaultMemberShouldBeFound("enqRefrenceNo.contains=" + DEFAULT_ENQ_REFRENCE_NO);

        // Get all the memberList where enqRefrenceNo contains UPDATED_ENQ_REFRENCE_NO
        defaultMemberShouldNotBeFound("enqRefrenceNo.contains=" + UPDATED_ENQ_REFRENCE_NO);
    }

    @Test
    @Transactional
    void getAllMembersByEnqRefrenceNoNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where enqRefrenceNo does not contain DEFAULT_ENQ_REFRENCE_NO
        defaultMemberShouldNotBeFound("enqRefrenceNo.doesNotContain=" + DEFAULT_ENQ_REFRENCE_NO);

        // Get all the memberList where enqRefrenceNo does not contain UPDATED_ENQ_REFRENCE_NO
        defaultMemberShouldBeFound("enqRefrenceNo.doesNotContain=" + UPDATED_ENQ_REFRENCE_NO);
    }

    @Test
    @Transactional
    void getAllMembersByNumberOfAssetsIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where numberOfAssets equals to DEFAULT_NUMBER_OF_ASSETS
        defaultMemberShouldBeFound("numberOfAssets.equals=" + DEFAULT_NUMBER_OF_ASSETS);

        // Get all the memberList where numberOfAssets equals to UPDATED_NUMBER_OF_ASSETS
        defaultMemberShouldNotBeFound("numberOfAssets.equals=" + UPDATED_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllMembersByNumberOfAssetsIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where numberOfAssets in DEFAULT_NUMBER_OF_ASSETS or UPDATED_NUMBER_OF_ASSETS
        defaultMemberShouldBeFound("numberOfAssets.in=" + DEFAULT_NUMBER_OF_ASSETS + "," + UPDATED_NUMBER_OF_ASSETS);

        // Get all the memberList where numberOfAssets equals to UPDATED_NUMBER_OF_ASSETS
        defaultMemberShouldNotBeFound("numberOfAssets.in=" + UPDATED_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllMembersByNumberOfAssetsIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where numberOfAssets is not null
        defaultMemberShouldBeFound("numberOfAssets.specified=true");

        // Get all the memberList where numberOfAssets is null
        defaultMemberShouldNotBeFound("numberOfAssets.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByNumberOfAssetsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where numberOfAssets is greater than or equal to DEFAULT_NUMBER_OF_ASSETS
        defaultMemberShouldBeFound("numberOfAssets.greaterThanOrEqual=" + DEFAULT_NUMBER_OF_ASSETS);

        // Get all the memberList where numberOfAssets is greater than or equal to UPDATED_NUMBER_OF_ASSETS
        defaultMemberShouldNotBeFound("numberOfAssets.greaterThanOrEqual=" + UPDATED_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllMembersByNumberOfAssetsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where numberOfAssets is less than or equal to DEFAULT_NUMBER_OF_ASSETS
        defaultMemberShouldBeFound("numberOfAssets.lessThanOrEqual=" + DEFAULT_NUMBER_OF_ASSETS);

        // Get all the memberList where numberOfAssets is less than or equal to SMALLER_NUMBER_OF_ASSETS
        defaultMemberShouldNotBeFound("numberOfAssets.lessThanOrEqual=" + SMALLER_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllMembersByNumberOfAssetsIsLessThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where numberOfAssets is less than DEFAULT_NUMBER_OF_ASSETS
        defaultMemberShouldNotBeFound("numberOfAssets.lessThan=" + DEFAULT_NUMBER_OF_ASSETS);

        // Get all the memberList where numberOfAssets is less than UPDATED_NUMBER_OF_ASSETS
        defaultMemberShouldBeFound("numberOfAssets.lessThan=" + UPDATED_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllMembersByNumberOfAssetsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where numberOfAssets is greater than DEFAULT_NUMBER_OF_ASSETS
        defaultMemberShouldNotBeFound("numberOfAssets.greaterThan=" + DEFAULT_NUMBER_OF_ASSETS);

        // Get all the memberList where numberOfAssets is greater than SMALLER_NUMBER_OF_ASSETS
        defaultMemberShouldBeFound("numberOfAssets.greaterThan=" + SMALLER_NUMBER_OF_ASSETS);
    }

    @Test
    @Transactional
    void getAllMembersByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where isActive equals to DEFAULT_IS_ACTIVE
        defaultMemberShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the memberList where isActive equals to UPDATED_IS_ACTIVE
        defaultMemberShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllMembersByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultMemberShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the memberList where isActive equals to UPDATED_IS_ACTIVE
        defaultMemberShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllMembersByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where isActive is not null
        defaultMemberShouldBeFound("isActive.specified=true");

        // Get all the memberList where isActive is null
        defaultMemberShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where isDeleted equals to DEFAULT_IS_DELETED
        defaultMemberShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the memberList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMembersByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultMemberShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the memberList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMembersByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where isDeleted is not null
        defaultMemberShouldBeFound("isDeleted.specified=true");

        // Get all the memberList where isDeleted is null
        defaultMemberShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByProfileStepperIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where profileStepper equals to DEFAULT_PROFILE_STEPPER
        defaultMemberShouldBeFound("profileStepper.equals=" + DEFAULT_PROFILE_STEPPER);

        // Get all the memberList where profileStepper equals to UPDATED_PROFILE_STEPPER
        defaultMemberShouldNotBeFound("profileStepper.equals=" + UPDATED_PROFILE_STEPPER);
    }

    @Test
    @Transactional
    void getAllMembersByProfileStepperIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where profileStepper in DEFAULT_PROFILE_STEPPER or UPDATED_PROFILE_STEPPER
        defaultMemberShouldBeFound("profileStepper.in=" + DEFAULT_PROFILE_STEPPER + "," + UPDATED_PROFILE_STEPPER);

        // Get all the memberList where profileStepper equals to UPDATED_PROFILE_STEPPER
        defaultMemberShouldNotBeFound("profileStepper.in=" + UPDATED_PROFILE_STEPPER);
    }

    @Test
    @Transactional
    void getAllMembersByProfileStepperIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where profileStepper is not null
        defaultMemberShouldBeFound("profileStepper.specified=true");

        // Get all the memberList where profileStepper is null
        defaultMemberShouldNotBeFound("profileStepper.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultMemberShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the memberList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultMemberShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the memberList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModified is not null
        defaultMemberShouldBeFound("lastModified.specified=true");

        // Get all the memberList where lastModified is null
        defaultMemberShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultMemberShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the memberList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy is not null
        defaultMemberShouldBeFound("lastModifiedBy.specified=true");

        // Get all the memberList where lastModifiedBy is null
        defaultMemberShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultMemberShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultMemberShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultMemberShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdBy equals to DEFAULT_CREATED_BY
        defaultMemberShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the memberList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultMemberShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the memberList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdBy is not null
        defaultMemberShouldBeFound("createdBy.specified=true");

        // Get all the memberList where createdBy is null
        defaultMemberShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdBy contains DEFAULT_CREATED_BY
        defaultMemberShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the memberList where createdBy contains UPDATED_CREATED_BY
        defaultMemberShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdBy does not contain DEFAULT_CREATED_BY
        defaultMemberShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the memberList where createdBy does not contain UPDATED_CREATED_BY
        defaultMemberShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMembersByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdOn equals to DEFAULT_CREATED_ON
        defaultMemberShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the memberList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMembersByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultMemberShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the memberList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMembersByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where createdOn is not null
        defaultMemberShouldBeFound("createdOn.specified=true");

        // Get all the memberList where createdOn is null
        defaultMemberShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultMemberShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultMemberShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultMemberShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the memberList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultMemberShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField1 is not null
        defaultMemberShouldBeFound("freeField1.specified=true");

        // Get all the memberList where freeField1 is null
        defaultMemberShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultMemberShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultMemberShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultMemberShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultMemberShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultMemberShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultMemberShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultMemberShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the memberList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultMemberShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField2 is not null
        defaultMemberShouldBeFound("freeField2.specified=true");

        // Get all the memberList where freeField2 is null
        defaultMemberShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultMemberShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultMemberShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultMemberShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultMemberShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultMemberShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultMemberShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultMemberShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the memberList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultMemberShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField3 is not null
        defaultMemberShouldBeFound("freeField3.specified=true");

        // Get all the memberList where freeField3 is null
        defaultMemberShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultMemberShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultMemberShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultMemberShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultMemberShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMembersByResidenceIsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where residence equals to DEFAULT_FREE_FIELD_4
        defaultMemberShouldBeFound("residence.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the memberList where residence equals to UPDATED_FREE_FIELD_4
        defaultMemberShouldNotBeFound("residence.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMembersByResidenceIsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where residence in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultMemberShouldBeFound("residence.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the memberList where residence equals to UPDATED_FREE_FIELD_4
        defaultMemberShouldNotBeFound("residence.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMembersByResidenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where residence is not null
        defaultMemberShouldBeFound("residence.specified=true");

        // Get all the memberList where residence is null
        defaultMemberShouldNotBeFound("residence.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByResidenceContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where residence contains DEFAULT_FREE_FIELD_4
        defaultMemberShouldBeFound("residence.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the memberList where residence contains UPDATED_FREE_FIELD_4
        defaultMemberShouldNotBeFound("residence.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMembersByResidenceNotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where residence does not contain DEFAULT_FREE_FIELD_4
        defaultMemberShouldNotBeFound("residence.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the memberList where residence does not contain UPDATED_FREE_FIELD_4
        defaultMemberShouldBeFound("residence.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultMemberShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the memberList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultMemberShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultMemberShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the memberList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultMemberShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField5 is not null
        defaultMemberShouldBeFound("freeField5.specified=true");

        // Get all the memberList where freeField5 is null
        defaultMemberShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultMemberShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the memberList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultMemberShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultMemberShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the memberList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultMemberShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultMemberShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the memberList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultMemberShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultMemberShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the memberList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultMemberShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField6 is not null
        defaultMemberShouldBeFound("freeField6.specified=true");

        // Get all the memberList where freeField6 is null
        defaultMemberShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllMembersByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultMemberShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the memberList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultMemberShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMembersByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        // Get all the memberList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultMemberShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the memberList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultMemberShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMembersByEnquiryDetailsIsEqualToSomething() throws Exception {
        EnquiryDetails enquiryDetails;
        if (TestUtil.findAll(em, EnquiryDetails.class).isEmpty()) {
            memberRepository.saveAndFlush(member);
            enquiryDetails = EnquiryDetailsResourceIT.createEntity(em);
        } else {
            enquiryDetails = TestUtil.findAll(em, EnquiryDetails.class).get(0);
        }
        em.persist(enquiryDetails);
        em.flush();
        //     member.setEnquiryDetails(enquiryDetails);
        memberRepository.saveAndFlush(member);
        Long enquiryDetailsId = enquiryDetails.getId();

        // Get all the memberList where enquiryDetails equals to enquiryDetailsId
        defaultMemberShouldBeFound("enquiryDetailsId.equals=" + enquiryDetailsId);

        // Get all the memberList where enquiryDetails equals to (enquiryDetailsId + 1)
        defaultMemberShouldNotBeFound("enquiryDetailsId.equals=" + (enquiryDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllMembersByBranchIsEqualToSomething() throws Exception {
        Branch branch;
        if (TestUtil.findAll(em, Branch.class).isEmpty()) {
            memberRepository.saveAndFlush(member);
            branch = BranchResourceIT.createEntity(em);
        } else {
            branch = TestUtil.findAll(em, Branch.class).get(0);
        }
        em.persist(branch);
        em.flush();
        //        member.setBranch(branch);
        memberRepository.saveAndFlush(member);
        Long branchId = branch.getId();

        // Get all the memberList where branch equals to branchId
        defaultMemberShouldBeFound("branchId.equals=" + branchId);

        // Get all the memberList where branch equals to (branchId + 1)
        defaultMemberShouldNotBeFound("branchId.equals=" + (branchId + 1));
    }

    @Test
    @Transactional
    void getAllMembersByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            //        memberRepository.saveAndFlush(member);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        //       member.setMember(member);
        memberRepository.saveAndFlush(member);
        Long memberId = member.getId();

        // Get all the memberList where member equals to memberId
        defaultMemberShouldBeFound("memberId.equals=" + memberId);

        // Get all the memberList where member equals to (memberId + 1)
        defaultMemberShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    @Test
    @Transactional
    void getAllMembersBySecurityUserIsEqualToSomething() throws Exception {
        SecurityUser securityUser;
        if (TestUtil.findAll(em, SecurityUser.class).isEmpty()) {
            memberRepository.saveAndFlush(member);
            securityUser = SecurityUserResourceIT.createEntity(em);
        } else {
            securityUser = TestUtil.findAll(em, SecurityUser.class).get(0);
        }
        em.persist(securityUser);
        em.flush();
        member.setSecurityUser(securityUser);
        memberRepository.saveAndFlush(member);
        Long securityUserId = securityUser.getId();

        // Get all the memberList where securityUser equals to securityUserId
        defaultMemberShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the memberList where securityUser equals to (securityUserId + 1)
        defaultMemberShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMemberShouldBeFound(String filter) throws Exception {
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(member.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            //     .andExpect(jsonPath("$.[*].memberId").value(hasItem(DEFAULT_MEMBER_ID)))
            .andExpect(jsonPath("$.[*].membershipNo").value(hasItem(DEFAULT_MEMBERSHIP_NO)))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].alternateMobile").value(hasItem(DEFAULT_ALTERNATE_MOBILE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].contactTimeFrom").value(hasItem(DEFAULT_CONTACT_TIME_FROM)))
            .andExpect(jsonPath("$.[*].contactTimeTo").value(hasItem(DEFAULT_CONTACT_TIME_TO)))
            .andExpect(jsonPath("$.[*].religion").value(hasItem(DEFAULT_RELIGION)))
            .andExpect(jsonPath("$.[*].custCategory").value(hasItem(DEFAULT_CUST_CATEGORY)))
            .andExpect(jsonPath("$.[*].cast").value(hasItem(DEFAULT_CAST)))
            .andExpect(jsonPath("$.[*].aadharCardNo").value(hasItem(DEFAULT_AADHAR_CARD_NO)))
            .andExpect(jsonPath("$.[*].panCard").value(hasItem(DEFAULT_PAN_CARD)))
            .andExpect(jsonPath("$.[*].passportNo").value(hasItem(DEFAULT_PASSPORT_NO)))
            .andExpect(jsonPath("$.[*].passportExpiry").value(hasItem(DEFAULT_PASSPORT_EXPIRY)))
            .andExpect(jsonPath("$.[*].rationCard").value(hasItem(DEFAULT_RATION_CARD)))
            .andExpect(jsonPath("$.[*].residenceStatus").value(hasItem(DEFAULT_RESIDENCE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].familyMemberCount").value(hasItem(DEFAULT_FAMILY_MEMBER_COUNT.intValue())))
            .andExpect(jsonPath("$.[*].occupation").value(hasItem(DEFAULT_OCCUPATION.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].noOfDependents").value(hasItem(DEFAULT_NO_OF_DEPENDENTS.intValue())))
            .andExpect(jsonPath("$.[*].applicationDate").value(hasItem(DEFAULT_APPLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].highestQualification").value(hasItem(DEFAULT_HIGHEST_QUALIFICATION)))
            .andExpect(jsonPath("$.[*].hasAdharCardVerified").value(hasItem(DEFAULT_HAS_ADHAR_CARD_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].hasPanCardVerified").value(hasItem(DEFAULT_HAS_PAN_CARD_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].loanStatus").value(hasItem(DEFAULT_LOAN_STATUS.toString())))
            .andExpect(jsonPath("$.[*].enqRefrenceNo").value(hasItem(DEFAULT_ENQ_REFRENCE_NO)))
            .andExpect(jsonPath("$.[*].numberOfAssets").value(hasItem(DEFAULT_NUMBER_OF_ASSETS)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].profileStepper").value(hasItem(DEFAULT_PROFILE_STEPPER.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].residence").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));

        // Check, that the count call also returns 1
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMemberShouldNotBeFound(String filter) throws Exception {
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMemberMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMember() throws Exception {
        // Get the member
        restMemberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member
        Member updatedMember = memberRepository.findById(member.getId()).get();
        // Disconnect from session so that the updates on updatedMember are not directly saved in db
        em.detach(updatedMember);
        updatedMember
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            //        .memberId(UPDATED_MEMBER_ID)
            .membershipNo(UPDATED_MEMBERSHIP_NO)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .gender(UPDATED_GENDER)
            .dob(UPDATED_DOB)
            .email(UPDATED_EMAIL)
            .mobileNo(UPDATED_MOBILE_NO)
            .alternateMobile(UPDATED_ALTERNATE_MOBILE)
            .fax(UPDATED_FAX)
            .contactTimeFrom(UPDATED_CONTACT_TIME_FROM)
            .contactTimeTo(UPDATED_CONTACT_TIME_TO)
            .religion(UPDATED_RELIGION)
            .custCategory(UPDATED_CUST_CATEGORY)
            .cast(UPDATED_CAST)
            .aadharCardNo(UPDATED_AADHAR_CARD_NO)
            .panCard(UPDATED_PAN_CARD)
            .passportNo(UPDATED_PASSPORT_NO)
            .passportExpiry(UPDATED_PASSPORT_EXPIRY)
            .rationCard(UPDATED_RATION_CARD)
            .residenceStatus(UPDATED_RESIDENCE_STATUS)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .familyMemberCount(UPDATED_FAMILY_MEMBER_COUNT)
            .occupation(UPDATED_OCCUPATION)
            .nationality(UPDATED_NATIONALITY)
            .noOfDependents(UPDATED_NO_OF_DEPENDENTS)
            .applicationDate(UPDATED_APPLICATION_DATE)
            .status(UPDATED_STATUS)
            .highestQualification(UPDATED_HIGHEST_QUALIFICATION)
            .hasAdharCardVerified(UPDATED_HAS_ADHAR_CARD_VERIFIED)
            .hasPanCardVerified(UPDATED_HAS_PAN_CARD_VERIFIED)
            .loanStatus(UPDATED_LOAN_STATUS)
            .enqRefrenceNo(UPDATED_ENQ_REFRENCE_NO)
            .numberOfAssets(UPDATED_NUMBER_OF_ASSETS)
            .isActive(UPDATED_IS_ACTIVE)
            .isDeleted(UPDATED_IS_DELETED)
            .profileStepper(UPDATED_PROFILE_STEPPER)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .residence(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        MemberDTO memberDTO = memberMapper.toDto(updatedMember);

        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMember.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMember.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testMember.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        //       assertThat(testMember.getMemberId()).isEqualTo(UPDATED_MEMBER_ID);
        assertThat(testMember.getMembershipNo()).isEqualTo(UPDATED_MEMBERSHIP_NO);
        assertThat(testMember.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testMember.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testMember.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testMember.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testMember.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMember.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testMember.getAlternateMobile()).isEqualTo(UPDATED_ALTERNATE_MOBILE);
        assertThat(testMember.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testMember.getContactTimeFrom()).isEqualTo(UPDATED_CONTACT_TIME_FROM);
        assertThat(testMember.getContactTimeTo()).isEqualTo(UPDATED_CONTACT_TIME_TO);
        assertThat(testMember.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testMember.getCustCategory()).isEqualTo(UPDATED_CUST_CATEGORY);
        assertThat(testMember.getCast()).isEqualTo(UPDATED_CAST);
        assertThat(testMember.getAadharCardNo()).isEqualTo(UPDATED_AADHAR_CARD_NO);
        assertThat(testMember.getPanCard()).isEqualTo(UPDATED_PAN_CARD);
        assertThat(testMember.getPassportNo()).isEqualTo(UPDATED_PASSPORT_NO);
        assertThat(testMember.getPassportExpiry()).isEqualTo(UPDATED_PASSPORT_EXPIRY);
        assertThat(testMember.getRationCard()).isEqualTo(UPDATED_RATION_CARD);
        assertThat(testMember.getResidenceStatus()).isEqualTo(UPDATED_RESIDENCE_STATUS);
        assertThat(testMember.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testMember.getFamilyMemberCount()).isEqualTo(UPDATED_FAMILY_MEMBER_COUNT);
        assertThat(testMember.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testMember.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testMember.getNoOfDependents()).isEqualTo(UPDATED_NO_OF_DEPENDENTS);
        assertThat(testMember.getApplicationDate()).isEqualTo(UPDATED_APPLICATION_DATE);
        assertThat(testMember.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMember.getHighestQualification()).isEqualTo(UPDATED_HIGHEST_QUALIFICATION);
        assertThat(testMember.getHasAdharCardVerified()).isEqualTo(UPDATED_HAS_ADHAR_CARD_VERIFIED);
        assertThat(testMember.getHasPanCardVerified()).isEqualTo(UPDATED_HAS_PAN_CARD_VERIFIED);
        assertThat(testMember.getLoanStatus()).isEqualTo(UPDATED_LOAN_STATUS);
        assertThat(testMember.getEnqRefrenceNo()).isEqualTo(UPDATED_ENQ_REFRENCE_NO);
        assertThat(testMember.getNumberOfAssets()).isEqualTo(UPDATED_NUMBER_OF_ASSETS);
        assertThat(testMember.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testMember.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMember.getProfileStepper()).isEqualTo(UPDATED_PROFILE_STEPPER);
        assertThat(testMember.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMember.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMember.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMember.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMember.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMember.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMember.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMember.getResidence()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testMember.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testMember.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberWithPatch() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member using partial update
        Member partialUpdatedMember = new Member();
        partialUpdatedMember.setId(member.getId());

        partialUpdatedMember
            .title(UPDATED_TITLE)
            .middleName(UPDATED_MIDDLE_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .gender(UPDATED_GENDER)
            .email(UPDATED_EMAIL)
            .fax(UPDATED_FAX)
            .contactTimeFrom(UPDATED_CONTACT_TIME_FROM)
            .religion(UPDATED_RELIGION)
            .passportNo(UPDATED_PASSPORT_NO)
            .passportExpiry(UPDATED_PASSPORT_EXPIRY)
            .residenceStatus(UPDATED_RESIDENCE_STATUS)
            .familyMemberCount(UPDATED_FAMILY_MEMBER_COUNT)
            .occupation(UPDATED_OCCUPATION)
            .noOfDependents(UPDATED_NO_OF_DEPENDENTS)
            .status(UPDATED_STATUS)
            .highestQualification(UPDATED_HIGHEST_QUALIFICATION)
            .hasAdharCardVerified(UPDATED_HAS_ADHAR_CARD_VERIFIED)
            .numberOfAssets(UPDATED_NUMBER_OF_ASSETS)
            .isActive(UPDATED_IS_ACTIVE)
            .isDeleted(UPDATED_IS_DELETED)
            .lastModified(UPDATED_LAST_MODIFIED)
            .createdBy(UPDATED_CREATED_BY)
            .freeField3(UPDATED_FREE_FIELD_3);

        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMember))
            )
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMember.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testMember.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testMember.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        //      assertThat(testMember.getMemberId()).isEqualTo(DEFAULT_MEMBER_ID);
        assertThat(testMember.getMembershipNo()).isEqualTo(DEFAULT_MEMBERSHIP_NO);
        assertThat(testMember.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testMember.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testMember.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testMember.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testMember.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMember.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testMember.getAlternateMobile()).isEqualTo(DEFAULT_ALTERNATE_MOBILE);
        assertThat(testMember.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testMember.getContactTimeFrom()).isEqualTo(UPDATED_CONTACT_TIME_FROM);
        assertThat(testMember.getContactTimeTo()).isEqualTo(DEFAULT_CONTACT_TIME_TO);
        assertThat(testMember.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testMember.getCustCategory()).isEqualTo(DEFAULT_CUST_CATEGORY);
        assertThat(testMember.getCast()).isEqualTo(DEFAULT_CAST);
        assertThat(testMember.getAadharCardNo()).isEqualTo(DEFAULT_AADHAR_CARD_NO);
        assertThat(testMember.getPanCard()).isEqualTo(DEFAULT_PAN_CARD);
        assertThat(testMember.getPassportNo()).isEqualTo(UPDATED_PASSPORT_NO);
        assertThat(testMember.getPassportExpiry()).isEqualTo(UPDATED_PASSPORT_EXPIRY);
        assertThat(testMember.getRationCard()).isEqualTo(DEFAULT_RATION_CARD);
        assertThat(testMember.getResidenceStatus()).isEqualTo(UPDATED_RESIDENCE_STATUS);
        assertThat(testMember.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testMember.getFamilyMemberCount()).isEqualTo(UPDATED_FAMILY_MEMBER_COUNT);
        assertThat(testMember.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testMember.getNationality()).isEqualTo(DEFAULT_NATIONALITY);
        assertThat(testMember.getNoOfDependents()).isEqualTo(UPDATED_NO_OF_DEPENDENTS);
        assertThat(testMember.getApplicationDate()).isEqualTo(DEFAULT_APPLICATION_DATE);
        assertThat(testMember.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMember.getHighestQualification()).isEqualTo(UPDATED_HIGHEST_QUALIFICATION);
        assertThat(testMember.getHasAdharCardVerified()).isEqualTo(UPDATED_HAS_ADHAR_CARD_VERIFIED);
        assertThat(testMember.getHasPanCardVerified()).isEqualTo(DEFAULT_HAS_PAN_CARD_VERIFIED);
        assertThat(testMember.getLoanStatus()).isEqualTo(DEFAULT_LOAN_STATUS);
        assertThat(testMember.getEnqRefrenceNo()).isEqualTo(DEFAULT_ENQ_REFRENCE_NO);
        assertThat(testMember.getNumberOfAssets()).isEqualTo(UPDATED_NUMBER_OF_ASSETS);
        assertThat(testMember.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testMember.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMember.getProfileStepper()).isEqualTo(DEFAULT_PROFILE_STEPPER);
        assertThat(testMember.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMember.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMember.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMember.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testMember.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testMember.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testMember.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMember.getResidence()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testMember.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testMember.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateMemberWithPatch() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeUpdate = memberRepository.findAll().size();

        // Update the member using partial update
        Member partialUpdatedMember = new Member();
        partialUpdatedMember.setId(member.getId());

        partialUpdatedMember
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            //        .memberId(UPDATED_MEMBER_ID)
            .membershipNo(UPDATED_MEMBERSHIP_NO)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .gender(UPDATED_GENDER)
            .dob(UPDATED_DOB)
            .email(UPDATED_EMAIL)
            .mobileNo(UPDATED_MOBILE_NO)
            .alternateMobile(UPDATED_ALTERNATE_MOBILE)
            .fax(UPDATED_FAX)
            .contactTimeFrom(UPDATED_CONTACT_TIME_FROM)
            .contactTimeTo(UPDATED_CONTACT_TIME_TO)
            .religion(UPDATED_RELIGION)
            .custCategory(UPDATED_CUST_CATEGORY)
            .cast(UPDATED_CAST)
            .aadharCardNo(UPDATED_AADHAR_CARD_NO)
            .panCard(UPDATED_PAN_CARD)
            .passportNo(UPDATED_PASSPORT_NO)
            .passportExpiry(UPDATED_PASSPORT_EXPIRY)
            .rationCard(UPDATED_RATION_CARD)
            .residenceStatus(UPDATED_RESIDENCE_STATUS)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .familyMemberCount(UPDATED_FAMILY_MEMBER_COUNT)
            .occupation(UPDATED_OCCUPATION)
            .nationality(UPDATED_NATIONALITY)
            .noOfDependents(UPDATED_NO_OF_DEPENDENTS)
            .applicationDate(UPDATED_APPLICATION_DATE)
            .status(UPDATED_STATUS)
            .highestQualification(UPDATED_HIGHEST_QUALIFICATION)
            .hasAdharCardVerified(UPDATED_HAS_ADHAR_CARD_VERIFIED)
            .hasPanCardVerified(UPDATED_HAS_PAN_CARD_VERIFIED)
            .loanStatus(UPDATED_LOAN_STATUS)
            .enqRefrenceNo(UPDATED_ENQ_REFRENCE_NO)
            .numberOfAssets(UPDATED_NUMBER_OF_ASSETS)
            .isActive(UPDATED_IS_ACTIVE)
            .isDeleted(UPDATED_IS_DELETED)
            .profileStepper(UPDATED_PROFILE_STEPPER)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .residence(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMember))
            )
            .andExpect(status().isOk());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
        Member testMember = memberList.get(memberList.size() - 1);
        assertThat(testMember.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testMember.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMember.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testMember.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        //      assertThat(testMember.getMemberId()).isEqualTo(UPDATED_MEMBER_ID);
        assertThat(testMember.getMembershipNo()).isEqualTo(UPDATED_MEMBERSHIP_NO);
        assertThat(testMember.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testMember.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testMember.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testMember.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testMember.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMember.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testMember.getAlternateMobile()).isEqualTo(UPDATED_ALTERNATE_MOBILE);
        assertThat(testMember.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testMember.getContactTimeFrom()).isEqualTo(UPDATED_CONTACT_TIME_FROM);
        assertThat(testMember.getContactTimeTo()).isEqualTo(UPDATED_CONTACT_TIME_TO);
        assertThat(testMember.getReligion()).isEqualTo(UPDATED_RELIGION);
        assertThat(testMember.getCustCategory()).isEqualTo(UPDATED_CUST_CATEGORY);
        assertThat(testMember.getCast()).isEqualTo(UPDATED_CAST);
        assertThat(testMember.getAadharCardNo()).isEqualTo(UPDATED_AADHAR_CARD_NO);
        assertThat(testMember.getPanCard()).isEqualTo(UPDATED_PAN_CARD);
        assertThat(testMember.getPassportNo()).isEqualTo(UPDATED_PASSPORT_NO);
        assertThat(testMember.getPassportExpiry()).isEqualTo(UPDATED_PASSPORT_EXPIRY);
        assertThat(testMember.getRationCard()).isEqualTo(UPDATED_RATION_CARD);
        assertThat(testMember.getResidenceStatus()).isEqualTo(UPDATED_RESIDENCE_STATUS);
        assertThat(testMember.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testMember.getFamilyMemberCount()).isEqualTo(UPDATED_FAMILY_MEMBER_COUNT);
        assertThat(testMember.getOccupation()).isEqualTo(UPDATED_OCCUPATION);
        assertThat(testMember.getNationality()).isEqualTo(UPDATED_NATIONALITY);
        assertThat(testMember.getNoOfDependents()).isEqualTo(UPDATED_NO_OF_DEPENDENTS);
        assertThat(testMember.getApplicationDate()).isEqualTo(UPDATED_APPLICATION_DATE);
        assertThat(testMember.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMember.getHighestQualification()).isEqualTo(UPDATED_HIGHEST_QUALIFICATION);
        assertThat(testMember.getHasAdharCardVerified()).isEqualTo(UPDATED_HAS_ADHAR_CARD_VERIFIED);
        assertThat(testMember.getHasPanCardVerified()).isEqualTo(UPDATED_HAS_PAN_CARD_VERIFIED);
        assertThat(testMember.getLoanStatus()).isEqualTo(UPDATED_LOAN_STATUS);
        assertThat(testMember.getEnqRefrenceNo()).isEqualTo(UPDATED_ENQ_REFRENCE_NO);
        assertThat(testMember.getNumberOfAssets()).isEqualTo(UPDATED_NUMBER_OF_ASSETS);
        assertThat(testMember.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testMember.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMember.getProfileStepper()).isEqualTo(UPDATED_PROFILE_STEPPER);
        assertThat(testMember.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMember.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMember.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMember.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMember.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMember.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMember.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMember.getResidence()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testMember.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testMember.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, memberDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMember() throws Exception {
        int databaseSizeBeforeUpdate = memberRepository.findAll().size();
        member.setId(count.incrementAndGet());

        // Create the Member
        MemberDTO memberDTO = memberMapper.toDto(member);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(memberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Member in the database
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMember() throws Exception {
        // Initialize the database
        memberRepository.saveAndFlush(member);

        int databaseSizeBeforeDelete = memberRepository.findAll().size();

        // Delete the member
        restMemberMockMvc
            .perform(delete(ENTITY_API_URL_ID, member.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Member> memberList = memberRepository.findAll();
        assertThat(memberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
