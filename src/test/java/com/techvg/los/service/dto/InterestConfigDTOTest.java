package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InterestConfigDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterestConfigDTO.class);
        InterestConfigDTO interestConfigDTO1 = new InterestConfigDTO();
        interestConfigDTO1.setId(1L);
        InterestConfigDTO interestConfigDTO2 = new InterestConfigDTO();
        assertThat(interestConfigDTO1).isNotEqualTo(interestConfigDTO2);
        interestConfigDTO2.setId(interestConfigDTO1.getId());
        assertThat(interestConfigDTO1).isEqualTo(interestConfigDTO2);
        interestConfigDTO2.setId(2L);
        assertThat(interestConfigDTO1).isNotEqualTo(interestConfigDTO2);
        interestConfigDTO1.setId(null);
        assertThat(interestConfigDTO1).isNotEqualTo(interestConfigDTO2);
    }
}
