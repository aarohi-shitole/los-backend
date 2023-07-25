package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.LoanDisbursement;
import com.techvg.los.repository.LoanDisbursementRepository;
import com.techvg.los.service.criteria.LoanDisbursementCriteria;
import com.techvg.los.service.dto.LoanDisbursementDTO;
import com.techvg.los.service.mapper.LoanDisbursementMapper;
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
 * Service for executing complex queries for {@link LoanDisbursement} entities in the database.
 * The main input is a {@link LoanDisbursementCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LoanDisbursementDTO} or a {@link Page} of {@link LoanDisbursementDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoanDisbursementQueryService extends QueryService<LoanDisbursement> {

    private final Logger log = LoggerFactory.getLogger(LoanDisbursementQueryService.class);

    private final LoanDisbursementRepository loanDisbursementRepository;

    private final LoanDisbursementMapper loanDisbursementMapper;

    public LoanDisbursementQueryService(
        LoanDisbursementRepository loanDisbursementRepository,
        LoanDisbursementMapper loanDisbursementMapper
    ) {
        this.loanDisbursementRepository = loanDisbursementRepository;
        this.loanDisbursementMapper = loanDisbursementMapper;
    }

    /**
     * Return a {@link List} of {@link LoanDisbursementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LoanDisbursementDTO> findByCriteria(LoanDisbursementCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LoanDisbursement> specification = createSpecification(criteria);
        return loanDisbursementMapper.toDto(loanDisbursementRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LoanDisbursementDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanDisbursementDTO> findByCriteria(LoanDisbursementCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LoanDisbursement> specification = createSpecification(criteria);
        return loanDisbursementRepository.findAll(specification, page).map(loanDisbursementMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LoanDisbursementCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LoanDisbursement> specification = createSpecification(criteria);
        return loanDisbursementRepository.count(specification);
    }

    /**
     * Function to convert {@link LoanDisbursementCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LoanDisbursement> createSpecification(LoanDisbursementCriteria criteria) {
        Specification<LoanDisbursement> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LoanDisbursement_.id));
            }
            if (criteria.getDtDisbDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDtDisbDate(), LoanDisbursement_.dtDisbDate));
            }
            if (criteria.getAccountNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNo(), LoanDisbursement_.accountNo));
            }
            if (criteria.getIfscCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIfscCode(), LoanDisbursement_.ifscCode));
            }
            if (criteria.getDisbAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisbAmount(), LoanDisbursement_.disbAmount));
            }
            if (criteria.getDisbuNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisbuNumber(), LoanDisbursement_.disbuNumber));
            }
            if (criteria.getDisbursementStatus() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDisbursementStatus(), LoanDisbursement_.disbursementStatus));
            }
            if (criteria.getPaymentMode() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentMode(), LoanDisbursement_.paymentMode));
            }
            if (criteria.getUtrNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUtrNo(), LoanDisbursement_.utrNo));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), LoanDisbursement_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), LoanDisbursement_.lastModifiedBy));
            }
            if (criteria.getSanctionAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSanctionAmt(), LoanDisbursement_.sanctionAmt));
            }
            if (criteria.getNumOfInstalls() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumOfInstalls(), LoanDisbursement_.numOfInstalls));
            }
            if (criteria.getInstallmentAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInstallmentAmt(), LoanDisbursement_.installmentAmt));
            }
            if (criteria.getFirstInstDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFirstInstDate(), LoanDisbursement_.firstInstDate));
            }
            if (criteria.getSecondInstDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSecondInstDate(), LoanDisbursement_.secondInstDate));
            }
            if (criteria.getRoi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRoi(), LoanDisbursement_.roi));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), LoanDisbursement_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), LoanDisbursement_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), LoanDisbursement_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), LoanDisbursement_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), LoanDisbursement_.freeField5));
            }
            if (criteria.getFreeField6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField6(), LoanDisbursement_.freeField6));
            }

            if (criteria.getLoanApplicationNo() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLoanApplicationNo(), LoanDisbursement_.loanApplicationNo));
            }

            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(LoanDisbursement_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(LoanDisbursement_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getLoanAccountId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanAccountId(),
                            root -> root.join(LoanDisbursement_.loanAccount, JoinType.LEFT).get(LoanAccount_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
