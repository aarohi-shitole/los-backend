package com.techvg.los.repository;

import com.techvg.los.domain.MasterAgreement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MasterAgreement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MasterAgreementRepository extends JpaRepository<MasterAgreement, Long>, JpaSpecificationExecutor<MasterAgreement> {}
