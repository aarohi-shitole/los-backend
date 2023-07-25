package com.techvg.los.service;

import com.techvg.los.domain.VoucherDetails;
import com.techvg.los.repository.VoucherDetailsRepository;
import com.techvg.los.service.dto.VoucherDetailsDTO;
import com.techvg.los.service.mapper.VoucherDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link VoucherDetails}.
 */
@Service
@Transactional
public class VoucherDetailsService {

    private final Logger log = LoggerFactory.getLogger(VoucherDetailsService.class);

    private final VoucherDetailsRepository voucherDetailsRepository;

    private final VoucherDetailsMapper voucherDetailsMapper;

    public VoucherDetailsService(VoucherDetailsRepository voucherDetailsRepository, VoucherDetailsMapper voucherDetailsMapper) {
        this.voucherDetailsRepository = voucherDetailsRepository;
        this.voucherDetailsMapper = voucherDetailsMapper;
    }

    /**
     * Save a voucherDetails.
     *
     * @param voucherDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public VoucherDetailsDTO save(VoucherDetailsDTO voucherDetailsDTO) {
        log.debug("Request to save VoucherDetails : {}", voucherDetailsDTO);
        VoucherDetails voucherDetails = voucherDetailsMapper.toEntity(voucherDetailsDTO);
        voucherDetails = voucherDetailsRepository.save(voucherDetails);
        return voucherDetailsMapper.toDto(voucherDetails);
    }

    /**
     * Update a voucherDetails.
     *
     * @param voucherDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public VoucherDetailsDTO update(VoucherDetailsDTO voucherDetailsDTO) {
        log.debug("Request to update VoucherDetails : {}", voucherDetailsDTO);
        VoucherDetails voucherDetails = voucherDetailsMapper.toEntity(voucherDetailsDTO);
        voucherDetails = voucherDetailsRepository.save(voucherDetails);
        return voucherDetailsMapper.toDto(voucherDetails);
    }

    /**
     * Partially update a voucherDetails.
     *
     * @param voucherDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VoucherDetailsDTO> partialUpdate(VoucherDetailsDTO voucherDetailsDTO) {
        log.debug("Request to partially update VoucherDetails : {}", voucherDetailsDTO);

        return voucherDetailsRepository
            .findById(voucherDetailsDTO.getId())
            .map(existingVoucherDetails -> {
                voucherDetailsMapper.partialUpdate(existingVoucherDetails, voucherDetailsDTO);

                return existingVoucherDetails;
            })
            .map(voucherDetailsRepository::save)
            .map(voucherDetailsMapper::toDto);
    }

    /**
     * Get all the voucherDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VoucherDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VoucherDetails");
        return voucherDetailsRepository.findAll(pageable).map(voucherDetailsMapper::toDto);
    }

    /**
     * Get one voucherDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VoucherDetailsDTO> findOne(Long id) {
        log.debug("Request to get VoucherDetails : {}", id);
        return voucherDetailsRepository.findById(id).map(voucherDetailsMapper::toDto);
    }

    /**
     * Delete the voucherDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VoucherDetails : {}", id);
        voucherDetailsRepository.deleteById(id);
    }
}
