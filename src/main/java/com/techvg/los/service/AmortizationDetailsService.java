package com.techvg.los.service;

import com.techvg.los.domain.AmortizationDetails;
import com.techvg.los.repository.AmortizationDetailsRepository;
import com.techvg.los.service.dto.AmortizationDetailsDTO;
import com.techvg.los.service.mapper.AmortizationDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AmortizationDetails}.
 */
@Service
@Transactional
public class AmortizationDetailsService {

    private final Logger log = LoggerFactory.getLogger(AmortizationDetailsService.class);

    private final AmortizationDetailsRepository amortizationDetailsRepository;

    private final AmortizationDetailsMapper amortizationDetailsMapper;

    public AmortizationDetailsService(
        AmortizationDetailsRepository amortizationDetailsRepository,
        AmortizationDetailsMapper amortizationDetailsMapper
    ) {
        this.amortizationDetailsRepository = amortizationDetailsRepository;
        this.amortizationDetailsMapper = amortizationDetailsMapper;
    }

    /**
     * Save a amortizationDetails.
     *
     * @param amortizationDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public AmortizationDetailsDTO save(AmortizationDetailsDTO amortizationDetailsDTO) {
        log.debug("Request to save AmortizationDetails : {}", amortizationDetailsDTO);
        AmortizationDetails amortizationDetails = amortizationDetailsMapper.toEntity(amortizationDetailsDTO);
        amortizationDetails = amortizationDetailsRepository.save(amortizationDetails);
        return amortizationDetailsMapper.toDto(amortizationDetails);
    }

    /**
     * Update a amortizationDetails.
     *
     * @param amortizationDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public AmortizationDetailsDTO update(AmortizationDetailsDTO amortizationDetailsDTO) {
        log.debug("Request to update AmortizationDetails : {}", amortizationDetailsDTO);
        AmortizationDetails amortizationDetails = amortizationDetailsMapper.toEntity(amortizationDetailsDTO);
        amortizationDetails = amortizationDetailsRepository.save(amortizationDetails);
        return amortizationDetailsMapper.toDto(amortizationDetails);
    }

    /**
     * Partially update a amortizationDetails.
     *
     * @param amortizationDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AmortizationDetailsDTO> partialUpdate(AmortizationDetailsDTO amortizationDetailsDTO) {
        log.debug("Request to partially update AmortizationDetails : {}", amortizationDetailsDTO);

        return amortizationDetailsRepository
            .findById(amortizationDetailsDTO.getId())
            .map(existingAmortizationDetails -> {
                amortizationDetailsMapper.partialUpdate(existingAmortizationDetails, amortizationDetailsDTO);

                return existingAmortizationDetails;
            })
            .map(amortizationDetailsRepository::save)
            .map(amortizationDetailsMapper::toDto);
    }

    /**
     * Get all the amortizationDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AmortizationDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AmortizationDetails");
        return amortizationDetailsRepository.findAll(pageable).map(amortizationDetailsMapper::toDto);
    }

    /**
     * Get one amortizationDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AmortizationDetailsDTO> findOne(Long id) {
        log.debug("Request to get AmortizationDetails : {}", id);
        return amortizationDetailsRepository.findById(id).map(amortizationDetailsMapper::toDto);
    }

    /**
     * Delete the amortizationDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AmortizationDetails : {}", id);
        amortizationDetailsRepository.deleteById(id);
    }
}
