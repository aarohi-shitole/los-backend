package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MasterAgreementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MasterAgreementDTO.class);
        MasterAgreementDTO masterAgreementDTO1 = new MasterAgreementDTO();
        masterAgreementDTO1.setId(1L);
        MasterAgreementDTO masterAgreementDTO2 = new MasterAgreementDTO();
        assertThat(masterAgreementDTO1).isNotEqualTo(masterAgreementDTO2);
        masterAgreementDTO2.setId(masterAgreementDTO1.getId());
        assertThat(masterAgreementDTO1).isEqualTo(masterAgreementDTO2);
        masterAgreementDTO2.setId(2L);
        assertThat(masterAgreementDTO1).isNotEqualTo(masterAgreementDTO2);
        masterAgreementDTO1.setId(null);
        assertThat(masterAgreementDTO1).isNotEqualTo(masterAgreementDTO2);
    }
}
