package com.techvg.los.service.mapper;

import com.techvg.los.domain.Address;
import com.techvg.los.domain.LoanAccount;
import com.techvg.los.domain.LoanApplications;
import com.techvg.los.domain.LoanApplicationsHistory;
import com.techvg.los.domain.Member;
import com.techvg.los.domain.Product;
import com.techvg.los.domain.SecurityUser;
import com.techvg.los.service.dto.AddressDTO;
import com.techvg.los.service.dto.LoanAccountDTO;
import com.techvg.los.service.dto.LoanApplicationsDTO;
import com.techvg.los.service.dto.LoanApplicationsHistoryDTO;
import com.techvg.los.service.dto.MemberDTO;
import com.techvg.los.service.dto.ProductDTO;
import com.techvg.los.service.dto.SecurityUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LoanApplications} and its DTO
 * {@link LoanApplicationsDTO}.
 */
@Mapper(componentModel = "spring")
public interface LoanApplicationsHistoryMapper extends EntityMapper<LoanApplicationsHistoryDTO, LoanApplicationsHistory> {
    // @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "securityUserId.id", source = "securityUser.id")
    @Mapping(target = "assignedTo.id", source = "assignedTo.id")
    @Mapping(target = "assignedFrom.id", source = "assignedFrom.id")
    @Mapping(target = "product.id", source = "product.id")
    @Mapping(target = "branch.id", source = "branch.id")
    @Mapping(target = "loanApplication.id", source = "loanApplications.id")
    LoanApplicationsHistoryDTO toDto(LoanApplicationsHistory s);

    // @Mapping(source = "memberId", target = "member.id")
    @Mapping(source = "securityUserId.id", target = "securityUser.id")
    @Mapping(source = "assignedTo.id", target = "assignedTo.id")
    @Mapping(source = "assignedFrom.id", target = "assignedFrom.id")
    @Mapping(source = "product.id", target = "product.id")
    @Mapping(target = "branch.id", source = "branch.id")
    @Mapping(target = "loanApplications.id", source = "loanApplication.id")
    LoanApplicationsHistory toEntity(LoanApplicationsHistoryDTO loanApplicationsHistoryDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract LoanApplicationsHistoryDTO toDtoId(LoanApplicationsHistory loanApplicationsHistory);
}
