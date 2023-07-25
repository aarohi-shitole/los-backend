package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployementDetailsMapperTest {

    private EmployementDetailsMapper employementDetailsMapper;

    @BeforeEach
    public void setUp() {
        employementDetailsMapper = new EmployementDetailsMapperImpl();
    }
}
