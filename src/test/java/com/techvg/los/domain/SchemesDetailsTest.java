package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SchemesDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchemesDetails.class);
        SchemesDetails schemesDetails1 = new SchemesDetails();
        schemesDetails1.setId(1L);
        SchemesDetails schemesDetails2 = new SchemesDetails();
        schemesDetails2.setId(schemesDetails1.getId());
        assertThat(schemesDetails1).isEqualTo(schemesDetails2);
        schemesDetails2.setId(2L);
        assertThat(schemesDetails1).isNotEqualTo(schemesDetails2);
        schemesDetails1.setId(null);
        assertThat(schemesDetails1).isNotEqualTo(schemesDetails2);
    }
}
