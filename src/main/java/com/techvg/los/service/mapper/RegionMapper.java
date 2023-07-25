package com.techvg.los.service.mapper;

import com.techvg.los.domain.Organisation;
import com.techvg.los.domain.Region;
import com.techvg.los.service.dto.OrganisationDTO;
import com.techvg.los.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Region} and its DTO {@link RegionDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegionMapper extends EntityMapper<RegionDTO, Region> {
    @Mapping(target = "organisation", source = "organisation", qualifiedByName = "organisationId")
    RegionDTO toDto(Region s);

    @Named("organisationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganisationDTO toDtoOrganisationId(Organisation organisation);
}
