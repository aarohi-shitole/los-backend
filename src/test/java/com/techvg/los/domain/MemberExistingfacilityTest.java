package com.techvg.los.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemberExistingfacilityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberExistingfacility.class);
        MemberExistingfacility memberExistingfacility1 = new MemberExistingfacility();
        memberExistingfacility1.setId(1L);
        MemberExistingfacility memberExistingfacility2 = new MemberExistingfacility();
        memberExistingfacility2.setId(memberExistingfacility1.getId());
        assertThat(memberExistingfacility1).isEqualTo(memberExistingfacility2);
        memberExistingfacility2.setId(2L);
        assertThat(memberExistingfacility1).isNotEqualTo(memberExistingfacility2);
        memberExistingfacility1.setId(null);
        assertThat(memberExistingfacility1).isNotEqualTo(memberExistingfacility2);
    }
}
