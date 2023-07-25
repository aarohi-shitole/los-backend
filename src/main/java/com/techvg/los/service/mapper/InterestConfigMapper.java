package com.techvg.los.service.mapper;

import com.techvg.los.domain.InterestConfig;
import com.techvg.los.domain.Product;
import com.techvg.los.service.dto.InterestConfigDTO;
import com.techvg.los.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link InterestConfig} and its DTO {@link InterestConfigDTO}.
 */
@Mapper(componentModel = "spring")
public interface InterestConfigMapper extends EntityMapper<InterestConfigDTO, InterestConfig> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    InterestConfigDTO toDto(InterestConfig s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}
