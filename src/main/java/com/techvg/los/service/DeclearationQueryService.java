package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.Declearation;
import com.techvg.los.repository.DeclearationRepository;
import com.techvg.los.service.criteria.DeclearationCriteria;
import com.techvg.los.service.dto.DeclearationDTO;
import com.techvg.los.service.mapper.DeclearationMapper;
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
 * Service for executing complex queries for {@link Declearation} entities in the database.
 * The main input is a {@link DeclearationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeclearationDTO} or a {@link Page} of {@link DeclearationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeclearationQueryService extends QueryService<Declearation> {

    private final Logger log = LoggerFactory.getLogger(DeclearationQueryService.class);

    private final DeclearationRepository declearationRepository;

    private final DeclearationMapper declearationMapper;

    public DeclearationQueryService(DeclearationRepository declearationRepository, DeclearationMapper declearationMapper) {
        this.declearationRepository = declearationRepository;
        this.declearationMapper = declearationMapper;
    }

    /**
     * Return a {@link List} of {@link DeclearationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeclearationDTO> findByCriteria(DeclearationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Declearation> specification = createSpecification(criteria);
        return declearationMapper.toDto(declearationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DeclearationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeclearationDTO> findByCriteria(DeclearationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Declearation> specification = createSpecification(criteria);
        return declearationRepository.findAll(specification, page).map(declearationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeclearationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Declearation> specification = createSpecification(criteria);
        return declearationRepository.count(specification);
    }

    /**
     * Function to convert {@link DeclearationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Declearation> createSpecification(DeclearationCriteria criteria) {
        Specification<Declearation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Declearation_.id));
            }
            if (criteria.getTiltle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTiltle(), Declearation_.tiltle));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Declearation_.type));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Declearation_.description));
            }
            if (criteria.getTag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTag(), Declearation_.tag));
            }
            if (criteria.getSubType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubType(), Declearation_.subType));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Declearation_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Declearation_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Declearation_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Declearation_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), Declearation_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), Declearation_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), Declearation_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), Declearation_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), Declearation_.freeField4));
            }
            if (criteria.getOrganisationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganisationId(),
                            root -> root.join(Declearation_.organisation, JoinType.LEFT).get(Organisation_.id)
                        )
                    );
            }
        }
        return specification;
    }
}