package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAccount.class);
        ProductAccount productAccount1 = new ProductAccount();
        productAccount1.setId(1L);
        ProductAccount productAccount2 = new ProductAccount();
        productAccount2.setId(productAccount1.getId());
        assertThat(productAccount1).isEqualTo(productAccount2);
        productAccount2.setId(2L);
        assertThat(productAccount1).isNotEqualTo(productAccount2);
        productAccount1.setId(null);
        assertThat(productAccount1).isNotEqualTo(productAccount2);
    }
}
