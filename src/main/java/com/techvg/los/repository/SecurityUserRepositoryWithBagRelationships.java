package com.techvg.los.repository;

import com.techvg.los.domain.SecurityUser;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface SecurityUserRepositoryWithBagRelationships {
    Optional<SecurityUser> fetchBagRelationships(Optional<SecurityUser> securityUser);

    List<SecurityUser> fetchBagRelationships(List<SecurityUser> securityUsers);

    Page<SecurityUser> fetchBagRelationships(Page<SecurityUser> securityUsers);
}
