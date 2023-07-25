package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LoanCatagoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoanCatagory.class);
        LoanCatagory loanCatagory1 = new LoanCatagory();
        loanCatagory1.setId(1L);
        LoanCatagory loanCatagory2 = new LoanCatagory();
        loanCatagory2.setId(loanCatagory1.getId());
        assertThat(loanCatagory1).isEqualTo(loanCatagory2);
        loanCatagory2.setId(2L);
        assertThat(loanCatagory1).isNotEqualTo(loanCatagory2);
        loanCatagory1.setId(null);
        assertThat(loanCatagory1).isNotEqualTo(loanCatagory2);
    }
}
