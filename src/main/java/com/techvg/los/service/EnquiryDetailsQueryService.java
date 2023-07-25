package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.EnquiryDetails;
import com.techvg.los.repository.EnquiryDetailsRepository;
import com.techvg.los.service.criteria.EnquiryDetailsCriteria;
import com.techvg.los.service.dto.EnquiryDetailsDTO;
import com.techvg.los.service.mapper.EnquiryDetailsMapper;
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
 * Service for executing complex queries for {@link EnquiryDetails} entities in the database.
 * The main input is a {@link EnquiryDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EnquiryDetailsDTO} or a {@link Page} of {@link EnquiryDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnquiryDetailsQueryService extends QueryService<EnquiryDetails> {

    private final Logger log = LoggerFactory.getLogger(EnquiryDetailsQueryService.class);

    private final EnquiryDetailsRepository enquiryDetailsRepository;

    private final EnquiryDetailsMapper enquiryDetailsMapper;

    public EnquiryDetailsQueryService(EnquiryDetailsRepository enquiryDetailsRepository, EnquiryDetailsMapper enquiryDetailsMapper) {
        this.enquiryDetailsRepository = enquiryDetailsRepository;
        this.enquiryDetailsMapper = enquiryDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link EnquiryDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EnquiryDetailsDTO> findByCriteria(EnquiryDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EnquiryDetails> specification = createSpecification(criteria);
        return enquiryDetailsMapper.toDto(enquiryDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EnquiryDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnquiryDetailsDTO> findByCriteria(EnquiryDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EnquiryDetails> specification = createSpecification(criteria);
        return enquiryDetailsRepository.findAll(specification, page).map(enquiryDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnquiryDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EnquiryDetails> specification = createSpecification(criteria);
        return enquiryDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link EnquiryDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EnquiryDetails> createSpecification(EnquiryDetailsCriteria criteria) {
        Specification<EnquiryDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EnquiryDetails_.id));
            }
            if (criteria.getCustomerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerName(), EnquiryDetails_.customerName));
            }
            if (criteria.getSubName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubName(), EnquiryDetails_.subName));
            }
            if (criteria.getPurpose() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPurpose(), EnquiryDetails_.purpose));
            }
            if (criteria.getMobileNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNo(), EnquiryDetails_.mobileNo));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), EnquiryDetails_.amount));
            }
            if (criteria.getRefrenceNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRefrenceNo(), EnquiryDetails_.refrenceNo));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), EnquiryDetails_.isDeleted));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), EnquiryDetails_.isActive));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), EnquiryDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), EnquiryDetails_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), EnquiryDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), EnquiryDetails_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), EnquiryDetails_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), EnquiryDetails_.freeField4));
            }
            if (criteria.getStateId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getStateId(), root -> root.join(EnquiryDetails_.state, JoinType.LEFT).get(State_.id))
                    );
            }
            if (criteria.getDistrictId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getDistrictId(),
                            root -> root.join(EnquiryDetails_.district, JoinType.LEFT).get(District_.id)
                        )
                    );
            }
            if (criteria.getTalukaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getTalukaId(), root -> root.join(EnquiryDetails_.taluka, JoinType.LEFT).get(Taluka_.id))
                    );
            }
            if (criteria.getCityId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getCityId(), root -> root.join(EnquiryDetails_.city, JoinType.LEFT).get(City_.id))
                    );
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(EnquiryDetails_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
