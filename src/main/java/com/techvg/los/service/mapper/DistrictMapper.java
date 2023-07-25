package com.techvg.los.service.mapper;

import com.techvg.los.domain.District;
import com.techvg.los.domain.State;
import com.techvg.los.service.dto.DistrictDTO;
import com.techvg.los.service.dto.StateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {
    @Mapping(target = "state", source = "state", qualifiedByName = "stateId")
    DistrictDTO toDto(District s);

    @Named("stateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StateDTO toDtoStateId(State state);
}
