package com.techvg.los.service;

import com.techvg.los.domain.Vouchers;
import com.techvg.los.repository.VouchersRepository;
import com.techvg.los.service.dto.VouchersDTO;
import com.techvg.los.service.mapper.VouchersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Vouchers}.
 */
@Service
@Transactional
public class VouchersService {

    private final Logger log = LoggerFactory.getLogger(VouchersService.class);

    private final VouchersRepository vouchersRepository;

    private final VouchersMapper vouchersMapper;

    public VouchersService(VouchersRepository vouchersRepository, VouchersMapper vouchersMapper) {
        this.vouchersRepository = vouchersRepository;
        this.vouchersMapper = vouchersMapper;
    }

    /**
     * Save a vouchers.
     *
     * @param vouchersDTO the entity to save.
     * @return the persisted entity.
     */
    public VouchersDTO save(VouchersDTO vouchersDTO) {
        log.debug("Request to save Vouchers : {}", vouchersDTO);
        Vouchers vouchers = vouchersMapper.toEntity(vouchersDTO);
        vouchers = vouchersRepository.save(vouchers);
        return vouchersMapper.toDto(vouchers);
    }

    /**
     * Update a vouchers.
     *
     * @param vouchersDTO the entity to save.
     * @return the persisted entity.
     */
    public VouchersDTO update(VouchersDTO vouchersDTO) {
        log.debug("Request to update Vouchers : {}", vouchersDTO);
        Vouchers vouchers = vouchersMapper.toEntity(vouchersDTO);
        vouchers = vouchersRepository.save(vouchers);
        return vouchersMapper.toDto(vouchers);
    }

    /**
     * Partially update a vouchers.
     *
     * @param vouchersDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<VouchersDTO> partialUpdate(VouchersDTO vouchersDTO) {
        log.debug("Request to partially update Vouchers : {}", vouchersDTO);

        return vouchersRepository
            .findById(vouchersDTO.getId())
            .map(existingVouchers -> {
                vouchersMapper.partialUpdate(existingVouchers, vouchersDTO);

                return existingVouchers;
            })
            .map(vouchersRepository::save)
            .map(vouchersMapper::toDto);
    }

    /**
     * Get all the vouchers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VouchersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Vouchers");
        return vouchersRepository.findAll(pageable).map(vouchersMapper::toDto);
    }

    /**
     * Get one vouchers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VouchersDTO> findOne(Long id) {
        log.debug("Request to get Vouchers : {}", id);
        return vouchersRepository.findById(id).map(vouchersMapper::toDto);
    }

    /**
     * Delete the vouchers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Vouchers : {}", id);
        vouchersRepository.deleteById(id);
    }
}
