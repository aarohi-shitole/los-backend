package com.techvg.los.service;

import com.techvg.los.domain.OrgPrerequisiteDoc;
import com.techvg.los.repository.OrgPrerequisiteDocRepository;
import com.techvg.los.service.dto.OrgPrerequisiteDocDTO;
import com.techvg.los.service.mapper.OrgPrerequisiteDocMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrgPrerequisiteDoc}.
 */
@Service
@Transactional
public class OrgPrerequisiteDocService {

    private final Logger log = LoggerFactory.getLogger(OrgPrerequisiteDocService.class);

    private final OrgPrerequisiteDocRepository orgPrerequisiteDocRepository;

    private final OrgPrerequisiteDocMapper orgPrerequisiteDocMapper;

    public OrgPrerequisiteDocService(
        OrgPrerequisiteDocRepository orgPrerequisiteDocRepository,
        OrgPrerequisiteDocMapper orgPrerequisiteDocMapper
    ) {
        this.orgPrerequisiteDocRepository = orgPrerequisiteDocRepository;
        this.orgPrerequisiteDocMapper = orgPrerequisiteDocMapper;
    }

    /**
     * Save a orgPrerequisiteDoc.
     *
     * @param orgPrerequisiteDocDTO the entity to save.
     * @return the persisted entity.
     */
    public OrgPrerequisiteDocDTO save(OrgPrerequisiteDocDTO orgPrerequisiteDocDTO) {
        log.debug("Request to save OrgPrerequisiteDoc : {}", orgPrerequisiteDocDTO);
        OrgPrerequisiteDoc orgPrerequisiteDoc = orgPrerequisiteDocMapper.toEntity(orgPrerequisiteDocDTO);
        orgPrerequisiteDoc = orgPrerequisiteDocRepository.save(orgPrerequisiteDoc);
        return orgPrerequisiteDocMapper.toDto(orgPrerequisiteDoc);
    }

    /**
     * Update a orgPrerequisiteDoc.
     *
     * @param orgPrerequisiteDocDTO the entity to save.
     * @return the persisted entity.
     */
    public OrgPrerequisiteDocDTO update(OrgPrerequisiteDocDTO orgPrerequisiteDocDTO) {
        log.debug("Request to update OrgPrerequisiteDoc : {}", orgPrerequisiteDocDTO);
        OrgPrerequisiteDoc orgPrerequisiteDoc = orgPrerequisiteDocMapper.toEntity(orgPrerequisiteDocDTO);
        orgPrerequisiteDoc = orgPrerequisiteDocRepository.save(orgPrerequisiteDoc);
        return orgPrerequisiteDocMapper.toDto(orgPrerequisiteDoc);
    }

    /**
     * Partially update a orgPrerequisiteDoc.
     *
     * @param orgPrerequisiteDocDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrgPrerequisiteDocDTO> partialUpdate(OrgPrerequisiteDocDTO orgPrerequisiteDocDTO) {
        log.debug("Request to partially update OrgPrerequisiteDoc : {}", orgPrerequisiteDocDTO);

        return orgPrerequisiteDocRepository
            .findById(orgPrerequisiteDocDTO.getId())
            .map(existingOrgPrerequisiteDoc -> {
                orgPrerequisiteDocMapper.partialUpdate(existingOrgPrerequisiteDoc, orgPrerequisiteDocDTO);

                return existingOrgPrerequisiteDoc;
            })
            .map(orgPrerequisiteDocRepository::save)
            .map(orgPrerequisiteDocMapper::toDto);
    }

    /**
     * Get all the orgPrerequisiteDocs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrgPrerequisiteDocDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrgPrerequisiteDocs");
        return orgPrerequisiteDocRepository.findAll(pageable).map(orgPrerequisiteDocMapper::toDto);
    }

    /**
     * Get one orgPrerequisiteDoc by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrgPrerequisiteDocDTO> findOne(Long id) {
        log.debug("Request to get OrgPrerequisiteDoc : {}", id);
        return orgPrerequisiteDocRepository.findById(id).map(orgPrerequisiteDocMapper::toDto);
    }

    /**
     * Delete the orgPrerequisiteDoc by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrgPrerequisiteDoc : {}", id);
        orgPrerequisiteDocRepository.deleteById(id);
    }
}
