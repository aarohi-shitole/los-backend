package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrgPrerequisiteDocMapperTest {

    private OrgPrerequisiteDocMapper orgPrerequisiteDocMapper;

    @BeforeEach
    public void setUp() {
        orgPrerequisiteDocMapper = new OrgPrerequisiteDocMapperImpl();
    }
}
