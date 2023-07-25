package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InterestConfigMapperTest {

    private InterestConfigMapper interestConfigMapper;

    @BeforeEach
    public void setUp() {
        interestConfigMapper = new InterestConfigMapperImpl();
    }
}
