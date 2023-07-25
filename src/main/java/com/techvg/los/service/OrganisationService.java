package com.techvg.los.service;

import com.techvg.los.domain.Branch;
import com.techvg.los.domain.Organisation;
import com.techvg.los.domain.enumeration.BranchType;
import com.techvg.los.repository.AddressRepository;
import com.techvg.los.repository.OrganisationRepository;
import com.techvg.los.service.dto.AddressDTO;
import com.techvg.los.service.dto.BranchDTO;
import com.techvg.los.service.dto.OrganisationDTO;
import com.techvg.los.service.mapper.OrganisationMapper;
import com.techvg.los.web.rest.errors.BadRequestAlertException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Organisation}.
 */
@Service
@Transactional
public class OrganisationService {

    private final Logger log = LoggerFactory.getLogger(OrganisationService.class);

    private final OrganisationRepository organisationRepository;

    private final OrganisationMapper organisationMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;

    public OrganisationService(OrganisationRepository organisationRepository, OrganisationMapper organisationMapper) {
        this.organisationRepository = organisationRepository;
        this.organisationMapper = organisationMapper;
    }

    /**
     * Save a organisation.
     *
     * @param organisationDTO the entity to save.
     * @return the persisted entity.
     */
    public OrganisationDTO save(OrganisationDTO organisationDTO) {
        log.debug("Request to save Organisation : {}", organisationDTO);
        // ----------------Save address of organization
        // -------------------------------------------
        if (organisationDTO.getAddress() != null) {
            AddressDTO addressDTO = organisationDTO.getAddress();
            if (addressDTO.getId() != null) {
                throw new BadRequestAlertException("A new address cannot already have an ID", "Address", "idexists");
            }
            AddressDTO addressObj = addressService.save(addressDTO);
            organisationDTO.setAddress(addressObj);
        }
        // -----------------------------------------------------------------------
        Organisation organisation = organisationMapper.toEntity(organisationDTO);
        organisation = organisationRepository.save(organisation);
        Long organisationId = organisation.getId();

        // ------------ Update SequenceGenrator table after saving organization----
        int next_val_member = 1;
        organisationRepository.updateSequenceGenrator(next_val_member, organisationId);

        return organisationMapper.toDto(organisation);
    }

    /**
     * Update a organisation.
     *
     * @param organisationDTO the entity to save.
     * @return the persisted entity.
     */
    public OrganisationDTO update(OrganisationDTO organisationDTO) {
        log.debug("Request to update Organisation : {}", organisationDTO);
        // ----------------update address of Organisation
        // -------------------------------------------
        if (organisationDTO.getAddress() != null) {
            AddressDTO addressDTO = organisationDTO.getAddress();
            if (addressDTO.getId() == null) {
                throw new BadRequestAlertException("Invalid id", "Address", "idnull");
            }
            if (!addressRepository.existsById(addressDTO.getId())) {
                throw new BadRequestAlertException("Entity not found", "Address", "idnotfound");
            }
            AddressDTO addressObj = addressService.update(addressDTO);
        }
        // -----------------------------------------------------------------------
        Organisation organisation = organisationMapper.toEntity(organisationDTO);
        organisation = organisationRepository.save(organisation);
        return organisationMapper.toDto(organisation);
    }

    /**
     * Partially update a organisation.
     *
     * @param organisationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrganisationDTO> partialUpdate(OrganisationDTO organisationDTO) {
        log.debug("Request to partially update Organisation : {}", organisationDTO);

        return organisationRepository
            .findById(organisationDTO.getId())
            .map(existingOrganisation -> {
                organisationMapper.partialUpdate(existingOrganisation, organisationDTO);

                return existingOrganisation;
            })
            .map(organisationRepository::save)
            .map(organisationMapper::toDto);
    }

    /**
     * Get all the organisations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganisationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organisations");
        return organisationRepository.findAll(pageable).map(organisationMapper::toDto);
    }

    /**
     * Get one organisation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrganisationDTO> findOne(Long id) {
        log.debug("Request to get Organisation : {}", id);
        return organisationRepository.findById(id).map(organisationMapper::toDto);
    }

    /**
     * Delete the organisation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Organisation : {}", id);
        organisationRepository.deleteById(id);
    }
}
