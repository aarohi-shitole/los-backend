package com.techvg.los.service.mapper;

import com.techvg.los.domain.Address;
import com.techvg.los.domain.Organisation;
import com.techvg.los.service.dto.AddressDTO;
import com.techvg.los.service.dto.OrganisationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Organisation} and its DTO
 * {@link OrganisationDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrganisationMapper extends EntityMapper<OrganisationDTO, Organisation> {
    @Mapping(target = "address.id", source = "address.id")
    OrganisationDTO toDto(Organisation s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract OrganisationDTO toDtoId(Organisation organisation);
}
