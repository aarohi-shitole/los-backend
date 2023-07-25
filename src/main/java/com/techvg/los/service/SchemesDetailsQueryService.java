package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.SchemesDetails;
import com.techvg.los.repository.SchemesDetailsRepository;
import com.techvg.los.service.criteria.SchemesDetailsCriteria;
import com.techvg.los.service.dto.SchemesDetailsDTO;
import com.techvg.los.service.mapper.SchemesDetailsMapper;
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
 * Service for executing complex queries for {@link SchemesDetails} entities in the database.
 * The main input is a {@link SchemesDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SchemesDetailsDTO} or a {@link Page} of {@link SchemesDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SchemesDetailsQueryService extends QueryService<SchemesDetails> {

    private final Logger log = LoggerFactory.getLogger(SchemesDetailsQueryService.class);

    private final SchemesDetailsRepository schemesDetailsRepository;

    private final SchemesDetailsMapper schemesDetailsMapper;

    public SchemesDetailsQueryService(SchemesDetailsRepository schemesDetailsRepository, SchemesDetailsMapper schemesDetailsMapper) {
        this.schemesDetailsRepository = schemesDetailsRepository;
        this.schemesDetailsMapper = schemesDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link SchemesDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SchemesDetailsDTO> findByCriteria(SchemesDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SchemesDetails> specification = createSpecification(criteria);
        return schemesDetailsMapper.toDto(schemesDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SchemesDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SchemesDetailsDTO> findByCriteria(SchemesDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SchemesDetails> specification = createSpecification(criteria);
        return schemesDetailsRepository.findAll(specification, page).map(schemesDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SchemesDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SchemesDetails> specification = createSpecification(criteria);
        return schemesDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link SchemesDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SchemesDetails> createSpecification(SchemesDetailsCriteria criteria) {
        Specification<SchemesDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SchemesDetails_.id));
            }
            if (criteria.getFdDurationDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFdDurationDays(), SchemesDetails_.fdDurationDays));
            }
            if (criteria.getInterest() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInterest(), SchemesDetails_.interest));
            }
            if (criteria.getLockInPeriod() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLockInPeriod(), SchemesDetails_.lockInPeriod));
            }
            if (criteria.getSchemeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSchemeName(), SchemesDetails_.schemeName));
            }
            if (criteria.getRdDurationMonths() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getRdDurationMonths(), SchemesDetails_.rdDurationMonths));
            }
            if (criteria.getReinvestInterest() != null) {
                specification = specification.and(buildSpecification(criteria.getReinvestInterest(), SchemesDetails_.reinvestInterest));
            }
            if (criteria.getMinAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinAmount(), SchemesDetails_.minAmount));
            }
            if (criteria.getLastDayOfScheam() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastDayOfScheam(), SchemesDetails_.lastDayOfScheam));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), SchemesDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), SchemesDetails_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), SchemesDetails_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), SchemesDetails_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), SchemesDetails_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), SchemesDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), SchemesDetails_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), SchemesDetails_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), SchemesDetails_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), SchemesDetails_.freeField5));
            }
            if (criteria.getOrganisationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganisationId(),
                            root -> root.join(SchemesDetails_.organisation, JoinType.LEFT).get(Organisation_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
