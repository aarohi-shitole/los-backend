package com.techvg.los.service.mapper;

import com.techvg.los.domain.AccountHeadCode;
import com.techvg.los.domain.LedgerAccounts;
import com.techvg.los.service.dto.AccountHeadCodeDTO;
import com.techvg.los.service.dto.LedgerAccountsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AccountHeadCode} and its DTO {@link AccountHeadCodeDTO}.
 */
@Mapper(componentModel = "spring")
public interface AccountHeadCodeMapper extends EntityMapper<AccountHeadCodeDTO, AccountHeadCode> {
    @Mapping(target = "ledgerAccounts", source = "ledgerAccounts", qualifiedByName = "ledgerAccountsId")
    AccountHeadCodeDTO toDto(AccountHeadCode s);

    @Named("ledgerAccountsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LedgerAccountsDTO toDtoLedgerAccountsId(LedgerAccounts ledgerAccounts);
}
