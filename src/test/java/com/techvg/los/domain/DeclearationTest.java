package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeclearationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Declearation.class);
        Declearation declearation1 = new Declearation();
        declearation1.setId(1L);
        Declearation declearation2 = new Declearation();
        declearation2.setId(declearation1.getId());
        assertThat(declearation1).isEqualTo(declearation2);
        declearation2.setId(2L);
        assertThat(declearation1).isNotEqualTo(declearation2);
        declearation1.setId(null);
        assertThat(declearation1).isNotEqualTo(declearation2);
    }
}
