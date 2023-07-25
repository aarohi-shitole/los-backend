package com.techvg.los.service;

import com.techvg.los.domain.Address_;
import com.techvg.los.domain.LoanApplicationMembers;
import com.techvg.los.domain.LoanApplicationMembers_;
import com.techvg.los.domain.LoanApplications_;
import com.techvg.los.domain.Member_;
import com.techvg.los.repository.LoanApplicationMembersRepository;
import com.techvg.los.service.criteria.LoanApplicationMembersCriteria;
import com.techvg.los.service.dto.LoanApplicationMembersDTO;
import com.techvg.los.service.mapper.LoanApplicationMembersMapper;
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

@Service
@Transactional(readOnly = true)
public class LoanApplicationMembersQueryService extends QueryService<LoanApplicationMembers> {

    private final Logger log = LoggerFactory.getLogger(LoanApplicationMembersQueryService.class);

    private final LoanApplicationMembersRepository loanApplicationMembersRepository;

    private final LoanApplicationMembersMapper loanApplicationMembersMapper;

    public LoanApplicationMembersQueryService(
        LoanApplicationMembersRepository loanApplicationMembersRepository,
        LoanApplicationMembersMapper loanApplicationMembersMapper
    ) {
        this.loanApplicationMembersRepository = loanApplicationMembersRepository;
        this.loanApplicationMembersMapper = loanApplicationMembersMapper;
    }

    @Transactional
    public List<LoanApplicationMembersDTO> findByCriteria(LoanApplicationMembersCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LoanApplicationMembers> specification = createSpecification(criteria);
        return loanApplicationMembersMapper.toDto(loanApplicationMembersRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public Page<LoanApplicationMembersDTO> findByCriteria(LoanApplicationMembersCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LoanApplicationMembers> specification = createSpecification(criteria);
        return loanApplicationMembersRepository.findAll(specification, page).map(loanApplicationMembersMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(LoanApplicationMembersCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LoanApplicationMembers> specification = createSpecification(criteria);
        return loanApplicationMembersRepository.count(specification);
    }

    protected Specification<LoanApplicationMembers> createSpecification(LoanApplicationMembersCriteria criteria) {
        Specification<LoanApplicationMembers> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getApplicantType() != null) {
                specification = specification.and(buildSpecification(criteria.getApplicantType(), LoanApplicationMembers_.applicantType));
            }

            if (criteria.getMemberId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMemberId(),
                            root -> root.join(LoanApplicationMembers_.member, JoinType.LEFT).get(Member_.id)
                        )
                    );
            }
            if (criteria.getLoanApplicationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanApplicationId(),
                            root -> root.join(LoanApplicationMembers_.loanApplications, JoinType.LEFT).get(LoanApplications_.id)
                        )
                    );
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LoanApplicationMembers_.id));
            }
        }
        return specification;
    }
}
