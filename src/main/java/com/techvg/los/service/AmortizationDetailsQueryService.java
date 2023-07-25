package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.AmortizationDetails;
import com.techvg.los.repository.AmortizationDetailsRepository;
import com.techvg.los.service.criteria.AmortizationDetailsCriteria;
import com.techvg.los.service.dto.AmortizationDetailsDTO;
import com.techvg.los.service.mapper.AmortizationDetailsMapper;
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
 * Service for executing complex queries for {@link AmortizationDetails} entities in the database.
 * The main input is a {@link AmortizationDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AmortizationDetailsDTO} or a {@link Page} of {@link AmortizationDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AmortizationDetailsQueryService extends QueryService<AmortizationDetails> {

    private final Logger log = LoggerFactory.getLogger(AmortizationDetailsQueryService.class);

    private final AmortizationDetailsRepository amortizationDetailsRepository;

    private final AmortizationDetailsMapper amortizationDetailsMapper;

    public AmortizationDetailsQueryService(
        AmortizationDetailsRepository amortizationDetailsRepository,
        AmortizationDetailsMapper amortizationDetailsMapper
    ) {
        this.amortizationDetailsRepository = amortizationDetailsRepository;
        this.amortizationDetailsMapper = amortizationDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link AmortizationDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AmortizationDetailsDTO> findByCriteria(AmortizationDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AmortizationDetails> specification = createSpecification(criteria);
        return amortizationDetailsMapper.toDto(amortizationDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AmortizationDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AmortizationDetailsDTO> findByCriteria(AmortizationDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AmortizationDetails> specification = createSpecification(criteria);
        return amortizationDetailsRepository.findAll(specification, page).map(amortizationDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AmortizationDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AmortizationDetails> specification = createSpecification(criteria);
        return amortizationDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link AmortizationDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AmortizationDetails> createSpecification(AmortizationDetailsCriteria criteria) {
        Specification<AmortizationDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AmortizationDetails_.id));
            }
            if (criteria.getInstallmentDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getInstallmentDate(), AmortizationDetails_.installmentDate));
            }
            if (criteria.getTotalOutstandingPrincipleAmt() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(
                            criteria.getTotalOutstandingPrincipleAmt(),
                            AmortizationDetails_.totalOutstandingPrincipleAmt
                        )
                    );
            }
            if (criteria.getTotalOutstandngInterestAmt() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getTotalOutstandngInterestAmt(), AmortizationDetails_.totalOutstandngInterestAmt)
                    );
            }
            if (criteria.getPaidPrincipleAmt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPaidPrincipleAmt(), AmortizationDetails_.paidPrincipleAmt));
            }
            if (criteria.getPaidInterestAmt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getPaidInterestAmt(), AmortizationDetails_.paidInterestAmt));
            }
            if (criteria.getBalPrincipleAmt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getBalPrincipleAmt(), AmortizationDetails_.balPrincipleAmt));
            }
            if (criteria.getBalInterestAmt() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getBalInterestAmt(), AmortizationDetails_.balInterestAmt));
            }
            if (criteria.getLoanEmiAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLoanEmiAmt(), AmortizationDetails_.loanEmiAmt));
            }
            if (criteria.getPrincipleEMI() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrincipleEMI(), AmortizationDetails_.principleEMI));
            }
            if (criteria.getRoi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRoi(), AmortizationDetails_.roi));
            }
            if (criteria.getPaymentStatus() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getPaymentStatus(), AmortizationDetails_.paymentStatus));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), AmortizationDetails_.year));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), AmortizationDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), AmortizationDetails_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), AmortizationDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), AmortizationDetails_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), AmortizationDetails_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), AmortizationDetails_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), AmortizationDetails_.freeField5));
            }
            if (criteria.getFreeField6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField6(), AmortizationDetails_.freeField6));
            }
            if (criteria.getLoanAccountId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanAccountId(),
                            root -> root.join(AmortizationDetails_.loanAccount, JoinType.LEFT).get(LoanAccount_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
