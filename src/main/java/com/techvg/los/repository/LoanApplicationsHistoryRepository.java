package com.techvg.los.repository;

import com.techvg.los.domain.LoanApplications;
import com.techvg.los.domain.LoanApplicationsHistory;
import com.techvg.los.service.dto.LoanApplicationsHistoryDTO;
import java.sql.Array;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoanApplications entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoanApplicationsHistoryRepository
    extends JpaRepository<LoanApplicationsHistory, Long>, JpaSpecificationExecutor<LoanApplicationsHistory> {
    @Query(
        value = "SELECT distinct(status) FROM  Loan_applications_history WHERE Loan_applications_history.loan_applications_id =:id ",
        nativeQuery = true
    )
    String[] findDistinctStatus(@Param("id") long id);
}
