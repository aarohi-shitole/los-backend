package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanRepaymentDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanRepaymentDetailsDTO.class);
        LoanRepaymentDetailsDTO loanRepaymentDetailsDTO1 = new LoanRepaymentDetailsDTO();
        loanRepaymentDetailsDTO1.setId(1L);
        LoanRepaymentDetailsDTO loanRepaymentDetailsDTO2 = new LoanRepaymentDetailsDTO();
        assertThat(loanRepaymentDetailsDTO1).isNotEqualTo(loanRepaymentDetailsDTO2);
        loanRepaymentDetailsDTO2.setId(loanRepaymentDetailsDTO1.getId());
        assertThat(loanRepaymentDetailsDTO1).isEqualTo(loanRepaymentDetailsDTO2);
        loanRepaymentDetailsDTO2.setId(2L);
        assertThat(loanRepaymentDetailsDTO1).isNotEqualTo(loanRepaymentDetailsDTO2);
        loanRepaymentDetailsDTO1.setId(null);
        assertThat(loanRepaymentDetailsDTO1).isNotEqualTo(loanRepaymentDetailsDTO2);
    }
}
