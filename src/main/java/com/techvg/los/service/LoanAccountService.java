package com.techvg.los.service;

import com.techvg.los.domain.LoanAccount;
import com.techvg.los.repository.LoanAccountRepository;
import com.techvg.los.service.dto.LoanAccountDTO;
import com.techvg.los.service.mapper.LoanAccountMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LoanAccount}.
 */
@Service
@Transactional
public class LoanAccountService {

    private final Logger log = LoggerFactory.getLogger(LoanAccountService.class);

    private final LoanAccountRepository loanAccountRepository;

    private final LoanAccountMapper loanAccountMapper;

    public LoanAccountService(LoanAccountRepository loanAccountRepository, LoanAccountMapper loanAccountMapper) {
        this.loanAccountRepository = loanAccountRepository;
        this.loanAccountMapper = loanAccountMapper;
    }

    /**
     * Save a loanAccount.
     *
     * @param loanAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanAccountDTO save(LoanAccountDTO loanAccountDTO) {
        log.debug("Request to save LoanAccount : {}", loanAccountDTO);
        LoanAccount loanAccount = loanAccountMapper.toEntity(loanAccountDTO);
        loanAccount = loanAccountRepository.save(loanAccount);
        return loanAccountMapper.toDto(loanAccount);
    }

    /**
     * Update a loanAccount.
     *
     * @param loanAccountDTO the entity to save.
     * @return the persisted entity.
     */
    public LoanAccountDTO update(LoanAccountDTO loanAccountDTO) {
        log.debug("Request to update LoanAccount : {}", loanAccountDTO);
        LoanAccount loanAccount = loanAccountMapper.toEntity(loanAccountDTO);
        loanAccount = loanAccountRepository.save(loanAccount);
        return loanAccountMapper.toDto(loanAccount);
    }

    /**
     * Partially update a loanAccount.
     *
     * @param loanAccountDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LoanAccountDTO> partialUpdate(LoanAccountDTO loanAccountDTO) {
        log.debug("Request to partially update LoanAccount : {}", loanAccountDTO);

        return loanAccountRepository
            .findById(loanAccountDTO.getId())
            .map(existingLoanAccount -> {
                loanAccountMapper.partialUpdate(existingLoanAccount, loanAccountDTO);

                return existingLoanAccount;
            })
            .map(loanAccountRepository::save)
            .map(loanAccountMapper::toDto);
    }

    /**
     * Get all the loanAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LoanAccounts");
        return loanAccountRepository.findAll(pageable).map(loanAccountMapper::toDto);
    }

    /**
     * Get one loanAccount by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LoanAccountDTO> findOne(Long id) {
        log.debug("Request to get LoanAccount : {}", id);
        return loanAccountRepository.findById(id).map(loanAccountMapper::toDto);
    }

    /**
     * Delete the loanAccount by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LoanAccount : {}", id);
        loanAccountRepository.deleteById(id);
    }
}
