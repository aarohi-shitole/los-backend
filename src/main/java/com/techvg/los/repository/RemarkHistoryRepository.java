package com.techvg.los.repository;

import com.techvg.los.domain.RemarkHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RemarkHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RemarkHistoryRepository extends JpaRepository<RemarkHistory, Long>, JpaSpecificationExecutor<RemarkHistory> {}
