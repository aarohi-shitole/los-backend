package com.techvg.los.service;

import com.techvg.los.domain.SequenceGenerator;
import com.techvg.los.repository.SequenceGeneratorRepository;
import com.techvg.los.service.dto.SequenceGeneratorDTO;
import com.techvg.los.service.mapper.SequenceGeneratorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SequenceGenerator}.
 */
@Service
@Transactional
public class SequenceGeneratorService {

    private final Logger log = LoggerFactory.getLogger(SequenceGeneratorService.class);

    private final SequenceGeneratorRepository sequenceGeneratorRepository;

    private final SequenceGeneratorMapper sequenceGeneratorMapper;

    public SequenceGeneratorService(
        SequenceGeneratorRepository sequenceGeneratorRepository,
        SequenceGeneratorMapper sequenceGeneratorMapper
    ) {
        this.sequenceGeneratorRepository = sequenceGeneratorRepository;
        this.sequenceGeneratorMapper = sequenceGeneratorMapper;
    }

    /**
     * Save a sequenceGenerator.
     *
     * @param sequenceGeneratorDTO the entity to save.
     * @return the persisted entity.
     */
    public SequenceGeneratorDTO save(SequenceGeneratorDTO sequenceGeneratorDTO) {
        log.debug("Request to save SequenceGenerator : {}", sequenceGeneratorDTO);
        SequenceGenerator sequenceGenerator = sequenceGeneratorMapper.toEntity(sequenceGeneratorDTO);
        sequenceGenerator = sequenceGeneratorRepository.save(sequenceGenerator);
        return sequenceGeneratorMapper.toDto(sequenceGenerator);
    }

    /**
     * Update a sequenceGenerator.
     *
     * @param sequenceGeneratorDTO the entity to save.
     * @return the persisted entity.
     */
    public SequenceGeneratorDTO update(SequenceGeneratorDTO sequenceGeneratorDTO) {
        log.debug("Request to update SequenceGenerator : {}", sequenceGeneratorDTO);
        SequenceGenerator sequenceGenerator = sequenceGeneratorMapper.toEntity(sequenceGeneratorDTO);
        sequenceGenerator = sequenceGeneratorRepository.save(sequenceGenerator);
        return sequenceGeneratorMapper.toDto(sequenceGenerator);
    }

    /**
     * Partially update a sequenceGenerator.
     *
     * @param sequenceGeneratorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SequenceGeneratorDTO> partialUpdate(SequenceGeneratorDTO sequenceGeneratorDTO) {
        log.debug("Request to partially update SequenceGenerator : {}", sequenceGeneratorDTO);

        return sequenceGeneratorRepository
            .findById(sequenceGeneratorDTO.getId())
            .map(existingSequenceGenerator -> {
                sequenceGeneratorMapper.partialUpdate(existingSequenceGenerator, sequenceGeneratorDTO);

                return existingSequenceGenerator;
            })
            .map(sequenceGeneratorRepository::save)
            .map(sequenceGeneratorMapper::toDto);
    }

    /**
     * Get all the sequenceGenerators.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceGeneratorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SequenceGenerators");
        return sequenceGeneratorRepository.findAll(pageable).map(sequenceGeneratorMapper::toDto);
    }

    /**
     * Get one sequenceGenerator by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SequenceGeneratorDTO> findOne(Long id) {
        log.debug("Request to get SequenceGenerator : {}", id);
        return sequenceGeneratorRepository.findById(id).map(sequenceGeneratorMapper::toDto);
    }

    /**
     * Delete the sequenceGenerator by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SequenceGenerator : {}", id);
        sequenceGeneratorRepository.deleteById(id);
    }
}
