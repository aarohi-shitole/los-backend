package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.LoanApplications;
import com.techvg.los.repository.LoanApplicationsRepository;
import com.techvg.los.service.criteria.LoanApplicationsCriteria;
import com.techvg.los.service.dto.LoanApplicationsDTO;
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
 * Service for executing complex queries for {@link LoanApplications} entities
 * in the database. The main input is a {@link LoanApplicationsCriteria} which
 * gets converted to {@link Specification}, in a way that all the filters must
 * apply. It returns a {@link List} of {@link LoanApplicationsDTO} or a
 * {@link Page} of {@link LoanApplicationsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoanApplicationsQueryService extends QueryService<LoanApplications> {

    private final Logger log = LoggerFactory.getLogger(LoanApplicationsQueryService.class);

    private final LoanApplicationsRepository loanApplicationsRepository;

    private final LoanApplicationsMapper loanApplicationsMapper;

    public LoanApplicationsQueryService(
        LoanApplicationsRepository loanApplicationsRepository,
        LoanApplicationsMapper loanApplicationsMapper
    ) {
        this.loanApplicationsRepository = loanApplicationsRepository;
        this.loanApplicationsMapper = loanApplicationsMapper;
    }

    /**
     * Return a {@link List} of {@link LoanApplicationsDTO} which matches the
     * criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LoanApplicationsDTO> findByCriteria(LoanApplicationsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LoanApplications> specification = createSpecification(criteria);
        return loanApplicationsMapper.toDto(loanApplicationsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LoanApplicationsDTO} which matches the
     * criteria from the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanApplicationsDTO> findByCriteria(LoanApplicationsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LoanApplications> specification = createSpecification(criteria);
        return loanApplicationsRepository.findAll(specification, page).map(loanApplicationsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LoanApplicationsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LoanApplications> specification = createSpecification(criteria);
        return loanApplicationsRepository.count(specification);
    }

    /**
     * Function to convert {@link LoanApplicationsCriteria} to a
     * {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LoanApplications> createSpecification(LoanApplicationsCriteria criteria) {
        Specification<LoanApplications> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LoanApplications_.id));
            }
            if (criteria.getApplicationNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApplicationNo(), LoanApplications_.applicationNo));
            }
            if (criteria.getDemandAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDemandAmount(), LoanApplications_.demandAmount));
            }
            if (criteria.getLoanPurpose() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoanPurpose(), LoanApplications_.loanPurpose));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), LoanApplications_.status));
            }
            if (criteria.getDemandedLandAreaInHector() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getDemandedLandAreaInHector(), LoanApplications_.demandedLandAreaInHector)
                    );
            }
            if (criteria.getSeasonOfCropLoan() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSeasonOfCropLoan(), LoanApplications_.seasonOfCropLoan));
            }
            if (criteria.getLoanSteps() != null) {
                specification = specification.and(buildSpecification(criteria.getLoanSteps(), LoanApplications_.loanSteps));
            }
            if (criteria.getIsInsured() != null) {
                specification = specification.and(buildSpecification(criteria.getIsInsured(), LoanApplications_.isInsured));
            }
            if (criteria.getLoanBenefitingArea() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLoanBenefitingArea(), LoanApplications_.loanBenefitingArea));
            }
            if (criteria.getCostOfInvestment() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCostOfInvestment(), LoanApplications_.costOfInvestment));
            }
            if (criteria.getMortgageDeedNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMortgageDeedNo(), LoanApplications_.mortgageDeedNo));
            }
            if (criteria.getResolutionNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getResolutionNo(), LoanApplications_.resolutionNo));
            }
            if (criteria.getMortgageDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMortgageDate(), LoanApplications_.mortgageDate));
            }

            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), LoanApplications_.createdOn));
            }
            if (criteria.getExtentMorgageValue() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExtentMorgageValue(), LoanApplications_.extentMorgageValue));
            }
            if (criteria.getDownPaymentAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDownPaymentAmt(), LoanApplications_.downPaymentAmt));
            }
            if (criteria.getLtvRatio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLtvRatio(), LoanApplications_.ltvRatio));
            }
            if (criteria.getProcessingFee() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcessingFee(), LoanApplications_.processingFee));
            }
            if (criteria.getPenalInterest() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPenalInterest(), LoanApplications_.penalInterest));
            }
            if (criteria.getMoratorium() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMoratorium(), LoanApplications_.moratorium));
            }
            if (criteria.getRoi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRoi(), LoanApplications_.roi));
            }
            if (criteria.getCommityAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCommityAmt(), LoanApplications_.commityAmt));
            }
            if (criteria.getCommityRoi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCommityRoi(), LoanApplications_.commityRoi));
            }
            if (criteria.getSanctionAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSanctionAmt(), LoanApplications_.sanctionAmt));
            }
            if (criteria.getSanctionRoi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSanctionRoi(), LoanApplications_.sanctionRoi));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), LoanApplications_.year));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), LoanApplications_.remark));
            }
            if (criteria.getSecurityDocUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSecurityDocUrl(), LoanApplications_.securityDocUrl));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), LoanApplications_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), LoanApplications_.lastModifiedBy));
            }
            if (criteria.getMargin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMargin(), LoanApplications_.margin));
            }
            if (criteria.getSecurityOffered() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSecurityOffered(), LoanApplications_.securityOffered));
            }
            if (criteria.getDirectorMeetDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getDirectorMeetDate(), LoanApplications_.directorMeetDate));
            }
            if (criteria.getCommityMeetDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getCommityMeetDate(), LoanApplications_.commityMeetDate));
            }
            if (criteria.getDirectorAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDirectorAmount(), LoanApplications_.directorAmount));
            }
            if (criteria.getDirectorRoi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDirectorRoi(), LoanApplications_.directorRoi));
            }
            if (criteria.getSanctionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSanctionDate(), LoanApplications_.sanctionDate));
            }
            // if (criteria.getMemberId() != null) {
            // specification =
            // specification.and(
            // buildSpecification(
            // criteria.getMemberId(),
            // root -> root.join(LoanApplications_.member, JoinType.LEFT).get(Member_.id)
            // )
            // );
            // }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(LoanApplications_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(LoanApplications_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }

            if (criteria.getBranchId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBranchId(),
                            root -> root.join(LoanApplications_.branch, JoinType.LEFT).get(Branch_.id)
                        )
                    );
            }
            if (criteria.getAssignedTo() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAssignedTo(),
                            root -> root.join(LoanApplications_.assignedTo, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getAssignedFrom() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAssignedFrom(),
                            root -> root.join(LoanApplications_.assignedFrom, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getHasSubmitLegal() != null) {
                specification = specification.and(buildSpecification(criteria.getHasSubmitLegal(), LoanApplications_.hasSubmitLegal));
            }
            if (criteria.getHasReceiveLegal() != null) {
                specification = specification.and(buildSpecification(criteria.getHasReceiveLegal(), LoanApplications_.hasReceiveLegal));
            }
            if (criteria.getHasRequiredDirRec() != null) {
                specification = specification.and(buildSpecification(criteria.getHasRequiredDirRec(), LoanApplications_.hasRequiredDirRec));
            }
        }
        return specification;
    }

    public List<LoanApplicationsDTO> findLoansByMemberId(Long memberId) {
        return loanApplicationsMapper.toDto(loanApplicationsRepository.findLoansByMemberId(memberId));
    }
}
