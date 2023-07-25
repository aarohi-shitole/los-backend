package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployementDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployementDetailsDTO.class);
        EmployementDetailsDTO employementDetailsDTO1 = new EmployementDetailsDTO();
        employementDetailsDTO1.setId(1L);
        EmployementDetailsDTO employementDetailsDTO2 = new EmployementDetailsDTO();
        assertThat(employementDetailsDTO1).isNotEqualTo(employementDetailsDTO2);
        employementDetailsDTO2.setId(employementDetailsDTO1.getId());
        assertThat(employementDetailsDTO1).isEqualTo(employementDetailsDTO2);
        employementDetailsDTO2.setId(2L);
        assertThat(employementDetailsDTO1).isNotEqualTo(employementDetailsDTO2);
        employementDetailsDTO1.setId(null);
        assertThat(employementDetailsDTO1).isNotEqualTo(employementDetailsDTO2);
    }
}
