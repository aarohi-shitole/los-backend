package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.MasterAgreement;
import com.techvg.los.repository.MasterAgreementRepository;
import com.techvg.los.service.criteria.MasterAgreementCriteria;
import com.techvg.los.service.dto.MasterAgreementDTO;
import com.techvg.los.service.mapper.MasterAgreementMapper;
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
 * Service for executing complex queries for {@link MasterAgreement} entities in the database.
 * The main input is a {@link MasterAgreementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MasterAgreementDTO} or a {@link Page} of {@link MasterAgreementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MasterAgreementQueryService extends QueryService<MasterAgreement> {

    private final Logger log = LoggerFactory.getLogger(MasterAgreementQueryService.class);

    private final MasterAgreementRepository masterAgreementRepository;

    private final MasterAgreementMapper masterAgreementMapper;

    public MasterAgreementQueryService(MasterAgreementRepository masterAgreementRepository, MasterAgreementMapper masterAgreementMapper) {
        this.masterAgreementRepository = masterAgreementRepository;
        this.masterAgreementMapper = masterAgreementMapper;
    }

    /**
     * Return a {@link List} of {@link MasterAgreementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MasterAgreementDTO> findByCriteria(MasterAgreementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MasterAgreement> specification = createSpecification(criteria);
        return masterAgreementMapper.toDto(masterAgreementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MasterAgreementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MasterAgreementDTO> findByCriteria(MasterAgreementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MasterAgreement> specification = createSpecification(criteria);
        return masterAgreementRepository.findAll(specification, page).map(masterAgreementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MasterAgreementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MasterAgreement> specification = createSpecification(criteria);
        return masterAgreementRepository.count(specification);
    }

    /**
     * Function to convert {@link MasterAgreementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MasterAgreement> createSpecification(MasterAgreementCriteria criteria) {
        Specification<MasterAgreement> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MasterAgreement_.id));
            }
            if (criteria.getMemberId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMemberId(), MasterAgreement_.memberId));
            }
            if (criteria.getPortfolioCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPortfolioCode(), MasterAgreement_.portfolioCode));
            }
            if (criteria.getProductCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductCode(), MasterAgreement_.productCode));
            }
            if (criteria.getHomeBranch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHomeBranch(), MasterAgreement_.homeBranch));
            }
            if (criteria.getServBranch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServBranch(), MasterAgreement_.servBranch));
            }
            if (criteria.getHomeState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHomeState(), MasterAgreement_.homeState));
            }
            if (criteria.getServState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServState(), MasterAgreement_.servState));
            }
            if (criteria.getGstExempted() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGstExempted(), MasterAgreement_.gstExempted));
            }
            if (criteria.getPrefRepayMode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPrefRepayMode(), MasterAgreement_.prefRepayMode));
            }
            if (criteria.getTdsCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTdsCode(), MasterAgreement_.tdsCode));
            }
            if (criteria.getTdsRate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTdsRate(), MasterAgreement_.tdsRate));
            }
            if (criteria.getSanctionedAmount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSanctionedAmount(), MasterAgreement_.sanctionedAmount));
            }
            if (criteria.getOriginationApplnNo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getOriginationApplnNo(), MasterAgreement_.originationApplnNo));
            }
            if (criteria.getCycleDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCycleDay(), MasterAgreement_.cycleDay));
            }
            if (criteria.getTenor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTenor(), MasterAgreement_.tenor));
            }
            if (criteria.getInterestRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInterestRate(), MasterAgreement_.interestRate));
            }
            if (criteria.getRepayFreq() != null) {
                specification = specification.and(buildSpecification(criteria.getRepayFreq(), MasterAgreement_.repayFreq));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), MasterAgreement_.isDeleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), MasterAgreement_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), MasterAgreement_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), MasterAgreement_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), MasterAgreement_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), MasterAgreement_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), MasterAgreement_.freeField4));
            }
        }
        return specification;
    }
}
