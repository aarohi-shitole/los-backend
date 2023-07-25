package com.techvg.los.service.mapper;

import com.techvg.los.domain.NpaSetting;
import com.techvg.los.domain.Organisation;
import com.techvg.los.service.dto.NpaSettingDTO;
import com.techvg.los.service.dto.OrganisationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link NpaSetting} and its DTO {@link NpaSettingDTO}.
 */
@Mapper(componentModel = "spring")
public interface NpaSettingMapper extends EntityMapper<NpaSettingDTO, NpaSetting> {
    @Mapping(target = "organisation", source = "organisation", qualifiedByName = "organisationId")
    NpaSettingDTO toDto(NpaSetting s);

    @Named("organisationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganisationDTO toDtoOrganisationId(Organisation organisation);
}
