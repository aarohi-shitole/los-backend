package com.techvg.los.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.los.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccountHeadCodeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountHeadCodeDTO.class);
        AccountHeadCodeDTO accountHeadCodeDTO1 = new AccountHeadCodeDTO();
        accountHeadCodeDTO1.setId(1L);
        AccountHeadCodeDTO accountHeadCodeDTO2 = new AccountHeadCodeDTO();
        assertThat(accountHeadCodeDTO1).isNotEqualTo(accountHeadCodeDTO2);
        accountHeadCodeDTO2.setId(accountHeadCodeDTO1.getId());
        assertThat(accountHeadCodeDTO1).isEqualTo(accountHeadCodeDTO2);
        accountHeadCodeDTO2.setId(2L);
        assertThat(accountHeadCodeDTO1).isNotEqualTo(accountHeadCodeDTO2);
        accountHeadCodeDTO1.setId(null);
        assertThat(accountHeadCodeDTO1).isNotEqualTo(accountHeadCodeDTO2);
    }
}
