package com.techvg.los.service;

import com.techvg.los.domain.MemberExistingfacility;
import com.techvg.los.repository.MemberExistingfacilityRepository;
import com.techvg.los.service.dto.MemberExistingfacilityDTO;
import com.techvg.los.service.mapper.MemberExistingfacilityMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MemberExistingfacility}.
 */
@Service
@Transactional
public class MemberExistingfacilityService {

    private final Logger log = LoggerFactory.getLogger(MemberExistingfacilityService.class);

    private final MemberExistingfacilityRepository memberExistingfacilityRepository;

    private final MemberExistingfacilityMapper memberExistingfacilityMapper;

    public MemberExistingfacilityService(
        MemberExistingfacilityRepository memberExistingfacilityRepository,
        MemberExistingfacilityMapper memberExistingfacilityMapper
    ) {
        this.memberExistingfacilityRepository = memberExistingfacilityRepository;
        this.memberExistingfacilityMapper = memberExistingfacilityMapper;
    }

    /**
     * Save a memberExistingfacility.
     *
     * @param memberExistingfacilityDTO the entity to save.
     * @return the persisted entity.
     */
    public MemberExistingfacilityDTO save(MemberExistingfacilityDTO memberExistingfacilityDTO) {
        log.debug("Request to save MemberExistingfacility : {}", memberExistingfacilityDTO);
        MemberExistingfacility memberExistingfacility = memberExistingfacilityMapper.toEntity(memberExistingfacilityDTO);
        memberExistingfacility = memberExistingfacilityRepository.save(memberExistingfacility);
        return memberExistingfacilityMapper.toDto(memberExistingfacility);
    }

    /**
     * Update a memberExistingfacility.
     *
     * @param memberExistingfacilityDTO the entity to save.
     * @return the persisted entity.
     */
    public MemberExistingfacilityDTO update(MemberExistingfacilityDTO memberExistingfacilityDTO) {
        log.debug("Request to update MemberExistingfacility : {}", memberExistingfacilityDTO);
        MemberExistingfacility memberExistingfacility = memberExistingfacilityMapper.toEntity(memberExistingfacilityDTO);
        memberExistingfacility = memberExistingfacilityRepository.save(memberExistingfacility);
        return memberExistingfacilityMapper.toDto(memberExistingfacility);
    }

    /**
     * Partially update a memberExistingfacility.
     *
     * @param memberExistingfacilityDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MemberExistingfacilityDTO> partialUpdate(MemberExistingfacilityDTO memberExistingfacilityDTO) {
        log.debug("Request to partially update MemberExistingfacility : {}", memberExistingfacilityDTO);

        return memberExistingfacilityRepository
            .findById(memberExistingfacilityDTO.getId())
            .map(existingMemberExistingfacility -> {
                memberExistingfacilityMapper.partialUpdate(existingMemberExistingfacility, memberExistingfacilityDTO);

                return existingMemberExistingfacility;
            })
            .map(memberExistingfacilityRepository::save)
            .map(memberExistingfacilityMapper::toDto);
    }

    /**
     * Get all the memberExistingfacilities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberExistingfacilityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MemberExistingfacilities");
        return memberExistingfacilityRepository.findAll(pageable).map(memberExistingfacilityMapper::toDto);
    }

    /**
     * Get one memberExistingfacility by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MemberExistingfacilityDTO> findOne(Long id) {
        log.debug("Request to get MemberExistingfacility : {}", id);
        return memberExistingfacilityRepository.findById(id).map(memberExistingfacilityMapper::toDto);
    }

    /**
     * Delete the memberExistingfacility by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MemberExistingfacility : {}", id);
        memberExistingfacilityRepository.deleteById(id);
    }
}
