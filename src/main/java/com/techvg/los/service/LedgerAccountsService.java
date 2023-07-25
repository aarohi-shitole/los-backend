package com.techvg.los.service;

import com.techvg.los.domain.LedgerAccounts;
import com.techvg.los.repository.LedgerAccountsRepository;
import com.techvg.los.service.dto.LedgerAccountsDTO;
import com.techvg.los.service.mapper.LedgerAccountsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LedgerAccounts}.
 */
@Service
@Transactional
public class LedgerAccountsService {

    private final Logger log = LoggerFactory.getLogger(LedgerAccountsService.class);

    private final LedgerAccountsRepository ledgerAccountsRepository;

    private final LedgerAccountsMapper ledgerAccountsMapper;

    public LedgerAccountsService(LedgerAccountsRepository ledgerAccountsRepository, LedgerAccountsMapper ledgerAccountsMapper) {
        this.ledgerAccountsRepository = ledgerAccountsRepository;
        this.ledgerAccountsMapper = ledgerAccountsMapper;
    }

    /**
     * Save a ledgerAccounts.
     *
     * @param ledgerAccountsDTO the entity to save.
     * @return the persisted entity.
     */
    public LedgerAccountsDTO save(LedgerAccountsDTO ledgerAccountsDTO) {
        log.debug("Request to save LedgerAccounts : {}", ledgerAccountsDTO);
        LedgerAccounts ledgerAccounts = ledgerAccountsMapper.toEntity(ledgerAccountsDTO);
        ledgerAccounts = ledgerAccountsRepository.save(ledgerAccounts);
        return ledgerAccountsMapper.toDto(ledgerAccounts);
    }

    /**
     * Update a ledgerAccounts.
     *
     * @param ledgerAccountsDTO the entity to save.
     * @return the persisted entity.
     */
    public LedgerAccountsDTO update(LedgerAccountsDTO ledgerAccountsDTO) {
        log.debug("Request to update LedgerAccounts : {}", ledgerAccountsDTO);
        LedgerAccounts ledgerAccounts = ledgerAccountsMapper.toEntity(ledgerAccountsDTO);
        ledgerAccounts = ledgerAccountsRepository.save(ledgerAccounts);
        return ledgerAccountsMapper.toDto(ledgerAccounts);
    }

    /**
     * Partially update a ledgerAccounts.
     *
     * @param ledgerAccountsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LedgerAccountsDTO> partialUpdate(LedgerAccountsDTO ledgerAccountsDTO) {
        log.debug("Request to partially update LedgerAccounts : {}", ledgerAccountsDTO);

        return ledgerAccountsRepository
            .findById(ledgerAccountsDTO.getId())
            .map(existingLedgerAccounts -> {
                ledgerAccountsMapper.partialUpdate(existingLedgerAccounts, ledgerAccountsDTO);

                return existingLedgerAccounts;
            })
            .map(ledgerAccountsRepository::save)
            .map(ledgerAccountsMapper::toDto);
    }

    /**
     * Get all the ledgerAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LedgerAccountsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LedgerAccounts");
        return ledgerAccountsRepository.findAll(pageable).map(ledgerAccountsMapper::toDto);
    }

    /**
     * Get one ledgerAccounts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LedgerAccountsDTO> findOne(Long id) {
        log.debug("Request to get LedgerAccounts : {}", id);
        return ledgerAccountsRepository.findById(id).map(ledgerAccountsMapper::toDto);
    }

    /**
     * Delete the ledgerAccounts by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LedgerAccounts : {}", id);
        ledgerAccountsRepository.deleteById(id);
    }
}
