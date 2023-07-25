package com.techvg.los.service.mapper;

import com.techvg.los.domain.MemberAssets;
import com.techvg.los.service.dto.MemberAssetsDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link MemberAssets} and its DTO {@link MemberAssetsDTO}.
 */
@Mapper(componentModel = "spring", uses = { LoanApplicationsMapper.class, MemberMapper.class, GuarantorMapper.class })
public interface MemberAssetsMapper extends EntityMapper<MemberAssetsDTO, MemberAssets> {
    @Mapping(target = "member.id", source = "member.id")
    @Mapping(target = "guarantor.id", source = "guarantor.id")
    @Mapping(target = "loanApplications.id", source = "loanApplications.id")
    MemberAssetsDTO toDto(MemberAssets memberAssets);

    //  @Mapping(source = "loanApplicationsId", target = "loanApplications")
    MemberAssets toEntity(MemberAssetsDTO memberAssetsDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract MemberAssetsDTO toDtoId(MemberAssets memberAssets);
    //    default MemberAssets fromId(Long id) {
    //        if (id == null) {
    //            return null;
    //        }
    //        MemberAssets memberAssets = new MemberAssets();
    //        memberAssets.setId(id);
    //        return memberAssets;
    //    }
}
