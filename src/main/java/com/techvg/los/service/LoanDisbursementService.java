package com.techvg.los.service;

import com.techvg.los.domain.LoanDisbursement;
import com.techvg.los.repository.LoanDisbursementRepository;
import com.techvg.los.service.dto.LoanDisbursementDTO;
import com.techvg.los.service.mapper.LoanDisbursementMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LoanDisbursement}.
 */
@Service
@Transactional
public class LoanDisbursementService {

    private final Logger log = LoggerFactory.getLogger(LoanDisbursementService.class);

    private final LoanDisbursementRepository loanDisbursementRepository;

    private final LoanDisbursementMapper loanDisbursementMapper;

    public LoanDisbursementService(LoanDisbursementRepository loanDisbursementRepository, LoanDisbursementMapper loanDisbursementMapper) {
        this.loanDisbursementRepository = loanDisbursementRepository;
        this.loanDisbursementMapper = loanDisbursementMapper;
    }

    /**
     * Save a loanDisbursement.
     *
     * @param loanDisbursementDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanDisbursementDTO save(LoanDisbursementDTO loanDisbursementDTO) {
        log.debug("Request to save LoanDisbursement : {}", loanDisbursementDTO);
        LoanDisbursement loanDisbursement = loanDisbursementMapper.toEntity(loanDisbursementDTO);
        loanDisbursement = loanDisbursementRepository.save(loanDisbursement);
        return loanDisbursementMapper.toDto(loanDisbursement);
    }

    /**
     * Update a loanDisbursement.
     *
     * @param loanDisbursementDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanDisbursementDTO update(LoanDisbursementDTO loanDisbursementDTO) {
        log.debug("Request to update LoanDisbursement : {}", loanDisbursementDTO);
        LoanDisbursement loanDisbursement = loanDisbursementMapper.toEntity(loanDisbursementDTO);
        loanDisbursement = loanDisbursementRepository.save(loanDisbursement);
        return loanDisbursementMapper.toDto(loanDisbursement);
    }

    /**
     * Partially update a loanDisbursement.
     *
     * @param loanDisbursementDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LoanDisbursementDTO> partialUpdate(LoanDisbursementDTO loanDisbursementDTO) {
        log.debug("Request to partially update LoanDisbursement : {}", loanDisbursementDTO);

        return loanDisbursementRepository
            .findById(loanDisbursementDTO.getId())
            .map(existingLoanDisbursement -> {
                loanDisbursementMapper.partialUpdate(existingLoanDisbursement, loanDisbursementDTO);

                return existingLoanDisbursement;
            })
            .map(loanDisbursementRepository::save)
            .map(loanDisbursementMapper::toDto);
    }

    /**
     * Get all the loanDisbursements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanDisbursementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoanDisbursements");
        return loanDisbursementRepository.findAll(pageable).map(loanDisbursementMapper::toDto);
    }

    /**
     * Get one loanDisbursement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LoanDisbursementDTO> findOne(Long id) {
        log.debug("Request to get LoanDisbursement : {}", id);
        return loanDisbursementRepository.findById(id).map(loanDisbursementMapper::toDto);
    }

    /**
     * Delete the loanDisbursement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LoanDisbursement : {}", id);
        loanDisbursementRepository.deleteById(id);
    }
}
