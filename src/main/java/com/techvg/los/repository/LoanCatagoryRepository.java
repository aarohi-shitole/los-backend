package com.techvg.los.repository;

import com.techvg.los.domain.LoanCatagory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoanCatagory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanCatagoryRepository extends JpaRepository<LoanCatagory, Long>, JpaSpecificationExecutor<LoanCatagory> {}
