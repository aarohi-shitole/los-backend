package com.techvg.los.service.mapper;

import com.techvg.los.domain.Branch;
import com.techvg.los.domain.LedgerAccounts;
import com.techvg.los.service.dto.BranchDTO;
import com.techvg.los.service.dto.LedgerAccountsDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link LedgerAccounts} and its DTO {@link LedgerAccountsDTO}.
 */
@Mapper(componentModel = "spring")
public interface LedgerAccountsMapper extends EntityMapper<LedgerAccountsDTO, LedgerAccounts> {
    @Mapping(target = "branch", source = "branch", qualifiedByName = "branchId")
    @Mapping(target = "ledgerAccountsId", source = "ledgerAccounts.id")
    LedgerAccountsDTO toDto(LedgerAccounts s);

    @Mapping(source = "ledgerAccountsId", target = "ledgerAccounts.id")
    LedgerAccounts toEntity(LedgerAccountsDTO ledgerAccountsDTO);

    @Named("branchId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BranchDTO toDtoBranchId(Branch branch);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    public abstract LedgerAccountsDTO toDtoId(LedgerAccounts ledgerAccounts);
}
