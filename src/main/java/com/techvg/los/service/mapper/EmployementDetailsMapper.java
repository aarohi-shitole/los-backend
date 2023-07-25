package com.techvg.los.service.mapper;

import com.techvg.los.domain.EmployementDetails;
import com.techvg.los.service.dto.EmployementDetailsDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link EmployementDetails} and its DTO
 * {@link EmployementDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployementDetailsMapper extends EntityMapper<EmployementDetailsDTO, EmployementDetails> {
    @Mapping(target = "member.id", source = "member.id")
    @Mapping(target = "guarantor.id", source = "guarantor.id")
    @Mapping(target = "address.id", source = "address.id")
    EmployementDetailsDTO toDto(EmployementDetails s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract EmployementDetailsDTO toDtoId(EmployementDetails employementDetails);
}
