package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GuarantorMapperTest {

    private GuarantorMapper guarantorMapper;

    @BeforeEach
    public void setUp() {
        guarantorMapper = new GuarantorMapperImpl();
    }
}
