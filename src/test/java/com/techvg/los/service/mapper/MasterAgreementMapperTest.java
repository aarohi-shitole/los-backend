package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MasterAgreementMapperTest {

    private MasterAgreementMapper masterAgreementMapper;

    @BeforeEach
    public void setUp() {
        masterAgreementMapper = new MasterAgreementMapperImpl();
    }
}
