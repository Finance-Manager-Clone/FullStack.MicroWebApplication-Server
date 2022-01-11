package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ProductAccount;
import com.mycompany.myapp.domain.enumeration.AccountType;
import com.mycompany.myapp.repository.ProductAccountRepository;
import com.mycompany.myapp.service.dto.ProductAccountDTO;
import com.mycompany.myapp.service.mapper.ProductAccountMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductAccountResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductAccountResourceIT {

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final AccountType DEFAULT_ACCOUN_TYPE = AccountType.Checking;
    private static final AccountType UPDATED_ACCOUN_TYPE = AccountType.Savings;

    private static final ZonedDateTime DEFAULT_OPENING_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_OPENING_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final BigDecimal DEFAULT_BALANCE = new BigDecimal(0);
    private static final BigDecimal UPDATED_BALANCE = new BigDecimal(1);

    private static final String ENTITY_API_URL = "/api/product-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductAccountRepository productAccountRepository;

    @Autowired
    private ProductAccountMapper productAccountMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductAccountMockMvc;

    private ProductAccount productAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAccount createEntity(EntityManager em) {
        ProductAccount productAccount = new ProductAccount()
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .accounType(DEFAULT_ACCOUN_TYPE)
            .openingDate(DEFAULT_OPENING_DATE)
            .balance(DEFAULT_BALANCE);
        return productAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAccount createUpdatedEntity(EntityManager em) {
        ProductAccount productAccount = new ProductAccount()
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accounType(UPDATED_ACCOUN_TYPE)
            .openingDate(UPDATED_OPENING_DATE)
            .balance(UPDATED_BALANCE);
        return productAccount;
    }

    @BeforeEach
    public void initTest() {
        productAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createProductAccount() throws Exception {
        int databaseSizeBeforeCreate = productAccountRepository.findAll().size();
        // Create the ProductAccount
        ProductAccountDTO productAccountDTO = productAccountMapper.toDto(productAccount);
        restProductAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productAccountDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductAccount in the database
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAccount testProductAccount = productAccountList.get(productAccountList.size() - 1);
        assertThat(testProductAccount.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testProductAccount.getAccounType()).isEqualTo(DEFAULT_ACCOUN_TYPE);
        assertThat(testProductAccount.getOpeningDate()).isEqualTo(DEFAULT_OPENING_DATE);
        assertThat(testProductAccount.getBalance()).isEqualByComparingTo(DEFAULT_BALANCE);
    }

    @Test
    @Transactional
    void createProductAccountWithExistingId() throws Exception {
        // Create the ProductAccount with an existing ID
        productAccount.setId(1L);
        ProductAccountDTO productAccountDTO = productAccountMapper.toDto(productAccount);

        int databaseSizeBeforeCreate = productAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAccount in the database
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = productAccountRepository.findAll().size();
        // set the field null
        productAccount.setAccountNumber(null);

        // Create the ProductAccount, which fails.
        ProductAccountDTO productAccountDTO = productAccountMapper.toDto(productAccount);

        restProductAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productAccountDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccounTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = productAccountRepository.findAll().size();
        // set the field null
        productAccount.setAccounType(null);

        // Create the ProductAccount, which fails.
        ProductAccountDTO productAccountDTO = productAccountMapper.toDto(productAccount);

        restProductAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productAccountDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOpeningDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = productAccountRepository.findAll().size();
        // set the field null
        productAccount.setOpeningDate(null);

        // Create the ProductAccount, which fails.
        ProductAccountDTO productAccountDTO = productAccountMapper.toDto(productAccount);

        restProductAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productAccountDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProductAccounts() throws Exception {
        // Initialize the database
        productAccountRepository.saveAndFlush(productAccount);

        // Get all the productAccountList
        restProductAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].accounType").value(hasItem(DEFAULT_ACCOUN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].openingDate").value(hasItem(sameInstant(DEFAULT_OPENING_DATE))))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(sameNumber(DEFAULT_BALANCE))));
    }

    @Test
    @Transactional
    void getProductAccount() throws Exception {
        // Initialize the database
        productAccountRepository.saveAndFlush(productAccount);

        // Get the productAccount
        restProductAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, productAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productAccount.getId().intValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.accounType").value(DEFAULT_ACCOUN_TYPE.toString()))
            .andExpect(jsonPath("$.openingDate").value(sameInstant(DEFAULT_OPENING_DATE)))
            .andExpect(jsonPath("$.balance").value(sameNumber(DEFAULT_BALANCE)));
    }

    @Test
    @Transactional
    void getNonExistingProductAccount() throws Exception {
        // Get the productAccount
        restProductAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductAccount() throws Exception {
        // Initialize the database
        productAccountRepository.saveAndFlush(productAccount);

        int databaseSizeBeforeUpdate = productAccountRepository.findAll().size();

        // Update the productAccount
        ProductAccount updatedProductAccount = productAccountRepository.findById(productAccount.getId()).get();
        // Disconnect from session so that the updates on updatedProductAccount are not directly saved in db
        em.detach(updatedProductAccount);
        updatedProductAccount
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accounType(UPDATED_ACCOUN_TYPE)
            .openingDate(UPDATED_OPENING_DATE)
            .balance(UPDATED_BALANCE);
        ProductAccountDTO productAccountDTO = productAccountMapper.toDto(updatedProductAccount);

        restProductAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAccountDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductAccount in the database
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeUpdate);
        ProductAccount testProductAccount = productAccountList.get(productAccountList.size() - 1);
        assertThat(testProductAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testProductAccount.getAccounType()).isEqualTo(UPDATED_ACCOUN_TYPE);
        assertThat(testProductAccount.getOpeningDate()).isEqualTo(UPDATED_OPENING_DATE);
        assertThat(testProductAccount.getBalance()).isEqualTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void putNonExistingProductAccount() throws Exception {
        int databaseSizeBeforeUpdate = productAccountRepository.findAll().size();
        productAccount.setId(count.incrementAndGet());

        // Create the ProductAccount
        ProductAccountDTO productAccountDTO = productAccountMapper.toDto(productAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAccount in the database
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductAccount() throws Exception {
        int databaseSizeBeforeUpdate = productAccountRepository.findAll().size();
        productAccount.setId(count.incrementAndGet());

        // Create the ProductAccount
        ProductAccountDTO productAccountDTO = productAccountMapper.toDto(productAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAccount in the database
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductAccount() throws Exception {
        int databaseSizeBeforeUpdate = productAccountRepository.findAll().size();
        productAccount.setId(count.incrementAndGet());

        // Create the ProductAccount
        ProductAccountDTO productAccountDTO = productAccountMapper.toDto(productAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAccountMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductAccount in the database
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductAccountWithPatch() throws Exception {
        // Initialize the database
        productAccountRepository.saveAndFlush(productAccount);

        int databaseSizeBeforeUpdate = productAccountRepository.findAll().size();

        // Update the productAccount using partial update
        ProductAccount partialUpdatedProductAccount = new ProductAccount();
        partialUpdatedProductAccount.setId(productAccount.getId());

        partialUpdatedProductAccount.accountNumber(UPDATED_ACCOUNT_NUMBER).balance(UPDATED_BALANCE);

        restProductAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductAccount))
            )
            .andExpect(status().isOk());

        // Validate the ProductAccount in the database
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeUpdate);
        ProductAccount testProductAccount = productAccountList.get(productAccountList.size() - 1);
        assertThat(testProductAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testProductAccount.getAccounType()).isEqualTo(DEFAULT_ACCOUN_TYPE);
        assertThat(testProductAccount.getOpeningDate()).isEqualTo(DEFAULT_OPENING_DATE);
        assertThat(testProductAccount.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void fullUpdateProductAccountWithPatch() throws Exception {
        // Initialize the database
        productAccountRepository.saveAndFlush(productAccount);

        int databaseSizeBeforeUpdate = productAccountRepository.findAll().size();

        // Update the productAccount using partial update
        ProductAccount partialUpdatedProductAccount = new ProductAccount();
        partialUpdatedProductAccount.setId(productAccount.getId());

        partialUpdatedProductAccount
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accounType(UPDATED_ACCOUN_TYPE)
            .openingDate(UPDATED_OPENING_DATE)
            .balance(UPDATED_BALANCE);

        restProductAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductAccount))
            )
            .andExpect(status().isOk());

        // Validate the ProductAccount in the database
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeUpdate);
        ProductAccount testProductAccount = productAccountList.get(productAccountList.size() - 1);
        assertThat(testProductAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testProductAccount.getAccounType()).isEqualTo(UPDATED_ACCOUN_TYPE);
        assertThat(testProductAccount.getOpeningDate()).isEqualTo(UPDATED_OPENING_DATE);
        assertThat(testProductAccount.getBalance()).isEqualByComparingTo(UPDATED_BALANCE);
    }

    @Test
    @Transactional
    void patchNonExistingProductAccount() throws Exception {
        int databaseSizeBeforeUpdate = productAccountRepository.findAll().size();
        productAccount.setId(count.incrementAndGet());

        // Create the ProductAccount
        ProductAccountDTO productAccountDTO = productAccountMapper.toDto(productAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productAccountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAccount in the database
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductAccount() throws Exception {
        int databaseSizeBeforeUpdate = productAccountRepository.findAll().size();
        productAccount.setId(count.incrementAndGet());

        // Create the ProductAccount
        ProductAccountDTO productAccountDTO = productAccountMapper.toDto(productAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAccount in the database
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductAccount() throws Exception {
        int databaseSizeBeforeUpdate = productAccountRepository.findAll().size();
        productAccount.setId(count.incrementAndGet());

        // Create the ProductAccount
        ProductAccountDTO productAccountDTO = productAccountMapper.toDto(productAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAccountMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductAccount in the database
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductAccount() throws Exception {
        // Initialize the database
        productAccountRepository.saveAndFlush(productAccount);

        int databaseSizeBeforeDelete = productAccountRepository.findAll().size();

        // Delete the productAccount
        restProductAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, productAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAccount> productAccountList = productAccountRepository.findAll();
        assertThat(productAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
