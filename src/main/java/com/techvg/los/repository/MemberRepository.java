package com.techvg.los.repository;

import com.techvg.los.domain.Member;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Member entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {
    @Query(
        value = "SELECT next_val_member FROM sequence_generator WHERE sequence_generator.organisation_id =:organisationId ",
        nativeQuery = true
    )
    int findCustomerNextvalue(@Param("organisationId") long organisationId);

    @Modifying
    @Query(
        value = "UPDATE sequence_generator SET sequence_generator.next_val_member=:nextVal where sequence_generator.next_val_member=:lastVal AND sequence_generator.organisation_id =:organisationId",
        nativeQuery = true
    )
    int updateCustomerNextvalue(@Param("lastVal") int lastVal, @Param("nextVal") int nextVal, @Param("organisationId") long organisationId);
}
