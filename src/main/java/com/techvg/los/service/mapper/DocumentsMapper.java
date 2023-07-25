package com.techvg.los.service.mapper;

import com.techvg.los.domain.Documents;
import com.techvg.los.service.dto.DocumentsDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Documents} and its DTO {@link DocumentsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentsMapper extends EntityMapper<DocumentsDTO, Documents> {
    @Mapping(target = "member.id", source = "member.id")
    //    @Mapping(target = "guarantorId", source = "guarantor.id")
    DocumentsDTO toDto(Documents s);

    @Mapping(source = "member.id", target = "member.id")
    //    @Mapping(source = "guarantorId", target = "guarantor.id")
    Documents toEntity(DocumentsDTO documentsDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract DocumentsDTO toDtoId(Documents documents);
}
