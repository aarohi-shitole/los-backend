package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EpayMapperTest {

    private EpayMapper epayMapper;

    @BeforeEach
    public void setUp() {
        epayMapper = new EpayMapperImpl();
    }
}
