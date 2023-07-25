package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.AccountHeadCode;
import com.techvg.los.repository.AccountHeadCodeRepository;
import com.techvg.los.service.criteria.AccountHeadCodeCriteria;
import com.techvg.los.service.dto.AccountHeadCodeDTO;
import com.techvg.los.service.mapper.AccountHeadCodeMapper;
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
 * Service for executing complex queries for {@link AccountHeadCode} entities in the database.
 * The main input is a {@link AccountHeadCodeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AccountHeadCodeDTO} or a {@link Page} of {@link AccountHeadCodeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AccountHeadCodeQueryService extends QueryService<AccountHeadCode> {

    private final Logger log = LoggerFactory.getLogger(AccountHeadCodeQueryService.class);

    private final AccountHeadCodeRepository accountHeadCodeRepository;

    private final AccountHeadCodeMapper accountHeadCodeMapper;

    public AccountHeadCodeQueryService(AccountHeadCodeRepository accountHeadCodeRepository, AccountHeadCodeMapper accountHeadCodeMapper) {
        this.accountHeadCodeRepository = accountHeadCodeRepository;
        this.accountHeadCodeMapper = accountHeadCodeMapper;
    }

    /**
     * Return a {@link List} of {@link AccountHeadCodeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AccountHeadCodeDTO> findByCriteria(AccountHeadCodeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AccountHeadCode> specification = createSpecification(criteria);
        return accountHeadCodeMapper.toDto(accountHeadCodeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AccountHeadCodeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AccountHeadCodeDTO> findByCriteria(AccountHeadCodeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AccountHeadCode> specification = createSpecification(criteria);
        return accountHeadCodeRepository.findAll(specification, page).map(accountHeadCodeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AccountHeadCodeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AccountHeadCode> specification = createSpecification(criteria);
        return accountHeadCodeRepository.count(specification);
    }

    /**
     * Function to convert {@link AccountHeadCodeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AccountHeadCode> createSpecification(AccountHeadCodeCriteria criteria) {
        Specification<AccountHeadCode> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AccountHeadCode_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), AccountHeadCode_.type));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), AccountHeadCode_.value));
            }
            if (criteria.getHeadCodeName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHeadCodeName(), AccountHeadCode_.headCodeName));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), AccountHeadCode_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), AccountHeadCode_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), AccountHeadCode_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), AccountHeadCode_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), AccountHeadCode_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), AccountHeadCode_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), AccountHeadCode_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), AccountHeadCode_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), AccountHeadCode_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), AccountHeadCode_.freeField5));
            }
            if (criteria.getLedgerAccountsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLedgerAccountsId(),
                            root -> root.join(AccountHeadCode_.ledgerAccounts, JoinType.LEFT).get(LedgerAccounts_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
