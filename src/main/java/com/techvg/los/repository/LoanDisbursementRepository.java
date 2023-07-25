package com.techvg.los.repository;

import com.techvg.los.domain.LoanDisbursement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoanDisbursement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanDisbursementRepository extends JpaRepository<LoanDisbursement, Long>, JpaSpecificationExecutor<LoanDisbursement> {}
