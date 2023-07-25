package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RemarkHistoryMapperTest {

    private RemarkHistoryMapper remarkHistoryMapper;

    @BeforeEach
    public void setUp() {
        remarkHistoryMapper = new RemarkHistoryMapperImpl();
    }
}
