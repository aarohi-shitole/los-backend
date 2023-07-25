package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.repository.RemarkHistoryRepository;
import com.techvg.los.service.criteria.RemarkHistoryCriteria;
import com.techvg.los.service.dto.RemarkHistoryDTO;
import com.techvg.los.service.mapper.RemarkHistoryMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import net.bytebuddy.build.BuildLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link RemarkHistory} entities in
 * the database. The main input is a {@link RemarkHistoryCriteria} which gets
 * converted to {@link Specification}, in a way that all the filters must apply.
 * It returns a {@link List} of {@link RemarkHistoryDTO} or a {@link Page} of
 * {@link RemarkHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RemarkHistoryQueryService extends QueryService<RemarkHistory> {

    private final Logger log = LoggerFactory.getLogger(RemarkHistoryQueryService.class);

    private final RemarkHistoryRepository remarkHistoryRepository;

    private final RemarkHistoryMapper remarkHistoryMapper;

    public RemarkHistoryQueryService(RemarkHistoryRepository remarkHistoryRepository, RemarkHistoryMapper remarkHistoryMapper) {
        this.remarkHistoryRepository = remarkHistoryRepository;
        this.remarkHistoryMapper = remarkHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link RemarkHistoryDTO} which matches the criteria
     * from the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RemarkHistoryDTO> findByCriteria(RemarkHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RemarkHistory> specification = createSpecification(criteria);
        return remarkHistoryMapper.toDto(remarkHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RemarkHistoryDTO} which matches the criteria
     * from the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RemarkHistoryDTO> findByCriteria(RemarkHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RemarkHistory> specification = createSpecification(criteria);
        return remarkHistoryRepository.findAll(specification, page).map(remarkHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RemarkHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RemarkHistory> specification = createSpecification(criteria);
        return remarkHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link RemarkHistoryCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RemarkHistory> createSpecification(RemarkHistoryCriteria criteria) {
        Specification<RemarkHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RemarkHistory_.id));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), RemarkHistory_.remark));
            }
            if (criteria.getLoanAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanAmt(), RemarkHistory_.loanAmt));
            }

            if (criteria.getModifiedAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedAmt(), RemarkHistory_.modifiedAmt));
            }
            if (criteria.getLoanInterest() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanInterest(), RemarkHistory_.loanInterest));
            }
            if (criteria.getModifiedInterest() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getModifiedInterest(), RemarkHistory_.modifiedInterest));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), RemarkHistory_.createdOn));
            }
            if (criteria.getTag() != null) {
                specification = specification.and(buildSpecification(criteria.getTag(), RemarkHistory_.tag));
            }

            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), RemarkHistory_.createdBy));
            }
            if (criteria.getBranch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranch(), RemarkHistory_.branch));
            }
            if (criteria.getApplicationNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApplicationNo(), RemarkHistory_.applicationNo));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), RemarkHistory_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), RemarkHistory_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), RemarkHistory_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), RemarkHistory_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), RemarkHistory_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), RemarkHistory_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), RemarkHistory_.freeField5));
            }
            if (criteria.getValutaionAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValutaionAmt(), RemarkHistory_.valutaionAmt));
            }
            if (criteria.getVerified() != null) {
                specification = specification.and(buildSpecification(criteria.getVerified(), RemarkHistory_.verified));
            }
            if (criteria.getFlag() != null) {
                specification = specification.and(buildSpecification(criteria.getFlag(), RemarkHistory_.flag));
            }
            if (criteria.getRemarkCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarkCode(), RemarkHistory_.remarkCode));
            }
            if (criteria.getRemarkType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarkType(), RemarkHistory_.remarkType));
            }
            if (criteria.getRemarkSubType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarkSubType(), RemarkHistory_.remarkSubType));
            }
            if (criteria.getReferenceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReferenceId(), RemarkHistory_.referenceId));
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(RemarkHistory_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getLoanApplicationsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanApplicationsId(),
                            root -> root.join(RemarkHistory_.loanApplications, JoinType.LEFT).get(LoanApplications_.id)
                        )
                    );
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), RemarkHistory_.isDeleted));
            }
        }
        return specification;
    }
}
