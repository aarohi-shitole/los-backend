package com.techvg.los.repository;

import com.techvg.los.domain.LoanApplications;
import com.techvg.los.service.dto.LoanApplicationsDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoanApplications entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanApplicationsRepository extends JpaRepository<LoanApplications, Long>, JpaSpecificationExecutor<LoanApplications> {
    @Query(value = "SELECT next_val_loan_app FROM sequence_generator WHERE sequence_generator.branch_id =:branchId ", nativeQuery = true)
    int findApplicationNextvalue(@Param("branchId") long branchId);

    @Modifying
    @Query(
        value = "UPDATE sequence_generator SET sequence_generator.next_val_loan_app=:nextVal where sequence_generator.next_val_loan_app=:lastVal AND sequence_generator.branch_id =:branchId",
        nativeQuery = true
    )
    int updateApplicationNextvalue(@Param("lastVal") int lastVal, @Param("nextVal") int nextVal, @Param("branchId") long branchId);

    @Query(value = "SELECT * FROM loan_applications WHERE member_id =:memberId ", nativeQuery = true)
    List<LoanApplications> findLoansByMemberId(@Param("memberId") Long memberId);
}
