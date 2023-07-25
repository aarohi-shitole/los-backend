package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.repository.EmployementDetailsRepository;
import com.techvg.los.service.criteria.EmployementDetailsCriteria;
import com.techvg.los.service.dto.EmployementDetailsDTO;
import com.techvg.los.service.mapper.EmployementDetailsMapper;
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
 * Service for executing complex queries for {@link EmployementDetails} entities in the database.
 * The main input is a {@link EmployementDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployementDetailsDTO} or a {@link Page} of {@link EmployementDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployementDetailsQueryService extends QueryService<EmployementDetails> {

    private final Logger log = LoggerFactory.getLogger(EmployementDetailsQueryService.class);

    private final EmployementDetailsRepository employementDetailsRepository;

    private final EmployementDetailsMapper employementDetailsMapper;

    public EmployementDetailsQueryService(
        EmployementDetailsRepository employementDetailsRepository,
        EmployementDetailsMapper employementDetailsMapper
    ) {
        this.employementDetailsRepository = employementDetailsRepository;
        this.employementDetailsMapper = employementDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link EmployementDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployementDetailsDTO> findByCriteria(EmployementDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployementDetails> specification = createSpecification(criteria);
        return employementDetailsMapper.toDto(employementDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployementDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployementDetailsDTO> findByCriteria(EmployementDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployementDetails> specification = createSpecification(criteria);
        return employementDetailsRepository.findAll(specification, page).map(employementDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployementDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployementDetails> specification = createSpecification(criteria);
        return employementDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployementDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployementDetails> createSpecification(EmployementDetailsCriteria criteria) {
        Specification<EmployementDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployementDetails_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), EmployementDetails_.type));
            }
            if (criteria.getEmployerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployerName(), EmployementDetails_.employerName));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), EmployementDetails_.status));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), EmployementDetails_.designation));
            }
            if (criteria.getDuration() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDuration(), EmployementDetails_.duration));
            }
            if (criteria.getEmployerAdd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployerAdd(), EmployementDetails_.employerAdd));
            }
            if (criteria.getPrevCompanyName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrevCompanyName(), EmployementDetails_.prevCompanyName));
            }
            if (criteria.getPrevCompanyduration() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPrevCompanyduration(), EmployementDetails_.prevCompanyduration));
            }
            if (criteria.getOrgType() != null) {
                specification = specification.and(buildSpecification(criteria.getOrgType(), EmployementDetails_.orgType));
            }
            if (criteria.getConstitutionType() != null) {
                specification = specification.and(buildSpecification(criteria.getConstitutionType(), EmployementDetails_.constitutionType));
            }
            if (criteria.getIndustryType() != null) {
                specification = specification.and(buildSpecification(criteria.getIndustryType(), EmployementDetails_.industryType));
            }
            if (criteria.getBusinessRegNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBusinessRegNo(), EmployementDetails_.businessRegNo));
            }
            if (criteria.getCompOwnerShip() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCompOwnerShip(), EmployementDetails_.compOwnerShip));
            }
            if (criteria.getPartnerName1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPartnerName1(), EmployementDetails_.partnerName1));
            }
            if (criteria.getPartnerName2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPartnerName2(), EmployementDetails_.partnerName2));
            }
            if (criteria.getPartnerName3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPartnerName3(), EmployementDetails_.partnerName3));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), EmployementDetails_.isDeleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), EmployementDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), EmployementDetails_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), EmployementDetails_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), EmployementDetails_.createdOn));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), EmployementDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), EmployementDetails_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), EmployementDetails_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), EmployementDetails_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), EmployementDetails_.freeField5));
            }
            if (criteria.getFreeField6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField6(), EmployementDetails_.freeField6));
            }
            if (criteria.getMemberId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMemberId(),
                            root -> root.join(EmployementDetails_.member, JoinType.LEFT).get(Member_.id)
                        )
                    );
            }
            if (criteria.getGuarantorId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getGuarantorId(),
                            root -> root.join(EmployementDetails_.guarantor, JoinType.LEFT).get(Guarantor_.id)
                        )
                    );
            }
            if (criteria.getAddressId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAddressId(),
                            root -> root.join(EmployementDetails_.address, JoinType.LEFT).get(Address_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
