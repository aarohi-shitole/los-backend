package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberAssetsMapperTest {

    private MemberAssetsMapper memberAssetsMapper;

    @BeforeEach
    public void setUp() {
        memberAssetsMapper = new MemberAssetsMapperImpl();
    }
}
