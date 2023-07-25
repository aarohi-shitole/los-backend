package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.OrgPrerequisiteDoc;
import com.techvg.los.repository.OrgPrerequisiteDocRepository;
import com.techvg.los.service.criteria.OrgPrerequisiteDocCriteria;
import com.techvg.los.service.dto.OrgPrerequisiteDocDTO;
import com.techvg.los.service.mapper.OrgPrerequisiteDocMapper;
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
 * Service for executing complex queries for {@link OrgPrerequisiteDoc} entities in the database.
 * The main input is a {@link OrgPrerequisiteDocCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrgPrerequisiteDocDTO} or a {@link Page} of {@link OrgPrerequisiteDocDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrgPrerequisiteDocQueryService extends QueryService<OrgPrerequisiteDoc> {

    private final Logger log = LoggerFactory.getLogger(OrgPrerequisiteDocQueryService.class);

    private final OrgPrerequisiteDocRepository orgPrerequisiteDocRepository;

    private final OrgPrerequisiteDocMapper orgPrerequisiteDocMapper;

    public OrgPrerequisiteDocQueryService(
        OrgPrerequisiteDocRepository orgPrerequisiteDocRepository,
        OrgPrerequisiteDocMapper orgPrerequisiteDocMapper
    ) {
        this.orgPrerequisiteDocRepository = orgPrerequisiteDocRepository;
        this.orgPrerequisiteDocMapper = orgPrerequisiteDocMapper;
    }

    /**
     * Return a {@link List} of {@link OrgPrerequisiteDocDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrgPrerequisiteDocDTO> findByCriteria(OrgPrerequisiteDocCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OrgPrerequisiteDoc> specification = createSpecification(criteria);
        return orgPrerequisiteDocMapper.toDto(orgPrerequisiteDocRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrgPrerequisiteDocDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrgPrerequisiteDocDTO> findByCriteria(OrgPrerequisiteDocCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OrgPrerequisiteDoc> specification = createSpecification(criteria);
        return orgPrerequisiteDocRepository.findAll(specification, page).map(orgPrerequisiteDocMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrgPrerequisiteDocCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OrgPrerequisiteDoc> specification = createSpecification(criteria);
        return orgPrerequisiteDocRepository.count(specification);
    }

    /**
     * Function to convert {@link OrgPrerequisiteDocCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OrgPrerequisiteDoc> createSpecification(OrgPrerequisiteDocCriteria criteria) {
        Specification<OrgPrerequisiteDoc> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OrgPrerequisiteDoc_.id));
            }
            if (criteria.getDocDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocDesc(), OrgPrerequisiteDoc_.docDesc));
            }
            if (criteria.getDocName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocName(), OrgPrerequisiteDoc_.docName));
            }
            if (criteria.getDocCatagory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocCatagory(), OrgPrerequisiteDoc_.docCatagory));
            }
            if (criteria.getIsmandatory() != null) {
                specification = specification.and(buildSpecification(criteria.getIsmandatory(), OrgPrerequisiteDoc_.ismandatory));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), OrgPrerequisiteDoc_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), OrgPrerequisiteDoc_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), OrgPrerequisiteDoc_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), OrgPrerequisiteDoc_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), OrgPrerequisiteDoc_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), OrgPrerequisiteDoc_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), OrgPrerequisiteDoc_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), OrgPrerequisiteDoc_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), OrgPrerequisiteDoc_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), OrgPrerequisiteDoc_.freeField5));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(OrgPrerequisiteDoc_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getOrganisationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganisationId(),
                            root -> root.join(OrgPrerequisiteDoc_.organisation, JoinType.LEFT).get(Organisation_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
