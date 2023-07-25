package com.techvg.los.service.mapper;

import com.techvg.los.domain.LoanAccount;
import com.techvg.los.domain.LoanDisbursement;
import com.techvg.los.domain.Product;
import com.techvg.los.domain.SecurityUser;
import com.techvg.los.service.dto.LoanAccountDTO;
import com.techvg.los.service.dto.LoanDisbursementDTO;
import com.techvg.los.service.dto.ProductDTO;
import com.techvg.los.service.dto.SecurityUserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LoanDisbursement} and its DTO {@link LoanDisbursementDTO}.
 */
@Mapper(componentModel = "spring")
public interface LoanDisbursementMapper extends EntityMapper<LoanDisbursementDTO, LoanDisbursement> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    @Mapping(target = "securityUserId", source = "securityUser.id")
    @Mapping(target = "loanAccount", source = "loanAccount", qualifiedByName = "loanAccountId")
    LoanDisbursementDTO toDto(LoanDisbursement s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);

    @Named("loanAccountId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LoanAccountDTO toDtoLoanAccountId(LoanAccount loanAccount);
    
    @Named("id")
   	@BeanMapping(ignoreByDefault = true)
   	@Mapping(target = "id", source = "id")
   	public abstract LoanDisbursementDTO toDtoId(LoanDisbursement loanDisbursement);
}
