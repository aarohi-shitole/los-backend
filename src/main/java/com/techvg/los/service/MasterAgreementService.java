package com.techvg.los.service;

import com.techvg.los.domain.MasterAgreement;
import com.techvg.los.repository.MasterAgreementRepository;
import com.techvg.los.service.dto.MasterAgreementDTO;
import com.techvg.los.service.mapper.MasterAgreementMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MasterAgreement}.
 */
@Service
@Transactional
public class MasterAgreementService {

    private final Logger log = LoggerFactory.getLogger(MasterAgreementService.class);

    private final MasterAgreementRepository masterAgreementRepository;

    private final MasterAgreementMapper masterAgreementMapper;

    public MasterAgreementService(MasterAgreementRepository masterAgreementRepository, MasterAgreementMapper masterAgreementMapper) {
        this.masterAgreementRepository = masterAgreementRepository;
        this.masterAgreementMapper = masterAgreementMapper;
    }

    /**
     * Save a masterAgreement.
     *
     * @param masterAgreementDTO the entity to save.
     * @return the persisted entity.
     */
    public MasterAgreementDTO save(MasterAgreementDTO masterAgreementDTO) {
        log.debug("Request to save MasterAgreement : {}", masterAgreementDTO);
        MasterAgreement masterAgreement = masterAgreementMapper.toEntity(masterAgreementDTO);
        masterAgreement = masterAgreementRepository.save(masterAgreement);
        return masterAgreementMapper.toDto(masterAgreement);
    }

    /**
     * Update a masterAgreement.
     *
     * @param masterAgreementDTO the entity to save.
     * @return the persisted entity.
     */
    public MasterAgreementDTO update(MasterAgreementDTO masterAgreementDTO) {
        log.debug("Request to update MasterAgreement : {}", masterAgreementDTO);
        MasterAgreement masterAgreement = masterAgreementMapper.toEntity(masterAgreementDTO);
        masterAgreement = masterAgreementRepository.save(masterAgreement);
        return masterAgreementMapper.toDto(masterAgreement);
    }

    /**
     * Partially update a masterAgreement.
     *
     * @param masterAgreementDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MasterAgreementDTO> partialUpdate(MasterAgreementDTO masterAgreementDTO) {
        log.debug("Request to partially update MasterAgreement : {}", masterAgreementDTO);

        return masterAgreementRepository
            .findById(masterAgreementDTO.getId())
            .map(existingMasterAgreement -> {
                masterAgreementMapper.partialUpdate(existingMasterAgreement, masterAgreementDTO);

                return existingMasterAgreement;
            })
            .map(masterAgreementRepository::save)
            .map(masterAgreementMapper::toDto);
    }

    /**
     * Get all the masterAgreements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MasterAgreementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MasterAgreements");
        return masterAgreementRepository.findAll(pageable).map(masterAgreementMapper::toDto);
    }

    /**
     * Get one masterAgreement by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MasterAgreementDTO> findOne(Long id) {
        log.debug("Request to get MasterAgreement : {}", id);
        return masterAgreementRepository.findById(id).map(masterAgreementMapper::toDto);
    }

    /**
     * Delete the masterAgreement by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MasterAgreement : {}", id);
        masterAgreementRepository.deleteById(id);
    }
}
