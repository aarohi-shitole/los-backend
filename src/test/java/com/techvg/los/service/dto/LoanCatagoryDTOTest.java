package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanCatagoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanCatagoryDTO.class);
        LoanCatagoryDTO loanCatagoryDTO1 = new LoanCatagoryDTO();
        loanCatagoryDTO1.setId(1L);
        LoanCatagoryDTO loanCatagoryDTO2 = new LoanCatagoryDTO();
        assertThat(loanCatagoryDTO1).isNotEqualTo(loanCatagoryDTO2);
        loanCatagoryDTO2.setId(loanCatagoryDTO1.getId());
        assertThat(loanCatagoryDTO1).isEqualTo(loanCatagoryDTO2);
        loanCatagoryDTO2.setId(2L);
        assertThat(loanCatagoryDTO1).isNotEqualTo(loanCatagoryDTO2);
        loanCatagoryDTO1.setId(null);
        assertThat(loanCatagoryDTO1).isNotEqualTo(loanCatagoryDTO2);
    }
}
