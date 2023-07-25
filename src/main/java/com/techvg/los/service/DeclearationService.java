package com.techvg.los.service;

import com.techvg.los.domain.Declearation;
import com.techvg.los.repository.DeclearationRepository;
import com.techvg.los.service.dto.DeclearationDTO;
import com.techvg.los.service.mapper.DeclearationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Declearation}.
 */
@Service
@Transactional
public class DeclearationService {

    private final Logger log = LoggerFactory.getLogger(DeclearationService.class);

    private final DeclearationRepository declearationRepository;

    private final DeclearationMapper declearationMapper;

    public DeclearationService(DeclearationRepository declearationRepository, DeclearationMapper declearationMapper) {
        this.declearationRepository = declearationRepository;
        this.declearationMapper = declearationMapper;
    }

    /**
     * Save a declearation.
     *
     * @param declearationDTO the entity to save.
     * @return the persisted entity.
     */
    public DeclearationDTO save(DeclearationDTO declearationDTO) {
        log.debug("Request to save Declearation : {}", declearationDTO);
        Declearation declearation = declearationMapper.toEntity(declearationDTO);
        declearation = declearationRepository.save(declearation);
        return declearationMapper.toDto(declearation);
    }

    /**
     * Update a declearation.
     *
     * @param declearationDTO the entity to save.
     * @return the persisted entity.
     */
    public DeclearationDTO update(DeclearationDTO declearationDTO) {
        log.debug("Request to update Declearation : {}", declearationDTO);
        Declearation declearation = declearationMapper.toEntity(declearationDTO);
        declearation = declearationRepository.save(declearation);
        return declearationMapper.toDto(declearation);
    }

    /**
     * Partially update a declearation.
     *
     * @param declearationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DeclearationDTO> partialUpdate(DeclearationDTO declearationDTO) {
        log.debug("Request to partially update Declearation : {}", declearationDTO);

        return declearationRepository
            .findById(declearationDTO.getId())
            .map(existingDeclearation -> {
                declearationMapper.partialUpdate(existingDeclearation, declearationDTO);

                return existingDeclearation;
            })
            .map(declearationRepository::save)
            .map(declearationMapper::toDto);
    }

    /**
     * Get all the declearations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DeclearationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Declearations");
        return declearationRepository.findAll(pageable).map(declearationMapper::toDto);
    }

    /**
     * Get one declearation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DeclearationDTO> findOne(Long id) {
        log.debug("Request to get Declearation : {}", id);
        return declearationRepository.findById(id).map(declearationMapper::toDto);
    }

    /**
     * Delete the declearation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Declearation : {}", id);
        declearationRepository.deleteById(id);
    }
}
