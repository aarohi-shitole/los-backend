package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.Vouchers;
import com.techvg.los.repository.VouchersRepository;
import com.techvg.los.service.criteria.VouchersCriteria;
import com.techvg.los.service.dto.VouchersDTO;
import com.techvg.los.service.mapper.VouchersMapper;
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
 * Service for executing complex queries for {@link Vouchers} entities in the database.
 * The main input is a {@link VouchersCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link VouchersDTO} or a {@link Page} of {@link VouchersDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class VouchersQueryService extends QueryService<Vouchers> {

    private final Logger log = LoggerFactory.getLogger(VouchersQueryService.class);

    private final VouchersRepository vouchersRepository;

    private final VouchersMapper vouchersMapper;

    public VouchersQueryService(VouchersRepository vouchersRepository, VouchersMapper vouchersMapper) {
        this.vouchersRepository = vouchersRepository;
        this.vouchersMapper = vouchersMapper;
    }

    /**
     * Return a {@link List} of {@link VouchersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<VouchersDTO> findByCriteria(VouchersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Vouchers> specification = createSpecification(criteria);
        return vouchersMapper.toDto(vouchersRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link VouchersDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<VouchersDTO> findByCriteria(VouchersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Vouchers> specification = createSpecification(criteria);
        return vouchersRepository.findAll(specification, page).map(vouchersMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(VouchersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Vouchers> specification = createSpecification(criteria);
        return vouchersRepository.count(specification);
    }

    /**
     * Function to convert {@link VouchersCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Vouchers> createSpecification(VouchersCriteria criteria) {
        Specification<Vouchers> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Vouchers_.id));
            }
            if (criteria.getVoucherDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVoucherDate(), Vouchers_.voucherDate));
            }
            if (criteria.getVoucherNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVoucherNo(), Vouchers_.voucherNo));
            }
            if (criteria.getPreparedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPreparedBy(), Vouchers_.preparedBy));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildSpecification(criteria.getCode(), Vouchers_.code));
            }
            if (criteria.getNarration() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNarration(), Vouchers_.narration));
            }
            if (criteria.getAuthorisedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuthorisedBy(), Vouchers_.authorisedBy));
            }
            if (criteria.getMode() != null) {
                specification = specification.and(buildSpecification(criteria.getMode(), Vouchers_.mode));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), Vouchers_.isDeleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Vouchers_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Vouchers_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), Vouchers_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), Vouchers_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), Vouchers_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), Vouchers_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), Vouchers_.freeField5));
            }
            if (criteria.getFreeField6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField6(), Vouchers_.freeField6));
            }
        }
        return specification;
    }
}
