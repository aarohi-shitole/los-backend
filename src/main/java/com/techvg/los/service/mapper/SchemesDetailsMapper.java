package com.techvg.los.service.mapper;

import com.techvg.los.domain.Organisation;
import com.techvg.los.domain.SchemesDetails;
import com.techvg.los.service.dto.OrganisationDTO;
import com.techvg.los.service.dto.SchemesDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SchemesDetails} and its DTO {@link SchemesDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface SchemesDetailsMapper extends EntityMapper<SchemesDetailsDTO, SchemesDetails> {
    @Mapping(target = "organisation", source = "organisation", qualifiedByName = "organisationId")
    SchemesDetailsDTO toDto(SchemesDetails s);

    @Named("organisationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganisationDTO toDtoOrganisationId(Organisation organisation);
}
