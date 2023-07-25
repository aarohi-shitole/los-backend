package com.techvg.los.service.mapper;

import com.techvg.los.domain.Branch;
import com.techvg.los.domain.Organisation;
import com.techvg.los.domain.SequenceGenerator;
import com.techvg.los.service.dto.BranchDTO;
import com.techvg.los.service.dto.OrganisationDTO;
import com.techvg.los.service.dto.SequenceGeneratorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SequenceGenerator} and its DTO {@link SequenceGeneratorDTO}.
 */
@Mapper(componentModel = "spring")
public interface SequenceGeneratorMapper extends EntityMapper<SequenceGeneratorDTO, SequenceGenerator> {
    @Mapping(target = "branch", source = "branch", qualifiedByName = "branchId")
    @Mapping(target = "organisation", source = "organisation", qualifiedByName = "organisationId")
    SequenceGeneratorDTO toDto(SequenceGenerator s);

    @Named("branchId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BranchDTO toDtoBranchId(Branch branch);

    @Named("organisationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganisationDTO toDtoOrganisationId(Organisation organisation);
}
