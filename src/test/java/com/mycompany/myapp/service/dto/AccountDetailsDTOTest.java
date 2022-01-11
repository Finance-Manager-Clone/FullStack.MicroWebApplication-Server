package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccountDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountDetailsDTO.class);
        AccountDetailsDTO accountDetailsDTO1 = new AccountDetailsDTO();
        accountDetailsDTO1.setId(1L);
        AccountDetailsDTO accountDetailsDTO2 = new AccountDetailsDTO();
        assertThat(accountDetailsDTO1).isNotEqualTo(accountDetailsDTO2);
        accountDetailsDTO2.setId(accountDetailsDTO1.getId());
        assertThat(accountDetailsDTO1).isEqualTo(accountDetailsDTO2);
        accountDetailsDTO2.setId(2L);
        assertThat(accountDetailsDTO1).isNotEqualTo(accountDetailsDTO2);
        accountDetailsDTO1.setId(null);
        assertThat(accountDetailsDTO1).isNotEqualTo(accountDetailsDTO2);
    }
}
