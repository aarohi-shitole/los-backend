package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoanRepaymentDetailsMapperTest {

    private LoanRepaymentDetailsMapper loanRepaymentDetailsMapper;

    @BeforeEach
    public void setUp() {
        loanRepaymentDetailsMapper = new LoanRepaymentDetailsMapperImpl();
    }
}
