package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductAccountMapperTest {

    private ProductAccountMapper productAccountMapper;

    @BeforeEach
    public void setUp() {
        productAccountMapper = new ProductAccountMapperImpl();
    }
}
