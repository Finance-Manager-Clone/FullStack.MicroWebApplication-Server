package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountDetailsMapperTest {

    private AccountDetailsMapper accountDetailsMapper;

    @BeforeEach
    public void setUp() {
        accountDetailsMapper = new AccountDetailsMapperImpl();
    }
}
