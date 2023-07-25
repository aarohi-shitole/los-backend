package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EpayDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EpayDTO.class);
        EpayDTO epayDTO1 = new EpayDTO();
        epayDTO1.setId(1L);
        EpayDTO epayDTO2 = new EpayDTO();
        assertThat(epayDTO1).isNotEqualTo(epayDTO2);
        epayDTO2.setId(epayDTO1.getId());
        assertThat(epayDTO1).isEqualTo(epayDTO2);
        epayDTO2.setId(2L);
        assertThat(epayDTO1).isNotEqualTo(epayDTO2);
        epayDTO1.setId(null);
        assertThat(epayDTO1).isNotEqualTo(epayDTO2);
    }
}
