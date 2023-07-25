package com.techvg.los.service;

import com.techvg.los.domain.EmployementDetails;
import com.techvg.los.repository.AddressRepository;
import com.techvg.los.repository.EmployementDetailsRepository;
import com.techvg.los.service.criteria.MemberCriteria;
import com.techvg.los.service.dto.AddressDTO;
import com.techvg.los.service.dto.EmployementDetailsDTO;
import com.techvg.los.service.dto.MemberDTO;
import com.techvg.los.service.mapper.EmployementDetailsMapper;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.LongFilter;

/**
 * Service Implementation for managing {@link EmployementDetails}.
 */
@Service
@Transactional
public class EmployementDetailsService {

    private final Logger log = LoggerFactory.getLogger(EmployementDetailsService.class);

    private final EmployementDetailsRepository employementDetailsRepository;

    private final EmployementDetailsMapper employementDetailsMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private MemberQueryService memberQueryService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MemberService memberService;

    public EmployementDetailsService(
        EmployementDetailsRepository employementDetailsRepository,
        EmployementDetailsMapper employementDetailsMapper
    ) {
        this.employementDetailsRepository = employementDetailsRepository;
        this.employementDetailsMapper = employementDetailsMapper;
    }

    /**
     * Save a employementDetails.
     *
     * @param employementDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployementDetailsDTO save(EmployementDetailsDTO employementDetailsDTO) {
        log.debug("Request to save EmployementDetails : {}", employementDetailsDTO);
        // ----------------Save address of EmployementDetails
        // -------------------------------------------
        if (employementDetailsDTO.getAddress() != null) {
            AddressDTO addressDTO = employementDetailsDTO.getAddress();
            if (addressDTO.getId() != null) {
                throw new BadRequestAlertException("A new address cannot already have an ID", "Address", "idexists");
            }
            AddressDTO addressObj = addressService.save(addressDTO);
            employementDetailsDTO.setAddress(addressObj);
        }
        // -----------------------------------------------------------------------
        EmployementDetails employementDetails = employementDetailsMapper.toEntity(employementDetailsDTO);
        employementDetails = employementDetailsRepository.save(employementDetails);

        //--------- Member ocupation--------------
        if (employementDetailsDTO.getMember().getId() != null) {
            MemberCriteria memberCriteria = new MemberCriteria();
            long memberID = employementDetailsDTO.getMember().getId();
            LongFilter memberID1 = new LongFilter();
            memberID1.setEquals(memberID);

            memberCriteria.setId(memberID1);

            List<MemberDTO> memberList = memberQueryService.findByCriteria(memberCriteria);
            if (employementDetailsDTO.getType() != null && memberList != null) {
                for (MemberDTO member : memberList) {
                    member.setOccupation(employementDetailsDTO.getType());
                    memberService.save(member);
                }
            }
        }
        ///---------------------------------------------
        return employementDetailsMapper.toDto(employementDetails);
    }

    /**
     * Update a employementDetails.
     *
     * @param employementDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployementDetailsDTO update(EmployementDetailsDTO employementDetailsDTO) {
        log.debug("Request to update EmployementDetails : {}", employementDetailsDTO);
        // ----------------update address of EmployementDetails -------------------
        if (employementDetailsDTO.getAddress() != null) {
            AddressDTO addressDTO = employementDetailsDTO.getAddress();
            if (addressDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", "Address", "idnull");
            }
            if (!addressRepository.existsById(addressDTO.getId())) {
                throw new BadRequestAlertException("Entity not found", "Address", "idnotfound");
            }
            AddressDTO addressObj = addressService.update(addressDTO);
        }
        // -----------------------------------------------------------------------
        EmployementDetails employementDetails = employementDetailsMapper.toEntity(employementDetailsDTO);
        employementDetails = employementDetailsRepository.save(employementDetails);

        //---------Update Member ocupation--------------
        if (employementDetailsDTO.getMember().getId() != null) {
            Optional<MemberDTO> memberDTO = memberService.findOne(employementDetailsDTO.getMember().getId());
            if (employementDetailsDTO.getType() != null) {
                memberDTO.get().setOccupation(employementDetailsDTO.getType());
                memberService.update(memberDTO.get());
            }
        }
        ///---------------------------------------------
        return employementDetailsMapper.toDto(employementDetails);
    }

    /**
     * Partially update a employementDetails.
     *
     * @param employementDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EmployementDetailsDTO> partialUpdate(EmployementDetailsDTO employementDetailsDTO) {
        log.debug("Request to partially update EmployementDetails : {}", employementDetailsDTO);

        return employementDetailsRepository
            .findById(employementDetailsDTO.getId())
            .map(existingEmployementDetails -> {
                employementDetailsMapper.partialUpdate(existingEmployementDetails, employementDetailsDTO);

                return existingEmployementDetails;
            })
            .map(employementDetailsRepository::save)
            .map(employementDetailsMapper::toDto);
    }

    /**
     * Get all the employementDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployementDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployementDetails");
        return employementDetailsRepository.findAll(pageable).map(employementDetailsMapper::toDto);
    }

    /**
     * Get one employementDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmployementDetailsDTO> findOne(Long id) {
        log.debug("Request to get EmployementDetails : {}", id);
        return employementDetailsRepository.findById(id).map(employementDetailsMapper::toDto);
    }

    /**
     * Delete the employementDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmployementDetails : {}", id);
        employementDetailsRepository.deleteById(id);
    }
}
