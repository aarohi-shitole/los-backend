package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.MemberExistingfacility;
import com.techvg.los.repository.MemberExistingfacilityRepository;
import com.techvg.los.service.criteria.MemberExistingfacilityCriteria;
import com.techvg.los.service.dto.MemberExistingfacilityDTO;
import com.techvg.los.service.mapper.MemberExistingfacilityMapper;
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
 * Service for executing complex queries for {@link MemberExistingfacility} entities in the database.
 * The main input is a {@link MemberExistingfacilityCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MemberExistingfacilityDTO} or a {@link Page} of {@link MemberExistingfacilityDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MemberExistingfacilityQueryService extends QueryService<MemberExistingfacility> {

    private final Logger log = LoggerFactory.getLogger(MemberExistingfacilityQueryService.class);

    private final MemberExistingfacilityRepository memberExistingfacilityRepository;

    private final MemberExistingfacilityMapper memberExistingfacilityMapper;

    public MemberExistingfacilityQueryService(
        MemberExistingfacilityRepository memberExistingfacilityRepository,
        MemberExistingfacilityMapper memberExistingfacilityMapper
    ) {
        this.memberExistingfacilityRepository = memberExistingfacilityRepository;
        this.memberExistingfacilityMapper = memberExistingfacilityMapper;
    }

    /**
     * Return a {@link List} of {@link MemberExistingfacilityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MemberExistingfacilityDTO> findByCriteria(MemberExistingfacilityCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MemberExistingfacility> specification = createSpecification(criteria);
        return memberExistingfacilityMapper.toDto(memberExistingfacilityRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MemberExistingfacilityDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberExistingfacilityDTO> findByCriteria(MemberExistingfacilityCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MemberExistingfacility> specification = createSpecification(criteria);
        return memberExistingfacilityRepository.findAll(specification, page).map(memberExistingfacilityMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MemberExistingfacilityCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MemberExistingfacility> specification = createSpecification(criteria);
        return memberExistingfacilityRepository.count(specification);
    }

    /**
     * Function to convert {@link MemberExistingfacilityCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MemberExistingfacility> createSpecification(MemberExistingfacilityCriteria criteria) {
        Specification<MemberExistingfacility> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MemberExistingfacility_.id));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getYear(), MemberExistingfacility_.year));
            }
            if (criteria.getFacilityName() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFacilityName(), MemberExistingfacility_.facilityName));
            }
            if (criteria.getFacilitatedFrom() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getFacilitatedFrom(), MemberExistingfacility_.facilitatedFrom));
            }
            if (criteria.getNature() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNature(), MemberExistingfacility_.nature));
            }
            if (criteria.getAmtInLac() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmtInLac(), MemberExistingfacility_.amtInLac));
            }
            if (criteria.getPurpose() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPurpose(), MemberExistingfacility_.purpose));
            }
            if (criteria.getSectionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSectionDate(), MemberExistingfacility_.sectionDate));
            }
            if (criteria.getOutstandingInLac() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getOutstandingInLac(), MemberExistingfacility_.outstandingInLac));
            }

            if (criteria.getEmiAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmiAmt(), MemberExistingfacility_.emiAmt));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), MemberExistingfacility_.status));
            }
            if (criteria.getRating() != null) {
                specification = specification.and(buildSpecification(criteria.getRating(), MemberExistingfacility_.rating));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), MemberExistingfacility_.isDeleted));
            }
            if (criteria.getLastModified() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModified(), MemberExistingfacility_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MemberExistingfacility_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), MemberExistingfacility_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), MemberExistingfacility_.createdOn));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), MemberExistingfacility_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), MemberExistingfacility_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), MemberExistingfacility_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), MemberExistingfacility_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), MemberExistingfacility_.freeField5));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), MemberExistingfacility_.remark));
            }
            if (criteria.getMemberId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMemberId(),
                            root -> root.join(MemberExistingfacility_.member, JoinType.LEFT).get(Member_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
