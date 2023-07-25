package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LedgerAccountsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LedgerAccounts.class);
        LedgerAccounts ledgerAccounts1 = new LedgerAccounts();
        ledgerAccounts1.setId(1L);
        LedgerAccounts ledgerAccounts2 = new LedgerAccounts();
        ledgerAccounts2.setId(ledgerAccounts1.getId());
        assertThat(ledgerAccounts1).isEqualTo(ledgerAccounts2);
        ledgerAccounts2.setId(2L);
        assertThat(ledgerAccounts1).isNotEqualTo(ledgerAccounts2);
        ledgerAccounts1.setId(null);
        assertThat(ledgerAccounts1).isNotEqualTo(ledgerAccounts2);
    }
}
