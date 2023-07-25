package com.techvg.los.service;

import com.techvg.los.domain.*; // for static metamodels
import com.techvg.los.domain.Member;
import com.techvg.los.repository.MemberRepository;
import com.techvg.los.service.criteria.MemberCriteria;
import com.techvg.los.service.dto.MemberDTO;
import com.techvg.los.service.mapper.MemberMapper;
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
 * Service for executing complex queries for {@link Member} entities in the database.
 * The main input is a {@link MemberCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MemberDTO} or a {@link Page} of {@link MemberDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MemberQueryService extends QueryService<Member> {

    private final Logger log = LoggerFactory.getLogger(MemberQueryService.class);

    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    public MemberQueryService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    /**
     * Return a {@link List} of {@link MemberDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MemberDTO> findByCriteria(MemberCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Member> specification = createSpecification(criteria);
        return memberMapper.toDto(memberRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MemberDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberDTO> findByCriteria(MemberCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Member> specification = createSpecification(criteria);
        return memberRepository.findAll(specification, page).map(memberMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MemberCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Member> specification = createSpecification(criteria);
        return memberRepository.count(specification);
    }

    /**
     * Function to convert {@link MemberCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Member> createSpecification(MemberCriteria criteria) {
        Specification<Member> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Member_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildSpecification(criteria.getTitle(), Member_.title));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), Member_.firstName));
            }
            if (criteria.getMiddleName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMiddleName(), Member_.middleName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), Member_.lastName));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustomerId(), Member_.customerId));
            }
            if (criteria.getMembershipNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMembershipNo(), Member_.membershipNo));
            }
            if (criteria.getFatherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFatherName(), Member_.fatherName));
            }
            if (criteria.getMotherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotherName(), Member_.motherName));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildSpecification(criteria.getGender(), Member_.gender));
            }
            if (criteria.getDob() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDob(), Member_.dob));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Member_.email));
            }
            if (criteria.getMobileNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobileNo(), Member_.mobileNo));
            }
            if (criteria.getAlternateMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAlternateMobile(), Member_.alternateMobile));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFax(), Member_.fax));
            }
            if (criteria.getHighestQualificationOthers() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getHighestQualificationOthers(), Member_.highestQualificationOthers)
                    );
            }
            if (criteria.getContactTimeFrom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactTimeFrom(), Member_.contactTimeFrom));
            }
            if (criteria.getContactTimeTo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactTimeTo(), Member_.contactTimeTo));
            }
            if (criteria.getReligion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReligion(), Member_.religion));
            }
            if (criteria.getCustCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCustCategory(), Member_.custCategory));
            }
            if (criteria.getCast() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCast(), Member_.cast));
            }
            if (criteria.getAadharCardNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAadharCardNo(), Member_.aadharCardNo));
            }
            if (criteria.getPanCard() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPanCard(), Member_.panCard));
            }
            if (criteria.getPassportNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassportNo(), Member_.passportNo));
            }
            if (criteria.getPassportExpiry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPassportExpiry(), Member_.passportExpiry));
            }
            if (criteria.getRationCard() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRationCard(), Member_.rationCard));
            }
            if (criteria.getResidenceStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getResidenceStatus(), Member_.residenceStatus));
            }
            if (criteria.getMaritalStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getMaritalStatus(), Member_.maritalStatus));
            }
            if (criteria.getFamilyMemberCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFamilyMemberCount(), Member_.familyMemberCount));
            }
            if (criteria.getOccupation() != null) {
                specification = specification.and(buildSpecification(criteria.getOccupation(), Member_.occupation));
            }
            if (criteria.getNationality() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNationality(), Member_.nationality));
            }
            if (criteria.getNoOfDependents() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfDependents(), Member_.noOfDependents));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAge(), Member_.age));
            }
            if (criteria.getApplicationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApplicationDate(), Member_.applicationDate));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Member_.status));
            }
            if (criteria.getHighestQualification() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getHighestQualification(), Member_.highestQualification));
            }
            if (criteria.getHasAdharCardVerified() != null) {
                specification = specification.and(buildSpecification(criteria.getHasAdharCardVerified(), Member_.hasAdharCardVerified));
            }
            if (criteria.getHasPanCardVerified() != null) {
                specification = specification.and(buildSpecification(criteria.getHasPanCardVerified(), Member_.hasPanCardVerified));
            }
            if (criteria.getLoanStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getLoanStatus(), Member_.loanStatus));
            }
            if (criteria.getEnqRefrenceNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEnqRefrenceNo(), Member_.enqRefrenceNo));
            }
            if (criteria.getNumberOfAssets() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumberOfAssets(), Member_.numberOfAssets));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Member_.isActive));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), Member_.isDeleted));
            }
            if (criteria.getProfileStepper() != null) {
                specification = specification.and(buildSpecification(criteria.getProfileStepper(), Member_.profileStepper));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Member_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Member_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Member_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Member_.createdOn));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), Member_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), Member_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), Member_.freeField3));
            }
            if (criteria.getResidence() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResidence(), Member_.residence));
            }
            if (criteria.getFreeField5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField5(), Member_.freeField5));
            }
            if (criteria.getFreeField6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField6(), Member_.freeField6));
            }
            //            if (criteria.getEnquiryDetailsId() != null) {
            //                specification =
            //                    specification.and(
            //                        buildSpecification(
            //                            criteria.getEnquiryDetailsId(),
            //                            root -> root.join(Member_.enquiryDetails, JoinType.LEFT).get(EnquiryDetails_.id)
            //                        )
            //                    );
            //            }
            if (criteria.getOrganisationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganisationId(),
                            root -> root.join(Member_.organisation, JoinType.LEFT).get(Organisation_.id)
                        )
                    );
            }
            if (criteria.getBranchId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getBranchId(), root -> root.join(Member_.branch, JoinType.LEFT).get(Branch_.id))
                    );
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(Member_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            //            if (criteria.getMemberId() != null) {
            //                specification = specification.and(buildRangeSpecification(criteria.getMemberId(), Member_.memberId));
            //            }
        }
        return specification;
    }
}
