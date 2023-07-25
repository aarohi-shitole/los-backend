package com.techvg.los.repository;

import com.techvg.los.domain.Vouchers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Vouchers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VouchersRepository extends JpaRepository<Vouchers, Long>, JpaSpecificationExecutor<Vouchers> {}
