package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.AccountDetails;
import com.mycompany.myapp.repository.AccountDetailsRepository;
import com.mycompany.myapp.service.dto.AccountDetailsDTO;
import com.mycompany.myapp.service.mapper.AccountDetailsMapper;
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
 * Integration tests for the {@link AccountDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccountDetailsResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "BZVEf#@1./<Em5";
    private static final String UPDATED_EMAIL = "e;+]n!@v4;V.PuY";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/account-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    @Autowired
    private AccountDetailsMapper accountDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountDetailsMockMvc;

    private AccountDetails accountDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountDetails createEntity(EntityManager em) {
        AccountDetails accountDetails = new AccountDetails()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY);
        return accountDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountDetails createUpdatedEntity(EntityManager em) {
        AccountDetails accountDetails = new AccountDetails()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY);
        return accountDetails;
    }

    @BeforeEach
    public void initTest() {
        accountDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createAccountDetails() throws Exception {
        int databaseSizeBeforeCreate = accountDetailsRepository.findAll().size();
        // Create the AccountDetails
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(accountDetails);
        restAccountDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        AccountDetails testAccountDetails = accountDetailsList.get(accountDetailsList.size() - 1);
        assertThat(testAccountDetails.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testAccountDetails.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testAccountDetails.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testAccountDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAccountDetails.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testAccountDetails.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAccountDetails.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void createAccountDetailsWithExistingId() throws Exception {
        // Create the AccountDetails with an existing ID
        accountDetails.setId(1L);
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(accountDetails);

        int databaseSizeBeforeCreate = accountDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountDetailsRepository.findAll().size();
        // set the field null
        accountDetails.setFirstName(null);

        // Create the AccountDetails, which fails.
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(accountDetails);

        restAccountDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountDetailsRepository.findAll().size();
        // set the field null
        accountDetails.setLastName(null);

        // Create the AccountDetails, which fails.
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(accountDetails);

        restAccountDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountDetailsRepository.findAll().size();
        // set the field null
        accountDetails.setPhoneNumber(null);

        // Create the AccountDetails, which fails.
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(accountDetails);

        restAccountDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountDetailsRepository.findAll().size();
        // set the field null
        accountDetails.setEmail(null);

        // Create the AccountDetails, which fails.
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(accountDetails);

        restAccountDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountDetailsRepository.findAll().size();
        // set the field null
        accountDetails.setAddress(null);

        // Create the AccountDetails, which fails.
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(accountDetails);

        restAccountDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountDetailsRepository.findAll().size();
        // set the field null
        accountDetails.setCity(null);

        // Create the AccountDetails, which fails.
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(accountDetails);

        restAccountDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountDetailsRepository.findAll().size();
        // set the field null
        accountDetails.setCountry(null);

        // Create the AccountDetails, which fails.
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(accountDetails);

        restAccountDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAccountDetails() throws Exception {
        // Initialize the database
        accountDetailsRepository.saveAndFlush(accountDetails);

        // Get all the accountDetailsList
        restAccountDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }

    @Test
    @Transactional
    void getAccountDetails() throws Exception {
        // Initialize the database
        accountDetailsRepository.saveAndFlush(accountDetails);

        // Get the accountDetails
        restAccountDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, accountDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accountDetails.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY));
    }

    @Test
    @Transactional
    void getNonExistingAccountDetails() throws Exception {
        // Get the accountDetails
        restAccountDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAccountDetails() throws Exception {
        // Initialize the database
        accountDetailsRepository.saveAndFlush(accountDetails);

        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();

        // Update the accountDetails
        AccountDetails updatedAccountDetails = accountDetailsRepository.findById(accountDetails.getId()).get();
        // Disconnect from session so that the updates on updatedAccountDetails are not directly saved in db
        em.detach(updatedAccountDetails);
        updatedAccountDetails
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY);
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(updatedAccountDetails);

        restAccountDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
        AccountDetails testAccountDetails = accountDetailsList.get(accountDetailsList.size() - 1);
        assertThat(testAccountDetails.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAccountDetails.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAccountDetails.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testAccountDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAccountDetails.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAccountDetails.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAccountDetails.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void putNonExistingAccountDetails() throws Exception {
        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();
        accountDetails.setId(count.incrementAndGet());

        // Create the AccountDetails
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(accountDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccountDetails() throws Exception {
        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();
        accountDetails.setId(count.incrementAndGet());

        // Create the AccountDetails
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(accountDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccountDetails() throws Exception {
        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();
        accountDetails.setId(count.incrementAndGet());

        // Create the AccountDetails
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(accountDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccountDetailsWithPatch() throws Exception {
        // Initialize the database
        accountDetailsRepository.saveAndFlush(accountDetails);

        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();

        // Update the accountDetails using partial update
        AccountDetails partialUpdatedAccountDetails = new AccountDetails();
        partialUpdatedAccountDetails.setId(accountDetails.getId());

        partialUpdatedAccountDetails
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .city(UPDATED_CITY);

        restAccountDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountDetails))
            )
            .andExpect(status().isOk());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
        AccountDetails testAccountDetails = accountDetailsList.get(accountDetailsList.size() - 1);
        assertThat(testAccountDetails.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAccountDetails.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAccountDetails.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testAccountDetails.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAccountDetails.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testAccountDetails.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAccountDetails.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    void fullUpdateAccountDetailsWithPatch() throws Exception {
        // Initialize the database
        accountDetailsRepository.saveAndFlush(accountDetails);

        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();

        // Update the accountDetails using partial update
        AccountDetails partialUpdatedAccountDetails = new AccountDetails();
        partialUpdatedAccountDetails.setId(accountDetails.getId());

        partialUpdatedAccountDetails
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY);

        restAccountDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccountDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccountDetails))
            )
            .andExpect(status().isOk());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
        AccountDetails testAccountDetails = accountDetailsList.get(accountDetailsList.size() - 1);
        assertThat(testAccountDetails.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testAccountDetails.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testAccountDetails.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testAccountDetails.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAccountDetails.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testAccountDetails.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAccountDetails.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void patchNonExistingAccountDetails() throws Exception {
        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();
        accountDetails.setId(count.incrementAndGet());

        // Create the AccountDetails
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(accountDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accountDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccountDetails() throws Exception {
        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();
        accountDetails.setId(count.incrementAndGet());

        // Create the AccountDetails
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(accountDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccountDetails() throws Exception {
        int databaseSizeBeforeUpdate = accountDetailsRepository.findAll().size();
        accountDetails.setId(count.incrementAndGet());

        // Create the AccountDetails
        AccountDetailsDTO accountDetailsDTO = accountDetailsMapper.toDto(accountDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccountDetails in the database
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccountDetails() throws Exception {
        // Initialize the database
        accountDetailsRepository.saveAndFlush(accountDetails);

        int databaseSizeBeforeDelete = accountDetailsRepository.findAll().size();

        // Delete the accountDetails
        restAccountDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, accountDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccountDetails> accountDetailsList = accountDetailsRepository.findAll();
        assertThat(accountDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
