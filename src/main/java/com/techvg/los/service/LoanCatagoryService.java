package com.techvg.los.service;

import com.techvg.los.domain.LoanCatagory;
import com.techvg.los.repository.LoanCatagoryRepository;
import com.techvg.los.service.dto.LoanCatagoryDTO;
import com.techvg.los.service.mapper.LoanCatagoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LoanCatagory}.
 */
@Service
@Transactional
public class LoanCatagoryService {

    private final Logger log = LoggerFactory.getLogger(LoanCatagoryService.class);

    private final LoanCatagoryRepository loanCatagoryRepository;

    private final LoanCatagoryMapper loanCatagoryMapper;

    public LoanCatagoryService(LoanCatagoryRepository loanCatagoryRepository, LoanCatagoryMapper loanCatagoryMapper) {
        this.loanCatagoryRepository = loanCatagoryRepository;
        this.loanCatagoryMapper = loanCatagoryMapper;
    }

    /**
     * Save a loanCatagory.
     *
     * @param loanCatagoryDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanCatagoryDTO save(LoanCatagoryDTO loanCatagoryDTO) {
        log.debug("Request to save LoanCatagory : {}", loanCatagoryDTO);
        LoanCatagory loanCatagory = loanCatagoryMapper.toEntity(loanCatagoryDTO);
        loanCatagory = loanCatagoryRepository.save(loanCatagory);
        return loanCatagoryMapper.toDto(loanCatagory);
    }

    /**
     * Update a loanCatagory.
     *
     * @param loanCatagoryDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanCatagoryDTO update(LoanCatagoryDTO loanCatagoryDTO) {
        log.debug("Request to update LoanCatagory : {}", loanCatagoryDTO);
        LoanCatagory loanCatagory = loanCatagoryMapper.toEntity(loanCatagoryDTO);
        loanCatagory = loanCatagoryRepository.save(loanCatagory);
        return loanCatagoryMapper.toDto(loanCatagory);
    }

    /**
     * Partially update a loanCatagory.
     *
     * @param loanCatagoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LoanCatagoryDTO> partialUpdate(LoanCatagoryDTO loanCatagoryDTO) {
        log.debug("Request to partially update LoanCatagory : {}", loanCatagoryDTO);

        return loanCatagoryRepository
            .findById(loanCatagoryDTO.getId())
            .map(existingLoanCatagory -> {
                loanCatagoryMapper.partialUpdate(existingLoanCatagory, loanCatagoryDTO);

                return existingLoanCatagory;
            })
            .map(loanCatagoryRepository::save)
            .map(loanCatagoryMapper::toDto);
    }

    /**
     * Get all the loanCatagories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanCatagoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoanCatagories");
        return loanCatagoryRepository.findAll(pageable).map(loanCatagoryMapper::toDto);
    }

    /**
     * Get one loanCatagory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LoanCatagoryDTO> findOne(Long id) {
        log.debug("Request to get LoanCatagory : {}", id);
        return loanCatagoryRepository.findById(id).map(loanCatagoryMapper::toDto);
    }

    /**
     * Delete the loanCatagory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LoanCatagory : {}", id);
        loanCatagoryRepository.deleteById(id);
    }
}
