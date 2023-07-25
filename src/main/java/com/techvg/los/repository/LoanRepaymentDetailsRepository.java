package com.techvg.los.repository;

import com.techvg.los.domain.LoanRepaymentDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoanRepaymentDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanRepaymentDetailsRepository
    extends JpaRepository<LoanRepaymentDetails, Long>, JpaSpecificationExecutor<LoanRepaymentDetails> {}
