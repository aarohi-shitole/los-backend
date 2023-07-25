package com.techvg.los.repository;

import com.techvg.los.domain.Declearation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Declearation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeclearationRepository extends JpaRepository<Declearation, Long>, JpaSpecificationExecutor<Declearation> {}
