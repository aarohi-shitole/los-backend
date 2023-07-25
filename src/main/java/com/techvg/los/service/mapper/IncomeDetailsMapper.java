package com.techvg.los.service.mapper;

import com.techvg.los.domain.IncomeDetails;
import com.techvg.los.service.dto.IncomeDetailsDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link IncomeDetails} and its DTO
 * {@link IncomeDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface IncomeDetailsMapper extends EntityMapper<IncomeDetailsDTO, IncomeDetails> {
    @Mapping(target = "memberId", source = "member.id")
    IncomeDetailsDTO toDto(IncomeDetails s);

    @Mapping(source = "memberId", target = "member.id")
    IncomeDetails toEntity(IncomeDetailsDTO incomeDetailsDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract IncomeDetailsDTO toDtoId(IncomeDetails incomeDetails);
}
