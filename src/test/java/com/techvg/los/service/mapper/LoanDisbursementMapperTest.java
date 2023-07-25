package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoanDisbursementMapperTest {

    private LoanDisbursementMapper loanDisbursementMapper;

    @BeforeEach
    public void setUp() {
        loanDisbursementMapper = new LoanDisbursementMapperImpl();
    }
}
