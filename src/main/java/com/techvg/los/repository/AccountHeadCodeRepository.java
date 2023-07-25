package com.techvg.los.repository;

import com.techvg.los.domain.AccountHeadCode;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AccountHeadCode entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccountHeadCodeRepository extends JpaRepository<AccountHeadCode, Long>, JpaSpecificationExecutor<AccountHeadCode> {}
