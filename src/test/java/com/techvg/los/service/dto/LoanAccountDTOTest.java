package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanAccountDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanAccountDTO.class);
        LoanAccountDTO loanAccountDTO1 = new LoanAccountDTO();
        loanAccountDTO1.setId(1L);
        LoanAccountDTO loanAccountDTO2 = new LoanAccountDTO();
        assertThat(loanAccountDTO1).isNotEqualTo(loanAccountDTO2);
        loanAccountDTO2.setId(loanAccountDTO1.getId());
        assertThat(loanAccountDTO1).isEqualTo(loanAccountDTO2);
        loanAccountDTO2.setId(2L);
        assertThat(loanAccountDTO1).isNotEqualTo(loanAccountDTO2);
        loanAccountDTO1.setId(null);
        assertThat(loanAccountDTO1).isNotEqualTo(loanAccountDTO2);
    }
}
