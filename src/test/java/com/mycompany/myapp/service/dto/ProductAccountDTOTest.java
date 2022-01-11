package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductAccountDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAccountDTO.class);
        ProductAccountDTO productAccountDTO1 = new ProductAccountDTO();
        productAccountDTO1.setId(1L);
        ProductAccountDTO productAccountDTO2 = new ProductAccountDTO();
        assertThat(productAccountDTO1).isNotEqualTo(productAccountDTO2);
        productAccountDTO2.setId(productAccountDTO1.getId());
        assertThat(productAccountDTO1).isEqualTo(productAccountDTO2);
        productAccountDTO2.setId(2L);
        assertThat(productAccountDTO1).isNotEqualTo(productAccountDTO2);
        productAccountDTO1.setId(null);
        assertThat(productAccountDTO1).isNotEqualTo(productAccountDTO2);
    }
}
