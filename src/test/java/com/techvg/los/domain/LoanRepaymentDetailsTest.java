package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanRepaymentDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanRepaymentDetails.class);
        LoanRepaymentDetails loanRepaymentDetails1 = new LoanRepaymentDetails();
        loanRepaymentDetails1.setId(1L);
        LoanRepaymentDetails loanRepaymentDetails2 = new LoanRepaymentDetails();
        loanRepaymentDetails2.setId(loanRepaymentDetails1.getId());
        assertThat(loanRepaymentDetails1).isEqualTo(loanRepaymentDetails2);
        loanRepaymentDetails2.setId(2L);
        assertThat(loanRepaymentDetails1).isNotEqualTo(loanRepaymentDetails2);
        loanRepaymentDetails1.setId(null);
        assertThat(loanRepaymentDetails1).isNotEqualTo(loanRepaymentDetails2);
    }
}
