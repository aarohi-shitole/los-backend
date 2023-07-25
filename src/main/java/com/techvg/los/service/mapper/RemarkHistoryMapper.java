package com.techvg.los.service.mapper;

import com.techvg.los.domain.LoanAccount;
import com.techvg.los.domain.RemarkHistory;
import com.techvg.los.service.dto.LoanAccountDTO;
import com.techvg.los.service.dto.RemarkHistoryDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link RemarkHistory} and its DTO {@link RemarkHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface RemarkHistoryMapper extends EntityMapper<RemarkHistoryDTO, RemarkHistory> {
    @Mapping(target = "securityUserId", source = "securityUser.id")
    @Mapping(target = "loanApplications.id", source = "loanApplications.id")
    RemarkHistoryDTO toDto(RemarkHistory s);

    @Mapping(source = "securityUserId", target = "securityUser.id")
    // @Mapping(source = "loanApplicationId", target = "loanApplications.id")
    RemarkHistory toEntity(RemarkHistoryDTO remarkHistoryDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract LoanAccountDTO toDtoId(LoanAccount loanAccount);
}
