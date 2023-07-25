package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoucherDetailsMapperTest {

    private VoucherDetailsMapper voucherDetailsMapper;

    @BeforeEach
    public void setUp() {
        voucherDetailsMapper = new VoucherDetailsMapperImpl();
    }
}
