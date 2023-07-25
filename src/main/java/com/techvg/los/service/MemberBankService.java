package com.techvg.los.service;

import com.techvg.los.domain.MemberBank;
import com.techvg.los.repository.MemberBankRepository;
import com.techvg.los.service.dto.MemberBankDTO;
import com.techvg.los.service.mapper.MemberBankMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MemberBank}.
 */
@Service
@Transactional
public class MemberBankService {

    private final Logger log = LoggerFactory.getLogger(MemberBankService.class);

    private final MemberBankRepository memberBankRepository;

    private final MemberBankMapper memberBankMapper;

    public MemberBankService(MemberBankRepository memberBankRepository, MemberBankMapper memberBankMapper) {
        this.memberBankRepository = memberBankRepository;
        this.memberBankMapper = memberBankMapper;
    }

    /**
     * Save a memberBank.
     *
     * @param memberBankDTO the entity to save.
     * @return the persisted entity.
     */
    public MemberBankDTO save(MemberBankDTO memberBankDTO) {
        log.debug("Request to save MemberBank : {}", memberBankDTO);
        MemberBank memberBank = memberBankMapper.toEntity(memberBankDTO);
        memberBank = memberBankRepository.save(memberBank);
        return memberBankMapper.toDto(memberBank);
    }

    /**
     * Update a memberBank.
     *
     * @param memberBankDTO the entity to save.
     * @return the persisted entity.
     */
    public MemberBankDTO update(MemberBankDTO memberBankDTO) {
        log.debug("Request to update MemberBank : {}", memberBankDTO);
        MemberBank memberBank = memberBankMapper.toEntity(memberBankDTO);
        memberBank = memberBankRepository.save(memberBank);
        return memberBankMapper.toDto(memberBank);
    }

    /**
     * Partially update a memberBank.
     *
     * @param memberBankDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MemberBankDTO> partialUpdate(MemberBankDTO memberBankDTO) {
        log.debug("Request to partially update MemberBank : {}", memberBankDTO);

        return memberBankRepository
            .findById(memberBankDTO.getId())
            .map(existingMemberBank -> {
                memberBankMapper.partialUpdate(existingMemberBank, memberBankDTO);

                return existingMemberBank;
            })
            .map(memberBankRepository::save)
            .map(memberBankMapper::toDto);
    }

    /**
     * Get all the memberBanks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberBankDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MemberBanks");
        return memberBankRepository.findAll(pageable).map(memberBankMapper::toDto);
    }

    /**
     * Get one memberBank by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MemberBankDTO> findOne(Long id) {
        log.debug("Request to get MemberBank : {}", id);
        return memberBankRepository.findById(id).map(memberBankMapper::toDto);
    }

    /**
     * Delete the memberBank by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MemberBank : {}", id);
        memberBankRepository.deleteById(id);
    }

    public Optional<MemberBankDTO> findAccountNumber(Long accountNumber) {
        log.debug("Request to get MemberBank : {}", accountNumber);
        return memberBankRepository.findAccountNumber(accountNumber).map(memberBankMapper::toDto);
    }
}
