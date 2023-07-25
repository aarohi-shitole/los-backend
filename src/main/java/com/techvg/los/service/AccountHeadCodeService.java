package com.techvg.los.service;

import com.techvg.los.domain.AccountHeadCode;
import com.techvg.los.repository.AccountHeadCodeRepository;
import com.techvg.los.service.dto.AccountHeadCodeDTO;
import com.techvg.los.service.mapper.AccountHeadCodeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AccountHeadCode}.
 */
@Service
@Transactional
public class AccountHeadCodeService {

    private final Logger log = LoggerFactory.getLogger(AccountHeadCodeService.class);

    private final AccountHeadCodeRepository accountHeadCodeRepository;

    private final AccountHeadCodeMapper accountHeadCodeMapper;

    public AccountHeadCodeService(AccountHeadCodeRepository accountHeadCodeRepository, AccountHeadCodeMapper accountHeadCodeMapper) {
        this.accountHeadCodeRepository = accountHeadCodeRepository;
        this.accountHeadCodeMapper = accountHeadCodeMapper;
    }

    /**
     * Save a accountHeadCode.
     *
     * @param accountHeadCodeDTO the entity to save.
     * @return the persisted entity.
     */
    public AccountHeadCodeDTO save(AccountHeadCodeDTO accountHeadCodeDTO) {
        log.debug("Request to save AccountHeadCode : {}", accountHeadCodeDTO);
        AccountHeadCode accountHeadCode = accountHeadCodeMapper.toEntity(accountHeadCodeDTO);
        accountHeadCode = accountHeadCodeRepository.save(accountHeadCode);
        return accountHeadCodeMapper.toDto(accountHeadCode);
    }

    /**
     * Update a accountHeadCode.
     *
     * @param accountHeadCodeDTO the entity to save.
     * @return the persisted entity.
     */
    public AccountHeadCodeDTO update(AccountHeadCodeDTO accountHeadCodeDTO) {
        log.debug("Request to update AccountHeadCode : {}", accountHeadCodeDTO);
        AccountHeadCode accountHeadCode = accountHeadCodeMapper.toEntity(accountHeadCodeDTO);
        accountHeadCode = accountHeadCodeRepository.save(accountHeadCode);
        return accountHeadCodeMapper.toDto(accountHeadCode);
    }

    /**
     * Partially update a accountHeadCode.
     *
     * @param accountHeadCodeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AccountHeadCodeDTO> partialUpdate(AccountHeadCodeDTO accountHeadCodeDTO) {
        log.debug("Request to partially update AccountHeadCode : {}", accountHeadCodeDTO);

        return accountHeadCodeRepository
            .findById(accountHeadCodeDTO.getId())
            .map(existingAccountHeadCode -> {
                accountHeadCodeMapper.partialUpdate(existingAccountHeadCode, accountHeadCodeDTO);

                return existingAccountHeadCode;
            })
            .map(accountHeadCodeRepository::save)
            .map(accountHeadCodeMapper::toDto);
    }

    /**
     * Get all the accountHeadCodes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountHeadCodeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AccountHeadCodes");
        return accountHeadCodeRepository.findAll(pageable).map(accountHeadCodeMapper::toDto);
    }

    /**
     * Get one accountHeadCode by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AccountHeadCodeDTO> findOne(Long id) {
        log.debug("Request to get AccountHeadCode : {}", id);
        return accountHeadCodeRepository.findById(id).map(accountHeadCodeMapper::toDto);
    }

    /**
     * Delete the accountHeadCode by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AccountHeadCode : {}", id);
        accountHeadCodeRepository.deleteById(id);
    }
}
