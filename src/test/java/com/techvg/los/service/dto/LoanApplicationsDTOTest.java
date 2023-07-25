package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanApplicationsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanApplicationsDTO.class);
        LoanApplicationsDTO loanApplicationsDTO1 = new LoanApplicationsDTO();
        loanApplicationsDTO1.setId(1L);
        LoanApplicationsDTO loanApplicationsDTO2 = new LoanApplicationsDTO();
        assertThat(loanApplicationsDTO1).isNotEqualTo(loanApplicationsDTO2);
        loanApplicationsDTO2.setId(loanApplicationsDTO1.getId());
        assertThat(loanApplicationsDTO1).isEqualTo(loanApplicationsDTO2);
        loanApplicationsDTO2.setId(2L);
        assertThat(loanApplicationsDTO1).isNotEqualTo(loanApplicationsDTO2);
        loanApplicationsDTO1.setId(null);
        assertThat(loanApplicationsDTO1).isNotEqualTo(loanApplicationsDTO2);
    }
}
