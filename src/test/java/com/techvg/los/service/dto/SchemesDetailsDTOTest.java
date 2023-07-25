package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SchemesDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchemesDetailsDTO.class);
        SchemesDetailsDTO schemesDetailsDTO1 = new SchemesDetailsDTO();
        schemesDetailsDTO1.setId(1L);
        SchemesDetailsDTO schemesDetailsDTO2 = new SchemesDetailsDTO();
        assertThat(schemesDetailsDTO1).isNotEqualTo(schemesDetailsDTO2);
        schemesDetailsDTO2.setId(schemesDetailsDTO1.getId());
        assertThat(schemesDetailsDTO1).isEqualTo(schemesDetailsDTO2);
        schemesDetailsDTO2.setId(2L);
        assertThat(schemesDetailsDTO1).isNotEqualTo(schemesDetailsDTO2);
        schemesDetailsDTO1.setId(null);
        assertThat(schemesDetailsDTO1).isNotEqualTo(schemesDetailsDTO2);
    }
}
