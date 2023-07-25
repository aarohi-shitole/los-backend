package com.techvg.los.service.mapper;

import com.techvg.los.domain.Organisation;
import com.techvg.los.domain.ParameterLookup;
import com.techvg.los.service.dto.OrganisationDTO;
import com.techvg.los.service.dto.ParameterLookupDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ParameterLookup} and its DTO {@link ParameterLookupDTO}.
 */
@Mapper(componentModel = "spring")
public interface ParameterLookupMapper extends EntityMapper<ParameterLookupDTO, ParameterLookup> {
    @Mapping(target = "organisation", source = "organisation", qualifiedByName = "organisationId")
    ParameterLookupDTO toDto(ParameterLookup s);

    @Named("organisationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganisationDTO toDtoOrganisationId(Organisation organisation);
}
