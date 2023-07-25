package com.techvg.los.service;

import com.techvg.los.domain.LoanApplications_;
import com.techvg.los.domain.MemberAssets_;
import com.techvg.los.domain.Member_;
import com.techvg.los.domain.MortgagedAssets;
import com.techvg.los.domain.MortgagedAssets_;
import com.techvg.los.repository.MortgagedAssetsRepository;
import com.techvg.los.service.criteria.MortgagedAssetsCriteria;
import com.techvg.los.service.dto.MortgagedAssetsDTO;
import com.techvg.los.service.mapper.MortgagedAssetsMapper;
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
@Transactional
public class MortgagedAssetsService extends QueryService<MortgagedAssets> {

    private final Logger log = LoggerFactory.getLogger(MortgagedAssetsService.class);

    private final MortgagedAssetsRepository mortgagedAssetsRepository;

    private final MortgagedAssetsMapper mortgagedAssetsMapper;

    public MortgagedAssetsService(MortgagedAssetsRepository mortgagedAssetsRepository, MortgagedAssetsMapper mortgagedAssetsMapper) {
        this.mortgagedAssetsRepository = mortgagedAssetsRepository;
        this.mortgagedAssetsMapper = mortgagedAssetsMapper;
    }

    public MortgagedAssetsDTO save(MortgagedAssetsDTO mortgagedAssetsDTO) {
        log.debug("Request to save MortgagedAssets : {}", mortgagedAssetsDTO);
        MortgagedAssets mortgagedAssets = mortgagedAssetsMapper.toEntity(mortgagedAssetsDTO);
        MortgagedAssets mortgagedAsset = mortgagedAssetsRepository.save(mortgagedAssets);
        return mortgagedAssetsMapper.toDto(mortgagedAsset);
    }

    public void delete(Long id) {
        log.debug("Request to delete mortgage assets : {}", id);
        mortgagedAssetsRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<MortgagedAssetsDTO> findByCriteria(MortgagedAssetsCriteria criteria) {
        log.debug("find by criteria : {}, page: {}", criteria);
        final Specification<MortgagedAssets> specification = createSpecification(criteria);
        return mortgagedAssetsMapper.toDto(mortgagedAssetsRepository.findAll(specification));
    }

    @Transactional(readOnly = true)
    public Page<MortgagedAssetsDTO> findByCriteria(MortgagedAssetsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MortgagedAssets> specification = createSpecification(criteria);
        return mortgagedAssetsRepository.findAll(specification, page).map(mortgagedAssetsMapper::toDto);
    }

    protected Specification<MortgagedAssets> createSpecification(MortgagedAssetsCriteria criteria) {
        Specification<MortgagedAssets> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MortgagedAssets_.id));
            }

            if (criteria.getMortgCreateDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMortgCreateDate(), MortgagedAssets_.mortgCreateDate));
            }
            if (criteria.getMortgPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMortgPercentage(), MortgagedAssets_.mortgPercentage));
            }
            if (criteria.getMemberId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMemberId(),
                            root -> root.join(MortgagedAssets_.member, JoinType.LEFT).get(Member_.id)
                        )
                    );
            }
            if (criteria.getMemberAssetsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMemberAssetsId(),
                            root -> root.join(MortgagedAssets_.memberAssets, JoinType.LEFT).get(MemberAssets_.id)
                        )
                    );
            }
            if (criteria.getLoanApplicationsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getLoanApplicationsId(),
                            root -> root.join(MortgagedAssets_.loanApplications, JoinType.LEFT).get(LoanApplications_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
