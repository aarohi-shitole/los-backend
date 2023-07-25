package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanDisbursementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanDisbursement.class);
        LoanDisbursement loanDisbursement1 = new LoanDisbursement();
        loanDisbursement1.setId(1L);
        LoanDisbursement loanDisbursement2 = new LoanDisbursement();
        loanDisbursement2.setId(loanDisbursement1.getId());
        assertThat(loanDisbursement1).isEqualTo(loanDisbursement2);
        loanDisbursement2.setId(2L);
        assertThat(loanDisbursement1).isNotEqualTo(loanDisbursement2);
        loanDisbursement1.setId(null);
        assertThat(loanDisbursement1).isNotEqualTo(loanDisbursement2);
    }
}
