package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NpaSettingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NpaSettingDTO.class);
        NpaSettingDTO npaSettingDTO1 = new NpaSettingDTO();
        npaSettingDTO1.setId(1L);
        NpaSettingDTO npaSettingDTO2 = new NpaSettingDTO();
        assertThat(npaSettingDTO1).isNotEqualTo(npaSettingDTO2);
        npaSettingDTO2.setId(npaSettingDTO1.getId());
        assertThat(npaSettingDTO1).isEqualTo(npaSettingDTO2);
        npaSettingDTO2.setId(2L);
        assertThat(npaSettingDTO1).isNotEqualTo(npaSettingDTO2);
        npaSettingDTO1.setId(null);
        assertThat(npaSettingDTO1).isNotEqualTo(npaSettingDTO2);
    }
}
