package com.techvg.los.service;

import com.techvg.los.domain.Guarantor;
import com.techvg.los.repository.EmployementDetailsRepository;
import com.techvg.los.repository.GuarantorRepository;
import com.techvg.los.repository.MemberAssetsRepository;
import com.techvg.los.service.dto.GuarantorDTO;
import com.techvg.los.service.mapper.GuarantorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Guarantor}.
 */
@Service
@Transactional
public class GuarantorService {

    private final Logger log = LoggerFactory.getLogger(GuarantorService.class);

    private final GuarantorRepository guarantorRepository;

    private final GuarantorMapper guarantorMapper;

    public GuarantorService(GuarantorRepository guarantorRepository, GuarantorMapper guarantorMapper) {
        this.guarantorRepository = guarantorRepository;
        this.guarantorMapper = guarantorMapper;
    }

    /**
     * Save a guarantor.
     *
     * @param guarantorDTO the entity to save.
     * @return the persisted entity.
     */
    public GuarantorDTO save(GuarantorDTO guarantorDTO) {
        log.debug("Request to save Guarantor : {}", guarantorDTO);
        Guarantor guarantor = guarantorMapper.toEntity(guarantorDTO);
        guarantor = guarantorRepository.save(guarantor);
        return guarantorMapper.toDto(guarantor);
    }

    /**
     * Update a guarantor.
     *
     * @param guarantorDTO the entity to save.
     * @return the persisted entity.
     */
    public GuarantorDTO update(GuarantorDTO guarantorDTO) {
        log.debug("Request to update Guarantor : {}", guarantorDTO);
        Guarantor guarantor = guarantorMapper.toEntity(guarantorDTO);
        guarantor = guarantorRepository.save(guarantor);
        return guarantorMapper.toDto(guarantor);
    }

    /**
     * Partially update a guarantor.
     *
     * @param guarantorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GuarantorDTO> partialUpdate(GuarantorDTO guarantorDTO) {
        log.debug("Request to partially update Guarantor : {}", guarantorDTO);

        return guarantorRepository
            .findById(guarantorDTO.getId())
            .map(existingGuarantor -> {
                guarantorMapper.partialUpdate(existingGuarantor, guarantorDTO);

                return existingGuarantor;
            })
            .map(guarantorRepository::save)
            .map(guarantorMapper::toDto);
    }

    /**
     * Get all the guarantors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GuarantorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Guarantors");
        return guarantorRepository.findAll(pageable).map(guarantorMapper::toDto);
    }

    /**
     * Get one guarantor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GuarantorDTO> findOne(Long id) {
        log.debug("Request to get Guarantor : {}", id);
        return guarantorRepository.findById(id).map(guarantorMapper::toDto);
    }

    /**
     * Delete the guarantor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Guarantor : {}", id);
        guarantorRepository.deleteById(id);
    }
}
