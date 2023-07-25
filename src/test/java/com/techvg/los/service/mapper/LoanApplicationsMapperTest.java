package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoanApplicationsMapperTest {

    private LoanApplicationsMapper loanApplicationsMapper;

    @BeforeEach
    public void setUp() {
        loanApplicationsMapper = new LoanApplicationsMapperImpl();
    }
}
