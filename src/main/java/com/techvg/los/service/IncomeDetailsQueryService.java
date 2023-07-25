package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.IncomeDetails;
import com.techvg.los.repository.IncomeDetailsRepository;
import com.techvg.los.service.criteria.IncomeDetailsCriteria;
import com.techvg.los.service.dto.IncomeDetailsDTO;
import com.techvg.los.service.mapper.IncomeDetailsMapper;
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
 * Service for executing complex queries for {@link IncomeDetails} entities in
 * the database. The main input is a {@link IncomeDetailsCriteria} which gets
 * converted to {@link Specification}, in a way that all the filters must apply.
 * It returns a {@link List} of {@link IncomeDetailsDTO} or a {@link Page} of
 * {@link IncomeDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class IncomeDetailsQueryService extends QueryService<IncomeDetails> {

    private final Logger log = LoggerFactory.getLogger(IncomeDetailsQueryService.class);

    private final IncomeDetailsRepository incomeDetailsRepository;

    private final IncomeDetailsMapper incomeDetailsMapper;

    public IncomeDetailsQueryService(IncomeDetailsRepository incomeDetailsRepository, IncomeDetailsMapper incomeDetailsMapper) {
        this.incomeDetailsRepository = incomeDetailsRepository;
        this.incomeDetailsMapper = incomeDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link IncomeDetailsDTO} which matches the criteria
     * from the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<IncomeDetailsDTO> findByCriteria(IncomeDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<IncomeDetails> specification = createSpecification(criteria);
        return incomeDetailsMapper.toDto(incomeDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link IncomeDetailsDTO} which matches the criteria
     * from the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<IncomeDetailsDTO> findByCriteria(IncomeDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<IncomeDetails> specification = createSpecification(criteria);
        return incomeDetailsRepository.findAll(specification, page).map(incomeDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(IncomeDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<IncomeDetails> specification = createSpecification(criteria);
        return incomeDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link IncomeDetailsCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<IncomeDetails> createSpecification(IncomeDetailsCriteria criteria) {
        Specification<IncomeDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), IncomeDetails_.id));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), IncomeDetails_.year));
            }
            if (criteria.getGrossIncome() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrossIncome(), IncomeDetails_.grossIncome));
            }
            if (criteria.getExpenses() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpenses(), IncomeDetails_.expenses));
            }
            if (criteria.getNetIncome() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNetIncome(), IncomeDetails_.netIncome));
            }

            if (criteria.getIncome() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIncome(), IncomeDetails_.income));
            }
            if (criteria.getPaidTaxes() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPaidTaxes(), IncomeDetails_.paidTaxes));
            }
            if (criteria.getCashSurplus() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCashSurplus(), IncomeDetails_.cashSurplus));
            }
            if (criteria.getIncomeType() != null) {
                specification = specification.and(buildSpecification(criteria.getIncomeType(), IncomeDetails_.incomeType));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), IncomeDetails_.isDeleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), IncomeDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), IncomeDetails_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), IncomeDetails_.createdBy));
            }

            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), IncomeDetails_.remark));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), IncomeDetails_.createdOn));
            }
            if (criteria.getOtherType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOtherType(), IncomeDetails_.otherType));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), IncomeDetails_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), IncomeDetails_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), IncomeDetails_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), IncomeDetails_.freeField5));
            }
            if (criteria.getFreeField6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField6(), IncomeDetails_.freeField6));
            }
            if (criteria.getMemberId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getMemberId(), root -> root.join(IncomeDetails_.member, JoinType.LEFT).get(Member_.id))
                    );
            }
        }
        return specification;
    }
}
