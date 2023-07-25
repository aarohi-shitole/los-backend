package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InterestConfigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterestConfig.class);
        InterestConfig interestConfig1 = new InterestConfig();
        interestConfig1.setId(1L);
        InterestConfig interestConfig2 = new InterestConfig();
        interestConfig2.setId(interestConfig1.getId());
        assertThat(interestConfig1).isEqualTo(interestConfig2);
        interestConfig2.setId(2L);
        assertThat(interestConfig1).isNotEqualTo(interestConfig2);
        interestConfig1.setId(null);
        assertThat(interestConfig1).isNotEqualTo(interestConfig2);
    }
}
