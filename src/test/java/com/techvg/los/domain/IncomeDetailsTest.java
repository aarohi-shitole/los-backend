package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IncomeDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncomeDetails.class);
        IncomeDetails incomeDetails1 = new IncomeDetails();
        incomeDetails1.setId(1L);
        IncomeDetails incomeDetails2 = new IncomeDetails();
        incomeDetails2.setId(incomeDetails1.getId());
        assertThat(incomeDetails1).isEqualTo(incomeDetails2);
        incomeDetails2.setId(2L);
        assertThat(incomeDetails1).isNotEqualTo(incomeDetails2);
        incomeDetails1.setId(null);
        assertThat(incomeDetails1).isNotEqualTo(incomeDetails2);
    }
}
