package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.MemberAssets;
import com.techvg.los.repository.MemberAssetsRepository;
import com.techvg.los.service.criteria.MemberAssetsCriteria;
import com.techvg.los.service.dto.MemberAssetsDTO;
import com.techvg.los.service.mapper.MemberAssetsMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link MemberAssets} entities in the database.
 * The main input is a {@link MemberAssetsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MemberAssetsDTO} or a {@link Page} of {@link MemberAssetsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MemberAssetsQueryService extends QueryService<MemberAssets> {

    private final Logger log = LoggerFactory.getLogger(MemberAssetsQueryService.class);

    private final MemberAssetsRepository memberAssetsRepository;

    private final MemberAssetsMapper memberAssetsMapper;

    public MemberAssetsQueryService(MemberAssetsRepository memberAssetsRepository, MemberAssetsMapper memberAssetsMapper) {
        this.memberAssetsRepository = memberAssetsRepository;
        this.memberAssetsMapper = memberAssetsMapper;
    }

    /**
     * Return a {@link List} of {@link MemberAssetsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MemberAssetsDTO> findByCriteria(MemberAssetsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MemberAssets> specification = createSpecification(criteria);
        return memberAssetsMapper.toDto(memberAssetsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MemberAssetsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberAssetsDTO> findByCriteria(MemberAssetsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MemberAssets> specification = createSpecification(criteria);
        return memberAssetsRepository.findAll(specification, page).map(memberAssetsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MemberAssetsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MemberAssets> specification = createSpecification(criteria);
        return memberAssetsRepository.count(specification);
    }

    /**
     * Function to convert {@link MemberAssetsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MemberAssets> createSpecification(MemberAssetsCriteria criteria) {
        Specification<MemberAssets> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MemberAssets_.id));
            }
            if (criteria.getAssetCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetCost(), MemberAssets_.assetCost));
            }
            if (criteria.getAssetType() != null) {
                specification = specification.and(buildSpecification(criteria.getAssetType(), MemberAssets_.assetType));
            }
            if (criteria.getAreaInSqFt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAreaInSqFt(), MemberAssets_.areaInSqFt));
            }
            if (criteria.getAssetNature() != null) {
                specification = specification.and(buildSpecification(criteria.getAssetNature(), MemberAssets_.assetNature));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), MemberAssets_.address));
            }
            if (criteria.getLandMark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLandMark(), MemberAssets_.landMark));
            }
            if (criteria.getAssetOwner() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetOwner(), MemberAssets_.assetOwner));
            }
            if (criteria.getCompletionYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompletionYear(), MemberAssets_.completionYear));
            }
            if (criteria.getMarketValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMarketValue(), MemberAssets_.marketValue));
            }
            if (criteria.getMortgagePercentage() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMortgagePercentage(), MemberAssets_.mortgagePercentage));
            }
            if (criteria.getMortgageCreationDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMortgageCreationDate(), MemberAssets_.mortgageCreationDate));
            }
            if (criteria.getIsInsured() != null) {
                specification = specification.and(buildSpecification(criteria.getIsInsured(), MemberAssets_.isInsured));
            }
            if (criteria.getIsUnderUsed() != null) {
                specification = specification.and(buildSpecification(criteria.getIsUnderUsed(), MemberAssets_.isUnderUsed));
            }
            if (criteria.getIsOwned() != null) {
                specification = specification.and(buildSpecification(criteria.getIsOwned(), MemberAssets_.isOwned));
            }
            if (criteria.getIsMortgage() != null) {
                specification = specification.and(buildSpecification(criteria.getIsMortgage(), MemberAssets_.isMortgage));
            }
            if (criteria.getLandType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLandType(), MemberAssets_.landType));
            }
            if (criteria.getLandGatNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLandGatNo(), MemberAssets_.landGatNo));
            }
            if (criteria.getLandAreaInHector() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLandAreaInHector(), MemberAssets_.landAreaInHector));
            }
            if (criteria.getJindagiPatrakNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJindagiPatrakNo(), MemberAssets_.jindagiPatrakNo));
            }
            if (criteria.getJindagiAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJindagiAmount(), MemberAssets_.jindagiAmount));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), MemberAssets_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MemberAssets_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), MemberAssets_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), MemberAssets_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), MemberAssets_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), MemberAssets_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), MemberAssets_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), MemberAssets_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), MemberAssets_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), MemberAssets_.freeField5));
            }
            if (criteria.getFreeField6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField6(), MemberAssets_.freeField6));
            }
            if (criteria.getMemberId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getMemberId(), root -> root.join(MemberAssets_.member, JoinType.LEFT).get(Member_.id))
                    );
            }
            if (criteria.getGuarantorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getGuarantorId(),
                            root -> root.join(MemberAssets_.guarantor, JoinType.LEFT).get(Guarantor_.id)
                        )
                    );
            }
            if (criteria.getLoanApplicationsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanApplicationsId(),
                            root -> root.join(MemberAssets_.loanApplications, JoinType.LEFT).get(LoanApplications_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
