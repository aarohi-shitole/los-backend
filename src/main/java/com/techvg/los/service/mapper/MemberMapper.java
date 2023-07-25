package com.techvg.los.service.mapper;

import com.techvg.los.domain.Branch;
import com.techvg.los.domain.Member;
import com.techvg.los.domain.Organisation;
import com.techvg.los.service.dto.BranchDTO;
import com.techvg.los.service.dto.MemberDTO;
import com.techvg.los.service.dto.OrganisationDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Member} and its DTO {@link MemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface MemberMapper extends EntityMapper<MemberDTO, Member> {
    // @Mapping(target = "enquiryDetailsId", source = "enquiryDetails.id")
    @Mapping(target = "organisation", source = "organisation", qualifiedByName = "organisationId")
    @Mapping(target = "branchId", source = "branch.id")
    @Mapping(target = "securityUserId", source = "securityUser.id")
    MemberDTO toDto(Member s);

    @Mapping(target = "securityUser.id", source = "securityUserId")
    @Mapping(target = "branch.id", source = "branchId")
    Member toEntity(MemberDTO memberDTO);

    @Named("organisationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganisationDTO toDtoOrganisationId(Organisation organisation);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract MemberDTO toDtoId(Member member);
}
