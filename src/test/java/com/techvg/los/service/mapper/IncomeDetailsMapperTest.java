package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IncomeDetailsMapperTest {

    private IncomeDetailsMapper incomeDetailsMapper;

    @BeforeEach
    public void setUp() {
        incomeDetailsMapper = new IncomeDetailsMapperImpl();
    }
}
