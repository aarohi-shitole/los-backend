package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoanCatagoryMapperTest {

    private LoanCatagoryMapper loanCatagoryMapper;

    @BeforeEach
    public void setUp() {
        loanCatagoryMapper = new LoanCatagoryMapperImpl();
    }
}
