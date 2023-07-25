package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanDisbursementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanDisbursementDTO.class);
        LoanDisbursementDTO loanDisbursementDTO1 = new LoanDisbursementDTO();
        loanDisbursementDTO1.setId(1L);
        LoanDisbursementDTO loanDisbursementDTO2 = new LoanDisbursementDTO();
        assertThat(loanDisbursementDTO1).isNotEqualTo(loanDisbursementDTO2);
        loanDisbursementDTO2.setId(loanDisbursementDTO1.getId());
        assertThat(loanDisbursementDTO1).isEqualTo(loanDisbursementDTO2);
        loanDisbursementDTO2.setId(2L);
        assertThat(loanDisbursementDTO1).isNotEqualTo(loanDisbursementDTO2);
        loanDisbursementDTO1.setId(null);
        assertThat(loanDisbursementDTO1).isNotEqualTo(loanDisbursementDTO2);
    }
}
