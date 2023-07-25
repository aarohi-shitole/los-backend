package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.LoanRepaymentDetails;
import com.techvg.los.repository.LoanRepaymentDetailsRepository;
import com.techvg.los.service.criteria.LoanRepaymentDetailsCriteria;
import com.techvg.los.service.dto.LoanRepaymentDetailsDTO;
import com.techvg.los.service.mapper.LoanRepaymentDetailsMapper;
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
 * Service for executing complex queries for {@link LoanRepaymentDetails} entities in the database.
 * The main input is a {@link LoanRepaymentDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LoanRepaymentDetailsDTO} or a {@link Page} of {@link LoanRepaymentDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoanRepaymentDetailsQueryService extends QueryService<LoanRepaymentDetails> {

    private final Logger log = LoggerFactory.getLogger(LoanRepaymentDetailsQueryService.class);

    private final LoanRepaymentDetailsRepository loanRepaymentDetailsRepository;

    private final LoanRepaymentDetailsMapper loanRepaymentDetailsMapper;

    public LoanRepaymentDetailsQueryService(
        LoanRepaymentDetailsRepository loanRepaymentDetailsRepository,
        LoanRepaymentDetailsMapper loanRepaymentDetailsMapper
    ) {
        this.loanRepaymentDetailsRepository = loanRepaymentDetailsRepository;
        this.loanRepaymentDetailsMapper = loanRepaymentDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link LoanRepaymentDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LoanRepaymentDetailsDTO> findByCriteria(LoanRepaymentDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LoanRepaymentDetails> specification = createSpecification(criteria);
        return loanRepaymentDetailsMapper.toDto(loanRepaymentDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LoanRepaymentDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanRepaymentDetailsDTO> findByCriteria(LoanRepaymentDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LoanRepaymentDetails> specification = createSpecification(criteria);
        return loanRepaymentDetailsRepository.findAll(specification, page).map(loanRepaymentDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LoanRepaymentDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LoanRepaymentDetails> specification = createSpecification(criteria);
        return loanRepaymentDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link LoanRepaymentDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LoanRepaymentDetails> createSpecification(LoanRepaymentDetailsCriteria criteria) {
        Specification<LoanRepaymentDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LoanRepaymentDetails_.id));
            }
            if (criteria.getRepaymentDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getRepaymentDate(), LoanRepaymentDetails_.repaymentDate));
            }
            if (criteria.getTotalRepaymentAmt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getTotalRepaymentAmt(), LoanRepaymentDetails_.totalRepaymentAmt));
            }
            if (criteria.getInstallmentNumber() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getInstallmentNumber(), LoanRepaymentDetails_.installmentNumber));
            }
            if (criteria.getPrinciplePaidAmt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPrinciplePaidAmt(), LoanRepaymentDetails_.principlePaidAmt));
            }
            if (criteria.getInterestPaidAmt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getInterestPaidAmt(), LoanRepaymentDetails_.interestPaidAmt));
            }
            if (criteria.getSurChargeAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSurChargeAmt(), LoanRepaymentDetails_.surChargeAmt));
            }
            if (criteria.getOverDueAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOverDueAmt(), LoanRepaymentDetails_.overDueAmt));
            }
            if (criteria.getBalanceInterestAmt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getBalanceInterestAmt(), LoanRepaymentDetails_.balanceInterestAmt));
            }
            if (criteria.getBalancePrincipleAmt() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getBalancePrincipleAmt(), LoanRepaymentDetails_.balancePrincipleAmt)
                    );
            }
            if (criteria.getPaymentMode() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentMode(), LoanRepaymentDetails_.paymentMode));
            }
            if (criteria.getForeClosureChargeAmt() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getForeClosureChargeAmt(), LoanRepaymentDetails_.foreClosureChargeAmt)
                    );
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), LoanRepaymentDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), LoanRepaymentDetails_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), LoanRepaymentDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), LoanRepaymentDetails_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), LoanRepaymentDetails_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), LoanRepaymentDetails_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), LoanRepaymentDetails_.freeField5));
            }
            if (criteria.getFreeField6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField6(), LoanRepaymentDetails_.freeField6));
            }
            if (criteria.getLoanAccountId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanAccountId(),
                            root -> root.join(LoanRepaymentDetails_.loanAccount, JoinType.LEFT).get(LoanAccount_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
