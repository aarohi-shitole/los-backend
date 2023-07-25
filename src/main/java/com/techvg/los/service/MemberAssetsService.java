package com.techvg.los.service;

import com.techvg.los.domain.MemberAssets;
import com.techvg.los.repository.MemberAssetsRepository;
import com.techvg.los.service.criteria.MortgagedAssetsCriteria;
import com.techvg.los.service.dto.MemberAssetsDTO;
import com.techvg.los.service.dto.MortgagedAssetsDTO;
import com.techvg.los.service.mapper.MemberAssetsMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.filter.LongFilter;

/**
 * Service Implementation for managing {@link MemberAssets}.
 */
@Service
@Transactional
public class MemberAssetsService {

    private final Logger log = LoggerFactory.getLogger(MemberAssetsService.class);

    private final MemberAssetsRepository memberAssetsRepository;

    private final MemberAssetsMapper memberAssetsMapper;

    @Autowired
    private MortgagedAssetsService mortgagedAssetsService;

    public MemberAssetsService(MemberAssetsRepository memberAssetsRepository, MemberAssetsMapper memberAssetsMapper) {
        this.memberAssetsRepository = memberAssetsRepository;
        this.memberAssetsMapper = memberAssetsMapper;
    }

    /**
     * Save a memberAssets.
     *
     * @param memberAssetsDTO the entity to save.
     * @return the persisted entity.
     */
    public MemberAssetsDTO save(MemberAssetsDTO memberAssetsDTO) {
        log.debug("Request to save MemberAssets : {}", memberAssetsDTO);
        MemberAssets memberAssets = memberAssetsMapper.toEntity(memberAssetsDTO);
        memberAssets = memberAssetsRepository.save(memberAssets);
        return memberAssetsMapper.toDto(memberAssets);
    }

    /**
     * Update a memberAssets.
     *
     * @param memberAssetsDTO the entity to save.
     * @return the persisted entity.
     */
    public MemberAssetsDTO update(MemberAssetsDTO memberAssetsDTO) {
        log.debug("Request to update MemberAssets : {}", memberAssetsDTO);
        MemberAssets memberAssets = memberAssetsMapper.toEntity(memberAssetsDTO);

        //------------Save mortgage asset in MortgagedAssets-------
        if (memberAssetsDTO.getIsMortgage() && memberAssetsDTO.getLoanApplications() != null) {
            // ---------------Delete existing mortgage assets ---------------------
            MortgagedAssetsCriteria mortgagedCriteria = new MortgagedAssetsCriteria();
            LongFilter idFilter = new LongFilter();
            idFilter.setEquals(memberAssetsDTO.getLoanApplications().getId());
            mortgagedCriteria.setLoanApplicationsId(idFilter);

            idFilter = new LongFilter();
            idFilter.setEquals(memberAssetsDTO.getMember().getId());
            mortgagedCriteria.setMemberId(idFilter);

            idFilter = new LongFilter();
            idFilter.setEquals(memberAssetsDTO.getId());
            mortgagedCriteria.setMemberAssetsId(idFilter);

            List<MortgagedAssetsDTO> assetsList = mortgagedAssetsService.findByCriteria(mortgagedCriteria);

            if (!assetsList.isEmpty()) {
                for (MortgagedAssetsDTO assetObj : assetsList) {
                    mortgagedAssetsService.delete(assetObj.getId());
                }
            }
            //-----------------------------------------------------------------
            //---------------Add new mortgaged Assets------------------------

            MortgagedAssetsDTO mortgagedAssets = new MortgagedAssetsDTO();
            mortgagedAssets.setLoanApplicationsId(memberAssetsDTO.getLoanApplications().getId());
            mortgagedAssets.setMemberId(memberAssetsDTO.getMember().getId());
            mortgagedAssets.setMemberAssets(memberAssetsDTO);
            mortgagedAssets.setMortgCreateDate(memberAssetsDTO.getMortgageCreationDate());
            mortgagedAssets.setMortgPercentage(memberAssetsDTO.getMortgagePercentage());
            mortgagedAssetsService.save(mortgagedAssets);
        }

        //-----------------------------------------------------------
        memberAssets = memberAssetsRepository.save(memberAssets);
        return memberAssetsMapper.toDto(memberAssets);
    }

    /**
     * Partially update a memberAssets.
     *
     * @param memberAssetsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MemberAssetsDTO> partialUpdate(MemberAssetsDTO memberAssetsDTO) {
        log.debug("Request to partially update MemberAssets : {}", memberAssetsDTO);

        return memberAssetsRepository
            .findById(memberAssetsDTO.getId())
            .map(existingMemberAssets -> {
                memberAssetsMapper.partialUpdate(existingMemberAssets, memberAssetsDTO);

                return existingMemberAssets;
            })
            .map(memberAssetsRepository::save)
            .map(memberAssetsMapper::toDto);
    }

    /**
     * Get all the memberAssets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberAssetsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MemberAssets");
        return memberAssetsRepository.findAll(pageable).map(memberAssetsMapper::toDto);
    }

    /**
     * Get one memberAssets by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MemberAssetsDTO> findOne(Long id) {
        log.debug("Request to get MemberAssets : {}", id);
        return memberAssetsRepository.findById(id).map(memberAssetsMapper::toDto);
    }

    /**
     * Delete the memberAssets by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MemberAssets : {}", id);
        memberAssetsRepository.deleteById(id);
    }

    public List<MemberAssetsDTO> getLoanMemberAssets(long memberId, long loanApplicationId) {
        List<MemberAssets> assetsList = new ArrayList<MemberAssets>();
        //-------- Get all mortgage asset of member in loan application-----------
        List<MemberAssets> mortgatedAssets = memberAssetsRepository.getAllMortgageAssets(memberId, loanApplicationId);
        if (!mortgatedAssets.isEmpty()) {
            assetsList.addAll(mortgatedAssets);
        }
        //-------- Get all assets of member which are not in mortgage -----------
        List<MemberAssets> unMortgatedAssets = memberAssetsRepository.getUnMortgagedAssets(memberId);
        if (!unMortgatedAssets.isEmpty()) {
            assetsList.addAll(unMortgatedAssets);
        }

        List<MemberAssetsDTO> memberAssetsList = memberAssetsMapper.toDto(assetsList);
        return memberAssetsList;
    }
}
