package com.techvg.los.service;

import com.techvg.los.domain.Epay;
import com.techvg.los.repository.EpayRepository;
import com.techvg.los.service.dto.EpayDTO;
import com.techvg.los.service.mapper.EpayMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Epay}.
 */
@Service
@Transactional
public class EpayService {

    private final Logger log = LoggerFactory.getLogger(EpayService.class);

    private final EpayRepository epayRepository;

    private final EpayMapper epayMapper;

    public EpayService(EpayRepository epayRepository, EpayMapper epayMapper) {
        this.epayRepository = epayRepository;
        this.epayMapper = epayMapper;
    }

    /**
     * Save a epay.
     *
     * @param epayDTO the entity to save.
     * @return the persisted entity.
     */
    public EpayDTO save(EpayDTO epayDTO) {
        log.debug("Request to save Epay : {}", epayDTO);
        Epay epay = epayMapper.toEntity(epayDTO);
        epay = epayRepository.save(epay);
        return epayMapper.toDto(epay);
    }

    /**
     * Update a epay.
     *
     * @param epayDTO the entity to save.
     * @return the persisted entity.
     */
    public EpayDTO update(EpayDTO epayDTO) {
        log.debug("Request to update Epay : {}", epayDTO);
        Epay epay = epayMapper.toEntity(epayDTO);
        epay = epayRepository.save(epay);
        return epayMapper.toDto(epay);
    }

    /**
     * Partially update a epay.
     *
     * @param epayDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EpayDTO> partialUpdate(EpayDTO epayDTO) {
        log.debug("Request to partially update Epay : {}", epayDTO);

        return epayRepository
            .findById(epayDTO.getId())
            .map(existingEpay -> {
                epayMapper.partialUpdate(existingEpay, epayDTO);

                return existingEpay;
            })
            .map(epayRepository::save)
            .map(epayMapper::toDto);
    }

    /**
     * Get all the epays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EpayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Epays");
        return epayRepository.findAll(pageable).map(epayMapper::toDto);
    }

    /**
     * Get one epay by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EpayDTO> findOne(Long id) {
        log.debug("Request to get Epay : {}", id);
        return epayRepository.findById(id).map(epayMapper::toDto);
    }

    /**
     * Delete the epay by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Epay : {}", id);
        epayRepository.deleteById(id);
    }
}
