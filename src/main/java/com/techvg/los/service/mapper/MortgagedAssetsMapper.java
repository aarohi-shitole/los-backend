package com.techvg.los.service.mapper;

import com.techvg.los.domain.MortgagedAssets;
import com.techvg.los.service.dto.MortgagedAssetsDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = { LoanApplicationsMapper.class, MemberMapper.class, MemberAssetsMapper.class })
public interface MortgagedAssetsMapper extends EntityMapper<MortgagedAssetsDTO, MortgagedAssets> {
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "memberAssets.id", source = "memberAssets.id")
    @Mapping(target = "loanApplicationsId", source = "loanApplications.id")
    MortgagedAssetsDTO toDto(MortgagedAssets mortgagedAssets);

    @Mapping(source = "memberId", target = "member.id")
    @Mapping(source = "loanApplicationsId", target = "loanApplications.id")
    MortgagedAssets toEntity(MortgagedAssetsDTO mortgagedAssetsDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract MortgagedAssetsDTO toDtoId(MortgagedAssets mortgagedAssets);
}
