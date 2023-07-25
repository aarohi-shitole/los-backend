package com.techvg.los.service;

import com.techvg.los.domain.EnquiryDetails;
import com.techvg.los.repository.EnquiryDetailsRepository;
import com.techvg.los.service.dto.EnquiryDetailsDTO;
import com.techvg.los.service.mapper.EnquiryDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EnquiryDetails}.
 */
@Service
@Transactional
public class EnquiryDetailsService {

    private final Logger log = LoggerFactory.getLogger(EnquiryDetailsService.class);

    private final EnquiryDetailsRepository enquiryDetailsRepository;

    private final EnquiryDetailsMapper enquiryDetailsMapper;

    public EnquiryDetailsService(EnquiryDetailsRepository enquiryDetailsRepository, EnquiryDetailsMapper enquiryDetailsMapper) {
        this.enquiryDetailsRepository = enquiryDetailsRepository;
        this.enquiryDetailsMapper = enquiryDetailsMapper;
    }

    /**
     * Save a enquiryDetails.
     *
     * @param enquiryDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public EnquiryDetailsDTO save(EnquiryDetailsDTO enquiryDetailsDTO) {
        log.debug("Request to save EnquiryDetails : {}", enquiryDetailsDTO);
        EnquiryDetails enquiryDetails = enquiryDetailsMapper.toEntity(enquiryDetailsDTO);
        enquiryDetails = enquiryDetailsRepository.save(enquiryDetails);
        return enquiryDetailsMapper.toDto(enquiryDetails);
    }

    /**
     * Update a enquiryDetails.
     *
     * @param enquiryDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public EnquiryDetailsDTO update(EnquiryDetailsDTO enquiryDetailsDTO) {
        log.debug("Request to update EnquiryDetails : {}", enquiryDetailsDTO);
        EnquiryDetails enquiryDetails = enquiryDetailsMapper.toEntity(enquiryDetailsDTO);
        enquiryDetails = enquiryDetailsRepository.save(enquiryDetails);
        return enquiryDetailsMapper.toDto(enquiryDetails);
    }

    /**
     * Partially update a enquiryDetails.
     *
     * @param enquiryDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EnquiryDetailsDTO> partialUpdate(EnquiryDetailsDTO enquiryDetailsDTO) {
        log.debug("Request to partially update EnquiryDetails : {}", enquiryDetailsDTO);

        return enquiryDetailsRepository
            .findById(enquiryDetailsDTO.getId())
            .map(existingEnquiryDetails -> {
                enquiryDetailsMapper.partialUpdate(existingEnquiryDetails, enquiryDetailsDTO);

                return existingEnquiryDetails;
            })
            .map(enquiryDetailsRepository::save)
            .map(enquiryDetailsMapper::toDto);
    }

    /**
     * Get all the enquiryDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EnquiryDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EnquiryDetails");
        return enquiryDetailsRepository.findAll(pageable).map(enquiryDetailsMapper::toDto);
    }

    /**
     * Get one enquiryDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnquiryDetailsDTO> findOne(Long id) {
        log.debug("Request to get EnquiryDetails : {}", id);
        return enquiryDetailsRepository.findById(id).map(enquiryDetailsMapper::toDto);
    }

    /**
     * Delete the enquiryDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EnquiryDetails : {}", id);
        enquiryDetailsRepository.deleteById(id);
    }
}
