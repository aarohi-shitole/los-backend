package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.Organisation;
import com.techvg.los.repository.OrganisationRepository;
import com.techvg.los.service.criteria.OrganisationCriteria;
import com.techvg.los.service.dto.OrganisationDTO;
import com.techvg.los.service.mapper.OrganisationMapper;
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
 * Service for executing complex queries for {@link Organisation} entities in the database.
 * The main input is a {@link OrganisationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrganisationDTO} or a {@link Page} of {@link OrganisationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrganisationQueryService extends QueryService<Organisation> {

    private final Logger log = LoggerFactory.getLogger(OrganisationQueryService.class);

    private final OrganisationRepository organisationRepository;

    private final OrganisationMapper organisationMapper;

    public OrganisationQueryService(OrganisationRepository organisationRepository, OrganisationMapper organisationMapper) {
        this.organisationRepository = organisationRepository;
        this.organisationMapper = organisationMapper;
    }

    /**
     * Return a {@link List} of {@link OrganisationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrganisationDTO> findByCriteria(OrganisationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Organisation> specification = createSpecification(criteria);
        return organisationMapper.toDto(organisationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrganisationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganisationDTO> findByCriteria(OrganisationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Organisation> specification = createSpecification(criteria);
        return organisationRepository.findAll(specification, page).map(organisationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrganisationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Organisation> specification = createSpecification(criteria);
        return organisationRepository.count(specification);
    }

    /**
     * Function to convert {@link OrganisationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Organisation> createSpecification(OrganisationCriteria criteria) {
        Specification<Organisation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Organisation_.id));
            }
            if (criteria.getOrgName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrgName(), Organisation_.orgName));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Organisation_.description));
            }
            if (criteria.getRegNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegNumber(), Organisation_.regNumber));
            }
            if (criteria.getGstin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGstin(), Organisation_.gstin));
            }
            if (criteria.getPan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPan(), Organisation_.pan));
            }
            if (criteria.getTan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTan(), Organisation_.tan));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Organisation_.phone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Organisation_.email));
            }
            if (criteria.getWebSite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebSite(), Organisation_.webSite));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFax(), Organisation_.fax));
            }
            if (criteria.getIsActivate() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActivate(), Organisation_.isActivate));
            }
            if (criteria.getOrgType() != null) {
                specification = specification.and(buildSpecification(criteria.getOrgType(), Organisation_.orgType));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Organisation_.createdOn));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Organisation_.createdBy));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsDeleted(), Organisation_.isDeleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Organisation_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Organisation_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), Organisation_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), Organisation_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), Organisation_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), Organisation_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), Organisation_.freeField5));
            }
            if (criteria.getAddressId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAddressId(),
                            root -> root.join(Organisation_.address, JoinType.LEFT).get(Address_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
