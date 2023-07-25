package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VouchersHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VouchersHistoryDTO.class);
        VouchersHistoryDTO vouchersHistoryDTO1 = new VouchersHistoryDTO();
        vouchersHistoryDTO1.setId(1L);
        VouchersHistoryDTO vouchersHistoryDTO2 = new VouchersHistoryDTO();
        assertThat(vouchersHistoryDTO1).isNotEqualTo(vouchersHistoryDTO2);
        vouchersHistoryDTO2.setId(vouchersHistoryDTO1.getId());
        assertThat(vouchersHistoryDTO1).isEqualTo(vouchersHistoryDTO2);
        vouchersHistoryDTO2.setId(2L);
        assertThat(vouchersHistoryDTO1).isNotEqualTo(vouchersHistoryDTO2);
        vouchersHistoryDTO1.setId(null);
        assertThat(vouchersHistoryDTO1).isNotEqualTo(vouchersHistoryDTO2);
    }
}
