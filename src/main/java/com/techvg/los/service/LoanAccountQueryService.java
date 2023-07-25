package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.repository.LoanAccountRepository;
import com.techvg.los.service.criteria.LoanAccountCriteria;
import com.techvg.los.service.dto.LoanAccountDTO;
import com.techvg.los.service.mapper.LoanAccountMapper;
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
 * Service for executing complex queries for {@link LoanAccount} entities in the database.
 * The main input is a {@link LoanAccountCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LoanAccountDTO} or a {@link Page} of {@link LoanAccountDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoanAccountQueryService extends QueryService<LoanAccount> {

    private final Logger log = LoggerFactory.getLogger(LoanAccountQueryService.class);

    private final LoanAccountRepository loanAccountRepository;

    private final LoanAccountMapper loanAccountMapper;

    public LoanAccountQueryService(LoanAccountRepository loanAccountRepository, LoanAccountMapper loanAccountMapper) {
        this.loanAccountRepository = loanAccountRepository;
        this.loanAccountMapper = loanAccountMapper;
    }

    /**
     * Return a {@link List} of {@link LoanAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LoanAccountDTO> findByCriteria(LoanAccountCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LoanAccount> specification = createSpecification(criteria);
        return loanAccountMapper.toDto(loanAccountRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LoanAccountDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanAccountDTO> findByCriteria(LoanAccountCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LoanAccount> specification = createSpecification(criteria);
        return loanAccountRepository.findAll(specification, page).map(loanAccountMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LoanAccountCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LoanAccount> specification = createSpecification(criteria);
        return loanAccountRepository.count(specification);
    }

    /**
     * Function to convert {@link LoanAccountCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LoanAccount> createSpecification(LoanAccountCriteria criteria) {
        Specification<LoanAccount> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LoanAccount_.id));
            }
            if (criteria.getLoanAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanAmount(), LoanAccount_.loanAmount));
            }
            if (criteria.getLoanAccountNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoanAccountNo(), LoanAccount_.loanAccountNo));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), LoanAccount_.status));
            }
            if (criteria.getLoanStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanStartDate(), LoanAccount_.loanStartDate));
            }
            if (criteria.getLoanEndDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanEndDate(), LoanAccount_.loanEndDate));
            }
            if (criteria.getDisbursementDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisbursementDate(), LoanAccount_.disbursementDate));
            }
            if (criteria.getDisbursementAllowance() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getDisbursementAllowance(), LoanAccount_.disbursementAllowance));
            }
            if (criteria.getLoanPlannedClosureDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLoanPlannedClosureDate(), LoanAccount_.loanPlannedClosureDate));
            }
            if (criteria.getLoanCloserDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanCloserDate(), LoanAccount_.loanCloserDate));
            }
            if (criteria.getEmiStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmiStartDate(), LoanAccount_.emiStartDate));
            }
            if (criteria.getLoanNpaClass() != null) {
                specification = specification.and(buildSpecification(criteria.getLoanNpaClass(), LoanAccount_.loanNpaClass));
            }
            if (criteria.getParentAccHeadCode() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getParentAccHeadCode(), LoanAccount_.parentAccHeadCode));
            }
            if (criteria.getLoanAccountName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLoanAccountName(), LoanAccount_.loanAccountName));
            }
            if (criteria.getDisbursementAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisbursementAmt(), LoanAccount_.disbursementAmt));
            }
            if (criteria.getDisbursementStatus() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDisbursementStatus(), LoanAccount_.disbursementStatus));
            }
            if (criteria.getRepaymentPeriod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRepaymentPeriod(), LoanAccount_.repaymentPeriod));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), LoanAccount_.year));
            }
            if (criteria.getProcessingFee() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProcessingFee(), LoanAccount_.processingFee));
            }
            if (criteria.getMoratorium() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMoratorium(), LoanAccount_.moratorium));
            }
            if (criteria.getRoi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRoi(), LoanAccount_.roi));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), LoanAccount_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), LoanAccount_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), LoanAccount_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), LoanAccount_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), LoanAccount_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), LoanAccount_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), LoanAccount_.freeField5));
            }
            if (criteria.getFreeField6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField6(), LoanAccount_.freeField6));
            }
            if (criteria.getLoanApplicationsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanApplicationsId(),
                            root -> root.join(LoanAccount_.loanApplications, JoinType.LEFT).get(LoanApplications_.id)
                        )
                    );
            }
            if (criteria.getMemberId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getMemberId(), root -> root.join(LoanAccount_.member, JoinType.LEFT).get(Member_.id))
                    );
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProductId(), root -> root.join(LoanAccount_.product, JoinType.LEFT).get(Product_.id))
                    );
            }

            if (criteria.getBranchId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getBranchId(), root -> root.join(LoanAccount_.branch, JoinType.LEFT).get(Branch_.id))
                    );
            }
        }
        return specification;
    }
}
