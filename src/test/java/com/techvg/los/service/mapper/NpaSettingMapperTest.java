package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NpaSettingMapperTest {

    private NpaSettingMapper npaSettingMapper;

    @BeforeEach
    public void setUp() {
        npaSettingMapper = new NpaSettingMapperImpl();
    }
}
