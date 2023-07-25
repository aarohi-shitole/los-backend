package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EpayTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Epay.class);
        Epay epay1 = new Epay();
        epay1.setId(1L);
        Epay epay2 = new Epay();
        epay2.setId(epay1.getId());
        assertThat(epay1).isEqualTo(epay2);
        epay2.setId(2L);
        assertThat(epay1).isNotEqualTo(epay2);
        epay1.setId(null);
        assertThat(epay1).isNotEqualTo(epay2);
    }
}
