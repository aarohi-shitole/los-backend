package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.NpaSetting;
import com.techvg.los.repository.NpaSettingRepository;
import com.techvg.los.service.criteria.NpaSettingCriteria;
import com.techvg.los.service.dto.NpaSettingDTO;
import com.techvg.los.service.mapper.NpaSettingMapper;
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
 * Service for executing complex queries for {@link NpaSetting} entities in the database.
 * The main input is a {@link NpaSettingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NpaSettingDTO} or a {@link Page} of {@link NpaSettingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NpaSettingQueryService extends QueryService<NpaSetting> {

    private final Logger log = LoggerFactory.getLogger(NpaSettingQueryService.class);

    private final NpaSettingRepository npaSettingRepository;

    private final NpaSettingMapper npaSettingMapper;

    public NpaSettingQueryService(NpaSettingRepository npaSettingRepository, NpaSettingMapper npaSettingMapper) {
        this.npaSettingRepository = npaSettingRepository;
        this.npaSettingMapper = npaSettingMapper;
    }

    /**
     * Return a {@link List} of {@link NpaSettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NpaSettingDTO> findByCriteria(NpaSettingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NpaSetting> specification = createSpecification(criteria);
        return npaSettingMapper.toDto(npaSettingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NpaSettingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NpaSettingDTO> findByCriteria(NpaSettingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NpaSetting> specification = createSpecification(criteria);
        return npaSettingRepository.findAll(specification, page).map(npaSettingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NpaSettingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NpaSetting> specification = createSpecification(criteria);
        return npaSettingRepository.count(specification);
    }

    /**
     * Function to convert {@link NpaSettingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NpaSetting> createSpecification(NpaSettingCriteria criteria) {
        Specification<NpaSetting> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NpaSetting_.id));
            }
            if (criteria.getNpaClassification() != null) {
                specification = specification.and(buildSpecification(criteria.getNpaClassification(), NpaSetting_.npaClassification));
            }
            if (criteria.getDurationStart() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDurationStart(), NpaSetting_.durationStart));
            }
            if (criteria.getDurationEnd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDurationEnd(), NpaSetting_.durationEnd));
            }
            if (criteria.getProvision() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProvision(), NpaSetting_.provision));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), NpaSetting_.year));
            }
            if (criteria.getInterestRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInterestRate(), NpaSetting_.interestRate));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), NpaSetting_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), NpaSetting_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), NpaSetting_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), NpaSetting_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), NpaSetting_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), NpaSetting_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), NpaSetting_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), NpaSetting_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), NpaSetting_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), NpaSetting_.freeField5));
            }
            if (criteria.getOrganisationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganisationId(),
                            root -> root.join(NpaSetting_.organisation, JoinType.LEFT).get(Organisation_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
