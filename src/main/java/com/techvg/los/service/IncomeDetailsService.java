package com.techvg.los.service;

import com.techvg.los.domain.IncomeDetails;
import com.techvg.los.repository.IncomeDetailsRepository;
import com.techvg.los.service.dto.IncomeDetailsDTO;
import com.techvg.los.service.mapper.IncomeDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link IncomeDetails}.
 */
@Service
@Transactional
public class IncomeDetailsService {

    private final Logger log = LoggerFactory.getLogger(IncomeDetailsService.class);

    private final IncomeDetailsRepository incomeDetailsRepository;

    private final IncomeDetailsMapper incomeDetailsMapper;

    public IncomeDetailsService(IncomeDetailsRepository incomeDetailsRepository, IncomeDetailsMapper incomeDetailsMapper) {
        this.incomeDetailsRepository = incomeDetailsRepository;
        this.incomeDetailsMapper = incomeDetailsMapper;
    }

    /**
     * Save a incomeDetails.
     *
     * @param incomeDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public IncomeDetailsDTO save(IncomeDetailsDTO incomeDetailsDTO) {
        log.debug("Request to save IncomeDetails : {}", incomeDetailsDTO);
        IncomeDetails incomeDetails = incomeDetailsMapper.toEntity(incomeDetailsDTO);
        incomeDetails = incomeDetailsRepository.save(incomeDetails);
        return incomeDetailsMapper.toDto(incomeDetails);
    }

    /**
     * Update a incomeDetails.
     *
     * @param incomeDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public IncomeDetailsDTO update(IncomeDetailsDTO incomeDetailsDTO) {
        log.debug("Request to update IncomeDetails : {}", incomeDetailsDTO);
        IncomeDetails incomeDetails = incomeDetailsMapper.toEntity(incomeDetailsDTO);
        incomeDetails = incomeDetailsRepository.save(incomeDetails);
        return incomeDetailsMapper.toDto(incomeDetails);
    }

    /**
     * Partially update a incomeDetails.
     *
     * @param incomeDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<IncomeDetailsDTO> partialUpdate(IncomeDetailsDTO incomeDetailsDTO) {
        log.debug("Request to partially update IncomeDetails : {}", incomeDetailsDTO);

        return incomeDetailsRepository
            .findById(incomeDetailsDTO.getId())
            .map(existingIncomeDetails -> {
                incomeDetailsMapper.partialUpdate(existingIncomeDetails, incomeDetailsDTO);

                return existingIncomeDetails;
            })
            .map(incomeDetailsRepository::save)
            .map(incomeDetailsMapper::toDto);
    }

    /**
     * Get all the incomeDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<IncomeDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IncomeDetails");
        return incomeDetailsRepository.findAll(pageable).map(incomeDetailsMapper::toDto);
    }

    /**
     * Get one incomeDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<IncomeDetailsDTO> findOne(Long id) {
        log.debug("Request to get IncomeDetails : {}", id);
        return incomeDetailsRepository.findById(id).map(incomeDetailsMapper::toDto);
    }

    /**
     * Delete the incomeDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete IncomeDetails : {}", id);
        incomeDetailsRepository.deleteById(id);
    }
}
