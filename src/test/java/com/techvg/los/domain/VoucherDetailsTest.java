package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoucherDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VoucherDetails.class);
        VoucherDetails voucherDetails1 = new VoucherDetails();
        voucherDetails1.setId(1L);
        VoucherDetails voucherDetails2 = new VoucherDetails();
        voucherDetails2.setId(voucherDetails1.getId());
        assertThat(voucherDetails1).isEqualTo(voucherDetails2);
        voucherDetails2.setId(2L);
        assertThat(voucherDetails1).isNotEqualTo(voucherDetails2);
        voucherDetails1.setId(null);
        assertThat(voucherDetails1).isNotEqualTo(voucherDetails2);
    }
}
