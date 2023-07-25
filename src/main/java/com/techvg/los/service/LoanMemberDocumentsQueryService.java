package com.techvg.los.service;

import com.techvg.los.domain.LoanApplications_;
import com.techvg.los.domain.LoanMemberDocuments;
import com.techvg.los.domain.LoanMemberDocuments_;
import com.techvg.los.domain.Member_;
import com.techvg.los.repository.LoanMemberDocumentsRepository;
import com.techvg.los.service.criteria.LoanMemberDocumentsCriteria;
import com.techvg.los.service.dto.LoanMemberDocumentsDTO;
import com.techvg.los.service.mapper.LoanMemberDocumentsMapper;
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
public class LoanMemberDocumentsQueryService extends QueryService<LoanMemberDocuments> {

    private final Logger log = LoggerFactory.getLogger(LoanMemberDocumentsQueryService.class);

    private final LoanMemberDocumentsRepository loanMemberDocumentsRepository;

    private final LoanMemberDocumentsMapper loanMemberDocumentsMapper;

    public LoanMemberDocumentsQueryService(
        LoanMemberDocumentsRepository loanMemberDocumentsRepository,
        LoanMemberDocumentsMapper loanMemberDocumentsMapper
    ) {
        this.loanMemberDocumentsRepository = loanMemberDocumentsRepository;
        this.loanMemberDocumentsMapper = loanMemberDocumentsMapper;
    }

    @Transactional
    public List<LoanMemberDocumentsDTO> findByCriteria(LoanMemberDocumentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<LoanMemberDocuments> specification = createSpecification(criteria);
        return loanMemberDocumentsMapper.toDto(loanMemberDocumentsRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public Page<LoanMemberDocumentsDTO> findByCriteria(LoanMemberDocumentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<LoanMemberDocuments> specification = createSpecification(criteria);
        return loanMemberDocumentsRepository.findAll(specification, page).map(loanMemberDocumentsMapper::toDto);
    }

    @Transactional(readOnly = true)
    public long countByCriteria(LoanMemberDocumentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<LoanMemberDocuments> specification = createSpecification(criteria);
        return loanMemberDocumentsRepository.count(specification);
    }

    protected Specification<LoanMemberDocuments> createSpecification(LoanMemberDocumentsCriteria criteria) {
        Specification<LoanMemberDocuments> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }

            if (criteria.getMemberId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMemberId(),
                            root -> root.join(LoanMemberDocuments_.member, JoinType.LEFT).get(Member_.id)
                        )
                    );
            }
            if (criteria.getLoanApplicationsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanApplicationsId(),
                            root -> root.join(LoanMemberDocuments_.loanApplications, JoinType.LEFT).get(LoanApplications_.id)
                        )
                    );
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), LoanMemberDocuments_.freeField1));
            }

            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), LoanMemberDocuments_.id));
            }

            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), LoanMemberDocuments_.isDeleted));
            }
        }
        return specification;
    }
}
