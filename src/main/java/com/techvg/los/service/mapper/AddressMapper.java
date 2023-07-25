package com.techvg.los.service.mapper;

import com.techvg.los.domain.Address;
import com.techvg.los.service.dto.AddressDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "state.id", source = "state.id")
    @Mapping(target = "district.id", source = "district.id")
    @Mapping(target = "taluka.id", source = "taluka.id")
    @Mapping(target = "member.id", source = "member.id")
    AddressDTO toDto(Address s);

    @Mapping(target = "state.id", source = "state.id")
    @Mapping(target = "district.id", source = "district.id")
    @Mapping(source = "member.id", target = "member.id")
    @Mapping(target = "taluka.id", source = "taluka.id")
    Address toEntity(AddressDTO addressDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract AddressDTO toDtoId(Address address);
}
