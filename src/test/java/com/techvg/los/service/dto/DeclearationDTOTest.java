package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeclearationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeclearationDTO.class);
        DeclearationDTO declearationDTO1 = new DeclearationDTO();
        declearationDTO1.setId(1L);
        DeclearationDTO declearationDTO2 = new DeclearationDTO();
        assertThat(declearationDTO1).isNotEqualTo(declearationDTO2);
        declearationDTO2.setId(declearationDTO1.getId());
        assertThat(declearationDTO1).isEqualTo(declearationDTO2);
        declearationDTO2.setId(2L);
        assertThat(declearationDTO1).isNotEqualTo(declearationDTO2);
        declearationDTO1.setId(null);
        assertThat(declearationDTO1).isNotEqualTo(declearationDTO2);
    }
}
