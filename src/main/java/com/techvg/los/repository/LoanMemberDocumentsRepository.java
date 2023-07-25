package com.techvg.los.repository;

import com.techvg.los.domain.LoanMemberDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LoanApplicationMembers entity.
 */
@Repository
public interface LoanMemberDocumentsRepository
    extends JpaRepository<LoanMemberDocuments, Long>, JpaSpecificationExecutor<LoanMemberDocuments> {}
