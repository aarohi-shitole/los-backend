package com.techvg.los.service;

import com.techvg.los.domain.SchemesDetails;
import com.techvg.los.repository.SchemesDetailsRepository;
import com.techvg.los.service.dto.SchemesDetailsDTO;
import com.techvg.los.service.mapper.SchemesDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SchemesDetails}.
 */
@Service
@Transactional
public class SchemesDetailsService {

    private final Logger log = LoggerFactory.getLogger(SchemesDetailsService.class);

    private final SchemesDetailsRepository schemesDetailsRepository;

    private final SchemesDetailsMapper schemesDetailsMapper;

    public SchemesDetailsService(SchemesDetailsRepository schemesDetailsRepository, SchemesDetailsMapper schemesDetailsMapper) {
        this.schemesDetailsRepository = schemesDetailsRepository;
        this.schemesDetailsMapper = schemesDetailsMapper;
    }

    /**
     * Save a schemesDetails.
     *
     * @param schemesDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public SchemesDetailsDTO save(SchemesDetailsDTO schemesDetailsDTO) {
        log.debug("Request to save SchemesDetails : {}", schemesDetailsDTO);
        SchemesDetails schemesDetails = schemesDetailsMapper.toEntity(schemesDetailsDTO);
        schemesDetails = schemesDetailsRepository.save(schemesDetails);
        return schemesDetailsMapper.toDto(schemesDetails);
    }

    /**
     * Update a schemesDetails.
     *
     * @param schemesDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public SchemesDetailsDTO update(SchemesDetailsDTO schemesDetailsDTO) {
        log.debug("Request to update SchemesDetails : {}", schemesDetailsDTO);
        SchemesDetails schemesDetails = schemesDetailsMapper.toEntity(schemesDetailsDTO);
        schemesDetails = schemesDetailsRepository.save(schemesDetails);
        return schemesDetailsMapper.toDto(schemesDetails);
    }

    /**
     * Partially update a schemesDetails.
     *
     * @param schemesDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SchemesDetailsDTO> partialUpdate(SchemesDetailsDTO schemesDetailsDTO) {
        log.debug("Request to partially update SchemesDetails : {}", schemesDetailsDTO);

        return schemesDetailsRepository
            .findById(schemesDetailsDTO.getId())
            .map(existingSchemesDetails -> {
                schemesDetailsMapper.partialUpdate(existingSchemesDetails, schemesDetailsDTO);

                return existingSchemesDetails;
            })
            .map(schemesDetailsRepository::save)
            .map(schemesDetailsMapper::toDto);
    }

    /**
     * Get all the schemesDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SchemesDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SchemesDetails");
        return schemesDetailsRepository.findAll(pageable).map(schemesDetailsMapper::toDto);
    }

    /**
     * Get one schemesDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SchemesDetailsDTO> findOne(Long id) {
        log.debug("Request to get SchemesDetails : {}", id);
        return schemesDetailsRepository.findById(id).map(schemesDetailsMapper::toDto);
    }

    /**
     * Delete the schemesDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SchemesDetails : {}", id);
        schemesDetailsRepository.deleteById(id);
    }
}
