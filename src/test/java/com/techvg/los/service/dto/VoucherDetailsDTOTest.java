package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoucherDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherDetailsDTO.class);
        VoucherDetailsDTO voucherDetailsDTO1 = new VoucherDetailsDTO();
        voucherDetailsDTO1.setId(1L);
        VoucherDetailsDTO voucherDetailsDTO2 = new VoucherDetailsDTO();
        assertThat(voucherDetailsDTO1).isNotEqualTo(voucherDetailsDTO2);
        voucherDetailsDTO2.setId(voucherDetailsDTO1.getId());
        assertThat(voucherDetailsDTO1).isEqualTo(voucherDetailsDTO2);
        voucherDetailsDTO2.setId(2L);
        assertThat(voucherDetailsDTO1).isNotEqualTo(voucherDetailsDTO2);
        voucherDetailsDTO1.setId(null);
        assertThat(voucherDetailsDTO1).isNotEqualTo(voucherDetailsDTO2);
    }
}
