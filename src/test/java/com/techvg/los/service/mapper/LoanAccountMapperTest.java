package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoanAccountMapperTest {

    private LoanAccountMapper loanAccountMapper;

    @BeforeEach
    public void setUp() {
        loanAccountMapper = new LoanAccountMapperImpl();
    }
}
