package com.techvg.los.repository;

import com.techvg.los.domain.Epay;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Epay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EpayRepository extends JpaRepository<Epay, Long>, JpaSpecificationExecutor<Epay> {}
