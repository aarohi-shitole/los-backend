package com.techvg.los.repository;

import com.techvg.los.domain.SecurityUser;
import com.techvg.los.domain.User;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SecurityUser entity.
 *
 * When extending this class, extend SecurityUserRepositoryWithBagRelationships
 * too. For more information refer to
 * https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface SecurityUserRepository extends JpaRepository<SecurityUser, Long>, JpaSpecificationExecutor<SecurityUser> {
    @Query(
        value = "select distinct securityUser from SecurityUser securityUser left join fetch securityUser.securityPermissions left join fetch securityUser.securityRoles",
        countQuery = "select count(distinct securityUser) from SecurityUser securityUser"
    )
    Page<SecurityUser> findAllWithEagerRelationships(Pageable pageable);

    @Query(
        "select distinct securityUser from SecurityUser securityUser left join fetch securityUser.securityPermissions left join fetch securityUser.securityRoles"
    )
    List<SecurityUser> findAllWithEagerRelationships();

    @Query(
        "select securityUser from SecurityUser securityUser left join fetch securityUser.securityPermissions left join fetch securityUser.securityRoles where securityUser.id =:id"
    )
    Optional<SecurityUser> findOneWithEagerRelationships(@Param("id") Long id);

    Optional<SecurityUser> findOneByActivationKey(String activationKey);

    List<SecurityUser> findAllByIsActivatedIsFalseAndActivationKeyIsNotNullAndLastModifiedBefore(Instant dateTime);

    Optional<SecurityUser> findOneByResetKey(String resetKey);

    Optional<SecurityUser> findOneByEmailIgnoreCase(String email);

    Optional<SecurityUser> findOneByUsername(String username);

    @EntityGraph(attributePaths = "securityRoles")
    Optional<SecurityUser> findOneWithSecurityRolesByEmailIgnoreCase(String email);

    Page<SecurityUser> findAllByUsernameNot(Pageable pageable, String username);

    Optional<SecurityUser> findOneWithSecurityPermissionsByUsername(String name);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = "securityRoles")
    @Query(
        value = "select l from SecurityUser l " +
        "join l.securityRoles sr " +
        "join sr.securityPermissions sp " +
        "where l.username = :username"
    )
    Optional<SecurityUser> findOneWithSecurityRolesByUsername(@Param("username") String username);
}
