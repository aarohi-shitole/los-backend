package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.InterestConfig;
import com.techvg.los.repository.InterestConfigRepository;
import com.techvg.los.service.criteria.InterestConfigCriteria;
import com.techvg.los.service.dto.InterestConfigDTO;
import com.techvg.los.service.mapper.InterestConfigMapper;
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
 * Service for executing complex queries for {@link InterestConfig} entities in the database.
 * The main input is a {@link InterestConfigCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link InterestConfigDTO} or a {@link Page} of {@link InterestConfigDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InterestConfigQueryService extends QueryService<InterestConfig> {

    private final Logger log = LoggerFactory.getLogger(InterestConfigQueryService.class);

    private final InterestConfigRepository interestConfigRepository;

    private final InterestConfigMapper interestConfigMapper;

    public InterestConfigQueryService(InterestConfigRepository interestConfigRepository, InterestConfigMapper interestConfigMapper) {
        this.interestConfigRepository = interestConfigRepository;
        this.interestConfigMapper = interestConfigMapper;
    }

    /**
     * Return a {@link List} of {@link InterestConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<InterestConfigDTO> findByCriteria(InterestConfigCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<InterestConfig> specification = createSpecification(criteria);
        return interestConfigMapper.toDto(interestConfigRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link InterestConfigDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<InterestConfigDTO> findByCriteria(InterestConfigCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<InterestConfig> specification = createSpecification(criteria);
        return interestConfigRepository.findAll(specification, page).map(interestConfigMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InterestConfigCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<InterestConfig> specification = createSpecification(criteria);
        return interestConfigRepository.count(specification);
    }

    /**
     * Function to convert {@link InterestConfigCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<InterestConfig> createSpecification(InterestConfigCriteria criteria) {
        Specification<InterestConfig> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), InterestConfig_.id));
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), InterestConfig_.startDate));
            }
            if (criteria.getEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEndDate(), InterestConfig_.endDate));
            }
            if (criteria.getInterestBasis() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInterestBasis(), InterestConfig_.interestBasis));
            }
            if (criteria.getEmiBasis() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmiBasis(), InterestConfig_.emiBasis));
            }
            if (criteria.getInterestType() != null) {
                specification = specification.and(buildSpecification(criteria.getInterestType(), InterestConfig_.interestType));
            }
            if (criteria.getIntrAccrualFreq() != null) {
                specification = specification.and(buildSpecification(criteria.getIntrAccrualFreq(), InterestConfig_.intrAccrualFreq));
            }
            if (criteria.getPenalInterestRate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPenalInterestRate(), InterestConfig_.penalInterestRate));
            }
            if (criteria.getPenalInterestBasis() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPenalInterestBasis(), InterestConfig_.penalInterestBasis));
            }
            if (criteria.getPenalAccountingBasis() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPenalAccountingBasis(), InterestConfig_.penalAccountingBasis));
            }
            if (criteria.getMinInterestRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinInterestRate(), InterestConfig_.minInterestRate));
            }
            if (criteria.getMaxInterestRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxInterestRate(), InterestConfig_.maxInterestRate));
            }
            if (criteria.getExtendedInterestRate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExtendedInterestRate(), InterestConfig_.extendedInterestRate));
            }
            if (criteria.getSurchargeRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurchargeRate(), InterestConfig_.surchargeRate));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), InterestConfig_.isDeleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), InterestConfig_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), InterestConfig_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), InterestConfig_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), InterestConfig_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), InterestConfig_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), InterestConfig_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), InterestConfig_.freeField5));
            }
            if (criteria.getFreeField6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField6(), InterestConfig_.freeField6));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(InterestConfig_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
