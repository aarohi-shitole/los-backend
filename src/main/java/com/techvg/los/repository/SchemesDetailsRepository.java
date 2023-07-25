package com.techvg.los.repository;

import com.techvg.los.domain.SchemesDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SchemesDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchemesDetailsRepository extends JpaRepository<SchemesDetails, Long>, JpaSpecificationExecutor<SchemesDetails> {}
