package com.techvg.los.service;

import com.techvg.los.domain.NpaSetting;
import com.techvg.los.repository.NpaSettingRepository;
import com.techvg.los.service.dto.NpaSettingDTO;
import com.techvg.los.service.mapper.NpaSettingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NpaSetting}.
 */
@Service
@Transactional
public class NpaSettingService {

    private final Logger log = LoggerFactory.getLogger(NpaSettingService.class);

    private final NpaSettingRepository npaSettingRepository;

    private final NpaSettingMapper npaSettingMapper;

    public NpaSettingService(NpaSettingRepository npaSettingRepository, NpaSettingMapper npaSettingMapper) {
        this.npaSettingRepository = npaSettingRepository;
        this.npaSettingMapper = npaSettingMapper;
    }

    /**
     * Save a npaSetting.
     *
     * @param npaSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public NpaSettingDTO save(NpaSettingDTO npaSettingDTO) {
        log.debug("Request to save NpaSetting : {}", npaSettingDTO);
        NpaSetting npaSetting = npaSettingMapper.toEntity(npaSettingDTO);
        npaSetting = npaSettingRepository.save(npaSetting);
        return npaSettingMapper.toDto(npaSetting);
    }

    /**
     * Update a npaSetting.
     *
     * @param npaSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public NpaSettingDTO update(NpaSettingDTO npaSettingDTO) {
        log.debug("Request to update NpaSetting : {}", npaSettingDTO);
        NpaSetting npaSetting = npaSettingMapper.toEntity(npaSettingDTO);
        npaSetting = npaSettingRepository.save(npaSetting);
        return npaSettingMapper.toDto(npaSetting);
    }

    /**
     * Partially update a npaSetting.
     *
     * @param npaSettingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NpaSettingDTO> partialUpdate(NpaSettingDTO npaSettingDTO) {
        log.debug("Request to partially update NpaSetting : {}", npaSettingDTO);

        return npaSettingRepository
            .findById(npaSettingDTO.getId())
            .map(existingNpaSetting -> {
                npaSettingMapper.partialUpdate(existingNpaSetting, npaSettingDTO);

                return existingNpaSetting;
            })
            .map(npaSettingRepository::save)
            .map(npaSettingMapper::toDto);
    }

    /**
     * Get all the npaSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NpaSettingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NpaSettings");
        return npaSettingRepository.findAll(pageable).map(npaSettingMapper::toDto);
    }

    /**
     * Get one npaSetting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NpaSettingDTO> findOne(Long id) {
        log.debug("Request to get NpaSetting : {}", id);
        return npaSettingRepository.findById(id).map(npaSettingMapper::toDto);
    }

    /**
     * Delete the npaSetting by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NpaSetting : {}", id);
        npaSettingRepository.deleteById(id);
    }
}
