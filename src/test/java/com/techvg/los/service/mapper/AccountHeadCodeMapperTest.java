package com.techvg.los.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountHeadCodeMapperTest {

    private AccountHeadCodeMapper accountHeadCodeMapper;

    @BeforeEach
    public void setUp() {
        accountHeadCodeMapper = new AccountHeadCodeMapperImpl();
    }
}
