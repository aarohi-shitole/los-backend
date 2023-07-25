package com.techvg.los.repository;

import com.techvg.los.domain.MemberBank;
import com.techvg.los.service.dto.MemberBankDTO;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MemberBank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberBankRepository extends JpaRepository<MemberBank, Long>, JpaSpecificationExecutor<MemberBank> {
    @Query(value = "SELECT * FROM member_bank WHERE account_number =:accountNumber", nativeQuery = true)
    Optional<MemberBank> findAccountNumber(@Param("accountNumber") Long accountNumber);
}
