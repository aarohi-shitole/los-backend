package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MasterAgreementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MasterAgreement.class);
        MasterAgreement masterAgreement1 = new MasterAgreement();
        masterAgreement1.setId(1L);
        MasterAgreement masterAgreement2 = new MasterAgreement();
        masterAgreement2.setId(masterAgreement1.getId());
        assertThat(masterAgreement1).isEqualTo(masterAgreement2);
        masterAgreement2.setId(2L);
        assertThat(masterAgreement1).isNotEqualTo(masterAgreement2);
        masterAgreement1.setId(null);
        assertThat(masterAgreement1).isNotEqualTo(masterAgreement2);
    }
}
