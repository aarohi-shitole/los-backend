package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.MemberLimit;
import com.techvg.los.repository.MemberLimitRepository;
import com.techvg.los.service.criteria.MemberLimitCriteria;
import com.techvg.los.service.dto.MemberLimitDTO;
import com.techvg.los.service.mapper.MemberLimitMapper;
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
 * Service for executing complex queries for {@link MemberLimit} entities in the database.
 * The main input is a {@link MemberLimitCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MemberLimitDTO} or a {@link Page} of {@link MemberLimitDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MemberLimitQueryService extends QueryService<MemberLimit> {

    private final Logger log = LoggerFactory.getLogger(MemberLimitQueryService.class);

    private final MemberLimitRepository memberLimitRepository;

    private final MemberLimitMapper memberLimitMapper;

    public MemberLimitQueryService(MemberLimitRepository memberLimitRepository, MemberLimitMapper memberLimitMapper) {
        this.memberLimitRepository = memberLimitRepository;
        this.memberLimitMapper = memberLimitMapper;
    }

    /**
     * Return a {@link List} of {@link MemberLimitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MemberLimitDTO> findByCriteria(MemberLimitCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MemberLimit> specification = createSpecification(criteria);
        return memberLimitMapper.toDto(memberLimitRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MemberLimitDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberLimitDTO> findByCriteria(MemberLimitCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MemberLimit> specification = createSpecification(criteria);
        return memberLimitRepository.findAll(specification, page).map(memberLimitMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MemberLimitCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MemberLimit> specification = createSpecification(criteria);
        return memberLimitRepository.count(specification);
    }

    /**
     * Function to convert {@link MemberLimitCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MemberLimit> createSpecification(MemberLimitCriteria criteria) {
        Specification<MemberLimit> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MemberLimit_.id));
            }
            if (criteria.getLimitSanctionAmount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLimitSanctionAmount(), MemberLimit_.limitSanctionAmount));
            }
            if (criteria.getDtLimitSanctioned() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDtLimitSanctioned(), MemberLimit_.dtLimitSanctioned));
            }
            if (criteria.getDtLimitExpired() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDtLimitExpired(), MemberLimit_.dtLimitExpired));
            }
            if (criteria.getPurpose() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPurpose(), MemberLimit_.purpose));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), MemberLimit_.isDeleted));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), MemberLimit_.isActive));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), MemberLimit_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MemberLimit_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), MemberLimit_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), MemberLimit_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), MemberLimit_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), MemberLimit_.freeField4));
            }
        }
        return specification;
    }
}
