package com.techvg.los.service.mapper;

import com.techvg.los.domain.Branch;
import com.techvg.los.domain.LoanAccount;
import com.techvg.los.domain.SecurityPermission;
import com.techvg.los.domain.SecurityRole;
import com.techvg.los.domain.SecurityUser;
import com.techvg.los.service.dto.BranchDTO;
import com.techvg.los.service.dto.LoanAccountDTO;
import com.techvg.los.service.dto.SecurityPermissionDTO;
import com.techvg.los.service.dto.SecurityRoleDTO;
import com.techvg.los.service.dto.SecurityUserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SecurityUser} and its DTO {@link SecurityUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface SecurityUserMapper extends EntityMapper<SecurityUserDTO, SecurityUser> {
    @Mapping(target = "branchId", source = "branch.id")
    @Mapping(source = "organisation.id", target = "organisation.id")
    @Mapping(target = "securityPermissions", source = "securityPermissions", qualifiedByName = "securityPermissionPermissionNameSet")
    @Mapping(target = "securityRoles", source = "securityRoles", qualifiedByName = "securityRoleRoleNameSet")
    SecurityUserDTO toDto(SecurityUser s);

    @Mapping(target = "branch.id", source = "branchId")
    @Mapping(target = "removeSecurityPermission", ignore = true)
    @Mapping(target = "removeSecurityRole", ignore = true)
    SecurityUser toEntity(SecurityUserDTO securityUserDTO);

    @Named("securityPermissionPermissionName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "permissionName", source = "permissionName")
    SecurityPermissionDTO toDtoSecurityPermissionPermissionName(SecurityPermission securityPermission);

    @Named("securityPermissionPermissionNameSet")
    default Set<SecurityPermissionDTO> toDtoSecurityPermissionPermissionNameSet(Set<SecurityPermission> securityPermission) {
        return securityPermission.stream().map(this::toDtoSecurityPermissionPermissionName).collect(Collectors.toSet());
    }

    @Named("securityRoleRoleName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "roleName", source = "roleName")
    SecurityRoleDTO toDtoSecurityRoleRoleName(SecurityRole securityRole);

    @Named("securityRoleRoleNameSet")
    default Set<SecurityRoleDTO> toDtoSecurityRoleRoleNameSet(Set<SecurityRole> securityRole) {
        return securityRole.stream().map(this::toDtoSecurityRoleRoleName).collect(Collectors.toSet());
    }

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract SecurityUserDTO toDtoId(SecurityUser securityUser);
}
