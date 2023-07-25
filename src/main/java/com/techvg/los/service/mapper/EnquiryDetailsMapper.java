package com.techvg.los.service.mapper;

import com.techvg.los.domain.City;
import com.techvg.los.domain.District;
import com.techvg.los.domain.EnquiryDetails;
import com.techvg.los.domain.Product;
import com.techvg.los.domain.State;
import com.techvg.los.domain.Taluka;
import com.techvg.los.service.dto.CityDTO;
import com.techvg.los.service.dto.DistrictDTO;
import com.techvg.los.service.dto.EnquiryDetailsDTO;
import com.techvg.los.service.dto.ProductDTO;
import com.techvg.los.service.dto.StateDTO;
import com.techvg.los.service.dto.TalukaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EnquiryDetails} and its DTO {@link EnquiryDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EnquiryDetailsMapper extends EntityMapper<EnquiryDetailsDTO, EnquiryDetails> {
    @Mapping(target = "state", source = "state", qualifiedByName = "stateId")
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    @Mapping(target = "taluka", source = "taluka", qualifiedByName = "talukaId")
    @Mapping(target = "city", source = "city", qualifiedByName = "cityId")
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    EnquiryDetailsDTO toDto(EnquiryDetails s);

    @Named("stateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StateDTO toDtoStateId(State state);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);

    @Named("talukaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TalukaDTO toDtoTalukaId(Taluka taluka);

    @Named("cityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CityDTO toDtoCityId(City city);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}
