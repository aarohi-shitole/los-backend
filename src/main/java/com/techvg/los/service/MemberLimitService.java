package com.techvg.los.service;

import com.techvg.los.domain.MemberLimit;
import com.techvg.los.repository.MemberLimitRepository;
import com.techvg.los.service.dto.MemberLimitDTO;
import com.techvg.los.service.mapper.MemberLimitMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MemberLimit}.
 */
@Service
@Transactional
public class MemberLimitService {

    private final Logger log = LoggerFactory.getLogger(MemberLimitService.class);

    private final MemberLimitRepository memberLimitRepository;

    private final MemberLimitMapper memberLimitMapper;

    public MemberLimitService(MemberLimitRepository memberLimitRepository, MemberLimitMapper memberLimitMapper) {
        this.memberLimitRepository = memberLimitRepository;
        this.memberLimitMapper = memberLimitMapper;
    }

    /**
     * Save a memberLimit.
     *
     * @param memberLimitDTO the entity to save.
     * @return the persisted entity.
     */
    public MemberLimitDTO save(MemberLimitDTO memberLimitDTO) {
        log.debug("Request to save MemberLimit : {}", memberLimitDTO);
        MemberLimit memberLimit = memberLimitMapper.toEntity(memberLimitDTO);
        memberLimit = memberLimitRepository.save(memberLimit);
        return memberLimitMapper.toDto(memberLimit);
    }

    /**
     * Update a memberLimit.
     *
     * @param memberLimitDTO the entity to save.
     * @return the persisted entity.
     */
    public MemberLimitDTO update(MemberLimitDTO memberLimitDTO) {
        log.debug("Request to update MemberLimit : {}", memberLimitDTO);
        MemberLimit memberLimit = memberLimitMapper.toEntity(memberLimitDTO);
        memberLimit = memberLimitRepository.save(memberLimit);
        return memberLimitMapper.toDto(memberLimit);
    }

    /**
     * Partially update a memberLimit.
     *
     * @param memberLimitDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MemberLimitDTO> partialUpdate(MemberLimitDTO memberLimitDTO) {
        log.debug("Request to partially update MemberLimit : {}", memberLimitDTO);

        return memberLimitRepository
            .findById(memberLimitDTO.getId())
            .map(existingMemberLimit -> {
                memberLimitMapper.partialUpdate(existingMemberLimit, memberLimitDTO);

                return existingMemberLimit;
            })
            .map(memberLimitRepository::save)
            .map(memberLimitMapper::toDto);
    }

    /**
     * Get all the memberLimits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberLimitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MemberLimits");
        return memberLimitRepository.findAll(pageable).map(memberLimitMapper::toDto);
    }

    /**
     * Get one memberLimit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MemberLimitDTO> findOne(Long id) {
        log.debug("Request to get MemberLimit : {}", id);
        return memberLimitRepository.findById(id).map(memberLimitMapper::toDto);
    }

    /**
     * Delete the memberLimit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MemberLimit : {}", id);
        memberLimitRepository.deleteById(id);
    }
}
