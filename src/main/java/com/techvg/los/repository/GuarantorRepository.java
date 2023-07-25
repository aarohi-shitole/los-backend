package com.techvg.los.repository;

import com.techvg.los.domain.Guarantor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Guarantor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuarantorRepository extends JpaRepository<Guarantor, Long>, JpaSpecificationExecutor<Guarantor> {}
