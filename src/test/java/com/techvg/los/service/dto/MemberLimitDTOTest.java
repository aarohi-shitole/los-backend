package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemberLimitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberLimitDTO.class);
        MemberLimitDTO memberLimitDTO1 = new MemberLimitDTO();
        memberLimitDTO1.setId(1L);
        MemberLimitDTO memberLimitDTO2 = new MemberLimitDTO();
        assertThat(memberLimitDTO1).isNotEqualTo(memberLimitDTO2);
        memberLimitDTO2.setId(memberLimitDTO1.getId());
        assertThat(memberLimitDTO1).isEqualTo(memberLimitDTO2);
        memberLimitDTO2.setId(2L);
        assertThat(memberLimitDTO1).isNotEqualTo(memberLimitDTO2);
        memberLimitDTO1.setId(null);
        assertThat(memberLimitDTO1).isNotEqualTo(memberLimitDTO2);
    }
}
