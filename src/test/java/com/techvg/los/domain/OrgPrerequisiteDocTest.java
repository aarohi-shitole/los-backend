package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrgPrerequisiteDocTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrgPrerequisiteDoc.class);
        OrgPrerequisiteDoc orgPrerequisiteDoc1 = new OrgPrerequisiteDoc();
        orgPrerequisiteDoc1.setId(1L);
        OrgPrerequisiteDoc orgPrerequisiteDoc2 = new OrgPrerequisiteDoc();
        orgPrerequisiteDoc2.setId(orgPrerequisiteDoc1.getId());
        assertThat(orgPrerequisiteDoc1).isEqualTo(orgPrerequisiteDoc2);
        orgPrerequisiteDoc2.setId(2L);
        assertThat(orgPrerequisiteDoc1).isNotEqualTo(orgPrerequisiteDoc2);
        orgPrerequisiteDoc1.setId(null);
        assertThat(orgPrerequisiteDoc1).isNotEqualTo(orgPrerequisiteDoc2);
    }
}
