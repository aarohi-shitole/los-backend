package com.techvg.los.service.mapper;

import com.techvg.los.domain.City;
import com.techvg.los.domain.Taluka;
import com.techvg.los.service.dto.CityDTO;
import com.techvg.los.service.dto.TalukaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<CityDTO, City> {
    @Mapping(target = "taluka", source = "taluka", qualifiedByName = "talukaId")
    CityDTO toDto(City s);

    @Named("talukaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TalukaDTO toDtoTalukaId(Taluka taluka);
}
