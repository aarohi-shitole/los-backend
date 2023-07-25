package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.LoanCatagory;
import com.techvg.los.repository.LoanCatagoryRepository;
import com.techvg.los.service.criteria.LoanCatagoryCriteria;
import com.techvg.los.service.dto.LoanCatagoryDTO;
import com.techvg.los.service.mapper.LoanCatagoryMapper;
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
 * Service for executing complex queries for {@link LoanCatagory} entities in the database.
 * The main input is a {@link LoanCatagoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LoanCatagoryDTO} or a {@link Page} of {@link LoanCatagoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LoanCatagoryQueryService extends QueryService<LoanCatagory> {

    private final Logger log = LoggerFactory.getLogger(LoanCatagoryQueryService.class);

    private final LoanCatagoryRepository loanCatagoryRepository;

    private final LoanCatagoryMapper loanCatagoryMapper;

    public LoanCatagoryQueryService(LoanCatagoryRepository loanCatagoryRepository, LoanCatagoryMapper loanCatagoryMapper) {
        this.loanCatagoryRepository = loanCatagoryRepository;
        this.loanCatagoryMapper = loanCatagoryMapper;
    }

    /**
     * Return a {@link List} of {@link LoanCatagoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LoanCatagoryDTO> findByCriteria(LoanCatagoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LoanCatagory> specification = createSpecification(criteria);
        return loanCatagoryMapper.toDto(loanCatagoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LoanCatagoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LoanCatagoryDTO> findByCriteria(LoanCatagoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LoanCatagory> specification = createSpecification(criteria);
        return loanCatagoryRepository.findAll(specification, page).map(loanCatagoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LoanCatagoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LoanCatagory> specification = createSpecification(criteria);
        return loanCatagoryRepository.count(specification);
    }

    /**
     * Function to convert {@link LoanCatagoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<LoanCatagory> createSpecification(LoanCatagoryCriteria criteria) {
        Specification<LoanCatagory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LoanCatagory_.id));
            }
            if (criteria.getProductName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductName(), LoanCatagory_.productName));
            }
            if (criteria.getDecription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDecription(), LoanCatagory_.decription));
            }
            if (criteria.getValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValue(), LoanCatagory_.value));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), LoanCatagory_.code));
            }
            if (criteria.getOffer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOffer(), LoanCatagory_.offer));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), LoanCatagory_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), LoanCatagory_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), LoanCatagory_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), LoanCatagory_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), LoanCatagory_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), LoanCatagory_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), LoanCatagory_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), LoanCatagory_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), LoanCatagory_.freeField4));
            }
        }
        return specification;
    }
}
