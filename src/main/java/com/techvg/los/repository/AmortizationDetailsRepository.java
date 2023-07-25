package com.techvg.los.repository;

import com.techvg.los.domain.AmortizationDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AmortizationDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmortizationDetailsRepository
    extends JpaRepository<AmortizationDetails, Long>, JpaSpecificationExecutor<AmortizationDetails> {}
