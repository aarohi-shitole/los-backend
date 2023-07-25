package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AmortizationDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationDetailsDTO.class);
        AmortizationDetailsDTO amortizationDetailsDTO1 = new AmortizationDetailsDTO();
        amortizationDetailsDTO1.setId(1L);
        AmortizationDetailsDTO amortizationDetailsDTO2 = new AmortizationDetailsDTO();
        assertThat(amortizationDetailsDTO1).isNotEqualTo(amortizationDetailsDTO2);
        amortizationDetailsDTO2.setId(amortizationDetailsDTO1.getId());
        assertThat(amortizationDetailsDTO1).isEqualTo(amortizationDetailsDTO2);
        amortizationDetailsDTO2.setId(2L);
        assertThat(amortizationDetailsDTO1).isNotEqualTo(amortizationDetailsDTO2);
        amortizationDetailsDTO1.setId(null);
        assertThat(amortizationDetailsDTO1).isNotEqualTo(amortizationDetailsDTO2);
    }
}
