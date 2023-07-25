package com.techvg.los.repository;

import com.techvg.los.domain.MemberAssets;
import com.techvg.los.domain.MortgagedAssets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface MortgagedAssetsRepository extends JpaRepository<MortgagedAssets, Long>, JpaSpecificationExecutor<MortgagedAssets> {}
