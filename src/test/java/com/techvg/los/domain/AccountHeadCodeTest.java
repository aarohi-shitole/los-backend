package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccountHeadCodeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountHeadCode.class);
        AccountHeadCode accountHeadCode1 = new AccountHeadCode();
        accountHeadCode1.setId(1L);
        AccountHeadCode accountHeadCode2 = new AccountHeadCode();
        accountHeadCode2.setId(accountHeadCode1.getId());
        assertThat(accountHeadCode1).isEqualTo(accountHeadCode2);
        accountHeadCode2.setId(2L);
        assertThat(accountHeadCode1).isNotEqualTo(accountHeadCode2);
        accountHeadCode1.setId(null);
        assertThat(accountHeadCode1).isNotEqualTo(accountHeadCode2);
    }
}
