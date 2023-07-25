package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.LoanApplications;
import com.techvg.los.domain.Member;
import com.techvg.los.domain.MemberAssets;
import com.techvg.los.domain.enumeration.AssetNature;
import com.techvg.los.domain.enumeration.AssetType;
import com.techvg.los.repository.MemberAssetsRepository;
import com.techvg.los.service.criteria.MemberAssetsCriteria;
import com.techvg.los.service.dto.MemberAssetsDTO;
import com.techvg.los.service.mapper.MemberAssetsMapper;
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
 * Integration tests for the {@link MemberAssetsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberAssetsResourceIT {

    private static final Double DEFAULT_ASSET_COST = 1D;
    private static final Double UPDATED_ASSET_COST = 2D;
    private static final Double SMALLER_ASSET_COST = 1D - 1D;

    //    private static final AssetType DEFAULT_ASSET_TYPE = AssetType.FARM_MACHINERY;
    //    private static final AssetType UPDATED_ASSET_TYPE = AssetType.HOUSE;

    private static final Double DEFAULT_AREA_IN_SQ_FT = 1D;
    private static final Double UPDATED_AREA_IN_SQ_FT = 2D;
    private static final Double SMALLER_AREA_IN_SQ_FT = 1D - 1D;

    private static final AssetNature DEFAULT_ASSET_NATURE = AssetNature.RESIDENTIAL;
    private static final AssetNature UPDATED_ASSET_NATURE = AssetNature.COMMERCIAL;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_LAND_MARK = "AAAAAAAAAA";
    private static final String UPDATED_LAND_MARK = "BBBBBBBBBB";

    private static final String DEFAULT_ASSET_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLETION_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_COMPLETION_YEAR = "BBBBBBBBBB";

    private static final Double DEFAULT_MARKET_VALUE = 1D;
    private static final Double UPDATED_MARKET_VALUE = 2D;
    private static final Double SMALLER_MARKET_VALUE = 1D - 1D;

    private static final Boolean DEFAULT_IS_INSURED = false;
    private static final Boolean UPDATED_IS_INSURED = true;

    private static final Boolean DEFAULT_IS_UNDER_USED = false;
    private static final Boolean UPDATED_IS_UNDER_USED = true;

    private static final Boolean DEFAULT_IS_OWNED = false;
    private static final Boolean UPDATED_IS_OWNED = true;

    private static final String DEFAULT_LAND_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LAND_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LAND_GAT_NO = "AAAAAAAAAA";
    private static final String UPDATED_LAND_GAT_NO = "BBBBBBBBBB";

    private static final Double DEFAULT_LAND_AREA_IN_HECTOR = 1D;
    private static final Double UPDATED_LAND_AREA_IN_HECTOR = 2D;
    private static final Double SMALLER_LAND_AREA_IN_HECTOR = 1D - 1D;

    private static final String DEFAULT_JINDAGI_PATRAK_NO = "AAAAAAAAAA";
    private static final String UPDATED_JINDAGI_PATRAK_NO = "BBBBBBBBBB";

    private static final Double DEFAULT_JINDAGI_AMOUNT = 1D;
    private static final Double UPDATED_JINDAGI_AMOUNT = 2D;
    private static final Double SMALLER_JINDAGI_AMOUNT = 1D - 1D;

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

    private static final String DEFAULT_FREE_FIELD_5 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_5 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_6 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_6 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/member-assets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberAssetsRepository memberAssetsRepository;

    @Autowired
    private MemberAssetsMapper memberAssetsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberAssetsMockMvc;

    private MemberAssets memberAssets;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberAssets createEntity(EntityManager em) {
        MemberAssets memberAssets = new MemberAssets()
            .assetCost(DEFAULT_ASSET_COST)
            //            .assetType(DEFAULT_ASSET_TYPE)
            .areaInSqFt(DEFAULT_AREA_IN_SQ_FT)
            .assetNature(DEFAULT_ASSET_NATURE)
            .address(DEFAULT_ADDRESS)
            .landMark(DEFAULT_LAND_MARK)
            .assetOwner(DEFAULT_ASSET_OWNER)
            .completionYear(DEFAULT_COMPLETION_YEAR)
            .marketValue(DEFAULT_MARKET_VALUE)
            .isInsured(DEFAULT_IS_INSURED)
            .isUnderUsed(DEFAULT_IS_UNDER_USED)
            .isOwned(DEFAULT_IS_OWNED)
            .landType(DEFAULT_LAND_TYPE)
            .landGatNo(DEFAULT_LAND_GAT_NO)
            .landAreaInHector(DEFAULT_LAND_AREA_IN_HECTOR)
            .jindagiPatrakNo(DEFAULT_JINDAGI_PATRAK_NO)
            .jindagiAmount(DEFAULT_JINDAGI_AMOUNT)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5)
            .freeField6(DEFAULT_FREE_FIELD_6);
        return memberAssets;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberAssets createUpdatedEntity(EntityManager em) {
        MemberAssets memberAssets = new MemberAssets()
            .assetCost(UPDATED_ASSET_COST)
            //            .assetType(UPDATED_ASSET_TYPE)
            .areaInSqFt(UPDATED_AREA_IN_SQ_FT)
            .assetNature(UPDATED_ASSET_NATURE)
            .address(UPDATED_ADDRESS)
            .landMark(UPDATED_LAND_MARK)
            .assetOwner(UPDATED_ASSET_OWNER)
            .completionYear(UPDATED_COMPLETION_YEAR)
            .marketValue(UPDATED_MARKET_VALUE)
            .isInsured(UPDATED_IS_INSURED)
            .isUnderUsed(UPDATED_IS_UNDER_USED)
            .isOwned(UPDATED_IS_OWNED)
            .landType(UPDATED_LAND_TYPE)
            .landGatNo(UPDATED_LAND_GAT_NO)
            .landAreaInHector(UPDATED_LAND_AREA_IN_HECTOR)
            .jindagiPatrakNo(UPDATED_JINDAGI_PATRAK_NO)
            .jindagiAmount(UPDATED_JINDAGI_AMOUNT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        return memberAssets;
    }

    @BeforeEach
    public void initTest() {
        memberAssets = createEntity(em);
    }

    @Test
    @Transactional
    void createMemberAssets() throws Exception {
        int databaseSizeBeforeCreate = memberAssetsRepository.findAll().size();
        // Create the MemberAssets
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);
        restMemberAssetsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeCreate + 1);
        MemberAssets testMemberAssets = memberAssetsList.get(memberAssetsList.size() - 1);
        assertThat(testMemberAssets.getAssetCost()).isEqualTo(DEFAULT_ASSET_COST);
        //        assertThat(testMemberAssets.getAssetType()).isEqualTo(DEFAULT_ASSET_TYPE);
        assertThat(testMemberAssets.getAreaInSqFt()).isEqualTo(DEFAULT_AREA_IN_SQ_FT);
        assertThat(testMemberAssets.getAssetNature()).isEqualTo(DEFAULT_ASSET_NATURE);
        assertThat(testMemberAssets.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testMemberAssets.getLandMark()).isEqualTo(DEFAULT_LAND_MARK);
        assertThat(testMemberAssets.getAssetOwner()).isEqualTo(DEFAULT_ASSET_OWNER);
        assertThat(testMemberAssets.getCompletionYear()).isEqualTo(DEFAULT_COMPLETION_YEAR);
        assertThat(testMemberAssets.getMarketValue()).isEqualTo(DEFAULT_MARKET_VALUE);
        assertThat(testMemberAssets.getIsInsured()).isEqualTo(DEFAULT_IS_INSURED);
        assertThat(testMemberAssets.getIsUnderUsed()).isEqualTo(DEFAULT_IS_UNDER_USED);
        assertThat(testMemberAssets.getIsOwned()).isEqualTo(DEFAULT_IS_OWNED);
        assertThat(testMemberAssets.getLandType()).isEqualTo(DEFAULT_LAND_TYPE);
        assertThat(testMemberAssets.getLandGatNo()).isEqualTo(DEFAULT_LAND_GAT_NO);
        assertThat(testMemberAssets.getLandAreaInHector()).isEqualTo(DEFAULT_LAND_AREA_IN_HECTOR);
        assertThat(testMemberAssets.getJindagiPatrakNo()).isEqualTo(DEFAULT_JINDAGI_PATRAK_NO);
        assertThat(testMemberAssets.getJindagiAmount()).isEqualTo(DEFAULT_JINDAGI_AMOUNT);
        assertThat(testMemberAssets.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMemberAssets.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMemberAssets.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMemberAssets.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testMemberAssets.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMemberAssets.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testMemberAssets.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testMemberAssets.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testMemberAssets.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testMemberAssets.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
        assertThat(testMemberAssets.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void createMemberAssetsWithExistingId() throws Exception {
        // Create the MemberAssets with an existing ID
        memberAssets.setId(1L);
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);

        int databaseSizeBeforeCreate = memberAssetsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberAssetsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMemberAssets() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList
        restMemberAssetsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberAssets.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetCost").value(hasItem(DEFAULT_ASSET_COST.doubleValue())))
            //            .andExpect(jsonPath("$.[*].assetType").value(hasItem(DEFAULT_ASSET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].areaInSqFt").value(hasItem(DEFAULT_AREA_IN_SQ_FT.doubleValue())))
            .andExpect(jsonPath("$.[*].assetNature").value(hasItem(DEFAULT_ASSET_NATURE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].landMark").value(hasItem(DEFAULT_LAND_MARK)))
            .andExpect(jsonPath("$.[*].assetOwner").value(hasItem(DEFAULT_ASSET_OWNER)))
            .andExpect(jsonPath("$.[*].completionYear").value(hasItem(DEFAULT_COMPLETION_YEAR)))
            .andExpect(jsonPath("$.[*].marketValue").value(hasItem(DEFAULT_MARKET_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].isInsured").value(hasItem(DEFAULT_IS_INSURED.booleanValue())))
            .andExpect(jsonPath("$.[*].isUnderUsed").value(hasItem(DEFAULT_IS_UNDER_USED.booleanValue())))
            .andExpect(jsonPath("$.[*].isOwned").value(hasItem(DEFAULT_IS_OWNED.booleanValue())))
            .andExpect(jsonPath("$.[*].landType").value(hasItem(DEFAULT_LAND_TYPE)))
            .andExpect(jsonPath("$.[*].landGatNo").value(hasItem(DEFAULT_LAND_GAT_NO)))
            .andExpect(jsonPath("$.[*].landAreaInHector").value(hasItem(DEFAULT_LAND_AREA_IN_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].jindagiPatrakNo").value(hasItem(DEFAULT_JINDAGI_PATRAK_NO)))
            .andExpect(jsonPath("$.[*].jindagiAmount").value(hasItem(DEFAULT_JINDAGI_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));
    }

    @Test
    @Transactional
    void getMemberAssets() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get the memberAssets
        restMemberAssetsMockMvc
            .perform(get(ENTITY_API_URL_ID, memberAssets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(memberAssets.getId().intValue()))
            .andExpect(jsonPath("$.assetCost").value(DEFAULT_ASSET_COST.doubleValue()))
            //            .andExpect(jsonPath("$.assetType").value(DEFAULT_ASSET_TYPE.toString()))
            .andExpect(jsonPath("$.areaInSqFt").value(DEFAULT_AREA_IN_SQ_FT.doubleValue()))
            .andExpect(jsonPath("$.assetNature").value(DEFAULT_ASSET_NATURE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.landMark").value(DEFAULT_LAND_MARK))
            .andExpect(jsonPath("$.assetOwner").value(DEFAULT_ASSET_OWNER))
            .andExpect(jsonPath("$.completionYear").value(DEFAULT_COMPLETION_YEAR))
            .andExpect(jsonPath("$.marketValue").value(DEFAULT_MARKET_VALUE.doubleValue()))
            .andExpect(jsonPath("$.isInsured").value(DEFAULT_IS_INSURED.booleanValue()))
            .andExpect(jsonPath("$.isUnderUsed").value(DEFAULT_IS_UNDER_USED.booleanValue()))
            .andExpect(jsonPath("$.isOwned").value(DEFAULT_IS_OWNED.booleanValue()))
            .andExpect(jsonPath("$.landType").value(DEFAULT_LAND_TYPE))
            .andExpect(jsonPath("$.landGatNo").value(DEFAULT_LAND_GAT_NO))
            .andExpect(jsonPath("$.landAreaInHector").value(DEFAULT_LAND_AREA_IN_HECTOR.doubleValue()))
            .andExpect(jsonPath("$.jindagiPatrakNo").value(DEFAULT_JINDAGI_PATRAK_NO))
            .andExpect(jsonPath("$.jindagiAmount").value(DEFAULT_JINDAGI_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.freeField5").value(DEFAULT_FREE_FIELD_5))
            .andExpect(jsonPath("$.freeField6").value(DEFAULT_FREE_FIELD_6));
    }

    @Test
    @Transactional
    void getMemberAssetsByIdFiltering() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        Long id = memberAssets.getId();

        defaultMemberAssetsShouldBeFound("id.equals=" + id);
        defaultMemberAssetsShouldNotBeFound("id.notEquals=" + id);

        defaultMemberAssetsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMemberAssetsShouldNotBeFound("id.greaterThan=" + id);

        defaultMemberAssetsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMemberAssetsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetCostIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetCost equals to DEFAULT_ASSET_COST
        defaultMemberAssetsShouldBeFound("assetCost.equals=" + DEFAULT_ASSET_COST);

        // Get all the memberAssetsList where assetCost equals to UPDATED_ASSET_COST
        defaultMemberAssetsShouldNotBeFound("assetCost.equals=" + UPDATED_ASSET_COST);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetCostIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetCost in DEFAULT_ASSET_COST or UPDATED_ASSET_COST
        defaultMemberAssetsShouldBeFound("assetCost.in=" + DEFAULT_ASSET_COST + "," + UPDATED_ASSET_COST);

        // Get all the memberAssetsList where assetCost equals to UPDATED_ASSET_COST
        defaultMemberAssetsShouldNotBeFound("assetCost.in=" + UPDATED_ASSET_COST);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetCost is not null
        defaultMemberAssetsShouldBeFound("assetCost.specified=true");

        // Get all the memberAssetsList where assetCost is null
        defaultMemberAssetsShouldNotBeFound("assetCost.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetCost is greater than or equal to DEFAULT_ASSET_COST
        defaultMemberAssetsShouldBeFound("assetCost.greaterThanOrEqual=" + DEFAULT_ASSET_COST);

        // Get all the memberAssetsList where assetCost is greater than or equal to UPDATED_ASSET_COST
        defaultMemberAssetsShouldNotBeFound("assetCost.greaterThanOrEqual=" + UPDATED_ASSET_COST);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetCost is less than or equal to DEFAULT_ASSET_COST
        defaultMemberAssetsShouldBeFound("assetCost.lessThanOrEqual=" + DEFAULT_ASSET_COST);

        // Get all the memberAssetsList where assetCost is less than or equal to SMALLER_ASSET_COST
        defaultMemberAssetsShouldNotBeFound("assetCost.lessThanOrEqual=" + SMALLER_ASSET_COST);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetCostIsLessThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetCost is less than DEFAULT_ASSET_COST
        defaultMemberAssetsShouldNotBeFound("assetCost.lessThan=" + DEFAULT_ASSET_COST);

        // Get all the memberAssetsList where assetCost is less than UPDATED_ASSET_COST
        defaultMemberAssetsShouldBeFound("assetCost.lessThan=" + UPDATED_ASSET_COST);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetCost is greater than DEFAULT_ASSET_COST
        defaultMemberAssetsShouldNotBeFound("assetCost.greaterThan=" + DEFAULT_ASSET_COST);

        // Get all the memberAssetsList where assetCost is greater than SMALLER_ASSET_COST
        defaultMemberAssetsShouldBeFound("assetCost.greaterThan=" + SMALLER_ASSET_COST);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);
        //
        //        // Get all the memberAssetsList where assetType equals to DEFAULT_ASSET_TYPE
        //        defaultMemberAssetsShouldBeFound("assetType.equals=" + DEFAULT_ASSET_TYPE);
        //
        //        // Get all the memberAssetsList where assetType equals to UPDATED_ASSET_TYPE
        //        defaultMemberAssetsShouldNotBeFound("assetType.equals=" + UPDATED_ASSET_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetTypeIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);
        // Get all the memberAssetsList where assetType in DEFAULT_ASSET_TYPE or UPDATED_ASSET_TYPE
        //        defaultMemberAssetsShouldBeFound("assetType.in=" + DEFAULT_ASSET_TYPE + "," + UPDATED_ASSET_TYPE);

        // Get all the memberAssetsList where assetType equals to UPDATED_ASSET_TYPE
        //        defaultMemberAssetsShouldNotBeFound("assetType.in=" + UPDATED_ASSET_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetType is not null
        defaultMemberAssetsShouldBeFound("assetType.specified=true");

        // Get all the memberAssetsList where assetType is null
        defaultMemberAssetsShouldNotBeFound("assetType.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAreaInSqFtIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where areaInSqFt equals to DEFAULT_AREA_IN_SQ_FT
        defaultMemberAssetsShouldBeFound("areaInSqFt.equals=" + DEFAULT_AREA_IN_SQ_FT);

        // Get all the memberAssetsList where areaInSqFt equals to UPDATED_AREA_IN_SQ_FT
        defaultMemberAssetsShouldNotBeFound("areaInSqFt.equals=" + UPDATED_AREA_IN_SQ_FT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAreaInSqFtIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where areaInSqFt in DEFAULT_AREA_IN_SQ_FT or UPDATED_AREA_IN_SQ_FT
        defaultMemberAssetsShouldBeFound("areaInSqFt.in=" + DEFAULT_AREA_IN_SQ_FT + "," + UPDATED_AREA_IN_SQ_FT);

        // Get all the memberAssetsList where areaInSqFt equals to UPDATED_AREA_IN_SQ_FT
        defaultMemberAssetsShouldNotBeFound("areaInSqFt.in=" + UPDATED_AREA_IN_SQ_FT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAreaInSqFtIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where areaInSqFt is not null
        defaultMemberAssetsShouldBeFound("areaInSqFt.specified=true");

        // Get all the memberAssetsList where areaInSqFt is null
        defaultMemberAssetsShouldNotBeFound("areaInSqFt.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAreaInSqFtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where areaInSqFt is greater than or equal to DEFAULT_AREA_IN_SQ_FT
        defaultMemberAssetsShouldBeFound("areaInSqFt.greaterThanOrEqual=" + DEFAULT_AREA_IN_SQ_FT);

        // Get all the memberAssetsList where areaInSqFt is greater than or equal to UPDATED_AREA_IN_SQ_FT
        defaultMemberAssetsShouldNotBeFound("areaInSqFt.greaterThanOrEqual=" + UPDATED_AREA_IN_SQ_FT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAreaInSqFtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where areaInSqFt is less than or equal to DEFAULT_AREA_IN_SQ_FT
        defaultMemberAssetsShouldBeFound("areaInSqFt.lessThanOrEqual=" + DEFAULT_AREA_IN_SQ_FT);

        // Get all the memberAssetsList where areaInSqFt is less than or equal to SMALLER_AREA_IN_SQ_FT
        defaultMemberAssetsShouldNotBeFound("areaInSqFt.lessThanOrEqual=" + SMALLER_AREA_IN_SQ_FT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAreaInSqFtIsLessThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where areaInSqFt is less than DEFAULT_AREA_IN_SQ_FT
        defaultMemberAssetsShouldNotBeFound("areaInSqFt.lessThan=" + DEFAULT_AREA_IN_SQ_FT);

        // Get all the memberAssetsList where areaInSqFt is less than UPDATED_AREA_IN_SQ_FT
        defaultMemberAssetsShouldBeFound("areaInSqFt.lessThan=" + UPDATED_AREA_IN_SQ_FT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAreaInSqFtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where areaInSqFt is greater than DEFAULT_AREA_IN_SQ_FT
        defaultMemberAssetsShouldNotBeFound("areaInSqFt.greaterThan=" + DEFAULT_AREA_IN_SQ_FT);

        // Get all the memberAssetsList where areaInSqFt is greater than SMALLER_AREA_IN_SQ_FT
        defaultMemberAssetsShouldBeFound("areaInSqFt.greaterThan=" + SMALLER_AREA_IN_SQ_FT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetNatureIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetNature equals to DEFAULT_ASSET_NATURE
        defaultMemberAssetsShouldBeFound("assetNature.equals=" + DEFAULT_ASSET_NATURE);

        // Get all the memberAssetsList where assetNature equals to UPDATED_ASSET_NATURE
        defaultMemberAssetsShouldNotBeFound("assetNature.equals=" + UPDATED_ASSET_NATURE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetNatureIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetNature in DEFAULT_ASSET_NATURE or UPDATED_ASSET_NATURE
        defaultMemberAssetsShouldBeFound("assetNature.in=" + DEFAULT_ASSET_NATURE + "," + UPDATED_ASSET_NATURE);

        // Get all the memberAssetsList where assetNature equals to UPDATED_ASSET_NATURE
        defaultMemberAssetsShouldNotBeFound("assetNature.in=" + UPDATED_ASSET_NATURE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetNatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetNature is not null
        defaultMemberAssetsShouldBeFound("assetNature.specified=true");

        // Get all the memberAssetsList where assetNature is null
        defaultMemberAssetsShouldNotBeFound("assetNature.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where address equals to DEFAULT_ADDRESS
        defaultMemberAssetsShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the memberAssetsList where address equals to UPDATED_ADDRESS
        defaultMemberAssetsShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultMemberAssetsShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the memberAssetsList where address equals to UPDATED_ADDRESS
        defaultMemberAssetsShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where address is not null
        defaultMemberAssetsShouldBeFound("address.specified=true");

        // Get all the memberAssetsList where address is null
        defaultMemberAssetsShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAddressContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where address contains DEFAULT_ADDRESS
        defaultMemberAssetsShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the memberAssetsList where address contains UPDATED_ADDRESS
        defaultMemberAssetsShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where address does not contain DEFAULT_ADDRESS
        defaultMemberAssetsShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the memberAssetsList where address does not contain UPDATED_ADDRESS
        defaultMemberAssetsShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandMarkIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landMark equals to DEFAULT_LAND_MARK
        defaultMemberAssetsShouldBeFound("landMark.equals=" + DEFAULT_LAND_MARK);

        // Get all the memberAssetsList where landMark equals to UPDATED_LAND_MARK
        defaultMemberAssetsShouldNotBeFound("landMark.equals=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandMarkIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landMark in DEFAULT_LAND_MARK or UPDATED_LAND_MARK
        defaultMemberAssetsShouldBeFound("landMark.in=" + DEFAULT_LAND_MARK + "," + UPDATED_LAND_MARK);

        // Get all the memberAssetsList where landMark equals to UPDATED_LAND_MARK
        defaultMemberAssetsShouldNotBeFound("landMark.in=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandMarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landMark is not null
        defaultMemberAssetsShouldBeFound("landMark.specified=true");

        // Get all the memberAssetsList where landMark is null
        defaultMemberAssetsShouldNotBeFound("landMark.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandMarkContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landMark contains DEFAULT_LAND_MARK
        defaultMemberAssetsShouldBeFound("landMark.contains=" + DEFAULT_LAND_MARK);

        // Get all the memberAssetsList where landMark contains UPDATED_LAND_MARK
        defaultMemberAssetsShouldNotBeFound("landMark.contains=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandMarkNotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landMark does not contain DEFAULT_LAND_MARK
        defaultMemberAssetsShouldNotBeFound("landMark.doesNotContain=" + DEFAULT_LAND_MARK);

        // Get all the memberAssetsList where landMark does not contain UPDATED_LAND_MARK
        defaultMemberAssetsShouldBeFound("landMark.doesNotContain=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetOwner equals to DEFAULT_ASSET_OWNER
        defaultMemberAssetsShouldBeFound("assetOwner.equals=" + DEFAULT_ASSET_OWNER);

        // Get all the memberAssetsList where assetOwner equals to UPDATED_ASSET_OWNER
        defaultMemberAssetsShouldNotBeFound("assetOwner.equals=" + UPDATED_ASSET_OWNER);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetOwnerIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetOwner in DEFAULT_ASSET_OWNER or UPDATED_ASSET_OWNER
        defaultMemberAssetsShouldBeFound("assetOwner.in=" + DEFAULT_ASSET_OWNER + "," + UPDATED_ASSET_OWNER);

        // Get all the memberAssetsList where assetOwner equals to UPDATED_ASSET_OWNER
        defaultMemberAssetsShouldNotBeFound("assetOwner.in=" + UPDATED_ASSET_OWNER);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetOwnerIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetOwner is not null
        defaultMemberAssetsShouldBeFound("assetOwner.specified=true");

        // Get all the memberAssetsList where assetOwner is null
        defaultMemberAssetsShouldNotBeFound("assetOwner.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetOwnerContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetOwner contains DEFAULT_ASSET_OWNER
        defaultMemberAssetsShouldBeFound("assetOwner.contains=" + DEFAULT_ASSET_OWNER);

        // Get all the memberAssetsList where assetOwner contains UPDATED_ASSET_OWNER
        defaultMemberAssetsShouldNotBeFound("assetOwner.contains=" + UPDATED_ASSET_OWNER);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByAssetOwnerNotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where assetOwner does not contain DEFAULT_ASSET_OWNER
        defaultMemberAssetsShouldNotBeFound("assetOwner.doesNotContain=" + DEFAULT_ASSET_OWNER);

        // Get all the memberAssetsList where assetOwner does not contain UPDATED_ASSET_OWNER
        defaultMemberAssetsShouldBeFound("assetOwner.doesNotContain=" + UPDATED_ASSET_OWNER);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCompletionYearIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where completionYear equals to DEFAULT_COMPLETION_YEAR
        defaultMemberAssetsShouldBeFound("completionYear.equals=" + DEFAULT_COMPLETION_YEAR);

        // Get all the memberAssetsList where completionYear equals to UPDATED_COMPLETION_YEAR
        defaultMemberAssetsShouldNotBeFound("completionYear.equals=" + UPDATED_COMPLETION_YEAR);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCompletionYearIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where completionYear in DEFAULT_COMPLETION_YEAR or UPDATED_COMPLETION_YEAR
        defaultMemberAssetsShouldBeFound("completionYear.in=" + DEFAULT_COMPLETION_YEAR + "," + UPDATED_COMPLETION_YEAR);

        // Get all the memberAssetsList where completionYear equals to UPDATED_COMPLETION_YEAR
        defaultMemberAssetsShouldNotBeFound("completionYear.in=" + UPDATED_COMPLETION_YEAR);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCompletionYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where completionYear is not null
        defaultMemberAssetsShouldBeFound("completionYear.specified=true");

        // Get all the memberAssetsList where completionYear is null
        defaultMemberAssetsShouldNotBeFound("completionYear.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCompletionYearContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where completionYear contains DEFAULT_COMPLETION_YEAR
        defaultMemberAssetsShouldBeFound("completionYear.contains=" + DEFAULT_COMPLETION_YEAR);

        // Get all the memberAssetsList where completionYear contains UPDATED_COMPLETION_YEAR
        defaultMemberAssetsShouldNotBeFound("completionYear.contains=" + UPDATED_COMPLETION_YEAR);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCompletionYearNotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where completionYear does not contain DEFAULT_COMPLETION_YEAR
        defaultMemberAssetsShouldNotBeFound("completionYear.doesNotContain=" + DEFAULT_COMPLETION_YEAR);

        // Get all the memberAssetsList where completionYear does not contain UPDATED_COMPLETION_YEAR
        defaultMemberAssetsShouldBeFound("completionYear.doesNotContain=" + UPDATED_COMPLETION_YEAR);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByMarketValueIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where marketValue equals to DEFAULT_MARKET_VALUE
        defaultMemberAssetsShouldBeFound("marketValue.equals=" + DEFAULT_MARKET_VALUE);

        // Get all the memberAssetsList where marketValue equals to UPDATED_MARKET_VALUE
        defaultMemberAssetsShouldNotBeFound("marketValue.equals=" + UPDATED_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByMarketValueIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where marketValue in DEFAULT_MARKET_VALUE or UPDATED_MARKET_VALUE
        defaultMemberAssetsShouldBeFound("marketValue.in=" + DEFAULT_MARKET_VALUE + "," + UPDATED_MARKET_VALUE);

        // Get all the memberAssetsList where marketValue equals to UPDATED_MARKET_VALUE
        defaultMemberAssetsShouldNotBeFound("marketValue.in=" + UPDATED_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByMarketValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where marketValue is not null
        defaultMemberAssetsShouldBeFound("marketValue.specified=true");

        // Get all the memberAssetsList where marketValue is null
        defaultMemberAssetsShouldNotBeFound("marketValue.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByMarketValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where marketValue is greater than or equal to DEFAULT_MARKET_VALUE
        defaultMemberAssetsShouldBeFound("marketValue.greaterThanOrEqual=" + DEFAULT_MARKET_VALUE);

        // Get all the memberAssetsList where marketValue is greater than or equal to UPDATED_MARKET_VALUE
        defaultMemberAssetsShouldNotBeFound("marketValue.greaterThanOrEqual=" + UPDATED_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByMarketValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where marketValue is less than or equal to DEFAULT_MARKET_VALUE
        defaultMemberAssetsShouldBeFound("marketValue.lessThanOrEqual=" + DEFAULT_MARKET_VALUE);

        // Get all the memberAssetsList where marketValue is less than or equal to SMALLER_MARKET_VALUE
        defaultMemberAssetsShouldNotBeFound("marketValue.lessThanOrEqual=" + SMALLER_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByMarketValueIsLessThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where marketValue is less than DEFAULT_MARKET_VALUE
        defaultMemberAssetsShouldNotBeFound("marketValue.lessThan=" + DEFAULT_MARKET_VALUE);

        // Get all the memberAssetsList where marketValue is less than UPDATED_MARKET_VALUE
        defaultMemberAssetsShouldBeFound("marketValue.lessThan=" + UPDATED_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByMarketValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where marketValue is greater than DEFAULT_MARKET_VALUE
        defaultMemberAssetsShouldNotBeFound("marketValue.greaterThan=" + DEFAULT_MARKET_VALUE);

        // Get all the memberAssetsList where marketValue is greater than SMALLER_MARKET_VALUE
        defaultMemberAssetsShouldBeFound("marketValue.greaterThan=" + SMALLER_MARKET_VALUE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByIsInsuredIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where isInsured equals to DEFAULT_IS_INSURED
        defaultMemberAssetsShouldBeFound("isInsured.equals=" + DEFAULT_IS_INSURED);

        // Get all the memberAssetsList where isInsured equals to UPDATED_IS_INSURED
        defaultMemberAssetsShouldNotBeFound("isInsured.equals=" + UPDATED_IS_INSURED);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByIsInsuredIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where isInsured in DEFAULT_IS_INSURED or UPDATED_IS_INSURED
        defaultMemberAssetsShouldBeFound("isInsured.in=" + DEFAULT_IS_INSURED + "," + UPDATED_IS_INSURED);

        // Get all the memberAssetsList where isInsured equals to UPDATED_IS_INSURED
        defaultMemberAssetsShouldNotBeFound("isInsured.in=" + UPDATED_IS_INSURED);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByIsInsuredIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where isInsured is not null
        defaultMemberAssetsShouldBeFound("isInsured.specified=true");

        // Get all the memberAssetsList where isInsured is null
        defaultMemberAssetsShouldNotBeFound("isInsured.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByIsUnderUsedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where isUnderUsed equals to DEFAULT_IS_UNDER_USED
        defaultMemberAssetsShouldBeFound("isUnderUsed.equals=" + DEFAULT_IS_UNDER_USED);

        // Get all the memberAssetsList where isUnderUsed equals to UPDATED_IS_UNDER_USED
        defaultMemberAssetsShouldNotBeFound("isUnderUsed.equals=" + UPDATED_IS_UNDER_USED);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByIsUnderUsedIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where isUnderUsed in DEFAULT_IS_UNDER_USED or UPDATED_IS_UNDER_USED
        defaultMemberAssetsShouldBeFound("isUnderUsed.in=" + DEFAULT_IS_UNDER_USED + "," + UPDATED_IS_UNDER_USED);

        // Get all the memberAssetsList where isUnderUsed equals to UPDATED_IS_UNDER_USED
        defaultMemberAssetsShouldNotBeFound("isUnderUsed.in=" + UPDATED_IS_UNDER_USED);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByIsUnderUsedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where isUnderUsed is not null
        defaultMemberAssetsShouldBeFound("isUnderUsed.specified=true");

        // Get all the memberAssetsList where isUnderUsed is null
        defaultMemberAssetsShouldNotBeFound("isUnderUsed.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByIsOwnedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where isOwned equals to DEFAULT_IS_OWNED
        defaultMemberAssetsShouldBeFound("isOwned.equals=" + DEFAULT_IS_OWNED);

        // Get all the memberAssetsList where isOwned equals to UPDATED_IS_OWNED
        defaultMemberAssetsShouldNotBeFound("isOwned.equals=" + UPDATED_IS_OWNED);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByIsOwnedIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where isOwned in DEFAULT_IS_OWNED or UPDATED_IS_OWNED
        defaultMemberAssetsShouldBeFound("isOwned.in=" + DEFAULT_IS_OWNED + "," + UPDATED_IS_OWNED);

        // Get all the memberAssetsList where isOwned equals to UPDATED_IS_OWNED
        defaultMemberAssetsShouldNotBeFound("isOwned.in=" + UPDATED_IS_OWNED);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByIsOwnedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where isOwned is not null
        defaultMemberAssetsShouldBeFound("isOwned.specified=true");

        // Get all the memberAssetsList where isOwned is null
        defaultMemberAssetsShouldNotBeFound("isOwned.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landType equals to DEFAULT_LAND_TYPE
        defaultMemberAssetsShouldBeFound("landType.equals=" + DEFAULT_LAND_TYPE);

        // Get all the memberAssetsList where landType equals to UPDATED_LAND_TYPE
        defaultMemberAssetsShouldNotBeFound("landType.equals=" + UPDATED_LAND_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandTypeIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landType in DEFAULT_LAND_TYPE or UPDATED_LAND_TYPE
        defaultMemberAssetsShouldBeFound("landType.in=" + DEFAULT_LAND_TYPE + "," + UPDATED_LAND_TYPE);

        // Get all the memberAssetsList where landType equals to UPDATED_LAND_TYPE
        defaultMemberAssetsShouldNotBeFound("landType.in=" + UPDATED_LAND_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landType is not null
        defaultMemberAssetsShouldBeFound("landType.specified=true");

        // Get all the memberAssetsList where landType is null
        defaultMemberAssetsShouldNotBeFound("landType.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandTypeContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landType contains DEFAULT_LAND_TYPE
        defaultMemberAssetsShouldBeFound("landType.contains=" + DEFAULT_LAND_TYPE);

        // Get all the memberAssetsList where landType contains UPDATED_LAND_TYPE
        defaultMemberAssetsShouldNotBeFound("landType.contains=" + UPDATED_LAND_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandTypeNotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landType does not contain DEFAULT_LAND_TYPE
        defaultMemberAssetsShouldNotBeFound("landType.doesNotContain=" + DEFAULT_LAND_TYPE);

        // Get all the memberAssetsList where landType does not contain UPDATED_LAND_TYPE
        defaultMemberAssetsShouldBeFound("landType.doesNotContain=" + UPDATED_LAND_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandGatNoIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landGatNo equals to DEFAULT_LAND_GAT_NO
        defaultMemberAssetsShouldBeFound("landGatNo.equals=" + DEFAULT_LAND_GAT_NO);

        // Get all the memberAssetsList where landGatNo equals to UPDATED_LAND_GAT_NO
        defaultMemberAssetsShouldNotBeFound("landGatNo.equals=" + UPDATED_LAND_GAT_NO);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandGatNoIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landGatNo in DEFAULT_LAND_GAT_NO or UPDATED_LAND_GAT_NO
        defaultMemberAssetsShouldBeFound("landGatNo.in=" + DEFAULT_LAND_GAT_NO + "," + UPDATED_LAND_GAT_NO);

        // Get all the memberAssetsList where landGatNo equals to UPDATED_LAND_GAT_NO
        defaultMemberAssetsShouldNotBeFound("landGatNo.in=" + UPDATED_LAND_GAT_NO);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandGatNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landGatNo is not null
        defaultMemberAssetsShouldBeFound("landGatNo.specified=true");

        // Get all the memberAssetsList where landGatNo is null
        defaultMemberAssetsShouldNotBeFound("landGatNo.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandGatNoContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landGatNo contains DEFAULT_LAND_GAT_NO
        defaultMemberAssetsShouldBeFound("landGatNo.contains=" + DEFAULT_LAND_GAT_NO);

        // Get all the memberAssetsList where landGatNo contains UPDATED_LAND_GAT_NO
        defaultMemberAssetsShouldNotBeFound("landGatNo.contains=" + UPDATED_LAND_GAT_NO);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandGatNoNotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landGatNo does not contain DEFAULT_LAND_GAT_NO
        defaultMemberAssetsShouldNotBeFound("landGatNo.doesNotContain=" + DEFAULT_LAND_GAT_NO);

        // Get all the memberAssetsList where landGatNo does not contain UPDATED_LAND_GAT_NO
        defaultMemberAssetsShouldBeFound("landGatNo.doesNotContain=" + UPDATED_LAND_GAT_NO);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandAreaInHectorIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landAreaInHector equals to DEFAULT_LAND_AREA_IN_HECTOR
        defaultMemberAssetsShouldBeFound("landAreaInHector.equals=" + DEFAULT_LAND_AREA_IN_HECTOR);

        // Get all the memberAssetsList where landAreaInHector equals to UPDATED_LAND_AREA_IN_HECTOR
        defaultMemberAssetsShouldNotBeFound("landAreaInHector.equals=" + UPDATED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandAreaInHectorIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landAreaInHector in DEFAULT_LAND_AREA_IN_HECTOR or UPDATED_LAND_AREA_IN_HECTOR
        defaultMemberAssetsShouldBeFound("landAreaInHector.in=" + DEFAULT_LAND_AREA_IN_HECTOR + "," + UPDATED_LAND_AREA_IN_HECTOR);

        // Get all the memberAssetsList where landAreaInHector equals to UPDATED_LAND_AREA_IN_HECTOR
        defaultMemberAssetsShouldNotBeFound("landAreaInHector.in=" + UPDATED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandAreaInHectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landAreaInHector is not null
        defaultMemberAssetsShouldBeFound("landAreaInHector.specified=true");

        // Get all the memberAssetsList where landAreaInHector is null
        defaultMemberAssetsShouldNotBeFound("landAreaInHector.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandAreaInHectorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landAreaInHector is greater than or equal to DEFAULT_LAND_AREA_IN_HECTOR
        defaultMemberAssetsShouldBeFound("landAreaInHector.greaterThanOrEqual=" + DEFAULT_LAND_AREA_IN_HECTOR);

        // Get all the memberAssetsList where landAreaInHector is greater than or equal to UPDATED_LAND_AREA_IN_HECTOR
        defaultMemberAssetsShouldNotBeFound("landAreaInHector.greaterThanOrEqual=" + UPDATED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandAreaInHectorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landAreaInHector is less than or equal to DEFAULT_LAND_AREA_IN_HECTOR
        defaultMemberAssetsShouldBeFound("landAreaInHector.lessThanOrEqual=" + DEFAULT_LAND_AREA_IN_HECTOR);

        // Get all the memberAssetsList where landAreaInHector is less than or equal to SMALLER_LAND_AREA_IN_HECTOR
        defaultMemberAssetsShouldNotBeFound("landAreaInHector.lessThanOrEqual=" + SMALLER_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandAreaInHectorIsLessThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landAreaInHector is less than DEFAULT_LAND_AREA_IN_HECTOR
        defaultMemberAssetsShouldNotBeFound("landAreaInHector.lessThan=" + DEFAULT_LAND_AREA_IN_HECTOR);

        // Get all the memberAssetsList where landAreaInHector is less than UPDATED_LAND_AREA_IN_HECTOR
        defaultMemberAssetsShouldBeFound("landAreaInHector.lessThan=" + UPDATED_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLandAreaInHectorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where landAreaInHector is greater than DEFAULT_LAND_AREA_IN_HECTOR
        defaultMemberAssetsShouldNotBeFound("landAreaInHector.greaterThan=" + DEFAULT_LAND_AREA_IN_HECTOR);

        // Get all the memberAssetsList where landAreaInHector is greater than SMALLER_LAND_AREA_IN_HECTOR
        defaultMemberAssetsShouldBeFound("landAreaInHector.greaterThan=" + SMALLER_LAND_AREA_IN_HECTOR);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByJindagiPatrakNoIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where jindagiPatrakNo equals to DEFAULT_JINDAGI_PATRAK_NO
        defaultMemberAssetsShouldBeFound("jindagiPatrakNo.equals=" + DEFAULT_JINDAGI_PATRAK_NO);

        // Get all the memberAssetsList where jindagiPatrakNo equals to UPDATED_JINDAGI_PATRAK_NO
        defaultMemberAssetsShouldNotBeFound("jindagiPatrakNo.equals=" + UPDATED_JINDAGI_PATRAK_NO);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByJindagiPatrakNoIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where jindagiPatrakNo in DEFAULT_JINDAGI_PATRAK_NO or UPDATED_JINDAGI_PATRAK_NO
        defaultMemberAssetsShouldBeFound("jindagiPatrakNo.in=" + DEFAULT_JINDAGI_PATRAK_NO + "," + UPDATED_JINDAGI_PATRAK_NO);

        // Get all the memberAssetsList where jindagiPatrakNo equals to UPDATED_JINDAGI_PATRAK_NO
        defaultMemberAssetsShouldNotBeFound("jindagiPatrakNo.in=" + UPDATED_JINDAGI_PATRAK_NO);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByJindagiPatrakNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where jindagiPatrakNo is not null
        defaultMemberAssetsShouldBeFound("jindagiPatrakNo.specified=true");

        // Get all the memberAssetsList where jindagiPatrakNo is null
        defaultMemberAssetsShouldNotBeFound("jindagiPatrakNo.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByJindagiPatrakNoContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where jindagiPatrakNo contains DEFAULT_JINDAGI_PATRAK_NO
        defaultMemberAssetsShouldBeFound("jindagiPatrakNo.contains=" + DEFAULT_JINDAGI_PATRAK_NO);

        // Get all the memberAssetsList where jindagiPatrakNo contains UPDATED_JINDAGI_PATRAK_NO
        defaultMemberAssetsShouldNotBeFound("jindagiPatrakNo.contains=" + UPDATED_JINDAGI_PATRAK_NO);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByJindagiPatrakNoNotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where jindagiPatrakNo does not contain DEFAULT_JINDAGI_PATRAK_NO
        defaultMemberAssetsShouldNotBeFound("jindagiPatrakNo.doesNotContain=" + DEFAULT_JINDAGI_PATRAK_NO);

        // Get all the memberAssetsList where jindagiPatrakNo does not contain UPDATED_JINDAGI_PATRAK_NO
        defaultMemberAssetsShouldBeFound("jindagiPatrakNo.doesNotContain=" + UPDATED_JINDAGI_PATRAK_NO);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByJindagiAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where jindagiAmount equals to DEFAULT_JINDAGI_AMOUNT
        defaultMemberAssetsShouldBeFound("jindagiAmount.equals=" + DEFAULT_JINDAGI_AMOUNT);

        // Get all the memberAssetsList where jindagiAmount equals to UPDATED_JINDAGI_AMOUNT
        defaultMemberAssetsShouldNotBeFound("jindagiAmount.equals=" + UPDATED_JINDAGI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByJindagiAmountIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where jindagiAmount in DEFAULT_JINDAGI_AMOUNT or UPDATED_JINDAGI_AMOUNT
        defaultMemberAssetsShouldBeFound("jindagiAmount.in=" + DEFAULT_JINDAGI_AMOUNT + "," + UPDATED_JINDAGI_AMOUNT);

        // Get all the memberAssetsList where jindagiAmount equals to UPDATED_JINDAGI_AMOUNT
        defaultMemberAssetsShouldNotBeFound("jindagiAmount.in=" + UPDATED_JINDAGI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByJindagiAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where jindagiAmount is not null
        defaultMemberAssetsShouldBeFound("jindagiAmount.specified=true");

        // Get all the memberAssetsList where jindagiAmount is null
        defaultMemberAssetsShouldNotBeFound("jindagiAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByJindagiAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where jindagiAmount is greater than or equal to DEFAULT_JINDAGI_AMOUNT
        defaultMemberAssetsShouldBeFound("jindagiAmount.greaterThanOrEqual=" + DEFAULT_JINDAGI_AMOUNT);

        // Get all the memberAssetsList where jindagiAmount is greater than or equal to UPDATED_JINDAGI_AMOUNT
        defaultMemberAssetsShouldNotBeFound("jindagiAmount.greaterThanOrEqual=" + UPDATED_JINDAGI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByJindagiAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where jindagiAmount is less than or equal to DEFAULT_JINDAGI_AMOUNT
        defaultMemberAssetsShouldBeFound("jindagiAmount.lessThanOrEqual=" + DEFAULT_JINDAGI_AMOUNT);

        // Get all the memberAssetsList where jindagiAmount is less than or equal to SMALLER_JINDAGI_AMOUNT
        defaultMemberAssetsShouldNotBeFound("jindagiAmount.lessThanOrEqual=" + SMALLER_JINDAGI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByJindagiAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where jindagiAmount is less than DEFAULT_JINDAGI_AMOUNT
        defaultMemberAssetsShouldNotBeFound("jindagiAmount.lessThan=" + DEFAULT_JINDAGI_AMOUNT);

        // Get all the memberAssetsList where jindagiAmount is less than UPDATED_JINDAGI_AMOUNT
        defaultMemberAssetsShouldBeFound("jindagiAmount.lessThan=" + UPDATED_JINDAGI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByJindagiAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where jindagiAmount is greater than DEFAULT_JINDAGI_AMOUNT
        defaultMemberAssetsShouldNotBeFound("jindagiAmount.greaterThan=" + DEFAULT_JINDAGI_AMOUNT);

        // Get all the memberAssetsList where jindagiAmount is greater than SMALLER_JINDAGI_AMOUNT
        defaultMemberAssetsShouldBeFound("jindagiAmount.greaterThan=" + SMALLER_JINDAGI_AMOUNT);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultMemberAssetsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the memberAssetsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberAssetsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultMemberAssetsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the memberAssetsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultMemberAssetsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModified is not null
        defaultMemberAssetsShouldBeFound("lastModified.specified=true");

        // Get all the memberAssetsList where lastModified is null
        defaultMemberAssetsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultMemberAssetsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberAssetsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberAssetsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultMemberAssetsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the memberAssetsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultMemberAssetsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModifiedBy is not null
        defaultMemberAssetsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the memberAssetsList where lastModifiedBy is null
        defaultMemberAssetsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultMemberAssetsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberAssetsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultMemberAssetsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultMemberAssetsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the memberAssetsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultMemberAssetsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdBy equals to DEFAULT_CREATED_BY
        defaultMemberAssetsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the memberAssetsList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberAssetsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultMemberAssetsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the memberAssetsList where createdBy equals to UPDATED_CREATED_BY
        defaultMemberAssetsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdBy is not null
        defaultMemberAssetsShouldBeFound("createdBy.specified=true");

        // Get all the memberAssetsList where createdBy is null
        defaultMemberAssetsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdBy contains DEFAULT_CREATED_BY
        defaultMemberAssetsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the memberAssetsList where createdBy contains UPDATED_CREATED_BY
        defaultMemberAssetsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultMemberAssetsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the memberAssetsList where createdBy does not contain UPDATED_CREATED_BY
        defaultMemberAssetsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdOn equals to DEFAULT_CREATED_ON
        defaultMemberAssetsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the memberAssetsList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberAssetsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultMemberAssetsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the memberAssetsList where createdOn equals to UPDATED_CREATED_ON
        defaultMemberAssetsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where createdOn is not null
        defaultMemberAssetsShouldBeFound("createdOn.specified=true");

        // Get all the memberAssetsList where createdOn is null
        defaultMemberAssetsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultMemberAssetsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the memberAssetsList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberAssetsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultMemberAssetsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the memberAssetsList where isDeleted equals to UPDATED_IS_DELETED
        defaultMemberAssetsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where isDeleted is not null
        defaultMemberAssetsShouldBeFound("isDeleted.specified=true");

        // Get all the memberAssetsList where isDeleted is null
        defaultMemberAssetsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultMemberAssetsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberAssetsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultMemberAssetsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultMemberAssetsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the memberAssetsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultMemberAssetsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField1 is not null
        defaultMemberAssetsShouldBeFound("freeField1.specified=true");

        // Get all the memberAssetsList where freeField1 is null
        defaultMemberAssetsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultMemberAssetsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberAssetsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultMemberAssetsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultMemberAssetsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the memberAssetsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultMemberAssetsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultMemberAssetsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberAssetsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultMemberAssetsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultMemberAssetsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the memberAssetsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultMemberAssetsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField2 is not null
        defaultMemberAssetsShouldBeFound("freeField2.specified=true");

        // Get all the memberAssetsList where freeField2 is null
        defaultMemberAssetsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultMemberAssetsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberAssetsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultMemberAssetsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultMemberAssetsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the memberAssetsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultMemberAssetsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultMemberAssetsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberAssetsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultMemberAssetsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultMemberAssetsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the memberAssetsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultMemberAssetsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField3 is not null
        defaultMemberAssetsShouldBeFound("freeField3.specified=true");

        // Get all the memberAssetsList where freeField3 is null
        defaultMemberAssetsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultMemberAssetsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberAssetsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultMemberAssetsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultMemberAssetsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the memberAssetsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultMemberAssetsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultMemberAssetsShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the memberAssetsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultMemberAssetsShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultMemberAssetsShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the memberAssetsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultMemberAssetsShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField4 is not null
        defaultMemberAssetsShouldBeFound("freeField4.specified=true");

        // Get all the memberAssetsList where freeField4 is null
        defaultMemberAssetsShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultMemberAssetsShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the memberAssetsList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultMemberAssetsShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultMemberAssetsShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the memberAssetsList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultMemberAssetsShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultMemberAssetsShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the memberAssetsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultMemberAssetsShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultMemberAssetsShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the memberAssetsList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultMemberAssetsShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField5 is not null
        defaultMemberAssetsShouldBeFound("freeField5.specified=true");

        // Get all the memberAssetsList where freeField5 is null
        defaultMemberAssetsShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultMemberAssetsShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the memberAssetsList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultMemberAssetsShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultMemberAssetsShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the memberAssetsList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultMemberAssetsShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField6IsEqualToSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField6 equals to DEFAULT_FREE_FIELD_6
        defaultMemberAssetsShouldBeFound("freeField6.equals=" + DEFAULT_FREE_FIELD_6);

        // Get all the memberAssetsList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultMemberAssetsShouldNotBeFound("freeField6.equals=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField6IsInShouldWork() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField6 in DEFAULT_FREE_FIELD_6 or UPDATED_FREE_FIELD_6
        defaultMemberAssetsShouldBeFound("freeField6.in=" + DEFAULT_FREE_FIELD_6 + "," + UPDATED_FREE_FIELD_6);

        // Get all the memberAssetsList where freeField6 equals to UPDATED_FREE_FIELD_6
        defaultMemberAssetsShouldNotBeFound("freeField6.in=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField6IsNullOrNotNull() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField6 is not null
        defaultMemberAssetsShouldBeFound("freeField6.specified=true");

        // Get all the memberAssetsList where freeField6 is null
        defaultMemberAssetsShouldNotBeFound("freeField6.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField6ContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField6 contains DEFAULT_FREE_FIELD_6
        defaultMemberAssetsShouldBeFound("freeField6.contains=" + DEFAULT_FREE_FIELD_6);

        // Get all the memberAssetsList where freeField6 contains UPDATED_FREE_FIELD_6
        defaultMemberAssetsShouldNotBeFound("freeField6.contains=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByFreeField6NotContainsSomething() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        // Get all the memberAssetsList where freeField6 does not contain DEFAULT_FREE_FIELD_6
        defaultMemberAssetsShouldNotBeFound("freeField6.doesNotContain=" + DEFAULT_FREE_FIELD_6);

        // Get all the memberAssetsList where freeField6 does not contain UPDATED_FREE_FIELD_6
        defaultMemberAssetsShouldBeFound("freeField6.doesNotContain=" + UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void getAllMemberAssetsByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            memberAssetsRepository.saveAndFlush(memberAssets);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        memberAssets.setMember(member);
        memberAssetsRepository.saveAndFlush(memberAssets);
        Long memberId = member.getId();

        // Get all the memberAssetsList where member equals to memberId
        defaultMemberAssetsShouldBeFound("memberId.equals=" + memberId);

        // Get all the memberAssetsList where member equals to (memberId + 1)
        defaultMemberAssetsShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    @Test
    @Transactional
    void getAllMemberAssetsByLoanApplicationsIsEqualToSomething() throws Exception {
        LoanApplications loanApplications;
        if (TestUtil.findAll(em, LoanApplications.class).isEmpty()) {
            memberAssetsRepository.saveAndFlush(memberAssets);
            loanApplications = LoanApplicationsResourceIT.createEntity(em);
        } else {
            loanApplications = TestUtil.findAll(em, LoanApplications.class).get(0);
        }
        em.persist(loanApplications);
        em.flush();
        memberAssets.setLoanApplications(loanApplications);
        memberAssetsRepository.saveAndFlush(memberAssets);
        Long loanApplicationsId = loanApplications.getId();

        // Get all the memberAssetsList where loanApplications equals to loanApplicationsId
        defaultMemberAssetsShouldBeFound("loanApplicationsId.equals=" + loanApplicationsId);

        // Get all the memberAssetsList where loanApplications equals to (loanApplicationsId + 1)
        defaultMemberAssetsShouldNotBeFound("loanApplicationsId.equals=" + (loanApplicationsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMemberAssetsShouldBeFound(String filter) throws Exception {
        restMemberAssetsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberAssets.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetCost").value(hasItem(DEFAULT_ASSET_COST.doubleValue())))
            //            .andExpect(jsonPath("$.[*].assetType").value(hasItem(DEFAULT_ASSET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].areaInSqFt").value(hasItem(DEFAULT_AREA_IN_SQ_FT.doubleValue())))
            .andExpect(jsonPath("$.[*].assetNature").value(hasItem(DEFAULT_ASSET_NATURE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].landMark").value(hasItem(DEFAULT_LAND_MARK)))
            .andExpect(jsonPath("$.[*].assetOwner").value(hasItem(DEFAULT_ASSET_OWNER)))
            .andExpect(jsonPath("$.[*].completionYear").value(hasItem(DEFAULT_COMPLETION_YEAR)))
            .andExpect(jsonPath("$.[*].marketValue").value(hasItem(DEFAULT_MARKET_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].isInsured").value(hasItem(DEFAULT_IS_INSURED.booleanValue())))
            .andExpect(jsonPath("$.[*].isUnderUsed").value(hasItem(DEFAULT_IS_UNDER_USED.booleanValue())))
            .andExpect(jsonPath("$.[*].isOwned").value(hasItem(DEFAULT_IS_OWNED.booleanValue())))
            .andExpect(jsonPath("$.[*].landType").value(hasItem(DEFAULT_LAND_TYPE)))
            .andExpect(jsonPath("$.[*].landGatNo").value(hasItem(DEFAULT_LAND_GAT_NO)))
            .andExpect(jsonPath("$.[*].landAreaInHector").value(hasItem(DEFAULT_LAND_AREA_IN_HECTOR.doubleValue())))
            .andExpect(jsonPath("$.[*].jindagiPatrakNo").value(hasItem(DEFAULT_JINDAGI_PATRAK_NO)))
            .andExpect(jsonPath("$.[*].jindagiAmount").value(hasItem(DEFAULT_JINDAGI_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)))
            .andExpect(jsonPath("$.[*].freeField6").value(hasItem(DEFAULT_FREE_FIELD_6)));

        // Check, that the count call also returns 1
        restMemberAssetsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMemberAssetsShouldNotBeFound(String filter) throws Exception {
        restMemberAssetsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMemberAssetsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMemberAssets() throws Exception {
        // Get the memberAssets
        restMemberAssetsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMemberAssets() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();

        // Update the memberAssets
        MemberAssets updatedMemberAssets = memberAssetsRepository.findById(memberAssets.getId()).get();
        // Disconnect from session so that the updates on updatedMemberAssets are not directly saved in db
        em.detach(updatedMemberAssets);
        updatedMemberAssets
            .assetCost(UPDATED_ASSET_COST)
            //            .assetType(UPDATED_ASSET_TYPE)
            .areaInSqFt(UPDATED_AREA_IN_SQ_FT)
            .assetNature(UPDATED_ASSET_NATURE)
            .address(UPDATED_ADDRESS)
            .landMark(UPDATED_LAND_MARK)
            .assetOwner(UPDATED_ASSET_OWNER)
            .completionYear(UPDATED_COMPLETION_YEAR)
            .marketValue(UPDATED_MARKET_VALUE)
            .isInsured(UPDATED_IS_INSURED)
            .isUnderUsed(UPDATED_IS_UNDER_USED)
            .isOwned(UPDATED_IS_OWNED)
            .landType(UPDATED_LAND_TYPE)
            .landGatNo(UPDATED_LAND_GAT_NO)
            .landAreaInHector(UPDATED_LAND_AREA_IN_HECTOR)
            .jindagiPatrakNo(UPDATED_JINDAGI_PATRAK_NO)
            .jindagiAmount(UPDATED_JINDAGI_AMOUNT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(updatedMemberAssets);

        restMemberAssetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberAssetsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isOk());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
        MemberAssets testMemberAssets = memberAssetsList.get(memberAssetsList.size() - 1);
        assertThat(testMemberAssets.getAssetCost()).isEqualTo(UPDATED_ASSET_COST);
        //        assertThat(testMemberAssets.getAssetType()).isEqualTo(UPDATED_ASSET_TYPE);
        assertThat(testMemberAssets.getAreaInSqFt()).isEqualTo(UPDATED_AREA_IN_SQ_FT);
        assertThat(testMemberAssets.getAssetNature()).isEqualTo(UPDATED_ASSET_NATURE);
        assertThat(testMemberAssets.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMemberAssets.getLandMark()).isEqualTo(UPDATED_LAND_MARK);
        assertThat(testMemberAssets.getAssetOwner()).isEqualTo(UPDATED_ASSET_OWNER);
        assertThat(testMemberAssets.getCompletionYear()).isEqualTo(UPDATED_COMPLETION_YEAR);
        assertThat(testMemberAssets.getMarketValue()).isEqualTo(UPDATED_MARKET_VALUE);
        assertThat(testMemberAssets.getIsInsured()).isEqualTo(UPDATED_IS_INSURED);
        assertThat(testMemberAssets.getIsUnderUsed()).isEqualTo(UPDATED_IS_UNDER_USED);
        assertThat(testMemberAssets.getIsOwned()).isEqualTo(UPDATED_IS_OWNED);
        assertThat(testMemberAssets.getLandType()).isEqualTo(UPDATED_LAND_TYPE);
        assertThat(testMemberAssets.getLandGatNo()).isEqualTo(UPDATED_LAND_GAT_NO);
        assertThat(testMemberAssets.getLandAreaInHector()).isEqualTo(UPDATED_LAND_AREA_IN_HECTOR);
        assertThat(testMemberAssets.getJindagiPatrakNo()).isEqualTo(UPDATED_JINDAGI_PATRAK_NO);
        assertThat(testMemberAssets.getJindagiAmount()).isEqualTo(UPDATED_JINDAGI_AMOUNT);
        assertThat(testMemberAssets.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberAssets.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberAssets.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMemberAssets.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMemberAssets.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMemberAssets.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMemberAssets.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMemberAssets.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMemberAssets.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testMemberAssets.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testMemberAssets.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void putNonExistingMemberAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();
        memberAssets.setId(count.incrementAndGet());

        // Create the MemberAssets
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberAssetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberAssetsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMemberAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();
        memberAssets.setId(count.incrementAndGet());

        // Create the MemberAssets
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberAssetsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMemberAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();
        memberAssets.setId(count.incrementAndGet());

        // Create the MemberAssets
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberAssetsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberAssetsWithPatch() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();

        // Update the memberAssets using partial update
        MemberAssets partialUpdatedMemberAssets = new MemberAssets();
        partialUpdatedMemberAssets.setId(memberAssets.getId());

        partialUpdatedMemberAssets
            .assetCost(UPDATED_ASSET_COST)
            //            .assetType(UPDATED_ASSET_TYPE)
            .assetNature(UPDATED_ASSET_NATURE)
            .landMark(UPDATED_LAND_MARK)
            .assetOwner(UPDATED_ASSET_OWNER)
            .completionYear(UPDATED_COMPLETION_YEAR)
            .landGatNo(UPDATED_LAND_GAT_NO)
            .landAreaInHector(UPDATED_LAND_AREA_IN_HECTOR)
            .jindagiPatrakNo(UPDATED_JINDAGI_PATRAK_NO)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);

        restMemberAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberAssets.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberAssets))
            )
            .andExpect(status().isOk());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
        MemberAssets testMemberAssets = memberAssetsList.get(memberAssetsList.size() - 1);
        assertThat(testMemberAssets.getAssetCost()).isEqualTo(UPDATED_ASSET_COST);
        //        assertThat(testMemberAssets.getAssetType()).isEqualTo(UPDATED_ASSET_TYPE);
        assertThat(testMemberAssets.getAreaInSqFt()).isEqualTo(DEFAULT_AREA_IN_SQ_FT);
        assertThat(testMemberAssets.getAssetNature()).isEqualTo(UPDATED_ASSET_NATURE);
        assertThat(testMemberAssets.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testMemberAssets.getLandMark()).isEqualTo(UPDATED_LAND_MARK);
        assertThat(testMemberAssets.getAssetOwner()).isEqualTo(UPDATED_ASSET_OWNER);
        assertThat(testMemberAssets.getCompletionYear()).isEqualTo(UPDATED_COMPLETION_YEAR);
        assertThat(testMemberAssets.getMarketValue()).isEqualTo(DEFAULT_MARKET_VALUE);
        assertThat(testMemberAssets.getIsInsured()).isEqualTo(DEFAULT_IS_INSURED);
        assertThat(testMemberAssets.getIsUnderUsed()).isEqualTo(DEFAULT_IS_UNDER_USED);
        assertThat(testMemberAssets.getIsOwned()).isEqualTo(DEFAULT_IS_OWNED);
        assertThat(testMemberAssets.getLandType()).isEqualTo(DEFAULT_LAND_TYPE);
        assertThat(testMemberAssets.getLandGatNo()).isEqualTo(UPDATED_LAND_GAT_NO);
        assertThat(testMemberAssets.getLandAreaInHector()).isEqualTo(UPDATED_LAND_AREA_IN_HECTOR);
        assertThat(testMemberAssets.getJindagiPatrakNo()).isEqualTo(UPDATED_JINDAGI_PATRAK_NO);
        assertThat(testMemberAssets.getJindagiAmount()).isEqualTo(DEFAULT_JINDAGI_AMOUNT);
        assertThat(testMemberAssets.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testMemberAssets.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testMemberAssets.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMemberAssets.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMemberAssets.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMemberAssets.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testMemberAssets.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMemberAssets.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMemberAssets.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testMemberAssets.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testMemberAssets.getFreeField6()).isEqualTo(DEFAULT_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void fullUpdateMemberAssetsWithPatch() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();

        // Update the memberAssets using partial update
        MemberAssets partialUpdatedMemberAssets = new MemberAssets();
        partialUpdatedMemberAssets.setId(memberAssets.getId());

        partialUpdatedMemberAssets
            .assetCost(UPDATED_ASSET_COST)
            //            .assetType(UPDATED_ASSET_TYPE)
            .areaInSqFt(UPDATED_AREA_IN_SQ_FT)
            .assetNature(UPDATED_ASSET_NATURE)
            .address(UPDATED_ADDRESS)
            .landMark(UPDATED_LAND_MARK)
            .assetOwner(UPDATED_ASSET_OWNER)
            .completionYear(UPDATED_COMPLETION_YEAR)
            .marketValue(UPDATED_MARKET_VALUE)
            .isInsured(UPDATED_IS_INSURED)
            .isUnderUsed(UPDATED_IS_UNDER_USED)
            .isOwned(UPDATED_IS_OWNED)
            .landType(UPDATED_LAND_TYPE)
            .landGatNo(UPDATED_LAND_GAT_NO)
            .landAreaInHector(UPDATED_LAND_AREA_IN_HECTOR)
            .jindagiPatrakNo(UPDATED_JINDAGI_PATRAK_NO)
            .jindagiAmount(UPDATED_JINDAGI_AMOUNT)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5)
            .freeField6(UPDATED_FREE_FIELD_6);

        restMemberAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberAssets.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberAssets))
            )
            .andExpect(status().isOk());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
        MemberAssets testMemberAssets = memberAssetsList.get(memberAssetsList.size() - 1);
        assertThat(testMemberAssets.getAssetCost()).isEqualTo(UPDATED_ASSET_COST);
        //        assertThat(testMemberAssets.getAssetType()).isEqualTo(UPDATED_ASSET_TYPE);
        assertThat(testMemberAssets.getAreaInSqFt()).isEqualTo(UPDATED_AREA_IN_SQ_FT);
        assertThat(testMemberAssets.getAssetNature()).isEqualTo(UPDATED_ASSET_NATURE);
        assertThat(testMemberAssets.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMemberAssets.getLandMark()).isEqualTo(UPDATED_LAND_MARK);
        assertThat(testMemberAssets.getAssetOwner()).isEqualTo(UPDATED_ASSET_OWNER);
        assertThat(testMemberAssets.getCompletionYear()).isEqualTo(UPDATED_COMPLETION_YEAR);
        assertThat(testMemberAssets.getMarketValue()).isEqualTo(UPDATED_MARKET_VALUE);
        assertThat(testMemberAssets.getIsInsured()).isEqualTo(UPDATED_IS_INSURED);
        assertThat(testMemberAssets.getIsUnderUsed()).isEqualTo(UPDATED_IS_UNDER_USED);
        assertThat(testMemberAssets.getIsOwned()).isEqualTo(UPDATED_IS_OWNED);
        assertThat(testMemberAssets.getLandType()).isEqualTo(UPDATED_LAND_TYPE);
        assertThat(testMemberAssets.getLandGatNo()).isEqualTo(UPDATED_LAND_GAT_NO);
        assertThat(testMemberAssets.getLandAreaInHector()).isEqualTo(UPDATED_LAND_AREA_IN_HECTOR);
        assertThat(testMemberAssets.getJindagiPatrakNo()).isEqualTo(UPDATED_JINDAGI_PATRAK_NO);
        assertThat(testMemberAssets.getJindagiAmount()).isEqualTo(UPDATED_JINDAGI_AMOUNT);
        assertThat(testMemberAssets.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testMemberAssets.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testMemberAssets.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMemberAssets.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testMemberAssets.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMemberAssets.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testMemberAssets.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testMemberAssets.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testMemberAssets.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testMemberAssets.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
        assertThat(testMemberAssets.getFreeField6()).isEqualTo(UPDATED_FREE_FIELD_6);
    }

    @Test
    @Transactional
    void patchNonExistingMemberAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();
        memberAssets.setId(count.incrementAndGet());

        // Create the MemberAssets
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, memberAssetsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMemberAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();
        memberAssets.setId(count.incrementAndGet());

        // Create the MemberAssets
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMemberAssets() throws Exception {
        int databaseSizeBeforeUpdate = memberAssetsRepository.findAll().size();
        memberAssets.setId(count.incrementAndGet());

        // Create the MemberAssets
        MemberAssetsDTO memberAssetsDTO = memberAssetsMapper.toDto(memberAssets);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberAssetsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberAssetsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberAssets in the database
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMemberAssets() throws Exception {
        // Initialize the database
        memberAssetsRepository.saveAndFlush(memberAssets);

        int databaseSizeBeforeDelete = memberAssetsRepository.findAll().size();

        // Delete the memberAssets
        restMemberAssetsMockMvc
            .perform(delete(ENTITY_API_URL_ID, memberAssets.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MemberAssets> memberAssetsList = memberAssetsRepository.findAll();
        assertThat(memberAssetsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
