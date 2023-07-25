package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SequenceGeneratorMapperTest {

    private SequenceGeneratorMapper sequenceGeneratorMapper;

    @BeforeEach
    public void setUp() {
        sequenceGeneratorMapper = new SequenceGeneratorMapperImpl();
    }
}
