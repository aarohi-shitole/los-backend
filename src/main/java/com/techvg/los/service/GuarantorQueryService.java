package com.techvg.los.service;

// for static metamodels
import com.techvg.los.domain.Guarantor;
import com.techvg.los.domain.Guarantor_;
import com.techvg.los.domain.LoanApplications_;
import com.techvg.los.domain.Member_;
import com.techvg.los.repository.GuarantorRepository;
import com.techvg.los.service.criteria.GuarantorCriteria;
import com.techvg.los.service.dto.GuarantorDTO;
import com.techvg.los.service.mapper.GuarantorMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Guarantor} entities in the
 * database. The main input is a {@link GuarantorCriteria} which gets converted
 * to {@link Specification}, in a way that all the filters must apply. It
 * returns a {@link List} of {@link GuarantorDTO} or a {@link Page} of
 * {@link GuarantorDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class GuarantorQueryService extends QueryService<Guarantor> {

    private final Logger log = LoggerFactory.getLogger(GuarantorQueryService.class);

    private final GuarantorRepository guarantorRepository;

    private final GuarantorMapper guarantorMapper;

    public GuarantorQueryService(GuarantorRepository guarantorRepository, GuarantorMapper guarantorMapper) {
        this.guarantorRepository = guarantorRepository;
        this.guarantorMapper = guarantorMapper;
    }

    /**
     * Return a {@link List} of {@link GuarantorDTO} which matches the criteria from
     * the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<GuarantorDTO> findByCriteria(GuarantorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Guarantor> specification = createSpecification(criteria);
        return guarantorMapper.toDto(guarantorRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link GuarantorDTO} which matches the criteria from
     * the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<GuarantorDTO> findByCriteria(GuarantorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Guarantor> specification = createSpecification(criteria);
        return guarantorRepository.findAll(specification, page).map(guarantorMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(GuarantorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Guarantor> specification = createSpecification(criteria);
        return guarantorRepository.count(specification);
    }

    /**
     * Function to convert {@link GuarantorCriteria} to a {@link Specification}
     *
     * @param criteria The object which holds all the filters, which the entities
     *                 should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Guarantor> createSpecification(GuarantorCriteria criteria) {
        Specification<Guarantor> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Guarantor_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildSpecification(criteria.getTitle(), Guarantor_.title));
            }
            if (criteria.getConstitutionType() != null) {
                specification = specification.and(buildSpecification(criteria.getConstitutionType(), Guarantor_.constitutionType));
            }

            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Guarantor_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), Guarantor_.middleName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Guarantor_.lastName));
            }
            if (criteria.getMembershipNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMembershipNo(), Guarantor_.membershipNo));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), Guarantor_.gender));
            }
            if (criteria.getDob() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDob(), Guarantor_.dob));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Guarantor_.email));
            }
            if (criteria.getMobileNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNo(), Guarantor_.mobileNo));
            }
            if (criteria.getHouseOwner() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHouseOwner(), Guarantor_.houseOwner));
            }
            if (criteria.getNatOfBusiness() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNatOfBusiness(), Guarantor_.natOfBusiness));
            }
            if (criteria.getBusiRegistration() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBusiRegistration(), Guarantor_.busiRegistration));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), Guarantor_.designation));
            }
            if (criteria.getOccupation() != null) {
                specification = specification.and(buildSpecification(criteria.getOccupation(), Guarantor_.occupation));
            }
            if (criteria.getEmployerNameAdd() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployerNameAdd(), Guarantor_.employerNameAdd));
            }
            if (criteria.getSoclibilAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSoclibilAmt(), Guarantor_.soclibilAmt));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAge(), Guarantor_.age));
            }
            if (criteria.getSoclibilType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSoclibilType(), Guarantor_.soclibilType));
            }
            if (criteria.getOtherlibilAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOtherlibilAmt(), Guarantor_.otherlibilAmt));
            }
            if (criteria.getOtherlibilType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOtherlibilType(), Guarantor_.otherlibilType));
            }
            if (criteria.getAadharCardNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAadharCardNo(), Guarantor_.aadharCardNo));
            }
            if (criteria.getPanCard() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPanCard(), Guarantor_.panCard));
            }
            if (criteria.getMaritalStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getMaritalStatus(), Guarantor_.maritalStatus));
            }
            if (criteria.getHasAdharVerified() != null) {
                specification = specification.and(buildSpecification(criteria.getHasAdharVerified(), Guarantor_.hasAdharVerified));
            }
            if (criteria.getHasPanVerified() != null) {
                specification = specification.and(buildSpecification(criteria.getHasPanVerified(), Guarantor_.hasPanVerified));
            }
            if (criteria.getNumberOfAssets() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfAssets(), Guarantor_.numberOfAssets));
            }
            if (criteria.getGrossAnnualInc() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrossAnnualInc(), Guarantor_.grossAnnualInc));
            }
            if (criteria.getNetIncome() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNetIncome(), Guarantor_.netIncome));
            }
            if (criteria.getIsIncomeTaxPayer() != null) {
                specification = specification.and(buildSpecification(criteria.getIsIncomeTaxPayer(), Guarantor_.isIncomeTaxPayer));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Guarantor_.isActive));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), Guarantor_.isDeleted));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Guarantor_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Guarantor_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Guarantor_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Guarantor_.createdOn));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Guarantor_.address));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), Guarantor_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), Guarantor_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), Guarantor_.freeField4));
            }
            if (criteria.getProfileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProfileUrl(), Guarantor_.profileUrl));
            }
            if (criteria.getFreeField6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField6(), Guarantor_.freeField6));
            }
            // if (criteria.getMemberAssetsId() != null) {
            // specification =
            // specification.and(
            // buildSpecification(
            // criteria.getMemberAssetsId(),
            // root -> root.join(Guarantor_.memberAssets,
            // JoinType.LEFT).get(MemberAssets_.id)
            // )
            // );
            // }
            // if (criteria.getEmployementDetailsId() != null) {
            // specification =
            // specification.and(
            // buildSpecification(
            // criteria.getEmployementDetailsId(),
            // root -> root.join(Guarantor_.employementDetails,
            // JoinType.LEFT).get(EmployementDetails_.id)
            // )
            // );
            // }
            if (criteria.getMemberId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getMemberId(), root -> root.join(Guarantor_.member, JoinType.LEFT).get(Member_.id))
                    );
            }

            if (criteria.getLoanApplicationsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanApplicationsId(),
                            root -> root.join(Guarantor_.loanApplications, JoinType.LEFT).get(LoanApplications_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
