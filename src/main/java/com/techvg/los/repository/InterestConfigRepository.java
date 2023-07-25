package com.techvg.los.repository;

import com.techvg.los.domain.InterestConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the InterestConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterestConfigRepository extends JpaRepository<InterestConfig, Long>, JpaSpecificationExecutor<InterestConfig> {}
