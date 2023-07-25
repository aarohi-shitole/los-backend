package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanAccount.class);
        LoanAccount loanAccount1 = new LoanAccount();
        loanAccount1.setId(1L);
        LoanAccount loanAccount2 = new LoanAccount();
        loanAccount2.setId(loanAccount1.getId());
        assertThat(loanAccount1).isEqualTo(loanAccount2);
        loanAccount2.setId(2L);
        assertThat(loanAccount1).isNotEqualTo(loanAccount2);
        loanAccount1.setId(null);
        assertThat(loanAccount1).isNotEqualTo(loanAccount2);
    }
}
