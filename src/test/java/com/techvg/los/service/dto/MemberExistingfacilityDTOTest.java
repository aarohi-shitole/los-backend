package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MemberExistingfacilityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MemberExistingfacilityDTO.class);
        MemberExistingfacilityDTO memberExistingfacilityDTO1 = new MemberExistingfacilityDTO();
        memberExistingfacilityDTO1.setId(1L);
        MemberExistingfacilityDTO memberExistingfacilityDTO2 = new MemberExistingfacilityDTO();
        assertThat(memberExistingfacilityDTO1).isNotEqualTo(memberExistingfacilityDTO2);
        memberExistingfacilityDTO2.setId(memberExistingfacilityDTO1.getId());
        assertThat(memberExistingfacilityDTO1).isEqualTo(memberExistingfacilityDTO2);
        memberExistingfacilityDTO2.setId(2L);
        assertThat(memberExistingfacilityDTO1).isNotEqualTo(memberExistingfacilityDTO2);
        memberExistingfacilityDTO1.setId(null);
        assertThat(memberExistingfacilityDTO1).isNotEqualTo(memberExistingfacilityDTO2);
    }
}
