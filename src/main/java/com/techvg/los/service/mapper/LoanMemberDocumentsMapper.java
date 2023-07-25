package com.techvg.los.service.mapper;

import com.techvg.los.domain.Documents;
import com.techvg.los.domain.LoanMemberDocuments;
import com.techvg.los.service.dto.DocumentsDTO;
import com.techvg.los.service.dto.LoanMemberDocumentsDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link Documents} and its DTO {@link DocumentsDTO}.
 */
@Mapper(componentModel = "spring")
public interface LoanMemberDocumentsMapper extends EntityMapper<LoanMemberDocumentsDTO, LoanMemberDocuments> {
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "loanApplicationsId", source = "loanApplications.id")
    LoanMemberDocumentsDTO toDto(LoanMemberDocuments s);

    @Mapping(source = "memberId", target = "member.id")
    @Mapping(source = "loanApplicationsId", target = "loanApplications.id")
    LoanMemberDocuments toEntity(LoanMemberDocumentsDTO loanMemberDocumentsDTO);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract LoanMemberDocumentsDTO toDtoId(LoanMemberDocuments loanMemberDocuments);
}
