package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrgPrerequisiteDocDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrgPrerequisiteDocDTO.class);
        OrgPrerequisiteDocDTO orgPrerequisiteDocDTO1 = new OrgPrerequisiteDocDTO();
        orgPrerequisiteDocDTO1.setId(1L);
        OrgPrerequisiteDocDTO orgPrerequisiteDocDTO2 = new OrgPrerequisiteDocDTO();
        assertThat(orgPrerequisiteDocDTO1).isNotEqualTo(orgPrerequisiteDocDTO2);
        orgPrerequisiteDocDTO2.setId(orgPrerequisiteDocDTO1.getId());
        assertThat(orgPrerequisiteDocDTO1).isEqualTo(orgPrerequisiteDocDTO2);
        orgPrerequisiteDocDTO2.setId(2L);
        assertThat(orgPrerequisiteDocDTO1).isNotEqualTo(orgPrerequisiteDocDTO2);
        orgPrerequisiteDocDTO1.setId(null);
        assertThat(orgPrerequisiteDocDTO1).isNotEqualTo(orgPrerequisiteDocDTO2);
    }
}
