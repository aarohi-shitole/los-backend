package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.Product;
import com.techvg.los.repository.ProductRepository;
import com.techvg.los.service.criteria.ProductCriteria;
import com.techvg.los.service.dto.ProductDTO;
import com.techvg.los.service.mapper.ProductMapper;
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
 * Service for executing complex queries for {@link Product} entities in the database.
 * The main input is a {@link ProductCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductDTO} or a {@link Page} of {@link ProductDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductQueryService extends QueryService<Product> {

    private final Logger log = LoggerFactory.getLogger(ProductQueryService.class);

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductQueryService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Return a {@link List} of {@link ProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> findByCriteria(ProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productMapper.toDto(productRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> findByCriteria(ProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification, page).map(productMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Product> createSpecification(ProductCriteria criteria) {
        Specification<Product> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Product_.id));
            }
            if (criteria.getProdCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProdCode(), Product_.prodCode));
            }
            if (criteria.getProdName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProdName(), Product_.prodName));
            }
            if (criteria.getBpiTreatmentFlag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBpiTreatmentFlag(), Product_.bpiTreatmentFlag));
            }
            if (criteria.getAmortizationMethod() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAmortizationMethod(), Product_.amortizationMethod));
            }
            if (criteria.getAmortizationType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAmortizationType(), Product_.amortizationType));
            }
            if (criteria.getCompoundingFreq() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCompoundingFreq(), Product_.compoundingFreq));
            }
            if (criteria.getEmiRounding() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmiRounding(), Product_.emiRounding));
            }
            if (criteria.getLastRowEMIThreshold() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastRowEMIThreshold(), Product_.lastRowEMIThreshold));
            }
            if (criteria.getGraceDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGraceDays(), Product_.graceDays));
            }
            if (criteria.getReschLockinPeriod() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReschLockinPeriod(), Product_.reschLockinPeriod));
            }
            if (criteria.getPrepayAfterInstNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrepayAfterInstNo(), Product_.prepayAfterInstNo));
            }
            if (criteria.getPrepayBeforeInstNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrepayBeforeInstNo(), Product_.prepayBeforeInstNo));
            }
            if (criteria.getMinInstallmentGapBetPrepay() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getMinInstallmentGapBetPrepay(), Product_.minInstallmentGapBetPrepay)
                    );
            }
            if (criteria.getMinPrepayAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinPrepayAmount(), Product_.minPrepayAmount));
            }
            if (criteria.getForecloseAfterInstNo() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getForecloseAfterInstNo(), Product_.forecloseAfterInstNo));
            }
            if (criteria.getForecloseBeforeInstaNo() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getForecloseBeforeInstaNo(), Product_.forecloseBeforeInstaNo));
            }
            if (criteria.getMinTenor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinTenor(), Product_.minTenor));
            }
            if (criteria.getMaxTenor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxTenor(), Product_.maxTenor));
            }
            if (criteria.getMinInstallmentAmount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMinInstallmentAmount(), Product_.minInstallmentAmount));
            }
            if (criteria.getMaxInstallmentAmount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getMaxInstallmentAmount(), Product_.maxInstallmentAmount));
            }
            if (criteria.getDropLineAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDropLineAmount(), Product_.dropLineAmount));
            }
            if (criteria.getDropLineODYN() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDropLineODYN(), Product_.dropLineODYN));
            }
            if (criteria.getDropLinePerc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDropLinePerc(), Product_.dropLinePerc));
            }
            if (criteria.getDropMode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDropMode(), Product_.dropMode));
            }
            if (criteria.getDropLIneFreq() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDropLIneFreq(), Product_.dropLIneFreq));
            }
            if (criteria.getDropLineCycleDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDropLineCycleDay(), Product_.dropLineCycleDay));
            }
            if (criteria.getRoi() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRoi(), Product_.roi));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), Product_.isDeleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Product_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Product_.lastModifiedBy));
            }
            if (criteria.getProdColour() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProdColour(), Product_.prodColour));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), Product_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), Product_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), Product_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), Product_.freeField5));
            }
            if (criteria.getProdIconUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProdIconUrl(), Product_.prodIconUrl));
            }
            if (criteria.getLoanCatagoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanCatagoryId(),
                            root -> root.join(Product_.loanCatagory, JoinType.LEFT).get(LoanCatagory_.id)
                        )
                    );
            }
            if (criteria.getOrganisationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganisationId(),
                            root -> root.join(Product_.organisation, JoinType.LEFT).get(Organisation_.id)
                        )
                    );
            }
            if (criteria.getLedgerAccountsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLedgerAccountsId(),
                            root -> root.join(Product_.ledgerAccounts, JoinType.LEFT).get(LedgerAccounts_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
