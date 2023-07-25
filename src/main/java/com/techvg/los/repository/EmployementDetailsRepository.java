package com.techvg.los.repository;

import com.techvg.los.domain.EmployementDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployementDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployementDetailsRepository
    extends JpaRepository<EmployementDetails, Long>, JpaSpecificationExecutor<EmployementDetails> {}
