package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SequenceGeneratorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SequenceGeneratorDTO.class);
        SequenceGeneratorDTO sequenceGeneratorDTO1 = new SequenceGeneratorDTO();
        sequenceGeneratorDTO1.setId(1L);
        SequenceGeneratorDTO sequenceGeneratorDTO2 = new SequenceGeneratorDTO();
        assertThat(sequenceGeneratorDTO1).isNotEqualTo(sequenceGeneratorDTO2);
        sequenceGeneratorDTO2.setId(sequenceGeneratorDTO1.getId());
        assertThat(sequenceGeneratorDTO1).isEqualTo(sequenceGeneratorDTO2);
        sequenceGeneratorDTO2.setId(2L);
        assertThat(sequenceGeneratorDTO1).isNotEqualTo(sequenceGeneratorDTO2);
        sequenceGeneratorDTO1.setId(null);
        assertThat(sequenceGeneratorDTO1).isNotEqualTo(sequenceGeneratorDTO2);
    }
}
