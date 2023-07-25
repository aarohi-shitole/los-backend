package com.techvg.los.service.mapper;

import com.techvg.los.domain.Declearation;
import com.techvg.los.domain.Organisation;
import com.techvg.los.service.dto.DeclearationDTO;
import com.techvg.los.service.dto.OrganisationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Declearation} and its DTO {@link DeclearationDTO}.
 */
@Mapper(componentModel = "spring")
public interface DeclearationMapper extends EntityMapper<DeclearationDTO, Declearation> {
    @Mapping(target = "organisation", source = "organisation", qualifiedByName = "organisationId")
    DeclearationDTO toDto(Declearation s);

    @Named("organisationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganisationDTO toDtoOrganisationId(Organisation organisation);
}
