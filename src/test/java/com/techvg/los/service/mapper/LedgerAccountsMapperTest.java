package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LedgerAccountsMapperTest {

    private LedgerAccountsMapper ledgerAccountsMapper;

    @BeforeEach
    public void setUp() {
        ledgerAccountsMapper = new LedgerAccountsMapperImpl();
    }
}
