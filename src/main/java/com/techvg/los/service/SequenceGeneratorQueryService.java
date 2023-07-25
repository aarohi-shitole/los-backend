package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.SequenceGenerator;
import com.techvg.los.repository.SequenceGeneratorRepository;
import com.techvg.los.service.criteria.SequenceGeneratorCriteria;
import com.techvg.los.service.dto.SequenceGeneratorDTO;
import com.techvg.los.service.mapper.SequenceGeneratorMapper;
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
 * Service for executing complex queries for {@link SequenceGenerator} entities in the database.
 * The main input is a {@link SequenceGeneratorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SequenceGeneratorDTO} or a {@link Page} of {@link SequenceGeneratorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SequenceGeneratorQueryService extends QueryService<SequenceGenerator> {

    private final Logger log = LoggerFactory.getLogger(SequenceGeneratorQueryService.class);

    private final SequenceGeneratorRepository sequenceGeneratorRepository;

    private final SequenceGeneratorMapper sequenceGeneratorMapper;

    public SequenceGeneratorQueryService(
        SequenceGeneratorRepository sequenceGeneratorRepository,
        SequenceGeneratorMapper sequenceGeneratorMapper
    ) {
        this.sequenceGeneratorRepository = sequenceGeneratorRepository;
        this.sequenceGeneratorMapper = sequenceGeneratorMapper;
    }

    /**
     * Return a {@link List} of {@link SequenceGeneratorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SequenceGeneratorDTO> findByCriteria(SequenceGeneratorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SequenceGenerator> specification = createSpecification(criteria);
        return sequenceGeneratorMapper.toDto(sequenceGeneratorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SequenceGeneratorDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SequenceGeneratorDTO> findByCriteria(SequenceGeneratorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SequenceGenerator> specification = createSpecification(criteria);
        return sequenceGeneratorRepository.findAll(specification, page).map(sequenceGeneratorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SequenceGeneratorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SequenceGenerator> specification = createSpecification(criteria);
        return sequenceGeneratorRepository.count(specification);
    }

    /**
     * Function to convert {@link SequenceGeneratorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SequenceGenerator> createSpecification(SequenceGeneratorCriteria criteria) {
        Specification<SequenceGenerator> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SequenceGenerator_.id));
            }
            if (criteria.getNextValMember() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextValMember(), SequenceGenerator_.nextValMember));
            }
            if (criteria.getNextValLoanApp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextValLoanApp(), SequenceGenerator_.nextValLoanApp));
            }
            if (criteria.getNextValLoanAccount() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNextValLoanAccount(), SequenceGenerator_.nextValLoanAccount));
            }
            if (criteria.getNextValVoucher() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextValVoucher(), SequenceGenerator_.nextValVoucher));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFreeField1(), SequenceGenerator_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFreeField2(), SequenceGenerator_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFreeField3(), SequenceGenerator_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFreeField4(), SequenceGenerator_.freeField4));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFreeField5(), SequenceGenerator_.freeField5));
            }
            if (criteria.getBranchId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBranchId(),
                            root -> root.join(SequenceGenerator_.branch, JoinType.LEFT).get(Branch_.id)
                        )
                    );
            }

            if (criteria.getOrganisationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getBranchId(),
                            root -> root.join(SequenceGenerator_.organisation, JoinType.LEFT).get(Organisation_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
