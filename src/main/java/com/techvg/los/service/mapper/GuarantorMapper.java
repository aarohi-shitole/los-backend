package com.techvg.los.service.mapper;

import com.techvg.los.domain.Guarantor;
import com.techvg.los.service.dto.GuarantorDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Guarantor} and its DTO {@link GuarantorDTO}.
 */
@Mapper(componentModel = "spring")
public interface GuarantorMapper extends EntityMapper<GuarantorDTO, Guarantor> {
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "loanApplicationsId", source = "loanApplications.id")
    GuarantorDTO toDto(Guarantor s);

    @Mapping(target = "member.id", source = "memberId")
    @Mapping(target = "loanApplications.id", source = "loanApplicationsId")
    Guarantor toEntity(GuarantorDTO guarantorDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract GuarantorDTO toDtoId(Guarantor guarantor);
}
