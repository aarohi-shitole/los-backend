package com.techvg.los.service;

import com.techvg.los.domain.InterestConfig;
import com.techvg.los.repository.InterestConfigRepository;
import com.techvg.los.service.dto.InterestConfigDTO;
import com.techvg.los.service.mapper.InterestConfigMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link InterestConfig}.
 */
@Service
@Transactional
public class InterestConfigService {

    private final Logger log = LoggerFactory.getLogger(InterestConfigService.class);

    private final InterestConfigRepository interestConfigRepository;

    private final InterestConfigMapper interestConfigMapper;

    public InterestConfigService(InterestConfigRepository interestConfigRepository, InterestConfigMapper interestConfigMapper) {
        this.interestConfigRepository = interestConfigRepository;
        this.interestConfigMapper = interestConfigMapper;
    }

    /**
     * Save a interestConfig.
     *
     * @param interestConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public InterestConfigDTO save(InterestConfigDTO interestConfigDTO) {
        log.debug("Request to save InterestConfig : {}", interestConfigDTO);
        InterestConfig interestConfig = interestConfigMapper.toEntity(interestConfigDTO);
        interestConfig = interestConfigRepository.save(interestConfig);
        return interestConfigMapper.toDto(interestConfig);
    }

    /**
     * Update a interestConfig.
     *
     * @param interestConfigDTO the entity to save.
     * @return the persisted entity.
     */
    public InterestConfigDTO update(InterestConfigDTO interestConfigDTO) {
        log.debug("Request to update InterestConfig : {}", interestConfigDTO);
        InterestConfig interestConfig = interestConfigMapper.toEntity(interestConfigDTO);
        interestConfig = interestConfigRepository.save(interestConfig);
        return interestConfigMapper.toDto(interestConfig);
    }

    /**
     * Partially update a interestConfig.
     *
     * @param interestConfigDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InterestConfigDTO> partialUpdate(InterestConfigDTO interestConfigDTO) {
        log.debug("Request to partially update InterestConfig : {}", interestConfigDTO);

        return interestConfigRepository
            .findById(interestConfigDTO.getId())
            .map(existingInterestConfig -> {
                interestConfigMapper.partialUpdate(existingInterestConfig, interestConfigDTO);

                return existingInterestConfig;
            })
            .map(interestConfigRepository::save)
            .map(interestConfigMapper::toDto);
    }

    /**
     * Get all the interestConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InterestConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InterestConfigs");
        return interestConfigRepository.findAll(pageable).map(interestConfigMapper::toDto);
    }

    /**
     * Get one interestConfig by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InterestConfigDTO> findOne(Long id) {
        log.debug("Request to get InterestConfig : {}", id);
        return interestConfigRepository.findById(id).map(interestConfigMapper::toDto);
    }

    /**
     * Delete the interestConfig by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete InterestConfig : {}", id);
        interestConfigRepository.deleteById(id);
    }
}
