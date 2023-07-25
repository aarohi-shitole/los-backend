package com.techvg.los.repository;

import com.techvg.los.domain.Organisation;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Organisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long>, JpaSpecificationExecutor<Organisation> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO sequence_generator(next_val_member,organisation_Id) VALUES (:next_val_member,:orgId)", nativeQuery = true)
    public int updateSequenceGenrator(@Param("next_val_member") int next_val_member, @Param("orgId") Long orgId);
}
