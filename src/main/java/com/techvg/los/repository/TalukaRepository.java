package com.techvg.los.repository;

import com.techvg.los.domain.Taluka;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Taluka entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TalukaRepository extends JpaRepository<Taluka, Long>, JpaSpecificationExecutor<Taluka> {}
