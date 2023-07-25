package com.techvg.los.service.mapper;

import com.techvg.los.domain.Branch;
import com.techvg.los.service.dto.BranchDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Branch} and its DTO {@link BranchDTO}.
 */
@Mapper(componentModel = "spring")
public interface BranchMapper extends EntityMapper<BranchDTO, Branch> {
    @Mapping(target = "address.id", source = "address.id")
    @Mapping(target = "organisationId", source = "organisation.id")
    //    @Mapping(target = "branchId", source = "branch.id")
    @Mapping(target = "regionId", source = "region.id")
    BranchDTO toDto(Branch s);

    //  @Mapping(target = "branch.id", source = "branchId")
    @Mapping(target = "organisation.id", source = "organisationId")
    @Mapping(target = "address.id", source = "address.id")
    @Mapping(target = "region.id", source = "regionId")
    Branch toEntity(BranchDTO branchDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract BranchDTO toDtoId(Branch branch);

    default Branch fromId(Long id) {
        if (id == null) {
            return null;
        }
        Branch branch = new Branch();
        branch.setId(id);
        return branch;
    }
}
