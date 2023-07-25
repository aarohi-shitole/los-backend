package com.techvg.los.service.mapper;

import com.techvg.los.domain.LoanApplicationMembers;
import com.techvg.los.service.dto.LoanApplicationMembersDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface LoanApplicationMembersMapper extends EntityMapper<LoanApplicationMembersDTO, LoanApplicationMembers> {
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "loanApplicationId", source = "loanApplications.id")
    LoanApplicationMembersDTO toDto(LoanApplicationMembers loanApplicationMembers);

    @Mapping(source = "memberId", target = "member.id")
    @Mapping(source = "loanApplicationId", target = "loanApplications.id")
    LoanApplicationMembers toEntity(LoanApplicationMembersDTO loanApplicationMembersDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract LoanApplicationMembersDTO toDtoId(LoanApplicationMembers loanApplicationMembers);
}
