package com.techvg.los.service.mapper;

import com.techvg.los.domain.MemberExistingfacility;
import com.techvg.los.service.dto.MemberExistingfacilityDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link MemberExistingfacility} and its DTO {@link MemberExistingfacilityDTO}.
 */
@Mapper(componentModel = "spring")
public interface MemberExistingfacilityMapper extends EntityMapper<MemberExistingfacilityDTO, MemberExistingfacility> {
    @Mapping(target = "memberId", source = "member.id")
    MemberExistingfacilityDTO toDto(MemberExistingfacility s);

    @Mapping(source = "memberId", target = "member.id")
    MemberExistingfacility toEntity(MemberExistingfacilityDTO memberExistingfacilityDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract MemberExistingfacilityDTO toDtoId(MemberExistingfacility address);
}
