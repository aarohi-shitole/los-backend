package com.techvg.los.service.mapper;

import com.techvg.los.domain.AmortizationDetails;
import com.techvg.los.domain.LoanAccount;
import com.techvg.los.domain.LoanRepaymentDetails;
import com.techvg.los.service.dto.AmortizationDetailsDTO;
import com.techvg.los.service.dto.LoanAccountDTO;
import com.techvg.los.service.dto.LoanRepaymentDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LoanRepaymentDetails} and its DTO {@link LoanRepaymentDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface LoanRepaymentDetailsMapper extends EntityMapper<LoanRepaymentDetailsDTO, LoanRepaymentDetails> {
    @Mapping(target = "loanAccountId", source = "loanAccount.id")
    LoanRepaymentDetailsDTO toDto(LoanRepaymentDetails s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract LoanRepaymentDetailsDTO toDtoId(LoanRepaymentDetails loanRepaymentDetails);
}
