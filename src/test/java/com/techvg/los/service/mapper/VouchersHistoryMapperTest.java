package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VouchersHistoryMapperTest {

    private VouchersHistoryMapper vouchersHistoryMapper;

    @BeforeEach
    public void setUp() {
        vouchersHistoryMapper = new VouchersHistoryMapperImpl();
    }
}
