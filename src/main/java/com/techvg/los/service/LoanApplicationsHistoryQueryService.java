package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.repository.LoanApplicationsHistoryRepository;
import com.techvg.los.repository.LoanApplicationsRepository;
import com.techvg.los.service.criteria.LoanApplicationsCriteria;
import com.techvg.los.service.criteria.LoanApplicationsHistoryCriteria;
import com.techvg.los.service.dto.LoanApplicationsDTO;
import com.techvg.los.service.dto.LoanApplicationsHistoryDTO;
import com.techvg.los.service.mapper.LoanApplicationsHistoryMapper;
import com.techvg.los.service.mapper.LoanApplicationsMapper;
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
 * Service for executing complex queries for {@link LoanApplicationsHistory} entities in the database.
 * The main input is a {@link LoanApplicationsHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LoanApplicationsHistoryDTO} or a {@link Page} of {@link LoanApplicationsHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoanApplicationsHistoryQueryService extends QueryService<LoanApplicationsHistory> {

    private final Logger log = LoggerFactory.getLogger(LoanApplicationsHistoryQueryService.class);

    private final LoanApplicationsHistoryRepository loanApplicationsHistoryRepository;

    private final LoanApplicationsHistoryMapper loanApplicationsHistoryMapper;

    public LoanApplicationsHistoryQueryService(
        LoanApplicationsHistoryRepository loanApplicationsHistoryRepository,
        LoanApplicationsHistoryMapper loanApplicationsHistoryMapper
    ) {
        this.loanApplicationsHistoryRepository = loanApplicationsHistoryRepository;
        this.loanApplicationsHistoryMapper = loanApplicationsHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link LoanApplicationsHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LoanApplicationsHistoryDTO> findByCriteria(LoanApplicationsHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LoanApplicationsHistory> specification = createSpecification(criteria);
        return loanApplicationsHistoryMapper.toDto(loanApplicationsHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LoanApplicationsHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */

    @Transactional(readOnly = true)
    public Page<LoanApplicationsHistoryDTO> findByCriteria(LoanApplicationsHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LoanApplicationsHistory> specification = createSpecification(criteria);
        return loanApplicationsHistoryRepository.findAll(specification, page).map(loanApplicationsHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LoanApplicationsHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LoanApplicationsHistory> specification = createSpecification(criteria);
        return loanApplicationsHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link LoanApplicationsHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LoanApplicationsHistory> createSpecification(LoanApplicationsHistoryCriteria criteria) {
        Specification<LoanApplicationsHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LoanApplicationsHistory_.id));
            }
            if (criteria.getApplicationNo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getApplicationNo(), LoanApplicationsHistory_.applicationNo));
            }
            if (criteria.getDemandAmount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDemandAmount(), LoanApplicationsHistory_.demandAmount));
            }
            if (criteria.getLoanPurpose() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLoanPurpose(), LoanApplicationsHistory_.loanPurpose));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), LoanApplicationsHistory_.status));
            }
            if (criteria.getDemandedLandAreaInHector() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getDemandedLandAreaInHector(), LoanApplicationsHistory_.demandedLandAreaInHector)
                    );
            }
            if (criteria.getSeasonOfCropLoan() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSeasonOfCropLoan(), LoanApplicationsHistory_.seasonOfCropLoan));
            }
            if (criteria.getLoanSteps() != null) {
                specification = specification.and(buildSpecification(criteria.getLoanSteps(), LoanApplicationsHistory_.loanSteps));
            }
            if (criteria.getIsInsured() != null) {
                specification = specification.and(buildSpecification(criteria.getIsInsured(), LoanApplicationsHistory_.isInsured));
            }
            if (criteria.getLoanBenefitingArea() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getLoanBenefitingArea(), LoanApplicationsHistory_.loanBenefitingArea)
                    );
            }
            if (criteria.getCostOfInvestment() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCostOfInvestment(), LoanApplicationsHistory_.costOfInvestment));
            }
            if (criteria.getMortgageDeedNo() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMortgageDeedNo(), LoanApplicationsHistory_.mortgageDeedNo));
            }
            if (criteria.getMortgageDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMortgageDate(), LoanApplicationsHistory_.mortgageDate));
            }
            if (criteria.getExtentMorgageValue() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getExtentMorgageValue(), LoanApplicationsHistory_.extentMorgageValue)
                    );
            }
            if (criteria.getDownPaymentAmt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDownPaymentAmt(), LoanApplicationsHistory_.downPaymentAmt));
            }
            if (criteria.getLtvRatio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLtvRatio(), LoanApplicationsHistory_.ltvRatio));
            }
            if (criteria.getProcessingFee() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getProcessingFee(), LoanApplicationsHistory_.processingFee));
            }
            if (criteria.getPenalInterest() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPenalInterest(), LoanApplicationsHistory_.penalInterest));
            }
            if (criteria.getMoratorium() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMoratorium(), LoanApplicationsHistory_.moratorium));
            }
            if (criteria.getRoi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRoi(), LoanApplicationsHistory_.roi));
            }
            if (criteria.getCommityAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCommityAmt(), LoanApplicationsHistory_.commityAmt));
            }
            if (criteria.getCommityRoi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCommityRoi(), LoanApplicationsHistory_.commityRoi));
            }
            if (criteria.getSanctionAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSanctionAmt(), LoanApplicationsHistory_.sanctionAmt));
            }
            if (criteria.getSanctionRoi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSanctionRoi(), LoanApplicationsHistory_.sanctionRoi));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), LoanApplicationsHistory_.year));
            }
            if (criteria.getSecurityDocUrl() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSecurityDocUrl(), LoanApplicationsHistory_.securityDocUrl));
            }
            if (criteria.getLastModified() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModified(), LoanApplicationsHistory_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), LoanApplicationsHistory_.lastModifiedBy));
            }
            if (criteria.getMargin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMargin(), LoanApplicationsHistory_.margin));
            }
            if (criteria.getSecurityOffered() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSecurityOffered(), LoanApplicationsHistory_.securityOffered));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), LoanApplicationsHistory_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), LoanApplicationsHistory_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), LoanApplicationsHistory_.freeField5));
            }
            if (criteria.getFreeField6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField6(), LoanApplicationsHistory_.freeField6));
            }
            if (criteria.getFreeField7() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField7(), LoanApplicationsHistory_.freeField7));
            }
            //            if (criteria.getMemberId() != null) {
            //                specification =
            //                    specification.and(
            //                        buildSpecification(
            //                            criteria.getMemberId(),
            //                            root -> root.join(LoanApplicationsHistory_.member, JoinType.LEFT).get(Member_.id)
            //                        )
            //                    );
            //            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(LoanApplicationsHistory_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(LoanApplicationsHistory_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }

            if (criteria.getBranchId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBranchId(),
                            root -> root.join(LoanApplicationsHistory_.branch, JoinType.LEFT).get(Branch_.id)
                        )
                    );
            }

            if (criteria.getLoanApplicationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanApplicationId(),
                            root -> root.join(LoanApplicationsHistory_.loanApplications, JoinType.LEFT).get(LoanApplications_.id)
                        )
                    );
            }

            if (criteria.getAssignedTo() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAssignedTo(),
                            root -> root.join(LoanApplicationsHistory_.assignedTo, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getAssignedFrom() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAssignedFrom(),
                            root -> root.join(LoanApplicationsHistory_.assignedFrom, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
