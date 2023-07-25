package com.techvg.los.repository;

import com.techvg.los.domain.OrgPrerequisiteDoc;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrgPrerequisiteDoc entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrgPrerequisiteDocRepository
    extends JpaRepository<OrgPrerequisiteDoc, Long>, JpaSpecificationExecutor<OrgPrerequisiteDoc> {}
