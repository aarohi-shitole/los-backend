package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanApplicationsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanApplications.class);
        LoanApplications loanApplications1 = new LoanApplications();
        loanApplications1.setId(1L);
        LoanApplications loanApplications2 = new LoanApplications();
        loanApplications2.setId(loanApplications1.getId());
        assertThat(loanApplications1).isEqualTo(loanApplications2);
        loanApplications2.setId(2L);
        assertThat(loanApplications1).isNotEqualTo(loanApplications2);
        loanApplications1.setId(null);
        assertThat(loanApplications1).isNotEqualTo(loanApplications2);
    }
}
