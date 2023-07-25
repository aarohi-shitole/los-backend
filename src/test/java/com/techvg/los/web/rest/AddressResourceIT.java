package com.techvg.los.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.los.IntegrationTest;
import com.techvg.los.domain.Address;
import com.techvg.los.domain.City;
import com.techvg.los.domain.District;
import com.techvg.los.domain.Member;
import com.techvg.los.domain.SecurityUser;
import com.techvg.los.domain.State;
import com.techvg.los.domain.Taluka;
import com.techvg.los.domain.enumeration.AddressType;
import com.techvg.los.repository.AddressRepository;
import com.techvg.los.service.criteria.AddressCriteria;
import com.techvg.los.service.dto.AddressDTO;
import com.techvg.los.service.mapper.AddressMapper;
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
 * Integration tests for the {@link AddressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AddressResourceIT {

    private static final AddressType DEFAULT_ADDR_STATUS = AddressType.CURRENT_ADDRESS;
    private static final AddressType UPDATED_ADDR_STATUS = AddressType.PERMANENT_ADDRESS;

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_LAND_MARK = "AAAAAAAAAA";
    private static final String UPDATED_LAND_MARK = "BBBBBBBBBB";

    private static final String DEFAULT_PINCODE = "AAAAAAAAAA";
    private static final String UPDATED_PINCODE = "BBBBBBBBBB";

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;
    private static final Double SMALLER_LATITUDE = 1D - 1D;

    private static final String DEFAULT_MAP_NEV_URL = "AAAAAAAAAA";
    private static final String UPDATED_MAP_NEV_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_PREFFERED_ADD = false;
    private static final Boolean UPDATED_IS_PREFFERED_ADD = true;

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

    private static final String ENTITY_API_URL = "/api/addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressMockMvc;

    private Address address;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity(EntityManager em) {
        Address address = new Address()
            .addrStatus(DEFAULT_ADDR_STATUS)
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .addressLine3(DEFAULT_ADDRESS_LINE_3)
            .landMark(DEFAULT_LAND_MARK)
            .pincode(DEFAULT_PINCODE)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .mapNevUrl(DEFAULT_MAP_NEV_URL)
            .isPrefferedAdd(DEFAULT_IS_PREFFERED_ADD)
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
        return address;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createUpdatedEntity(EntityManager em) {
        Address address = new Address()
            .addrStatus(UPDATED_ADDR_STATUS)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .addressLine3(UPDATED_ADDRESS_LINE_3)
            .landMark(UPDATED_LAND_MARK)
            .pincode(UPDATED_PINCODE)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .mapNevUrl(UPDATED_MAP_NEV_URL)
            .isPrefferedAdd(UPDATED_IS_PREFFERED_ADD)
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
        return address;
    }

    @BeforeEach
    public void initTest() {
        address = createEntity(em);
    }

    @Test
    @Transactional
    void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();
        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);
        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getAddrStatus()).isEqualTo(DEFAULT_ADDR_STATUS);
        assertThat(testAddress.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testAddress.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testAddress.getAddressLine3()).isEqualTo(DEFAULT_ADDRESS_LINE_3);
        assertThat(testAddress.getLandMark()).isEqualTo(DEFAULT_LAND_MARK);
        assertThat(testAddress.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testAddress.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testAddress.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testAddress.getMapNevUrl()).isEqualTo(DEFAULT_MAP_NEV_URL);
        assertThat(testAddress.getIsPrefferedAdd()).isEqualTo(DEFAULT_IS_PREFFERED_ADD);
        assertThat(testAddress.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testAddress.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAddress.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAddress.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testAddress.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testAddress.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testAddress.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testAddress.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testAddress.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void createAddressWithExistingId() throws Exception {
        // Create the Address with an existing ID
        address.setId(1L);
        AddressDTO addressDTO = addressMapper.toDto(address);

        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].addrStatus").value(hasItem(DEFAULT_ADDR_STATUS.toString())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1)))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2)))
            .andExpect(jsonPath("$.[*].addressLine3").value(hasItem(DEFAULT_ADDRESS_LINE_3)))
            .andExpect(jsonPath("$.[*].landMark").value(hasItem(DEFAULT_LAND_MARK)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].mapNevUrl").value(hasItem(DEFAULT_MAP_NEV_URL)))
            .andExpect(jsonPath("$.[*].isPrefferedAdd").value(hasItem(DEFAULT_IS_PREFFERED_ADD.booleanValue())))
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
    void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc
            .perform(get(ENTITY_API_URL_ID, address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.addrStatus").value(DEFAULT_ADDR_STATUS.toString()))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2))
            .andExpect(jsonPath("$.addressLine3").value(DEFAULT_ADDRESS_LINE_3))
            .andExpect(jsonPath("$.landMark").value(DEFAULT_LAND_MARK))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.mapNevUrl").value(DEFAULT_MAP_NEV_URL))
            .andExpect(jsonPath("$.isPrefferedAdd").value(DEFAULT_IS_PREFFERED_ADD.booleanValue()))
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
    void getAddressesByIdFiltering() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        Long id = address.getId();

        defaultAddressShouldBeFound("id.equals=" + id);
        defaultAddressShouldNotBeFound("id.notEquals=" + id);

        defaultAddressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAddressShouldNotBeFound("id.greaterThan=" + id);

        defaultAddressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAddressShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAddressesByAddrStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addrStatus equals to DEFAULT_ADDR_STATUS
        defaultAddressShouldBeFound("addrStatus.equals=" + DEFAULT_ADDR_STATUS);

        // Get all the addressList where addrStatus equals to UPDATED_ADDR_STATUS
        defaultAddressShouldNotBeFound("addrStatus.equals=" + UPDATED_ADDR_STATUS);
    }

    @Test
    @Transactional
    void getAllAddressesByAddrStatusIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addrStatus in DEFAULT_ADDR_STATUS or UPDATED_ADDR_STATUS
        defaultAddressShouldBeFound("addrStatus.in=" + DEFAULT_ADDR_STATUS + "," + UPDATED_ADDR_STATUS);

        // Get all the addressList where addrStatus equals to UPDATED_ADDR_STATUS
        defaultAddressShouldNotBeFound("addrStatus.in=" + UPDATED_ADDR_STATUS);
    }

    @Test
    @Transactional
    void getAllAddressesByAddrStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addrStatus is not null
        defaultAddressShouldBeFound("addrStatus.specified=true");

        // Get all the addressList where addrStatus is null
        defaultAddressShouldNotBeFound("addrStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByAddressLine1IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressLine1 equals to DEFAULT_ADDRESS_LINE_1
        defaultAddressShouldBeFound("addressLine1.equals=" + DEFAULT_ADDRESS_LINE_1);

        // Get all the addressList where addressLine1 equals to UPDATED_ADDRESS_LINE_1
        defaultAddressShouldNotBeFound("addressLine1.equals=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressLine1IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressLine1 in DEFAULT_ADDRESS_LINE_1 or UPDATED_ADDRESS_LINE_1
        defaultAddressShouldBeFound("addressLine1.in=" + DEFAULT_ADDRESS_LINE_1 + "," + UPDATED_ADDRESS_LINE_1);

        // Get all the addressList where addressLine1 equals to UPDATED_ADDRESS_LINE_1
        defaultAddressShouldNotBeFound("addressLine1.in=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressLine1IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressLine1 is not null
        defaultAddressShouldBeFound("addressLine1.specified=true");

        // Get all the addressList where addressLine1 is null
        defaultAddressShouldNotBeFound("addressLine1.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByAddressLine1ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressLine1 contains DEFAULT_ADDRESS_LINE_1
        defaultAddressShouldBeFound("addressLine1.contains=" + DEFAULT_ADDRESS_LINE_1);

        // Get all the addressList where addressLine1 contains UPDATED_ADDRESS_LINE_1
        defaultAddressShouldNotBeFound("addressLine1.contains=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressLine1NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressLine1 does not contain DEFAULT_ADDRESS_LINE_1
        defaultAddressShouldNotBeFound("addressLine1.doesNotContain=" + DEFAULT_ADDRESS_LINE_1);

        // Get all the addressList where addressLine1 does not contain UPDATED_ADDRESS_LINE_1
        defaultAddressShouldBeFound("addressLine1.doesNotContain=" + UPDATED_ADDRESS_LINE_1);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressLine2IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressLine2 equals to DEFAULT_ADDRESS_LINE_2
        defaultAddressShouldBeFound("addressLine2.equals=" + DEFAULT_ADDRESS_LINE_2);

        // Get all the addressList where addressLine2 equals to UPDATED_ADDRESS_LINE_2
        defaultAddressShouldNotBeFound("addressLine2.equals=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressLine2IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressLine2 in DEFAULT_ADDRESS_LINE_2 or UPDATED_ADDRESS_LINE_2
        defaultAddressShouldBeFound("addressLine2.in=" + DEFAULT_ADDRESS_LINE_2 + "," + UPDATED_ADDRESS_LINE_2);

        // Get all the addressList where addressLine2 equals to UPDATED_ADDRESS_LINE_2
        defaultAddressShouldNotBeFound("addressLine2.in=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressLine2IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressLine2 is not null
        defaultAddressShouldBeFound("addressLine2.specified=true");

        // Get all the addressList where addressLine2 is null
        defaultAddressShouldNotBeFound("addressLine2.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByAddressLine2ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressLine2 contains DEFAULT_ADDRESS_LINE_2
        defaultAddressShouldBeFound("addressLine2.contains=" + DEFAULT_ADDRESS_LINE_2);

        // Get all the addressList where addressLine2 contains UPDATED_ADDRESS_LINE_2
        defaultAddressShouldNotBeFound("addressLine2.contains=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressLine2NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressLine2 does not contain DEFAULT_ADDRESS_LINE_2
        defaultAddressShouldNotBeFound("addressLine2.doesNotContain=" + DEFAULT_ADDRESS_LINE_2);

        // Get all the addressList where addressLine2 does not contain UPDATED_ADDRESS_LINE_2
        defaultAddressShouldBeFound("addressLine2.doesNotContain=" + UPDATED_ADDRESS_LINE_2);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressLine3IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressLine3 equals to DEFAULT_ADDRESS_LINE_3
        defaultAddressShouldBeFound("addressLine3.equals=" + DEFAULT_ADDRESS_LINE_3);

        // Get all the addressList where addressLine3 equals to UPDATED_ADDRESS_LINE_3
        defaultAddressShouldNotBeFound("addressLine3.equals=" + UPDATED_ADDRESS_LINE_3);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressLine3IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressLine3 in DEFAULT_ADDRESS_LINE_3 or UPDATED_ADDRESS_LINE_3
        defaultAddressShouldBeFound("addressLine3.in=" + DEFAULT_ADDRESS_LINE_3 + "," + UPDATED_ADDRESS_LINE_3);

        // Get all the addressList where addressLine3 equals to UPDATED_ADDRESS_LINE_3
        defaultAddressShouldNotBeFound("addressLine3.in=" + UPDATED_ADDRESS_LINE_3);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressLine3IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressLine3 is not null
        defaultAddressShouldBeFound("addressLine3.specified=true");

        // Get all the addressList where addressLine3 is null
        defaultAddressShouldNotBeFound("addressLine3.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByAddressLine3ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressLine3 contains DEFAULT_ADDRESS_LINE_3
        defaultAddressShouldBeFound("addressLine3.contains=" + DEFAULT_ADDRESS_LINE_3);

        // Get all the addressList where addressLine3 contains UPDATED_ADDRESS_LINE_3
        defaultAddressShouldNotBeFound("addressLine3.contains=" + UPDATED_ADDRESS_LINE_3);
    }

    @Test
    @Transactional
    void getAllAddressesByAddressLine3NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where addressLine3 does not contain DEFAULT_ADDRESS_LINE_3
        defaultAddressShouldNotBeFound("addressLine3.doesNotContain=" + DEFAULT_ADDRESS_LINE_3);

        // Get all the addressList where addressLine3 does not contain UPDATED_ADDRESS_LINE_3
        defaultAddressShouldBeFound("addressLine3.doesNotContain=" + UPDATED_ADDRESS_LINE_3);
    }

    @Test
    @Transactional
    void getAllAddressesByLandMarkIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landMark equals to DEFAULT_LAND_MARK
        defaultAddressShouldBeFound("landMark.equals=" + DEFAULT_LAND_MARK);

        // Get all the addressList where landMark equals to UPDATED_LAND_MARK
        defaultAddressShouldNotBeFound("landMark.equals=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllAddressesByLandMarkIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landMark in DEFAULT_LAND_MARK or UPDATED_LAND_MARK
        defaultAddressShouldBeFound("landMark.in=" + DEFAULT_LAND_MARK + "," + UPDATED_LAND_MARK);

        // Get all the addressList where landMark equals to UPDATED_LAND_MARK
        defaultAddressShouldNotBeFound("landMark.in=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllAddressesByLandMarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landMark is not null
        defaultAddressShouldBeFound("landMark.specified=true");

        // Get all the addressList where landMark is null
        defaultAddressShouldNotBeFound("landMark.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLandMarkContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landMark contains DEFAULT_LAND_MARK
        defaultAddressShouldBeFound("landMark.contains=" + DEFAULT_LAND_MARK);

        // Get all the addressList where landMark contains UPDATED_LAND_MARK
        defaultAddressShouldNotBeFound("landMark.contains=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllAddressesByLandMarkNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landMark does not contain DEFAULT_LAND_MARK
        defaultAddressShouldNotBeFound("landMark.doesNotContain=" + DEFAULT_LAND_MARK);

        // Get all the addressList where landMark does not contain UPDATED_LAND_MARK
        defaultAddressShouldBeFound("landMark.doesNotContain=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode equals to DEFAULT_PINCODE
        defaultAddressShouldBeFound("pincode.equals=" + DEFAULT_PINCODE);

        // Get all the addressList where pincode equals to UPDATED_PINCODE
        defaultAddressShouldNotBeFound("pincode.equals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode in DEFAULT_PINCODE or UPDATED_PINCODE
        defaultAddressShouldBeFound("pincode.in=" + DEFAULT_PINCODE + "," + UPDATED_PINCODE);

        // Get all the addressList where pincode equals to UPDATED_PINCODE
        defaultAddressShouldNotBeFound("pincode.in=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode is not null
        defaultAddressShouldBeFound("pincode.specified=true");

        // Get all the addressList where pincode is null
        defaultAddressShouldNotBeFound("pincode.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode contains DEFAULT_PINCODE
        defaultAddressShouldBeFound("pincode.contains=" + DEFAULT_PINCODE);

        // Get all the addressList where pincode contains UPDATED_PINCODE
        defaultAddressShouldNotBeFound("pincode.contains=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode does not contain DEFAULT_PINCODE
        defaultAddressShouldNotBeFound("pincode.doesNotContain=" + DEFAULT_PINCODE);

        // Get all the addressList where pincode does not contain UPDATED_PINCODE
        defaultAddressShouldBeFound("pincode.doesNotContain=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where longitude equals to DEFAULT_LONGITUDE
        defaultAddressShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the addressList where longitude equals to UPDATED_LONGITUDE
        defaultAddressShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultAddressShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the addressList where longitude equals to UPDATED_LONGITUDE
        defaultAddressShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where longitude is not null
        defaultAddressShouldBeFound("longitude.specified=true");

        // Get all the addressList where longitude is null
        defaultAddressShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultAddressShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the addressList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultAddressShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultAddressShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the addressList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultAddressShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where longitude is less than DEFAULT_LONGITUDE
        defaultAddressShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the addressList where longitude is less than UPDATED_LONGITUDE
        defaultAddressShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where longitude is greater than DEFAULT_LONGITUDE
        defaultAddressShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the addressList where longitude is greater than SMALLER_LONGITUDE
        defaultAddressShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where latitude equals to DEFAULT_LATITUDE
        defaultAddressShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the addressList where latitude equals to UPDATED_LATITUDE
        defaultAddressShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultAddressShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the addressList where latitude equals to UPDATED_LATITUDE
        defaultAddressShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where latitude is not null
        defaultAddressShouldBeFound("latitude.specified=true");

        // Get all the addressList where latitude is null
        defaultAddressShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultAddressShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the addressList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultAddressShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultAddressShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the addressList where latitude is less than or equal to SMALLER_LATITUDE
        defaultAddressShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where latitude is less than DEFAULT_LATITUDE
        defaultAddressShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the addressList where latitude is less than UPDATED_LATITUDE
        defaultAddressShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where latitude is greater than DEFAULT_LATITUDE
        defaultAddressShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the addressList where latitude is greater than SMALLER_LATITUDE
        defaultAddressShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByMapNevUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where mapNevUrl equals to DEFAULT_MAP_NEV_URL
        defaultAddressShouldBeFound("mapNevUrl.equals=" + DEFAULT_MAP_NEV_URL);

        // Get all the addressList where mapNevUrl equals to UPDATED_MAP_NEV_URL
        defaultAddressShouldNotBeFound("mapNevUrl.equals=" + UPDATED_MAP_NEV_URL);
    }

    @Test
    @Transactional
    void getAllAddressesByMapNevUrlIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where mapNevUrl in DEFAULT_MAP_NEV_URL or UPDATED_MAP_NEV_URL
        defaultAddressShouldBeFound("mapNevUrl.in=" + DEFAULT_MAP_NEV_URL + "," + UPDATED_MAP_NEV_URL);

        // Get all the addressList where mapNevUrl equals to UPDATED_MAP_NEV_URL
        defaultAddressShouldNotBeFound("mapNevUrl.in=" + UPDATED_MAP_NEV_URL);
    }

    @Test
    @Transactional
    void getAllAddressesByMapNevUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where mapNevUrl is not null
        defaultAddressShouldBeFound("mapNevUrl.specified=true");

        // Get all the addressList where mapNevUrl is null
        defaultAddressShouldNotBeFound("mapNevUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByMapNevUrlContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where mapNevUrl contains DEFAULT_MAP_NEV_URL
        defaultAddressShouldBeFound("mapNevUrl.contains=" + DEFAULT_MAP_NEV_URL);

        // Get all the addressList where mapNevUrl contains UPDATED_MAP_NEV_URL
        defaultAddressShouldNotBeFound("mapNevUrl.contains=" + UPDATED_MAP_NEV_URL);
    }

    @Test
    @Transactional
    void getAllAddressesByMapNevUrlNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where mapNevUrl does not contain DEFAULT_MAP_NEV_URL
        defaultAddressShouldNotBeFound("mapNevUrl.doesNotContain=" + DEFAULT_MAP_NEV_URL);

        // Get all the addressList where mapNevUrl does not contain UPDATED_MAP_NEV_URL
        defaultAddressShouldBeFound("mapNevUrl.doesNotContain=" + UPDATED_MAP_NEV_URL);
    }

    @Test
    @Transactional
    void getAllAddressesByIsPrefferedAddIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where isPrefferedAdd equals to DEFAULT_IS_PREFFERED_ADD
        defaultAddressShouldBeFound("isPrefferedAdd.equals=" + DEFAULT_IS_PREFFERED_ADD);

        // Get all the addressList where isPrefferedAdd equals to UPDATED_IS_PREFFERED_ADD
        defaultAddressShouldNotBeFound("isPrefferedAdd.equals=" + UPDATED_IS_PREFFERED_ADD);
    }

    @Test
    @Transactional
    void getAllAddressesByIsPrefferedAddIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where isPrefferedAdd in DEFAULT_IS_PREFFERED_ADD or UPDATED_IS_PREFFERED_ADD
        defaultAddressShouldBeFound("isPrefferedAdd.in=" + DEFAULT_IS_PREFFERED_ADD + "," + UPDATED_IS_PREFFERED_ADD);

        // Get all the addressList where isPrefferedAdd equals to UPDATED_IS_PREFFERED_ADD
        defaultAddressShouldNotBeFound("isPrefferedAdd.in=" + UPDATED_IS_PREFFERED_ADD);
    }

    @Test
    @Transactional
    void getAllAddressesByIsPrefferedAddIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where isPrefferedAdd is not null
        defaultAddressShouldBeFound("isPrefferedAdd.specified=true");

        // Get all the addressList where isPrefferedAdd is null
        defaultAddressShouldNotBeFound("isPrefferedAdd.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAddressShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the addressList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAddressShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAddressShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the addressList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAddressShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModified is not null
        defaultAddressShouldBeFound("lastModified.specified=true");

        // Get all the addressList where lastModified is null
        defaultAddressShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy is not null
        defaultAddressShouldBeFound("lastModifiedBy.specified=true");

        // Get all the addressList where lastModifiedBy is null
        defaultAddressShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdBy equals to DEFAULT_CREATED_BY
        defaultAddressShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the addressList where createdBy equals to UPDATED_CREATED_BY
        defaultAddressShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultAddressShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the addressList where createdBy equals to UPDATED_CREATED_BY
        defaultAddressShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdBy is not null
        defaultAddressShouldBeFound("createdBy.specified=true");

        // Get all the addressList where createdBy is null
        defaultAddressShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdBy contains DEFAULT_CREATED_BY
        defaultAddressShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the addressList where createdBy contains UPDATED_CREATED_BY
        defaultAddressShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdBy does not contain DEFAULT_CREATED_BY
        defaultAddressShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the addressList where createdBy does not contain UPDATED_CREATED_BY
        defaultAddressShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdOn equals to DEFAULT_CREATED_ON
        defaultAddressShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the addressList where createdOn equals to UPDATED_CREATED_ON
        defaultAddressShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultAddressShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the addressList where createdOn equals to UPDATED_CREATED_ON
        defaultAddressShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdOn is not null
        defaultAddressShouldBeFound("createdOn.specified=true");

        // Get all the addressList where createdOn is null
        defaultAddressShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where isDeleted equals to DEFAULT_IS_DELETED
        defaultAddressShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the addressList where isDeleted equals to UPDATED_IS_DELETED
        defaultAddressShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllAddressesByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultAddressShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the addressList where isDeleted equals to UPDATED_IS_DELETED
        defaultAddressShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllAddressesByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where isDeleted is not null
        defaultAddressShouldBeFound("isDeleted.specified=true");

        // Get all the addressList where isDeleted is null
        defaultAddressShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultAddressShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the addressList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultAddressShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultAddressShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the addressList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultAddressShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField1 is not null
        defaultAddressShouldBeFound("freeField1.specified=true");

        // Get all the addressList where freeField1 is null
        defaultAddressShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultAddressShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the addressList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultAddressShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultAddressShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the addressList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultAddressShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultAddressShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the addressList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultAddressShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultAddressShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the addressList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultAddressShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField2 is not null
        defaultAddressShouldBeFound("freeField2.specified=true");

        // Get all the addressList where freeField2 is null
        defaultAddressShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultAddressShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the addressList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultAddressShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultAddressShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the addressList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultAddressShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultAddressShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the addressList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultAddressShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultAddressShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the addressList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultAddressShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField3 is not null
        defaultAddressShouldBeFound("freeField3.specified=true");

        // Get all the addressList where freeField3 is null
        defaultAddressShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultAddressShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the addressList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultAddressShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultAddressShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the addressList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultAddressShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultAddressShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the addressList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultAddressShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultAddressShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the addressList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultAddressShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField4 is not null
        defaultAddressShouldBeFound("freeField4.specified=true");

        // Get all the addressList where freeField4 is null
        defaultAddressShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultAddressShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the addressList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultAddressShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultAddressShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the addressList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultAddressShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField5IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField5 equals to DEFAULT_FREE_FIELD_5
        defaultAddressShouldBeFound("freeField5.equals=" + DEFAULT_FREE_FIELD_5);

        // Get all the addressList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultAddressShouldNotBeFound("freeField5.equals=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField5IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField5 in DEFAULT_FREE_FIELD_5 or UPDATED_FREE_FIELD_5
        defaultAddressShouldBeFound("freeField5.in=" + DEFAULT_FREE_FIELD_5 + "," + UPDATED_FREE_FIELD_5);

        // Get all the addressList where freeField5 equals to UPDATED_FREE_FIELD_5
        defaultAddressShouldNotBeFound("freeField5.in=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField5IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField5 is not null
        defaultAddressShouldBeFound("freeField5.specified=true");

        // Get all the addressList where freeField5 is null
        defaultAddressShouldNotBeFound("freeField5.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField5ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField5 contains DEFAULT_FREE_FIELD_5
        defaultAddressShouldBeFound("freeField5.contains=" + DEFAULT_FREE_FIELD_5);

        // Get all the addressList where freeField5 contains UPDATED_FREE_FIELD_5
        defaultAddressShouldNotBeFound("freeField5.contains=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField5NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField5 does not contain DEFAULT_FREE_FIELD_5
        defaultAddressShouldNotBeFound("freeField5.doesNotContain=" + DEFAULT_FREE_FIELD_5);

        // Get all the addressList where freeField5 does not contain UPDATED_FREE_FIELD_5
        defaultAddressShouldBeFound("freeField5.doesNotContain=" + UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void getAllAddressesByStateIsEqualToSomething() throws Exception {
        State state;
        if (TestUtil.findAll(em, State.class).isEmpty()) {
            addressRepository.saveAndFlush(address);
            state = StateResourceIT.createEntity(em);
        } else {
            state = TestUtil.findAll(em, State.class).get(0);
        }
        em.persist(state);
        em.flush();
        address.setState(state);
        addressRepository.saveAndFlush(address);
        Long stateId = state.getId();

        // Get all the addressList where state equals to stateId
        defaultAddressShouldBeFound("stateId.equals=" + stateId);

        // Get all the addressList where state equals to (stateId + 1)
        defaultAddressShouldNotBeFound("stateId.equals=" + (stateId + 1));
    }

    @Test
    @Transactional
    void getAllAddressesByDistrictIsEqualToSomething() throws Exception {
        District district;
        if (TestUtil.findAll(em, District.class).isEmpty()) {
            addressRepository.saveAndFlush(address);
            district = DistrictResourceIT.createEntity(em);
        } else {
            district = TestUtil.findAll(em, District.class).get(0);
        }
        em.persist(district);
        em.flush();
        address.setDistrict(district);
        addressRepository.saveAndFlush(address);
        Long districtId = district.getId();

        // Get all the addressList where district equals to districtId
        defaultAddressShouldBeFound("districtId.equals=" + districtId);

        // Get all the addressList where district equals to (districtId + 1)
        defaultAddressShouldNotBeFound("districtId.equals=" + (districtId + 1));
    }

    @Test
    @Transactional
    void getAllAddressesByTalukaIsEqualToSomething() throws Exception {
        Taluka taluka;
        if (TestUtil.findAll(em, Taluka.class).isEmpty()) {
            addressRepository.saveAndFlush(address);
            taluka = TalukaResourceIT.createEntity(em);
        } else {
            taluka = TestUtil.findAll(em, Taluka.class).get(0);
        }
        em.persist(taluka);
        em.flush();
        address.setTaluka(taluka);
        addressRepository.saveAndFlush(address);
        Long talukaId = taluka.getId();

        // Get all the addressList where taluka equals to talukaId
        defaultAddressShouldBeFound("talukaId.equals=" + talukaId);

        // Get all the addressList where taluka equals to (talukaId + 1)
        defaultAddressShouldNotBeFound("talukaId.equals=" + (talukaId + 1));
    }

    @Test
    @Transactional
    void getAllAddressesByCityIsEqualToSomething() throws Exception {
        City city;
        if (TestUtil.findAll(em, City.class).isEmpty()) {
            addressRepository.saveAndFlush(address);
            city = CityResourceIT.createEntity(em);
        } else {
            city = TestUtil.findAll(em, City.class).get(0);
        }
        em.persist(city);
        em.flush();
        //     address.setCity(city);
        addressRepository.saveAndFlush(address);
        Long cityId = city.getId();

        // Get all the addressList where city equals to cityId
        defaultAddressShouldBeFound("cityId.equals=" + cityId);

        // Get all the addressList where city equals to (cityId + 1)
        defaultAddressShouldNotBeFound("cityId.equals=" + (cityId + 1));
    }

    @Test
    @Transactional
    void getAllAddressesByMemberIsEqualToSomething() throws Exception {
        Member member;
        if (TestUtil.findAll(em, Member.class).isEmpty()) {
            addressRepository.saveAndFlush(address);
            member = MemberResourceIT.createEntity(em);
        } else {
            member = TestUtil.findAll(em, Member.class).get(0);
        }
        em.persist(member);
        em.flush();
        address.setMember(member);
        addressRepository.saveAndFlush(address);
        Long memberId = member.getId();

        // Get all the addressList where member equals to memberId
        defaultAddressShouldBeFound("memberId.equals=" + memberId);

        // Get all the addressList where member equals to (memberId + 1)
        defaultAddressShouldNotBeFound("memberId.equals=" + (memberId + 1));
    }

    @Test
    @Transactional
    void getAllAddressesBySecurityUserIsEqualToSomething() throws Exception {
        SecurityUser securityUser;
        if (TestUtil.findAll(em, SecurityUser.class).isEmpty()) {
            addressRepository.saveAndFlush(address);
            securityUser = SecurityUserResourceIT.createEntity(em);
        } else {
            securityUser = TestUtil.findAll(em, SecurityUser.class).get(0);
        }
        em.persist(securityUser);
        em.flush();
        //      address.setSecurityUser(securityUser);
        addressRepository.saveAndFlush(address);
        Long securityUserId = securityUser.getId();

        // Get all the addressList where securityUser equals to securityUserId
        defaultAddressShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the addressList where securityUser equals to (securityUserId + 1)
        defaultAddressShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAddressShouldBeFound(String filter) throws Exception {
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].addrStatus").value(hasItem(DEFAULT_ADDR_STATUS.toString())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1)))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2)))
            .andExpect(jsonPath("$.[*].addressLine3").value(hasItem(DEFAULT_ADDRESS_LINE_3)))
            .andExpect(jsonPath("$.[*].landMark").value(hasItem(DEFAULT_LAND_MARK)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].mapNevUrl").value(hasItem(DEFAULT_MAP_NEV_URL)))
            .andExpect(jsonPath("$.[*].isPrefferedAdd").value(hasItem(DEFAULT_IS_PREFFERED_ADD.booleanValue())))
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
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAddressShouldNotBeFound(String filter) throws Exception {
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).get();
        // Disconnect from session so that the updates on updatedAddress are not directly saved in db
        em.detach(updatedAddress);
        updatedAddress
            .addrStatus(UPDATED_ADDR_STATUS)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .addressLine3(UPDATED_ADDRESS_LINE_3)
            .landMark(UPDATED_LAND_MARK)
            .pincode(UPDATED_PINCODE)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .mapNevUrl(UPDATED_MAP_NEV_URL)
            .isPrefferedAdd(UPDATED_IS_PREFFERED_ADD)
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
        AddressDTO addressDTO = addressMapper.toDto(updatedAddress);

        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getAddrStatus()).isEqualTo(UPDATED_ADDR_STATUS);
        assertThat(testAddress.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testAddress.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testAddress.getAddressLine3()).isEqualTo(UPDATED_ADDRESS_LINE_3);
        assertThat(testAddress.getLandMark()).isEqualTo(UPDATED_LAND_MARK);
        assertThat(testAddress.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testAddress.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAddress.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testAddress.getMapNevUrl()).isEqualTo(UPDATED_MAP_NEV_URL);
        assertThat(testAddress.getIsPrefferedAdd()).isEqualTo(UPDATED_IS_PREFFERED_ADD);
        assertThat(testAddress.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAddress.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAddress.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAddress.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testAddress.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testAddress.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testAddress.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testAddress.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testAddress.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void putNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .addressLine3(UPDATED_ADDRESS_LINE_3)
            .landMark(UPDATED_LAND_MARK)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddress))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getAddrStatus()).isEqualTo(DEFAULT_ADDR_STATUS);
        assertThat(testAddress.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testAddress.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testAddress.getAddressLine3()).isEqualTo(UPDATED_ADDRESS_LINE_3);
        assertThat(testAddress.getLandMark()).isEqualTo(UPDATED_LAND_MARK);
        assertThat(testAddress.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testAddress.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAddress.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testAddress.getMapNevUrl()).isEqualTo(DEFAULT_MAP_NEV_URL);
        assertThat(testAddress.getIsPrefferedAdd()).isEqualTo(DEFAULT_IS_PREFFERED_ADD);
        assertThat(testAddress.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testAddress.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAddress.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAddress.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testAddress.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testAddress.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testAddress.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testAddress.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testAddress.getFreeField5()).isEqualTo(DEFAULT_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void fullUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress
            .addrStatus(UPDATED_ADDR_STATUS)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .addressLine3(UPDATED_ADDRESS_LINE_3)
            .landMark(UPDATED_LAND_MARK)
            .pincode(UPDATED_PINCODE)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .mapNevUrl(UPDATED_MAP_NEV_URL)
            .isPrefferedAdd(UPDATED_IS_PREFFERED_ADD)
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

        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddress))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getAddrStatus()).isEqualTo(UPDATED_ADDR_STATUS);
        assertThat(testAddress.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testAddress.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testAddress.getAddressLine3()).isEqualTo(UPDATED_ADDRESS_LINE_3);
        assertThat(testAddress.getLandMark()).isEqualTo(UPDATED_LAND_MARK);
        assertThat(testAddress.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testAddress.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAddress.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testAddress.getMapNevUrl()).isEqualTo(UPDATED_MAP_NEV_URL);
        assertThat(testAddress.getIsPrefferedAdd()).isEqualTo(UPDATED_IS_PREFFERED_ADD);
        assertThat(testAddress.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAddress.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAddress.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAddress.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testAddress.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testAddress.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testAddress.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testAddress.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testAddress.getFreeField5()).isEqualTo(UPDATED_FREE_FIELD_5);
    }

    @Test
    @Transactional
    void patchNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Delete the address
        restAddressMockMvc
            .perform(delete(ENTITY_API_URL_ID, address.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
