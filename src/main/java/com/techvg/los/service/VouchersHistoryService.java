package com.techvg.los.service;

import com.techvg.los.domain.VouchersHistory;
import com.techvg.los.repository.VouchersHistoryRepository;
import com.techvg.los.service.dto.VouchersHistoryDTO;
import com.techvg.los.service.mapper.VouchersHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VouchersHistory}.
 */
@Service
@Transactional
public class VouchersHistoryService {

    private final Logger log = LoggerFactory.getLogger(VouchersHistoryService.class);

    private final VouchersHistoryRepository vouchersHistoryRepository;

    private final VouchersHistoryMapper vouchersHistoryMapper;

    public VouchersHistoryService(VouchersHistoryRepository vouchersHistoryRepository, VouchersHistoryMapper vouchersHistoryMapper) {
        this.vouchersHistoryRepository = vouchersHistoryRepository;
        this.vouchersHistoryMapper = vouchersHistoryMapper;
    }

    /**
     * Save a vouchersHistory.
     *
     * @param vouchersHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public VouchersHistoryDTO save(VouchersHistoryDTO vouchersHistoryDTO) {
        log.debug("Request to save VouchersHistory : {}", vouchersHistoryDTO);
        VouchersHistory vouchersHistory = vouchersHistoryMapper.toEntity(vouchersHistoryDTO);
        vouchersHistory = vouchersHistoryRepository.save(vouchersHistory);
        return vouchersHistoryMapper.toDto(vouchersHistory);
    }

    /**
     * Update a vouchersHistory.
     *
     * @param vouchersHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public VouchersHistoryDTO update(VouchersHistoryDTO vouchersHistoryDTO) {
        log.debug("Request to update VouchersHistory : {}", vouchersHistoryDTO);
        VouchersHistory vouchersHistory = vouchersHistoryMapper.toEntity(vouchersHistoryDTO);
        vouchersHistory = vouchersHistoryRepository.save(vouchersHistory);
        return vouchersHistoryMapper.toDto(vouchersHistory);
    }

    /**
     * Partially update a vouchersHistory.
     *
     * @param vouchersHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VouchersHistoryDTO> partialUpdate(VouchersHistoryDTO vouchersHistoryDTO) {
        log.debug("Request to partially update VouchersHistory : {}", vouchersHistoryDTO);

        return vouchersHistoryRepository
            .findById(vouchersHistoryDTO.getId())
            .map(existingVouchersHistory -> {
                vouchersHistoryMapper.partialUpdate(existingVouchersHistory, vouchersHistoryDTO);

                return existingVouchersHistory;
            })
            .map(vouchersHistoryRepository::save)
            .map(vouchersHistoryMapper::toDto);
    }

    /**
     * Get all the vouchersHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VouchersHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VouchersHistories");
        return vouchersHistoryRepository.findAll(pageable).map(vouchersHistoryMapper::toDto);
    }

    /**
     * Get one vouchersHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VouchersHistoryDTO> findOne(Long id) {
        log.debug("Request to get VouchersHistory : {}", id);
        return vouchersHistoryRepository.findById(id).map(vouchersHistoryMapper::toDto);
    }

    /**
     * Delete the vouchersHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VouchersHistory : {}", id);
        vouchersHistoryRepository.deleteById(id);
    }
}
