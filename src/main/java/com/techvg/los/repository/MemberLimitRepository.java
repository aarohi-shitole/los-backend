package com.techvg.los.repository;

import com.techvg.los.domain.MemberLimit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MemberLimit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberLimitRepository extends JpaRepository<MemberLimit, Long>, JpaSpecificationExecutor<MemberLimit> {}
