package com.techvg.los.repository;

import com.techvg.los.domain.IncomeDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the IncomeDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomeDetailsRepository extends JpaRepository<IncomeDetails, Long>, JpaSpecificationExecutor<IncomeDetails> {}
