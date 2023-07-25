package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.LedgerAccounts;
import com.techvg.los.repository.LedgerAccountsRepository;
import com.techvg.los.service.criteria.LedgerAccountsCriteria;
import com.techvg.los.service.dto.LedgerAccountsDTO;
import com.techvg.los.service.mapper.LedgerAccountsMapper;
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
 * Service for executing complex queries for {@link LedgerAccounts} entities in the database.
 * The main input is a {@link LedgerAccountsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LedgerAccountsDTO} or a {@link Page} of {@link LedgerAccountsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LedgerAccountsQueryService extends QueryService<LedgerAccounts> {

    private final Logger log = LoggerFactory.getLogger(LedgerAccountsQueryService.class);

    private final LedgerAccountsRepository ledgerAccountsRepository;

    private final LedgerAccountsMapper ledgerAccountsMapper;

    public LedgerAccountsQueryService(LedgerAccountsRepository ledgerAccountsRepository, LedgerAccountsMapper ledgerAccountsMapper) {
        this.ledgerAccountsRepository = ledgerAccountsRepository;
        this.ledgerAccountsMapper = ledgerAccountsMapper;
    }

    /**
     * Return a {@link List} of {@link LedgerAccountsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LedgerAccountsDTO> findByCriteria(LedgerAccountsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LedgerAccounts> specification = createSpecification(criteria);
        return ledgerAccountsMapper.toDto(ledgerAccountsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LedgerAccountsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LedgerAccountsDTO> findByCriteria(LedgerAccountsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LedgerAccounts> specification = createSpecification(criteria);
        return ledgerAccountsRepository.findAll(specification, page).map(ledgerAccountsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LedgerAccountsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LedgerAccounts> specification = createSpecification(criteria);
        return ledgerAccountsRepository.count(specification);
    }

    /**
     * Function to convert {@link LedgerAccountsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LedgerAccounts> createSpecification(LedgerAccountsCriteria criteria) {
        Specification<LedgerAccounts> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LedgerAccounts_.id));
            }
            if (criteria.getAccountNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountNo(), LedgerAccounts_.accountNo));
            }
            if (criteria.getAccountName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountName(), LedgerAccounts_.accountName));
            }
            if (criteria.getAccBalance() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccBalance(), LedgerAccounts_.accBalance));
            }
            if (criteria.getAccHeadCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccHeadCode(), LedgerAccounts_.accHeadCode));
            }
            if (criteria.getLedgerCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLedgerCode(), LedgerAccounts_.ledgerCode));
            }
            if (criteria.getAppCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAppCode(), LedgerAccounts_.appCode));
            }
            if (criteria.getLedgerClassification() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getLedgerClassification(), LedgerAccounts_.ledgerClassification));
            }
            if (criteria.getLevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLevel(), LedgerAccounts_.level));
            }
            if (criteria.getYear() != null) {
                specification = specification.and(buildStringSpecification(criteria.getYear(), LedgerAccounts_.year));
            }
            if (criteria.getAccountType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountType(), LedgerAccounts_.accountType));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), LedgerAccounts_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), LedgerAccounts_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), LedgerAccounts_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), LedgerAccounts_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), LedgerAccounts_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), LedgerAccounts_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), LedgerAccounts_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), LedgerAccounts_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), LedgerAccounts_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), LedgerAccounts_.freeField5));
            }
            if (criteria.getBranchId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getBranchId(), root -> root.join(LedgerAccounts_.branch, JoinType.LEFT).get(Branch_.id))
                    );
            }
            if (criteria.getLedgerAccountsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLedgerAccountsId(),
                            root -> root.join(LedgerAccounts_.ledgerAccounts, JoinType.LEFT).get(LedgerAccounts_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
