package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemberLimitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberLimit.class);
        MemberLimit memberLimit1 = new MemberLimit();
        memberLimit1.setId(1L);
        MemberLimit memberLimit2 = new MemberLimit();
        memberLimit2.setId(memberLimit1.getId());
        assertThat(memberLimit1).isEqualTo(memberLimit2);
        memberLimit2.setId(2L);
        assertThat(memberLimit1).isNotEqualTo(memberLimit2);
        memberLimit1.setId(null);
        assertThat(memberLimit1).isNotEqualTo(memberLimit2);
    }
}
