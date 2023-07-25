package com.techvg.los.service.mapper;

import com.techvg.los.domain.Documents;
import com.techvg.los.domain.OrgPrerequisiteDoc;
import com.techvg.los.domain.Organisation;
import com.techvg.los.domain.Product;
import com.techvg.los.service.dto.DocumentsDTO;
import com.techvg.los.service.dto.OrgPrerequisiteDocDTO;
import com.techvg.los.service.dto.OrganisationDTO;
import com.techvg.los.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrgPrerequisiteDoc} and its DTO {@link OrgPrerequisiteDocDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrgPrerequisiteDocMapper extends EntityMapper<OrgPrerequisiteDocDTO, OrgPrerequisiteDoc> {
    @Mapping(target = "product.id", source = "product.id")
    @Mapping(target = "organisation", source = "organisation", qualifiedByName = "organisationId")
    OrgPrerequisiteDocDTO toDto(OrgPrerequisiteDoc s);

    @Named("organisationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganisationDTO toDtoOrganisationId(Organisation organisation);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract OrgPrerequisiteDocDTO toDtoId(OrgPrerequisiteDoc orgPrerequisiteDoc);
}
