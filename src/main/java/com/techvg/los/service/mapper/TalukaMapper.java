package com.techvg.los.service.mapper;

import com.techvg.los.domain.District;
import com.techvg.los.domain.Taluka;
import com.techvg.los.service.dto.DistrictDTO;
import com.techvg.los.service.dto.TalukaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Taluka} and its DTO {@link TalukaDTO}.
 */
@Mapper(componentModel = "spring")
public interface TalukaMapper extends EntityMapper<TalukaDTO, Taluka> {
    @Mapping(target = "district", source = "district", qualifiedByName = "districtId")
    TalukaDTO toDto(Taluka s);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);
}
