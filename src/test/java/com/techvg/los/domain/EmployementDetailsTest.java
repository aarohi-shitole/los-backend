package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployementDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployementDetails.class);
        EmployementDetails employementDetails1 = new EmployementDetails();
        employementDetails1.setId(1L);
        EmployementDetails employementDetails2 = new EmployementDetails();
        employementDetails2.setId(employementDetails1.getId());
        assertThat(employementDetails1).isEqualTo(employementDetails2);
        employementDetails2.setId(2L);
        assertThat(employementDetails1).isNotEqualTo(employementDetails2);
        employementDetails1.setId(null);
        assertThat(employementDetails1).isNotEqualTo(employementDetails2);
    }
}
