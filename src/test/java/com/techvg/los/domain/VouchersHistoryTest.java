package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VouchersHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VouchersHistory.class);
        VouchersHistory vouchersHistory1 = new VouchersHistory();
        vouchersHistory1.setId(1L);
        VouchersHistory vouchersHistory2 = new VouchersHistory();
        vouchersHistory2.setId(vouchersHistory1.getId());
        assertThat(vouchersHistory1).isEqualTo(vouchersHistory2);
        vouchersHistory2.setId(2L);
        assertThat(vouchersHistory1).isNotEqualTo(vouchersHistory2);
        vouchersHistory1.setId(null);
        assertThat(vouchersHistory1).isNotEqualTo(vouchersHistory2);
    }
}
