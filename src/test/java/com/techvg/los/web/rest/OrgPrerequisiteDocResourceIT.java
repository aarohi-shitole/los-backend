package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.OrgPrerequisiteDoc;
import com.techvg.los.domain.Organisation;
import com.techvg.los.domain.Product;
import com.techvg.los.repository.OrgPrerequisiteDocRepository;
import com.techvg.los.service.criteria.OrgPrerequisiteDocCriteria;
import com.techvg.los.service.dto.OrgPrerequisiteDocDTO;
import com.techvg.los.service.mapper.OrgPrerequisiteDocMapper;
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
 * Integration tests for the {@link OrgPrerequisiteDocResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrgPrerequisiteDocResourceIT {

    private static final String DEFAULT_DOC_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DOC_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_DOC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOC_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DOC_CATAGORY = "AAAAAAAAAA";
    private static final String UPDATED_DOC_CATAGORY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ISMANDATORY = false;
    private static final Boolean UPDATED_ISMANDATORY = true;

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

    private static final String ENTITY_API_URL = "/api/org-prerequisite-docs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrgPrerequisiteDocRepository orgPrerequisiteDocRepository;

    @Autowired
    private OrgPrerequisiteDocMapper orgPrerequisiteDocMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrgPrerequisiteDocMockMvc;

    private OrgPrerequisiteDoc orgPrerequisiteDoc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrgPrerequisiteDoc createEntity(EntityManager em) {
        OrgPrerequisiteDoc orgPrerequisiteDoc = new OrgPrerequisiteDoc()
            .docDesc(DEFAULT_DOC_DESC)
            .docName(DEFAULT_DOC_NAME)
            .docCatagory(DEFAULT_DOC_CATAGORY)
            .ismandatory(DEFAULT_ISMANDATORY)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .freeField5(DEFAULT_FREE_FIELD_5);
        return orgPrerequisiteDoc;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrgPrerequisiteDoc createUpdatedEntity(EntityManager em) {
        OrgPrerequisiteDoc orgPrerequisiteDoc = new OrgPrerequisiteDoc()
            .docDesc(UPDATED_DOC_DESC)
            .docName(UPDATED_DOC_NAME)
            .docCatagory(UPDATED_DOC_CATAGORY)
            .ismandatory(UPDATED_ISMANDATORY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);
        return orgPrerequisiteDoc;
    }

    @BeforeEach
    public void initTest() {
        orgPrerequisiteDoc = createEntity(em);
    }

    @Test
    @Transactional
    void createOrgPrerequisiteDoc() throws Exception {
        int databaseSizeBeforeCreate = orgPrerequisiteDocRepository.findAll().size();
        // Create the OrgPrerequisiteDoc
        OrgPrerequisiteDocDTO orgPrerequisiteDocDTO = orgPrerequisiteDocMapper.toDto(orgPrerequisiteDoc);
        restOrgPrerequisiteDocMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgPrerequisiteDocDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrgPrerequisiteDoc in the database
        List<OrgPrerequisiteDoc> orgPrerequisiteDocList = orgPrerequisiteDocRepository.findAll();
        assertThat(orgPrerequisiteDocList).hasSize(databaseSizeBeforeCreate + 1);
        OrgPrerequisiteDoc testOrgPrerequisiteDoc = orgPrerequisiteDocList.get(orgPrerequisiteDocList.size() - 1);
        assertThat(testOrgPrerequisiteDoc.getDocDesc()).isEqualTo(DEFAULT_DOC_DESC);
        assertThat(testOrgPrerequisiteDoc.getDocName()).isEqualTo(DEFAULT_DOC_NAME);
        assertThat(testOrgPrerequisiteDoc.getDocCatagory()).isEqualTo(DEFAULT_DOC_CATAGORY);
        assertThat(testOrgPrerequisiteDoc.getIsmandatory()).isEqualTo(DEFAULT_ISMANDATORY);
        assertThat(testOrgPrerequisiteDoc.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testOrgPrerequisiteDoc.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testOrgPrerequisiteDoc.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrgPrerequisiteDoc.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testOrgPrerequisiteDoc.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testOrgPrerequisiteDoc.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testOrgPrerequisiteDoc.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testOrgPrerequisiteDoc.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testOrgPrerequisiteDoc.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testOrgPrerequisiteDoc.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void createOrgPrerequisiteDocWithExistingId() throws Exception {
        // Create the OrgPrerequisiteDoc with an existing ID
        orgPrerequisiteDoc.setId(1L);
        OrgPrerequisiteDocDTO orgPrerequisiteDocDTO = orgPrerequisiteDocMapper.toDto(orgPrerequisiteDoc);

        int databaseSizeBeforeCreate = orgPrerequisiteDocRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrgPrerequisiteDocMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgPrerequisiteDocDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgPrerequisiteDoc in the database
        List<OrgPrerequisiteDoc> orgPrerequisiteDocList = orgPrerequisiteDocRepository.findAll();
        assertThat(orgPrerequisiteDocList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocs() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList
        restOrgPrerequisiteDocMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orgPrerequisiteDoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].docDesc").value(hasItem(DEFAULT_DOC_DESC)))
            .andExpect(jsonPath("$.[*].docName").value(hasItem(DEFAULT_DOC_NAME)))
            .andExpect(jsonPath("$.[*].docCatagory").value(hasItem(DEFAULT_DOC_CATAGORY)))
            .andExpect(jsonPath("$.[*].ismandatory").value(hasItem(DEFAULT_ISMANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)));
    }

    @Test
    @Transactional
    void getOrgPrerequisiteDoc() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get the orgPrerequisiteDoc
        restOrgPrerequisiteDocMockMvc
            .perform(get(ENTITY_API_URL_ID, orgPrerequisiteDoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orgPrerequisiteDoc.getId().intValue()))
            .andExpect(jsonPath("$.docDesc").value(DEFAULT_DOC_DESC))
            .andExpect(jsonPath("$.docName").value(DEFAULT_DOC_NAME))
            .andExpect(jsonPath("$.docCatagory").value(DEFAULT_DOC_CATAGORY))
            .andExpect(jsonPath("$.ismandatory").value(DEFAULT_ISMANDATORY.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.freeField5").value(DEFAULT_FREE_FIELD_5));
    }

    @Test
    @Transactional
    void getOrgPrerequisiteDocsByIdFiltering() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        Long id = orgPrerequisiteDoc.getId();

        defaultOrgPrerequisiteDocShouldBeFound("id.equals=" + id);
        defaultOrgPrerequisiteDocShouldNotBeFound("id.notEquals=" + id);

        defaultOrgPrerequisiteDocShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrgPrerequisiteDocShouldNotBeFound("id.greaterThan=" + id);

        defaultOrgPrerequisiteDocShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrgPrerequisiteDocShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByDocDescIsEqualToSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where docDesc equals to DEFAULT_DOC_DESC
        defaultOrgPrerequisiteDocShouldBeFound("docDesc.equals=" + DEFAULT_DOC_DESC);

        // Get all the orgPrerequisiteDocList where docDesc equals to UPDATED_DOC_DESC
        defaultOrgPrerequisiteDocShouldNotBeFound("docDesc.equals=" + UPDATED_DOC_DESC);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByDocDescIsInShouldWork() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where docDesc in DEFAULT_DOC_DESC or UPDATED_DOC_DESC
        defaultOrgPrerequisiteDocShouldBeFound("docDesc.in=" + DEFAULT_DOC_DESC + "," + UPDATED_DOC_DESC);

        // Get all the orgPrerequisiteDocList where docDesc equals to UPDATED_DOC_DESC
        defaultOrgPrerequisiteDocShouldNotBeFound("docDesc.in=" + UPDATED_DOC_DESC);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByDocDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where docDesc is not null
        defaultOrgPrerequisiteDocShouldBeFound("docDesc.specified=true");

        // Get all the orgPrerequisiteDocList where docDesc is null
        defaultOrgPrerequisiteDocShouldNotBeFound("docDesc.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByDocDescContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where docDesc contains DEFAULT_DOC_DESC
        defaultOrgPrerequisiteDocShouldBeFound("docDesc.contains=" + DEFAULT_DOC_DESC);

        // Get all the orgPrerequisiteDocList where docDesc contains UPDATED_DOC_DESC
        defaultOrgPrerequisiteDocShouldNotBeFound("docDesc.contains=" + UPDATED_DOC_DESC);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByDocDescNotContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where docDesc does not contain DEFAULT_DOC_DESC
        defaultOrgPrerequisiteDocShouldNotBeFound("docDesc.doesNotContain=" + DEFAULT_DOC_DESC);

        // Get all the orgPrerequisiteDocList where docDesc does not contain UPDATED_DOC_DESC
        defaultOrgPrerequisiteDocShouldBeFound("docDesc.doesNotContain=" + UPDATED_DOC_DESC);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByDocNameIsEqualToSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where docName equals to DEFAULT_DOC_NAME
        defaultOrgPrerequisiteDocShouldBeFound("docName.equals=" + DEFAULT_DOC_NAME);

        // Get all the orgPrerequisiteDocList where docName equals to UPDATED_DOC_NAME
        defaultOrgPrerequisiteDocShouldNotBeFound("docName.equals=" + UPDATED_DOC_NAME);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByDocNameIsInShouldWork() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where docName in DEFAULT_DOC_NAME or UPDATED_DOC_NAME
        defaultOrgPrerequisiteDocShouldBeFound("docName.in=" + DEFAULT_DOC_NAME + "," + UPDATED_DOC_NAME);

        // Get all the orgPrerequisiteDocList where docName equals to UPDATED_DOC_NAME
        defaultOrgPrerequisiteDocShouldNotBeFound("docName.in=" + UPDATED_DOC_NAME);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByDocNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where docName is not null
        defaultOrgPrerequisiteDocShouldBeFound("docName.specified=true");

        // Get all the orgPrerequisiteDocList where docName is null
        defaultOrgPrerequisiteDocShouldNotBeFound("docName.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByDocNameContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where docName contains DEFAULT_DOC_NAME
        defaultOrgPrerequisiteDocShouldBeFound("docName.contains=" + DEFAULT_DOC_NAME);

        // Get all the orgPrerequisiteDocList where docName contains UPDATED_DOC_NAME
        defaultOrgPrerequisiteDocShouldNotBeFound("docName.contains=" + UPDATED_DOC_NAME);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByDocNameNotContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where docName does not contain DEFAULT_DOC_NAME
        defaultOrgPrerequisiteDocShouldNotBeFound("docName.doesNotContain=" + DEFAULT_DOC_NAME);

        // Get all the orgPrerequisiteDocList where docName does not contain UPDATED_DOC_NAME
        defaultOrgPrerequisiteDocShouldBeFound("docName.doesNotContain=" + UPDATED_DOC_NAME);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByDocCatagoryIsEqualToSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where docCatagory equals to DEFAULT_DOC_CATAGORY
        defaultOrgPrerequisiteDocShouldBeFound("docCatagory.equals=" + DEFAULT_DOC_CATAGORY);

        // Get all the orgPrerequisiteDocList where docCatagory equals to UPDATED_DOC_CATAGORY
        defaultOrgPrerequisiteDocShouldNotBeFound("docCatagory.equals=" + UPDATED_DOC_CATAGORY);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByDocCatagoryIsInShouldWork() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where docCatagory in DEFAULT_DOC_CATAGORY or UPDATED_DOC_CATAGORY
        defaultOrgPrerequisiteDocShouldBeFound("docCatagory.in=" + DEFAULT_DOC_CATAGORY + "," + UPDATED_DOC_CATAGORY);

        // Get all the orgPrerequisiteDocList where docCatagory equals to UPDATED_DOC_CATAGORY
        defaultOrgPrerequisiteDocShouldNotBeFound("docCatagory.in=" + UPDATED_DOC_CATAGORY);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByDocCatagoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where docCatagory is not null
        defaultOrgPrerequisiteDocShouldBeFound("docCatagory.specified=true");

        // Get all the orgPrerequisiteDocList where docCatagory is null
        defaultOrgPrerequisiteDocShouldNotBeFound("docCatagory.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByDocCatagoryContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where docCatagory contains DEFAULT_DOC_CATAGORY
        defaultOrgPrerequisiteDocShouldBeFound("docCatagory.contains=" + DEFAULT_DOC_CATAGORY);

        // Get all the orgPrerequisiteDocList where docCatagory contains UPDATED_DOC_CATAGORY
        defaultOrgPrerequisiteDocShouldNotBeFound("docCatagory.contains=" + UPDATED_DOC_CATAGORY);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByDocCatagoryNotContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where docCatagory does not contain DEFAULT_DOC_CATAGORY
        defaultOrgPrerequisiteDocShouldNotBeFound("docCatagory.doesNotContain=" + DEFAULT_DOC_CATAGORY);

        // Get all the orgPrerequisiteDocList where docCatagory does not contain UPDATED_DOC_CATAGORY
        defaultOrgPrerequisiteDocShouldBeFound("docCatagory.doesNotContain=" + UPDATED_DOC_CATAGORY);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByIsmandatoryIsEqualToSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where ismandatory equals to DEFAULT_ISMANDATORY
        defaultOrgPrerequisiteDocShouldBeFound("ismandatory.equals=" + DEFAULT_ISMANDATORY);

        // Get all the orgPrerequisiteDocList where ismandatory equals to UPDATED_ISMANDATORY
        defaultOrgPrerequisiteDocShouldNotBeFound("ismandatory.equals=" + UPDATED_ISMANDATORY);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByIsmandatoryIsInShouldWork() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where ismandatory in DEFAULT_ISMANDATORY or UPDATED_ISMANDATORY
        defaultOrgPrerequisiteDocShouldBeFound("ismandatory.in=" + DEFAULT_ISMANDATORY + "," + UPDATED_ISMANDATORY);

        // Get all the orgPrerequisiteDocList where ismandatory equals to UPDATED_ISMANDATORY
        defaultOrgPrerequisiteDocShouldNotBeFound("ismandatory.in=" + UPDATED_ISMANDATORY);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByIsmandatoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where ismandatory is not null
        defaultOrgPrerequisiteDocShouldBeFound("ismandatory.specified=true");

        // Get all the orgPrerequisiteDocList where ismandatory is null
        defaultOrgPrerequisiteDocShouldNotBeFound("ismandatory.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultOrgPrerequisiteDocShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the orgPrerequisiteDocList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultOrgPrerequisiteDocShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultOrgPrerequisiteDocShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the orgPrerequisiteDocList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultOrgPrerequisiteDocShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where lastModified is not null
        defaultOrgPrerequisiteDocShouldBeFound("lastModified.specified=true");

        // Get all the orgPrerequisiteDocList where lastModified is null
        defaultOrgPrerequisiteDocShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultOrgPrerequisiteDocShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the orgPrerequisiteDocList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOrgPrerequisiteDocShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultOrgPrerequisiteDocShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the orgPrerequisiteDocList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOrgPrerequisiteDocShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where lastModifiedBy is not null
        defaultOrgPrerequisiteDocShouldBeFound("lastModifiedBy.specified=true");

        // Get all the orgPrerequisiteDocList where lastModifiedBy is null
        defaultOrgPrerequisiteDocShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultOrgPrerequisiteDocShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the orgPrerequisiteDocList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultOrgPrerequisiteDocShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultOrgPrerequisiteDocShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the orgPrerequisiteDocList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultOrgPrerequisiteDocShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where createdBy equals to DEFAULT_CREATED_BY
        defaultOrgPrerequisiteDocShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the orgPrerequisiteDocList where createdBy equals to UPDATED_CREATED_BY
        defaultOrgPrerequisiteDocShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultOrgPrerequisiteDocShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the orgPrerequisiteDocList where createdBy equals to UPDATED_CREATED_BY
        defaultOrgPrerequisiteDocShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where createdBy is not null
        defaultOrgPrerequisiteDocShouldBeFound("createdBy.specified=true");

        // Get all the orgPrerequisiteDocList where createdBy is null
        defaultOrgPrerequisiteDocShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where createdBy contains DEFAULT_CREATED_BY
        defaultOrgPrerequisiteDocShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the orgPrerequisiteDocList where createdBy contains UPDATED_CREATED_BY
        defaultOrgPrerequisiteDocShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where createdBy does not contain DEFAULT_CREATED_BY
        defaultOrgPrerequisiteDocShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the orgPrerequisiteDocList where createdBy does not contain UPDATED_CREATED_BY
        defaultOrgPrerequisiteDocShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where createdOn equals to DEFAULT_CREATED_ON
        defaultOrgPrerequisiteDocShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the orgPrerequisiteDocList where createdOn equals to UPDATED_CREATED_ON
        defaultOrgPrerequisiteDocShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultOrgPrerequisiteDocShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the orgPrerequisiteDocList where createdOn equals to UPDATED_CREATED_ON
        defaultOrgPrerequisiteDocShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where createdOn is not null
        defaultOrgPrerequisiteDocShouldBeFound("createdOn.specified=true");

        // Get all the orgPrerequisiteDocList where createdOn is null
        defaultOrgPrerequisiteDocShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where isDeleted equals to DEFAULT_IS_DELETED
        defaultOrgPrerequisiteDocShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the orgPrerequisiteDocList where isDeleted equals to UPDATED_IS_DELETED
        defaultOrgPrerequisiteDocShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultOrgPrerequisiteDocShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the orgPrerequisiteDocList where isDeleted equals to UPDATED_IS_DELETED
        defaultOrgPrerequisiteDocShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where isDeleted is not null
        defaultOrgPrerequisiteDocShouldBeFound("isDeleted.specified=true");

        // Get all the orgPrerequisiteDocList where isDeleted is null
        defaultOrgPrerequisiteDocShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultOrgPrerequisiteDocShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the orgPrerequisiteDocList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultOrgPrerequisiteDocShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the orgPrerequisiteDocList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField1 is not null
        defaultOrgPrerequisiteDocShouldBeFound("freeField1.specified=true");

        // Get all the orgPrerequisiteDocList where freeField1 is null
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultOrgPrerequisiteDocShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the orgPrerequisiteDocList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the orgPrerequisiteDocList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultOrgPrerequisiteDocShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultOrgPrerequisiteDocShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the orgPrerequisiteDocList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultOrgPrerequisiteDocShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the orgPrerequisiteDocList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField2 is not null
        defaultOrgPrerequisiteDocShouldBeFound("freeField2.specified=true");

        // Get all the orgPrerequisiteDocList where freeField2 is null
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultOrgPrerequisiteDocShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the orgPrerequisiteDocList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the orgPrerequisiteDocList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultOrgPrerequisiteDocShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultOrgPrerequisiteDocShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the orgPrerequisiteDocList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultOrgPrerequisiteDocShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the orgPrerequisiteDocList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField3 is not null
        defaultOrgPrerequisiteDocShouldBeFound("freeField3.specified=true");

        // Get all the orgPrerequisiteDocList where freeField3 is null
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultOrgPrerequisiteDocShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the orgPrerequisiteDocList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the orgPrerequisiteDocList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultOrgPrerequisiteDocShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultOrgPrerequisiteDocShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the orgPrerequisiteDocList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultOrgPrerequisiteDocShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the orgPrerequisiteDocList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField4 is not null
        defaultOrgPrerequisiteDocShouldBeFound("freeField4.specified=true");

        // Get all the orgPrerequisiteDocList where freeField4 is null
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultOrgPrerequisiteDocShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the orgPrerequisiteDocList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the orgPrerequisiteDocList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultOrgPrerequisiteDocShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultOrgPrerequisiteDocShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the orgPrerequisiteDocList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultOrgPrerequisiteDocShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the orgPrerequisiteDocList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField5 is not null
        defaultOrgPrerequisiteDocShouldBeFound("freeField5.specified=true");

        // Get all the orgPrerequisiteDocList where freeField5 is null
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultOrgPrerequisiteDocShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the orgPrerequisiteDocList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        // Get all the orgPrerequisiteDocList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultOrgPrerequisiteDocShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the orgPrerequisiteDocList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultOrgPrerequisiteDocShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByProductIsEqualToSomething() throws Exception {
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);
            product = ProductResourceIT.createEntity(em);
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        orgPrerequisiteDoc.setProduct(product);
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);
        Long productId = product.getId();

        // Get all the orgPrerequisiteDocList where product equals to productId
        defaultOrgPrerequisiteDocShouldBeFound("productId.equals=" + productId);

        // Get all the orgPrerequisiteDocList where product equals to (productId + 1)
        defaultOrgPrerequisiteDocShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllOrgPrerequisiteDocsByOrganisationIsEqualToSomething() throws Exception {
        Organisation organisation;
        if (TestUtil.findAll(em, Organisation.class).isEmpty()) {
            orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);
            organisation = OrganisationResourceIT.createEntity(em);
        } else {
            organisation = TestUtil.findAll(em, Organisation.class).get(0);
        }
        em.persist(organisation);
        em.flush();
        orgPrerequisiteDoc.setOrganisation(organisation);
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);
        Long organisationId = organisation.getId();

        // Get all the orgPrerequisiteDocList where organisation equals to organisationId
        defaultOrgPrerequisiteDocShouldBeFound("organisationId.equals=" + organisationId);

        // Get all the orgPrerequisiteDocList where organisation equals to (organisationId + 1)
        defaultOrgPrerequisiteDocShouldNotBeFound("organisationId.equals=" + (organisationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrgPrerequisiteDocShouldBeFound(String filter) throws Exception {
        restOrgPrerequisiteDocMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orgPrerequisiteDoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].docDesc").value(hasItem(DEFAULT_DOC_DESC)))
            .andExpect(jsonPath("$.[*].docName").value(hasItem(DEFAULT_DOC_NAME)))
            .andExpect(jsonPath("$.[*].docCatagory").value(hasItem(DEFAULT_DOC_CATAGORY)))
            .andExpect(jsonPath("$.[*].ismandatory").value(hasItem(DEFAULT_ISMANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].freeField5").value(hasItem(DEFAULT_FREE_FIELD_5)));

        // Check, that the count call also returns 1
        restOrgPrerequisiteDocMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrgPrerequisiteDocShouldNotBeFound(String filter) throws Exception {
        restOrgPrerequisiteDocMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrgPrerequisiteDocMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrgPrerequisiteDoc() throws Exception {
        // Get the orgPrerequisiteDoc
        restOrgPrerequisiteDocMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrgPrerequisiteDoc() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        int databaseSizeBeforeUpdate = orgPrerequisiteDocRepository.findAll().size();

        // Update the orgPrerequisiteDoc
        OrgPrerequisiteDoc updatedOrgPrerequisiteDoc = orgPrerequisiteDocRepository.findById(orgPrerequisiteDoc.getId()).get();
        // Disconnect from session so that the updates on updatedOrgPrerequisiteDoc are not directly saved in db
        em.detach(updatedOrgPrerequisiteDoc);
        updatedOrgPrerequisiteDoc
            .docDesc(UPDATED_DOC_DESC)
            .docName(UPDATED_DOC_NAME)
            .docCatagory(UPDATED_DOC_CATAGORY)
            .ismandatory(UPDATED_ISMANDATORY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);
        OrgPrerequisiteDocDTO orgPrerequisiteDocDTO = orgPrerequisiteDocMapper.toDto(updatedOrgPrerequisiteDoc);

        restOrgPrerequisiteDocMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orgPrerequisiteDocDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgPrerequisiteDocDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrgPrerequisiteDoc in the database
        List<OrgPrerequisiteDoc> orgPrerequisiteDocList = orgPrerequisiteDocRepository.findAll();
        assertThat(orgPrerequisiteDocList).hasSize(databaseSizeBeforeUpdate);
        OrgPrerequisiteDoc testOrgPrerequisiteDoc = orgPrerequisiteDocList.get(orgPrerequisiteDocList.size() - 1);
        assertThat(testOrgPrerequisiteDoc.getDocDesc()).isEqualTo(UPDATED_DOC_DESC);
        assertThat(testOrgPrerequisiteDoc.getDocName()).isEqualTo(UPDATED_DOC_NAME);
        assertThat(testOrgPrerequisiteDoc.getDocCatagory()).isEqualTo(UPDATED_DOC_CATAGORY);
        assertThat(testOrgPrerequisiteDoc.getIsmandatory()).isEqualTo(UPDATED_ISMANDATORY);
        assertThat(testOrgPrerequisiteDoc.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testOrgPrerequisiteDoc.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOrgPrerequisiteDoc.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrgPrerequisiteDoc.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testOrgPrerequisiteDoc.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testOrgPrerequisiteDoc.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testOrgPrerequisiteDoc.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testOrgPrerequisiteDoc.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testOrgPrerequisiteDoc.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testOrgPrerequisiteDoc.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void putNonExistingOrgPrerequisiteDoc() throws Exception {
        int databaseSizeBeforeUpdate = orgPrerequisiteDocRepository.findAll().size();
        orgPrerequisiteDoc.setId(count.incrementAndGet());

        // Create the OrgPrerequisiteDoc
        OrgPrerequisiteDocDTO orgPrerequisiteDocDTO = orgPrerequisiteDocMapper.toDto(orgPrerequisiteDoc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgPrerequisiteDocMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orgPrerequisiteDocDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgPrerequisiteDocDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgPrerequisiteDoc in the database
        List<OrgPrerequisiteDoc> orgPrerequisiteDocList = orgPrerequisiteDocRepository.findAll();
        assertThat(orgPrerequisiteDocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrgPrerequisiteDoc() throws Exception {
        int databaseSizeBeforeUpdate = orgPrerequisiteDocRepository.findAll().size();
        orgPrerequisiteDoc.setId(count.incrementAndGet());

        // Create the OrgPrerequisiteDoc
        OrgPrerequisiteDocDTO orgPrerequisiteDocDTO = orgPrerequisiteDocMapper.toDto(orgPrerequisiteDoc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgPrerequisiteDocMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgPrerequisiteDocDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgPrerequisiteDoc in the database
        List<OrgPrerequisiteDoc> orgPrerequisiteDocList = orgPrerequisiteDocRepository.findAll();
        assertThat(orgPrerequisiteDocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrgPrerequisiteDoc() throws Exception {
        int databaseSizeBeforeUpdate = orgPrerequisiteDocRepository.findAll().size();
        orgPrerequisiteDoc.setId(count.incrementAndGet());

        // Create the OrgPrerequisiteDoc
        OrgPrerequisiteDocDTO orgPrerequisiteDocDTO = orgPrerequisiteDocMapper.toDto(orgPrerequisiteDoc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgPrerequisiteDocMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orgPrerequisiteDocDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrgPrerequisiteDoc in the database
        List<OrgPrerequisiteDoc> orgPrerequisiteDocList = orgPrerequisiteDocRepository.findAll();
        assertThat(orgPrerequisiteDocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrgPrerequisiteDocWithPatch() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        int databaseSizeBeforeUpdate = orgPrerequisiteDocRepository.findAll().size();

        // Update the orgPrerequisiteDoc using partial update
        OrgPrerequisiteDoc partialUpdatedOrgPrerequisiteDoc = new OrgPrerequisiteDoc();
        partialUpdatedOrgPrerequisiteDoc.setId(orgPrerequisiteDoc.getId());

        partialUpdatedOrgPrerequisiteDoc
            .docDesc(UPDATED_DOC_DESC)
            .docName(UPDATED_DOC_NAME)
            .docCatagory(UPDATED_DOC_CATAGORY)
            .ismandatory(UPDATED_ISMANDATORY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restOrgPrerequisiteDocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrgPrerequisiteDoc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrgPrerequisiteDoc))
            )
            .andExpect(status().isOk());

        // Validate the OrgPrerequisiteDoc in the database
        List<OrgPrerequisiteDoc> orgPrerequisiteDocList = orgPrerequisiteDocRepository.findAll();
        assertThat(orgPrerequisiteDocList).hasSize(databaseSizeBeforeUpdate);
        OrgPrerequisiteDoc testOrgPrerequisiteDoc = orgPrerequisiteDocList.get(orgPrerequisiteDocList.size() - 1);
        assertThat(testOrgPrerequisiteDoc.getDocDesc()).isEqualTo(UPDATED_DOC_DESC);
        assertThat(testOrgPrerequisiteDoc.getDocName()).isEqualTo(UPDATED_DOC_NAME);
        assertThat(testOrgPrerequisiteDoc.getDocCatagory()).isEqualTo(UPDATED_DOC_CATAGORY);
        assertThat(testOrgPrerequisiteDoc.getIsmandatory()).isEqualTo(UPDATED_ISMANDATORY);
        assertThat(testOrgPrerequisiteDoc.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testOrgPrerequisiteDoc.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOrgPrerequisiteDoc.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrgPrerequisiteDoc.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testOrgPrerequisiteDoc.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testOrgPrerequisiteDoc.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testOrgPrerequisiteDoc.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testOrgPrerequisiteDoc.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testOrgPrerequisiteDoc.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testOrgPrerequisiteDoc.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void fullUpdateOrgPrerequisiteDocWithPatch() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        int databaseSizeBeforeUpdate = orgPrerequisiteDocRepository.findAll().size();

        // Update the orgPrerequisiteDoc using partial update
        OrgPrerequisiteDoc partialUpdatedOrgPrerequisiteDoc = new OrgPrerequisiteDoc();
        partialUpdatedOrgPrerequisiteDoc.setId(orgPrerequisiteDoc.getId());

        partialUpdatedOrgPrerequisiteDoc
            .docDesc(UPDATED_DOC_DESC)
            .docName(UPDATED_DOC_NAME)
            .docCatagory(UPDATED_DOC_CATAGORY)
            .ismandatory(UPDATED_ISMANDATORY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .freeField5(UPDATED_FREE_FIELD_5);

        restOrgPrerequisiteDocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrgPrerequisiteDoc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrgPrerequisiteDoc))
            )
            .andExpect(status().isOk());

        // Validate the OrgPrerequisiteDoc in the database
        List<OrgPrerequisiteDoc> orgPrerequisiteDocList = orgPrerequisiteDocRepository.findAll();
        assertThat(orgPrerequisiteDocList).hasSize(databaseSizeBeforeUpdate);
        OrgPrerequisiteDoc testOrgPrerequisiteDoc = orgPrerequisiteDocList.get(orgPrerequisiteDocList.size() - 1);
        assertThat(testOrgPrerequisiteDoc.getDocDesc()).isEqualTo(UPDATED_DOC_DESC);
        assertThat(testOrgPrerequisiteDoc.getDocName()).isEqualTo(UPDATED_DOC_NAME);
        assertThat(testOrgPrerequisiteDoc.getDocCatagory()).isEqualTo(UPDATED_DOC_CATAGORY);
        assertThat(testOrgPrerequisiteDoc.getIsmandatory()).isEqualTo(UPDATED_ISMANDATORY);
        assertThat(testOrgPrerequisiteDoc.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testOrgPrerequisiteDoc.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOrgPrerequisiteDoc.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrgPrerequisiteDoc.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testOrgPrerequisiteDoc.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testOrgPrerequisiteDoc.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testOrgPrerequisiteDoc.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testOrgPrerequisiteDoc.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testOrgPrerequisiteDoc.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testOrgPrerequisiteDoc.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void patchNonExistingOrgPrerequisiteDoc() throws Exception {
        int databaseSizeBeforeUpdate = orgPrerequisiteDocRepository.findAll().size();
        orgPrerequisiteDoc.setId(count.incrementAndGet());

        // Create the OrgPrerequisiteDoc
        OrgPrerequisiteDocDTO orgPrerequisiteDocDTO = orgPrerequisiteDocMapper.toDto(orgPrerequisiteDoc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrgPrerequisiteDocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orgPrerequisiteDocDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgPrerequisiteDocDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgPrerequisiteDoc in the database
        List<OrgPrerequisiteDoc> orgPrerequisiteDocList = orgPrerequisiteDocRepository.findAll();
        assertThat(orgPrerequisiteDocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrgPrerequisiteDoc() throws Exception {
        int databaseSizeBeforeUpdate = orgPrerequisiteDocRepository.findAll().size();
        orgPrerequisiteDoc.setId(count.incrementAndGet());

        // Create the OrgPrerequisiteDoc
        OrgPrerequisiteDocDTO orgPrerequisiteDocDTO = orgPrerequisiteDocMapper.toDto(orgPrerequisiteDoc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgPrerequisiteDocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgPrerequisiteDocDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrgPrerequisiteDoc in the database
        List<OrgPrerequisiteDoc> orgPrerequisiteDocList = orgPrerequisiteDocRepository.findAll();
        assertThat(orgPrerequisiteDocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrgPrerequisiteDoc() throws Exception {
        int databaseSizeBeforeUpdate = orgPrerequisiteDocRepository.findAll().size();
        orgPrerequisiteDoc.setId(count.incrementAndGet());

        // Create the OrgPrerequisiteDoc
        OrgPrerequisiteDocDTO orgPrerequisiteDocDTO = orgPrerequisiteDocMapper.toDto(orgPrerequisiteDoc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrgPrerequisiteDocMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orgPrerequisiteDocDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrgPrerequisiteDoc in the database
        List<OrgPrerequisiteDoc> orgPrerequisiteDocList = orgPrerequisiteDocRepository.findAll();
        assertThat(orgPrerequisiteDocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrgPrerequisiteDoc() throws Exception {
        // Initialize the database
        orgPrerequisiteDocRepository.saveAndFlush(orgPrerequisiteDoc);

        int databaseSizeBeforeDelete = orgPrerequisiteDocRepository.findAll().size();

        // Delete the orgPrerequisiteDoc
        restOrgPrerequisiteDocMockMvc
            .perform(delete(ENTITY_API_URL_ID, orgPrerequisiteDoc.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrgPrerequisiteDoc> orgPrerequisiteDocList = orgPrerequisiteDocRepository.findAll();
        assertThat(orgPrerequisiteDocList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
