package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VouchersMapperTest {

    private VouchersMapper vouchersMapper;

    @BeforeEach
    public void setUp() {
        vouchersMapper = new VouchersMapperImpl();
    }
}
