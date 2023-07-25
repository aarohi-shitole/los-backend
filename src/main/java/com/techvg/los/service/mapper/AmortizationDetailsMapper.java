package com.techvg.los.service.mapper;

import com.techvg.los.domain.AmortizationDetails;
import com.techvg.los.domain.Branch;
import com.techvg.los.domain.LoanAccount;
import com.techvg.los.service.dto.AmortizationDetailsDTO;
import com.techvg.los.service.dto.BranchDTO;
import com.techvg.los.service.dto.LoanAccountDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AmortizationDetails} and its DTO {@link AmortizationDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface AmortizationDetailsMapper extends EntityMapper<AmortizationDetailsDTO, AmortizationDetails> {
    @Mapping(target = "loanAccountId", source = "loanAccount.id")
    AmortizationDetailsDTO toDto(AmortizationDetails s);

    //    @Named("loanAccountId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    LoanAccountDTO toDtoLoanAccountId(LoanAccount loanAccount);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract AmortizationDetailsDTO toDtoId(AmortizationDetails amortizationDetails);
}
