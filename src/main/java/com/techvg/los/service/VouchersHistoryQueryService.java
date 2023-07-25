package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.VouchersHistory;
import com.techvg.los.repository.VouchersHistoryRepository;
import com.techvg.los.service.criteria.VouchersHistoryCriteria;
import com.techvg.los.service.dto.VouchersHistoryDTO;
import com.techvg.los.service.mapper.VouchersHistoryMapper;
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
 * Service for executing complex queries for {@link VouchersHistory} entities in the database.
 * The main input is a {@link VouchersHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VouchersHistoryDTO} or a {@link Page} of {@link VouchersHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VouchersHistoryQueryService extends QueryService<VouchersHistory> {

    private final Logger log = LoggerFactory.getLogger(VouchersHistoryQueryService.class);

    private final VouchersHistoryRepository vouchersHistoryRepository;

    private final VouchersHistoryMapper vouchersHistoryMapper;

    public VouchersHistoryQueryService(VouchersHistoryRepository vouchersHistoryRepository, VouchersHistoryMapper vouchersHistoryMapper) {
        this.vouchersHistoryRepository = vouchersHistoryRepository;
        this.vouchersHistoryMapper = vouchersHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link VouchersHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VouchersHistoryDTO> findByCriteria(VouchersHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VouchersHistory> specification = createSpecification(criteria);
        return vouchersHistoryMapper.toDto(vouchersHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VouchersHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VouchersHistoryDTO> findByCriteria(VouchersHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VouchersHistory> specification = createSpecification(criteria);
        return vouchersHistoryRepository.findAll(specification, page).map(vouchersHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VouchersHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VouchersHistory> specification = createSpecification(criteria);
        return vouchersHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link VouchersHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VouchersHistory> createSpecification(VouchersHistoryCriteria criteria) {
        Specification<VouchersHistory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VouchersHistory_.id));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), VouchersHistory_.createdOn));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), VouchersHistory_.createdBy));
            }
            if (criteria.getVoucherDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVoucherDate(), VouchersHistory_.voucherDate));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildSpecification(criteria.getCode(), VouchersHistory_.code));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), VouchersHistory_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), VouchersHistory_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), VouchersHistory_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), VouchersHistory_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), VouchersHistory_.freeField5));
            }
            if (criteria.getFreeField6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField6(), VouchersHistory_.freeField6));
            }
        }
        return specification;
    }
}
