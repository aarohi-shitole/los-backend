package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IncomeDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomeDetailsDTO.class);
        IncomeDetailsDTO incomeDetailsDTO1 = new IncomeDetailsDTO();
        incomeDetailsDTO1.setId(1L);
        IncomeDetailsDTO incomeDetailsDTO2 = new IncomeDetailsDTO();
        assertThat(incomeDetailsDTO1).isNotEqualTo(incomeDetailsDTO2);
        incomeDetailsDTO2.setId(incomeDetailsDTO1.getId());
        assertThat(incomeDetailsDTO1).isEqualTo(incomeDetailsDTO2);
        incomeDetailsDTO2.setId(2L);
        assertThat(incomeDetailsDTO1).isNotEqualTo(incomeDetailsDTO2);
        incomeDetailsDTO1.setId(null);
        assertThat(incomeDetailsDTO1).isNotEqualTo(incomeDetailsDTO2);
    }
}
