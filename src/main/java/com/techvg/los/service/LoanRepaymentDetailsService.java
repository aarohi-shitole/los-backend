package com.techvg.los.service;

import com.techvg.los.domain.LoanRepaymentDetails;
import com.techvg.los.repository.LoanRepaymentDetailsRepository;
import com.techvg.los.service.dto.LoanRepaymentDetailsDTO;
import com.techvg.los.service.mapper.LoanRepaymentDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LoanRepaymentDetails}.
 */
@Service
@Transactional
public class LoanRepaymentDetailsService {

    private final Logger log = LoggerFactory.getLogger(LoanRepaymentDetailsService.class);

    private final LoanRepaymentDetailsRepository loanRepaymentDetailsRepository;

    private final LoanRepaymentDetailsMapper loanRepaymentDetailsMapper;

    public LoanRepaymentDetailsService(
        LoanRepaymentDetailsRepository loanRepaymentDetailsRepository,
        LoanRepaymentDetailsMapper loanRepaymentDetailsMapper
    ) {
        this.loanRepaymentDetailsRepository = loanRepaymentDetailsRepository;
        this.loanRepaymentDetailsMapper = loanRepaymentDetailsMapper;
    }

    /**
     * Save a loanRepaymentDetails.
     *
     * @param loanRepaymentDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanRepaymentDetailsDTO save(LoanRepaymentDetailsDTO loanRepaymentDetailsDTO) {
        log.debug("Request to save LoanRepaymentDetails : {}", loanRepaymentDetailsDTO);
        LoanRepaymentDetails loanRepaymentDetails = loanRepaymentDetailsMapper.toEntity(loanRepaymentDetailsDTO);
        loanRepaymentDetails = loanRepaymentDetailsRepository.save(loanRepaymentDetails);
        return loanRepaymentDetailsMapper.toDto(loanRepaymentDetails);
    }

    /**
     * Update a loanRepaymentDetails.
     *
     * @param loanRepaymentDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanRepaymentDetailsDTO update(LoanRepaymentDetailsDTO loanRepaymentDetailsDTO) {
        log.debug("Request to update LoanRepaymentDetails : {}", loanRepaymentDetailsDTO);
        LoanRepaymentDetails loanRepaymentDetails = loanRepaymentDetailsMapper.toEntity(loanRepaymentDetailsDTO);
        loanRepaymentDetails = loanRepaymentDetailsRepository.save(loanRepaymentDetails);
        return loanRepaymentDetailsMapper.toDto(loanRepaymentDetails);
    }

    /**
     * Partially update a loanRepaymentDetails.
     *
     * @param loanRepaymentDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LoanRepaymentDetailsDTO> partialUpdate(LoanRepaymentDetailsDTO loanRepaymentDetailsDTO) {
        log.debug("Request to partially update LoanRepaymentDetails : {}", loanRepaymentDetailsDTO);

        return loanRepaymentDetailsRepository
            .findById(loanRepaymentDetailsDTO.getId())
            .map(existingLoanRepaymentDetails -> {
                loanRepaymentDetailsMapper.partialUpdate(existingLoanRepaymentDetails, loanRepaymentDetailsDTO);

                return existingLoanRepaymentDetails;
            })
            .map(loanRepaymentDetailsRepository::save)
            .map(loanRepaymentDetailsMapper::toDto);
    }

    /**
     * Get all the loanRepaymentDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanRepaymentDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoanRepaymentDetails");
        return loanRepaymentDetailsRepository.findAll(pageable).map(loanRepaymentDetailsMapper::toDto);
    }

    /**
     * Get one loanRepaymentDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LoanRepaymentDetailsDTO> findOne(Long id) {
        log.debug("Request to get LoanRepaymentDetails : {}", id);
        return loanRepaymentDetailsRepository.findById(id).map(loanRepaymentDetailsMapper::toDto);
    }

    /**
     * Delete the loanRepaymentDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LoanRepaymentDetails : {}", id);
        loanRepaymentDetailsRepository.deleteById(id);
    }
}
