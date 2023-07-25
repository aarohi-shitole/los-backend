package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GuarantorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuarantorDTO.class);
        GuarantorDTO guarantorDTO1 = new GuarantorDTO();
        guarantorDTO1.setId(1L);
        GuarantorDTO guarantorDTO2 = new GuarantorDTO();
        assertThat(guarantorDTO1).isNotEqualTo(guarantorDTO2);
        guarantorDTO2.setId(guarantorDTO1.getId());
        assertThat(guarantorDTO1).isEqualTo(guarantorDTO2);
        guarantorDTO2.setId(2L);
        assertThat(guarantorDTO1).isNotEqualTo(guarantorDTO2);
        guarantorDTO1.setId(null);
        assertThat(guarantorDTO1).isNotEqualTo(guarantorDTO2);
    }
}
