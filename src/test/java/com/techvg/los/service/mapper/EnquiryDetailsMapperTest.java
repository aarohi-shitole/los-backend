package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnquiryDetailsMapperTest {

    private EnquiryDetailsMapper enquiryDetailsMapper;

    @BeforeEach
    public void setUp() {
        enquiryDetailsMapper = new EnquiryDetailsMapperImpl();
    }
}
