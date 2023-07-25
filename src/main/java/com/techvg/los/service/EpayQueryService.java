package com.techvg.los.service;

// for static metamodels
import com.techvg.los.domain.Epay;
import com.techvg.los.domain.Epay_;
import com.techvg.los.repository.EpayRepository;
import com.techvg.los.service.criteria.EpayCriteria;
import com.techvg.los.service.dto.EpayDTO;
import com.techvg.los.service.mapper.EpayMapper;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Epay} entities in the database.
 * The main input is a {@link EpayCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EpayDTO} or a {@link Page} of {@link EpayDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EpayQueryService extends QueryService<Epay> {

    private final Logger log = LoggerFactory.getLogger(EpayQueryService.class);

    private final EpayRepository epayRepository;

    private final EpayMapper epayMapper;

    public EpayQueryService(EpayRepository epayRepository, EpayMapper epayMapper) {
        this.epayRepository = epayRepository;
        this.epayMapper = epayMapper;
    }

    /**
     * Return a {@link List} of {@link EpayDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EpayDTO> findByCriteria(EpayCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Epay> specification = createSpecification(criteria);
        return epayMapper.toDto(epayRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EpayDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EpayDTO> findByCriteria(EpayCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Epay> specification = createSpecification(criteria);
        return epayRepository.findAll(specification, page).map(epayMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EpayCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Epay> specification = createSpecification(criteria);
        return epayRepository.count(specification);
    }

    /**
     * Function to convert {@link EpayCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Epay> createSpecification(EpayCriteria criteria) {
        Specification<Epay> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Epay_.id));
            }
            if (criteria.getInstrumentType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstrumentType(), Epay_.instrumentType));
            }
            if (criteria.getDtFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDtFromDate(), Epay_.dtFromDate));
            }
            if (criteria.getDtToDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDtToDate(), Epay_.dtToDate));
            }
            if (criteria.getBankCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankCode(), Epay_.bankCode));
            }
            if (criteria.getBankBranchCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankBranchCode(), Epay_.bankBranchCode));
            }
            if (criteria.getAccountType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountType(), Epay_.accountType));
            }
            if (criteria.getAccountNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNo(), Epay_.accountNo));
            }
            if (criteria.getMaxCeilAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxCeilAmount(), Epay_.maxCeilAmount));
            }
            if (criteria.getInstallmentAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInstallmentAmount(), Epay_.installmentAmount));
            }
            if (criteria.getMaxInstallmentAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxInstallmentAmount(), Epay_.maxInstallmentAmount));
            }
            if (criteria.getMandateRefNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMandateRefNo(), Epay_.mandateRefNo));
            }
            if (criteria.getDepositBank() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepositBank(), Epay_.depositBank));
            }
            if (criteria.getMandateType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMandateType(), Epay_.mandateType));
            }
            if (criteria.getFrequency() != null) {
                specification = specification.and(buildSpecification(criteria.getFrequency(), Epay_.frequency));
            }
            if (criteria.getIfscCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIfscCode(), Epay_.ifscCode));
            }
            if (criteria.getUtrNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUtrNo(), Epay_.utrNo));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), Epay_.isDeleted));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Epay_.isActive));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Epay_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Epay_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), Epay_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), Epay_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), Epay_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), Epay_.freeField4));
            }
        }
        return specification;
    }
}
