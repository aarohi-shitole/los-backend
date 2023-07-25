package com.techvg.los.repository;

import com.techvg.los.domain.LoanAccount;
import com.techvg.los.domain.LoanApplications;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoanAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanAccountRepository extends JpaRepository<LoanAccount, Long>, JpaSpecificationExecutor<LoanAccount> {
    @Query(
        value = "SELECT next_val_loan_account FROM sequence_generator WHERE sequence_generator.branch_id =:branchId ",
        nativeQuery = true
    )
    int findApplicationNextvalue(@Param("branchId") long branchId);

    @Modifying
    @Query(
        value = "UPDATE sequence_generator SET sequence_generator.next_val_loan_account=:nextVal where sequence_generator.next_val_loan_account=:lastVal AND sequence_generator.branch_id =:branchId",
        nativeQuery = true
    )
    int updateApplicationNextvalue(@Param("lastVal") int lastVal, @Param("nextVal") int nextVal, @Param("branchId") long branchId);
}
