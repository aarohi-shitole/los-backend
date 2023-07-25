package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AmortizationDetailsMapperTest {

    private AmortizationDetailsMapper amortizationDetailsMapper;

    @BeforeEach
    public void setUp() {
        amortizationDetailsMapper = new AmortizationDetailsMapperImpl();
    }
}
