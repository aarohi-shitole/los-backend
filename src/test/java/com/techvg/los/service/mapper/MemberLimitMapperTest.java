package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberLimitMapperTest {

    private MemberLimitMapper memberLimitMapper;

    @BeforeEach
    public void setUp() {
        memberLimitMapper = new MemberLimitMapperImpl();
    }
}
