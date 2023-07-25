package com.techvg.los.service;

import com.techvg.los.domain.RemarkHistory;
import com.techvg.los.repository.RemarkHistoryRepository;
import com.techvg.los.service.dto.RemarkHistoryDTO;
import com.techvg.los.service.mapper.RemarkHistoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RemarkHistory}.
 */
@Service
@Transactional
public class RemarkHistoryService {

    private final Logger log = LoggerFactory.getLogger(RemarkHistoryService.class);

    private final RemarkHistoryRepository remarkHistoryRepository;

    private final RemarkHistoryMapper remarkHistoryMapper;

    public RemarkHistoryService(RemarkHistoryRepository remarkHistoryRepository, RemarkHistoryMapper remarkHistoryMapper) {
        this.remarkHistoryRepository = remarkHistoryRepository;
        this.remarkHistoryMapper = remarkHistoryMapper;
    }

    /**
     * Save a remarkHistory.
     *
     * @param remarkHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public RemarkHistoryDTO save(RemarkHistoryDTO remarkHistoryDTO) {
        log.debug("Request to save RemarkHistory : {}", remarkHistoryDTO);
        if (remarkHistoryDTO.getIsDeleted() == null) {
            remarkHistoryDTO.setIsDeleted(false);
        }
        RemarkHistory remarkHistory = remarkHistoryMapper.toEntity(remarkHistoryDTO);
        remarkHistory = remarkHistoryRepository.save(remarkHistory);
        return remarkHistoryMapper.toDto(remarkHistory);
    }

    /**
     * Update a remarkHistory.
     *
     * @param remarkHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public RemarkHistoryDTO update(RemarkHistoryDTO remarkHistoryDTO) {
        log.debug("Request to update RemarkHistory : {}", remarkHistoryDTO);
        RemarkHistory remarkHistory = remarkHistoryMapper.toEntity(remarkHistoryDTO);
        remarkHistory = remarkHistoryRepository.save(remarkHistory);
        return remarkHistoryMapper.toDto(remarkHistory);
    }

    /**
     * Partially update a remarkHistory.
     *
     * @param remarkHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RemarkHistoryDTO> partialUpdate(RemarkHistoryDTO remarkHistoryDTO) {
        log.debug("Request to partially update RemarkHistory : {}", remarkHistoryDTO);

        return remarkHistoryRepository
            .findById(remarkHistoryDTO.getId())
            .map(existingRemarkHistory -> {
                remarkHistoryMapper.partialUpdate(existingRemarkHistory, remarkHistoryDTO);

                return existingRemarkHistory;
            })
            .map(remarkHistoryRepository::save)
            .map(remarkHistoryMapper::toDto);
    }

    /**
     * Get all the remarkHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RemarkHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RemarkHistories");
        return remarkHistoryRepository.findAll(pageable).map(remarkHistoryMapper::toDto);
    }

    /**
     * Get one remarkHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RemarkHistoryDTO> findOne(Long id) {
        log.debug("Request to get RemarkHistory : {}", id);
        return remarkHistoryRepository.findById(id).map(remarkHistoryMapper::toDto);
    }

    /**
     * Delete the remarkHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete RemarkHistory : {}", id);
        remarkHistoryRepository.deleteById(id);
    }
}
