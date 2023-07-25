package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemarkHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemarkHistory.class);
        RemarkHistory remarkHistory1 = new RemarkHistory();
        remarkHistory1.setId(1L);
        RemarkHistory remarkHistory2 = new RemarkHistory();
        remarkHistory2.setId(remarkHistory1.getId());
        assertThat(remarkHistory1).isEqualTo(remarkHistory2);
        remarkHistory2.setId(2L);
        assertThat(remarkHistory1).isNotEqualTo(remarkHistory2);
        remarkHistory1.setId(null);
        assertThat(remarkHistory1).isNotEqualTo(remarkHistory2);
    }
}
