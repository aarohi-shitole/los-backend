package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.VoucherDetails;
import com.techvg.los.repository.VoucherDetailsRepository;
import com.techvg.los.service.criteria.VoucherDetailsCriteria;
import com.techvg.los.service.dto.VoucherDetailsDTO;
import com.techvg.los.service.mapper.VoucherDetailsMapper;
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
 * Service for executing complex queries for {@link VoucherDetails} entities in the database.
 * The main input is a {@link VoucherDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VoucherDetailsDTO} or a {@link Page} of {@link VoucherDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VoucherDetailsQueryService extends QueryService<VoucherDetails> {

    private final Logger log = LoggerFactory.getLogger(VoucherDetailsQueryService.class);

    private final VoucherDetailsRepository voucherDetailsRepository;

    private final VoucherDetailsMapper voucherDetailsMapper;

    public VoucherDetailsQueryService(VoucherDetailsRepository voucherDetailsRepository, VoucherDetailsMapper voucherDetailsMapper) {
        this.voucherDetailsRepository = voucherDetailsRepository;
        this.voucherDetailsMapper = voucherDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link VoucherDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VoucherDetailsDTO> findByCriteria(VoucherDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<VoucherDetails> specification = createSpecification(criteria);
        return voucherDetailsMapper.toDto(voucherDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VoucherDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VoucherDetailsDTO> findByCriteria(VoucherDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<VoucherDetails> specification = createSpecification(criteria);
        return voucherDetailsRepository.findAll(specification, page).map(voucherDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VoucherDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<VoucherDetails> specification = createSpecification(criteria);
        return voucherDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link VoucherDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<VoucherDetails> createSpecification(VoucherDetailsCriteria criteria) {
        Specification<VoucherDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), VoucherDetails_.id));
            }
            if (criteria.getAccHeadCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccHeadCode(), VoucherDetails_.accHeadCode));
            }
            if (criteria.getCreditAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreditAccount(), VoucherDetails_.creditAccount));
            }
            if (criteria.getDebitAccount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDebitAccount(), VoucherDetails_.debitAccount));
            }
            if (criteria.getTransferAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransferAmt(), VoucherDetails_.transferAmt));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), VoucherDetails_.isDeleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), VoucherDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), VoucherDetails_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), VoucherDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), VoucherDetails_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), VoucherDetails_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), VoucherDetails_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), VoucherDetails_.freeField5));
            }
            if (criteria.getFreeField6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField6(), VoucherDetails_.freeField6));
            }
        }
        return specification;
    }
}
