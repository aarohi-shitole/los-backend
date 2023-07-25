package com.techvg.los.service.mapper;

import com.techvg.los.domain.Product;
import com.techvg.los.service.dto.ProductDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "loanCatagory.id", source = "loanCatagory.id")
    @Mapping(target = "organisationId", source = "organisation.id")
    @Mapping(target = "ledgerAccountsId", source = "ledgerAccounts.id")
    ProductDTO toDto(Product s);

    @Mapping(source = "organisationId", target = "organisation.id")
    // @Mapping(source = "ledgerAccountsId", target = "ledgerAccounts.id")
    @Mapping(source = "loanCatagory.id", target = "loanCatagory.id")
    Product toEntity(ProductDTO productDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract ProductDTO toDtoId(Product product);
}
