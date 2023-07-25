package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LedgerAccountsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LedgerAccountsDTO.class);
        LedgerAccountsDTO ledgerAccountsDTO1 = new LedgerAccountsDTO();
        ledgerAccountsDTO1.setId(1L);
        LedgerAccountsDTO ledgerAccountsDTO2 = new LedgerAccountsDTO();
        assertThat(ledgerAccountsDTO1).isNotEqualTo(ledgerAccountsDTO2);
        ledgerAccountsDTO2.setId(ledgerAccountsDTO1.getId());
        assertThat(ledgerAccountsDTO1).isEqualTo(ledgerAccountsDTO2);
        ledgerAccountsDTO2.setId(2L);
        assertThat(ledgerAccountsDTO1).isNotEqualTo(ledgerAccountsDTO2);
        ledgerAccountsDTO1.setId(null);
        assertThat(ledgerAccountsDTO1).isNotEqualTo(ledgerAccountsDTO2);
    }
}
