package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GuarantorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Guarantor.class);
        Guarantor guarantor1 = new Guarantor();
        guarantor1.setId(1L);
        Guarantor guarantor2 = new Guarantor();
        guarantor2.setId(guarantor1.getId());
        assertThat(guarantor1).isEqualTo(guarantor2);
        guarantor2.setId(2L);
        assertThat(guarantor1).isNotEqualTo(guarantor2);
        guarantor1.setId(null);
        assertThat(guarantor1).isNotEqualTo(guarantor2);
    }
}
