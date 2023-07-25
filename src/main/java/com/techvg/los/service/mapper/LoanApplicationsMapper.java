package com.techvg.los.service.mapper;

import com.techvg.los.domain.LoanApplications;
import com.techvg.los.service.dto.LoanApplicationsDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link LoanApplications} and its DTO
 * {@link LoanApplicationsDTO}.
 */
@Mapper(componentModel = "spring")
public interface LoanApplicationsMapper extends EntityMapper<LoanApplicationsDTO, LoanApplications> {
    // @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "securityUserId", source = "securityUser.id")
    @Mapping(target = "assignedTo.id", source = "assignedTo.id")
    @Mapping(target = "assignedFrom.id", source = "assignedFrom.id")
    @Mapping(target = "product.id", source = "product.id")
    @Mapping(target = "branch.id", source = "branch.id")
    LoanApplicationsDTO toDto(LoanApplications s);

    // @Mapping(source = "memberId", target = "member.id")
    @Mapping(source = "securityUserId", target = "securityUser.id")
    @Mapping(source = "assignedTo.id", target = "assignedTo.id")
    @Mapping(source = "assignedFrom.id", target = "assignedFrom.id")
    @Mapping(source = "product.id", target = "product.id")
    @Mapping(target = "branch.id", source = "branch.id")
    LoanApplications toEntity(LoanApplicationsDTO loanApplicationsDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract LoanApplicationsDTO toDtoId(LoanApplications loanApplications);
}
