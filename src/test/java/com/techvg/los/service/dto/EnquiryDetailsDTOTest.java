package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnquiryDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnquiryDetailsDTO.class);
        EnquiryDetailsDTO enquiryDetailsDTO1 = new EnquiryDetailsDTO();
        enquiryDetailsDTO1.setId(1L);
        EnquiryDetailsDTO enquiryDetailsDTO2 = new EnquiryDetailsDTO();
        assertThat(enquiryDetailsDTO1).isNotEqualTo(enquiryDetailsDTO2);
        enquiryDetailsDTO2.setId(enquiryDetailsDTO1.getId());
        assertThat(enquiryDetailsDTO1).isEqualTo(enquiryDetailsDTO2);
        enquiryDetailsDTO2.setId(2L);
        assertThat(enquiryDetailsDTO1).isNotEqualTo(enquiryDetailsDTO2);
        enquiryDetailsDTO1.setId(null);
        assertThat(enquiryDetailsDTO1).isNotEqualTo(enquiryDetailsDTO2);
    }
}
