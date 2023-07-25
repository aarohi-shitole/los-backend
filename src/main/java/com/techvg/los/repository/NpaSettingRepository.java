package com.techvg.los.repository;

import com.techvg.los.domain.NpaSetting;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NpaSetting entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NpaSettingRepository extends JpaRepository<NpaSetting, Long>, JpaSpecificationExecutor<NpaSetting> {}
