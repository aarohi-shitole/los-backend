package com.techvg.los.repository;

import com.techvg.los.domain.Branch;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Branch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BranchRepository extends JpaRepository<Branch, Long>, JpaSpecificationExecutor<Branch> {
    @Transactional
    @Modifying
    @Query(
        value = "INSERT INTO sequence_generator(next_val_member, next_val_loan_app, next_val_loan_account, next_val_voucher, branch_id) VALUES (:next_val_member, :next_val_loan_app, :next_val_loan_account, :next_val_voucher, :branchId)",
        nativeQuery = true
    )
    public int updateSequenceGenrator(
        @Param("next_val_member") int next_val_member,
        @Param("next_val_loan_app") int next_val_loan_app,
        @Param("next_val_loan_account") int next_val_loan_account,
        @Param("next_val_voucher") int next_val_voucher,
        @Param("branchId") Long branchId
    );
}
