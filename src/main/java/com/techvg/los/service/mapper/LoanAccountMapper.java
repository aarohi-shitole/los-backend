package com.techvg.los.service.mapper;

import com.techvg.los.domain.LoanAccount;
import com.techvg.los.domain.Product;
import com.techvg.los.service.dto.LoanAccountDTO;
import com.techvg.los.service.dto.ProductDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link LoanAccount} and its DTO {@link LoanAccountDTO}.
 */
@Mapper(componentModel = "spring")
public interface LoanAccountMapper extends EntityMapper<LoanAccountDTO, LoanAccount> {
    @Mapping(target = "loanApplicationsId", source = "loanApplications.id")
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "product.id", source = "product.id")
    @Mapping(target = "branch.id", source = "branch.id")
    LoanAccountDTO toDto(LoanAccount s);

    @Mapping(source = "product.id", target = "product.id")
    @Mapping(target = "branch.id", source = "branch.id")
    @Mapping(source = "memberId", target = "member.id")
    @Mapping(source = "loanApplicationsId", target = "loanApplications.id")
    LoanAccount toEntity(LoanAccountDTO loanAccountDTO);

    //    @Named("productId")
    //    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    //    ProductDTO toDtoProductId(Product product);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract LoanAccountDTO toDtoId(LoanAccount loanAccount);
}
