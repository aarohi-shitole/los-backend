package com.techvg.los.repository;

import com.techvg.los.domain.LoanApplications;
import com.techvg.los.domain.MemberAssets;
import com.techvg.los.service.dto.MemberAssetsDTO;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MemberAssets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberAssetsRepository extends JpaRepository<MemberAssets, Long>, JpaSpecificationExecutor<MemberAssets> {
    @Query(
        value = "SELECT * FROM los_db.member_assets WHERE member_assets.member_id=:memberId AND member_assets.loan_applications_id =:loanApplicationId AND los_db.member_assets.is_mortgage=1 AND los_db.member_assets.is_deleted!=1",
        nativeQuery = true
    )
    List<MemberAssets> getAllMortgageAssets(@Param("memberId") Long memberId, @Param("loanApplicationId") Long loanApplicationId);

    @Query(
        value = "SELECT * FROM los_db.member_assets WHERE member_assets.member_id =:memberId AND los_db.member_assets.is_mortgage!=1 AND los_db.member_assets.is_deleted!=1",
        nativeQuery = true
    )
    List<MemberAssets> getUnMortgagedAssets(@Param("memberId") Long memberId);
}
