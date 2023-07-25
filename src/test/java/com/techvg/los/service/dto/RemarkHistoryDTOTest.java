package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RemarkHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RemarkHistoryDTO.class);
        RemarkHistoryDTO remarkHistoryDTO1 = new RemarkHistoryDTO();
        remarkHistoryDTO1.setId(1L);
        RemarkHistoryDTO remarkHistoryDTO2 = new RemarkHistoryDTO();
        assertThat(remarkHistoryDTO1).isNotEqualTo(remarkHistoryDTO2);
        remarkHistoryDTO2.setId(remarkHistoryDTO1.getId());
        assertThat(remarkHistoryDTO1).isEqualTo(remarkHistoryDTO2);
        remarkHistoryDTO2.setId(2L);
        assertThat(remarkHistoryDTO1).isNotEqualTo(remarkHistoryDTO2);
        remarkHistoryDTO1.setId(null);
        assertThat(remarkHistoryDTO1).isNotEqualTo(remarkHistoryDTO2);
    }
}
