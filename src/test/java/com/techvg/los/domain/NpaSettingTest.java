package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NpaSettingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NpaSetting.class);
        NpaSetting npaSetting1 = new NpaSetting();
        npaSetting1.setId(1L);
        NpaSetting npaSetting2 = new NpaSetting();
        npaSetting2.setId(npaSetting1.getId());
        assertThat(npaSetting1).isEqualTo(npaSetting2);
        npaSetting2.setId(2L);
        assertThat(npaSetting1).isNotEqualTo(npaSetting2);
        npaSetting1.setId(null);
        assertThat(npaSetting1).isNotEqualTo(npaSetting2);
    }
}
