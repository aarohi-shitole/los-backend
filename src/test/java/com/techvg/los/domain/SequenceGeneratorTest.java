package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SequenceGeneratorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceGenerator.class);
        SequenceGenerator sequenceGenerator1 = new SequenceGenerator();
        sequenceGenerator1.setId(1L);
        SequenceGenerator sequenceGenerator2 = new SequenceGenerator();
        sequenceGenerator2.setId(sequenceGenerator1.getId());
        assertThat(sequenceGenerator1).isEqualTo(sequenceGenerator2);
        sequenceGenerator2.setId(2L);
        assertThat(sequenceGenerator1).isNotEqualTo(sequenceGenerator2);
        sequenceGenerator1.setId(null);
        assertThat(sequenceGenerator1).isNotEqualTo(sequenceGenerator2);
    }
}
