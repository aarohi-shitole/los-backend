package com.techvg.los.repository;

import com.techvg.los.domain.MemberExistingfacility;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MemberExistingfacility entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberExistingfacilityRepository
    extends JpaRepository<MemberExistingfacility, Long>, JpaSpecificationExecutor<MemberExistingfacility> {}
