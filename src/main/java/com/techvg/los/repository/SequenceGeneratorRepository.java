package com.techvg.los.repository;

import com.techvg.los.domain.SequenceGenerator;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SequenceGenerator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SequenceGeneratorRepository extends JpaRepository<SequenceGenerator, Long>, JpaSpecificationExecutor<SequenceGenerator> {}
